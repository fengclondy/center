package cn.htd.marketcenter.service.impl.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.service.BuyerInterestChangeService;
import cn.htd.marketcenter.service.BuyerPromotionDeal;
import cn.htd.marketcenter.service.handle.BuyerCouponHandle;
import cn.htd.marketcenter.service.handle.BuyerTimelimitedHandle;
import cn.htd.marketcenter.service.handle.PromotionRedisHandle;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("buyerInterestChangeService")
public class BuyerInterestChangeServiceImpl implements BuyerInterestChangeService {

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisHandle promotionRedisHandle;

    @Resource
    private BuyerCouponHandle buyerCouponHandle;

    @Resource
    private BuyerTimelimitedHandle buyerTimelimitedHandle;

    /**
     * 取得促销对象的Handle
     *
     * @param promotionType
     * @return
     */
    private BuyerPromotionDeal getHandle(String promotionType) {
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
                .equals(promotionType)) {
            return buyerCouponHandle;
        } else if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
                .equals(promotionType)) {
            return buyerTimelimitedHandle;
        }
        return null;
    }

    /**
     * 批量执行锁定、释放、扣减、回滚会员优惠券、秒杀活动
     *
     * @param messageId
     * @param orderItemPromotionList
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void saveBuyerPromotionChange(String messageId, List<OrderItemPromotionDTO> orderItemPromotionList)
            throws MarketCenterBusinessException, Exception {
        BuyerPromotionDeal promotionDealHandle = null;
        String promotionType = "";
        String promotionChangeType = "";

        if (orderItemPromotionList != null && !orderItemPromotionList.isEmpty()) {
            promotionType = orderItemPromotionList.get(0).getPromotionType();
            promotionChangeType = orderItemPromotionList.get(0).getPromoitionChangeType();
            promotionDealHandle = getHandle(promotionType);
            if (promotionDealHandle == null) {
                return;
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK).equals(promotionChangeType)) {
                promotionDealHandle.rollbackBuyerPromotion(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE).equals(promotionChangeType)) {
                promotionDealHandle.releaseBuyerPromotion(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE).equals(promotionChangeType)) {
                promotionDealHandle.reduceBuyerPromotion(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(promotionChangeType)) {
                promotionDealHandle.reserveBuyerPromotion(messageId, orderItemPromotionList);
            }
        }
    }

    private ExecuteResult<String> changeBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<OrderItemPromotionDTO> itemPromotionList = new ArrayList<OrderItemPromotionDTO>();
        String lockKey = "";
        List<String> lockKeyList = new ArrayList<String>();
        String oldPromotionType = "";
        String oldBuyerCode = "";
        String buyerCode = "";
        String reverseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String couponType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_COUPON);
        String timelimitedType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        try {
            for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
                if (StringUtils.isEmpty(promotionDTO.getPromotionType())) {
                    continue;
                }
                // 输入DTO的验证
                ValidateResult validateResult = ValidationUtils.validateEntity(promotionDTO);
                // 有错误信息时返回错误信息
                if (validateResult.isHasErrors()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                            validateResult.getErrorMsg());
                }
                if (couponType.equals(promotionDTO.getPromotionType())) {
                    if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
                        continue;
                    }
                    if (StringUtils.isEmpty(promotionDTO.getOrderNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号为空");
                    }
                    if (StringUtils.isEmpty(promotionDTO.getOrderItemNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的子订单编号为空");
                    }
                    if (StringUtils.isEmpty(promotionDTO.getCouponCode())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的会员优惠券编号为空");
                    }
                    lockKey = promotionDTO.getOrderItemNo();
                } else if (timelimitedType.equals(promotionDTO.getPromotionType())) {
                    if (StringUtils.isEmpty(promotionDTO.getSeckillLockNo())
                            && StringUtils.isEmpty(promotionDTO.getOrderNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号或秒杀锁定预占订单号都为空");
                    }
                    lockKey = promotionDTO.getOrderNo();
                    if (reverseStatus.equals(promotionDTO.getPromoitionChangeType())) {
                        lockKey = promotionDTO.getSeckillLockNo();
                    }
                }
                if (StringUtils.isEmpty(oldPromotionType)) {
                    oldPromotionType = promotionDTO.getPromotionType();
                } else {
                    if (!oldPromotionType.equals(promotionDTO.getPromotionType())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "会员促销活动处理一次只能处理一种业务（优惠惠券或秒杀）");
                    }
                }
                buyerCode = promotionDTO.getBuyerCode();
                if (StringUtils.isEmpty(oldBuyerCode)) {
                    oldBuyerCode = buyerCode;
                }
                if (!buyerCode.equals(oldBuyerCode)) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                            "会员促销活动处理一次只能处理一个会员的业务");
                }
                if (!promotionRedisHandle.lockRedisPromotionAction(messageId, lockKey)) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.LOCK_FAIL_ERROR,
                            "会员促销活动正在处理中不能重复处理 messageId:" + messageId + " 参数:" + JSON.toJSONString(promotionDTO));
                }
                itemPromotionList.add(promotionDTO);
                lockKeyList.add(lockKey);
            }
            saveBuyerPromotionChange(messageId, itemPromotionList);
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            promotionRedisHandle.unlockRedisPromotionAction(lockKeyList);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> reserveBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<OrderItemPromotionDTO> targetPromotionList = new ArrayList<OrderItemPromotionDTO>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String couponType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_COUPON);
        String timelimitedType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
            if (couponType.equals(promotionDTO.getPromotionType())) {
                if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
                    continue;
                }
                if (StringUtils.isEmpty(promotionDTO.getLevelCode())) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                            "促销活动ID:" + promotionDTO.getPromotionId() + " 的层级编码为空");
                }
            } else if (timelimitedType.equals(promotionDTO.getPromotionType())) {
                if (promotionDTO.getQuantity().intValue() < 1) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                            "促销活动ID:" + promotionDTO.getPromotionId() + " 的秒杀商品数量不能小于1");
                }
            }
            promotionDTO.setPromoitionChangeType(status);
            targetPromotionList.add(promotionDTO);
        }
        result = changeBuyerPromotion(messageId, targetPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> reduceBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = "";
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE);
        for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
            promotionDTO.setPromoitionChangeType(status);
        }
        result = changeBuyerPromotion(messageId, orderItemPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> releaseBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = "";
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
        for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
            promotionDTO.setPromoitionChangeType(status);
        }
        result = changeBuyerPromotion(messageId, orderItemPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> rollbackBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = "";
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK);
        for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
            promotionDTO.setPromoitionChangeType(status);
        }
        result = changeBuyerPromotion(messageId, orderItemPromotionList);
        return result;
    }
}

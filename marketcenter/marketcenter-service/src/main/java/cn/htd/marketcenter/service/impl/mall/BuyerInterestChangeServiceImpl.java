package cn.htd.marketcenter.service.impl.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.htd.marketcenter.service.handle.BuyerLimitedDiscountHandle;
import cn.htd.marketcenter.service.handle.BuyerTimelimitedHandle;
import cn.htd.marketcenter.service.handle.PromotionRedisHandle;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
    @Resource
    private BuyerLimitedDiscountHandle buyerLimitedDiscountHandle;
    //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----

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
            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT).equals(promotionType)) {
            return buyerLimitedDiscountHandle;
            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
        }
        return null;
    }

    /**
     * 批量执行锁定、释放、扣减、回滚会员优惠券、秒杀活动
     *
     * @param messageId
     * @param promotionType
     * @param promotionChangeType
     * @param orderItemPromotionList
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
    private void saveBuyerPromotionChange(String messageId, String promotionType, String promotionChangeType,
            List<OrderItemPromotionDTO> orderItemPromotionList) throws MarketCenterBusinessException, Exception {
        BuyerPromotionDeal promotionDealHandle = null;
        promotionDealHandle = getHandle(promotionType);
        if (promotionDealHandle == null) {
            return;
        }
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK).equals(promotionChangeType)) {
            promotionDealHandle.rollbackBuyerPromotion(messageId, orderItemPromotionList);
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE).equals(promotionChangeType)) {
            promotionDealHandle.releaseBuyerPromotion(messageId, orderItemPromotionList);
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE).equals(promotionChangeType)) {
            promotionDealHandle.reduceBuyerPromotion(messageId, orderItemPromotionList);
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(promotionChangeType)) {
            promotionDealHandle.reserveBuyerPromotion(messageId, orderItemPromotionList);
        }
    }
    //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----

    /**
     * 批量处理会员优惠券、秒杀
     *
     * @param messageId
     * @param promotionChangeType
     * @param orderItemPromotionList
     * @return
     */
    private ExecuteResult<String> changeBuyerPromotion(String messageId, String promotionChangeType, List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<OrderItemPromotionDTO> itemPromotionList = null;
        Map<String, List<OrderItemPromotionDTO>> itemPromotionMap = new HashMap<String, List<OrderItemPromotionDTO>>();
        String promotionType = "";
        String lockKey = "";
        List<String> lockKeyList = new ArrayList<String>();
        String oldBuyerCode = "";
        String buyerCode = "";
        String reverseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String couponType = dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON);
        String timelimitedType = dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        try {
            for (OrderItemPromotionDTO promotionDTO : orderItemPromotionList) {
                promotionType = promotionDTO.getPromotionType();
                if (StringUtils.isEmpty(promotionType)) {
                    continue;
                }
                // 输入DTO的验证
                ValidateResult validateResult = ValidationUtils.validateEntity(promotionDTO);
                // 有错误信息时返回错误信息
                if (validateResult.isHasErrors()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                            validateResult.getErrorMsg());
                }
                //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
//                if (couponType.equals(promotionDTO.getPromotionType())) {
//                    if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
//                        continue;
//                    }
//                    if (StringUtils.isEmpty(promotionDTO.getOrderNo())) {
//                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号为空");
//                    }
//                    if (StringUtils.isEmpty(promotionDTO.getOrderItemNo())) {
//                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的子订单编号为空");
//                    }
//                    if (StringUtils.isEmpty(promotionDTO.getCouponCode())) {
//                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的会员优惠券编号为空");
//                    }
//                    lockKey = promotionDTO.getOrderItemNo();
//                } else if (timelimitedType.equals(promotionDTO.getPromotionType())) {
//                    if (StringUtils.isEmpty(promotionDTO.getSeckillLockNo())
//                            && StringUtils.isEmpty(promotionDTO.getOrderNo())) {
//                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号或秒杀锁定预占订单号都为空");
//                    }
//                    lockKey = promotionDTO.getOrderNo();
//                    if (reverseStatus.equals(promotionDTO.getPromoitionChangeType())) {
//                        lockKey = promotionDTO.getSeckillLockNo();
//                    }
//                }
//                if (StringUtils.isEmpty(oldPromotionType)) {
//                    oldPromotionType = promotionDTO.getPromotionType();
//                } else {
//                    if (!oldPromotionType.equals(promotionDTO.getPromotionType())) {
//                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                                "会员促销活动处理一次只能处理一种业务（优惠惠券或秒杀）");
//                    }
//                }
                promotionDTO.setPromoitionChangeType(promotionChangeType);
                if (timelimitedType.equals(promotionType)) {
                    if (StringUtils.isEmpty(promotionDTO.getSeckillLockNo()) && StringUtils
                            .isEmpty(promotionDTO.getOrderNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号或秒杀锁定预占订单号都为空");
                    }
                    lockKey = promotionDTO.getOrderNo();
                    if (reverseStatus.equals(promotionChangeType)) {
                        lockKey = promotionDTO.getSeckillLockNo();
                    }
                } else {
                    if (StringUtils.isEmpty(promotionDTO.getOrderNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号为空");
                    }
                    if (StringUtils.isEmpty(promotionDTO.getOrderItemNo())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的子订单编号为空");
                    }
                    if (couponType.equals(promotionType)) {
                        if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
                            continue;
                        }
                        if (StringUtils.isEmpty(promotionDTO.getCouponCode())) {
                            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                                    "促销活动ID:" + promotionDTO.getPromotionId() + " 的会员优惠券编号为空");
                        }
                    }
                    lockKey = promotionDTO.getOrderItemNo();
                }
                //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
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
                if (itemPromotionMap.containsKey(promotionType)) {
                    itemPromotionList = itemPromotionMap.get(promotionType);
                } else {
                    itemPromotionList = new ArrayList<OrderItemPromotionDTO>();
                }
                itemPromotionList.add(promotionDTO);
                itemPromotionMap.put(promotionType, itemPromotionList);
                lockKeyList.add(lockKey);
            }
            dealBuyerPromotionChange(messageId, promotionChangeType, itemPromotionMap);
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        } finally {
            promotionRedisHandle.unlockRedisPromotionAction(lockKeyList);
        }
        return result;
    }

    /**
     * 批量执行锁定、释放、扣减、回滚会员优惠券、秒杀活动
     *
     * @param messageId
     * @param promotionChangeType
     * @param itemPromotionMap
     * @throws Exception
     */
    private void dealBuyerPromotionChange(String messageId, String promotionChangeType,
            Map<String, List<OrderItemPromotionDTO>> itemPromotionMap) throws Exception {
        String promotionType = "";
        List<OrderItemPromotionDTO> orderItemPromotionList = null;
        Map<String, List<OrderItemPromotionDTO>> dealSuccessPromotionMap = new HashMap<String, List<OrderItemPromotionDTO>>();

        try {
            for (Map.Entry<String, List<OrderItemPromotionDTO>> entry : itemPromotionMap.entrySet()) {
                promotionType = entry.getKey();
                orderItemPromotionList = entry.getValue();
                saveBuyerPromotionChange(messageId, promotionType, promotionChangeType, orderItemPromotionList);
                dealSuccessPromotionMap.put(promotionType, orderItemPromotionList);
            }
        } catch (Exception e) {
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(promotionChangeType)
                    && !dealSuccessPromotionMap.isEmpty()) {
                for (Map.Entry<String, List<OrderItemPromotionDTO>> entry : dealSuccessPromotionMap.entrySet()) {
                    promotionType = entry.getKey();
                    orderItemPromotionList = entry.getValue();
                    saveBuyerPromotionChange(messageId, promotionType, dictionary
                            .getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE), orderItemPromotionList);
                }
            }
            throw e;
        }
    }

    @Override
    public ExecuteResult<String> reserveBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        result = changeBuyerPromotion(messageId, status, orderItemPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> reduceBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        result = changeBuyerPromotion(messageId, status, orderItemPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> releaseBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        result = changeBuyerPromotion(messageId, status, orderItemPromotionList);
        return result;
    }

    @Override
    public ExecuteResult<String> rollbackBuyerPromotion(String messageId,
            List<OrderItemPromotionDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        result = changeBuyerPromotion(messageId, status, orderItemPromotionList);
        return result;
    }
}

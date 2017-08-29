package cn.htd.promotion.cpc.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionInterestChangeAPI;
import cn.htd.promotion.cpc.api.handler.PromotionTimelimitedHandle;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.response.PromotionOrderItemDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("buyerInterestChangeService")
public class PromotionInterestChangeAPIImpl implements PromotionInterestChangeAPI {

    @Resource
    private DictionaryUtils dictionary;
    
    @Resource
    PromotionTimelimitedHandle promotionTimelimitedHandle;

    private ExecuteResult<String> changeBuyerPromotion(String messageId,
            List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<PromotionOrderItemDTO> itemPromotionList = new ArrayList<PromotionOrderItemDTO>();
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
            for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
                if (StringUtils.isEmpty(promotionDTO.getPromotionType())) {
                    continue;
                }
                // 输入DTO的验证
                ValidateResult validateResult = ValidationUtils.validateEntity(promotionDTO);
                // 有错误信息时返回错误信息
                if (validateResult.isHasErrors()) {
                    throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                            validateResult.getErrorMsg());
                }
                if (couponType.equals(promotionDTO.getPromotionType())) {
                    if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
                        continue;
                    }
                    if (StringUtils.isEmpty(promotionDTO.getOrderNo())) {
                        throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的订单编号为空");
                    }
                    if (StringUtils.isEmpty(promotionDTO.getOrderItemNo())) {
                        throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的子订单编号为空");
                    }
                    if (StringUtils.isEmpty(promotionDTO.getCouponCode())) {
                        throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                                "促销活动ID:" + promotionDTO.getPromotionId() + " 的会员优惠券编号为空");
                    }
                    lockKey = promotionDTO.getOrderItemNo();
                } else if (timelimitedType.equals(promotionDTO.getPromotionType())) {
                    if (StringUtils.isEmpty(promotionDTO.getSeckillLockNo())
                            && StringUtils.isEmpty(promotionDTO.getOrderNo())) {
                        throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
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
                        throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                                "会员促销活动处理一次只能处理一种业务（优惠惠券或秒杀）");
                    }
                }
                buyerCode = promotionDTO.getBuyerCode();
                if (StringUtils.isEmpty(oldBuyerCode)) {
                    oldBuyerCode = buyerCode;
                }
                if (!buyerCode.equals(oldBuyerCode)) {
                    throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                            "会员促销活动处理一次只能处理一个会员的业务");
                }
//                if (!promotionRedisHandle.lockRedisPromotionAction(messageId, lockKey)) {
//                    throw new PromotionCenterBusinessException(PromotionCenterConst.LOCK_FAIL_ERROR,
//                            "会员促销活动正在处理中不能重复处理 messageId:" + messageId + " 参数:" + JSON.toJSONString(promotionDTO));
//                }
                itemPromotionList.add(promotionDTO);
                lockKeyList.add(lockKey);
            }
            saveBuyerPromotionChange(messageId, itemPromotionList);
        } catch (PromotionCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
           // promotionRedisHandle.unlockRedisPromotionAction(lockKeyList);
        }
        return result;
    }

    /**
     * 批量执行锁定、释放、扣减、回滚会员优惠券、秒杀活动
     *
     * @param messageId
     * @param orderItemPromotionList
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void saveBuyerPromotionChange(String messageId, List<PromotionOrderItemDTO> orderItemPromotionList)
            throws PromotionCenterBusinessException, Exception {
      
        String promotionType = "";
        String promotionChangeType = "";

        if (orderItemPromotionList != null && !orderItemPromotionList.isEmpty()) {
            promotionType = orderItemPromotionList.get(0).getPromotionType();
            promotionChangeType = orderItemPromotionList.get(0).getPromoitionChangeType();
            
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.rollbackBuyerPromotionDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.releaseBuyerPromotionDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.reduceBuyerPromotionDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.reserveBuyerPromotionDeal(messageId, orderItemPromotionList);
            }
        }
    }
    
	@Override
	public ExecuteResult<String> reserveBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<PromotionOrderItemDTO> targetPromotionList = new ArrayList<PromotionOrderItemDTO>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String couponType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_COUPON);
        String timelimitedType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
            if (couponType.equals(promotionDTO.getPromotionType())) {
                if (BigDecimal.ZERO.compareTo(promotionDTO.getDiscountAmount()) >= 0) {
                    continue;
                }
                if (StringUtils.isEmpty(promotionDTO.getLevelCode())) {
                    throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                            "促销活动ID:" + promotionDTO.getPromotionId() + " 的层级编码为空");
                }
            } else if (timelimitedType.equals(promotionDTO.getPromotionType())) {
                if (promotionDTO.getQuantity().intValue() < 1) {
                    throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
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
			List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = "";
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE);
        for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
            promotionDTO.setPromoitionChangeType(status);
        }
        result = changeBuyerPromotion(messageId, orderItemPromotionList);
        return result;
	}

	@Override
	public ExecuteResult<String> releaseBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        String status = "";
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
        for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
            promotionDTO.setPromoitionChangeType(status);
        }
        result = changeBuyerPromotion(messageId, orderItemPromotionList);
        return result;
	}

	@Override
	public ExecuteResult<String> rollbackBuyerPromotion(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList) {
	     ExecuteResult<String> result = new ExecuteResult<String>();
	        String status = "";
	        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
	            return result;
	        }
	        status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
	                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK);
	        for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
	            promotionDTO.setPromoitionChangeType(status);
	        }
	        result = changeBuyerPromotion(messageId, orderItemPromotionList);
	        return result;
	}

}

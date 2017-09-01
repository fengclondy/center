package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionInterestChangeAPI;
import cn.htd.promotion.cpc.api.handler.PromotionRedisHandle;
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
import com.alibaba.fastjson.JSON;

@Service("buyerInterestChangeService")
public class PromotionInterestChangeAPIImpl implements PromotionInterestChangeAPI {

    @Resource
    private DictionaryUtils dictionary;
    
    @Resource
    PromotionTimelimitedHandle promotionTimelimitedHandle;
    
    @Resource
    PromotionRedisHandle promotionRedisHandle;
    
	/**
	 * 秒杀 - 参数合法性验证
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
    private ExecuteResult<String> changePromotionTimelimited(String messageId,List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<PromotionOrderItemDTO> itemPromotionList = new ArrayList<PromotionOrderItemDTO>();
        String lockKey = "";
        List<String> lockKeyList = new ArrayList<String>();
        String oldPromotionType = "";
        String oldBuyerCode = "";
        String buyerCode = "";
        String reverseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String timelimitedType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_HEAD_TIMELIMITED);
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
                
                if (timelimitedType.equals(promotionDTO.getPromotionType())) {
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
                                "会员促销活动处理一次只能处理一种业务（秒杀）");
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
                if (!promotionRedisHandle.lockRedisPromotionAction(messageId, lockKey)) {
                   throw new PromotionCenterBusinessException(PromotionCenterConst.LOCK_FAIL_ERROR,
                            "会员促销活动正在处理中不能重复处理 messageId:" + messageId + " 参数:" + JSON.toJSONString(promotionDTO));
               }
                itemPromotionList.add(promotionDTO);
                lockKeyList.add(lockKey);
            }
            savePromotionTimelimitedChange(messageId, itemPromotionList);
        } catch (PromotionCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            promotionRedisHandle.unlockRedisPromotionAction(lockKeyList);
        }
        return result;
    }

    /**
     * 秒杀 - 执行 锁定、释放、扣减、回滚
     *
     * @param messageId
     * @param orderItemPromotionList
     * @throws Exception
     */
    private void savePromotionTimelimitedChange(String messageId, List<PromotionOrderItemDTO> orderItemPromotionList)
            throws PromotionCenterBusinessException, Exception {
    	
        String promotionChangeType = "";
        if (orderItemPromotionList != null && !orderItemPromotionList.isEmpty()) {
            promotionChangeType = orderItemPromotionList.get(0).getPromoitionChangeType();
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_ROLLBACK).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.rollbackPromotionTimelimitedDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.releasePromotionTimelimitedDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.reducePromotionTimelimitedDeal(messageId, orderItemPromotionList);
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                    DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(promotionChangeType)) {
            	promotionTimelimitedHandle.reservePromotionTimelimitedDeal(messageId, orderItemPromotionList);
            }
        }
    }
    
	/**
	 * 秒杀 - 锁定 （创建订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	@Override
	public ExecuteResult<String> reservePromotionTimelimited(String messageId,
			List<PromotionOrderItemDTO> orderItemPromotionList) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<PromotionOrderItemDTO> targetPromotionList = new ArrayList<PromotionOrderItemDTO>();
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String timelimitedType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_HEAD_TIMELIMITED);
        if (orderItemPromotionList == null || orderItemPromotionList.isEmpty()) {
            return result;
        }
        for (PromotionOrderItemDTO promotionDTO : orderItemPromotionList) {
            if(timelimitedType.equals(promotionDTO.getPromotionType())) {
                if (promotionDTO.getQuantity().intValue() < 1) {
                    throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR,
                            "促销活动ID:" + promotionDTO.getPromotionId() + " 的秒杀商品数量不能小于1");
                }
            }
            promotionDTO.setPromoitionChangeType(status);
            targetPromotionList.add(promotionDTO);
        }
        result = changePromotionTimelimited(messageId, targetPromotionList);
        return result;
	}

	/**
	 * 秒杀 - 扣减 （支付完成）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	@Override
	public ExecuteResult<String> reducePromotionTimelimited(String messageId,
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
        result = changePromotionTimelimited(messageId, orderItemPromotionList);
        return result;
	}

	/**
	 * 秒杀 - 解锁 （取消未支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	@Override
	public ExecuteResult<String> releasePromotionTimelimited(String messageId,
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
        result = changePromotionTimelimited(messageId, orderItemPromotionList);
        return result;
	}

	/**
	 * 秒杀 - 回滚（取消已支付订单）
	 * 
	 * @param messageId
	 * @param orderItemPromotionList
	 * @return
	 */
	@Override
	public ExecuteResult<String> rollbackPromotionTimelimited(String messageId,
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
	        result = changePromotionTimelimited(messageId, orderItemPromotionList);
	        return result;
	}

}

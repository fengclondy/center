/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TradeOrderBaseService.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: 订单基础服务  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.service.handle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.service.BuyerInterestChangeService;
import cn.htd.tradecenter.common.exception.TradeCenterBusinessException;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;
import cn.htd.tradecenter.dto.TradeOrderItemsDiscountDTO;

@Service("tradeOrderCouponHandle")
public class TradeOrderCouponHandle {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderCouponHandle.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private BuyerInterestChangeService buyerInterestChangeService;

	/**
	 * 锁定订单优惠券
	 * 
	 * @param messageId
	 * @param orderItemList
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	public ExecuteResult<String> reserveOrderCoupon(String messageId, List<TradeOrderItemsDTO> orderItemList)
			throws TradeCenterBusinessException, Exception {
		ExecuteResult<String> couponResutl = null;
		OrderItemPromotionDTO itemPromotionDTO = null;
		List<OrderItemPromotionDTO> itemPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		try {
			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				if (StringUtils.isEmpty(orderItemDTO.getOrderItemNo())) {
					continue;
				}
				if (orderItemDTO.getDiscountDTOList() == null || orderItemDTO.getDiscountDTOList().isEmpty()) {
					continue;
				}
				if (!"delete".equals(orderItemDTO.getDealFlag())) {
					continue;
				}
				for (TradeOrderItemsDiscountDTO couponInfo : orderItemDTO.getDiscountDTOList()) {
					if (StringUtils.isEmpty(couponInfo.getBuyerCouponCode())) {
						continue;
					}
					itemPromotionDTO = new OrderItemPromotionDTO();
					itemPromotionDTO.setOrderNo(couponInfo.getOrderNo());
					itemPromotionDTO.setOrderItemNo(couponInfo.getOrderItemNo());
					itemPromotionDTO.setBuyerCode(couponInfo.getBuyerCode());
					itemPromotionDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
							DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
					itemPromotionDTO.setPromotionId(couponInfo.getPromotionId());
					itemPromotionDTO.setLevelCode(couponInfo.getLevelCode());
					itemPromotionDTO.setCouponCode(couponInfo.getBuyerCouponCode());
					itemPromotionDTO.setDiscountAmount(couponInfo.getCouponDiscount());
					itemPromotionDTO.setOperaterId(orderItemDTO.getModifyId());
					itemPromotionDTO.setOperaterName(orderItemDTO.getModifyName());
					itemPromotionDTOList.add(itemPromotionDTO);
				}
			}
			couponResutl = buyerInterestChangeService.reserveBuyerPromotion(messageId, itemPromotionDTOList);
			if (!couponResutl.isSuccess()) {
				throw new TradeCenterBusinessException(couponResutl.getCode(),
						StringUtils.join(couponResutl.getErrorMessages(), "\n"));
			}
		} catch (TradeCenterBusinessException tcbe) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderCouponHandle-reserveOrderCoupon",
					JSONObject.toJSONString(tcbe));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderCouponHandle-reserveOrderCoupon", JSONObject.toJSONString(e));
			throw e;
		}
		return couponResutl;
	}

	/**
	 * 释放订单优惠券
	 * 
	 * @param messageId
	 * @param orderItemList
	 * @throws TradeCenterBusinessException
	 * @throws Exception
	 */
	public ExecuteResult<String> releaseOrderCoupon(String messageId, List<TradeOrderItemsDTO> orderItemList)
			throws TradeCenterBusinessException, Exception {
		ExecuteResult<String> couponResutl = null;
		OrderItemPromotionDTO itemPromotionDTO = null;
		List<OrderItemPromotionDTO> itemPromotionDTOList = new ArrayList<OrderItemPromotionDTO>();
		try {

			for (TradeOrderItemsDTO orderItemDTO : orderItemList) {
				if (!"delete".equals(orderItemDTO.getDealFlag())) {
					continue;
				}
				if (orderItemDTO.getDiscountDTOList() == null || orderItemDTO.getDiscountDTOList().isEmpty()) {
					continue;
				}
				for (TradeOrderItemsDiscountDTO couponInfo : orderItemDTO.getDiscountDTOList()) {
					if (StringUtils.isEmpty(couponInfo.getBuyerCouponCode())) {
						continue;
					}
					itemPromotionDTO = new OrderItemPromotionDTO();
					itemPromotionDTO.setOrderNo(couponInfo.getOrderNo());
					itemPromotionDTO.setOrderItemNo(couponInfo.getOrderItemNo());
					itemPromotionDTO.setBuyerCode(couponInfo.getBuyerCode());
					itemPromotionDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
							DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
					itemPromotionDTO.setPromotionId(couponInfo.getPromotionId());
					itemPromotionDTO.setLevelCode(couponInfo.getLevelCode());
					itemPromotionDTO.setCouponCode(couponInfo.getBuyerCouponCode());
					itemPromotionDTO.setDiscountAmount(couponInfo.getCouponDiscount());
					itemPromotionDTO.setOperaterId(orderItemDTO.getModifyId());
					itemPromotionDTO.setOperaterName(orderItemDTO.getModifyName());
					itemPromotionDTOList.add(itemPromotionDTO);
				}
			}
			if (itemPromotionDTOList != null && !itemPromotionDTOList.isEmpty()) {
				couponResutl = buyerInterestChangeService.releaseBuyerPromotion(messageId, itemPromotionDTOList);
				if (!couponResutl.isSuccess()) {
					throw new TradeCenterBusinessException(couponResutl.getCode(),
							StringUtils.join(couponResutl.getErrorMessages(), "\n"));
				}
			}
		} catch (TradeCenterBusinessException tcbe) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderCouponHandle-reserveOrderCoupon",
					JSONObject.toJSONString(tcbe));
			throw tcbe;
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeOrderCouponHandle-reserveOrderCoupon", JSONObject.toJSONString(e));
			throw e;
		}
		return couponResutl;
	}
}

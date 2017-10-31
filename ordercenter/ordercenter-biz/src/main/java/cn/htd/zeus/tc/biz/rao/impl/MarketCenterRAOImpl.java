package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.marketcenter.service.BuyerInterestChangeService;
import cn.htd.marketcenter.service.BuyerInterestValidService;
import cn.htd.marketcenter.service.TimelimitedInfoService;
import cn.htd.marketcenter.service.TimelimitedPurchaseService;
import cn.htd.zeus.tc.biz.rao.MarketCenterRAO;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

@Service
public class MarketCenterRAOImpl implements MarketCenterRAO {

	@Autowired
	private BuyerInterestValidService buyerInterestValidService;

	/*
	 * 锁定优惠券
	 */
	@Autowired
	private BuyerInterestChangeService buyerInterestChangeService;

	@Autowired
	private TimelimitedInfoService timelimitedInfoService;
	
	@Autowired
	private TimelimitedPurchaseService timelimitedPurchaseService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MarketCenterRAOImpl.class);

	/**
	 * 查询用户优惠券信息
	 * 
	 * @param messageId
	 * @param cart
	 * @return
	 */
	public OtherCenterResDTO<TradeInfoDTO> getAvailableCouponInfo(TradeInfoDTO cart,
			String messageId) {
		OtherCenterResDTO<TradeInfoDTO> other = new OtherCenterResDTO<TradeInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("订单结算--查询会员优惠券信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<TradeInfoDTO> tradeResult = buyerInterestValidService
					.getAvailableCouponInfo(messageId, cart);
			LOGGER.info("MessageId:{} 订单结算--查询会员优惠券信息--返回结果:{}", messageId,
					JSONObject.toJSONString(tradeResult));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询会员优惠券信息 耗时:{}", messageId, endTime - startTime);
			if (tradeResult.getResult() != null && tradeResult.isSuccess() == true) {
				other.setOtherCenterResult(tradeResult.getResult());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法MarketCenterRAOImpl.getAvailableCouponInfo没有查询到正确的值{}",
						messageId, JSONObject.toJSONString(tradeResult));
				other.setOtherCenterResult(tradeResult.getResult());
				other.setOtherCenterResponseMsg(tradeResult.getErrorMessages().get(0));
				other.setOtherCenterResponseCode(tradeResult.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.getAvailableCouponInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/*
	 * 锁定会员优惠券、秒杀
	 */
	@Override
	public OtherCenterResDTO<String> reserveBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(reserveBuyerPromotion锁定会员优惠券、秒杀)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(orderItemPromotionList));
			ExecuteResult<String> reservePromotion = buyerInterestChangeService
					.reserveBuyerPromotion(messageId, orderItemPromotionList);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(reserveBuyerPromotion锁定会员优惠券、秒杀)--返回结果:{}", messageId,
					JSONObject.toJSONString(reservePromotion) + " 耗时:" + (endTime - startTime));
			if (reservePromotion.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				String errorMsg = "";
				if (null != reservePromotion && null != reservePromotion.getErrorMessages()) {
					List<String> errorMessages = reservePromotion.getErrorMessages();
					errorMsg = JSONObject.toJSONString(errorMessages);
				} else {
					errorMsg = ResultCodeEnum.MARKETCENTER_RESERVE_STOCK_FAILED.getMsg();
				}
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.MARKETCENTER_RESERVE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(errorMsg);
				LOGGER.warn("MessageId:{} 调用促销中心(reserveBuyerPromotion锁定会员优惠券、秒杀)-返回错误码和错误信息:{}",
						messageId, ResultCodeEnum.MARKETCENTER_RESERVE_STOCK_FAILED.getCode()
								+ otherCenterResDTO.getOtherCenterResponseMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.reserveBuyerPromotion出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 释放会员优惠券、秒杀
	 */
	@Override
	public OtherCenterResDTO<String> releaseBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(releaseBuyerPromotion释放会员优惠券、秒杀)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(orderItemPromotionList));
			ExecuteResult<String> releasePromotion = buyerInterestChangeService
					.releaseBuyerPromotion(messageId, orderItemPromotionList);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(releaseBuyerPromotion释放会员优惠券、秒杀)--返回结果:{}", messageId,
					JSONObject.toJSONString(releasePromotion) + " 耗时:" + (endTime - startTime));
			if (releasePromotion.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用促销中心(releaseBuyerPromotion释放会员优惠券、秒杀)-返回错误码和错误信息:{}",
						messageId, ResultCodeEnum.MARKETCENTER_RELEASE_STOCK_FAILED.getCode()
								+ ResultCodeEnum.MARKETCENTER_RELEASE_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.MARKETCENTER_RELEASE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.MARKETCENTER_RELEASE_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.releaseBuyerPromotion出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 扣减会员优惠券、秒杀
	 */
	@Override
	public OtherCenterResDTO<String> reduceBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(reduceBuyerPromotion扣减会员优惠券、秒杀)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(orderItemPromotionList));
			ExecuteResult<String> reducePromotion = buyerInterestChangeService
					.reduceBuyerPromotion(messageId, orderItemPromotionList);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(reduceBuyerPromotion扣减会员优惠券、秒杀)--返回结果:{}", messageId,
					JSONObject.toJSONString(reducePromotion) + " 耗时:" + (endTime - startTime));
			if (reducePromotion.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用促销中心(reduceBuyerPromotion扣减会员优惠券、秒杀)-返回错误码和错误信息:{}",
						messageId, ResultCodeEnum.MARKETCENTER_REDUCE_STOCK_FAILED.getCode()
								+ ResultCodeEnum.MARKETCENTER_REDUCE_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.MARKETCENTER_REDUCE_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.MARKETCENTER_REDUCE_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.reduceBuyerPromotion出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 回滚会员优惠券、秒杀
	 */
	@Override
	public OtherCenterResDTO<String> rollbackBuyerPromotion(
			List<OrderItemPromotionDTO> orderItemPromotionList, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(rollbackBuyerPromotion回滚会员优惠券、秒杀)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(orderItemPromotionList));
			ExecuteResult<String> rollbackBuyerPromotion = buyerInterestChangeService
					.rollbackBuyerPromotion(messageId, orderItemPromotionList);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(rollbackBuyerPromotion回滚会员优惠券、秒杀)--返回结果:{}", messageId,
					JSONObject.toJSONString(rollbackBuyerPromotion) + " 耗时:"
							+ (endTime - startTime));
			if (rollbackBuyerPromotion.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				LOGGER.warn("MessageId:{} 调用促销中心(rollbackBuyerPromotion回滚会员优惠券、秒杀)-返回错误码和错误信息:{}",
						messageId, ResultCodeEnum.MARKETCENTER_ROLLBACK_STOCK_FAILED.getCode()
								+ ResultCodeEnum.MARKETCENTER_ROLLBACK_STOCK_FAILED.getMsg());
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.MARKETCENTER_ROLLBACK_STOCK_FAILED.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.MARKETCENTER_ROLLBACK_STOCK_FAILED.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.reduceBuyerPromotion出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	@Override
	public OtherCenterResDTO<TimelimitedMallInfoDTO> getSeckillInfo(String promotionId,
			String messageId) {
		OtherCenterResDTO<TimelimitedMallInfoDTO> otherCenterResDTO = new OtherCenterResDTO<TimelimitedMallInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			if (StringUtils.isNotBlank(promotionId)) {
				LOGGER.info("MessageId:{}调用促销中心(getSeckillInfo查询秒杀信息)--组装查询参数开始:{}", messageId,
						promotionId);
				ExecuteResult<TimelimitedMallInfoDTO> seckillInfo = timelimitedInfoService
						.getMallTimelimitedInfo(messageId, promotionId, "", "");
				Long endTime = System.currentTimeMillis();
				LOGGER.info("MessageId:{}调用促销中心(getSeckillInfo查询秒杀信息)--返回结果:{}", messageId,
						JSONObject.toJSONString(seckillInfo) + " 耗时:" + (endTime - startTime));
				if (seckillInfo.isSuccess() == true) {
					otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
					otherCenterResDTO.setOtherCenterResult(seckillInfo.getResult());
				} else {
					LOGGER.warn("MessageId:{} 调用促销中心(getSeckillInfo查询秒杀信息)-返回错误码和错误信息:{}",
							messageId, ResultCodeEnum.MARKETCENTER_SECKILL_INFO_FAILED.getCode()
									+ ResultCodeEnum.MARKETCENTER_SECKILL_INFO_FAILED.getMsg());
					otherCenterResDTO.setOtherCenterResponseCode(
							ResultCodeEnum.MARKETCENTER_SECKILL_INFO_FAILED.getCode());
					otherCenterResDTO.setOtherCenterResponseMsg(
							ResultCodeEnum.MARKETCENTER_SECKILL_INFO_FAILED.getMsg());
				}
			} else {
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_PROMOTION_ID_NULL.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.ORDERSETTLEMENT_PROMOTION_ID_NULL.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.getSeckillInfo出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}

	/*
	 * 根据选择的优惠券信息计算订单行的分摊金额 (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.zeus.tc.biz.rao.MarketCenterRAO#calculateCouponDiscount(java.lang.
	 * String, java.util.List, cn.htd.marketcenter.dto.TradeInfoDTO)
	 */
	@Override
	public OtherCenterResDTO<TradeInfoDTO> calculateCouponDiscount(List<String> couponCodeList,
			TradeInfoDTO cart, String messageId) {
		OtherCenterResDTO<TradeInfoDTO> otherCenterResDTO = new OtherCenterResDTO<TradeInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("调用促销中心(calculateCouponDiscount根据选择的优惠券信息计算订单行的分摊金额)--组装查询参数开始:"
					+ "MessageId:" + messageId + " couponCodeList:"
					+ JSONObject.toJSONString(couponCodeList) + " cart"
					+ JSONObject.toJSONString(cart));
			cart.setCouponCodeList(couponCodeList);
			ExecuteResult<TradeInfoDTO> calculateCouponInfo = buyerInterestValidService
					.calculateTradeDiscount(messageId, cart);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}调用促销中心(calculateCouponDiscount根据选择的优惠券信息计算订单行的分摊金额)--返回结果:{}",
					messageId,
					JSONObject.toJSONString(calculateCouponInfo) + " 耗时:" + (endTime - startTime));
			if (null != calculateCouponInfo.getResult()
					&& calculateCouponInfo.isSuccess() == true) {
				otherCenterResDTO.setOtherCenterResult(calculateCouponInfo.getResult());
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.MARKETCENTER_CALCULATE_COUPON_DISCOUNT_FAILED.getCode());
				String errorMsg = "";
				if (null != calculateCouponInfo && null != calculateCouponInfo.getErrorMessages()) {
					List<String> errorMessages = calculateCouponInfo.getErrorMessages();
					errorMsg = JSONObject.toJSONString(errorMessages);
				} else {
					errorMsg = ResultCodeEnum.MARKETCENTER_CALCULATE_COUPON_DISCOUNT_FAILED
							.getMsg();
				}
				otherCenterResDTO.setOtherCenterResponseMsg(errorMsg);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.calculateCouponDiscount出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}
	
	/**
	 * 限时购	  －  获取对应的限时购信息
	 * @param skuCode
	 * @return
	 */
	@Override
	public OtherCenterResDTO<TimelimitedInfoDTO> getTimelimitedInfo(
			String skuCode, String messageId) {
		OtherCenterResDTO<TimelimitedInfoDTO> otherCenterResDTO = new OtherCenterResDTO<TimelimitedInfoDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info(
					"调用促销中心(getTimelimitedInfo获取对应的限时购信息)--组装查询参数开始:MessageId:{},skuCode:{}",
					messageId, skuCode);
			ExecuteResult<TimelimitedInfoDTO> timelimitedInfoDTORes = timelimitedPurchaseService
					.getTimelimitedInfo(skuCode);
			Long endTime = System.currentTimeMillis();
			LOGGER.info(
					"MessageId:{}调用促销中心(getTimelimitedInfo获取对应的限时购信息)--返回结果:{}",
					messageId, JSONObject.toJSONString(timelimitedInfoDTORes)
							+ " 耗时:" + (endTime - startTime));
			String resCode = timelimitedInfoDTORes.getCode();
			if (ResultCodeEnum.SUCCESS.getCode().equals(resCode)) {
				otherCenterResDTO.setOtherCenterResult(timelimitedInfoDTORes.getResult());
				otherCenterResDTO.setOtherCenterResponseCode(resCode);
			} else {
				String errorMsg = "没有成功获取到skuCode:"+skuCode+"对应的限时购信息";
				if (null != timelimitedInfoDTORes && null != timelimitedInfoDTORes.getErrorMessages()) {
					List<String> errorMessages = timelimitedInfoDTORes.getErrorMessages();
					errorMsg = JSONObject.toJSONString(errorMessages);
				}
				otherCenterResDTO.setOtherCenterResponseMsg(errorMsg);
				otherCenterResDTO.setOtherCenterResponseCode(resCode);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法MarketCenterRAOImpl.getTimelimitedInfo出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return otherCenterResDTO;
	}
	
	/**
	 * 促销中心统计限时购销量
	 * @param timelimitedInfoDTO
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<String> updateTimitedInfoSalesVolumeRedis(
			TimelimitedInfoDTO timelimitedInfoDTO, String messageId) {
		OtherCenterResDTO<String> otherCenterResDTO = new OtherCenterResDTO<String>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info(
					"调用促销中心(updateTimitedInfoSalesVolumeRedis统计限时购销量)--组装查询参数开始:MessageId:{},timelimitedInfoDTO:{}",
					messageId, JSONObject.toJSONString(timelimitedInfoDTO));
			ExecuteResult<String> updateRaoRes = timelimitedPurchaseService.updateTimitedInfoSalesVolumeRedis(timelimitedInfoDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info(
					"MessageId:{}调用促销中心(updateTimitedInfoSalesVolumeRedis统计限时购销量)--返回结果:{}",
					messageId, JSONObject.toJSONString(updateRaoRes)
							+ " 耗时:" + (endTime - startTime));
			String resCode = updateRaoRes.getCode();
			if (ResultCodeEnum.SUCCESS.getCode().equals(resCode)) {
				otherCenterResDTO.setOtherCenterResponseCode(resCode);
			}else {
				String errorMsg = "更新统计限时购销量失败:参数"+JSONObject.toJSONString(timelimitedInfoDTO);
				if (null != updateRaoRes && null != updateRaoRes.getErrorMessages()) {
					List<String> errorMessages = updateRaoRes.getErrorMessages();
					errorMsg = JSONObject.toJSONString(errorMessages);
				}
				otherCenterResDTO.setOtherCenterResponseMsg(errorMsg);
				otherCenterResDTO.setOtherCenterResponseCode(resCode);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法MarketCenterRAOImpl.updateTimitedInfoSalesVolumeRedis出现异常{}",
					messageId, w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR
					.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR
					.getCode());
		}
		return otherCenterResDTO;

	}
}

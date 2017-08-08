
package cn.htd.zeus.tc.biz.rao;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.marketcenter.service.BuyerInterestValidService;
import cn.htd.membercenter.service.BoxRelationshipService;
import cn.htd.membercenter.service.ConsigneeAddressService;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.membercenter.service.MemberInvoiceService;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

import com.alibaba.fastjson.JSONObject;

@Service
public class OrderSettleMentRAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSettleMentRAO.class);

	@Autowired
	private MallItemExportService mallItemExportService;

	@Autowired
	private ItemSkuPriceService itemSkuPriceService;

	@Autowired
	private MemberGradeService memberGradeService;

	@Autowired
	private ConsigneeAddressService consigneeAddressService;

	@Autowired
	private MemberInvoiceService memberInvoiceService;

	@Autowired
	private BoxRelationshipService boxRelationshipService;

	@Autowired
	private BuyerInterestValidService buyerInterestValidService;

	public OtherCenterResDTO<CommonItemSkuPriceDTO> queryCommonItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO, String messageId) {
		OtherCenterResDTO<CommonItemSkuPriceDTO> other = new OtherCenterResDTO<CommonItemSkuPriceDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			CommonItemSkuPriceDTO priceDTO = new CommonItemSkuPriceDTO();
			LOGGER.info("订单结算--查询商品sku价格信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<CommonItemSkuPriceDTO> skuPrice = itemSkuPriceService
					.queryCommonItemSkuPrice(queryCommonItemSkuPriceDTO);
			LOGGER.info("MessageId:{} 订单结算--查询商品sku价格信息--返回结果:{}", messageId,
					JSONObject.toJSONString(skuPrice));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询商品sku价格信息 耗时:{}", messageId, endTime - startTime);
			priceDTO = skuPrice.getResult();
			if (priceDTO != null && skuPrice.isSuccess() == true) {
				other.setOtherCenterResult(priceDTO);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.warn(
						"MessageId:{} 订单结算--查询商品sku价格信息(queryCommonItemSkuPrice查询商品价格信息)-没有查到数据 参数:{}",
						messageId, JSONObject.toJSONString(skuPrice));
				other.setOtherCenterResponseMsg(
						ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_PRICE_NULL.getMsg());
				other.setOtherCenterResponseCode(
						ResultCodeEnum.ORDERSETTLEMENT_PRODUCT_PRICE_NULL.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSettleMentRAO.queryCommonItemSkuPrice出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	public OtherCenterResDTO<Boolean> getBusinessRelationShipInfo(long memberId, long sellerId,
			String messageId) {
		OtherCenterResDTO<Boolean> other = new OtherCenterResDTO<Boolean>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("订单结算--查询会员经营关系信息--组装查询参数开始:" + "MessageId:" + messageId);
			ExecuteResult<Integer> relationFlag = boxRelationshipService
					.selectBusiRelationListLong(memberId, sellerId);
			LOGGER.info("MessageId:{} 订单结算--查询会员经营关系信息--返回结果:{}", messageId,
					JSONObject.toJSONString(relationFlag));
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{} 订单结算--查询会员经营关系信息 耗时:{}", messageId, endTime - startTime);
			if (relationFlag != null && relationFlag.getResult() == 1) {
				other.setOtherCenterResult(true);
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info(
						"MessageId:{} 调用方法OrderSettleMentRAO.getBusinessRelationShipInfo没有查询到正确的值{}",
						messageId, relationFlag);
				other.setOtherCenterResult(false);
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSettleMentRAO.getBusinessRelationShipInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

	/**
	 * 查询用户优惠券信息
	 * 
	 * @param messageId
	 * @param cart
	 * @return
	 */
	public OtherCenterResDTO<TradeInfoDTO> getAvailableCouponInfo(String messageId,
			TradeInfoDTO cart) {
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
			if (tradeResult != null && tradeResult.isSuccess() == true) {
				other.setOtherCenterResult(tradeResult.getResult());
				other.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
				other.setOtherCenterResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				LOGGER.info("MessageId:{} 调用方法OrderSettleMentRAO.getAvailableCouponInfo没有查询到正确的值{}",
						messageId, JSONObject.toJSONString(tradeResult));
				other.setOtherCenterResult(tradeResult.getResult());
				other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
				other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法OrderSettleMentRAO.getAvailableCouponInfo出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

}

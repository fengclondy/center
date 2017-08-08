package cn.htd.zeus.tc.biz.rao.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.OrderItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import cn.htd.zeus.tc.biz.rao.PriceCenterRAO;
import cn.htd.zeus.tc.common.enums.ResultCodeEnum;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

@Service
public class PriceCenterRAOImpl implements PriceCenterRAO {

	@Autowired
	private ItemSkuPriceService itemSkuPriceService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PriceCenterRAOImpl.class);

	/*
	 * 从价格中心查询商品价格
	 */
	public OtherCenterResDTO<OrderItemSkuPriceDTO> queryOrderItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO, String messageId) {
		OtherCenterResDTO<OrderItemSkuPriceDTO> otherCenterResDTO = new OtherCenterResDTO<OrderItemSkuPriceDTO>();
		try {
			Long startTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询价格中心(queryOrderItemSkuPrice查询商品价格详情)--组装查询参数开始:{}",
					messageId, JSONObject.toJSONString(queryCommonItemSkuPriceDTO));
			OrderItemSkuPriceDTO orderItemSkuPriceDTO = new OrderItemSkuPriceDTO();
			ExecuteResult<OrderItemSkuPriceDTO> commonItemSkuPriceResDTO = itemSkuPriceService
					.queryOrderItemSkuPrice(queryCommonItemSkuPriceDTO);
			Long endTime = System.currentTimeMillis();
			LOGGER.info("MessageId:{}查询价格中心(queryOrderItemSkuPrice查询商品价格详情)--返回结果:{}", messageId,
					JSONObject.toJSONString(commonItemSkuPriceResDTO) + " 耗时:"
							+ (endTime - startTime));
			if (null != commonItemSkuPriceResDTO.getResult() && commonItemSkuPriceResDTO.isSuccess()) {
				orderItemSkuPriceDTO = commonItemSkuPriceResDTO.getResult();
				otherCenterResDTO.setOtherCenterResult(orderItemSkuPriceDTO);
				otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.SUCCESS.getCode());
			} else {
				// 没有查到数据
				LOGGER.warn("MessageId:{} 查询价格中心-(queryOrderItemSkuPrice查询商品价格详情)-没有查到数据 ",
						messageId);
				otherCenterResDTO.setOtherCenterResponseCode(
						ResultCodeEnum.PRICECENTER_QUERY_PRICE_NOT_RESULT.getCode());
				otherCenterResDTO.setOtherCenterResponseMsg(
						ResultCodeEnum.PRICECENTER_QUERY_PRICE_NOT_RESULT.getMsg());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法PriceCenterRAOImpl.queryOrderItemSkuPrice出现异常{}", messageId,
					w.toString());
			otherCenterResDTO.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			otherCenterResDTO.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}

		return otherCenterResDTO;
	}

	/**
	 * 查询订单结算信息的商品价格
	 * 
	 * @param queryCommonItemSkuPriceDTO
	 * @param messageId
	 * @return
	 */
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
			LOGGER.error("MessageId:{} 调用方法PriceCenterRAOImpl.queryCommonItemSkuPrice出现异常{}",
					messageId, w.toString());
			other.setOtherCenterResponseMsg(ResultCodeEnum.ERROR.getMsg());
			other.setOtherCenterResponseCode(ResultCodeEnum.ERROR.getCode());
		}
		return other;
	}

}

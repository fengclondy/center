package cn.htd.zeus.tc.biz.rao;

import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.OrderItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

public interface PriceCenterRAO {

	/*
	 * 从价格中心查询商品价格
	 */
	public OtherCenterResDTO<OrderItemSkuPriceDTO> queryOrderItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO, String messageId);

	/*
	 * 调用价格中心-查询订单结算商品价格
	 */
	public OtherCenterResDTO<CommonItemSkuPriceDTO> queryCommonItemSkuPrice(
			QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO, String messageId);
}

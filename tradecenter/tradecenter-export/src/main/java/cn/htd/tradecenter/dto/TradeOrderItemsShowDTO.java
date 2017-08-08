package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.List;

public class TradeOrderItemsShowDTO extends TradeOrderItemsDTO implements Serializable {

	private static final long serialVersionUID = 5288178707871825276L;
	/**
	 * 渠道名称
	 */
	private String channelCodeName;
	/**
	 * 商品单价种类名称
	 */
	private String goodsPriceTypeName;
	/**
	 * 商品+采购订单状态
	 */
	private String outerChannelPuchaseStatusName;
	/**
	 * 平台公司状态
	 */
	private String outerChannelStatusName;
	/**
	 * 订单行状态名称
	 */
	private String OrderItemStatusName;
	/**
	 * 退货/退款状态
	 */
	private String refundStatusName;
	/**
	 * 是否超出配送范围名称
	 */
	private String isOutDistribtionName;
	/**
	 * 是否已结算名称
	 */
	private String isSettledName;
	/**
	 * 库存展示信息
	 */
	private List<TradeOrderItemsWarehouseDetailShowDTO> warehouseShowDTOList;
	
	public String getChannelCodeName() {
		return channelCodeName;
	}

	public void setChannelCodeName(String channelCodeName) {
		this.channelCodeName = channelCodeName;
	}

	public String getGoodsPriceTypeName() {
		return goodsPriceTypeName;
	}

	public void setGoodsPriceTypeName(String goodsPriceTypeName) {
		this.goodsPriceTypeName = goodsPriceTypeName;
	}

	public String getOuterChannelPuchaseStatusName() {
		return outerChannelPuchaseStatusName;
	}

	public void setOuterChannelPuchaseStatusName(String outerChannelPuchaseStatusName) {
		this.outerChannelPuchaseStatusName = outerChannelPuchaseStatusName;
	}

	public String getOuterChannelStatusName() {
		return outerChannelStatusName;
	}

	public void setOuterChannelStatusName(String outerChannelStatusName) {
		this.outerChannelStatusName = outerChannelStatusName;
	}

	public String getOrderItemStatusName() {
		return OrderItemStatusName;
	}

	public void setOrderItemStatusName(String orderItemStatusName) {
		OrderItemStatusName = orderItemStatusName;
	}

	public String getRefundStatusName() {
		return refundStatusName;
	}

	public void setRefundStatusName(String refundStatusName) {
		this.refundStatusName = refundStatusName;
	}

	public String getIsOutDistribtionName() {
		return isOutDistribtionName;
	}

	public void setIsOutDistribtionName(String isOutDistribtionName) {
		this.isOutDistribtionName = isOutDistribtionName;
	}

	public String getIsSettledName() {
		return isSettledName;
	}

	public void setIsSettledName(String isSettledName) {
		this.isSettledName = isSettledName;
	}

	public List<TradeOrderItemsWarehouseDetailShowDTO> getWarehouseShowDTOList() {
		return warehouseShowDTOList;
	}

	public void setWarehouseShowDTOList(List<TradeOrderItemsWarehouseDetailShowDTO> warehouseShowDTOList) {
		this.warehouseShowDTOList = warehouseShowDTOList;
	}
}
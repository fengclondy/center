package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.List;

public class TradeOrdersShowDTO extends TradeOrdersDTO implements Serializable {

	private static final long serialVersionUID = -4682246743329482317L;
	/**
	 * 城市站名称
	 */
	private String siteName;
	/**
	 * 买家类型名称
	 */
	private String buyerTypeName;
	/**
	 * 卖家类型名称
	 */
	private String sellerTypeName;
	/**
	 * 订单来源名称
	 */
	private String orderFromName;
	/**
	 * 销售方式名称
	 */
	private String salesTypeName;
	/**
	 * 是否有外接商品名称
	 */
	private String hasProductPlusName;
	/**
	 * 是否使用优惠券名称
	 */
	private String hasUsedCouponName;
	/**
	 * 是否议价名称
	 */
	private String isChangePriceName;
	/**
	 * 订单状态名称
	 */
	private String OrderStatusName;
	/**
	 * 支付类型名称
	 */
	private String payTypeName;
	/**
	 * 支付状态名称
	 */
	private String payStatusName;
	/**
	 * 发票类型名称
	 */
	private String invoiceTypeName;
	/**
	 * 配送类型名称
	 */
	private String deliveryTypeName;
	/**
	 * 物流状态名称
	 */
	private String logisticsStatusName;
	/**
	 * 订单行展示信息list
	 */
	private List<TradeOrderItemsShowDTO> orderItemShowList;
	/**
	 * 更新时间字符串 YYYY-MM-DD HH24:MI:SS
	 */
	private String modifyTimeStr;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getBuyerTypeName() {
		return buyerTypeName;
	}

	public void setBuyerTypeName(String buyerTypeName) {
		this.buyerTypeName = buyerTypeName;
	}

	public String getSellerTypeName() {
		return sellerTypeName;
	}

	public void setSellerTypeName(String sellerTypeName) {
		this.sellerTypeName = sellerTypeName;
	}

	public String getOrderFromName() {
		return orderFromName;
	}

	public void setOrderFromName(String orderFromName) {
		this.orderFromName = orderFromName;
	}

	public String getSalesTypeName() {
		return salesTypeName;
	}

	public void setSalesTypeName(String salesTypeName) {
		this.salesTypeName = salesTypeName;
	}

	public String getHasProductPlusName() {
		return hasProductPlusName;
	}

	public void setHasProductPlusName(String hasProductPlusName) {
		this.hasProductPlusName = hasProductPlusName;
	}

	public String getHasUsedCouponName() {
		return hasUsedCouponName;
	}

	public void setHasUsedCouponName(String hasUsedCouponName) {
		this.hasUsedCouponName = hasUsedCouponName;
	}

	public String getIsChangePriceName() {
		return isChangePriceName;
	}

	public void setIsChangePriceName(String isChangePriceName) {
		this.isChangePriceName = isChangePriceName;
	}

	public String getOrderStatusName() {
		return OrderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		OrderStatusName = orderStatusName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getPayStatusName() {
		return payStatusName;
	}

	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	public String getLogisticsStatusName() {
		return logisticsStatusName;
	}

	public void setLogisticsStatusName(String logisticsStatusName) {
		this.logisticsStatusName = logisticsStatusName;
	}

	public List<TradeOrderItemsShowDTO> getOrderItemShowList() {
		return orderItemShowList;
	}

	public void setOrderItemShowList(List<TradeOrderItemsShowDTO> orderItemShowList) {
		this.orderItemShowList = orderItemShowList;
	}

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public void setTradeOrdersShowInfo(TradeOrdersShowDTO orderShowDTO) {
		super.setTradeOrderAllInfo(orderShowDTO);
		this.siteName = orderShowDTO.getSiteName();
		this.buyerTypeName = orderShowDTO.getBuyerTypeName();
		this.sellerTypeName = orderShowDTO.getSellerTypeName();
		this.orderFromName = orderShowDTO.getOrderFromName();
		this.salesTypeName = orderShowDTO.getSalesTypeName();
		this.hasProductPlusName = orderShowDTO.getHasProductPlusName();
		this.hasUsedCouponName = orderShowDTO.getHasUsedCouponName();
		this.isChangePriceName = orderShowDTO.getIsChangePriceName();
		this.OrderStatusName = orderShowDTO.getOrderStatusName();
		this.payTypeName = orderShowDTO.getPayTypeName();
		this.payStatusName = orderShowDTO.getPayStatusName();
		this.invoiceTypeName = orderShowDTO.getInvoiceTypeName();
		this.deliveryTypeName = orderShowDTO.getDeliveryTypeName();
		this.logisticsStatusName = orderShowDTO.getLogisticsStatusName();
		this.modifyTimeStr = orderShowDTO.getModifyTimeStr();
	}
}
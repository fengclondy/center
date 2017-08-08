package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.List;

public class TradeOrdersQueryInDTO extends VenusTradeOrdersQueryInDTO implements Serializable {

	private static final long serialVersionUID = -825510315914028295L;
	// ----------------------------运营平台检索条件 start-----------------------
	/**
	 * 交易号
	 */
	private String tradeNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 子订单号
	 */
	private String orderItemNo;
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 物流状态
	 */
	private String logisticsStatus;
	/**
	 * 分销单号
	 */
	private String erpSholesalerCode;
	/**
	 * 供应商名称
	 */
	private String sellerName;
	
	/**
	 * 会员名称
	 */
	private String buyerName;
	
	/**
	 * 供应商类型
	 */
	private String sellerType;
	/**
	 * 外部平台采购编号
	 */
	private String outerChannelOrderNo;
	/**
	 * 采购订单状态
	 */
	private String outerChannelPuchaseStatus;
	/**
	 * 平台公司状态
	 */
	private String outerChannelStatus;
	
	/**
	 * 是否删取消 0：不取消1：取消2：vms删除
	 */
	private Integer isCancelOrderItem;
	/**
	 * 订单异常状态
	 */
	private String orderItemErrorStatus;
	// ----------------------------运营平台检索条件 end-----------------------
	/**
	 * 商品+商品渠道编码
	 */
	private List<String> productPlusChannelCodeList;
	
	/**
	 * 商品编码
	 */
	private String itemCode;
	
	/**
	 * 是否为包厢 0：区域1：包厢
	 * @return
	 */
	private Integer isBoxFlag;
	
	private String buySellerName;//供应商或者会员名称
	
	private Integer buySellerType;//供应商或者会员类型1：供应商2：会员
	
	/**
	 * 查询专用
	 * 0:普通订单
	 * 1：秒杀订单
	 * 2：优惠券订单
	 */
	private Integer orderType;
	
	public String getBuySellerName() {
		return buySellerName;
	}

	public void setBuySellerName(String buySellerName) {
		this.buySellerName = buySellerName;
	}

	public Integer getBuySellerType() {
		return buySellerType;
	}

	public void setBuySellerType(Integer buySellerType) {
		this.buySellerType = buySellerType;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public Integer getIsCancelOrderItem() {
		return isCancelOrderItem;
	}

	public void setIsCancelOrderItem(Integer isCancelOrderItem) {
		this.isCancelOrderItem = isCancelOrderItem;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(String erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getOuterChannelOrderNo() {
		return outerChannelOrderNo;
	}

	public void setOuterChannelOrderNo(String outerChannelOrderNo) {
		this.outerChannelOrderNo = outerChannelOrderNo;
	}

	public String getOuterChannelPuchaseStatus() {
		return outerChannelPuchaseStatus;
	}

	public void setOuterChannelPuchaseStatus(String outerChannelPuchaseStatus) {
		this.outerChannelPuchaseStatus = outerChannelPuchaseStatus;
	}

	public String getOuterChannelStatus() {
		return outerChannelStatus;
	}

	public void setOuterChannelStatus(String outerChannelStatus) {
		this.outerChannelStatus = outerChannelStatus;
	}

	public List<String> getProductPlusChannelCodeList() {
		return productPlusChannelCodeList;
	}

	public void setProductPlusChannelCodeList(List<String> productPlusChannelCodeList) {
		this.productPlusChannelCodeList = productPlusChannelCodeList;
	}

	public String getOrderItemErrorStatus() {
		return orderItemErrorStatus;
	}

	public void setOrderItemErrorStatus(String orderItemErrorStatus) {
		this.orderItemErrorStatus = orderItemErrorStatus;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}

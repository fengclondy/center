package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用作输出条件查询订单列表(暂时跟TradeOrdersQueryInNewDTO一样) add by lh 20161122
 */
public class TradeOrdersQueryOutDTO implements Serializable {

	private static final long serialVersionUID = -825510315914028295L;

	private String tradeNo;// 交易号
	private String orderNo;// 订单号
	private String orderItemStatus;// 订单行状态
	private String orderItemNo;// 子订单号
	private String payStatus;// 支付状态
	private String logisticsStatus;// 物流状态
	private Long erpSholesalerCode;// 分销单号
	private String supplierName;// 供货商名称
	private String sellerName;// 卖家名称(公司名称)
	private String sellerType;// 供应商类型
	private Date createOrderTime;// 下单时间、订单日期
	private Date createStart;// 下单开始时间
	private Date createEnd;// 下单结束时间
	private String outerChannelPuchaseStatus;// 采购订单状态
	private Long erpDistributionId;// 分销单ID
	private String buyerName;// 会员名称、客户名称
	private String consigneeAddress;// 收货地址
	private String outerChannelOrderNo;// 外界渠道采购编号
	private BigDecimal commissionAmount;// 佣金
	private BigDecimal orderTotalPrice;// 订单总价
	private BigDecimal orderTotalAmount;// 订单总金额
	private String orderStatus;// 订单状态
	private String channelCode;// 渠道编码，外部平台
	private String outerChannelStatus;// 平台公司状态
	private BigDecimal orderItemTotalAmount;// 订单行实付金额
	private BigDecimal goodsAmount;// 订单行总额
	private Integer goodsCount;// 商品数量
	private Integer isChangePrice;// 是否议价
	private String deliveryType;// 配送方式
	private String consigneeName;// 联系人
	private String orderRemarks;// 备注
	private String consigneePhoneNum;// 收货人联系话
	private String totalGoodsCount;// 商品总数量
	private BigDecimal totalDiscountAmount;// 订单行优惠金额
	private BigDecimal orderPayAmount;//订单实付金额
	private Date modifyTime;//更新时间

	//jcl 新增
	private String buyerType;// 会员类型

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
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

	public Long getErpSholesalerCode() {
		return erpSholesalerCode;
	}

	public void setErpSholesalerCode(Long erpSholesalerCode) {
		this.erpSholesalerCode = erpSholesalerCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public Date getCreateStart() {
		return createStart;
	}

	public void setCreateStart(Date createStart) {
		this.createStart = createStart;
	}

	public Date getCreateEnd() {
		return createEnd;
	}

	public void setCreateEnd(Date createEnd) {
		this.createEnd = createEnd;
	}

	public String getOuterChannelPuchaseStatus() {
		return outerChannelPuchaseStatus;
	}

	public void setOuterChannelPuchaseStatus(String outerChannelPuchaseStatus) {
		this.outerChannelPuchaseStatus = outerChannelPuchaseStatus;
	}

	public Long getErpDistributionId() {
		return erpDistributionId;
	}

	public void setErpDistributionId(Long erpDistributionId) {
		this.erpDistributionId = erpDistributionId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getOuterChannelOrderNo() {
		return outerChannelOrderNo;
	}

	public void setOuterChannelOrderNo(String outerChannelOrderNo) {
		this.outerChannelOrderNo = outerChannelOrderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getOuterChannelStatus() {
		return outerChannelStatus;
	}

	public void setOuterChannelStatus(String outerChannelStatus) {
		this.outerChannelStatus = outerChannelStatus;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getConsigneePhoneNum() {
		return consigneePhoneNum;
	}

	public void setConsigneePhoneNum(String consigneePhoneNum) {
		this.consigneePhoneNum = consigneePhoneNum;
	}

	public String getTotalGoodsCount() {
		return totalGoodsCount;
	}

	public void setTotalGoodsCount(String totalGoodsCount) {
		this.totalGoodsCount = totalGoodsCount;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public BigDecimal getOrderItemTotalAmount() {
		return orderItemTotalAmount;
	}

	public void setOrderItemTotalAmount(BigDecimal orderItemTotalAmount) {
		this.orderItemTotalAmount = orderItemTotalAmount;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}


}

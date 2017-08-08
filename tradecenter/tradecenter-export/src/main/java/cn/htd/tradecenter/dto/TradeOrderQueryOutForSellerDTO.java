package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/16.
 */

// 卖家中心，订单列表
public class TradeOrderQueryOutForSellerDTO implements Serializable {

	private static final long serialVersionUID = -825510315914028295L;
	private String orderNo;// 订单号
	private String shopName;// 店铺名称
	private BigDecimal totalGoodsAmount;// 订单总金额
	private BigDecimal totalFreight;// 运费总金额
	private BigDecimal totalDiscountAmount;//用券优惠总金额   订单分担优惠券总金额
	private BigDecimal platformDiscountAmount;//平台用券优惠总金额   分担优惠券总金额中，平台优惠总金额
	private BigDecimal shopDiscountAmount;//店铺用券优惠总金额   分担优惠券总金额中，店铺优惠总金额
	private String orderStatus;// 订单状态
	private Date createOrderTime;// 下单时间
	private String buyerName;// 买家名称
	private String consigneeName;// 收货人姓名
	private String consigneeAddress;// 收货人地址
	private String consigneePhoneNum;// 收货人电话
	private Integer isChangePrice;// 是否议价
	private Long shopId;// 店铺id

	private String invoiceNotify;// 开票户头
	private String deliveryType;// 配送方式
	private String invoiceType;// 发票类型
	private String taxManId;// 纳税人识别号
	private String invoiceAddress;// 发票地址
	private String contactPhone;// 发票联系电话
	private String bankName;// 开户银行
	private String bankAccount;// 银行账号
	private String orderRemarks;// 订单备注
	private String buyerRemarks;// 卖家备注
	private String payType;// 支付方式
	private String logisticsCompany;// 快递公司
	private String logisticsNo;// 快递单号
	private String promotionId;// 优惠券号id
	private BigDecimal bargainingOrderAmount;//改价后订单金额
	private BigDecimal bargainingOrderFreight;//改价后运费金额
	private BigDecimal beforeTotalPrice;//改价总金额
	private Integer totalGoodsCount;//订单商品总数量
	private BigDecimal orderPayAmount;//实际支付
	private BigDecimal orderTotalAmount;//订单总价
	private Integer isCancelOrder;//是否是取消订单 0:未取消，1：已取消
	private Integer isExpressDelivery;//是否快递公司发货 0:否，1:是

	// 订单行信息
	private List<TradeOrderItemsDTO> items = new ArrayList<TradeOrderItemsDTO>();

	public String getOrderNo() {
		return orderNo;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getTotalGoodsAmount() {
		return totalGoodsAmount;
	}

	public void setTotalGoodsAmount(BigDecimal totalGoodsAmount) {
		this.totalGoodsAmount = totalGoodsAmount;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneePhoneNum() {
		return consigneePhoneNum;
	}

	public void setConsigneePhoneNum(String consigneePhoneNum) {
		this.consigneePhoneNum = consigneePhoneNum;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public List<TradeOrderItemsDTO> getItems() {
		return items;
	}

	public void setItems(List<TradeOrderItemsDTO> items) {
		this.items = items;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getPlatformDiscountAmount() {
		return platformDiscountAmount;
	}

	public void setPlatformDiscountAmount(BigDecimal platformDiscountAmount) {
		this.platformDiscountAmount = platformDiscountAmount;
	}

	public BigDecimal getShopDiscountAmount() {
		return shopDiscountAmount;
	}

	public void setShopDiscountAmount(BigDecimal shopDiscountAmount) {
		this.shopDiscountAmount = shopDiscountAmount;
	}

	public BigDecimal getBargainingOrderAmount() {
		return bargainingOrderAmount;
	}

	public void setBargainingOrderAmount(BigDecimal bargainingOrderAmount) {
		this.bargainingOrderAmount = bargainingOrderAmount;
	}

	public BigDecimal getBargainingOrderFreight() {
		return bargainingOrderFreight;
	}

	public void setBargainingOrderFreight(BigDecimal bargainingOrderFreight) {
		this.bargainingOrderFreight = bargainingOrderFreight;
	}

	public BigDecimal getBeforeTotalPrice() {
		return beforeTotalPrice;
	}

	public void setBeforeTotalPrice(BigDecimal beforeTotalPrice) {
		this.beforeTotalPrice = beforeTotalPrice;
	}

	public Integer getTotalGoodsCount() {
		return totalGoodsCount;
	}

	public void setTotalGoodsCount(Integer totalGoodsCount) {
		this.totalGoodsCount = totalGoodsCount;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public Integer getIsExpressDelivery() {
		return isExpressDelivery;
	}

	public void setIsExpressDelivery(Integer isExpressDelivery) {
		this.isExpressDelivery = isExpressDelivery;
	}
}

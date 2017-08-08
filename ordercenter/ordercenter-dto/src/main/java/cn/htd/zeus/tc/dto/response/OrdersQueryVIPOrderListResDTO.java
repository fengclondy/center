package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrdersQueryVIPOrderListResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559978524864939014L;

	private String tradeNo;

	private String orderNo;

	private String site;

	private String buyerCode;

	private String buyerType;

	private String buyerName;

	private String sellerCode;

	private String sellerType;

	private String sellerName;

	private Long shopId;

	private String shopName;

	private String orderFrom;

	private String salesType;

	private String salesDepartmentCode;

	private Integer hasProductplusFlag;

	private Integer totalGoodsCount;

	private BigDecimal totalGoodsAmount;

	private BigDecimal totalFreight;

	private BigDecimal totalDiscountAmount;

	private BigDecimal shopDiscountAmount;

	private BigDecimal platformDiscountAmount;

	private BigDecimal usedRebateAmount;

	private BigDecimal bargainingOrderAmount;

	private BigDecimal bargainingOrderFreight;

	private BigDecimal orderTotalAmount;

	private BigDecimal orderPayAmount;

	private Date createOrderTime;

	private Integer hasUsedCoupon;

	private Integer isChangePrice;

	private String orderStatus;

	private Date orderReceiptTime;

	private String orderErrorStatus;

	private Date orderErrorTime;

	private String orderErrorReason;

	private Integer isCancelOrder;

	private Date orderCancelTime;

	private Long orderCancelMemberId;

	private String orderCancelMemberName;

	private String orderCancelReason;

	private String payType;

	private Date payTimeLimit;

	private Date payOrderTime;

	private String payStatus;

	private String paySerialNo;

	private Integer isOutDistribtion;

	private Integer isTimelimitedOrder;

	private String promotionId;

	private String buyerRemarks;

	private String orderRemarks;

	private Integer isNeedInvoice;

	private String invoiceType;

	private String invoiceNotify;

	private String invoiceCompanyName;

	private String taxManId;

	private String bankName;

	private String bankAccount;

	private String contactPhone;

	private String invoiceAddress;

	private String deliveryType;

	private String consigneeName;

	private String consigneePhoneNum;

	private String consigneeAddress;

	private String consigneeAddressProvince;

	private String consigneeAddressCity;

	private String consigneeAddressDistrict;

	private String consigneeAddressTown;

	private String consigneeAddressDetail;

	private String postCode;

	private String logisticsStatus;

	private String logisticsCompany;

	private String logisticsNo;

	private BigDecimal usedActivityAccountAmount;

	private Integer orderDeleteStatus;

	private String pk;

	private Long createId;

	private String createName;

	private Date createTime;

	private List<OrdersQueryVIPOrderItemListResDTO> orderItemsList;

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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	public Integer getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(Integer hasProductplusFlag) {
		this.hasProductplusFlag = hasProductplusFlag;
	}

	public Integer getTotalGoodsCount() {
		return totalGoodsCount;
	}

	public void setTotalGoodsCount(Integer totalGoodsCount) {
		this.totalGoodsCount = totalGoodsCount;
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

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getShopDiscountAmount() {
		return shopDiscountAmount;
	}

	public void setShopDiscountAmount(BigDecimal shopDiscountAmount) {
		this.shopDiscountAmount = shopDiscountAmount;
	}

	public BigDecimal getPlatformDiscountAmount() {
		return platformDiscountAmount;
	}

	public void setPlatformDiscountAmount(BigDecimal platformDiscountAmount) {
		this.platformDiscountAmount = platformDiscountAmount;
	}

	public BigDecimal getUsedRebateAmount() {
		return usedRebateAmount;
	}

	public void setUsedRebateAmount(BigDecimal usedRebateAmount) {
		this.usedRebateAmount = usedRebateAmount;
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

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public Integer getHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(Integer hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderReceiptTime() {
		return orderReceiptTime;
	}

	public void setOrderReceiptTime(Date orderReceiptTime) {
		this.orderReceiptTime = orderReceiptTime;
	}

	public String getOrderErrorStatus() {
		return orderErrorStatus;
	}

	public void setOrderErrorStatus(String orderErrorStatus) {
		this.orderErrorStatus = orderErrorStatus;
	}

	public Date getOrderErrorTime() {
		return orderErrorTime;
	}

	public void setOrderErrorTime(Date orderErrorTime) {
		this.orderErrorTime = orderErrorTime;
	}

	public String getOrderErrorReason() {
		return orderErrorReason;
	}

	public void setOrderErrorReason(String orderErrorReason) {
		this.orderErrorReason = orderErrorReason;
	}

	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public Date getOrderCancelTime() {
		return orderCancelTime;
	}

	public void setOrderCancelTime(Date orderCancelTime) {
		this.orderCancelTime = orderCancelTime;
	}

	public Long getOrderCancelMemberId() {
		return orderCancelMemberId;
	}

	public void setOrderCancelMemberId(Long orderCancelMemberId) {
		this.orderCancelMemberId = orderCancelMemberId;
	}

	public String getOrderCancelMemberName() {
		return orderCancelMemberName;
	}

	public void setOrderCancelMemberName(String orderCancelMemberName) {
		this.orderCancelMemberName = orderCancelMemberName;
	}

	public String getOrderCancelReason() {
		return orderCancelReason;
	}

	public void setOrderCancelReason(String orderCancelReason) {
		this.orderCancelReason = orderCancelReason;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}

	public Date getPayOrderTime() {
		return payOrderTime;
	}

	public void setPayOrderTime(Date payOrderTime) {
		this.payOrderTime = payOrderTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo;
	}

	public Integer getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(Integer isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public Integer getIsTimelimitedOrder() {
		return isTimelimitedOrder;
	}

	public void setIsTimelimitedOrder(Integer isTimelimitedOrder) {
		this.isTimelimitedOrder = isTimelimitedOrder;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify;
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName;
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId;
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

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
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

	public String getConsigneePhoneNum() {
		return consigneePhoneNum;
	}

	public void setConsigneePhoneNum(String consigneePhoneNum) {
		this.consigneePhoneNum = consigneePhoneNum;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeAddressProvince() {
		return consigneeAddressProvince;
	}

	public void setConsigneeAddressProvince(String consigneeAddressProvince) {
		this.consigneeAddressProvince = consigneeAddressProvince;
	}

	public String getConsigneeAddressCity() {
		return consigneeAddressCity;
	}

	public void setConsigneeAddressCity(String consigneeAddressCity) {
		this.consigneeAddressCity = consigneeAddressCity;
	}

	public String getConsigneeAddressDistrict() {
		return consigneeAddressDistrict;
	}

	public void setConsigneeAddressDistrict(String consigneeAddressDistrict) {
		this.consigneeAddressDistrict = consigneeAddressDistrict;
	}

	public String getConsigneeAddressTown() {
		return consigneeAddressTown;
	}

	public void setConsigneeAddressTown(String consigneeAddressTown) {
		this.consigneeAddressTown = consigneeAddressTown;
	}

	public String getConsigneeAddressDetail() {
		return consigneeAddressDetail;
	}

	public void setConsigneeAddressDetail(String consigneeAddressDetail) {
		this.consigneeAddressDetail = consigneeAddressDetail;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
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

	public BigDecimal getUsedActivityAccountAmount() {
		return usedActivityAccountAmount;
	}

	public void setUsedActivityAccountAmount(BigDecimal usedActivityAccountAmount) {
		this.usedActivityAccountAmount = usedActivityAccountAmount;
	}

	public Integer getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(Integer orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<OrdersQueryVIPOrderItemListResDTO> getOrderItemsList() {
		return orderItemsList;
	}

	public void setOrderItemsList(
			List<OrdersQueryVIPOrderItemListResDTO> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}
   
}

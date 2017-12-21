package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TradeOrdersDTO implements Serializable {

	private static final long serialVersionUID = -2644517116542048906L;

	private Long id;

	private String tradeNo;

	private String orderNo;

	private String site;

	private Long buyerId;

	private String buyerCode;

	private String buyerType;

	private String buyerName;

	private Long sellerId;

	private String sellerCode;

	private String sellerErpCode;

	private String sellerType;

	private String sellerName;

	private Long shopId;

	private String shopName;

	private String orderFrom;

	private String salesType;

	private String salesDepartmentCode;

	private int hasProductplusFlag;

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

	private int hasUsedCoupon;

	private int isChangePrice;

	private String orderStatus;

	private Date orderReceiptTime;

	private String orderErrorStatus;

	private Date orderErrorTime;

	private String orderErrorReason;

	private int isCancelOrder;

	private Date orderCancelTime;

	private Long orderCancelMemberId;

	private String orderCancelMemberName;

	private String orderCancelReason;

	private String payType;

	private Date payTimeLimit;

	private Date payOrderTime;

	private String payStatus;

	private String paySerialNo;

	private int isOutDistribtion;

	private int isTimelimitedOrder;

	private String promotionId;

	private String buyerRemarks;

	private String orderRemarks;

	private int isNeedInvoice;

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

	private int isExpressDelivery;

	private String logisticsStatus;

	private String logisticsCompany;

	private String logisticsNo;

	private BigDecimal usedActivityAccountAmount;

	private int orderDeleteStatus;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	private Date confirmTime;

	private List<TradeOrderItemsDTO> orderItemList;

	private List<TradeOrderErpDistributionDTO> erpDistributionDTOList;

	private List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList;

	private List<TradeOrderPayInfoDTO> orderPayDTOList;

	private List<String> erpLockBalanceCodeList;

	private List<String> erpSholesalerCodeList;

	private List<String> promotionNameList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo == null ? null : tradeNo.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site == null ? null : site.trim();
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType == null ? null : buyerType.trim();
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName == null ? null : buyerName.trim();
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerErpCode() {
		return sellerErpCode;
	}

	public void setSellerErpCode(String sellerErpCode) {
		this.sellerErpCode = sellerErpCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType == null ? null : sellerType.trim();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName == null ? null : sellerName.trim();
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
		this.shopName = shopName == null ? null : shopName.trim();
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom == null ? null : orderFrom.trim();
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType == null ? null : salesType.trim();
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode == null ? null : salesDepartmentCode.trim();
	}

	public int getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(int hasProductplusFlag) {
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

	public int getHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(int hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public int getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(int isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
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
		this.orderErrorStatus = orderErrorStatus == null ? null : orderErrorStatus.trim();
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
		this.orderErrorReason = orderErrorReason == null ? null : orderErrorReason.trim();
	}

	public int getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(int isCancelOrder) {
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
		this.orderCancelMemberName = orderCancelMemberName == null ? null : orderCancelMemberName.trim();
	}

	public String getOrderCancelReason() {
		return orderCancelReason;
	}

	public void setOrderCancelReason(String orderCancelReason) {
		this.orderCancelReason = orderCancelReason == null ? null : orderCancelReason.trim();
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
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
		this.payStatus = payStatus == null ? null : payStatus.trim();
	}

	public String getPaySerialNo() {
		return paySerialNo;
	}

	public void setPaySerialNo(String paySerialNo) {
		this.paySerialNo = paySerialNo == null ? null : paySerialNo.trim();
	}

	public int getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(int isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public int getIsTimelimitedOrder() {
		return isTimelimitedOrder;
	}

	public void setIsTimelimitedOrder(int isTimelimitedOrder) {
		this.isTimelimitedOrder = isTimelimitedOrder;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId == null ? null : promotionId.trim();
	}

	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks == null ? null : buyerRemarks.trim();
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks == null ? null : orderRemarks.trim();
	}

	public int getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(int isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType == null ? null : invoiceType.trim();
	}

	public String getInvoiceNotify() {
		return invoiceNotify;
	}

	public void setInvoiceNotify(String invoiceNotify) {
		this.invoiceNotify = invoiceNotify == null ? null : invoiceNotify.trim();
	}

	public String getInvoiceCompanyName() {
		return invoiceCompanyName;
	}

	public void setInvoiceCompanyName(String invoiceCompanyName) {
		this.invoiceCompanyName = invoiceCompanyName == null ? null : invoiceCompanyName.trim();
	}

	public String getTaxManId() {
		return taxManId;
	}

	public void setTaxManId(String taxManId) {
		this.taxManId = taxManId == null ? null : taxManId.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone == null ? null : contactPhone.trim();
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType == null ? null : deliveryType.trim();
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName == null ? null : consigneeName.trim();
	}

	public String getConsigneePhoneNum() {
		return consigneePhoneNum;
	}

	public void setConsigneePhoneNum(String consigneePhoneNum) {
		this.consigneePhoneNum = consigneePhoneNum == null ? null : consigneePhoneNum.trim();
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
	}

	public String getConsigneeAddressProvince() {
		return consigneeAddressProvince;
	}

	public void setConsigneeAddressProvince(String consigneeAddressProvince) {
		this.consigneeAddressProvince = consigneeAddressProvince == null ? null : consigneeAddressProvince.trim();
	}

	public String getConsigneeAddressCity() {
		return consigneeAddressCity;
	}

	public void setConsigneeAddressCity(String consigneeAddressCity) {
		this.consigneeAddressCity = consigneeAddressCity == null ? null : consigneeAddressCity.trim();
	}

	public String getConsigneeAddressDistrict() {
		return consigneeAddressDistrict;
	}

	public void setConsigneeAddressDistrict(String consigneeAddressDistrict) {
		this.consigneeAddressDistrict = consigneeAddressDistrict == null ? null : consigneeAddressDistrict.trim();
	}

	public String getConsigneeAddressTown() {
		return consigneeAddressTown;
	}

	public void setConsigneeAddressTown(String consigneeAddressTown) {
		this.consigneeAddressTown = consigneeAddressTown == null ? null : consigneeAddressTown.trim();
	}

	public String getConsigneeAddressDetail() {
		return consigneeAddressDetail;
	}

	public void setConsigneeAddressDetail(String consigneeAddressDetail) {
		this.consigneeAddressDetail = consigneeAddressDetail == null ? null : consigneeAddressDetail.trim();
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode == null ? null : postCode.trim();
	}

	public int getIsExpressDelivery() {
		return isExpressDelivery;
	}

	public void setIsExpressDelivery(int isExpressDelivery) {
		this.isExpressDelivery = isExpressDelivery;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus == null ? null : logisticsStatus.trim();
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
	}

	public BigDecimal getUsedActivityAccountAmount() {
		return usedActivityAccountAmount;
	}

	public void setUsedActivityAccountAmount(BigDecimal usedActivityAccountAmount) {
		this.usedActivityAccountAmount = usedActivityAccountAmount;
	}

	public int getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(int orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
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
		this.createName = createName == null ? null : createName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the confirmTime
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}

	/**
	 * @param confirmTime
	 *            the confirmTime to set
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public List<TradeOrderItemsDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<TradeOrderItemsDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<TradeOrderErpDistributionDTO> getErpDistributionDTOList() {
		return erpDistributionDTOList;
	}

	public void setErpDistributionDTOList(List<TradeOrderErpDistributionDTO> erpDistributionDTOList) {
		this.erpDistributionDTOList = erpDistributionDTOList;
	}

	public List<TradeOrderStatusHistoryDTO> getOrderStatusHistoryDTOList() {
		return orderStatusHistoryDTOList;
	}

	public void setOrderStatusHistoryDTOList(List<TradeOrderStatusHistoryDTO> orderStatusHistoryDTOList) {
		this.orderStatusHistoryDTOList = orderStatusHistoryDTOList;
	}

	public List<TradeOrderPayInfoDTO> getOrderPayDTOList() {
		return orderPayDTOList;
	}

	public void setOrderPayDTOList(List<TradeOrderPayInfoDTO> orderPayDTOList) {
		this.orderPayDTOList = orderPayDTOList;
	}

	public List<String> getErpLockBalanceCodeList() {
		return erpLockBalanceCodeList;
	}

	public void setErpLockBalanceCodeList(List<String> erpLockBalanceCodeList) {
		this.erpLockBalanceCodeList = erpLockBalanceCodeList;
	}

	public List<String> getErpSholesalerCodeList() {
		return erpSholesalerCodeList;
	}

	public void setErpSholesalerCodeList(List<String> erpSholesalerCodeList) {
		this.erpSholesalerCodeList = erpSholesalerCodeList;
	}

	public List<String> getPromotionNameList() {
		return promotionNameList;
	}

	public void setPromotionNameList(List<String> promotionNameList) {
		this.promotionNameList = promotionNameList;
	}

	public void setTradeOrderInfo(TradeOrdersDTO orderDTO) {
		this.tradeNo = orderDTO.getTradeNo();
		this.orderNo = orderDTO.getOrderNo();
		this.site = orderDTO.getSite();
		this.buyerId = orderDTO.getBuyerId();
		this.buyerCode = orderDTO.getBuyerCode();
		this.buyerType = orderDTO.getBuyerType();
		this.buyerName = orderDTO.getBuyerName();
		this.sellerId = orderDTO.getSellerId();
		this.sellerCode = orderDTO.getSellerCode();
		this.sellerType = orderDTO.getSellerType();
		this.sellerName = orderDTO.getSellerName();
		this.sellerErpCode = orderDTO.getSellerErpCode();
		this.shopId = orderDTO.getShopId();
		this.shopName = orderDTO.getShopName();
		this.orderFrom = orderDTO.getOrderFrom();
		this.salesType = orderDTO.getSalesType();
		this.salesDepartmentCode = orderDTO.getSalesDepartmentCode();
		this.hasProductplusFlag = orderDTO.getHasProductplusFlag();
		this.totalGoodsCount = orderDTO.getTotalGoodsCount();
		this.totalGoodsAmount = orderDTO.getTotalGoodsAmount();
		this.totalFreight = orderDTO.getTotalFreight();
		this.bargainingOrderAmount = orderDTO.getBargainingOrderAmount();
		this.bargainingOrderFreight = orderDTO.getBargainingOrderFreight();
		this.orderTotalAmount = orderDTO.getOrderTotalAmount();
		this.orderPayAmount = orderDTO.getOrderPayAmount();
		this.createOrderTime = orderDTO.getCreateOrderTime();
		this.orderStatus = orderDTO.getOrderStatus();
		this.orderReceiptTime = orderDTO.getOrderReceiptTime();
		this.orderErrorStatus = orderDTO.getOrderErrorStatus();
		this.orderErrorTime = orderDTO.getOrderErrorTime();
		this.orderErrorReason = orderDTO.getOrderErrorReason();
		this.isCancelOrder = orderDTO.getIsCancelOrder();
		this.orderCancelTime = orderDTO.getOrderCancelTime();
		this.orderCancelMemberId = orderDTO.getOrderCancelMemberId();
		this.orderCancelMemberName = orderDTO.getOrderCancelMemberName();
		this.orderCancelReason = orderDTO.getOrderCancelReason();
		this.payType = orderDTO.getPayType();
		this.payTimeLimit = orderDTO.getPayTimeLimit();
		this.payOrderTime = orderDTO.getPayOrderTime();
		this.payStatus = orderDTO.getPayStatus();
		this.paySerialNo = orderDTO.getPaySerialNo();
		this.isOutDistribtion = orderDTO.getIsOutDistribtion();
		this.isTimelimitedOrder = orderDTO.getIsTimelimitedOrder();
		this.promotionId = orderDTO.getPromotionId();
		this.buyerRemarks = orderDTO.getBuyerRemarks();
		this.orderRemarks = orderDTO.getOrderRemarks();
		this.isNeedInvoice = orderDTO.getIsNeedInvoice();
		this.invoiceType = orderDTO.getInvoiceType();
		this.invoiceNotify = orderDTO.getInvoiceNotify();
		this.invoiceCompanyName = orderDTO.getInvoiceCompanyName();
		this.taxManId = orderDTO.getTaxManId();
		this.bankName = orderDTO.getBankName();
		this.bankAccount = orderDTO.getBankAccount();
		this.contactPhone = orderDTO.getContactPhone();
		this.invoiceAddress = orderDTO.getInvoiceAddress();
		this.deliveryType = orderDTO.getDeliveryType();
		this.consigneeName = orderDTO.getConsigneeName();
		this.consigneePhoneNum = orderDTO.getConsigneePhoneNum();
		this.consigneeAddress = orderDTO.getConsigneeAddress();
		this.consigneeAddressProvince = orderDTO.getConsigneeAddressProvince();
		this.consigneeAddressCity = orderDTO.getConsigneeAddressCity();
		this.consigneeAddressDistrict = orderDTO.getConsigneeAddressDistrict();
		this.consigneeAddressTown = orderDTO.getConsigneeAddressTown();
		this.consigneeAddressDetail = orderDTO.getConsigneeAddressDetail();
		this.postCode = orderDTO.getPostCode();
		this.isExpressDelivery = orderDTO.getIsExpressDelivery();
		this.logisticsStatus = orderDTO.getLogisticsStatus();
		this.logisticsCompany = orderDTO.getLogisticsCompany();
		this.logisticsNo = orderDTO.getLogisticsNo();
		this.usedActivityAccountAmount = orderDTO.getUsedActivityAccountAmount();
		this.orderDeleteStatus = orderDTO.getOrderDeleteStatus();
	}

	public void setTradeOrderAllInfo(TradeOrdersDTO orderDTO) {
		this.setTradeOrderInfo(orderDTO);
		this.totalDiscountAmount = orderDTO.getTotalDiscountAmount();
		this.shopDiscountAmount = orderDTO.getShopDiscountAmount();
		this.platformDiscountAmount = orderDTO.getPlatformDiscountAmount();
		this.usedRebateAmount = orderDTO.getUsedRebateAmount();
		this.hasUsedCoupon = orderDTO.getHasUsedCoupon();
		this.isChangePrice = orderDTO.getIsChangePrice();
		this.createId = orderDTO.getCreateId();
		this.createName = orderDTO.getCreateName();
		this.createTime = orderDTO.getCreateTime();
		this.modifyId = orderDTO.getModifyId();
		this.modifyName = orderDTO.getModifyName();
		this.modifyTime = orderDTO.getModifyTime();
		this.confirmTime = orderDTO.getConfirmTime();
		this.orderItemList = orderDTO.getOrderItemList();
		this.erpDistributionDTOList = orderDTO.getErpDistributionDTOList();
		this.orderStatusHistoryDTOList = orderDTO.getOrderStatusHistoryDTOList();
		this.orderPayDTOList = orderDTO.getOrderPayDTOList();
		this.erpLockBalanceCodeList = orderDTO.getErpLockBalanceCodeList();
		this.erpSholesalerCodeList = orderDTO.getErpSholesalerCodeList();
		this.promotionNameList = orderDTO.getPromotionNameList();
		this.salesDepartmentCode = orderDTO.getSalesDepartmentCode();
	}
}
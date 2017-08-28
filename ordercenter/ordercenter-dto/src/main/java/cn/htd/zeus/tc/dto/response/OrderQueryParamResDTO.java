package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 查询订单返回DTO(orderList) Copyright (C), 2013-2016, 汇通达网络有限公司 FileName:
 * OrderQueryParamResDTO.java Author: jiaop Date: 2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述 History: //修改记录
 */
public class OrderQueryParamResDTO extends GenricResDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2123791264009204711L;

	/**
	 * 主键
	 */
	private Long id;

	private String site;

	private String sellerQQ;

	/**
	 * 交易号
	 */
	private String tradeNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	private Integer orderDeleteStatus;

	/**
	 * 买家id
	 */
	private Long buyerId;

	/**
	 * 买家code
	 */
	private String buyerCode;

	/**
	 * 买家类型
	 */
	private String buyerType;

	/**
	 * 买家名称
	 */
	private String buyerName;

	/**
	 * 卖家id
	 */
	private Long sellerId;

	/**
	 * 卖家code
	 */
	private String sellerCode;

	/**
	 * 卖家类型
	 */
	private String sellerType;

	/**
	 * 卖家名称
	 */
	private String sellerName;

	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 订单来源
	 */
	private String orderFrom;

	/**
	 * 售卖类型
	 */
	private String salesType;
	
	private Integer orderType;

	/**
	 * 销售部门编码
	 */
	private String salesDepartmentCode;

	/**
	 *
	 */
	private Integer hasProductplusFlag;

	/**
	 * 订单商品数量
	 */
	private Integer totalGoodsCount;

	/**
	 * 订单商品金额
	 */
	private BigDecimal totalGoodsAmount;

	/**
	 * 运费
	 */
	private BigDecimal totalFreight;

	/**
	 * 优惠金额
	 */
	private BigDecimal totalDiscountAmount;

	/**
	 * 店铺优惠金额
	 */
	private BigDecimal shopDiscountAmount;

	/**
	 * 平台用券优惠总金额
	 */
	private BigDecimal platformDiscountAmount;

	/**
	 * 使用返利金额
	 */
	private BigDecimal usedRebateAmount;

	/**
	 * 议价后价格
	 */
	private BigDecimal bargainingOrderAmount;

	/**
	 * 议价后运费
	 */
	private BigDecimal bargainingOrderFreight;

	/**
	 * 订单总金额
	 */
	private BigDecimal orderTotalAmount;

	/**
	 * 订单支付金额
	 */
	private BigDecimal orderPayAmount;

	/**
	 * 下单时间
	 */
	private Date createOrderTime;

	/**
	 * 是否参与优惠
	 */
	private Integer hasUsedCoupon;

	/**
	 * 是否议价
	 */
	private Integer isChangePrice;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单收货时间
	 */
	private Date orderReceiptTime;

	/**
	 * 订单异常状态
	 */
	private String orderErrorStatus;

	/**
	 * 订单异常时间
	 */
	private Date orderErrorTime;

	/**
	 * 订单取消时间
	 */
	private String orderCancelStatus;

	/**
	 * 订单取消时间
	 */
	private Date orderCancelTime;

	/**
	 * 订单取消人id
	 */
	private Long orderCancelMemberId;

	/**
	 * 订单取消人名称
	 */
	private String orderCancelMemberName;

	/**
	 * 订单取消原因
	 */
	private String orderCancelReason;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 支付时间
	 */
	private Date payOrderTime;

	private Date payTimeLimit;

	/**
	 * 支付状态
	 */
	private String payStatus;

	/**
	 * 是否超出配送范围
	 */
	private String isOutDistribtion;

	/**
	 *
	 */
	private Integer isRefund;

	private Date refundTime;

	/**
	 * 是否为秒杀订单 0-否，1-是
	 */
	private Integer isTimelimitedOrder;

	/**
	 * 促销活动ID
	 */
	private String promotionId;

	/**
	 * 买家留言
	 */
	private String buyerRemarks;

	/**
	 * 订单备注
	 */
	private String orderRemarks;

	/**
	 * 是否要发票
	 */
	private Integer isNeedInvoice;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 普通发票抬头
	 */
	private String invoiceNotify;

	/**
	 * 增值税发票公司名称
	 */
	private String invoiceCompanyName;

	/**
	 * 纳税人识别号
	 */
	private String taxManId;

	/**
	 * 开户行名称
	 */
	private String bankName;

	/**
	 * 银行账号
	 */
	private String bankAccount;

	/**
	 * 银行账号预留手机号
	 */
	private String contactPhone;

	/**
	 * 发票邮寄地址-详细
	 */
	private String invoiceAddress;

	/**
	 * 配送方式
	 */
	private String deliveryType;

	/**
	 * 联系人
	 */
	private String consigneeName;

	/**
	 * 联系人电话
	 */
	private String consigneePhoneNum;

	/**
	 * 联系人地址
	 */
	private String consigneeAddress;

	/**
	 * 省
	 */
	private String consigneeAddressProvince;

	/**
	 * 市
	 */
	private String consigneeAddressCity;

	/**
	 * 区
	 */
	private String consigneeAddressDistrict;

	/**
	 * 镇
	 */
	private String consigneeAddressTown;

	/**
	 * 详细
	 */
	private String consigneeAddressDetail;

	/**
	 * 邮政编码
	 */
	private String postCode;

	/**
	 * 物流状态
	 */
	private String logisticsStatus;

	/**
	 * 物流公司
	 */
	private String logisticsCompany;

	/**
	 * 物流编号
	 */
	private String logisticsNo;

	/**
	 * 使用活动账户金额
	 */
	private BigDecimal usedActivityAccountAmount;

	/**
	 * 创建人id
	 */
	private Long createId;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人id
	 */
	private Long modifyId;

	/**
	 * 修改人
	 */
	private String modifyName;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	private String orderErrorReason;

	private Integer isCancelOrder;

	private String erpPayCode;// 余额帐支付编号

	private String pk;// 数据迁移用主键

	private List<OrderQueryItemsResDTO> orderItemsList;

	private List<TradeOrderStatusHistoryResDTO> orderTail;

	public String getOrderNo() {
		return orderNo;
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

	/**
	 * @return the sellerQQ
	 */
	public String getSellerQQ() {
		return sellerQQ;
	}

	/**
	 * @param sellerQQ
	 *            the sellerQQ to set
	 */
	public void setSellerQQ(String sellerQQ) {
		this.sellerQQ = sellerQQ;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(Integer isChangePrice) {
		this.isChangePrice = isChangePrice;
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

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public BigDecimal getTotalGoodsAmount() {
		return totalGoodsAmount;
	}

	public void setTotalGoodsAmount(BigDecimal totalGoodsAmount) {
		this.totalGoodsAmount = totalGoodsAmount;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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

	public List<OrderQueryItemsResDTO> getOrderItemsList() {
		return orderItemsList;
	}

	public void setOrderItemsList(List<OrderQueryItemsResDTO> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public List<TradeOrderStatusHistoryResDTO> getOrderTail() {
		return orderTail;
	}

	public void setOrderTail(List<TradeOrderStatusHistoryResDTO> orderTail) {
		this.orderTail = orderTail;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

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
		this.tradeNo = tradeNo;
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Integer getHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(Integer hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
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

	public String getOrderCancelStatus() {
		return orderCancelStatus;
	}

	public void setOrderCancelStatus(String orderCancelStatus) {
		this.orderCancelStatus = orderCancelStatus;
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

	public String getIsOutDistribtion() {
		return isOutDistribtion;
	}

	public void setIsOutDistribtion(String isOutDistribtion) {
		this.isOutDistribtion = isOutDistribtion;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
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
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOrderErrorReason() {
		return orderErrorReason;
	}

	public void setOrderErrorReason(String orderErrorReason) {
		this.orderErrorReason = orderErrorReason;
	}

	public String getErpPayCode() {
		return erpPayCode;
	}

	public void setErpPayCode(String erpPayCode) {
		this.erpPayCode = erpPayCode;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(Integer orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
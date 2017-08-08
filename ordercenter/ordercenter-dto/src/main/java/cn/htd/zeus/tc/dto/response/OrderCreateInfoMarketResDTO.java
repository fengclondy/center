package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class OrderCreateInfoMarketResDTO  extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8362833259898482947L;
	
	//交易号
	private String tradeNo;
	
	//会员编号
	private String buyerCode;
	
	//优惠卷编码
	private String couponCode;

	//促销活动ID
	private String promotionId;
	
	//是否要发票
	private int isNeedInvoice;
	
	//发票类型
	private String invoiceType;
	
	//普通发票抬头
	private String invoiceNotify;
	
	//增值税发票公司名称
	private String invoiceCompanyName;
	
	//发票邮寄地址
	private String invoiceAddress;
	
	//配送方式
	private String deliveryType;
	
	//收货人姓名
	private String consigneeName;
	
	//收货人联系电话
	private String consigneePhoneNum;
	
	//收货地址
	private String consigneeAddress;
	
	//收货地址-省
	private String consigneeAddressProvince;
	
	//收货地址-市
	private String consigneeAddressCity;
	
	//收货地址-区
	private String consigneeAddressDistrict;
	
	//收货地址-镇
	private String consigneeAddressTown;
	
	//收货地址-详细
	private String consigneeAddressDetail;
	
	//促销活动类型 1：优惠券，2:秒杀
	private String promotionType;
	
	//邮政编码
	private String postCode;
	
	//站点
	private String site;
	
	//消息ID
	private String messageId;

	//订单集合
	private List<OrderCreateListInfoMarketResDTO> orderList;
	
	//优惠券类型
	private String couponType;
	
	//会员类型
	private String buyerType;
		
	//会员名称
	private String buyerName;
	
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
    
	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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
	
	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public List<OrderCreateListInfoMarketResDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderCreateListInfoMarketResDTO> orderList) {
		this.orderList = orderList;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
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
}

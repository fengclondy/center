package cn.htd.zeus.tc.dto.resquest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class OrderCreateInfoReqDTO extends OrderCreateInfoMarketReqDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7773190570896945662L;

	//交易号
	@NotEmpty(message = "tradeNo不能为空")
	private String tradeNo;
	
	//会员编号
	@NotEmpty(message = "buyerCode不能为空")
	private String buyerCode;
	
	//优惠卷编码
	private String couponCode;

	//促销活动ID
	private String promotionId;
	
	//是否要发票
	@NotNull(message = "isNeedInvoice不能为空")
	private Integer isNeedInvoice;
	
	//发票类型
	private String invoiceType;
	
	//普通发票抬头
	private String invoiceNotify;
	
	//增值税发票公司名称
	private String invoiceCompanyName;
	
	//纳税人识别号
	private String taxManId;

	//开户行名称
	private String bankName;
	
	//银行账号
	private String bankAccount;

	//联系电话
	private String contactPhone;
	
	//发票邮寄地址
	private String invoiceAddress;
	
	//配送方式  1:供应商配送  2:自提
	@NotEmpty(message = "deliveryType不能为空")
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
	
	//秒杀锁定编号  秒杀时候必传
	private String seckillLockNo;
	
	//邮政编码
	private String postCode;
	
	//站点
	@NotEmpty(message = "site不能为空")
	private String site;

	//订单集合
	@NotNull(message = "orderList不能为空")
	@Valid
	private List<OrderCreateListInfoReqDTO> orderList;
	
	//是否有限时购商品  0:没有，1：有
	private int isHasLimitedTimePurchase;

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

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<OrderCreateListInfoReqDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderCreateListInfoReqDTO> orderList) {
		this.orderList = orderList;
	}

	public String getSeckillLockNo() {
		return seckillLockNo;
	}

	public void setSeckillLockNo(String seckillLockNo) {
		this.seckillLockNo = seckillLockNo;
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

	public int getIsHasLimitedTimePurchase() {
		return isHasLimitedTimePurchase;
	}

	public void setIsHasLimitedTimePurchase(int isHasLimitedTimePurchase) {
		this.isHasLimitedTimePurchase = isHasLimitedTimePurchase;
	}	
	
}

package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class TradeInfoDTO implements Serializable {

	private static final long serialVersionUID = -3039390975190044394L;

	/**
	 * 交易号
	 */
	private String tradeNo;
	/**
	 * 秒杀锁定编号 秒杀时候必传
	 */
	private String seckillLockNo;
	/**
	 * 买家编码
	 */
	@NotBlank(message = "买家编码不能为空")
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
	 * 买家会员等级
	 */
	@NotBlank(message = "买家等级不能为空")
	private String buyerGrade;
	/**
	 * 是否要发票
	 */
	private int isNeedInvoice;

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
	 * 联系电话
	 */
	private String contactPhone;

	/**
	 * 发票邮寄地址
	 */
	private String invoiceAddress;

	/**
	 * 配送方式
	 */
	private String deliveryType;

	/**
	 * 收货人姓名
	 */
	private String consigneeName;

	/**
	 * 收货人联系电话
	 */
	private String consigneePhoneNum;

	/**
	 * 收货地址
	 */
	private String consigneeAddress;

	/**
	 * 收货地址-省
	 */
	private String consigneeAddressProvince;

	/**
	 * 收货地址-市
	 */
	private String consigneeAddressCity;

	/**
	 * 收货地址-区
	 */
	private String consigneeAddressDistrict;

	/**
	 * 收货地址-镇
	 */
	private String consigneeAddressTown;

	/**
	 * 收货地址-详细
	 */
	private String consigneeAddressDetail;

	/**
	 * 邮政编码
	 */
	private String postCode;

	/**
	 * 站点
	 */
	private String site;

	/**
	 * 店铺商品List
	 */
	@NotNull(message = "购物车商品不能为空")
	@Valid
	private List<OrderInfoDTO> orderList;

	/**
	 * 能够适用的平台和店铺优惠券列表
	 */
	private List<OrderItemCouponDTO> avaliableCouponList = new ArrayList<OrderItemCouponDTO>();

	/**
	 * 不能够适用的平台和店铺优惠券列表
	 */
	private List<OrderItemCouponDTO> unavaliableCouponList = new ArrayList<OrderItemCouponDTO>();

	/**
	 * 是否有参加秒杀活动的商品
	 */
	private boolean hasTimelimitedProduct = false;

	/**
	 * 有秒杀活动时的秒杀活动ID
	 */
	private String promotionId;

	/**
	 * 参加优惠活动类型 1:优惠券，2:秒杀
	 */
	private String promotionType;

	/**
	 * 使用优惠券时设定优惠券编码列表
	 */
	private List<String> couponCodeList;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getSeckillLockNo() {
		return seckillLockNo;
	}

	public void setSeckillLockNo(String seckillLockNo) {
		this.seckillLockNo = seckillLockNo;
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

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<OrderInfoDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderInfoDTO> orderList) {
		this.orderList = orderList;
	}

	public List<OrderItemCouponDTO> getAvaliableCouponList() {
		return avaliableCouponList;
	}

	public void setAvaliableCouponList(List<OrderItemCouponDTO> avaliableCouponList) {
		this.avaliableCouponList = avaliableCouponList;
	}

	public List<OrderItemCouponDTO> getUnavaliableCouponList() {
		return unavaliableCouponList;
	}

	public void setUnavaliableCouponList(List<OrderItemCouponDTO> unavaliableCouponList) {
		this.unavaliableCouponList = unavaliableCouponList;
	}

	public boolean isHasTimelimitedProduct() {
		return hasTimelimitedProduct;
	}

	public void setHasTimelimitedProduct(boolean hasTimelimitedProduct) {
		this.hasTimelimitedProduct = hasTimelimitedProduct;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public List<String> getCouponCodeList() {
		return couponCodeList;
	}

	public void setCouponCodeList(List<String> couponCodeList) {
		this.couponCodeList = couponCodeList;
	}

	public void initBeforeCalculateCoupon() {
		avaliableCouponList = new ArrayList<OrderItemCouponDTO>();
		unavaliableCouponList = new ArrayList<OrderItemCouponDTO>();
		hasTimelimitedProduct = false;
		if (orderList != null && !orderList.isEmpty()) {
			for (OrderInfoDTO orderDTO : orderList) {
				orderDTO.initBeforeCalculateCoupon();
			}
		}
	}
}

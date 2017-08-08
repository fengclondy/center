package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberOutsideSupplierDTO implements Serializable{

	private static final long serialVersionUID = -2484363325259147908L;
	
	private Long memberId;//会员Id
	private String companyName;//公司名称
	private String companyCode;//商家编号  
	private String sellerType;//商家类型   0全部  1外部  2二代
	private String realNameStatus;//企业实名认证状态
	private String cardBindStatus;//银行卡绑定状态
	private String locationProvince;//省   
	private String locationCity;//市    
	private String locationCounty;//区
	private String locationTown;//镇
	private String locationAllAddress;//商家地域
	private String shopCode;//店铺编号
	private String shopName;//店铺名称
	private String shopStatus;//店铺状态
	private String shopStartTime;//店铺开通时间
	
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	public String getCardBindStatus() {
		return cardBindStatus;
	}
	public void setCardBindStatus(String cardBindStatus) {
		this.cardBindStatus = cardBindStatus;
	}
	public String getLocationAllAddress() {
		return locationAllAddress;
	}
	public void setLocationAllAddress(String locationAllAddress) {
		this.locationAllAddress = locationAllAddress;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getLocationProvince() {
		return locationProvince;
	}
	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public String getLocationCounty() {
		return locationCounty;
	}
	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}
	public String getLocationTown() {
		return locationTown;
	}
	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}
	public String getShopStartTime() {
		return shopStartTime;
	}
	public void setShopStartTime(String shopStartTime) {
		this.shopStartTime = shopStartTime;
	}
	

}

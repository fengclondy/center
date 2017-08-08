package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberVerifyStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private String companyName;// 公司名称
	private String locationProvince;// 省
	private String locationCity;// 市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationDetail;// 详细地址
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人电话
	private Integer isDiffIndustry;// 是否异业：1是,0否
	private String industryCategory;// 行业类型
	private String registTime;// 注册时间
	private String locationAddr;// 会员地址

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public Integer getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(Integer isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	/**
	 * @return the locationAddr
	 */
	public String getLocationAddr() {
		return locationAddr;
	}

	/**
	 * @param locationAddr
	 *            the locationAddr to set
	 */
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

}

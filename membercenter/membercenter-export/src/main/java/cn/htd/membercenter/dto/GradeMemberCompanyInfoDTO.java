package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class GradeMemberCompanyInfoDTO implements Serializable {

	private static final long serialVersionUID = 4706273589905626690L;

	private String artificialPersonMobile;// 手机号
	private Integer isVip;// 是否收费会员
	private Long buyerId;// 会员ID
	private String memberPackageType;// 会员套餐类型
	private Date packageActiveStartTime;// 套餐生效开始时间
	private Date packageActiveEndTime;// 套餐生效结束时间
	private Date packageUpdateTime;// 套餐更新时间

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getMemberPackageType() {
		return memberPackageType;
	}

	public void setMemberPackageType(String memberPackageType) {
		this.memberPackageType = memberPackageType;
	}

	public Date getPackageActiveStartTime() {
		return packageActiveStartTime;
	}

	public void setPackageActiveStartTime(Date packageActiveStartTime) {
		this.packageActiveStartTime = packageActiveStartTime;
	}

	public Date getPackageActiveEndTime() {
		return packageActiveEndTime;
	}

	public void setPackageActiveEndTime(Date packageActiveEndTime) {
		this.packageActiveEndTime = packageActiveEndTime;
	}

	public Date getPackageUpdateTime() {
		return packageUpdateTime;
	}

	public void setPackageUpdateTime(Date packageUpdateTime) {
		this.packageUpdateTime = packageUpdateTime;
	}

}

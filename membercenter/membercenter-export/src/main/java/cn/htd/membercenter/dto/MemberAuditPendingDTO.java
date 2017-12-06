package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberAuditPendingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
   
	/**
	 * 会员id或者商家id，会员和商家的id都是member_base_info表的id
	 */
	private Long memberId; 
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 法人姓名
	 */
	private String artificialPersonName;
	
	/**
	 * 是否异业
	 */
	private String isDiffIndustry;
	
	/**
	 * 审核状态 1：待审核，2：已通过
	 */
	private String verifyStatus;
	
	/**
	 * 注册开始时间
	 */
	private Date startTime;
	
	/**
	 * 注册结束时间
	 */
	private Date endTime;

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

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(String isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

package cn.htd.membercenter.dto;

import java.io.Serializable;

public class SellerInfoDTO implements Serializable{

	/**
	 * 
	 */
	

	private static final long serialVersionUID = 8714912471165992142L;
	
	private Long memberId;
	private String memberCode;
	private String companyName;
	private String companyCode;
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
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

}

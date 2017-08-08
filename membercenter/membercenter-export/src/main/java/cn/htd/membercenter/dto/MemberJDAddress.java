package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberJDAddress implements Serializable{

	private static final long serialVersionUID = -2309570969066412540L;
	
	private Long memberID;//
	
	private String companyName;//公司名称
	
	private String memberCode;//商家编码
	
	private String consigneeName;// 收货人姓名
	
	private String consigneeMobile;// 收货人手机号码
	

	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	
	

}

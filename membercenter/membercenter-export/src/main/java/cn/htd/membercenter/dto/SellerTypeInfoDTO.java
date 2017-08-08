package cn.htd.membercenter.dto;

import java.io.Serializable;

public class SellerTypeInfoDTO implements Serializable {

	private static final long serialVersionUID = 2326380371272457843L;
	private Long memberID;// 供应商ID
	private String companyCode;// 供应商编码
	private String sellerType;// 供应商类型
	private String companyName;// 供应商名称

	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

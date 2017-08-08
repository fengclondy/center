package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class MembVeriBrandCategDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger memberId;//会员ID
	private String businessCategory;//经营类目
	private String majorBusinessCategory;//主营类目
	private String businessBrand;//经营品牌
	public BigInteger getMemberId() {
		return memberId;
	}
	public void setMemberId(BigInteger memberId) {
		this.memberId = memberId;
	}
	public String getBusinessCategory() {
		return businessCategory;
	}
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	public String getMajorBusinessCategory() {
		return majorBusinessCategory;
	}
	public void setMajorBusinessCategory(String majorBusinessCategory) {
		this.majorBusinessCategory = majorBusinessCategory;
	}
	public String getBusinessBrand() {
		return businessBrand;
	}
	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand;
	}
	
	
}

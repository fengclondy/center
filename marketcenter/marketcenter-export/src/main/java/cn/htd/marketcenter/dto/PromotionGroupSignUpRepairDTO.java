package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class PromotionGroupSignUpRepairDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//手机号
	private String mobile;
	//收货人名称
	private String recevierName;
	//会员店编码
	private String memberCode;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRecevierName() {
		return recevierName;
	}
	public void setRecevierName(String recevierName) {
		this.recevierName = recevierName;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
	

}

package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerBaseInfoDTO</p>
* @author root
* @date 2016年12月27日
* <p>Description: 
*			买家中心   认证信息
* </p>
 */
public class MemberBuyerAuthenticationDTO implements Serializable{

	private static final long serialVersionUID = -289060242327217106L;

	private Long memberId;
	private String isPhoneAuthenticated;//是否手机号已验证  0否 1是
	private String artificialPersonMobile;//手机号
	private String realNameStatus;//企业实名认证状态 1.未认2.审核中 3.认证成功 4.认证失败'
	private Long modifyId;//修改人id
	private String modifyName;//修改人名称
	private String artificialPersonName;//法人姓名
	private String artificialPersonIdcard;//法人身份证号
	
	
	public String getArtificialPersonName() {
		return artificialPersonName;
	}
	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}
	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}
	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}
	public Long getModifyId() {
		return modifyId;
	}
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}
	public String getModifyName() {
		return modifyName;
	}
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getIsPhoneAuthenticated() {
		return isPhoneAuthenticated;
	}
	public void setIsPhoneAuthenticated(String isPhoneAuthenticated) {
		this.isPhoneAuthenticated = isPhoneAuthenticated;
	}
	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}
	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	
}

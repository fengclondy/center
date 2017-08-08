package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberRemoveRelationshipDTO implements Serializable {

	private static final long serialVersionUID = 9065001383771849625L;

	private Long memberId;// 会员ID
	private String memberCode;//会员code
	private Long oldSellerId;// 会员ID
	private Long belongMemberId;// 归属商家ID
	private String curBelongManagerId;// 归属客户经理iD
	private String companyName;// 会员名称
	private String contactMobile;// 手机号
	private String artificialPersonMobile;// 法人手机号码
	private String belongCompanyName;// 当前归属商家名称
	private String companyCode;// JL代码 （公司编码）列表页 详情页面
	private String companyCodeDetail;// JL代码(当前归属公司JL) 详细页面
	private String status;// 状态 1为待审核，2为驳回，9为审核通过
	private String remark;// 驳回原因
	private Long verifyInfoId;// 审批表id
	private Long modifyId;
	private String modifyName;

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
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

	public Long getBelongMemberId() {
		return belongMemberId;
	}

	public void setBelongMemberId(Long belongMemberId) {
		this.belongMemberId = belongMemberId;
	}

	public Long getVerifyInfoId() {
		return verifyInfoId;
	}

	public void setVerifyInfoId(Long verifyInfoId) {
		this.verifyInfoId = verifyInfoId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getBelongCompanyName() {
		return belongCompanyName;
	}

	public void setBelongCompanyName(String belongCompanyName) {
		this.belongCompanyName = belongCompanyName;
	}

	public String getCompanyCodeDetail() {
		return companyCodeDetail;
	}

	public void setCompanyCodeDetail(String companyCodeDetail) {
		this.companyCodeDetail = companyCodeDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	/**
	 * @return the oldSellerId
	 */
	public Long getOldSellerId() {
		return oldSellerId;
	}

	/**
	 * @param oldSellerId
	 *            the oldSellerId to set
	 */
	public void setOldSellerId(Long oldSellerId) {
		this.oldSellerId = oldSellerId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
	
}

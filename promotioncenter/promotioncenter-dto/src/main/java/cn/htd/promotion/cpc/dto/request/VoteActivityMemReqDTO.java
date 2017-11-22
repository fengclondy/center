package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class VoteActivityMemReqDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035130844818817569L;
	@NotNull(message="voteId不能为空")
	private Long voteId;
	@NotEmpty(message="memberCode不能为空")
	private String memberCode;
	@NotEmpty(message="memberName不能为空")
	private String memberName;
	@NotEmpty(message="vendorName不能为空")
	private String vendorName;
	private String contactName;
	private String contactPhone;
	@NotEmpty(message="operatorName不能为空")
	private String operatorName;
	@NotNull(message="operatorId不能为空")
	private Long operatorId;
	public Long getVoteId() {
		return voteId;
	}
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
}

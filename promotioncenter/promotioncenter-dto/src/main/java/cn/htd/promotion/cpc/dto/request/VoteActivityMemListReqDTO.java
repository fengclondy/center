package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

public class VoteActivityMemListReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6018186252615751034L;
	private String voteId;
	//平台公司名称
	private String sellerName;
	//会员店名称
	private String memberName;
	//0：待审核；1：已审核；2：驳回
	private Integer auditStatus;
	//报名状态； 0：未报名 1：已报名
	private Integer signUpStatus;
	
	private Integer start;
	
	private Integer pageSize;
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Integer getSignUpStatus() {
		return signUpStatus;
	}
	public void setSignUpStatus(Integer signUpStatus) {
		this.signUpStatus = signUpStatus;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getVoteId() {
		return voteId;
	}
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}
	
	
}

package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class VoteActivityMemListResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1332179944149075388L;
	//排序号
	private Integer sortNum;
	//平台名称
	private String sellerName;
	//会员店名称
	private String memberName;
	//联系人名称
	private String contactName;
	//手机号
	private String phone;
	//报名状态； 0：未报名 1：已报名
	private Integer signupStatus;
	//审核状态；0：待审核；1：已审核；2：已驳回
	private Integer auditStatus;
	//报名时间
	private Date signupTime;
	//投票数
	private Integer voteCount;
	//转发数
	private Integer forwardCount;
	//投票会员id
	private Long voteMemberId;
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
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
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSignupStatus() {
		return signupStatus;
	}
	public void setSignupStatus(Integer signupStatus) {
		this.signupStatus = signupStatus;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Date getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}
	public Integer getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
	public Integer getForwardCount() {
		return forwardCount;
	}
	public void setForwardCount(Integer forwardCount) {
		this.forwardCount = forwardCount;
	}
	public Long getVoteMemberId() {
		return voteMemberId;
	}
	public void setVoteMemberId(Long voteMemberId) {
		this.voteMemberId = voteMemberId;
	}

}

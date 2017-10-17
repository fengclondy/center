package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class VoteActivityMemResDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359072162960512347L;
	private Integer sortNum;
	private String sellerName;
	private String memberName;
	private String contactName;
	private String contactPhone;
	private Integer signStatus;
	private Integer auditStatus;
	private Date signupTime;
	private Date voteStartTime;
	private Date voteEndTime;
	private Date voteSignupStartTime;
	private Date voteSignupEndTime;
	private String memberActivityDec;
	private String voteName;
	private String voteTopicPic;
	private String voteSamplePic;
	private Integer voteCount;
	private Integer forwardCount;
	private Long voteMemberId;
	private Date memberVoteLastTime;
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
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
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
	public Date getVoteStartTime() {
		return voteStartTime;
	}
	public void setVoteStartTime(Date voteStartTime) {
		this.voteStartTime = voteStartTime;
	}
	public Date getVoteEndTime() {
		return voteEndTime;
	}
	public void setVoteEndTime(Date voteEndTime) {
		this.voteEndTime = voteEndTime;
	}
	public Date getVoteSignupStartTime() {
		return voteSignupStartTime;
	}
	public void setVoteSignupStartTime(Date voteSignupStartTime) {
		this.voteSignupStartTime = voteSignupStartTime;
	}
	public Date getVoteSignupEndTime() {
		return voteSignupEndTime;
	}
	public void setVoteSignupEndTime(Date voteSignupEndTime) {
		this.voteSignupEndTime = voteSignupEndTime;
	}
	public String getMemberActivityDec() {
		return memberActivityDec;
	}
	public void setMemberActivityDec(String memberActivityDec) {
		this.memberActivityDec = memberActivityDec;
	}
	public String getVoteName() {
		return voteName;
	}
	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}
	public String getVoteTopicPic() {
		return voteTopicPic;
	}
	public void setVoteTopicPic(String voteTopicPic) {
		this.voteTopicPic = voteTopicPic;
	}
	public String getVoteSamplePic() {
		return voteSamplePic;
	}
	public void setVoteSamplePic(String voteSamplePic) {
		this.voteSamplePic = voteSamplePic;
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
	public Date getMemberVoteLastTime() {
		return memberVoteLastTime;
	}
	public void setMemberVoteLastTime(Date memberVoteLastTime) {
		this.memberVoteLastTime = memberVoteLastTime;
	}
	
	
}

package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class VoteActivityMemResDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359072162960512347L;
	private Integer sortNum;
	//平台公司名称
	private String sellerName;
	//会员店名称
	private String memberName;
	//联系人
	private String contactName;
	//联系人电话
	private String contactPhone;
	//报名状态； 0：未报名 1：已报名
	private Integer signStatus;
	//审核状态；0：待审核；1：已审核；2：驳回
	private Integer auditStatus;
	//报名时间
	private Date signupTime;
	//投票开始时间
	private Date voteStartTime;
	//投票结束时间
	private Date voteEndTime;
	//投票活动报名开始时间
	private Date voteSignupStartTime;
	//投票活动报名结束时间
	private Date voteSignupEndTime;
	//会员店活动宣言
	private String memberActivityDec;
	//活动名称
	private String voteName;
	//活动主题图片
	private String voteTopicPic;
	//投票示意图
	private String voteSamplePic;
	//投票数
	private Integer voteCount;
	//转发数
	private Integer forwardCount;
	//投票活动会员店id
	private Long voteMemberId;
	//(截止目前)最终投票时间
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

package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class VoteActivityListResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -40725551660531952L;
	
	//投票活动id
	private Long voteId;
	//投票活动名字
	private String voteActivityName;
	//1 进行中、2 未开始、3 已结束（根据投票开始、结束时间对应状态）
	private Integer status;
	//投票开始时间
    private Date voteStartTime;
    //投票结束时间
    private Date voteEndTime;
    //投票活动报名开始时间
    private Date voteSignUpStartTime;
    //投票活动截止时间
    private Date voteSignUpEndTime;
    //授权门店数
    private Integer authShopCount;
    //报名门店数
    private Integer signupShopCount;
    //投票数
    private Long voteCount;
    //转发数
    private Long forwardCount;
    
	public Date getVoteSignUpStartTime() {
		return voteSignUpStartTime;
	}
	public void setVoteSignUpStartTime(Date voteSignUpStartTime) {
		this.voteSignUpStartTime = voteSignUpStartTime;
	}
	public Date getVoteSignUpEndTime() {
		return voteSignUpEndTime;
	}
	public void setVoteSignUpEndTime(Date voteSignUpEndTime) {
		this.voteSignUpEndTime = voteSignUpEndTime;
	}
	public Long getVoteId() {
		return voteId;
	}
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getVoteActivityName() {
		return voteActivityName;
	}
	public void setVoteActivityName(String voteActivityName) {
		this.voteActivityName = voteActivityName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getAuthShopCount() {
		return authShopCount;
	}
	public void setAuthShopCount(Integer authShopCount) {
		this.authShopCount = authShopCount;
	}
	public Integer getSignupShopCount() {
		return signupShopCount;
	}
	public void setSignupShopCount(Integer signupShopCount) {
		this.signupShopCount = signupShopCount;
	}
	public Long getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(Long voteCount) {
		this.voteCount = voteCount;
	}
	public Long getForwardCount() {
		return forwardCount;
	}
	public void setForwardCount(Long forwardCount) {
		this.forwardCount = forwardCount;
	}
    
    
}

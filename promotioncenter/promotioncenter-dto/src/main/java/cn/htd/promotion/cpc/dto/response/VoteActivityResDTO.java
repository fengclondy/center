package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class VoteActivityResDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2180315925144834135L;
	
    //投票活动id
	private Long voteId;
	//投票活动名称
	@NotEmpty(message="投票活动名称未维护")
	@Length(max=10,message="投票活动名称不能超过10个字符")
    private String voteName;
    //活动主题图片
	@NotEmpty(message="活动主题图片未维护")
    private String voteTopicPic;
    //活动报名示意
	@NotEmpty(message="活动报名示意未维护")
    private String voteSamplePic;
    //每个帐户每日最多可投多少家店
	@Digits(integer=5,fraction=5,message="每个帐户每日最多可投多少家店要为数字")
	@NotNull(message="每个帐户每日最多可投多少家店未维护")
    private Integer voteSotreNumPAccountPDay;
    //每个帐户每日最多可投多少张票
	@Digits(integer=5,fraction=5,message="每个帐户每日最多可投多少张票要为数字")
	@NotNull(message="每个帐户每日最多可投多少张票未维护")
    private Integer voteNumPAccountPDayPStore;
    //投票报名开始时间
	@NotNull(message="投票报名开始时间未维护")
    private Date voteSignUpStartTime;
    //投票报名结束时间
	@NotNull(message="投票报名结束时间未维护")
    private Date voteSignUpEndTime;
    //投票开始时间
	@NotNull(message="投票开始时间未维护")
    private Date voteStartTime;
    //投票结束时间
	@NotNull(message="投票结束时间未维护")
    private Date voteEndTime;
    //删除标志：0 未删除 1 已删除
    private Integer deleteFlag;
    
    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;
    //报名规则
    @NotNull(message="报名规则未维护")
    private String voteRule;

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName == null ? null : voteName.trim();
    }

    public String getVoteTopicPic() {
        return voteTopicPic;
    }

    public void setVoteTopicPic(String voteTopicPic) {
        this.voteTopicPic = voteTopicPic == null ? null : voteTopicPic.trim();
    }

    public String getVoteSamplePic() {
        return voteSamplePic;
    }

    public void setVoteSamplePic(String voteSamplePic) {
        this.voteSamplePic = voteSamplePic == null ? null : voteSamplePic.trim();
    }

    public Integer getVoteSotreNumPAccountPDay() {
        return voteSotreNumPAccountPDay;
    }

    public void setVoteSotreNumPAccountPDay(Integer voteSotreNumPAccountPDay) {
        this.voteSotreNumPAccountPDay = voteSotreNumPAccountPDay;
    }

    public Integer getVoteNumPAccountPDayPStore() {
        return voteNumPAccountPDayPStore;
    }

    public void setVoteNumPAccountPDayPStore(Integer voteNumPAccountPDayPStore) {
        this.voteNumPAccountPDayPStore = voteNumPAccountPDayPStore;
    }

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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getVoteRule() {
        return voteRule;
    }

    public void setVoteRule(String voteRule) {
        this.voteRule = voteRule == null ? null : voteRule.trim();
    }
}
package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class VoteActivityResDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2180315925144834135L;

	private Long voteId;

    private String voteName;

    private String voteTopicPic;

    private String voteSamplePic;

    private Integer voteSotreNumPAccountPDay;

    private Integer voteNumPAccountPDayPStore;

    private Date voteSignUpStartTime;

    private Date voteSignUpEndTime;

    private Date voteStartTime;

    private Date voteEndTime;

    private Byte deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

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

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
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
package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 汇掌柜-会员店投票详情
 */
public class VoteActivityMemberVoteDetailDTO implements Serializable {

    private static final long serialVersionUID = 5654998071269663340L;

    /**
     * 活动ID
     */
    private Long voteId;
    /**
     * 活动名称
     */
    private String voteName;
    /**
     * 投票规则：投票门店上限
     */
    private Integer voteSotreNumPAccountPDay;
    /**
     * 投票规则：一个门店可以投票数上限
     */
    private Integer voteNumPAccountPDayPStore;
    /**
     * 会员店编码
     */
    private String memberCode;
    /**
     * 会员店名称
     */
    private String memberName;
    /**
     * 报名状态 0：未报名 1：已报名
     */
    private Integer signStatus;
    /**
     * 审核状态  0：待审核  1：已审核  2：未审核
     */
    private Integer auditStatus;
    /**
     * 投票结束时间{计算倒计时}
     */
    private Long voteEndTime;
    /**
     * 服务器时间{计算倒计时}
     */
    private Long systemTime;
    /**
     * 门店宣言
     */
    private String memberActivityDec;
    /**
     * 排名
     */
    private Integer ranking;
    /**
     * 投票数
     */
    private Integer voteNum;

    /**
     * 活动主题图片
     */
    private String voteTopicPic;

    /**
     * 图片集合
     */
    private List<String> voteActivityMemberPictureResDTOList;
    /**
     * 排名列表
     */
    private List<VoteActivityMemberRankingDTO> voteActivityMemberRankingDTOList;

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
        this.voteName = voteName;
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

    public Long getVoteEndTime() {
        return voteEndTime;
    }

    public void setVoteEndTime(Long voteEndTime) {
        this.voteEndTime = voteEndTime;
    }

    public Long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Long systemTime) {
        this.systemTime = systemTime;
    }

    public String getMemberActivityDec() {
        return memberActivityDec;
    }

    public void setMemberActivityDec(String memberActivityDec) {
        this.memberActivityDec = memberActivityDec;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public List<String> getVoteActivityMemberPictureResDTOList() {
        return voteActivityMemberPictureResDTOList;
    }

    public void setVoteActivityMemberPictureResDTOList(List<String> voteActivityMemberPictureResDTOList) {
        this.voteActivityMemberPictureResDTOList = voteActivityMemberPictureResDTOList;
    }

    public List<VoteActivityMemberRankingDTO> getVoteActivityMemberRankingDTOList() {
        return voteActivityMemberRankingDTOList;
    }

    public void setVoteActivityMemberRankingDTOList(List<VoteActivityMemberRankingDTO> voteActivityMemberRankingDTOList) {
        this.voteActivityMemberRankingDTOList = voteActivityMemberRankingDTOList;
    }

    public String getVoteTopicPic() {
        return voteTopicPic;
    }

    public void setVoteTopicPic(String voteTopicPic) {
        this.voteTopicPic = voteTopicPic;
    }
}

package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

public class VoteActivityMemberRankingDTO implements Serializable {

    private static final long serialVersionUID = 5654998071269663340L;
    /**
     * 排名号
     */
    private int rowNum;
    /**
     * 会员店名称
     */
    private String memberName;
    /**
     * 投票数
     */
    private int votenum;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getVotenum() {
        return votenum;
    }

    public void setVotenum(int votenum) {
        this.votenum = votenum;
    }
}

package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityFansForwardResDTO;

public interface VoteActivityFansForwardDAO {
    int deleteByPrimaryKey(Long forwardFansId);

    int insert(VoteActivityFansForwardResDTO record);

    int insertSelective(VoteActivityFansForwardResDTO record);

    VoteActivityFansForwardResDTO selectByPrimaryKey(Long forwardFansId);

    int updateByPrimaryKeySelective(VoteActivityFansForwardResDTO record);

    int updateByPrimaryKey(VoteActivityFansForwardResDTO record);
    
    Long selectVoteActivityForwardCount(Long voteId);

    /***
     * 根据会员报名编码查询转发数
     * @param voteMemberId
     * @return
     */
    Long selectForwordCountByVMId(Long voteMemberId);
}
package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityFansVoteResDTO;

public interface VoteActivityFansVoteDAO {
    int deleteByPrimaryKey(Long voteFansId);

    int insert(VoteActivityFansVoteResDTO record);

    int insertSelective(VoteActivityFansVoteResDTO record);

    VoteActivityFansVoteResDTO selectByPrimaryKey(Long voteFansId);

    int updateByPrimaryKeySelective(VoteActivityFansVoteResDTO record);

    int updateByPrimaryKey(VoteActivityFansVoteResDTO record);
    
    Long selectVoteCountByActivityId(Long voteId);
    
    int deleteVoteInfoByVoteMemberId(Long voteMemberId);
}
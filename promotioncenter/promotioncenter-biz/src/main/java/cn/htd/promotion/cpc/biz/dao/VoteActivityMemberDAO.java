package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import org.apache.ibatis.annotations.Param;

public interface VoteActivityMemberDAO {
    int deleteByPrimaryKey(Long voteMemberId);

    int insert(VoteActivityMemberResDTO record);

    int insertSelective(VoteActivityMemberResDTO record);

    VoteActivityMemberResDTO selectByPrimaryKey(Long voteMemberId);

    int updateByPrimaryKeySelective(VoteActivityMemberResDTO record);

    int updateByPrimaryKey(VoteActivityMemberResDTO record);

    // 有且只有一条
    VoteActivityMemberResDTO selectByVoteIdAndMemberCode(@Param("voteId") Long voteId, @Param("memberCode") String memberCode);
}
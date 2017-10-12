package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;

public interface VoteActivityMemberDAO {
    int deleteByPrimaryKey(Long voteMemberId);

    int insert(VoteActivityMemberResDTO record);

    int insertSelective(VoteActivityMemberResDTO record);

    VoteActivityMemberResDTO selectByPrimaryKey(Long voteMemberId);

    int updateByPrimaryKeySelective(VoteActivityMemberResDTO record);

    int updateByPrimaryKey(VoteActivityMemberResDTO record);
}
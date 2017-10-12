package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

public interface VoteActivityDAO {
    int deleteByPrimaryKey(Long voteId);

    int insert(VoteActivityResDTO record);

    int insertSelective(VoteActivityResDTO record);

    VoteActivityResDTO selectByPrimaryKey(Long voteId);

    int updateByPrimaryKeySelective(VoteActivityResDTO record);

    int updateByPrimaryKeyWithBLOBs(VoteActivityResDTO record);

    int updateByPrimaryKey(VoteActivityResDTO record);
}
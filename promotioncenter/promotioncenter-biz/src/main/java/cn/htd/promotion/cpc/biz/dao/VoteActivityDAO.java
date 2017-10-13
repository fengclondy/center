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

    // 查询当前时间所处的投票活动
    VoteActivityResDTO selectCurrentActivity();
}
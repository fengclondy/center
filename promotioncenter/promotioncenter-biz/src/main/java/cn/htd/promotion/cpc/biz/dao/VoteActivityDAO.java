package cn.htd.promotion.cpc.biz.dao;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    int queryVoteActivityByTime(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /***
     * 查询当前活动
     * @return
     */
    VoteActivityResDTO selectCurrentActivity();
    
    Long selectVoteActivityTotalCount(@Param("voteActName") String voteActName,@Param("actStatus") String actStatus);
    
    List<VoteActivityResDTO> selectPagedVoteActivity(@Param("start") int start,@Param("pageSize") int pageSize, @Param("voteActName")
    		String voteActName,@Param("actStatus") String actStatus);
}
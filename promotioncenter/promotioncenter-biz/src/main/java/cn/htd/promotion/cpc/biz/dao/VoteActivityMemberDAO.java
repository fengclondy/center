package cn.htd.promotion.cpc.biz.dao;

import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface VoteActivityMemberDAO {
    int deleteByPrimaryKey(Long voteMemberId);

    int insert(VoteActivityMemberResDTO record);

    int insertSelective(VoteActivityMemberResDTO record);

    VoteActivityMemberResDTO selectByPrimaryKey(Long voteMemberId);

    int updateByPrimaryKeySelective(VoteActivityMemberResDTO record);

    int updateByPrimaryKey(VoteActivityMemberResDTO record);

    // 有且只有一条
    VoteActivityMemberResDTO selectByVoteIdAndMemberCode(@Param("voteId") Long voteId, @Param("memberCode") String memberCode);

    // 查询活动下会员店投票排行 key：
    // rownum 排名
    // memberCode 会员店编码
    // memberName 会员店名称
    // votenum 得票数
    List<HashMap<String, String>> selectMemberRankingTop10(@Param("voteId") Long voteId);

    // 根据活动ID，会员店编码获取当前会员店的投票排情况
    HashMap<String, String> selectMemberRankingByMemberCode(@Param("voteId") Long voteId, @Param("memberCode") String memberCode);

}
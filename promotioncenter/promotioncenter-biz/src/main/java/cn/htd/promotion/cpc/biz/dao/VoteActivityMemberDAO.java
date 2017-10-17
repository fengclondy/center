package cn.htd.promotion.cpc.biz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;

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
    List<HashMap<String, Object>> selectMemberRankingTop10(@Param("voteId") Long voteId);

    // 根据活动ID，会员店编码获取当前会员店的投票排情况
    HashMap<String, Object> selectMemberRankingByMemberCode(@Param("voteId") Long voteId, @Param("memberCode") String memberCode);
    
    List<Map> querySignupMemberCount(@Param("voteId") Long voteId);
    
    Long queryTotalSignupMemberInfo(VoteActivityMemListReqDTO voteActivityMemListReqDTO);
    
    List<VoteActivityMemListResDTO> queryPagedSignupMemberInfoList(VoteActivityMemListReqDTO voteActivityMemListReqDTO);
    
    VoteActivityMemResDTO querySignupMemberDetailInfo(Long voteMemberId);

}
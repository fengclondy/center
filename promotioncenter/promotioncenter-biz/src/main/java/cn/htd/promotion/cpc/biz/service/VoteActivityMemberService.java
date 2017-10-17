package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.VoteActivityMemListReqDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemListResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemResDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberResDTO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 会员店投票活动报名Service
 */
public interface VoteActivityMemberService {

    /***
     * 根据活动ID和会员编码查询投票活动
     * @param voteId
     * @param memberCode
     * @return
     */
    VoteActivityMemberResDTO selectByVoteIdAndMemberCode(Long voteId,String memberCode);
}
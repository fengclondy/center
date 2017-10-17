package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberVoteDetailDTO;

import java.util.Date;

/**
 * 投票活动-粉丝投票相关服务
 * @author chenkang
 * @date 2017-10-12
 */
public interface VoteActivityFansVoteService {
    /**
     * 校验粉丝当日投此门店票数是否超过活动设定
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param memberCode
     * @param date
     * @return 投此门店票数
     */
    ExecuteResult<String> validateFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date);

    /**
     * 校验粉丝当日投门店数量是否超过活动设定
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param date
     * @return 粉丝当天投票门店数
     */
    ExecuteResult<String> validateFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date);

    /**
     * 粉丝投票
     * @param voteActivityId 投票活动ID
     * @param fansId 粉丝标志
     * @param memberCode 会员店编码
     * @return 投票是否成功
     */
    ExecuteResult<String> voteByFans(Long voteActivityId, Long fansId, String memberCode);

    /**
     * 粉丝转发
     * @param voteActivityId 投票活动ID
     * @param memberCode 会员店编码
     * @return 投票是否成功
     */
    ExecuteResult<String> forwardByFans(Long voteActivityId, String memberCode);

    /**
     * 该会员店是否展示投票活动
     * @param memberCode 会员店编码
     * @return 是否展示投票活动
     */
    ExecuteResult<String> isShowVoteActivityByMemberCode(String memberCode);

    /**
     * 获取会员店投票详情
     * @param voteActivityId
     * @param memberCode
     * @return
     */
    ExecuteResult<VoteActivityMemberVoteDetailDTO> getMemberVoteDetail(Long voteActivityId, String memberCode);
}

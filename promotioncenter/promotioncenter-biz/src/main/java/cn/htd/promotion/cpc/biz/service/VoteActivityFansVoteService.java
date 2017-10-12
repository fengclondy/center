package cn.htd.promotion.cpc.biz.service;

import java.util.Date;

/**
 * 投票活动-粉丝投票相关服务
 * @author chenkang
 * @date 2017-10-12
 */
public interface VoteActivityFansVoteService {
    /**
     * 查询粉丝当日投此门店票数
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param memberCode
     * @param date
     * @return 投此门店票数
     */
    int queryFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date);

    /**
     * 查询粉丝当日投门店数量
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param date
     * @return 粉丝当天投票门店数
     */
    int queryFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date);


    /**
     * 校验粉丝当日投此门店票数是否超过活动设定
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param memberCode
     * @param date
     * @return 投此门店票数
     */
    boolean validateFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date);

    /**
     * 校验粉丝当日投门店数量是否超过活动设定
     * @param voteActivityId 投票活动ID
     * @param fansId
     * @param date
     * @return 粉丝当天投票门店数
     */
    boolean validateFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date);

    /**
     * 粉丝投票
     * @param voteActivityId 投票活动ID
     * @param fansId 粉丝标志
     * @param memberCode 会员店编码
     * @return 投票是否成功
     */
    boolean voteByFans(Long voteActivityId, Long fansId, String memberCode);
}

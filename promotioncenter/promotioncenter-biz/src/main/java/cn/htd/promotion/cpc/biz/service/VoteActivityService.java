package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberVoteDetailDTO;
import cn.htd.promotion.cpc.dto.response.VoteActivityResDTO;

import java.util.Date;

/**
 * 投票活动-活动相关服务
 * @author zhengy
 * @date 2017-10-12
 */
public interface VoteActivityService {

    /***
     * 查询当前活动
     * @return
     */
    VoteActivityResDTO selectCurrentActivity();
}

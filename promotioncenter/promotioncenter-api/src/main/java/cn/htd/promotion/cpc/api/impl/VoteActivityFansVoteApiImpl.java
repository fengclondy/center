package cn.htd.promotion.cpc.api.impl;

import cn.htd.promotion.cpc.api.VoteActivityFansVoteApi;
import cn.htd.promotion.cpc.biz.service.VoteActivityFansVoteService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.VoteActivityMemberVoteDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 投票活动-粉丝投票相关服务
 * @author chenkang
 * @date 2017-10-12
 */
@Service("voteActivityFansVoteApi")
public class VoteActivityFansVoteApiImpl implements VoteActivityFansVoteApi {

    @Autowired
    private VoteActivityFansVoteService voteActivityFansVoteService;

    private Logger logger = LoggerFactory.getLogger(VoteActivityFansVoteApiImpl.class);

    @Override
    public ExecuteResult<String> voteByFans(Long voteActivityId, Long fansId, String memberCode) {
        logger.info("粉丝投票服务, 投票活动ID:{}, 粉丝ID:{}，会员店编码：{}", voteActivityId, fansId, memberCode);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            Date date = new Date();
            executeResult = voteActivityFansVoteService.validateFansVoteStoreNumByDay(voteActivityId, fansId, date);
            if (!executeResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                return executeResult;
            }
            executeResult = voteActivityFansVoteService.validateFansVoteNumByDayAndStore(voteActivityId, fansId, memberCode, date);
            if (!executeResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                return executeResult;
            }
            // 投票
            executeResult = voteActivityFansVoteService.voteByFans(voteActivityId, fansId, memberCode);
        } catch (Exception e) {
            logger.error("粉丝投票服务出错, 投票活动ID:{}, 粉丝ID:{}, 会员编码：{}", voteActivityId, fansId, memberCode, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> forwardByFans(Long voteActivityId, String memberCode) {
        logger.info("粉丝转发服务, 投票活动ID:{}, 会员店编码：{}", voteActivityId, memberCode);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            executeResult = this.voteActivityFansVoteService.forwardByFans(voteActivityId, memberCode);
        } catch (Exception e) {
            logger.error("粉丝转发服务出错, 投票活动ID:{}, 会员编码：{}", voteActivityId, memberCode, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> isShowVoteActivityByMemberCode(String memberCode) {
        logger.info("校验进入汇掌柜是否展示投票活动, 会员编码：{}", memberCode);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            executeResult = voteActivityFansVoteService.isShowVoteActivityByMemberCode(memberCode);
        } catch (Exception e) {
            logger.error("校验进入汇掌柜是否展示投票活动错粗, 会员编码：{}", memberCode, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<VoteActivityMemberVoteDetailDTO> getMemberVoteDetail(Long voteActivityId, String memberCode) {
        logger.info("查询会员店投票活动详情，活动ID:{}, 会员店编码:{}", voteActivityId, memberCode);
        ExecuteResult<VoteActivityMemberVoteDetailDTO> executeResult = this.voteActivityFansVoteService.getMemberVoteDetail(voteActivityId, memberCode);
        return executeResult;
    }
}

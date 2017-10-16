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
    public ExecuteResult<Integer> queryFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date) {
        logger.info("查询粉丝当日投当前门店票数, 投票活动ID:{}, 粉丝ID:{}, 会员店编码：{}，日期：{}", voteActivityId, fansId, memberCode, date);
        ExecuteResult<Integer> executeResult = new ExecuteResult<>();
        try {
            int count = voteActivityFansVoteService.queryFansVoteNumByDayAndStore(voteActivityId, fansId, memberCode, date);
            executeResult.setResult(count);
        } catch (Exception e) {
            logger.error("查询粉丝当日投当前门店票数出错, 投票活动ID:{}, 粉丝ID:{}, 会员店编码：{}，日期：{}", voteActivityId, fansId, memberCode, date, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Integer> queryFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date) {
        logger.info("查询粉丝当日投门店数量, 投票活动ID:{}, 粉丝ID:{}，日期：{}", voteActivityId, fansId, date);
        ExecuteResult<Integer> executeResult = new ExecuteResult<>();
        try {
            int count = voteActivityFansVoteService.queryFansVoteStoreNumByDay(voteActivityId, fansId, date);
            executeResult.setResult(count);
        } catch (Exception e) {
            logger.error("查询粉丝当日投门店数量出错, 投票活动ID:{}, 粉丝ID:{}，日期：{}", voteActivityId, fansId, date, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Boolean> validateFansVoteNumByDayAndStore(Long voteActivityId, Long fansId, String memberCode, Date date) {
        logger.info("校验粉丝当日投此门店票数是否超过活动设定, 投票活动ID:{}, 粉丝ID:{}, 会员店编码：{}，日期：{}", voteActivityId, fansId, date);
        ExecuteResult<Boolean> executeResult = new ExecuteResult<>();
        try {
            boolean flag = voteActivityFansVoteService.validateFansVoteNumByDayAndStore(voteActivityId, fansId, memberCode, date);
            executeResult.setResult(flag);
        } catch (Exception e) {
            logger.error("校验粉丝当日投此门店票数是否超过活动设定出错, 投票活动ID:{}, 粉丝ID:{}, 会员店编码：{}，日期：{}", voteActivityId, fansId, memberCode, date, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Boolean> validateFansVoteStoreNumByDay(Long voteActivityId, Long fansId, Date date) {
        logger.info("校验粉丝当日投门店数量是否超过活动设定, 投票活动ID:{}, 粉丝ID:{}，日期：{}", voteActivityId, fansId, date);
        ExecuteResult<Boolean> executeResult = new ExecuteResult<>();
        try {
            boolean flag = voteActivityFansVoteService.validateFansVoteStoreNumByDay(voteActivityId, fansId, date);
            executeResult.setResult(flag);
        } catch (Exception e) {
            logger.error("校验粉丝当日投此门店票数是否超过活动设定出错, 投票活动ID:{}, 粉丝ID:{}，日期：{}", voteActivityId, fansId, date, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Boolean> voteByFans(Long voteActivityId, Long fansId, String memberCode) {
        logger.info("粉丝投票服务, 投票活动ID:{}, 粉丝ID:{}，日期：{}", voteActivityId, fansId);
        ExecuteResult<Boolean> executeResult = new ExecuteResult<>();
        try {
            Date date = new Date();
            boolean flag1 = voteActivityFansVoteService.validateFansVoteStoreNumByDay(voteActivityId, fansId, date);
            if (!flag1) {
                executeResult.setCode(""); // TODO
                executeResult.setResultMessage("您今天已经达到每日可投票门店数上限，明天再来吧！");
                return executeResult;
            }
            boolean flag = voteActivityFansVoteService.validateFansVoteNumByDayAndStore(voteActivityId, fansId, memberCode, date);
            if (!flag) {
                executeResult.setCode(""); // TODO
                executeResult.setResultMessage("您今天已经投过我了，谢谢您！");
                return executeResult;
            }
            // 投票
            boolean flag2 = voteActivityFansVoteService.voteByFans(voteActivityId, fansId, memberCode);
            executeResult.setResult(flag2);
        } catch (Exception e) {
            logger.error("粉丝投票服务出错, 投票活动ID:{}, 粉丝ID:{}, 会员编码：{}", voteActivityId, fansId, memberCode, e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            executeResult.setErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<Boolean> isShowVoteActivityByMemberCode(String memberCode) {
        logger.info("校验进入汇掌柜是否展示投票活动, 会员编码：{", memberCode);
        ExecuteResult<Boolean> executeResult = new ExecuteResult<>();
        try {
            boolean flag = voteActivityFansVoteService.isShowVoteActivityByMemberCode(memberCode);
            executeResult.setResult(flag);
        } catch (Exception e) {
            logger.error("校验进入汇掌柜是否展示投票活动错粗, 会员编码：{", memberCode, e);
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

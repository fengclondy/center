package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.BuyerCheckInfo;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("promotionLotteryCommonService")
public class PromotionLotteryCommonServiceImpl implements PromotionLotteryCommonService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionLotteryCommonServiceImpl.class);

    private String LOTTERY_MAX_LOOP_SIZE = "promotion.lottery.max.loop.size";

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    /**
     * 取得Redis抽奖活动信息
     *
     * @param promotionId
     */
    public PromotionExtendInfoDTO getRedisLotteryInfo(String promotionId, Map<String, String> dictMap)
            throws PromotionCenterBusinessException {
        PromotionExtendInfoDTO lotteryInfo = null;
        String lotteryJsonStr = "";
        String validStatus = "";

        validStatus = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_VALID, promotionId);
        if (!StringUtils.isEmpty(validStatus) && !dictMap.get(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS + "&"
                + DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validStatus)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_VALID.getCode(),
                    "抽奖活动ID:" + promotionId + " 该活动未上架");
        }
        lotteryJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_INFO, promotionId);
        lotteryInfo = JSON.parseObject(lotteryJsonStr, PromotionExtendInfoDTO.class);
        if (lotteryInfo == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
                    "抽奖活动ID:" + promotionId + " 该活动不存在");
        }
        return lotteryInfo;
    }

    /**
     * 校验粉丝扭蛋活动合法性
     *
     * @param promotionInfoDTO
     * @param requestDTO
     * @param dictMap
     * @return
     * @throws PromotionCenterBusinessException
     */
    public boolean checkBuyerPromotionLotteryValid(PromotionExtendInfoDTO promotionInfoDTO,
            DrawLotteryReqDTO requestDTO, Map<String, String> dictMap) throws PromotionCenterBusinessException {
        String promotionId = requestDTO.getPromotionId();
        String buyerCode = requestDTO.getBuyerCode();
        Date nowDt = new Date();
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();
        Map<String, String> lotteryTimesInfoMap = null;
        Map<String, String> buyerTimesInfoMap = null;

        if (nowDt.before(promotionInfoDTO.getEffectiveTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_START.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动未开始");
        }
        if (nowDt.after(promotionInfoDTO.getInvalidTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_HAS_EXPIRED.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动已结束");
        }
        if (nowDt.before(DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachStartTime()))) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NOT_IN_START_TIME.getCode(),
                    "抽奖活动编号:" + promotionId + " 时间段:" + DateUtil.format(promotionInfoDTO.getEachStartTime()) + "~"
                            + DateUtil.format(promotionInfoDTO.getEachEndTime()) + " 当前时间未到该活动抽奖开始时间");
        }
        if (nowDt.after(DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachEndTime()))) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_HAS_PASSED_END_TIME.getCode(),
                    "抽奖活动编号:" + promotionId + " 时间段:" + DateUtil.format(promotionInfoDTO.getEachStartTime()) + "~"
                            + DateUtil.format(promotionInfoDTO.getEachEndTime()) + " 当前时间已过该活动抽奖结束时间");
        }
        buyerCheckInfo.setBuyerCode(buyerCode);
        buyerCheckInfo.setIsFirstLogin(requestDTO.getIsBuyerFirstLogin());
        if (!baseService.checkPromotionBuyerRule(promotionInfoDTO, buyerCheckInfo, dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_AUTHIORITY.getCode(),
                    "该活动粉丝没有抽奖权限 入参:" + JSON.toJSONString(requestDTO));
        }
        lotteryTimesInfoMap =
                promotionRedisDB.getHashOperations(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId);
        if (Integer.parseInt(lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT)) <= 0) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                    "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
        }
        if (!baseService.checkPromotionSellerRule(promotionInfoDTO, requestDTO.getSellerCode(), dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getCode(),
                    "会员店没有参加本次抽奖活动 入参:" + JSON.toJSONString(requestDTO));
        }
        buyerTimesInfoMap = promotionRedisDB
                .getHashOperations(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode);
        if (buyerTimesInfoMap == null || buyerTimesInfoMap.isEmpty()) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_AUTHIORITY.getCode(),
                    "该活动粉丝没有抽奖记录信息 入参:" + JSON.toJSONString(requestDTO));
        }
        if (Integer.parseInt(buyerTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES)) <= 0) {
            if (!lotteryTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES)
                    || Integer.parseInt(lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES))
                    <= 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_MORE_DRAW_CHANCE.getCode(),
                        "粉丝已经用完了所有抽奖机会，需分享获得额外抽奖机 入参:" + JSON.toJSONString(requestDTO));
            } else if (!lotteryTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES) ||
                    Integer.parseInt(lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES))
                            <= 0 || (buyerTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES)
                    && YesNoEnum.YES.getValue() == Integer
                    .parseInt(buyerTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES)))) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_MORE_EXTRA_CHANCE.getCode(),
                        "粉丝已经用完了自有和分享额外获取的抽奖机 入参:" + JSON.toJSONString(requestDTO));
            }
        }
        return true;
    }

    /**
     * 校验扭蛋活动合法性
     *
     * @param promotionInfoDTO
     * @param requestDTO
     * @param dictMap
     * @return
     * @throws PromotionCenterBusinessException
     */
    public boolean checkPromotionLotteryValid(PromotionExtendInfoDTO promotionInfoDTO, DrawLotteryReqDTO requestDTO,
            Map<String, String> dictMap) throws PromotionCenterBusinessException {
        String promotionId = requestDTO.getPromotionId();
        String buyerCode = requestDTO.getBuyerCode();
        Date nowDt = new Date();
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();

        if (nowDt.before(promotionInfoDTO.getEffectiveTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_START.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动未开始");
        }
        if (nowDt.after(promotionInfoDTO.getInvalidTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_HAS_EXPIRED.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动已结束");
        }
        if (!baseService.checkPromotionSellerRule(promotionInfoDTO, requestDTO.getSellerCode(), dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getCode(),
                    "会员店没有参加本次抽奖活动 入参:" + JSON.toJSONString(requestDTO));
        }
        if (!StringUtils.isEmpty(buyerCode)) {
            buyerCheckInfo.setBuyerCode(buyerCode);
            buyerCheckInfo.setIsFirstLogin(requestDTO.getIsBuyerFirstLogin());
            if (!baseService.checkPromotionBuyerRule(promotionInfoDTO, buyerCheckInfo, dictMap)) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_AUTHIORITY.getCode(),
                        "该活动粉丝没有抽奖权限 入参:" + JSON.toJSONString(requestDTO));
            }
        }
        return true;
    }

    /**
     * 执行抽奖处理
     *
     * @param requestDTO
     * @param errorWinningRecord
     * @param ticket
     */
    @Override
    public void doDrawLotteryWithThread(DrawLotteryReqDTO requestDTO, final BuyerWinningRecordDTO errorWinningRecord,
            final String ticket) {
        final String promotionId = requestDTO.getPromotionId();
        final String buyerCode = requestDTO.getBuyerCode();
        final String sellerCode = requestDTO.getSellerCode();
        new Thread() {
            public void run() {
                BuyerWinningRecordDTO winningRecordDTO = null;
                String awardPercentStr = "";
                List<PromotionAccumulatyDTO> accuList = null;
                try {
                    awardPercentStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
                            RedisConst.REDIS_LOTTERY_AWARD_WINNING_PERCENTAGE);
                    if (StringUtils.isEmpty(awardPercentStr)) {
                        throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                                "抽奖活动编号:" + promotionId + " 奖项设置异常没有设置奖项");
                    }
                    accuList = JSON.parseArray(awardPercentStr, PromotionAccumulatyDTO.class);
                    if (accuList == null || accuList.isEmpty()) {
                        throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                                "抽奖活动编号:" + promotionId + " 奖项设置异常没有设置奖项");
                    }
                    winningRecordDTO = drawLotteryAward(accuList);
                    if (winningRecordDTO == null) {
                        throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                                "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
                    }
                    if (winningRecordDTO.getRewardType().equals(errorWinningRecord.getRewardType())) {
                        promotionRedisDB.incrHash(
                                RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                                RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES);
                        promotionRedisDB.incr(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_"
                                + sellerCode);
                    }
                    winningRecordDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
                    winningRecordDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
                    winningRecordDTO.setBuyerCode(buyerCode);
                    winningRecordDTO.setSellerCode(sellerCode);
                    winningRecordDTO.setWinningTime(new Date());
                } catch (PromotionCenterBusinessException pcbe) {
                    if (winningRecordDTO == null) {
                        winningRecordDTO = errorWinningRecord;
                    }
                    winningRecordDTO.setResponseCode(pcbe.getCode());
                    winningRecordDTO.setResponseMsg(pcbe.getMessage());
                }
                promotionRedisDB.setHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                        promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket,
                        JSON.toJSONString(winningRecordDTO));
            }

            private BuyerWinningRecordDTO drawLotteryAward(List<PromotionAccumulatyDTO> accuList)
                    throws PromotionCenterBusinessException {
                int luckNo = 0;
                PromotionAccumulatyDTO goalAccuDTO = null;
                String lotteryKey = "";
                String awardJsonStr = "";
                BuyerWinningRecordDTO winningRecordDTO = null;
                String maxLoopConf = SysProperties.getProperty(LOTTERY_MAX_LOOP_SIZE);
                int maxLoopSize = StringUtils.isEmpty(maxLoopConf) ? accuList.size() : Integer.parseInt(maxLoopConf);
                int loopSize = 0;
                List<PromotionAccumulatyDTO> targetAccuList = null;
                int currentPercent = 0;
                int totalPercent = 0;
                PromotionAccumulatyDTO tmpAccuDTO = null;
                PromotionAccumulatyDTO lastAccuDTO = null;

                if (promotionRedisDB.decrHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
                        RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT).longValue() < 0) {
                    promotionRedisDB.incrHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
                            RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT);
                    throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                            "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
                }
                if (promotionRedisDB
                        .decrHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                                RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES).longValue() < 0) {
                    throw new PromotionCenterBusinessException(
                            ResultCodeEnum.LOTTERY_BUYER_NO_MORE_DRAW_CHANCE.getCode(),
                            "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 粉丝已经用完了所有抽奖机会");
                }
                if (promotionRedisDB
                        .decrHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                                RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES).longValue() < 0) {
                    throw new PromotionCenterBusinessException(
                            ResultCodeEnum.LOTTERY_BUYER_REACH_WINNING_LIMMIT.getCode(),
                            "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 粉丝已达中奖次数上限");
                }
                if (promotionRedisDB
                        .decr(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_" + sellerCode)
                        .longValue() < 0) {
                    throw new PromotionCenterBusinessException(
                            ResultCodeEnum.LOTTERY_SELLER_REACH_WINNING_LIMMIT.getCode(),
                            "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 会员店已达中奖次数上限");
                }
                while (loopSize < maxLoopSize) {
                    targetAccuList = new ArrayList<PromotionAccumulatyDTO>();
                    for (PromotionAccumulatyDTO checkAccuDTO : accuList) {
                        if ("0".equals(checkAccuDTO.getLevelAmount())) {
                            continue;
                        }
                        lotteryKey =
                                RedisConst.REDIS_LOTTERY_AWARD_PREFIX + promotionId + "_" + checkAccuDTO.getLevelCode();
                        if (promotionRedisDB.getLlen(lotteryKey) <= 0) {
                            continue;
                        }
                        targetAccuList.add(checkAccuDTO);
                        totalPercent += Integer.parseInt(checkAccuDTO.getLevelAmount());
                    }
                    if (targetAccuList.size() == 1) {
                        goalAccuDTO = targetAccuList.get(0);
                    } else {
                        for (int i = 0; i < targetAccuList.size(); i ++) {
                            tmpAccuDTO = targetAccuList.get(i);
                            if (i == targetAccuList.size() - 1) {
                                tmpAccuDTO.setLevelAmount("100");
                                break;
                            }
                            if (lastAccuDTO == null) {
                                currentPercent = 0;
                            } else {
                                currentPercent = Integer.parseInt(lastAccuDTO.getLevelAmount());
                            }
                            currentPercent += Integer.parseInt(tmpAccuDTO.getLevelAmount()) / totalPercent;
                            tmpAccuDTO.setLevelAmount(String.valueOf(currentPercent));
                        }
                        luckNo = noGenerator.getRandomNum();
                        logger.info("------------------luckNo:" + luckNo);
                        for (PromotionAccumulatyDTO accuDTO : targetAccuList) {
                            if (luckNo <= Integer.parseInt(accuDTO.getLevelAmount())) {
                                goalAccuDTO = accuDTO;
                                break;
                            }
                        }
                    }
                    lotteryKey = RedisConst.REDIS_LOTTERY_AWARD_PREFIX + promotionId + "_" + goalAccuDTO.getLevelCode();
                    awardJsonStr = promotionRedisDB.headPop(lotteryKey);
                    if (StringUtils.isEmpty(awardJsonStr)) {
                        loopSize++;
                        continue;
                    }
                    winningRecordDTO = JSON.parseObject(awardJsonStr, BuyerWinningRecordDTO.class);
                    break;
                }
                return winningRecordDTO;
            }
        }.start();
    }

    /**
     * 异步初始化抽奖活动Reids数据
     *
     * @param promotionInfoDTO
     */
    @Override
    public void initPromotionLotteryRedisInfoWithThread(final PromotionExtendInfoDTO promotionInfoDTO) {
        new Thread() {
            public void run() {
                initPromotionLotteryRedisInfo(promotionInfoDTO);
            }
        }.start();
    }

    /**
     * 初始化抽奖活动Reids数据
     *
     * @param promotionInfoDTO
     */
    @Override
    public void initPromotionLotteryRedisInfo(final PromotionExtendInfoDTO promotionInfoDTO) {
        StringRedisTemplate stringRedisTemplate = null;
        PromotionInfoDMO promotionInfoDMO = new PromotionInfoDMO();
        promotionInfoDMO.setPromotionId(promotionInfoDTO.getPromotionId());
        try {
            promotionInfoDTO.setPromotionStatusHistoryList(null);
            promotionInfoDTO.setPromotionDetailDescribeDTO(null);
            stringRedisTemplate = promotionRedisDB.getStringRedisTemplate();
            stringRedisTemplate.executePipelined(new RedisCallback<List<Object>>() {
                @Override
                public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                    String promotionId = promotionInfoDTO.getPromotionId();
                    List<PromotionAwardInfoDTO> promotionAwardInfoDTOList =
                            (List<PromotionAwardInfoDTO>) promotionInfoDTO.getPromotionAccumulatyList();
                    BuyerWinningRecordDTO buyerWinningRecordDTO = null;
                    PromotionAccumulatyDTO accuDTO = null;
                    List<PromotionAccumulatyDTO> accuList = new ArrayList<PromotionAccumulatyDTO>();
                    Map<String, String> timesInfoMap = new HashMap<String, String>();
                    long totalAwardCnt = 0L;
                    String levelCode = "";
                    String redisKey = "";
                    long pushCnt = 0L;
                    long diffTime = promotionInfoDTO.getInvalidTime().getTime() - new Date().getTime();
                    int seconds = (int) (diffTime / 1000);
                    try {

                        timesInfoMap.put(RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES,
                                String.valueOf(promotionInfoDTO.getDailyBuyerPartakeTimes()));
                        timesInfoMap.put(RedisConst.REDIS_LOTTERY_BUYER_DAILY_WINNING_TIMES,
                                String.valueOf(promotionInfoDTO.getDailyBuyerWinningTimes()));
                        timesInfoMap.put(RedisConst.REDIS_LOTTERY_SELLER_DAILY_TOTAL_TIMES,
                                String.valueOf(promotionInfoDTO.getDailyWinningTimes()));
                        if (YesNoEnum.YES.getValue() == promotionInfoDTO.getIsShareTimesLimit().intValue()) {
                            timesInfoMap.put(RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES,
                                    String.valueOf(promotionInfoDTO.getShareExtraPartakeTimes()));
                            if (promotionInfoDTO.getTopExtraPartakeTimes() != null) {
                                timesInfoMap.put(RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES,
                                        String.valueOf(promotionInfoDTO.getTopExtraPartakeTimes()));
                            }
                        }

                        stringRedisConnection.openPipeline();
                        for (PromotionAwardInfoDTO awardInfoDTO : promotionAwardInfoDTOList) {
                            accuDTO = new PromotionAccumulatyDTO();
                            accuDTO.setPromotionAccumulaty(awardInfoDTO);
                            accuList.add(accuDTO);
                            levelCode = awardInfoDTO.getLevelCode();
                            redisKey = RedisConst.REDIS_LOTTERY_AWARD_PREFIX + promotionId + "_" + levelCode;
                            pushCnt = awardInfoDTO.getProvideCount().longValue() - promotionRedisDB.getLlen(redisKey)
                                    .longValue();
                            totalAwardCnt += awardInfoDTO.getProvideCount().longValue();
                            if (pushCnt > 0) {
                                buyerWinningRecordDTO = new BuyerWinningRecordDTO();
                                buyerWinningRecordDTO.setBuyerWinningRecordByAwardInfo(awardInfoDTO);
                                buyerWinningRecordDTO.setBuyerWinningRecordByPromoitonInfo(promotionInfoDTO);
                                while (pushCnt > 0) {
                                    stringRedisConnection.rPush(redisKey, JSON.toJSONString(buyerWinningRecordDTO));
                                    pushCnt--;
                                }
                            } else if (pushCnt < 0) {
                                while (pushCnt < 0) {
                                    stringRedisConnection.lPop(redisKey);
                                    pushCnt++;
                                }
                            }
                            stringRedisConnection.expire(redisKey, seconds);
                        }
                        timesInfoMap
                                .put(RedisConst.REDIS_LOTTERY_AWARD_WINNING_PERCENTAGE, JSON.toJSONString(accuList));
                        timesInfoMap.put(RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT, String.valueOf(totalAwardCnt));
                        stringRedisConnection
                                .hMSet(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId, timesInfoMap);
                        stringRedisConnection.expire(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId, seconds);
                        stringRedisConnection
                                .hSet(RedisConst.REDIS_LOTTERY_INFO, promotionId, JSON.toJSONString(promotionInfoDTO));
                        stringRedisConnection
                                .hSet(RedisConst.REDIS_LOTTERY_VALID, promotionId, promotionInfoDTO.getShowStatus());
                        stringRedisConnection.hSet(RedisConst.REDIS_LOTTERY_INDEX,
                                RedisConst.REDIS_GASHAPON_PREFIX + promotionInfoDTO.getEffectiveTime().getTime() + "_"
                                        + promotionInfoDTO.getInvalidTime().getTime(), promotionId);
                        stringRedisConnection.closePipeline();
                    } catch (Exception e) {
                        logger.info(
                                "初始化抽奖活动Redis数据PromotionLotteryCommonServiceImpl-initPromotionLotteryRedisInfo:入参:{},异常:{}",
                                JSON.toJSONString(promotionInfoDTO), ExceptionUtils.getFullStackTrace(e));
                        throw e;
                    }
                    return null;
                }
            });
            promotionInfoDMO.setDealFlag(YesNoEnum.NO.getValue());
        } catch (Exception e) {
            promotionInfoDMO.setDealFlag(YesNoEnum.YES.getValue());
            throw e;
        } finally {
            promotionInfoDAO.updatePromotionDealFlag(promotionInfoDMO);
        }
    }
}

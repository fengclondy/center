package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryResultReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryWinningReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.GenricResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("promotionLotteryService")
public class PromotionLotteryServiceImpl implements PromotionLotteryService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionLotteryServiceImpl.class);

    @Value("promotion.lottery.max.loop.size")
    private String lotteryMaxLoopSize;

    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private PromotionLotteryCommonService promotionLotteryCommonService;

    /**
     * 开始抽奖处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public DrawLotteryResDTO beginDrawLottery(DrawLotteryReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = "";
        Map<String, String> dictMap = null;
        PromotionExtendInfoDTO promotionInfoDTO = null;
        PromotionSellerDetailDTO sellerDTO = null;
        BuyerWinningRecordDTO defaultWinningRecord = new BuyerWinningRecordDTO();

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        dictMap = baseService.initPromotionDictMap();
        promotionInfoDTO = promotionLotteryCommonService.getRedisLotteryInfo(promotionId, dictMap);
        if (promotionLotteryCommonService.checkPromotionLotteryValid(promotionInfoDTO, requestDTO, dictMap)) {
            sellerDTO = baseService.getPromotionSellerInfo(promotionInfoDTO, sellerCode, dictMap);
            defaultWinningRecord.setBuyerWinningRecordByPromoitonInfo(promotionInfoDTO);
            defaultWinningRecord.setBuyerCode(buyerCode);
            defaultWinningRecord.setSellerCode(sellerDTO.getSellerCode());
            defaultWinningRecord.setSellerName(sellerDTO.getSellerName());
            defaultWinningRecord.setBelongSuperiorName(sellerDTO.getBelongSuperiorName());
            defaultWinningRecord.setRewardType(dictMap.get(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE + "&"
                    + DictionaryConst.OPT_PROMOTION_REWARD_TYPE_THANKS));
            ticket = noGenerator.generateLotteryTicket(promotionId + sellerCode + buyerCode);
            responseDTO.setTicket(ticket);
            new Thread(new DoPromotionLotteryDealTask(requestDTO, defaultWinningRecord, ticket)).start();
        }
        return responseDTO;
    }

    /**
     * 查询抽奖结果处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public BuyerWinningRecordDTO getDrawLotteryResult(DrawLotteryResultReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = requestDTO.getTicket();
        String recordJsonStr = "";
        BuyerWinningRecordDTO responseDTO = new BuyerWinningRecordDTO();

        responseDTO.setResponseCode(ResultCodeEnum.LOTTERY_NO_RESULT.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.LOTTERY_NO_RESULT.getMsg());
        if (promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket)) {
            recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                    promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket);
            responseDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
            if (responseDTO == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(),
                        "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 领奖编号:" + ticket
                                + " 抽奖结果异常 " + recordJsonStr);
            }
            responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
            responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        }
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
    }

    /**
     * 保存中奖结果信息
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public GenricResDTO saveDrawLotteryWinning(DrawLotteryWinningReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = requestDTO.getTicket();
        String recordJsonStr = "";
        GenricResDTO responseDTO = new GenricResDTO();
        BuyerWinningRecordDTO winningRecordDTO = null;

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        if (!promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_WINNING_RECORD.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 领奖编号:" + ticket
                            + " 粉丝没有中奖记录");
        }
        recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket);
        winningRecordDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
        if (winningRecordDTO == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 领奖编号:" + ticket
                            + " 抽奖结果异常 " + recordJsonStr);
        }
        winningRecordDTO.setBuyerName(requestDTO.getBuyerName());
        winningRecordDTO.setBuyerTelephone(requestDTO.getBuyerTelephone());
        winningRecordDTO.setSellerName(requestDTO.getSellerName());
        winningRecordDTO.setSellerAddress(requestDTO.getSellerAddress());
        winningRecordDTO.setWinnerName(requestDTO.getWinnerName());
        winningRecordDTO.setWinningContact(requestDTO.getWinningContact());
        winningRecordDTO.setChargeTelephone(requestDTO.getChargeTelephone());
        winningRecordDTO.setCreateId(0L);
        winningRecordDTO.setCreateName(requestDTO.getBuyerName());
        promotionRedisDB.tailPush(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST, JSON.toJSONString(winningRecordDTO));
        promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket);
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
    }

    /**
     * 抽奖处理异步线程
     */
    private class DoPromotionLotteryDealTask extends Thread {

        private BuyerWinningRecordDTO defaultWinningRecordDTO;

        private String promotionId;

        private String buyerCode;

        private String sellerCode;

        private String ticket;

        public DoPromotionLotteryDealTask(DrawLotteryReqDTO reqDTO, BuyerWinningRecordDTO defaultWinningRecordDTO,
                String ticket) {
            this.promotionId = reqDTO.getPromotionId();
            this.buyerCode = reqDTO.getBuyerCode();
            this.sellerCode = reqDTO.getSellerCode();
            this.defaultWinningRecordDTO = defaultWinningRecordDTO;
            this.ticket = ticket;
        }

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
                if (accuList == null || !accuList.isEmpty()) {
                    throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                            "抽奖活动编号:" + promotionId + " 奖项设置异常没有设置奖项");
                } else if ("100".equals(accuList.get(accuList.size() - 1).getLevelAmount())) {
                    throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                            "抽奖活动编号:" + promotionId + " 奖项设置异常没有合适奖项 " + JSON.toJSONString(accuList));
                }
                winningRecordDTO = drawLotteryAward(accuList);
                if (winningRecordDTO == null) {
                    throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                            "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
                }
                winningRecordDTO.setBuyerCode(buyerCode);
                winningRecordDTO.setSellerCode(sellerCode);
                winningRecordDTO.setSellerName(defaultWinningRecordDTO.getSellerName());
                winningRecordDTO.setBelongSuperiorName(defaultWinningRecordDTO.getBelongSuperiorName());
                winningRecordDTO.setWinningTime(new Date());
            } catch (PromotionCenterBusinessException pcbe) {
                if (winningRecordDTO == null) {
                    winningRecordDTO = defaultWinningRecordDTO;
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
            int maxLoopSize =
                    StringUtils.isEmpty(lotteryMaxLoopSize) ? accuList.size() : Integer.parseInt(lotteryMaxLoopSize);
            int loopSize = 0;

            if (promotionRedisDB.decrHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
                    RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT).longValue() < 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                        "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
            }
            if (promotionRedisDB
                    .decrHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                            RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES).longValue() < 0) {
                promotionRedisDB
                        .incrHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                                RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES);
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_MORE_DRAW_CHANCE.getCode(),
                        "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 粉丝已经用完了所有抽奖机会");
            }
            if (promotionRedisDB
                    .decrHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                            RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES).longValue() < 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_REACH_WINNING_LIMMIT.getCode(),
                        "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 粉丝已达中奖次数上限");

            }
            if (promotionRedisDB
                    .decr(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_" + sellerCode)
                    .longValue() < 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_SELLER_REACH_WINNING_LIMMIT.getCode(),
                        "抽奖活动编号:" + promotionId + " 会员店:" + sellerCode + " 抽奖粉丝编号:" + buyerCode + " 会员店已达中奖次数上限");
            }
            while (loopSize++ < maxLoopSize) {
                if (accuList.size() == 1) {
                    goalAccuDTO = accuList.get(0);
                } else {
                    luckNo = noGenerator.getRandomNum();
                    for (PromotionAccumulatyDTO accuDTO : accuList) {
                        if (luckNo <= Integer.parseInt(accuDTO.getLevelAmount())) {
                            goalAccuDTO = accuDTO;
                            break;
                        }
                    }
                }
                lotteryKey = RedisConst.REDIS_LOTTERY_AWARD_PREFIX + promotionId + "_" + goalAccuDTO.getLevelCode();
                awardJsonStr = promotionRedisDB.headPop(lotteryKey);
                if (StringUtils.isEmpty(awardJsonStr)) {
                    continue;
                }
                winningRecordDTO = JSON.parseObject(awardJsonStr, BuyerWinningRecordDTO.class);
                break;
            }
            return winningRecordDTO;
        }
    }
}

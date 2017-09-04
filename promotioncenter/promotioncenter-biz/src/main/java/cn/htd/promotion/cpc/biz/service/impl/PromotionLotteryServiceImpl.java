package cn.htd.promotion.cpc.biz.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionLotteryService")
public class PromotionLotteryServiceImpl implements PromotionLotteryService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionLotteryServiceImpl.class);

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
            promotionLotteryCommonService.doDrawLotteryWithThread(requestDTO, defaultWinningRecord, ticket);
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
        long partakeTimes = 0L;

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
            partakeTimes = Long.valueOf(promotionRedisDB
                    .getHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                            RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES));
            responseDTO.setRemainLotteryChance(partakeTimes < 0 ? 0 : partakeTimes);
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
        Map<String, String> dictMap = new HashMap<String, String>();

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
        baseService.initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
        if (dictMap.get(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE + "&"
                + DictionaryConst.OPT_PROMOTION_REWARD_TYPE_PRACTICALITY).equals(winningRecordDTO.getRewardType())) {
            if (StringUtils.isEmpty(requestDTO.getWinnerName())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人姓名不能为空");
            }
            if (StringUtils.isEmpty(requestDTO.getWinningContact())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人联系方式不能为空");
            }
        } else if (dictMap.get(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE + "&"
                + DictionaryConst.OPT_PROMOTION_REWARD_TYPE_TEL_RECHARGE).equals(winningRecordDTO.getRewardType())) {
            if (StringUtils.isEmpty(requestDTO.getWinnerName())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人姓名不能为空");
            }
            if (StringUtils.isEmpty(requestDTO.getChargeTelephone())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "充值手机号码不能为空");
            }
            if (!Pattern.matches("^1[34578]\\d{9}$", requestDTO.getChargeTelephone())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "充值手机号码不正确");
            }
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
        promotionRedisDB
                .tailPush(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST, JSON.toJSONString(winningRecordDTO));
        promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + requestDTO.getBuyerCode() + "_" + ticket);
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
    }
}

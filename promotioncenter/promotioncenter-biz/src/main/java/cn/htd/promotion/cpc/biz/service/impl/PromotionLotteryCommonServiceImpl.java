package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.BuyerCheckInfo;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PromotionLotteryCommonServiceImpl implements PromotionLotteryCommonService {

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private PromotionBaseService baseService;

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
     * 取得扭蛋促销活动信息
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
        if (nowDt.before(DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachStartTime())) || nowDt
                .after(DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachEndTime()))) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NOT_IN_TIME_INTERVAL.getCode(),
                    "抽奖活动编号:" + promotionId + " 时间段:" + DateUtil.format(promotionInfoDTO.getEachStartTime()) + "~"
                            + DateUtil.format(promotionInfoDTO.getEachEndTime()) + " 该活动当前不在抽奖时间段");
        }
        lotteryTimesInfoMap =
                promotionRedisDB.getHashOperations(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId);
        if (Integer.parseInt(lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT)) < 0) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                    "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
        }
        buyerCheckInfo.setBuyerCode(requestDTO.getBuyerCode());
        buyerCheckInfo.setIsFirstLogin(requestDTO.getIsBuyerFirstLogin());
        if (!baseService.checkPromotionBuyerRule(promotionInfoDTO, buyerCheckInfo, dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_AUTHIORITY.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode() + " 抽奖粉丝编号:" + requestDTO
                            .getBuyerCode() + " 是否首次登陆:" + requestDTO.getIsBuyerFirstLogin() + " 该活动粉丝没有秒杀权限");
        }
        if (Integer.parseInt(lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_AWARD_TOTAL_COUNT)) <= 0) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NO_MORE_AWARD_NUM.getCode(),
                    "抽奖活动编号:" + promotionId + " 抽奖活动目前奖品数量不足");
        }
        if (!baseService.checkPromotionSellerRule(promotionInfoDTO, requestDTO.getSellerCode(), dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode() + " 抽奖粉丝编号:" + requestDTO
                            .getBuyerCode() + " 是否首次登陆:" + requestDTO.getIsBuyerFirstLogin() + " 会员店没有参加本次抽奖活动");
        }
        buyerTimesInfoMap = promotionRedisDB
                .getHashOperations(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode);
        if (buyerTimesInfoMap != null) {
            if (Integer.parseInt(buyerTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES)) <= 0) {
                if (!lotteryTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES) ||
                        Integer.parseInt(
                                lotteryTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES)) <= 0) {
                    throw new PromotionCenterBusinessException(
                            ResultCodeEnum.LOTTERY_BUYER_NO_MORE_DRAW_CHANCE.getCode(),
                            "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode() + " 抽奖粉丝编号:" + requestDTO
                                    .getBuyerCode() + " 粉丝已经用完了所有抽奖机会，需分享获得额外抽奖机");
                } else if (!buyerTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES) ||
                        Integer.parseInt(
                                buyerTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES)) <= 0
                        || (buyerTimesInfoMap.containsKey(RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES)
                        && YesNoEnum.YES.getValue() == Integer
                        .parseInt(buyerTimesInfoMap.get(RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES)))) {
                    throw new PromotionCenterBusinessException(
                            ResultCodeEnum.LOTTERY_BUYER_NO_MORE_EXTRA_CHANCE.getCode(),
                            "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode() + " 抽奖粉丝编号:" + requestDTO
                                    .getBuyerCode() + " 粉丝已经用完了自有和分享额外获取的抽奖机");
                }
            }
        }
        return true;
    }
}

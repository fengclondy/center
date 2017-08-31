package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.biz.handle.PromotionLotteryRedisHandle;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.BuyerCheckInfo;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionLotteryService")
public class PromotionLotteryServiceImpl implements PromotionLotteryService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionLotteryServiceImpl.class);

    @Resource
    private GeneratorUtils keyGenerator;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionLotteryRedisHandle lotteryRedisHandle;

    @Resource
    private PromotionRedisDB promotionRedisDB;

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
        String redisKey = "";
        Map<String, String> dictMap = baseService.initPromotionDictMap();

        ticket = keyGenerator.generateLotteryTicket(promotionId + sellerCode+buyerCode);
        redisKey = promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket;

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setTicket(ticket);
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
    public DrawLotteryResDTO getDrawLotteryResult(DrawLotteryReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        return null;
    }

    /**
     * 取得扭蛋促销活动信息
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     */
    private PromotionExtendInfoDTO getPromotionLotteryValid(DrawLotteryReqDTO requestDTO, Map<String, String> dictMap)
            throws PromotionCenterBusinessException {
        String promotionId = requestDTO.getPromotionId();
        PromotionExtendInfoDTO promotionInfoDTO = null;
        Date nowDt = new Date();
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();

        promotionInfoDTO = lotteryRedisHandle.getRedisLotteryInfo(promotionId);
        if (nowDt.before(promotionInfoDTO.getEffectiveTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_START.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动未开始");
        }
        if (nowDt.after(promotionInfoDTO.getInvalidTime())) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_HAS_EXPIRED.getCode(),
                    "抽奖活动编号:" + promotionId + " 该活动已结束");
        }
        if (nowDt.before(DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachStartTime())) || nowDt.after
                (DateUtil.getNowDateSpecifiedTime(promotionInfoDTO.getEachEndTime()))) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_NOT_IN_TIME_INTERVAL.getCode(),
                    "抽奖活动编号:" + promotionId + " 时间段:" + DateUtil.format(promotionInfoDTO.getEachStartTime()) + "~" +
                            DateUtil.format(promotionInfoDTO.getEachEndTime()) + " 该活动当前不在抽奖时间段");
        }

        buyerCheckInfo.setBuyerCode(requestDTO.getBuyerCode());
        buyerCheckInfo.setIsFirstLogin(requestDTO.getIsBuyerFirstLogin());
        if (!baseService.checkPromotionBuyerRule(promotionInfoDTO, buyerCheckInfo, dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_AUTHIORITY.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode()  + " 抽奖粉丝编号:" +
                            requestDTO.getBuyerCode() + " 是否首次登陆:" + requestDTO.getIsBuyerFirstLogin()
                            + " 该活动粉丝没有秒杀权限");
        }
        if (!baseService.checkPromotionSellerRule(promotionInfoDTO, requestDTO.getSellerCode(), dictMap)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getCode(),
                    "抽奖活动编号:" + promotionId + " 会员店:" + requestDTO.getSellerCode()  + " 抽奖粉丝编号:" +
                            requestDTO.getBuyerCode() + " 是否首次登陆:" + requestDTO.getIsBuyerFirstLogin()
                            + " 会员店没有参加本次抽奖活动");
        }

        return promotionInfoDTO;
    }
}

package cn.htd.promotion.cpc.biz.service;

import java.util.Map;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;

public interface PromotionLotteryCommonService {

    /**
     * 取得Redis抽奖活动信息
     *
     * @param promotionId
     * @param dictMap
     * @return
     */
    public PromotionExtendInfoDTO getRedisLotteryInfo(String promotionId, Map<String, String> dictMap);

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
            DrawLotteryReqDTO requestDTO, Map<String, String> dictMap) throws PromotionCenterBusinessException;

    /**
     * 检验抽奖活动是否有效
     *
     * @param promotionInfoDTO
     * @param requestDTO
     * @param dictMap
     * @return
     */
    public boolean checkPromotionLotteryValid(PromotionExtendInfoDTO promotionInfoDTO, DrawLotteryReqDTO requestDTO,
            Map<String, String> dictMap);

    /**
     * 执行抽奖处理
     *
     * @param requestDTO
     * @param errorWinningRecord
     * @param ticket
     */
    public void doDrawLotteryWithThread(DrawLotteryReqDTO requestDTO, BuyerWinningRecordDTO errorWinningRecord,
            String ticket);

    /**
     * 进行抽奖
     *
     * @param requestDTO
     * @param errorWinningRecord
     * @param ticket
     */
    public void doDrawLottery(DrawLotteryReqDTO requestDTO, BuyerWinningRecordDTO errorWinningRecord, String ticket);

    /**
     * 异步初始化抽奖活动的Redis数据
     *
     * @param promotionInfoDTO
     */
    public void initPromotionLotteryRedisInfoWithThread(PromotionExtendInfoDTO promotionInfoDTO);

    /**
     * 初始化抽奖活动的Redis数据
     *
     * @param promotionInfoDTO
     */
    public void initPromotionLotteryRedisInfo(PromotionExtendInfoDTO promotionInfoDTO);
}

package cn.htd.promotion.cpc.biz.service;

import java.util.Map;

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
     * @param defaultWinningRecordDTO
     * @param ticket
     */
    public void doDrawLotteryWithThread(DrawLotteryReqDTO requestDTO, BuyerWinningRecordDTO defaultWinningRecordDTO,
            String ticket);

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

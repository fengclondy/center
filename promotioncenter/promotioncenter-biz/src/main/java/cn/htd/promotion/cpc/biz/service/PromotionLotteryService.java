package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;

public interface PromotionLotteryService {

    /**
     * 开始抽奖处理
     * @param requestDTO
     * @return
     */
    public DrawLotteryResDTO beginDrawLottery(DrawLotteryReqDTO requestDTO);

    /**
     * 查询抽奖结果处理
     * @param requestDTO
     * @return
     */
    public DrawLotteryResDTO getDrawLotteryResult(DrawLotteryReqDTO requestDTO);
}

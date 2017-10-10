package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryResultReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryWinningReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.GenricResDTO;

public interface PromotionLotteryService {

    /**
     * 开始抽奖处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public DrawLotteryResDTO beginDrawLottery(DrawLotteryReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception;

    /**
     * 查询抽奖结果处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public BuyerWinningRecordDTO getDrawLotteryResult(DrawLotteryResultReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception;

    /**
     * 保存中奖结果信息
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public GenricResDTO saveDrawLotteryWinning(DrawLotteryWinningReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception;

    /**
     * 执行抽奖处理
     *
     * @param requestDTO
     * @param ticket
     * @param useThread
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    public DrawLotteryResDTO beginDrawLotteryExecute(DrawLotteryReqDTO requestDTO, String ticket, boolean useThread)
            throws PromotionCenterBusinessException, Exception;
}

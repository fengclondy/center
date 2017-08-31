package cn.htd.promotion.cpc.biz.service.impl;

import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("promotionLotteryService")
public class PromotionLotteryServiceImpl implements PromotionLotteryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionLotteryServiceImpl.class);

    /**
     * 开始抽奖处理
     *
     * @param requestDTO
     * @return
     */
    @Override
    public DrawLotteryResDTO beginDrawLottery(DrawLotteryReqDTO requestDTO) {
        return null;
    }

    /**
     * 查询抽奖结果处理
     *
     * @param requestDTO
     * @return
     */
    @Override
    public DrawLotteryResDTO getDrawLotteryResult(DrawLotteryReqDTO requestDTO) {
        return null;
    }
}

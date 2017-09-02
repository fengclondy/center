package cn.htd.promotion.cpc.biz.convert;

import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;
import org.springframework.beans.BeanUtils;

public class BuyerWinningRecordConvert extends AbstractConvert<BuyerWinningRecordDMO, PromotionAwardDTO> {

    @Override
    protected PromotionAwardDTO populateTarget(BuyerWinningRecordDMO source) {

        PromotionAwardDTO promotionAwardDTO = new PromotionAwardDTO();
        BeanUtils.copyProperties(source, promotionAwardDTO);

        return promotionAwardDTO;
    }

    @Override
    protected BuyerWinningRecordDMO populateSource(PromotionAwardDTO target) {
        return null;
    }

}

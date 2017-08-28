package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;

/**
 * Created by tangjiayong on 2017/8/22.
 */
public interface AwardRecordService {

    public DataGrid<PromotionAwardDTO> getAwardRecordByPromotionId(PromotionAwardReqDTO dto , String messageId);
}

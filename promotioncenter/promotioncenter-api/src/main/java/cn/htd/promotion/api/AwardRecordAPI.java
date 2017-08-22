package cn.htd.promotion.api;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;

import java.util.List;

/**
 * Created by tangjiayong on 2017/8/22.
 */
public interface AwardRecordAPI {

    /**
     * 通过活动编号查询所有中奖纪录
     * @param PromotionAwardReqDTO
     * @return
     */
    public ExecuteResult<DataGrid<PromotionAwardDTO>> getAwardRecordByPromotionId(PromotionAwardReqDTO dto);

}

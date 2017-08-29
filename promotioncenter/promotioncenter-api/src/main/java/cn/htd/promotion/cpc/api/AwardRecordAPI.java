package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;
import cn.htd.promotion.cpc.dto.response.ImportResultDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardDTO;

/**
 * Created by tangjiayong on 2017/8/22.
 */
public interface AwardRecordAPI {

    /**
     * 通过活动编号查询所有中奖纪录
     * @param dto
     * @param messageId
     * @return
     */
    public ExecuteResult<DataGrid<PromotionAwardDTO>> getAwardRecordByPromotionId(PromotionAwardReqDTO dto,String messageId);

    ExecuteResult<ImportResultDTO> importWinningRecord(List<PromotionAwardReqDTO> list,String messageId);

}

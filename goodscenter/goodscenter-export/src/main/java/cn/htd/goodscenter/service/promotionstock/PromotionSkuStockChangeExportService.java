package cn.htd.goodscenter.service.promotionstock;

import java.util.List;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;

public interface PromotionSkuStockChangeExportService {

    /**
     * 批量扣减库存 （活动中心，扣减库存）
     * 注：只针对内部供应商和外部供应商商品
     * @return
     */
    ExecuteResult<String> batchReduceStock(List<PromotionStockChangeDTO> promotionStockChangeDTOs);
    
    /**
     * 释放库存 （活动中心，取消订单）
     * 注：只针对内部供应商和外部供应商商品
     * @return
     */
    ExecuteResult<String> batchReleaseStock(List<PromotionStockChangeDTO> promotionStockChangeDTOs);
}

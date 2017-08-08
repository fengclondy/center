package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.StockNoReduceRecordException;
import org.springframework.stereotype.Service;

/**
 * 可见库存 - 回滚
 * @author chenakng
 */
@Service("skuStockRollbackImpl")
public class SkuStockRollbackImplHandler extends AbstractSkuStockChangeHandler {

    @Override
    protected void doChange(Order4StockEntryDTO order4StockEntryDTO, Long stockId) throws Exception {
        String orderNo = order4StockEntryDTO.getOrderNo();
        String resource = order4StockEntryDTO.getOrderResource();
        Integer quantity = order4StockEntryDTO.getQuantity(); // 商品数量
        String messageId = order4StockEntryDTO.getMessageId(); // 消息ID
        // 幂等查询
        if(idempotentHandle(orderNo, stockId, StockTypeEnum.ROLLBACK)) {
            return;
        }
        // ADD-START 校验该订单的商品库存有没有扣减过
        if (!validateStockChangePreCondition(orderNo, stockId, StockTypeEnum.REDUCE, quantity)) {
            // 如果没有校验通过
            throw new StockNoReduceRecordException("回滚库存错误-前置校验不通过【该订单下该商品没有扣减的记录】, orderNo : "
                    + orderNo + ", stockId : " + stockId);
        }
        // ADD-END
        // 查询实时库存
        ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
        // 可见库存回滚
        itemSkuPublishInfo.setDisplayQuantity(itemSkuPublishInfo.getDisplayQuantity() + quantity);
        // 更新库存信息
        this.updateItemSkuPublishInfo(itemSkuPublishInfo);
        // 添加操作历史记录
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = this.createItemSkuPublishInfoHistory(orderNo, resource, quantity, StockTypeEnum.ROLLBACK.name(), messageId, itemSkuPublishInfo);
        this.addItemSkuPublishInfoHistory(itemSkuPublishInfoHistory);
    }
}

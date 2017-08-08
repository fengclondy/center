package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.StockNoReserveRecordException;
import cn.htd.goodscenter.service.impl.stock.exception.StockNotEnoughDisplayQuantityException;
import cn.htd.goodscenter.service.impl.stock.exception.StockNotEnoughReserveQuantityException;
import org.springframework.stereotype.Service;

/**
 * 可见库存 - 扣减
 * 支付完成
 * @author chenakng
 */
@Service("skuStockReduceImpl")
public class SkuStockReduceImplHandler extends AbstractSkuStockChangeHandler {

    @Override
    protected void doChange(Order4StockEntryDTO order4StockEntryDTO, Long stockId, boolean isSpecialOrder) throws Exception {
        String orderNo = order4StockEntryDTO.getOrderNo();
        String resource = order4StockEntryDTO.getOrderResource();
        Integer quantity = order4StockEntryDTO.getQuantity(); // 商品数量
        String messageId = order4StockEntryDTO.getMessageId(); // 消息ID
        // 幂等查询
        if (idempotentHandle(orderNo, stockId, StockTypeEnum.REDUCE, messageId, isSpecialOrder)) {
            return;
        }
        // ADD-START 校验该订单的商品库存有没有锁定过
        if (!validateStockChangePreCondition(orderNo, stockId, isSpecialOrder, StockTypeEnum.RESERVE)) {
            // 如果没有校验通过
            throw new StockNoReserveRecordException("扣减库存错误-前置校验不通过【该订单下该商品没有锁定的记录】, orderNo : "
                    + orderNo + ", stockId : " + stockId + ",isSpecialOrder : " + isSpecialOrder);
        }
        // ADD-END
        // 查询实时库存
        ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
        if (itemSkuPublishInfo.getReserveQuantity() - quantity < 0) {
            throw new StockNotEnoughReserveQuantityException("扣减库存失败-锁定库存不足以扣减, 详细 :"
                    + formatExceptionMessage(itemSkuPublishInfo, order4StockEntryDTO));
        }
        if (itemSkuPublishInfo.getDisplayQuantity() - quantity < 0) {
            throw new StockNotEnoughDisplayQuantityException("扣减库存失败-可见库存不足以扣减, 详细 :"
                    + formatExceptionMessage(itemSkuPublishInfo, order4StockEntryDTO));
        }
        itemSkuPublishInfo.setDisplayQuantity(itemSkuPublishInfo.getDisplayQuantity() - quantity);
        itemSkuPublishInfo.setReserveQuantity(itemSkuPublishInfo.getReserveQuantity() - quantity);
        // 更新库存信息
        this.updateItemSkuPublishInfo(itemSkuPublishInfo);
        // 添加操作历史记录
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = this.createItemSkuPublishInfoHistory(orderNo, resource, quantity, StockTypeEnum.REDUCE.name(), messageId, itemSkuPublishInfo);
        this.addItemSkuPublishInfoHistory(itemSkuPublishInfoHistory);
    }
}

package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.StockNoReserveRecordException;
import cn.htd.goodscenter.service.impl.stock.exception.StockNotEnoughReserveQuantityException;
import org.springframework.stereotype.Service;

/**
 * 可见库存 - 释放库存
 * 取消订单场景，释放库存
 * @author chenakng
 */
@Service("skuStockReleaseImpl")
public class SkuStockReleaseImplHandler extends AbstractSkuStockChangeHandler {

    @Override
    protected void doChange(Order4StockEntryDTO order4StockEntryDTO, Long stockId) throws Exception {
        String orderNo = order4StockEntryDTO.getOrderNo();
        String resource = order4StockEntryDTO.getOrderResource();
        Integer quantity = order4StockEntryDTO.getQuantity(); // 商品数量
        String messageId = order4StockEntryDTO.getMessageId(); // 消息ID
        // 幂等查询
        if (idempotentHandle(orderNo, stockId, StockTypeEnum.RELEASE)) {
            return;
        }
        // ADD-START 校验该订单的商品库存有没有锁定过
        if (!validateStockChangePreCondition(orderNo, stockId, StockTypeEnum.RESERVE, quantity)) {
            // 如果没有校验通过
            throw new StockNoReserveRecordException("释放库存错误-前置校验不通过【该订单下该商品没有锁定的记录或者数量前后不对】, orderNo : "
                    + orderNo + ", stockId : " + stockId);
        }
        // ADD-END
        // 查询实时库存
        ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
        // 释放锁定的库存数量
        if (itemSkuPublishInfo.getReserveQuantity() - quantity < 0) {
            // 锁定库存的数量小于待释放的库存
            throw new StockNotEnoughReserveQuantityException("释放库存错误-锁定库存数不足"
                    + formatExceptionMessage(itemSkuPublishInfo, order4StockEntryDTO));
        }
        itemSkuPublishInfo.setReserveQuantity(itemSkuPublishInfo.getReserveQuantity() - quantity);
        // 更新库存信息
        this.updateItemSkuPublishInfo(itemSkuPublishInfo);
        // 添加操作历史记录
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = this.createItemSkuPublishInfoHistory(orderNo, resource, quantity, StockTypeEnum.RELEASE.name(), messageId, itemSkuPublishInfo);
        this.addItemSkuPublishInfoHistory(itemSkuPublishInfoHistory);
    }
}

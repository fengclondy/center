package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.StockNotEnoughAvailableStockException;
import org.springframework.stereotype.Service;

/**
 * 可见库存 - 锁定
 * 下单锁定可见库存
 * @author chenakng
 */
@Service("skuStockReserveImpl")
public class SkuStockReserveImplHandler extends AbstractSkuStockChangeHandler {
    /**
     * 锁定库存
     * @param order4StockEntryDTO
     * @param stockId
     * @return
     */
    @Override
    protected void doChange(Order4StockEntryDTO order4StockEntryDTO, Long stockId) throws Exception {
        String orderNo = order4StockEntryDTO.getOrderNo();
        String resource = order4StockEntryDTO.getOrderResource();
        Integer quantity = order4StockEntryDTO.getQuantity(); // 商品数量
        String messageId = order4StockEntryDTO.getMessageId(); // 消息ID
        // 幂等查询
        if(idempotentHandle(orderNo, stockId, StockTypeEnum.RESERVE)) {
            return;
        }
        // 查询实时库存
        ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
        if (itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity() < quantity) {
            // 可卖库存小于下单商品数量
            throw new StockNotEnoughAvailableStockException("锁定库存错误-可卖库存不足, 详细 : "
                    + formatExceptionMessage(itemSkuPublishInfo, order4StockEntryDTO));
        }
        // 增加该sku的锁定库存数量
        itemSkuPublishInfo.setReserveQuantity(itemSkuPublishInfo.getReserveQuantity() + quantity);
        // 更新库存信息
        this.updateItemSkuPublishInfo(itemSkuPublishInfo);
        // 添加操作历史记录
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = this.createItemSkuPublishInfoHistory(orderNo, resource, quantity, StockTypeEnum.RESERVE.name(), messageId, itemSkuPublishInfo);
        this.addItemSkuPublishInfoHistory(itemSkuPublishInfoHistory);
    }

}

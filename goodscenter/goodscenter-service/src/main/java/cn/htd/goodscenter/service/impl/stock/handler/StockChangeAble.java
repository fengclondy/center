package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;

/**
 * 库存变动接口
 * @author chenakng
 * @date 2016/11/23.
 */
public interface StockChangeAble {
    /**
     * 改变库存 - 订单（包含订单行）
     * @param order4StockChangeDTO
     */
    void changeStock(Order4StockChangeDTO order4StockChangeDTO) throws Exception;

    /**
     * 改变库存 - 订单行
     * @param order4StockEntryDTO 订单行
     */
    void changeEntryStock(Order4StockEntryDTO order4StockEntryDTO) throws Exception;
}

package cn.htd.goodscenter.service.stock;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;

import java.util.List;

/**
 * sku库存变动接口
 * 注：只针对内部供应商和外部供应商商品
 * @author chenakng
 * @date 2016-11-23
 */
public interface SkuStockChangeExportService {
    /**
     * 扣减库存 （支付完成，扣减库存）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> reduceStock(Order4StockChangeDTO order4StockChangeDTO);

    /**
     * 释放库存 （未支付，取消订单）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> releaseStock(Order4StockChangeDTO order4StockChangeDTO);

    /**
     * 锁定库存 （创建订单，锁定库存）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> reserveStock(Order4StockChangeDTO order4StockChangeDTO);

    /**
     * 回滚库存  （支付完成，取消订单）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> rollbackStock(Order4StockChangeDTO order4StockChangeDTO);

    /**
     * 批量扣减库存 （支付完成，扣减库存）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> batchReduceStock(List<Order4StockChangeDTO> order4StockChangeDTOs);

    /**
     * 释放库存 （未支付，取消订单）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> batchReleaseStock(List<Order4StockChangeDTO> order4StockChangeDTOs);

    /**
     * 锁定库存 （创建订单，锁定库存）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> batchReserveStock(List<Order4StockChangeDTO> order4StockChangeDTOs);

    /**
     * 回滚库存  （支付完成，取消订单）
     * 注：只针对内部供应商和外部供应商商品
     * 【orderNo + skuCode + isBoxFlag + type  幂等唯一】
     * @return
     */
    ExecuteResult<String> batchRollbackStock(List<Order4StockChangeDTO> order4StockChangeDTOs);

    /**
     * 议价库存数量接口
     * 商品数量传入每次议价后的数量
     * 议价流程，先把原来的锁定全部释放，然后重新锁定此次议价的数量
     * 比如：原来锁定了10个，现在议价成5个，则传入5个；
     * @param order4StockChangeDTO
     * @return
     */
    ExecuteResult<String> changePriceStock(Order4StockChangeDTO order4StockChangeDTO);

//    /**
//     * 组合操作库存 - (一个订单有N种库存操作方式) 注 ：提供给ＶＭＳ拆单  议价专用
//     * 注：只针对内部供应商和外部供应商商品
//     * 【messageId + orderNo + skuCode + isBoxFlag + type  幂等唯一】
//     * @return
//     */
//    ExecuteResult<String> comboChangeStock(Order4StockChangeDTO order4StockChangeDTO);

}

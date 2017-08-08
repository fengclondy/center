package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 可卖库存不足异常【display_quantity - reserve_quantity】
 * 可见库存 - 锁定库存 = 可卖库存
 */
public class StockNotEnoughAvailableStockException extends Exception {

    private String message;

    StockNotEnoughAvailableStockException() {

    }

    public StockNotEnoughAvailableStockException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

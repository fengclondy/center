package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 锁定库存数不足【reserve_quantity】
 */
public class StockNotEnoughReserveQuantityException extends Exception {

    private String message;

    StockNotEnoughReserveQuantityException() {

    }

    public StockNotEnoughReserveQuantityException(String message) {
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

package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 释放或者扣减前校验：没有锁定库存记录异常
 */
public class StockNoReserveRecordException extends Exception {

    private String message;

    StockNoReserveRecordException() {

    }

    public StockNoReserveRecordException(String message) {
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

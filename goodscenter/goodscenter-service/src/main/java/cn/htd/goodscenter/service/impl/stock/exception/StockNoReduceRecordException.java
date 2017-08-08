package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 回滚前校验：没有扣减库存记录异常
 */
public class StockNoReduceRecordException extends Exception {

    private String message;

    StockNoReduceRecordException() {

    }

    public StockNoReduceRecordException(String message) {
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

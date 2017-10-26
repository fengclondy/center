package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 库存入参校验异常
 */
public class StockSystemBusyException extends Exception {

    private String message;

    StockSystemBusyException() {

    }

    public StockSystemBusyException(String message) {
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

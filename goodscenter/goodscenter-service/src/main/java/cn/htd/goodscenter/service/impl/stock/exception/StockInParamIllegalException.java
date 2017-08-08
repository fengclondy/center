package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 库存入参校验异常
 */
public class StockInParamIllegalException extends Exception {

    private String message;

    StockInParamIllegalException() {

    }

    public StockInParamIllegalException(String message) {
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

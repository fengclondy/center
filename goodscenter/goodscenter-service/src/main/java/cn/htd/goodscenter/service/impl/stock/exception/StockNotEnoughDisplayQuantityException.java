package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 可见存数不足【display_quantity】
 */
public class StockNotEnoughDisplayQuantityException extends Exception {

    private String message;

    StockNotEnoughDisplayQuantityException() {

    }

    public StockNotEnoughDisplayQuantityException(String message) {
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

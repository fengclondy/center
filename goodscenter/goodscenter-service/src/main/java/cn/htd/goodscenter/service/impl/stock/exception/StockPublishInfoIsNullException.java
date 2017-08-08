package cn.htd.goodscenter.service.impl.stock.exception;

/**
 * 库存信息为空
 */
public class StockPublishInfoIsNullException extends Exception {

    private String message;

    StockPublishInfoIsNullException() {

    }

    public StockPublishInfoIsNullException(String message) {
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

package cn.htd.goodscenter.dto.stock;

import java.io.Serializable;

/**
 * 库存变动DTO -  订单行信息
 * @author chenkang
 * @date 2016-11-23
 */
public class Order4StockEntryDTO implements Serializable {
    private static final long serialVersionUID = 8822053862865554343L;

    private String skuCode; // 商品SKU编码、必传

    private Integer isBoxFlag; // 是否包厢关系 0-大厅 1-包厢；【条件：内部供应商必传】

    /**
     * 订单中该商品的数量
     */
    private Integer quantity;

    /**
     * 库存变动类型
     */
    private StockTypeEnum stockTypeEnum;

    // 冗余字段可不设值
    private String orderNo;
    // 冗余字段可不设值
    private String orderResource;
    // 冗余字段可不设值
    private String messageId;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public StockTypeEnum getStockTypeEnum() {
        return stockTypeEnum;
    }

    public void setStockTypeEnum(StockTypeEnum stockTypeEnum) {
        this.stockTypeEnum = stockTypeEnum;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderResource() {
        return orderResource;
    }

    public void setOrderResource(String orderResource) {
        this.orderResource = orderResource;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Order4StockEntryDTO{" +
                "skuCode='" + skuCode + '\'' +
                ", isBoxFlag=" + isBoxFlag +
                ", quantity=" + quantity +
                ", stockTypeEnum=" + stockTypeEnum +
                ", orderNo='" + orderNo + '\'' +
                ", orderResource='" + orderResource + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }

}

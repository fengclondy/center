package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;

public class QueryOffShelfItemOutDTO implements Serializable {
    // 商品名称
    private String itemName;

    private Long itemId;

    private Long skuId;

    private String itemCode;

    private String skuCode;
    //分销限价
    private BigDecimal saleLimitedPrice;
    //零售价
    private BigDecimal retailPrice;
    //销售价
    private BigDecimal salePrice;
    //是否包厢
    private Integer isBoxFlag;
    //如果上架做少库存，可能存在锁定的
    private int minStock;
    //可上架库存
    private int aviableStock;
    //实际库存
    private int totalStock;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getSaleLimitedPrice() {
        return saleLimitedPrice;
    }

    public void setSaleLimitedPrice(BigDecimal saleLimitedPrice) {
        this.saleLimitedPrice = saleLimitedPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public int getAviableStock() {
        return aviableStock;
    }

    public void setAviableStock(int aviableStock) {
        this.aviableStock = aviableStock;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    /** 用来计算可上架库存 **/
    private Integer itemStatus;
    private Integer currentIsVisable;
    private int currentDisplayQuantity; // 当前可见库存
    private int currentReserveQuantity; // 当前锁定库存
    private Integer otherIsVisable;
    private int otherDisplayQuantity; // 其他可见库存
    private int otherReserveQuantity; // 其他锁定库存

    public int getCurrentDisplayQuantity() {
        return currentDisplayQuantity;
    }

    public void setCurrentDisplayQuantity(int currentDisplayQuantity) {
        this.currentDisplayQuantity = currentDisplayQuantity;
    }

    public int getCurrentReserveQuantity() {
        return currentReserveQuantity;
    }

    public void setCurrentReserveQuantity(int currentReserveQuantity) {
        this.currentReserveQuantity = currentReserveQuantity;
    }

    public int getOtherDisplayQuantity() {
        return otherDisplayQuantity;
    }

    public void setOtherDisplayQuantity(int otherDisplayQuantity) {
        this.otherDisplayQuantity = otherDisplayQuantity;
    }

    public int getOtherReserveQuantity() {
        return otherReserveQuantity;
    }

    public void setOtherReserveQuantity(int otherReserveQuantity) {
        this.otherReserveQuantity = otherReserveQuantity;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getCurrentIsVisable() {
        return currentIsVisable;
    }

    public void setCurrentIsVisable(Integer currentIsVisable) {
        this.currentIsVisable = currentIsVisable;
    }

    public Integer getOtherIsVisable() {
        return otherIsVisable;
    }

    public void setOtherIsVisable(Integer otherIsVisable) {
        this.otherIsVisable = otherIsVisable;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }
}

package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量上架商品DTO
 */
public class BatchOnShelfItemInDTO implements Serializable {

    private Long itemId;

    private Long skuId;

    private String skuCode;

    private String itemCode;

    private String itemName;

    //分销限价
    private BigDecimal saleLimitedPrice;

    //ERP零售价
    private BigDecimal wsaleUtprice;

    //销售价
    private BigDecimal salePrice;

    //零售价
    private BigDecimal retailPrice;

    //上架库存
    private int onShelfQuantiy;

    /** 从QueryOffShelfItemOutDTO透传回来给我 **/
    //如果上架做少库存，可能存在锁定的
    private int minStock;
    //可上架库存
    private int aviableStock;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getSaleLimitedPrice() {
        return saleLimitedPrice;
    }

    public void setSaleLimitedPrice(BigDecimal saleLimitedPrice) {
        this.saleLimitedPrice = saleLimitedPrice;
    }

    public BigDecimal getWsaleUtprice() {
        return wsaleUtprice;
    }

    public void setWsaleUtprice(BigDecimal wsaleUtprice) {
        this.wsaleUtprice = wsaleUtprice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getOnShelfQuantiy() {
        return onShelfQuantiy;
    }

    public void setOnShelfQuantiy(int onShelfQuantiy) {
        this.onShelfQuantiy = onShelfQuantiy;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public int getAviableStock() {
        return aviableStock;
    }

    public void setAviableStock(int aviableStock) {
        this.aviableStock = aviableStock;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
}

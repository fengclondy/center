package cn.htd.goodscenter.dto.vms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量上架商品DTO
 */
public class BatchOnShelfItemInDTO implements Serializable {
    @NotNull(message = "item不能为NULL")
    private Long itemId;
    @NotNull(message = "skuId不能为NULL")
    private Long skuId;
    @NotEmpty(message = "skuCode不能为NULL")
    private String skuCode;
    @NotEmpty(message = "itemCode不能为NULL")
    private String itemCode;
    @NotEmpty(message = "itemName不能为NULL")
    private String itemName;

    //分销限价
    @NotNull(message = "ssaleLimitedPrice不能为空")
    private BigDecimal saleLimitedPrice;

    //ERP零售价
    @NotNull(message = "wsaleUtprice不能为空")
    private BigDecimal wsaleUtprice;

    //销售价
    private BigDecimal salePrice;

    //零售价
    private BigDecimal retailPrice;

    //上架库存
    private Integer onShelfQuantiy;

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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public Integer getOnShelfQuantiy() {
        return onShelfQuantiy;
    }

    public void setOnShelfQuantiy(Integer onShelfQuantiy) {
        this.onShelfQuantiy = onShelfQuantiy;
    }
}

package cn.htd.pricecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**阶梯价格DTO.
 * Created by GZG on 2016/12/14.
 */
public class LadderPriceDto implements Serializable{

    private static final long serialVersionUID = -8288011909682652206L;

    private Long ladderId;

    private Long skuId;

    private String skuCode;

    private String eanCode;

    private String attributes;//销售属性

    private Integer displayQuantity;//显示、可用库存

    private Integer mimQuantity;//起订量

    private Integer maxPurchaseQuantity;//限购量

    private Long itemId;

    private Long sellerId;

    private Long shopId;

    private Long minNum;

    private Long maxNum;

    private BigDecimal price;

    public Long getLadderId() {
        return ladderId;
    }

    public void setLadderId(Long ladderId) {
        this.ladderId = ladderId;
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

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Integer getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(Integer displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public Integer getMimQuantity() {
        return mimQuantity;
    }

    public void setMimQuantity(Integer mimQuantity) {
        this.mimQuantity = mimQuantity;
    }

    public Integer getMaxPurchaseQuantity() {
        return maxPurchaseQuantity;
    }

    public void setMaxPurchaseQuantity(Integer maxPurchaseQuantity) {
        this.maxPurchaseQuantity = maxPurchaseQuantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getMinNum() {
        return minNum;
    }

    public void setMinNum(Long minNum) {
        this.minNum = minNum;
    }

    public Long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

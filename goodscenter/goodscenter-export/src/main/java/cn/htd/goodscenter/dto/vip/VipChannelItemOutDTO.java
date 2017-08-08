package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * vip渠道商品
 */
public class VipChannelItemOutDTO implements Serializable {

    private String skuCode;// 商品sku编码

    private Long skuId;// skuId

    private Long itemId;// 商品id

    private String itemName;// 商品名称

    private String itemCode; // item编码

    private String modelType; // 型号

    private Long sellerId;// 卖家ID

    private Long shopId;// 店铺id

    private String itemPictureUrl;// item主图 列表页展示

    private BigDecimal vipPrice;// vip价格

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

    public String getItemPictureUrl() {
        return itemPictureUrl;
    }

    public void setItemPictureUrl(String itemPictureUrl) {
        this.itemPictureUrl = itemPictureUrl;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    @Override
    public String toString() {
        return "VipItemOutDTO{" +
                "skuCode='" + skuCode + '\'' +
                ", skuId=" + skuId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", modelType='" + modelType + '\'' +
                ", sellerId=" + sellerId +
                ", shopId=" + shopId +
                ", itemPictureUrl='" + itemPictureUrl + '\'' +
                ", vipPrice=" + vipPrice +
                '}';
    }
}

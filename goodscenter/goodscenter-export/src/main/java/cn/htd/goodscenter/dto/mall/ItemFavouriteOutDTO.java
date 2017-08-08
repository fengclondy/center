package cn.htd.goodscenter.dto.mall;

import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品收藏
 */
public class ItemFavouriteOutDTO implements Serializable {

    private Long favouriteId;

    private Long userId;

    /** SKU **/
    private String skuCode; //商品sku编码

    private Long skuId;

    /** ITEM **/
    private Long itemId;// 商品id

    private String itemName;// 商品名称

    private String itemCode;//商品item编码

    private Long sellerId;// 卖家ID

    private Long shopId;// 店铺id

    private String itemPictureUrl;// item主图

    private Integer itemStatus;//商品状态

    private String productChannelCode; // 商品渠道编码　10：内部供应商商品 20：外部供应商商品 30^：外接渠道商品

    private Long thirdCategoryId;

    private Long brandId;

    public Long getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(Long favouriteId) {
        this.favouriteId = favouriteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getProductChannelCode() {
        return productChannelCode;
    }

    public void setProductChannelCode(String productChannelCode) {
        this.productChannelCode = productChannelCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Long getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Long thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}

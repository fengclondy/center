package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 超级老板/商城移动化采购/采购首页
 * @author chenkang
 * @date 2017-06-27
 */
public class MallRecommendItemOutDTO implements Serializable {
    /**
     * 是否直营商品
     */
    private Integer isDirectItem;
    /**
     * itemId
     */
    private Long itemId;
    /**
     * item编码
     */
    private String itemCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 大B id
     */
    private Long sellerId;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 供应商名称
     */
    private String sellerName;
    /**
     * 图片url
     */
    private String pictureUrl;
    /**
     * 销售价
     */
    private BigDecimal salePrice;
    /**
     * 销量
     */
    private Integer salesVolume;
    /**
     * 上架时间
     */
    private Date listtingTime;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getIsDirectItem() {
        return isDirectItem;
    }

    public void setIsDirectItem(Integer isDirectItem) {
        this.isDirectItem = isDirectItem;
    }

    public Date getListtingTime() {
        return listtingTime;
    }

    public void setListtingTime(Date listtingTime) {
        this.listtingTime = listtingTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}

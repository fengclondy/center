package cn.htd.pricecenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemSkuBasePrice implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6187480348724529881L;

	private Long skuId;

    private Long itemId;

    private Long sellerId;

    private Long shopId;

    private String itemCode;

    private Long categoryId;

    private Long brandId;

    private String channelCode;

    private BigDecimal costPrice;

    private BigDecimal saleLimitedPrice;

    private BigDecimal priceFloatingRatio;

    private BigDecimal areaSalePrice;

    private BigDecimal boxSalePrice;

    private BigDecimal retailPriceFloatingRatio;

    private BigDecimal retailPrice;

    private BigDecimal commissionRatio;

    private String vipPriceFloatingRatio;

    private Date lastPriceSyncTime;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSaleLimitedPrice() {
        return saleLimitedPrice;
    }

    public void setSaleLimitedPrice(BigDecimal saleLimitedPrice) {
        this.saleLimitedPrice = saleLimitedPrice;
    }

    public BigDecimal getPriceFloatingRatio() {
        return priceFloatingRatio;
    }

    public void setPriceFloatingRatio(BigDecimal priceFloatingRatio) {
        this.priceFloatingRatio = priceFloatingRatio;
    }

    public BigDecimal getAreaSalePrice() {
        return areaSalePrice;
    }

    public void setAreaSalePrice(BigDecimal areaSalePrice) {
        this.areaSalePrice = areaSalePrice;
    }

    public BigDecimal getBoxSalePrice() {
        return boxSalePrice;
    }

    public void setBoxSalePrice(BigDecimal boxSalePrice) {
        this.boxSalePrice = boxSalePrice;
    }

    public BigDecimal getRetailPriceFloatingRatio() {
        return retailPriceFloatingRatio;
    }

    public void setRetailPriceFloatingRatio(BigDecimal retailPriceFloatingRatio) {
        this.retailPriceFloatingRatio = retailPriceFloatingRatio;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(BigDecimal commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getVipPriceFloatingRatio() {
        return vipPriceFloatingRatio;
    }

    public void setVipPriceFloatingRatio(String vipPriceFloatingRatio) {
        this.vipPriceFloatingRatio = vipPriceFloatingRatio;
    }

    public Date getLastPriceSyncTime() {
        return lastPriceSyncTime;
    }

    public void setLastPriceSyncTime(Date lastPriceSyncTime) {
        this.lastPriceSyncTime = lastPriceSyncTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "ItemSkuBasePrice{" +
                "skuId=" + skuId +
                ", itemId=" + itemId +
                ", sellerId=" + sellerId +
                ", shopId=" + shopId +
                ", itemCode='" + itemCode + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", channelCode='" + channelCode + '\'' +
                ", costPrice=" + costPrice +
                ", saleLimitedPrice=" + saleLimitedPrice +
                ", priceFloatingRatio=" + priceFloatingRatio +
                ", areaSalePrice=" + areaSalePrice +
                ", boxSalePrice=" + boxSalePrice +
                ", retailPriceFloatingRatio=" + retailPriceFloatingRatio +
                ", retailPrice=" + retailPrice +
                ", commissionRatio=" + commissionRatio +
                ", vipPriceFloatingRatio=" + vipPriceFloatingRatio +
                ", lastPriceSyncTime=" + lastPriceSyncTime +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", modifyId=" + modifyId +
                ", modifyName='" + modifyName + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
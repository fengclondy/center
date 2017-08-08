package cn.htd.goodscenter.dto.productplus;

import cn.htd.goodscenter.domain.ItemPicture;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 京东商品查询信息 -- for superBoss
 */
public class JdProductQueryItemDTO implements Serializable {

    private String skuCode; // 商品sku编码 - 中台商品编码

    private String itemCode; // 商品item编码

    private String outSkuId; // 京东skuId

    private String itemName; // 商品名称

    private Long sellerId; // 卖家ID

    private Integer isPreSale; // 是否预售商品  0 否 1 是

    private Date preSaleStartTime; // 预售开始时间

    private Date preSaleEndTime; // 预售结束时间

    private String productChannelCode; // 商品渠道编码　10：内部供应商商品 20：外部供应商商品 30^：外接渠道商品

    private String productChannelName; // 商品渠道名称　10：内部供应商商品 20：外部供应商商品 30^：外接渠道商品

    private Date itemCreateTime; // 商品创建时间

    private Date itemModifyTime; // 商品修改时间

    /** 品牌 **/
    private Long brandId; // 品牌编码

    private String brandName; // 品牌名称

    private String brandKey; // 品牌首字母

    private String brandErpCode; // 品牌ERP编码

    private Date brandCreateTime; // 品牌创建时间

    private Date brandModifyTime; // 品牌修改时间

    /** 品类 **/
    private Long categoryId;// 三级类目编码

    private String categoryName; // 三级类目名称

    private Date categoryCreateTime; // 品类创建时间

    private Date categoryModifyTime; // 品类修改时间

    /** 图片 **/
    List<ItemPicture> itemPictureList;

    /**
     * 上下架状态
     * 1： 上架
     * 0：下架
     */
    private Integer isUpShelf;

    /**
     * 零售价格
     */
    private BigDecimal retailPrice;

    /**
     * 销售价格
     */
    private BigDecimal salePrice;

    private Long itemId;

    private Long skuId;

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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Integer isPreSale) {
        this.isPreSale = isPreSale;
    }

    public Date getPreSaleStartTime() {
        return preSaleStartTime;
    }

    public void setPreSaleStartTime(Date preSaleStartTime) {
        this.preSaleStartTime = preSaleStartTime;
    }

    public Date getPreSaleEndTime() {
        return preSaleEndTime;
    }

    public void setPreSaleEndTime(Date preSaleEndTime) {
        this.preSaleEndTime = preSaleEndTime;
    }

    public String getProductChannelCode() {
        return productChannelCode;
    }

    public void setProductChannelCode(String productChannelCode) {
        this.productChannelCode = productChannelCode;
    }

    public String getProductChannelName() {
        return productChannelName;
    }

    public void setProductChannelName(String productChannelName) {
        this.productChannelName = productChannelName;
    }

    public Date getItemCreateTime() {
        return itemCreateTime;
    }

    public void setItemCreateTime(Date itemCreateTime) {
        this.itemCreateTime = itemCreateTime;
    }

    public Date getItemModifyTime() {
        return itemModifyTime;
    }

    public void setItemModifyTime(Date itemModifyTime) {
        this.itemModifyTime = itemModifyTime;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandKey() {
        return brandKey;
    }

    public void setBrandKey(String brandKey) {
        this.brandKey = brandKey;
    }

    public String getBrandErpCode() {
        return brandErpCode;
    }

    public void setBrandErpCode(String brandErpCode) {
        this.brandErpCode = brandErpCode;
    }

    public Date getBrandCreateTime() {
        return brandCreateTime;
    }

    public void setBrandCreateTime(Date brandCreateTime) {
        this.brandCreateTime = brandCreateTime;
    }

    public Date getBrandModifyTime() {
        return brandModifyTime;
    }

    public void setBrandModifyTime(Date brandModifyTime) {
        this.brandModifyTime = brandModifyTime;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCategoryCreateTime() {
        return categoryCreateTime;
    }

    public void setCategoryCreateTime(Date categoryCreateTime) {
        this.categoryCreateTime = categoryCreateTime;
    }

    public Date getCategoryModifyTime() {
        return categoryModifyTime;
    }

    public void setCategoryModifyTime(Date categoryModifyTime) {
        this.categoryModifyTime = categoryModifyTime;
    }

    public List<ItemPicture> getItemPictureList() {
        return itemPictureList;
    }

    public void setItemPictureList(List<ItemPicture> itemPictureList) {
        this.itemPictureList = itemPictureList;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public Integer getIsUpShelf() {
        return isUpShelf;
    }

    public void setIsUpShelf(Integer isUpShelf) {
        this.isUpShelf = isUpShelf;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}

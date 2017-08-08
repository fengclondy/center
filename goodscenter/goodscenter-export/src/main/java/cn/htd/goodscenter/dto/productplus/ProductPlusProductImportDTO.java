package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 外接渠道商品导入DTO
 *  @author chenkang
 */
public class ProductPlusProductImportDTO implements Serializable {
    /** item **/
    private String itemName;// 商品名称
    private String outerItemStatus;// 外部商品状态 1：未上架，2：已上架
    private String outerChannelCategoryCode;// 外接渠道品类编码
    private String outerChannelBrandCode;// 外接渠道品牌编码
    private String modelType;// 型号
    private String weightUnit;// 商品毛重
    private BigDecimal taxRate;// 税率
    private BigDecimal weight;// 商品毛重
    private BigDecimal netWeight;// 净重
    private BigDecimal length;// 长
    private BigDecimal width;// 宽
    private BigDecimal height;// 高
    private String ad;// 广告词
    private String origin;// 商品产地
    private String itemQualification;// 商品参数
    private String itemPictureUrl;// 商品主图URL
    private String packingList;// 包装清单
    private String afterService;// 售后服务
    private String keywords;// 关键字

    /** sku **/
    private String outerSkuId;// 外接商品编码 skuId

    /** category **/
    private String outerChannelCategoryName; // 外部类目名称

    /** brand **/
    private String outerChannelBrandName; // 外部品牌名称

    /** price **/
    private BigDecimal costPrice; // 外部供货价
    private BigDecimal saleLimitedPrice; // 外部销售价

    /** pic **/
    List<ProductPlusPictureImportDTO> productPlusPictureImportDTOList;

    /** 补充, 注：不需要传 **/
    private Long categoryId;
    private Long brandId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOuterItemStatus() {
        return outerItemStatus;
    }

    public void setOuterItemStatus(String outerItemStatus) {
        this.outerItemStatus = outerItemStatus;
    }

    public String getOuterChannelCategoryCode() {
        return outerChannelCategoryCode;
    }

    public void setOuterChannelCategoryCode(String outerChannelCategoryCode) {
        this.outerChannelCategoryCode = outerChannelCategoryCode;
    }

    public String getOuterChannelCategoryName() {
        return outerChannelCategoryName;
    }

    public void setOuterChannelCategoryName(String outerChannelCategoryName) {
        this.outerChannelCategoryName = outerChannelCategoryName;
    }

    public String getOuterChannelBrandCode() {
        return outerChannelBrandCode;
    }

    public void setOuterChannelBrandCode(String outerChannelBrandCode) {
        this.outerChannelBrandCode = outerChannelBrandCode;
    }

    public String getOuterChannelBrandName() {
        return outerChannelBrandName;
    }

    public void setOuterChannelBrandName(String outerChannelBrandName) {
        this.outerChannelBrandName = outerChannelBrandName;
    }

    public List<ProductPlusPictureImportDTO> getProductPlusPictureImportDTOList() {
        return productPlusPictureImportDTOList;
    }

    public void setProductPlusPictureImportDTOList(List<ProductPlusPictureImportDTO> productPlusPictureImportDTOList) {
        this.productPlusPictureImportDTOList = productPlusPictureImportDTOList;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getItemQualification() {
        return itemQualification;
    }

    public void setItemQualification(String itemQualification) {
        this.itemQualification = itemQualification;
    }

    public String getItemPictureUrl() {
        return itemPictureUrl;
    }

    public void setItemPictureUrl(String itemPictureUrl) {
        this.itemPictureUrl = itemPictureUrl;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
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
}

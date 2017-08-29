package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 推送预售信息DTO
 */
public class PreSaleProductPushDTO implements Serializable {
    /**
     * 版本号
     */
    private Integer version;

    /**
     * 商品品牌信息
     */
    private String brandName;
    /**
     * 品牌code
     */
    private String brandCode;
    /**
     * 商品名称
     */
    private String spxxname;
    /**
     * 商品内码 Erp编码
     */
    private String spxxnmno;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 预售标志
     */
    private Integer isPreSell;
    /**
     * 供应商ID
     */
    private String sellerId;
    /**
     * 供应商code
     */
    private String sellerCode;
    /**
     * 供应商名称
     */
    private String sellerName;
    /**
     * 库存数
     */
    private Integer kcnum;
    /**
     * 一级类目
     */
    private String category1;
    /**
     * 一级类目名称
     */
    private String category1Name;
    /**
     * 二级类目
     */
    private String category2;
    /**
     * 二级类目名称
     */
    private String category2Name;
    /**
     * 三级类目
     */
    private String category3;
    /**
     * 三级类目名称
     */
    private String category3Name;
    /**
     * 建议零售价
     */
    private BigDecimal recommendedPrice;
    /**
     * 普通会员店采购价
     */
    private BigDecimal memberPrice;
    /**
     * vip会员店采购价
     */
    private BigDecimal vipPrice;
    /**
     * 上下架状态
     */
    private Integer listStatus;
    /**
     * 商品详情
     */
    private String describeContent;
    /**
     * 销售区域
     */
    private List<PreSaleProductSaleAreaDTO> region;
    /**
     * 商品属性
     */
    private List<PreSaleProductAttributeDTO> itemAttr;
    /**
     * 商品图片
     */
    private List<PreSaleProductPictureDTO> spjpgs;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getSpxxname() {
        return spxxname;
    }

    public void setSpxxname(String spxxname) {
        this.spxxname = spxxname;
    }

    public String getSpxxnmno() {
        return spxxnmno;
    }

    public void setSpxxnmno(String spxxnmno) {
        this.spxxnmno = spxxnmno;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getIsPreSell() {
        return isPreSell;
    }

    public void setIsPreSell(Integer isPreSell) {
        this.isPreSell = isPreSell;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getKcnum() {
        return kcnum;
    }

    public void setKcnum(Integer kcnum) {
        this.kcnum = kcnum;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory1Name() {
        return category1Name;
    }

    public void setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory2Name() {
        return category2Name;
    }

    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCategory3Name() {
        return category3Name;
    }

    public void setCategory3Name(String category3Name) {
        this.category3Name = category3Name;
    }

    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getListStatus() {
        return listStatus;
    }

    public void setListStatus(Integer listStatus) {
        this.listStatus = listStatus;
    }

    public String getDescribeContent() {
        return describeContent;
    }

    public void setDescribeContent(String describeContent) {
        this.describeContent = describeContent;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<PreSaleProductSaleAreaDTO> getRegion() {
        return region;
    }

    public void setRegion(List<PreSaleProductSaleAreaDTO> region) {
        this.region = region;
    }

    public List<PreSaleProductAttributeDTO> getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(List<PreSaleProductAttributeDTO> itemAttr) {
        this.itemAttr = itemAttr;
    }

    public List<PreSaleProductPictureDTO> getSpjpgs() {
        return spjpgs;
    }

    public void setSpjpgs(List<PreSaleProductPictureDTO> spjpgs) {
        this.spjpgs = spjpgs;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}

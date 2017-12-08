package cn.htd.goodscenter.dto.vms;

import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class VmsItemAddInDTO implements Serializable {
    //三级目录
    @NotNull(message = "商品三级目录不能为空")
    @Digits(integer = 100, fraction = 100, message = "商品三级目录要为数字")
    private Long categoryId;
    //品牌ID
    @NotNull(message = "商品品牌Id不能为空")
    @Digits(integer = 100, fraction = 100, message = "商品三级目录要为数字")
    private Long brandId;
    //型号
    @NotEmpty(message = "型号不能为空")
    private String serial;
    //商品名称
    @NotEmpty(message = "商品名称不能为空")
    private String productName;
    //单位
    @NotEmpty(message = "商品单位不能为空")
    private String unit;
    //税率
    @NotEmpty(message = "商品税率不能为空")
    private String taxRate;
    //毛重量
    @Digits(integer = 11, fraction = 2, message = "毛重量小数位数不正确")
    private String grossWeight;
    //净重量
    @Digits(integer = 11, fraction = 3, message = "净重量小数位数不正确")
    private String netWeight;
    //长
    @Digits(integer = 14, fraction = 2, message = "长小数位数不正确")
    private String length;
    //宽
    @Digits(integer = 14, fraction = 2, message = "宽小数位数不正确")
    private String width;
    //高
    @Digits(integer = 14, fraction = 2, message = "高小数位数不正确")
    private String height;
    //颜色
    private String color;
    //广告语
    private String ad;
    //生产地
    private String originPlace;
    //eanCode
    private String eanCode;
    //图片
    private List<ItemPicture> pictures;
    //目录属性
    private String categoryAttribute;
    //商品描述
    private ItemDescribe describe;
    //操作者ID
    private Long operatorId;
    //操作者名称
    private String operatorName;
    //内部供应商ID
    private Long htdVendorId;
    //店铺ID
    private Long shopId;
    //店铺类目id
    private Long shopCid;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public List<ItemPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ItemPicture> pictures) {
        this.pictures = pictures;
    }

    public String getCategoryAttribute() {
        return categoryAttribute;
    }

    public void setCategoryAttribute(String categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
    }

    public ItemDescribe getDescribe() {
        return describe;
    }

    public void setDescribe(ItemDescribe describe) {
        this.describe = describe;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getHtdVendorId() {
        return htdVendorId;
    }

    public void setHtdVendorId(Long htdVendorId) {
        this.htdVendorId = htdVendorId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public Long getShopCid() {
        return shopCid;
    }

    public void setShopCid(Long shopCid) {
        this.shopCid = shopCid;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

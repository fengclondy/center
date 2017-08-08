package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 商家外接渠道品类品牌屏蔽
 * @author chenkang
 */
public class SellerCategoryBrandShieldDTO implements Serializable {
    private Long categoryBrandShieldId;

    private Long sellerId;

    private Long shopId;

    private Long categoryBrandId;

    private Long firstCategoryId;

    private String firstCategoryName;

    private Long secondCategoryId;

    private String secondCategoryName;

    private Long thirdCategoryId;

    private String thirdCategoryName;

    private Long[] categoryParam;

    private String categoryName;

    private Long brandId;

    private String brandName;

    private String channelCode;

    private String channelName;

    private Integer shieldFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getCategoryBrandShieldId() {
        return categoryBrandShieldId;
    }

    public void setCategoryBrandShieldId(Long categoryBrandShieldId) {
        this.categoryBrandShieldId = categoryBrandShieldId;
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

    public Long getCategoryBrandId() {
        return categoryBrandId;
    }

    public void setCategoryBrandId(Long categoryBrandId) {
        this.categoryBrandId = categoryBrandId;
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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public Integer getShieldFlag() {
        return shieldFlag;
    }

    public void setShieldFlag(Integer shieldFlag) {
        this.shieldFlag = shieldFlag;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Long[] getCategoryParam() {
        return categoryParam;
    }

    public void setCategoryParam(Long[] categoryParam) {
        this.categoryParam = categoryParam;
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

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public String getThirdCategoryName() {
        return thirdCategoryName;
    }

    public void setThirdCategoryName(String thirdCategoryName) {
        this.thirdCategoryName = thirdCategoryName;
    }

    @Override
    public String toString() {
        return "SellerCategoryBrandShieldDTO{" +
                "categoryBrandShieldId=" + categoryBrandShieldId +
                ", sellerId=" + sellerId +
                ", shopId=" + shopId +
                ", categoryBrandId=" + categoryBrandId +
                ", firstCategoryId=" + firstCategoryId +
                ", firstCategoryName='" + firstCategoryName + '\'' +
                ", secondCategoryId=" + secondCategoryId +
                ", secondCategoryName='" + secondCategoryName + '\'' +
                ", thirdCategoryId=" + thirdCategoryId +
                ", thirdCategoryName='" + thirdCategoryName + '\'' +
                ", categoryParam=" + Arrays.toString(categoryParam) +
                ", categoryName='" + categoryName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", channelName='" + channelName + '\'' +
                ", shieldFlag=" + shieldFlag +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", modifyId=" + modifyId +
                ", modifyName='" + modifyName + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
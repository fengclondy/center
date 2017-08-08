package cn.htd.goodscenter.domain.productplus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SellerCategoryBrandShield implements Serializable {
    private Long categoryBrandShieldId;

    @NotNull(message = "卖家ID不能为空")
    private Long sellerId;

    private Long shopId;

    private Long categoryBrandId;
    @NotNull(message = "类目ID不能为空")
    private Long thirdCategoryId;
    @NotNull(message = "品牌ID不能为空")
    private Long brandId;
    @NotNull(message = "渠道编码不能为空")
    private String channelCode;

    private Integer shieldFlag;
    @NotNull(message = "创建者ID不能为空")
    private Long createId;
    @NotNull(message = "创建者名称不能为空")
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
}
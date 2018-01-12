package cn.htd.goodscenter.dto.vms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 我的商品 - 批量申请商品
 * @author chenkang
 * @date 2017-12-11
 */
public class BatchAddItemInDTO implements Serializable {
    //商品名称
    @NotEmpty(message = "商品名称不能为空")
    private String productName;
    //类目名称
    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;
    //品牌名称
    @NotEmpty(message = "品牌名称不能为空")
    private String brandName;
    //商品型号
    @NotEmpty(message = "商品型号不能为空")
    private String modelType;
    //单位
    @NotEmpty(message = "单位不能为空")
    private String unit;
    //税率
    @NotEmpty(message = "税率不能为空")
    private String taxRate;
    //大B ID
    @NotNull(message = "sellerId不能为null")
    private Long sellerId;
    //店铺ID
    @NotNull(message = "店铺ID不能为null")
    private Long shopId;
    //店铺类目id
    private Long shopCid;
    //操作者ID
    @NotNull(message = "operatorId不能为null")
    private Long operatorId;
    //操作者名称
    @NotEmpty(message = "操作者名称不能为空")
    private String operatorName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

    public Long getShopCid() {
        return shopCid;
    }

    public void setShopCid(Long shopCid) {
        this.shopCid = shopCid;
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


    /** 接口内部使用 **/
    private Long firstCid;

    private Long secondCid;

    private Long thirdCid;

    private Long brandId;

    private String unitCode;

    private String parentCategoryName;

    public Long getFirstCid() {
        return firstCid;
    }

    public void setFirstCid(Long firstCid) {
        this.firstCid = firstCid;
    }

    public Long getSecondCid() {
        return secondCid;
    }

    public void setSecondCid(Long secondCid) {
        this.secondCid = secondCid;
    }

    public Long getThirdCid() {
        return thirdCid;
    }

    public void setThirdCid(Long thirdCid) {
        this.thirdCid = thirdCid;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}

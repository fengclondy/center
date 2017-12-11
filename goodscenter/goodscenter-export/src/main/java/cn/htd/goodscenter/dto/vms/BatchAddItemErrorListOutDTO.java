package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;

/**
 * 我的商品 - 批量申请商品
 * @author chenkang
 * @date 2017-12-11
 */
public class BatchAddItemErrorListOutDTO implements Serializable {

    //商品名称
    private String productName;
    //类目名称
    private String categoryName;
    //品牌名称
    private String brandName;
    //商品型号
    private String modelType;
    //单位
    private String unit;
    //税率
    private String taxRate;
    //错误信息
    private String erroMsg;

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

    public String getErroMsg() {
        return erroMsg;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }
}

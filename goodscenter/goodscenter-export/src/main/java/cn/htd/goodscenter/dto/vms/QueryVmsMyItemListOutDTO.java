package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;

/**
 * 我的商品 - 列表查询入参DTO
 * @author chenkang
 * @date 2017-12-11
 */
public class QueryVmsMyItemListOutDTO implements Serializable {
    //商品编码
    private Long itemId;
    //商品item编码
    private String itemCode;
    //商品skuid
    private Long skuId;
    //商品sku编码
    private String skuCode;
    //商品名称
    private String productName;
    //目录ID
    private Long categoryId;
    //目录名称
    private String categoryName;
    //品牌Id
    private Long brandId;
    //品牌名称
    private String brandName;
    // 审核状态（只做展示，区别于商品状态） 0 : 待审核；   1 ： 审核通过；  2：新增审核不通过；   3 :  修改待审核；  4： 修改审核不通过；
    private Integer auditStatus;

    private Integer itemStatus;

    private Integer status;

    private String erpCode;

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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode;
    }
}

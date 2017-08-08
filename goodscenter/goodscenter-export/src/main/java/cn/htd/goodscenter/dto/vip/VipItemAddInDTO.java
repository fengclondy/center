package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;

/**
 * vip套餐
 */
public class VipItemAddInDTO implements Serializable {

    // 套餐子商品编码
    String skuCode;

    private Long basePrice;

    private Long salePrice;

    private String supplierName;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "VipItemAddInDTO{" +
                "skuCode='" + skuCode + '\'' +
                ", basePrice=" + basePrice +
                ", salePrice=" + salePrice +
                '}';
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}

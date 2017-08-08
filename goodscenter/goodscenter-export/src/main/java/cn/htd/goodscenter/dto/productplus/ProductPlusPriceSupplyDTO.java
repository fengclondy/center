package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品加 价格修改【增量】DTO
 */
public class ProductPlusPriceSupplyDTO implements Serializable {
    /** sku **/
    private String outerSkuId;// 外接商品编码 skuId
    /** price **/
    private BigDecimal costPrice; // 外部供货价
    private BigDecimal saleLimitedPrice; // 外部销售价

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
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

    @Override
    public String toString() {
        return "ProductPlusPriceSupplyDTO{" +
                "outerSkuId='" + outerSkuId + '\'' +
                ", costPrice=" + costPrice +
                ", saleLimitedPrice=" + saleLimitedPrice +
                '}';
    }
}

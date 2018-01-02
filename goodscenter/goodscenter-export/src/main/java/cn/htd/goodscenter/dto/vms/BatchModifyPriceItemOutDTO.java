package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量上架商品DTO
 */
public class BatchModifyPriceItemOutDTO implements Serializable {

    private String itemCode;

    private String itemName;
    //销售价
    private BigDecimal salePrice;
    //零售价
    private BigDecimal retailPrice;
    //分销限价
    private BigDecimal saleLimitPrice;
    //错误原因
    private String errorMsg;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getSaleLimitPrice() {
        return saleLimitPrice;
    }

    public void setSaleLimitPrice(BigDecimal saleLimitPrice) {
        this.saleLimitPrice = saleLimitPrice;
    }
}

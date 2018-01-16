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
    private String salePrice;
    //零售价
    private String retailPrice;
    //分销限价
    private String saleLimitPrice;
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

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getSaleLimitPrice() {
        return saleLimitPrice;
    }

    public void setSaleLimitPrice(String saleLimitPrice) {
        this.saleLimitPrice = saleLimitPrice;
    }
}

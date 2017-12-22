package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量上架商品DTO
 */
public class BatchOnShelfItemOutDTO implements Serializable {

    private String itemCode;

    private String itemName;

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
}

package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/9.
 */
public class QueryItemStockDetailInDTO implements Serializable {
    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 是否包厢标记 0：大厅  ； 1：包厢
     *
     * 如果是外部供应商商品 传 0
     */
    private Integer isBoxFlag;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }
}

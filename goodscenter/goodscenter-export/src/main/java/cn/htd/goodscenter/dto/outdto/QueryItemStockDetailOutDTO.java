package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/9.
 */
public class QueryItemStockDetailOutDTO implements Serializable {
    /**
     * 总库存
     */
    private Integer displayQuantity;

    /**
     * 锁定库存
     */
    private Integer reserveQuantity;

    public Integer getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(Integer displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public Integer getReserveQuantity() {
        return reserveQuantity;
    }

    public void setReserveQuantity(Integer reserveQuantity) {
        this.reserveQuantity = reserveQuantity;
    }
}

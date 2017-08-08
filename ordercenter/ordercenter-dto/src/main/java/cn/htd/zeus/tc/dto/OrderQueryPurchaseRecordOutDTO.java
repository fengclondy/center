package cn.htd.zeus.tc.dto;

import cn.htd.zeus.tc.dto.response.GenricResDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 根据买家查询最近其购买商品的大B （排除没有包厢关系的大B）
 */
public class OrderQueryPurchaseRecordOutDTO extends GenricResDTO implements Serializable {
    /**
     * 卖家会员编码
     */
    private String sellerCode;

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }
}

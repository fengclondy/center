package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 根据买家查询最近其购买商品的大B （排除没有包厢关系的大B）
 */
public class OrderQueryPurchaseRecordInDTO implements Serializable {
    /**
     * 买家会员编码
     */
    private String buyerCode;

    /**
     * 和小B有包厢关系的大B列表
     */
    private List<String> boxSellerCodeList;

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public List<String> getBoxSellerCodeList() {
        return boxSellerCodeList;
    }

    public void setBoxSellerCodeList(List<String> boxSellerCodeList) {
        this.boxSellerCodeList = boxSellerCodeList;
    }
}

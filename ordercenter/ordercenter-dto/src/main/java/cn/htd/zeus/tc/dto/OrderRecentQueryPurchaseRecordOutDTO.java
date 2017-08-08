package cn.htd.zeus.tc.dto;

import cn.htd.zeus.tc.dto.response.GenricResDTO;

import java.io.Serializable;

/**
 * 根据买家查询最近其购买商品的大B （排除没有包厢关系的大B）
 */
public class OrderRecentQueryPurchaseRecordOutDTO extends GenricResDTO implements Serializable {
    /**
     * 买家会员编码
     */
    private String sellerCode;

    private String itemCode;

    private String skuCode;

    private Integer isBoxFlag;

    private int salesVolume;

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
}

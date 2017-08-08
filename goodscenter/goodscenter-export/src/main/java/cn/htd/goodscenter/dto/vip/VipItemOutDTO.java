package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;

/**
 * vip套餐基本信息
 */
public class VipItemOutDTO implements Serializable {

    String skuCode;

    String itemName;

    String companyName;

    Long sellerId;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "VipItemOutDTO{" +
                "skuCode='" + skuCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", sellerId=" + sellerId +
                '}';
    }
}

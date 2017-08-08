package cn.htd.tradecenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/1/11.
 */
public class ItemSkuSellReportInDTO implements Serializable {
    private static final long serialVersionUID = 6899198921199464338L;
    private String sellerCode;//商家code
    private String skuCode; // skucode
    private String itemCode; // 商品id
    private String goodsName;//商品名称
    private String goodsCode;//商品编码
    private String goodSkuCode;//商品sku编码
    private String startDate; // 开始日期 yyyyMMdd
    private String endDate; // 结束日期 yyyyMMdd
    private String dateFormat; // 日期格式 （默认是 yyyyMMdd ，如不需转其他格式，不用传 ）


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodSkuCode() {
        return goodSkuCode;
    }

    public void setGoodSkuCode(String goodSkuCode) {
        this.goodSkuCode = goodSkuCode;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}

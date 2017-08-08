package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by thinkpad on 2017/1/11.
 */
public class ItemSkuSellReportOutDTO implements Serializable {
    private static final long serialVersionUID = -6946528599849249901L;

    private static NumberFormat currency = NumberFormat.getNumberInstance(); // 处理数字，显示为带逗号的货币形式

    // 销售额
    private BigDecimal sellPriceTotal;
    private String sellPriceTotalStr;

    // 销售量
    private BigDecimal sellTotalNum;
    private String sellTotalNumStr;

    // 下单量
    private BigDecimal orderNum;
    private String orderNumStr;

    // 下单完成量
    private BigDecimal orderPayNum;
    private String orderPayNumStr;

    private String dealDate; // 日期

    private String goodsName;//商品名称

    private String goodsCode;//商品编码

    private String goodSkuCode;//商品sku编码


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

    public ItemSkuSellReportOutDTO() {
    }

    public ItemSkuSellReportOutDTO(String dealDate) {
        this.dealDate = dealDate;
        sellPriceTotal = new BigDecimal(0);
        sellTotalNum = new BigDecimal(0);
        orderNum = new BigDecimal(0);
        orderPayNum = new BigDecimal(0);
        sellPriceTotalStr = "0";
        sellTotalNumStr = "0";
        orderNumStr = "0";
        orderPayNumStr = "0";
    }

    public BigDecimal getSellPriceTotal() {
        return sellPriceTotal;
    }

    public void setSellPriceTotal(BigDecimal sellPriceTotal) {
        this.sellPriceTotal = sellPriceTotal;
        this.sellPriceTotalStr = currency.format(sellPriceTotal);
    }

    public BigDecimal getSellTotalNum() {
        return sellTotalNum;
    }

    public void setSellTotalNum(BigDecimal sellTotalNum) {
        this.sellTotalNum = sellTotalNum;
        this.sellTotalNumStr = currency.format(sellTotalNum);
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getSellPriceTotalStr() {
        return sellPriceTotalStr;
    }

    public void setSellPriceTotalStr(String sellPriceTotalStr) {
        this.sellPriceTotalStr = sellPriceTotalStr;
    }

    public String getSellTotalNumStr() {
        return sellTotalNumStr;
    }

    public void setSellTotalNumStr(String sellTotalNumStr) {
        this.sellTotalNumStr = sellTotalNumStr;
    }

    public BigDecimal getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(BigDecimal orderNum) {
        this.orderNum = orderNum;
        this.orderNumStr = currency.format(orderNum);
    }

    public String getOrderNumStr() {
        return orderNumStr;
    }

    public void setOrderNumStr(String orderNumStr) {
        this.orderNumStr = orderNumStr;
    }

    public BigDecimal getOrderPayNum() {
        return orderPayNum;
    }

    public void setOrderPayNum(BigDecimal orderPayNum) {
        this.orderPayNum = orderPayNum;
        this.orderPayNumStr = currency.format(orderPayNum);
    }

    public String getOrderPayNumStr() {
        return orderPayNumStr;
    }

    public void setOrderPayNumStr(String orderPayNumStr) {
        this.orderPayNumStr = orderPayNumStr;
    }

}


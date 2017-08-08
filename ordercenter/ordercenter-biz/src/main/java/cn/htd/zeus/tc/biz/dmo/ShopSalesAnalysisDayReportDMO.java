package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class ShopSalesAnalysisDayReportDMO extends BaseDMO{
	
    private Integer salesId;

    private Integer shopId;

    private Integer salesTime;

    private String skuCode;

    private String goodsName;

    private String itemCode;

    private BigDecimal salesAmount;

    private Integer salesGoodsCount;

    private Integer orderGoodsCount;

    private Date statisticsTime;

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(Integer salesTime) {
        this.salesTime = salesTime;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getSalesGoodsCount() {
        return salesGoodsCount;
    }

    public void setSalesGoodsCount(Integer salesGoodsCount) {
        this.salesGoodsCount = salesGoodsCount;
    }

    public Integer getOrderGoodsCount() {
        return orderGoodsCount;
    }

    public void setOrderGoodsCount(Integer orderGoodsCount) {
        this.orderGoodsCount = orderGoodsCount;
    }

    public Date getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(Date statisticsTime) {
        this.statisticsTime = statisticsTime;
    }
}
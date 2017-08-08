package cn.htd.tradecenter.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TradeOrderItemsPriceHistoryDTO {
    private Long id;

    private String orderNo;

    private String orderItemNo;

    private String channelCode;

    private String itemCode;

    private String skuCode;

    private BigDecimal beforeBargainingGoodsPrice;

    private Integer beforeBargainingGoodsCount;

    private BigDecimal beforeTotalPrice;

    private BigDecimal beforeFreight;

    private BigDecimal beforeTotalDiscount;

    private BigDecimal beforeShopDiscount;

    private BigDecimal beforePlatformDiscount;

    private BigDecimal beforePaymentPrice;

    private BigDecimal afterBargainingGoodsPrice;

    private Integer afterBargainingGoodsCount;

    private BigDecimal afterTotalPrice;

    private BigDecimal afterFreight;

    private BigDecimal afterTotalDiscount;

    private BigDecimal afterShopDiscount;

    private BigDecimal afterPlatformDiscount;

    private BigDecimal afterPaymentPrice;

    private Long createId;

    private String createName;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderItemNo() {
        return orderItemNo;
    }

    public void setOrderItemNo(String orderItemNo) {
        this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public BigDecimal getBeforeBargainingGoodsPrice() {
        return beforeBargainingGoodsPrice;
    }

    public void setBeforeBargainingGoodsPrice(BigDecimal beforeBargainingGoodsPrice) {
        this.beforeBargainingGoodsPrice = beforeBargainingGoodsPrice;
    }

    public Integer getBeforeBargainingGoodsCount() {
        return beforeBargainingGoodsCount;
    }

    public void setBeforeBargainingGoodsCount(Integer beforeBargainingGoodsCount) {
        this.beforeBargainingGoodsCount = beforeBargainingGoodsCount;
    }

    public BigDecimal getBeforeTotalPrice() {
        return beforeTotalPrice;
    }

    public void setBeforeTotalPrice(BigDecimal beforeTotalPrice) {
        this.beforeTotalPrice = beforeTotalPrice;
    }

    public BigDecimal getBeforeFreight() {
        return beforeFreight;
    }

    public void setBeforeFreight(BigDecimal beforeFreight) {
        this.beforeFreight = beforeFreight;
    }

    public BigDecimal getBeforeTotalDiscount() {
        return beforeTotalDiscount;
    }

    public void setBeforeTotalDiscount(BigDecimal beforeTotalDiscount) {
        this.beforeTotalDiscount = beforeTotalDiscount;
    }

    public BigDecimal getBeforeShopDiscount() {
        return beforeShopDiscount;
    }

    public void setBeforeShopDiscount(BigDecimal beforeShopDiscount) {
        this.beforeShopDiscount = beforeShopDiscount;
    }

    public BigDecimal getBeforePlatformDiscount() {
        return beforePlatformDiscount;
    }

    public void setBeforePlatformDiscount(BigDecimal beforePlatformDiscount) {
        this.beforePlatformDiscount = beforePlatformDiscount;
    }

    public BigDecimal getBeforePaymentPrice() {
        return beforePaymentPrice;
    }

    public void setBeforePaymentPrice(BigDecimal beforePaymentPrice) {
        this.beforePaymentPrice = beforePaymentPrice;
    }

    public BigDecimal getAfterBargainingGoodsPrice() {
        return afterBargainingGoodsPrice;
    }

    public void setAfterBargainingGoodsPrice(BigDecimal afterBargainingGoodsPrice) {
        this.afterBargainingGoodsPrice = afterBargainingGoodsPrice;
    }

    public Integer getAfterBargainingGoodsCount() {
        return afterBargainingGoodsCount;
    }

    public void setAfterBargainingGoodsCount(Integer afterBargainingGoodsCount) {
        this.afterBargainingGoodsCount = afterBargainingGoodsCount;
    }

    public BigDecimal getAfterTotalPrice() {
        return afterTotalPrice;
    }

    public void setAfterTotalPrice(BigDecimal afterTotalPrice) {
        this.afterTotalPrice = afterTotalPrice;
    }

    public BigDecimal getAfterFreight() {
        return afterFreight;
    }

    public void setAfterFreight(BigDecimal afterFreight) {
        this.afterFreight = afterFreight;
    }

    public BigDecimal getAfterTotalDiscount() {
        return afterTotalDiscount;
    }

    public void setAfterTotalDiscount(BigDecimal afterTotalDiscount) {
        this.afterTotalDiscount = afterTotalDiscount;
    }

    public BigDecimal getAfterShopDiscount() {
        return afterShopDiscount;
    }

    public void setAfterShopDiscount(BigDecimal afterShopDiscount) {
        this.afterShopDiscount = afterShopDiscount;
    }

    public BigDecimal getAfterPlatformDiscount() {
        return afterPlatformDiscount;
    }

    public void setAfterPlatformDiscount(BigDecimal afterPlatformDiscount) {
        this.afterPlatformDiscount = afterPlatformDiscount;
    }

    public BigDecimal getAfterPaymentPrice() {
        return afterPaymentPrice;
    }

    public void setAfterPaymentPrice(BigDecimal afterPaymentPrice) {
        this.afterPaymentPrice = afterPaymentPrice;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
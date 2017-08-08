package cn.htd.tradecenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/1/11.
 */
public class OrderDealReportInDTO implements Serializable {

    private static final long serialVersionUID = -24085632872124025L;

    private Long shopId; // 店铺id
    private String sellerCode;//商家id
    private String startDate; // 开始日期 yyyyMMdd
    private String endDate; // 结束日期
    private Integer dayInterval; // 折线图日期间隔
    private String dateFormat; // 日期格式 （默认是 yyyyMMdd ，如不需转其他格式，不用传 ）

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
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

    public Integer getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(Integer dayInterval) {
        this.dayInterval = dayInterval;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}

package cn.htd.goodscenter.dto.productplus;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品加上架设置DTO
 * @author chenkang
 */
public class ProductPlusItemUpShelfSettingDTO implements Serializable {

    @NotNull(message = "价格浮动比例")
    @DecimalMin("0")
    private BigDecimal priceFloatingRatio; //价格浮动比例

    @NotNull(message = "零售价格浮动比例")
    @DecimalMin("0")
    private BigDecimal retailPriceFloatingRatio; // 零售价格浮动比例

    @NotNull(message = "佣金比例")
    private BigDecimal commissionRatio; // 佣金比例

    private String vipPriceFloatingRatio; // vip价格浮动比例

    private Integer isPreSale;// 是否预售

    private String preSaleStarttime;// 预售开始时间

    private String preSaleEndtime;// 预售结束时间

    private String ad;// 广告语

    public BigDecimal getPriceFloatingRatio() {
        return priceFloatingRatio;
    }

    public void setPriceFloatingRatio(BigDecimal priceFloatingRatio) {
        this.priceFloatingRatio = priceFloatingRatio;
    }

    public String getVipPriceFloatingRatio() {
        return vipPriceFloatingRatio;
    }

    public void setVipPriceFloatingRatio(String vipPriceFloatingRatio) {
        this.vipPriceFloatingRatio = vipPriceFloatingRatio;
    }

    public BigDecimal getRetailPriceFloatingRatio() {
        return retailPriceFloatingRatio;
    }

    public void setRetailPriceFloatingRatio(BigDecimal retailPriceFloatingRatio) {
        this.retailPriceFloatingRatio = retailPriceFloatingRatio;
    }

    public BigDecimal getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(BigDecimal commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public Integer getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Integer isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getPreSaleStarttime() {
        return preSaleStarttime;
    }

    public void setPreSaleStarttime(String preSaleStarttime) {
        this.preSaleStarttime = preSaleStarttime;
    }

    public String getPreSaleEndtime() {
        return preSaleEndtime;
    }

    public void setPreSaleEndtime(String preSaleEndtime) {
        this.preSaleEndtime = preSaleEndtime;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}

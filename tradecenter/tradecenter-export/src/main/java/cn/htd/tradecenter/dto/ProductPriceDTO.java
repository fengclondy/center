/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    ProductPriceDTO.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: ERP商品价格信息  
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ERP商品价格信息
 */
public class ProductPriceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ERP成本价
     */
    private BigDecimal costPrice;
    /**
     * ERP分销限价
     */
    private BigDecimal floorPrice;
    /**
     * ERP分销单价
     */
    private BigDecimal webPrice;
    /**
     * ERP零售价
     */
    private BigDecimal wsaleUtprice;

    public ProductPriceDTO() {
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
    }

    public BigDecimal getWebPrice() {
        return webPrice;
    }

    public void setWebPrice(BigDecimal webPrice) {
        this.webPrice = webPrice;
    }

    public BigDecimal getWsaleUtprice() {
        return wsaleUtprice;
    }

    public void setWsaleUtprice(BigDecimal wsaleUtprice) {
        this.wsaleUtprice = wsaleUtprice;
    }
}

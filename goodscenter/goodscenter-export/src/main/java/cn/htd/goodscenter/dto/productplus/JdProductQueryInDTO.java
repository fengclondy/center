package cn.htd.goodscenter.dto.productplus;

import cn.htd.common.Pager;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询京东商品IN DTO - for superBoss
 */
public class JdProductQueryInDTO implements Serializable {

    private Date startTime; // 开始时间 【skuCode为空时必填】

    private Date endTime; // 结束时间 【skuCode为空时必填】

    private Pager pager; // 分页条件, 构造需要：page（当前页），rows（每页条数） 【skuCode为空时必填】

    private Integer isPreSale; // 是否预售商品 【可为空】

    private String skuCode; // 商品编码【可为空】

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Integer getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Integer isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


}

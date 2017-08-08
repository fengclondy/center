package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/3/7.
 */
public class SearchRecommendProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long recommendWordId;//搜索推荐Id
    private String hotWord;//品牌名称
    private String productCode;//商品编码
    private String productName;//商品名称
    private String sellerName;//供应商名称
    private Long sortNum;//显示顺序

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecommendWordId() {
        return recommendWordId;
    }

    public void setRecommendWordId(Long recommendWordId) {
        this.recommendWordId = recommendWordId;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}

package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;

public class QueryOffShelfItemInDTO implements Serializable {

    private Long firstCategoryId;

    private Long secondCategoryId;

    private Long thirdCategoryId;

    private Long brandId;

    private Long sellerId;

    // 0 : 区域   1：包厢
    private Integer isBoxFlag;

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Long getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Long thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }
}

package cn.htd.goodscenter.dto.vms;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class QueryOffShelfItemInDTO implements Serializable {

    private Long firstCategoryId;

    private Long secondCategoryId;

    private Long thirdCategoryId;

    private Long brandId;
    @NotNull(message = "sellerId必填")
    private Long sellerId;
    @NotNull(message = "供应商编码必填")
    private String supplyCode;
    @NotNull(message = "isBoxFlag必填")
    private Integer isBoxFlag;

    private List<Long> thirdCategoryIdList;

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

    public List<Long> getThirdCategoryIdList() {
        return thirdCategoryIdList;
    }

    public void setThirdCategoryIdList(List<Long> thirdCategoryIdList) {
        this.thirdCategoryIdList = thirdCategoryIdList;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }
}

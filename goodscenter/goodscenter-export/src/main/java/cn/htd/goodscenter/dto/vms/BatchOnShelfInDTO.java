package cn.htd.goodscenter.dto.vms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批量上架DTO
 */
public class BatchOnShelfInDTO implements Serializable {
    //是否包厢
    @NotNull(message = "isBoxFlag不能为NULL")
    private Integer isBoxFlag;
    //大B
    @NotNull(message = "sellerId不能为NULL")
    private Long sellerId;
    //大B注册所在地省code
    @NotEmpty(message = "defaultAreaCode不能为空")
    private String defaultAreaCode;
    //是否有低于分销限价的权限 0：没有  1：有
    @NotNull(message = "hasBelowLimitPriceAuth不能为NULL")
    private Integer hasBelowLimitPriceAuth;

    /**
     * 1 : 默认价格
     * 2 ：自定义价格
     * 3 ：自定义涨幅
     */
    @NotNull(message = "batchOnShelfType不能为NULL")
    private Integer batchOnShelfType;

    /**
     * 当类型是3的时候，需要传涨幅比例
     */
    private BigDecimal ratio;

    @NotNull(message = "dataList不能为NULL")
    List<BatchOnShelfItemInDTO> dataList;

    public Integer getBatchOnShelfType() {
        return batchOnShelfType;
    }

    public void setBatchOnShelfType(Integer batchOnShelfType) {
        this.batchOnShelfType = batchOnShelfType;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public List<BatchOnShelfItemInDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<BatchOnShelfItemInDTO> dataList) {
        this.dataList = dataList;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getDefaultAreaCode() {
        return defaultAreaCode;
    }

    public void setDefaultAreaCode(String defaultAreaCode) {
        this.defaultAreaCode = defaultAreaCode;
    }

    public Integer getHasBelowLimitPriceAuth() {
        return hasBelowLimitPriceAuth;
    }

    public void setHasBelowLimitPriceAuth(Integer hasBelowLimitPriceAuth) {
        this.hasBelowLimitPriceAuth = hasBelowLimitPriceAuth;
    }
}

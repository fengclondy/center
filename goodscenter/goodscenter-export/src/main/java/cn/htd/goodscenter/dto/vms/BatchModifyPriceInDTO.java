package cn.htd.goodscenter.dto.vms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批量上架DTO
 */
public class BatchModifyPriceInDTO implements Serializable {
    //是否包厢
    @NotNull(message = "isBoxFlag不能为NULL")
    private Integer isBoxFlag;
    //大B
    @NotNull(message = "sellerId不能为NULL")
    private Long sellerId;
    //大B编码
    @NotNull(message = "supplierCode不能为NULL")
    private String supplierCode;

    //是否有低于分销限价的权限 0：没有  1：有
    @NotNull(message = "hasBelowLimitPriceAuth不能为NULL")
    private Integer hasBelowLimitPriceAuth;

    @NotNull(message = "dataList不能为NULL")
    List<BatchModifyPriceItemInDTO> dataList;

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

    public List<BatchModifyPriceItemInDTO> getDataList() {
        return dataList;
    }

    public void setDataList(List<BatchModifyPriceItemInDTO> dataList) {
        this.dataList = dataList;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getHasBelowLimitPriceAuth() {
        return hasBelowLimitPriceAuth;
    }

    public void setHasBelowLimitPriceAuth(Integer hasBelowLimitPriceAuth) {
        this.hasBelowLimitPriceAuth = hasBelowLimitPriceAuth;
    }
}

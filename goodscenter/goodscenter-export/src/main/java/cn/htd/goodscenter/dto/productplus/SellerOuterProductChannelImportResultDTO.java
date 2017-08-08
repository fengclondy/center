package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商家接入外接渠道 批量导入结果DTO
 * @author chenkang
 */
public class SellerOuterProductChannelImportResultDTO implements Serializable {

    private Integer total;

    private Integer success;

    private Integer fail;

    private List<SellerOuterProductChannelDTO> failureList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public List<SellerOuterProductChannelDTO> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<SellerOuterProductChannelDTO> failureList) {
        this.failureList = failureList;
    }

    @Override
    public String toString() {
        return "SellerOuterProductChannelImportResultDTO{" +
                "total=" + total +
                ", success=" + success +
                ", fail=" + fail +
                ", failureList=" + failureList +
                '}';
    }
}
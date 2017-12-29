package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.util.List;

/**
 * 批量上架DTO
 */
public class BatchModifyPriceOutDTO implements Serializable {

    private int success;

    private int failCount;

    private List<BatchModifyPriceItemOutDTO> failureList;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public List<BatchModifyPriceItemOutDTO> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<BatchModifyPriceItemOutDTO> failureList) {
        this.failureList = failureList;
    }
}

package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批量上架DTO
 */
public class BatchOnShelfOutDTO implements Serializable {

    private int success;

    private int failCount;

    private List<BatchOnShelfItemOutDTO> failureList;

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

    public List<BatchOnShelfItemOutDTO> getFailureList() {
        return failureList;
    }

    public void setFailureList(List<BatchOnShelfItemOutDTO> failureList) {
        this.failureList = failureList;
    }
}

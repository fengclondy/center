package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.util.List;

/**
 * 我的商品 - 批量申请商品
 * @author chenkang
 * @date 2017-12-11
 */
public class BatchAddItemOutDTO implements Serializable {
    // 总数
    private int totalCount;
    // 成功数量
    private int successCount;
    // 失败数量
    private int failureCount;
    // 失败记录
    private List<BatchAddItemErrorListOutDTO> batchAddItemListOutDTOErrorList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public List<BatchAddItemErrorListOutDTO> getBatchAddItemListOutDTOErrorList() {
        return batchAddItemListOutDTOErrorList;
    }

    public void setBatchAddItemListOutDTOErrorList(List<BatchAddItemErrorListOutDTO> batchAddItemListOutDTOErrorList) {
        this.batchAddItemListOutDTOErrorList = batchAddItemListOutDTOErrorList;
    }
}

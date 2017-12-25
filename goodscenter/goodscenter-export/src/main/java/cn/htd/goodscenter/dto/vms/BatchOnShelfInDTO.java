package cn.htd.goodscenter.dto.vms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 批量上架DTO
 */
public class BatchOnShelfInDTO implements Serializable {
    //是否包厢
    private Integer isBoxFlag;
    //大B
    private Long sellerId;
    //大B注册所在地省code
    private String defaultAreaCode;

    /**
     * 1 : 默认价格
     * 2 ：自定义价格
     * 3 ：自定义涨幅
     */
    private Integer batchOnShelfType;

    /**
     * 当类型是3的时候，需要传涨幅比例
     */
    private BigDecimal ratio;

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
}

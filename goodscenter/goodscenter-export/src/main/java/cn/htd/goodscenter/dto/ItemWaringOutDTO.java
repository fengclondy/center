package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangxiaolong on 17/3/31.
 */
public class ItemWaringOutDTO implements Serializable{

    private Long itemId;//商品id

    private String itemCode;//商品编码

    private String skuCode;//商品sku编码

    private Date failTime;//下行失败时间

    private String  downErpStatus;//下行状态

    private String failureReason;//失败原因

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


    public String getDownErpStatus() {
        return downErpStatus;
    }

    public void setDownErpStatus(String downErpStatus) {
        this.downErpStatus = downErpStatus;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }
}

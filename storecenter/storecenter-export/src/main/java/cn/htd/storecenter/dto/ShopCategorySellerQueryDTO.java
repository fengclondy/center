package cn.htd.storecenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/2/25.
 */
public class ShopCategorySellerQueryDTO implements Serializable {
    private static final long serialVersionUID = 1l;
    private Long sellerId;
    private Long cid; //二级类目ID
    private Long parentCid;//一级类目ID
    private String cname;//二级类目名称
    private String parentCName; //一级类目名称
    private String createName;
    private Long createId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getParentCName() {
        return parentCName;
    }

    public void setParentCName(String parentCName) {
        this.parentCName = parentCName;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getParentCid() {
        return parentCid;
    }

    public void setParentCid(Long parentCid) {
        this.parentCid = parentCid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }
}

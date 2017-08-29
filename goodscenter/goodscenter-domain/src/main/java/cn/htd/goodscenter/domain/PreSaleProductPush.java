package cn.htd.goodscenter.domain;

import java.util.Date;

/**
 * Created by admin on 2017/8/14.
 */
public class PreSaleProductPush {

    private Long id;

    private Long itemId;

    private Integer pushStatus;

    private Integer pushVersion;

    private Integer lastPreSaleStatus;

    private Date createTime;// 创建日期

    private Long createId;// 创建人ID

    private String createName;// 创建人名称

    private Date modifyTime;// 修改日期

    private Long modifyId;// 更新人ID

    private String modifyName;// 更新人名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getPushVersion() {
        return pushVersion;
    }

    public void setPushVersion(Integer pushVersion) {
        this.pushVersion = pushVersion;
    }

    public Integer getLastPreSaleStatus() {
        return lastPreSaleStatus;
    }

    public void setLastPreSaleStatus(Integer lastPreSaleStatus) {
        this.lastPreSaleStatus = lastPreSaleStatus;
    }
}

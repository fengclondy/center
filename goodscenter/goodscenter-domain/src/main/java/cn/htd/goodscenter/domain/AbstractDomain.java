package cn.htd.goodscenter.domain;

import java.util.Date;

/**
 * 抽象Domain，拥有基础6个字段。
 * Created by admin on 2016/11/16.
 */
public abstract class AbstractDomain {
    /**
     * 创建人ID
     */
    protected Long createId;

    /**
     * 创建人名称
     */
    protected String createName;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 修改人ID
     */
    protected Long modifyId;

    /**
     * 修改人名称
     */
    protected String modifyName;

    /**
     * 修改时间
     */
    protected Date modifyTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemDraftDescribe implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3277274562430018622L;

	private Long itemDraftDesId;

    private Long itemDraftId;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private String describeContent;

    public Long getItemDraftDesId() {
        return itemDraftDesId;
    }

    public void setItemDraftDesId(Long itemDraftDesId) {
        this.itemDraftDesId = itemDraftDesId;
    }

    public Long getItemDraftId() {
        return itemDraftId;
    }

    public void setItemDraftId(Long itemDraftId) {
        this.itemDraftId = itemDraftId;
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
        this.createName = createName == null ? null : createName.trim();
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDescribeContent() {
        return describeContent;
    }

    public void setDescribeContent(String describeContent) {
        this.describeContent = describeContent == null ? null : describeContent.trim();
    }
}
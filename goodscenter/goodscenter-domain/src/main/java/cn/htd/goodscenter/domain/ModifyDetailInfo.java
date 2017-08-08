package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ModifyDetailInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -207748799867431303L;

	private Long modifyId;

    private Long modifyInfoId;

    private String modifyRecordType;

    private Long modifyRecordId;

    private String modifyTableId;

    private String modifyFieldId;

    private String beforeChange;

    private String afterChange;

    private Long createId;

    private String createName;

    private Date createTime;

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public Long getModifyInfoId() {
        return modifyInfoId;
    }

    public void setModifyInfoId(Long modifyInfoId) {
        this.modifyInfoId = modifyInfoId;
    }

    public String getModifyRecordType() {
        return modifyRecordType;
    }

    public void setModifyRecordType(String modifyRecordType) {
        this.modifyRecordType = modifyRecordType == null ? null : modifyRecordType.trim();
    }

    public Long getModifyRecordId() {
        return modifyRecordId;
    }

    public void setModifyRecordId(Long modifyRecordId) {
        this.modifyRecordId = modifyRecordId;
    }

    public String getModifyTableId() {
        return modifyTableId;
    }

    public void setModifyTableId(String modifyTableId) {
        this.modifyTableId = modifyTableId == null ? null : modifyTableId.trim();
    }

    public String getModifyFieldId() {
        return modifyFieldId;
    }

    public void setModifyFieldId(String modifyFieldId) {
        this.modifyFieldId = modifyFieldId == null ? null : modifyFieldId.trim();
    }

    public String getBeforeChange() {
        return beforeChange;
    }

    public void setBeforeChange(String beforeChange) {
        this.beforeChange = beforeChange == null ? null : beforeChange.trim();
    }

    public String getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(String afterChange) {
        this.afterChange = afterChange == null ? null : afterChange.trim();
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
}
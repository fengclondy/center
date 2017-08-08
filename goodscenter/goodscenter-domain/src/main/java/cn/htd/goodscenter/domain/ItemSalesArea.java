package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemSalesArea implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8760598452966341423L;

	private Long salesAreaId;

    private Long itemId;

    private Integer isBoxFlag;

    private Integer isSalesWholeCountry;

    private Integer deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(Long salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public Integer getIsSalesWholeCountry() {
        return isSalesWholeCountry;
    }

    public void setIsSalesWholeCountry(Integer isSalesWholeCountry) {
        this.isSalesWholeCountry = isSalesWholeCountry;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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
}
package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseChannelsDTO implements Serializable {
	private static final long serialVersionUID = 2990627565835351070L;

	private Long id;

    private String name;// 名称

    private Long sortNum;// 显示顺序

    private String status;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOList = new ArrayList<PurchaseChannelsSubDTO>();
    
    public List<PurchaseChannelsSubDTO> getPurchaseChannelsSubDTOList() {
		return purchaseChannelsSubDTOList;
	}

	public void setPurchaseChannelsSubDTOList(List<PurchaseChannelsSubDTO> purchaseChannelsSubDTOList) {
		this.purchaseChannelsSubDTOList = purchaseChannelsSubDTOList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FloorNav implements Serializable{

	private static final long serialVersionUID = -3734811713451103487L;

	private Long id;

    private Long floorId;

    private String name;

    private String navTemp;

    private String navTempSrc;

    private Long sortNum;

    private String status;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;
    
    private List<Floor> floorList;//获取楼层内容列表
    
    private List<Floor> contentSubList;//获取楼层品牌列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNavTemp() {
        return navTemp;
    }

    public void setNavTemp(String navTemp) {
        this.navTemp = navTemp == null ? null : navTemp.trim();
    }

    public String getNavTempSrc() {
        return navTempSrc;
    }

    public void setNavTempSrc(String navTempSrc) {
        this.navTempSrc = navTempSrc == null ? null : navTempSrc.trim();
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

	public List<Floor> getFloorList() {
		return floorList;
	}

	public void setFloorList(List<Floor> floorList) {
		this.floorList = floorList;
	}

	public List<Floor> getContentSubList() {
		return contentSubList;
	}

	public void setContentSubList(List<Floor> contentSubList) {
		this.contentSubList = contentSubList;
	}
    
	
}
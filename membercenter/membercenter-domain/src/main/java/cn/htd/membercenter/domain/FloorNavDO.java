package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Purpose 楼层导航DO
 * @author zf.zhang
 * @since 2017-3-11 17:22
 */
public class FloorNavDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2289865498901530981L;
	
	
	//主键
	private Long id;
    //楼层ID
    private Long floorId;
    //名称
    private String name;
    //导航模板
    private String navTemp;
    //导航模板式样
    private String navTempSrc;
    //显示顺序
    private int sortNum;
    //状态  1:上架、0:下架
    private String status;

    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    
    //图片列表
    private List<FloorPicDO> floorPicList;
    
    
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
		this.name = name;
	}
	public String getNavTemp() {
		return navTemp;
	}
	public void setNavTemp(String navTemp) {
		this.navTemp = navTemp;
	}
	public String getNavTempSrc() {
		return navTempSrc;
	}
	public void setNavTempSrc(String navTempSrc) {
		this.navTempSrc = navTempSrc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public List<FloorPicDO> getFloorPicList() {
		return floorPicList;
	}
	public void setFloorPicList(List<FloorPicDO> floorPicList) {
		this.floorPicList = floorPicList;
	}

    
    
    
}
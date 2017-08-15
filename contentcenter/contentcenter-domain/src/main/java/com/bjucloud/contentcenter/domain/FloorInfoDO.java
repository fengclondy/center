package com.bjucloud.contentcenter.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Purpose 楼层信息DO
 * @author zf.zhang
 * @since 2017-3-11 17:22
 */
public class FloorInfoDO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8363837125921844611L;
	
	
	
	//主键
	private Long id;
	//名称	
    private String name;
    //图标地址
    private String iconUrl;
    //左侧图片标题
    private String leftPicTitle;
    //左侧图片地址
    private String leftPicUrl;
    //左侧图片指向连接地址
    private String leftPicLinkUrl;
    //显示顺序
    private int sortNum;
    //状态  1:上架、0:下架、2.删除	
    private String status;
    //是否展示品牌 1:展示、0:不展示
    private String showBrand;

    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    
    //导航栏列表
    private List<FloorNavDO> floorNavList;
    //品牌栏列表
    private List<FloorBrandDO> floorBrandList;
    
    
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
		this.name = name;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getLeftPicTitle() {
		return leftPicTitle;
	}
	public void setLeftPicTitle(String leftPicTitle) {
		this.leftPicTitle = leftPicTitle;
	}
	public String getLeftPicUrl() {
		return leftPicUrl;
	}
	public void setLeftPicUrl(String leftPicUrl) {
		this.leftPicUrl = leftPicUrl;
	}
	public String getLeftPicLinkUrl() {
		return leftPicLinkUrl;
	}
	public void setLeftPicLinkUrl(String leftPicLinkUrl) {
		this.leftPicLinkUrl = leftPicLinkUrl;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShowBrand() {
		return showBrand;
	}
	public void setShowBrand(String showBrand) {
		this.showBrand = showBrand;
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
	public List<FloorNavDO> getFloorNavList() {
		return floorNavList;
	}
	public void setFloorNavList(List<FloorNavDO> floorNavList) {
		this.floorNavList = floorNavList;
	}
	public List<FloorBrandDO> getFloorBrandList() {
		return floorBrandList;
	}
	public void setFloorBrandList(List<FloorBrandDO> floorBrandList) {
		this.floorBrandList = floorBrandList;
	}
    
    

    
    

    
    
    
}
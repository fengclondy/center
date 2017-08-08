package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class Floor implements Serializable {

	private static final long serialVersionUID = 1648331538269746097L;

	private Long Infoid;//楼层ID
	
	private String Infoname;//名称
	 
	private Long InfosortNum;//显示顺序

	private String Infostatus;//状态  1:上架、0:下架'

	private Long Navid;//楼层导航ID

	private String NavName;//楼层导航名称

	private String NavNavTemp;//楼层导航模板

	private String NavNavTempSrc;//楼层导航模板式样

	private Long NavSortNum;//楼层导航显示顺序

	private String NavStatus;//楼层导航状态
	
	private Long contentId;//楼层内容信息ID

	private Date startTime;//楼层内容信息生效开始时间

	private Date endTime;//楼层内容信息生效结束时间

	private Long showBrand;//楼层内容信息是否展示品牌
	
	private Long contentPicSubId;//楼层内容图片ID

	private String picUrl;//楼层内容图片地址

	private String linkUrl;//楼层内容图片指向连接地址

	private Long contentPicSubsortNum;//楼层内容图片显示顺序
	
	private Long contentSubId;//楼层内容品牌子表

	private String brandName;//品牌名称

	private Long brandId;//品牌id

	private Long sortNum;//楼层内容品牌显示顺序
	
	private String brandLogoUrl;// 品牌logo地址
	
	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;
	

	public Long getInfoid() {
		return Infoid;
	}

	public void setInfoid(Long infoid) {
		Infoid = infoid;
	}

	public String getInfoname() {
		return Infoname;
	}

	public void setInfoname(String infoname) {
		Infoname = infoname;
	}

	public Long getInfosortNum() {
		return InfosortNum;
	}

	public void setInfosortNum(Long infosortNum) {
		InfosortNum = infosortNum;
	}

	public String getInfostatus() {
		return Infostatus;
	}

	public void setInfostatus(String infostatus) {
		Infostatus = infostatus;
	}

	public Long getNavid() {
		return Navid;
	}

	public void setNavid(Long navid) {
		Navid = navid;
	}

	public String getNavName() {
		return NavName;
	}

	public void setNavName(String navName) {
		NavName = navName;
	}

	public String getNavNavTemp() {
		return NavNavTemp;
	}

	public void setNavNavTemp(String navNavTemp) {
		NavNavTemp = navNavTemp;
	}

	public String getNavNavTempSrc() {
		return NavNavTempSrc;
	}

	public void setNavNavTempSrc(String navNavTempSrc) {
		NavNavTempSrc = navNavTempSrc;
	}

	public Long getNavSortNum() {
		return NavSortNum;
	}

	public void setNavSortNum(Long navSortNum) {
		NavSortNum = navSortNum;
	}

	public String getNavStatus() {
		return NavStatus;
	}

	public void setNavStatus(String navStatus) {
		NavStatus = navStatus;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getShowBrand() {
		return showBrand;
	}

	public void setShowBrand(Long showBrand) {
		this.showBrand = showBrand;
	}

	public Long getContentPicSubId() {
		return contentPicSubId;
	}

	public void setContentPicSubId(Long contentPicSubId) {
		this.contentPicSubId = contentPicSubId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getContentPicSubsortNum() {
		return contentPicSubsortNum;
	}

	public void setContentPicSubsortNum(Long contentPicSubsortNum) {
		this.contentPicSubsortNum = contentPicSubsortNum;
	}

	public Long getContentSubId() {
		return contentSubId;
	}

	public void setContentSubId(Long contentSubId) {
		this.contentSubId = contentSubId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
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

	public String getBrandLogoUrl() {
		return brandLogoUrl;
	}

	public void setBrandLogoUrl(String brandLogoUrl) {
		this.brandLogoUrl = brandLogoUrl;
	}

	
}

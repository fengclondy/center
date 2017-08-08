package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

public class SubAd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String subId;// 城市站ID

	private Long subAdId;// 城市站广告ID

	private Long subContentId;// 城市站内容信息Id

	private String name;// 名称

	private String navTemp;// 导航模板

	private String navTempSrc;// 导航模板式样

	private String status;// 状态 1:上架、0:下架

	private String picUrl;// 图片地址

	private String linkUrl;// 指向连接地址

	private Long SubContentPicSubSortNum;// 城市站内容图片显示顺序

	private String sellerName;// 供应商名称

	private String sellerCode;// 供应商编码

	private Date startTime;// 生效开始时间

	private Date endTime;// 生效结束时间

	private Long SubContentSubSortNum;// 供应商表里面显示顺序

	private Long createId;// 创建人ID

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人名称

	private Date modifyTime;// 更新时间

	private Long memberId;// 供应商编码对应的商家ID
	private String businessScope; // 经营范围
	private String businessCategory;// 经营类目
	private String majorBusinessCategory;// 主营类目
	private String businessBrand;// 经营品牌
	private String operatingLife; // 经营年限

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId == null ? null : subId.trim();
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

	public Long getSubAdId() {
		return subAdId;
	}

	public void setSubAdId(Long subAdId) {
		this.subAdId = subAdId;
	}

	public Long getSubContentId() {
		return subContentId;
	}

	public void setSubContentId(Long subContentId) {
		this.subContentId = subContentId;
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

	public Long getSubContentPicSubSortNum() {
		return SubContentPicSubSortNum;
	}

	public void setSubContentPicSubSortNum(Long subContentPicSubSortNum) {
		SubContentPicSubSortNum = subContentPicSubSortNum;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
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

	public Long getSubContentSubSortNum() {
		return SubContentSubSortNum;
	}

	public void setSubContentSubSortNum(Long subContentSubSortNum) {
		SubContentSubSortNum = subContentSubSortNum;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getMajorBusinessCategory() {
		return majorBusinessCategory;
	}

	public void setMajorBusinessCategory(String majorBusinessCategory) {
		this.majorBusinessCategory = majorBusinessCategory;
	}

	public String getBusinessBrand() {
		return businessBrand;
	}

	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand;
	}

	public String getOperatingLife() {
		return operatingLife;
	}

	public void setOperatingLife(String operatingLife) {
		this.operatingLife = operatingLife;
	}

}
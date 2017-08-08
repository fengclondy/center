package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮助主题领域对象
 * @author zf.zhang
 * @since 2017-3-30 14:23
 */
public class HelpDocTopicDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9018194502414665500L;
	//主键
	private Long id;
	//主题编码
	private String topictCode;
	//主题名称
	private String topictName;
	//分类编码
	private String categoryCode;
	//分类名称
	private String categoryName;
	//二级分类编码
	private String secondCategoryCode;
	//二级分类名称
	private String secondCategoryName;
	//显示顺序
	private int sortNum;
	//内容
	private String content;
	//是否发布(0.否 ;  1.是  ; 2.草稿)
	private Integer isPublish;
	//访问地址
	private String visitUrl;
	
	private int pageNo;
	
	private int pageSize;

	private Long createId;
	private String createName;
	private Date createTime;

	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	
	private String createStartTime;
	private String createEndTime;
	private String modifyStartTime;
	private String modifyEndTime;
	
	
	public String getCreateStartTime() {
		return createStartTime;
	}
	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}
	public String getCreateEndTime() {
		return createEndTime;
	}
	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}
	public String getModifyStartTime() {
		return modifyStartTime;
	}
	public void setModifyStartTime(String modifyStartTime) {
		this.modifyStartTime = modifyStartTime;
	}
	public String getModifyEndTime() {
		return modifyEndTime;
	}
	public void setModifyEndTime(String modifyEndTime) {
		this.modifyEndTime = modifyEndTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTopictCode() {
		return topictCode;
	}
	public void setTopictCode(String topictCode) {
		this.topictCode = topictCode;
	}
	public String getTopictName() {
		return topictName;
	}
	public void setTopictName(String topictName) {
		this.topictName = topictName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSecondCategoryCode() {
		return secondCategoryCode;
	}
	public void setSecondCategoryCode(String secondCategoryCode) {
		this.secondCategoryCode = secondCategoryCode;
	}
	public String getSecondCategoryName() {
		return secondCategoryName;
	}
	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
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
	public String getVisitUrl() {
		return visitUrl;
	}
	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	

	
	
}

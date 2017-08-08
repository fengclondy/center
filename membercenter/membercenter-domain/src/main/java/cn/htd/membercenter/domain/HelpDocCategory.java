package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮助分类领域对象
 * @author zf.zhang
 * @since 2017-3-30 14:23
 */
public class HelpDocCategory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6981273739382601719L;
	
	//主键
	private Long id;
	//分类编码
	private String categoryCode;
	//分类名称
	private String categoryName;
	//父分类编码
	private String parentCategoryCode;
	//父分类名称
	private String parentCategoryName;
	//分类级别(1.一级分类、2.二级分类、3.三级分类、等等)
	private int level;
	//是否发布(0.否 false、 1.是 true)
	private Boolean isPublish;
	//显示顺序
	private int sortNum;

	private Long createId;
	private String createName;
	private Date createTime;

	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getParentCategoryCode() {
		return parentCategoryCode;
	}
	public void setParentCategoryCode(String parentCategoryCode) {
		this.parentCategoryCode = parentCategoryCode;
	}
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
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
	
	
	
	
}

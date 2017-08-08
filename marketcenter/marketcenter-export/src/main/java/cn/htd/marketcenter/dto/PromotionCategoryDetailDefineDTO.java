package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2016/12/2.
 */
public class PromotionCategoryDetailDefineDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 规则ID
	 */
	private Long ruleId;
	/**
	 * 三级类目ID
	 */
	private Long categoryId;
	/**
	 * 品牌ID列表
	 */
	private String brandIdList;
	/**
	 * 删除标记
	 */
	private int deleteFlag;
	/**
	 * 创建人ID
	 */
	private Long createId;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新人ID
	 */
	private Long modifyId;
	/**
	 * 更新人名称
	 */
	private String modifyName;
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	/**
	 * 品牌ID集合
	 */
	private List<Long> bids;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(String brandIdList) {
		this.brandIdList = brandIdList;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
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

	public List<Long> getBids() {
		return bids;
	}

	public void setBids(List<Long> bids) {
		this.bids = bids;
	}
}

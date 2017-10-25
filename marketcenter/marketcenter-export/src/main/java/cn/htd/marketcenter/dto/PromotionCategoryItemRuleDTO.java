package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 促销活动品类商品规则
 */
public class PromotionCategoryItemRuleDTO implements Serializable {

	private static final long serialVersionUID = -509418216057211902L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 促销活动编码
	 */
	private String promotionId;
	/**
	 * 规则ID
	 */
	private Long ruleId;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 规则品类商品对象
	 */
	private String ruleTargetType;
	/**
	 * 对象商品限制
	 */
	private String targetItemLimit;
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
	 * 促销活动品类明细
	 */
	private List<PromotionCategoryDetailDTO> categoryDetailList;
	/**
	 * 促销活动商品明细
	 */
	private List<PromotionItemDetailDTO> itemDetailList;
	//----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
	/**
	 * 指定促销活动品类列表
	 */
	private Map<String, String> targetCategoryCodeMap;
	/**
	 * 指定促销活动商品列表
	 */
	private List<String> targetItemCodeList;
	//----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleTargetType() {
		return ruleTargetType;
	}

	public void setRuleTargetType(String ruleTargetType) {
		this.ruleTargetType = ruleTargetType;
	}

	public String getTargetItemLimit() {
		return targetItemLimit;
	}

	public void setTargetItemLimit(String targetItemLimit) {
		this.targetItemLimit = targetItemLimit;
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

	public List<PromotionCategoryDetailDTO> getCategoryDetailList() {
		return categoryDetailList;
	}

	public void setCategoryDetailList(List<PromotionCategoryDetailDTO> categoryDetailList) {
		this.categoryDetailList = categoryDetailList;
	}

	public List<PromotionItemDetailDTO> getItemDetailList() {
		return itemDetailList;
	}

	public void setItemDetailList(List<PromotionItemDetailDTO> itemDetailList) {
		this.itemDetailList = itemDetailList;
	}

	public Map<String, String> getTargetCategoryCodeMap() {
		return targetCategoryCodeMap;
	}

	public void setTargetCategoryCodeMap(Map<String, String> targetCategoryCodeMap) {
		this.targetCategoryCodeMap = targetCategoryCodeMap;
	}

	public List<String> getTargetItemCodeList() {
		return targetItemCodeList;
	}

	public void setTargetItemCodeList(List<String> targetItemCodeList) {
		this.targetItemCodeList = targetItemCodeList;
	}
}
package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 促销活动会员规则
 */
public class PromotionBuyerRuleDTO implements Serializable {

	private static final long serialVersionUID = 5564906942823539638L;
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
	 * 会员规则对象
	 */
	private String ruleTargetType;
	/**
	 * 对象会员级别
	 */
	private String targetBuyerLevel;
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
	 * 指定买家信息
	 */
	private List<PromotionBuyerDetailDTO> buyerDetailList;
	/**
	 * 对象会员级别列表
	 */
	private List<String> targetBuyerLevelList;
	
	/**
	 * 会员分组
	 */
	private String targetBuyerGroup;
	
	/**
	 * 会员分组列表
	 */
	private List<String> targetBuyerGroupList;

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

	public String getTargetBuyerLevel() {
		return targetBuyerLevel;
	}

	public void setTargetBuyerLevel(String targetBuyerLevel) {
		this.targetBuyerLevel = targetBuyerLevel;
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

	public List<PromotionBuyerDetailDTO> getBuyerDetailList() {
		return buyerDetailList;
	}

	public void setBuyerDetailList(List<PromotionBuyerDetailDTO> buyerDetailList) {
		this.buyerDetailList = buyerDetailList;
	}

	public List<String> getTargetBuyerLevelList() {
		return targetBuyerLevelList;
	}

	public void setTargetBuyerLevelList(List<String> targetBuyerLevelList) {
		this.targetBuyerLevelList = targetBuyerLevelList;
	}

	public String getTargetBuyerGroup() {
		return targetBuyerGroup;
	}

	public void setTargetBuyerGroup(String targetBuyerGroup) {
		this.targetBuyerGroup = targetBuyerGroup;
	}

	public List<String> getTargetBuyerGroupList() {
		return targetBuyerGroupList;
	}

	public void setTargetBuyerGroupList(List<String> targetBuyerGroupList) {
		this.targetBuyerGroupList = targetBuyerGroupList;
	}
	
	

}
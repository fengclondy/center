package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class PromotionRulesDefineDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long ruleId; // 规则ID
	private String ruleType; // 规则种类 1:会员规则，2：卖家规则，3：品类/商品规则
	private String ruleName; // 规则名称
	private String ruleDescribe; // 规则描述
	private int deleteFlag; // 删除标志 0：未删除 1：已删除
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;

	public void setRuleDefine(PromotionRulesDefineDTO ruleDTO) {
		this.ruleType = ruleDTO.getRuleType();
		this.ruleName = ruleDTO.getRuleName();
		this.ruleDescribe = ruleDTO.getRuleDescribe();
		this.deleteFlag = ruleDTO.getDeleteFlag();
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDescribe() {
		return ruleDescribe;
	}

	public void setRuleDescribe(String ruleDescribe) {
		this.ruleDescribe = ruleDescribe;
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
}

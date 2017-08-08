package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.List;

public class PromotionBuyerRuleDefineDTO extends PromotionRulesDefineDTO implements Serializable {

	private static final long serialVersionUID = 3796563243557035474L;

	private String ruleTargetType; // 会员规则对象

	private String targetBuyerLevel; // 对象会员级别

	private List<String> targetBuyerLevelList;// 对象会员级别列表

	private List<PromotionBuyerDetailDefineDTO> buyerDetailList;

	public String getRuleTargetType() {
		return ruleTargetType;
	}

	public void setRuleTargetType(String ruleTargetType) {
		this.ruleTargetType = ruleTargetType == null ? null : ruleTargetType.trim();
	}

	public String getTargetBuyerLevel() {
		return targetBuyerLevel;
	}

	public void setTargetBuyerLevel(String targetBuyerLevel) {
		this.targetBuyerLevel = targetBuyerLevel == null ? null : targetBuyerLevel.trim();
	}

	public List<String> getTargetBuyerLevelList() {
		return targetBuyerLevelList;
	}

	public void setTargetBuyerLevelList(List<String> targetBuyerLevelList) {
		this.targetBuyerLevelList = targetBuyerLevelList;
	}

	public List<PromotionBuyerDetailDefineDTO> getBuyerDetailList() {
		return buyerDetailList;
	}

	public void setBuyerDetailList(List<PromotionBuyerDetailDefineDTO> buyerDetailList) {
		this.buyerDetailList = buyerDetailList;
	}
}
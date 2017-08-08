package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.List;

public class PromotionCategoryItemRuleDefineDTO extends PromotionRulesDefineDTO implements Serializable {

	private static final long serialVersionUID = 7384630619493826475L;
	/**
	 * 规则品类商品对象 1-类目、品牌限制，2-商品限制
	 */
	private String ruleTargetType;
	/**
	 * 对象商品限制 1-指定商品可用，2-指定商品不可用
	 */
	private String targetItemLimit;

	private List<PromotionCategoryDetailDefineDTO> categoryList;

	private List<PromotionItemDetailDefineDTO> itemList;

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

	public List<PromotionCategoryDetailDefineDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<PromotionCategoryDetailDefineDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public List<PromotionItemDetailDefineDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<PromotionItemDetailDefineDTO> itemList) {
		this.itemList = itemList;
	}

}

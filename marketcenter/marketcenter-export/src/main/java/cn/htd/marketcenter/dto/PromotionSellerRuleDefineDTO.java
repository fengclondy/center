package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.List;

public class PromotionSellerRuleDefineDTO extends PromotionRulesDefineDTO implements Serializable {

	private static final long serialVersionUID = -6052268931974242088L;
	/**
	 * 卖家规则对象种类 ：1：指定供应商类型，2：部分供应商
	 */
	private String ruleTargetType;
	/**
	 * 卖家规则对象种类为1时使用 0：全部通用 1（平台公司），2（商品+），3（外部供应商）
	 */
	private String targetSellerType;

	private List<PromotionSellerDetailDefineDTO> sellerDetailList;

	public String getRuleTargetType() {
		return ruleTargetType;
	}

	public void setRuleTargetType(String ruleTargetType) {
		this.ruleTargetType = ruleTargetType;
	}

	public String getTargetSellerType() {
		return targetSellerType;
	}

	public void setTargetSellerType(String targetSellerType) {
		this.targetSellerType = targetSellerType;
	}

	public List<PromotionSellerDetailDefineDTO> getSellerDetailList() {
		return sellerDetailList;
	}

	public void setSellerDetailList(List<PromotionSellerDetailDefineDTO> sellerDetailList) {
		this.sellerDetailList = sellerDetailList;
	}

}

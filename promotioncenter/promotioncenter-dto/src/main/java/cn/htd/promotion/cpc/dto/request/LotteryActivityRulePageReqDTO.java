package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class LotteryActivityRulePageReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578890730621274319L;

	/**
	 * 促销活动编码
	 */
	@NotEmpty(message = "promotionId不能为空")
	private String promotionId;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
}

package cn.htd.promotion.cpc.dto.request;

public class LotteryActivityRulePageReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578890730621274319L;

	/**
	 * 促销活动编码
	 */
	private String promotionId;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
}

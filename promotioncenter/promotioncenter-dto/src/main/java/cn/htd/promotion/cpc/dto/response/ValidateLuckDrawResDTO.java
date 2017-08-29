package cn.htd.promotion.cpc.dto.response;

public class ValidateLuckDrawResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8775374321532714518L;

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

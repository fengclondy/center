package cn.htd.promotion.cpc.dto.response;

public class ValidateScratchCardResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1850564365522903462L;
	
	/**
	 * 促销活动编码
	 */
	private String promotionId;
	
	/**
	 * 会员店id
	 */
	private String orgId;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}

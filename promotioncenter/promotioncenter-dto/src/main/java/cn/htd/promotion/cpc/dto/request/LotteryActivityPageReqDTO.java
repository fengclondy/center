package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class LotteryActivityPageReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357054974681996019L;

	/**
	 * 会员店id
	 * 小b的id
	 */
	@NotEmpty(message = "orgId不能为空")
	private String orgId;
	
	/**
	 * 粉丝id
	 */
	private String memberNo;
	
	/**
	 * 促销活动编码
	 */
	@NotEmpty(message = "promotionId不能为空")
	private String promotionId;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
}

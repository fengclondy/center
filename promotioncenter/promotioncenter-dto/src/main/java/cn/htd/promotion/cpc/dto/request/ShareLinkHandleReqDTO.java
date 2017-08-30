package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class ShareLinkHandleReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7564592165627630560L;

	/**
	 * 粉丝id
	 */
	@NotEmpty(message = "memberNo不能为空")
	private String memberNo;

	/**
	 * 会员店id
	 */
	@NotEmpty(message = "orgId不能为空")
	private String orgId;

	/**
	 * 促销活动编码
	 */
	@NotEmpty(message = "promotionId不能为空")
	private String promotionId;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
}

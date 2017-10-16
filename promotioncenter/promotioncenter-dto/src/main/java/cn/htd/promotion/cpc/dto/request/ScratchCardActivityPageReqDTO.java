package cn.htd.promotion.cpc.dto.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class ScratchCardActivityPageReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4219678011407115811L;

	/**
	 * 订单号
	 */
	@NotEmpty(message = "orderNo不能为空")
	private String orderNo;
	
	/**
	 * 粉丝id
	 */
	@NotEmpty(message = "memberNo不能为空")
	private String memberNo;
	
	/**
     * 抽奖活动卖家编码
     */
    @NotEmpty(message = "卖家编码不能为空")
    private String orgId;
    
    /**
     * 活动编码
     */
    @NotEmpty(message = "promotionId不能为空")
    private String promotionId;
	
	/**
	 * 老的粉丝id
	 */
	@NotEmpty(message = "oldMemberNo不能为空")
	private String oldMemberNo;
	
	/**
	 * 支付时间
	 */
	private Date payDate;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

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

	public String getOldMemberNo() {
		return oldMemberNo;
	}

	public void setOldMemberNo(String oldMemberNo) {
		this.oldMemberNo = oldMemberNo;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}

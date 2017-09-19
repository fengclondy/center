package cn.htd.promotion.cpc.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

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
	 * 订单金额
	 */
	@NotNull(message = "orderAmount不能为空")
	private BigDecimal orderAmount;
	
	/**
	 * 支付方式
	 */
	@NotEmpty(message = "payType不能为空")
	private String payType;
	
	/**
	 * 老的粉丝id
	 */
	@NotEmpty(message = "oldMemberNo不能为空")
	private String oldMemberNo;

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

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOldMemberNo() {
		return oldMemberNo;
	}

	public void setOldMemberNo(String oldMemberNo) {
		this.oldMemberNo = oldMemberNo;
	}
}

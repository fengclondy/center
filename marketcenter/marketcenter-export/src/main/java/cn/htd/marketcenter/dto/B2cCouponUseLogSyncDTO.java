package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class B2cCouponUseLogSyncDTO extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766379265602402885L;
	
	/**
	 * B2C活动编号
	 */
	@NotEmpty(message = "b2cActivityCode不能为空")
	private String b2cActivityCode;
	
	/**
	 * B2C粉丝编码
	 */
	@NotEmpty(message = "b2cBuyerCode不能为空")
	private String b2cBuyerCode;

	/**
	 * 中台会员店Id
	 */
	private String memberId;

	/**
	 * 中台会员店code
	 */
	private String memberCode;
	
	/**
	 * B2C订单号
	 */
	@NotEmpty(message = "b2cOrderNo不能为空")
	private String b2cOrderNo;

	/**
	 * B2C优惠券号
	 */
	@NotEmpty(message = "b2cBuyerCouponCode不能为空")
	private String b2cBuyerCouponCode;

	/**
	 * B2C用券金额	
	 */
	@NotNull(message = "b2cCouponUsedAmount不能为空")
	private BigDecimal b2cCouponUsedAmount;

	/**
	 * 用券类型
	 * 1：在线支付订单，2：货到付款订单确认，3：在线支付订单取消确认后
	 */
	@NotEmpty(message = "useType不能为空")
	private String useType;
    
    /**
     * 创建人ID
     */
    @NotNull(message = "创建人ID不能为空")
    private Long createId;
    /**
     * 创建人名称
     */
    @NotNull(message = "创建人名称不能为空")
    private String createName;

	public String getB2cActivityCode() {
		return b2cActivityCode;
	}

	public void setB2cActivityCode(String b2cActivityCode) {
		this.b2cActivityCode = b2cActivityCode;
	}

	public String getB2cBuyerCode() {
		return b2cBuyerCode;
	}

	public void setB2cBuyerCode(String b2cBuyerCode) {
		this.b2cBuyerCode = b2cBuyerCode;
	}

	public String getB2cOrderNo() {
		return b2cOrderNo;
	}

	public void setB2cOrderNo(String b2cOrderNo) {
		this.b2cOrderNo = b2cOrderNo;
	}

	public String getB2cBuyerCouponCode() {
		return b2cBuyerCouponCode;
	}

	public void setB2cBuyerCouponCode(String b2cBuyerCouponCode) {
		this.b2cBuyerCouponCode = b2cBuyerCouponCode;
	}

	public BigDecimal getB2cCouponUsedAmount() {
		return b2cCouponUsedAmount;
	}

	public void setB2cCouponUsedAmount(BigDecimal b2cCouponUsedAmount) {
		this.b2cCouponUsedAmount = b2cCouponUsedAmount;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
}

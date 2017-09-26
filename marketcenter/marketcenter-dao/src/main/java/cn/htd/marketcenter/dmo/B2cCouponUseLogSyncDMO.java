package cn.htd.marketcenter.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class B2cCouponUseLogSyncDMO {
	
	/**
	 * B2C活动编号
	 */
	private String b2cActivityCode;
	
	/**
	 * B2C粉丝编码
	 */
	private String b2cBuyerCode;

	/**
	 * B2C会员店编码
	 */
	private String b2cSellerCode;

	/**
	 * B2C订单号
	 */
	private String b2cOrderNo;

	/**
	 * B2C优惠券号
	 */
	private String b2cBuyerCouponCode;

	/**
	 * B2C用券金额	
	 */
	private BigDecimal b2cCouponUsedAmount;

	/**
	 * 用券类型
	 * 1：在线支付订单，2：货到付款订单确认，3：在线支付订单取消确认后
	 */
	private String useType;
    
    /**
     * 创建人ID
     */
    private Long createId;
    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 前端不需要传入
     */
    private int dealFlag;
    
    /**
     * 前端不需要传入
     */
    private Date createTime; 
    
    /**
     * 前端不需要传入
     */
    private Integer id;

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

	public String getB2cSellerCode() {
		return b2cSellerCode;
	}

	public void setB2cSellerCode(String b2cSellerCode) {
		this.b2cSellerCode = b2cSellerCode;
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

	public int getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(int dealFlag) {
		this.dealFlag = dealFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

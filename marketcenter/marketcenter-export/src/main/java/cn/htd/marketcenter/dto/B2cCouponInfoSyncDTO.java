package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class B2cCouponInfoSyncDTO extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4278387265082086915L;
	
	/**
	 * B2C活动编号
	 */
	@NotEmpty(message = "b2cActivityCode不能为空")
	private String b2cActivityCode;
	
	/**
	 * 优惠券名称
	 */
	@NotEmpty(message = "couponName不能为空")
	private String couponName;

	/**
	 * 优惠券描述
	 */
	@NotEmpty(message = "couponDescribe不能为空")
	private String couponDescribe;

	/**
	 * 优惠券类型
	 * 1：满减券 2：折扣券
	 */
	@NotEmpty(message = "couponType不能为空")
	private String couponType;

	/**
	 * 券发放方式
	 * 1-自动发放，2-会员领取，3-触发返券
	 */
	@NotEmpty(message = "couponProvideType不能为空")
	private String couponProvideType;

	/**
	 * 优惠券有效期开始时间
	 */
	@NotNull(message = "couponStartTime不能为空")
	private Date couponStartTime;

	/**
	 * 优惠券有效期结束时间
	 */
	@NotNull(message = "couponEndTime不能为空")
	private Date couponEndTime;

	/**
	 * 使用门槛阈值（满减时表示满多少元）v
	 */
	@NotNull(message = "discountThreshold不能为空")
	private BigDecimal discountThreshold;

	/**
	 * 折扣券单次使用百分比值
	 */
	@NotNull(message = "discountPercent不能为空")
	private Integer discountPercent;
	
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

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponDescribe() {
		return couponDescribe;
	}

	public void setCouponDescribe(String couponDescribe) {
		this.couponDescribe = couponDescribe;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponProvideType() {
		return couponProvideType;
	}

	public void setCouponProvideType(String couponProvideType) {
		this.couponProvideType = couponProvideType;
	}

	public Date getCouponStartTime() {
		return couponStartTime;
	}

	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public BigDecimal getDiscountThreshold() {
		return discountThreshold;
	}

	public void setDiscountThreshold(BigDecimal discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
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
}

package cn.htd.marketcenter.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class B2cCouponInfoSyncDMO {

    /**
     * ID
     */
    private Long id;
    /**
     * B2C活动编号
     */
    private String b2cActivityCode;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 优惠券描述
     */
    private String couponDescribe;
    /**
     * 优惠券类型
     * 1：满减券 2：折扣券
     */
    private String couponType;
    /**
     * 券发放方式
     * 1-自动发放，2-会员领取，3-触发返券
     */
    private String couponProvideType;
    /**
     * 优惠券有效期开始时间
     */
    private Date couponStartTime;
    /**
     * 优惠券有效期结束时间
     */
    private Date couponEndTime;
    /**
     * 使用门槛阈值（满减时表示满多少元）v
     */
    private BigDecimal discountThreshold;
    /**
     * 折扣券单次使用百分比值
     */
    private Integer discountPercent;
    /**
     * 处理标记 O:未处理，1:处理成功，2：处理失败
     */
    private int dealFlag;
    /**
     * 处理失败原因
     */
    private String dealFailReason;
    /**
     * 创建人ID
     */
    private Long createId;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人ID
     */
    private Long modifyId;
    /**
     * 更新人名称
     */
    private String modifyName;
    /**
     * 更新时间
     */
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(int dealFlag) {
        this.dealFlag = dealFlag;
    }

    public String getDealFailReason() {
        return dealFailReason;
    }

    public void setDealFailReason(String dealFailReason) {
        this.dealFailReason = dealFailReason;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

package cn.htd.marketcenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员优惠券使用履历
 */
public class BuyerUseCouponLog implements Serializable {

	private static final long serialVersionUID = -224691833021518997L;

	private Long id;

	private String buyerCode;// 会员编码

	private String promotionId;

	private String levelCode;

	private String orderNo;// 订单号

	private String orderItemNo;// 订单行号

	private String buyerCouponCode;// 会员优惠券号

	private BigDecimal couponUsedAmount;// 券使用金额

	private String useType;// 用券类型

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo == null ? null : orderItemNo.trim();
	}

	public String getBuyerCouponCode() {
		return buyerCouponCode;
	}

	public void setBuyerCouponCode(String buyerCouponCode) {
		this.buyerCouponCode = buyerCouponCode == null ? null : buyerCouponCode.trim();
	}

	public BigDecimal getCouponUsedAmount() {
		return couponUsedAmount;
	}

	public void setCouponUsedAmount(BigDecimal couponUsedAmount) {
		this.couponUsedAmount = couponUsedAmount;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType == null ? null : useType.trim();
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
		this.createName = createName == null ? null : createName.trim();
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
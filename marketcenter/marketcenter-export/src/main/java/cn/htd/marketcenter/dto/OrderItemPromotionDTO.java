package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderItemPromotionDTO implements Serializable {

	private static final long serialVersionUID = 8570167778544878055L;

	/**
	 * 促销活动变动类型
	 */
	@NotEmpty(message = "会员促销活动处理类型为空")
	private String promoitionChangeType;
	/**
	 * 秒杀时的预占订单号
	 */
	private String seckillLockNo;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单行号
	 */
	private String orderItemNo;
	/**
	 * 买家编号
	 */
	@NotEmpty(message = "买家编号不能为空")
	private String buyerCode;
	/**
	 * 促销活动类型 1：优惠券，2:秒杀
	 */
	@NotEmpty(message = "促销活动类型不能为空")
	private String promotionType;
	/**
	 * 促销活动编码
	 */
	@NotEmpty(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 促销活动层级编码
	 */
	private String levelCode;
	/**
	 * 促销活动类型为优惠券时，会员优惠券编号
	 */
	private String couponCode;
	/**
	 * 商品购买数量
	 */
	private Integer quantity = new Integer(1);
	/**
	 * 商品行优惠金额
	 */
	private BigDecimal discountAmount = BigDecimal.ZERO;
	/**
	 * 操作人ID
	 */
	@NotNull(message = "操作人ID不能为空")
	private Long operaterId;
	/**
	 * 操作人名称
	 */
	@NotEmpty(message = "操作人名称不能为空")
	private String operaterName;

	/**
	 * 内部用参数使用Log数量
	 */
	private Long useLogCount;

	public String getPromoitionChangeType() {
		return promoitionChangeType;
	}

	public void setPromoitionChangeType(String promoitionChangeType) {
		this.promoitionChangeType = promoitionChangeType;
	}

	public String getSeckillLockNo() {
		return seckillLockNo == null ? "" : seckillLockNo.trim();
	}

	public void setSeckillLockNo(String seckillLockNo) {
		this.seckillLockNo = seckillLockNo;
	}

	public String getOrderNo() {
		return orderNo == null ? "" : orderNo.trim();
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
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

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}

	public String getOperaterName() {
		return operaterName;
	}

	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}

	public Long getUseLogCount() {
		return useLogCount;
	}

	public void setUseLogCount(Long useLogCount) {
		this.useLogCount = useLogCount;
	}
}

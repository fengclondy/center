package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderSeckillInfoDTO implements Serializable {

	private static final long serialVersionUID = 7110753315100037410L;

	/**
	 * 秒杀活动编码
	 */
	private String promotionId;

	/**
	 * 秒杀活动类型
	 */
	private String promotionType;

	/**
	 * 活动层级
	 */
	private String levelCode;

	/**
	 * 预占订单编号
	 */
	private String reOrderId;
	/**
	 * 商品秒杀价
	 */
	private BigDecimal skuTimelimitedPrice;

	/**
	 * 参与秒杀商品数量
	 */
	private Integer timelimitedSkuCount;

	/**
	 * 每人限秒数量
	 */
	private Integer timelimitedThreshold;

	/**
	 * 秒杀订单有效时间（单位：分钟）
	 */
	private Integer timelimitedValidInterval;

	/**
	 * @return the promotionId
	 */
	public String getPromotionId() {
		return promotionId;
	}

	/**
	 * @param promotionId
	 *            the promotionId to set
	 */
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the promotionType
	 */
	public String getPromotionType() {
		return promotionType;
	}

	/**
	 * @param promotionType
	 *            the promotionType to set
	 */
	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	/**
	 * @return the levelCode
	 */
	public String getLevelCode() {
		return levelCode;
	}

	/**
	 * @param levelCode
	 *            the levelCode to set
	 */
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	/**
	 * @return the reOrderId
	 */
	public String getReOrderId() {
		return reOrderId;
	}

	/**
	 * @param reOrderId
	 *            the reOrderId to set
	 */
	public void setReOrderId(String reOrderId) {
		this.reOrderId = reOrderId;
	}

	/**
	 * @return the skuTimelimitedPrice
	 */
	public BigDecimal getSkuTimelimitedPrice() {
		return skuTimelimitedPrice;
	}

	/**
	 * @param skuTimelimitedPrice
	 *            the skuTimelimitedPrice to set
	 */
	public void setSkuTimelimitedPrice(BigDecimal skuTimelimitedPrice) {
		this.skuTimelimitedPrice = skuTimelimitedPrice;
	}

	/**
	 * @return the timelimitedSkuCount
	 */
	public Integer getTimelimitedSkuCount() {
		return timelimitedSkuCount;
	}

	/**
	 * @param timelimitedSkuCount
	 *            the timelimitedSkuCount to set
	 */
	public void setTimelimitedSkuCount(Integer timelimitedSkuCount) {
		this.timelimitedSkuCount = timelimitedSkuCount;
	}

	/**
	 * @return the timelimitedThreshold
	 */
	public Integer getTimelimitedThreshold() {
		return timelimitedThreshold;
	}

	/**
	 * @param timelimitedThreshold
	 *            the timelimitedThreshold to set
	 */
	public void setTimelimitedThreshold(Integer timelimitedThreshold) {
		this.timelimitedThreshold = timelimitedThreshold;
	}

	/**
	 * @return the timelimitedValidInterval
	 */
	public Integer getTimelimitedValidInterval() {
		return timelimitedValidInterval;
	}

	/**
	 * @param timelimitedValidInterval
	 *            the timelimitedValidInterval to set
	 */
	public void setTimelimitedValidInterval(Integer timelimitedValidInterval) {
		this.timelimitedValidInterval = timelimitedValidInterval;
	}

}

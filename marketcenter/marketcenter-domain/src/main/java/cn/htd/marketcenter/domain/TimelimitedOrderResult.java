package cn.htd.marketcenter.domain;

/**
 * 秒杀订单数据
 */
public class TimelimitedOrderResult {
	/**
	 * 秒杀活动编码
	 */
	private String promotionId;
	/**
	 * 秒杀活动层级编码
	 */
	private String levelCode;
	/**
	 * 秒杀商品数量
	 */
	private Integer orderCount;

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

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

}
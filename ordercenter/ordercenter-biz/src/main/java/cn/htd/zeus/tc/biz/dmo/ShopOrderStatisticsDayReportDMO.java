package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class ShopOrderStatisticsDayReportDMO extends BaseDMO {

	private Integer statisticsId;

	private Long shopId;

	private Integer salesTime;

	private BigDecimal salesAmount;

	private Integer buyerCount;

	private Integer orderCount;

	private Integer statisticsGoodCount;

	private Integer payOrderCount;

	private Date statisticsTime;

	private Integer orderGoodsCount;

	private Integer salesGoodsCount;

	public Integer getStatisticsId() {
		return statisticsId;
	}

	public void setStatisticsId(Integer statisticsId) {
		this.statisticsId = statisticsId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getSalesTime() {
		return salesTime;
	}

	public void setSalesTime(Integer salesTime) {
		this.salesTime = salesTime;
	}

	public BigDecimal getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Integer getBuyerCount() {
		return buyerCount;
	}

	public void setBuyerCount(Integer buyerCount) {
		this.buyerCount = buyerCount;
	}

	public Integer getStatisticsGoodCount() {
		return statisticsGoodCount;
	}

	public void setStatisticsGoodCount(Integer statisticsGoodCount) {
		this.statisticsGoodCount = statisticsGoodCount;
	}

	public Integer getPayOrderCount() {
		return payOrderCount;
	}

	public void setPayOrderCount(Integer payOrderCount) {
		this.payOrderCount = payOrderCount;
	}

	public Date getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * @return the orderGoodsCount
	 */
	public Integer getOrderGoodsCount() {
		return orderGoodsCount;
	}

	/**
	 * @param orderGoodsCount
	 *            the orderGoodsCount to set
	 */
	public void setOrderGoodsCount(Integer orderGoodsCount) {
		this.orderGoodsCount = orderGoodsCount;
	}

	/**
	 * @return the salesGoodsCount
	 */
	public Integer getSalesGoodsCount() {
		return salesGoodsCount;
	}

	/**
	 * @param salesGoodsCount
	 *            the salesGoodsCount to set
	 */
	public void setSalesGoodsCount(Integer salesGoodsCount) {
		this.salesGoodsCount = salesGoodsCount;
	}

}
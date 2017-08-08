package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSkuAnalysisDMO {

	private long shopId;

	private String sellerCode;

	private int salesTime;

	private String skuCode;

	private String goodsName;

	private String itemCode;

	private BigDecimal salesAmount;

	private long salesGoodsCount;

	private long orderGoodsCount;

	private Date statisticsTime;

	/**
	 * @return the shopId
	 */
	public long getShopId() {
		return shopId;
	}

	/**
	 * @param shopId
	 *            the shopId to set
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the sellerCode
	 */
	public String getSellerCode() {
		return sellerCode;
	}

	/**
	 * @param sellerCode
	 *            the sellerCode to set
	 */
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	/**
	 * @return the salesTime
	 */
	public int getSalesTime() {
		return salesTime;
	}

	/**
	 * @param salesTime
	 *            the salesTime to set
	 */
	public void setSalesTime(int salesTime) {
		this.salesTime = salesTime;
	}

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode
	 *            the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the salesAmount
	 */
	public BigDecimal getSalesAmount() {
		return salesAmount;
	}

	/**
	 * @param salesAmount
	 *            the salesAmount to set
	 */
	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}

	/**
	 * @return the salesGoodsCount
	 */
	public long getSalesGoodsCount() {
		return salesGoodsCount;
	}

	/**
	 * @param salesGoodsCount
	 *            the salesGoodsCount to set
	 */
	public void setSalesGoodsCount(long salesGoodsCount) {
		this.salesGoodsCount = salesGoodsCount;
	}

	/**
	 * @return the orderGoodsCount
	 */
	public long getOrderGoodsCount() {
		return orderGoodsCount;
	}

	/**
	 * @param orderGoodsCount
	 *            the orderGoodsCount to set
	 */
	public void setOrderGoodsCount(long orderGoodsCount) {
		this.orderGoodsCount = orderGoodsCount;
	}

	/**
	 * @return the statisticsTime
	 */
	public Date getStatisticsTime() {
		return statisticsTime;
	}

	/**
	 * @param statisticsTime
	 *            the statisticsTime to set
	 */
	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

}

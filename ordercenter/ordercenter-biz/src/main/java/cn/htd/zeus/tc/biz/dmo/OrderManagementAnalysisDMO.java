package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderManagementAnalysisDMO {

	private long shopId;

	private BigDecimal payPriceTotal;

	private long buyPersonNum;

	private long tradeNum;

	private long payGoodsNum;

	private String sellerCode;

	private int salesTime;

	private int orderCount;

	private Date statisticsTime;

	/**
	 * @return the payPriceTotal
	 */
	public BigDecimal getPayPriceTotal() {
		return payPriceTotal;
	}

	/**
	 * @param payPriceTotal
	 *            the payPriceTotal to set
	 */
	public void setPayPriceTotal(BigDecimal payPriceTotal) {
		this.payPriceTotal = payPriceTotal;
	}

	/**
	 * @return the buyPersonNum
	 */
	public long getBuyPersonNum() {
		return buyPersonNum;
	}

	/**
	 * @param buyPersonNum
	 *            the buyPersonNum to set
	 */
	public void setBuyPersonNum(long buyPersonNum) {
		this.buyPersonNum = buyPersonNum;
	}

	/**
	 * @return the tradeNum
	 */
	public long getTradeNum() {
		return tradeNum;
	}

	/**
	 * @param tradeNum
	 *            the tradeNum to set
	 */
	public void setTradeNum(long tradeNum) {
		this.tradeNum = tradeNum;
	}

	/**
	 * @return the payGoodsNum
	 */
	public long getPayGoodsNum() {
		return payGoodsNum;
	}

	/**
	 * @param payGoodsNum
	 *            the payGoodsNum to set
	 */
	public void setPayGoodsNum(long payGoodsNum) {
		this.payGoodsNum = payGoodsNum;
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
	 * @return the orderCount
	 */
	public int getOrderCount() {
		return orderCount;
	}

	/**
	 * @param orderCount
	 *            the orderCount to set
	 */
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
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

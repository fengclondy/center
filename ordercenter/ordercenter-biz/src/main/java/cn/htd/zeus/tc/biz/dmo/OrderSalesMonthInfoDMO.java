package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class OrderSalesMonthInfoDMO extends GenericDMO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4566794863714404725L;

	private Integer salesMonthYear;

	private Long salesAmount;

	private Date countTime;

	private Long supperlierId;// 供应商ID

	private long shopId;

	private String sellerCode;

	public Integer getSalesMonthYear() {
		return salesMonthYear;
	}

	public void setSalesMonthYear(Integer salesMonthYear) {
		this.salesMonthYear = salesMonthYear;
	}

	public Long getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Long salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Date getCountTime() {
		return countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}

	public Long getSupperlierId() {
		return supperlierId;
	}

	public void setSupperlierId(Long supperlierId) {
		this.supperlierId = supperlierId;
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

}
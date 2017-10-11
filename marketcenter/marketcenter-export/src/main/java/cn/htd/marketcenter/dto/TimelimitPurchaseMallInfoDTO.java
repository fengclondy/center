package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TimelimitPurchaseMallInfoDTO extends TimelimitedInfoDTO implements
		Serializable, Comparable<TimelimitPurchaseMallInfoDTO> {

	private static final long serialVersionUID = -4893611882477342978L;

	/**
	 * 上架时间
	 */
	private Date startTime;
	/**
	 * 销售量
	 */
	private int salesVolume;
	/**
	 * 优惠力度
	 */
	private double preferentialStrength;
	/**
	 * 销售额（销量*销售价）
	 */
	private double salesVolumePrice;
	/**
	 * 价格
	 */
	private BigDecimal skuTimelimitedPrice;

	public BigDecimal getSkuTimelimitedPrice() {
		return skuTimelimitedPrice;
	}

	public void setSkuTimelimitedPrice(BigDecimal skuTimelimitedPrice) {
		this.skuTimelimitedPrice = skuTimelimitedPrice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public double getPreferentialStrength() {
		return preferentialStrength;
	}

	public void setPreferentialStrength(double preferentialStrength) {
		this.preferentialStrength = preferentialStrength;
	}

	public double getSalesVolumePrice() {
		return salesVolumePrice;
	}

	public void setSalesVolumePrice(double salesVolumePrice) {
		this.salesVolumePrice = salesVolumePrice;
	}

	@Override
	public int compareTo(TimelimitPurchaseMallInfoDTO o) {
		int diffSalesVolume = o.getSalesVolume() - this.salesVolume;
		int compareResult1 = Double.compare(o.getPreferentialStrength(),this.preferentialStrength);
		int compareResult2 = Double.compare(o.getSalesVolumePrice(),this.salesVolumePrice);
		int compareResult3 = Long.compare(o.getStartTime().getTime(),this.startTime.getTime());
		if(o.getPurchaseSort() != 0){
			if(o.getPurchaseSort() == 1){
				return diffSalesVolume;
			}else if(o.getPurchaseSort() == 2){
				return compareResult3;
			}else if(o.getPurchaseSort() == 3){
				return o.getSkuTimelimitedPrice().compareTo(this.skuTimelimitedPrice);
			}else if(o.getPurchaseSort() == 4){
				return this.skuTimelimitedPrice.compareTo(o.getSkuTimelimitedPrice());
			}
		}
		if (compareResult1 != 0) {
			return compareResult1;
		}
		if (diffSalesVolume != 0) {
			return diffSalesVolume;
		}
		if (compareResult2 != 0) {
			return compareResult2;
		}
		return compareResult3;
	}

}

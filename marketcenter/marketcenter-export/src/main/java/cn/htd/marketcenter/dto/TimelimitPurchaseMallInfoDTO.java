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
	private Date startTimeSort;
	/**
	 * 销售量
	 */
	private int salesVolumeSort;
	/**
	 * 优惠力度
	 */
	private double preferentialStrengthSort;
	/**
	 * 销售额（销量*销售价）
	 */
	private double salesVolumePriceSort;
	/**
	 * 价格
	 */
	private BigDecimal skuTimelimitedPriceSort;

	public Date getStartTimeSort() {
		return startTimeSort;
	}



	public void setStartTimeSort(Date startTimeSort) {
		this.startTimeSort = startTimeSort;
	}



	public int getSalesVolumeSort() {
		return salesVolumeSort;
	}



	public void setSalesVolumeSort(int salesVolumeSort) {
		this.salesVolumeSort = salesVolumeSort;
	}



	public double getPreferentialStrengthSort() {
		return preferentialStrengthSort;
	}



	public void setPreferentialStrengthSort(double preferentialStrengthSort) {
		this.preferentialStrengthSort = preferentialStrengthSort;
	}



	public double getSalesVolumePriceSort() {
		return salesVolumePriceSort;
	}



	public void setSalesVolumePriceSort(double salesVolumePriceSort) {
		this.salesVolumePriceSort = salesVolumePriceSort;
	}



	public BigDecimal getSkuTimelimitedPriceSort() {
		return skuTimelimitedPriceSort;
	}



	public void setSkuTimelimitedPriceSort(BigDecimal skuTimelimitedPriceSort) {
		this.skuTimelimitedPriceSort = skuTimelimitedPriceSort;
	}


	@Override
	public int compareTo(TimelimitPurchaseMallInfoDTO o) {
		int diffSalesVolume = Integer.compare(o.getSalesVolume(), this.salesVolumeSort);
		int compareResult1 = Double.compare(o.getPreferentialStrength(),this.preferentialStrengthSort);
		int compareResult2 = Double.compare(o.getSalesVolumePrice(),this.salesVolumePriceSort);
		int compareResult3 = Long.compare(this.startTimeSort.getTime(),o.getStartTime().getTime());
		if(o.getPurchaseSort() != 0){
			if(o.getPurchaseSort() == 1){
				if(diffSalesVolume != 0){
					return diffSalesVolume;
				}else{
					return compareResult3;
				}
			}else if(o.getPurchaseSort() == 2){
				return compareResult3;
			}else if(o.getPurchaseSort() == 3){
				int priceResult1 = o.getSkuTimelimitedPrice().compareTo(this.skuTimelimitedPriceSort);
				if(priceResult1 != 0){
					return priceResult1;
				}else{
					return compareResult3;
				}
			}else if(o.getPurchaseSort() == 4){
				int priceResult2 = this.skuTimelimitedPriceSort.compareTo(o.getSkuTimelimitedPrice());
				if(priceResult2 != 0){
					return priceResult2;
				}else{
					return compareResult3;
				}
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

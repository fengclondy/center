package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;

public class StockNewResultVoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059268899952428791L;

	private String areaId;// 配送地址ID

	private long skuId;//

	private int stockStateId;// 库存状态编号 33,39,40,36,34

	/*
	 * 库存状态描述 33 有货 现货-下单立即发货 39 有货 在途-正在内部配货，预计2~6天到达本仓库 40 有货 可配货-下单后从有货仓库配货
	 * 36 预订 34 无货
	 */
	private String stockStateDesc;

	/*
	 * 剩余数量 -1代表未知： 1. 返回真实数量 2. 返回-1，代表未知
	 */
	private int remainNum;

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId
	 *            the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the skuId
	 */
	public long getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the stockStateId
	 */
	public int getStockStateId() {
		return stockStateId;
	}

	/**
	 * @param stockStateId
	 *            the stockStateId to set
	 */
	public void setStockStateId(int stockStateId) {
		this.stockStateId = stockStateId;
	}

	/**
	 * @return the stockStateDesc
	 */
	public String getStockStateDesc() {
		return stockStateDesc;
	}

	/**
	 * @param stockStateDesc
	 *            the stockStateDesc to set
	 */
	public void setStockStateDesc(String stockStateDesc) {
		this.stockStateDesc = stockStateDesc;
	}

	/**
	 * @return the remainNum
	 */
	public int getRemainNum() {
		return remainNum;
	}

	/**
	 * @param remainNum
	 *            the remainNum to set
	 */
	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

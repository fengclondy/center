package cn.htd.marketcenter.dto;

import java.io.Serializable;

/**
 * 秒杀活动检索条件
 */
public class TimelimitedConditionDTO implements Serializable {

	private static final long serialVersionUID = 5210372509736403342L;
	/**
	 * 商品编码
	 */
	private String skuCode;
	/**
	 * 商品SKU名称
	 */
	private String skuName;
	/**
	 * 活动状态
	 */
	private String status;
	
	/**
	 * 供应商编码
	 */
	private String selleCode;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSelleCode() {
		return selleCode;
	}

	public void setSelleCode(String selleCode) {
		this.selleCode = selleCode;
	}
	
}

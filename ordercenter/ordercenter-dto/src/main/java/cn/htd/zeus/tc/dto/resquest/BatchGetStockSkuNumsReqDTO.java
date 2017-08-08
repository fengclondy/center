package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

public class BatchGetStockSkuNumsReqDTO extends GenricReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3571808014385783033L;

	private String skuId;

	private Long num;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}

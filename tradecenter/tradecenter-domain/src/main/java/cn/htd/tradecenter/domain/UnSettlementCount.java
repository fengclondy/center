package cn.htd.tradecenter.domain;

import java.io.Serializable;

public class UnSettlementCount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vendorType;//	//卖家类型 20:是内部，10是外部。

	private int count;//数量

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	
	

}

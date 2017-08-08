package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

public class JDCreateOrderSkuReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4223913636848315614L;
	
	private String skuId;
	
	private Integer num;//商品数量
	
	private Boolean bNeedAnnex;
	
	private Boolean bNeedGift;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Boolean getbNeedAnnex() {
		return bNeedAnnex;
	}

	public void setbNeedAnnex(Boolean bNeedAnnex) {
		this.bNeedAnnex = bNeedAnnex;
	}

	public Boolean getbNeedGift() {
		return bNeedGift;
	}

	public void setbNeedGift(Boolean bNeedGift) {
		this.bNeedGift = bNeedGift;
	}
	
}

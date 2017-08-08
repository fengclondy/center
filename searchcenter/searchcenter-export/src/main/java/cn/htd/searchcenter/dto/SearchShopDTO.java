package cn.htd.searchcenter.dto;

import java.io.Serializable;

public class SearchShopDTO implements Serializable {

	private static final long serialVersionUID = -6484619094488936688L;

	private Long shopId;// 店铺ID

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}


}

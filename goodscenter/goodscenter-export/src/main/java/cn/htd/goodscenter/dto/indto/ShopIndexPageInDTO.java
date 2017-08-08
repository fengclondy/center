package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;

public class ShopIndexPageInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -733133606042826897L;
	
	private Long sellerId;
	private Long shopId;
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	

}

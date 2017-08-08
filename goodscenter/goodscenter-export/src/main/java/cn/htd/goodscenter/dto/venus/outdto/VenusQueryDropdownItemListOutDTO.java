package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusQueryDropdownItemListOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911769317125771750L;
	private String itemName;
	private String skuCode;
	private String itemCode;
	private Long skuId;
	private String erpCode;
	private String spuCode;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	
}

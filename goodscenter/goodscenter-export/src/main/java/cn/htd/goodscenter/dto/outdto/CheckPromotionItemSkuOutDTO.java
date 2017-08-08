package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

public class CheckPromotionItemSkuOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976178926772721126L;
	private Long itemId;
	private Long skuId;
	private String itemName;
	private String skuCode;
	private String itemCode;
	private boolean isLegal;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isLegal() {
		return isLegal;
	}
	public void setLegal(boolean isLegal) {
		this.isLegal = isLegal;
	}
	
}

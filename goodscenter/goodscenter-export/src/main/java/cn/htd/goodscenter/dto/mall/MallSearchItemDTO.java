package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.htd.goodscenter.domain.ItemPicture;

public class MallSearchItemDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5681300388511446304L;
	private Long itemId;
	private ItemPicture firstPic;
	private String itemName;
	private Long supplierId;
	private String itemCode;
	private BigDecimal retailPrice;
	private String itemChannelCode;
	public ItemPicture getFirstPic() {
		return firstPic;
	}
	public void setFirstPic(ItemPicture firstPic) {
		this.firstPic = firstPic;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemChannelCode() {
		return itemChannelCode;
	}
	public void setItemChannelCode(String itemChannelCode) {
		this.itemChannelCode = itemChannelCode;
	}
	
}

package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

import cn.htd.goodscenter.domain.ItemPicture;

public class HotSellItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6584087905081345686L;
	
	private Long itemId;
	private String itemCode;
	private String itemName;
	private Long sellerId;
	private Long cid;
	private Long brand;
	private Long salesVolume;
	// 主图
	private ItemPicture firstPic;
	//商品skuId
	private Long skuId;
	private boolean isProdPlus;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	public Long getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Long salesVolume) {
		this.salesVolume = salesVolume;
	}
	public ItemPicture getFirstPic() {
		return firstPic;
	}
	public void setFirstPic(ItemPicture firstPic) {
		this.firstPic = firstPic;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public boolean isProdPlus() {
		return isProdPlus;
	}
	public void setProdPlus(boolean isProdPlus) {
		this.isProdPlus = isProdPlus;
	}
	
}

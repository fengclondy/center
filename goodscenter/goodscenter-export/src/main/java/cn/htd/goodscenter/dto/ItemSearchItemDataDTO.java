package cn.htd.goodscenter.dto;

import java.util.Date;

public class ItemSearchItemDataDTO {

	private String itemId;
	private String itemCode;
	private String itemName;
	private double price;
	private boolean isVBC;
	private int itemType; //0:秒杀 1:外部供应商 2:商品+ 3:包厢 4:区域
	private boolean isSalesWholeCountry;
	private int salesVolume;
	private String sellerId;
	private String sellerName;
	private String imgURL;
	private String shopId;
	private Date listtingTime;
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isVBC() {
		return isVBC;
	}

	public void setVBC(boolean isVBC) {
		this.isVBC = isVBC;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public boolean isSalesWholeCountry() {
		return isSalesWholeCountry;
	}

	public void setSalesWholeCountry(boolean isSalesWholeCountry) {
		this.isSalesWholeCountry = isSalesWholeCountry;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Date getListtingTime() {
		return listtingTime;
	}

	public void setListtingTime(Date listtingTime) {
		this.listtingTime = listtingTime;
	}
}

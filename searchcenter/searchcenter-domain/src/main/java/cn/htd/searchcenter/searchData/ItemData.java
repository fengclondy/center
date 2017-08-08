package cn.htd.searchcenter.searchData;

import java.util.Date;

public class ItemData {
	private String id;
	private String itemId;
	private String itemName;
	private String itemCode;
	private double price;
	private boolean isVBC;
	private int itemType;
	private boolean isSalesWholeCountry;
	private Integer salesVolume;
	private String sellerId;
	private String sellerName;
	private String imgURL;
	private String shopId;
	private Date listtingTime;
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Date getListtingTime() {
		return listtingTime;
	}

	public void setListtingTime(Date listtingTime) {
		this.listtingTime = listtingTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}

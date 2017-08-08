package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

public class SyncItemStockSearchOutDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7084249333718224803L;
	private Long sellerId;
	private String comName;
	private String itemName;
	private String skuCode;
	private String erpCode;
	private String firstCatName;
	private String secondCatName;
	private Long thirdCatId;
	private String thirdCatName;
	private String brandName;
	private String totalStock;
	private String spuCode;
	private Long spuId;
	
	public Long getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getFirstCatName() {
		return firstCatName;
	}
	public void setFirstCatName(String firstCatName) {
		this.firstCatName = firstCatName;
	}
	public String getSecondCatName() {
		return secondCatName;
	}
	public void setSecondCatName(String secondCatName) {
		this.secondCatName = secondCatName;
	}
	public String getThirdCatName() {
		return thirdCatName;
	}
	public void setThirdCatName(String thirdCatName) {
		this.thirdCatName = thirdCatName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(String totalStock) {
		this.totalStock = totalStock;
	}
	public Long getThirdCatId() {
		return thirdCatId;
	}
	public void setThirdCatId(Long thirdCatId) {
		this.thirdCatId = thirdCatId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
}

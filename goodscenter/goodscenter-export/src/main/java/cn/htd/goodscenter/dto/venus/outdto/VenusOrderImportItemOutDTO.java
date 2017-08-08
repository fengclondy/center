package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;
import java.util.List;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;

public class VenusOrderImportItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3030159741870268543L;
	private Long skuId;
	private String skuCode;
	private String itemCode;
	private String erpCode;
	private Long cid;
	//类目名称
	private String catName;
	private Long brandId;
	private String brandName;
	private String itemName;
	private String spuCode;
	private ItemSkuBasePriceDTO itemSkuBasePrice;
	private List<ItemSkuPublishInfo> itemSkuPublishInfo;
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
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public ItemSkuBasePriceDTO getItemSkuBasePrice() {
		return itemSkuBasePrice;
	}
	public void setItemSkuBasePrice(ItemSkuBasePriceDTO itemSkuBasePrice) {
		this.itemSkuBasePrice = itemSkuBasePrice;
	}
	
	public List<ItemSkuPublishInfo> getItemSkuPublishInfo() {
		return itemSkuPublishInfo;
	}
	public void setItemSkuPublishInfo(List<ItemSkuPublishInfo> itemSkuPublishInfo) {
		this.itemSkuPublishInfo = itemSkuPublishInfo;
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
	
}

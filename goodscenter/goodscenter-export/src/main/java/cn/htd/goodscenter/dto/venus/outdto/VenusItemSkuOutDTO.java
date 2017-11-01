package cn.htd.goodscenter.dto.venus.outdto;


import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thinkpad on 2016/12/30.
 */
public class VenusItemSkuOutDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long itemId;
    private String itemCode;
    private String itemName;
    private Long sellerId;
    private Long shopId;
    private Long skuId;
    private String skuCode;
    private String subTitle;
    private Integer skuType;
    private String ad;
    private String attributes;
    private String skuErpCode;
    private String eanCode;
    private Integer displayQuantity;
    private Integer mimQuantity;
    private Integer maxPurchaseQuantity;
    List<ItemSkuLadderPrice> itemSkuLadderPrices;
    List<ItemAttrDTO> itemAttr;
    private Integer reserveQuantity;
    
    private List<ItemPicture> itemPictureList;
	private List<ItemSkuPicture> itemSkuPictureList; // 商品详情页sku图片 【外部供应商商品sku图片】

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getSkuErpCode() {
        return skuErpCode;
    }

    public void setSkuErpCode(String skuErpCode) {
        this.skuErpCode = skuErpCode;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public Integer getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(Integer displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public Integer getMimQuantity() {
        return mimQuantity;
    }

    public void setMimQuantity(Integer mimQuantity) {
        this.mimQuantity = mimQuantity;
    }

    public Integer getMaxPurchaseQuantity() {
        return maxPurchaseQuantity;
    }

    public void setMaxPurchaseQuantity(Integer maxPurchaseQuantity) {
        this.maxPurchaseQuantity = maxPurchaseQuantity;
    }

    public List<ItemSkuLadderPrice> getItemSkuLadderPrices() {
        return itemSkuLadderPrices;
    }

    public void setItemSkuLadderPrices(List<ItemSkuLadderPrice> itemSkuLadderPrices) {
        this.itemSkuLadderPrices = itemSkuLadderPrices;
    }

    public List<ItemAttrDTO> getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(List<ItemAttrDTO> itemAttr) {
        this.itemAttr = itemAttr;
    }

	public Integer getReserveQuantity() {
		return reserveQuantity;
	}

	public void setReserveQuantity(Integer reserveQuantity) {
		this.reserveQuantity = reserveQuantity;
	}



	public List<ItemPicture> getItemPictureList() {
		return itemPictureList;
	}

	public void setItemPictureList(List<ItemPicture> itemPictureList) {
		this.itemPictureList = itemPictureList;
	}

	public List<ItemSkuPicture> getItemSkuPictureList() {
		return itemSkuPictureList;
	}

	public void setItemSkuPictureList(List<ItemSkuPicture> itemSkuPictureList) {
		this.itemSkuPictureList = itemSkuPictureList;
	}
    
}

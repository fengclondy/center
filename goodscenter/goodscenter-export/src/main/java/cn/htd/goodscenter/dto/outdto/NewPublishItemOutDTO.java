package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.htd.goodscenter.domain.ItemPicture;

public class NewPublishItemOutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7954239763188839366L;

	// 主图
	private ItemPicture firstPic;
	// 商品名称
	private String itemName;
	// 最小阶梯价
	private BigDecimal salePrice;
	//供应商id
	private Long supplierId;
	//三级类目id
	private Long thirdLevelCatId;
	//品牌id
	private Long brandId;
	//商品skuId
	private Long skuId;
	//商品编码
	private String itemCode;
	//
	private boolean isProdPlus;
	
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
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getThirdLevelCatId() {
		return thirdLevelCatId;
	}
	public void setThirdLevelCatId(Long thirdLevelCatId) {
		this.thirdLevelCatId = thirdLevelCatId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isProdPlus() {
		return isProdPlus;
	}
	public void setProdPlus(boolean isProdPlus) {
		this.isProdPlus = isProdPlus;
	}
	
}

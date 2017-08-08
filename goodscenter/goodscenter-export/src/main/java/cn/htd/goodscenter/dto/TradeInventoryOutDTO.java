package cn.htd.goodscenter.dto;

import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Description: [库存出参]
 * </p>
 */
public class TradeInventoryOutDTO implements Serializable {

	private static final long serialVersionUID = -2939079443858315000L;

	private Long skuId;// skuid
	private String skuCode;//sku编码
	private Integer totalInventory;// 实际库存量
	private BigDecimal marketPrice;// 市场价(item)
	private String itemName;// 商品名称(item)
	private Long cid;// 类目id
	private Long itemId; // 商品Id
	private String itemCode;// 商品编码
	private Integer itemStatus;// 商品状态(item),1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
								// 7： 申请解锁
//	private List<SellPriceDTO> areaPrices; // 阶梯价
	private List<SkuPictureDTO> skuPicture; // sku图片
	private List<ItemCatCascadeDTO> itemCatCascadeDTO; // 类目属性
	private List<ItemAttrDTO> itemAttr;// 商品属性（商品信息）
	private String attrSale;// 商品的销售属性
	private String attributes;// sku的销售属性集合：keyId:valueId
	private Integer skuStatus;// SKU 状态 1有效 2无效
	private String weightUnit;//单位
	private List<ItemSkuLadderPrice> itemSkuLadderPrices;//商品sku阶梯价格

	public String getAttrSale() {
		return attrSale;
	}

	public void setAttrSale(String attrSale) {
		this.attrSale = attrSale;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public List<ItemAttrDTO> getItemAttr() {
		return itemAttr;
	}

	public void setItemAttr(List<ItemAttrDTO> itemAttr) {
		this.itemAttr = itemAttr;
	}

	public List<ItemCatCascadeDTO> getItemCatCascadeDTO() {
		return itemCatCascadeDTO;
	}

	public void setItemCatCascadeDTO(List<ItemCatCascadeDTO> itemCatCascadeDTO) {
		this.itemCatCascadeDTO = itemCatCascadeDTO;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public List<SkuPictureDTO> getSkuPicture() {
		return skuPicture;
	}

	public void setSkuPicture(List<SkuPictureDTO> skuPicture) {
		this.skuPicture = skuPicture;
	}

//	public List<SellPriceDTO> getAreaPrices() {
//		return areaPrices;
//	}
//
//	public void setAreaPrices(List<SellPriceDTO> areaPrices) {
//		this.areaPrices = areaPrices;
//	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getTotalInventory() {
		return totalInventory;
	}

	public void setTotalInventory(Integer totalInventory) {
		this.totalInventory = totalInventory;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(Integer skuStatus) {
		this.skuStatus = skuStatus;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public List<ItemSkuLadderPrice> getItemSkuLadderPrices() {
		return itemSkuLadderPrices;
	}

	public void setItemSkuLadderPrices(List<ItemSkuLadderPrice> itemSkuLadderPrices) {
		this.itemSkuLadderPrices = itemSkuLadderPrices;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
}

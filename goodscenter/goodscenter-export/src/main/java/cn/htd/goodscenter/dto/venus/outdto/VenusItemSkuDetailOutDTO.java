package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;

public class VenusItemSkuDetailOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6870651246708988606L;
	//sku id
	private Long skuId;
	//item id
	private Long itemId;
	//商品编码
	private String itemCode;
	//一级目录ID
	private Long firstCatId;
	//一级目录名称
	private String firstCatName;
	//二级目录id
	private Long secondCatId;
	//二级目录名称
	private String secondCatName;
	//品类名称
	private String categoryName;
	//三级目录id
	private Long thirdCatId;
	//品牌名称
	private String brandName;
	private Long brandId;
	//型号
	private String modelType;
	//属性
	private String attributes;

	//类目属性(解析后的值)
	private Map<String, String[]> categoryAttrHandled;

	//商品名称
	private String itemName;
	//单位
	private String unit;
	//广告语（原卖点）
	private String ad;
	//毛重
	private String weight;
	//净重
	private String netWeight;
	//长
	private String length;
	//宽
	private String width;
	//高
	private String height;
	//税率
	private String taxRate;
	//颜色
	private String color;
	//商品产地
	private String origin;
	//图片
	private List<ItemPicture> pictures;
	//描述
	private ItemDescribe describe;
	//1：审核通过,2：审核驳回，3：待ERP上行库存及价格或外部商品品类价格待映射，4 未上架，5：已上架  6：已删除
	private Integer itemStatus;
	
    //状态变更原因
	private String statusChangeReason;

	// erpCode
	private String erpCode;

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<ItemPicture> getPictures() {
		return pictures;
	}
	public void setPictures(List<ItemPicture> pictures) {
		this.pictures = pictures;
	}
	public ItemDescribe getDescribe() {
		return describe;
	}
	public void setDescribe(ItemDescribe describe) {
		this.describe = describe;
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Integer getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}
	public Long getFirstCatId() {
		return firstCatId;
	}
	public void setFirstCatId(Long firstCatId) {
		this.firstCatId = firstCatId;
	}
	public Long getSecondCatId() {
		return secondCatId;
	}
	public void setSecondCatId(Long secondCatId) {
		this.secondCatId = secondCatId;
	}
	public Long getThirdCatId() {
		return thirdCatId;
	}
	public void setThirdCatId(Long thirdCatId) {
		this.thirdCatId = thirdCatId;
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
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getStatusChangeReason() {
		return statusChangeReason;
	}
	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}


	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public Map<String, String[]> getCategoryAttrHandled() {
		return categoryAttrHandled;
	}

	public void setCategoryAttrHandled(Map<String, String[]> categoryAttrHandled) {
		this.categoryAttrHandled = categoryAttrHandled;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}

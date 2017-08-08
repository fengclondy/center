package cn.htd.goodscenter.dto;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkuInfoDTO implements Serializable {

	private static final long serialVersionUID = -5919786885017397474L;

	private Long skuId;// SKU id
	private List<SkuPictureDTO> skuPics = new ArrayList<SkuPictureDTO>();// SKU图片URL
	private String attributes;// 销售属性组
	private List<ItemAttrDTO> skuSaleAttributes = new ArrayList<ItemAttrDTO>(); // sku销售属性
	private Long skuType;// sku 类型 sku 类型 1:主sku,2:非主sku
	private Integer skuInventory;// Sku库存

	private Long itemId;// 商品id
	private Integer itemStatus;// 商品状态(上架状态)
	private Long sellerId;// 商家id:无效
	private String ad;// 广告语,未使用！！
	private Integer skuStatus;// sku 状态,1:有效;2 无效
	private Date created;// 创建日期
	private Date modified;// 修改日期
	private Long shopId;// 店铺id
	private String erpStatus;//ERP下行状态
	private Date erpDownTime;//ERP下行时间
	private String erpErrorMsg;//ERP下行错误信息
	private String subTitle;//商品副标题
	private String skuErpCode;//商品SKU编码

	private String itemName;// 商品名称
	private Long cid;// 类目ID
	private String skuScope;// SKU评价评分

	private Integer hasPrice;// 是否显示价格 1：有价格；2：暂无报价
	private Long createId;//创建人ID
	private String createName;//创建人名称
	private Long modifyId;//更新人ID
	private String modifyName;//更新人名称
	private String skuCode;//商品sku编码，保存当前hybris系统中的HTDH_******类似的编码
	private String outerSkuId;//外接商品sku_id或者erp上行过来的sku
	private List<ItemSkuLadderPrice> skuLadderPrices;//阶梯价格
	private String eanCode;//EAN编码

	private List<ItemSkuPublishInfo> itemSkuPublishInfos;

	public List<ItemSkuPublishInfo> getItemSkuPublishInfos() {
		return itemSkuPublishInfos;
	}

	public void setItemSkuPublishInfos(List<ItemSkuPublishInfo> itemSkuPublishInfos) {
		this.itemSkuPublishInfos = itemSkuPublishInfos;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Long getSkuType() {
		return skuType;
	}

	public void setSkuType(Long skuType) {
		this.skuType = skuType;
	}

	public Integer getSkuInventory() {
		return skuInventory;
	}

	public void setSkuInventory(Integer skuInventory) {
		this.skuInventory = skuInventory;
	}

	public List<SkuPictureDTO> getSkuPics() {
		return skuPics;
	}

	public void setSkuPics(List<SkuPictureDTO> skuPics) {
		this.skuPics = skuPics;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(Integer skuStatus) {
		this.skuStatus = skuStatus;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSkuErpCode() {
		return skuErpCode;
	}

	public void setSkuErpCode(String skuErpCode) {
		this.skuErpCode = skuErpCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getSkuScope() {
		return skuScope;
	}

	public void setSkuScope(String skuScope) {
		this.skuScope = skuScope;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	public List<ItemSkuLadderPrice> getSkuLadderPrices() {
		return skuLadderPrices;
	}

	public void setSkuLadderPrices(List<ItemSkuLadderPrice> skuLadderPrices) {
		this.skuLadderPrices = skuLadderPrices;
	}

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}

	public List<ItemAttrDTO> getSkuSaleAttributes() {
		return skuSaleAttributes;
	}

	public void setSkuSaleAttributes(List<ItemAttrDTO> skuSaleAttributes) {
		this.skuSaleAttributes = skuSaleAttributes;
	}
}

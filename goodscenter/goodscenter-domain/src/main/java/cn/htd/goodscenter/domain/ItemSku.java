package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [SUK实体]
 * </p>
 */
public class ItemSku implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5172430541319744117L;
	private Long skuId;// sku id
	private Long itemId;// 商品id
	private String skuCode;// 商品sku编码，保存当前hybris系统中的HTDH_******类似的编码
	private Long sellerId;// 商家id
	private Long shopId;// 店铺id
	private String subTitle;// 商品副标题
	private Integer skuStatus;// sku 状态,1:有效;2:无效
	private Integer skuType;// sku 类型 1:主sku,2:非主sku
	private String ad;// 广告语,未使用！！
	private String attributes;// 销售属性集合：keyId:valueId
	private String skuErpCode;
	private String outerSkuId;// 外接商品sku_id或者erp上行过来的sku
	private String eanCode;//EAN编码
	private Date created;// 创建日期
	private Long createId;// 创建人ID
	private String createName;// 创建人名称
	private Date modified;// 修改日期
	private Long modifyId;// 更新人ID
	private String modifyName;// 更新人名称

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

	public String getEanCode() {
		return eanCode;
	}

	public void setEanCode(String eanCode) {
		this.eanCode = eanCode;
	}
}

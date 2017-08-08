package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemDraft implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2889781928217192275L;

	private Long itemDraftId;

    private Long itemId;

    private String itemName;

    private Long sellerId;

    private Long shopId;

    private Long cid;

    private Long brand;

    private String modelType;

    private String weightUnit;

    private BigDecimal taxRate;

    private BigDecimal weight;

    private BigDecimal netWeight;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private String ad;

    private String origin;

    private String attributes;

    private String attrSale;

    private Integer isSpu;

    private Long itemSpuId;

    private Date created;

    private Long createId;

    private String createName;

    private Date modified;

    private Long modifyId;

    private String modifyName;

    private String eanCode;
    
    private Integer verifyStatus;
    
    private String verifyName;
    
    //状态：0 待审核 1 审核通过 2 审核驳回
    private Integer status;

    public Long getItemDraftId() {
        return itemDraftId;
    }

    public void setItemDraftId(Long itemDraftId) {
        this.itemDraftId = itemDraftId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit == null ? null : weightUnit.trim();
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad == null ? null : ad.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes == null ? null : attributes.trim();
    }

    public String getAttrSale() {
        return attrSale;
    }

    public void setAttrSale(String attrSale) {
        this.attrSale = attrSale == null ? null : attrSale.trim();
    }

    public Integer getIsSpu() {
        return isSpu;
    }

    public void setIsSpu(Integer isSpu) {
        this.isSpu = isSpu;
    }

    public Long getItemSpuId() {
        return itemSpuId;
    }

    public void setItemSpuId(Long itemSpuId) {
        this.itemSpuId = itemSpuId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode == null ? null : eanCode.trim();
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
}
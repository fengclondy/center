package cn.htd.goodscenter.domain.spu;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemSpu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2875285832337619287L;

	private Long spuId;

    private String spuName;

    private String spuCode;

    private String itemCode;// 商品编码

    private String itemName;// 商品名称

    private Long categoryId;

    private Long brandId;

    private String categoryAttributes;

    private String itemQualification;

    private String unit;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

    private String modelType;

    private BigDecimal volume;

    private String origin;

    private String packingList;

    private String afterService;

    private String status;

    private String erpFirstCategoryCode;

    private String erpFiveCategoryCode;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private Integer wide;// 宽

    private Integer high;// 高

    private Integer length;// 长
    
    private Integer deleteFlag;
    
    private String erpCode;//erp编码
    
    private BigDecimal taxRate;// 税率

    public Integer getWide() {
        return wide;
    }

    public void setWide(Integer wide) {
        this.wide = wide;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName == null ? null : spuName.trim();
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode == null ? null : spuCode.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(String categoryAttributes) {
        this.categoryAttributes = categoryAttributes == null ? null : categoryAttributes.trim();
    }

    public String getItemQualification() {
        return itemQualification;
    }

    public void setItemQualification(String itemQualification) {
        this.itemQualification = itemQualification == null ? null : itemQualification.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList == null ? null : packingList.trim();
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService == null ? null : afterService.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErpFirstCategoryCode() {
        return erpFirstCategoryCode;
    }

    public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
        this.erpFirstCategoryCode = erpFirstCategoryCode == null ? null : erpFirstCategoryCode.trim();
    }

    public String getErpFiveCategoryCode() {
        return erpFiveCategoryCode;
    }

    public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
        this.erpFiveCategoryCode = erpFiveCategoryCode == null ? null : erpFiveCategoryCode.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
    
}
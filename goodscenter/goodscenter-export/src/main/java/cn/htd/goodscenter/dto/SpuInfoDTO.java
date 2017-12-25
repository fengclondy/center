package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lih on 2016/11/26.
 */
public class SpuInfoDTO implements Serializable {
    private static final long serialVersionUID = 5559658487629841495L;

    private Long spuId;

    private String spuName;// 商品模板名称

    private String spuCode;// 商品模板编码

    private Long firstCategoryId;// 一级品类ID

    private String firstCategoryName;// 一级品类NAME

    private Long secondCategoryId;// 二级品类ID

    private String secondCategoryName;// 二级品类NAME

    private Long categoryId;// 三级品类ID

    private String categoryName;// 三级品类NAME

    private String brandName;// 品牌名称

    private Long brandId;// 品牌编码

    private String categoryAttributes;// 类目属性

    //类目属性(解析后的值)
    private Map<String, String[]> categoryAttrHandled;

    private String itemQualification;// 商品参数

    private String unit;// 单位

    private BigDecimal grossWeight;// 毛重

    private BigDecimal netWeight;// 净重

    private String modelType;// 型号

    private BigDecimal volume;// 体积

    private String origin;// 商品产地

    private String erpFirstCategoryCode;// ERP一级类目编码

    private String erpFiveCategoryCode;// ERP五级类目编码

    private Integer wide;// 宽

    private Integer high;// 高

    private Integer length;// 长

    private BigDecimal taxRate;

    private String packingList;// 包装清单

    private String afterService;// 售后服务

    private String status;// 状态

    private Long desId;

    private String spuDesc;

    private List<ItemSpuPictureDTO> itemSpuPictureDTOList;


    private Long itemId;

    private String itemCode;// 商品编码

    private String itemName;// 商品名称

    private Long describeId;// 商品描述ID

    private Long pictureId;

    private String pictureUrl;

    private String pictureSize;

    private Byte isFirst;

    private Integer sortNum;

    private Byte deleteFlag;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

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
        this.spuName = spuName;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public String getFirstCategoryName() {
        return firstCategoryName;
    }

    public void setFirstCategoryName(String firstCategoryName) {
        this.firstCategoryName = firstCategoryName;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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
        this.categoryAttributes = categoryAttributes;
    }

    public String getItemQualification() {
        return itemQualification;
    }

    public void setItemQualification(String itemQualification) {
        this.itemQualification = itemQualification;
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
        this.modelType = modelType;
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
        this.origin = origin;
    }

    public String getErpFirstCategoryCode() {
        return erpFirstCategoryCode;
    }

    public void setErpFirstCategoryCode(String erpFirstCategoryCode) {
        this.erpFirstCategoryCode = erpFirstCategoryCode;
    }

    public String getErpFiveCategoryCode() {
        return erpFiveCategoryCode;
    }

    public void setErpFiveCategoryCode(String erpFiveCategoryCode) {
        this.erpFiveCategoryCode = erpFiveCategoryCode;
    }

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

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDesId() {
        return desId;
    }

    public void setDesId(Long desId) {
        this.desId = desId;
    }

    public String getSpuDesc() {
        return spuDesc;
    }

    public void setSpuDesc(String spuDesc) {
        this.spuDesc = spuDesc;
    }

    public List<ItemSpuPictureDTO> getItemSpuPictureDTOList() {
        return itemSpuPictureDTOList;
    }

    public void setItemSpuPictureDTOList(List<ItemSpuPictureDTO> itemSpuPictureDTOList) {
        this.itemSpuPictureDTOList = itemSpuPictureDTOList;
    }

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

    public Long getDescribeId() {
        return describeId;
    }

    public void setDescribeId(Long describeId) {
        this.describeId = describeId;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public Byte getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Byte isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
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
        this.modifyName = modifyName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Map<String, String[]> getCategoryAttrHandled() {
        return categoryAttrHandled;
    }

    public void setCategoryAttrHandled(Map<String, String[]> categoryAttrHandled) {
        this.categoryAttrHandled = categoryAttrHandled;
    }
}

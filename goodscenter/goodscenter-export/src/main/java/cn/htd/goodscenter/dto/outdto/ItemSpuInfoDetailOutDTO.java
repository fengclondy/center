package cn.htd.goodscenter.dto.outdto;

import cn.htd.goodscenter.dto.ItemSpuPictureDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品模板详情
 */
public class ItemSpuInfoDetailOutDTO implements Serializable {
    // 商品模板名称
    private String spuName;
    // 商品模板code
    private String spuCode;
    // 品牌ID
    private Long brandId;
    // 品牌名称
    private String brandName;
    // 型号
    private String modelType;
    // 毛重
    private BigDecimal grossWeight;
    // 净重
    private BigDecimal netWeight;
    // 税率
    private BigDecimal taxRate;
    // 长
    private Integer length;
    // 宽
    private Integer wide;
    // 高
    private Integer high;
    // 产地
    private String origin;
    // 单位
    private String unit;
    // 描述
    private String spuDesc;

    private Long spuId;

    // 一级类目id
    private Long firstCid;
    // 一级类目名称
    private String firstCName;
    // 二级类目id
    private Long secondCid;
    // 二级类目名称
    private String secondCName;
    // 三级类目id
    private Long cid;
    // 三级类目名称
    private String cName;

    private List<ItemSpuPictureDTO> itemSpuPictureList;

    /**
     * 商品详情介绍图片（从图文字解析来的url）  （注：已经是完整的图片url，不需要自己拼域名）
     */
    private List<String> itemSpuDescPictureList;

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpuDesc() {
        return spuDesc;
    }

    public void setSpuDesc(String spuDesc) {
        this.spuDesc = spuDesc;
    }

    public List<ItemSpuPictureDTO> getItemSpuPictureList() {
        return itemSpuPictureList;
    }

    public void setItemSpuPictureList(List<ItemSpuPictureDTO> itemSpuPictureList) {
        this.itemSpuPictureList = itemSpuPictureList;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getFirstCid() {
        return firstCid;
    }

    public void setFirstCid(Long firstCid) {
        this.firstCid = firstCid;
    }

    public String getFirstCName() {
        return firstCName;
    }

    public void setFirstCName(String firstCName) {
        this.firstCName = firstCName;
    }

    public Long getSecondCid() {
        return secondCid;
    }

    public void setSecondCid(Long secondCid) {
        this.secondCid = secondCid;
    }

    public String getSecondCName() {
        return secondCName;
    }

    public void setSecondCName(String secondCName) {
        this.secondCName = secondCName;
    }

    public List<String> getItemSpuDescPictureList() {
        return itemSpuDescPictureList;
    }

    public void setItemSpuDescPictureList(List<String> itemSpuDescPictureList) {
        this.itemSpuDescPictureList = itemSpuDescPictureList;
    }
}

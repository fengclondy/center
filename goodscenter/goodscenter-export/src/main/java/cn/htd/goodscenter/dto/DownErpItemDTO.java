package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by GZG on 2016/12/26.
 */
public class DownErpItemDTO implements Serializable{

    private static final long serialVersionUID = 2233283761682649834L;
    private String productERPCode;//商品内码(生成主键)
    private String brandCode;//品牌代码
    private String brandName;//品牌名称
    private String categoryCode;//分类代码
    private String categoryName;//分类名称
    private String chargeUnit;//计价单位
    private String marque;//商品型号
    private String productSpecifications;//商品规格
    private String SKU;//SKU
    private String productName;//商品名称
    private String outputRate;//销项税率
    private String productColorCode;//商品颜色代码
    private String productColorName;//商品颜色
    private String registrar;//登记人
    private Date registrationDate;//登记日期
    private String classABC;//ABC分类
    private String origin;//产地
    private String qualityGrade;//质量等级
    private String modifier;//修改人
    private Date modificationDate;//商品颜色代码
    private String functionIntroduction;//商品功能简介
    private String productType;//商品类型
    private String exfactoryPrice;//出厂价
    private String lastPurchasePrice;//最后进价
    private String priceTag;//限价标记
    private String validTags;//1有效    2无效（不允许进货及预售）0无效（不允许进货及销售），为空填1
    private String incomeTaxRates;//进项税率，为空填0.17
    private String machineCodeMark;//机器码标记，为空填0
    private String manyCodeLabel;//多码标记，为空填0
    private String commodityCommission;//商品提成(导购员工资项目)，为空填0
    private String highestPrice;//最高供价，为空填0
    private String retailPrice;//最低零售价，为空填0
    private String lowestDistributionPrice ;//最低分销价，为空填0
    private String pricing;//定价，为空填0
    private String packingContent;//包装含量，为空填1
    private String whetherOil;//0：非油品 1：油品，为空填0
    private String findustry;//产业大类序号，为空填1
    private int isUpdateFlag;//是否更新，更新：1，新增：0

    public String getProductERPCode() {
        return productERPCode;
    }

    public void setProductERPCode(String productERPCode) {
        this.productERPCode = productERPCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(String productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOutputRate() {
        return outputRate;
    }

    public void setOutputRate(String outputRate) {
        this.outputRate = outputRate;
    }

    public String getProductColorCode() {
        return productColorCode;
    }

    public void setProductColorCode(String productColorCode) {
        this.productColorCode = productColorCode;
    }

    public String getProductColorName() {
        return productColorName;
    }

    public void setProductColorName(String productColorName) {
        this.productColorName = productColorName;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getClassABC() {
        return classABC;
    }

    public void setClassABC(String classABC) {
        this.classABC = classABC;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getQualityGrade() {
        return qualityGrade;
    }

    public void setQualityGrade(String qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getFunctionIntroduction() {
        return functionIntroduction;
    }

    public void setFunctionIntroduction(String functionIntroduction) {
        this.functionIntroduction = functionIntroduction;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getExfactoryPrice() {
        return exfactoryPrice;
    }

    public void setExfactoryPrice(String exfactoryPrice) {
        this.exfactoryPrice = exfactoryPrice;
    }

    public String getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(String lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public String getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(String priceTag) {
        this.priceTag = priceTag;
    }

    public String getValidTags() {
        return validTags;
    }

    public void setValidTags(String validTags) {
        this.validTags = validTags;
    }

    public String getIncomeTaxRates() {
        return incomeTaxRates;
    }

    public void setIncomeTaxRates(String incomeTaxRates) {
        this.incomeTaxRates = incomeTaxRates;
    }

    public String getMachineCodeMark() {
        return machineCodeMark;
    }

    public void setMachineCodeMark(String machineCodeMark) {
        this.machineCodeMark = machineCodeMark;
    }

    public String getManyCodeLabel() {
        return manyCodeLabel;
    }

    public void setManyCodeLabel(String manyCodeLabel) {
        this.manyCodeLabel = manyCodeLabel;
    }

    public String getCommodityCommission() {
        return commodityCommission;
    }

    public void setCommodityCommission(String commodityCommission) {
        this.commodityCommission = commodityCommission;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getLowestDistributionPrice() {
        return lowestDistributionPrice;
    }

    public void setLowestDistributionPrice(String lowestDistributionPrice) {
        this.lowestDistributionPrice = lowestDistributionPrice;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getPackingContent() {
        return packingContent;
    }

    public void setPackingContent(String packingContent) {
        this.packingContent = packingContent;
    }

    public String getWhetherOil() {
        return whetherOil;
    }

    public void setWhetherOil(String whetherOil) {
        this.whetherOil = whetherOil;
    }

    public String getFindustry() {
        return findustry;
    }

    public void setFindustry(String findustry) {
        this.findustry = findustry;
    }

    public int getIsUpdateFlag() {
        return isUpdateFlag;
    }

    public void setIsUpdateFlag(int isUpdateFlag) {
        this.isUpdateFlag = isUpdateFlag;
    }
}

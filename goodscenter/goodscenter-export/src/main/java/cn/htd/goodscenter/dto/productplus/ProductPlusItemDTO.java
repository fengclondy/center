package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * 商品+商品DTO
 * @author chenakng
 */
public class ProductPlusItemDTO implements Serializable {
    /** item **/
    @NotNull(message = "itemId不能为空")
    private Long itemId;// 商品id
    private String itemName;// 商品名称
    private String itemCode;
    private Long cid;// 三级类目ID
    private String categoryName; // 品类名称 一级类目>>二级类目>>三级类目
    private Long brandId;// 品牌
    private String brandName;// 品牌名称
    private String productChannelCode;// 渠道编码
    private String productChannelName;// 渠道名称
    private Integer itemStatus;
    private String itemStatusStr;
    private String outerItemStatus;// 外部商品状态 外部商品状态 1：未上架，2：已上架
    private Integer isPreSale;// 是否预售
    private String preSaleStarttime;// 预售开始时间 yyyy-MM-dd
    private String preSaleEndtime;// 预售结束时间 yyyy-MM-dd
    private String ad;// 广告语
    private Integer hasVipPrice;// 0：无vip价格； 1：有vip价格； null:全部

    /** sku **/
    @NotNull(message = "skuId不能为空")
    private Long skuId; // skuid
    private String skuCode;// 商品编码
    private String outerSkuId;// 外部商品编码

    /** 价格 **/
    private BigDecimal costPrice; // 外部供货价
    private BigDecimal saleLimitedPrice; // 外部销售价
    @NotNull(message = "商城销售价不能为空")
    private BigDecimal areaSalePrice; // 商城销售价
    @NotNull(message = "价格浮动比例不能为空")
    private BigDecimal priceFloatingRatio; //价格浮动比例
    @NotNull(message = "零售价不能为空")
    private BigDecimal retailPrice; // 零售价
    @NotNull(message = "零售价格浮动比例不能为空")
    private BigDecimal retailPriceFloatingRatio; // 零售价格浮动比例
    @NotNull(message = "佣金比例不能为空")
    private BigDecimal commissionRatio; // 佣金比例
    private String vipPriceFloatingRatio;
    private BigDecimal vipPrice;

    /** 卖家 **/
    private Long sellerId;

    private Long shopId;

    /** 类目 **/
    // 一级类目id
    private Long firstCategoryId;
    // 二级类目Id
    private Long secondCategoryId;
    // 三级类目参数
    private Long[] categoryParam;
    //修改商品上架时间 by zhangxiaolong 为0时更新下架时间，为1时修改上架时间
    private Integer updateShelfTimeFlag;
    private Date listtingTime;// 上架时间

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
        this.itemName = itemName;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getProductChannelCode() {
        return productChannelCode;
    }

    public void setProductChannelCode(String productChannelCode) {
        this.productChannelCode = productChannelCode;
    }

    public String getProductChannelName() {
        return productChannelName;
    }

    public void setProductChannelName(String productChannelName) {
        this.productChannelName = productChannelName;
    }

    public String getOuterItemStatus() {
        return outerItemStatus;
    }

    public void setOuterItemStatus(String outerItemStatus) {
        this.outerItemStatus = outerItemStatus;
    }

    public Integer getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Integer isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getPreSaleStarttime() {
        return preSaleStarttime;
    }

    public void setPreSaleStarttime(String preSaleStarttime) {
        this.preSaleStarttime = preSaleStarttime;
    }

    public String getPreSaleEndtime() {
        return preSaleEndtime;
    }

    public void setPreSaleEndtime(String preSaleEndtime) {
        this.preSaleEndtime = preSaleEndtime;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
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

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSaleLimitedPrice() {
        return saleLimitedPrice;
    }

    public void setSaleLimitedPrice(BigDecimal saleLimitedPrice) {
        this.saleLimitedPrice = saleLimitedPrice;
    }

    public BigDecimal getPriceFloatingRatio() {
        return priceFloatingRatio;
    }

    public void setPriceFloatingRatio(BigDecimal priceFloatingRatio) {
        this.priceFloatingRatio = priceFloatingRatio;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getRetailPriceFloatingRatio() {
        return retailPriceFloatingRatio;
    }

    public void setRetailPriceFloatingRatio(BigDecimal retailPriceFloatingRatio) {
        this.retailPriceFloatingRatio = retailPriceFloatingRatio;
    }

    public BigDecimal getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(BigDecimal commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getVipPriceFloatingRatio() {
        return vipPriceFloatingRatio;
    }

    public void setVipPriceFloatingRatio(String vipPriceFloatingRatio) {
        this.vipPriceFloatingRatio = vipPriceFloatingRatio;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getAreaSalePrice() {
        return areaSalePrice;
    }

    public void setAreaSalePrice(BigDecimal areaSalePrice) {
        this.areaSalePrice = areaSalePrice;
    }

    public String getItemStatusStr() {
        return itemStatusStr;
    }

    public void setItemStatusStr(String itemStatusStr) {
        this.itemStatusStr = itemStatusStr;
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

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Long[] getCategoryParam() {
        return categoryParam;
    }

    public void setCategoryParam(Long[] categoryParam) {
        this.categoryParam = categoryParam;
    }

    public Integer getHasVipPrice() {
        return hasVipPrice;
    }

    public void setHasVipPrice(Integer hasVipPrice) {
        this.hasVipPrice = hasVipPrice;
    }

    @Override
    public String toString() {
        return "ProductPlusItemDTO{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", cid=" + cid +
                ", categoryName='" + categoryName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", productChannelCode='" + productChannelCode + '\'' +
                ", productChannelName='" + productChannelName + '\'' +
                ", itemStatus=" + itemStatus +
                ", itemStatusStr='" + itemStatusStr + '\'' +
                ", outerItemStatus='" + outerItemStatus + '\'' +
                ", isPreSale=" + isPreSale +
                ", preSaleStarttime='" + preSaleStarttime + '\'' +
                ", preSaleEndtime='" + preSaleEndtime + '\'' +
                ", ad='" + ad + '\'' +
                ", hasVipPrice=" + hasVipPrice +
                ", skuId=" + skuId +
                ", skuCode='" + skuCode + '\'' +
                ", outerSkuId='" + outerSkuId + '\'' +
                ", costPrice=" + costPrice +
                ", saleLimitedPrice=" + saleLimitedPrice +
                ", areaSalePrice=" + areaSalePrice +
                ", priceFloatingRatio=" + priceFloatingRatio +
                ", retailPrice=" + retailPrice +
                ", retailPriceFloatingRatio=" + retailPriceFloatingRatio +
                ", commissionRatio=" + commissionRatio +
                ", vipPriceFloatingRatio=" + vipPriceFloatingRatio +
                ", vipPrice=" + vipPrice +
                ", sellerId=" + sellerId +
                ", shopId=" + shopId +
                ", firstCategoryId=" + firstCategoryId +
                ", secondCategoryId=" + secondCategoryId +
                ", categoryParam=" + Arrays.toString(categoryParam) +
                '}';
    }

    public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getUpdateShelfTimeFlag() {
		return updateShelfTimeFlag;
	}

	public void setUpdateShelfTimeFlag(Integer updateShelfTimeFlag) {
		this.updateShelfTimeFlag = updateShelfTimeFlag;
	}

	public Date getListtingTime() {
		return listtingTime;
	}

	public void setListtingTime(Date listtingTime) {
		this.listtingTime = listtingTime;
	}
    
}

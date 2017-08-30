package cn.htd.promotion.cpc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价发起信息表
 * @author xuxuejiao
 *
 */
public class BuyerLaunchBargainInfoDMO{

	private Integer id;
	
	private String bargainCode;//砍价发起编号
	
	private String promotionId;//促销活动编码
	
	private String levelCode;//层级编码
	
	private String buyerCode;//会员编码
	
	private String buyerName;//会员名称
	
	private String buyerTelephone;//会员电话号码
	
	private String goodsPicture;//商品图片
	
	private String goodsName;//商品名称
	
	private BigDecimal goodsCostPrice;//商品原价
	
	private BigDecimal goodsFloorPrice;//商品底价
	
	private Date launchTime;//发起时间
	
	private Date bargainOverTime;//砍完时间
	
	private Integer isBargainOver;//是否砍完  0：没有砍完 1：砍完   2：已售完
	
	private BigDecimal goodsCurrentPrice;//商品当前价格
	
	private Integer bargainPersonNum;//帮忙砍价人数
	
	private BigDecimal surplusPrice;//还剩多少价没砍
	
	private Integer createId;//创建人ID
	
	private String createName;//创建人名称
	
	private Date createTime;//创建时间
	
	private Integer modifyId;//更新人ID
	
	private String modifyName;//更新人名称
	
	private Date modifyTime;//更新时间
	
	private Integer templateFlagD;//模板类型

	public Integer getTemplateFlagD() {
		return templateFlagD;
	}

	public void setTemplateFlagD(Integer templateFlagD) {
		this.templateFlagD = templateFlagD;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBargainCode() {
		return bargainCode;
	}

	public void setBargainCode(String bargainCode) {
		this.bargainCode = bargainCode;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerTelephone() {
		return buyerTelephone;
	}

	public void setBuyerTelephone(String buyerTelephone) {
		this.buyerTelephone = buyerTelephone;
	}

	public String getGoodsPicture() {
		return goodsPicture;
	}

	public void setGoodsPicture(String goodsPicture) {
		this.goodsPicture = goodsPicture;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsCostPrice() {
		return goodsCostPrice;
	}

	public void setGoodsCostPrice(BigDecimal goodsCostPrice) {
		this.goodsCostPrice = goodsCostPrice;
	}

	public BigDecimal getGoodsFloorPrice() {
		return goodsFloorPrice;
	}

	public void setGoodsFloorPrice(BigDecimal goodsFloorPrice) {
		this.goodsFloorPrice = goodsFloorPrice;
	}

	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	public Date getBargainOverTime() {
		return bargainOverTime;
	}

	public void setBargainOverTime(Date bargainOverTime) {
		this.bargainOverTime = bargainOverTime;
	}

	public Integer getIsBargainOver() {
		return isBargainOver;
	}

	public void setIsBargainOver(Integer isBargainOver) {
		this.isBargainOver = isBargainOver;
	}

	public BigDecimal getGoodsCurrentPrice() {
		return goodsCurrentPrice;
	}

	public void setGoodsCurrentPrice(BigDecimal goodsCurrentPrice) {
		this.goodsCurrentPrice = goodsCurrentPrice;
	}

	public Integer getBargainPersonNum() {
		return bargainPersonNum;
	}

	public void setBargainPersonNum(Integer bargainPersonNum) {
		this.bargainPersonNum = bargainPersonNum;
	}

	public BigDecimal getSurplusPrice() {
		return surplusPrice;
	}

	public void setSurplusPrice(BigDecimal surplusPrice) {
		this.surplusPrice = surplusPrice;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
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

	public Integer getModifyId() {
		return modifyId;
	}

	public void setModifyId(Integer modifyId) {
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

}

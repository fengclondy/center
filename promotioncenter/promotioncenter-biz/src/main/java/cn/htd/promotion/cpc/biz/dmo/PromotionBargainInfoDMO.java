package cn.htd.promotion.cpc.biz.dmo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价活动信息表
 * @author admin
 *
 */
public class PromotionBargainInfoDMO{

	private Integer bargainId;//砍价ID
	
	private String promotionId;//促销活动编码
	
	private String levelCode;//层级编码
	
	private String goodsPicture;//商品图片
	
	private String goodsName;//商品名称
	
	private BigDecimal goodsCostPrice;//商品原价
	
	private BigDecimal goodsFloorPrice;//商品底价
	
	private Integer partakeTimes;//参与砍价的人数
	
	private Integer goodsNum;//参砍商品数量
	
	private Integer createId;//创建人ID
	
	private String createName;//创建人名称
	
	private Date createTime;//创建时间
	
	private Integer modifyId;//更新人ID
	
	private String modifyName;//更新人名称
	
	private Date modifyTime;//更新时间
	
	private String promotionDesc;//宣传标语
	
	private String sellerName;//店铺名称
	
	private String contactName;//联系人电话
	
	private String contactTelphone;//联系电话
	
	private String contactAddress;//联系地址
	
	private Date effectiveTime;//活动开始有效期
	
	private Date invalidTime;//活动结束有效期
	
	private Date eachStartTimeD;//活动开始时间
	
	private Date eachEndTimeD;//活动结束时间
	
	private Date offlineStartTimeD;//到店购买开始时间
	
	private Date offlineEndTimeD;//到店购买结束时间
	
	private Date launchTime;//发起时间
	
	private Date bargainOverTime;//砍完时间
	
	private Integer isBargainOver;//是否砍完  0：没有砍完 1：砍完   2：已售完
	
	private BigDecimal goodsCurrentPrice;//商品当前价格
	
	private Integer bargainPersonNum;//帮忙砍价人数
	
	private BigDecimal surplusPrice;//还剩多少价没砍
	
	private BigDecimal hasBargainPrice;//已经砍得价格
	
	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public BigDecimal getHasBargainPrice() {
		return hasBargainPrice;
	}

	public void setHasBargainPrice(BigDecimal hasBargainPrice) {
		this.hasBargainPrice = hasBargainPrice;
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

	public Date getOfflineStartTimeD() {
		return offlineStartTimeD;
	}

	public void setOfflineStartTimeD(Date offlineStartTimeD) {
		this.offlineStartTimeD = offlineStartTimeD;
	}

	public Date getOfflineEndTimeD() {
		return offlineEndTimeD;
	}

	public void setOfflineEndTimeD(Date offlineEndTimeD) {
		this.offlineEndTimeD = offlineEndTimeD;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelphone() {
		return contactTelphone;
	}

	public void setContactTelphone(String contactTelphone) {
		this.contactTelphone = contactTelphone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Date getEachStartTimeD() {
		return eachStartTimeD;
	}

	public void setEachStartTimeD(Date eachStartTimeD) {
		this.eachStartTimeD = eachStartTimeD;
	}

	public Date getEachEndTimeD() {
		return eachEndTimeD;
	}

	public void setEachEndTimeD(Date eachEndTimeD) {
		this.eachEndTimeD = eachEndTimeD;
	}

	public Integer getBargainId() {
		return bargainId;
	}

	public void setBargainId(Integer bargainId) {
		this.bargainId = bargainId;
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

	public Integer getPartakeTimes() {
		return partakeTimes;
	}

	public void setPartakeTimes(Integer partakeTimes) {
		this.partakeTimes = partakeTimes;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
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

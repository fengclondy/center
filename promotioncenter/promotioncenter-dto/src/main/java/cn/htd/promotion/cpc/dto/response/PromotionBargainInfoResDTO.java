package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 砍价活动信息表
 * @author xxj
 *
 */
public class PromotionBargainInfoResDTO implements Serializable{

	private static final long serialVersionUID = 8605548926689874138L;

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
	
	private String promotionDescribe;//宣传标语
	
	private String sellerName;//店铺名称
	
	private String contactName;//联系人电话
	
	private String contactTelphone;//联系电话
	
	private String contactAddress;//联系地址
	
	private Date eachStartTime;//活动开始时间
	
	private Date eachEndTime;//活动结束时间
	
	private Date offlineStartTime;//到店购买开始时间
	
	private Date offlineEndTime;//到店购买结束时间
	
	private Date launchTime;//发起时间
	
	private Date bargainOverTime;//砍完时间
	
	private Integer isBargainOver;//是否砍完  0：没有砍完 1：砍完   2：已售完
	
	private BigDecimal goodsCurrentPrice;//商品当前价格
	
	private Integer bargainPersonNum;//帮忙砍价人数
	
	private BigDecimal surplusPrice;//还剩多少价没砍
	
	private BigDecimal hasBargainPrice;//已经砍得价
	
	private List<BuyerBargainRecordResDTO> buyerBargainRecordList;//砍价记录

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

	public Date getOfflineStartTime() {
		return offlineStartTime;
	}

	public void setOfflineStartTime(Date offlineStartTime) {
		this.offlineStartTime = offlineStartTime;
	}

	public Date getOfflineEndTime() {
		return offlineEndTime;
	}

	public void setOfflineEndTime(Date offlineEndTime) {
		this.offlineEndTime = offlineEndTime;
	}

	public String getPromotionDescribe() {
		return promotionDescribe;
	}

	public void setPromotionDescribe(String promotionDescribe) {
		this.promotionDescribe = promotionDescribe;
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

	public Date getEachStartTime() {
		return eachStartTime;
	}

	public void setEachStartTime(Date eachStartTime) {
		this.eachStartTime = eachStartTime;
	}

	public Date getEachEndTime() {
		return eachEndTime;
	}

	public void setEachEndTime(Date eachEndTime) {
		this.eachEndTime = eachEndTime;
	}

	public List<BuyerBargainRecordResDTO> getBuyerBargainRecordList() {
		return buyerBargainRecordList;
	}

	public void setBuyerBargainRecordList(List<BuyerBargainRecordResDTO> buyerBargainRecordList) {
		this.buyerBargainRecordList = buyerBargainRecordList;
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

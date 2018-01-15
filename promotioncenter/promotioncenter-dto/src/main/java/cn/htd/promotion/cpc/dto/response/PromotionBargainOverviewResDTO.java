package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

import java.math.BigDecimal;
public class PromotionBargainOverviewResDTO implements Serializable{
	private static final long serialVersionUID = 8789694479515897752L;

	private String promotionId;//活动编码
	
	private String goodsName;//商品名称
	
	private BigDecimal goodsCostPrice;//商品原价
	
	private BigDecimal goodsFloorPrice;//商品底价
	
	private Integer partakeTimes;//参与砍价的人数
	
	private Integer launchTimes;//已发起砍价数量
	
	private Integer overTimes;//已砍商品数量
	
	private Integer surplusTimes;//剩余商品数量
	
	private Integer goodsNum; //参砍数量

	private int goodsBuysers; //参加帮砍人数

	private String levelCode;	//层级编码

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
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

	public Integer getLaunchTimes() {
		return launchTimes;
	}

	public void setLaunchTimes(Integer launchTimes) {
		this.launchTimes = launchTimes;
	}

	public Integer getOverTimes() {
		return overTimes;
	}

	public void setOverTimes(Integer overTimes) {
		this.overTimes = overTimes;
	}

	public Integer getSurplusTimes() {
		return surplusTimes;
	}

	public void setSurplusTimes(Integer surplusTimes) {
		this.surplusTimes = surplusTimes;
	}

	public int getGoodsBuysers() {
		return goodsBuysers;
	}

	public void setGoodsBuysers(int goodsBuysers) {
		this.goodsBuysers = goodsBuysers;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
}
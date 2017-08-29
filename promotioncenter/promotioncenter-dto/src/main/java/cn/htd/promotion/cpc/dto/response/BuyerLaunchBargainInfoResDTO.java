package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 砍价发起信息表
 * @author xuxuejiao
 *
 */
public class BuyerLaunchBargainInfoResDTO implements Serializable{

	private static final long serialVersionUID = -906744366850879596L;

	private Integer id;
	
	private String bargainCode;//砍价发起编号
	
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;//促销活动编码
	
	@NotBlank(message = "促销活动层级编码不能为空")
	private String levelCode;//层级编码
	
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;//会员编码
	
	@NotBlank(message = "会员名称不能为空")
	private String buyerName;//会员名称
	
	@NotBlank(message = "会员头像不能为空")
	private String headSculptureURL; //会员头像
	
	@NotBlank(message = "会员电话号码不能为空")
	private String buyerTelephone;//会员电话号码
	
	@NotBlank(message = "商品图片不能为空")
	private String goodsPicture;//商品图片
	
	@NotBlank(message = "商品名称不能为空")
	private String goodsName;//商品名称
	
	@NotNull(message = "商品原价不能为空")
	private BigDecimal goodsCostPrice;//商品原价
	
	@NotNull(message = "商品低价不能为空")
	private BigDecimal goodsFloorPrice;//商品底价
	
	@NotNull(message = "参与砍价的人数不能为空")
	@Min(value = 1, message = "参与砍价的人数必须大于0")
	private Integer partakeTimes;//参与砍价的人数
	
	@NotNull(message = "参砍商品数量不能为空")
	@Min(value = 1, message = "参砍商品数量必须大于0")
	private Integer goodsNum;//参砍商品数量
	
	private Date launchTime;//发起时间
	
	private Date bargainOverTime;//砍完时间
	
	private Integer isBargainOver;//是否砍完  0：没有砍完 1：砍完   2：已售完
	
	private BigDecimal goodsCurrentPrice;//商品当前价格
	
	private Integer bargainPersonNum;//帮忙砍价人数
	
	private BigDecimal surplusPrice;//还剩多少价没砍
	
	private BigDecimal hasBargainPrice;//已经看的价格
	
	@NotNull(message = "创建人id不能为空")
	private Integer createId;//创建人ID
	
	@NotNull(message = "创建人名称不能为空")
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

	public BigDecimal getHasBargainPrice() {
		return hasBargainPrice;
	}

	public void setHasBargainPrice(BigDecimal hasBargainPrice) {
		this.hasBargainPrice = hasBargainPrice;
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

	public String getHeadSculptureURL() {
		return headSculptureURL;
	}

	public void setHeadSculptureURL(String headSculptureURL) {
		this.headSculptureURL = headSculptureURL;
	}
}

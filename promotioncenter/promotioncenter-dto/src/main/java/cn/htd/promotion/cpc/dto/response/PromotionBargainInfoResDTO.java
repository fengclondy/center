package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import java.util.List;

/**
 * 砍价活动信息表
 * @author xxj
 *
 */
public class PromotionBargainInfoResDTO extends PromotionAccumulatyDTO implements Serializable{

	private static final long serialVersionUID = 8605548926689874138L;

	private String bargainId;//砍价ID
	
	@NotBlank(message = "商品图片不能为空")
	private String goodsPicture;//商品图片
	
	@NotBlank(message = "商品名称不能为空")
	private String goodsName;//商品名称
	
	@NotNull(message = "商品原价不能为空")
	@Min(value = 0, message = "商品原价必须大于0")
	private BigDecimal goodsCostPrice;//商品原价
	
	@NotNull(message = "商品底价不能为空")
	@Min(value = 0, message = "商品底价必须大于0")
	private BigDecimal goodsFloorPrice;//商品底价
	
	@NotNull(message = "参与砍价的人数不能为空")
	@Min(value = 1, message = "参与砍价的人数必须大于0")
	private Integer partakeTimes;//参与砍价的人数
	
	@NotNull(message = "参砍商品数量不能为空")
	@Min(value = 1, message = "参砍商品数量必须大于0")
	private Integer goodsNum;//参砍商品数量
	
	private Integer virtualQuantity;//汇掌柜端虚拟显示人数增加
	
	private String promotionDesc;//宣传标语
	
	private String sellerNameD;//店铺名称
	
	private String contactNameD;//联系人电话
	
	private String contactTelphoneD;//联系电话
	
	private String contactAddressD;//联系地址
	
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
	
	private BigDecimal hasBargainPrice;//已经砍得价
	
	private List<BuyerBargainRecordResDTO> buyerBargainRecordList;//砍价记录
	
	private String promotionSlogan;//宣传语
	
	private List<String> sloganList;//宣传语集合
	
	private String upFlag; //上架标记 1:保存暂不上架  默认为空，表示立即上架
	
	private Integer templateFlagD;//模板类型
	
	private String buyerCode;
	
	private String opendId;
	
	private String showStatusD; //上下架状态
	
	private String bargainCodeD;//砍价编码
	
	private String previewFlag;//是否超级老板预览进入
	
	private String statusD;//活动状态
	
	public String getPreviewFlag() {
		return previewFlag;
	}

	public void setPreviewFlag(String previewFlag) {
		this.previewFlag = previewFlag;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getOpendId() {
		return opendId;
	}

	public void setOpendId(String opendId) {
		this.opendId = opendId;
	}

	public Integer getTemplateFlagD() {
		return templateFlagD;
	}

	public void setTemplateFlagD(Integer templateFlagD) {
		this.templateFlagD = templateFlagD;
	}

	public String getUpFlag() {
		return upFlag;
	}

	public void setUpFlag(String upFlag) {
		this.upFlag = upFlag;
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

	public String getSellerNameD() {
		return sellerNameD;
	}

	public void setSellerNameD(String sellerNameD) {
		this.sellerNameD = sellerNameD;
	}

	public String getContactNameD() {
		return contactNameD;
	}

	public void setContactNameD(String contactNameD) {
		this.contactNameD = contactNameD;
	}

	public String getContactTelphoneD() {
		return contactTelphoneD;
	}

	public void setContactTelphoneD(String contactTelphoneD) {
		this.contactTelphoneD = contactTelphoneD;
	}

	public String getContactAddressD() {
		return contactAddressD;
	}

	public void setContactAddressD(String contactAddressD) {
		this.contactAddressD = contactAddressD;
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

	public List<BuyerBargainRecordResDTO> getBuyerBargainRecordList() {
		return buyerBargainRecordList;
	}

	public void setBuyerBargainRecordList(List<BuyerBargainRecordResDTO> buyerBargainRecordList) {
		this.buyerBargainRecordList = buyerBargainRecordList;
	}

	public Integer getVirtualQuantity() {
		return virtualQuantity;
	}

	public void setVirtualQuantity(Integer virtualQuantity) {
		this.virtualQuantity = virtualQuantity;
	}

	public String getPromotionSlogan() {
		return promotionSlogan;
	}

	public void setPromotionSlogan(String promotionSlogan) {
		this.promotionSlogan = promotionSlogan;
	}

	public String getBargainId() {
		return bargainId;
	}

	public void setBargainId(String bargainId) {
		this.bargainId = bargainId;
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

	public List<String> getSloganList() {
		return sloganList;
	}

	public void setSloganList(List<String> sloganList) {
		this.sloganList = sloganList;
	}

	public String getShowStatusD() {
		return showStatusD;
	}

	public void setShowStatusD(String showStatusD) {
		this.showStatusD = showStatusD;
	}

	public String getBargainCodeD() {
		return bargainCodeD;
	}

	public void setBargainCodeD(String bargainCodeD) {
		this.bargainCodeD = bargainCodeD;
	}

	public String getStatusD() {
		return statusD;
	}

	public void setStatusD(String statusD) {
		this.statusD = statusD;
	}
}

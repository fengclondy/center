package cn.htd.searchcenter.dump.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopSearchData implements Serializable {

	private static final long serialVersionUID = 8737941431210274666L;

	private Long shopId;// 店铺id
	private String shopName;// 店铺名称
	private Long sellerId;// 卖家id
	private Integer status;// 店铺建新状态;1是申请，2是通过，3是驳回， 4是平台关闭，5是开通
	private String shopUrl;// 店铺域名
	private String logoUrl;// 店铺logourl
	private String keyword;// 关键字
	private String introduce;// 店铺介绍
	private String cids;
	private String sellerName;
	private String sellerType;
	private String locationAddress;
	private String mainSell;// 店铺主营
	private Date passTime;// 开通时间
	private Date endTime;// 终止时间
	private Integer modifyStatus;// 店铺信息修改状态，1为待审核，2为驳回，3为修改通过
	private Integer runStatus;// 店铺运行状态（只能允许卖家操作，默认为不开启），1表示卖家开启铺店，2表示卖家关闭店铺

	private String createId;// 申请时间开始
	private String modifyId;// 申请时间开始
	private String createName;// 申请时间开始
	private String modifyName;// 申请时间开始
	private Date createTime;// 申请时间结束
	private Date modifyTime;// 申请时间结束

	private Integer shopType;// 店铺类型 1 品牌商 2经销商
	private Integer businessType; // 经营类型 1自有品牌 2 代理品牌
	private String disclaimer;// 免责声明
	private String shopQQ;

	public String getShopQQ() {
		return shopQQ;
	}

	public void setShopQQ(String shopQQ) {
		this.shopQQ = shopQQ;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getMainSell() {
		return mainSell;
	}

	public void setMainSell(String mainSell) {
		this.mainSell = mainSell;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(Integer modifyStatus) {
		this.modifyStatus = modifyStatus;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getCids() {
		return cids;
	}

	public void setCids(String cids) {
		this.cids = cids;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
}

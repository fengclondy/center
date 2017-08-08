package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopDTO implements Serializable {

	private static final long serialVersionUID = 8737941431210274666L;
	private Long shopId;// 店铺id
	private String shopName;// 店铺名称
	private Long sellerId;// 卖家id
	private String sellerName;//买家姓名
	private String status;// 店铺建新状态;1是申请，2是通过，3是驳回， 4是平台关闭，5是开通
	private String[] statuss;// 店铺建新状态;1是申请，2是通过，3是驳回， 4是平台关闭，5是开通
	private String shopUrl="";// 店铺域名
	private String logoUrl="";// 店铺logourl
	private String keyword="";// 关键字
	private String introduce="";// 店铺介绍
	private Long verifyId=0l; //审核信息ID
	private Date createTime;// 申请时间
	private Date modifyTime;// 更新时间
	private Date passTime;// 开通时间
	private Date endTime;// 终止时间
	private String createName;  //创建人
	private Long createId; //创建人id
	private String modifyName; //修改人名称
	private Long modifyId; //修改人id
	private Date createdstr;// 申请时间开始
	private Date createdend;// 申请时间结束
	private Date modifiedstr;// 更新时间开始
	private Date modifiedend;// 更新时间结束
	private Date passTimeBegin; // 开通时间开始
	private Date passTimeEnd; // 开通时间结束
	private Long cid;// 类目id
	private String cStatus;// 类目状态
	private Integer collation = 1; // 1 修改时间升序 2 修改时间降序 3 评分升序 4评分降序 5销量升序 6销量降序
	private String shopType;// 店铺类型 1 品牌商 2经销商
	private String businessType; // 经营类型 1自有品牌 2 代理品牌
	private String disclaimer;// 免责声明
	private String bannerUrl="";//店铺横幅


	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getPassTimeBegin() {
		return passTimeBegin;
	}

	public void setPassTimeBegin(Date passTimeBegin) {
		this.passTimeBegin = passTimeBegin;
	}

	public Date getPassTimeEnd() {
		return passTimeEnd;
	}

	public void setPassTimeEnd(Date passTimeEnd) {
		this.passTimeEnd = passTimeEnd;
	}



	public Integer getCollation() {
		return collation;
	}

	public void setCollation(Integer collation) {
		this.collation = collation;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public Date getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(Date createdstr) {
		this.createdstr = createdstr;
	}

	public Date getCreatedend() {
		return createdend;
	}

	public void setCreatedend(Date createdend) {
		this.createdend = createdend;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long value) {
		this.shopId = value;
	}

	public Date getModifiedstr() {
		return modifiedstr;
	}

	public void setModifiedstr(Date modifiedstr) {
		this.modifiedstr = modifiedstr;
	}

	public Date getModifiedend() {
		return modifiedend;
	}

	public void setModifiedend(Date modifiedend) {
		this.modifiedend = modifiedend;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String value) {
		this.shopName = value;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long value) {
		this.sellerId = value;
	}



	public String getShopUrl() {
		return this.shopUrl;
	}

	public void setShopUrl(String value) {
		this.shopUrl = value;
	}

	public String getLogoUrl() {
		return this.logoUrl;
	}

	public void setLogoUrl(String value) {
		this.logoUrl = value;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String value) {
		this.keyword = value;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String value) {
		this.introduce = value;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public Date getPassTime() {
		return this.passTime;
	}

	public void setPassTime(Date value) {
		this.passTime = value;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date value) {
		this.endTime = value;
	}

	public String getStatus() {
		return status;
	}

	public String[] getStatuss() {
		return statuss;
	}


	public void setStatus(String status) {

		this.status = status;
	}

	public void setStatuss(String[] statuss) {
		this.statuss = statuss;
	}


	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}


	public Long getVerifyId() {
		return verifyId;
	}

	public String getCreateName() {
		return createName;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
}

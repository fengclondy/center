package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.htd.membercenter.domain.VerifyDetailInfo;

public class MemberBaseInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 会员ID
	private String companyLeagalPersionFlag;// 法人/自然人标记 0：未设置，1：法人，2：自然人
	private Integer isBuyer;// 是否是买家
	private Integer isSeller;// 是否是商家
	private Integer canMallLogin;// 是否登陆商城
	private Integer hasGuaranteeLicense; // 是否有担保证明
	private Integer hasBusinessLicense; // 是否有营业执照
	private Date registTime; // 注册时间
	private Integer isCenterStore; // 是否中心店
	private Date upgradeCenterStoreTime; // 升级为中心店时间
	private String sellerType; // 商家类型 1:内部供应商，2:外部供应商'
	private Integer isGeneration; // 是否是二代
	private String industryCategory; // 发展行业
	private Integer isDiffIndustry; // 是否异业
	private String contactName; // 联系人姓名
	private String contactMobile; // 联系人手机号码
	private String contactEmail; // 联系人邮箱
	private String contactIdcard; // 联系人身份证号
	private String contactPicSrc; // 联系人身份证电子版图片地址
	private String contactPicBackSrc; // 联系人身份证电子版图片地址(反面)
	private Integer isPhoneAuthenticated; // 是否手机号已验证
	private Integer isRealNameAuthenticated; // 是否实名验证标记
	private String cooperateVendor; // 合作供应商
	private String registFrom; // 注册来源
	private String promotionPerson; // 推广人编号
	private String status; // 状态 空白：审核中未成为正式会员，0：无效，1：有效
	private Long createId; // 创建人ID
	private String createName; // 创建人名称
	private Date createTime; // 创建时间
	private Long modifyId; // 更新人ID
	private String modifyName; // 更新人名称
	private Date modifyTime; // 更新时间
	private String locationProvince;// 所在地-省
	private String locationCity;// 所在地-市
	private String locationCounty;// 所在地-区
	private String locationTown;// 所在地-镇
	private String locationDetail;// 所在地-详细
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人手机号码
	private String buyerSellerType;// 会员/商家类型 1：会员，2：商家
	private Date startDate;// 注册开始时间
	private Date endDate;// 注册结束时间
	private String mallAccount;// 商城账号
	private String infoType;// 会员状态信息类型
	private String custType;// 客户类别
	private String curBelongManagerName;
	private Date becomeMemberTime;// 成为会员时间

	private Long belongSellerId;// 归属商家
	private Long curBelongSellerId;// 当前归属商家

	private Date birthday;// 生日
	private String buyerBusinessLicenseId;// 会员营业执照号码
	private String buyerFeature;// y
	private String companyCode;// 公司编码，金力代码
	private String invoiceAddress;// 发票地址
	private String businessLicenseId;// 营业执照号
	private List<Long> ids;
	private List<Long> curBelongCompanyIds;
	private List<Long> belongCompanyIds;
	private List<VerifyDetailInfo> verDtoList;
	private String remark;
	private String syncKey;
	private Date upgradeSellerTime;
	private String realNameStatus;// 实名认证状态
	private String accountNo;// 支付账号
	private String locationAddr;// 详细地址
	private String bindId;// 银行卡绑定id

	private Long companyId;

	private Long verifyId;

	private String afterChange;

	private String beforeChange;

	private String businessType;// 企业类型，1：个体户，2：企业用户
	private String recommenderCode;// 推荐人编码
	
	private String managerStatus;//超级经理人展示状态
	
	private String parentComCode;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public List<Long> getCurBelongCompanyIds() {
		return curBelongCompanyIds;
	}

	public void setCurBelongCompanyIds(List<Long> curBelongCompanyIds) {
		this.curBelongCompanyIds = curBelongCompanyIds;
	}

	public List<Long> getBelongCompanyIds() {
		return belongCompanyIds;
	}

	public void setBelongCompanyIds(List<Long> belongCompanyIds) {
		this.belongCompanyIds = belongCompanyIds;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCurBelongManagerName() {
		return curBelongManagerName;
	}

	public void setCurBelongManagerName(String curBelongManagerName) {
		this.curBelongManagerName = curBelongManagerName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}

	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}

	public String getArtificialPersonPicSrc() {
		return artificialPersonPicSrc;
	}

	public void setArtificialPersonPicSrc(String artificialPersonPicSrc) {
		this.artificialPersonPicSrc = artificialPersonPicSrc;
	}

	public String getArtificialPersonIdcardPicSrc() {
		return artificialPersonIdcardPicSrc;
	}

	public void setArtificialPersonIdcardPicSrc(String artificialPersonIdcardPicSrc) {
		this.artificialPersonIdcardPicSrc = artificialPersonIdcardPicSrc;
	}

	public String getArtificialPersonPicBackSrc() {
		return artificialPersonPicBackSrc;
	}

	public void setArtificialPersonPicBackSrc(String artificialPersonPicBackSrc) {
		this.artificialPersonPicBackSrc = artificialPersonPicBackSrc;
	}

	private String verifyStatus;// 审核状态/系统同步状态 1：待审核，2：已通过，3：被驳回
	private String cooperateVerifyStatus;// 供应商审核状态/系统同步状态 1：待审核，2：已通过，3：被驳回
	private String belongCompanyName;// 所属公司
	private Long belongCompanyId;// 所属公司
	private String curBelongCompanyName;// 当前所属公司
	private Long curBelongCompanyId;// 当前所属公司
	private String companyName;// 公司名称
	private String memberType;// 会员状态 1：非会员，3：担保会员 ，2：正式会员
	private String accountType;// 会员账号类型
	private String memberCode;// 会员编码

	public Long getBelongCompanyId() {
		return belongCompanyId;
	}

	public void setBelongCompanyId(Long belongCompanyId) {
		this.belongCompanyId = belongCompanyId;
	}

	public Long getCurBelongCompanyId() {
		return curBelongCompanyId;
	}

	public void setCurBelongCompanyId(Long curBelongCompanyId) {
		this.curBelongCompanyId = curBelongCompanyId;
	}

	private String belongManagerName;// 归属客户经理
	private String curBelongManagerId;// 当前归属客户经理id
	private String buyerGrade;// 会员等级
	private String artificialPersonIdcard;// 身份证号
	private String artificialPersonPicSrc;// 身份证图片地址
	private String artificialPersonIdcardPicSrc;// 手持身份证图片地址
	private String artificialPersonPicBackSrc;// 身份证反面
	private String JFCode;// JL编码
	private String buyerGuaranteeLicensePicSrc;// 担保证明照片地址
	private String buyerBusinessLicensePicSrc;// 营业执照照片地址
	private String memberVerifyStatus;// 会员审核状态 0：全部、
	// 1：待运营审核、2：待供应商审核、3：审核不通过、4：审核通过

	private String belongManagerId;

	public String getJFCode() {
		return JFCode;
	}

	public void setJFCode(String jFCode) {
		JFCode = jFCode;
	}

	public String getBuyerGuaranteeLicensePicSrc() {
		return buyerGuaranteeLicensePicSrc;
	}

	public void setBuyerGuaranteeLicensePicSrc(String buyerGuaranteeLicensePicSrc) {
		this.buyerGuaranteeLicensePicSrc = buyerGuaranteeLicensePicSrc;
	}

	public String getBuyerBusinessLicensePicSrc() {
		return buyerBusinessLicensePicSrc;
	}

	public void setBuyerBusinessLicensePicSrc(String buyerBusinessLicensePicSrc) {
		this.buyerBusinessLicensePicSrc = buyerBusinessLicensePicSrc;
	}

	public String getBelongCompanyName() {
		return belongCompanyName;
	}

	public void setBelongCompanyName(String belongCompanyName) {
		this.belongCompanyName = belongCompanyName;
	}

	public String getCurBelongCompanyName() {
		return curBelongCompanyName;
	}

	public void setCurBelongCompanyName(String curBelongCompanyName) {
		this.curBelongCompanyName = curBelongCompanyName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getLocationProvince() {
		return locationProvince;
	}

	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getLocationCounty() {
		return locationCounty;
	}

	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}

	public String getLocationTown() {
		return locationTown;
	}

	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyLeagalPersionFlag() {
		return companyLeagalPersionFlag;
	}

	public void setCompanyLeagalPersionFlag(String companyLeagalPersionFlag) {
		this.companyLeagalPersionFlag = companyLeagalPersionFlag;
	}

	public Integer getIsBuyer() {
		return isBuyer;
	}

	public void setIsBuyer(Integer isBuyer) {
		this.isBuyer = isBuyer;
	}

	public Integer getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(Integer isSeller) {
		this.isSeller = isSeller;
	}

	public Integer getCanMallLogin() {
		return canMallLogin;
	}

	public void setCanMallLogin(Integer canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	public Integer getHasGuaranteeLicense() {
		return hasGuaranteeLicense;
	}

	public void setHasGuaranteeLicense(Integer hasGuaranteeLicense) {
		this.hasGuaranteeLicense = hasGuaranteeLicense;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Integer getIsCenterStore() {
		return isCenterStore;
	}

	public void setIsCenterStore(Integer isCenterStore) {
		this.isCenterStore = isCenterStore;
	}

	public Date getUpgradeCenterStoreTime() {
		return upgradeCenterStoreTime;
	}

	public void setUpgradeCenterStoreTime(Date upgradeCenterStoreTime) {
		this.upgradeCenterStoreTime = upgradeCenterStoreTime;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public Integer getIsGeneration() {
		return isGeneration;
	}

	public void setIsGeneration(Integer isGeneration) {
		this.isGeneration = isGeneration;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public Integer getIsDiffIndustry() {
		return isDiffIndustry;
	}

	public void setIsDiffIndustry(Integer isDiffIndustry) {
		this.isDiffIndustry = isDiffIndustry;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactIdcard() {
		return contactIdcard;
	}

	public void setContactIdcard(String contactIdcard) {
		this.contactIdcard = contactIdcard;
	}

	public String getContactPicSrc() {
		return contactPicSrc;
	}

	public void setContactPicSrc(String contactPicSrc) {
		this.contactPicSrc = contactPicSrc;
	}

	public String getContactPicBackSrc() {
		return contactPicBackSrc;
	}

	public void setContactPicBackSrc(String contactPicBackSrc) {
		this.contactPicBackSrc = contactPicBackSrc;
	}

	public Integer getIsPhoneAuthenticated() {
		return isPhoneAuthenticated;
	}

	public void setIsPhoneAuthenticated(Integer isPhoneAuthenticated) {
		this.isPhoneAuthenticated = isPhoneAuthenticated;
	}

	public Integer getIsRealNameAuthenticated() {
		return isRealNameAuthenticated;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public void setIsRealNameAuthenticated(Integer isRealNameAuthenticated) {
		this.isRealNameAuthenticated = isRealNameAuthenticated;
	}

	public String getCooperateVendor() {
		return cooperateVendor;
	}

	public void setCooperateVendor(String cooperateVendor) {
		this.cooperateVendor = cooperateVendor;
	}

	public String getRegistFrom() {
		return registFrom;
	}

	public void setRegistFrom(String registFrom) {
		this.registFrom = registFrom;
	}

	public String getPromotionPerson() {
		return promotionPerson;
	}

	public void setPromotionPerson(String promotionPerson) {
		this.promotionPerson = promotionPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
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

	public String getBuyerSellerType() {
		return buyerSellerType;
	}

	public void setBuyerSellerType(String buyerSellerType) {
		this.buyerSellerType = buyerSellerType;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getBelongManagerName() {
		return belongManagerName;
	}

	public void setBelongManagerName(String belongManagerName) {
		this.belongManagerName = belongManagerName;
	}

	public String getBelongManagerId() {
		return belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	/**
	 * @return the hasBusinessLicense
	 */
	public Integer getHasBusinessLicense() {
		return hasBusinessLicense;
	}

	/**
	 * @param hasBusinessLicense
	 *            the hasBusinessLicense to set
	 */
	public void setHasBusinessLicense(Integer hasBusinessLicense) {
		this.hasBusinessLicense = hasBusinessLicense;
	}

	/**
	 * @return the memberVerifyStatus
	 */
	public String getMemberVerifyStatus() {
		return memberVerifyStatus;
	}

	/**
	 * @param memberVerifyStatus
	 *            the memberVerifyStatus to set
	 */
	public void setMemberVerifyStatus(String memberVerifyStatus) {
		this.memberVerifyStatus = memberVerifyStatus;
	}

	/**
	 * @return the mallAccount
	 */
	public String getMallAccount() {
		return mallAccount;
	}

	/**
	 * @param mallAccount
	 *            the mallAccount to set
	 */
	public void setMallAccount(String mallAccount) {
		this.mallAccount = mallAccount;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the buyerBusinessLicenseId
	 */
	public String getBuyerBusinessLicenseId() {
		return buyerBusinessLicenseId;
	}

	/**
	 * @param buyerBusinessLicenseId
	 *            the buyerBusinessLicenseId to set
	 */
	public void setBuyerBusinessLicenseId(String buyerBusinessLicenseId) {
		this.buyerBusinessLicenseId = buyerBusinessLicenseId;
	}

	/**
	 * @return the buyerFeature
	 */
	public String getBuyerFeature() {
		return buyerFeature;
	}

	/**
	 * @param buyerFeature
	 *            the buyerFeature to set
	 */
	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the invoiceAddress
	 */
	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	/**
	 * @param invoiceAddress
	 *            the invoiceAddress to set
	 */
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	/**
	 * @return the businessLicenseId
	 */
	public String getBusinessLicenseId() {
		return businessLicenseId;
	}

	/**
	 * @param businessLicenseId
	 *            the businessLicenseId to set
	 */
	public void setBusinessLicenseId(String businessLicenseId) {
		this.businessLicenseId = businessLicenseId;
	}

	/**
	 * @return the verDtoList
	 */
	public List<VerifyDetailInfo> getVerDtoList() {
		return verDtoList;
	}

	/**
	 * @param verDtoList
	 *            the verDtoList to set
	 */
	public void setVerDtoList(List<VerifyDetailInfo> verDtoList) {
		this.verDtoList = verDtoList;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the cooperateVerifyStatus
	 */
	public String getCooperateVerifyStatus() {
		return cooperateVerifyStatus;
	}

	/**
	 * @param cooperateVerifyStatus
	 *            the cooperateVerifyStatus to set
	 */
	public void setCooperateVerifyStatus(String cooperateVerifyStatus) {
		this.cooperateVerifyStatus = cooperateVerifyStatus;
	}

	/**
	 * @return the infoType
	 */
	public String getInfoType() {
		return infoType;
	}

	/**
	 * @param infoType
	 *            the infoType to set
	 */
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	/**
	 * @return the verifyId
	 */
	public Long getVerifyId() {
		return verifyId;
	}

	/**
	 * @param verifyId
	 *            the verifyId to set
	 */
	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	/**
	 * @return the syncKey
	 */
	public String getSyncKey() {
		return syncKey;
	}

	/**
	 * @param syncKey
	 *            the syncKey to set
	 */
	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the becomeMemberTime
	 */
	public Date getBecomeMemberTime() {
		return becomeMemberTime;
	}

	/**
	 * @param becomeMemberTime
	 *            the becomeMemberTime to set
	 */
	public void setBecomeMemberTime(Date becomeMemberTime) {
		this.becomeMemberTime = becomeMemberTime;
	}

	/**
	 * @return the belongSellerId
	 */
	public Long getBelongSellerId() {
		return belongSellerId;
	}

	/**
	 * @param belongSellerId
	 *            the belongSellerId to set
	 */
	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
	}

	/**
	 * @return the curBelongSellerId
	 */
	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	/**
	 * @param curBelongSellerId
	 *            the curBelongSellerId to set
	 */
	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	/**
	 * @return the upgradeSellerTime
	 */
	public Date getUpgradeSellerTime() {
		return upgradeSellerTime;
	}

	/**
	 * @param upgradeSellerTime
	 *            the upgradeSellerTime to set
	 */
	public void setUpgradeSellerTime(Date upgradeSellerTime) {
		this.upgradeSellerTime = upgradeSellerTime;
	}

	/**
	 * @return the realNameStatus
	 */
	public String getRealNameStatus() {
		return realNameStatus;
	}

	/**
	 * @param realNameStatus
	 *            the realNameStatus to set
	 */
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the locationAddr
	 */
	public String getLocationAddr() {
		return locationAddr;
	}

	/**
	 * @param locationAddr
	 *            the locationAddr to set
	 */
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	/**
	 * @return the bindId
	 */
	public String getBindId() {
		return bindId;
	}

	/**
	 * @param bindId
	 *            the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType
	 *            the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the recommenderCode
	 */
	public String getRecommenderCode() {
		return recommenderCode;
	}

	/**
	 * @param recommenderCode
	 *            the recommenderCode to set
	 */
	public void setRecommenderCode(String recommenderCode) {
		this.recommenderCode = recommenderCode;
	}

	public String getManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}

	public String getParentComCode() {
		return parentComCode;
	}

	public void setParentComCode(String parentComCode) {
		this.parentComCode = parentComCode;
	}

}

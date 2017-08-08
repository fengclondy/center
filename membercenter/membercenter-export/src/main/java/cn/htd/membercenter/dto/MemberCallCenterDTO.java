package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberCallCenterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8098705779974800212L;
	private Long id;
	private Long memberID;// 会员ID
	private String memberCode;// 会员Code(会员账号)
	private String companyName;// // 会员店名称
	private String artificialPersonMobile;// 法人手机号
	private String artificialPersonName;// 法人姓名
	private Long curBelongSellerId;// 当前归属商家（查看平台公司名称）
	private String curBelongManagerId;// 当前商家客户经理ID(客户经理名称)
	private String curBelongSellerName;// 平台公司名称
	private String curBelongManagerName;// 客户经理名称
	
	/**
	 * isSeller
	 */
	private String accountType;// 账号类型 isSeller判断账户类型的，1，是供应商，0 是客户
	private Integer canMallLogin;// 是否是会员(是否登陆商城判断是否是会员)1（是）/0(否)
	private String status;// 账号有效性 0：无效，1：有效'
	private String locationProvince;// 省(会员店地址)
	private String locationCity;// 城市
	private String locationCounty;// 区
	private String locationTown;// 镇
	private String locationAddr;// 所在地
	private String locationDetail;// 所在地详细
	private String address;// 拼接的地址
	private Integer isCenterStore;// 是否是中心店 1（是）/0(否)
	private Integer isVip;// 是否收费 1（是）/0(否)
	private String buyerGrade;// 会员等级
	private Integer createId;// 创建人ID
	private String createName;// 创建人名称
	private String createTime;// 创建时间
	private Integer modifyId;// 更新人ID
	private String modifyName;// 更新人名称
	private String modifyTime;// 更新人时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberID() {
		return memberID;
	}

	public void setMemberID(Long memberID) {
		this.memberID = memberID;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public Long getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(Long curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	public String getCurBelongSellerName() {
		return curBelongSellerName;
	}

	public void setCurBelongSellerName(String curBelongSellerName) {
		this.curBelongSellerName = curBelongSellerName;
	}

	public String getCurBelongManagerName() {
		return curBelongManagerName;
	}

	public void setCurBelongManagerName(String curBelongManagerName) {
		this.curBelongManagerName = curBelongManagerName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getCanMallLogin() {
		return canMallLogin;
	}

	public void setCanMallLogin(Integer canMallLogin) {
		this.canMallLogin = canMallLogin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getIsCenterStore() {
		return isCenterStore;
	}

	public void setIsCenterStore(Integer isCenterStore) {
		this.isCenterStore = isCenterStore;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getLocationTown() {
		return locationTown;
	}

	public void setLocationTown(String locationTown) {
		this.locationTown = locationTown;
	}

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberShipDTO implements Serializable {
	private static final long serialVersionUID = 3217780789372436605L;
	private Long memberId;// 会员ID
	private String memberCode;// 会员编码
	private String companyName;// 公司名称
	private String companyCode;// 公司编号
	private String locationProvince;// 所在地-省
	private String locationCity;// 所在地-市
	private String locationCounty;// 所在地-区
	private String locationTown;// 所在地-镇
	private String locationDetail;// 所在地-详细
	private String locationAddr;// 所在地
	private String artificialPersonName;// 法人姓名
	private String artificialPersonMobile;// 法人手机号码
	private String isStatus;// 是否有效状态 默认为空 有效为1，无效为0,
	private Integer buyerSellerType;// 会员/商家类型 1：会员，2：商家
	private String passWord;// 密码
	private String loginId;// 账号
	private String curBelongSellerId;// 当前归属id
	private String curBelongManagerId;// 当前归属客户经理
	private String accountNo;// 支付账号
	private String memberType;// 会员类型，1：非会员，3：担保会员 ，2：正式会员

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
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord
	 *            the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getLocationAddr() {
		return locationAddr;
	}

	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	public String getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
	}

	public Integer getBuyerSellerType() {
		return buyerSellerType;
	}

	public void setBuyerSellerType(Integer buyerSellerType) {
		this.buyerSellerType = buyerSellerType;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCurBelongSellerId() {
		return curBelongSellerId;
	}

	public void setCurBelongSellerId(String curBelongSellerId) {
		this.curBelongSellerId = curBelongSellerId;
	}

	public String getCurBelongManagerId() {
		return curBelongManagerId;
	}

	public void setCurBelongManagerId(String curBelongManagerId) {
		this.curBelongManagerId = curBelongManagerId;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType
	 *            the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}

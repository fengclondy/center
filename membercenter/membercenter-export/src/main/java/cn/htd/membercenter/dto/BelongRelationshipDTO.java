package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.domain.BelongRelationship;

/**
 * @version 创建时间：2016年12月6日 下午2:49:48 类说明:归属关系管理
 */
public class BelongRelationshipDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long memberId;// 会员ID
	private Long belongId;// 归属关系ID
	private Long boxId;// 包厢关系ID
	private String boxSellerName;// 包厢关系商家名称
	private String companyName;// 公司名称/会员名称
	private String contactMobile;// 联系人手机号码
	private Long belongSellerId;// 归属商家ID
	private String belongManagerId;// 归属商家客户经理ID
	private Long curBelongSellerId;// 当前归属商家ID
	private String curBelongManagerId;// 当前商家客户经理ID
	private String belongSellerName;// 归属商家名称
	private String belongManagerName;// 归属商家客户经理名称
	private String curBelongSellerName;// 当前归属商家名称
	private String curBelongManagerName;// 当前商家客户经理名称
	private Long modifyId;// 修改人ID
	private String modifyName;// 修改人
	private String modifyTime;// 修改时间
	private String mallAccount;// 商城账号
	private String isBusinessRelation;// 是否有经营关系
	private List<BelongRelationship> belongHistory;// 历史归属关系
	private String locationAddr;// 会员地址
	private String verifyStatus;// 审核状态
	private Long verifyId;// 审批ID
	private String buyerFeature;// 客商属性（会员属性）
	private String artificialPersonMobile;
	private Long sellerId;// 包厢sellerID
	private String memberType;// 会员类型1：非会员，3：担保会员 ，2：正式会员
	
	private String memberComCode;//公司编码

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBoxSellerName() {
		return boxSellerName;
	}

	public void setBoxSellerName(String boxSellerName) {
		this.boxSellerName = boxSellerName;
	}

	public Long getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBelongId() {
		return belongId;
	}

	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Long getBelongSellerId() {
		return belongSellerId;
	}

	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
	}

	public String getBelongManagerId() {
		return belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
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

	public String getBelongSellerName() {
		return belongSellerName;
	}

	public void setBelongSellerName(String belongSellerName) {
		this.belongSellerName = belongSellerName;
	}

	public String getBelongManagerName() {
		return belongManagerName;
	}

	public void setBelongManagerName(String belongManagerName) {
		this.belongManagerName = belongManagerName;
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

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getMallAccount() {
		return mallAccount;
	}

	public void setMallAccount(String mallAccount) {
		this.mallAccount = mallAccount;
	}

	public List<BelongRelationship> getBelongHistory() {
		return belongHistory;
	}

	public void setBelongHistory(List<BelongRelationship> belongHistory) {
		this.belongHistory = belongHistory;
	}

	public String getIsBusinessRelation() {
		return isBusinessRelation;
	}

	public void setIsBusinessRelation(String isBusinessRelation) {
		this.isBusinessRelation = isBusinessRelation;
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

	public String getBuyerFeature() {
		return buyerFeature;
	}

	public void setBuyerFeature(String buyerFeature) {
		this.buyerFeature = buyerFeature;
	}

	/**
	 * @return the artificialPersonMobile
	 */
	public String getArtificialPersonMobile() {
		return artificialPersonMobile;
	}

	/**
	 * @param artificialPersonMobile
	 *            the artificialPersonMobile to set
	 */
	public void setArtificialPersonMobile(String artificialPersonMobile) {
		this.artificialPersonMobile = artificialPersonMobile;
	}

	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public String getMemberComCode() {
		return memberComCode;
	}

	public void setMemberComCode(String memberComCode) {
		this.memberComCode = memberComCode;
	}

}

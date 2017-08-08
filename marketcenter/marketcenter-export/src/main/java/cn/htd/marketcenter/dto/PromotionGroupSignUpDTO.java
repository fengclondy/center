package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;

public class PromotionGroupSignUpDTO implements Serializable {

	private static final long serialVersionUID = 823243024412208719L;

	// 团购主键
	private Long groupId;
	// 团购活动编码
	private Long promotionId;
	// 买家编码
	private String buyerCode;
	// 团购商品数量
	private Integer productNum;
	// 买家姓名
	private String buyerName;
	// 买家手机
	private String buyerMobile;
	// 团购渠道0：平台 1：超级老板 2：H5
	private String signupChannel;
	// 省
	private String buyerAddressProvince;
	// 市
	private String buyerAddressCity;
	// 区
	private String buyerAddressDistrict;
	// 县
	private String buyerAddressTown;
	// 详细地址
	private String buyerAddressDetail;

	private Date createTime;

	private Long createId;

	private String modifyTime;

	private Long modifyId;

	private String remark;

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the promotionId
	 */
	public Long getPromotionId() {
		return promotionId;
	}

	/**
	 * @param promotionId
	 *            the promotionId to set
	 */
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * @return the buyerCode
	 */
	public String getBuyerCode() {
		return buyerCode;
	}

	/**
	 * @param buyerCode
	 *            the buyerCode to set
	 */
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	/**
	 * @return the productNum
	 */
	public Integer getProductNum() {
		return productNum;
	}

	/**
	 * @param productNum
	 *            the productNum to set
	 */
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	/**
	 * @return the buyerName
	 */
	public String getBuyerName() {
		return buyerName;
	}

	/**
	 * @param buyerName
	 *            the buyerName to set
	 */
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/**
	 * @return the buyerMobile
	 */
	public String getBuyerMobile() {
		return buyerMobile;
	}

	/**
	 * @param buyerMobile
	 *            the buyerMobile to set
	 */
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	/**
	 * @return the signupChannel
	 */
	public String getSignupChannel() {
		return signupChannel;
	}

	/**
	 * @param signupChannel
	 *            the signupChannel to set
	 */
	public void setSignupChannel(String signupChannel) {
		this.signupChannel = signupChannel;
	}

	/**
	 * @return the buyerAddressProvince
	 */
	public String getBuyerAddressProvince() {
		return buyerAddressProvince;
	}

	/**
	 * @param buyerAddressProvince
	 *            the buyerAddressProvince to set
	 */
	public void setBuyerAddressProvince(String buyerAddressProvince) {
		this.buyerAddressProvince = buyerAddressProvince;
	}

	/**
	 * @return the buyerAddressCity
	 */
	public String getBuyerAddressCity() {
		return buyerAddressCity;
	}

	/**
	 * @param buyerAddressCity
	 *            the buyerAddressCity to set
	 */
	public void setBuyerAddressCity(String buyerAddressCity) {
		this.buyerAddressCity = buyerAddressCity;
	}

	/**
	 * @return the buyerAddressDistrict
	 */
	public String getBuyerAddressDistrict() {
		return buyerAddressDistrict;
	}

	/**
	 * @param buyerAddressDistrict
	 *            the buyerAddressDistrict to set
	 */
	public void setBuyerAddressDistrict(String buyerAddressDistrict) {
		this.buyerAddressDistrict = buyerAddressDistrict;
	}

	/**
	 * @return the buyerAddressTown
	 */
	public String getBuyerAddressTown() {
		return buyerAddressTown;
	}

	/**
	 * @param buyerAddressTown
	 *            the buyerAddressTown to set
	 */
	public void setBuyerAddressTown(String buyerAddressTown) {
		this.buyerAddressTown = buyerAddressTown;
	}

	/**
	 * @return the buyerAddressDetail
	 */
	public String getBuyerAddressDetail() {
		return buyerAddressDetail;
	}

	/**
	 * @param buyerAddressDetail
	 *            the buyerAddressDetail to set
	 */
	public void setBuyerAddressDetail(String buyerAddressDetail) {
		this.buyerAddressDetail = buyerAddressDetail;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createId
	 */
	public Long getCreateId() {
		return createId;
	}

	/**
	 * @param createId
	 *            the createId to set
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/**
	 * @return the modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the modifyId
	 */
	public Long getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
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

}

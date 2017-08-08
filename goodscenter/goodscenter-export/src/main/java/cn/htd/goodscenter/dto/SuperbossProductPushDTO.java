package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

public class SuperbossProductPushDTO implements Serializable {

	private static final long serialVersionUID = -2930648923673917518L;

	public long id;

	public String skuCode;

	public String productName;

	public String categoryCode;

	public String categoryName;

	public String brandCode;

	public String brandName;

	public String cidBrand;

	public String companyName;

	public String recommendClass;

	public int sortNum;

	private String htdFlag;

	private String deleteFlag;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode
	 *            the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode
	 *            the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the brandCode
	 */
	public String getBrandCode() {
		return brandCode;
	}

	/**
	 * @param brandCode
	 *            the brandCode to set
	 */
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the cidBrand
	 */
	public String getCidBrand() {
		return cidBrand;
	}

	/**
	 * @param cidBrand
	 *            the cidBrand to set
	 */
	public void setCidBrand(String cidBrand) {
		this.cidBrand = cidBrand;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the recommendClass
	 */
	public String getRecommendClass() {
		return recommendClass;
	}

	/**
	 * @param recommendClass
	 *            the recommendClass to set
	 */
	public void setRecommendClass(String recommendClass) {
		this.recommendClass = recommendClass;
	}

	/**
	 * @return the sortNum
	 */
	public int getSortNum() {
		return sortNum;
	}

	/**
	 * @param sortNum
	 *            the sortNum to set
	 */
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	/**
	 * @return the htdFlag
	 */
	public String getHtdFlag() {
		return htdFlag;
	}

	/**
	 * @param htdFlag
	 *            the htdFlag to set
	 */
	public void setHtdFlag(String htdFlag) {
		this.htdFlag = htdFlag;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	 * @return the createName
	 */
	public String getCreateName() {
		return createName;
	}

	/**
	 * @param createName
	 *            the createName to set
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
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
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}

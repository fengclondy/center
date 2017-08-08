package cn.htd.goodscenter.dto.middleware.indto;

import java.io.Serializable;

public class QuerySpecialItemInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3766816150790761945L;
	//令牌内容
	private String token;
	//中台供应商编号
	private String supplierCode;
	//是否是协议商品，是：1、否：0
	private Integer isAgreement;
	//一页显示多少行
	private Integer pageCount;
	//当前多少页
	private Integer pageIndex;
	//可卖数，默认正序，正序：1、倒序：0
	private Integer canSellNum;
	//分配数量，默认正序，正序：1、倒序：0
	private Integer distributionNum;
	//剩余数量，默认正序，正序：1、倒序：0
	private Integer surplusNum;
	//商品名称
	private String productName;
	//品类名称
	private String categroyName;
	//品牌名称
	private String brandName;
	//是否有货，默认是，是：1、否：0
	private Integer hasStore;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public Integer getIsAgreement() {
		return isAgreement;
	}
	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getCanSellNum() {
		return canSellNum;
	}
	public void setCanSellNum(Integer canSellNum) {
		this.canSellNum = canSellNum;
	}
	public Integer getDistributionNum() {
		return distributionNum;
	}
	public void setDistributionNum(Integer distributionNum) {
		this.distributionNum = distributionNum;
	}
	public Integer getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategroyName() {
		return categroyName;
	}
	public void setCategroyName(String categroyName) {
		this.categroyName = categroyName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getHasStore() {
		return hasStore;
	}
	public void setHasStore(Integer hasStore) {
		this.hasStore = hasStore;
	}
}

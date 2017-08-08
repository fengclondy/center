package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerSupplierDTO</p>
* @author root
* @date 2016年12月26日
* <p>Description: 
*			买家中心  供应商信息
* </p>
 */
public class MemberBuyerSupplierDTO implements Serializable{

	private static final long serialVersionUID = 47990568411072427L;
	
	private Long memberId;//会员ID 
	private String memberCode;//会员编码
	private Long supplierId;//供应商id
	private Long categoryId;//类目id
	private Long brandId;//品牌id
    private String companyName;//公司名称
	private String contactName;//联系人名称
	private String contactMobile;//联系人手机号
	private String headUrl;//头像地址
	private List<MemberBuyerCategoryDTO> categoryList;//品牌，类目列表
	
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public List<MemberBuyerCategoryDTO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<MemberBuyerCategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	
}


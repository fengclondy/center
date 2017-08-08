package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public /**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerCategory</p>
* @author root
* @date 2016年12月26日
* <p>Description: 
*			会员中心  类目字段
* </p>
 */
class MemberBuyerCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1920660097095155133L;
	
	private Long brandId;//品牌id
	private String brandName;//品牌名称
	private List<MemberBuyerCaListDTO> categoryList;//类目list
	public List<MemberBuyerCaListDTO> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<MemberBuyerCaListDTO> categoryList) {
		this.categoryList = categoryList;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
}
package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * @version 创建时间：2016年12月2日 上午11:17:59 类说明
 */
public class CategoryBrandDTO implements Serializable {

	private static final long serialVersionUID = 8737822560587874667L;

	private Long businessId;// 经营关系ID
	private Long memberId;// 会员ID
	private Long categoryId;// 品类ID
	private Long brandId;// 品牌ID
	private String categoryName;// 经营品类
	private String brandName;// 经营品牌
	private String createTime;// 申请时间

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}

package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.List;

public class ShopBrandCategoryDTO implements Serializable {

	private static final long serialVersionUID = 1l;

	private List<Long> brandId; // 品牌Id集合
	private List<Long> categoryId; // 品类Id集合

	public List<Long> getBrandId() {
		return brandId;
	}

	public void setBrandId(List<Long> brandId) {
		this.brandId = brandId;
	}

	public List<Long> getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(List<Long> categoryId) {
		this.categoryId = categoryId;
	}

}

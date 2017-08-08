package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberBuyerCaListDTO implements Serializable{

	private static final long serialVersionUID = -1810639065623715038L;
	private Long categoryId;//类目id
	private String categoryName;//类目名称
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}

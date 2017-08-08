package cn.htd.goodscenter.dto;

import java.io.Serializable;

public class SkuPictureDTO implements Serializable {

	private static final long serialVersionUID = -1190943828116813412L;

	private Long skuId;//
	private String picUrl;// 图片地址
	private Integer sortNumber;// 排序号
	private String saleAttribute;//销售属性

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getSaleAttribute() {
		return saleAttribute;
	}

	public void setSaleAttribute(String saleAttribute) {
		this.saleAttribute = saleAttribute;
	}
}

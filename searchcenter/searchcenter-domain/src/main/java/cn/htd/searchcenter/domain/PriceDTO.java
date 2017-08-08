package cn.htd.searchcenter.domain;

import java.math.BigDecimal;

public class PriceDTO {

	private Long itemId;
	private Long sellerId;
	private BigDecimal price;
	private String buyerGrade;
	private String areaCode;
	
	public String getBuyerGrade() {
		return buyerGrade;
	}
	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}

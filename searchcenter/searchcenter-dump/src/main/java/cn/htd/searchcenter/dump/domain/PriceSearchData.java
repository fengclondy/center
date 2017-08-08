package cn.htd.searchcenter.dump.domain;

import java.math.BigDecimal;

public class PriceSearchData {
	private Long itemId;
	private Long sellerId;
	private BigDecimal price;

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
}

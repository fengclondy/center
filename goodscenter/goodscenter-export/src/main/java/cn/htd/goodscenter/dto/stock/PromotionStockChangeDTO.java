package cn.htd.goodscenter.dto.stock;

import java.io.Serializable;

public class PromotionStockChangeDTO implements Serializable {

	private static final long serialVersionUID = 7599256155577993115L;

	private String skuCode; // 商品SKU编码、必传
	/**
	 * 商品的数量
	 */
	private Integer quantity;
	
	private String messageId;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}

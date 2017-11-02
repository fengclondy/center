package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.math.BigDecimal;

public class MallMobileItemOutDTO implements Serializable {

	private int mimQuantity; // 起订量
	private int maxPurchaseQuantity; // 最大限购数
	private BigDecimal ladderMinPrice; //阶梯价最小金额
	private BigDecimal ladderMaxPrice; //阶梯价最大金额
	private int isPurchaseLimit;
	
	public int getIsPurchaseLimit() {
		return isPurchaseLimit;
	}

	public void setIsPurchaseLimit(int isPurchaseLimit) {
		this.isPurchaseLimit = isPurchaseLimit;
	}

	public int getMimQuantity() {
		return mimQuantity;
	}

	public void setMimQuantity(int mimQuantity) {
		this.mimQuantity = mimQuantity;
	}

	public int getMaxPurchaseQuantity() {
		return maxPurchaseQuantity;
	}

	public void setMaxPurchaseQuantity(int maxPurchaseQuantity) {
		this.maxPurchaseQuantity = maxPurchaseQuantity;
	}

	public BigDecimal getLadderMinPrice() {
		return ladderMinPrice;
	}

	public void setLadderMinPrice(BigDecimal ladderMinPrice) {
		this.ladderMinPrice = ladderMinPrice;
	}

	public BigDecimal getLadderMaxPrice() {
		return ladderMaxPrice;
	}

	public void setLadderMaxPrice(BigDecimal ladderMaxPrice) {
		this.ladderMaxPrice = ladderMaxPrice;
	}
}

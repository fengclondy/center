package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class BuyerTimelimitedInfo4SuperBossDTO implements Serializable {

	private static final long serialVersionUID = -8681451757193014911L;

	/**
	 * 会员已秒商品数量（含提交订单未支付）
	 */
	private int hasBuyProductCount;
	/**
	 * 秒杀活动商品限秒数量
	 */
	private int buyProductCountLimit;

	public int getHasBuyProductCount() {
		return hasBuyProductCount;
	}

	public void setHasBuyProductCount(int hasBuyProductCount) {
		this.hasBuyProductCount = hasBuyProductCount;
	}

	public int getBuyProductCountLimit() {
		return buyProductCountLimit;
	}

	public void setBuyProductCountLimit(int buyProductCountLimit) {
		this.buyProductCountLimit = buyProductCountLimit;
	}
}

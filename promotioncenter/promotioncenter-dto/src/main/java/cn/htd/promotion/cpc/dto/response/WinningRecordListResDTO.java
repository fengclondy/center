package cn.htd.promotion.cpc.dto.response;

import java.util.Date;

public class WinningRecordListResDTO extends GenricResDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String rewardName;

	/**
	 * 
	 */
	private Date winningTime;

	/**
	 * 
	 */
	private String promotionType;

	/**
	 * 
	 */
	private String sellerAddress;
	
	/**
	 * 归属会员店名称
	 */
	private String sellerName;

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Date getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Date winningTime) {
		this.winningTime = winningTime;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
}

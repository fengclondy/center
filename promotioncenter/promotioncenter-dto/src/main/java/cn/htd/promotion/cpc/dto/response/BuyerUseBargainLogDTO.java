package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

public class BuyerUseBargainLogDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8654317677384248929L;

	private String buyerCode; //买家编码

    private String promotionId; //活动编码

    private String levelCode;  //层级编码

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
}

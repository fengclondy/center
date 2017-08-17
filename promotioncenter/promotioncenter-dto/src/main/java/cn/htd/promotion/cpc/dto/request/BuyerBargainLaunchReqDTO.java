package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class BuyerBargainLaunchReqDTO implements Serializable{

	private static final long serialVersionUID = -3459854858917893780L;
	
	private String buyerCode;//买家编码
	
	@NotEmpty(message = "promotionId不能为空")
	private String promotionId;//促销活动编码
	
	@NotEmpty(message = "levelCode不能为空")
	private String levelCode;//层级编码
	
	@NotEmpty(message = "messageId不能为空")
	private String messageId;

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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}

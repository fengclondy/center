package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动宣传语表
 * @author xmz
 *
 */
public class PromotionSloganResDTO extends PromotionBargainInfoResDTO implements Serializable {
	
	private static final long serialVersionUID = 2937241331370615679L;

	private String promotionId; // 促销活动id
	
	private String promotionSlogan; // 宣传语

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionSlogan() {
		return promotionSlogan;
	}

	public void setPromotionSlogan(String promotionSlogan) {
		this.promotionSlogan = promotionSlogan;
	}
}

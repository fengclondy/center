package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动宣传语表
 * @author xmz
 *
 */
public class PromotionSloganResDTO implements Serializable {
	
	private static final long serialVersionUID = 2937241331370615679L;

	private String promotionId; // 促销活动id
	
	private String promotionSlogan; // 宣传语
	
	private String createId; // 创建人id
	
	private String createName; // 创建人名称
	
	private Date createTime; // 创建时间

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

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

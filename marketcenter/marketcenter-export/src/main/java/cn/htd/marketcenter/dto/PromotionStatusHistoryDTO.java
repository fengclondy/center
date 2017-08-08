package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动状态履历表
 */
public class PromotionStatusHistoryDTO implements Serializable {

	private static final long serialVersionUID = -3562287193169657409L;

	private Long id;

	private String promotionId;// 促销活动ID

	private String promotionStatus;// 促销活动状态履历表

	private String promotionStatusText;// 活动状态文本

	private Long createId;

	private String createName;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus == null ? null : promotionStatus.trim();
	}

	public String getPromotionStatusText() {
		return promotionStatusText;
	}

	public void setPromotionStatusText(String promotionStatusText) {
		this.promotionStatusText = promotionStatusText == null ? null : promotionStatusText.trim();
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName == null ? null : createName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
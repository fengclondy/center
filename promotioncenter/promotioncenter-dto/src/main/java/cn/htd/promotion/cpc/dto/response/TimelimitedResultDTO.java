package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * 秒杀活动结果表
 */
public class TimelimitedResultDTO implements Serializable {
	private static final long serialVersionUID = 4207219940532334357L;
	/**
	 * 促销活动编码
	 */
	@NotBlank(message = "促销活动编码不能为空")
	private String promotionId;
	/**
	 * 商品总数量
	 */
	private Integer totalSkuCount = 0;
	/**
	 * 展示剩余商品数量
	 */
	private Integer showRemainSkuCount = 0;
	/**
	 * 展示已秒杀商品数量
	 */
	@Min(value = 0, message = "展示已秒杀商品数量不能小于0")
	private Integer showTimelimitedBuyedCount = 0;
	/**
	 * 展示参与秒杀人数
	 */
	@Min(value = 0, message = "展示参与秒杀人数不能小于0")
	private Integer showTimelimitedActorCount = 0;
	/**
	 * 真实剩余商品数量
	 */
	private Integer realRemainSkuCount = 0;
	/**
	 * 真实已秒杀商品数量
	 */
	private Integer realTimelimitedBuyedCount = 0;
	/**
	 * 真实参与秒杀人数
	 */
	private Integer realTimelimitedActorCount = 0;

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Integer getTotalSkuCount() {
		return totalSkuCount;
	}

	public void setTotalSkuCount(Integer totalSkuCount) {
		this.totalSkuCount = totalSkuCount;
	}

	public Integer getShowRemainSkuCount() {
		return showRemainSkuCount;
	}

	public void setShowRemainSkuCount(Integer showRemainSkuCount) {
		this.showRemainSkuCount = showRemainSkuCount;
		this.showTimelimitedBuyedCount = this.totalSkuCount - showRemainSkuCount;
		this.showTimelimitedBuyedCount = this.showTimelimitedBuyedCount.intValue() < 0 ? 0
				: this.showTimelimitedBuyedCount;
	}

	public Integer getShowTimelimitedBuyedCount() {
		return showTimelimitedBuyedCount;
	}

	public void setShowTimelimitedBuyedCount(Integer showTimelimitedBuyedCount) {
		this.showTimelimitedBuyedCount = showTimelimitedBuyedCount;
	}

	public Integer getShowTimelimitedActorCount() {
		return showTimelimitedActorCount;
	}

	public void setShowTimelimitedActorCount(Integer showTimelimitedActorCount) {
		this.showTimelimitedActorCount = showTimelimitedActorCount;
	}

	public Integer getRealRemainSkuCount() {
		return realRemainSkuCount;
	}

	public void setRealRemainSkuCount(Integer realRemainSkuCount) {
		this.realRemainSkuCount = realRemainSkuCount;
		this.realTimelimitedBuyedCount = this.totalSkuCount - realRemainSkuCount;
	}

	public Integer getRealTimelimitedBuyedCount() {
		return realTimelimitedBuyedCount;
	}

	public Integer getRealTimelimitedActorCount() {
		return realTimelimitedActorCount;
	}

	public void setRealTimelimitedActorCount(Integer realTimelimitedActorCount) {
		this.realTimelimitedActorCount = realTimelimitedActorCount;
	}

}
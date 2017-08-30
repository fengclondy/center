package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;


/**
 * 秒杀活动汇掌柜展示用DTO
 */
public class PromotionTimelimitedShowDTO extends TimelimitedInfoResDTO
		implements Serializable, Comparable<PromotionTimelimitedShowDTO> {

	private static final long serialVersionUID = -273656067277908315L;

	/**
	 * 剩余可秒商品数量
	 */
	private int remainCount = 0;
	/**
	 * 秒杀活动状态 1:进行中，2:未开始，3:已抢光，4:已结束
	 */
	private int compareStatus;
	/**
	 * 秒杀活动显示状态 processing:进行中，noStart:未开始，clear:已抢光，ended:已结束
	 */
	private String showStatusStr;

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public int getCompareStatus() {
		return compareStatus;
	}

	public void setCompareStatus(int compareStatus) {
		this.compareStatus = compareStatus;
	}

	public String getShowStatusStr() {
		return showStatusStr;
	}

	public void setShowStatusStr(String showStatusStr) {
		this.showStatusStr = showStatusStr;
	}

	@Override
	public int compareTo(PromotionTimelimitedShowDTO o) {
		int diffStatus = this.compareStatus - o.compareStatus;
		int diffModifyTime = o.getCreateTime().compareTo(this.getCreateTime());
		if (diffStatus != 0) {
			return diffStatus;
		}
		return diffModifyTime;
	}
}

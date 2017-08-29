package cn.htd.promotion.cpc.dto.response;

import java.io.Serializable;

/**
 * 秒杀活动汇掌柜展示用DTO
 */
public class PromotionTimelimitedShowDTO extends TimelimitedInfoResDTO
		implements Serializable, Comparable<PromotionTimelimitedShowDTO> {

	private static final long serialVersionUID = -273656067277908315L;

	/**
	 * 按创建时间排序
	 */
	
	@Override
	public int compareTo(PromotionTimelimitedShowDTO o) {
		int diffModifyTime = o.getCreateTime().compareTo(this.getCreateTime());
		return diffModifyTime;
	}
}

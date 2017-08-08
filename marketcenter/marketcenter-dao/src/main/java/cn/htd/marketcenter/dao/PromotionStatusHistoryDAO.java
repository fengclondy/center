package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;

/**
 * 促销活动状态履历
 */
public interface PromotionStatusHistoryDAO extends BaseDAO<PromotionStatusHistoryDTO> {

	/**
	 * 根据促销活动编码获取活动状态履历
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionStatusHistoryDTO> queryByPromotionId(String promotionId);
}
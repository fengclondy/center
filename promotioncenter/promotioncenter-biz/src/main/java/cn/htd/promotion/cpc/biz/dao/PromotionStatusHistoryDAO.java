package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;

/**
 * 促销活动状态履历
 */
@Repository("cn.htd.promotion.cpc.biz.dao.promotionStatusHistoryDAO")
public interface PromotionStatusHistoryDAO extends BaseDAO<PromotionStatusHistoryDTO> {

	/**
	 * 根据促销活动编码获取活动状态履历
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionStatusHistoryDTO> queryByPromotionId(String promotionId);
}
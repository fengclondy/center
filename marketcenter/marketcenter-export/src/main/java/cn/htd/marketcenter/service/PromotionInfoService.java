package cn.htd.marketcenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionInfoDTO;

/**
 * 促销活动信息
 */
public interface PromotionInfoService {

	/**
	 * 根据促销活动ID查询促销活动信息
	 * 
	 * @param promotionIdList
	 * @return
	 */
	public ExecuteResult<List<PromotionInfoDTO>> queryPromotionInfoByPromotionId(List<String> promotionIdList);

}

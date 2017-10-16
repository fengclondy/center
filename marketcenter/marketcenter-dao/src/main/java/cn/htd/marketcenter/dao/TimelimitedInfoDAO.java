package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;

public interface TimelimitedInfoDAO extends BaseDAO<TimelimitedInfoDTO> {

	/**
	 * 根据ID获取秒杀活动信息
	 * 
	 * @param timelimitedDTO
	 * @return
	 */
	public TimelimitedInfoDTO queryTimelimitedInfoById(PromotionAccumulatyDTO timelimitedDTO);

	/**
	 * 根据促销活动编码查询秒杀活动列表
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<TimelimitedInfoDTO> queryTimelimitedInfoByPromotionId(String promotionId);

}
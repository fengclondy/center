package cn.htd.marketcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedListDTO;

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

	/**
	 * 获取总数
	 * 
	 * @param timelimitedDTO
	 * @param pager
	 * @return
	 */
	Long queryTimelimitedInfoListCount(@Param("entity") TimelimitedInfoDTO timelimitedDTO);

	/**
	 * 根据条件查询秒杀活动列表
	 * 
	 * @param timelimitedDTO
	 * @return
	 */
	public List<TimelimitedListDTO> queryTimelimitedInfoList(@Param("entity") TimelimitedInfoDTO timelimitedDTO,
			@Param("page") Pager<TimelimitedListDTO> pager);
}
package cn.htd.marketcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;

public interface PromotionAccumulatyDAO extends BaseDAO<PromotionAccumulatyDTO> {

	/**
	 * 取得促销活动的层级
	 * 
	 * @param promotionId
	 * @param levelCodeList
	 * @return
	 */
	public List<PromotionAccumulatyDTO> queryAccumulatyListByPromotionId(@Param("promotionId") String promotionId,
			@Param("levelCodeList") List<String> levelCodeList);

}
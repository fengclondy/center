package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionAccumulatyDAO")
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

	public List<PromotionAccumulatyDTO> queryPromotionAccumulatyByPromotionId(@Param("promotionId") String promotionId);
	
}
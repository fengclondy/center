package cn.htd.marketcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;

public interface PromotionRulesDefineDAO extends BaseDAO<PromotionRulesDefineDTO> {

	/**
	 * 根据规则名称检查重复名称的规则数量
	 * 
	 * @param name
	 * @return
	 */
	public Long queryCountByName(String name);
}

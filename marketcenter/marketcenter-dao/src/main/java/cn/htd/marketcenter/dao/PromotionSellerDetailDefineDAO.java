package cn.htd.marketcenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDefineDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDefineDTO;

/**
 * Created by thinkpad on 2016/12/1.
 */
public interface PromotionSellerDetailDefineDAO extends BaseDAO<PromotionSellerDetailDefineDTO> {

	/**
	 * 根据ruleId修改删除标志
	 */
	public Long deleteByRuleId(PromotionSellerRuleDefineDTO dto);

	/**
	 * 按照ID删除供应商详细信息
	 * 
	 * @param detailDTO
	 * @return
	 */
	public Long deleteDetailList(PromotionSellerDetailDefineDTO detailDTO);

	/**
	 * 查询供应商详细信息
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PromotionSellerDetailDefineDTO> querySellerDetailList(Long ruleId);
}

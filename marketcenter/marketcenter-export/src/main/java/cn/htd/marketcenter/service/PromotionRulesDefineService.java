package cn.htd.marketcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;

/**
 * Created by thinkpad on 2016/12/2.
 */
public interface PromotionRulesDefineService {

	/**
	 * 根据条件查询
	 * 
	 * @param condition
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionRulesDefineDTO>> queryPromotionRulesDefine(PromotionRulesDefineDTO condition,
			Pager<PromotionRulesDefineDTO> pager);

	/**
	 * 根据ID删除
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> deletePromotionRulesDefine(PromotionRulesDefineDTO dto);

}

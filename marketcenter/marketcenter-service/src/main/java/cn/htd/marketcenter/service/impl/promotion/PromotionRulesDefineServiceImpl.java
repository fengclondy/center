package cn.htd.marketcenter.service.impl.promotion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionRulesDefineDAO;
import cn.htd.marketcenter.dto.PromotionRulesDefineDTO;
import cn.htd.marketcenter.service.PromotionRulesDefineService;

/**
 * Created by thinkpad on 2016/12/2.
 */
@Service("promotionRulesDefineService")
public class PromotionRulesDefineServiceImpl implements PromotionRulesDefineService {

	@Resource
	private PromotionRulesDefineDAO promotionRulesDefineDAO;

	@Override
	public ExecuteResult<DataGrid<PromotionRulesDefineDTO>> queryPromotionRulesDefine(PromotionRulesDefineDTO condition,
			Pager<PromotionRulesDefineDTO> pager) {
		ExecuteResult<DataGrid<PromotionRulesDefineDTO>> result = new ExecuteResult<DataGrid<PromotionRulesDefineDTO>>();
		DataGrid<PromotionRulesDefineDTO> dataGrid = new DataGrid<PromotionRulesDefineDTO>();
		long count = 0;
		List<PromotionRulesDefineDTO> ruleList = null;
		try {

			count = promotionRulesDefineDAO.queryCount(condition);
			if (count > 0) {
				ruleList = promotionRulesDefineDAO.queryList(condition, pager);
			}
			dataGrid.setRows(ruleList);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deletePromotionRulesDefine(PromotionRulesDefineDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			promotionRulesDefineDAO.delete(dto);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}

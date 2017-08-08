package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.service.PromotionInfoService;

@Service("promotionInfoService")
public class PromotionInfoServiceImpl implements PromotionInfoService {

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Override
	public ExecuteResult<List<PromotionInfoDTO>> queryPromotionInfoByPromotionId(List<String> promotionIdList) {
		ExecuteResult<List<PromotionInfoDTO>> result = new ExecuteResult<List<PromotionInfoDTO>>();
		List<PromotionInfoDTO> promotionList = new ArrayList<PromotionInfoDTO>();
		try {
			promotionList = promotionInfoDAO.queryPromotionInfoByIdList(promotionIdList);
			result.setResult(promotionList);
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;

	}

}

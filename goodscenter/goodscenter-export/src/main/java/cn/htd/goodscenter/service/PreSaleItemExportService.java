package cn.htd.goodscenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.presale.PreSaleProdQueryDTO;

public interface PreSaleItemExportService {
	
	/**
	 * 实时查询预售商品信息
	 * 
	 * @param skuCode
	 * @return
	 */
	ExecuteResult<PreSaleProdQueryDTO> queryPreSaleProdInfo(String skuCode);
}

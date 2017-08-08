/**
 * 
 */
package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.dto.response.ShopOrderAnalsisDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderAnalysisDayReportReqDTO;

/**
 * @author ly
 *
 */
public interface ShopOrderAnalysisReportService {

	/**
	 * 
	 * @param shopOrderAnalysisDayReportReqDTO
	 * @return
	 */
	ShopOrderAnalsisDayReportResDTO queryShopOrderAnalsisDayReportList(
			ShopOrderAnalysisDayReportReqDTO shopOrderAnalysisDayReportReqDTO);
}

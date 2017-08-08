/**
 * 
 */
package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.ShopOrderAnalsisDayReportResDTO;
import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderAnalysisDayReportReqDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderStatisticsDayReportReqDTO;

/**
 * @author ly
 *
 */
public interface ShopOrderReportAPI 
{
	/**
	 * 
	 * @param shopOrderAnalysisDayReportReqDTO
	 * @return
	 */
	public ShopOrderAnalsisDayReportResDTO queryShopOrderDayAnalsis(ShopOrderAnalysisDayReportReqDTO shopOrderAnalysisDayReportReqDTO);
	
	/**
	 * 
	 * @param shopOrderStatisticsDayReportReqDTO
	 * @return
	 */
	public ShopOrderStatisticsDayReportResDTO queryShopOrderDayStatistics(ShopOrderStatisticsDayReportReqDTO shopOrderStatisticsDayReportReqDTO);
}

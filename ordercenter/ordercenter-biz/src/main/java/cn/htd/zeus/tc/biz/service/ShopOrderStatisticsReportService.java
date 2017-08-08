/**
 * 
 */
package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderStatisticsDayReportReqDTO;

/**
 * @author ly
 *
 */
public interface ShopOrderStatisticsReportService 
{

	ShopOrderStatisticsDayReportResDTO queryShopOrderStatisticsDayReportList(
			ShopOrderStatisticsDayReportReqDTO shopOrderStatisticsDayReportReqDTO);

}

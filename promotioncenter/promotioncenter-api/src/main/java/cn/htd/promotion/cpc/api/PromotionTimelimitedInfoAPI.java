package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
/**
 * 秒杀活动服务
 */
public interface PromotionTimelimitedInfoAPI {

	/**
	 * 汇掌柜APP -  取得秒杀信息，用于判断会员店是否有总部秒杀
	 * 
	 * @param messageId
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode);


	/**
	 * 汇掌柜APP - 查询秒杀活动列表
	 * 
	 * @param messageId
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> getPromotionTimelimitedList(String messageId,
			Pager<TimelimitedInfoResDTO> page);
	
	
	/**
	 * 汇掌柜APP - 查询秒杀活动详情
	 * 
	 * @param messageId
	 * @param promotionId
	 * @param buyerCode
	 * @param buyerGrade
	 * @return
	 */
	public ExecuteResult<PromotionTimelimitedShowDTO> getPromotionTimelimitedInfoDetail(String messageId, String promotionId,
			String buyerCode, String buyerGrade);
}

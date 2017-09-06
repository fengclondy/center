package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
public interface TimelimitedInfoAPI {
	
	/**
	 * 添加秒杀活动
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);
	
	/**
	 * 修改秒杀活动
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);
	
	/**
	 * 根据promotionId获取完整的秒杀活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoResDTO> getSingleFullTimelimitedInfoByPromotionId(String promotionId,String messageId);

	/**
	 * 根据条件分页查询秒杀活动
	 * @param page
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedInfoResDTO>> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
	
	/**
	 * 根据活动编码进行上下架操作
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<String> updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
	
}

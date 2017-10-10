package cn.htd.promotion.cpc.api;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;

public interface GroupbuyingAPI {
	
	/**
	 * 添加团购活动
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> addGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId);
	
	/**
	 * 修改团购活动
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> updateGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO,String messageId);
//	
//	/**
//	 * 根据promotionId获取完整的秒杀活动信息
//	 * @param promotionId
//	 * @param messageId
//	 * @return
//	 */
//	public ExecuteResult<TimelimitedInfoResDTO> getSingleFullTimelimitedInfoByPromotionId(String promotionId,String messageId);
//
//	/**
//	 * 根据条件分页查询秒杀活动
//	 * @param page
//	 * @param timelimitedInfoReqDTO
//	 * @param messageId
//	 * @return
//	 */
//	public ExecuteResult<DataGrid<TimelimitedInfoResDTO>> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
//			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
//	
//	/**
//	 * 根据活动编码进行上下架操作
//	 * @param timelimitedInfoReqDTO
//	 * @param messageId
//	 * @return
//	 */
//	public ExecuteResult<String> updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
//	
//	/**
//	 * 总部秒杀参与会员店列表取得
//	 * 
//	 * @param promotionId 活动ID
//	 * @param messageId 消息ID
//	 * @return 总部秒杀参与会员店列表
//	 */
//	public ExecuteResult<List<PromotionSellerDetailDTO>> getPromotionSellerDetailList(String promotionId, String messageId);

}

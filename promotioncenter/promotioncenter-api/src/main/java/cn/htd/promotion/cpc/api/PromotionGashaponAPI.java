package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

public interface PromotionGashaponAPI {
	
	/**
	 * 查询扭蛋机活动列表
	 * PromotionInfoDTO 取参数数据值如下
	 * @param promotion_name 活动名称
	 * @param status 活动状态：1：活动未开始，2：活动进行中，3：活动已结束，9：已删除',
	 * @param effective_time 活动开始时间
	 * @param invalid_time 活动结束时间
	 * @param modify_time 最后操作时间状态：
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionInfoDTO>> getPromotionGashaponByInfo(PromotionInfoReqDTO promotionInfoDTO,Pager<PromotionInfoReqDTO> pager);
}

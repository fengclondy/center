package cn.htd.marketcenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.TimelimitPurchaseMallInfoDTO;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedListDTO;

/**
 * 限时购活动服务
 */
public interface TimelimitedPurchaseService {

	/**
	 * 限时购 - 新增限时购活动信息
	 * 
	 * @param timelimitedInfo
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo);

	/**
	 * 根据条件查询秒杀活动列表
	 * 
	 * @param conditionDTO
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedListDTO>> queryTimelimitedListByCondition(
			TimelimitedConditionDTO conditionDTO, Pager<TimelimitedListDTO> page);

	/**
	 * 限时购 - 查询限时购活动详情
	 * 
	 * @param promotionId
	 * @return
	 */
	public ExecuteResult<List<TimelimitedInfoDTO>> queryTimelimitedInfo(String promotionId);

	/**
	 * 限时购 － 获取对应的限时购信息(根据skuCode查询)
	 * 
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<List<TimelimitedInfoDTO>> getTimelimitedInfo(String skuCode);

	/**
	 * 限时购 － 获取对应的限时购信息
	 * 
	 * @param
	 * @return
	 */
	public ExecuteResult<List<TimelimitPurchaseMallInfoDTO>> getTimelimitedInfo(TimelimitedInfoDTO timelimitedInfoDTO);
	
}

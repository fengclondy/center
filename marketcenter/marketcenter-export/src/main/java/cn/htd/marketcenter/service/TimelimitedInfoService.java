package cn.htd.marketcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;

/**
 * 秒杀活动服务
 */
public interface TimelimitedInfoService {

	/**
	 * 根据商品编码取得秒杀信息
	 * 
	 * @param messageId
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoDTO> getSkuTimelimitedInfo(String messageId, String skuCode);

	/**
	 * 根据商品编码取得该商品参与的供应商秒杀和平台秒杀的商品总数量
	 *
	 * @param messageId
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<Integer> getSkuTimelimitedAllCount(String messageId, String skuCode);

	/**
	 * 商城查询活动中秒杀活动列表（非VIP）
	 * 
	 * @param messageId
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getMallTimelimitedList(String messageId,
			Pager<TimelimitedInfoDTO> page);

	/**
	 * 商城查询活动中秒杀活动列表（VIP）
	 * 
	 * @param messageId
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getMallVIPTimelimitedList(String messageId,
			Pager<TimelimitedInfoDTO> page);

	/**
	 * 根据条件查询秒杀活动列表
	 * 
	 * @param conditionDTO
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<TimelimitedInfoDTO>> queryTimelimitedListByCondition(
			TimelimitedConditionDTO conditionDTO, Pager<TimelimitedInfoDTO> page);

	/**
	 * 查询商城用秒杀活动详情
	 * 
	 * @param messageId
	 * @param promotionId
	 * @param buyerCode
	 * @param buyerGrade
	 * @return
	 */
	public ExecuteResult<TimelimitedMallInfoDTO> getMallTimelimitedInfo(String messageId, String promotionId,
			String buyerCode, String buyerGrade);

	/**
	 * 查询秒杀活动详情
	 * 
	 * @param promotionId
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoDTO> queryTimelimitedInfo(String promotionId);

	/**
	 * 新增秒杀活动信息
	 * 
	 * @param timelimitedInfo
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo);

	/**
	 * 启用／不启用秒杀活动信息
	 * 
	 * @param validDTO
	 * @return
	 */
	public ExecuteResult<String> saveTimelimitedValidStatus(PromotionValidDTO validDTO);

	/**
	 * 删除秒杀活动信息
	 * 
	 * @param validDTO
	 * @return
	 */
	public ExecuteResult<String> deleteTimelimitedInfo(PromotionValidDTO validDTO);

	/**
	 * 更新秒杀活动展示结果
	 * 
	 * @param resultDTO
	 * @return
	 */
	public ExecuteResult<String> updateTimelimitedResultShowCount(TimelimitedResultDTO resultDTO);
	
	/**
	 * 修改秒杀活动信息
	 *
	 * @param timelimitedInfo
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoDTO> updateTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo);

}

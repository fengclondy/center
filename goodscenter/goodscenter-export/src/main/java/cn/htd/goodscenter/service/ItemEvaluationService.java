package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.goodscenter.dto.ItemEvaluationDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryInDTO;
import cn.htd.goodscenter.dto.ItemEvaluationReplyDTO;
import cn.htd.goodscenter.dto.ItemEvaluationTotalDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.ItemEvaluationQueryOutDTO;

/**
 * <p>
 * Description: [商品评价服务类]
 * </p>
 */
public interface ItemEvaluationService {

	/**
	 * 添加买家对商品的评价/卖家对买家的评价
	 */
	public ExecuteResult<ItemEvaluationDTO> addItemEvaluation(ItemEvaluationDTO itemEvaluationDTO);

	/**
	 * <p>
	 * Discription:[根据评价信息唯一标识，修改评价状态]
	 * </p>
	 * 
	 * @param itemId
	 *            唯一标识
	 * @param flag
	 *            标记：0在用；1删除；2屏蔽
	 */
	public ExecuteResult<String> updFlagItemEvaluationById(Long itemId, Integer flag);

	/**
	 * 批量添加
	 */
	public ExecuteResult<ItemEvaluationDTO> addItemEvaluations(List<ItemEvaluationDTO> itemEvaluationDTOList);

	/**
	 * 添加针对评价的回复信息
	 */
	public ExecuteResult<ItemEvaluationReplyDTO> addItemEvaluationReply(ItemEvaluationReplyDTO itemEvaluationReplyDTO);

	/**
	 * 查询买家对商品的评价记录
	 */
	public DataGrid<ItemEvaluationQueryOutDTO> queryItemEvaluationList(ItemEvaluationQueryInDTO itemEvaluationQueryInDTO, Pager page);

	/**
	 * 对商品评价统计/对买家评价统计
	 */
	public ExecuteResult<ItemEvaluationTotalDTO> queryItemEvaluationTotal(ItemEvaluationQueryInDTO itemEvaluationQueryInDTO);

	/**
	 * 查询评价详情
	 */
	public ItemEvaluationDTO queryItemEvaluationById(ItemEvaluationDTO itemEvaluationDTO);

	/**
	 * 查询评价的回复数据集合
	 */
	public DataGrid<ItemEvaluationReplyDTO> queryItemEvaluationReplyList(ItemEvaluationReplyDTO itemEvaluationReplyDTO, Pager page);

	public List<ItemEvaluationQueryOutDTO> querySkuEvaluationBySyncTime(Date syncTime);
}

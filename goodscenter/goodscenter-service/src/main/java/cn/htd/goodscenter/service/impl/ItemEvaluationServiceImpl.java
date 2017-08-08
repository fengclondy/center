package cn.htd.goodscenter.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.goodscenter.service.ItemEvaluationService;
import cn.htd.goodscenter.service.ItemSkuExportService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.ItemAttributeDAO;
import cn.htd.goodscenter.dao.ItemAttributeValueDAO;
import cn.htd.goodscenter.dao.ItemEvaluationDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dto.ItemEvaluationDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryInDTO;
import cn.htd.goodscenter.dto.ItemEvaluationQueryOutDTO;
import cn.htd.goodscenter.dto.ItemEvaluationReplyDTO;
import cn.htd.goodscenter.dto.ItemEvaluationTotalDTO;
import cn.htd.goodscenter.dto.ItemEvaluationTotalDetailDTO;
import cn.htd.goodscenter.dto.ItemEvaluationTotalQueryDTO;

/**
 * <p>
 * Description: [商品评价服务实现类]
 * </p>
 */
@Service("itemEvaluationService")
public class ItemEvaluationServiceImpl implements ItemEvaluationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemEvaluationServiceImpl.class);
	@Resource
	private ItemSkuExportService itemSkuExportService;
	@Resource
	private ItemEvaluationDAO itemEvaluationDAO;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private ItemAttributeDAO itemAttributeDAO;
	@Resource
	private ItemAttributeValueDAO itemAttributeValueDAO;

	/**
	 * 添加买家对商品的评价/卖家对买家的评价
	 */
	@Override
	public ExecuteResult<ItemEvaluationDTO> addItemEvaluation(ItemEvaluationDTO itemEvaluationDTO) {
		LOGGER.info("=============开始保存评价====================");
		ExecuteResult<ItemEvaluationDTO> result = new ExecuteResult<ItemEvaluationDTO>();
		try {
			if (itemEvaluationDTO == null) {
				result.addErrorMessage("执行方法【addItemEvaluation】出错,错误:参数不能为空");
				return result;
			}
			itemEvaluationDAO.insertItemEvaluation(itemEvaluationDTO);
			result.setResult(itemEvaluationDTO);
		} catch (Exception e) {
			LOGGER.error("调用方法【addItemEvaluation】出错,错误:" + e);
			result.addErrorMessage("执行方法【addItemEvaluation】报错:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束评价保存====================");
		}
		return result;
	}

	/**
	 * 添加卖家对评价的回复
	 */
	@Override
	public ExecuteResult<ItemEvaluationReplyDTO> addItemEvaluationReply(ItemEvaluationReplyDTO itemEvaluationReplyDTO) {
		LOGGER.info("=============开始保存评价回复====================");
		ExecuteResult<ItemEvaluationReplyDTO> result = new ExecuteResult<ItemEvaluationReplyDTO>();
		try {
			if (itemEvaluationReplyDTO == null) {
				result.addErrorMessage("执行方法【addItemEvaluationReply】出错,错误:参数不能为空");
				return result;
			}
			itemEvaluationDAO.insertItemEvaluationReply(itemEvaluationReplyDTO);
			result.setResult(itemEvaluationReplyDTO);
		} catch (Exception e) {
			LOGGER.error("调用方法【addItemEvaluationReply】出错,错误:" + e);
			result.addErrorMessage("执行方法【addItemEvaluationReply】报错:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束评价保存====================");
		}
		return result;
	}

	/**
	 * 查询商品评价
	 */
	@Override
	public DataGrid<ItemEvaluationQueryOutDTO> queryItemEvaluationList(ItemEvaluationQueryInDTO itemEvaluationQueryInDTO, Pager page) {
		LOGGER.info("=============开始查询商品评价====================");
		DataGrid<ItemEvaluationQueryOutDTO> dg = new DataGrid<ItemEvaluationQueryOutDTO>();
		try {
			if (itemEvaluationQueryInDTO != null && StringUtils.isNotEmpty(itemEvaluationQueryInDTO.getScopeLevel())) {
				// 好评为 5
				if ("1".equals(itemEvaluationQueryInDTO.getScopeLevel())) {
					itemEvaluationQueryInDTO.setScope(5);
				} else if ("2".equals(itemEvaluationQueryInDTO.getScopeLevel())) {
					itemEvaluationQueryInDTO.setBeginScope(3L);
					itemEvaluationQueryInDTO.setEndScope(4L);
				} else if ("3".equals(itemEvaluationQueryInDTO.getScopeLevel())) {
					itemEvaluationQueryInDTO.setBeginScope(1L);
					itemEvaluationQueryInDTO.setEndScope(2L);
				}
			}
			LOGGER.info("=============开始查询商品评价数据列表====================");
			List<ItemEvaluationQueryOutDTO> rows = this.itemEvaluationDAO.queryItemEvaluationList(itemEvaluationQueryInDTO, page);
			if (rows != null && rows.size() > 0) {
				for (ItemEvaluationQueryOutDTO itemEvaluationQueryOutDTO : rows) {
					String itemSkuAttrStr = itemSkuExportService.queryItemSkuAttrStr(itemEvaluationQueryOutDTO.getSkuId());
					itemEvaluationQueryOutDTO.setAttributes(itemSkuAttrStr);
				}
			}
			LOGGER.info("=============完成查询商品评价数据列表====================");
			LOGGER.info("=============开始查询商品评价总条数====================");
			Long total = this.itemEvaluationDAO.queryCount(itemEvaluationQueryInDTO);
			LOGGER.info("=============完成查询商品评价总条数====================");
			dg.setRows(rows);
			dg.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("调用方法【queryItemEvaluationList】出错,错误:" + e);
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束查询商品评价====================");
		}
		return dg;
	}

	/**
	 * 对商品评价/对买家评价统计
	 */
	@Override
	public ExecuteResult<ItemEvaluationTotalDTO> queryItemEvaluationTotal(ItemEvaluationQueryInDTO itemEvaluationQueryInDTO) {
		ExecuteResult<ItemEvaluationTotalDTO> result = new ExecuteResult<ItemEvaluationTotalDTO>();
		LOGGER.info("=============开始评价统计====================");
		try {
			ItemEvaluationTotalDTO itemEvaluationTotalDTO = null;
			LOGGER.info("=============开始查询评价统计列表,按照级别分组====================");
			List<ItemEvaluationTotalQueryDTO> totalList = this.itemEvaluationDAO.queryItemEvaluationTotal(itemEvaluationQueryInDTO);
			LOGGER.info("=============完成查询评价统计列表====================");
			if (totalList != null && totalList.size() > 0) {
				LOGGER.info("=============开始组装列表数据====================");
				Map<Integer, ItemEvaluationTotalDetailDTO> scopeAvgDetails = new HashMap<Integer, ItemEvaluationTotalDetailDTO>();
				for (ItemEvaluationTotalQueryDTO it : totalList) {
					if (it == null)
						continue;
					// 获取总体评分
					BigDecimal allCountBg = new BigDecimal(it.getAllCount());
					BigDecimal allSumBg = new BigDecimal(it.getAllSum());
					if (itemEvaluationTotalDTO == null) {
						itemEvaluationTotalDTO = new ItemEvaluationTotalDTO();
						// 获取总评价人数
						itemEvaluationTotalDTO.setEvaluationNum(it.getAllCount());
						if (allCountBg.intValue() == 0) {
							itemEvaluationTotalDTO.setScopeAvg(0.0);
						} else {
							itemEvaluationTotalDTO.setScopeAvg(allSumBg.divide(allCountBg, 1, BigDecimal.ROUND_HALF_UP).doubleValue());
						}
					}
					// 组装评价
					ItemEvaluationTotalDetailDTO itemEvaluationTotalDetail = new ItemEvaluationTotalDetailDTO();
					itemEvaluationTotalDetail.setCount(it.getScopeCount().intValue());
					if (allCountBg.intValue() == 0) {
						itemEvaluationTotalDetail.setPercent(0.0);
					} else {
						BigDecimal scopeCount = new BigDecimal(it.getScopeCount());
						itemEvaluationTotalDetail.setPercent(scopeCount.divide(allCountBg, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).doubleValue());
					}
					scopeAvgDetails.put(it.getScope(), itemEvaluationTotalDetail);
				}
				if (itemEvaluationTotalDTO != null)
					itemEvaluationTotalDTO.setScopeAvgDetails(scopeAvgDetails);
				LOGGER.info("=============完成组装列表数据====================");
			}
			result.setResult(itemEvaluationTotalDTO);
		} catch (Exception e) {
			LOGGER.error("调用方法【queryItemEvaluationTotal】出错,错误:" + e);
			result.addErrorMessage("执行方法【queryItemEvaluationTotal】报错:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束查询商品评价====================");
		}
		LOGGER.info("=============结束评价统计====================");
		return result;
	}

	/**
	 * 批量添加
	 * 
	 */
	public ExecuteResult<ItemEvaluationDTO> addItemEvaluations(List<ItemEvaluationDTO> itemEvaluationDTOList) {
		ExecuteResult<ItemEvaluationDTO> result = new ExecuteResult<ItemEvaluationDTO>();
		try {
			if (itemEvaluationDTOList == null || itemEvaluationDTOList.size() == 0) {
				result.addErrorMessage("参数不能为空");
				return result;
			}
			for (ItemEvaluationDTO itemEvaluationDTO : itemEvaluationDTOList) {
				if (itemEvaluationDTO != null) {
					this.itemEvaluationDAO.insertItemEvaluation(itemEvaluationDTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error("调用方法【addItemEvaluations】出错,错误:" + e);
			result.addErrorMessage("执行方法【addItemEvaluations】报错:" + e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ItemEvaluationDTO queryItemEvaluationById(ItemEvaluationDTO itemEvaluationDTO) {
		return null;
	}

	@Override
	public DataGrid<ItemEvaluationReplyDTO> queryItemEvaluationReplyList(ItemEvaluationReplyDTO itemEvaluationReplyDTO, Pager page) {
		LOGGER.info("=============开始查询卖家回复====================");
		DataGrid<ItemEvaluationReplyDTO> dg = new DataGrid<ItemEvaluationReplyDTO>();
		try {
			LOGGER.info("=============开始查询卖家回复列表====================");
			List<ItemEvaluationReplyDTO> rows = this.itemEvaluationDAO.queryItemEvaluationReplyList(itemEvaluationReplyDTO, page);
			LOGGER.info("=============完成查询卖家回复列表====================");
			LOGGER.info("=============开始查询卖家回复总条数====================");
			Long total = this.itemEvaluationDAO.queryTotal(itemEvaluationReplyDTO);
			LOGGER.info("=============完成查询卖家回复总条数====================");
			dg.setRows(rows);
			dg.setTotal(total);
		} catch (Exception e) {
			LOGGER.error("调用方法【queryItemEvaluationReplyList】出错,错误:" + e);
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束查询商品评价====================");
		}
		return dg;
	}

	/**
	 * <p>
	 * Discription:[根据评价信息唯一标识，修改评价状态]
	 * </p>
	 * 
	 * @param itemId       唯一标识
	 * @param flag         标记：0在用；1删除；2屏蔽
	 */
	public ExecuteResult<String> updFlagItemEvaluationById(Long itemId, Integer flag) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (null == itemId || 0 >= itemId || null == flag || 0 >= flag) {
			result.addErrorMsg("参数传递错误！");
		}
		try {
			this.itemEvaluationDAO.updFlagItemEvaluationById(itemId, flag);
			result.setResult("修改成功");
		} catch (Exception e) {
			LOGGER.error("调用方法【shieldItemEvaluationById】屏蔽评论信息 出错,错误:" + e);
			result.addErrorMessage(e.getMessage());
		}
		return result;

	}

	@Override
	public List<ItemEvaluationQueryOutDTO> querySkuEvaluationBySyncTime(Date syncTime) {
		return itemEvaluationDAO.querySkuEvaluationBySyncTime(syncTime);
	}

}

package cn.htd.marketcenter.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;
import cn.htd.goodscenter.service.promotionstock.PromotionSkuStockChangeExportService;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.DateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

public class CleanExpiredPurchaseScheduleTask implements
		IScheduleTaskDealMulti<PromotionInfoDTO> {

	protected static transient Logger logger = LoggerFactory
			.getLogger(CleanExpiredPromotionScheduleTask.class);

	/**
	 * 清除过期优惠券的过期天数
	 */
	private static final String EXPIRE_PROMOTION_PURCHASE_INTERVAL = "remove.redis.promotion.purchase.interval";

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	@Resource
	private PromotionSkuStockChangeExportService promotionSkuStockChangeExportService;

	@Resource
	private MarketCenterRedisDB marketRedisDB;

	@Override
	public Comparator<PromotionInfoDTO> getComparator() {
		return new Comparator<PromotionInfoDTO>() {
			public int compare(PromotionInfoDTO o1, PromotionInfoDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	/**
	 * 根据条件,查询当前调度服务器可处理的任务
	 *
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskQueueList
	 *            当前调度服务器,分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PromotionInfoDTO> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum)
			throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]",
				"CleanExpiredPurchaseScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign,
				"taskQueueNum:" + taskQueueNum,
				JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:"
						+ eachFetchDataNum);
		PromotionInfoDTO condition = new PromotionInfoDTO();
		Pager<PromotionInfoDTO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<PromotionInfoDTO> couponInfoDTOList = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<PromotionInfoDTO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskQueueList != null && taskQueueList.size() > 0) {
				for (TaskItemDefine taskItem : taskQueueList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				condition.setPromotionType(taskParameter);
				couponInfoDTOList = promotionInfoDAO
						.queryNeedCleanRedisPromotion5Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]",
					"CleanExpiredPromotionScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]",
					"CleanExpiredPromotionScheduleTask-selectTasks",
					JSONObject.toJSONString(couponInfoDTOList));
		}
		return couponInfoDTOList;
	}

	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
	 *
	 * @param tasks
	 *            任务数组
	 * @param ownSign
	 *            当前环境名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean execute(PromotionInfoDTO[] tasks, String ownSign)
			throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]",
				"CleanExpiredPromotionScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		int expirePromotionInterval = Integer.parseInt(SysProperties.getProperty(EXPIRE_PROMOTION_PURCHASE_INTERVAL));
		ExecuteResult<String> stockResult = new ExecuteResult<String>();
		List<PromotionStockChangeDTO> stockChangeList = new ArrayList<PromotionStockChangeDTO>();
		List<TimelimitedInfoDTO> oldTimelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
		Date expireDt = DateUtils.getSpecifiedDay(new Date(), -1 * expirePromotionInterval);
		TimelimitedInfoDTO timelimitedInfoDTO = null;
		TimelimitedInfoDTO timelimite = null;
		String timelimitedJSONStr = "";
		String promotionId = "";
		String promotionType = "";
		try {
			if (tasks != null && tasks.length > 0) {
				for (PromotionInfoDTO promotionInfoDTO : tasks) {
					promotionId = promotionInfoDTO.getPromotionId();
					if("3171623330242".equals(promotionId)){
						System.out.println(111);
					}
					promotionType = promotionInfoDTO.getPromotionType();
					if (dictionary.getValueByCode(
									DictionaryConst.TYPE_PROMOTION_TYPE,
									DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT)
							.equals(promotionType)) {
						timelimitedJSONStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
						timelimitedInfoDTO = JSON.parseObject(timelimitedJSONStr,TimelimitedInfoDTO.class);
						if(null != timelimitedInfoDTO){
							List list = timelimitedInfoDTO.getPromotionAccumulatyList();
							if (null != list && !list.isEmpty()) {
								for (int i = 0; i < list.size(); i++) {
									timelimite = JSONObject.toJavaObject((JSONObject) list.get(i),TimelimitedInfoDTO.class);
									if (expireDt.compareTo(timelimite.getEndTime()) > 0) {
										PromotionStockChangeDTO stockChangeDTO = timelimitedRedisHandle
												.getPromotionStockChangeList(promotionId,timelimite.getSkuCode(),promotionType);
										stockChangeList.add(stockChangeDTO);
									} else {
										oldTimelimitedInfoList.add(timelimite);
									}
								}
								if (!stockChangeList.isEmpty()) {
									stockResult = promotionSkuStockChangeExportService.batchReleaseStock(stockChangeList);
								}
								if (!oldTimelimitedInfoList.isEmpty()) {
									if(!stockChangeList.isEmpty() && "00000".equals(stockResult.getCode())){
										timelimitedInfoDTO.setPromotionAccumulatyList(oldTimelimitedInfoList);
										marketRedisDB.setHash(RedisConst.REDIS_TIMELIMITED,promotionId,JSON.toJSONString(timelimitedInfoDTO));
									}
								} else{
									if("00000".equals(stockResult.getCode())){
										timelimitedRedisHandle.deleteRedisTimelimitedInfo(promotionId);
										promotionInfoDAO.updateCleanedRedisPromotionStatus(promotionInfoDTO);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]",
					"CleanExpiredPurchaseScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]",
					"CleanExpiredPurchaseScheduleTask-execute",
					JSONObject.toJSONString(result));
		}
		return result;
	}
}

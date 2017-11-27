package cn.htd.marketcenter.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;
import cn.htd.goodscenter.service.promotionstock.PromotionSkuStockChangeExportService;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据系统时间、促销活动开始时间、结束时间和促销活动状态修改促销活动状态
 */
public class UpdatePromotionStatusScheduleTask implements IScheduleTaskDealMulti<PromotionInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdatePromotionStatusScheduleTask.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
	
	@Resource
	private PromotionSkuStockChangeExportService promotionSkuStockChangeExportService;

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
	public List<PromotionInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdatePromotionStatusScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		PromotionInfoDTO condition = new PromotionInfoDTO();
		Pager<PromotionInfoDTO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		//----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//		List<String> statusList = new ArrayList<String>();
		//----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
		List<String> verifyStatusList = new ArrayList<String>();
		List<PromotionInfoDTO> promotionInfoDTOList = null;
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
				//----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//				statusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
//						DictionaryConst.OPT_PROMOTION_STATUS_NO_START));
//				statusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
//						DictionaryConst.OPT_PROMOTION_STATUS_START));
				//----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
				verifyStatusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
				verifyStatusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS));
				condition.setVerifyStatusList(verifyStatusList);
				//----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//				condition.setStatusList(statusList);
				condition.setStartedStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_START));
				condition.setEndStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_END));
				condition.setDeletedStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
				//----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				promotionInfoDTOList = promotionInfoDAO.queryPromotionList4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "UpdatePromotionStatusScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdatePromotionStatusScheduleTask-selectTasks",
					JSONObject.toJSONString(promotionInfoDTOList));
		}
		return promotionInfoDTOList;
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
	@Override
	public boolean execute(PromotionInfoDTO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdatePromotionStatusScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		Date nowDt = new Date();
		List<PromotionInfoDTO> promotionInfoList = new ArrayList<PromotionInfoDTO>();
		List<PromotionStockChangeDTO> stockChangeList = null;
		String status = "";
		String timeStatus = "";
		String noStartStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
		String startStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_START);
		String endStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_END);

		try {
			if (tasks != null && tasks.length > 0) {
				for (PromotionInfoDTO promotionInfo : tasks) {
					status = promotionInfo.getStatus();
					if (nowDt.before(promotionInfo.getEffectiveTime())) {
						timeStatus = noStartStatus;
					} else if (!nowDt.before(promotionInfo.getEffectiveTime())
							&& !nowDt.after(promotionInfo.getInvalidTime())) {
						timeStatus = startStatus;
					} else {
						timeStatus = endStatus;
					}
					if (timeStatus.equals(status)) {
						continue;
					}
					promotionInfo.setStatus(timeStatus);
					promotionInfoList.add(promotionInfo);
					if (!dictionary
							.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
									DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
							.equals(promotionInfo.getPromotionType())) {
						timelimitedRedisHandle.updateRedisTimeilimitedStatus(promotionInfo);
					}
				}
				for (PromotionInfoDTO tmpPromotionInfo : promotionInfoList) {
					updatePromotionStatus(tmpPromotionInfo);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateExpiredBuyerCouponScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateExpiredBuyerCouponScheduleTask-execute",
					JSON.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新优惠活动状态
	 * 
	 * @param promotionInfo
	 * @throws Exception
	 */
	public void updatePromotionStatus(PromotionInfoDTO promotionInfo) throws Exception {
		PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();

		historyDTO.setPromotionId(promotionInfo.getPromotionId());
		historyDTO.setPromotionStatus(promotionInfo.getStatus());
		historyDTO.setPromotionStatusText(
				dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, promotionInfo.getStatus()));
		historyDTO.setCreateId(new Long("0"));
		historyDTO.setCreateName("sys");

		promotionInfo.setModifyId(0L);
		promotionInfo.setModifyName("sys");

		promotionInfoDAO.updatePromotionStatusById(promotionInfo);
		promotionStatusHistoryDAO.add(historyDTO);
	}

}

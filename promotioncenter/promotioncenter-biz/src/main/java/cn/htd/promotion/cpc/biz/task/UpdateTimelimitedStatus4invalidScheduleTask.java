package cn.htd.promotion.cpc.biz.task;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionTimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.handle.PromotionTimelimitedRedisHandle;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.DateUtil;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * job for 汇掌柜 更新秒杀活动状态
 * 根据系统时间、促销活动开始时间、结束时间和促销活动状态修改促销活动状态
 * 若秒杀商品已到结束时间，运营人员未进行下架操作，则超过24小时后，秒杀商品会自动下架
 */
public class UpdateTimelimitedStatus4invalidScheduleTask implements IScheduleTaskDealMulti<PromotionInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateTimelimitedStatus4invalidScheduleTask.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;

	@Resource
	private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
	
    @Resource
    private PromotionRedisDB promotionRedisDB;
    
	@Resource
	private PromotionTimelimitedInfoDAO promotionTimelimitedInfoDAO;

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
		logger.info("\n 方法:[{}],入参:[{}]", "UpdateTimelimitedStatusScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		PromotionInfoDTO condition = new PromotionInfoDTO();
		Pager<PromotionInfoDTO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<String> statusList = new ArrayList<String>();
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
				statusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_END));
				verifyStatusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
				condition.setVerifyStatusList(verifyStatusList);
				condition.setStatusList(statusList);
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				promotionInfoDTOList = promotionTimelimitedInfoDAO.queryPromotionList(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateTimelimitedStatusScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateTimelimitedStatusScheduleTask-selectTasks",
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
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateTimelimitedStatusScheduleTask-execute",JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		List<PromotionInfoDTO> promotionInfoList = new ArrayList<PromotionInfoDTO>();
		String status = "";
		String endStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,DictionaryConst.OPT_PROMOTION_STATUS_END);
		String showStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
		 Date expireDt = DateUtil.getSpecifiedDay(new Date(), -1 );//活动结束一天
		 TimelimitedInfoResDTO timelimitedInfoDTO = null;
		 String timelimitedJsonStr = "";
		try {
			if (tasks != null && tasks.length > 0) {
				for (PromotionInfoDTO promotionInfo : tasks) {
					status = promotionInfo.getStatus();
                    if(status.equals(endStatus) && expireDt.compareTo(promotionInfo.getInvalidTime()) > 0){//活动结束超过24小时需要自动下架
    					timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionInfo.getPromotionId());
    					timelimitedInfoDTO = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
    					timelimitedInfoDTO.setShowStatus(showStatus);
    					promotionTimelimitedRedisHandle.saveTimelimitedValidStatus2Redis(timelimitedInfoDTO);
       					promotionInfo.setShowStatus(showStatus);
    					promotionInfoList.add(promotionInfo);	
                    }
				}
				for (PromotionInfoDTO tmpPromotionInfo : promotionInfoList) {
					updatePromotionStatus(tmpPromotionInfo);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateTimelimitedStatusScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateTimelimitedStatusScheduleTask-execute",
					JSON.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新活动状态已结束超过24小时的促销活动状态为下架
	 * 
	 * @param promotionInfo
	 * @throws Exception
	 */
	public void updatePromotionStatus(PromotionInfoDTO promotionInfo) throws Exception {
		PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
		historyDTO.setPromotionId(promotionInfo.getPromotionId());
		historyDTO.setPromotionStatus(promotionInfo.getStatus());
		historyDTO.setPromotionStatusText(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, promotionInfo.getStatus()));
		historyDTO.setCreateId(new Long("0"));
		historyDTO.setCreateName("sys");
		promotionInfo.setModifyId(0L);
		promotionInfo.setModifyName("sys");
		promotionTimelimitedInfoDAO.updatePromotionShowStatusById(promotionInfo);
		promotionStatusHistoryDAO.add(historyDTO);
	}

}

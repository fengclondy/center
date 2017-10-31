/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	PromotionAddDailyTask.java
 * Author:   	jiangt
 * Date:     	2017年01月12日
 * Description: 会员等级日计算
 * History: 	
 * <author>		<time>      	<version>	<desc>
 */
package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 初始化redis
 * 
 * @author admin
 *
 */
public class PromotionClearDailyTask implements IScheduleTaskDealMulti<PromotionInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(PromotionClearDailyTask.class);

	@Resource
	private LuckDrawService luckDrawService;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;
	@Resource
	private PromotionLotteryCommonService promotionLotteryCommonService;
    @Resource
    private TimelimitedInfoService timelimitedInfoService;
    @Resource
    private GroupbuyingService groupbuyingService;

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
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "PromotionClearDailyTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		PromotionInfoDTO condition = new PromotionInfoDTO();
		Pager<PromotionInfoDTO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
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
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
//				condition.setPromotionType("21");
				promotionInfoDTOList = promotionInfoDAO.queryInitRedisPromotion4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "PromotionClearDailyTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "PromotionClearDailyTask-selectTasks",
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
		logger.info("\n 方法:[{}],入参:[{}][{}]", "PromotionClearDailyTask-execute", JSONObject.toJSONString(tasks),
				"ownSign:" + ownSign);
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				for (PromotionInfoDTO promotionInfoDTO : tasks) {
					
					if(TimelimitedConstants.PromotionTypeEnum.DRAW_LOTTERY.key().equals(promotionInfoDTO.getPromotionType())){//扭蛋机
						PromotionExtendInfoDTO dbo = luckDrawService.viewDrawLotteryInfo(promotionInfoDTO.getPromotionId());
						promotionLotteryCommonService.initPromotionLotteryRedisInfo(dbo);
					}else if(TimelimitedConstants.PromotionTypeEnum.TIMELIMITED.key().equals(promotionInfoDTO.getPromotionType())){//总部秒杀
			        	TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getSingleFullTimelimitedInfoByPromotionId(promotionInfoDTO.getPromotionId(),TimelimitedConstants.TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT, null);
			        	timelimitedInfoService.initTimelimitedInfoRedisInfo(timelimitedInfoResDTO);
					}else if(TimelimitedConstants.PromotionTypeEnum.GROUPBUYING.key().equals(promotionInfoDTO.getPromotionType())){//阶梯团
			        	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = groupbuyingService.getGroupbuyingInfoCmplByPromotionId(promotionInfoDTO.getPromotionId(), null);
			        	groupbuyingService.initGroupbuyingInfoRedisInfo(groupbuyingInfoCmplResDTO);
					}
					
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "PromotionClearDailyTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "PromotionClearDailyTask-execute", JSONObject.toJSONString(result));
		}
		return result;
	}
}

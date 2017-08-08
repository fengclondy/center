/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	BasePlacardScheduleTask.java
 * Author:   	jiangkun
 * Date:     	2016年11月24日
 * Description: 公告定时处理
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月24日	1.0			创建
 */

package cn.htd.basecenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BasePlacardDAO;
import cn.htd.basecenter.dao.BasePlacardMemberDAO;
import cn.htd.basecenter.dao.BasePlacardScopeDAO;
import cn.htd.basecenter.domain.BasePlacard;
import cn.htd.basecenter.dto.PlacardCondition;
import cn.htd.basecenter.enums.PlacardStatusEnum;
import cn.htd.basecenter.enums.PlacardTypeEnum;
import cn.htd.common.Pager;
import cn.htd.common.util.DateUtils;

/**
 * 公告定时处理
 * 
 * @author jiangkun
 */
public class PublishPlacardScheduleTask implements IScheduleTaskDealMulti<BasePlacard> {

	protected static transient Logger logger = LoggerFactory.getLogger(PublishPlacardScheduleTask.class);

	@Resource
	private BasePlacardDAO basePlacardDAO;

	@Resource
	private BasePlacardScopeDAO basePlacardScopeDAO;

	@Resource
	private BasePlacardMemberDAO basePlacardMemberDAO;

	@Override
	public Comparator<BasePlacard> getComparator() {
		return new Comparator<BasePlacard>() {
			public int compare(BasePlacard o1, BasePlacard o2) {
				Long placardId1 = o1.getPlacardId();
				Long placardId2 = o2.getPlacardId();
				return placardId1.compareTo(placardId2);
			}
		};
	}

	/**
	 * 根据条件，查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskItemList
	 *            当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BasePlacard> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}][{}][{}][{}]", "PublishPlacardScheduleTask-selectTasks",
				"taskParameter=" + taskParameter, "ownSign=" + ownSign, "taskQueueNum=" + taskQueueNum,
				JSONObject.toJSONString(taskItemList), "eachFetchDataNum=" + eachFetchDataNum);
		PlacardCondition condition = new PlacardCondition();
		Pager<PlacardCondition> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<BasePlacard> dealPlacardList = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<PlacardCondition>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				dealPlacardList = basePlacardDAO.queryPlacardList4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "BasePlacardScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BasePlacardScheduleTask-selectTasks",
					JSONObject.toJSONString(dealPlacardList));
		}
		return dealPlacardList;
	}

	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组，只能传递OBJECT[]
	 * 
	 * @param tasks
	 *            任务数组
	 * @param ownSign
	 *            当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(BasePlacard[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}]", "BasePlacardScheduleTask-execute", JSONObject.toJSONString(tasks),
				"ownSign=" + ownSign);
		boolean result = true;
		String sendType = "";
		String status = "";
		String newStatus = "";
		String expireTimeStr = "";
		String nowDateStr = DateUtils.format(new Date(), DateUtils.YMDHMS);
		Long sellerId = null;
		Long placardId = null;

		if (tasks != null && tasks.length > 0) {
			for (BasePlacard placard : tasks) {
				try {
					placardId = placard.getPlacardId();
					sendType = placard.getSendType();
					status = placard.getStatus();
					if (PlacardTypeEnum.PLATFORM.getValue().equals(sendType)) {
						if (PlacardStatusEnum.PENDING.getValue().equals(status)) {
							newStatus = PlacardStatusEnum.SENDING.getValue();
						} else {
							expireTimeStr = DateUtils.format(placard.getExpireTime(), DateUtils.YMDHMS);
							if (nowDateStr.compareTo(expireTimeStr) >= 0) {
								newStatus = PlacardStatusEnum.EXPIRED.getValue();
							} else {
								continue;
							}
						}
					} else if (PlacardTypeEnum.SELLER.getValue().equals(sendType)) {
						sellerId = placard.getSendSellerId();
						if (sellerId == null) {
							logger.warn("\n 商家公告ID=" + placardId + "的所属商家ID为空");
							continue;
						}
						newStatus = PlacardStatusEnum.SENT.getValue();
					}
					updatePlacardStatus(placard, newStatus);
				} catch (Exception e) {
					logger.error("\n 方法[{}]，异常：[{}]", "BasePlacardScheduleTask-execute",
							ExceptionUtils.getStackTraceAsString(e));
				}
			}
		}
		logger.info("\n 方法[{}]，出参：[{}]", "BasePlacardScheduleTask-execute", JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 更新公告状态
	 * 
	 * @param placard
	 * @param newStatus
	 */
	public void updatePlacardStatus(BasePlacard placard, String newStatus) {
		placard.setNewStatus(newStatus);
		placard.setModifyId(new Long("0"));
		placard.setModifyName("sys");
		basePlacardDAO.updatePlacardStatusById(placard);
	}
}

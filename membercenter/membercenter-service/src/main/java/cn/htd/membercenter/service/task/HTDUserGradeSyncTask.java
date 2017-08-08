/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	HTDUserGradeDailyTask.java
 * Author:   	jiangt
 * Date:     	2017年01月12日
 * Description: 会员等级日计算
 * History: 	
 * <author>		<time>      	<version>	<desc>
 */
package cn.htd.membercenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dto.BuyerFinanceHistoryDTO;
import cn.htd.membercenter.service.MemberGradeService;

public class HTDUserGradeSyncTask implements IScheduleTaskDealMulti<BuyerFinanceHistoryDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(HTDUserGradeSyncTask.class);

	@Resource
	private MemberGradeService memberGradeService;

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Override
	public Comparator<BuyerFinanceHistoryDTO> getComparator() {
		return new Comparator<BuyerFinanceHistoryDTO>() {
			@Override
			public int compare(BuyerFinanceHistoryDTO o1, BuyerFinanceHistoryDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
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
	 * @param taskQueueList
	 *            当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BuyerFinanceHistoryDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "HTDUserGradeSyncTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));

		ArrayList<BuyerFinanceHistoryDTO> userFinanceHisList = new ArrayList<BuyerFinanceHistoryDTO>();

		return userFinanceHisList;
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
	public boolean execute(BuyerFinanceHistoryDTO[] tasks, String ownSign) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}

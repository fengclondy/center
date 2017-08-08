package cn.htd.membercenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;

import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.dto.MemberBuyerPersonalInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.middleware.common.message.erp.RelationshipMessage;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

public class SendPersonBirthdayTask implements
		IScheduleTaskDealMulti<MemberBuyerPersonalInfoDTO> {
	protected static transient Logger logger = LoggerFactory
			.getLogger(SendPersonBirthdayTask.class);
	@Resource
	private AmqpTemplate amqpTemplateSendPersonBirthday;
	@Resource
	private MemberTaskDAO memberTaskDAO;

	@Override
	public Comparator<MemberBuyerPersonalInfoDTO> getComparator() {
		return new Comparator<MemberBuyerPersonalInfoDTO>() {
			@Override
			public int compare(MemberBuyerPersonalInfoDTO o1,
					MemberBuyerPersonalInfoDTO o2) {
				Long id1 = o1.getMemberId();
				Long id2 = o2.getMemberId();
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
	public List<MemberBuyerPersonalInfoDTO> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "SendPersonBirthdayTask",
				JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign),
				JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList),
				JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<MemberBuyerPersonalInfoDTO> personalInfoList = new ArrayList<MemberBuyerPersonalInfoDTO>();
		// List<Long> ids = null;
		@SuppressWarnings("rawtypes")
		Pager pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<MemberDownDTO>();
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
				personalInfoList = memberTaskDAO.selectSendBirthday(condition,
						pager);
				int perSize = personalInfoList.size();
				if (perSize > 0) {
					for (int i = 0; i < perSize; i++) {
						String birthday = personalInfoList.get(i).getBirthday();
						String mobilePhone = personalInfoList.get(i)
								.getArtificialPersonMobile();
						// 发送短信接口

					}
				}
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]",
					"SendPersonBirthdayTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]",
					"SendPersonBirthdayTask-selectTasks",
					JSONObject.toJSONString(personalInfoList));
		}
		return null;
	}

	@Override
	public boolean execute(MemberBuyerPersonalInfoDTO[] tasks, String ownSign)
			throws Exception {
		int len = tasks.length;
		MemberBuyerPersonalInfoDTO memberBuyerPersonalInfoDTO = null;
		for (int i = 0; i < len; i++) {
			memberBuyerPersonalInfoDTO = tasks[i];
			RelationshipMessage msg = new RelationshipMessage();
			BeanUtils.copyProperties(memberBuyerPersonalInfoDTO, msg);
			amqpTemplateSendPersonBirthday.convertAndSend(
					"membercenter-sendPersonBirthday-exchange-key", msg);
		}
		return true;
	}

}

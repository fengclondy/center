/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	AddressErpDownScheduleTask.java
 * Author:   	youyj
 * Date:     	2016年12月05日
 * Description: 地址信息定时处理
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * youyj		2016年12月05日	1.0			创建
 */
package cn.htd.basecenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.basecenter.common.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseAddressDAO;
import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.domain.ErpArea;
import cn.htd.basecenter.enums.ErpStatusEnum;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.DictionaryUtils;

/**
 * 地址信息定时处理
 * 
 * @author youyj
 */
public class AddressErpDownScheduleTask implements IScheduleTaskDealMulti<BaseAddress> {

	protected static transient Logger logger = LoggerFactory.getLogger(AddressErpDownScheduleTask.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private BaseAddressDAO baseAddressDAO;

	@Resource
	private AmqpTemplate amqpTemplate;

	@Override
	public Comparator<BaseAddress> getComparator() {
		return new Comparator<BaseAddress>() {
			public int compare(BaseAddress o1, BaseAddress o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	/**
	 * 根据条件查询当前调度服务器可处理的任务
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
	public List<BaseAddress> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}][{}][{}][{}]", "AddressErpDownScheduleTask-selectTasks",
				"taskParameter=" + taskParameter, "ownSign=" + ownSign, "taskQueueNum=" + taskQueueNum,
				JSONObject.toJSONString(taskItemList), "eachFetchDataNum=" + eachFetchDataNum);
		BaseAddress condition = new BaseAddress();
		List<String> taskIdList = new ArrayList<String>();
		List<BaseAddress> dealAddressList = null;
		Pager<BaseAddress> pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<BaseAddress>();
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
				condition.setErpStatus(ErpStatusEnum.PENDING.getValue());
				condition.setErpStatus1(ErpStatusEnum.FAILURE.getValue());
				dealAddressList = baseAddressDAO.queryAddressList4Erp(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "AddressErpDownScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "AddressErpDownScheduleTask-selectTasks",
					JSONObject.toJSONString(dealAddressList));
		}
		return dealAddressList;
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
	public boolean execute(BaseAddress[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}]", "AddressErpDownScheduleTask-execute", JSONObject.toJSONString(tasks),
				"ownSign=" + ownSign);
		boolean result = true;
		MQSendUtil mqSender = new MQSendUtil();
		ErpArea erpDownObj = null;

		try {
			mqSender.setAmqpTemplate(amqpTemplate);
			if (tasks != null && tasks.length > 0) {
				for (BaseAddress address : tasks) {
					if (StringUtils.isBlank(address.getCode()) || StringUtils.isBlank(address.getName())) {
						continue;
					}
					erpDownObj = convert2ErpArea(address);
					// 发送MQ
					mqSender.sendToMQWithRoutingKey(erpDownObj, Constants.ADDRESS_DOWN_ERP_ROUTING_KEY);
					updateAddressStatus(address);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法[{}]，异常：[{}]", "AddressErpDownScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "AddressErpDownScheduleTask-execute", JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 将baseAddress转换成ERP下行用对象
	 * 
	 * @param baseAddress
	 * @return
	 */
	private ErpArea convert2ErpArea(BaseAddress baseAddress) {
		ErpArea erpDownObj = new ErpArea();
		int isLastLevel = dictionary
				.getValueByCode(DictionaryConst.TYPE_ADDRESS_LEVEL, DictionaryConst.OPT_ADDRESS_LEVEL_TOWN)
				.equals(String.valueOf(baseAddress.getLevel())) ? YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue();
		Date erpDownTime = baseAddress.getErpDownTime();
		erpDownObj.setAreaCode(baseAddress.getCode());
		erpDownObj.setAreaName(baseAddress.getName());
		erpDownObj.setIsLastLevel(String.valueOf(isLastLevel));
		// 省时，父编码保存自身
		erpDownObj.setAreaFatherCode(
				StringUtils.isEmpty(baseAddress.getParentCode()) ? baseAddress.getCode() : baseAddress.getParentCode());
		erpDownObj.setIsUpdateFlag(erpDownTime == null ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue());
		return erpDownObj;
	}

	/**
	 * 更新地址信息下行状态：下行中
	 * 
	 * @param address
	 */
	public void updateAddressStatus(BaseAddress address) {
		address.setErpStatus(ErpStatusEnum.DOWNING.getValue());
		address.setErpDownTime(new Date());
		address.setErpErrorMsg("");
		address.setErpDownTimes(address.getErpDownTimes() + 1);
		baseAddressDAO.updateAddressStatusById(address);
	}
}

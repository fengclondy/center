/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	BaseAddressScheduleTask.java
 * Author:   	jiangkun
 * Date:     	2016年11月24日
 * Description: 地址信息定时处理
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月24日	1.0			创建
 */

package cn.htd.basecenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseAddressDAO;
import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.enums.AddressLevelEnum;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;

/**
 * 地址信息定时处理
 * 
 * @author jiangkun
 */
public class UpdateAddress2RedisScheduleTask implements IScheduleTaskDealMulti<BaseAddress> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateAddress2RedisScheduleTask.class);

	// Redis地址数据
	private static final String REDIS_ADDRESS = "B2B_MIDDLE_ADDRESS";

	// Redis地址关系数据
	private static final String REDIS_ADDRESS_RELATION = "B2B_MIDDLE_ADDRESS_RELATION";

	@Resource
	private BaseAddressDAO baseAddressDAO;

	@Resource
	private RedisDB redisDB;

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
		logger.info("\n 方法[{}]，入参：[{}][{}][{}][{}][{}]", "UpdateAddress2RedisScheduleTask-selectTasks",
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
				dealAddressList = baseAddressDAO.queryAddressList4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "UpdateAddress2RedisScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "UpdateAddress2RedisScheduleTask-selectTasks",
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
		logger.info("\n 方法[{}]，入参：[{}][{}]", "UpdateAddress2RedisScheduleTask-execute", JSONObject.toJSONString(tasks),
				"ownSign=" + ownSign);
		boolean result = true;
		String code = "";
		String name = "";
		int level = 0;
		String subKey = "";
		String parentCode = "";
		int count = 0;
		List<Long> idList = new ArrayList<Long>();

		try {
			if (tasks != null && tasks.length > 0) {
				for (BaseAddress address : tasks) {
					code = address.getCode();
					name = address.getName();
					level = address.getLevel();
					parentCode = address.getParentCode();
					if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
						continue;
					}
					redisDB.setHash(REDIS_ADDRESS, code, name + "&" + level + "&" + parentCode);
					subKey = getAddressRelation4Redis(parentCode);
					if (StringUtils.isBlank(parentCode)) {
						parentCode = "province";
					}
					redisDB.setHash(REDIS_ADDRESS_RELATION, parentCode, subKey);
					if (AddressLevelEnum.TOWN.getValue() != level) {
						if (YesNoEnum.NO.getValue() == address.getDeleteFlag()) {
							subKey = getAddressRelation4Redis(code);
							redisDB.setHash(REDIS_ADDRESS_RELATION, code, subKey);
						} else {
							redisDB.setHash(REDIS_ADDRESS_RELATION, code, "");
						}
					}
					idList.add(address.getId());
					count++;
					if (count % 500 == 0 || count == tasks.length) {
						updateAddressRedisStatus(idList);
						idList = new ArrayList<Long>();
					}
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法[{}]，异常：[{}]", "BaseAddressScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "UpdateAddress2RedisScheduleTask-execute",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新地址信息Redis更新状态
	 * 
	 * @param idList
	 */
	public void updateAddressRedisStatus(List<Long> idList) {
		baseAddressDAO.updateRedisFlagById(idList);
	}

	/**
	 * 根据地址编码将下级地址信息保存到Redis中
	 * 
	 * @param parentCode
	 * @return
	 */
	private String getAddressRelation4Redis(String parentCode) {
		String subKey = "";
		String subCode = "";
		List<BaseAddress> subAddressList = null;

		subAddressList = baseAddressDAO.queryBaseAddressListByParentCode(parentCode);
		if (subAddressList != null && subAddressList.size() > 0) {
			for (BaseAddress subAddress : subAddressList) {
				subCode = subAddress.getCode();
				if (!StringUtils.isBlank(subCode)) {
					subKey += "," + subCode;
				}
			}
			subKey = subKey.substring(1);
		}
		return subKey;
	}
}

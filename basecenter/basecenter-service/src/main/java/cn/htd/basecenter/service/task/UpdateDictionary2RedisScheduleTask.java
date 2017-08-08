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
import cn.htd.basecenter.dao.BaseDictionaryDAO;
import cn.htd.basecenter.domain.BaseDictionary;
import cn.htd.basecenter.dto.BaseDictionaryDTO;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;

/**
 * 地址信息定时处理
 * 
 * @author jiangkun
 */
public class UpdateDictionary2RedisScheduleTask implements IScheduleTaskDealMulti<BaseDictionary> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateDictionary2RedisScheduleTask.class);

	// Redis字典数据
	private static final String REDIS_DICTIONARY = "B2B_MIDDLE_DICTIONARY";

	// Redis字典类型数据
	private static final String REDIS_DICTIONARY_TYPE = "B2B_MIDDLE_DICTIONARY_TYPE";

	@Resource
	private BaseDictionaryDAO baseDictionaryDAO;

	@Resource
	private RedisDB redisDB;

	@Override
	public Comparator<BaseDictionary> getComparator() {
		return new Comparator<BaseDictionary>() {
			public int compare(BaseDictionary o1, BaseDictionary o2) {
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
	public List<BaseDictionary> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}][{}][{}][{}]", "UpdateDictionary2RedisScheduleTask-selectTasks",
				"taskParameter=" + taskParameter, "ownSign=" + ownSign, "taskQueueNum=" + taskQueueNum,
				JSONObject.toJSONString(taskItemList), "eachFetchDataNum=" + eachFetchDataNum);
		BaseDictionary condition = new BaseDictionary();
		List<String> taskIdList = new ArrayList<String>();
		List<BaseDictionary> dealDictionaryList = null;
		Pager<BaseDictionary> pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<BaseDictionary>();
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
				dealDictionaryList = baseDictionaryDAO.queryDictionaryList4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "UpdateDictionary2RedisScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "UpdateDictionary2RedisScheduleTask-selectTasks",
					JSONObject.toJSONString(dealDictionaryList));
		}
		return dealDictionaryList;
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
	public boolean execute(BaseDictionary[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}]", "UpdateDictionary2RedisScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign=" + ownSign);
		boolean result = true;
		String parentCode = "";
		String code = "";
		String name = "";
		String value = "";
		String subKey = "";
		int count = 0;
		List<Long> idList = new ArrayList<Long>();

		try {
			if (tasks != null && tasks.length > 0) {
				for (BaseDictionary dict : tasks) {
					parentCode = dict.getParentCode();
					code = StringUtils.trim(dict.getCode());
					name = StringUtils.trim(dict.getName()) + "&" + StringUtils.trim(dict.getRemark());
					value = StringUtils.trim(dict.getValue());
					if (!StringUtils.isBlank(parentCode)) {
						if (StringUtils.isBlank(code) || StringUtils.isBlank(value)) {
							continue;
						}
					}
					if (!StringUtils.isBlank(parentCode)) {
						redisDB.setHash(REDIS_DICTIONARY + "_" + parentCode, code + "&" + value, name);
						subKey = getDictionaryTypeStr4Redis(parentCode);
						redisDB.setHash(REDIS_DICTIONARY_TYPE, parentCode, subKey);
					} else {
						if (YesNoEnum.YES.getValue() == dict.getStatus()) {
							subKey = getDictionaryTypeStr4Redis(code);
							redisDB.setHash(REDIS_DICTIONARY_TYPE, code, subKey);
						} else {
							redisDB.setHash(REDIS_DICTIONARY_TYPE, code, "");
						}
					}
					idList.add(dict.getId());
					count++;
					if (count % 500 == 0 || count == tasks.length) {
						updateRedisStatusFlag(idList);
						idList = new ArrayList<Long>();
					}
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法[{}]，异常：[{}]", "UpdateDictionary2RedisScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "UpdateDictionary2RedisScheduleTask-execute",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新字典信息Redis状态
	 * 
	 * @param idList
	 */
	public void updateRedisStatusFlag(List<Long> idList) {
		baseDictionaryDAO.updateRedisFlagById(idList);
	}

	/**
	 * 根据字典编码将下级字典信息保存到Redis中
	 * 
	 * @param parentCode
	 * @return
	 */
	private String getDictionaryTypeStr4Redis(String parentCode) {
		String subKey = "";
		String subCode = "";
		String subValue = "";
		List<BaseDictionary> subDictionaryList = null;
		BaseDictionaryDTO dictDTO = new BaseDictionaryDTO();

		dictDTO.setParentCode(parentCode);
		dictDTO.setStatus(YesNoEnum.YES.getValue());
		subDictionaryList = baseDictionaryDAO.queryBaseDictionaryList(dictDTO);
		if (subDictionaryList != null && subDictionaryList.size() > 0) {
			for (BaseDictionary subDict : subDictionaryList) {
				subCode = StringUtils.trim(subDict.getCode());
				subValue = StringUtils.trim(subDict.getValue());
				if (!StringUtils.isBlank(subCode) && !StringUtils.isBlank(subValue)) {
					subKey += "," + subCode + "&" + subValue;
				}
			}
			subKey = subKey.substring(1);
		}
		return subKey;
	}
}

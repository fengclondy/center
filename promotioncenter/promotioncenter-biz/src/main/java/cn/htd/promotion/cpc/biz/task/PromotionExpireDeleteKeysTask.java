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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 活动过期后删除相关的key
 * 
 * @author admin
 *
 */
public class PromotionExpireDeleteKeysTask implements IScheduleTaskDealMulti<Object> {

	protected static transient Logger logger = LoggerFactory.getLogger(PromotionExpireDeleteKeysTask.class);

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	public Comparator getComparator() {
		// TODO Auto-generated method stub
		return null;
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
	public List<Object> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "PromotionExpireDeleteKeysTask-selectTasks 活动过期后删除相关的key-开始执行",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		try {
			Date nowDt = new Date();
			Map<String, String> indexMap = promotionRedisDB
					.getHashOperations(RedisConst.REDIS_LOTTERY_INDEX);
			if (null != indexMap && !indexMap.isEmpty()) {
				for (Map.Entry<String, String> m : indexMap.entrySet()) {
					String key = m.getKey();
					String value = m.getValue();
					String[] keyArray = key.split("_");
					if (keyArray == null || keyArray.length < 2) {
						continue;
					}
					String promotionId = keyArray[1];
					String[] valueArray = value.split("_");
					if (null == valueArray || valueArray.length < 2) {
						continue;
					}
					Long endTime = new Long(valueArray[1]);
					Date endDate = new Date(endTime);
					if(nowDt.after(endDate)){
						List<String> awardKeyList = promotionRedisDB.getHashFields(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO);
						if(!CollectionUtils.isEmpty(awardKeyList)){
							for(String awardKey : awardKeyList){
								String[] awardKeyArray = awardKey.split("_");
								if(null == awardKeyArray || awardKeyArray.length<3){
									continue;
								}
								if(promotionId.equals(awardKeyArray[0])){
									//活动过期后删除粉丝中奖信息
									promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO, awardKey);
								}
							}
						}
					}
				}
			}
		} finally {
			logger.info("\n 方法:[{}]执行结束", "PromotionExpireDeleteKeysTask-selectTasks");
		}
		return null;
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
	public boolean execute(Object[] tasks, String ownSign) throws Exception {
		return false;
	}
}

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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;

/**
 * 每日初始化投票值 1.BUYER_PARTAKE_TIMES 粉丝活动粉丝当日剩余参与次数 2.BUYER_WINNING_TIMES 粉丝当日中奖次数
 * 3.B2B_MIDDLE_LOTTERY_SELLER_WINED_TIMES_{抽奖活动的promotionId}_{店铺编码}
 * 抽奖活动店铺每日中奖次数
 * 
 * @author admin
 *
 */
public class PromotionAddDailyTask implements IScheduleTaskDealMulti<Long> {

	protected static transient Logger logger = LoggerFactory.getLogger(PromotionAddDailyTask.class);

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	public Comparator<Long> getComparator() {
		return new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return o1.compareTo(o2);
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
	public List<Long> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "HTDUserGradeDailyTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		Date jobDate = new Date();
		Map<String, String> keyset = promotionRedisDB.getHashOperations(RedisConst.REDIS_LOTTERY_VALID);
		Set<Entry<String, String>> mset = keyset.entrySet();
		String promotionId = "";
		Set<String> sset = null;
		String buyerDailyDrawTimes = "";
		Integer buyerDailyDrawTimesint = 0;
		String buyerDailyWinningTimes = "";
		String swt = "";
		String BUYER_SHARE_TIMES = "";
		String BUYER_SHARE_EXTRA_PARTAKE_TIMES = "";
		Integer BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
		Integer BUYER_SHARE_TIMESint = 0;
		String BUYER_TOP_EXTRA_PARTAKE_TIMES = "";
		Integer BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
		for (Entry<String, String> entry : mset) {
			if (entry.getValue().equals("3")) {
				promotionId = entry.getKey();
				// 粉丝每日抽奖次数限制
				buyerDailyDrawTimes = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES);
				buyerDailyWinningTimes = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_DAILY_WINNING_TIMES);
				BUYER_SHARE_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES);
				BUYER_TOP_EXTRA_PARTAKE_TIMES = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
						RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES);
				if (StringUtils.isEmpty(BUYER_SHARE_EXTRA_PARTAKE_TIMES)) {
					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = 0;
				} else {
					BUYER_SHARE_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_SHARE_EXTRA_PARTAKE_TIMES);
				}
				if (StringUtils.isEmpty(BUYER_TOP_EXTRA_PARTAKE_TIMES)) {
					BUYER_TOP_EXTRA_PARTAKE_TIMESint = 0;
				} else {
					BUYER_TOP_EXTRA_PARTAKE_TIMESint = Integer.parseInt(BUYER_TOP_EXTRA_PARTAKE_TIMES);
				}
				if (StringUtils.isEmpty(buyerDailyDrawTimes)) {
					buyerDailyDrawTimes = "0";
					buyerDailyDrawTimesint = 0;
				} else {
					buyerDailyDrawTimesint = Integer.valueOf(buyerDailyDrawTimes);
				}
				if (StringUtils.isEmpty(buyerDailyWinningTimes)) {
					buyerDailyWinningTimes = "0";
				}
				sset = promotionRedisDB.getStringRedisTemplate()
						.keys(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_*");
				for (String b2bMiddleLotteryBuyerTimesInfo : sset) {
					if (promotionRedisDB.existsHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES)) {
						BUYER_SHARE_TIMES = promotionRedisDB.getHash(b2bMiddleLotteryBuyerTimesInfo,
								RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);

						if (StringUtils.isEmpty(BUYER_SHARE_TIMES)) {
							BUYER_SHARE_TIMESint = 0;
						} else {
							BUYER_SHARE_TIMESint = Integer.valueOf(BUYER_SHARE_TIMES);
						}
						if (BUYER_TOP_EXTRA_PARTAKE_TIMESint
								.compareTo(BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint) > 0) {
							buyerDailyDrawTimesint = buyerDailyDrawTimesint
									+ (BUYER_SHARE_EXTRA_PARTAKE_TIMESint * BUYER_SHARE_TIMESint);
						} else {
							buyerDailyDrawTimesint = buyerDailyDrawTimesint + BUYER_TOP_EXTRA_PARTAKE_TIMESint;
						}
					}

					// 粉丝活动粉丝当日剩余参与次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES, buyerDailyDrawTimesint.toString());
					// 粉丝当日中奖次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES, buyerDailyWinningTimes);
				}

				sset = promotionRedisDB.getStringRedisTemplate()
						.keys(RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES + "_" + promotionId + "_*");
				for (String string : sset) {
					swt = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
							RedisConst.REDIS_LOTTERY_SELLER_DAILY_TOTAL_TIMES);
					promotionRedisDB.set(string, swt);
				}
			}
		}


		ArrayList<Long> list = new ArrayList<Long>();
		return list;
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
	public boolean execute(Long[] tasks, String ownSign) throws Exception {

		return false;
	}
}


package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerUseTimelimitedLogDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.common.util.RedissonClientUtil;

/**
 * 释放秒杀活动预锁1小时以上，但是没提交订单而导致被锁定的库存
 */
public class ReleaseTimelimitedLockedNoOrderStock4hlScheduleTask
		implements IScheduleTaskDealMulti<BuyerUseTimelimitedLogDMO> {

	protected static transient Logger logger = LoggerFactory
			.getLogger(ReleaseTimelimitedLockedNoOrderStock4hlScheduleTask.class);

	/*
	 * RedisMessageId数据
	 */
	private static final String REDIS_MESSAGE_ID_KEY = "B2B_MIDDLE_MESSAGEID_MSB_SEQ";

	// 默认15分钟未提交的订单释放库存
	public static final String RELEASE_LOCK_STOCK_TIME = "1";

	/**
	 * 清除Reids中预锁但是没有提交订单的库存信息的时间间隔(单位：分钟)
	 */
	private static final String RELEASE_TIMELIMITED_LOCKED_STOCK_INTERVAL = "release.timelimited.locked.stock.interval";

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private RedisDB redisDB;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private BuyerUseTimelimitedLogDAO buyerUseTimelimitedLogDAO;

	@Autowired
	private RedissonClientUtil redissonClientUtil;

	@Override
	public Comparator<BuyerUseTimelimitedLogDMO> getComparator() {
		return new Comparator<BuyerUseTimelimitedLogDMO>() {
			public int compare(BuyerUseTimelimitedLogDMO o1, BuyerUseTimelimitedLogDMO o2) {
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
	public List<BuyerUseTimelimitedLogDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]",
				"ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks", "taskParameter:" + taskParameter,
				"ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum, JSONObject.toJSONString(taskQueueList),
				"eachFetchDataNum:" + eachFetchDataNum);
		BuyerUseTimelimitedLogDMO condition = new BuyerUseTimelimitedLogDMO();
		Pager<BuyerUseTimelimitedLogDMO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<BuyerUseTimelimitedLogDMO> BuyerUseTimelimitedLogDMOList = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<BuyerUseTimelimitedLogDMO>();
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
				condition.setUseType(Constants.SECKILL_RESERVE);
				condition.setReleaseStockInterval(RELEASE_LOCK_STOCK_TIME);
				BuyerUseTimelimitedLogDMOList = buyerUseTimelimitedLogDAO
						.queryNeedReleaseTimelimitedStock4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks",
					JSONObject.toJSONString(BuyerUseTimelimitedLogDMOList));
		}
		return BuyerUseTimelimitedLogDMOList;
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
	public boolean execute(BuyerUseTimelimitedLogDMO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		String reverseStatus = Constants.SECKILL_RESERVE;
		String releaseStatus = Constants.SECKILL_RELEASE;
		String seckillLockNo = "";
		String promotionId = "";
		String buyerCode = "";
		String useLogJsonStr = "";
		RLock rLock = null;
		Integer skuCount = 0;
		// long buyerTimelimitedCount = 0L;
		BuyerUseTimelimitedLogDMO redisUseLog = null;
		RedissonClient redissonClient = redissonClientUtil.getInstance();

		try {
			if (tasks != null && tasks.length > 0) {
				for (BuyerUseTimelimitedLogDMO useTimelimitedLog : tasks) {
					seckillLockNo = useTimelimitedLog.getSeckillLockNo();
					promotionId = useTimelimitedLog.getPromotionId();
					buyerCode = useTimelimitedLog.getBuyerCode();
					String lockKey = Constants.REDIS_KEY_PREFIX_STOCK + String.valueOf(promotionId); // 竞争资源标志
					rLock = redissonClient.getLock(lockKey);
					/** 上锁 **/
					rLock.lock();
					useLogJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG,
							buyerCode + "&" + promotionId);
					redisUseLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLogDMO.class);
					if (redisUseLog == null) {
						buyerUseTimelimitedLogDAO.updateTimelimitedReleaseStockStatus(useTimelimitedLog);
						continue;
					}
					String reserveHashKey = RedisConst.PROMOTION_REIDS_BUYER_TIMELIMITED_RESERVE_HASH + "_"
							+ promotionId;
					String reserveFlag = promotionRedisDB.getHash(reserveHashKey, buyerCode);
					logger.info("秒杀锁定reserveFlag:{},promotionId{}", reserveFlag, promotionId);
					if (StringUtils.isNotBlank(reserveFlag)) {
						skuCount = redisUseLog.getUsedCount();
						String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
						promotionRedisDB.incrHashBy(timelimitedResultKey,
								RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT, skuCount);
						promotionRedisDB.incrHashBy(timelimitedResultKey,
								RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT, -1);
						promotionRedisDB.incrHashBy(timelimitedResultKey,
								RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT, -1);
						// buyerTimelimitedCount = promotionRedisDB.incrHashBy(
						// RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_COUNT,
						// buyerCode + "&" + promotionId,
						// skuCount * -1);
						// if (buyerTimelimitedCount < 0) {
						// promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_COUNT,
						// buyerCode + "&" + promotionId, "0");
						// }
						// 该秒杀活动对应库存队列
						String timeLimitedQueueKey = RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_QUEUE + "_"
								+ promotionId;
						for (int i = 0; i < skuCount; i++) {
							// 释放库存则往队列插入一个请求
							promotionRedisDB.rpush(timeLimitedQueueKey, promotionId);
						}
						redisUseLog.setUseType(releaseStatus);
						redisUseLog.setModifyTime(new Date());
						promotionRedisDB.delHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG,
								buyerCode + "&" + promotionId);

						promotionRedisDB.delHash(reserveHashKey, buyerCode);
						promotionRedisDB.tailPush(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG,
								JSON.toJSONString(redisUseLog));
						logger.info("秒杀锁定useTimelimitedLog:{}", JSONObject.toJSONString(useTimelimitedLog));
						buyerUseTimelimitedLogDAO.updateTimelimitedReleaseStockStatus(useTimelimitedLog);
					}
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
					JSONObject.toJSONString(result));
			/** 释放锁资源 **/
			if (rLock != null) {
				rLock.unlock();
			}
		}
		return result;
	}

}

package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.promotion.cpc.biz.dao.BuyerUseTimelimitedLogDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import redis.clients.jedis.Jedis;

/**
 * 定时任务 更新优惠券状态进DB处理
 */
public class UpdateBuyerSeckillUseLog4hlScheduleTask implements IScheduleTaskDealMulti<BuyerUseTimelimitedLogDMO> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateBuyerSeckillUseLog4hlScheduleTask.class);

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private BuyerUseTimelimitedLogDAO buyerUseTimelimitedLogDAO;

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
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerSeckillUseLogScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		List<BuyerUseTimelimitedLogDMO> dealTargetList = new ArrayList<BuyerUseTimelimitedLogDMO>();
		try {
			if (promotionRedisDB.getLlen(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG) > 0) {
				dealTargetList.add(new BuyerUseTimelimitedLogDMO());
			}
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerSeckillUseLogScheduleTask-selectTasks",
					JSON.toJSONString(dealTargetList));
		}
		return dealTargetList;
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
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateBuyerSeckillUseLogScheduleTask-execute", JSON.toJSONString(tasks),
				"ownSign:" + ownSign);
		BuyerUseTimelimitedLogDMO dealTargetInfo = null;
		Jedis jedis = null;
		int addCount = 0;
		String useLogStr = "";
		try {
			while (jedis.llen(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG) > 0) {
				useLogStr = jedis.lpop(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG);
				dealTargetInfo = JSON.parseObject(useLogStr, BuyerUseTimelimitedLogDMO.class);
				if (dealTargetInfo == null) {
					continue;
				}
				buyerUseTimelimitedLogDAO.add(dealTargetInfo);
				addCount++;
			}
		} finally {
			logger.info("\n 方法:[{}],插入件数:[{}]", "UpdateBuyerSeckillUseLogScheduleTask-execute", addCount);
		}
		return true;
	}
}

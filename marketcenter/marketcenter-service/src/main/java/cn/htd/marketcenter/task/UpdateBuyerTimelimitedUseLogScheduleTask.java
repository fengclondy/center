package cn.htd.marketcenter.task;

import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.BuyerUseTimelimitedLogDAO;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 定时任务 更新优惠券状态进DB处理
 */
public class UpdateBuyerTimelimitedUseLogScheduleTask implements IScheduleTaskDealMulti<BuyerUseTimelimitedLog> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateBuyerTimelimitedUseLogScheduleTask.class);

	@Resource
	private MarketCenterRedisDB marketRedisDB;

	@Resource
	private BuyerUseTimelimitedLogDAO buyerUseTimelimitedLogDAO;

	@Override
	public Comparator<BuyerUseTimelimitedLog> getComparator() {
		return new Comparator<BuyerUseTimelimitedLog>() {
			public int compare(BuyerUseTimelimitedLog o1, BuyerUseTimelimitedLog o2) {
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
	public List<BuyerUseTimelimitedLog> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerTimelimitedUseLogScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		List<BuyerUseTimelimitedLog> dealTargetList = new ArrayList<BuyerUseTimelimitedLog>();
		try {
			if (marketRedisDB.getLlen(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG) > 0) {
				dealTargetList.add(new BuyerUseTimelimitedLog());
			}
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerTimelimitedUseLogScheduleTask-selectTasks",
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
	public boolean execute(BuyerUseTimelimitedLog[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateBuyerTimelimitedUseLogScheduleTask-execute",
				JSON.toJSONString(tasks), "ownSign:" + ownSign);
		BuyerUseTimelimitedLog dealTargetInfo = null;
		Jedis jedis= null;
		int addCount = 0;
		String useLogStr = "";
		try {
			jedis = marketRedisDB.getResource();
			while (jedis.llen(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG) > 0) {
				useLogStr = jedis.lpop(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG);
				dealTargetInfo = JSON.parseObject(useLogStr, BuyerUseTimelimitedLog.class);
				if (dealTargetInfo == null) {
					continue;
				}
				buyerUseTimelimitedLogDAO.add(dealTargetInfo);
				addCount++;
			}
		} finally {
			marketRedisDB.releaseResource(jedis);
			logger.info("\n 方法:[{}],插入件数:[{}]", "UpdateBuyerTimelimitedUseLogScheduleTask-execute", addCount);
		}
		return true;
	}
}

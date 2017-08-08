
package cn.htd.marketcenter.task;

import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
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
public class UpdateBuyerCouponScheduleTask implements IScheduleTaskDealMulti<BuyerCouponInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateBuyerCouponScheduleTask.class);

	@Resource
	private MarketCenterRedisDB marketRedisDB;

	@Resource
	private BuyerCouponInfoDAO buyerCouponInfoDAO;

	@Override
	public Comparator<BuyerCouponInfoDTO> getComparator() {
		return new Comparator<BuyerCouponInfoDTO>() {
			public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
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
	public List<BuyerCouponInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerCouponScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		List<BuyerCouponInfoDTO> dealTargetList = new ArrayList<BuyerCouponInfoDTO>();
		try {
			if (marketRedisDB.getSetLen(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST) > 0) {
				dealTargetList.add(new BuyerCouponInfoDTO());
			}
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerCouponScheduleTask-selectTasks",
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
	public boolean execute(BuyerCouponInfoDTO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateBuyerCouponScheduleTask-execute", JSON.toJSONString(tasks),
				"ownSign:" + ownSign);
		boolean result = true;
		String buyerCouponStr = "";
		String dealTargetStr = "";
		BuyerCouponInfoDTO dealTargetInfo = null;
		int updateNum = 0;
		Jedis jedis = null;
		try {
			jedis = marketRedisDB.getResource();
			while (jedis.scard(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST) > 0) {
				buyerCouponStr = jedis.spop(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST);
				if (StringUtils.isEmpty(buyerCouponStr) || "nil".equals(buyerCouponStr)) {
					continue;
				}
				while (jedis.llen(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCouponStr) > 0) {
					dealTargetStr = jedis.lpop(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" +
							buyerCouponStr);
					dealTargetInfo = JSON.parseObject(dealTargetStr, BuyerCouponInfoDTO.class);
					if (dealTargetInfo == null) {
						continue;
					}
					try {
						updateNum = buyerCouponInfoDAO.updateBuyerCouponUseInfo(dealTargetInfo);
						if (updateNum == 0) {
							buyerCouponInfoDAO.addBuyerCouponInfo(dealTargetInfo);
						}
					} catch (Exception e) {
						result = false;
						jedis.lpush(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCouponStr,
								dealTargetStr);
						jedis.sadd(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCouponStr);
						logger.error("\n 方法:[{}],异常:[{}],入参:[{}][{}]", "UpdateBuyerCouponScheduleTask-execute",
								ExceptionUtils.getStackTraceAsString(e), dealTargetStr);
						break;
					}
				}
			}
		} finally {
			marketRedisDB.releaseResource(jedis);
		}
		return result;
	}
}

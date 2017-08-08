
package cn.htd.marketcenter.task;

import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
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
 * 定时任务 优惠券保存进DB处理
 */
public class SendCoupon2BuyerAccountScheduleTask implements IScheduleTaskDealMulti<BuyerCouponInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(SendCoupon2BuyerAccountScheduleTask.class);

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
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "SendCoupon2BuyerAccountScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		List<BuyerCouponInfoDTO> dealTargetList = new ArrayList<BuyerCouponInfoDTO>();
		try {
			if (marketRedisDB.getLlen(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST) > 0) {
				dealTargetList.add(new BuyerCouponInfoDTO());
			}
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "SendCoupon2BuyerAccountScheduleTask-selectTasks",
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
		logger.info("\n 方法:[{}],入参:[{}][{}]", "SendCoupon2BuyerAccountScheduleTask-execute", JSON.toJSONString(tasks),
				"ownSign:" + ownSign);
		BuyerCouponInfoDTO buyerCouponDTO = null;
		BuyerCouponInfoDTO savedCouoponInfo = null;
		Jedis jedis = null;
		boolean result = true;
		int addNum = 0;
		String valueStr = "";
		try {
			jedis = marketRedisDB.getResource();
			while (jedis.llen(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST) > 0) {
				valueStr = "";
				buyerCouponDTO = null;
				try {
					valueStr = jedis.lpop(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST);
					buyerCouponDTO = JSON.parseObject(valueStr, BuyerCouponInfoDTO.class);
					if (buyerCouponDTO == null) {
						continue;
					}
					logger.info("\n 方法:[{}],入参:[{}]", "SendCoupon2BuyerAccountScheduleTask-execute", valueStr);
					savedCouoponInfo = buyerCouponInfoDAO.queryCouponInfoByCode(buyerCouponDTO);
					if (savedCouoponInfo != null) {
						logger.warn("\n 方法:[{}],警告信息:[{}],入参:[{}]", "SendCoupon2BuyerAccountScheduleTask-execute",
								"该会员优惠券数据已经存在在Buyer_COUPON_INFO表中", valueStr);
						continue;
					}
					addNum = buyerCouponInfoDAO.addBuyerCouponInfo(buyerCouponDTO);
					if (addNum <= 0) {
						throw new MarketCenterBusinessException(MarketCenterCodeConst.SYSTEM_ERROR,
								"会员优惠券:" + buyerCouponDTO.getBuyerCouponCode() + "保存DB失败");
					}
				} catch (MarketCenterBusinessException mcbe) {
					result = false;
					if (jedis != null && buyerCouponDTO != null) {
						jedis.rpush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, valueStr);
					}
					logger.error("\n 方法:[{}],异常:[{}],入参:[{}]", "SendCoupon2BuyerAccountScheduleTask-execute",
							ExceptionUtils.getStackTraceAsString(mcbe), valueStr);
				} catch (Exception e) {
					logger.info("\n 方法:[{}],异常:[{}],入参:[{}]", "SendCoupon2BuyerAccountScheduleTask-execute",
							ExceptionUtils.getStackTraceAsString(e), valueStr);
					throw e;
				}
			}
		} finally {
			marketRedisDB.releaseResource(jedis);
		}
		return result;

	}
}

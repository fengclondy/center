package cn.htd.marketcenter.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.B2cCouponUseLogSyncHistoryDAO;
import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 无敌券
 * 定时任务从b2c_coupon_use_log_sync_history表里取出deal_flag=0的记录，将对应的金额增加|减少到会员店对应的优惠券上
 * 
 * @author 张丁
 * 
 */
public class Updateb2cMemberCouponAmountTask implements
		IScheduleTaskDealMulti<B2cCouponUseLogSyncDMO> {

	protected static transient Logger logger = LoggerFactory
			.getLogger(Updateb2cMemberCouponAmountTask.class);

	@Resource
	private B2cCouponUseLogSyncHistoryDAO b2cCouponUseLogSyncHistoryDAO;
	
	@Resource
    private MarketCenterRedisDB marketRedisDB;

	private final static int START_LINE = 0;

	private final static int END_LINE = 10000;

	private static final int zero = 0;
	
	private static final String ON_LINE_ORDER = "1"; // 在线支付订单
	
	private static final String CASH_ON_DELIVERY_ORDER_SURE = "2"; // 货到付款订单确认
	
	private static final String ON_LINE_SURE_CANCLE_ORDER = "3"; // 在线支付订单取消确认

	@Override
	public Comparator<B2cCouponUseLogSyncDMO> getComparator() {
		return new Comparator<B2cCouponUseLogSyncDMO>() {
			public int compare(B2cCouponUseLogSyncDMO o1,
					B2cCouponUseLogSyncDMO o2) {
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
	public List<B2cCouponUseLogSyncDMO> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		logger.info(
				"【selectTasks】【无敌券-更新会员店优惠券金额--开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[] { JSONObject.toJSONString(taskParameter),
						JSONObject.toJSONString(ownSign),
						JSONObject.toJSONString(taskQueueNum),
						JSONObject.toJSONString(taskItemList),
						JSONObject.toJSONString(eachFetchDataNum) });
		List<String> taskIdList = new ArrayList<String>();
		List<B2cCouponUseLogSyncDMO> queryB2cCouponUseLogSyncHistoryList = null;
		Map paramMap = new HashMap();
		paramMap.put("startLine", START_LINE);
		if (eachFetchDataNum > 0) {
			paramMap.put("endLine", eachFetchDataNum);
		} else {
			paramMap.put("endLine", END_LINE);
		}

		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				paramMap.put("taskQueueNum", taskQueueNum);
				paramMap.put("taskIdList", taskIdList);
				queryB2cCouponUseLogSyncHistoryList = b2cCouponUseLogSyncHistoryDAO.queryB2cCouponUseLogSyncHistoryList(paramMap);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			logger.error("无敌券-调用更新会员店优惠券金额时候发生异常:" + w.toString());
		} finally {

		}
		return queryB2cCouponUseLogSyncHistoryList;
	}

	@Override
	public boolean execute(B2cCouponUseLogSyncDMO[] tasks, String ownSign)
			throws Exception {
		logger.info("【execute】【无敌券-更新会员店优惠券金额--开始执行】【请求参数：】【tasks:{},ownSign:{}】",new Object[]{JSONObject.toJSONString(tasks),JSONObject.toJSONString(ownSign)});
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				for(B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO : tasks){
					String useType = b2cCouponUseLogSyncDMO.getUseType();
					/*校验 如果useType是在线支付订单取消确认,数据库里必须存在在   线支付订单|货到付款订单确认  的记录
					 * 如果不存在，将该条记录记为deal_flag=2(处理失败)
					 */
					if(useType.equals(ON_LINE_SURE_CANCLE_ORDER)){
						Long useCouponCount = b2cCouponUseLogSyncHistoryDAO.queryB2cCouponUseLogSyncHistoryUseCouponCount(b2cCouponUseLogSyncDMO);
						if(null == useCouponCount || useCouponCount.intValue() == 0){
							B2cCouponUseLogSyncDMO record = new B2cCouponUseLogSyncDMO();
							record.setModifyId(0L);
							record.setModifyName("sys");
							String dealFailReason = "该条数据非法--会员店的优惠券金额并未做过累加";
							record.setDealFailReason(dealFailReason);
							record.setB2cOrderNo(b2cCouponUseLogSyncDMO.getB2cOrderNo());
							record.setB2cBuyerCouponCode(b2cCouponUseLogSyncDMO.getB2cBuyerCouponCode());
							int updateResult = b2cCouponUseLogSyncHistoryDAO.updateB2cCouponUseLogSyncHistory(record);
							logger.warn(dealFailReason+"参数:{},跟新数据库结果{}",JSONObject.toJSONString(b2cCouponUseLogSyncDMO),updateResult);
							continue;
						}
					}
					
					String b2cActivityCode = b2cCouponUseLogSyncDMO.getB2cActivityCode();
					String buyerCode = b2cCouponUseLogSyncDMO.getB2cSellerCode();
					
					String promotionId = "";//TODO 1-从redis里获取到对应的中台promotionId
					
					//查询该会员获取优惠券数量,如果为空就做券，如果不为空就加金额
					String count = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode+"&"+promotionId);
					if(StringUtils.isEmpty(count)){
						// 做券
					}else{
						// 增加或者扣减金额
					}
				}
			}
		}catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        logger.error("无敌券-更新会员店优惠券金额-调用execute方法时候发生异常"+ w.toString());
		} finally {
		}
		return result;
	}

}

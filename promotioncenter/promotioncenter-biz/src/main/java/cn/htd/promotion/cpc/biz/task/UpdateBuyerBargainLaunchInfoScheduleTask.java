package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;

public class UpdateBuyerBargainLaunchInfoScheduleTask implements IScheduleTaskDealMulti<BuyerBargainLaunchReqDTO>{
	
	protected static transient Logger logger = LoggerFactory.getLogger(UpdateBuyerBargainLaunchInfoScheduleTask.class);
	
	//砍价发起常量
	public static final String BUYER_LAUNCH_BARGAIN_INFO = "BUYER_LAUNCH_BARGAIN_INFO";
	
	@Resource
    private PromotionRedisDB promotionRedisDB;
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	
	@Override
	public Comparator<BuyerBargainLaunchReqDTO> getComparator() {
		return new Comparator<BuyerBargainLaunchReqDTO>() {
			public int compare(BuyerBargainLaunchReqDTO o1, BuyerBargainLaunchReqDTO o2) {
				Integer id1 = o1.getId();
				Integer id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<BuyerBargainLaunchReqDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskQueueList,
			int eachFetchDataNum) throws Exception {
		
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		BuyerBargainLaunchReqDTO buyerBargainLaunch = new BuyerBargainLaunchReqDTO();
		String str = promotionRedisDB.headPop(BUYER_LAUNCH_BARGAIN_INFO);
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerBargainRecordScheduleTask-更新砍价发起表",str);
		List<BuyerBargainLaunchReqDTO> buyerBargainLaunchList = new ArrayList<BuyerBargainLaunchReqDTO>();;
		buyerBargainLaunch = JSON.parseObject(str, BuyerBargainLaunchReqDTO.class);
		try {
			if (taskQueueList != null && taskQueueList.size() > 0) {
				buyerBargainLaunchList.add(buyerBargainLaunch);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-selectTasks",
					JSONObject.toJSONString(buyerBargainLaunchList));
		}
		return buyerBargainLaunchList;
	}

	@Override
	public boolean execute(BuyerBargainLaunchReqDTO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		List<BuyerBargainLaunchReqDTO> buyerBargainLaunchList = new ArrayList<BuyerBargainLaunchReqDTO>();
		try {
			if (tasks != null && tasks.length > 0) {
				for (BuyerBargainLaunchReqDTO buyerBargainLaunch : tasks) {
					buyerBargainLaunchList.add(buyerBargainLaunch);
				}
				for (BuyerBargainLaunchReqDTO tmpBuyerBargainLaunch : buyerBargainLaunchList) {
					buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo(tmpBuyerBargainLaunch);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerBargainLaunchInfoScheduleTask-execute",
					JSON.toJSONString(result));
		}
		return result;
	}

}

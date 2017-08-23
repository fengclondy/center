package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionCenterRedisDB;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;

public class InsertBuyerBargainRecordScheduleTask implements IScheduleTaskDealMulti<BuyerBargainRecordReqDTO>{
	
	protected static transient Logger logger = LoggerFactory.getLogger(InsertBuyerBargainRecordScheduleTask.class);
	
	//砍价记录常量
	public static final String BUYER_BARGAIN_RECORD = "BUYER_BARGAIN_RECORD";
	
	@Resource
    private PromotionCenterRedisDB promotionRedisDB;
	
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
	
	@Override
	public Comparator<BuyerBargainRecordReqDTO> getComparator() {
		return new Comparator<BuyerBargainRecordReqDTO>() {
			public int compare(BuyerBargainRecordReqDTO o1, BuyerBargainRecordReqDTO o2) {
				Integer id1 = o1.getId();
				Integer id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<BuyerBargainRecordReqDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskQueueList,
			int eachFetchDataNum) throws Exception {
		
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "InsertBuyerBargainRecordScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		BuyerBargainRecordReqDTO buyerBargainRecord = new BuyerBargainRecordReqDTO();
		String str = promotionRedisDB.headPop(BUYER_BARGAIN_RECORD);
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "InsertBuyerBargainRecordScheduleTask-插入砍价记录",str);
		List<BuyerBargainRecordReqDTO> buyerBargainRecordList = new ArrayList<BuyerBargainRecordReqDTO>();;
		buyerBargainRecord = JSON.parseObject(str, BuyerBargainRecordReqDTO.class);
		try {
			if (taskQueueList != null && taskQueueList.size() > 0) {
				buyerBargainRecordList.add(buyerBargainRecord);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "InsertBuyerBargainRecordScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "InsertBuyerBargainRecordScheduleTask-selectTasks",
					JSONObject.toJSONString(buyerBargainRecordList));
		}
		return buyerBargainRecordList;
	}

	@Override
	public boolean execute(BuyerBargainRecordReqDTO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "InsertBuyerBargainRecordScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		List<BuyerBargainRecordReqDTO> buyerBargainRecordList = new ArrayList<BuyerBargainRecordReqDTO>();
		try {
			if (tasks != null && tasks.length > 0) {
				for (BuyerBargainRecordReqDTO buyerBargainRecord : tasks) {
					buyerBargainRecordList.add(buyerBargainRecord);
				}
				for (BuyerBargainRecordReqDTO tmpBuyerBargainRecord : buyerBargainRecordList) {
					buyerBargainRecordDAO.insertBuyerBargainRecord(tmpBuyerBargainRecord);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "InsertBuyerBargainRecordScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "InsertBuyerBargainRecordScheduleTask-execute",
					JSON.toJSONString(result));
		}
		return result;
	}

}

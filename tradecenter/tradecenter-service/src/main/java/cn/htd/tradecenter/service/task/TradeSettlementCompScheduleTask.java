/**
 * 结算单补偿job
 * @author tangjiayong
 */
package cn.htd.tradecenter.service.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.tradecenter.domain.TradeSettlementCount;
import cn.htd.tradecenter.domain.order.TradeSettlementComp;
import cn.htd.tradecenter.service.TradeSettlementService;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;

public class TradeSettlementCompScheduleTask implements IScheduleTaskDealSingle<TradeSettlementCount> {

	protected static transient Logger logger = LoggerFactory.getLogger(TradeSettlementCompScheduleTask.class);
	
	@Resource
	private TradeSettlementService tradeSettlementService;

	@Override
	public List<TradeSettlementCount> selectTasks(String paramString1, String paramString2, int paramInt1, List<TaskItemDefine> paramList, int paramInt2) throws Exception {
		List<TradeSettlementCount> countList = new ArrayList<TradeSettlementCount>();
		TradeSettlementCount count = new TradeSettlementCount();
		count.setCnt(paramInt2);
		countList.add(count);
		return countList;
	}

	@Override
	public Comparator<TradeSettlementCount> getComparator() {
		return null;
	}

	@Override
	public boolean execute(TradeSettlementCount paramT, String paramString) throws Exception {
		
		// 完全时间 年月日时分秒
	      DateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	      Calendar calendar = Calendar.getInstance();
	      Date currentTime = calendar.getTime();
	      String currentTimeStr = sdf_full.format(currentTime);
	      
	      logger.info("======>current time[" + currentTimeStr + "]");
		
		try {
	  		  // 任务开始执行时间
	  		  long startTime_consume = System.currentTimeMillis();
	          // job补偿生成结算单
	  		  process();
	  		  // 任务执行结束时间
	  		  long endTime_consume = System.currentTimeMillis();
	  		  logger.info("===>tradeSettlementCompScheduleTask execution time of " + (endTime_consume - startTime_consume) + " ms");
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "TradeSettlementCompScheduleTask-execute", e.toString());
			return false;
		}
		
		return true;
	}

	
	public void process() {
		
		List<TradeSettlementComp> tradeSettlementCompList = tradeSettlementService.getTradeSettlementComps();
		if(null == tradeSettlementCompList || tradeSettlementCompList.size() < 1){
			logger.info("======>tradeSettlementCompList is null......");
		}
		
		for(TradeSettlementComp tradeSettlementComp : tradeSettlementCompList){
			tradeSettlementService.rebuildTheTradeSettlementComp(tradeSettlementComp);
		}
		
	}
	

}

package cn.htd.tradecenter.service.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.tradecenter.common.utils.PaySDK;
import cn.htd.tradecenter.dao.TradeSettlementDAO;
import cn.htd.tradecenter.dao.TradeSettlementDetailDAO;
import cn.htd.tradecenter.dao.TradeSettlementWithdrawDAO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;
import cn.htd.tradecenter.service.TradeSettlementService;

public class BatchDistributionScheduleTask implements IScheduleTaskDealSingle<TradeSettlementWithdrawDTO>{

	@Resource
	private TradeSettlementDAO tradeSettlementDAO;
	
	@Resource
	private TradeSettlementWithdrawDAO tradeSettlementWithdrawDAO;
	
	@Resource
	private TradeSettlementDetailDAO tradeSettlementDetailDAO;
	
	@Resource
	private PaySDK paySDK;
	
	@Resource
	private TradeSettlementService tradeSettlementService;
	
	protected static transient Logger logger = LoggerFactory.getLogger(BatchDistributionScheduleTask.class);
	
	@Override
	public Comparator<TradeSettlementWithdrawDTO> getComparator() {
		return null;
	}

	@Override
	public List<TradeSettlementWithdrawDTO> selectTasks(String arg0, String arg1, int arg2, List<TaskItemDefine> arg3,
			int arg4) throws Exception {
		return null;
	}

	@Override
	public boolean execute(TradeSettlementWithdrawDTO arg0, String arg1) throws Exception {
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
	  		logger.info("===>BatchDistributionScheduleTask execution time of " + (endTime_consume - startTime_consume) + " ms");
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "BatchDistributionScheduleTask-execute", e.toString());
			return false;
		}
		return true;		
	}
	
	public void process() {
		TradeSettlementWithdrawDTO dto = new TradeSettlementWithdrawDTO();
		dto.setOperateType("1");//清分
		dto.setTradeType("1");//外部供应商
		List<TradeSettlementWithdrawDTO> ousideList = tradeSettlementWithdrawDAO.queryUnSuccessTraSetWithdraw(dto);
		
		if(CollectionUtils.isEmpty(ousideList)){
			logger.info("======>BatchDistributionScheduleTask query list is null......");
		}
		
		for(TradeSettlementWithdrawDTO tradeSettlementWithdraw : ousideList){
			List<String> merchantOrderNoList = tradeSettlementDAO.getMerchantOrderNoList(tradeSettlementWithdraw);
			Map<String,Object> sumMap = tradeSettlementDetailDAO.queryCountMoney(tradeSettlementWithdraw);
			tradeSettlementService.batchDistribution(tradeSettlementWithdraw , merchantOrderNoList , sumMap.get("orderitempayamounts").toString() , paySDK.getBatchReturn_url());
			tradeSettlementWithdraw.setModifyTime(new Date());
			tradeSettlementWithdraw.setContent("SUCCESS");
			tradeSettlementWithdrawDAO.updateTraSetWithdraw(tradeSettlementWithdraw);
		}
		
		dto.setTradeType("2");//平台公司
		
		List<TradeSettlementWithdrawDTO> insideList = tradeSettlementWithdrawDAO.queryUnSuccessTraSetWithdraw(dto);
		
		if(CollectionUtils.isEmpty(insideList)){
			logger.info("======>BatchDistributionScheduleTask query list is null......");
		}
		
		List<String> list = new ArrayList<String>();
		list.add("2");
		list.add("3");
		for(TradeSettlementWithdrawDTO tradeSettlementWithdraw : insideList){
			tradeSettlementWithdraw.setPayTypeList(list);
			List<String> merchantOrderNoList = tradeSettlementDAO.getMerchantOrderNoList(tradeSettlementWithdraw);
			if(!CollectionUtils.isEmpty(merchantOrderNoList)){
				Map<String,Object> sumMap = tradeSettlementDetailDAO.queryCountMoney(tradeSettlementWithdraw);
				tradeSettlementService.batchDistribution(tradeSettlementWithdraw , merchantOrderNoList , sumMap.get("orderitempayamounts").toString() , paySDK.getReturn_url());
				tradeSettlementWithdraw.setModifyTime(new Date());
				tradeSettlementWithdraw.setContent("SUCCESS");
				tradeSettlementWithdrawDAO.updateTraSetWithdraw(tradeSettlementWithdraw);
			}
		}
	}

}

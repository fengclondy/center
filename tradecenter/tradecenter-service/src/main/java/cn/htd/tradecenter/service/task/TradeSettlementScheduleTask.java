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

import cn.htd.tradecenter.common.constant.SettlementConstants;
import cn.htd.tradecenter.common.enums.SettlementEnum;
import cn.htd.tradecenter.common.utils.SettlementUtils;
import cn.htd.tradecenter.dao.TradeOrderSettlementDAO;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSetSellerInfoDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.service.TradeSettlementService;
import cn.htd.tradecenter.service.convert.TradeSettlementConvert;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;


public class TradeSettlementScheduleTask implements IScheduleTaskDealMulti<TradeSetSellerInfoDTO>{
	

	protected static transient Logger logger = LoggerFactory.getLogger(TradeSettlementScheduleTask.class);
	
	// 结算单工具类
	@Resource
	private SettlementUtils settlementUtils;
	
	// 订单DAO
	@Resource
	private TradeOrderSettlementDAO tradeOrderSettlementDAO;
	
	@Resource
	private TradeSettlementService tradeSettlementService;
	
	@Resource
	private TradeSettlementConvert tradeSettlementConvert;
	
	
	@Override
	public Comparator<TradeSetSellerInfoDTO> getComparator() {
		
		//1,从小到大；0，从大到小
//		return new Comparator<TradeOrders>() {
//			@Override
//			public int compare(TradeOrders orders1, TradeOrders orders2) {
//				boolean comparaeResult = orders1.getId() > orders2.getId();
//				return comparaeResult ? 1 : 0;
//			}
//		};
		
		return null;
	}




	/**
	 * 根据条件查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter 任务的自定义参数
	 * @param ownSign 当前环境名称
	 * @param taskQueueNum 当前任务类型的任务队列数量
	 * @param taskQueueList 当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum 每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TradeSetSellerInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		List<TradeSetSellerInfoDTO> targetList = new ArrayList<TradeSetSellerInfoDTO>();
		TradeOrderSettlementDTO tradeOrderSettlementDTO = tradeSettlementConvert.generateBaseTradeOrderSettlementDTO();
		tradeOrderSettlementDTO.setCurrentPage(1);
		tradeOrderSettlementDTO.setPageSize(eachFetchDataNum);
		
	     // 查询符合结算规则的订单里的所有供应商（外部供应商和商品+的订单，筛选出供应商） 渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
		 List<TradeSetSellerInfoDTO> tradeSetSellerInfoList = tradeOrderSettlementDAO.getTradeSetSellerInfos(tradeOrderSettlementDTO);
		 if(null == tradeSetSellerInfoList || tradeSetSellerInfoList.size() < 1){
			logger.info("查询符合结算规则的订单里的所有供应商为空======》tradeSetSellerInfoList:" + tradeSetSellerInfoList);
			return null;
		 }
		 for (int i = 0 ; i <tradeSetSellerInfoList.size(); i ++) {
			 int mod = i % taskQueueNum;
			 for(TaskItemDefine taskItemDefine : taskItemList){
				 if (taskItemDefine.getTaskItemId().contains(String.valueOf(mod))) {
					 targetList.add(tradeSetSellerInfoList.get(i));
				 }
			 }
		
		 }
		return targetList;
	}


	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组，只能传递OBJECT[]
	 * 
	 * @param task 任务
	 * @param ownSign 当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(TradeSetSellerInfoDTO[] tradeSetSellerInfoArray, String ownSign) throws Exception {
//		logger.info("\n 方法[{}]，入参：[{}]", "TradeSettlementScheduleTask-execute", JSONObject.toJSONString(tradeSetSellerInfoArray),
//				JSONObject.toJSONString(ownSign));
		
//		结算单解释
//		结算单规则：T+M的订单符合当次账期时间，就会被纳入当次账期来出结算单
//		T+M：T为确认收货的时间点，M为自行设置的时间点
//		---自动确认收货时间为发货后的15天；M为7天
//		账期：一个自然月
//		出结算单时间：每个自然月的8号0点
		
		// 技术逻辑
		// 1.判断当前时间是否是每个自然月的8号0点
		// 2.根据标记位判断是否当前任务正在跑
		// 3.判断供应商是否生成了结算单
		
		
		// 完全时间 年月日时分秒
//      DateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        
//      Calendar calendar = Calendar.getInstance();
//      Date currentTime = calendar.getTime();
//      String currentTimeStr = sdf_full.format(currentTime);
//      
//      logger.info("======>current time[" + currentTimeStr + "]");
      
      
    try {
        
  		  // 任务开始执行时间
//  		  long startTime_consume = System.currentTimeMillis();
          // 生成结算单
  		  process(tradeSetSellerInfoArray);
  		  // 任务执行结束时间
//  		  long endTime_consume = System.currentTimeMillis();
//  		  logger.info("===>tradeSettlementScheduleTask execution time of " + (endTime_consume - startTime_consume) + " ms");
          
	} catch (Exception e) {
		logger.error("tradeSettlementScheduleTask execute exception!!!",e);
		 return false;
	}
		return true;
	}
	
	/**
	 * 处理生成结算单
	 * @param currentTime
	 */
	public void process(TradeSetSellerInfoDTO[] tradeSetSellerInfoArray) {
		
		// 完全时间 年月日时分秒
	      DateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	      Calendar calendar = Calendar.getInstance();
	      Date currentTime = calendar.getTime();
	      String currentTimeStr = sdf_full.format(currentTime);
	      
	      logger.info("======>current time[" + currentTimeStr + "]");
  		  // 任务开始执行时间
  		  long startTime_consume = System.currentTimeMillis();
		
		
	     // 查询符合结算规则的订单里的所有供应商（外部供应商和商品+的订单，筛选出供应商） 渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
		 if(null == tradeSetSellerInfoArray || tradeSetSellerInfoArray.length < 1){
			logger.info("查询符合结算规则的订单里的所有供应商为空======》tradeSetSellerInfoArray:" + tradeSetSellerInfoArray);
			return;
		 }
		 
		 TradeOrderSettlementDTO tradeOrderSettlementDTO = tradeSettlementConvert.generateBaseTradeOrderSettlementDTO();
		 
			 for(TradeSetSellerInfoDTO tradeSetSellerInfo : tradeSetSellerInfoArray){
				 
				 tradeOrderSettlementDTO.setSellerId(tradeSetSellerInfo.getSellerId());
				 tradeOrderSettlementDTO.setSellerCode(tradeSetSellerInfo.getSellerCode());
				 tradeOrderSettlementDTO.setSellerName(tradeSetSellerInfo.getSellerName());
//				 tradeOrderSettlementDTO.setSellerType(tradeSetSellerInfo.getSellerType());
				 
				 TradeSettlementConstDTO tradeSettlementConstDTO = tradeSettlementService.getSetConsByTypeAndKey(SettlementConstants.CONST_TYPE_PRODUCT_CHANNEL,tradeSetSellerInfo.getProductChannelCode());

					//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
					 String productChannelCode = tradeSettlementConstDTO.getConstKey();
					//渠道名称
					 String productChannelName = tradeSettlementConstDTO.getConstValue();
					//结算单类型: 10 外部供应商 20 平台公司
					 String settlementTypeCode = null;
					//渠道名称
					 String settlementTypeName = null;
					 
					 // 外部供应商
					 if(SettlementEnum.ProductChannel.PRODUCT_CHANNEL_20.key().equals(productChannelCode)){
						 settlementTypeCode = SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.key();
						 settlementTypeName = SettlementEnum.SettlementType.SETTLEMENT_TYPE_10.value();
					 }else{//商品+等等其他外接商品渠道
						 settlementTypeCode = SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.key();
						 settlementTypeName = SettlementEnum.SettlementType.SETTLEMENT_TYPE_20.value();
					 }
					
				//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
				tradeOrderSettlementDTO.setProductChannelCode(productChannelCode);
				tradeOrderSettlementDTO.setProductChannelName(productChannelName);
				//结算单类型: 10 外部供应商 20 平台公司
				tradeOrderSettlementDTO.setSettlementTypeCode(settlementTypeCode);
				tradeOrderSettlementDTO.setSettlementTypeName(settlementTypeName);
				 
				tradeOrderSettlementDTO.setOperateType(SettlementConstants.OPERATE_TYPE_JOB);
				 
				 
			     // 查询符合结算规则的订单数量
			     Integer cnt = tradeOrderSettlementDAO.getOrderSettlementCount(tradeOrderSettlementDTO);
			     if(null == cnt || cnt.intValue() < 1){//没有符合条件的订单记录
			    	 logger.info("供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]没有符合条件的订单记录！！！");
			    	 return;
			     }
			     //设置总记录数
			     tradeOrderSettlementDTO.setTotalRows(cnt);
			     
			     // 生成新的结算单
			     String settlementNo = tradeSettlementService.generateAStatementBySeller(tradeOrderSettlementDTO);
			     logger.info("======》供应商[" + tradeOrderSettlementDTO.getSellerName() + "," + tradeOrderSettlementDTO.getSellerCode() + "]生成的结算单号为:" + settlementNo);
				 
			 }
		
			 
	  		  // 任务执行结束时间
	  		  long endTime_consume = System.currentTimeMillis();
	  		  logger.info("===>tradeSettlementScheduleTask execution time of " + (endTime_consume - startTime_consume) + " ms");
		
	}


}

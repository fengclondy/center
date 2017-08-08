package cn.htd.zeus.tc.biz.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderItemsDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderQueryService;
import cn.htd.zeus.tc.biz.service.TradeOrderStatusHistoryService;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringConvertToMapUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

/**
 * 系统自动确认收货-定时任务
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: OrderExpireReceiptTask.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public class OrderExpireReceiptTask implements IScheduleTaskDealMulti<TradeOrdersDMO>{

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderExpireReceiptTask.class);

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	@Autowired
	private OrderQueryService orderQueryService;

	@Autowired
	private TradeOrderStatusHistoryService tradeOrderStatusHistoryService;

	private final static int START_LINE = 0;

	private final static int END_LINE = 10000;

	private final static int SEVEN_DAYS = -7;

	@Override
	public Comparator<TradeOrdersDMO> getComparator() {
		return new Comparator<TradeOrdersDMO>() {
			public int compare(TradeOrdersDMO o1, TradeOrdersDMO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<TradeOrdersDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		LOGGER.info("【selectTasks】【系统自动确认收货接口—开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[]{JSONObject.toJSONString(taskParameter),JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		List<String> taskIdList = new ArrayList<String>();
		List<TradeOrdersDMO> tradeOrdersList = new ArrayList<TradeOrdersDMO>();
		Map paramMap = new HashMap();
		paramMap.put("startLine", START_LINE);
		if (eachFetchDataNum > 0) {
			paramMap.put("endLine", eachFetchDataNum);
		}else{
			paramMap.put("endLine", END_LINE);
		}

		taskParameter = "{"+taskParameter+"}";
		Map<String,Object> taskParameterMap  = (Map<String, Object>)StringConvertToMapUtil.getValue(taskParameter);
		if(null != taskParameterMap && StringUtilHelper.isNotNull(taskParameterMap.get("createTime"))){
			paramMap.put("createTime", DateUtil.getDaysTime(Integer.valueOf(taskParameterMap.get("createTime").toString()).intValue()));
		}else{
			paramMap.put("createTime",DateUtil.getDaysTime(SEVEN_DAYS));
		}

		try{
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				paramMap.put("taskQueueNum", taskQueueNum);
				paramMap.put("taskIdList", taskIdList);
				tradeOrdersList = tradeOrdersDAO.selectOrderByOrderStatusAndTime(paramMap);
			}
		}catch(Exception e){
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("系统自动确认收货发生异常:" + w.toString());
		}finally{

		}
		return tradeOrdersList;
	}

	@Override
	public boolean execute(TradeOrdersDMO[] tasks, String ownSign) throws Exception {
		LOGGER.info("【execute】【系统自动确认收货接口—开始执行】【请求参数：】【tasks:{},ownSign:{}】",new Object[]{JSONObject.toJSONString(tasks),JSONObject.toJSONString(ownSign)});
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				for(TradeOrdersDMO tradeOrdersDMO : tasks){
					TradeOrderItemsDMO recoed = new TradeOrderItemsDMO();
					recoed.setOrderNo(tradeOrdersDMO.getOrderNo());
					recoed.setOrderItemStatus(OrderStatusEnum.DELIVERYED.getCode());
//					Boolean flag = orderQueryService.queryOrderItemStatus(recoed);
//					if(flag == true){
					UpdateOrderStatusReqDTO updateOrderStatusReqDTO = new UpdateOrderStatusReqDTO();
					updateOrderStatusReqDTO.setOrderNo(tradeOrdersDMO.getOrderNo());
					updateOrderStatusReqDTO.setOrderStatus(OrderStatusEnum.EXPIRE_RECEIPT.getCode());
					updateOrderStatusReqDTO.setOrderStatusText(OrderStatusEnum.EXPIRE_RECEIPT.getMsg());
					tradeOrderStatusHistoryService.updateOrderStatus(updateOrderStatusReqDTO);
//					}
				}
			}
		}catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        LOGGER.error("系统自动确认收货-调用execute方法时候发生异常"+ w.toString());
		} finally {
		}
		return result;
	}

}

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

import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrderConsigneeDownInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderConsigneeDownService;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringConvertToMapUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 确认收货时间下行到erp
 * 
 * @author zhangding
 * 
 */
public class OrderConsigneeDownTask implements
		IScheduleTaskDealMulti<TradeOrderConsigneeDownInfoDMO> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderConsigneeDownTask.class);
	
	@Autowired
	private OrderConsigneeDownService orderConsigneeDownService;

	private final static int START_LINE = 0;

	private final static int END_LINE = 10000;

	@Override
	public Comparator<TradeOrderConsigneeDownInfoDMO> getComparator() {
		return new Comparator<TradeOrderConsigneeDownInfoDMO>() {
			public int compare(TradeOrderConsigneeDownInfoDMO o1,
					TradeOrderConsigneeDownInfoDMO o2) {
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
	public List<TradeOrderConsigneeDownInfoDMO> selectTasks(
			String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		LOGGER.info("【selectTasks】【确认收货时间下行到erp--开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[]{JSONObject.toJSONString(taskParameter),JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		List<String> taskIdList = new ArrayList<String>();
		List<TradeOrderConsigneeDownInfoDMO> orderConsigneeDownInfoList = null;
		Map paramMap = new HashMap();
		paramMap.put("startLine", START_LINE);
		if (eachFetchDataNum > 0) {
			paramMap.put("endLine", eachFetchDataNum);
		}else{
			paramMap.put("endLine", END_LINE);
		}
		taskParameter = "{"+taskParameter+"}";
		Map<String,Object> taskParameterMap  = (Map<String, Object>)StringConvertToMapUtil.getValue(taskParameter);
		if(null != taskParameterMap && StringUtilHelper.isNotNull(taskParameterMap.get("days"))){
			paramMap.put("startTime", DateUtil.getDaysTime(Integer.valueOf(taskParameterMap.get("days").toString()).intValue()));
		}else{
			paramMap.put("startTime", DateUtil.getDaysTime(1));
		}
		
		try{
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				paramMap.put("taskQueueNum", taskQueueNum);
				paramMap.put("taskIdList", taskIdList);
				orderConsigneeDownInfoList = orderConsigneeDownService.selectTradeOrderConsigneeDownInfoList(paramMap);
			}
		}catch(Exception e){
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 查询确认收货时间下行到erp时候发生异常:{}","", w.toString());
		}finally{
			
		}
		return orderConsigneeDownInfoList;
	}

	@Override
	public boolean execute(TradeOrderConsigneeDownInfoDMO[] tasks, String ownSign)
			throws Exception {
		LOGGER.info("【execute】【确认收货时间下行到erp--开始执行】【请求参数：】【tasks:{},ownSign:{}】",new Object[]{JSONObject.toJSONString(tasks),JSONObject.toJSONString(ownSign)});
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				orderConsigneeDownService.orderConsigneeDownERP(tasks);
			}
		}catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        LOGGER.error("MessageId:{}确认收货时间下行到erp-调用execute方法时候发生异常:{}","", w.toString());
		} finally {
		}
		return result;
	}

}

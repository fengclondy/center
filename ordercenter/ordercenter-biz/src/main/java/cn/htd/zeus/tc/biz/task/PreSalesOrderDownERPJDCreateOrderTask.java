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

import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.service.PreSalesOrderDownERPJDCreateOrderService;
import cn.htd.zeus.tc.common.util.StringConvertToMapUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/*
 * 定时任务执行-预售下行接口
 */
public class PreSalesOrderDownERPJDCreateOrderTask implements IScheduleTaskDealMulti<JDOrderInfoDMO>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreSalesOrderDownERPJDCreateOrderTask.class);
	
	@Autowired
	private PreSalesOrderDownERPJDCreateOrderService preSalesOrderDownService;
	
	private final static int START_LINE = 0;
	
	private final static int END_LINE = 10000;
	
	private static final int zero = 0;
	
	@Override
	public Comparator<JDOrderInfoDMO> getComparator() {
		return new Comparator<JDOrderInfoDMO>() {
			public int compare(JDOrderInfoDMO o1, JDOrderInfoDMO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
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
	public List<JDOrderInfoDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		LOGGER.info("【selectTasks】【预售下行接口-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[]{JSONObject.toJSONString(taskParameter),JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		List<String> taskIdList = new ArrayList<String>();
		List<JDOrderInfoDMO> jdOrderInfoList = null;
		Map paramMap = new HashMap();
		paramMap.put("startLine", START_LINE);
		if (eachFetchDataNum > 0) {
			paramMap.put("endLine", eachFetchDataNum);
		}else{
			paramMap.put("endLine", END_LINE);
		}
		taskParameter = "{"+taskParameter+"}";
		Map<String,Object> taskParameterMap  = (Map<String, Object>)StringConvertToMapUtil.getValue(taskParameter);
		if(null != taskParameterMap && StringUtilHelper.isNotNull(taskParameterMap.get("erpBookSendCount"))){
			paramMap.put("erpBookSendCount", Integer.valueOf(taskParameterMap.get("erpBookSendCount").toString()).intValue());
		}else{
			paramMap.put("erpBookSendCount", zero);
		}
		
		try{
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				paramMap.put("taskQueueNum", taskQueueNum);
				paramMap.put("taskIdList", taskIdList);
				jdOrderInfoList = preSalesOrderDownService.selectERPOrderNOFromJDOrderInfo(paramMap);
			}
		}catch(Exception e){
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用预售下行接口方法时候发生异常:{}" ,"",w.toString());
		}finally{
			
		}
		return jdOrderInfoList;
	}

	@Override
	public boolean execute(JDOrderInfoDMO[] tasks, String ownSign) throws Exception {
		LOGGER.info("【execute】【预售下行接口-开始执行】【请求参数：】【tasks:{},ownSign:{}】",new Object[]{JSONObject.toJSONString(tasks),JSONObject.toJSONString(ownSign)});
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				preSalesOrderDownService.preSalesOrderDown(tasks);
			}
		}catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
	        e.printStackTrace(new PrintWriter(w));
	        LOGGER.error("MessageId:{} 预售下行接口-调用preSalesOrderDown方法时候发生异常:{}","", w.toString());
		} finally {
		}
		return result;
	}

}

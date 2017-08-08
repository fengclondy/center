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

import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.service.ExceptionOrderNoticeService;
import cn.htd.zeus.tc.biz.service.PostStrikeaBalanceService;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.StringConvertToMapUtil;
import cn.htd.zeus.tc.common.util.StringUtilHelper;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/*
 * 收付款下行异常告警
 */
public class PostStrikeaExceptionOrderNoticeTask implements IScheduleTaskDealMulti<PayOrderInfoDMO>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostStrikeaExceptionOrderNoticeTask.class);
	
	@Autowired
	private ExceptionOrderNoticeService exceptionOrderNotice;
	
	private final static int START_LINE = 0;
	
	private final static int END_LINE = 10000;
	
	private static final int zero = 0;
	
	@Override
	public Comparator<PayOrderInfoDMO> getComparator() {
		return new Comparator<PayOrderInfoDMO>() {
			public int compare(PayOrderInfoDMO o1, PayOrderInfoDMO o2) {
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
	public List<PayOrderInfoDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		LOGGER.info("【selectTasks】【收付款下行异常--告警-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[]{JSONObject.toJSONString(taskParameter),JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
		List<String> taskIdList = new ArrayList<String>();
		List<PayOrderInfoDMO> payOrderInfoList = null;
		Map paramMap = new HashMap();
		paramMap.put("startLine", START_LINE);
		if (eachFetchDataNum > 0) {
			paramMap.put("endLine", eachFetchDataNum);
		}else{
			paramMap.put("endLine", END_LINE);
		}
		taskParameter = "{"+taskParameter+"}";
		Map<String,Object> taskParameterMap  = (Map<String, Object>)StringConvertToMapUtil.getValue(taskParameter);
		if(null != taskParameterMap && StringUtilHelper.isNotNull(taskParameterMap.get("minutes"))){
			int minutes = Integer.valueOf(taskParameterMap.get("minutes").toString()).intValue();
			paramMap.put("minutes", DateUtil.getMinutesLaterTime(-minutes));
		}else{
			paramMap.put("minutes", DateUtil.getMinutesLaterTime(-15));
		}
		
		try{
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				paramMap.put("taskQueueNum", taskQueueNum);
				paramMap.put("taskIdList", taskIdList);
		        payOrderInfoList = exceptionOrderNotice.selectPostStrikeaExceptionOrdersList(paramMap);
		        if(null != payOrderInfoList && payOrderInfoList.size()>0){
		        	LOGGER.info("收付款下行,异常告警发送邮件开始");
		        	exceptionOrderNotice.executePostStrikeaExceptionOrder(payOrderInfoList);
		        }
			}
		}catch(Exception e){
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用收付款下行异常--告警方法时候发生异常:" + w.toString());
		}finally{
			
		}
		return payOrderInfoList;
	}

	@Override
	public boolean execute(PayOrderInfoDMO[] tasks, String ownSign) throws Exception {
		return true;
	}

}

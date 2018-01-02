/**
 * 
 */
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

import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.zeus.tc.biz.dao.TradeOrdersDAO;
import cn.htd.zeus.tc.biz.dmo.OrderQueryParamDMO;
import cn.htd.zeus.tc.biz.dmo.TradeOrdersDMO;
import cn.htd.zeus.tc.biz.service.OrderCancelService;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.util.DateUtil;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.resquest.OrderCancelInfoReqDTO;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * @author ly
 *
 */
public class OrderCancelTask implements IScheduleTaskDealMulti<TradeOrdersDMO> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderCancelTask.class);

	@Autowired
	private OrderCancelService orderCancelService;

	@Autowired
	private TradeOrdersDAO tradeOrdersDAO;

	private final static int START_LINE = 0;

	private final static int END_LINE = 10000;

	private static final int zero = 0;

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
	public List<TradeOrdersDMO> selectTasks(String taskParameter,
			String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		LOGGER.info(
				"【selectTasks】【取消订单接口-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[] { JSONObject.toJSONString(taskParameter),
						JSONObject.toJSONString(ownSign),
						JSONObject.toJSONString(taskQueueNum),
						JSONObject.toJSONString(taskItemList),
						JSONObject.toJSONString(eachFetchDataNum) });
		List<String> taskIdList = new ArrayList<String>();
		List<TradeOrdersDMO> tradeOrdersDMOList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
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
				OrderQueryParamDMO orderQueryParamDMO = new OrderQueryParamDMO();
				List<String> orderStatusList = new ArrayList<String>();
				orderStatusList.add(OrderStatusEnum.PRE_CHECK.getCode());
				orderStatusList.add(OrderStatusEnum.PRE_PAY.getCode());
				orderStatusList.add(OrderStatusEnum.CHECK_ADOPT_PRE_PAY.getCode());
				// 新增待确认状态订单自动取消
				orderStatusList.add(OrderStatusEnum.PRE_CONFIRM.getCode());
				orderQueryParamDMO.setOrderStatus(orderStatusList);
				orderQueryParamDMO.setPayTimeLimit(DateUtil.getSystemTime());
				orderQueryParamDMO.setIsCancelOrder(Integer.valueOf(OrderStatusEnum.NOT_CANCLE.getCode()));
				orderQueryParamDMO.setOrderDeleteStatus(Integer.valueOf(OrderStatusEnum.ORDER_NOT_DELETE_STATUS.getCode()));
				tradeOrdersDMOList = tradeOrdersDAO.selectOrderByTradeOrdersParamByCancel(orderQueryParamDMO);
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用取消订单定时任务selectTasks方法时候发生异常:" ,"", w.toString());
		} finally {

		}
		return tradeOrdersDMOList;
	}

	@Override
	public boolean execute(TradeOrdersDMO[] tradeOrdersDMOs, String ownSign)
			throws Exception {
		LOGGER.info("【execute】【取消订单--开始执行】【参请求数：】【tasks:{},ownSign:{}】",new Object[]{JSONObject.toJSONString(tradeOrdersDMOs),JSONObject.toJSONString(ownSign)});
		boolean result = true;
		String messageId = GenerateIdsUtil.generateId(null);
		try {
			if(tradeOrdersDMOs != null)
			{
				for(int i = 0;i< tradeOrdersDMOs.length;i++)
				{
					TradeOrdersDMO tradeOrdersDMO = tradeOrdersDMOs[i];
					String memberCode = tradeOrdersDMO.getBuyerCode();
					String orderNo = tradeOrdersDMO.getOrderNo();
					OrderCancelInfoReqDTO orderCancelInfoReqDTO = new OrderCancelInfoReqDTO();
					orderCancelInfoReqDTO.setMemberCode(memberCode);
					orderCancelInfoReqDTO.setOrderNo(orderNo);
					orderCancelInfoReqDTO.setMessageId(messageId);
					tradeOrdersDMO = orderCancelService.orderCancel(orderCancelInfoReqDTO);
					String resultCode = tradeOrdersDMO.getResultCode();
					if(!ResultCodeEnum.SUCCESS.getCode().equals(resultCode))
					{
						LOGGER.info("MessageId:{} 调用取消订单定时任务execute方法时候发生取消订单失败，入参如下：{}，回调参数如下:{}" + messageId,
								JSONObject.toJSONString(orderCancelInfoReqDTO),JSONObject.toJSONString(tradeOrdersDMO) );
					}
				}
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用取消订单定时任务selectTasks方法时候发生异常:{}",messageId, w.toString());
		}
		return result;
	}

}

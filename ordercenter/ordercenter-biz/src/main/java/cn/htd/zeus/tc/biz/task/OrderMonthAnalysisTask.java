package cn.htd.zeus.tc.biz.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.zeus.tc.biz.dmo.OrderSalesMonthInfoDMO;
import cn.htd.zeus.tc.biz.service.OrderMonthAnalysisService;
import cn.htd.zeus.tc.common.util.DateUtil;

public class OrderMonthAnalysisTask implements IScheduleTaskDealMulti<OrderSalesMonthInfoDMO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderMonthAnalysisTask.class);

	@Autowired
	private OrderMonthAnalysisService orderMonthAnalysisService;

	@Override
	public Comparator<OrderSalesMonthInfoDMO> getComparator() {
		return new Comparator<OrderSalesMonthInfoDMO>() {
			public int compare(OrderSalesMonthInfoDMO o1, OrderSalesMonthInfoDMO o2) {
				Long id1 = o1.getSupperlierId();
				Long id2 = o2.getSupperlierId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<OrderSalesMonthInfoDMO> selectTasks(String taskParameter, String ownSign,
			int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum)
					throws Exception {
		LOGGER.info(
				"【selectTasks】============================【店铺经营信息店铺查询-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[] { JSONObject.toJSONString(taskParameter),
						JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
						JSONObject.toJSONString(taskItemList),
						JSONObject.toJSONString(eachFetchDataNum) });
		List<OrderSalesMonthInfoDMO> shopInfo = new ArrayList<OrderSalesMonthInfoDMO>();
		try {
			shopInfo = orderMonthAnalysisService.queryShopInfo();
			LOGGER.info("【selectTasks】============================【店铺经营信息店铺查询-执行成功】【返回参数：{}" + "】",
					com.alibaba.fastjson.JSONObject.toJSONString(shopInfo));
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("店铺经营信息导入获取店铺信息时候发生异常:" + w.toString());
		}
		return shopInfo;
	}

	@Override
	public boolean execute(OrderSalesMonthInfoDMO[] tasks, String ownSign) throws Exception {
		LOGGER.info("【execute】【店铺经营信息导入-开始执行】【请求参数：】【tasks:{},ownSign:{}】",
				new Object[] { JSONObject.toJSONString(tasks), JSONObject.toJSONString(ownSign) });
		boolean result = true;
		int date = Integer.valueOf(DateUtil.getLastMonth());
		try {
			if (tasks != null && tasks.length > 0) {
				for (OrderSalesMonthInfoDMO analysis : tasks) {
					OrderSalesMonthInfoDMO analysis2 = orderMonthAnalysisService
							.queryOrderMonthAnalysisInfo(analysis.getSellerCode(),
									DateUtil.getLastMonthFirstDay(),
									DateUtil.getLastMonthLastDay());
					if (analysis2 != null) {
						analysis.setSalesAmount(analysis2.getSalesAmount());
					} else {
						analysis.setSalesAmount(0L);
					}
					analysis.setSalesMonthYear(date);
					orderMonthAnalysisService.insertOrderMonthAnalysisInfo(analysis);
				}
			}
		} catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("店铺经营信息导入-调用导入方法方法时候发生异常" + w.toString());
		} finally {
		}
		return result;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		int date = Integer.valueOf(sdf.format(DateUtil.getLastMonthFirstDay()));
		System.out.println(date);
	}

}

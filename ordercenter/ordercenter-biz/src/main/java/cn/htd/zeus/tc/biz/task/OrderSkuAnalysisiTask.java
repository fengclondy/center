package cn.htd.zeus.tc.biz.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.util.DateUtils;
import cn.htd.zeus.tc.biz.dmo.OrderSkuAnalysisDMO;
import cn.htd.zeus.tc.biz.service.OrderSkuAnalysisService;

public class OrderSkuAnalysisiTask implements IScheduleTaskDealMulti<OrderSkuAnalysisDMO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSkuAnalysisiTask.class);

	@Autowired
	private OrderSkuAnalysisService orderskuAnalysisService;

	@Override
	public Comparator<OrderSkuAnalysisDMO> getComparator() {
		return new Comparator<OrderSkuAnalysisDMO>() {
			public int compare(OrderSkuAnalysisDMO o1, OrderSkuAnalysisDMO o2) {
				Long id1 = o1.getShopId();
				Long id2 = o2.getShopId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<OrderSkuAnalysisDMO> selectTasks(String taskParameter, String ownSign,
			int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum)
					throws Exception {
		LOGGER.info(
				"【selectTasks】============================【店铺销售分析店铺查询-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[] { JSONObject.toJSONString(taskParameter),
						JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
						JSONObject.toJSONString(taskItemList),
						JSONObject.toJSONString(eachFetchDataNum) });
		List<OrderSkuAnalysisDMO> shopInfo = new ArrayList<OrderSkuAnalysisDMO>();
		try {
			shopInfo = orderskuAnalysisService.queryShopInfo();
			LOGGER.info("【selectTasks】============================【店铺销售分析店铺查询-执行成功】【返回参数：{}" + "】",
					com.alibaba.fastjson.JSONObject.toJSONString(shopInfo));
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("店铺销售分析导入获取店铺信息时候发生异常:" + w.toString());
		}
		return shopInfo;
	}

	@Override
	public boolean execute(OrderSkuAnalysisDMO[] tasks, String ownSign) throws Exception {
		LOGGER.info("【execute】【店铺销售分析导入-开始执行】【请求参数：】【tasks:{},ownSign:{}】",
				new Object[] { JSONObject.toJSONString(tasks), JSONObject.toJSONString(ownSign) });
		boolean result = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int date = Integer
				.valueOf(sdf.format(DateUtils.offsetDate(new Date(), Calendar.DAY_OF_MONTH, -1)));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String payOrderDate = sdf2
				.format(DateUtils.offsetDate(new Date(), Calendar.DAY_OF_MONTH, -1));
		try {
			if (tasks != null && tasks.length > 0) {
				for (OrderSkuAnalysisDMO analysis : tasks) {
					List<OrderSkuAnalysisDMO> analysis2 = orderskuAnalysisService
							.queryOrderSkuInfo(analysis.getSellerCode(), payOrderDate);
					if (CollectionUtils.isNotEmpty(analysis2)) {
						for (OrderSkuAnalysisDMO skuInfo : analysis2) {
							skuInfo.setShopId(analysis.getShopId());
							skuInfo.setSellerCode(analysis.getSellerCode());
							skuInfo.setSalesTime(date);
							orderskuAnalysisService.insertOrderSkuInfo(skuInfo);
						}

					}
				}
			}
		} catch (Exception e) {
			result = false;
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("店铺销售分析导入-调用导入方法时候发生异常" + w.toString());
		} finally {
		}
		return result;
	}

}

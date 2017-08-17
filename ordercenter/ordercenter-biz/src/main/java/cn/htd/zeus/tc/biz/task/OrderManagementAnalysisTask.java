package cn.htd.zeus.tc.biz.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.util.DateUtils;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.zeus.tc.biz.dmo.OrderManagementAnalysisDMO;
import cn.htd.zeus.tc.biz.rao.MemberCenterRAO;
import cn.htd.zeus.tc.biz.service.OrderManagementAnalysisService;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

public class OrderManagementAnalysisTask implements IScheduleTaskDealMulti<ShopDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementAnalysisTask.class);

	@Autowired
	private OrderManagementAnalysisService orderManagementAnalysisService;

	@Autowired
	private MemberCenterRAO memberCenterRAO;

	@Override
	public Comparator<ShopDTO> getComparator() {
		return new Comparator<ShopDTO>() {
			public int compare(ShopDTO o1, ShopDTO o2) {
				Long id1 = o1.getShopId();
				Long id2 = o2.getShopId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<ShopDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		LOGGER.info(
				"【selectTasks】============================【店铺经营信息店铺查询-开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
				new Object[] { JSONObject.toJSONString(taskParameter),
						JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
						JSONObject.toJSONString(taskItemList),
						JSONObject.toJSONString(eachFetchDataNum) });
		List<ShopDTO> shopInfo = new ArrayList<ShopDTO>();
		String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
		try {
			shopInfo = orderManagementAnalysisService.queryShopInfo(messageId);
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
	public boolean execute(ShopDTO[] tasks, String ownSign) throws Exception {
		LOGGER.info("【execute】【店铺经营信息导入-开始执行】【请求参数：】【tasks:{},ownSign:{}】",
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
				for (ShopDTO analysis : tasks) {
					OtherCenterResDTO<String> sellerCode = memberCenterRAO
							.queryMemberCodeByMemberId(analysis.getSellerId(), "123456");
					OrderManagementAnalysisDMO analysis2 = orderManagementAnalysisService
							.queryOrderManagermentInfo(sellerCode.getOtherCenterResult(),
									payOrderDate);
					OrderManagementAnalysisDMO orderManagementAnalysisDMO = new OrderManagementAnalysisDMO();
					orderManagementAnalysisDMO.setSellerCode(sellerCode.getOtherCenterResult());
					orderManagementAnalysisDMO.setShopId(analysis.getShopId());
					orderManagementAnalysisDMO.setBuyPersonNum(analysis2.getBuyPersonNum());
					orderManagementAnalysisDMO.setPayGoodsNum(analysis2.getPayGoodsNum());
					orderManagementAnalysisDMO.setPayPriceTotal(analysis2.getPayPriceTotal());
					orderManagementAnalysisDMO.setSalesTime(date);
					orderManagementAnalysisDMO.setTradeNum(analysis2.getTradeNum());
					orderManagementAnalysisDMO.setOrderCount(analysis2.getOrderCount());
					orderManagementAnalysisService
							.insertOrderManagementInfo(orderManagementAnalysisDMO);
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

}

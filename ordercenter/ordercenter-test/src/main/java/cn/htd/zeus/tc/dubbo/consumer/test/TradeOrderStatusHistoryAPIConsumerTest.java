package cn.htd.zeus.tc.dubbo.consumer.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.TradeOrderStatusHistoryAPI;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.dto.response.TradeOrderItemStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.TradeOrderStatusHistoryListResDTO;
import cn.htd.zeus.tc.dto.response.UpdateOrderStatusResDTO;
import cn.htd.zeus.tc.dto.resquest.TradeOrderStatusHistoryReqDTO;
import cn.htd.zeus.tc.dto.resquest.UpdateOrderStatusReqDTO;

public class TradeOrderStatusHistoryAPIConsumerTest {

	public static void selectByOrderNoAndStatus() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		TradeOrderStatusHistoryAPI test = (TradeOrderStatusHistoryAPI) context.getBean("tradeOrderStatusHistoryAPI");
		TradeOrderStatusHistoryReqDTO reqDTO = new  TradeOrderStatusHistoryReqDTO();
		reqDTO.setOrderNo("344557777904");
		reqDTO.setMessageId("1223544");
		TradeOrderStatusHistoryListResDTO resDTO = test.selectHistoryByOrderNo(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void selectItemsHistoryByOrderNo() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		TradeOrderStatusHistoryAPI test = (TradeOrderStatusHistoryAPI) context.getBean("tradeOrderStatusHistoryAPI");
		TradeOrderStatusHistoryReqDTO reqDTO = new  TradeOrderStatusHistoryReqDTO();
		reqDTO.setOrderNo("344557777904");
		reqDTO.setMessageId("1223544");
		TradeOrderItemStatusHistoryListResDTO resDTO = test.selectItemsHistoryByOrderNo(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void updateOrderStatus() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		TradeOrderStatusHistoryAPI test = (TradeOrderStatusHistoryAPI) context.getBean("tradeOrderStatusHistoryAPI");
		UpdateOrderStatusReqDTO reqDTO = new UpdateOrderStatusReqDTO();
		reqDTO.setOrderNo("10017021511192300124");
		reqDTO.setBuyerCode("HTD10000001");
		reqDTO.setMessageId("1223544");
		reqDTO.setOrderStatus(OrderStatusEnum.BUYER_RECEIPT.getCode());
		reqDTO.setOrderStatusText(OrderStatusEnum.BUYER_RECEIPT.getMsg());
		UpdateOrderStatusResDTO resDTO = test.updateOrderStatus(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void main(String[] args) throws Exception {
		//查询订单状态履历
//		selectByOrderNoAndStatus();
		//更新订单状态
		updateOrderStatus();
		//查询订单行状态履历
//		selectItemsHistoryByOrderNo();
	}
}

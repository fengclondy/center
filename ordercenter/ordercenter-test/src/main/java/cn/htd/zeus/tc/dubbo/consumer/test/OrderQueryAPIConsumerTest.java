package cn.htd.zeus.tc.dubbo.consumer.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderQueryAPI;
import cn.htd.zeus.tc.dto.response.OrderQueryDetailResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryPageSizeResDTO;
import cn.htd.zeus.tc.dto.response.OrderQueryResDTO;
import cn.htd.zeus.tc.dto.response.OrdersQueryParamListResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryParamReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderQueryReqDTO;

public class OrderQueryAPIConsumerTest {

	public static void queryListOrder() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setBuyerCode("HTD_13125455");
		reqDTO.setMessageId("1223444444444444444444");
		OrdersQueryParamListResDTO resDTO = test.queryListOrder(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void queryOrderBySellerIdAndBuyerId() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryReqDTO reqDTO = new  OrderQueryReqDTO();
		reqDTO.setBuyerCode("HTD_13125455");
		reqDTO.setSellerCode("0832");
		reqDTO.setMessageId("1223444444444444444444");
		OrderQueryResDTO resDTO = test.queryOrderBySellerIdAndBuyerId(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void selectOrderByBuyerId() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setBuyerCode("HTD_13125455");
		reqDTO.setStart(0);
		reqDTO.setRows(10);
		reqDTO.setMessageId("1223544");
		OrdersQueryParamListResDTO resDTO = test.selectOrderByBuyerId(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void selectOrderByTradeOrdersParam() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setBuyerCode("HTD_13125455");
		reqDTO.setOrderDeleteStatus(0);
		reqDTO.setStart(10);
		reqDTO.setRows(20);
		reqDTO.setMessageId("1223444444444444444444");
		OrdersQueryParamListResDTO resDTO = test.selectOrderByTradeOrdersParam(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void selectOrderCountByBuyerId() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setBuyerCode("HTD_13125455");
		reqDTO.setOrderDeleteStatus(0);
		reqDTO.setIsCancelOrder(1);
		List<String> list = new ArrayList<String>();
		list.add("10");
//		reqDTO.setOrderStatus(list);
		reqDTO.setMessageId("1223544");
		OrderQueryPageSizeResDTO resDTO = test.selectOrderCountByBuyerId(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void queryDetailsOrder() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setOrderNo("10017022016553100288");
		reqDTO.setMessageId("1223544");
		OrderQueryDetailResDTO resDTO = test.queryDetailsOrder(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void selectOrderByPrimaryKey() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderQueryAPI test = (OrderQueryAPI) context.getBean("orderQueryAPI");
		OrderQueryParamReqDTO reqDTO = new  OrderQueryParamReqDTO();
		reqDTO.setOrderNo("10017021609364200202");
		reqDTO.setMessageId("1223544");
		OrderQueryDetailResDTO resDTO = test.selectOrderByPrimaryKey(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void main(String[] args) throws Exception {
//		selectOrderCountByBuyerId();
		selectOrderByTradeOrdersParam();
//		selectOrderByBuyerId();
//		queryOrderBySellerIdAndBuyerId();
//		selectOrderByPrimaryKey();
//		queryDetailsOrder();
//		queryListOrder();
	}

}
package cn.htd.zeus.tc.dubbo.consumer.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.zeus.tc.api.OrderSalesMonthAPI;
import cn.htd.zeus.tc.dto.response.OrderSalesMonthInfoQueryListResDTO;
import cn.htd.zeus.tc.dto.response.OrderSalesMonthInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSalesMonthInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

public class OrderSalesMonthAPIConsumerTest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderSalesMonthAPI test = (OrderSalesMonthAPI) context.getBean("orderSalesMonthAPI");
		OrderSalesMonthInfoReqDTO reqDTO = new  OrderSalesMonthInfoReqDTO();
		reqDTO.setMessageId("12234567894566");
		reqDTO.setSupperlierId(1L);
		OrderSalesMonthInfoQueryListResDTO resDTO = test.queryOrderSalesMonthInfoSevenMonthsAgo(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

}
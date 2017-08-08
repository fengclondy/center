package cn.htd.zeus.tc.dubbo.consumer.test;

import java.math.BigDecimal;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderPaymentResultAPI;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultListResDTO;
import cn.htd.zeus.tc.dto.response.OrderPaymentResultResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderPaymentResultReqDTO;

public class OrderPaymentResultAPIConsumerTest {

	public static void selectPaymentResultByTradeNo() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderPaymentResultAPI test = (OrderPaymentResultAPI) context.getBean("orderPaymentResultAPI");
		OrderPaymentResultReqDTO reqDTO = new  OrderPaymentResultReqDTO();
		String batchPayInfos = "[{\"amount\":\"0.00\",\"distributionStatus\":\"PROFIT_INIT\",\"merchantOrderNo\":\"10017021513490700137\",\"tradeStatus\":\"SUCCESS\",\"tradeType\":\"BALANCE_PAY\"}]";
		reqDTO.setBatchPayInfos(batchPayInfos);
		reqDTO.setMessageId("1223544");
		OrderPaymentResultListResDTO resDTO = test.selectPaymentResultByTradeNo(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void updateOrderStatusByTradeNo() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderPaymentResultAPI test = (OrderPaymentResultAPI) context.getBean("orderPaymentResultAPI");
		OrderPaymentResultReqDTO reqDTO = new OrderPaymentResultReqDTO();
		String batchPayInfos = "[{\"amount\":\"0.00\",\"distributionStatus\":\"PROFIT_INIT\",\"merchantOrderNo\":\"10017022010475700273\",\"tradeStatus\":\"SUCCESS\",\"tradeType\":\"BALANCE_PAY\"}]";
		reqDTO.setBatchPayInfos(batchPayInfos);
		reqDTO.setMessageId("1223544");
		reqDTO.setBuyerCode("HTD_13125455");
		OrderPaymentResultResDTO resDTO = test.updateOrderStatusByTradeNo(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

	public static void main(String[] args) throws Exception {
//		selectPaymentResultByTradeNo();
		updateOrderStatusByTradeNo();
	}

}
package cn.htd.zeus.tc.dubbo.consumer.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.zeus.tc.api.OrderCompensateERPCallBackAPI;
import cn.htd.zeus.tc.dto.response.OrderCompensateERPCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;

import com.alibaba.fastjson.JSONObject;

public class OrderCompensateERPCallBackAPIConsumerTest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderCompensateERPCallBackAPI test = (OrderCompensateERPCallBackAPI) context.getBean("orderCompensateERPCallBackAPI");
		OrderCompensateERPCallBackReqDTO reqDTO = new OrderCompensateERPCallBackReqDTO();
		reqDTO.setErrormessage("11");
		reqDTO.setJL_ComPanyCode("1");
		reqDTO.setJL_CreateRecordCode("99");
		reqDTO.setJL_SholesalerCode("1222");
		reqDTO.setJL_WholesalePayment("8882");
		reqDTO.setMessageId("913209432904");
		reqDTO.setOrderCode("10");
		reqDTO.setResult("1");
		OrderCompensateERPCallBackResDTO resDTO = test.orderCompensateERPCallBack(reqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

}
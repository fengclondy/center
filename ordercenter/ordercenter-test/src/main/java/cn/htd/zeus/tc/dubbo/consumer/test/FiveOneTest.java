package cn.htd.zeus.tc.dubbo.consumer.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.zeus.tc.api.OrderCompensateERPCallBackAPI;
import cn.htd.zeus.tc.api.OrderCreateAPI;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPCallBackDMO;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.OrderCompensateERPCallBackResDTO;
import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

public class FiveOneTest {
	public static void main(String[] args) throws UnknownHostException {

		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		
		
		context.start();
		OrderCompensateERPCallBackAPI test = (OrderCompensateERPCallBackAPI) context.getBean("orderCompensateERPCallBackAPI");
		
		OrderCompensateERPCallBackReqDTO reqInfo = new OrderCompensateERPCallBackReqDTO();
		reqInfo.setBrandCode("41");	
		reqInfo.setCategroyCode("11");
		reqInfo.setErrormessage("错误");
		reqInfo.setJL_ComPanyCode("21");
		reqInfo.setResult("0");
		reqInfo.setOrderCode("22223");
		
		OrderCompensateERPCallBackResDTO s = test.orderCompensateERPCallBack(reqInfo);
		
		System.out.println(JSONObject.toJSONString(s));
	}
}

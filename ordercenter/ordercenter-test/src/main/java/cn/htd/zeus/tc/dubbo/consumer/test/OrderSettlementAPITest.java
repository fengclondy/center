package cn.htd.zeus.tc.dubbo.consumer.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.zeus.tc.api.OrderSettleMentAPI;
import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;
import cn.htd.zeus.tc.dto.OrderSkuInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

public class OrderSettlementAPITest {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml" });
		context.start();

		OrderSettleMentAPI orderSettleMentAPI = (OrderSettleMentAPI) context.getBean("orderSettleMentAPI");
		OrderSettleMentReqDTO orderSettleMentReqDTO = new OrderSettleMentReqDTO();
		List<OrderSellerInfoDTO> sellerInfoList = new ArrayList<OrderSellerInfoDTO>();
		orderSettleMentReqDTO.setMemberId("HTD_13125455");
		orderSettleMentReqDTO.setMessageId("1234567");
		orderSettleMentReqDTO.setIsSeckill("0");
		orderSettleMentReqDTO.setCitySiteCode("021");
		List<OrderSkuInfoDTO> skuList = new ArrayList<OrderSkuInfoDTO>();
		OrderSkuInfoDTO skuInfo = new OrderSkuInfoDTO();
		skuInfo.setSkuCode("HTDH_100000001");
		skuInfo.setIsBoxFlag(1);
		skuInfo.setIsHasDevRelation(1);
		skuInfo.setItemChannel("20");
		skuInfo.setProductCount(10);
		skuList.add(skuInfo);
		// OrderSkuInfoDTO skuInfo2 = new OrderSkuInfoDTO();
		// skuInfo2.setSkuCode("1000004509");
		// skuInfo2.setIsBoxFlag(0);
		// skuInfo2.setIsHasDevRelation(0);
		// skuInfo2.setItemChannel("3010");
		// skuInfo2.setProductCount(10);
		// skuList.add(skuInfo2);
		OrderSellerInfoDTO sellerInfo = new OrderSellerInfoDTO();
		sellerInfo.setSellerCode("htd20012342131");
		sellerInfo.setSellerId(2);
		sellerInfo.setSkuInfoList(skuList);
		sellerInfo.setShopName("测试公司");
		sellerInfoList.add(sellerInfo);
		orderSettleMentReqDTO.setSellerInfoList(sellerInfoList);
		OrderSettleMentResDTO resDTO = orderSettleMentAPI.getOrderSettleMentInfo(orderSettleMentReqDTO);
		System.out.println(JSONObject.toJSONString(resDTO));
	}

}

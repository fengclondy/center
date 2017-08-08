package cn.htd.zeus.tc.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.zeus.tc.api.OrderSettleMentAPI;
import cn.htd.zeus.tc.dto.OrderSellerInfoDTO;
import cn.htd.zeus.tc.dto.OrderSkuInfoDTO;
import cn.htd.zeus.tc.dto.response.OrderSettleMentResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderSettleMentReqDTO;

import com.alibaba.fastjson.JSONObject;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext_test.xml", "classpath:mybatis/sqlconfig/sqlMapConfig.xml" })
public class OrderSettlementAPIImplTestUnit {

	@Resource
	private OrderSettleMentAPI orderSettleMentAPI;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Rollback(false)
	public void testOrderSettlement() {
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

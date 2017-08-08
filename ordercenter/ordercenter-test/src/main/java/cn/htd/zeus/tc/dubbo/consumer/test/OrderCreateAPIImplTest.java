package cn.htd.zeus.tc.dubbo.consumer.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.zeus.tc.api.OrderCreateAPI;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;
import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateItemListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

import com.alibaba.fastjson.JSONObject;

public class OrderCreateAPIImplTest {
	public static void main(String[] args) throws UnknownHostException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "test-consumer.xml","test-tc-redis.xml"});
		context.start();
		OrderCreateAPI test = (OrderCreateAPI) context.getBean("orderCreateAPI");
		//GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
		OrderCreateInfoReqDTO orderCreateInfo = new OrderCreateInfoReqDTO();
		//优惠券开始
	 // orderCreateInfo.setCouponCode("10000000000066");
		//orderCreateInfo.setPromotionType("1");
		//优惠券结束
		// 此地方就是生成订单的方法
		int i = 15;
		int messageId = 12222+i;
		int tradeNo = 1223344+i;
		int orderNo = 32223+i;
		int orderItemNo = 333331+i;
		
		orderCreateInfo.setBuyerCode("HTD_13125455");
		orderCreateInfo.setCouponCode("111111");
		orderCreateInfo.setPromotionId("1");
		orderCreateInfo.setIsNeedInvoice(1);// 是否需要发票
		orderCreateInfo.setInvoiceType("1");
		orderCreateInfo.setInvoiceNotify("汇通达");
		orderCreateInfo.setDeliveryType("2");
		orderCreateInfo.setMessageId(String.valueOf(messageId));
		// 调试拼装 用的是固定数据
		List<OrderCreateListInfoReqDTO> orderList = new ArrayList<OrderCreateListInfoReqDTO>();
		orderCreateInfo.setOrderList(orderList);
		orderCreateInfo.setTradeNo(String.valueOf(tradeNo));
		orderCreateInfo.setConsigneeAddressProvince("安徽砀山县");
		orderCreateInfo.setSite("021");
		OrderCreateListInfoReqDTO inforeqDto = new OrderCreateListInfoReqDTO();
		inforeqDto.setHasProductplusFlag(1);
		inforeqDto.setOrderFrom("2");
		inforeqDto.setOrderNo(String.valueOf(orderNo));
		inforeqDto.setOrderRemarks("游亚军");
		inforeqDto.setSellerCode("0832");
		inforeqDto.setShopId(5L);
		inforeqDto.setShopName("yyj");
		List<OrderCreateItemListInfoReqDTO> ordreItemList = new ArrayList<OrderCreateItemListInfoReqDTO>();
		OrderCreateItemListInfoReqDTO itemInfoReqDto = new OrderCreateItemListInfoReqDTO();
		itemInfoReqDto.setOrderItemNo(String.valueOf(orderItemNo));
		itemInfoReqDto.setSkuCode("HTDH_100000001");
		itemInfoReqDto.setGoodsCount(1L);
		itemInfoReqDto.setChannelCode("20");
		itemInfoReqDto.setIsBoxFlag(1);
		itemInfoReqDto.setIsHasDevRelation(1);
		ordreItemList.add(itemInfoReqDto);
		inforeqDto.setOrderItemList(ordreItemList);
		orderList.add(inforeqDto);

		OrderCreateInfoResDTO resDTO = test.orderCreate(orderCreateInfo);
		System.out.println(JSONObject.toJSONString(resDTO));
	}
}

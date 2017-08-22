
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

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import cn.htd.zeus.tc.api.OrderCreateAPI;
import cn.htd.zeus.tc.biz.service.OrderCreateService;
import cn.htd.zeus.tc.dto.response.OrderCreateInfoResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreate4huilinReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateOrderListInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateSkuListInfoReqDTO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext_test.xml",
		"classpath:mybatis/sqlconfig/sqlMapConfig.xml" })
public class OrderCreateAPIImplTestUnit {

	@Resource
	private OrderCreateService orderCreateService;

	@Resource
	private OrderCreateAPI orderCreateAPI;

	@Before
	public void setUp() throws Exception {
	}

	// @Test
	// @Rollback(false)
	// public void testOrderCreate() {
	// try {
	//
	// OrderCreateInfoReqDTO orderCreateInfo = new OrderCreateInfoReqDTO();
	// // 优惠券开始
	// /*
	// * orderCreateInfo.setCouponCode("100000390507");
	// * orderCreateInfo.setSeckillLockNo(GenerateIdsUtil.generateId(
	// * GenerateIdsUtil.getHostIp()));
	// * orderCreateInfo.setPromotionType("1");
	// * orderCreateInfo.setPromotionId("1172145420288");
	// */
	// // orderCreateInfo.setLevelCode("17000000025301");
	// // 优惠券结束
	// // 此地方就是生成订单的方法
	// // orderCreateInfo.setBuyerCode("htd1051000");
	//
	// orderCreateInfo.setIsNeedInvoice(1);// 是否需要发票
	// orderCreateInfo.setInvoiceType("1");
	// orderCreateInfo.setInvoiceNotify("汇通达");
	// orderCreateInfo.setDeliveryType("2");
	// orderCreateInfo.setMessageId(GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp()));
	// // 调试拼装 用的是固定数据
	// List<OrderCreateListInfoReqDTO> orderList = new
	// ArrayList<OrderCreateListInfoReqDTO>();
	// orderCreateInfo.setOrderList(orderList);
	// orderCreateInfo.setTradeNo(GenerateIdsUtil.generateTradeID("10"));
	// orderCreateInfo.setConsigneeAddressProvince("安徽砀山县");
	// orderCreateInfo.setSite("3201");
	// OrderCreateListInfoReqDTO inforeqDto = new OrderCreateListInfoReqDTO();
	// inforeqDto.setOrderFrom("1");
	// inforeqDto.setOrderNo("2012345678901234567");
	// inforeqDto.setOrderRemarks("游亚军");
	// inforeqDto.setSellerCode("htd1000000");
	// inforeqDto.setShopId(532L);
	// inforeqDto.setShopName("哇哈哈");
	// List<OrderCreateItemListInfoReqDTO> ordreItemList = new
	// ArrayList<OrderCreateItemListInfoReqDTO>();
	// OrderCreateItemListInfoReqDTO itemInfoReqDto = new
	// OrderCreateItemListInfoReqDTO();
	// itemInfoReqDto.setOrderItemNo("201234567890123456789");
	// itemInfoReqDto.setSkuCode("HTDH_0000084301");
	// itemInfoReqDto.setGoodsCount(1L);
	// itemInfoReqDto.setChannelCode("10");
	// itemInfoReqDto.setIsBoxFlag(0);
	// itemInfoReqDto.setIsHasDevRelation(1);
	// ordreItemList.add(itemInfoReqDto);
	// inforeqDto.setOrderItemList(ordreItemList);
	// orderList.add(inforeqDto);
	//
	// OrderCreateInfoResDTO orderCreateInfoDMO =
	// orderCreateAPI.orderCreate(orderCreateInfo);
	// System.out.println("create::::::::::;;;" +
	// JSONObject.toJSONString(orderCreateInfoDMO));
	//
	// } catch (Exception e) {
	// StringWriter w = new StringWriter();
	// e.printStackTrace(new PrintWriter(w));
	// System.out.println("创建订单时候发生异常:" + w.toString());
	// }
	// }

	@Test
	@Rollback(false)
	public void testCreateOrder4huilin() {
		OrderCreate4huilinReqDTO orderCreate4huilinReqDTO = new OrderCreate4huilinReqDTO();
		orderCreate4huilinReqDTO.setMessageId("123456");
		orderCreate4huilinReqDTO.setBuyerId(15965L);
		
		List<OrderCreateOrderListInfoReqDTO> orderList = new ArrayList<OrderCreateOrderListInfoReqDTO>();
		OrderCreateOrderListInfoReqDTO order1 = new OrderCreateOrderListInfoReqDTO();
		order1.setSellerId(33131L);
		order1.setOrderFrom("5");
		
		List<OrderCreateSkuListInfoReqDTO> skuList = new ArrayList<OrderCreateSkuListInfoReqDTO>();
		OrderCreateSkuListInfoReqDTO sku = new OrderCreateSkuListInfoReqDTO();
		sku.setSkuCode("HTDH_0000035411");//35411
		sku.setGoodsCount(1L);
		sku.setChannelCode("10");
		sku.setIsBoxFlag(0);
		skuList.add(sku);
		
		order1.setSkuList(skuList);
		orderList.add(order1);
		
		OrderCreateOrderListInfoReqDTO order2 = new OrderCreateOrderListInfoReqDTO();
		order2.setSellerId(33131L);
		order2.setOrderFrom("5");
		order2.setSkuList(skuList);
		orderList.add(order2);
		
		orderCreate4huilinReqDTO.setOrderList(orderList);
		
		OrderCreateInfoResDTO res = orderCreateAPI.orderCreate4Huilin(orderCreate4huilinReqDTO);
		System.out.println("create::::::::::;;;" + JSONObject.toJSONString(res));
	}
	
	public static void main(String[] args) {
		OrderCreate4huilinReqDTO orderCreate4huilinReqDTO = new OrderCreate4huilinReqDTO();
		orderCreate4huilinReqDTO.setMessageId("123456");
		orderCreate4huilinReqDTO.setBuyerId(15965L);
		
		List<OrderCreateOrderListInfoReqDTO> orderList = new ArrayList<OrderCreateOrderListInfoReqDTO>();
		OrderCreateOrderListInfoReqDTO order1 = new OrderCreateOrderListInfoReqDTO();
		order1.setSellerId(33131L);
		order1.setOrderFrom("5");
		
		List<OrderCreateSkuListInfoReqDTO> skuList = new ArrayList<OrderCreateSkuListInfoReqDTO>();
		OrderCreateSkuListInfoReqDTO sku = new OrderCreateSkuListInfoReqDTO();
		sku.setSkuCode("HTDH_0000035411");//35411
		sku.setGoodsCount(1L);
		sku.setChannelCode("10");
		sku.setIsBoxFlag(0);
		skuList.add(sku);
		
		order1.setSkuList(skuList);
		orderList.add(order1);
		
		OrderCreateOrderListInfoReqDTO order2 = new OrderCreateOrderListInfoReqDTO();
		order2.setSellerId(33131L);
		order2.setOrderFrom("5");
		order2.setSkuList(skuList);
		orderList.add(order2);
		
		orderCreate4huilinReqDTO.setOrderList(orderList);
		System.out.println(JSONObject.toJSONString(orderCreate4huilinReqDTO));
	}
}

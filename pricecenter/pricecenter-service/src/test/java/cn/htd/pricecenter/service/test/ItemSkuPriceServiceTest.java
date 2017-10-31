package cn.htd.pricecenter.service.test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.middleware.JdItemPriceResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.HzgPriceInDTO;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import cn.htd.pricecenter.service.common.CommonTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

public class ItemSkuPriceServiceTest extends CommonTest{
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	
	@Test
	public void batchQueryItemSkuBasePriceTest_withoutparam(){
		List<Long> skuIdList =Lists.newArrayList();
		ExecuteResult<List<ItemSkuBasePrice>> result=itemSkuPriceService.batchQueryItemSkuBasePrice(skuIdList);
		Assert.assertFalse(result.isSuccess());
	}
	
	@Test
	public void batchQueryItemSkuBasePriceTest_fullparam(){
		List<Long> skuIdList =Lists.newArrayList();
		skuIdList.add(1000018838L);
		ExecuteResult<List<ItemSkuBasePrice>> result=itemSkuPriceService.batchQueryItemSkuBasePrice(skuIdList);
		if(result!=null&&result.getResult()!=null&&CollectionUtils.isNotEmpty(result.getResult())){
			for(ItemSkuBasePrice b:result.getResult()){
				System.out.println(b.getSkuId());
			}
		}
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void queryStandardPrice4InnerSeller_hasSkuId(){
		Long skuId=1000038518L;
		ExecuteResult<StandardPriceDTO> result=itemSkuPriceService.queryStandardPrice4InnerSeller(skuId, 1);
		if(result!=null&&result.getResult()!=null){
			StandardPriceDTO standardPrice=result.getResult();
			standardPrice.getItemSkuBasePrice();
			standardPrice.getAreaPriceList();
			standardPrice.getItemSkuMemberGroupPriceList();
			standardPrice.getItemSkuMemberLevelPriceList();
		}
		
		Assert.assertTrue(result.isSuccess());
	}

	@Test
	public void testQueryItemSkuBasePrice() {
		ExecuteResult<ItemSkuBasePriceDTO> executeResult = itemSkuPriceService.queryItemSkuBasePrice(1000036347L);
		System.out.println(executeResult.getResult());
		Assert.assertTrue(executeResult.isSuccess());
	}

	@Test
	public void testBatchUpdateItemSkuLadderPrice() {
		List<ItemSkuLadderPrice> skuLadderPriceList = itemSkuPriceService.getSkuLadderPrice(1000029374L);
		for (ItemSkuLadderPrice itemSkuLadderPrice : skuLadderPriceList) {
			itemSkuLadderPrice.setLadderId(Long.valueOf(new Random().nextInt(10000)));
		}
		ExecuteResult<String> executeResult = itemSkuPriceService.batchUpdateItemSkuLadderPrice(skuLadderPriceList);
		System.out.println(executeResult.getResult());
		Assert.assertTrue(executeResult.isSuccess());
	}
	
	@Test
	public void testDeleteAreaPrice(){
		itemSkuPriceService.deleteAreaPrice(1000029439L, "025");
	}

	@Test
	public void testQueryItemSkuBasePriceByItemCode() {
		String itemCode = "376056";
		ExecuteResult<ItemSkuBasePrice> result = itemSkuPriceService.queryItemSkuBasePriceByItemCode(itemCode);
		Assert.assertTrue(result.isSuccess());
		System.out.println(result.getResult());
	}

	@Test
	public void testQueryLadderPriceBySellerIdAndSkuId(){
		DataGrid<ItemSkuLadderPrice> dataGrid = itemSkuPriceService.queryLadderPriceBySellerIdAndSkuId(1l,1000029374l);
		System.out.println("hehe");
	}

	@Test
	public void testDeleteLadderPriceBySkuId(){
		ItemSkuLadderPrice dto = new ItemSkuLadderPrice();
		dto.setSkuId(1000029362l);
		dto.setModifyId(999l);
		dto.setModifyName("周杰伦");
		ExecuteResult<String> result = itemSkuPriceService.deleteLadderPriceBySkuId(dto);
	}
	
	@Test
	public void tesQueryCommonItemSkuPrice(){
		QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO=new QueryCommonItemSkuPriceDTO();
//		"buyerGrade":"1","isBoxFlag":1,"isHasDevRelation":1,"isLogin":1,"itemChannelCode":"10","memberGroupId":10113,"skuId":1000038547}
		queryCommonItemSkuPriceDTO.setBuyerGrade("1");
		queryCommonItemSkuPriceDTO.setCitySiteCode("1");
		queryCommonItemSkuPriceDTO.setIsBoxFlag(1);
		queryCommonItemSkuPriceDTO.setIsHasDevRelation(1);
		queryCommonItemSkuPriceDTO.setIsLogin(1);
		queryCommonItemSkuPriceDTO.setItemChannelCode("10");
		queryCommonItemSkuPriceDTO.setMemberGroupId(10113L);
		queryCommonItemSkuPriceDTO.setSkuId(1000038547L);
		ExecuteResult<CommonItemSkuPriceDTO> result = itemSkuPriceService.queryCommonItemSkuPrice(queryCommonItemSkuPriceDTO);
	}


	@Test
	public void tesQueryInnerItemSkuMemberLevelPrice(){
		ExecuteResult<InnerItemSkuPrice> result = itemSkuPriceService.queryInnerItemSkuMemberLevelPrice(1000018838l, "3", "6", 0);
		System.out.println(result.getResult());
	}

	@Test
	public void tesqueryOrderItemSkuPrice(){
		QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO = new QueryCommonItemSkuPriceDTO();
		queryCommonItemSkuPriceDTO.setIsLogin(1);//
		queryCommonItemSkuPriceDTO.setIsHasDevRelation(1);
		queryCommonItemSkuPriceDTO.setSkuId(1100029522L);
		queryCommonItemSkuPriceDTO.setItemChannelCode("3010");
		itemSkuPriceService.queryOrderItemSkuPrice(queryCommonItemSkuPriceDTO);
	}
	
	@Test
	public void testQueryJdItemPrice(){
		//String result="";
//				try{
//					//result=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_JD_PRICE_URL+"?skuIds=1454910&token="+MiddlewareInterfaceUtil.getAccessToken(), false);
//					//{"code":1,"msg":"价格为null或者小于0时，为暂无报价","data":[{"price":1170.0,"skuId":1454910,"jdPrice":1199.0}]}
//				
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//				
		
		JdItemPriceResponseDTO result=MiddlewareInterfaceUtil.getJdItemRealPrice("1539467");
		System.out.println(result.getJdPrice());
	}
	
	@Test
	public void testSaveHzgPrice(){
		HzgPriceInDTO hzgPriceInDTO=new HzgPriceInDTO();
		hzgPriceInDTO.setItemId(2L);
		hzgPriceInDTO.setOperatorId(897L);
		hzgPriceInDTO.setOperatorName("zhangxiaolong");
		hzgPriceInDTO.setRetailPrice(new BigDecimal("38.90"));
		hzgPriceInDTO.setSalePrice(new BigDecimal("37.90"));
		hzgPriceInDTO.setVipPrice(new BigDecimal("36.90"));
		hzgPriceInDTO.setSellerId(3752L);
		hzgPriceInDTO.setShopId(46L);
		hzgPriceInDTO.setSkuId(2L);
		itemSkuPriceService.saveHzgTerminalPrice(hzgPriceInDTO);
	}
	
	@Test
	public void testPrice(){
		 List<ItemSkuLadderPrice> list = itemSkuPriceService.queryMobileExternalLadderPriceBySkuId("218845");
		 System.out.println(JSON.toJSONString(list));
	}

	
}

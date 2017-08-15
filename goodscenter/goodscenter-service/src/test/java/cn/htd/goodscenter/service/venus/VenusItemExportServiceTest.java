package cn.htd.goodscenter.service.venus;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.dto.enums.ItemStatusEnum;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSetShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuAutoShelfStatusInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInfoInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusQueryDropdownItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemListStyleOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemMainDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemListOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusWarningStockLevelListOutDTO;
import cn.htd.goodscenter.dto.venus.po.QuerySkuPublishInfoDetailParamDTO;
import cn.htd.goodscenter.dto.venus.po.QueryVenusItemListParamDTO;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.goodscenter.test.common.CommonTest;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;

import com.google.common.collect.Lists;

public class VenusItemExportServiceTest extends CommonTest {
	@Resource
	private VenusItemExportService venusItemExportService;
	
	@Test
	public void  testAddItem_empty_param(){
		VenusItemInDTO venusItemInDTO = new VenusItemInDTO();
	    ExecuteResult<String>result=venusItemExportService.addItem(venusItemInDTO);
	    for(String str:result.getErrorMessages()){
	    	System.out.println(str);
	    }
	    Assert.assertNotNull(result.getErrorMessages());;
	}
	
	@Test
	public void  testAddItem_full_param(){
		VenusItemInDTO venusItemInDTO = new VenusItemInDTO();
		venusItemInDTO.setProductCode("HTDH_20000001");
		venusItemInDTO.setProductStatus(ItemStatusEnum.NOT_PUBLISH.getCode());
		venusItemInDTO.setFirstLevelCategoryId(11111L);
		venusItemInDTO.setSecondLevelCategoryId(2222L);
		venusItemInDTO.setThirdLevelCategoryId(123L);
		venusItemInDTO.setBrandId(456L);
		venusItemInDTO.setSerial("c200");
		venusItemInDTO.setProductName("创维-配件-65E99ST");
		venusItemInDTO.setUnit("个");
		venusItemInDTO.setTaxRate("0.007");
		venusItemInDTO.setGrossWeight("10");
		venusItemInDTO.setNetWeight("8");
		venusItemInDTO.setLength("100");
		venusItemInDTO.setWidth("20");
		venusItemInDTO.setHeight("10");
		venusItemInDTO.setColor("红");
		venusItemInDTO.setAd("红");
		venusItemInDTO.setOriginPlace("法国");
		List<ItemPicture> pics=Lists.newArrayList();
		ItemPicture pic=new ItemPicture();
		pic.setDeleteFlag(0);
		pic.setIsFirst(1);
		pic.setPictureStatus(1);
		pic.setPictureUrl("https://img12.360buyimg.com/n1/jfs/t2695/281/4257847456/241390/aeab7302/57b58ac5N7078ceaa.jpg");
		pics.add(pic);
		venusItemInDTO.setPictures(pics);
		venusItemInDTO.setCategoryAttribute("资质认证：非转基因国产/进口：国产");
		
		ItemDescribe desc=new ItemDescribe();
		desc.setDescribeContent("123");
		venusItemInDTO.setDescribe(desc);
		venusItemInDTO.setOperatorId(111111L);
		venusItemInDTO.setOperatorName("测试帐号1");
		venusItemInDTO.setHtdVendorId(1000000L);
		venusItemInDTO.setShopId(10L);
	    ExecuteResult<String> result=venusItemExportService.addItem(venusItemInDTO);
	    for(String str:result.getErrorMessages()){
	    	System.out.println(str);
	    }
	    Assert.assertTrue(ErrorCodes.SUCCESS.name().equals(result.getCode()));
	}
	
	@Test
	public void testUpdateItem_wihtoutPK(){
		VenusItemInDTO venusItemInDTO = new VenusItemInDTO();
		venusItemInDTO.setItemId(23L);
		venusItemInDTO.setProductCode("HTDH_20000001");
		venusItemInDTO.setProductStatus(ItemStatusEnum.NOT_PUBLISH.getCode());
		venusItemInDTO.setFirstLevelCategoryId(11111L);
		venusItemInDTO.setSecondLevelCategoryId(2222L);
		venusItemInDTO.setThirdLevelCategoryId(123L);
		venusItemInDTO.setBrandId(456L);
		venusItemInDTO.setSerial("c250");
		venusItemInDTO.setProductName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		venusItemInDTO.setUnit("12个");
		venusItemInDTO.setTaxRate("0.007");
		venusItemInDTO.setGrossWeight("10");
		venusItemInDTO.setNetWeight("8");
		venusItemInDTO.setLength("100");
		venusItemInDTO.setWidth("20");
		venusItemInDTO.setHeight("10");
		venusItemInDTO.setColor("红");
		venusItemInDTO.setAd("红");
		venusItemInDTO.setOriginPlace("法国");
		List<ItemPicture> pics=Lists.newArrayList();
		ItemPicture pic=new ItemPicture();
		pic.setDeleteFlag(0);
		pic.setIsFirst(1);
		pic.setPictureStatus(1);
		pic.setPictureUrl("https://img12.360buyimg.com/n1/jfs/t2695/281/4257847456/241390/aeab7302/57b58ac5N7078ceaa.jpg");
		pics.add(pic);
		venusItemInDTO.setPictures(pics);
		venusItemInDTO.setCategoryAttribute("资质认证：非转基因国产/进口：国产");
		
		ItemDescribe desc=new ItemDescribe();
		desc.setDescribeContent("this is a description");
		venusItemInDTO.setDescribe(desc);
		venusItemInDTO.setOperatorId(111111L);
		venusItemInDTO.setOperatorName("测试帐号1");
		venusItemInDTO.setHtdVendorId(1000000L);
		venusItemInDTO.setShopId(10L);
		ExecuteResult<String> result=venusItemExportService.updateItem(venusItemInDTO);
	    for(String str:result.getErrorMessages()){
	    	System.out.println(str);
	    }
	    
	    Assert.assertTrue(result.isSuccess());
		
	}
	
	@Test
	public void testQueryItemList_wihtoutParam(){
		QueryVenusItemListParamDTO queryVenusItemListPo =new QueryVenusItemListParamDTO();
		queryVenusItemListPo.setProductCode("HTDH_100000000");
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> result=venusItemExportService.queryItemSkuList(queryVenusItemListPo, pager);
		for(VenusItemListStyleOutDTO v:result.getResult().getRows()){
			System.out.println(v.getProductName());
		}
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryItemList_wihtParam(){
		QueryVenusItemListParamDTO queryVenusItemListPo =new QueryVenusItemListParamDTO();
		queryVenusItemListPo.setProductStatus("0");
		queryVenusItemListPo.setHtdVendorId(1L);
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		ExecuteResult<DataGrid<VenusItemListStyleOutDTO>> result=venusItemExportService.queryItemSkuList(queryVenusItemListPo, pager);
		for(VenusItemListStyleOutDTO v:result.getResult().getRows()){
			System.out.println(v.getProductName());
		}
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryItemSkuPublishInfoList_withParam(){
		VenusItemSkuPublishInfoInDTO venumItemIn=new VenusItemSkuPublishInfoInDTO();
		venumItemIn.setProductCode("HTDH_100000000");
		venumItemIn.setSellerId(1000000L);
		//venumItemIn.setShelfType("1");
		//venumItemIn.setSortColumn("t.item_name");
		//venumItemIn.setSortType("2");
		//venumItemIn.setShelfStatus("0");
		//venumItemIn.setSort("name");
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		ExecuteResult<DataGrid<VenusItemSkuPublishInfoOutDTO>> result=venusItemExportService.queryItemSkuPublishInfoList(venumItemIn, pager);
		if(result!=null&&result.getResult()!=null&&CollectionUtils.isNotEmpty(result.getResult().getRows())){
			for(VenusItemSkuPublishInfoOutDTO v:result.getResult().getRows()){
				System.out.println(v.getItemName());
			}
		}
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryItemSkuPublishInfoDetail_withParam(){
		QuerySkuPublishInfoDetailParamDTO querySkuPublishInfoDetailParamDTO=new QuerySkuPublishInfoDetailParamDTO();
		querySkuPublishInfoDetailParamDTO.setShelfType("1");
		querySkuPublishInfoDetailParamDTO.setSkuId(1000029361L);
		ExecuteResult<VenusItemSkuPublishInfoDetailOutDTO> result=
				venusItemExportService.queryItemSkuPublishInfoDetail(querySkuPublishInfoDetailParamDTO);
		System.out.println(result.getResult().getSkuCode());
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testTxPublishItemSkuInfo_withParam(){
		VenusItemSkuPublishInDTO venusItemSkuPublishInDTO=new VenusItemSkuPublishInDTO();
		
		venusItemSkuPublishInDTO.setSubTitle("zhangxiaolong unit test subtitle");
		venusItemSkuPublishInDTO.setSkuId(99L);
		venusItemSkuPublishInDTO.setSkuCode("99");
		venusItemSkuPublishInDTO.setDisplayQty("100");
		venusItemSkuPublishInDTO.setShelfType("1");
		venusItemSkuPublishInDTO.setOperatorId(1234L);
		venusItemSkuPublishInDTO.setOperatorName("jerry");
		venusItemSkuPublishInDTO.setSupplierCode("0801");
		
		venusItemSkuPublishInDTO.setPreSaleFlag(1);
		
		StandardPriceDTO standardPrice=new StandardPriceDTO();
		ItemSkuBasePrice itemSkuBasePrice=new ItemSkuBasePrice();
		itemSkuBasePrice.setBoxSalePrice(new BigDecimal(122));
		itemSkuBasePrice.setCostPrice(new BigDecimal(132));
		itemSkuBasePrice.setRetailPrice(new BigDecimal(132));
		itemSkuBasePrice.setSellerId(1l);
		itemSkuBasePrice.setShopId(0l);
		itemSkuBasePrice.setSkuId(1000029361L);
		standardPrice.setItemSkuBasePrice(itemSkuBasePrice);
		venusItemSkuPublishInDTO.setStandardPrice(standardPrice);
		//
		HzgPriceDTO hzgPriceInDTO=new HzgPriceDTO();
		hzgPriceInDTO.setRetailPrice(new BigDecimal("38.90"));
		hzgPriceInDTO.setSalePrice(new BigDecimal("37.90"));
		hzgPriceInDTO.setVipPrice(new BigDecimal("36.90"));
		standardPrice.setHzgPriceDTO(hzgPriceInDTO);
		
		ExecuteResult<String> result=venusItemExportService.txPublishItemSkuInfo(venusItemSkuPublishInDTO);
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryItemMainDataList_withParam(){
		VenusItemMainDataInDTO venusItemMainData=new VenusItemMainDataInDTO();
		venusItemMainData.setProductName("HTDH_100000012");
		venusItemMainData.setSellerId(1000000L);
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		
		ExecuteResult<DataGrid<VenusItemMainDataOutDTO>> result=venusItemExportService.queryItemMainDataList(venusItemMainData, pager);
		
		Assert.assertTrue(result.isSuccess());
		
		Assert.assertNotNull(result.getResult());
		
		Assert.assertNotNull(result.getResult().getRows());
		
		System.out.println("total:"+result.getResult().getTotal());
		
		for(VenusItemMainDataOutDTO v:result.getResult().getRows()){
			System.out.println(v.getItemName());
		}
	}
	
	@Test
	public void testQueryItemSkuDetail_withParam(){
		Long skuId=1000029361L;
		ExecuteResult<VenusItemSkuDetailOutDTO> result=venusItemExportService.queryItemSkuDetail(skuId);
		Assert.assertTrue(result.isSuccess());
		Assert.assertNotNull(result.getResult());
		
		System.out.println(result.getResult().getItemName());
	}
	
	@Test
	public void testBatchSetShelfStatus_withParam(){
		List<Long> list=Lists.newArrayList(1000029362L);
		VenusItemSetShelfStatusInDTO v=new VenusItemSetShelfStatusInDTO();
		v.setOperatorId(111L);
		v.setOperatorName("jerru");
		v.setSetStatusFlag("1");
		v.setShelfType("0");
		v.setSkuIdList(list);
		ExecuteResult<String> s=venusItemExportService.txBatchSetItemSkuOnShelfStatus(v);
		Assert.assertTrue(s.isSuccess());
	}
	
	@Test
	public void testOrderQueryItemSkuDetail_withParam(){
		String skuCode="1000004509";
		ExecuteResult<VenusOrderItemSkuDetailOutDTO> result=venusItemExportService.queryOrderItemSkuDetail(skuCode, "1");
		Assert.assertTrue(result.isSuccess());
		Assert.assertNotNull(result.getResult());
		
		System.out.println(result.getResult().getItemName());
	}
	
	@Test
	public void testTxBatchSetItemSkuAutoOnShelfStatus(){
		VenusItemSkuAutoShelfStatusInDTO venusItemSkuAutoShelfStatusInDTO=new VenusItemSkuAutoShelfStatusInDTO();
		venusItemSkuAutoShelfStatusInDTO.setDownShelfTime("2016-12-27 11:00");
		venusItemSkuAutoShelfStatusInDTO.setUpShelfTime("2016-12-20 11:00");
		venusItemSkuAutoShelfStatusInDTO.setOperatorId(123456L);
		venusItemSkuAutoShelfStatusInDTO.setOperatorName("monkey mao");
		venusItemSkuAutoShelfStatusInDTO.setShelfType("1");
		List<Long> skuIdList=Lists.newArrayList();
		skuIdList.add(1000029361L);
		skuIdList.add(1000029362L);
		venusItemSkuAutoShelfStatusInDTO.setSkuIdList(skuIdList);
		venusItemSkuAutoShelfStatusInDTO.setSortByStockStatus("1");
		venusItemSkuAutoShelfStatusInDTO.setUpShelfTime("2016-12-26 16:25:00");
		venusItemExportService.txBatchSetItemSkuAutoOnShelfStatus(venusItemSkuAutoShelfStatusInDTO);
	}
	
	@Test
	public void testGenerateSpuCode(){
		System.out.println(ItemCodeGenerator.generateSpuCode());
	}
	
	@Test
	public void testQueryDropdownItemList(){
		VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO=new VenusQueryDropdownItemInDTO();
		venusQueryDropdownItemInDTO.setBrandId(11L);
		venusQueryDropdownItemInDTO.setCatId(4L);
		venusQueryDropdownItemInDTO.setSellerId(1L);
		ExecuteResult<List<VenusQueryDropdownItemListOutDTO>> r=venusItemExportService.queryDropdownItemList(venusQueryDropdownItemInDTO);
		Assert.assertNotNull(r.getResult());
		Assert.assertTrue(r.isSuccess());
		
	}
	

	@Test
	public void testQueryDropdownItemDetail(){
		VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO=new VenusQueryDropdownItemInDTO();
		venusQueryDropdownItemInDTO.setSkuId(1000029361L);;
		venusQueryDropdownItemInDTO.setSellerId(1L);
		venusQueryDropdownItemInDTO.setSupplierCode("0803");
		ExecuteResult<VenusQueryDropdownItemDetailOutDTO> r=venusItemExportService.queryDropdownItemDetail(venusQueryDropdownItemInDTO);
		Assert.assertNotNull(r.getResult());
		Assert.assertTrue(r.isSuccess());
		
	}
	
	@Test
	public void testGetWarningStockLevelProductsInfoList(){
		ExecuteResult<List<VenusWarningStockLevelListOutDTO>> result=venusItemExportService.getWarningStockLevelProductsInfoList(1L);
		
		Assert.assertTrue(result.isSuccess());
		
		List<VenusWarningStockLevelListOutDTO> r=result.getResult();
		for(VenusWarningStockLevelListOutDTO v:r){
			System.out.println(v.getItemName());
		}
	}
	
	@Test
	public void testGetWarningStockLevelProductsData(){
		ExecuteResult<VenusWarningStockLevelDataOutDTO> result=venusItemExportService.getWarningStockLevelProductsData(1L);
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryVenusAggrementItemList(){
		VenusStockItemInDTO venusStockItemInDTO=new VenusStockItemInDTO();
		venusStockItemInDTO.setIsAgreement("1");
		venusStockItemInDTO.setSupplierCode("htd1000000");
		venusStockItemInDTO.setPageCount("5");
		venusStockItemInDTO.setPageIndex("1");
		venusStockItemInDTO.setCanSellNum("1");
		venusStockItemInDTO.setDistributionNum("1");
		venusStockItemInDTO.setSurplusNum("1");
		venusStockItemInDTO.setSupplierId(17606L);
		venusItemExportService.queryVenusAggrementItemList(venusStockItemInDTO);
	}
	
	@Test
	public void testQueryItemSpuDataList(){
		VenusItemMainDataInDTO venusItemMainData=new VenusItemMainDataInDTO();
		venusItemMainData.setProductName("10");
		venusItemMainData.setSellerId(2L);
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		ExecuteResult<DataGrid<VenusItemSpuDataOutDTO>> s=venusItemExportService.queryItemSpuDataList(venusItemMainData, pager);
		
		Assert.assertTrue(s.isSuccess());
	}
	
	@Test
	public void testApplyHtdProduct(){
		
		List<Long> spuIdList =Lists.newArrayList();
		spuIdList.add(16802L);
		
		Pager<String> pager=new Pager<String>();
		pager.setRows(10);
		pager.setPage(1);
		ExecuteResult<String> s=venusItemExportService.applyItemSpu2HtdProduct(spuIdList, 
				"2", "0", "1", "zhangxiaolong");
				
		
		Assert.assertTrue(s.isSuccess());
	}
	
}

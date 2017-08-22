package cn.htd.goodscenter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.goodscenter.domain.PreSaleProductPush;
import cn.htd.goodscenter.dto.indto.ItemSpuInfoListInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockSearchInDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoDetailOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoListOutDTO;
import cn.htd.goodscenter.service.task.PreSaleProductPushTask;
import cn.htd.goodscenter.service.task.PreSaleProductQueryTask;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.junit.Assert;
import org.junit.Test;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.goodscenter.common.utils.SpringUtils;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.ItemAdDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemDBDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemQueryInDTO;
import cn.htd.goodscenter.dto.ItemQueryOutDTO;
import cn.htd.goodscenter.dto.ItemShopCartDTO;
import cn.htd.goodscenter.dto.ItemShopCidDTO;
import cn.htd.goodscenter.dto.ItemStatusModifyDTO;
import cn.htd.goodscenter.dto.SkuInfoDTO;
import cn.htd.goodscenter.dto.SkuPictureDTO;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.WaitAuditItemInfoDTO;
import cn.htd.goodscenter.dto.enums.ItemPlatLinkStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemStatusEnum;
import cn.htd.goodscenter.dto.indto.PauseItemInDTO;
import cn.htd.goodscenter.dto.outdto.ItemToDoCountDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import cn.htd.middleware.common.message.erp.ProductMessage;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ItemExportServiceImplAllTest extends CommonTest {

	@Resource
	protected ItemExportService itemExportService;
	@Resource
	protected ItemEvaluationService itemEvaluationService;
	@Resource
	protected ItemSpuExportService itemSpuExportService;

	@Test
	public void testQueryItemList() throws Exception {
		// 入参数
		ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
		//itemInDTO.setShopFreightTemplateId(720L);
		List<Integer> itemStatusList = new ArrayList<Integer>();
		itemStatusList.add(ItemStatusEnum.NOT_PUBLISH.getCode());
		itemStatusList.add(ItemStatusEnum.AUDITING.getCode());
		itemStatusList.add(ItemStatusEnum.SHELVING.getCode());
		itemStatusList.add(ItemStatusEnum.SALING.getCode());
		itemStatusList.add(ItemStatusEnum.UNSHELVED.getCode());
		itemStatusList.add(ItemStatusEnum.LOCKED.getCode());
		itemStatusList.add(ItemStatusEnum.APPLYING.getCode());
		itemStatusList.add(ItemStatusEnum.REJECTED.getCode());
		itemInDTO.setItemStatusList(itemStatusList);
		itemInDTO.setItemId(1000018838);
		itemInDTO.setItemName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		itemInDTO.setItemStatus(0);
		itemInDTO.setCid(11L);
		DataGrid<ItemQueryOutDTO> size = itemExportService.queryItemList(itemInDTO, null);
	}

	@Test
	public void testGetItemById() {
		Long id = 217276L;
		//Long id = 1000023420L;
		ExecuteResult<ItemDTO> result = this.itemExportService.getItemById(id,0);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testItemByItemIds() {
		Long[] iids = new Long[] { 1000000379L, 1000000380L };
		List<ItemDTO> result = this.itemExportService.getItemDTOByItemIds(iids);

		for (ItemDTO idto : result) {
			System.out.println(idto.getItemId() + " - " + idto.getItemName());
		}
	}

	@Test
	public void testGetSkuShopCart() {
		ItemShopCartDTO dto = new ItemShopCartDTO();
		dto.setAreaCode("11");// 省市区编码
		dto.setSkuId(1000029039L);// SKU id
		dto.setQty(2);// 数量
		dto.setShopId(2000000325L);// 店铺ID
		dto.setItemId(1000014636L);// 商品ID
		ExecuteResult<ItemShopCartDTO> result = this.itemExportService.getSkuShopCart(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyItemAdBatch() {
		List<ItemAdDTO> params = new ArrayList<ItemAdDTO>();
		ItemAdDTO dto = new ItemAdDTO();
		dto.setItemId(1L);
		dto.setAd("测试广告1");
		params.add(dto);
		ExecuteResult<String> result = this.itemExportService.modifyItemAdBatch(params);
		Assert.assertEquals(result.isSuccess(), true);
	}

	@Test
	public void testModifyItemShopCidBatch() {
		List<ItemShopCidDTO> params = new ArrayList<ItemShopCidDTO>();
		ItemShopCidDTO dto = new ItemShopCidDTO();
		dto.setItemId(1L);
		dto.setShopCid(1L);
		params.add(dto);
		dto = new ItemShopCidDTO();
		dto.setItemId(2L);
		dto.setShopCid(2L);
		params.add(dto);
		ExecuteResult<String> result = this.itemExportService.modifyItemShopCidBatch(params);
		Assert.assertEquals(result.isSuccess(), true);
	}

	@Test
	public void testAddItemInfo() {
		Long[] shopIds = new Long[] { 2000000348L, 2000000349L, 2000000350L, 2000000351L, 2000000352L, 2000000353L,
				2000000354L, 2000000355L, 2000000356L, 2000000357L, 2000000358L, 2000000359L, 2000000360L, 2000000361L,
				2000000362L, 2000000363L, 2000000364L, 2000000365L, 2000000366L, 2000000367L, 2000000368L, 2000000369L,
				2000000370L, 2000000371L, 2000000372L, 2000000373L, 2000000374L, 2000000375L, 2000000376L, 2000000377L,
				2000000378L, 2000000379L, 2000000380L, 2000000381L, 2000000382L, 2000000383L, 2000000384L, 2000000385L,
				2000000386L, 2000000387L, 2000000388L, 2000000389L, 2000000390L, 2000000391L, 2000000392L, 2000000393L,
				2000000394L, 2000000395L, 2000000396L, 2000000397L, 2000000398L, 2000000399L };

		long[] userIds = new long[] { 1000000289L, 1000000812L, 1000000813L, 1000000814L, 1000000815L, 1000000816L,
				1000000817L, 1000000818L, 1000000819L, 1000000820L, 1000000821L, 1000000822L, 1000000823L, 1000000824L,
				1000000825L, 1000000826L, 1000000827L, 1000000828L, 1000000829L, 1000000830L, 1000000831L, 1000000832L,
				1000000833L, 1000000834L, 1000000835L, 1000000836L, 1000000837L, 1000000838L, 1000000839L, 1000000840L,
				1000000841L, 1000000842L, 1000000843L, 1000000844L, 1000000845L, 1000000846L, 1000000847L, 1000000848L,
				1000000849L, 1000000850L, 1000000851L, 1000000852L, 1000000853L, 1000000854L, 1000000855L, 1000000856L,
				1000000857L, 1000000858L, 1000000859L, 1000000860L, 1000000861L, 1000000862 };

		for (int j = 0; j < 2; j++) {
			long userId = userIds[j];
			long shopId = shopIds[j];
			for (int i = 0; i < 2000; i++) {
				ItemDTO item = new ItemDTO();
				item.setOperator(1);// 操作者
				item.setAd("测试商品");
				item.setAddSource(1);// 自定义添加
				item.setAfterService("售后服务测试");
				item.setAttributesStr("574:1228;574:1229;");
				item.setAttrSaleStr("575:1230;575:1231;");
				item.setBrand(306L);
				item.setCid(480L);
				item.setDescribeUrl("商品描述测试");
				item.setGuidePrice(new BigDecimal("12300"));
				item.setHasPrice(1);
				item.setInventory(99999999);
				item.setItemName("测试商品" + i);
				item.setItemStatus(ItemStatusEnum.SALING.getCode());
				item.setMarketPrice(new BigDecimal(1000));
				item.setMarketPrice2(new BigDecimal(899));
				item.setOrigin("北京");
				item.setPackingList("商品包装");
				String[] picUrls = new String[] { "replaceUrl", "replaceUrl" };
				item.setPicUrls(picUrls);
				item.setPlatLinkStatus(null);
				item.setPlstItemId(null);
				item.setProductId(123456L);
				item.setSellerId(userId);
				//List<SellPriceDTO> sellPrices = new ArrayList<SellPriceDTO>();
				//SellPriceDTO price = new SellPriceDTO();
				//price.setAreaId(0L);
				//price.setAreaName("全国");
				//price.setCostPrice(new BigDecimal(1));
				//price.setMaketPirce(new BigDecimal(1));
				//price.setMaxNum(1000);
				//price.setMinNum(1);
				//price.setSellerId(userId);
				//price.setSellPrice(new BigDecimal(1));
				//price.setShopId(shopId);
				//price.setStepIndex(0);
				//sellPrices.add(price);
				//item.setSellPrices(sellPrices);
				item.setShopCid(1L);
				item.setShopId(shopId);
				List<SkuInfoDTO> skuInfos = new ArrayList<SkuInfoDTO>();
				SkuInfoDTO sku = new SkuInfoDTO();
				sku.setAttributes("575:1231;");
				//sku.setSellPrices(sellPrices);
				sku.setSkuInventory(99999999);
				List<SkuPictureDTO> skuPics = new ArrayList<SkuPictureDTO>();
				SkuPictureDTO skuPic = new SkuPictureDTO();
				skuPic.setPicUrl("replaceUrl");
				skuPics.add(skuPic);
				sku.setSkuPics(skuPics);
				sku.setSkuType(1L);
				skuInfos.add(sku);
				sku = new SkuInfoDTO();
				sku.setAttributes("575:1230;");
				//sku.setSellPrices(sellPrices);
				sku.setSkuInventory(99999999);
				skuPics = new ArrayList<SkuPictureDTO>();
				skuPic = new SkuPictureDTO();
				skuPic.setPicUrl("replaceUrl");
				skuPics.add(skuPic);
				sku.setSkuPics(skuPics);
				sku.setSkuType(1L);
				skuInfos.add(sku);
				item.setSkuInfos(skuInfos);
				item.setStatusChangeReason(null);
				item.setWeight(new BigDecimal(10000));

				item.setPlatLinkStatus(3);

				item.setWeightUnit("克");

				List<ItemAttrDTO> attrs = new ArrayList<ItemAttrDTO>();
				ItemAttrDTO itemAttr = new ItemAttrDTO();
				itemAttr.setId(574L);
				itemAttr.setName("年份");
				List<ItemAttrValueDTO> values = new ArrayList<ItemAttrValueDTO>();
				ItemAttrValueDTO val = new ItemAttrValueDTO();
				val.setAttrId(574L);
				val.setId(1228L);
				val.setName("1988");
				values.add(val);
				val = new ItemAttrValueDTO();
				val.setAttrId(574L);
				val.setId(1229L);
				val.setName("2000");
				values.add(val);
				itemAttr.setValues(values);
				attrs.add(itemAttr);
				item.setAttributes(attrs);

				attrs = new ArrayList<ItemAttrDTO>();
				itemAttr = new ItemAttrDTO();
				itemAttr.setId(575L);
				itemAttr.setName("颜色");
				values = new ArrayList<ItemAttrValueDTO>();
				val = new ItemAttrValueDTO();
				val.setAttrId(575L);
				val.setId(1230L);
				val.setName("黑");
				values.add(val);
				val = new ItemAttrValueDTO();
				val.setAttrId(575L);
				val.setId(1231L);
				val.setName("白");
				values.add(val);
				itemAttr.setValues(values);
				attrs.add(itemAttr);

				item.setAttrSale(attrs);
				ExecuteResult<ItemDTO> result = this.itemExportService.addItemInfo(item);
			}
		}
	}

	@Test
	public void queryItemByCid() {
		Pager page = new Pager();
		ExecuteResult<DataGrid<ItemQueryOutDTO>> result = itemExportService.queryItemByCid(347l, page);
		System.out.println("result----------" + JSON.toJSONString(result.getResult()));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyItemStatusBatch() {
		ItemStatusModifyDTO dto = new ItemStatusModifyDTO();
		dto.setItemStatus(ItemStatusEnum.SHELVING.getCode());
		dto.setOperator(1);// 操作商家商品
		dto.setUserId(1L);
		List<Long> list = new ArrayList<Long>();
		list.add(1000000318L);
		dto.setItemIds(list);
		dto.setCreatePlatItem(true);
		ExecuteResult<String> result = this.itemExportService.modifyItemStatusBatch(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyItemById() {
		ItemDTO item = new ItemDTO();
		//item = this.itemExportService.getItemById(1000018849L).getResult();
		//item.setWeightUnit("1");

		//List<ItemAttrDTO> attrs = new ArrayList<ItemAttrDTO>();
		//ItemAttrDTO itemAttr = new ItemAttrDTO();
		//itemAttr.setId(2L);
		//itemAttr.setName("商品属性1");
		//attrs.add(itemAttr);
		//List<ItemAttrValueDTO> values = new ArrayList<ItemAttrValueDTO>();
		//ItemAttrValueDTO val = new ItemAttrValueDTO();
		//val.setAttrId(2L);
		//val.setId(2L);
		//val.setName("商品属性值11");
		//values.add(val);
		//itemAttr.setValues(values);
		//attrs.add(itemAttr);
		//item.setAttributes(attrs);
		//item.setAttrSale(attrs);
		//item.setAttributesStr("2181:3574;2265:3798;");
		//item.setAttrSaleStr("7:9;7:10;8:12;8:13;");

//		item.setOperator(1);
//		item.setOperator(2);

		item.setAd("yoyoy1");
		item.setAttrSaleStr("7:9;7:10;8:12;8:13;");
		item.setBrand(4L);
		item.setCid(11L);
		item.setDescribeUrl("我就测试下描述");
		item.setErpFirstCategoryCode("1");
		item.setErpFiveCategoryCode("5");
		item.setHeight("10.00");
		item.setItemId(1000018849L);
		item.setItemQualification("尺寸：8寸，10寸，12寸；分辨率：1080，720；能效等级：1级，2级，4级，5句；");
		item.setLength("100.00");
		item.setModelType("c250");
    item.setModified(new Date());
		item.setModifyId(1L);
		item.setModifyName("gzgtest");
		item.setNetWeight(new BigDecimal("8"));
		item.setOrigin("法国");
		item.setProductChannelCode("10");
		item.setShopCid(9L);
		item.setWidth("20.00");
		item.setPackingList("");
		item.setAttrSale(new ArrayList<ItemAttrDTO>());
		item.setAttributes(new ArrayList<ItemAttrDTO>());
		item.setSkuInfos(new ArrayList<SkuInfoDTO>());

		//buchong
		item.setSpu(false);
		item.setWeight(new BigDecimal("12"));
		item.setTaxRate(new BigDecimal("0.008"));

		////sku picture
		//List<SkuInfoDTO> skuInfos = item.getSkuInfos();
		//List<SkuPictureDTO> pics = new ArrayList<SkuPictureDTO>();
		//for (int i = 0; i < skuInfos.size(); i++) {
		//	SkuInfoDTO skuInfoDTO = skuInfos.get(i);
		//	SkuPictureDTO spdTO = new SkuPictureDTO();
		//	spdTO.setSaleAttribute(i + ":" + i);
		//	spdTO.setPicUrl("picURL:www.baidu.com://"+ i +".jpg" );
		//	spdTO.setSkuId(1000029374L);
		//	pics.add(spdTO);
		//	skuInfoDTO.setSkuPics(pics);
		//}
		//item.setSkuInfos(skuInfos);

		//ITEM PICTURE
		String[] itemPicURLS = new String[2];
		for (int j = 88; j < 90; j++) {
			itemPicURLS[j - 88] = "itemPicURL:" +j;
		}
		item.setPicUrls(itemPicURLS);
		ExecuteResult<ItemDTO> result = this.itemExportService.modifyItemById(item);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryShopIdByPlatItemId() {
		ExecuteResult<DataGrid<ItemQueryOutDTO>> result = this.itemExportService.queryItemByPlatItemId(123L,new Pager<Long>());
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyItemPlatStatus() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1000000170L);
		ExecuteResult<String> result = this.itemExportService.modifyItemPlatStatus(ids, ItemPlatLinkStatusEnum.STORING.getCode());
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testModifyItemStatus() {
		ItemStatusModifyDTO inDTO = new ItemStatusModifyDTO();
		inDTO.setShopId(1L);
		inDTO.setChangeReason("关闭店铺下架");
		inDTO.setItemStatus(ItemStatusEnum.UNSHELVED.getCode());
		ExecuteResult<String> result = itemExportService.modifyShopItemStatus(inDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testDeleteItem() {
		ExecuteResult<String> result = itemExportService.deleteItem(10000247L);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testAddItemToPlat() {
		ExecuteResult<String> result = itemExportService.addItemToPlat(1000000011l);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryItemDB() {
		List<ItemDBDTO> itemDBDTOList = itemExportService.queryItemDB().getResult();
		System.out.println(itemDBDTOList.get(0).getType() + "-----------------------");

	}

	@Test
	public void testMondifyMarketPrice() {
		itemExportService.mondifyMarketPrice(new String[] { "1000000379", "1000000380" }, new BigDecimal(-1));
	}

	@Test
	public void testQueryItemSpuList(){
		SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
		spuInfoDTO.setItemName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		itemSpuExportService.queryByCondition(spuInfoDTO, null);
	}
	@Test
	public void testQueryByCondition(){
		SpuInfoDTO spuInfoDTO = null;
		Pager pager = new Pager();
		itemSpuExportService.queryByCondition(spuInfoDTO, pager);
	}

	@Test
	public void testGetItemSpuBySpuId(){
		ExecuteResult<SpuInfoDTO> result = itemSpuExportService.getItemSpuBySpuId(1l);
	}

	@Test
	public void testModifyItemSpuBySpuId(){
		SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
		spuInfoDTO.setSpuId(1L);
		spuInfoDTO.setVolume(BigDecimal.valueOf(1));
		spuInfoDTO.setPictureSize("lh");
		spuInfoDTO.setSpuDesc("gzg");
		spuInfoDTO.setBrandId(4L);
		spuInfoDTO.setCategoryId(11L);
		spuInfoDTO.setModifyId(1L);
		spuInfoDTO.setModifyName("杰克");
		spuInfoDTO.setGrossWeight(new BigDecimal("1.5"));
		spuInfoDTO.setNetWeight(new BigDecimal("25.3"));
		spuInfoDTO.setHigh(5);
		spuInfoDTO.setModelType("帝王之巅");
		itemSpuExportService.modifyItemSpuBySpuId(spuInfoDTO);
	}

	@Test
	public void testUpdateDeleteFlag(){
		SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
		spuInfoDTO.setDeleteFlag(Byte.valueOf("1"));
		itemSpuExportService.updateDeleteFlag(spuInfoDTO);
	}

	@Test
	public void testAddItemSpuInfo(){
		/**
		 * 联调的时候需要参数:
		 * 	itemId, create的一套, modify的一套
		 */
		SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
		spuInfoDTO.setItemId(1000018838L);/*
		spuInfoDTO.setSpuName("lhtest");
		spuInfoDTO.setSpuCode("1130");
		spuInfoDTO.setCategoryId(1L);
		spuInfoDTO.setBrandId(1L);
		spuInfoDTO.setCategoryAttributes("1:1;2:2");
		spuInfoDTO.setItemQualification("test");
		spuInfoDTO.setUnit(1L);
		spuInfoDTO.setGrossWeight(new BigDecimal(1));
		spuInfoDTO.setNetWeight(new BigDecimal(1));
		spuInfoDTO.setModelType("1");
		spuInfoDTO.setOrigin("test");
		spuInfoDTO.setDescribeId(1L);
		spuInfoDTO.setPackingList("一包");
		spuInfoDTO.setAfterService("七天无理由退款");
		spuInfoDTO.setStatus("1");
		spuInfoDTO.setErpFirstCategoryCode("1");
		spuInfoDTO.setErpFiveCategoryCode("5");*/
		spuInfoDTO.setCreateId(1L);
		spuInfoDTO.setCreateName("lhtest");
		spuInfoDTO.setCreateTime(new Date());
		spuInfoDTO.setModifyId(1L);
		spuInfoDTO.setModifyName("lhtest");/*
		spuInfoDTO.setWide(1);
		spuInfoDTO.setLength(2);
		spuInfoDTO.setHigh(3);

		spuInfoDTO.setPictureUrl("url");
		spuInfoDTO.setPictureSize("2");
		spuInfoDTO.setIsFirst(Byte.valueOf("1"));
		spuInfoDTO.setSortNum(1);

		spuInfoDTO.setSpuDesc("描述");

		spuInfoDTO.setSpuCode("1130");
		spuInfoDTO.setPictureSize("1130");
		spuInfoDTO.setSpuDesc("lhtest");*/
		itemSpuExportService.addItemSpuInfo(spuInfoDTO);
	}

	@Test
	public void testqueryItemSpuByName(){
		itemSpuExportService.queryItemSpuByName("创维-配件-65E99ST");
	}


	@Test
	public void testCheckErpFirstAndErpFiveCode(){
		boolean flag = itemExportService.checkErpFirstAndErpFiveCode("10111", "1002010101");
		Assert.assertTrue(flag);
	}
	@Test
	public void testItemToERPDown(){

		//DownErpItemDTO downErpItemDTO = new DownErpItemDTO();
		//downErpItemDTO.setProductERPCode("20000006");
		//downErpItemDTO.setBrandCode("4");
		//downErpItemDTO.setBrandName("海尔2");
		//downErpItemDTO.setCategoryCode("11");
		//downErpItemDTO.setCategoryName("冰箱");
		//downErpItemDTO.setChargeUnit("个");
		//downErpItemDTO.setMarque("KFR-51GW/dLN47-230（2）");
		//downErpItemDTO.setProductSpecifications("尺寸：8寸，10寸，12寸；分辨率：1080，720；能效等级：1级，2级，4级，5句；");
		//downErpItemDTO.setSKU("20000006");
		//downErpItemDTO.setProductName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		//downErpItemDTO.setOutputRate("0.007");
		//downErpItemDTO.setProductColorCode("0");
		//downErpItemDTO.setProductColorName("0");
		//downErpItemDTO.setRegistrar("0");
		//downErpItemDTO.setRegistrationDate(new Date());
		//downErpItemDTO.setClassABC(null);
		//downErpItemDTO.setOrigin("北京");
		//downErpItemDTO.setQualityGrade("0");
		//downErpItemDTO.setModifier("1");
		//downErpItemDTO.setModificationDate(new Date());
		//downErpItemDTO.setFunctionIntroduction("0");
		//downErpItemDTO.setProductType("0");
		//downErpItemDTO.setExfactoryPrice("0");
		//downErpItemDTO.setLastPurchasePrice("0");
		//downErpItemDTO.setPriceTag("1");
		//downErpItemDTO.setValidTags("1");
		//downErpItemDTO.setIncomeTaxRates("0.17");
		//downErpItemDTO.setMachineCodeMark("0");
		//downErpItemDTO.setManyCodeLabel("0");
		//downErpItemDTO.setCommodityCommission("0");
		//downErpItemDTO.setHighestPrice("0");
		//downErpItemDTO.setHighestPrice("0");
		//downErpItemDTO.setFindustry("1");
		//downErpItemDTO.setRetailPrice("0");
		//downErpItemDTO.setLowestDistributionPrice("0");
		//downErpItemDTO.setPricing("0");
		//downErpItemDTO.setPackingContent("1");
		//downErpItemDTO.setIsUpdateFlag(1);
		//downErpItemDTO.setWhetherOil("0");
		MQSendUtil mqSendUtil = SpringUtils.getBean(MQSendUtil.class);
		ProductMessage productMessage = new ProductMessage();
		productMessage.setProductCode("20000006");
		productMessage.setBrandCode("4");
		productMessage.setCategoryCode("11");
		productMessage.setChargeUnit("个");
		productMessage.setMarque("KFR-51GW/dLN47-230（2）");
		productMessage.setProductSpecifications("尺寸：8寸，10寸，12寸；分辨率：1080，720；能效等级：1级，2级，4级，5句；");
		productMessage.setProductName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		productMessage.setOutputRate("0.007");
		productMessage.setProductColorCode("0");
		productMessage.setProductColorName("0");
		productMessage.setOrigin("北京");
		productMessage.setQualityGrade("0");
		productMessage.setFunctionIntroduction("0");
		productMessage.setValidTags(1);
		productMessage.setIncomeTaxRates("0.17");
		productMessage.setPackingContent("1");
		productMessage.setIsUpdateFlag(1);
		mqSendUtil.sendToMQWithRoutingKey(productMessage, MQRoutingKeyConstant.ITEM_DOWN_ERP_ROUTING_KEY);
	}

	@Test
	public void testGetSpuInfoListByItemId(){
		ExecuteResult<List<SpuInfoDTO>> EX = itemSpuExportService.getSpuInfoListByItemId(1000018849L);
		System.out.println("success...");
	}

	@Test
	public void testQueryToDoCount(){
		PauseItemInDTO dto = new PauseItemInDTO();
		dto.setSellerId(1l);
		dto.setShopId(1l);
		DataGrid<ItemToDoCountDTO> list = itemExportService.queryToDoCount(dto);
		System.out.println("hehe");
	}

	@Test
	public void testQetItemByCode() {
		ExecuteResult<Item> executeResult = this.itemExportService.getItemByCode("10006975");
		Assert.assertTrue(executeResult.isSuccess());
		System.out.println(executeResult.getResult());
	}
	
	@Test
	public void testQueryItemListForSaleManageSystem() throws Exception {
		// 入参数
		ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
		//itemInDTO.setShopFreightTemplateId(720L);
		List<Integer> itemStatusList = new ArrayList<Integer>();
		itemStatusList.add(ItemStatusEnum.NOT_PUBLISH.getCode());
		itemStatusList.add(ItemStatusEnum.AUDITING.getCode());
		itemStatusList.add(ItemStatusEnum.SHELVING.getCode());
		itemStatusList.add(ItemStatusEnum.SALING.getCode());
		itemStatusList.add(ItemStatusEnum.UNSHELVED.getCode());
		itemStatusList.add(ItemStatusEnum.LOCKED.getCode());
		itemStatusList.add(ItemStatusEnum.APPLYING.getCode());
		itemStatusList.add(ItemStatusEnum.REJECTED.getCode());
		itemInDTO.setItemStatusList(itemStatusList);
		itemInDTO.setItemId(1000018838);
		itemInDTO.setItemName("阿玛尼(Emporio Armani) Renato 深棕色 皮革表带 男士商务时尚智能手表 ART3002");
		itemInDTO.setItemStatus(0);
		itemInDTO.setCid(11L);
		
		Pager p=new Pager();
		p.setPage(1);
		p.setRows(10);
		DataGrid<ItemQueryOutDTO> size = itemExportService.queryItemListForSaleManageSystem(itemInDTO, null);
	}
	
	@Test
	public void testQueryWaitAuditItemInfo(){
		ExecuteResult<List<WaitAuditItemInfoDTO>> re=itemExportService.queryWaitAuditItemInfo();
		System.out.println(re.getResult().get(0).getCount());
	}


	@Test
	public void testqueryItemSpuDetialBySpuCode(){
		ExecuteResult<ItemSpuInfoDetailOutDTO> executeResult = itemSpuExportService.queryItemSpuDetialBySpuCode("HP_0000009344");
	}


	@Test
	public void tesqueryItemSpuList4SupperBoss(){
		Pager page = new Pager();
		ExecuteResult<DataGrid<ItemSpuInfoListOutDTO>> executeResult  = itemSpuExportService.queryItemSpuList4SupperBoss("", page);
	}

	@Test
	public void testquerySyncItemStockSearchList() {
		SyncItemStockSearchInDTO syncItemStockSearchInDTO = new SyncItemStockSearchInDTO();
		syncItemStockSearchInDTO.setErpCode("119452");
		Pager page = new Pager();
		itemExportService.querySyncItemStockSearchList(syncItemStockSearchInDTO, page);
	}


	@Autowired
	@Qualifier("preSaleProductQueryTask")
	PreSaleProductQueryTask preSaleProductQueryTask;

	@Autowired
	@Qualifier("preSaleProductPushTask")
	PreSaleProductPushTask preSaleProductPushTask;

	@Test
	public void testpreSaleProductQueryTask() {
		List<TaskItemDefine> taskItemList = new ArrayList<>();
		TaskItemDefine taskItemDefine = new TaskItemDefine();
		taskItemDefine.setTaskItemId("0");
		taskItemList.add(taskItemDefine);
		try {
			preSaleProductQueryTask.selectTasks("", "", 1, taskItemList, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testpreSaleProductPushTask() {
		List<TaskItemDefine> taskItemList = new ArrayList<>();
		TaskItemDefine taskItemDefine = new TaskItemDefine();
		taskItemDefine.setTaskItemId("0");
		taskItemList.add(taskItemDefine);
		try {
			preSaleProductPushTask.selectTasks("", "", 1, taskItemList, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testpreSaleProductPushTask2() {
		List<TaskItemDefine> taskItemList = new ArrayList<>();
		TaskItemDefine taskItemDefine = new TaskItemDefine();
		taskItemDefine.setTaskItemId("0");
		taskItemList.add(taskItemDefine);
		try {
			List<PreSaleProductPush> preSaleProductPushList = preSaleProductPushTask.selectTasks("", "", 1, taskItemList, 100);
			preSaleProductPushTask.execute(preSaleProductPushList.toArray(new PreSaleProductPush[]{}), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
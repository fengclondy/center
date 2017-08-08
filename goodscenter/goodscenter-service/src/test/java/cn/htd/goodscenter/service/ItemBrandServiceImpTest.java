package cn.htd.goodscenter.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.BrandOfShopDTO;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.dto.indto.QueryBrandErpExceptionInDto;
import cn.htd.goodscenter.dto.indto.QueryItemBrandInDTO;
import cn.htd.goodscenter.test.common.CommonTest;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public class ItemBrandServiceImpTest extends CommonTest {

	@Resource
	private ItemBrandExportService itemBrandService;

	@Test
	public void testGetSmsConfig() {
		ExecuteResult<DataGrid<ItemBrandDTO>> data = itemBrandService.queryItemBrandAllList(null);
		System.out.println(data.getResult().getRows());
	}

	@Test
	public void testAddItemBrand() {
		ExecuteResult<ItemBrandDTO> result = new ExecuteResult<ItemBrandDTO>();
		ItemBrandDTO brand = new ItemBrandDTO();
		brand.setBrandName("  测试下行8 ");
		brand.setBrandLogoUrl("/d/d");
		brand.setThirdLevCid(11L);
		brand.setCreateId(100000L);
		brand.setCreateName("CK");
		result = itemBrandService.addItemBrand(brand);
		System.out.println(result);
	}

	@Test
	public void testAddCategoryBrandBatch() {
		ExecuteResult<ItemBrandDTO> result = new ExecuteResult<ItemBrandDTO>();
		ItemBrandDTO brand = new ItemBrandDTO();
//		brand.setSecondLevCid(3L);
		brand.setThirdLevCid(7L);
		brand.setCreateId(10000L);
		brand.setCreateName("ck");
		Long[] ids = new Long[] { 1L};
		brand.setBrandIds(ids);
		result = itemBrandService.addCategoryBrandBatch(brand);
		System.out.println(result);
	}

	@Test
	public void queryBrandListTest() {
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
//		itemBrandDTO.setBrandKey("d");
		itemBrandDTO.setBrandName("nike");
		Pager page = new Pager();
		ExecuteResult<DataGrid<ItemBrandDTO>> result = itemBrandService.queryBrandList(itemBrandDTO, page);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void queryBrandListByCategoryIdTest() {
		BrandOfShopDTO brandOfShopDTO = new BrandOfShopDTO();
		brandOfShopDTO.setThirdCid(77771L);
		brandOfShopDTO.setIsIgnoreErpStatus(true);
		ExecuteResult<DataGrid<ItemBrandDTO>> dtoDataGrid = itemBrandService.queryBrandListByCategoryId(brandOfShopDTO);
		System.out.println("====================结果：" + dtoDataGrid.getResult().getRows());
	}

	@Test
	public void modifyItemBrandTest() {
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		itemBrandDTO.setBrandId(488L);
		itemBrandDTO.setBrandName("测试下行81");
		itemBrandDTO.setModifyId(10000L);
		itemBrandDTO.setModifyName("ck");
		itemBrandDTO.setBrandLogoUrl("ccc");
		ExecuteResult executeResult = itemBrandService.modifyItemBrand(itemBrandDTO);
		Assert.assertTrue(executeResult.isSuccess());
	}

	@Test
	public void deleteBrandByIdTest() {
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		itemBrandDTO.setBrandId(488L);
		itemBrandDTO.setModifyId(10000L);
		itemBrandDTO.setModifyName("ck");
		ExecuteResult<String> result = itemBrandService.deleteBrand(itemBrandDTO);
		System.out.println(JSON.toJSONString(result));
		System.out.println(result.getResultMessage());
		Assert.assertTrue(result.isSuccess());
	}
//
//	@Test
//	public void deleteBrandTest() {
//
//		ExecuteResult<String> result = itemBrandService.deleteBrand(7l);
//		System.out.println(JSON.toJSONString(result));
//		Assert.assertEquals(true, result.isSuccess());
//	}

	@Test
	public void deleteCategoryBrandTest() {
		ExecuteResult<String> result = itemBrandService.deleteCategoryBrand(12L, 4L);
		System.out.println(JSON.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryItemBrandById() {
		ExecuteResult<ItemBrand> executeResult = itemBrandService.queryItemBrandById(4l);
		System.out.println(executeResult.getResult());
	}

	@Test
	public void testQueryItemBrandByIds() {
		Long[] l = new Long[]{4l};
		ExecuteResult<List<ItemBrandDTO>>  executeResult = itemBrandService.queryItemBrandByIds(l);
		System.out.println(executeResult.getResult());
	}
	@Test
	public void testQueryItemBrandByName() {
		ExecuteResult<ItemBrand> executeResult = itemBrandService.queryItemBrandByName("美的");
		System.out.println(executeResult.getResult());
	}

	@Test
	public void testqueryErpExceptionBrandList() {
		QueryBrandErpExceptionInDto queryBrandErpExceptionInDto = new QueryBrandErpExceptionInDto();
		queryBrandErpExceptionInDto.setPeriod(15);
		itemBrandService.queryErpExceptionBrandList(null, queryBrandErpExceptionInDto);
	}

	@Test
	public void testqueryBrandList4SuperBoss() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -60);
		QueryItemBrandInDTO itemBrandDTO = new QueryItemBrandInDTO();
		itemBrandDTO.setStartTime(calendar.getTime());
		itemBrandDTO.setEndTime(new Date());
		Pager page = new Pager<>();
		itemBrandService.queryBrandList4SuperBoss(itemBrandDTO, page);
	}
}

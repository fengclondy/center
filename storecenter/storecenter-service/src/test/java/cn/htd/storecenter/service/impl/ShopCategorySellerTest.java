package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopCategorySellerDTO;
import cn.htd.storecenter.service.ShopCategorySellerExportService;

import java.util.Date;

public class ShopCategorySellerTest {
	private ShopCategorySellerExportService shopCategorySellerExportService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopCategorySellerExportService = (ShopCategorySellerExportService) ctx
				.getBean("shopCategorySellerExportService");
	}

	@Test
	public void queryShoCategoryTest() {
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		Pager pager = new Pager();
		dto.setSellerId(123);
		dto.setCname("数码");
		ExecuteResult<DataGrid<ShopCategorySellerDTO>> result = shopCategorySellerExportService
				.queryShopCategoryList(dto, pager);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void deleteTest() {
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		dto.setCid(1);
		dto.setModifyName("刘德华");
		dto.setModifyId(999l);
		ExecuteResult<String> result = shopCategorySellerExportService.deleteById(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void addTest() {
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		dto.setParentCid(0l);
		dto.setShopId(2000000354);
		dto.setCname("数码");
		dto.setLev(1);
		dto.setHasLeaf(0);
		dto.setSortNumber(1);
		dto.setHomeShow(1);
		dto.setExpand(1);
		dto.setDeleted(0);
		dto.setCreated(new Date());
		dto.setCreateId(123l);
		dto.setCreateName("周星驰");
		dto.setModified(new Date());
		dto.setModifyId(999l);
		dto.setModifyName("刘德华");
		ExecuteResult<Long> result = shopCategorySellerExportService.addShopCategory(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void deletes() {
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		long[] lg = { 1L, 2L };
		dto.setCids(lg);
		ExecuteResult<String> result = shopCategorySellerExportService.deletes(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyTest() {
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		dto.setCid(1);
		dto.setCname("食品");
		ExecuteResult<String> result = shopCategorySellerExportService.update(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryChildCategoryByShopId(){
		ShopCategorySellerDTO dto = new ShopCategorySellerDTO();
		dto.setShopId(2000000354l);
		dto.setSellerId(123l);
		DataGrid<ShopCategorySellerDTO> dataGrid = shopCategorySellerExportService.queryChildCategoryByShopId(dto,2);
		System.out.println("heh");
	}

	@Test
	public void testQueryParentCname(){

		ExecuteResult<ShopCategorySellerDTO> result = shopCategorySellerExportService.queryParentCname(12l);
		System.out.println("heh");
	}

}

package cn.htd.storecenter.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopCategoryDTO;
import cn.htd.storecenter.service.ShopCategoryExportService;

public class ShopCategoryExportServiceTest {

	ApplicationContext ctx = null;
	ShopCategoryExportService shopCategoryExportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopCategoryExportService = (ShopCategoryExportService) ctx.getBean("shopCategoryExportService");
	}

	@Test
	public void queryShopCategoryListTest() {
		ExecuteResult<List<ShopCategoryDTO>> result = shopCategoryExportService.queryShopCategoryList(2000000354l, 1);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void addShopCategoryTest() {
		ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
		shopCategoryDTO.setCid(111l);
		shopCategoryDTO.setShopId(2000000354l);
		shopCategoryDTO.setSellerId(123l);
		shopCategoryDTO.setStatus("2");
		shopCategoryDTO.setComment("测试");
		shopCategoryDTO.setPassTime(new Date());
		shopCategoryDTO.setCreated(new Date());
		shopCategoryDTO.setCreateId(123l);
		shopCategoryDTO.setCreateName("周星驰");
		shopCategoryDTO.setModifyName("刘德华");
		shopCategoryDTO.setModifyId(999l);
		shopCategoryDTO.setModified(new Date());
		//ExecuteResult<String> result = shopCategoryExportService.addShopCategory(shopCategoryDTO);
		//Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void queryShopCategoryTest() {
		ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
		shopCategoryDTO.setStatus("1");
		shopCategoryDTO.setIsGroupBy(1l);
		shopCategoryDTO.setSellerId(123l);
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopCategoryDTO>> result = shopCategoryExportService.queryShopCategory(shopCategoryDTO,
				page);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void queryShopCategoryAllTest() {
		ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
		shopCategoryDTO.setStatus("1");
		shopCategoryDTO.setIsGroupBy(1l);
		shopCategoryDTO.setSellerId(123l);
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopCategoryDTO>> result = shopCategoryExportService
				.queryShopCategoryAll(shopCategoryDTO, page);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void modifyShopCategoryStatusTest() {
		ShopCategoryDTO shopCategoryDTO = new ShopCategoryDTO();
		shopCategoryDTO.setStatus("2");
		shopCategoryDTO.setId(1l);
		shopCategoryDTO.setModifyName("贾超飞");
		shopCategoryDTO.setModifyId(123l);
		shopCategoryDTO.setComment("haha");
		ExecuteResult<String> result = shopCategoryExportService.modifyShopCategoryStatus(shopCategoryDTO);
		Assert.assertEquals(true, result.isSuccess());

	}

	/**
	 * 默认是开发环境
	 */
	@Test
	public void getShopCategoryBysellerId() {
		List<ShopCategoryDTO> result = shopCategoryExportService.getShopCategoryBysellerId(1000000931L);
		System.out.println(result);
	}

	@Test
	public void testDeleteByShopId() {
		ShopCategoryDTO dto = new ShopCategoryDTO();
		dto.setShopId(2000000355l);
		dto.setModifyName("hah");
		dto.setModifyId(999l);
		shopCategoryExportService.deleteByShopId(dto);

	}


}

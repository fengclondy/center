package cn.htd.storecenter.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopBrandCategoryDTO;
import cn.htd.storecenter.service.ShopBrandCategoryService;

public class ShopBrandCategoryTest {
	private Logger LOG = LoggerFactory.getLogger(ShopBrandCategoryTest.class);
	private ShopBrandCategoryService shopBrandCategoryService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopBrandCategoryService = (ShopBrandCategoryService) ctx.getBean("shopBrandCategoryService");
	}

	@Test
	public void queryShopBrandListTest() {
		ShopBrandCategoryDTO shopBrandCategoryDTO = new ShopBrandCategoryDTO();

		ExecuteResult<DataGrid<Long>> result = shopBrandCategoryService.queryShopIdList(shopBrandCategoryDTO);

	}

}

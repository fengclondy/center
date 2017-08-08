package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopSearchExportService;

public class ShopSearchExportServiceTest {

	ApplicationContext ctx = null;
	ShopSearchExportService shopSearchExportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopSearchExportService = (ShopSearchExportService) ctx.getBean("shopSearchExportService");
	}

	@Test
	public void testSearchShop() {
		ShopDTO inDTO = new ShopDTO();
		inDTO.setKeyword("s");
		ExecuteResult<DataGrid<ShopDTO>> result = shopSearchExportService.searchShop(inDTO, null);
		System.out.println(result.getResult().getRows());
		Assert.assertEquals(true, result.isSuccess());
	}
}

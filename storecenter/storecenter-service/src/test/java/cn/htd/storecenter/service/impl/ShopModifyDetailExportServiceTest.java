package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;
import cn.htd.storecenter.service.ShopModifyDetailExportService;

public class ShopModifyDetailExportServiceTest {

	private ShopModifyDetailExportService shopModifyDetailExportService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopModifyDetailExportService = (ShopModifyDetailExportService) ctx.getBean("shopModifyDetailExportService");
	}

	@Test
	public void queryShopModifyDetailTest() {
		ShopModifyDetailDTO shopModifyDetailDTO = new ShopModifyDetailDTO();
		shopModifyDetailDTO.setShopId(47l);
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopModifyDetailDTO>> result = shopModifyDetailExportService
				.queryShopModifyDetail(shopModifyDetailDTO, page);
		Assert.assertEquals(true, result.isSuccess());
	}



}

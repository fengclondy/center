package cn.htd.storecenter.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopTemplatesDTO;
import cn.htd.storecenter.service.ShopTemplatesService;

public class ShopTemplatesServiceTest {

	ApplicationContext ctx = null;
	ShopTemplatesService shopTemplatesExportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopTemplatesExportService = (ShopTemplatesService) ctx.getBean("shopTemplatesExportService");
	}

	@Test
	public void queryShopTemplateListTest() {
		ShopTemplatesDTO shopTemplatesDTO = new ShopTemplatesDTO();
		shopTemplatesDTO.setShopId(1l);
		shopTemplatesDTO.setCreateId(213l);
		shopTemplatesDTO.setCreateName("周星驰");
		shopTemplatesDTO.setModifyName("刘德华");
		shopTemplatesDTO.setModifyId(999l);
		ExecuteResult<List<ShopTemplatesDTO>> result = shopTemplatesExportService
				.createShopTemplatesList(shopTemplatesDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopTemplatesStatusTest() {
		ShopTemplatesDTO dto = new ShopTemplatesDTO();
		dto.setId(2l);
		dto.setShopId(Long.valueOf(2000000354));
		dto.setModifyId(999l);
		dto.setModifyName("刘德华");
		ExecuteResult<String> result = shopTemplatesExportService.modifyShopTemplatesStatus(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopTemplatesColorTest() {
		ShopTemplatesDTO dto = new ShopTemplatesDTO();
		dto.setId(2l);
		dto.setColor("#111222");
		dto.setModifyId(999l);
		dto.setModifyName("周星驰");
		ExecuteResult<String> result = shopTemplatesExportService.modifyShopTemplatesColor(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

}

package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopAuditDTO;
import cn.htd.storecenter.service.ShopAuditService;

public class ShopAuditServiceTest {

	ApplicationContext ctx = null;
	ShopAuditService shopAudiExportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopAudiExportService = (ShopAuditService) ctx.getBean("shopAudiExportService");
	}

	@Test
	public void queryShopAuditInfoTest() {
		ExecuteResult<ShopAuditDTO> result = shopAudiExportService.queryShopAuditInfo(1l);
		Assert.assertEquals(true, result.isSuccess());
	}

}

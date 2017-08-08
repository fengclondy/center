package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.service.ShopDomainService;

public class ShopDomainServiceTest {

	private ShopDomainService shopDomainExportService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopDomainExportService = (ShopDomainService) ctx.getBean("shopDomainExportService");
	}

	@Test
	public void existShopUrlTest() {
		ExecuteResult<Boolean> result = shopDomainExportService.existShopUrl("abc");
		Assert.assertEquals(true, result.isSuccess());
	}

}

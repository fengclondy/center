package cn.htd.storecenter.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopWidgetDTO;
import cn.htd.storecenter.service.ShopWidgetService;

public class ShopWidgetServiceTest {

	private ShopWidgetService shopWidgetService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopWidgetService = (ShopWidgetService) ctx.getBean("shopWidgetService");
	}

	@Test
	public void find() {
		ShopWidgetDTO widgetDTO = new ShopWidgetDTO();
		ExecuteResult<List<ShopWidgetDTO>> er = shopWidgetService.findWidget(widgetDTO);
		Assert.assertNotNull(er.getResult());
	}
}

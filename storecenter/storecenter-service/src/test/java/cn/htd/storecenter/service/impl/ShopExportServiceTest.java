package cn.htd.storecenter.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopExportService;

public class ShopExportServiceTest {

	private ShopExportService shopExportService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopExportService = (ShopExportService) ctx.getBean("shopExportService");
	}

	@Test
	public void queryShopInfoByidsTest() {
		ShopAuditInDTO shopAudiinDTO = new ShopAuditInDTO();
		Long[] shopIds = new Long[2];
		shopIds[0] = 10L;
		shopIds[1] = 11L;
		shopAudiinDTO.setShopIds(shopIds);
		ExecuteResult<List<ShopDTO>> result = shopExportService.queryShopInfoByids(shopAudiinDTO);
		System.out.println(JSON.toJSONString(result));
	}

}

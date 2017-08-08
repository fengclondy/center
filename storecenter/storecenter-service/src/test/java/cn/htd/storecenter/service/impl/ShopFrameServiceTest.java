package cn.htd.storecenter.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopFrameDTO;
import cn.htd.storecenter.service.ShopFrameService;

public class ShopFrameServiceTest {

	private ShopFrameService shopFrameService = null;
	ApplicationContext ctx = null;

	private static Long frameId;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopFrameService = (ShopFrameService) ctx.getBean("shopFrameService");
	}

	@Test
	public void testAddFrame() {
		ShopFrameDTO frameDTO = new ShopFrameDTO();
		frameDTO.setShopId(10000l);
		frameDTO.setVersionType(2);
		ExecuteResult<ShopFrameDTO> er = shopFrameService.addFrame(frameDTO);
		Assert.assertNotNull(er.getResult());
		frameId = er.getResult().getId();
	}

	@Test
	public void testUpdateFrame() {
		if (frameId == null) {
			return;
		}
		ShopFrameDTO frameDTO = new ShopFrameDTO();
		frameDTO.setBgColor("yellow");
		frameDTO.setId(frameId);
		frameDTO.setShopId(999l);
		ExecuteResult<Boolean> er = shopFrameService.updateFrame(frameDTO);
		Assert.assertTrue(er.getResult());
	}

	@Test
	public void testDelFrameById() {
		if (frameId == null) {
			return;
		}
		ExecuteResult<Boolean> er = shopFrameService.delFrameById(frameId);
		Assert.assertTrue(er.getResult());
	}

	// @Test
	// public void testFindFrameById(){
	// ExecuteResult<ShopFrameDTO> er = shopFrameService.findFrameById(frameId);
	// Assert.assertNotNull(er.getResult());
	// System.out.println(er.getResult());
	// }
}

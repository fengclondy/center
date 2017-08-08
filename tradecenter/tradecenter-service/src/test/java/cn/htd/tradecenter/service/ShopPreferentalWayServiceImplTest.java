package cn.htd.tradecenter.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.ShopPreferentialWayDTO;
import junit.framework.TestCase;

public class ShopPreferentalWayServiceImplTest extends TestCase {
	ApplicationContext ctx = null;
	ShopPreferentialWayExportService shopPreferentialWayService = null;

	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopPreferentialWayService = (ShopPreferentialWayExportService) ctx.getBean("shopPreferentialWayService");
	}

	@Test
	public void testAddShopPreferentialWayDTO() throws Exception {
		ShopPreferentialWayDTO test = new ShopPreferentialWayDTO();
		test.setShopId(3L);
		test.setSellerId(4L);
		test.setTemplateId(5L);
		test.setPreferentialWay("1");
		test.setFull(new BigDecimal(10L));
		test.setReduce(new BigDecimal(11L));
		test.setStrategy(12);
		test.setCreateTime(new Date());
		test.setModifyTime(new Date());
		Assert.assertNotNull(shopPreferentialWayService.addShopPreferentialWay(test));
	}

	@Test
	public void testDeleteShopPreferentialWayDTO() throws Exception {
		ShopPreferentialWayDTO test = new ShopPreferentialWayDTO();
		test.setId(10L);
		Assert.assertNotNull(shopPreferentialWayService.deleteShopPreferentialWay(test));
	}

	@Test
	public void testUpdateShopPreferentialWayDTO() throws Exception {
		ShopPreferentialWayDTO test = new ShopPreferentialWayDTO();
		test.setId(2L);
		test.setShopId(31L);
		test.setSellerId(41L);
		test.setTemplateId(51L);
		test.setPreferentialWay("2");
		test.setFull(new BigDecimal(101L));
		test.setReduce(new BigDecimal(111L));
		test.setStrategy(121);
		test.setCreateTime(new Date());
		test.setModifyTime(new Date());
		test.setDeleteFlag(Byte.valueOf("1"));
		Assert.assertNotNull(shopPreferentialWayService.updateShopPreferentialWay(test));
	}

	@Test
	public void testGetShopPreferentialWayDTO() throws Exception {
		ShopPreferentialWayDTO test = new ShopPreferentialWayDTO();
		test.setId(721L);
		test.setDeleteFlag(Byte.valueOf("0"));
		ExecuteResult<List<ShopPreferentialWayDTO>> ShopPreferentialWayDTO = shopPreferentialWayService
				.queryShopPreferentialWay(test);
		Assert.assertNotNull(ShopPreferentialWayDTO);
	}

}
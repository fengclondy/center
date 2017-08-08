package cn.htd.tradecenter.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.ShopDeliveryTypeDTO;
import junit.framework.TestCase;

public class ShopDeliveryTypeServiceImplTest extends TestCase {
	ApplicationContext ctx = null;
	ShopDeliveryTypeExportService shopDeliveryTypeService = null;

	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopDeliveryTypeService = (ShopDeliveryTypeExportService) ctx.getBean("shopDeliveryTypeService");
	}

	@Test
	public void testAddShopDeliveryTypeDTO() throws Exception {
		ShopDeliveryTypeDTO test = new ShopDeliveryTypeDTO();
		test.setShopId(3L);
		test.setSellerId(4L);
		test.setTemplateId(5L);
		test.setDeliveryType(6);
		test.setContinuePart(new BigDecimal(10L));
		test.setDeliveryTo("运送至");
		test.setFirstPart(new BigDecimal(11L));
		test.setCreateTime(new Date());
		test.setUpdateTime(new Date());
		test.setDeleteFlag(Byte.valueOf("1"));
		test.setDeliveryAddress("运送至");
		Assert.assertNotNull(shopDeliveryTypeService.addShopDeliveryType(test));
	}

	@Test
	public void testDeleteShopDeliveryTypeDTO() throws Exception {
		ShopDeliveryTypeDTO test = new ShopDeliveryTypeDTO();
		test.setId(19L);
		Assert.assertNotNull(shopDeliveryTypeService.deleteShopDeliveryType(test));
	}

	@Test
	public void testUpdateShopDeliveryTypeDTO() throws Exception {
		ShopDeliveryTypeDTO test = new ShopDeliveryTypeDTO();
		test.setId(95L);
		test.setDeliveryType(1);
		test.setContinuePart(new BigDecimal(10.000));
		test.setContinuePrice(new BigDecimal(10.000));
		test.setFirstPart(new BigDecimal(10.000));
		test.setFirstPrice(new BigDecimal(10.000));
		test.setDeliveryTo("运送至111111");
		test.setUpdateTime(new Date());
		test.setDeliveryAddress("dfdfd");
		Assert.assertNotNull(shopDeliveryTypeService.updateShopDeliveryType(test));
	}

	@Test
	public void testGetShopDeliveryTypeDTO() throws Exception {
		ShopDeliveryTypeDTO test = new ShopDeliveryTypeDTO();
		test.setId(1L);
		test.setDeleteFlag(Byte.valueOf("0"));
		ExecuteResult<List<ShopDeliveryTypeDTO>> ShopDeliveryTypeDTO = shopDeliveryTypeService.queryShopDeliveryType(test);
		Assert.assertNotNull(ShopDeliveryTypeDTO);
	}

}
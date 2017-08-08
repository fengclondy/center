package cn.htd.tradecenter.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.ShopFreightTemplateDTO;
import junit.framework.TestCase;

public class ShopFreightTemplateServiceImplTest extends TestCase {

	private final static Logger logger = LoggerFactory.getLogger(ShopFreightTemplateServiceImplTest.class);
	ApplicationContext ctx = null;
	private ShopFreightTemplateExportService shopFreightTemplateService;

	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopFreightTemplateService = (ShopFreightTemplateExportService) ctx.getBean("shopFreightTemplateService");
	}

	@Test
	public void testAddShopFreightTemplateDTO() throws Exception {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		ShopFreightTemplateDTO test = new ShopFreightTemplateDTO();
		// test.setTemplateName("辅导辅导方法");
		// test.setValuationWay(2);
		// test.setShopId(3L);
		// test.setSellerId(4L);
		// test.setAddressDetails("详细地址");
		// test.setCityId(5L);
		// test.setCountyId(6L);
		// test.setPostageFree(7);
		// test.setProvinceId(8L);
		// test.setDeliveryTime("4小时");
		// test.setDeliveryType("1,2,3");
		// test.setDelState("1");
		test.setStartTime(formatter.parse("2015-12-1"));
		test.setEndTime(formatter.parse("2015-12-4"));
		ExecuteResult<ShopFreightTemplateDTO> result = shopFreightTemplateService.addShopFreightTemplate(test);
		Assert.assertNotNull(result.getResult());
	}

	@Test
	public void testDeleteShopFreightTemplateDTO() throws Exception {
		Assert.assertNotNull(shopFreightTemplateService.deleteShopFreightTemplateById(40L));
	}

	@Test
	public void testUpdateShopFreightTemplateDTO() throws Exception {
		ShopFreightTemplateDTO test = new ShopFreightTemplateDTO();
		test.setTemplateName("辅导辅导方法11111111111");
		test.setId(37L);
		Assert.assertNotNull(shopFreightTemplateService.update(test));
	}

	@Test
	public void testGetShopFreightTemplateDTO() throws Exception {
		ShopFreightTemplateDTO shopFreightTemplateDTO = shopFreightTemplateService.queryShopFreightTemplateById(720L);
		Assert.assertNotNull(shopFreightTemplateDTO);
	}

	@Test
	public void testQueryShopFreightTemplate() throws Exception {
		ShopFreightTemplateDTO test = new ShopFreightTemplateDTO();
		test.setShopId(8L);
		test.setDeleteFlag((byte) 0);
		Pager pager = new Pager();
		ExecuteResult<DataGrid<ShopFreightTemplateDTO>> shopFreightTemplateById = shopFreightTemplateService
				.queryShopFreightTemplateList(test, pager);
		Assert.assertNotNull(shopFreightTemplateById);
	}

	@Test
	public void testqueryById() {
		ExecuteResult<ShopFreightTemplateDTO> ddd = shopFreightTemplateService.queryById(721L);
		Assert.assertNotNull(ddd);
	}

	@Test
	public void testCopy() throws Exception {
		ExecuteResult<ShopFreightTemplateDTO> result = shopFreightTemplateService.copy(720L);
		logger.info(JSONObject.toJSONString(result));
	}

}
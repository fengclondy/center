package cn.htd.storecenter.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.service.ShopBrandExportService;

public class ShopBrandTest {
	private Logger LOG = LoggerFactory.getLogger(ShopBrandTest.class);
	private ShopBrandExportService shopBrandExportService = null;
	ApplicationContext ctx = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		shopBrandExportService = (ShopBrandExportService) ctx.getBean("shopBrandExportService");
	}

	@Test
	public void queryShopBrandListTest() {
		ExecuteResult<List<ShopBrandDTO>> result = shopBrandExportService.queryShopBrandList(2000000354l, 2);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void addShopCategoryTest() {
		ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
		shopBrandDTO.setCategoryId(971L);
		shopBrandDTO.setShopId(2000000354l);
		shopBrandDTO.setSellerId(1099656l);
		shopBrandDTO.setBrandId(77790l);
		shopBrandDTO.setStatus("2");
		shopBrandDTO.setPassTime(new Date());
		shopBrandDTO.setCreateId(123l);
		shopBrandDTO.setCreateName("周星驰");
		shopBrandDTO.setCreateTime(new Date());
		shopBrandDTO.setModifyId(999l);
		shopBrandDTO.setModifyName("刘德华");
		shopBrandDTO.setModifyTime(new Date());
		ExecuteResult<String> result = shopBrandExportService.addShopCategoryAndBrand(shopBrandDTO);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void queryShopCategoryTest() {
		ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
		shopBrandDTO.setStatus("2");
		shopBrandDTO.setBrandId(3l);
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopBrandDTO>> result = shopBrandExportService.queryShopBrand(shopBrandDTO, page);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void modifyShopCategoryStatusTest() {
		ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
		shopBrandDTO.setStatus("1");
		shopBrandDTO.setId(1l);
		shopBrandDTO.setModifyId(222l);
		shopBrandDTO.setModifyName("隔壁王大爷");
		ExecuteResult<String> result = shopBrandExportService.modifyShopBrandStatus(shopBrandDTO);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void queryShopCategoryAllTest() {
		ShopBrandDTO shopBrandDTO = new ShopBrandDTO();
		shopBrandDTO.setSellerId(123l);
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopBrandDTO>> result = shopBrandExportService.queryShopBrandAll(shopBrandDTO, page);
		Assert.assertEquals(true, result.isSuccess());

	}

	@Test
	public void selectBrandidByIdTest() {
		List<ShopBrandDTO> shopBrandDTO = shopBrandExportService.selectBrandIdById(new Long(168));
		LOG.info(shopBrandDTO.get(0).toString());
	}

	@Test
	public void updatebrandtest() {
		ShopBrandDTO dto = new ShopBrandDTO();
		dto.setId(Long.valueOf(47));
		shopBrandExportService.updateStatusByIdAndBrandId(dto);
	}

}

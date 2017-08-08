package cn.htd.storecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.storecenter.dto.ShopAuditInDTO;
import cn.htd.storecenter.dto.ShopBrandDTO;
import cn.htd.storecenter.dto.ShopCategoryDTO;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.dto.ShopInfoModifyInDTO;
import cn.htd.storecenter.service.ShopExportService;

public class ShopServiceTestHT {
	Logger LOG = LoggerFactory.getLogger(ShopServiceTestHT.class);
	ApplicationContext ctx = null;
	ShopExportService shopExportService = null;
	RedisDB redisDB = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		shopExportService = (ShopExportService) ctx.getBean("shopExportService");
		redisDB = (RedisDB) ctx.getBean("redisDB");
	}

	@Test
	public void testSaveShopInfo() throws Exception {
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setShopName("炸弹娃娃专卖店");
		shopDTO.setSellerId(Long.valueOf(999l));
		shopDTO.setKeyword("nihao");
		shopDTO.setIntroduce("这只是第二次测试");
		shopDTO.setShopType("1");
		shopDTO.setBusinessType("2");
		shopDTO.setDisclaimer("我们还是不道歉");
		shopDTO.setStatus("5");
		shopDTO.setVerifyId(Long.valueOf(111));
		shopDTO.setCreateId(Long.valueOf(123));
		shopDTO.setCreateName("刘德华");
		shopDTO.setCreateTime(new Date());
		shopDTO.setModifyId(Long.valueOf(456));
		shopDTO.setModifyName("niaho");
		shopDTO.setEndTime(new Date());
		shopDTO.setPassTime(new Date());
		shopDTO.setModifyTime(new Date());
		ExecuteResult<String> result = shopExportService.saveShopInfo(shopDTO);

	}

	@Test
	public void testFindShopInfoById() throws Exception {
		ExecuteResult<ShopDTO> result = shopExportService.findShopInfoById(2000000354);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testFindShopInfoByCondition() throws Exception {
		Pager pager = new Pager();
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setShopType("1");
		shopDTO.setCollation(2);
		ExecuteResult<DataGrid<ShopDTO>> result = shopExportService.findShopInfoByCondition(shopDTO, pager);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testmodifyShopInfostatus() {
		Long shopId = 2000000354l;
		ShopDTO dto = new ShopDTO();
		dto.setShopId(shopId);
		dto.setStatus("3");
		dto.setModifyName("haha");
		dto.setModifyId(Long.valueOf(100));
		ExecuteResult<String> result = shopExportService.modifyShopInfostatus(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void queryByIdsTest() {
		ShopAuditInDTO ss = new ShopAuditInDTO();
		Long[] shopIds = new Long[2];
		shopIds[0] = 2000000354l;
		shopIds[1] = 2000000355l;
		ss.setShopIds(shopIds);
		ExecuteResult<List<ShopDTO>> result = shopExportService.queryShopByids(ss);
		LOG.info("操作方法{}，结果信息{}", "queryBYIdsTest", result.getResult().size());
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopInfoUpdateTest() {
		ShopInfoModifyInDTO shopInfoModifyInDTO = new ShopInfoModifyInDTO();
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setShopId(2000000204l);
		shopDTO.setSellerId(674l);
		shopDTO.setShopName("11111");
		shopDTO.setLogoUrl("logo/url");
		shopDTO.setKeyword("铁锅，铁燥改改改");
		shopDTO.setIntroduce("我们家的商品质量很不错改改改");

		shopInfoModifyInDTO.setShopDTO(shopDTO);

		List<ShopBrandDTO> shopBrandList = new ArrayList<ShopBrandDTO>();
		ShopBrandDTO sb = new ShopBrandDTO();
		sb.setBrandId(4l);
		sb.setStatus("1");
		shopBrandList.add(sb);
		shopInfoModifyInDTO.setShopBrandList(shopBrandList);

		List<ShopCategoryDTO> shopCategoryList = new ArrayList<ShopCategoryDTO>();
		ShopCategoryDTO sc = new ShopCategoryDTO();
		sc.setStatus("1");
		sc.setCid(3l);
		shopCategoryList.add(sc);
		shopInfoModifyInDTO.setShopCategoryList(shopCategoryList);
		ExecuteResult<String> result = shopExportService.modifyShopInfoUpdate(shopInfoModifyInDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopInfoTest() {
		ShopInfoModifyInDTO shopInfoModifyInDTO = new ShopInfoModifyInDTO();
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setShopId(2000000354l);
		shopDTO.setShopName("优衣库");
		shopDTO.setSellerId(123l);
		shopDTO.setSellerName("周杰伦");
		shopDTO.setCreateId(123l);
		shopDTO.setCreateName("刘德华");
		shopInfoModifyInDTO.setShopDTO(shopDTO);
		ExecuteResult<String> result = shopExportService.modifyShopInfo(shopDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopInfoAndcbstatusTest() {
		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setShopId(2000000130l);
		shopDTO.setStatus("2");
		shopDTO.setModifyId(789l);
		shopDTO.setModifyName("周星驰");
		ExecuteResult<String> result = shopExportService.modifyShopInfoAndcbstatus(shopDTO);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopstatusAndRunstatusTest() {
		ShopDTO dto = new ShopDTO();
		dto.setShopId(2000000354l);
		dto.setModifyName("周星驰");
		dto.setModifyId(999l);
		ExecuteResult<String> result = shopExportService.modifyShopStatus(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void modifyShopstatusAndRunstatusBackTest() {
		ShopDTO dto = new ShopDTO();
		dto.setShopId(2000000088l);
		ExecuteResult<String> result = shopExportService.modifyShopStatusBack(dto);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void queryShopInfoByBrandIdTest() {
		Pager page = new Pager();
		ExecuteResult<DataGrid<ShopDTO>> result = shopExportService.queryShopInfoByBrandId(272l, page);
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void addSecondDomainToRedisTest() {
		String obj = redisDB.get("second_domain_key");
		if (!StringUtils.isEmpty(obj)) {
			Map<String, String> secondDomainMap = JSON.parseObject(obj, new TypeReference<Map<String, String>>() {
			});
			System.out.println(secondDomainMap);
		}
		System.out.println(obj);
	}

	@Test
	public void testQueryShopInfoByids() {
		ShopAuditInDTO dto = new ShopAuditInDTO();
		Long[] aa = { 10000237l, 10000238l, 10000279l };
		dto.setSellerIds(aa);
		ExecuteResult<List<ShopDTO>> result = shopExportService.queryShopByids(dto);
		System.out.println("dsfsa");
	}
}

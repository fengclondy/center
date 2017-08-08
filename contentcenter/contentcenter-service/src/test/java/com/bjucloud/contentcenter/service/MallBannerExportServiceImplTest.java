package com.bjucloud.contentcenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallBannerDTO;
import com.bjucloud.contentcenter.dto.MallBannerInDTO;
import com.bjucloud.contentcenter.service.MallBannerExportService;

public class MallBannerExportServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallBannerExportServiceImplTest.class);
	ApplicationContext ctx = null;
	MallBannerExportService mallBannerExportService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallBannerExportService = (MallBannerExportService) ctx.getBean("mallBannerExportService");
	}

	@Test
	public void queryMallBannerListTest() {
		Pager page = new Pager();
		page.setPage(1);
		page.setRows(6);
		DataGrid<MallBannerDTO> banners = mallBannerExportService.queryMallBannerList("1", page);
		System.out.println(JSON.toJSONString(banners));
	}

	@Test
	public void testqueryReportList() {
		AdReportInDTO mallAdCountDTO = new AdReportInDTO();
		mallAdCountDTO.setClickDateBegin("2015-06-01");
		mallAdCountDTO.setClickDateEnd("2015-06-03");
		mallAdCountDTO.setDateFormat("yyyy-MM-dd");
		DataGrid<AdReportOutDTO> result = mallBannerExportService.queryReportList(mallAdCountDTO, null);
		LOGGER.info("\n 操作结果{},信息{}", result.getTotal() > 0, JSONObject.toJSONString(result));
	}

	@Test
	public void testQueryListDTOToAdmin() {
		Pager page = new Pager();
		page.setRows(4);
		page.setTotalPage(2);
		page.setTotalCount(10);
		MallBannerDTO mallBannerDTO = new MallBannerDTO();
		mallBannerDTO.setType("1");
		mallBannerDTO.setStatus("1");
		mallBannerDTO.setThemeId(10);
		DataGrid<MallBannerDTO> result = mallBannerExportService.queryMallBannerList(mallBannerDTO, null, page);
		System.out.println("-------------result.size:" + result.getRows().size());
		LOGGER.info("\n 操作结果{},信息{}", result.getTotal() > 0, JSONObject.toJSONString(result));
	}

	@Test
	public void testAddMallBanner() {
		ExecuteResult<String> re = new ExecuteResult<String>();
		MallBannerInDTO md = new MallBannerInDTO();
		md.setTitle("轮播图1");
		md.setType("212");
		md.setBannerLink("www.baidu.com");
		md.setBannerUrl("1234543");
		md.setSortNumber(222);
		md.setTimeFlag("213123");
		md.setThemeId(61);
		re = this.mallBannerExportService.addMallBanner(md);

	}

	@Test
	public void testGetMallBannerById() {
		MallBannerDTO mallBannerDTO = this.mallBannerExportService.getMallBannerById(77);
	}

	@Test
	public void testQueryTimeListDTO() {
		List<MallBannerDTO> mallList = this.mallBannerExportService.queryTimeListDTO();
		System.out.println("查询结果个数：" + mallList.size());
	}

	@Test
	public void testUpdate() {
		this.mallBannerExportService.motifyMallBannerStatus(Long.valueOf(141), "0");
	}
}

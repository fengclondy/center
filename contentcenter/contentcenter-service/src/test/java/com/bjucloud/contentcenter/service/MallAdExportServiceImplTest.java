package com.bjucloud.contentcenter.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallAdCountDTO;
import com.bjucloud.contentcenter.dto.MallAdDTO;
import com.bjucloud.contentcenter.dto.MallAdInDTO;
import com.bjucloud.contentcenter.dto.MallAdQueryDTO;
import com.bjucloud.contentcenter.service.MallAdExportService;

public class MallAdExportServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallAdExportServiceImplTest.class);
	ApplicationContext ctx = null;
	MallAdExportService mallAdvService = null;
	public static final String DATEFORMAT = "yyyy-MM-dd";

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallAdvService = (MallAdExportService) ctx.getBean("mallAdExportService");
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testQueryMallAdList() {
		Pager page = new Pager();
		MallAdQueryDTO mallAdQueryDTO = new MallAdQueryDTO();
		mallAdQueryDTO.setStatus(1);
		mallAdQueryDTO.setAdType(6);
		mallAdQueryDTO.setThemeId(11);
		DataGrid<MallAdDTO> res = mallAdvService.queryMallAdList(page, mallAdQueryDTO);
		LOGGER.info("操作方法{}，结果信息{}", "testQueryMallAdList", JSONObject.toJSONString(res));
	}

	@Test
	public void testGetMallAdById() {
		MallAdDTO res = mallAdvService.getMallAdById(88L);
		System.out.println("-------------res:" + res.getTitle());
		LOGGER.info("操作方法{}，结果信息{}", "testGetMallAdById", JSONObject.toJSONString(res));
	}

	@Test
	public void testAddMallAd() {

		MallAdInDTO mallAdInDTO = new MallAdInDTO();
		mallAdInDTO.setAdType(3);
		mallAdInDTO.setCid(2l);
		mallAdInDTO.setAdURL("ssds");
		mallAdInDTO.setClose(2);
		mallAdInDTO.setThemeId(2222);
		mallAdvService.addMallAd(mallAdInDTO);
	}

	@Test
	public void testDelById() throws Exception {
		mallAdvService.delById(new Long(186));
	}

	@Test
	public void testModifyMallBanner() {
		MallAdInDTO mallAdInDTO = new MallAdInDTO();
		mallAdInDTO.setId(94l);
		mallAdInDTO.setAdType(3);
		mallAdInDTO.setCid(2l);
		mallAdInDTO.setAdURL("ssdddd.ds");
		mallAdInDTO.setClose(1);
		mallAdInDTO.setThemeId(999);
		mallAdvService.modifyMallBanner(mallAdInDTO);
	}

	@Test
	public void testModifyMallAdStatus() {
	}

	@Test
	public void testsaveMallAdCount() {
		try {
			ExecuteResult<MallAdCountDTO> result = mallAdvService.saveMallAdCount(12l, 1l);
			LOGGER.info("\n 操作结果{},信息{}", result.isSuccess(), JSONObject.toJSONString(result));
		} catch (Exception e) {
			LOGGER.info("\n 操作异常,信息{}", e.getMessage());
		}
	}

	@Test
	public void testfindMallAdCountById() {
		MallAdCountDTO result = mallAdvService.findMallAdCountById(6l);
		LOGGER.info("\n 操作结果{},信息{}", result != null, JSONObject.toJSONString(result));
	}

	@Test
	public void testfindAdCountList() {
		MallAdCountDTO mallAdCountDTO = new MallAdCountDTO();
		mallAdCountDTO.setClickDateBegin("2015-05-11");
		mallAdCountDTO.setClickDateEnd("2015-05-11");
		mallAdCountDTO.setAdCountMin(2l);
		mallAdCountDTO.setAdCountMax(4l);
		DataGrid<MallAdCountDTO> result = mallAdvService.findAdCountList(mallAdCountDTO, null);
		LOGGER.info("\n 操作结果{},信息{}", result.getTotal() > 0, JSONObject.toJSONString(result));
	}

	public static Date strToDate(String dateStr, String formatType) {
		try {
			if (StringUtils.isBlank(dateStr))
				return null;
			if (StringUtils.isBlank(formatType))
				formatType = DATEFORMAT;
			DateFormat sdf = new SimpleDateFormat(formatType);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void testqueryReportList() {
		AdReportInDTO mallAdCountDTO = new AdReportInDTO();
		mallAdCountDTO.setClickDateBegin("2015-06-01");
		mallAdCountDTO.setClickDateEnd("2015-06-03");
		mallAdCountDTO.setDateFormat("yyyy-MM-dd");
		DataGrid<AdReportOutDTO> result = mallAdvService.queryReportList(mallAdCountDTO, null);
		LOGGER.info("\n 操作结果{},信息{}", result.getTotal() > 0, JSONObject.toJSONString(result));
	}
}

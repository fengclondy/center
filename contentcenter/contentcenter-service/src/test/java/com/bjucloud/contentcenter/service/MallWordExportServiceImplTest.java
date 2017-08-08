package com.bjucloud.contentcenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallWordDTO;
import com.bjucloud.contentcenter.service.MallWordExportService;

public class MallWordExportServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallWordExportServiceImplTest.class);
	ApplicationContext ctx = null;
	MallWordExportService mallWordExportService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallWordExportService = (MallWordExportService) ctx.getBean("mallWordExportService");
	}

	@Test
	public void testAdd() {
		MallWordDTO dto = new MallWordDTO();
		dto.setWord("测试word21111");
		dto.setThemeId(11);
		ExecuteResult<MallWordDTO> result = mallWordExportService.add(dto);

		LOGGER.info(JSONObject.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

	@Test
	public void testQueryList() {
		MallWordDTO dto = new MallWordDTO();
		dto.setWord("测试word");
		dto.setThemeId(11);
		Pager page = new Pager();
		page.setRows(5);
		page.setPage(1);
		DataGrid<MallWordDTO> dg = mallWordExportService.datagrid(dto, page);
		System.out.println("-------------dg.total:" + dg.getTotal());
	}

	@Test
	public void testDelete() {
		ExecuteResult<String> result = mallWordExportService.delete(67L);

		LOGGER.info(JSONObject.toJSONString(result));
		Assert.assertEquals(true, result.isSuccess());
	}

}

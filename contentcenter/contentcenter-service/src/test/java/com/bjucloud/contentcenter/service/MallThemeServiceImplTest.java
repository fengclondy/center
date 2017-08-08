package com.bjucloud.contentcenter.service;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallThemeDTO;
import com.bjucloud.contentcenter.service.MallThemeService;

public class MallThemeServiceImplTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MallThemeServiceImplTest.class);
	ApplicationContext ctx = null;
	MallThemeService mallThemeService = null;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallThemeService = (MallThemeService) ctx.getBean("mallThemeService");
	}

	@Test
	public void queryMallThemeListTest1() {
		MallThemeDTO dto = new MallThemeDTO();
		dto.setId(1);
		Pager p = new Pager();
		p.setRows(5);
		p.setPage(1);
		mallThemeService.queryMallThemeList(dto, "1", p);
		System.out.println("queryMallThemeList.......");
	}

	@Test
	public void queryMallThemeListTest() {
		Pager p = new Pager();
		p.setRows(5);
		p.setPage(1);
		MallThemeDTO dto = new MallThemeDTO();
		// dto.setAddressId(2L);
		// dto.setcId(3L);
		dto.setType(1);
		// dto.setClev(5);
		// dto.setColorb("#123211");
		// dto.setCreated(Date.valueOf("2015-10-30"));
		// dto.setUserId("1");
		DataGrid<MallThemeDTO> dg = mallThemeService.queryMallThemeList(dto, "1", p);

		System.out.println("queryMallThemeList.......get(0).getId():" + dg.getRows().size());
	}

	@Test
	public void getMallThemeByIdTest() {
		MallThemeDTO dto = mallThemeService.getMallThemeById(1);
		System.out.println("queryMallThemeList.......dto:" + dto.toString());
	}

	@Test
	public void updateStatusByIdTest() {
		MallThemeDTO dto = new MallThemeDTO();
		dto.setAddressId(2L);
		dto.setcId(5L);
		// MallTheme mt = new MallTheme();
		dto.setType(1);
		dto.setId(1);
		dto.setColorb("111212");
		dto.setThemeName("修改测试11-11");
		ExecuteResult<String> er = mallThemeService.motifyMallTheme(dto);
		System.out.println("queryMallThemeList.......dto:" + er.getResultMessage());
	}

	@Test
	public void addMallThemeTest() {
		MallThemeDTO dto = new MallThemeDTO();
		dto.setThemeName("测试主题1");
		dto.setAddressId(2L);
		dto.setcId(3L);
		dto.setClev(5);
		dto.setStatus(1);
		dto.setColor("#123211");
		dto.setColorb("22211");
		dto.setType(1);
		dto.setCreated(Date.valueOf("2015-10-30"));
		dto.setSortNum(1);
		mallThemeService.addMallTheme(dto);
		System.out.println("addMallTheme.......");
	}

	@Test
	public void testmotifyMallThemeStatus() {
		mallThemeService.motifyMallThemeStatus(2L, "0");
	}

}

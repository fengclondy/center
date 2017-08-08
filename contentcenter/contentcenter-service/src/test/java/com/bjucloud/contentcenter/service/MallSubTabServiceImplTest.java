package com.bjucloud.contentcenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjucloud.contentcenter.dto.MallSubTabDTO;
import com.bjucloud.contentcenter.dto.MallSubTabECMDTO;
import com.bjucloud.contentcenter.service.MallSubTabService;

public class MallSubTabServiceImplTest {

	public static final Logger logger = LoggerFactory.getLogger(MallSubTabServiceImplTest.class);
	ApplicationContext cxf = null;
	MallSubTabService mallSubTabService = null;

	@Before
	public void setUp() throws Exception {
		cxf = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallSubTabService = (MallSubTabService) cxf.getBean("mallSubTabService");
	}

	// @Test
	public void selectMallSubTab() {
		MallSubTabDTO mllSubTabDTO = new MallSubTabDTO();
		// mllSubTabDTO.setTitle("我");
		// mllSubTabDTO.setStartTime("2015-01-02");
		// mllSubTabDTO.setStartTime("2015-12-02");
		// mllSubTabDTO.setRedId("13");
		DataGrid<MallSubTabDTO> dagta = new DataGrid<MallSubTabDTO>();
		Pager page = new Pager();
		try {
			dagta = mallSubTabService.queryMallSubTabPage(mllSubTabDTO, page);
			List<MallSubTabDTO> list = dagta.getRows();
			for (MallSubTabDTO mallSubTabDTO : list) {
				System.out.println(mallSubTabDTO.getTemplateId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Assert.assertEquals(true, dagta.);
		// assertThat((List<Mallsub>)dagta.getRows()).isEmpty();
	}

	@Test
	public void add() {
		MallSubTabDTO dto = new MallSubTabDTO();
		long sortNum = Long.parseLong("1");
		dto.setUrl("www.baidu.com");
		dto.setTitle("新版");
		dto.setRedId("1");
		dto.setSortNum(sortNum);
		dto.setStatus("1");
		dto.setTemplateId("楼层模板一");
		try {
			mallSubTabService.addMallSubTab(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void update() {
		MallSubTabDTO dto = new MallSubTabDTO();
		dto.setId(Long.parseLong("3"));
		dto.setUrl("www.baidu.com");
		dto.setTitle("titless");
		dto.setRedId("3");
		long sortNum = Long.parseLong("1");
		dto.setSortNum(sortNum);
		dto.setStatus("1");
		mallSubTabService.updateMallSubData(dto);
	}

	// @Test
	public void getSubTab() {
		MallSubTabDTO mass = mallSubTabService.getMallSubTabData(Long.parseLong("2"));
		System.out.println(mass.getEndTime() + "结束时间");
	}

	@Test
	public void testequeryMallSubTabPage() {
		MallSubTabECMDTO med = new MallSubTabECMDTO();
		DataGrid<MallSubTabDTO> dagta = new DataGrid<MallSubTabDTO>();
		Pager page = new Pager();
		// page.setPage(1);
		// page.setRows(5);
		// med.setThemeId(1);
		// med.setThemeType(1);
		// med.setStatus("1");
		mallSubTabService.equeryMallSubTabPage(med, page);
	}
}

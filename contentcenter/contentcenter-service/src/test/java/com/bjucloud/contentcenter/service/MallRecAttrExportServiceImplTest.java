package com.bjucloud.contentcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallRecAttrDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrECMDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrQueryDTO;
import com.bjucloud.contentcenter.service.MallRecAttrExportService;

public class MallRecAttrExportServiceImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MallRecAttrExportServiceImplTest.class);
	ApplicationContext ctx = null;
	MallRecAttrExportService mallRecAttrExportService = null;
	
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		mallRecAttrExportService = (MallRecAttrExportService) ctx.getBean("mallRecAttrExportService");
	}

	@Test
	public void testQueryMallRecAttrList() {
		Pager page=new Pager();
		MallRecAttrQueryDTO mallRecAttrQueryDTO=new MallRecAttrQueryDTO();
		mallRecAttrQueryDTO.setStatus(1);
		DataGrid<MallRecAttrDTO> res=mallRecAttrExportService.queryMallRecAttrList(page, mallRecAttrQueryDTO);
		LOGGER.info("操作方法{}，结果信息{}","queryMallRecAttrList", JSONObject.toJSONString(res));
	}


	@Test
	public void testequeryMallRecAttrList() {
		Pager page=new Pager();
		MallRecAttrECMDTO mallRecAttrECMDTO=new MallRecAttrECMDTO();
		mallRecAttrECMDTO.setStatus(1);
		DataGrid<MallRecAttrECMDTO> res=mallRecAttrExportService.equeryMallRecAttrList(page, mallRecAttrECMDTO);
	}

	@Test
	public void testDelById() throws Exception {
		mallRecAttrExportService.delById(new Long(178));

	}
}

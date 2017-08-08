package com.bjucloud.contentcenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjucloud.contentcenter.dto.HTDAdvertisementDTO;

import cn.htd.common.ExecuteResult;


@SuppressWarnings("all")
public class HTDStaticAdvertiseTest {

	private static final Logger logger = LoggerFactory.getLogger(CreditsExchangeServiceImplTest.class);
	
	ApplicationContext ctx = null;
	HTDStaticAdvertiseExportService htdStaticAdvertiseExportService = null;
	HTDAdvertisementExportService htdAdvertisementExportService = null;
	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		htdStaticAdvertiseExportService = (HTDStaticAdvertiseExportService) ctx.getBean("htdStaticAdvertiseExportService");
		htdAdvertisementExportService = (HTDAdvertisementExportService) ctx.getBean("htdAdvertisementExportService");
	}
	
	@Test
	public void test001(){
		//测试init
		//htdStaticAdvertiseExportService.updateStaticAdvStatusInit();
		HTDAdvertisementDTO dto = new HTDAdvertisementDTO();
		dto.setId(13L);
		dto.setStatus("1");
		dto.setModify_id(1L);
		dto.setModify_name("123");
		htdAdvertisementExportService.updateTopAdvertisement(dto);
	}
	
}

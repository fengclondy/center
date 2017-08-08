package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.FreightTemplateAddressDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class FreightTemplateAddressServiceTest {
	private FreightTemplateAddressExportService freightTemplateAddressExportService;
	ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		freightTemplateAddressExportService = (FreightTemplateAddressExportService) ctx.getBean("freightTemplateAddressExportService");
	}


	@Test
	public void queryAllTest(){
		List<FreightTemplateAddressDTO> list = freightTemplateAddressExportService.queryAll();
	}
}

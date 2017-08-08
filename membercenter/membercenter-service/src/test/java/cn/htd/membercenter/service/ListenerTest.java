package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.membercenter.dto.YijifuCorporateDTO;

public class ListenerTest {
	ApplicationContext ctx = null;
	PayInfoService payInfoService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		payInfoService = (PayInfoService) ctx.getBean("payInfoService");
	}

	@Test
	public void testErpUp() {
		YijifuCorporateDTO dto = new YijifuCorporateDTO();
		payInfoService.realNameSaveVerify(dto);
	}
}

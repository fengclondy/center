package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberErpDTO;

public class MemberDownErpServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceMember.class);
	ApplicationContext ctx = null;
	MemberDownErpService memberDownErpService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberDownErpService = (MemberDownErpService) ctx.getBean("memberDownErpService");
	}

	public void testselectErpDownList() {
		MemberErpDTO dto = new MemberErpDTO();
		dto.setErpDownType("3");
		dto.setSpaceTime(6000L);
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		memberDownErpService.selectErpDownList(dto, pager);
	}

	@Test
	public void testsaveErpDownReset() {
		MemberErpDTO dto = new MemberErpDTO();
		dto.setErpDownType("3");
		dto.setId(110L);
		memberDownErpService.saveErpDownReset(dto);
	}
}

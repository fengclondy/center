package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.Pager;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;

public class MemberSuperBossServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceMember.class);
	ApplicationContext ctx = null;
	MemberSuperBossService memberSuperBossService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberSuperBossService = (MemberSuperBossService) ctx.getBean("memberSuperBossService");
	}

	@Test
	public void selectMemberByCustmanagerCode() {
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setBuyerSellerType("1");
		String key = KeygenGenerator.getUidKey();
		// ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs =
		// memberSuperBossService.selectMemberByCustmanagerCode("08019999",
		// pager);
		System.out.print(key);
	}
}

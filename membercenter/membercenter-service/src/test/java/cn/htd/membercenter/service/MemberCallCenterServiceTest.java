package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberGroupDTO;

public class MemberCallCenterServiceTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MemberCallCenterServiceTest.class);
	ApplicationContext ctx = null;
	MemberCallCenterService memberCallCenterService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberCallCenterService = (MemberCallCenterService) ctx
				.getBean("memberCallCenterService");
	}

	@Test
	public void selectMemberNameTest() {
		 ExecuteResult<MemberGroupDTO> rs = memberCallCenterService
		 .selectBuyCodeSellCode("0832", "HTD_13125455");
		// MemberGroupDTO m = rs.getResult();
		//ExecuteResult<MemberCallCenterDTO> rs = memberCallCenterService
		//		.selectMobilePhoneCallCenterInfo("");
		if (rs.isSuccess()) {
			System.out.println(11);
		}
		/*
		 * ExecuteResult<List<MemberCallCenterDTO>> executeResult = new
		 * ExecuteResult<List<MemberCallCenterDTO>>(); executeResult =
		 * memberCallCenterService.selectMemberName("测试1229");
		 * 
		 * @SuppressWarnings("unused") List<MemberCallCenterDTO> list =
		 * executeResult.getResult();
		 * Assert.assertTrue(executeResult.isSuccess());
		 */
		/*
		 * ExecuteResult<MemberBaseInfoDTO> rs = memberCallCenterService
		 * .selectMemberBaseName("htd0000000000000000000", "1");
		 * MemberBaseInfoDTO m = rs.getResult();
		 */
	}
}

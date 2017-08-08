package cn.htd.membercenter.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;

public class MemberVerifyStatusServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberVerifyStatusServiceTest.class);
	ApplicationContext ctx = null;
	MemberVerifyStatusService memberVerifyStatusService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberVerifyStatusService = (MemberVerifyStatusService) ctx.getBean("memberVerifyStatusService");
	}

	@Test
	public void queryMemberGradeListInfo() {
		Pager page = new Pager();
		String verifyStatus = null;
		Long sellerId = null;
		String name = null;
		String isDiffIndustry = "0";
		Date startTime = null;
		Date endTime = null;
		ExecuteResult<DataGrid<MemberVerifyStatusDTO>> res = memberVerifyStatusService.selectByStatus(page,
				verifyStatus, sellerId, name, isDiffIndustry, startTime, endTime);
		Assert.assertTrue(res.isSuccess());
	}

}

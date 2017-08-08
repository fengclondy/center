package cn.htd.membercenter.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberImportFailInfoDTO;

@Ignore
public class MemberImportServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberBaseServiceTest.class);
	ApplicationContext ctx = null;
	MemberImportService memberImportService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberImportService = (MemberImportService) ctx.getBean("memberImportService");
	}

	@Test
	public void queryMemberGradeListInfo() {
		List<MemberImportFailInfoDTO> fList = new ArrayList<MemberImportFailInfoDTO>();
		MemberImportFailInfoDTO ff = new MemberImportFailInfoDTO();
		ff.setMemberCode("123");
		fList.add(ff);
		ExecuteResult res = memberImportService.memberImport(fList);
		Assert.assertTrue(res.isSuccess());
	}

}

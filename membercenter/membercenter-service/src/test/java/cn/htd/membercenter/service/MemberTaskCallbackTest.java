package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.membercenter.dto.CompanyRelationDownErpCallbackDTO;
import cn.htd.membercenter.dto.MemberDownCallbackDTO;

public class MemberTaskCallbackTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberTaskCallbackTest.class);
	ApplicationContext ctx = null;
	MemberErpCallbackService memberBaseInfoService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBaseInfoService = (MemberErpCallbackService) ctx.getBean("memberErpCallbackService");
	}

	@Test
	public void testmemberDownErpCallback() {
		MemberDownCallbackDTO dto = new MemberDownCallbackDTO();
		dto.setMerchOrderNo("000@196AD89B0BB2");
		dto.setErpErrormessage("dsasa");
		dto.setErpMemberCode("08032000");
		dto.setMemberCode("HTD12232131");
		dto.setErpResult(1);
		memberBaseInfoService.memberDownErpCallback(dto);
	}

	public void testCompanyRelationDownErpCallback() {
		CompanyRelationDownErpCallbackDTO dto = new CompanyRelationDownErpCallbackDTO();
		dto.setMerchOrderNo("000@1");
		dto.setErrormessage("dsasa");
		dto.setResult(1);
		memberBaseInfoService.companyRelationDownErpCallback(dto);
	}
}

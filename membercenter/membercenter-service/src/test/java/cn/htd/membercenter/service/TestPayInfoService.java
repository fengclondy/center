package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.membercenter.dto.BindingBankCardCallbackDTO;
import cn.htd.membercenter.dto.BindingBankCardDTO;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;

public class TestPayInfoService {
	ApplicationContext ctx = null;
	PayInfoService payInfoService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		payInfoService = (PayInfoService) ctx.getBean("payInfoService");
	}

	public void testrealNameSaveVerify() {
		YijifuCorporateDTO dto = new YijifuCorporateDTO();
		dto.setComName("123213131");
		dto.setCorporateUserType("INDIVIDUAL");
		dto.setEmail("522503467@qq.com");
		dto.setCorporateUserType("");
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setRealName("游亚军");
		legalPerson.setAddress("123213131");
		legalPerson.setCertNo("522227199212174017");
		legalPerson.setEmail("522503467@qq.com");
		legalPerson.setMobileNo("15165167025");
		dto.setLegalPerson(legalPerson);
		dto.setLicenceNo("123213131");
		dto.setOrganizationCode("123213131");
		dto.setOrganizationCode("123213131");
		dto.setOutUserId("HTD_13125455");
		dto.setTaxAuthorityNo("123213131");
		dto.setVerifyRealName("YES");
		payInfoService.realNameSaveVerify(dto);
	}

	public void testrealNameModifyVerify() {
		YijifuCorporateModifyDTO dto = new YijifuCorporateModifyDTO();
		dto.setComName("123213131");
		dto.setCorporateUserType("INDIVIDUAL");
		dto.setUserId("17022414313011100000");
		dto.setEmail("122503456@qq.com");
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setRealName("游亚军");
		legalPerson.setAddress("123213131");
		legalPerson.setCertNo("522227199212174017");
		legalPerson.setEmail("522503467@qq.com");
		legalPerson.setMobileNo("15165167025");
		dto.setLicenceNo("123213131");
		dto.setOrganizationCode("123213131");
		dto.setOrganizationCode("123213131");
		dto.setTaxAuthorityNo("123213131");
		dto.setVerifyRealName("YES");
		dto.setLegalPersonCertNo("522227199212174017");
		dto.setLegalPersonName("游亚军");
		payInfoService.realNameModifyVerify(dto);
	}

	public void testbindingBankCard() {
		BindingBankCardDTO dto = new BindingBankCardDTO();
		dto.setBankCardNo("6222023100078818195");
		dto.setBankCode("ICBC");
		dto.setBankName("中国工商银行");
		dto.setCertNo("522227199212174017");
		dto.setCity("123213131");
		dto.setMobile("15165167025");
		dto.setProvince("123213131");
		dto.setPublicTag("N");
		dto.setUserId("17022414313011100000");
		dto.setRealName("游亚军");
		payInfoService.bindingBankCard(dto);
	}

	@Test
	public void testbindCardBack() {
		BindingBankCardCallbackDTO dto = new BindingBankCardCallbackDTO();
		dto.setBankId("32111111131");
		dto.setPactStatus("ENABLE");
		dto.setUserId("3242222223242342");
		payInfoService.bindCardBack(dto);
	}

	public void testyijiRealNameModify() {
		// payInfoService.yijiRealNameModify("16121914084711100000");
	}
}

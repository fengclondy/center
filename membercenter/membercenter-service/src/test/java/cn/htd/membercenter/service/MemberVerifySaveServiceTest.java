package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.CategoryBrandDTO;

public class MemberVerifySaveServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberVerifySaveServiceTest.class);
	ApplicationContext ctx = null;
	MemberVerifySaveService memberVerifySaveService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberVerifySaveService = (MemberVerifySaveService) ctx.getBean("memberVerifySaveService");
	}

	@Test
	public void testsaveMemberVerifyInfo() {
		ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
		CategoryBrandDTO dto = new CategoryBrandDTO();
		applyBusiRelationDto.setMemberId(21L);
		applyBusiRelationDto.setMemberId(1L);
		applyBusiRelationDto.setModifyName("AAAA");
		applyBusiRelationDto.setAuditStatus("2");
		applyBusiRelationDto.setSellerId(23L);
		applyBusiRelationDto.setBrandId(0L);
		applyBusiRelationDto.setCustomerManagerId("");
		applyBusiRelationDto.setCategoryId(0l);
		applyBusiRelationDto.setCreateId(0l);
		applyBusiRelationDto.setCreateName("AAAA");
		applyBusiRelationDto.setModifyId(0l);
		applyBusiRelationDto.setModifyName("AAAA");
		memberVerifySaveService.saveMemberVerifyInfo(applyBusiRelationDto);
	}
}

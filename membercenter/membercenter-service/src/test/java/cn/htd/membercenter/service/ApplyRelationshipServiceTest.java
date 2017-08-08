package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;

public class ApplyRelationshipServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplyRelationshipServiceTest.class);
	ApplicationContext ctx = null;
	ApplyRelationshipService applyRelationshipService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		applyRelationshipService = (ApplyRelationshipService) ctx.getBean("applyRelationshipService");
	}

	public void testsaveMemberVerifyInfo() {
		MyNoMemberDTO applyBusiRelationDto = new MyNoMemberDTO();
		CategoryBrandDTO dto = new CategoryBrandDTO();
		applyBusiRelationDto.setMemberId(21L);
		applyBusiRelationDto.setMemberId(1L);
		applyBusiRelationDto.setModifyName("AAAA");
		applyBusiRelationDto.setAddress("DASDADA");
		applyBusiRelationDto.setArtificialPersonIdcard("DSADADAD");
		applyBusiRelationDto.setArtificialPersonMobile("15922222121");
		applyBusiRelationDto.setArtificialPersonName("大师傅");
		applyBusiRelationDto.setArtificialPersonPicSrc("/dsada.img");
		applyBusiRelationDto.setArtificialPersonPicBackSrc("/dadsa.img");
		// applyBusiRelationDto.s
		// applyBusiRelationDto.setSellerId(23L);

		applyBusiRelationDto.setModifyId(0l);
		applyBusiRelationDto.setModifyName("AAAA");
		applyRelationshipService.applynoMemberToMember(applyBusiRelationDto);
	}

	public void testupdateOutSellerInfo() {
		MemberOutsideSupplierCompanyDTO outCompanyDto = new MemberOutsideSupplierCompanyDTO();
		outCompanyDto.setLocationAddr("320611400");
		outCompanyDto.setLocationCity("320611400");
		outCompanyDto.setLocationCounty("3206114");
		outCompanyDto.setLocationProvince("320611400");
		outCompanyDto.setLocationTown("320611400");
		outCompanyDto.setBankCounty("320611");
		outCompanyDto.setMemberId(1000l);
		// outCompanyDto.set

		applyRelationshipService.updateOutSellerInfo(outCompanyDto);
	}

	@Test
	public void checkOutCompanyRegister() {
		MemberOutsideSupplierCompanyDTO outCompanyDto = new MemberOutsideSupplierCompanyDTO();
		outCompanyDto.setCompanyName("dsada");
		outCompanyDto.setArtificialPersonMobile("321313");
		applyRelationshipService.checkOutCompanyRegister(outCompanyDto);
	}
}

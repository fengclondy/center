package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberModifyDTO;

public class TestModifyMember {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceMember.class);
	ApplicationContext ctx = null;
	MemberModifyVerifyService memberBaseInfoService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBaseInfoService = (MemberModifyVerifyService) ctx.getBean("memberModifyVerifyService");
	}

	public void testselectMemberModifyVerify() {
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		// memberBaseInfoDTO.setMemberType("1");
		memberBaseInfoDTO.setCompanyName("0812");
		//// memberBaseInfoDTO.setBelongCompanyName("中国");
		// memberBaseInfoDTO.setCurBelongCompanyName("adc");
		// memberBaseInfoDTO.setArtificialPersonMobile(Long.valueOf(1234567));
		// memberBaseInfoDTO.setBuyerSellerType("1");
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> res = memberBaseInfoService
				.selectMemberModifyVerify(memberBaseInfoDTO, pager);
		LOGGER.info(res.toString());
	}

	public void testlectModifyVerifyInfo() {
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		// memberBaseInfoDTO.setMemberType("1");
		memberBaseInfoDTO.setCompanyName("0812");
		//// memberBaseInfoDTO.setBelongCompanyName("中国");
		// memberBaseInfoDTO.setCurBelongCompanyName("adc");
		// memberBaseInfoDTO.setArtificialPersonMobile(Long.valueOf(1234567));
		// memberBaseInfoDTO.setBuyerSellerType("1");
		// ExecuteResult<DataGrid<VerifyDetailInfo>> res =
		//// memberBaseInfoService.selectModifyVerifyInfo(Long.valueOf(1));
		// LOGGER.info(res.toString());
	}

	@Test
	public void testsaveMemberModifyVerify() {
		MemberModifyDTO memberModifyDTO = new MemberModifyDTO();
		memberModifyDTO.setMemberId(Long.valueOf(1));
		memberModifyDTO.setOperatorId(Long.valueOf(1));
		memberModifyDTO.setVerifyId(Long.valueOf(5));
		memberModifyDTO.setOperatorName("dasdad");
		memberModifyDTO.setInfoType("15");
		memberModifyDTO.setVerifyStatus("2");
		memberBaseInfoService.saveMemberModifyVerify(memberModifyDTO);
	}

	public void testgetVerifyInfoById() {
		memberBaseInfoService.getVerifyInfoById(Long.valueOf(1));
	}

}

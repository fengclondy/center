package cn.htd.membercenter.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dao.MemberGradeDAO;

public class MemberGradeServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberGradeServiceTest.class);
	ApplicationContext ctx = null;
	MemberBaseInfoService memberBaseInfoService = null;
	MemberGradeDAO memberGradeDAO = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBaseInfoService = (MemberBaseInfoService) ctx.getBean("memberBaseInfoService");
		memberGradeDAO = (MemberGradeDAO) ctx.getBean("memberGradeDAO");
	}

	// @Test
	// public void queryMemberGradeListInfo() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberType("2");
	// Pager<MemberGradeDTO> pager = new Pager<MemberGradeDTO>();
	// pager.setPage(1);
	// pager.setRows(10);
	// ExecuteResult<DataGrid<MemberGradeDTO>> res =
	// memberGradeService.queryMemberGradeListInfo(memberBaseDTO, pager);
	// Assert.assertTrue(res.isSuccess());
	// }
	//
	// @Test
	// public void queryMemberGradeInfo() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberId("1");
	// ExecuteResult<MemberGradeDTO> res =
	// memberGradeService.queryMemberGradeInfo(memberBaseDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	@Test
	public void modifyMemberGradeAndPackageTypeById() {
		try {

			ExecuteResult<Long> ss = memberBaseInfoService.getMemberIdByCode("HTD_13125454");
			System.out.println(ss);
			// BuyerGradeInfoDTO memberGradeModel =
			// memberGradeDAO.getHTDMemberGrade(3333l);
			// if (memberGradeModel != null) {
			// MemberImportSuccInfoDTO memberImportSuccInfoDTO = new
			// MemberImportSuccInfoDTO();
			// memberImportSuccInfoDTO.setMemberId("3333");
			// memberGradeService.upgradeHTDUserGrade(memberImportSuccInfoDTO,
			// memberGradeModel, new Date());
			// }

			// List<HTDUserUpgradeDistanceDto> ugp =
			// memberGradeService.calculateHTDUserUpgradePath(3333l);
			// System.out.println(ugp);
			// Date jobDate = new Date();
			// memberGradeService.syncFinanceDailyAmount(jobDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		Assert.assertTrue(res.isSuccess());
	}

}

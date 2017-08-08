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
import cn.htd.membercenter.dto.MemberGradeRuleHistoryDTO;
import cn.htd.membercenter.dto.MemberScoreSetDTO;

public class MemberScoreSetTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberScoreSetTest.class);
	ApplicationContext ctx = null;
	MemberScoreSetService memberScoreSetService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberScoreSetService = (MemberScoreSetService) ctx.getBean("memberScoreSetService");
	}

	// @Test
	// public void insertMemberScoreSet() {
	// MemberScoreSetDTO memberScoreSetDTO = new MemberScoreSetDTO();
	// memberScoreSetDTO.setIntervalType("2");
	// memberScoreSetDTO.setFromAmount("0,1000,2000,3000");
	// memberScoreSetDTO.setToAmount("500,1500,2500,9999");
	// memberScoreSetDTO.setScore("100,200,300,400");
	// memberScoreSetDTO.setOperateId("1");
	// memberScoreSetDTO.setOperateName("1");
	// ExecuteResult<Boolean> res =
	// memberScoreSetService.updateMemberScoreSet(memberScoreSetDTO);
	// }

	// @Test
	// public void insertMemberScoreRule() {
	// MemberScoreSetDTO memberScoreSetDTO = new MemberScoreSetDTO();
	// memberScoreSetDTO.setFromScore("0,1000,2000,3000,4000");
	// memberScoreSetDTO.setToScore("500,1500,2500,3500,9999");
	// memberScoreSetDTO.setLowestPoint("100,200,300,400,500");
	// memberScoreSetDTO.setBuyerLevel("1,2,3,4,5");
	// memberScoreSetDTO.setOperateId("1");
	// memberScoreSetDTO.setOperateName("1");
	// ExecuteResult<Boolean> res =
	// memberScoreSetService.updateMemberScoreRule(memberScoreSetDTO);
	// }

	// @Test
	// public void queryMemberScoreSetList() {
	// MemberScoreSetDTO memberScoreSetDTO = new MemberScoreSetDTO();
	// memberScoreSetDTO.setIntervalType("1");
	// ExecuteResult<List<MemberScoreSetDTO>> res =
	// memberScoreSetService.queryMemberScoreSetList(memberScoreSetDTO);
	// }

	@Test
	public void queryMemberScoreRuleHistory() {
		MemberScoreSetDTO memberScoreSetDTO = new MemberScoreSetDTO();
		memberScoreSetDTO.setIntervalType("1");
		Pager<MemberGradeRuleHistoryDTO> pager = new Pager<MemberGradeRuleHistoryDTO>();
		pager.setPage(1);
		pager.setRows(10);
		ExecuteResult<DataGrid<MemberGradeRuleHistoryDTO>> res = memberScoreSetService
				.queryMemberScoreRuleHistory(memberScoreSetDTO, pager);
	}

	// @Test
	// public void insertMemberScoreWeight() {
	// MemberScoreSetDTO memberScoreSetDTO = new MemberScoreSetDTO();
	// memberScoreSetDTO.setMallWeight("50");
	// memberScoreSetDTO.setFinanceWeight("50");
	// memberScoreSetDTO.setOperateId("1");
	// memberScoreSetDTO.setOperateName("test");
	// ExecuteResult<Boolean> res =
	// memberScoreSetService.updateMemberScoreWeight(memberScoreSetDTO);
	// }

}

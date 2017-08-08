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
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;

public class MemberBusinessRelationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberBusinessRelationTest.class);
	ApplicationContext ctx = null;
	MemberBusinessRelationService memberBusinessRelationService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBusinessRelationService = (MemberBusinessRelationService) ctx.getBean("memberBusinessRelationService");
	}

	@Test
	public void queryMemberNoneBusinessRelationListInfo() {
		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		memberBusinessRelationDTO.setBuyerId("100911");
		memberBusinessRelationDTO.setSellerId("1");
		Pager<MemberBusinessRelationDTO> pager = new Pager<MemberBusinessRelationDTO>();
		pager.setPage(1);
		pager.setRows(10);
		ExecuteResult<DataGrid<MemberBusinessRelationDTO>> res = memberBusinessRelationService
				.queryMemberNoneBusinessRelationListInfo(memberBusinessRelationDTO, pager);
	}

	// @Test
	// public void queryMemberBusinessRelationPendingAudit() {
	// MemberBusinessRelationDTO memberBusinessRelationDTO = new
	// MemberBusinessRelationDTO();
	// memberBusinessRelationDTO.setBuyerId("1");
	// memberBusinessRelationDTO.setSellerId("1");
	// ExecuteResult<MemberBaseDTO> rs = memberBusinessRelationService
	// .queryMemberBusinessRelationPendingAudit(memberBusinessRelationDTO);
	// }

	// @Test
	// public void queryMemberBusinessRelationDetail() {
	// List<MemberBusinessRelationDTO> mb = new
	// ArrayList<MemberBusinessRelationDTO>();
	// MemberBusinessRelationDTO memberBusinessRelationDTO = new
	// MemberBusinessRelationDTO();
	// memberBusinessRelationDTO.setBuyerId("8");
	// memberBusinessRelationDTO.setSellerId("8");
	// memberBusinessRelationDTO.setCategoryId(5);
	// memberBusinessRelationDTO.setBrandId(5);
	// memberBusinessRelationDTO.setCustomerManagerId("1");
	// memberBusinessRelationDTO.setOperateId("1");
	// memberBusinessRelationDTO.setOperateName("test");
	// mb.add(memberBusinessRelationDTO);
	// ExecuteResult<Boolean> rs =
	// memberBusinessRelationService.queryMemberBoxRelationInfo(memberBusinessRelationDTO);
	// if (rs.getResult()) {
	// memberBusinessRelationService.insertMemberBusinessRelationInfo(mb);
	// } else {
	// memberBusinessRelationService.insertMeberBoxRelationInfo(memberBusinessRelationDTO);
	// memberBusinessRelationService.insertMemberBusinessRelationInfo(mb);
	// }
	// }

}

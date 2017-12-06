package cn.htd.membercenter.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yiji.openapi.tool.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.ErpSellerupDTO;
import cn.htd.membercenter.dto.MemberAuditPendingDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;

public class MemberBusinessRelationTest {

	private static final Logger logger = LoggerFactory.getLogger(MemberBusinessRelationTest.class);
	ApplicationContext ctx = null;
	MemberBusinessRelationService memberBusinessRelationService = null;
	@Resource
	ErpService erpService;
	MemberVerifyStatusService memberVerifyStatusService;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberVerifyStatusService = (MemberVerifyStatusService) ctx.getBean("memberVerifyStatusService");
	}
	
	@Test
	public void queryAuditPendingMember() throws ParseException {
		MemberAuditPendingDTO memberAuditPending = new MemberAuditPendingDTO();
		memberAuditPending.setMemberId(17606l);
		memberAuditPending.setVerifyStatus("1");
		String date = "2017-12-07 00:00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date a = dateFormat.parse(date);
		memberAuditPending.setEndTime(a);
		Pager<MemberVerifyStatusDTO> pager = new Pager<MemberVerifyStatusDTO>();
		pager.setPage(1);
		pager.setRows(10);
		ExecuteResult<DataGrid<MemberVerifyStatusDTO>> result = null;
	    result = memberVerifyStatusService.queryAuditPendingMember(pager,memberAuditPending);
	    logger.info("result = " + JSON.toJSONString(result));

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


	@Test
	public void updateInvoiceInfo() {
		ErpSellerupDTO dto = new ErpSellerupDTO();
		dto.setAccountNo("12");
		dto.setVendorName("常州驰晟网络科技有限公司1");
		dto.setTaxpayerIDnumber("34");
		dto.setDepositBank("56");
		dto.setFinancialAccount("78");
		dto.setContactMobile("91");
		dto.setRegisteredAddressProvince("13");
		dto.setBusinessAddressCity("46");
		dto.setBusinessAddressCounty("79");
		dto.setBusinessAddressDetailAddress("11");
		dto.setVendorCode("8126");
		erpService.saveErpSellerup(dto);
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

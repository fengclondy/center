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
import cn.htd.membercenter.dao.MemberLicenceInfoDao;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;

public class MemberOutsideSupplierTest {

	
	private static final Logger logger = LoggerFactory.getLogger(MemberOutsideSupplierTest.class);
	
	ApplicationContext ctx = null;
	MemberStatusService memberStatusService = null;
	MemberLicenceService memberLicenceService = null;
	MemberLicenceInfoDao memberLicenceInfoDao = null;
	
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberStatusService = (MemberStatusService)ctx.getBean("memberStatusService");
		memberLicenceService = (MemberLicenceService)ctx.getBean("memberLicenceService");
		memberLicenceInfoDao = (MemberLicenceInfoDao)ctx.getBean("memberLicenceInfoDao");
	}
	
	@Test
	public void test001(){
		//查询履历测试
		Pager page = new Pager();
		page.setPageOffset(0);
		page.setRows(Integer.MAX_VALUE);
		ExecuteResult<DataGrid<VerifyDetailInfo>> verifyById = memberStatusService.getVerifyById(page,1L);
		System.out.println(verifyById.toString());
	}
	
	@Test
	public void test002(){
		//查询会员解除归属关系待审核列表
		Pager page = new Pager(); 
		page.setPageOffset(0);
		page.setRows(10);
		MemberRemoveRelationshipDTO dto = new MemberRemoveRelationshipDTO();
		dto.setStatus("0");
		ExecuteResult<DataGrid<MemberRemoveRelationshipDTO>> queryRemoveRelationship = memberStatusService.queryRemoveRelationship(page, dto);
		System.out.println(queryRemoveRelationship.toString());
		System.out.println("=====");
	}
	
	@Test
	public void test003(){
		//查询会员解除归属关系待审详情
		ExecuteResult<MemberRemoveRelationshipDTO> queryRemoveRelationshipDetail = memberStatusService.queryRemoveRelationshipDetail("1", 25L);
		System.out.println(queryRemoveRelationshipDetail.toString());
		System.out.println("------------");
	}
	
	@Test
	public void test004(){
		//会员解决归属关系审核
		MemberRemoveRelationshipDTO dto = new MemberRemoveRelationshipDTO();
		dto.setMemberId(25L);
		dto.setStatus("2");
		dto.setBelongMemberId(1L);
		dto.setVerifyInfoId(30L);
		dto.setModifyId(123L);
		dto.setModifyName("dsga");
		ExecuteResult<Boolean> verifyRemoveRelationship = memberLicenceService.verifyRemoveRelationship(dto);
		System.out.println(verifyRemoveRelationship.toString());
	}
	
	
	@Test
	public void test005(){
		//更新发票信息
		MemberInvoiceDTO dto = new MemberInvoiceDTO();
		dto.setInvoiceId("11");
		dto.setBankName("江苏南京工商马甸支行");
		dto.setModifyId("123");
		dto.setModifyName("dag");
		memberLicenceService.updateMemberInvoiceInfo(dto);
	}
	
	@Test
	public void test006(){
		MemberRemoveRelationshipDTO dto = new MemberRemoveRelationshipDTO();
		dto.setMemberId(51L);
		memberLicenceInfoDao.updateMemberBaseInfo(dto);
	}
	
}

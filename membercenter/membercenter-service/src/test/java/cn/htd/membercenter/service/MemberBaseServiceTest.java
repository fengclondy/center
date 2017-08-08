package cn.htd.membercenter.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;

public class MemberBaseServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberBaseServiceTest.class);
	ApplicationContext ctx = null;
	MemberBaseService memberBaseService = null;
	MemberBaseInfoService memberBaseInfoService = null;
	MemberVerifyStatusService memberVerifyStatusService = null;
	MemberInvoiceService memberInvoiceService = null;
	ConsigneeAddressService consigneeAddressService = null;
	ApplyRelationshipService applyRelationshipService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		/*
		 * memberBaseService = (MemberBaseService) ctx
		 * .getBean("memberBaseService");
		 */

		memberInvoiceService = (MemberInvoiceService) ctx.getBean("memberInvoiceService");
		applyRelationshipService = (ApplyRelationshipService) ctx.getBean("applyRelationshipService");

	}

	@Test
	public void insertOutSellerInfo() {
		MemberOutsideSupplierCompanyDTO outCompanyDto = new MemberOutsideSupplierCompanyDTO();
		outCompanyDto.setMemberCode("");
		outCompanyDto.setBusinessCategory("3");
		outCompanyDto.setCompanyName("test");
		outCompanyDto.setAreaCode("025");
		outCompanyDto.setLandline("1234567");
		outCompanyDto.setSellerType("1");
		outCompanyDto.setBelongCompanyName("");
		outCompanyDto.setArtificialPersonName("cc");
		outCompanyDto.setArtificialPersonIdcard("123456778901234567");
		outCompanyDto.setArtificialPersonMobile("18811111111");
		outCompanyDto.setBusinessScope("ssss");
		outCompanyDto.setLocationProvince("32");
		outCompanyDto.setLocationCity("3201");
		outCompanyDto.setLocationCounty("320102");
		outCompanyDto.setLocationDetail("jjjjjjjjjjj");
		outCompanyDto.setFinancingNumber("");
		outCompanyDto.setCertificateType("1");
		outCompanyDto.setBusinessLicenseId("11111111");
		outCompanyDto.setUnifiedSocialCreditCode("");
		outCompanyDto.setTaxManId("222222");
		outCompanyDto.setTaxpayerType("1");
		outCompanyDto.setArtificialPersonPicSrc("/FhrRATseXPx.jpg");
		outCompanyDto.setBusinessLicensePicSrc("/FZ3ha5bK3i5.jpg");
		outCompanyDto.setOrganizationPicSrc("/F8st6iyDCri.jpg");
		outCompanyDto.setTaxRegistrationCertificatePicSrc("/FRPsbZFzPdc.jpg");
		outCompanyDto.setTaxpayerCertificatePicSrc("/F5racXhfTbR.jpg");
		outCompanyDto.setContractJssAddr("/F3PaxEFxjTn.pdf");
		outCompanyDto.setBankAccountName("测试银行");
		outCompanyDto.setBankAccount("62233546");
		outCompanyDto.setBankName("sss");
		outCompanyDto.setBankBranchJointLine("1234");
		outCompanyDto.setBankProvince("32");
		outCompanyDto.setBankCity("3201");
		outCompanyDto.setBankCounty("320102");
		outCompanyDto.setCompanyType("1");
		outCompanyDto.setHomePage("http://www.htd.cn");
		outCompanyDto.setOperateId("1");
		outCompanyDto.setOperateName("test");
		outCompanyDto.setCreateId(1L);
		outCompanyDto.setCreateName("test");
		applyRelationshipService.insertOutSellerInfo(outCompanyDto);
	}

	// @Test
	// public void queryMemberBaseInfoById() {
	// consigneeAddressService.selectChannelAddressDTO("1111", "HTD20000016",
	// "3010");
	// }

	// memberBaseService = (MemberBaseService) ctx
	// .getBean("memberBaseService");

	// memberInvoiceService = (MemberInvoiceService) ctx
	// .getBean("memberInvoiceService");
	//
	// @Test
	// public void queryMemberBaseInfoById() {
	// MemberInvoiceDTO memberInvoiceDTO = new MemberInvoiceDTO();
	// memberInvoiceDTO.setInvoiceNotify("test");
	// memberInvoiceDTO.setTaxManId("111");
	// memberInvoiceDTO.setContactPhone("1111");
	// memberInvoiceDTO.setInvoiceAddress("1111");
	// memberInvoiceDTO.setBankName("111");
	// memberInvoiceDTO.setBankAccount("111");
	// memberInvoiceDTO.setOperateId("1");
	// memberInvoiceDTO.setOperateName("111");
	// memberInvoiceDTO.setMemberId("2");
	// ExecuteResult<Boolean> test =
	// memberInvoiceService.modifyMemberInoviceInfo(memberInvoiceDTO);
	// }

	// @Test
	// public void queryMemberBaseInfo4order() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberId("1");
	// ExecuteResult<MemberBaseDTO> res =
	// memberBaseService.queryMemberBaseInfo4order(memberBaseDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	//
	// @Test
	// public void queryMemberGradeInfo() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberId("1");
	// ExecuteResult<MemberGradeDTO> res =
	// memberBaseService.queryMemberGradeInfo(memberBaseDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void queryMemberGradeHistoryListInfo() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberId("1");
	// Pager<MemberGradeHistoryDTO> pager = new Pager<MemberGradeHistoryDTO>();
	// pager.setPage(1);
	// pager.setRows(10);
	// ExecuteResult<DataGrid<MemberGradeHistoryDTO>> res = memberBaseService
	// .queryMemberGradeHistoryListInfo(memberBaseDTO, pager);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void updateMemberGradeInfo() {
	// MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
	// memberBaseDTO.setMemberId("1");
	// memberBaseDTO.setBuyerGrade("3");
	// ExecuteResult<Boolean> res =
	// memberBaseService.modifyMemberGrade4Grade(memberBaseDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void queryMemberInvoiceInfo() {
	// MemberInvoiceDTO memberInvoiceDTO = new MemberInvoiceDTO();
	// memberInvoiceDTO.setMemberId("1");
	// ExecuteResult<MemberInvoiceDTO> res =
	// memberBaseService.queryMemberInvoiceInfo(memberInvoiceDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	@Test
	public void modifyMemberInvoiceInfo() {
		MemberInvoiceDTO memberInvoiceDTO = new MemberInvoiceDTO();
		memberInvoiceDTO.setMemberId("10086");
		memberInvoiceDTO.setBankAccount("2");
		memberInvoiceDTO.setOperateType("1");
		memberInvoiceDTO.setTaxManId("8");
		memberInvoiceDTO.setOperateId("1");
		memberInvoiceDTO.setOperateName("测试用户");
		ExecuteResult<Boolean> res = memberInvoiceService.modifyMemberInoviceInfo(memberInvoiceDTO);

	}

	// @Test
	// public void queryMemberBusinessRelationListInfo() {
	// MemberBusinessRelationDTO memberBusinessRelationDTO = new
	// MemberBusinessRelationDTO();
	// memberBusinessRelationDTO.setBuyerId("1");
	// Pager<MemberBusinessRelationDTO> pager = new
	// Pager<MemberBusinessRelationDTO>();
	// pager.setPage(1);
	// pager.setRows(10);
	// ExecuteResult<DataGrid<MemberBusinessRelationDTO>> res =
	// memberBaseService
	// .queryMemberBusinessRelationListInfo(memberBusinessRelationDTO, pager);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void deleteMemberBusinessRelationInfo() {
	// MemberBusinessRelationDTO memberBusinessRelationDTO = new
	// MemberBusinessRelationDTO();
	// memberBusinessRelationDTO.setBuyerId("1");
	// memberBusinessRelationDTO.setSellerId("1");
	// memberBusinessRelationDTO.setBrandId("1");
	// memberBusinessRelationDTO.setCategoryId("1");
	// ExecuteResult<Boolean> res =
	// memberBaseService.deleteMemberBusinessRelationInfo(memberBusinessRelationDTO);
	// Assert.assertTrue(res.isSuccess());
	// }

	// @Test
	// public void insertMemberBusinessRelationInfo() {
	// MemberBusinessRelationDTO memberBusinessRelationDTO = new
	// MemberBusinessRelationDTO();
	// memberBusinessRelationDTO.setBuyerId("2");
	// memberBusinessRelationDTO.setSellerId("2");
	// memberBusinessRelationDTO.setBrandId("2");
	// memberBusinessRelationDTO.setCategoryId("23");
	// memberBusinessRelationDTO.setCustomerManagerId("2");
	// memberBusinessRelationDTO.setShopId("2");
	// memberBusinessRelationDTO.setCreateId("2");
	// memberBusinessRelationDTO.setCreateName("test");
	// memberBusinessRelationDTO.setModifyId("2");
	// memberBusinessRelationDTO.setModifyName("test");
	// ExecuteResult<Boolean> res =
	// memberBaseService.insertMemberBusinessRelationInfo(memberBusinessRelationDTO);
	// Assert.assertTrue(res.isSuccess());
	// }
	// @Test
	// public void test() {
	// Pager<MemberVerifyStatusDTO> page = new Pager<MemberVerifyStatusDTO>();
	// page.setSort("memberId");
	// page.setPageOffset(0);
	// page.setRows(Integer.MAX_VALUE);
	// ExecuteResult<DataGrid<MemberVerifyStatusDTO>> selectByStatus =
	// memberVerifyStatusService.selectByStatus(page,
	// "1");
	// }

	/*
	 * @Test public void queryMemberLoginInfo() { MemberBaseDTO memberBaseDTO =
	 * new MemberBaseDTO(); memberBaseDTO.setMemberId("1");
	 * ExecuteResult<MemberBaseDTO> res =
	 * memberBaseService.queryMemberLoginInfo(memberBaseDTO);
	 * Assert.assertTrue(res.isSuccess()); }
	 */

	// @Test
	// public void insertMemberBaseInfo() {
	// MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new
	// MemberBaseInfoRegisterDTO();
	// memberBaseInfoRegisterDTO.setHasBusinessLicense(1);
	// memberBaseInfoRegisterDTO.setArtificialPersonIdcard("1111");
	// memberBaseInfoRegisterDTO.setArtificialPersonIdcardPicSrc("照片地址");
	// memberBaseInfoRegisterDTO.setArtificialPersonMobile("15114215010");
	// memberBaseInfoRegisterDTO.setCompanyName("");
	// memberBaseInfoRegisterDTO.setCreateId(Long.valueOf("111"));
	// memberBaseInfoRegisterDTO.setCreateName("lj");
	// memberBaseInfoRegisterDTO.setModifyId(Long.valueOf("2222"));
	// memberBaseInfoRegisterDTO.setModifyName("haha");
	// // memberBaseInfoRegisterDTO.setRegistFrom("注册来源1");
	// // ExecuteResult<String> res = memberBaseInfoService
	// // .insertMemberBaseRegisterInfo(memberBaseInfoRegisterDTO);
	// // ExecuteResult<MemberBackupContactInfo> t = memberBaseInfoService
	// // .getContactId(Long.valueOf("11"));
	// // ExecuteResult<Integer> rs =
	// // memberBaseService.selectIsRealNameAuthenticated(Long.valueOf("25"));
	// // System.out.println(rs);
	//
	// ExecuteResult<MemberBaseInfoRegisterDTO> rs = memberBaseInfoService
	// .selectRegisterProgress(Long.valueOf("100002756"));
	// if (rs.isSuccess()) {
	// System.out.println(rs + "----");
	// }
	// /*
	// * MemberCompanyInfoDTO memberCompanyInfoDTO = new
	// * MemberCompanyInfoDTO(); memberCompanyInfoDTO.setBuyerSellerType(1);
	// * ExecuteResult<MemberCompanyInfoDTO> res = memberBaseInfoService
	// * .searchVoidMemberCompanyInfo(memberCompanyInfoDTO);
	// */
	//
	// }

	/*
	 * @Test public void queryMemberInfo() { String memberCode = "HTD20000019";
	 * ExecuteResult<MemberBaseInfoDTO> result = memberBaseInfoService
	 * .queryMemberBaseInfoByMemberCode(memberCode); MemberBaseInfoDTO dto =
	 * result.getResult(); System.out.println(dto.getId());
	 * 
	 * }
	 */

}

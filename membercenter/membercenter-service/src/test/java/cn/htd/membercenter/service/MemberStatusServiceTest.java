
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
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierDTO;
import cn.htd.membercenter.dto.MemberUncheckedDTO;

/**
 * 
 * 1.searchUncheckMemberStatus 非会员转会员、密码找回、手机号更改 列表查询 参数 company_name;//原公司名称
 * belong_company_name;//归属公司名称 belong_company_name_cur;//当前归属公司名称
 * contact_mobile;//联系人电话 member_type;//会员类型：1：担保会员，2：非会员，3：正式会员
 * verify_status;//审核状态/系统同步状态 1：待审核，2：已通过，3：被驳回
 * info_type;//信息类型1：找回密码未变更联系人信息审核，2：找回密码变更联系人信息审核，3：会员手机号码更改审核，4：支付系统同步状态，5：
 * ERP下行状态，
 * 11：会员注册运营审核，12：会员注册供应商审核，13：非会员注册审核，14：非会员转会员审核，15：会员信息修改审核，16：会员金融信息审核，
 * 21：商家注册审核，22：商家合同审核，23：商家店铺审核，24：商家信息修改审核'
 * 
 * query update add delete
 *
 * 2.searchUncheckMemberStatusDetail 非会员转会员详细信息查询 参数 member_id; //会员ID
 *
 *
 * 3.searchMemberPasswordUnCheckDetail 密码找回、手机号更改详细信息查询 参数 member_id; //会员ID
 * info_type 1：找回密码未变更联系人信息审核，2：找回密码变更联系人信息审核 3:会员手机号码更改审核
 *
 *
 *
 * 
 * 4.saveCheckMember 审核接口
 *
 * 参数 status_id //状态id
 *
 * info_type;//信息类型1：找回密码未变更联系人信息审核，2：找回密码变更联系人信息审核，3：会员手机号码更改审核，4：支付系统同步状态，5：
 * ERP下行状态，
 * 11：会员注册运营审核，12：会员注册供应商审核，13：非会员注册审核，14：非会员转会员审核，15：会员信息修改审核，16：会员金融信息审核，
 * 21：商家注册审核，22：商家合同审核，23：商家店铺审核，24：商家信息修改审核' checkstatus 为1 审核通过 其他均为不通过
 * buyer_business_license_pic_src_new;//营业执照变更证明 \现营业执照照片
 * buyer_guarantee_license_pic_src_new;//现担保证明 member_id; //会员ID
 * contact_mobile_new;//新手机号码 company_name_new;//公司名称（新）
 * artificial_person_name_new;//公司法人（新） modify_id;//更新人ID modify_name;//更新人名称
 * modify_time;//更新人时间
 *
 *
 *
 */
@SuppressWarnings("all")
public class MemberStatusServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(MemberStatusServiceTest.class);

	ApplicationContext ctx = null;
	MemberStatusService memberStatusService = null;
	MemberLicenceService memberLicenceService = null;

	MemberVerifyStatusService memberVerifyStatusService = null;

	MemberVerifyInfoService memberVerifyInfoService = null;

	MyMemberService myMemberService = null;

	@Before
	public void setUp() {
		try {
			ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
			memberStatusService = (MemberStatusService) ctx.getBean("memberStatusService");
			memberLicenceService = (MemberLicenceService) ctx.getBean("memberLicenceService");
			memberVerifyStatusService = (MemberVerifyStatusService) ctx.getBean("memberVerifyStatusService");
			memberVerifyInfoService = (MemberVerifyInfoService) ctx.getBean("memberVerifyInfoService");
			myMemberService = (MyMemberService) ctx.getBean("myMemberService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void MemberStatusTest() {

		Pager page = new Pager();
		page.setPageOffset(0);
		page.setRows(100);
		MemberUncheckedDTO dto = new MemberUncheckedDTO();
		dto.setMember_type("1");

		// ExecuteResult<DataGrid<MemberUncheckedDTO>>
		// queryNonMemberToMemberList =
		// memberStatusService.queryNonMemberToMemberList(page, dto);
		ExecuteResult<DataGrid<MemberUncheckedDTO>> passwordRecoveryVerifyList = memberStatusService
				.queryPasswordRecoveryVerifyList(page, dto);
				/*
				 * MemberUncheckedDetailDTO dto2 = new
				 * MemberUncheckedDetailDTO(); dto2.setMember_id(1L);
				 * dto2.setStatus_id(1L);
				 * memberLicenceService.verifyNonMemberToMember(dto2);
				 */

		// memberStatusService.queryPasswordRecoveryVerifyDetail(1L, "1");
		/*
		 * MemberLicenceInfoDetailDTO dto = new MemberLicenceInfoDetailDTO();
		 * dto.setStatus_id(1L); dto.setModify_id("12");
		 * dto.setModify_name("111"); dto.setModify_time("2016-01-01");
		 * dto.setVerify_status("2");
		 * memberLicenceService.verifyPasswordRecovery(dto);
		 */

		/**
		 * MyNoMemberDTO dto = new MyNoMemberDTO(); //
		 * dto.setLocationDetail(12); dto.setCompanyName("ddd"); //
		 * dto.setContactName("aa");
		 * myMemberService.saveNoMemberRegistInfo(dto);
		 **/

		System.out.println();
	}

	public void testExample() {
		System.out.println(memberStatusService.toString());
		System.out.println("----------测试用列------");
	}

	/**
	 * 测试非会员转会员查询待审列表
	 */
	public void testQueryList() {

		/*
		 * Pager page = new Pager(); page.setPageOffset(0);
		 * page.setRows(Integer.MAX_VALUE);
		 * 
		 * MemberStatusVO vo = new MemberStatusVO(); vo.setVerify_status("1");
		 * vo.setMember_type(2); vo.setInfo_type("1,2");
		 * 
		 * //查询密码找回的审核列表 ExecuteResult<DataGrid<MemberUncheckedDTO>>
		 * searchUncheckMemberStatus =
		 * memberStatusService.searchUncheckMemberStatus(page,vo);
		 * System.out.println(searchUncheckMemberStatus); MemberUncheckedDTO
		 * uncheckedDTO =
		 * searchUncheckMemberStatus.getResult().getRows().get(0);
		 * //Assert.assertTrue(searchUncheckMemberStatus.getResult().getRows().
		 * size()>0);
		 * 
		 * MemberStatusVO vo2 = new MemberStatusVO();
		 * vo2.setMember_id(uncheckedDTO.getMember_id());
		 * ExecuteResult<MemberUncheckedDetailDTO> uncheckMemberStatusDetail =
		 * memberStatusService.searchUncheckMemberStatusDetail(vo2);
		 * System.out.println(uncheckMemberStatusDetail); MemberLicenceInfoVO
		 * vo3 = new MemberLicenceInfoVO(); vo3.setMember_id(new Long(1));
		 * vo3.setInfo_type("2"); ExecuteResult<MemberLicenceInfoDetailDTO>
		 * passwordUnCheckDetail =
		 * memberLicenceService.searchMemberPasswordUnCheckDetail(vo3);
		 * 
		 * 
		 * 
		 * MemberStatusVO vo1 = new MemberStatusVO(); vo1.setStatus_id("1");
		 * vo1.setInfo_type("1"); vo1.setCheckstatus(1); vo1.setMember_id("1");
		 * vo1.setBuyer_business_license_pic_src_new("/aa/bb");
		 * vo1.setBuyer_guarantee_license_pic_src_new("/aa/cc");
		 * vo1.setContact_mobile_new("12345678901");
		 * vo1.setCompany_name_new("xindegongsi");
		 * vo1.setArtificial_person_name_new("xindefaren");
		 * vo1.setModify_id("10"); vo1.setModify_name("lisi"); SimpleDateFormat
		 * format = new SimpleDateFormat("yyyy-MM-dd");
		 * vo1.setModify_time(format.format(new Date())); ExecuteResult<String>
		 * saveCheckMember = memberStatusService.saveCheckMember(vo1, new
		 * MemberBaseInfoVO(), new MemberCompanyInfoVO());
		 * System.out.println(saveCheckMember.isSuccess());
		 * Assert.assertTrue(saveCheckMember.isSuccess());
		 * 
		 */
	}

	@Test
	public void test001() {

		/*
		 * Pager<MemberVerifyStatusDTO> page = new
		 * Pager<MemberVerifyStatusDTO>(); page.setSort("memberId");
		 * page.setPageOffset(0); page.setRows(Integer.MAX_VALUE);
		 * //ExecuteResult<DataGrid<MemberVerifyStatusDTO>> selectByStatus =
		 * memberVerifyStatusService.selectByStatus(page, "1");
		 * //System.out.println(selectByStatus);
		 * 
		 * ExecuteResult<MemberVerifyInfoDTO> queryInfoByMemberId =
		 * memberVerifyInfoService.queryInfoByMemberId(1); System.out.println();
		 */

	}

	public void test004() {
		MemberOutsideSupplierDTO dto = new MemberOutsideSupplierDTO();
		dto.setCardBindStatus("0");
		dto.setRealNameStatus("1");
		Pager page = new Pager();
		page.setPageOffset(0);
		page.setRows(Integer.MAX_VALUE);
		ExecuteResult<DataGrid<MemberOutsideSupplierDTO>> queryOutsideSupplier = memberStatusService
				.queryOutsideSupplier(page, dto);
		System.out.println(queryOutsideSupplier);
	}

	public void queryRemoveRelationship() {
		ExecuteResult<MemberLicenceInfoDetailDTO> passwordRecoveryVerifyList = memberStatusService
				.queryPhoneChangeVerifyDetail(Long.valueOf(100002691));
	}

	@Test
	public void queryPasswordRecoveryVerifyDetail() {
		ExecuteResult<MemberLicenceInfoDetailDTO> passwordRecoveryVerifyList = memberStatusService
				.queryPasswordRecoveryVerifyDetail(100002691L, "1");

	}

}

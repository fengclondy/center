package cn.htd.membercenter.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.dto.BoxAddDto;
import cn.htd.membercenter.dto.BuyerGroupInfo;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.CenterUpdateInfo;
import cn.htd.membercenter.dto.ErpSellerupDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.MemberInfoMotifyDTO;

public class TestServiceMember {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceMember.class);
	ApplicationContext ctx = null;
	MemberBaseInfoService memberBaseInfoService = null;
	ErpService erpService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBaseInfoService = (MemberBaseInfoService) ctx.getBean("memberBaseInfoService");
		erpService = (ErpService) ctx.getBean("erpService");
	}

	public void testSelectBaseInfo() {
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setBuyerSellerType("1");
		// memberBaseInfoDTO.setMemberType("2");
		// memberBaseInfoDTO.setMemberType("1");
		// memberBaseInfoDTO.setCompanyName("广发");
		//// memberBaseInfoDTO.setBelongCompanyName("中国");
		// memberBaseInfoDTO.setCurBelongCompanyName("adc");
		// memberBaseInfoDTO.setArtificialPersonMobile(Long.valueOf(1234567));
		// memberBaseInfoDTO.setBuyerSellerType("1");
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> res = memberBaseInfoService.selectBaseMemberInfo(memberBaseInfoDTO,
				pager);
		LOGGER.info(res.toString());
	}

	public void testmemberBaseInfoRegisterDTO() {
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
		memberBaseInfoRegisterDTO.setArtificialPersonMobile("12321");
		memberBaseInfoRegisterDTO.setArtificialPersonName("111");
		memberBaseInfoRegisterDTO.setCompanyName("111");
		memberBaseInfoRegisterDTO.setBelongSellerId(0l);
		// memberBaseInfoDTO.setMemberType("2");
		// memberBaseInfoDTO.setMemberType("1");
		// memberBaseInfoDTO.setCompanyName("广发");
		//// memberBaseInfoDTO.setBelongCompanyName("中国");
		// memberBaseInfoDTO.setCurBelongCompanyName("adc");
		// memberBaseInfoDTO.setArtificialPersonMobile(Long.valueOf(1234567));
		// memberBaseInfoDTO.setBuyerSellerType("1");
		ExecuteResult<String> res = memberBaseInfoService.insertMemberBaseRegisterInfo(memberBaseInfoRegisterDTO);
		LOGGER.info(res.toString());
	}

	public void testUpdateMemberInfo() {
		List<MemberBaseInfoDTO> dto = new ArrayList<MemberBaseInfoDTO>();
		MemberBaseInfoDTO memberBaseInfoDTO = null;
		memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setMemberCode("HTD20000016");
		memberBaseInfoDTO.setModifyId(Long.valueOf(11));
		memberBaseInfoDTO.setUpgradeCenterStoreTime(new Date());
		memberBaseInfoDTO.setModifyName("dsa");
		// Timestamp(System.currentTimeMillis()));
		dto.add(memberBaseInfoDTO);
		memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setMemberCode("456456");
		memberBaseInfoDTO.setModifyId(Long.valueOf(11));
		memberBaseInfoDTO.setModifyName("dsa");
		memberBaseInfoDTO.setUpgradeCenterStoreTime(new Date());
		dto.add(memberBaseInfoDTO);
		memberBaseInfoDTO.setUpgradeCenterStoreTime(new java.sql.Date((new java.util.Date()).getTime()));
		ExecuteResult<CenterUpdateInfo> res = memberBaseInfoService.updateMemberInfo(dto);
		System.out.println(res);
	};

	public void testupdateMemberBaseInfo() {
		MemberInfoMotifyDTO dto = new MemberInfoMotifyDTO();
		dto.setCompanyName("dasdsa");
		dto.setOperatorId(Long.valueOf(11));
		dto.setMemberId(Long.valueOf(50));
		dto.setOperatorName("");
		dto.setArtificialPersonIdcard("2131");
		dto.setArtificialPersonName("eqweqw");
		ExecuteResult<Boolean> res = memberBaseInfoService.updateMemberBaseInfo(dto);
		System.out.println(res);
	};

	public void testgetMemBaseInfoForExport() {
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		// memberBaseInfoDTO.setCompanyName("中国");
		// memberBaseInfoDTO.setBelongCompanyName("中国");
		// memberBaseInfoDTO.setCurBelongCompanyName("adc");
		// memberBaseInfoDTO.setArtificialPersonMobile(Long.valueOf(1234567));
		// memberBaseInfoDTO.setBuyerSellerType("1");
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> res = memberBaseInfoService
				.getMemBaseInfoForExport(memberBaseInfoDTO);
		System.out.println(res);
	};

	public void testgetMemberDetailById() {
		ExecuteResult<MemberDetailInfo> res = memberBaseInfoService.getMemberDetailById(Long.valueOf(1));
		System.out.println(res);
	};

	public void testgetMemberVerifyDetailById() {
		ExecuteResult<MemberDetailInfo> res = memberBaseInfoService.getMemberVerifyDetailById(Long.valueOf(73));
		System.out.println(res);
	};

	public void testupdateMemberIsvalid() {
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setId(Long.valueOf(1));
		memberBaseInfoDTO.setStatus("1");
		memberBaseInfoDTO.setAccountType("dsadsada");
		memberBaseInfoDTO.setModifyName("dsada");
		memberBaseInfoDTO.setModifyId(Long.valueOf(11));
		ExecuteResult<Boolean> res = memberBaseInfoService.updateMemberIsvalid(memberBaseInfoDTO);
		System.out.println(res);
	};

	public void testupgetMemberGroupInfo() {
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		ExecuteResult<DataGrid<BuyerGroupInfo>> res = memberBaseInfoService.getMemberGroupInfo(Long.valueOf(1), pager);
		System.out.println(res);
	};

	public void testgetBuyerPointHis() throws ParseException {
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		BuyerHisPointDTO dto = new BuyerHisPointDTO();

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		String aa = trancDate.toString();
		dto.setProvideEndTime(trancDate);
		dto.setProvideStartTime(trancDate);
		dto.setMemberId(Long.valueOf(50));
		ExecuteResult<DataGrid<BuyerPointHistory>> res = memberBaseInfoService.getBuyerPointHis(dto, pager);
		LOGGER.info(res.toString());
	};

	public void testupdateMemberCenter() {
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO dto = new MemberBaseInfoDTO();
		dto.setModifyId(Long.valueOf(0));
		dto.setModifyName("q11");
		dto.setId(Long.valueOf(1));
		dto.setIsCenterStore(1);
		ExecuteResult<Boolean> res = memberBaseInfoService.updateMemberCenter(dto);
		// Long.valueOf(1), 1);
		System.out.println(res);
	};

	public void testselectVerifyMember() {
		Pager pager = new Pager();
		pager.setRows(10);
		pager.setPage(0);
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> res = memberBaseInfoService.selectVerifyMember(memberBaseInfoDTO,
				pager);
		System.out.println(res);
	};

	public void testselectMemberLicenceInfoById() {
		ExecuteResult<MemberLicenceInfo> rs = memberBaseInfoService.selectMemberLicenceInfoById(Long.valueOf(1));
		System.out.println(rs);
	}

	public void testsaveVerifyInfo() {
		MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
		memberBaseInfoDTO.setId(Long.valueOf(9));
		memberBaseInfoDTO.setStatus("1");
		memberBaseInfoDTO.setAccountType("dsadsada");
		memberBaseInfoDTO.setModifyName("dsada");
		memberBaseInfoDTO.setModifyId(Long.valueOf(14341));
		memberBaseInfoDTO.setVerifyStatus("2");
		memberBaseInfoDTO.setRemark("sadas");

		ExecuteResult<Boolean> rs = memberBaseInfoService.saveVerifyInfo(memberBaseInfoDTO);
		System.out.println(rs);
	}

	public void selectSupplierByMemberCode() {
		ExecuteResult<List<MemberBaseInfoDTO>> rs = memberBaseInfoService.selectSupplierByMemberCode("htd1000000");
		System.out.println(rs);
	}

	public void testselectMemberbaseListByTime() throws ParseException {
		Timestamp date2 = new Timestamp(1487310686);
		Timestamp date1 = new Timestamp(1487310682);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date trancDate = format.parse(format.format(date));
		ExecuteResult<List<MemberBaseInfoDTO>> rs = memberBaseInfoService.selectMemberbaseListByTime(1487310682l,
				1487310686l);
		System.out.println(rs);
	}

	public void testGetMemberInfoByCompanyCode() {
		// Date trancDate = format.parse(format.format(date));
		ExecuteResult<List<MemberBaseInfoDTO>> rs = memberBaseInfoService
				.getMemberInfoByCompanyCode(GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
		System.out.println(rs);
	}

	public void testorderAfterDownErp() {
		BoxAddDto dto = new BoxAddDto();
		dto.setMemberCode("HTD20000019");
		dto.setSupplierCode("htd0832");
		dto.setModifyId(1L);
		dto.setBrandCode("111");
		dto.setClassCategoryCode("1141");
		dto.setModifyName("111");
		memberBaseInfoService.orderAfterDownErp(dto);
	}

	public void testErpModify() {
		try {
			ErpSellerupDTO dto = new ErpSellerupDTO();
			dto.setBusinessAddressCity("12345");
			dto.setBusinessAddressCounty("12345");
			dto.setBusinessAddressDetailAddress("12345");
			dto.setCompanyLeagalPersion("12345");
			dto.setContactMobile("12345");
			dto.setContactEmail("12345");
			dto.setContactName("12345");
			dto.setCreateTime("12345");
			dto.setDepositBank("12345");
			dto.setDepositBank("12345");
			dto.setFinancialAccount("12345");
			dto.setIsNormalTaxpayer("12345");
			dto.setMemberCode("12345");
			dto.setBusinessLicenseCode("11");
			dto.setRegisteredAddressProvince("12345");
			dto.setTaxpayerIDnumber("12345");
			dto.setUseOtherChannels("12345");
			dto.setVendorName("12345");
			dto.setVendorCode("0832");
			erpService.saveErpSellerup(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testupdateMemberBussinessType() {
		MemberBaseInfoDTO dto = new MemberBaseInfoDTO();
		dto.setBusinessType("2");
		dto.setId(17606l);
		memberBaseInfoService.updateMemberBussinessType(dto);

	}

	public void queryAccountNoListByName() {
		ExecuteResult<List<MemberImportSuccInfoDTO>> rs = memberBaseInfoService
				.queryAccountNoListByName("无锡市崇安区阳光家电有限公司");
		System.out.print(rs);

	}

	@Test
	public void IsHasInnerComapanyCert() {
		ExecuteResult<String> rs = memberBaseInfoService.IsHasInnerComapanyCert("htd927536");
		System.out.print(rs);
	}

	@Test
	public void getInnerInfoByOuterHTDCode() {
		ExecuteResult<String> rs = memberBaseInfoService.IsHasInnerComapanyCert("htd927536");
		System.out.print(rs);
	}

}

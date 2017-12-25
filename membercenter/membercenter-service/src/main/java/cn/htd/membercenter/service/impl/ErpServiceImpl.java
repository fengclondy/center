package cn.htd.membercenter.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.common.util.DateUtils;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.ErpSellerupDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.domain.ErpInnerVendorUpLog;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.domain.MemberInvoiceInfo;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.ErpSellerupDTO;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.service.ErpService;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.membercenter.service.PayInfoService;
import cn.htd.storecenter.dto.ShopDTO;
import cn.htd.storecenter.service.ShopExportService;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.service.CustomerService;

@Service("erpService")
public class ErpServiceImpl implements ErpService {
	protected static transient Logger logger = LoggerFactory.getLogger(ErpServiceImpl.class);
	@Resource
	ErpSellerupDAO erpSellerupDAO;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Resource
	BelongRelationshipDAO belongRelationshipDAO;

	@Resource
	MemberVerifySaveDAO verifyInfoDAO;

	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;

	@Resource
	MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Autowired
	CustomerService customerService;

	@Autowired
	PayInfoService payInfoService;

	@Autowired
	MemberBaseInfoService memberBaseInfoService;

	@Autowired
	ShopExportService shopExportService;

	@Autowired
	private MemberBaseService memberBaseService;

	@Resource
	private MemberGradeService memberGradeService;

	@Override
	public boolean saveErpSellerup(ErpSellerupDTO dto) {
		// dto.setContactMobile("15077824436");
		// dto.setCompanyLeagalPersion("呵呵呵");
		// dto.setBusinessLicenseCode("432342349359");
		logger.info("ErpServiceImpl=========" + "服务执行开始,参数:" + JSONObject.toJSONString(dto));

		// 判断companyCode是否存在
		List<MemberBaseInfoDTO> rsList = memberBaseInfoService
				.getMemberInfoByCompanyCode(dto.getVendorCode(), GlobalConstant.IS_SELLER).getResult();
		if (null != rsList && rsList.size() > 0) {
			dto.setMemberCenterCode(rsList.get(0).getMemberCode());
			dto.setMemberId(rsList.get(0).getId());
			if (!StringUtils.isEmpty(rsList.get(0).getAccountNo())) {
				dto.setAccountNo(rsList.get(0).getAccountNo());
			}
			updateVendorInfo(dto);
			return true;
		}
		MemberBaseInfoDTO memberBaseDto = tranceToMemberBaseInfoDto(dto);

		// 根据规则获取memberCode
		String memberCode = customerService.genSellerCode().getResult();
		memberBaseDto.setMemberCode(memberCode);
		erpSellerupDAO.saveErpToMemberBaseInfo(memberBaseDto);
		dto.setMemberId(memberBaseDto.getId());
		dto.setMemberCenterCode(memberBaseDto.getMemberCode());

		// 生成用户
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLoginId(memberCode);
		customerDTO.setMobile(dto.getContactMobile());
		customerDTO.setPassword(dto.getContactMobile());
		customerDTO.setName(dto.getVendorName());
		customerDTO.setIsVmsInnerUser(false);
		customerDTO.setCompanyId(dto.getMemberId());
		customerDTO.setVmsPermissions(GlobalConstant.VMSPERM);
		customerService.addSeller(customerDTO, 0L);
		YijifuCorporateDTO yijifuDto = tranceToYijifuDto(dto);

		YijifuCorporateCallBackDTO yijifuCallbackDto = payInfoService.realNameSaveVerify(yijifuDto).getResult();
		MemberCompanyInfo companyInfo = tranceToMemberCompanyInfo(dto);
		companyInfo.setAccountNo(yijifuCallbackDto.getAccountNo());
		companyInfo.setRealNameStatus("3");// 统一改成已实名

		erpSellerupDAO.saveErpToMemberCompanyInfo(companyInfo);
		companyInfo.setBuyerSellerType(GlobalConstant.IS_BUYER);
		companyInfo.setCompanyCode(dto.getMemberCode());
		erpSellerupDAO.saveErpToMemberCompanyInfo(companyInfo);

		MemberInvoiceInfo invoiceInfo = tranceToMemberInvoiceInfo(dto);
		erpSellerupDAO.saveErpToMemberInvoiceInfo(invoiceInfo);

		MemberOutsideSupplierCompanyDTO outCompany = new MemberOutsideSupplierCompanyDTO();
		outCompany.setMemberId(dto.getMemberId());
		outCompany.setTaxManId(null != dto.getTaxpayerIDnumber() ? dto.getTaxpayerIDnumber() : "");
		outCompany.setBusinessLicenseId(dto.getBusinessLicenseCode());
		applyRelationshipDao.insertMemberLicenceInfo(outCompany);

        // 新增会员等级
        BuyerGradeInfoDTO gradeDto = new BuyerGradeInfoDTO();
        gradeDto.setBuyerId(dto.getMemberId());
        gradeDto.setBuyerGrade("1");
        gradeDto.setPointGrade(1l);
        gradeDto.setCreateId(memberBaseDto.getCreateId());
        gradeDto.setCreateName(memberBaseDto.getCreateName());
        gradeDto.setModifyId(memberBaseDto.getModifyId());
        gradeDto.setModifyName(memberBaseDto.getModifyName());
        memberGradeService.insertGrade(gradeDto);

		addDownErpSuccessStatus(memberBaseDto);

		saveBelongAndBoxRelation(memberBaseDto);

		ShopDTO shopDTO = tranceToShopDto(dto);
		shopDTO.setSellerId(dto.getMemberId());
		shopExportService.saveShopInfo(shopDTO);// 新增店铺

		logger.info("ErpServiceImpl=========saveErpSellerup服务执行结束,执行成功");
		return true;
	}

	@Override
	public boolean saveErpUpLog(ErpSellerupDTO dto) {
		logger.info("ErpServiceImpl=========saveErpSellerup服务执行开始,参数:" + JSONObject.toJSONString(dto));
		ErpInnerVendorUpLog erpLog = tranceToErpInnerLog(dto);
		erpLog.setMemberCode(null != dto.getMemberCode() ? dto.getMemberCode() : "");
		erpLog.setMemberId(dto.getMemberId());
		logger.info("ErpServiceImpl=========saveErpSellerup服务执行结束,执行成功");
		erpSellerupDAO.saveErpToMemberErpLog(erpLog);
		return true;
	}

	private MemberBaseInfoDTO tranceToMemberBaseInfoDto(ErpSellerupDTO dto) {
		MemberBaseInfoDTO memberBaseDto = new MemberBaseInfoDTO();
		Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		Date defaultDate = DateUtils.parse("0000-00-00 00:00:00", "yyyy-MM-dd HH:mm:ss");
		memberBaseDto.setCompanyCode(null != dto.getVendorCode() ? dto.getVendorCode() : "");
		memberBaseDto.setBelongManagerId("");
		memberBaseDto.setCurBelongManagerId("");
		memberBaseDto.setCompanyLeagalPersionFlag("");
		memberBaseDto.setIsBuyer(GlobalConstant.FLAG_YES);
		memberBaseDto.setIsSeller(GlobalConstant.FLAG_YES);
		memberBaseDto.setCanMallLogin(GlobalConstant.FLAG_YES);
		memberBaseDto.setHasBusinessLicense(GlobalConstant.FLAG_YES);
		memberBaseDto.setHasGuaranteeLicense(GlobalConstant.FLAG_NO);
		memberBaseDto.setUpgradeSellerTime(tranceDate);
		Date registTime = new Date();
		if (dto.getCreateTime() != null) {
			try {
				registTime = DateUtils.parse(dto.getCreateTime(), "yyyy-MM-dd");
			} catch (Exception e) {
				registTime = tranceDate;
			}
		}
		memberBaseDto.setRegistTime(registTime);
		memberBaseDto.setAccountType("0");
		memberBaseDto.setBecomeMemberTime(registTime);
		memberBaseDto.setIsCenterStore(GlobalConstant.FLAG_NO);
		memberBaseDto.setUpgradeCenterStoreTime(defaultDate);
		memberBaseDto.setSellerType(GlobalConstant.INER_SELLER_TYPE);
		memberBaseDto.setIsGeneration(GlobalConstant.FLAG_NO);
		memberBaseDto.setIndustryCategory("");
		memberBaseDto.setIsDiffIndustry(GlobalConstant.FLAG_NO);
		memberBaseDto.setContactName(null != dto.getContactName() ? dto.getContactName() : "");
		memberBaseDto.setContactEmail(null != dto.getContactEmail() ? dto.getContactEmail() : "");
		memberBaseDto.setContactMobile(dto.getContactMobile());
		memberBaseDto.setContactIdcard("");
		memberBaseDto.setContactPicBackSrc("");
		memberBaseDto.setContactPicSrc("");
		memberBaseDto.setIsPhoneAuthenticated(GlobalConstant.FLAG_NO);
		memberBaseDto.setIsRealNameAuthenticated(GlobalConstant.FLAG_NO);
		memberBaseDto.setCooperateVendor("");
		memberBaseDto.setRegistFrom("");
		memberBaseDto.setPromotionPerson("");
		memberBaseDto.setBelongSellerId(GlobalConstant.NULL_DEFAUL_VALUE);
		memberBaseDto.setCurBelongSellerId(GlobalConstant.NULL_DEFAUL_VALUE);
		memberBaseDto.setStatus(GlobalConstant.STATUS_YES);
		memberBaseDto.setCreateId(GlobalConstant.NULL_DEFAUL_VALUE);
		memberBaseDto.setCreateTime(tranceDate);
		memberBaseDto.setCreateName("");
		memberBaseDto.setModifyId(GlobalConstant.NULL_DEFAUL_VALUE);
		memberBaseDto.setModifyName("");
		memberBaseDto.setModifyTime(tranceDate);
		memberBaseDto.setBusinessType("2");
		return memberBaseDto;
	}

	private ShopDTO tranceToShopDto(ErpSellerupDTO dto) {
		ShopDTO shopDto = new ShopDTO();
		shopDto.setShopName(dto.getVendorName());
		shopDto.setShopType("2");
		shopDto.setDisclaimer("平台公司");
		shopDto.setStatus("5");
		shopDto.setCreateName("内部供应商上行");
		shopDto.setCreateId(0L);
		shopDto.setModifyId(0L);
		shopDto.setModifyName("内部供应商上行");
		// 默认为代理
		shopDto.setBusinessType("2");
		// shopDto.set
		return shopDto;
	}

	private MemberCompanyInfo tranceToMemberCompanyInfo(ErpSellerupDTO dto) {
		Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		MemberCompanyInfo companyInfo = new MemberCompanyInfo();
		companyInfo.setMemberId(dto.getMemberId());
		companyInfo.setBuyerSellerType(GlobalConstant.IS_SELLER);
		companyInfo.setCompanyName(null != dto.getVendorName() ? dto.getVendorName() : "");
		companyInfo.setCompanyCode(null != dto.getVendorCode() ? dto.getVendorCode() : "");
		companyInfo.setArtificialPersonName(null != dto.getCompanyLeagalPersion() ? dto.getCompanyLeagalPersion() : "");
		companyInfo.setArtificialPersonMobile(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		companyInfo.setArtificialPersonIdcard("");
		companyInfo.setArtificialPersonPicSrc("");
		companyInfo.setArtificialPersonPicBackSrc("");
		companyInfo.setArtificialPersonIdcardPicSrc("");
		companyInfo.setAreaCode("");
		companyInfo.setLandline("");
		companyInfo.setFaxNumber("");
		companyInfo.setLocationProvince(
				null != dto.getRegisteredAddressProvince() ? dto.getRegisteredAddressProvince() : "");
		companyInfo.setLocationCity(null != dto.getBusinessAddressCity() ? dto.getBusinessAddressCity() : "");
		companyInfo.setLocationCounty(null != dto.getBusinessAddressCounty() ? dto.getBusinessAddressCounty() : "");
		companyInfo.setLocationDetail(
				null != dto.getBusinessAddressDetailAddress() ? dto.getBusinessAddressDetailAddress() : "");
		companyInfo.setLocationTown("");
		String areaCode = "";
		String locationAddr = "";
		String locadetail = "";
		if (!StringUtils.isEmpty(dto.getBusinessAddressCounty())) {
			areaCode = dto.getBusinessAddressCounty();
		} else if (!StringUtils.isEmpty(dto.getBusinessAddressCity())) {
			areaCode = dto.getBusinessAddressCity();
		} else if (!StringUtils.isEmpty(dto.getRegisteredAddressProvince())) {
			areaCode = dto.getRegisteredAddressProvince();
		}
		if (!StringUtils.isEmpty(areaCode)) {
			locationAddr = memberBaseService.getAddressBaseByCode(areaCode);
		}

		if (!StringUtils.isEmpty(dto.getBusinessAddressDetailAddress())) {
			locadetail = dto.getBusinessAddressDetailAddress();
		}

		if (!StringUtils.isEmpty(locationAddr)) {
			locationAddr = locationAddr + locadetail;
		}

		companyInfo.setLocationAddr(locationAddr);
		companyInfo.setCreateId(GlobalConstant.NULL_DEFAUL_VALUE);
		companyInfo.setCreateName("");
		companyInfo.setCreateTime(tranceDate);
		companyInfo.setModifyId(GlobalConstant.NULL_DEFAUL_VALUE);
		companyInfo.setModifyName("");
		companyInfo.setModifyTime(tranceDate);

		return companyInfo;
	}

	private MemberInvoiceInfo tranceToMemberInvoiceInfo(ErpSellerupDTO dto) {
		Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		Date defaultDate = DateUtils.parse("0000-00-00 00:00:00", "yyyy-MM-dd HH:mm:ss");
		MemberInvoiceInfo invoiceInfo = new MemberInvoiceInfo();
		invoiceInfo.setMemberId(dto.getMemberId());
		invoiceInfo.setChannelCode("");
		invoiceInfo.setInvoiceNotify(null != dto.getVendorName() ? dto.getVendorName() : "");
		invoiceInfo.setInvoiceCompanyName(null != dto.getVendorName() ? dto.getVendorName() : "");
		invoiceInfo.setTaxManId(null != dto.getTaxpayerIDnumber() ? dto.getTaxpayerIDnumber() : "");
		invoiceInfo.setBankName(null != dto.getDepositBank() ? dto.getDepositBank() : "");
		invoiceInfo.setBankAccount(null != dto.getFinancialAccount() ? dto.getFinancialAccount() : "");
		invoiceInfo.setContactPhone(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		invoiceInfo.setInvoiceAddressProvince(
				null != dto.getRegisteredAddressProvince() ? dto.getRegisteredAddressProvince() : "");
		invoiceInfo.setInvoiceAddressCity(null != dto.getBusinessAddressCity() ? dto.getBusinessAddressCity() : "");
		invoiceInfo
				.setInvoiceAddressCounty(null != dto.getBusinessAddressCounty() ? dto.getBusinessAddressCounty() : "");
		invoiceInfo.setInvoiceAddressTown("");
		invoiceInfo.setInvoiceAddressDetail(
				null != dto.getBusinessAddressDetailAddress() ? dto.getBusinessAddressDetailAddress() : "");
		invoiceInfo.setDeleteFlag(GlobalConstant.FLAG_NO);
		invoiceInfo.setErpStatus(ErpStatusEnum.SUCCESS.getValue());
		//invoiceInfo.setErpDownTime(defaultDate);
		invoiceInfo.setErpDownTime(tranceDate);
		invoiceInfo.setErpErrorMsg("");
		invoiceInfo.setInvoicePerson("");
		invoiceInfo.setCreateId(GlobalConstant.NULL_DEFAUL_VALUE);
		invoiceInfo.setCreateName("");
		invoiceInfo.setCreateTime(tranceDate);
		invoiceInfo.setModifyId(GlobalConstant.NULL_DEFAUL_VALUE);
		invoiceInfo.setModifyName("");
		invoiceInfo.setModifyTime(tranceDate);

		invoiceInfo.setBankAccount(dto.getFinancialAccount());
		invoiceInfo.setBankName(dto.getDepositBank());
		return invoiceInfo;
	}

	private ErpInnerVendorUpLog tranceToErpInnerLog(ErpSellerupDTO dto) {
		Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		ErpInnerVendorUpLog erpLog = new ErpInnerVendorUpLog();
		erpLog.setBusinessAddressCity(null != dto.getBusinessAddressCity() ? dto.getBusinessAddressCity() : "");
		erpLog.setBusinessAddressCounty(null != dto.getBusinessAddressCounty() ? dto.getBusinessAddressCounty() : "");
		erpLog.setBusinessAddressDetail(
				null != dto.getBusinessAddressDetailAddress() ? dto.getBusinessAddressDetailAddress() : "");
		erpLog.setBusinessAddressProvince("");
		erpLog.setBusinessBrand("");
		erpLog.setBusinessCategory("");
		erpLog.setBusinessLicenseId("");
		erpLog.setBusinessScope("");
		erpLog.setChainOfAuthorize("");
		erpLog.setCompanyLeagalPersionFlag("0");
		erpLog.setCompanySalesScale("");
		erpLog.setContactEmail(null != dto.getContactEmail() ? dto.getContactEmail() : "");
		erpLog.setContactMobile(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		erpLog.setContactName(null != dto.getContactName() ? dto.getContactName() : "");
		erpLog.setCreateId(GlobalConstant.NULL_DEFAUL_VALUE);
		erpLog.setCreateName("SYS");
		erpLog.setCreateTime(null != DateUtils.parse(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss")
				? DateUtils.parse(dto.getCreateTime(), "yyyy-MM-dd HH:mm:ss") : tranceDate);
		erpLog.setDepositBank(null != dto.getDepositBank() ? dto.getDepositBank() : "");
		erpLog.setErrorMsg(null != dto.getErrMsg() ? dto.getErrMsg() : "");
		erpLog.setFinancialAccount(null != dto.getFinancialAccount() ? dto.getFinancialAccount() : "");
		erpLog.setInvoiceContactPhone(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		if (null != erpLog.getIsNormaTaxpayer()) {
			erpLog.setIsNormaTaxpayer(null != dto.getIsNormalTaxpayer() ? Integer.parseInt(dto.getIsNormalTaxpayer())
					: GlobalConstant.FLAG_NO);
		}

		if (null != dto.getIsNormalTaxpayer()) {
			if ("YES".equals(dto.getIsNormalTaxpayer())) {
				erpLog.setIsNormaTaxpayer(GlobalConstant.FLAG_YES);
			} else {
				erpLog.setIsNormaTaxpayer(GlobalConstant.FLAG_NO);
			}
		} else {
			erpLog.setIsNormaTaxpayer(GlobalConstant.FLAG_NO);
		}

		if (null != dto.getUseOtherChannels()) {
			if ("YES".equals(dto.getUseOtherChannels())) {
				erpLog.setIsUseOtherPlatform(GlobalConstant.FLAG_YES);
			} else {
				erpLog.setIsUseOtherPlatform(GlobalConstant.FLAG_NO);
			}
		} else {
			erpLog.setIsUseOtherPlatform(GlobalConstant.FLAG_NO);
		}

		erpLog.setMajorBusinessCategory("");
		erpLog.setModifyId(GlobalConstant.NULL_DEFAUL_VALUE);
		erpLog.setModifyName("");
		erpLog.setModifyTime(tranceDate);
		erpLog.setOperatingLife("");
		erpLog.setOrganizationId("");
		erpLog.setRegisteredAddress("");
		erpLog.setRegisteredAddressCity("");
		erpLog.setRegisteredAddressCounty("");
		erpLog.setRegisteredAddressDetail("");
		erpLog.setVendorNature("");
		erpLog.setVendorName(null != dto.getVendorName() ? dto.getVendorName() : "");
		erpLog.setVendorCompanyType("");
		erpLog.setVendorCode(null != dto.getVendorCode() ? dto.getVendorCode() : "");
		erpLog.setTaxManId(null != dto.getTaxpayerIDnumber() ? dto.getTaxpayerIDnumber() : "");
		erpLog.setStatus(dto.getStatus());
		erpLog.setSigningBankCard(null != dto.getFinancialAccount() ? dto.getFinancialAccount() : "");
		erpLog.setSigningBank(null != dto.getDepositBank() ? dto.getDepositBank() : "");
		return erpLog;
	}

	private YijifuCorporateDTO tranceToYijifuDto(ErpSellerupDTO dto) {
		YijifuCorporateDTO payDto = new YijifuCorporateDTO();
		payDto.setComName(null != dto.getVendorName() ? dto.getVendorName() : "");
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		LegalPerson legalPerson = new LegalPerson();
		legalPerson
				.setAddress(null != dto.getBusinessAddressDetailAddress() ? dto.getBusinessAddressDetailAddress() : "");
		legalPerson.setCertNo("");
		legalPerson.setEmail(null != dto.getContactEmail() ? dto.getContactEmail() : "");
		legalPerson.setMobileNo(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		legalPerson.setRealName(null != dto.getCompanyLeagalPersion() ? dto.getCompanyLeagalPersion() : "");
		payDto.setLegalPerson(legalPerson);
		payDto.setLicenceNo(dto.getBusinessLicenseCode());
		payDto.setEmail(null != dto.getContactEmail() ? dto.getContactEmail() : "");
		payDto.setMobileNo(null != dto.getContactMobile() ? dto.getContactMobile() : "");
		payDto.setOrganizationCode("");
		payDto.setOutUserId(null != dto.getMemberCenterCode() ? dto.getMemberCenterCode() : "");
		payDto.setTaxAuthorityNo(null != dto.getTaxpayerIDnumber() ? dto.getTaxpayerIDnumber() : "");
		payDto.setVerifyRealName(GlobalConstant.REAL_NAME_NO);
		return payDto;
	}

	/**
	 * 保存归属关系
	 * 
	 * @param memberBaseDto
	 * @return
	 */
	private boolean saveBelongAndBoxRelation(MemberBaseInfoDTO memberBaseDto) {
		memberBaseDto.setCurBelongSellerId(memberBaseDto.getId());
		memberBaseDto.setBelongSellerId(memberBaseDto.getId());
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		belongRelationshipDto.setMemberId(memberBaseDto.getId());
		String managerCode = memberBaseDto.getCompanyCode() + "9999";
		memberBaseDto.setBelongManagerId(managerCode);
		memberBaseDto.setCurBelongManagerId(managerCode);
		belongRelationshipDto.setCurBelongManagerId(managerCode);
		belongRelationshipDto.setCurBelongSellerId(memberBaseDto.getBelongSellerId());
		belongRelationshipDto.setBelongManagerId(managerCode);
		belongRelationshipDto.setBelongSellerId(memberBaseDto.getBelongSellerId());
		belongRelationshipDto.setModifyId(0L);
		belongRelationshipDto.setModifyName("内部供应商上行");
		belongRelationshipDto.setBuyerFeature("16");// 默认电商类型
		belongRelationshipDto.setVerifyStatus("3");
		belongRelationshipDAO.insertBelongInfo(belongRelationshipDto);

		/**
		 * ApplyBusiRelationDTO applyBusiRelationDto = new
		 * ApplyBusiRelationDTO(); applyBusiRelationDto.setAuditStatus("1");
		 * applyBusiRelationDto.setMemberId(memberBaseDto.getId());
		 * applyBusiRelationDto.setSellerId(memberBaseDto.getBelongSellerId());
		 * applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
		 * applyBusiRelationDto.setCreateId(0L);
		 * applyBusiRelationDto.setCustomerManagerId(managerCode);
		 * applyBusiRelationDto.setCreateName("内部供应商上行");
		 * applyBusiRelationDto.setModifyId(0L);
		 * applyBusiRelationDto.setModifyName("内部供应商上行");
		 * verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);
		 */

		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		memberBusinessRelationDTO.setBuyerId(memberBaseDto.getId().toString());
		memberBusinessRelationDTO.setSellerId(memberBaseDto.getBelongSellerId().toString());
		//memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
		memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.SUCCESS.getValue());//默认成功，但实际上并未下行到erp
		memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
		memberBusinessRelationDTO.setCreateId("0");
		memberBusinessRelationDTO.setModifyId("0");
		memberBusinessRelationDTO.setCreateName("内部供应商上行");
		memberBusinessRelationDTO.setModifyName("内部供应商上行");
		memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

		// erpSellerupDAO.updateMemberInfo(memberBaseDto);

		MemberStatusInfo statusInfo = new MemberStatusInfo();// 审核状态信息
		java.util.Date date = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate;
		try {
			trancDate = new Date(format.parse(format.format(date)).getTime());
		} catch (ParseException e) {
			trancDate = null;
			e.printStackTrace();
		}
		statusInfo.setModifyId(0L);
		statusInfo.setModifyName("内部供应商上行");
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(memberBaseDto.getId());
		statusInfo.setSyncErrorMsg("");
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setVerifyId(0L);
		//statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		statusInfo.setVerifyStatus(ErpStatusEnum.SUCCESS.getValue());//因为是erp上行而来，故默认下行成功
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
		statusInfo.setCreateId(0L);
		statusInfo.setCreateName("内部供应商上行");
		statusInfo.setCreateTime(trancDate);
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);

		return true;
	}

	/**
	 * 
	 * @param memberBaseDto
	 * @return
	 * @throws ParseException
	 */
	private boolean addDownErpSuccessStatus(MemberBaseInfoDTO memberBaseDto) {
		MemberStatusInfo statusInfo = new MemberStatusInfo();// 运营商审核状态信息
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			statusInfo.setVerifyTime(trancDate);
			statusInfo.setModifyTime(trancDate);
			statusInfo.setCreateTime(trancDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		statusInfo.setSyncErrorMsg("");
		statusInfo.setModifyId(0L);
		statusInfo.setVerifyId(0L);
		statusInfo.setModifyName("内部供应商上行");
		statusInfo.setMemberId(memberBaseDto.getId());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setCreateId(0L);
		statusInfo.setCreateName("内部供应商上行");

		// 插入erp下行成功状态
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
		statusInfo.setVerifyStatus(ErpStatusEnum.SUCCESS.getValue());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

	/**
	 * 更新供应商信息
	 * 
	 * @param dto
	 * @return
	 */
	private boolean updateVendorInfo(ErpSellerupDTO dto) {
		Date registTime = null;
		if (dto.getCreateTime() != null) {
			try {
				registTime = new Date();
				registTime = DateUtils.parse(dto.getCreateTime(), "yyyy-MM-dd");
			} catch (Exception e) {
				registTime = null;
			}
		}
		dto.setRegistTime(registTime);
		erpSellerupDAO.updateMemberBaseInfo(dto);
		if (StringUtils.isEmpty(dto.getAccountNo())) {
			YijifuCorporateDTO yijifuDto = tranceToYijifuDto(dto);
			YijifuCorporateCallBackDTO yijifuCallbackDto = payInfoService.realNameSaveVerify(yijifuDto).getResult();
			dto.setAccountNo(yijifuCallbackDto.getAccountNo());
			dto.setRealNameStatus("3");// 统一改成已实名
		} else {
			YijifuCorporateCallBackDTO modifyPayInfo = downToYijifuModify(dto);
			if (null == modifyPayInfo.getResultCode() || !"EXECUTE_SUCCESS".equals(modifyPayInfo.getResultCode())) {
				logger.error(modifyPayInfo.getResultDetail() + modifyPayInfo.getResultMessage());
				throw new RuntimeException("更新失败,公司编码：" + dto.getVendorCode());
			}
		}
		erpSellerupDAO.updateCompanyInfo(dto);
		erpSellerupDAO.updateInvoiceInfo(dto);
		MemberOutsideSupplierCompanyDTO outCompany = new MemberOutsideSupplierCompanyDTO();
		outCompany.setMemberId(dto.getMemberId());
		outCompany.setTaxManId(null != dto.getTaxpayerIDnumber() ? dto.getTaxpayerIDnumber() : "");
		outCompany.setBusinessLicenseId(dto.getBusinessLicenseCode());
		applyRelationshipDao.updateMemberLicenceInfo(outCompany);
		// erpSellerupDAO.updateMemberLicenceInfo(dto);
		return true;
	}

	/**
	 * 修改实名信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModify(ErpSellerupDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getMemberId());
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getMemberId(),
				GlobalConstant.IS_SELLER);
		if (null != dto.getVendorName() && !dto.getVendorName().equals(baseInfo.getCompanyName())) {
			payDto.setComName(dto.getVendorName());
		}
		if (null != dto.getContactMobile() && !dto.getContactMobile().equals(baseInfo.getArtificialPersonMobile())) {
			payDto.setMobileNo(dto.getContactMobile());
		}
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		if (null != dto.getBusinessLicenseCode()
				&& !dto.getBusinessLicenseCode().equals(licenceInfo.getBusinessLicenseId())) {
			payDto.setLicenceNo(dto.getBusinessLicenseCode());
		}
		if (null != dto.getTaxpayerIDnumber() && !dto.getTaxpayerIDnumber().equals(licenceInfo.getTaxManId())) {
			payDto.setTaxAuthorityNo(dto.getTaxpayerIDnumber());
		}
		payDto.setUserId(baseInfo.getAccountNo());
		if (null != dto.getContactName() && !dto.getContactName().equals(baseInfo.getArtificialPersonName())) {
			payDto.setLegalPersonName(dto.getContactName());
		}
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}
}

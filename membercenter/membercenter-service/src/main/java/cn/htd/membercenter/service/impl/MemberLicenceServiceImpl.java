package cn.htd.membercenter.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.ExecuteResult;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.common.util.DateUtils;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseInfoDao;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberCompanyInfoDao;
import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dao.MemberLicenceInfoDao;
import cn.htd.membercenter.dao.MemberStatusDao;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoVO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.membercenter.service.MemberLicenceService;
import cn.htd.membercenter.service.PayInfoService;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.CustomerService;
import cn.htd.usercenter.service.UserExportService;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberLicenceServiceImpl
 * </p>
 * 
 * @author root
 * @date 2016年11月26日
 *       <p>
 *       Description: 非会员转会员 、密码找回、手机号更改 审核功能相关接口
 *       </p>
 */
@Service("memberLicenceService")
@SuppressWarnings("all")
public class MemberLicenceServiceImpl implements MemberLicenceService {
	private final static Logger logger = LoggerFactory.getLogger(MemberLicenceServiceImpl.class);

	@Resource
	private MemberStatusDao memberStatusDao;
	@Resource
	private MemberBaseInfoDao memberBaseInfoDao;
	@Resource
	private MemberCompanyInfoDao memberCompanyInfoDao;
	@Resource
	private MemberLicenceInfoDao memberLicenceInfoDao;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private BelongRelationshipDAO belongRelationshipDAO;
	@Resource
	private MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Resource
	private MemberVerifySaveDAO verifyInfoDAO;

	@Autowired
	private SendSmsEmailService sendSmsEmailService;

	@Autowired
	private PayInfoService payInfoService;

	@Resource
	private CustomerService customerService;

	@Resource
	private UserExportService userExportService;

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Resource
	private MemberGradeService memberGradeService;

	@Resource
	private TransactionRelationService transactionRelationService;

	@Override
	public ExecuteResult<Boolean> verifyNonMemberToMember(MemberUncheckedDetailDTO dto) {
		logger.debug("--------------------非会员转会员审核---------------------");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberBaseInfoById(dto.getMember_id(),
					GlobalConstant.IS_BUYER);
			if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(dto.getVerify_status())) {
				// 调用支付中间件接口生成会员支付账号
				YijifuCorporateCallBackDTO callback = downToYijifu(dto);
				if (null == callback.getResultCode() || !"EXECUTE_SUCCESS".equals(callback.getResultCode())) {
					String detail = callback.getResultDetail();
					if (null != detail && (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
						rs.addErrorMessage("请输入正确身份证号");
					} else
						if (null != callback.getResultMessage() && (callback.getResultMessage().indexOf("certNo") >= 0
								|| callback.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
						rs.addErrorMessage("请输入正确身份证号");
					} else {
						rs.addErrorMessage(detail);
					}
					rs.setResultMessage("fail");
					rs.setResult(false);
					return rs;
				}
				MemberBaseInfoDTO baseDto = new MemberBaseInfoDTO();
				baseDto.setRealNameStatus("1");// 统一改成未实名
				baseDto.setAccountNo(null != callback.getAccountNo() ? callback.getAccountNo() : "0");
				baseDto.setId(dto.getMember_id());
				baseDto.setModifyId(Long.valueOf(dto.getModify_id()));
				baseDto.setModifyName(dto.getModify_name());
				Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
						"yyyy-MM-dd HH:mm:ss");
				baseDto.setModifyTime(tranceDate);
				baseDto.setBuyerSellerType(GlobalConstant.IS_BUYER);
				baseDto.setIndustryCategory(dto.getIndustry_category());
				updateUncheckMember(baseDto);

				// 生成用户
				CustomerDTO customerDTO = new CustomerDTO();
				ExecuteResult<UserDTO> user = userExportService.queryUserByLoginId(memberBase.getMemberCode());
				if (user.getResult() != null) {
					customerDTO.setUserId(user.getResult().getId());
					customerDTO.setLoginId(memberBase.getMemberCode());
					customerDTO.setMobile(memberBase.getArtificialPersonMobile());
					customerDTO.setPassword(memberBase.getArtificialPersonMobile());
					customerDTO.setName(memberBase.getCompanyName());
					customerDTO.setIsVmsInnerUser(false);
					customerDTO.setCompanyId(dto.getMember_id());
					customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
					customerService.editCustomer(customerDTO, Long.valueOf(dto.getModify_id()));
					// 更新最终更新时间
					memberCompanyInfoDao.updateCompanyTime(memberBase.getMemberCode());
				} else {
					customerDTO.setLoginId(memberBase.getMemberCode());
					customerDTO.setMobile(memberBase.getArtificialPersonMobile());
					customerDTO.setPassword(memberBase.getArtificialPersonMobile());
					customerDTO.setName(memberBase.getCompanyName());
					customerDTO.setIsVmsInnerUser(false);
					customerDTO.setCompanyId(dto.getMember_id());
					customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
					customerService.addCustomer(customerDTO, Long.valueOf(dto.getModify_id()));
				}

				BuyerGradeInfoDTO memberGradeModel = new BuyerGradeInfoDTO();
				memberGradeModel.setBuyerId(dto.getMember_id());
				memberGradeModel.setBuyerGrade("1");
				memberGradeModel.setPointGrade(1l);
				memberGradeModel.setCreateId(Long.valueOf(dto.getModify_id()));
				memberGradeModel.setCreateName(dto.getModify_name());
				memberGradeModel.setModifyId(Long.valueOf(dto.getModify_id()));
				memberGradeModel.setModifyName(dto.getModify_name());
				memberGradeDAO.deleteGrade(dto.getMember_id());
				memberGradeService.insertGrade(memberGradeModel);

				String smsType = "205";
				SendSmsDTO sendSms = new SendSmsDTO();
				sendSms.setPhone(memberBase.getArtificialPersonMobile());
				sendSms.setSmsType(smsType);
				sendSmsEmailService.sendSms(sendSms);

				// 关联交易名单
				TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
				transactionRelationDTO.setBuyerName(memberBase.getCompanyName());
				ExecuteResult<TransactionRelationDTO> executeResult = transactionRelationService
						.getSingleTransactionRelationByParams(transactionRelationDTO);
				if (executeResult.getResult() != null) {
					TransactionRelationDTO transactionRelation = executeResult.getResult();
					transactionRelationDTO.setId(transactionRelation.getId());
					transactionRelationDTO.setBuyerCode(memberBase.getMemberCode());
					transactionRelationDTO.setIsExist("1");//1.true 0.false
					transactionRelationDTO.setModifyId(Long.valueOf(dto.getModify_id())+"");
					transactionRelationDTO.setModifyName(dto.getModify_name());
					transactionRelationDTO.setModifyTime(new Date());
					transactionRelationService.updateTransactionRelation(transactionRelationDTO);
				}
			} else if ("4".equals(dto.getVerify_status())) {
				MemberBaseInfoDTO baseDto = new MemberBaseInfoDTO();
				baseDto.setId(dto.getMember_id());
				baseDto.setModifyId(Long.valueOf(dto.getModify_id()));
				baseDto.setModifyName(dto.getModify_name());
				baseDto.setArtificialPersonMobile(memberBase.getArtificialPersonMobile() + "终止");
				baseDto.setBuyerSellerType(GlobalConstant.IS_BUYER);
				memberBaseOperationDAO.updateMemberCompanyInfo(baseDto);
			}

			// 插入修改参数
			insertVerifyDetailInfoUtil(dto, "14");
			memberLicenceInfoDao.updateMemberStatusInfo(dto);

			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberLicenceServiceImpl -  verifyNonMemberToMember】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	private boolean insertVerifyDetailInfoUtil(MemberUncheckedDetailDTO dto, String modifyType) {
		try {
			MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getMember_id(),
					GlobalConstant.IS_BUYER);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			Date defaultDate = format.parse("0000-00-00 00:00:00");
			VerifyInfo verufyInfo = new VerifyInfo();// 审核信息
			verufyInfo.setCreateId(Long.valueOf(dto.getModify_id()));
			verufyInfo.setCreateName(dto.getModify_name());
			verufyInfo.setCreateTime(trancDate);
			verufyInfo.setModifyId(Long.valueOf(dto.getModify_id()));
			verufyInfo.setModifyName(dto.getModify_name());
			verufyInfo.setModifyTime(trancDate);
			verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
			verufyInfo.setRecordId(dto.getMember_id());
			verufyInfo.setRemark(dto.getSync_error_msg());
			verufyInfo.setVerifierId(Long.valueOf(dto.getModify_id()));
			verufyInfo.setVerifierName(dto.getModify_name());
			verufyInfo.setVerifyTime(trancDate);
			verufyInfo.setVerifyStatus(dto.getVerify_status());
			verufyInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
			verufyInfo.setBelongSellerId(oldDto.getCurBelongSellerId());
			memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
			dto.setVerify_id(verufyInfo.getId());

			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
			VerifyDetailInfo verifyDetailInfo = new VerifyDetailInfo();
			verifyDetailInfo.setVerifyId(verufyInfo.getVerifierId());
			verifyDetailInfo.setModifyType(modifyType);
			verifyDetailInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
			verifyDetailInfo.setRecordId(dto.getMember_id());
			verifyDetailInfo.setContentName("非会员转会员审核");
			verifyDetailInfo.setChangeTableId("member_status_info");
			verifyDetailInfo.setChangeFieldId("verify_status");
			verifyDetailInfo.setBeforeChange("1");
			verifyDetailInfo.setBeforeChangeDesc("待审核");
			verifyDetailInfo.setOperatorId(dto.getMember_id());
			verifyDetailInfo.setOperatorName(dto.getModify_name());
			verifyDetailInfo.setOperateTime(trancDate);
			if ("2".equalsIgnoreCase(dto.getVerify_status())) {
				verifyDetailInfo.setAfterChange("2");
				verifyDetailInfo.setAfterChangeDesc("审核通过");
				verDtoList.add(verifyDetailInfo);
			} else if ("3".equalsIgnoreCase(dto.getVerify_status())) {
				verifyDetailInfo.setAfterChange("3");
				verifyDetailInfo.setAfterChangeDesc("审核驳回");
				verDtoList.add(verifyDetailInfo);

			} else if ("4".equalsIgnoreCase(dto.getVerify_status())) {
				verifyDetailInfo.setAfterChange("4");
				verifyDetailInfo.setAfterChangeDesc("审核终止");
				verDtoList.add(verifyDetailInfo);

			}
			memberBaseOperationDAO.insertVerifyInfo(verDtoList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 更改公司信息和会员基本信息
	 * 
	 * @param dto
	 * @return
	 */
	private boolean updateUncheckMember(MemberBaseInfoDTO dto) {
		dto.setCanMallLogin(GlobalConstant.FLAG_YES);
		dto.setStatus(GlobalConstant.STATUS_YES);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate;
		try {
			trancDate = format.parse(format.format(date));
			dto.setBecomeMemberTime(trancDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		dto.setBuyerSellerType("1");
		memberBaseOperationDAO.updateMemberIsvalid(dto);
		memberBaseOperationDAO.updateMemberCompanyInfo(dto);
		return true;
	}

	@Override
	public ExecuteResult<Boolean> verifyPasswordRecovery(MemberLicenceInfoDetailDTO dto) {
		logger.debug("--------------------密码找回审核---------------------");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		String smsType = "";

		try {

			if (dto.getVerify_status().equals("2")) {
				ExecuteResult<Boolean> custRs = userExportService.memberPasswdReset(dto.getMember_code(),
						dto.getArtificial_person_mobile(), Long.valueOf(dto.getModify_id()));
				if (!custRs.isSuccess()) {
					rs.setResult(false);
					rs.addErrorMessage("用户更改密码失败");
					return rs;
				} else {
					// 更新最终更新时间
					memberCompanyInfoDao.updateCompanyTime(dto.getMember_code());
				}
			}

			/**
			 * // 变更 1 if (dto.getInfo_type().equalsIgnoreCase("1") &&
			 * dto.getVerify_status().equals("2")) {
			 * 
			 * 
			 * // 公司名称修改时，调用支付中间件接口同步会员修改信息 YijifuCorporateCallBackDTO
			 * modifyPayInfo = downPasswordToYijifuModify(dto); if (null ==
			 * modifyPayInfo.getResultCode() ||
			 * !"EXECUTE_SUCCESS".equals(modifyPayInfo.getResultCode())) {
			 * String detail = modifyPayInfo.getResultDetail(); if (null !=
			 * detail && (detail.indexOf("certNo") >= 0 ||
			 * detail.indexOf("legalPersonCertNo") >= 0)) {
			 * rs.addErrorMessage("请输入正确身份证号"); } else if (null !=
			 * modifyPayInfo.getResultMessage() &&
			 * (modifyPayInfo.getResultMessage().indexOf("certNo") >= 0 ||
			 * modifyPayInfo.getResultMessage().indexOf("legalPersonCertNo") >
			 * 0)) { rs.addErrorMessage("请输入正确身份证号"); } else {
			 * rs.addErrorMessage(detail); } rs.setResultMessage("fail");
			 * rs.setResult(false); return rs; }
			 * 
			 * // 手机号 MemberBaseInfoVO mbase = new MemberBaseInfoVO();
			 * mbase.setId(new Long(dto.getMember_id())); //
			 * mbase.setContact_mobile(dto.getContact_mobile_new());
			 * mbase.setModify_id(Integer.parseInt(dto.getModify_id()));
			 * mbase.setModify_name(dto.getModify_name());
			 * mbase.setModify_time(dto.getModify_time());
			 * memberBaseInfoDao.updateMemberBaseInfo(mbase); // 公司名称 法人
			 * MemberCompanyInfoVO company = new MemberCompanyInfoVO();
			 * company.setMember_id(dto.getMember_id());
			 * company.setArtificial_person_name(dto.
			 * getArtificial_person_name_new()); //
			 * company.setArtificial_person_mobile(dto.
			 * getArtificial_person_mobile_new());
			 * company.setCompany_name(dto.getCompany_name_new());
			 * company.setModify_id(dto.getModify_id());
			 * company.setModify_name(dto.getModify_name());
			 * company.setModify_time(dto.getModify_time());
			 * memberCompanyInfoDao.updateMemberCompanyInfoList(company);
			 * 
			 * 
			 * } // 营业执照 担保证明 MemberLicenceInfoVO licence = new
			 * MemberLicenceInfoVO(); licence.setMember_id(new
			 * Long(dto.getMember_id())); //
			 * licence.setBuyer_business_license_pic_src(dto.
			 * getBuyer_business_license_pic_src_new());
			 * licence.setBuyer_guarantee_license_pic_src(dto.
			 * getBuyer_guarantee_license_pic_src_new());
			 * licence.setModify_id(Long.parseLong(dto.getModify_id()));
			 * licence.setModify_name(dto.getModify_name());
			 * licence.setModify_time(dto.getModify_time());
			 * memberLicenceInfoDao.updateMemberLicenceInfo(licence);
			 */
			if (dto.getInfo_type().equalsIgnoreCase("1")) {
				MemberLicenceInfoDetailDTO detail = memberStatusDao
						.queryPasswordRecoveryVerifyDetail(new Long(dto.getMember_id()), "1");
				insertVerifyDetailInfoUtil2(detail, dto, "1");
			} else if (dto.getInfo_type().equalsIgnoreCase("2")) {
				MemberLicenceInfoDetailDTO detail = memberStatusDao
						.queryPasswordRecoveryVerifyDetail(new Long(dto.getMember_id()), "2");
				insertVerifyDetailInfoUtil2(detail, dto, "2");
			}

			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");

			List<String> param = new ArrayList<String>();
			param.add(dto.getArtificial_person_mobile());
			SendSmsDTO sendSms = new SendSmsDTO();
			if (dto.getVerify_status().equals("2")) {
				smsType = "207";
				sendSms.setParameterList(param);
			} else {
				smsType = "208";
			}

			sendSms.setPhone(dto.getArtificial_person_mobile_new());
			sendSms.setSmsType(smsType);
			sendSmsEmailService.sendSms(sendSms);

			// 更新状态表
			MemberUncheckedDetailDTO dto2 = new MemberUncheckedDetailDTO();
			dto2.setStatus_id(dto.getStatus_id());
			dto2.setVerify_status(dto.getVerify_status());
			dto2.setModify_id(dto.getModify_id());
			dto2.setModify_name(dto.getModify_name());
			dto2.setModify_time(dto.getModify_time());
			dto2.setSync_error_msg(dto.getSync_error_msg());
			memberLicenceInfoDao.updateMemberStatusInfo(dto2);
			// modifyDownToErp(dto);
			// sendMemberSms(dto.getArtificial_person_mobile_new(), smsType);
		} catch (Exception e) {
			logger.error("执行方法【MemberLicenceServiceImpl -  verifyPasswordRecovery】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	private boolean insertVerifyDetailInfoUtil2(MemberLicenceInfoDetailDTO detail, MemberLicenceInfoDetailDTO dto,
			String modifyType) {
		try {
			// MemberUncheckedDetailDTO detail =
			// memberStatusDao.queryNonMemberToMemberDetail(dto.getMember_id());
			String modifyTypeVerify = "";
			String contenttemp = "";
			if ("1".equalsIgnoreCase(modifyType) || "2".equalsIgnoreCase(modifyType)) {
				contenttemp = "密码找回审核";
				modifyTypeVerify = GlobalConstant.INFO_TYPE_PASSWORD_LIST;
			} else if ("3".equalsIgnoreCase(modifyType)) {
				contenttemp = "手机号更改审核";
				modifyTypeVerify = GlobalConstant.INFO_TYPE_PHONE_LIST;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(new Date()));
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
			VerifyDetailInfo verifyDetailInfo = new VerifyDetailInfo();
			VerifyDetailInfo verifyDetailInfo2 = new VerifyDetailInfo();
			verifyDetailInfo.setVerifyId(new Long(detail.getVerify_id()));
			verifyDetailInfo.setModifyType(modifyTypeVerify);
			verifyDetailInfo.setContentName(contenttemp);
			verifyDetailInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
			verifyDetailInfo.setRecordId(new Long(dto.getMember_id()));
			verifyDetailInfo.setChangeTableId("member_status_info");
			verifyDetailInfo.setChangeFieldId("verify_status");
			verifyDetailInfo.setBeforeChange("1");
			verifyDetailInfo.setBeforeChangeDesc("待审核");
			verifyDetailInfo.setOperatorId(new Long(dto.getMember_id()));
			verifyDetailInfo.setOperatorName(dto.getModify_name());
			verifyDetailInfo.setOperateTime(trancDate);
			if ("2".equalsIgnoreCase(dto.getVerify_status())) {
				verifyDetailInfo.setAfterChange("2");
				verifyDetailInfo.setAfterChangeDesc("审核通过");
				verDtoList.add(verifyDetailInfo);
			}
			if ("3".equalsIgnoreCase(dto.getVerify_status())) {
				verifyDetailInfo.setAfterChange("3");
				verifyDetailInfo.setAfterChangeDesc("审核驳回");
				/**
				 * verifyDetailInfo2.setVerifyId(new
				 * Long(detail.getVerify_id()));
				 * verifyDetailInfo2.setModifyType(modifyTypeVerify);
				 * verifyDetailInfo2.setRecordType(1);
				 * verifyDetailInfo2.setRecordId(new Long(dto.getMember_id()));
				 * verifyDetailInfo2.setContentName(contenttemp);
				 * verifyDetailInfo2.setChangeTableId("member_status_info");
				 * verifyDetailInfo2.setChangeFieldId("sync_error_msg");
				 * verifyDetailInfo2.setBeforeChange("");
				 * verifyDetailInfo2.setAfterChange(dto.getSync_error_msg());
				 * verifyDetailInfo2.setOperatorId(new
				 * Long(dto.getMember_id()));
				 * verifyDetailInfo2.setOperatorName(dto.getModify_name());
				 * verifyDetailInfo2.setOperateTime(trancDate);
				 **/
				verDtoList.add(verifyDetailInfo);
				// verDtoList.add(verifyDetailInfo2);
			}
			memberBaseOperationDAO.insertVerifyInfo(verDtoList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ExecuteResult<Boolean> verifyPhoneChange(MemberLicenceInfoDetailDTO dto) {
		logger.debug("--------------------手机号更改审核---------------------");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();

		// TODO 6.调用基础中心接口向手机号码发送审核通过的短信
		try {
			if ("2".equals(dto.getVerify_status())) {// 审核通过
				// TODO 4.调用支付中间件接口同步会员修改信息（如有必要）
				downToYijifuModify(dto);
				// 5.调用用户中心接口通知保存新手机号码
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setMobile(dto.getArtificial_person_mobile_new());
				UserDTO user = userExportService.queryUserByLoginId(dto.getMember_code()).getResult();
				customerDTO.setUserId(user.getId());
				// 5.调用用户中心接口通知保存新手机号码
				customerService.editCustomerMobile(customerDTO, new Long(dto.getModify_id()));

				// 更新手机号
				MemberCompanyInfoVO company = new MemberCompanyInfoVO();
				company.setMember_id(dto.getMember_id());
				company.setArtificial_person_name(dto.getArtificial_person_name_new());
				company.setArtificial_person_mobile(dto.getArtificial_person_mobile_new());
				company.setCompany_name(dto.getCompany_name_new());
				company.setModify_id(dto.getModify_id());
				company.setModify_name(dto.getModify_name());
				company.setModify_time(dto.getModify_time());
				memberCompanyInfoDao.updateMemberCompanyInfoList(company);
			}
			// 更新状态表
			MemberUncheckedDetailDTO dto2 = new MemberUncheckedDetailDTO();
			dto2.setStatus_id(dto.getStatus_id());
			dto2.setVerify_status(dto.getVerify_status());
			dto2.setModify_id(dto.getModify_id());
			dto2.setModify_name(dto.getModify_name());
			dto2.setModify_time(dto.getModify_time());
			dto2.setSync_error_msg(dto.getSync_error_msg());
			memberLicenceInfoDao.updateMemberStatusInfo(dto2);

			// 插入修改参数
			MemberLicenceInfoDetailDTO detail = memberStatusDao
					.queryPhoneChangeVerifyDetail(new Long(dto.getMember_id()));
			insertVerifyDetailInfoUtil2(detail, dto, "3");
			String smsType = "";
			SendSmsDTO sendSms = new SendSmsDTO();
			if (dto.getVerify_status().equals("2")) {
				smsType = "211";
			} else {
				smsType = "212";
			}
			sendSms.setPhone(dto.getArtificial_person_mobile_new());
			sendSms.setSmsType(smsType);
			sendSmsEmailService.sendSms(sendSms);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberLicenceServiceImpl -  verifyPhoneChange】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 会员解除归属关系待审核
	 */
	@Override
	public ExecuteResult<Boolean> verifyRemoveRelationship(MemberRemoveRelationshipDTO dto) {
		logger.debug("--------------------会员解除归属关系待审核---------------------");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (dto.getMemberId() == null || dto.getBelongMemberId() == null || dto.getVerifyInfoId() == null
					|| dto.getModifyId() == null || dto.getModifyName() == null) {
				rs.addErrorMessage("会员id,所属商家id,审批表id,修改人id，修改人名称不能为空");
				throw new RuntimeException();
			}
			// dto.setOldSellerId(dto.getBelongMemberId());
			// 9审核通过,2审核驳回
			if ("3".equalsIgnoreCase(dto.getStatus())) {
				// 归属0801商家id 和归属经理
				List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO
						.getMemberInfoByCompanyCode(GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
				if (rsList != null && rsList.size() > 0) {
					Long belongSellerID = rsList.get(0).getId();
					dto.setBelongMemberId(belongSellerID);
				}
				// 获取当前客户经理iD
				// UserDTO user =
				// userExportService.queryUserByLoginId("08019999").getResult();
				// if (user != null) {
				dto.setCurBelongManagerId("08019999");
				// }
				// 更新基本信息表
				memberLicenceInfoDao.updateMemberBaseInfo(dto);
				// 查询是否是0801归属公司的，如果是执行，只修改审核状态
				String code = memberBaseOperationDAO.getMemberCompanyInfoCodeById(dto.getBelongMemberId());
				if (GlobalConstant.DEFAULT_MEMBER_COOPERATE.equals(code)) {
					dto.setStatus("2");
					// 更新审批表 审批详细信息表
					memberLicenceInfoDao.updateVerifyInfo(dto);
				} else {
					// 保存与默认平台公司的包厢经营关系
					dto.setStatus("9");// 归属删除
					// 更新归属关系表
					memberLicenceInfoDao.updateBelongRelationship(dto);
					dto.setStatus("2");
					// 更新审批表 审批详细信息表
					memberLicenceInfoDao.updateVerifyInfo(dto);
					saveBelongBoxRelation(dto);
				}
				// 更新审核状态
				updateMemberStatus(dto);
				// 插入操作记录
				insertVerifyDetail(dto);
				// 恢复成原来状态，防止执行下面判断
				dto.setStatus("3");
			}
			if ("2".equalsIgnoreCase(dto.getStatus())) {
				// 更新归属关系表
				// memberLicenceInfoDao.updateBelongRelationship(dto);
				dto.setStatus("3");
				// 更新审批表 审批详细信息表
				memberLicenceInfoDao.updateVerifyInfo(dto);
				// 更新审核状态
				updateMemberStatus(dto);
				// 插入操作记录
				insertVerifyDetail(dto);
			}
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberLicenceServiceImpl -  verifyRemoveRelationship】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 保存修改发票信息
	 */
	@Override
	public ExecuteResult<Boolean> updateMemberInvoiceInfo(MemberInvoiceDTO dto) {
		logger.debug("--------------------保存修改发票信息---------------------");
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (StringUtils.isEmpty(dto.getModifyId()) || StringUtils.isEmpty(dto.getModifyName())
					|| StringUtils.isEmpty(dto.getInvoiceId())) {
				rs.addErrorMessage("发票id,修改人id，修改人名称不能为空");
				throw new RuntimeException("发票id,修改人id，修改人名称不能为空");
			}
			// 更新发票信息
			memberLicenceInfoDao.updateMemberInvoiceInfo(dto);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberLicenceServiceImpl -  updateMemberInvoiceInfo】报错！{}", e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 生成支付账号
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifu(MemberUncheckedDetailDTO dto) {
		YijifuCorporateDTO payDto = new YijifuCorporateDTO();
		MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getMember_id());
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseById(dto.getMember_id(),
				GlobalConstant.IS_BUYER);
		payDto.setComName(baseInfo.getCompanyName());
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_INDIVIDUAL);
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setAddress(baseInfo.getLocationDetail());
		legalPerson.setCertNo(baseInfo.getArtificialPersonIdcard());
		legalPerson.setEmail(baseInfo.getContactEmail());
		legalPerson.setMobileNo(baseInfo.getArtificialPersonMobile());
		legalPerson.setRealName(baseInfo.getArtificialPersonName());
		payDto.setLegalPerson(legalPerson);
		payDto.setLicenceNo(licenceInfo.getBuyerBusinessLicenseId());
		payDto.setEmail(baseInfo.getContactEmail());
		payDto.setMobileNo(baseInfo.getArtificialPersonMobile());
		payDto.setOrganizationCode(licenceInfo.getOrganizationId());
		payDto.setOutUserId(baseInfo.getMemberCode());
		payDto.setTaxAuthorityNo(licenceInfo.getTaxManId());
		payDto.setVerifyRealName(GlobalConstant.REAL_NAME_NO);
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameSaveVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 修改实名信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModify(MemberLicenceInfoDetailDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(Long.valueOf(dto.getMember_id()),
				GlobalConstant.IS_BUYER);
		payDto.setMobileNo(dto.getArtificial_person_mobile_new());
		payDto.setUserId(baseInfo.getAccountNo());

		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 修改实名信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downPasswordToYijifuModify(MemberLicenceInfoDetailDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		payDto.setMobileNo(dto.getArtificial_person_mobile_new());
		MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberbaseById(Long.valueOf(dto.getMember_id()),
				GlobalConstant.IS_BUYER);
		if (null != memberBase && !memberBase.getArtificialPersonName().equals(dto.getArtificial_person_name_new())) {
			payDto.setLegalPersonName(dto.getArtificial_person_name_new());
			payDto.setUserId(memberBase.getAccountNo());

			ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
			return callBack.getResult();
		}
		return null;
	}

	/**
	 * 发送短信
	 * 
	 * @param dto
	 * @param smsType
	 */
	private void sendMemberSms(String phone, String smsType) {
		SendSmsDTO sendSms = new SendSmsDTO();
		sendSms.setPhone(phone);
		sendSms.setSmsType(smsType);
		sendSmsEmailService.sendSms(sendSms);
	}

	/**
	 * 保存归属关系与包厢关系
	 * 
	 * @param dto
	 * @return
	 */
	private boolean saveBelongBoxRelation(MemberRemoveRelationshipDTO dto) {
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		belongRelationshipDto.setCurBelongManagerId(dto.getCurBelongManagerId());
		belongRelationshipDto.setCurBelongSellerId(dto.getBelongMemberId());
		belongRelationshipDto.setBelongSellerId(dto.getBelongMemberId());
		memberBusinessRelationDTO.setSellerId(dto.getBelongMemberId().toString());
		belongRelationshipDto.setMemberId(dto.getMemberId());
		belongRelationshipDto.setBelongManagerId(dto.getCurBelongManagerId());

		belongRelationshipDto.setModifyId(Long.valueOf(dto.getModifyId()));
		belongRelationshipDto.setModifyName(dto.getModifyName());
		belongRelationshipDto.setBuyerFeature("16");// 默认电商类型
		belongRelationshipDto.setVerifyStatus("3");
		belongRelationshipDAO.insertBelongInfo(belongRelationshipDto);

		ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
		applyBusiRelationDto.setAuditStatus("1");
		applyBusiRelationDto.setMemberId(dto.getMemberId());
		applyBusiRelationDto.setSellerId(dto.getBelongMemberId());
		applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
		applyBusiRelationDto.setCreateId(Long.valueOf(dto.getModifyId()));
		applyBusiRelationDto.setCustomerManagerId(dto.getCurBelongManagerId());
		applyBusiRelationDto.setCreateName(dto.getModifyName());
		applyBusiRelationDto.setModifyId(Long.valueOf(dto.getModifyId()));
		applyBusiRelationDto.setModifyName(dto.getModifyName());
		verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);

		memberBusinessRelationDTO.setBuyerId(dto.getMemberId().toString());
		memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
		memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
		memberBusinessRelationDTO.setCreateId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setModifyId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setCreateName(dto.getModifyName());
		memberBusinessRelationDTO.setModifyName(dto.getModifyName());
		memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

		MemberBaseInfoDTO memberBase = new MemberBaseInfoDTO();
		memberBase.setId(dto.getMemberId());
		memberBase.setModifyId(dto.getModifyId());
		memberBase.setModifyName(dto.getModifyName());
		memberBase.setBelongManagerId(dto.getCurBelongManagerId());
		memberBase.setCurBelongManagerId(dto.getCurBelongManagerId());
		memberBase.setCurBelongSellerId(dto.getBelongMemberId());
		memberBase.setBelongSellerId(dto.getBelongMemberId());
		memberBaseOperationDAO.updateMemberIsvalid(memberBase);

		return true;
	}

	/**
	 * 更新会员状态信息表
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean updateMemberStatus(MemberRemoveRelationshipDTO dto) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));

		// 更新会员运营审核状态
		MemberStatusInfo statusInfo = new MemberStatusInfo();// 运营商审核状态信息
		statusInfo.setSyncErrorMsg(dto.getRemark());
		statusInfo.setVerifyStatus(dto.getStatus());
		statusInfo.setModifyId(dto.getModifyId());
		statusInfo.setModifyName(dto.getModifyName());
		statusInfo.setSyncKey("");
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(dto.getMemberId());
		statusInfo.setVerifyId(dto.getVerifyInfoId());
		statusInfo.setInfoType(GlobalConstant.REMOVE_RELATION_VERIFY);
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setModifyTime(trancDate);
		statusInfo.setCreateId(dto.getModifyId());
		statusInfo.setCreateName(dto.getModifyName());
		statusInfo.setCreateTime(trancDate);
		memberBaseOperationDAO.saveMemberStatusInfo(statusInfo);

		if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(dto.getStatus())) {// 增加会员修改下行ERP状态
			statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
			statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
			statusInfo.setSyncKey(KeygenGenerator.getUidKey());
			memberBaseOperationDAO.deleteMemberStatus(statusInfo);
			memberBaseOperationDAO.insertMemberStatus(statusInfo);
		}

		return true;
	}

	/**
	 * 记录操作详细记录
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean insertVerifyDetail(MemberRemoveRelationshipDTO dto) throws ParseException {
		List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
		VerifyDetailInfo verDto = new VerifyDetailInfo();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		String afterChangeDesc = "";
		String beforeChangeDesc = "";
		if ("2".equals(dto.getStatus())) {
			afterChangeDesc = "审核通过";
		} else {
			beforeChangeDesc = "审核驳回";
		}
		verDto.setAfterChangeDesc(afterChangeDesc);
		verDto.setBeforeChangeDesc("待审核");
		verDto.setAfterChange(dto.getStatus());
		verDto.setBeforeChange("1");
		verDto.setChangeFieldId("STATUS");
		verDto.setChangeTableId("MEMBER_BASE_INFO");
		verDto.setContentName("会员解除归属审核");
		verDto.setOperateTime(trancDate);
		verDto.setOperatorId(dto.getModifyId());
		verDto.setOperatorName(dto.getModifyName());
		verDto.setVerifyId(dto.getVerifyInfoId());
		verDto.setRecordId(dto.getMemberId());
		verDto.setModifyType(GlobalConstant.REMOVE_RELATION_VERIFY);
		verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
		verDtoList.add(verDto);
		memberBaseOperationDAO.insertVerifyInfo(verDtoList);// 插入履历信息
		return true;
	}

	/**
	 * 修改下行
	 * 
	 * @param dto
	 * @return
	 */
	private boolean modifyDownToErp(MemberLicenceInfoDetailDTO dto) {
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
		statusInfo.setModifyId(Long.valueOf(dto.getModify_id()));
		statusInfo.setVerifyId(Long.valueOf(dto.getModify_id()));
		statusInfo.setModifyName(dto.getModify_name());
		statusInfo.setMemberId(Long.valueOf(dto.getMember_id()));
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setCreateId(Long.valueOf(dto.getModify_id()));
		statusInfo.setCreateName(dto.getModify_name());

		// 插入erp下行状态
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
		statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

}

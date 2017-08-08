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

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.ExecuteResult;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberVerifySaveService;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.CustomerService;
import cn.htd.usercenter.service.UserExportService;

@Service("memberVerifySaveService")
public class MemberVerifySaveServiceImpl implements MemberVerifySaveService {

	private final static Logger logger = LoggerFactory.getLogger(MemberVerifySaveServiceImpl.class);
	@Resource
	MemberVerifySaveDAO verifyInfoDAO = null;
	@Autowired
	MemberBaseInfoService memberBaseInfoService;
	@Resource
	BelongRelationshipDAO belongRelationshipDAO;
	@Resource
	MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	MemberBusinessRelationDAO memberBusinessRelationDAO;
	@Autowired
	private UserExportService userExportService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	SendSmsEmailService sendSmsEmailService;

	@Resource
	private TransactionRelationService transactionRelationService;

	@Override
	public ExecuteResult<String> saveMemberVerifyInfo(ApplyBusiRelationDTO applyBusiRelationDto) {

		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {

			if (applyBusiRelationDto != null) {
				VerifyResultDTO verifyResultDTO = memberBaseOperationDAO.selectVerifyResult(
						applyBusiRelationDto.getMemberId(), GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
				if (null != verifyResultDTO
						&& verifyResultDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_WAIT)) {
					String smsType = "";
					Long memberId = applyBusiRelationDto.getMemberId();
					String verifyStatus = applyBusiRelationDto.getAuditStatus();
					Long createId = applyBusiRelationDto.getCreateId();
					String createName = applyBusiRelationDto.getCreateName();
					verifyInfoDAO.saveMemberVerifyInfo(memberId, verifyStatus, createId, createName);
					if (applyBusiRelationDto.getAuditStatus().equalsIgnoreCase(GlobalConstant.VERIFY_STATUS_ACCESS)) {
						// 修改审批状态
						verifyInfoDAO.saveMemberVerifyInfo(memberId, "2", applyBusiRelationDto.getModifyId(),
								applyBusiRelationDto.getModifyName());
						applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
						// 添加business_relationship(0,1,2)审核状态 0待审批，1审批通过，2驳回
						// (参考经营关系对象MemberBusinessRelationDTO设置的状态)
						// 不勾选品牌品类没有经营关系
						if (applyBusiRelationDto.getCategoryBrand().size() > 0) {
							applyBusiRelationDto.setAuditStatus("1");
							int size = applyBusiRelationDto.getCategoryBrand().size();
							CategoryBrandDTO categoryBrandDTO = null;
							for (int i = 0; i < size; i++) {
								categoryBrandDTO = applyBusiRelationDto.getCategoryBrand().get(i);
								applyBusiRelationDto.setBrandId(categoryBrandDTO.getBrandId());
								applyBusiRelationDto.setCategoryId(categoryBrandDTO.getCategoryId());
								verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);
							}
							applyBusiRelationDto.setAuditStatus("2");
						}
						smsType = GlobalConstant.SMSTYPE_VERIFY_SUPPLIER_SUCCESS;
						/**
						 * verifyInfoDAO
						 * .updateBelongRelationInfo(applyBusiRelationDto);
						 */
					}
					if (applyBusiRelationDto.getAuditStatus().equalsIgnoreCase(GlobalConstant.VERIFY_STATUS_REFUZE)) {

						// 修改审批状态
						verifyInfoDAO.saveMemberVerifyInfo(memberId, "3", applyBusiRelationDto.getModifyId(),
								applyBusiRelationDto.getModifyName());
								// 添加审核驳回 添加belong_relationship(1,2,3)审核通过状态
								// 2为驳回
								// applyBusiRelationDto.setAuditStatus("2");
								// verifyInfoDAO.updateBelongRelationInfo(applyBusiRelationDto);
								// applyBusiRelationDto.setAuditStatus("3");
								/**
								 * // 如果驳回则会员自动归入0801公司 if (result.getResult()
								 * != null) { Long curBelongSellerId = new
								 * Long(result.getResult() .getMemberId());
								 * verifyInfoDAO.updateBelongRelationRebut(
								 * memberId, curBelongSellerId,
								 * applyBusiRelationDto.getModifyId(),
								 * applyBusiRelationDto.getModifyName()); } else
								 * { rs.setResultMessage("没有检索到0801公司memberID");
								 * }
								 **/
						smsType = GlobalConstant.SMSTYPE_VERIFY_SUPPLIER_FAIL;
					}
					// goto.调用支付中间件接口生成会员支付账号，
					// 生成用户
					CustomerDTO customerDTO = new CustomerDTO();
					MemberBaseInfoDTO memberBase = memberBaseInfoService.getMemberDetailById(memberId).getResult()
							.getMemberBaseInfoDTO();
					ExecuteResult<UserDTO> user = userExportService.queryUserByLoginId(memberBase.getMemberCode());
					if (user.getResult() != null) {
						customerDTO.setLoginId(memberBase.getMemberCode());
						customerDTO.setMobile(memberBase.getArtificialPersonMobile());
						customerDTO.setPassword(memberBase.getArtificialPersonMobile());
						customerDTO.setName(memberBase.getCompanyName());
						customerDTO.setIsVmsInnerUser(false);
						customerDTO.setCompanyId(memberBase.getId());
						customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
						customerService.editCustomer(customerDTO, applyBusiRelationDto.getModifyId());
					} else {
						customerDTO.setLoginId(memberBase.getMemberCode());
						customerDTO.setMobile(memberBase.getArtificialPersonMobile());
						customerDTO.setPassword(memberBase.getArtificialPersonMobile());
						customerDTO.setName(memberBase.getCompanyName());
						customerDTO.setIsVmsInnerUser(false);
						customerDTO.setCompanyId(memberBase.getId());
						customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
						customerService.addCustomer(customerDTO, applyBusiRelationDto.getModifyId());
					}
					saveBelongBoxRelation(applyBusiRelationDto);

					// 修改memberStatus审批状态
					verifyInfoDAO.saveStatusInfoTypeByMemberId(applyBusiRelationDto.getVerifyId(), memberId,
							verifyStatus, applyBusiRelationDto.getModifyId(), applyBusiRelationDto.getModifyName());

					sendMemberSms(applyBusiRelationDto.getArtificialPersonMobile(), smsType);

					// 关联交易名单
					TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
					transactionRelationDTO.setBuyerName(memberBase.getCompanyName());
					ExecuteResult<TransactionRelation> executeResult = transactionRelationService
							.getSingleTransactionRelationByParams(transactionRelationDTO);
					if (executeResult.getResult() != null) {
						TransactionRelation transactionRelation = executeResult.getResult();
						transactionRelationDTO.setId(transactionRelation.getId());
						transactionRelationDTO.setBuyerCode(memberBase.getMemberCode());
						transactionRelationDTO.setIsExist(Boolean.TRUE);
						transactionRelationDTO.setModifyId(applyBusiRelationDto.getModifyId());
						transactionRelationDTO.setModifyName(applyBusiRelationDto.getModifyName());
						transactionRelationDTO.setModifyTime(new Date());
						transactionRelationService.updateTransactionRelation(transactionRelationDTO);
					}
					rs.setResultMessage("success");
				}

			}

		} catch (Exception e) {
			rs.addErrorMessage("执行方法【saveMemberVerifyInfo】报错：{}" + e.getMessage());
			logger.error("执行方法【saveMemberVerifyInfo】报错：{}", e.getMessage());
			rs.setResultMessage("error");
			throw new RuntimeException(e);

		}

		return rs;
	}

	/**
	 * 保存归属关系与包厢关系
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean saveBelongBoxRelation(ApplyBusiRelationDTO dto) throws ParseException {
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		MemberBaseInfoDTO memberBase = memberBaseInfoService.getMemberDetailById(dto.getMemberId()).getResult()
				.getMemberBaseInfoDTO();
		memberBase.setCanMallLogin(GlobalConstant.FLAG_YES);// 能登陆
		memberBase.setStatus(GlobalConstant.STATUS_YES);
		memberBase.setModifyId(dto.getModifyId());
		memberBase.setModifyName(dto.getModifyName());
		dto.setArtificialPersonMobile(memberBase.getArtificialPersonMobile());

		if (dto.getAuditStatus().equalsIgnoreCase(GlobalConstant.VERIFY_STATUS_ACCESS)) {

			belongRelationshipDto.setBelongManagerId(dto.getCustomerManagerId());
			belongRelationshipDto.setCurBelongManagerId(dto.getCustomerManagerId());

			belongRelationshipDto.setBuyerFeature(dto.getBuyerFeature());
			memberBase.setBelongManagerId(dto.getCustomerManagerId());
			memberBase.setCurBelongManagerId(dto.getCustomerManagerId());

			belongRelationshipDto.setCurBelongSellerId(memberBase.getBelongSellerId());
			belongRelationshipDto.setBelongSellerId(memberBase.getBelongSellerId());
			memberBusinessRelationDTO.setSellerId(memberBase.getBelongSellerId().toString());

		} else {
			// UserDTO user =
			// userExportService.queryUserByLoginId("08019999").getResult();
			List<MemberBaseInfoDTO> memberList = memberBaseInfoService
					.getMemberInfoByCompanyCode(GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER)
					.getResult();
			if (memberList.size() > 0) {
				Long memberId = memberList.get(0).getId();
				belongRelationshipDto.setBelongSellerId(memberId);
				belongRelationshipDto.setCurBelongSellerId(memberId);
				memberBusinessRelationDTO.setSellerId(memberId.toString());
				memberBase.setCurBelongSellerId(memberId);
				memberBase.setBelongSellerId(memberId);
				// memberBase.setBelongManagerId("08019999");
			} else {
				belongRelationshipDto.setBelongSellerId(0L);
				belongRelationshipDto.setBelongSellerId(0L);
				memberBusinessRelationDTO.setSellerId("0");
				memberBase.setCurBelongSellerId(0L);
			}
			belongRelationshipDto.setBuyerFeature("16");// 默认电商类型
			belongRelationshipDto.setCurBelongManagerId("08019999");
			belongRelationshipDto.setBelongManagerId("08019999");
			memberBase.setCurBelongManagerId("08019999");
			memberBase.setBelongManagerId("08019999");
		}
		addDownErpStatus(dto);

		saveVerifyInfo(dto);

		memberBase.setVerifyId(dto.getVerifyId());
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		memberBase.setBecomeMemberTime(trancDate);
		memberBaseOperationDAO.updateMemberIsvalid(memberBase);

		belongRelationshipDto.setMemberId(dto.getMemberId());
		belongRelationshipDto.setModifyId(dto.getModifyId());
		belongRelationshipDto.setModifyName(dto.getModifyName());
		belongRelationshipDto.setVerifyStatus("3");

		belongRelationshipDAO.insertBelongInfo(belongRelationshipDto);

		memberBusinessRelationDTO.setBuyerId(dto.getMemberId().toString());

		memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
		memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
		memberBusinessRelationDTO.setCreateId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setModifyId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setCreateName(dto.getModifyName());
		memberBusinessRelationDTO.setModifyName(dto.getModifyName());
		memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

		return true;
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
	 * 保存下行信息到erp状态
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean addDownErpStatus(ApplyBusiRelationDTO dto) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(new Date()));
		MemberStatusInfo statusInfo = new MemberStatusInfo();
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
		statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setSyncErrorMsg(null != dto.getRemark() ? dto.getRemark() : "");
		statusInfo.setModifyId(dto.getModifyId());
		statusInfo.setModifyName(dto.getModifyName());
		statusInfo.setMemberId(dto.getMemberId());
		statusInfo.setVerifyId(dto.getModifyId());
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setModifyTime(trancDate);
		statusInfo.setCreateId(dto.getModifyId());
		statusInfo.setCreateName(dto.getModifyName());
		statusInfo.setCreateTime(trancDate);
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

	/**
	 * 插入审批信息
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	public boolean saveVerifyInfo(ApplyBusiRelationDTO dto) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		VerifyInfo verufyInfo = new VerifyInfo();// 审核信息
		verufyInfo.setBelongSellerId(dto.getSellerId());
		verufyInfo.setCreateId(dto.getModifyId());
		verufyInfo.setCreateName(dto.getModifyName());
		verufyInfo.setCreateTime(trancDate);
		verufyInfo.setModifyId(dto.getModifyId());
		verufyInfo.setModifyName(dto.getModifyName());
		verufyInfo.setModifyTime(trancDate);
		verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
		verufyInfo.setRecordId(dto.getMemberId());
		verufyInfo.setRemark(dto.getRemark());
		verufyInfo.setVerifierId(dto.getModifyId());
		verufyInfo.setVerifierName(dto.getModifyName());
		verufyInfo.setVerifyTime(trancDate);
		verufyInfo.setVerifyStatus(dto.getAuditStatus());
		verufyInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
		memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
		dto.setVerifyId(verufyInfo.getId());
		return true;
	}
}

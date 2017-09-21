package cn.htd.membercenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberBuyerDao;
import cn.htd.membercenter.dao.MemberModifyVerifyDAO;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.dao.MyMemberDAO;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerFinanceDTO;
import cn.htd.membercenter.dto.MemberModifyDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.TableSqlDto;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberModifyVerifyService;
import cn.htd.membercenter.service.PayInfoService;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.UserExportService;

@Service("memberModifyVerifyService")
public class MemberModifyVerifyServiceImpl implements MemberModifyVerifyService {

	private final static Logger logger = LoggerFactory.getLogger(MemberModifyVerifyServiceImpl.class);

	@Resource
	private MemberModifyVerifyDAO memberModifyVerifyDAO;
	@Resource
	private MemberBuyerDao memberBuyerDao;
	@Autowired
	private PayInfoService payInfoService;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Resource
	private BelongRelationshipDAO belongRelationshipDAO;

	@Resource
	private MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Resource
	private MemberVerifySaveDAO verifyInfoDAO;

	@Resource
	private MyMemberDAO myMemberDAO;

	@Autowired
	private UserExportService userExportService;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Resource
	private TransactionRelationService transactionRelationService;

	/**
	 * 查询会员修改审核列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectMemberModifyVerify(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			Long count = memberModifyVerifyDAO.selectMemberModifyCount(memberBaseInfoDTO);
			if (count > 0) {
				List<MemberBaseInfoDTO> resList = memberModifyVerifyDAO.selectMemberModifyVerify(memberBaseInfoDTO,
						pager);
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberModifyVerifyServiceImpl----->selectMemberModifyVerify=" + e);
		}
		return rs;
	}

	/**
	 * 查询信息修改详情
	 */
	@Override
	public ExecuteResult<DataGrid<VerifyDetailInfoDTO>> selectModifyVerifyInfo(Long id, String infoType) {
		ExecuteResult<DataGrid<VerifyDetailInfoDTO>> rs = new ExecuteResult<DataGrid<VerifyDetailInfoDTO>>();
		DataGrid<VerifyDetailInfoDTO> dg = new DataGrid<VerifyDetailInfoDTO>();
		try {
			List<MemberStatusInfo> verifyInfo = memberModifyVerifyDAO.selectModifyVerify(id);// 查询审核id
			List<Long> verifyIds = new ArrayList<Long>();
			int size = verifyInfo.size();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Long verifyId = verifyInfo.get(i).getVerifyId();
					if (null != verifyId && infoType.equals(verifyInfo.get(i).getInfoType())) {
						verifyIds.add(verifyId);
					}
				}
				if (verifyIds.size() > 0) {// 通过审核ID查询审核修改信息履历
					Long count = memberModifyVerifyDAO.selectVerifyCountByVerifyIds(verifyIds);
					if (count > 0) {
						List<VerifyDetailInfoDTO> resList = memberModifyVerifyDAO.selectVerifyByVerifyIds(verifyIds);
						VerifyDetailInfoDTO resDto = null;
						MemberStatusInfo statusInfo = null;
						int resSize = resList.size();
						for (int i = 0; i < resSize; i++) {
							resDto = resList.get(i);
							for (int j = 0; j < size; j++) {
								statusInfo = verifyInfo.get(j);
								Long verifyId = statusInfo.getVerifyId();
								if (null != verifyId && verifyId.equals(resDto.getVerifyId())) {
									resDto.setVerifyStatus(statusInfo.getVerifyStatus());
								}
							}
						}
						dg.setRows(resList);
						dg.setTotal(count);
						rs.setResult(dg);
						rs.setResultMessage("success");
					} else {
						rs.setResultMessage("没有查询到数据");
					}
				} else {
					rs.setResult(dg);
					rs.setResultMessage("没有查询到数据");
				}
			}

		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberModifyVerifyServiceImpl----->selectModifyVerifyInfo=" + e);
		}
		return rs;
	}

	/**
	 * 保存会员修改审核
	 */
	@Override
	public ExecuteResult<Boolean> saveMemberModifyVerify(MemberModifyDTO memberModifyDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());

			MemberStatusInfo dto = new MemberStatusInfo();// 会员审核状态信息
			dto.setModifyId(memberModifyDTO.getOperatorId());
			dto.setModifyTime(trancDate);
			dto.setVerifyTime(trancDate);
			dto.setVerifyStatus(memberModifyDTO.getVerifyStatus());
			dto.setMemberId(memberModifyDTO.getMemberId());
			dto.setModifyName(memberModifyDTO.getOperatorName());
			dto.setSyncKey("");
			dto.setSyncErrorMsg(memberModifyDTO.getRemark());
			String companyName = "";
			String idCard = "";
			String realName = "";
			String buyerBusinessLicenseId = "";
			boolean isDownToYijifu = false;
			List<Long> verifyIds = new ArrayList<Long>();
			List<MemberStatusInfo> verifyInfo = memberModifyVerifyDAO.selectModifyVerify(memberModifyDTO.getMemberId());// 查询审核id
			MemberStatusInfo statusDto = null;
			if (verifyInfo.size() > 0) {
				for (int i = 0; i < verifyInfo.size(); i++) {
					statusDto = verifyInfo.get(i);
					if (memberModifyDTO.getInfoType().equals(statusDto.getInfoType())
							&& statusDto.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_WAIT)) {
						verifyIds.add(statusDto.getVerifyId());
					}
				}
			}
			// verifyIds.add(memberModifyDTO.getVerifyId());
			List<VerifyDetailInfoDTO> verifyList = memberModifyVerifyDAO.selectVerifyByVerifyIds(verifyIds);

			if (memberModifyDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_ACCESS)) {
				boolean tranceMemberType = false;
				int size = verifyList.size();
				if (size > 0) {
					List<TableSqlDto> sqlList = new ArrayList<TableSqlDto>();
					TableSqlDto sqlDto = null;
					VerifyDetailInfoDTO verifyDto = null;
					for (int i = 0; i < size; i++) {// 执行更新操作
						verifyDto = verifyList.get(i);
						String conditionClomn = "MEMBER_ID";
						String tableName = verifyDto.getChangeTableId();
						sqlDto = new TableSqlDto();

						if (tableName.equalsIgnoreCase("member_licence_info")
								&& verifyDto.getChangeFieldId().equalsIgnoreCase("buyer_business_license_id")) {
							if (!StringUtils.isEmpty(verifyDto.getAfterChange())) {
								tranceMemberType = true;
							}
						}

						if (tableName.equalsIgnoreCase("MEMBER_BASE_INFO")) {
							conditionClomn = "ID";
							sqlDto.setConditionValue(memberModifyDTO.getMemberId());
						} else if (tableName.equalsIgnoreCase("MEMBER_COMPANY_INFO")) {
							conditionClomn = "ID";
							if ("ARTIFICIAL_PERSON_IDCARD".equalsIgnoreCase(verifyDto.getChangeFieldId())) {
								idCard = verifyDto.getAfterChange();
								isDownToYijifu = true;
							}
							if ("COMPANY_NAME".equalsIgnoreCase(verifyDto.getChangeFieldId())) {
								companyName = verifyDto.getAfterChange();
								isDownToYijifu = true;
								MyNoMemberDTO myMemberDto = new MyNoMemberDTO();
								myMemberDto.setMemberId(dto.getMemberId());
								myMemberDto.setInvoiceNotify(companyName);
								myMemberDto.setModifyId(dto.getModifyId());
								myMemberDto.setMemberName(dto.getModifyName());
								myMemberDto.setInvoiceCompanyName(companyName);
								// 更新发票抬头信息
								myMemberDAO.updateMemberInvoiceInfo(myMemberDto);
								// 更新user名称
								UserDTO user = userExportService.queryUserByMemberId(memberModifyDTO.getMemberId())
										.getResult();
								if (user != null) {
									user.setName(companyName);
									userExportService.updateUserName(user);
								}

								// 关联交易名单
								ExecuteResult<String> code = memberBaseInfoService
										.getMemberCodeById(memberModifyDTO.getMemberId());
								TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
								transactionRelationDTO.setBuyerName(companyName);
								ExecuteResult<TransactionRelationDTO> executeResult = transactionRelationService
										.getSingleTransactionRelationByParams(transactionRelationDTO);
								if (executeResult.getResult() != null) {
									TransactionRelationDTO transactionRelation = executeResult.getResult();
									transactionRelationDTO.setId(transactionRelation.getId());
									transactionRelationDTO.setBuyerCode(code.getResult());
									transactionRelationDTO.setIsExist("1");//1.true 0.false
									transactionRelationDTO.setModifyId(dto.getModifyId()+"");
									transactionRelationDTO.setModifyName(dto.getModifyName());
									transactionRelationDTO.setModifyTime(new Date());
									transactionRelationService.updateTransactionRelation(transactionRelationDTO);
								}

							}
							if ("ARTIFICIAL_PERSON_NAME".equalsIgnoreCase(verifyDto.getChangeFieldId())) {
								realName = verifyDto.getAfterChange();
								isDownToYijifu = true;
								// 显示修改身份证提示
								verifyInfoDAO.updateShowIdMsg(memberModifyDTO.getMemberId(), 1);
							}
						} else if (tableName.equalsIgnoreCase("MEMBER_BACKUP_CONTACT_INFO")) {
							conditionClomn = "CONTACT_ID";
						} else if (tableName.equalsIgnoreCase("MEMBER_LICENCE_INFO")
								&& "BUYER_BUSINESS_LICENSE_ID".equalsIgnoreCase(verifyDto.getChangeFieldId())) {
							buyerBusinessLicenseId = verifyDto.getAfterChange();
							isDownToYijifu = true;

						} else {
							conditionClomn = "MEMBER_ID";
						}
						sqlDto.setConditionValue(verifyDto.getRecordId());
						sqlDto.setConditionClomn(conditionClomn);
						sqlDto.setFieldName(verifyDto.getChangeFieldId());
						sqlDto.setFileldValue(verifyDto.getAfterChange());
						sqlDto.setTableName(tableName);
						sqlList.add(sqlDto);

					}
					if (isDownToYijifu) {
						MemberBaseInfoDTO base = new MemberBaseInfoDTO();
						base.setId(memberModifyDTO.getMemberId());
						if (!"".equals(companyName)) {
							base.setCompanyName(companyName);
						}
						if (!"".equals(idCard)) {
							base.setArtificialPersonIdcard(idCard);
						}
						if (!"".equals(realName)) {
							base.setArtificialPersonName(realName);
						}
						if (!"".equals(buyerBusinessLicenseId)) {
							base.setBuyerBusinessLicenseId(buyerBusinessLicenseId);
						}
						YijifuCorporateCallBackDTO payBack = downToYijifuModify(base);
						if (null == payBack.getResultCode() || !"EXECUTE_SUCCESS".equals(payBack.getResultCode())) {
							String detail = payBack.getResultDetail();
							if (null != detail && detail.indexOf("certNo") >= 0) {
								rs.addErrorMessage("请输入正确身份证号");
							} else if (null != payBack.getResultMessage()
									&& payBack.getResultMessage().indexOf("certNo") >= 0) {
								rs.addErrorMessage("请输入正确身份证号");
							} else {
								rs.addErrorMessage(detail);
							}
							rs.setResultMessage("fail");
							rs.setResult(false);
							return rs;
						}
					}
					memberModifyVerifyDAO.updateTables(sqlList);

				}
				if (tranceMemberType) {
					MemberBaseInfoDTO memberBase = new MemberBaseInfoDTO();
					memberBase.setHasGuaranteeLicense(GlobalConstant.FLAG_NO);
					memberBase.setHasBusinessLicense(GlobalConstant.FLAG_YES);
					memberBase.setId(memberModifyDTO.getMemberId());
					memberBaseOperationDAO.updateMemberBaseInfo(memberBase);
				}
				dto.setSyncKey(KeygenGenerator.getUidKey());
				dto.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
				dto.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
				dto.setVerifyId(memberModifyDTO.getVerifyId());
				dto.setCreateId(memberModifyDTO.getOperatorId());
				dto.setCreateName(memberModifyDTO.getOperatorName());
				dto.setCreateTime(trancDate);
				memberBaseOperationDAO.deleteMemberStatus(dto);
				memberBaseOperationDAO.insertMemberStatus(dto);
			} else {
				int size = verifyList.size();
				if (size > 0) {
					VerifyDetailInfoDTO verifyDto = null;
					for (int i = 0; i < size; i++) {
						verifyDto = verifyList.get(i);
						String tableName = verifyDto.getChangeTableId();
						if (tableName.equalsIgnoreCase("MEMBER_COMPANY_INFO")) {
							if ("ARTIFICIAL_PERSON_IDCARD".equalsIgnoreCase(verifyDto.getChangeFieldId())) {
								// 显示修改身份证提示
								verifyInfoDAO.updateShowIdMsg(memberModifyDTO.getMemberId(), 1);
							}
						}
					}
				}
			}

			// 修改备用联系人信息
			if (memberModifyDTO.getInfoType().equals(GlobalConstant.INFO_TYPE_MEMBER_FINANCE_MODIFY)) {
				MemberBuyerFinanceDTO dto1 = new MemberBuyerFinanceDTO();
				if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(memberModifyDTO.getVerifyStatus())) {
					// 审核通过更新备用联系人状态为启用
					dto1.setStatus("4");
				} else if (GlobalConstant.VERIFY_STATUS_REFUZE.equals(memberModifyDTO.getVerifyStatus())) {
					// 2：更新备用联系人被驳回
					dto1.setStatus("2");
				}
				dto1.setContactId(verifyList.get(0).getRecordId());
				memberBuyerDao.updateBuyerBackupContact(dto1);
			}

			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
			VerifyDetailInfo verDto = null;
			verDto = new VerifyDetailInfo();// 会员审核状态审核履历
			String afterChangeDesc = "";
			if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(memberModifyDTO.getVerifyStatus())) {
				afterChangeDesc = "审核通过";
			} else {
				afterChangeDesc = "审核驳回";
			}
			verDto.setAfterChangeDesc(afterChangeDesc);
			verDto.setBeforeChangeDesc("待审核");
			verDto.setAfterChange(memberModifyDTO.getVerifyStatus());
			verDto.setBeforeChange(memberModifyDTO.getInfoType());
			verDto.setChangeFieldId("VERIFY_STATUS");
			verDto.setChangeTableId("MEMBER_STATUS_INFO");
			if (memberModifyDTO.getInfoType().equals(GlobalConstant.INFO_TYPE_MEMBER_MODIFY)) {
				verDto.setContentName("会员基本信息修改审核");
			} else if (memberModifyDTO.getInfoType().equals(GlobalConstant.INFO_TYPE_MEMBER_BACKUP_MODIFY)) {
				verDto.setContentName("会员金融信息修改审核");
			} else if (memberModifyDTO.getInfoType().equals(GlobalConstant.INFO_TYPE_MEMBER_FINANCE_MODIFY)) {
				verDto.setContentName("会员金融备份联系人信息修改审核");
			} else if (memberModifyDTO.getInfoType().equals(GlobalConstant.INFO_TYPE_MEMBER__INVOICE_MODIFY)) {
				verDto.setContentName("会员发票信息修改审核");
			}

			verDto.setModifyType(memberModifyDTO.getInfoType());
			verDto.setOperateTime(trancDate);
			verDto.setOperatorId(memberModifyDTO.getOperatorId());
			verDto.setOperatorName(memberModifyDTO.getOperatorName());
			verDto.setRecordId(memberModifyDTO.getMemberId());
			verDto.setVerifyId(memberModifyDTO.getVerifyId());
			verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
			verDtoList.add(verDto);

			VerifyInfo verufyInfo = new VerifyInfo();// 修改审核信息
			verufyInfo.setModifyId(memberModifyDTO.getOperatorId());
			verufyInfo.setModifyName(memberModifyDTO.getOperatorName());
			verufyInfo.setModifyTime(trancDate);
			verufyInfo.setId(memberModifyDTO.getVerifyId());
			verufyInfo.setModifyType(memberModifyDTO.getInfoType());
			verufyInfo.setRemark(memberModifyDTO.getRemark());
			verufyInfo.setVerifierId(memberModifyDTO.getOperatorId());
			verufyInfo.setVerifierName(memberModifyDTO.getOperatorName());
			verufyInfo.setVerifyTime(trancDate);
			verufyInfo.setVerifyStatus(memberModifyDTO.getVerifyStatus());

			dto.setInfoType(memberModifyDTO.getInfoType());
			dto.setSyncKey("");
			dto.setVerifyStatus(memberModifyDTO.getVerifyStatus());
			memberBaseOperationDAO.saveMemberStatusInfo(dto);

			memberModifyVerifyDAO.updateVerifyInfo(verufyInfo);
			memberBaseOperationDAO.insertVerifyInfo(verDtoList);
			rs.setResult(true);
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("审核异常");
			logger.error("MemberModifyVerifyServiceImpl----->saveMemberModifyVerify=" + e);
		}

		return rs;
	}

	/**
	 * 非会员注册审核列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectUnMemberVerify(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			Long count = memberModifyVerifyDAO.selectUnMemberVerifyCount(memberBaseInfoDTO);
			if (count > 0) {
				List<MemberBaseInfoDTO> resList = memberModifyVerifyDAO.selectUnMemberVerify(memberBaseInfoDTO, pager);
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberModifyVerifyServiceImpl----->selectMemberModifyVerify=" + e);
		}
		return rs;
	}

	/**
	 * 获取注册审核结果
	 */
	@Override
	public ExecuteResult<VerifyInfo> getVerifyInfoById(Long id) {
		ExecuteResult<VerifyInfo> rs = new ExecuteResult<VerifyInfo>();
		try {
			VerifyInfo verifyInfo = memberModifyVerifyDAO.getVerifyInfoById(id);
			if (null != verifyInfo) {
				rs.setResult(verifyInfo);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("查询数据不存在");
			}

		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询失败");
			logger.error("MemberModifyVerifyServiceImpl----->getVerifyInfoById=" + e);
		}
		return rs;
	}

	/**
	 * 非会员注册审核
	 */
	@Override
	public ExecuteResult<Boolean> saveUnMemberVerify(MemberBaseInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			VerifyResultDTO verifyResultDTO = memberModifyVerifyDAO.selectUnMemeberStatus(dto.getId());
			if (null != verifyResultDTO
					&& verifyResultDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_WAIT)) {
				VerifyInfo verufyInfo = new VerifyInfo();// 审核信息
				MemberStatusInfo statusInfo = new MemberStatusInfo();// 审核状态信息

				List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
				VerifyDetailInfo verDto = null;
				java.util.Date date = new java.util.Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date trancDate = new Date(format.parse(format.format(date)).getTime());
				dto.setModifyTime(trancDate);
				if (null != dto.getVerifyStatus()
						&& dto.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_ACCESS)) {
					dto.setStatus(GlobalConstant.STATUS_YES);
				} else {
					dto.setStatus(GlobalConstant.STATUS_NO);
				}

				statusInfo.setVerifyStatus(dto.getVerifyStatus());
				statusInfo.setModifyId(dto.getModifyId());
				statusInfo.setModifyName(dto.getModifyName());
				statusInfo.setVerifyTime(trancDate);
				statusInfo.setModifyTime(trancDate);
				statusInfo.setSyncKey("");
				statusInfo.setMemberId(dto.getId());
				statusInfo.setInfoType(GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);
				statusInfo.setSyncErrorMsg(dto.getRemark());

				verufyInfo.setModifyId(dto.getModifyId());
				verufyInfo.setId(dto.getVerifyId());
				verufyInfo.setModifyName(dto.getModifyName());
				verufyInfo.setModifyTime(trancDate);
				verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);
				verufyInfo.setRecordId(dto.getId());
				verufyInfo.setRemark(dto.getRemark());
				verufyInfo.setVerifierId(dto.getModifyId());
				verufyInfo.setVerifierName(dto.getModifyName());
				verufyInfo.setVerifyTime(trancDate);
				verufyInfo.setVerifyStatus(dto.getVerifyStatus());
				verufyInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);

				memberModifyVerifyDAO.updateVerifyInfo(verufyInfo);

				verDto = new VerifyDetailInfo();// 会员审核状态审核履历
				String afterChangeDesc = "";
				if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(dto.getVerifyStatus())) {
					afterChangeDesc = "审核通过";
				} else if(GlobalConstant.VERIFY_STATUS_STOP.equals(dto.getVerifyStatus())){
					 afterChangeDesc = "审核终止";
				}else {
					afterChangeDesc = "审核驳回";
				}
				verDto.setAfterChangeDesc(afterChangeDesc);
				verDto.setBeforeChangeDesc("待审核");
				verDto.setAfterChange(dto.getVerifyStatus());
				verDto.setBeforeChange(GlobalConstant.VERIFY_STATUS_WAIT);
				verDto.setChangeFieldId("VERIFY_STATUS");
				verDto.setChangeTableId("MEMBER_STATUS_INFO");
				verDto.setContentName("非会员注册审核");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getModifyId());
				verDto.setOperatorName(dto.getModifyName());
				verDto.setRecordId(dto.getId());
				verDto.setVerifyId(dto.getVerifyId());
				verDto.setRecordId(dto.getId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);

				memberBaseOperationDAO.saveMemberStatusInfo(statusInfo);
				memberBaseOperationDAO.insertVerifyInfo(verDtoList);

				if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(dto.getVerifyStatus())) {
					statusInfo.setSyncKey(KeygenGenerator.getUidKey());
					statusInfo.setVerifyId(dto.getVerifyId());
					statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
					statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
					statusInfo.setCreateId(dto.getModifyId());
					statusInfo.setCreateName(dto.getModifyName());
					statusInfo.setCreateTime(trancDate);

					memberBaseOperationDAO.deleteMemberStatus(statusInfo);
					memberBaseOperationDAO.insertMemberStatus(statusInfo);
					saveBelongBoxRelation(dto);

				}
				//add by lijun for 非会员增加终止功能 start
				else if(GlobalConstant.VERIFY_STATUS_STOP.equals(dto.getVerifyStatus())){
						MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getId(),GlobalConstant.IS_BUYER);
						dto.setCompanyName(oldDto.getCompanyName() + "终止");
						dto.setArtificialPersonMobile(oldDto.getArtificialPersonMobile() + "终止");
						dto.setBuyerSellerType(GlobalConstant.IS_BUYER);
						memberBaseOperationDAO.updateMemberCompanyInfo(dto);
				}
				memberBaseOperationDAO.updateMemberIsvalid(dto);
				//add by lijun for 非会员增加终止功能 end
				rs.setResult(true);
				rs.setResultMessage("success");
			} else {
				rs.setResult(false);
				rs.setResultMessage("此信息已被审核");
			}

		} catch (Exception e) {
			rs.addErrorMessage("更新失败");
			rs.setResultMessage("fail");
			rs.setResult(false);
			logger.error("MemberModifyVerifyServiceImpl----->saveUnMemberVerify=" + e);
		}
		return rs;
	}

	/**
	 * 保存归属关系与包厢关系
	 * 
	 * @param dto
	 * @return
	 */
	private boolean saveBelongBoxRelation(MemberBaseInfoDTO dto) {
		MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberbaseById(dto.getId(), GlobalConstant.IS_BUYER);
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		belongRelationshipDto.setMemberId(dto.getId());
		dto.setBelongManagerId(memberBase.getCurBelongManagerId());
		dto.setCurBelongManagerId(memberBase.getCurBelongManagerId());

		if (null != dto.getVerifyStatus() && dto.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_ACCESS)) {
			belongRelationshipDto.setVerifyStatus("3");
		} else {
			belongRelationshipDto.setVerifyStatus("2");
		}
		belongRelationshipDto.setMemberId(dto.getId());
		belongRelationshipDto.setModifyId(dto.getModifyId());
		belongRelationshipDto.setModifyName(dto.getModifyName());

		belongRelationshipDAO.updateBelongVerify(belongRelationshipDto);// 更新归属关系状态

		ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
		applyBusiRelationDto.setAuditStatus("1");
		applyBusiRelationDto.setMemberId(dto.getId());
		applyBusiRelationDto.setSellerId(memberBase.getBelongSellerId());
		applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
		applyBusiRelationDto.setCreateId(dto.getModifyId());
		applyBusiRelationDto.setCustomerManagerId(dto.getCurBelongManagerId());
		applyBusiRelationDto.setCreateName(dto.getModifyName());
		applyBusiRelationDto.setModifyId(dto.getModifyId());
		applyBusiRelationDto.setModifyName(dto.getModifyName());
		verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);
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
			transactionRelationDTO.setModifyId(dto.getModifyId()+"");
			transactionRelationDTO.setModifyName(dto.getModifyName());
			transactionRelationDTO.setModifyTime(new Date());
			transactionRelationService.updateTransactionRelation(transactionRelationDTO);
		}
		return true;
	}

	/**
	 * 修改支付信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModify(MemberBaseInfoDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getId(),
				GlobalConstant.IS_BUYER);
		if (!StringUtils.isEmpty(dto.getArtificialPersonName())) {

			if (!StringUtils.isEmpty(dto.getArtificialPersonName())) {
				payDto.setLegalPersonName(dto.getArtificialPersonName());
			} else {
				payDto.setLegalPersonName(baseInfo.getArtificialPersonName());
			}
		}
		if (!StringUtils.isEmpty(dto.getArtificialPersonIdcard())) {
			if (!StringUtils.isEmpty(dto.getArtificialPersonIdcard())) {
				payDto.setLegalPersonCertNo(dto.getArtificialPersonIdcard());
			} else {
				payDto.setLegalPersonCertNo(baseInfo.getArtificialPersonIdcard());
			}

		}
		if (!StringUtils.isEmpty(dto.getCompanyName())) {
			payDto.setComName(dto.getCompanyName());
		} else {
			payDto.setComName(baseInfo.getCompanyName());
		}
		if (!StringUtils.isEmpty(dto.getBuyerBusinessLicenseId())) {
			payDto.setLicenceNo(dto.getBuyerBusinessLicenseId());
		}
		payDto.setUserId(baseInfo.getAccountNo());

		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectUnMemberCompanyNameVerify(
			MemberBaseInfoDTO memberBaseInfoDTO, String startTime, String endTime,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			Long count = memberModifyVerifyDAO.selectUnMemberCompanyNameVerifyCount(memberBaseInfoDTO, startTime,
					endTime);
			if (count > 0) {
				List<MemberBaseInfoDTO> resList = memberModifyVerifyDAO
						.selectUnMemberCompanyNameVerify(memberBaseInfoDTO, startTime, endTime, pager);
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberModifyVerifyServiceImpl----->selectMemberModifyVerify=" + e);
		}
		return rs;
	}
}

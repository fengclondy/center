package cn.htd.membercenter.service.impl;

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
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.common.exception.MemberCenterException;
import cn.htd.membercenter.common.util.HttpUtils;
import cn.htd.membercenter.common.util.ValidateResult;
import cn.htd.membercenter.common.util.ValidationUtils;
import cn.htd.membercenter.costs.MemberCenterCodeConst;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.ConsigneeAddressDAO;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dao.MemberBaseInfoDao;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberBuyerDao;
import cn.htd.membercenter.dao.MemberCompanyInfoDao;
import cn.htd.membercenter.dao.MemberDownErpDAO;
import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dao.MemberLicenceInfoDao;
import cn.htd.membercenter.dao.MemberModifyVerifyDAO;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.dao.MyMemberDAO;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.domain.BuyerGroupRelationship;
import cn.htd.membercenter.domain.BuyerPointHistory;
import cn.htd.membercenter.domain.MemberBackupContactInfo;
import cn.htd.membercenter.domain.MemberExtendInfo;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BoxAddDto;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.BuyerGroupInfo;
import cn.htd.membercenter.dto.BuyerHisPointDTO;
import cn.htd.membercenter.dto.CenterUpdateInfo;
import cn.htd.membercenter.dto.FailDetailInfo;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoRegisterDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberBuyerVerifyDetailInfoDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.MemberInfoMotifyDTO;
import cn.htd.membercenter.dto.MemberInvoiceInfoDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.SalemanDTO;
import cn.htd.membercenter.dto.SellerTypeInfoDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.membercenter.service.PayInfoService;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.CustomerService;
import cn.htd.usercenter.service.UserExportService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("memberBaseInfoService")
public class MemberBaseInfoServiceImpl implements MemberBaseInfoService {
	private final static Logger logger = LoggerFactory.getLogger(MemberBaseInfoServiceImpl.class);
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private MemberModifyVerifyDAO memberModifyVerifyDAO;

	@Resource
	private MemberBuyerDao memberBuyerDao;

	@Resource
	private DictionaryUtils dictionaryUtils;
	@Resource
	private MemberBaseInfoDao memberBaseInfoDao;
	@Resource
	MyMemberDAO myMemberDAO;
	@Resource
	private ConsigneeAddressDAO consigneeAddressDAO;
	@Resource
	private MemberBaseDAO memberBaseDAO;
	@Resource
	BelongRelationshipDAO belongRelationshipDAO;
	@Resource
	MemberVerifySaveDAO verifyInfoDAO;
	@Autowired
	private MemberBaseService memberBaseService;
	@Resource
	MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Resource
	private MemberLicenceInfoDao memberLicenceInfoDao;

	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;

	@Resource
	private MemberCompanyInfoDao memberCompanyInfoDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserExportService userExportService;

	@Autowired
	private PayInfoService payInfoService;

	@Autowired
	private SendSmsEmailService sendSmsEmailService;

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Resource
	private BoxRelationshipDAO boxRelationshipDao;

	@Resource
	private MemberGradeService memberGradeService;

	@Resource
	MemberDownErpDAO memberDownErpDAO;

	@Resource
	private TransactionRelationService transactionRelationService;

	/**
	 * NO.1 查询审核通过的会员信息列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectBaseMemberInfo(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			List<MemberBaseInfoDTO> resList = memberBaseOperationDAO.selectBaseMemberInfo(memberBaseInfoDTO, pager);
			Long count = memberBaseOperationDAO.selectMemberCount(memberBaseInfoDTO);
			if (null != resList) {
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
			logger.error("MemberBaseInfoServiceImpl----->selectBaseMemberInfo=" + e);
		}
		return rs;
	}

	/**
	 * NO.2 中心店会员导入
	 */
	@Override
	public ExecuteResult<CenterUpdateInfo> updateMemberInfo(List<MemberBaseInfoDTO> dto) {
		ExecuteResult<CenterUpdateInfo> rs = new ExecuteResult<CenterUpdateInfo>();
		List<MemberBaseInfoDTO> effectiveList = new ArrayList<MemberBaseInfoDTO>();
		List<FailDetailInfo> failList = new ArrayList<FailDetailInfo>();
		int successCount = 0;
		int failCount = 0;
		List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
		VerifyDetailInfo verDto = null;
		try {
			int count = dto.size();
			if (count > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {// 导入最大条数限制
				rs.setResultMessage("导入最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
			} else {
				CenterUpdateInfo centerUpdateInfo = new CenterUpdateInfo();
				FailDetailInfo failinfo = null;
				MemberBaseInfoDTO memberImport = null;
				MemberBaseInfoDTO memberExit = null;
				List<MemberBaseInfoDTO> memberCodeExitList = memberBaseOperationDAO.selectMemberExit(dto);
				int size = memberCodeExitList.size();
				int importSize = dto.size();
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date trancDate = format.parse(format.format(date));
				if (importSize > 0) {
					for (int i = 0; i < importSize; i++) {// 从导入数据中筛选出会员编码存在的有效数据
						memberImport = dto.get(i);
						String importMemberCode = memberImport.getMemberCode();
						Date time = memberImport.getUpgradeCenterStoreTime();
						Boolean isExit = false;
						for (int j = 0; j < size; j++) {
							memberExit = memberCodeExitList.get(j);
							if (null == memberExit.getUpgradeCenterStoreTime()) {
								memberExit.setUpgradeCenterStoreTime(format.parse("0000-00-00 00:00:00"));
							}
							String memberCodeExit = memberExit.getMemberCode();
							if (null != importMemberCode && importMemberCode.equals(memberCodeExit)) {
								isExit = true;
								if (null != time) {
									int isCenterStore = memberExit.getIsCenterStore();
									verDto = new VerifyDetailInfo();// 升级中心店时间修改履历信息
									verDto.setAfterChange(format.format(trancDate));
									verDto.setAfterChangeDesc(format.format(trancDate));
									verDto.setBeforeChange(format.format(memberExit.getUpgradeCenterStoreTime()));
									verDto.setBeforeChangeDesc(format.format(memberExit.getUpgradeCenterStoreTime()));
									verDto.setChangeFieldId("UPGRADE_CENTER_STORE_TIME");
									verDto.setChangeTableId("MEMBER_BASE_INFO");
									verDto.setContentName("导入中心店时间");
									verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
									verDto.setOperateTime(trancDate);
									verDto.setOperatorId(memberImport.getModifyId());
									verDto.setOperatorName(memberImport.getModifyName());
									verDto.setRecordId(memberExit.getId());
									verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
									verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
									verDtoList.add(verDto);

									if (isCenterStore != GlobalConstant.IS_CENTER_STORE_YES) {
										verDto = new VerifyDetailInfo();// 中心店状态修改履历信息
										verDto.setAfterChange(String.valueOf(GlobalConstant.IS_CENTER_STORE_YES));
										verDto.setBeforeChange(String.valueOf(GlobalConstant.IS_CENTER_STORE_NO));
										verDto.setAfterChangeDesc("中心店");
										verDto.setBeforeChangeDesc("非中心店");
										verDto.setChangeFieldId("IS_CENTER_STORE");
										verDto.setChangeTableId("MEMBER_BASE_INFO");
										verDto.setContentName("是否中心店");
										verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
										verDto.setOperateTime(trancDate);
										verDto.setOperatorId(memberImport.getModifyId());
										verDto.setOperatorName(memberImport.getModifyName());
										verDto.setRecordId(memberExit.getId());
										verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
										verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
										verDtoList.add(verDto);
									}
									memberExit.setModifyTime(trancDate);
									memberExit.setModifyName(memberImport.getModifyName());
									memberExit.setModifyId(memberImport.getModifyId());
									memberExit.setUpgradeCenterStoreTime(time);
									memberExit.setIsCenterStore(GlobalConstant.IS_CENTER_STORE_YES);
									effectiveList.add(memberExit);
								} else {
									failinfo = new FailDetailInfo();
									failinfo.setMemberCode(importMemberCode);
									failinfo.setFailDesc("生效时间填写有误");
									failList.add(failinfo);
								}
							}
						}
						if (!isExit) {
							failinfo = new FailDetailInfo();
							failinfo.setMemberCode(importMemberCode);
							failinfo.setFailDesc("会员编码不存在");
							failList.add(failinfo);
						}
					}
				}

				if (effectiveList.size() > 0) {
					int update = memberBaseOperationDAO.updateMemberInfo(effectiveList);
					memberBaseOperationDAO.insertVerifyInfo(verDtoList);// 增加修改履历
					if (update > 0) {
						failCount = failList.size();
						successCount = dto.size() - failCount;
					} else {
						int eSize = effectiveList.size();
						for (int i = 0; i < eSize; i++) {
							failinfo = new FailDetailInfo();
							failinfo.setFailDesc("更新失败");
							failinfo.setMemberCode(effectiveList.get(i).getMemberCode());
						}
						failCount = dto.size();
						successCount = 0;
					}
				} else {
					failCount = dto.size();
					successCount = 0;
				}

				centerUpdateInfo.setFailCount(failCount);
				centerUpdateInfo.setSuccessCount(successCount);
				centerUpdateInfo.setFailInfoList(failList);
				rs.setResult(centerUpdateInfo);
				rs.setResultMessage("success");
			}

		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("更新异常");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberInfo=" + e);
		}
		return rs;
	}

	/**
	 * NO.3 审核通过的会员基本信息导出
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> getMemBaseInfoForExport(MemberBaseInfoDTO memberBaseInfoDTO) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			Long count = memberBaseOperationDAO.selectMemberCount(memberBaseInfoDTO);
			if (count > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {
				rs.setResultMessage("导出最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
			} else {
				List<MemberBaseInfoDTO> resList = memberBaseOperationDAO.getMemBaseInfoForExport(memberBaseInfoDTO);
				if (null != resList) {
					dg.setRows(resList);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
				rs.setResultMessage("success");
			}

		} catch (Exception e) {
			rs.addErrorMessage("要查询的数据不存在");
			rs.setResultMessage("fail");
			logger.error("MemberBaseInfoServiceImpl----->getMemBaseInfoForExport=" + e);
		}
		return rs;
	}

	/**
	 * NO.4 根据会员ID查询会员基本信息、发票信息、金融备份联系人等详情
	 */
	@Override
	public ExecuteResult<MemberDetailInfo> getMemberDetailById(Long id) {
		ExecuteResult<MemberDetailInfo> rs = new ExecuteResult<MemberDetailInfo>();
		MemberDetailInfo memberDetailInfo = new MemberDetailInfo();
		try {
			MemberBaseInfoDTO memberBaseInfoDTO = memberBaseOperationDAO.getMemberbaseById(id, GlobalConstant.IS_BUYER);// 查询会员基本信息
			if (memberBaseInfoDTO == null) {
				rs.setResultMessage("fail");
				rs.addErrorMessage("查询不到会员信息");
				return rs;
			}
			// if (null != memberBaseInfoDTO.getBelongManagerId() &&
			// !"0".equals(memberBaseInfoDTO.getBelongManagerId())) {
			// EmployeeDTO employeeDTO =
			// employeeService.getEmployeeInfo(memberBaseInfoDTO.getBelongManagerId());
			// if (null != employeeDTO) {
			memberBaseInfoDTO.setBelongManagerName(getManagerName(memberBaseInfoDTO.getBelongSellerId().toString(),
					memberBaseInfoDTO.getBelongManagerId()));
					// }
					// }

			// if (null != memberBaseInfoDTO.getCurBelongManagerId()
			// && !"0".equals(memberBaseInfoDTO.getCurBelongManagerId())) {
			// EmployeeDTO employeeCurDTO =
			// employeeService.getEmployeeInfo(memberBaseInfoDTO.getCurBelongManagerId());
			// if (null != employeeCurDTO) {
			memberBaseInfoDTO.setCurBelongManagerName(getManagerName(
					memberBaseInfoDTO.getCurBelongSellerId().toString(), memberBaseInfoDTO.getCurBelongManagerId()));
			// }
			// }

			List<MemberBackupContactInfo> memberBackupContactInfo = memberBaseOperationDAO.getBackupContactById(id);// 查询会员备份联系人信息
			MemberInvoiceInfoDTO memberInvoiceInfo = memberBaseOperationDAO.getMemberInvoiceById(id);// 查询会员发票信息
			/**
			 * // 拼接发票地址 if (memberInvoiceInfo != null &&
			 * memberInvoiceInfo.getInvoiceAddressTown() != null) { String
			 * invoiceAddress = memberBaseService
			 * .getAddressBaseByCode(memberInvoiceInfo.getInvoiceAddressTown().
			 * toString()) + memberInvoiceInfo.getInvoiceAddressDetail();
			 * memberInvoiceInfo.setInvoiceAddress(invoiceAddress); }
			 **/
			memberDetailInfo.setMemberBackupContactInfo(memberBackupContactInfo);
			memberDetailInfo.setMemberBaseInfoDTO(memberBaseInfoDTO);
			memberDetailInfo.setMemberInvoiceInfo(memberInvoiceInfo);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getMemberDetailById=" + e);
		}
		rs.setResult(memberDetailInfo);
		return rs;
	}

	/**
	 * NO.5 根据会员ID查询卖家基本信息、发票信息、金融备份联系人等详情
	 */
	@Override
	public ExecuteResult<MemberDetailInfo> getMemberDetailBySellerId(Long id) {
		ExecuteResult<MemberDetailInfo> rs = new ExecuteResult<MemberDetailInfo>();
		MemberDetailInfo memberDetailInfo = new MemberDetailInfo();
		try {
			MemberBaseInfoDTO memberBaseInfoDTO = memberBaseOperationDAO.getMemberbaseBySellerId(id,
					GlobalConstant.IS_SELLER);// 查询会员基本信息
			List<MemberBackupContactInfo> memberBackupContactInfo = memberBaseOperationDAO.getBackupContactById(id);// 查询会员备份联系人信息
			MemberInvoiceInfoDTO memberInvoiceInfo = memberBaseOperationDAO.getMemberInvoiceById(id);// 查询会员发票信息

			memberDetailInfo.setMemberBackupContactInfo(memberBackupContactInfo);
			memberDetailInfo.setMemberBaseInfoDTO(memberBaseInfoDTO);
			memberDetailInfo.setMemberInvoiceInfo(memberInvoiceInfo);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getMemberDetailBySellerId=" + e);
		}
		rs.setResult(memberDetailInfo);
		return rs;
	}

	/**
	 * 查询信息修改履历
	 */
	@Override
	public ExecuteResult<DataGrid<VerifyDetailInfo>> getVerifyById(Long id, String infoType,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<VerifyDetailInfo>> rs = new ExecuteResult<DataGrid<VerifyDetailInfo>>();
		DataGrid<VerifyDetailInfo> dg = new DataGrid<VerifyDetailInfo>();
		try {
			Long count = memberBaseOperationDAO.getVerifyCountById(id, infoType);
			if (count > 0) {
				List<VerifyDetailInfo> resList = memberBaseOperationDAO.getVerifyById(id, infoType, pager);
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getVerifyById=" + e);
		}
		return rs;
	}

	/**
	 * NO.5 更改会员账号有效性
	 */
	@Override
	public ExecuteResult<Boolean> updateMemberIsvalid(MemberBaseInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();// 修改履历信息
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			dto.setModifyTime(trancDate);
			MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getId(),
					GlobalConstant.IS_BUYER);
			if (!oldDto.getStatus().equals(dto.getStatus())) {
				verDto = new VerifyDetailInfo();// 有效性修改履历
				String afterChangeDesc = "";
				String beforeChangeDesc = "";
				if (GlobalConstant.STATUS_YES.equals(oldDto.getStatus())) {
					beforeChangeDesc = "有效性更改为有效";
				} else {
					beforeChangeDesc = "有效性更改为无效";
				}
				if (GlobalConstant.STATUS_YES.equals(dto.getStatus())) {
					afterChangeDesc = "有效性更改为有效(备注：" + dto.getRemark() + ")";
				} else {
					afterChangeDesc = "有效性更改为无效(备注：" + dto.getRemark() + ")";
				}
				verDto.setAfterChangeDesc(afterChangeDesc);
				verDto.setBeforeChangeDesc(beforeChangeDesc);
				verDto.setAfterChange(dto.getStatus());
				verDto.setBeforeChange(oldDto.getStatus());
				verDto.setChangeFieldId("STATUS");
				verDto.setChangeTableId("MEMBER_BASE_INFO");
				verDto.setContentName("会员状态有效性修改");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getModifyId());
				verDto.setOperatorName(dto.getModifyName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);
			}
			if (verDtoList.size() > 0) {
				Boolean status;
				if ("1".equals(dto.getStatus())) {
					status = true;
				} else {
					status = false;
				}
				// 下行状态到用户中心
				ExecuteResult<Boolean> custRs = customerService.editValidCustomer(oldDto.getMemberCode(), status,
						dto.getModifyId());
				if (custRs.isSuccess()) {
					memberBaseOperationDAO.insertVerifyInfo(verDtoList);
					memberBaseOperationDAO.updateMemberIsvalid(dto);
					rs.setResultMessage("success");
					rs.setResult(true);
				} else {
					rs.setResult(false);
					rs.addErrorMessage(custRs.getErrorMessages().get(0));
					rs.setResultMessage("fail");
				}

			}

		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("更新异常");
			rs.setResultMessage("fail");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberIsvalid=" + e);
		}
		return rs;
	}

	/**
	 * 密码重置
	 */
	@Override
	public ExecuteResult<Boolean> updateMemberPassword(Long userid, String memberCode, String password, Long userId,
			String userName) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();// 修改履历信息
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			verDto = new VerifyDetailInfo();
			verDto.setAfterChange("会员密码重置");
			verDto.setBeforeChange("");
			verDto.setAfterChangeDesc("会员密码重置");
			verDto.setBeforeChangeDesc("");
			verDto.setChangeFieldId("PASSWORD");
			verDto.setChangeTableId("USER");
			verDto.setContentName("会员密码重置");
			verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
			verDto.setOperateTime(trancDate);
			verDto.setOperatorId(userId);
			verDto.setOperatorName(userName);
			verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
			verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
			verDto.setRecordId(userid);
			verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
			verDtoList.add(verDto);
			if (verDtoList.size() > 0) {
				// 下行到用户中心
				ExecuteResult<Boolean> custRs = userExportService.memberPasswdReset(memberCode, password, userId);
				if (custRs.isSuccess()) {
					memberBaseOperationDAO.insertVerifyInfo(verDtoList);
					rs.setResultMessage("success");
					rs.setResult(true);
				} else {
					rs.setResult(false);
					rs.addErrorMessage(custRs.getErrorMessages().get(0));
					rs.setResultMessage("fail");
				}
			}

		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("更新异常");
			rs.setResultMessage("fail");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberPassword=" + e);
		}
		return rs;
	}

	/**
	 * NO.6 根据会员ID查询会员所属卖家的分组列表信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<BuyerGroupInfo>> getMemberGroupInfo(Long id,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<BuyerGroupInfo>> rs = new ExecuteResult<DataGrid<BuyerGroupInfo>>();
		DataGrid<BuyerGroupInfo> dg = new DataGrid<BuyerGroupInfo>();
		try {
			List<BuyerGroupRelationship> groupRelationList = memberBaseOperationDAO.getBuyerGroupRelation(id);
			int size = groupRelationList.size();
			if (size > 0) {
				MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberBaseInfoById(id,
						GlobalConstant.IS_BUYER);
				List<Long> groupIds = new ArrayList<Long>();
				for (int i = 0; i < size; i++) {
					groupIds.add(groupRelationList.get(i).getGroupId());
				}
				Long count = memberBaseOperationDAO.selectMemberGroupCount(groupIds);
				if (count > 0) {
					List<BuyerGroupInfo> list = memberBaseOperationDAO.getMemberGroupInfo(groupIds, pager);// 查询会员分钟
					int buyerGroupSize = list.size();
					MemberBaseInfoDTO memberBaseSeller = null;
					for (int i = 0; i < buyerGroupSize; i++) {// 获取供应商信息
						BuyerGroupInfo resDto = list.get(i);
						Long sellerId = resDto.getSellerId();
						if (null != sellerId) {
							memberBaseSeller = memberBaseOperationDAO.getMemberBaseInfoById(sellerId,
									GlobalConstant.IS_SELLER);
							if (null != memberBaseSeller) {
								resDto.setSellerCode(memberBaseSeller.getMemberCode());
								resDto.setSellerName(memberBaseSeller.getCompanyName());
							}
						}
						resDto.setCompanyName(memberBase.getCompanyName());
					}
					dg.setRows(list);
					dg.setTotal(count);
					rs.setResult(dg);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getMemberGroupInfo=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员经验值变动履历列表信息
	 */
	@Override
	public ExecuteResult<DataGrid<BuyerPointHistory>> getBuyerPointHis(BuyerHisPointDTO dto,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<BuyerPointHistory>> rs = new ExecuteResult<DataGrid<BuyerPointHistory>>();
		DataGrid<BuyerPointHistory> dg = new DataGrid<BuyerPointHistory>();
		try {
			Long count = memberBaseOperationDAO.selectBuyerPointCount(dto);
			List<BuyerPointHistory> list = memberBaseOperationDAO.getBuyerPointHis(dto, pager);
			if (count > 0) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);

			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getBuyerPointHis=" + e);
		}

		return rs;
	}

	/**
	 * 更改会员中心状态
	 */
	@Override
	public ExecuteResult<Boolean> updateMemberCenter(MemberBaseInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			dto.setModifyTime(trancDate);
			dto.setUpgradeCenterStoreTime(trancDate);
			MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getId(),
					GlobalConstant.IS_BUYER);
			if (null != oldDto.getIsCenterStore() && null != dto.getIsCenterStore()
					&& oldDto.getIsCenterStore().intValue() != dto.getIsCenterStore().intValue()) {
				verDto = new VerifyDetailInfo();// 会员中心店信息修改履历
				String afterChangeDesc = "";
				String beforeChangeDesc = "";
				if (GlobalConstant.FLAG_YES == oldDto.getIsCenterStore().intValue()) {
					beforeChangeDesc = "设置中心店为是";
				} else {
					beforeChangeDesc = "设置中心店为否";
				}
				if (GlobalConstant.FLAG_YES == dto.getIsCenterStore().intValue()) {
					afterChangeDesc = "设置中心店为是";
				} else {
					afterChangeDesc = "设置中心店为否";
				}
				verDto.setAfterChangeDesc(afterChangeDesc);
				verDto.setBeforeChangeDesc(beforeChangeDesc);
				verDto.setAfterChange(String.valueOf(dto.getIsCenterStore()));
				verDto.setBeforeChange(String.valueOf(oldDto.getIsCenterStore()));
				verDto.setChangeFieldId("IS_CENTER_STORE");
				verDto.setChangeTableId("MEMBER_BASE_INFO");
				verDto.setContentName("是否会员中心店");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getModifyId());
				verDto.setOperatorName(dto.getModifyName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);
			}
			if (verDtoList.size() > 0) {
				memberBaseOperationDAO.insertVerifyInfo(verDtoList);
				memberBaseOperationDAO.updateMemberCenter(dto);
			}
			rs.setResult(true);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("更新失败");
			rs.setResultMessage("fail");
			rs.setResult(false);
			logger.error("MemberBaseInfoServiceImpl----->updateMemberCenter=" + e);
		}
		return rs;
	}

	/**
	 * 查询审核会员基本信息
	 */
	@Override
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectVerifyMember(MemberBaseInfoDTO memberBaseInfoDTO,
			@SuppressWarnings("rawtypes") Pager pager) {
		ExecuteResult<DataGrid<MemberBaseInfoDTO>> rs = new ExecuteResult<DataGrid<MemberBaseInfoDTO>>();
		try {
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			DataGrid<MemberBaseInfoDTO> dg = new DataGrid<MemberBaseInfoDTO>();
			List<MemberBaseInfoDTO> resList = memberBaseOperationDAO.selectVerifyMember(memberBaseInfoDTO, pager);
			Long count = memberBaseOperationDAO.selectVerifyMemberCount(memberBaseInfoDTO);
			if (null != resList) {
				dg.setRows(resList);
				dg.setTotal(count);
				rs.setResult(dg);

			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->selectBaseMemberInfo=" + e);
		}
		return rs;
	}

	/**
	 * 查询审核会员信息详情
	 */
	@Override
	public ExecuteResult<MemberDetailInfo> getMemberVerifyDetailById(Long id) {
		ExecuteResult<MemberDetailInfo> rs = new ExecuteResult<MemberDetailInfo>();
		MemberDetailInfo memberDetailInfo = new MemberDetailInfo();
		try {
			VerifyResultDTO verifyResultDTO = null;
			MemberBaseInfoDTO memberBaseInfoDTO = memberBaseOperationDAO.getMemberbaseById(id, GlobalConstant.IS_BUYER);
			List<MemberBackupContactInfo> memberBackupContactInfo = memberBaseOperationDAO.getBackupContactById(id);
			MemberInvoiceInfoDTO memberInvoiceInfo = memberBaseOperationDAO.getMemberInvoiceById(id);

			// if (null != memberBaseInfoDTO.getBelongManagerId() &&
			// !"0".equals(memberBaseInfoDTO.getBelongManagerId())) {
			// EmployeeDTO employeeDTO =
			// employeeService.getEmployeeInfo(memberBaseInfoDTO.getBelongManagerId());
			// if (null != employeeDTO) {
			memberBaseInfoDTO.setBelongManagerName(getManagerName(memberBaseInfoDTO.getBelongSellerId().toString(),
					memberBaseInfoDTO.getBelongManagerId()));
					// }
					// }

			// if (null != memberBaseInfoDTO.getCurBelongManagerId()
			// && !"0".equals(memberBaseInfoDTO.getCurBelongManagerId())) {
			// EmployeeDTO employeeCurDTO =
			// employeeService.getEmployeeInfo(memberBaseInfoDTO.getCurBelongManagerId());
			// if (null != employeeCurDTO) {
			memberBaseInfoDTO.setCurBelongManagerName(getManagerName(
					memberBaseInfoDTO.getCurBelongSellerId().toString(), memberBaseInfoDTO.getCurBelongManagerId()));
			// }
			// }

			verifyResultDTO = memberBaseOperationDAO.selectVerifyResult(id, GlobalConstant.INFO_TYPE_VERIFY);// 第一步，查询会员审核状态
			if (null != verifyResultDTO) {
				if (!verifyResultDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_WAIT)) {
					VerifyResultDTO verifyInfo = memberBaseOperationDAO
							.selectVerifyResultById(verifyResultDTO.getVerifyId());// 第二步，查询会员审核信息
					if (verifyInfo != null) {
						verifyResultDTO.setModifyTime(verifyInfo.getModifyTime());
						verifyResultDTO.setRemark(verifyInfo.getRemark());
						verifyResultDTO.setOperatorName(verifyInfo.getOperatorName());
					}

					if (verifyResultDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_ACCESS)) {
						boolean belongDefault = false;
						// 归属0801商家id
						List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO.getMemberInfoByCompanyCode(
								GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
						if (rsList.size() > 0) {
							if (rsList.get(0).getId().intValue() == memberBaseInfoDTO.getCurBelongSellerId()
									.intValue()) {
								belongDefault = true;
							}
						}
						if (!belongDefault) {
							VerifyResultDTO verifyCooperateInfoDto = memberBaseOperationDAO// 第三步，查询供应商状态
									.selectCooperateVerifyResult(id);
							if (null != verifyCooperateInfoDto) {
								verifyResultDTO.setCooperateVerifyStatus(verifyCooperateInfoDto.getVerifyStatus());
								VerifyResultDTO verifyCooperateInfo = memberBaseOperationDAO
										.selectVerifyResultById(verifyCooperateInfoDto.getVerifyId());// 第四步，查询供应商审核信息
								if (null != verifyCooperateInfo) {
									verifyResultDTO.setCooperateModifyTime(verifyCooperateInfo.getModifyTime());
								}
							}

						}
					}
				}

			}

			memberDetailInfo.setMemberBackupContactInfo(memberBackupContactInfo);
			memberDetailInfo.setMemberBaseInfoDTO(memberBaseInfoDTO);
			memberDetailInfo.setMemberInvoiceInfo(memberInvoiceInfo);
			memberDetailInfo.setVerifyResultDTO(verifyResultDTO);
			rs.setResult(memberDetailInfo);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询失败");
			logger.error("MemberBaseInfoServiceImpl----->getMemberDetailById=" + e);
		}

		return rs;
	}

	/**
	 * 保存修改后基本信息
	 */
	@Override
	public ExecuteResult<Boolean> updateMemberBaseInfo(MemberInfoMotifyDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		MemberBaseInfoDTO baseDto = new MemberBaseInfoDTO();
		try {
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			baseDto.setId(dto.getMemberId());
			baseDto.setBuyerSellerType(GlobalConstant.IS_BUYER);
			baseDto.setModifyTime(trancDate);
			baseDto.setCompanyName(dto.getCompanyName());
			baseDto.setIndustryCategory(dto.getIndustryCategory());
			if ("7".equals(dto.getIndustryCategory())) {
				baseDto.setIsDiffIndustry(GlobalConstant.FLAG_NO);
			} else {
				baseDto.setIsDiffIndustry(GlobalConstant.FLAG_YES);
			}
			// baseDto.setRealNameStatus("1");// 重新设置未实名
			baseDto.setArtificialPersonName(dto.getArtificialPersonName());
			baseDto.setArtificialPersonIdcard(dto.getArtificialPersonIdcard());
			baseDto.setModifyId(dto.getOperatorId());
			baseDto.setModifyName(dto.getOperatorName());
			MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getMemberId(),
					GlobalConstant.IS_BUYER);
			if (!oldDto.getCompanyName().equals(dto.getCompanyName())) {
				verDto = new VerifyDetailInfo();// 公司名称修改履历
				verDto.setAfterChange(dto.getCompanyName());
				verDto.setBeforeChange(oldDto.getCompanyName());
				verDto.setAfterChangeDesc(dto.getCompanyName());
				verDto.setBeforeChangeDesc(oldDto.getCompanyName());
				verDto.setChangeFieldId("COMPANY_NAME");
				verDto.setChangeTableId("MEMBER_COMPANY_INFO");
				verDto.setContentName("公司名称");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getOperatorId());
				verDto.setOperatorName(dto.getOperatorName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getMemberId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);

				MyNoMemberDTO myMemberDto = new MyNoMemberDTO();
				myMemberDto.setMemberId(dto.getMemberId());
				myMemberDto.setInvoiceNotify(dto.getCompanyName());
				myMemberDto.setInvoiceCompanyName(dto.getCompanyName());
				myMemberDto.setModifyId(dto.getOperatorId());
				myMemberDto.setMemberName(dto.getOperatorName());
				// 更新发票抬头信息
				myMemberDAO.updateMemberInvoiceInfo(myMemberDto);

				// 更新用户名称
				UserDTO user = userExportService.queryUserByMemberId(baseDto.getId()).getResult();
				if (user != null) {
					user.setName(dto.getCompanyName());
					userExportService.updateUserName(user);
				}

				// 关联交易名单
				ExecuteResult<String> code = getMemberCodeById(dto.getMemberId());
				TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
				transactionRelationDTO.setBuyerName(dto.getCompanyName());
				ExecuteResult<TransactionRelationDTO> executeResult = transactionRelationService
						.getSingleTransactionRelationByParams(transactionRelationDTO);
				if (executeResult.getResult() != null) {
					TransactionRelationDTO transactionRelation = executeResult.getResult();
					transactionRelationDTO.setId(transactionRelation.getId());
					transactionRelationDTO.setBuyerCode(code.getResult());
					transactionRelationDTO.setIsExist("1");//1.true 0.false
					transactionRelationDTO.setModifyId(dto.getOperatorId()+"");
					transactionRelationDTO.setModifyName(dto.getOperatorName());
					transactionRelationDTO.setModifyTime(new Date());
					transactionRelationService.updateTransactionRelation(transactionRelationDTO);
				}
			}
			if (!oldDto.getIndustryCategory().equals(dto.getIndustryCategory())) {
				verDto = new VerifyDetailInfo();// 公司名称修改履历
				verDto.setAfterChange(dto.getIndustryCategory());
				verDto.setBeforeChange(oldDto.getIndustryCategory());
				String afterDesc = dictionaryUtils.getNameByValue(DictionaryConst.TYPE_DEVELOPMENT_INDUSTRY,
						dto.getIndustryCategory());
				String beforDesc = dictionaryUtils.getNameByValue(DictionaryConst.TYPE_DEVELOPMENT_INDUSTRY,
						oldDto.getIndustryCategory());
				verDto.setBeforeChangeDesc(beforDesc);
				verDto.setAfterChangeDesc(afterDesc);
				verDto.setChangeFieldId("INDUSTRY_CATEGORY");
				verDto.setChangeTableId("MEMBER_BASE_INFO");
				verDto.setContentName("发展行业");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getOperatorId());
				verDto.setOperatorName(dto.getOperatorName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getMemberId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);

			}
			if (!oldDto.getArtificialPersonName().equals(dto.getArtificialPersonName())) {
				verDto = new VerifyDetailInfo();// 公司法人名称修改履历
				verDto.setAfterChange(dto.getArtificialPersonName());
				verDto.setBeforeChange(oldDto.getArtificialPersonName());
				verDto.setAfterChangeDesc(dto.getArtificialPersonName());
				verDto.setBeforeChangeDesc(oldDto.getArtificialPersonName());
				verDto.setChangeFieldId("ARTIFICIAL_PERSON_NAME");
				verDto.setChangeTableId("MEMBER_COMPANY_INFO");
				verDto.setContentName("公司法人名称");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getOperatorId());
				verDto.setOperatorName(dto.getOperatorName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getMemberId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);
			}
			if (!oldDto.getArtificialPersonIdcard().equals(dto.getArtificialPersonIdcard())) {
				verDto = new VerifyDetailInfo();// 公司法人证件号修改履历
				verDto.setAfterChange(dto.getArtificialPersonIdcard());
				verDto.setBeforeChange(oldDto.getArtificialPersonIdcard());
				verDto.setAfterChangeDesc(dto.getArtificialPersonIdcard());
				verDto.setBeforeChangeDesc(oldDto.getArtificialPersonIdcard());
				verDto.setChangeFieldId("ARTIFICIAL_PERSON_IDCARD");
				verDto.setChangeTableId("MEMBER_COMPANY_INFO");
				verDto.setContentName("公司法人证件号码");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getOperatorId());
				verDto.setOperatorName(dto.getOperatorName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getMemberId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);
			}

			MemberStatusInfo statusInfo = new MemberStatusInfo();
			statusInfo.setVerifyTime(trancDate);
			statusInfo.setSyncKey(KeygenGenerator.getUidKey());
			statusInfo.setVerifyId(Long.valueOf(0));
			statusInfo.setSyncErrorMsg("");
			statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
			statusInfo.setModifyId(dto.getOperatorId());
			statusInfo.setModifyName(dto.getOperatorName());
			statusInfo.setModifyTime(trancDate);
			statusInfo.setMemberId(dto.getMemberId());
			statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
			statusInfo.setCreateId(dto.getOperatorId());
			statusInfo.setCreateName(dto.getOperatorName());
			statusInfo.setCreateTime(trancDate);

			if (verDtoList.size() > 0) {
				memberBaseOperationDAO.insertVerifyInfo(verDtoList);
				MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseById(dto.getMemberId(),
						GlobalConstant.IS_BUYER);
				if (GlobalConstant.STATUS_YES.equals(baseInfo.getStatus())) {
					baseInfo.setArtificialPersonIdcard(dto.getArtificialPersonIdcard());
					baseInfo.setArtificialPersonName(dto.getArtificialPersonName());
					baseInfo.setCompanyName(dto.getCompanyName());
					YijifuCorporateCallBackDTO payBack = downToYijifuModify(baseInfo, oldDto);
					if (null == payBack.getResultCode() || !"EXECUTE_SUCCESS".equals(payBack.getResultCode())) {
						String detail = payBack.getResultDetail();
						if (null != detail
								&& (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else
							if (null != payBack.getResultMessage() && (payBack.getResultMessage().indexOf("certNo") >= 0
									|| payBack.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else {
							rs.addErrorMessage(detail);
						}
						rs.setResultMessage("fail");
						rs.setResult(false);
						return rs;
					}
				}

			}

			memberBaseOperationDAO.deleteMemberStatus(statusInfo);
			memberBaseOperationDAO.insertMemberStatus(statusInfo);
			memberBaseOperationDAO.updateMemberCompanyInfo(baseDto);
			memberBaseOperationDAO.updateMemberBaseInfo(baseDto);
			rs.setResult(true);
			rs.setResultMessage("success");

		} catch (Exception e) {
			rs.setResult(false);
			rs.setResultMessage("fail");
			rs.addErrorMessage("更新异常");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberBaseInfo=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员金融信息
	 */
	@Override
	public ExecuteResult<List<MemberBackupContactInfo>> getMemberBackupContactInfo(Long id) {
		ExecuteResult<List<MemberBackupContactInfo>> rs = new ExecuteResult<List<MemberBackupContactInfo>>();
		try {
			List<MemberBackupContactInfo> memberBackupContactInfo = memberBaseOperationDAO.getBackupContactById(id);
			if (null != memberBackupContactInfo) {
				rs.setResult(memberBackupContactInfo);
			} else {
				rs.setResultMessage("查询没有数据");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getMemberBackupContactInfo=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员证件照信息
	 */
	@Override
	public ExecuteResult<MemberLicenceInfo> selectMemberLicenceInfoById(Long id) {
		ExecuteResult<MemberLicenceInfo> rs = new ExecuteResult<MemberLicenceInfo>();
		try {
			MemberLicenceInfo memberLicenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(id);
			if (null != memberLicenceInfo) {
				rs.setResult(memberLicenceInfo);
			} else {
				rs.setResultMessage("查询没有数据");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->selectMemberLicenceInfoById=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员拓展信息
	 */
	@Override
	public ExecuteResult<MemberExtendInfo> queryMemberExtendInfoById(Long id) {
		ExecuteResult<MemberExtendInfo> rs = new ExecuteResult<MemberExtendInfo>();
		try {
			MemberExtendInfo memberExtendInfo = memberBaseOperationDAO.queryMemberExtendInfoById(id);
			if (null != memberExtendInfo) {
				rs.setResult(memberExtendInfo);
			} else {
				rs.setResultMessage("查询没有数据");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->queryMemberExtendInfoById=" + e);
		}
		return rs;
	}

	/**
	 * 会员注册审核
	 */
	@Override
	public ExecuteResult<Boolean> saveVerifyInfo(MemberBaseInfoDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			VerifyResultDTO verifyResultDTO = memberBaseOperationDAO.selectVerifyResult(dto.getId(),
					GlobalConstant.INFO_TYPE_VERIFY);
			if (null != verifyResultDTO
					&& verifyResultDTO.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_WAIT)) {
				boolean isSendSms = false;// 是否发送短信
				String smsType = "";// 短信sms类型

				VerifyInfo verufyInfo = new VerifyInfo();// 审核信息
				MemberStatusInfo statusInfo = new MemberStatusInfo();// 运营商审核状态信息
				MemberStatusInfo cooperateStatusInfo = new MemberStatusInfo();// 供应商状态审核信息
				MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberBaseInfoById(dto.getId(),
						GlobalConstant.IS_BUYER);
				String oldCompanyName = oldDto.getCompanyName();

				List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
				VerifyDetailInfo verDto = null;
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date trancDate = format.parse(format.format(date));
				Date defaultDate = format.parse("0000-00-00 00:00:00");
				dto.setModifyTime(trancDate);
				if (null != dto.getVerifyStatus()
						&& dto.getVerifyStatus().equals(GlobalConstant.VERIFY_STATUS_ACCESS)) {
					dto.setStatus(GlobalConstant.STATUS_YES);

					BuyerGradeInfoDTO memberGradeModel = new BuyerGradeInfoDTO();
					memberGradeModel.setBuyerId(dto.getId());
					memberGradeModel.setBuyerGrade("1");
					memberGradeModel.setPointGrade(1l);
					memberGradeModel.setCreateId(dto.getCreateId());
					memberGradeModel.setCreateName("SYS");
					memberGradeModel.setModifyId(dto.getCreateId());
					memberGradeModel.setModifyName("SYS");
					memberGradeDAO.deleteGrade(dto.getId());
					memberGradeService.insertGrade(memberGradeModel);
				} else {
					dto.setStatus(GlobalConstant.STATUS_NO);
				}

				statusInfo.setSyncErrorMsg(dto.getRemark());
				statusInfo.setVerifyStatus(dto.getVerifyStatus());
				statusInfo.setModifyId(dto.getModifyId());
				statusInfo.setModifyName(dto.getModifyName());
				statusInfo.setSyncKey("");
				statusInfo.setModifyTime(trancDate);
				statusInfo.setMemberId(dto.getId());
				statusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY);
				// statusInfo.setVerifyId(verifyResultDTO.getVerifyId());
				statusInfo.setVerifyTime(trancDate);
				statusInfo.setSyncKey(KeygenGenerator.getUidKey());
				statusInfo.setModifyTime(trancDate);
				statusInfo.setCreateId(dto.getModifyId());
				statusInfo.setCreateName(dto.getModifyName());
				statusInfo.setCreateTime(trancDate);

				cooperateStatusInfo.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
				cooperateStatusInfo.setModifyId(dto.getModifyId());
				cooperateStatusInfo.setModifyName(dto.getModifyName());
				cooperateStatusInfo.setModifyTime(trancDate);
				cooperateStatusInfo.setMemberId(dto.getId());
				cooperateStatusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
				cooperateStatusInfo.setCreateId(dto.getModifyId());
				cooperateStatusInfo.setCreateName(dto.getModifyName());
				cooperateStatusInfo.setCreateTime(trancDate);
				cooperateStatusInfo.setSyncKey(KeygenGenerator.getUidKey());
				cooperateStatusInfo.setVerifyTime(defaultDate);
				cooperateStatusInfo.setVerifyId(verifyResultDTO.getVerifyId());
				cooperateStatusInfo.setSyncErrorMsg("");

				verufyInfo.setId(verifyResultDTO.getVerifyId());
				verufyInfo.setCreateId(dto.getModifyId());
				verufyInfo.setCreateName(dto.getModifyName());
				verufyInfo.setCreateTime(trancDate);
				verufyInfo.setModifyId(dto.getModifyId());
				verufyInfo.setModifyName(dto.getModifyName());
				verufyInfo.setModifyTime(trancDate);
				verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verufyInfo.setRecordId(dto.getId());
				verufyInfo.setRemark(dto.getRemark());
				verufyInfo.setVerifierId(dto.getModifyId());
				verufyInfo.setVerifierName(dto.getModifyName());
				verufyInfo.setVerifyTime(trancDate);
				verufyInfo.setVerifyStatus(dto.getVerifyStatus());
				verufyInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);

				memberModifyVerifyDAO.updateVerifyInfo(verufyInfo);
				verDto = new VerifyDetailInfo();// 会员审核状态审核履历
				String afterChangeVerifyDesc = "";
				if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(dto.getVerifyStatus())) {
					afterChangeVerifyDesc = "审核通过";

					// 下行到易极付
					YijifuCorporateCallBackDTO payBack = downToYijifu(dto);
					if (null == payBack.getResultCode() || !"EXECUTE_SUCCESS".equals(payBack.getResultCode())) {
						String detail = payBack.getResultDetail();
						if (null != detail
								&& (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else
							if (null != payBack.getResultMessage() && (payBack.getResultMessage().indexOf("certNo") >= 0
									|| payBack.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else {
							rs.addErrorMessage(detail);
						}
						rs.setResultMessage("fail");
						rs.setResult(false);
						return rs;
					}
					dto.setRealNameStatus("1");// 统一改成未实名
					String accountNo = "0";
					if (null != payBack.getAccountNo()) {
						accountNo = payBack.getAccountNo();
					}
					dto.setAccountNo(accountNo);
					dto.setCompanyId(oldDto.getCompanyId());
					memberBaseOperationDAO.updateMemberCompanyPayInfo(dto);// 更新公司支付信息
					oldDto.setModifyId(dto.getModifyId());
					oldDto.setModifyName(dto.getModifyName());

					List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO.getMemberInfoByCompanyCode(
							GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
					if (rsList.size() > 0) {
						MemberBaseInfoDTO sellerInfo = rsList.get(0);
						if (sellerInfo.getId().longValue() != oldDto.getBelongSellerId().longValue()) {// 插入供应商审批
							verufyInfo.setBelongSellerId(oldDto.getBelongSellerId());
							verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
							verufyInfo.setVerifyStatus("1");
							memberBaseOperationDAO.deleteMemberStatus(cooperateStatusInfo);
							memberBaseOperationDAO.insertMemberStatus(cooperateStatusInfo);

							memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
							statusInfo.setVerifyId(verufyInfo.getId());
						} else {
							// 生成用户
							CustomerDTO customerDTO = new CustomerDTO();
							ExecuteResult<UserDTO> user = userExportService.queryUserByLoginId(oldDto.getMemberCode());
							if (user.getResult() != null) {
								customerDTO.setLoginId(oldDto.getMemberCode());
								customerDTO.setMobile(oldDto.getArtificialPersonMobile());
								customerDTO.setPassword(oldDto.getArtificialPersonMobile());
								customerDTO.setName(oldDto.getCompanyName());
								customerDTO.setIsVmsInnerUser(false);
								customerDTO.setCompanyId(dto.getId());
								customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
								customerService.editCustomer(customerDTO, dto.getModifyId());
							} else {
								customerDTO.setLoginId(oldDto.getMemberCode());
								customerDTO.setMobile(oldDto.getArtificialPersonMobile());
								customerDTO.setPassword(oldDto.getArtificialPersonMobile());
								customerDTO.setName(oldDto.getCompanyName());
								customerDTO.setIsVmsInnerUser(false);
								customerDTO.setCompanyId(dto.getId());
								customerDTO.setDefaultContact(GlobalConstant.FLAG_YES);
								customerService.addCustomer(customerDTO, dto.getModifyId());
							}

							dto.setCanMallLogin(GlobalConstant.FLAG_YES);// 能登陆
							saveBelongBoxRelation(oldDto);// 保存归属关系与包厢关系
							dto.setBelongManagerId(oldDto.getBelongManagerId());
							verufyInfo.setBelongSellerId(rsList.get(0).getId());
							dto.setCurBelongManagerId(oldDto.getCurBelongManagerId());
							dto.setBecomeMemberTime(trancDate);
							memberBaseOperationDAO.updateMemberIsvalid(dto);

							memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
							statusInfo.setVerifyId(verufyInfo.getId());

							// 插入erp下行状态
							statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
							statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
							statusInfo.setSyncKey(KeygenGenerator.getUidKey());
							memberBaseOperationDAO.deleteMemberStatus(statusInfo);
							memberBaseOperationDAO.insertMemberStatus(statusInfo);
							isSendSms = true;
							smsType = GlobalConstant.SMSTYPE_VERIFY_SUCCESS;

							// 关联交易名单

							TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
							transactionRelationDTO.setBuyerName(oldDto.getCompanyName());
							ExecuteResult<TransactionRelationDTO> executeResult = transactionRelationService
									.getSingleTransactionRelationByParams(transactionRelationDTO);
							if (executeResult.getResult() != null) {
								TransactionRelationDTO transactionRelation = executeResult.getResult();
								transactionRelationDTO.setId(transactionRelation.getId());
								transactionRelationDTO.setBuyerCode(oldDto.getMemberCode());
								transactionRelationDTO.setIsExist("1");//1.true 0.false
								transactionRelationDTO.setModifyId(dto.getModifyId()+"");
								transactionRelationDTO.setModifyName(dto.getModifyName());
								transactionRelationDTO.setModifyTime(new Date());
								transactionRelationService.updateTransactionRelation(transactionRelationDTO);
							}
						}
					} else {
						verufyInfo.setBelongSellerId(oldDto.getBelongSellerId());
						verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
						verufyInfo.setVerifyStatus("1");
						memberBaseOperationDAO.deleteMemberStatus(cooperateStatusInfo);
						memberBaseOperationDAO.insertMemberStatus(cooperateStatusInfo);

						memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
						statusInfo.setVerifyId(verufyInfo.getId());
					}

				} else {
					if ("4".equals(dto.getVerifyStatus())) {
						dto.setCompanyName(oldCompanyName + "终止");
						dto.setArtificialPersonMobile(oldDto.getArtificialPersonMobile() + "终止");
						dto.setBuyerSellerType(GlobalConstant.IS_BUYER);
						memberBaseOperationDAO.updateMemberCompanyInfo(dto);

					}
					memberBaseOperationDAO.updateMemberIsvalid(dto);
					isSendSms = true;
					afterChangeVerifyDesc = "审核不通过";
					smsType = GlobalConstant.SMSTYPE_VERIFY_FAIL;

					memberBaseOperationDAO.saveVerifyInfo(verufyInfo);
					statusInfo.setVerifyId(verufyInfo.getId());
				}

				verDto.setAfterChangeDesc(afterChangeVerifyDesc);
				verDto.setBeforeChangeDesc("待审核");
				verDto.setAfterChange(dto.getVerifyStatus());
				verDto.setBeforeChange(GlobalConstant.VERIFY_STATUS_WAIT);
				verDto.setChangeFieldId("VERIFY_STATUS");
				verDto.setChangeTableId("MEMBER_STATUS_INFO");
				verDto.setContentName("会员注册审核");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getModifyId());
				verDto.setOperatorName(dto.getModifyName());
				verDto.setVerifyId(verufyInfo.getId());
				verDto.setRecordId(dto.getId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);

				statusInfo.setVerifyStatus(dto.getVerifyStatus());
				statusInfo.setSyncKey(null);
				statusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY);
				memberBaseOperationDAO.saveMemberStatusInfo(statusInfo);// 更新会员运营审核状态
				memberBaseOperationDAO.insertVerifyInfo(verDtoList);// 插入履历信息

				if (isSendSms) {
					sendMemberSms(oldDto.getArtificialPersonMobile(), smsType);
				}

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
			logger.error("MemberBaseInfoServiceImpl----->saveVerifyInfo=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#queryMember(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryMemberName(Long compid, String memberName) {
		ExecuteResult<List<MemberImportSuccInfoDTO>> rs = new ExecuteResult<List<MemberImportSuccInfoDTO>>();
		try {
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.selectMemberName(compid, memberName);
			rs.setResult(list);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#queryAllMemberName(java
	 * .lang.String)
	 */
	@Override
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> queryAllMemberName(String memberName, Pager pager) {
		ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> rs = new ExecuteResult<DataGrid<MemberImportSuccInfoDTO>>();
		DataGrid<MemberImportSuccInfoDTO> dg = new DataGrid<MemberImportSuccInfoDTO>();
		try {
			Long count = memberBaseOperationDAO.selectAllMemberNameCount(memberName);
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.selectAllMemberName(memberName, pager);
			if (count > 0) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->queryAllMemberName=" + e);
		}

		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#queryAllMemberName(java
	 * .lang.String)
	 */
	@Override
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> queryAllSupplierName(String memberName, String type,
			Pager pager) {
		ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> rs = new ExecuteResult<DataGrid<MemberImportSuccInfoDTO>>();
		DataGrid<MemberImportSuccInfoDTO> dg = new DataGrid<MemberImportSuccInfoDTO>();
		try {
			Long count = memberBaseOperationDAO.selectAllSupplierNameCount(memberName, type);
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.selectAllSupplierName(memberName, type, pager);
			if (count > 0) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->queryAllSupplierName=" + e);
		}

		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#querySellerName(java.
	 * lang.Long)
	 */
	@Override
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerName(Long sellerId) {
		ExecuteResult<MemberImportSuccInfoDTO> rs = new ExecuteResult<MemberImportSuccInfoDTO>();
		try {
			MemberImportSuccInfoDTO dto = memberBaseOperationDAO.selectSellerName(sellerId);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	@SuppressWarnings("unused")
	@Override
	public ExecuteResult<String> insertMemberBaseRegisterInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();
		String emsg="";
        // 输入DTO的验证
        ValidateResult validateResult = ValidationUtils.validateEntity(memberBaseInfoRegisterDTO);
        // 有错误信息时返回错误信息
        if (validateResult.isHasErrors()) {
//            throw new MemberCenterException(MemberCenterCodeConst.INPUT_PARAMETER_ERROR,
//                   validateResult.getErrorMsg());
        	if(StringUtils.isNotBlank(validateResult.getErrorMsg()) && StringUtils.isNotBlank(validateResult.getErrorMsg().split(",")[0])){
        	      emsg=validateResult.getErrorMsg().split(",")[0].split(":")[1];
			      rs.addErrorMessage(emsg.trim());
        	}
			return rs;
        }
        if(StringUtils.isNotBlank(memberBaseInfoRegisterDTO.getCompanyName()) && checkCompanyNameUnique(memberBaseInfoRegisterDTO.getCompanyName(),0l)){
			rs.addErrorMessage("公司名称已经存在，请重新填写!");
			return rs;
        }
        
		try {
			boolean mobilecheck = checkMemberMobile(memberBaseInfoRegisterDTO.getArtificialPersonMobile(), 0l);
			if (mobilecheck) {
				rs.addErrorMessage("手机号已存在，请重新填写!");
				return rs;
			}
			// 拼接省市区镇详细地址
			if (memberBaseInfoRegisterDTO.getLocationTown() != null) {
				String locationAddr = memberBaseService.getAddressBaseByCode(
						memberBaseInfoRegisterDTO.getLocationTown()) + memberBaseInfoRegisterDTO.getLocationDetail();
				memberBaseInfoRegisterDTO.setLocationAddr(locationAddr);
			}
			String code = customerService.genMemberCode().getResult();
			if (code == null) {
				rs.setResultMessage("生成memberCode失败！");
				rs.addErrorMessage("生成memberCode失败！");
				return rs;
			}
			String cooperateVendor = memberBaseInfoRegisterDTO.getCooperateVendor();
			if (StringUtils.isNotBlank(cooperateVendor)) {
				Long belongSellerID = memberCompanyInfoDao
						.selectBelongSellerId(memberBaseInfoRegisterDTO.getCooperateVendor());
				memberBaseInfoRegisterDTO.setBelongSellerId(belongSellerID);
				memberBaseInfoRegisterDTO.setCurBelongSellerId(belongSellerID);
			} else {
				// 归属0801商家id
				List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO
						.getMemberInfoByCompanyCode(GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
				if (rsList != null && rsList.size() > 0) {
					Long belongSellerID = rsList.get(0).getId();
					memberBaseInfoRegisterDTO.setBelongSellerId(belongSellerID);
					memberBaseInfoRegisterDTO.setCurBelongSellerId(belongSellerID);
				}
			}

			memberBaseInfoRegisterDTO.setMemberCode(code);
			// String locationAddr =
			// memberBaseInfoRegisterDTO.setLocationAddr(locationAddr);

			// 添加是否异业判断
			if ("7".equals(memberBaseInfoRegisterDTO.getBusinessScope())) {
				memberBaseInfoRegisterDTO.setIsDiffIndustry(GlobalConstant.FLAG_NO);
			} else {
				memberBaseInfoRegisterDTO.setIsDiffIndustry(GlobalConstant.FLAG_YES);
			}
			memberBaseInfoDao.insertMemberBaseInfoRegister(memberBaseInfoRegisterDTO);
			Long memberId = memberBaseInfoRegisterDTO.getId();
			if (memberId != null) {
				memberBaseInfoRegisterDTO.setMemberId(memberId);
				memberBaseInfoRegisterDTO.setBuyerSellerType(1);
				memberBaseInfoDao.insertCompanyInfo(memberBaseInfoRegisterDTO);
				MemberConsigAddressDTO memberConsigAddressDto = new MemberConsigAddressDTO();
				memberConsigAddressDto.setInvoiceCompanyName(memberBaseInfoRegisterDTO.getCompanyName());
				memberConsigAddressDto.setInvoiceNotify(memberBaseInfoRegisterDTO.getCompanyName());
				memberConsigAddressDto.setMemberId(memberId);
				memberConsigAddressDto.setModifyId(memberBaseInfoRegisterDTO.getCreateId());
				memberConsigAddressDto.setModifyName(memberBaseInfoRegisterDTO.getCreateName());
				consigneeAddressDAO.insertInvoiceInfo(memberConsigAddressDto);
				// memberBaseInfoDao.insertMemberExtendInfo(memberBaseInfoRegisterDTO);
				memberBaseInfoDao.insertMemberLicenceInfoRegister(memberBaseInfoRegisterDTO);
				memberBaseInfoDao.insertMemberPersonInfo(memberBaseInfoRegisterDTO);
				// 添加会员状态，会员审批和会员审批详情
				memberBaseInfoDao.insertMemberStatusInfoRegister(memberId, memberBaseInfoRegisterDTO.getCreateId(),
						memberBaseInfoRegisterDTO.getCreateName(), "11", null);

				// belongRelationshipDTO.setMemberId(memberBaseInfoRegisterDTO.getMemberId());
				// belongRelationshipDTO.setModifyId(memberBaseInfoRegisterDTO.getModifyId());
				// belongRelationshipDTO.setModifyName(memberBaseInfoRegisterDTO.getModifyName());
				// memberBaseInfoDao.insertVerifyInfoRegister(belongRelationshipDTO,
				// "11");
				rs.setResult("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl----->insertMemberBaseInfo=" + e);
			rs.addErrorMessage("异常");
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseInfoDTO> queryMemberBaseInfoByMemberCode(String memberCode) {
		ExecuteResult<MemberBaseInfoDTO> result = new ExecuteResult<MemberBaseInfoDTO>();
		try {
			MemberBaseInfoDTO dto = memberBaseOperationDAO.selectMemberBaseInfoByMemberCode(memberCode);
			result.setResult(dto);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.addErrorMessage("查询失败");
			result.setResultMessage("fail");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#querySellerIdByName(
	 * java.lang.String)
	 */
	@Override
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerIdByName(String name) {
		ExecuteResult<MemberImportSuccInfoDTO> rs = new ExecuteResult<MemberImportSuccInfoDTO>();
		try {
			MemberImportSuccInfoDTO dto = memberBaseOperationDAO.querySellerIdByName(name);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#querySellerIdByCode(
	 * java.lang.String)
	 */
	@Override
	public ExecuteResult<MemberImportSuccInfoDTO> querySellerIdByCode(String code) {
		ExecuteResult<MemberImportSuccInfoDTO> rs = new ExecuteResult<MemberImportSuccInfoDTO>();
		try {
			MemberImportSuccInfoDTO dto = memberBaseOperationDAO.querySellerIdByCode(code);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> insertPasswordVerify(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO,
			String modifyStatus) {
		// TODO Auto-generated method stub
		ExecuteResult<String> rs = new ExecuteResult<String>();
		List<VerifyDetailInfoDTO> list = new ArrayList<VerifyDetailInfoDTO>();
		try {
			// 根据公司名称查询会员ID
			MemberCompanyInfoDTO memberCompanyInfoDTO = new MemberCompanyInfoDTO();
			memberCompanyInfoDTO.setCompanyName(memberBaseInfoRegisterDTO.getCompanyName());
			memberCompanyInfoDTO.setBuyerSellerType(1);
			List<MemberCompanyInfoDTO> memberCompanyInfoDTOList = memberCompanyInfoDao
					.searchVoidMemberCompanyInfo(memberCompanyInfoDTO);
			Long memberId = null;
			if (memberCompanyInfoDTOList != null && memberCompanyInfoDTOList.size() > 0) {
				if (memberCompanyInfoDTOList.size() == 1) {
					memberCompanyInfoDTO = memberCompanyInfoDTOList.get(0);
					memberId = new Long(memberCompanyInfoDTOList.get(0).getMemberId());
				} else {
					rs.addErrorMessage("查询条件结果不唯一");
				}
			}
			// 根据会员ID查询会员证件信息
			MemberLicenceInfo memberLicenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(memberId);
			// 下面是公用对象
			VerifyDetailInfoDTO companyNameVerifyDetail = new VerifyDetailInfoDTO();
			companyNameVerifyDetail.setAfterChange(memberBaseInfoRegisterDTO.getCompanyName());
			companyNameVerifyDetail.setAfterChangeDesc("修改后公司名称");
			companyNameVerifyDetail.setChangeFieldId("company_name");
			companyNameVerifyDetail.setChangeTableId("member_company_info");
			companyNameVerifyDetail.setBeforeChange(memberCompanyInfoDTO.getCompanyName());
			companyNameVerifyDetail.setBeforeChangeDesc("修改前公司名称");

			VerifyDetailInfoDTO artificialPersonNameverifyDetailInfo = new VerifyDetailInfoDTO();
			artificialPersonNameverifyDetailInfo.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonName());
			artificialPersonNameverifyDetailInfo.setAfterChangeDesc("修改后法人代表姓名");
			artificialPersonNameverifyDetailInfo.setChangeFieldId("artificial_person_name");
			artificialPersonNameverifyDetailInfo.setChangeTableId("member_company_info");
			artificialPersonNameverifyDetailInfo.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonName());
			artificialPersonNameverifyDetailInfo.setBeforeChangeDesc("修改前法人代表姓名");

			VerifyDetailInfoDTO artificialPersonMobileVerifyDetail = new VerifyDetailInfoDTO();
			artificialPersonMobileVerifyDetail.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonMobile());
			artificialPersonMobileVerifyDetail.setAfterChangeDesc("修改后法人手机号码");
			artificialPersonMobileVerifyDetail.setChangeFieldId("artificial_person_mobile");
			artificialPersonMobileVerifyDetail.setChangeTableId("member_company_info");
			artificialPersonMobileVerifyDetail.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonMobile());
			artificialPersonMobileVerifyDetail.setBeforeChangeDesc("修改前法人手机号码");
			// 未变更公司名称
			if ("1".equals(modifyStatus)) {
				list.add(companyNameVerifyDetail);
				list.add(artificialPersonNameverifyDetailInfo);
				VerifyDetailInfoDTO artificialPersonIdcardVerifyDetail = new VerifyDetailInfoDTO();
				artificialPersonIdcardVerifyDetail
						.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonIdcard());
				artificialPersonIdcardVerifyDetail.setAfterChangeDesc("修改后法人身份证号");
				artificialPersonIdcardVerifyDetail.setChangeFieldId("artificial_person_idcard");
				artificialPersonIdcardVerifyDetail.setChangeTableId("member_company_info");
				artificialPersonIdcardVerifyDetail.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonIdcard());
				artificialPersonIdcardVerifyDetail.setBeforeChangeDesc("修改前法人身份证号");
				list.add(artificialPersonIdcardVerifyDetail);
				VerifyDetailInfoDTO ArtificialPersonPicSrcVerifyDetail = new VerifyDetailInfoDTO();
				ArtificialPersonPicSrcVerifyDetail
						.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonPicSrc());
				ArtificialPersonPicSrcVerifyDetail.setAfterChangeDesc("修改后法人身份证正面");
				ArtificialPersonPicSrcVerifyDetail.setChangeFieldId("artificial_person_pic_src");
				ArtificialPersonPicSrcVerifyDetail.setChangeTableId("member_company_info");
				ArtificialPersonPicSrcVerifyDetail.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonPicSrc());
				ArtificialPersonPicSrcVerifyDetail.setBeforeChangeDesc("修改前法人身份证正面");
				list.add(ArtificialPersonPicSrcVerifyDetail);
				VerifyDetailInfoDTO artificialPersonPicBackSrcVerifyDetail = new VerifyDetailInfoDTO();
				artificialPersonPicBackSrcVerifyDetail
						.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonPicBackSrc());
				artificialPersonPicBackSrcVerifyDetail.setAfterChangeDesc("修改后法人身份证反面");
				artificialPersonPicBackSrcVerifyDetail.setChangeFieldId("artificial_person_pic_back_src");
				artificialPersonPicBackSrcVerifyDetail.setChangeTableId("member_company_info");
				artificialPersonPicBackSrcVerifyDetail
						.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonPicBackSrc());
				artificialPersonPicBackSrcVerifyDetail.setBeforeChangeDesc("修改前法人身份证反面");
				list.add(artificialPersonPicBackSrcVerifyDetail);
				VerifyDetailInfoDTO artificialPersonIdcardPicSrcVerifyDetail = new VerifyDetailInfoDTO();
				artificialPersonIdcardPicSrcVerifyDetail
						.setAfterChange(memberBaseInfoRegisterDTO.getArtificialPersonIdcardPicSrc());
				artificialPersonIdcardPicSrcVerifyDetail.setAfterChangeDesc("修改后法人手持身份证");
				artificialPersonIdcardPicSrcVerifyDetail.setChangeFieldId("artificial_person_idcard_pic_src");
				artificialPersonIdcardPicSrcVerifyDetail.setChangeTableId("member_company_info");
				artificialPersonIdcardPicSrcVerifyDetail
						.setBeforeChange(memberCompanyInfoDTO.getArtificialPersonIdcardPicSrc());
				artificialPersonIdcardPicSrcVerifyDetail.setBeforeChangeDesc("修改前法人手持身份证");
				list.add(artificialPersonIdcardPicSrcVerifyDetail);
				// 判断是否有营业执照0表示没有，1表示有
				if (memberBaseInfoRegisterDTO.getHasBusinessLicense() == 1) {
					VerifyDetailInfoDTO buyerBusinessLicensePicSrcVerifyDetail = new VerifyDetailInfoDTO();
					buyerBusinessLicensePicSrcVerifyDetail
							.setAfterChange(memberBaseInfoRegisterDTO.getBuyerBusinessLicensePicSrc());
					buyerBusinessLicensePicSrcVerifyDetail.setAfterChangeDesc("修改后会员营业执照图片地址");
					buyerBusinessLicensePicSrcVerifyDetail.setChangeFieldId("buyer_business_license_pic_src");
					buyerBusinessLicensePicSrcVerifyDetail.setChangeTableId("member_licence_info");
					if (memberLicenceInfo != null) {
						buyerBusinessLicensePicSrcVerifyDetail
								.setBeforeChange(memberLicenceInfo.getBuyerBusinessLicensePicSrc());
						buyerBusinessLicensePicSrcVerifyDetail.setBeforeChangeDesc("修改前会员营业执照图片地址");
					}
					list.add(buyerBusinessLicensePicSrcVerifyDetail);
				} else if (memberBaseInfoRegisterDTO.getHasBusinessLicense() == 0) {
					VerifyDetailInfoDTO buyerGuaranteeLicensePicSrcVerifyDetail = new VerifyDetailInfoDTO();
					buyerGuaranteeLicensePicSrcVerifyDetail
							.setAfterChange(memberBaseInfoRegisterDTO.getBuyerGuaranteeLicensePicSrc());
					buyerGuaranteeLicensePicSrcVerifyDetail.setAfterChangeDesc("修改后担保证明地址");
					buyerGuaranteeLicensePicSrcVerifyDetail.setChangeFieldId("buyer_guarantee_license_pic_src");
					buyerGuaranteeLicensePicSrcVerifyDetail.setChangeTableId("member_licence_info");
					if (memberLicenceInfo != null) {
						buyerGuaranteeLicensePicSrcVerifyDetail
								.setBeforeChange(memberLicenceInfo.getBuyerGuaranteeLicensePicSrc());
						buyerGuaranteeLicensePicSrcVerifyDetail.setBeforeChangeDesc("修改前担保证明地址");
					}
					list.add(buyerGuaranteeLicensePicSrcVerifyDetail);
				}
				list.add(artificialPersonMobileVerifyDetail);
				if (memberId != null) {
					memberBaseInfoRegisterDTO.setMemberId(memberId);
					// 添加一条审批记录
					BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();
					belongRelationshipDTO.setMemberId(memberId);
					belongRelationshipDTO.setModifyId(memberBaseInfoRegisterDTO.getModifyId());
					belongRelationshipDTO.setModifyName(memberBaseInfoRegisterDTO.getModifyName());
					Long num = memberBaseInfoDao.insertVerifyInfoRegister(belongRelationshipDTO, "1");
					if (num != null) {
						// 判断memberStatusinfo是否已经存在，存在就修改考核状态
						VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(memberId, "1", null);
						if (v != null) {
							memberBaseInfoRegisterDTO.setVerifyInfoId(belongRelationshipDTO.getVerifyId());
							memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "1");
						} else {
							memberBaseInfoDao.insertMemberStatusInfoRegister(memberId,
									memberBaseInfoRegisterDTO.getCreateId(), memberBaseInfoRegisterDTO.getCreateName(),
									"1", belongRelationshipDTO.getVerifyId());
						}
						// 添加审批详情记录
						int size = list.size();
						for (int i = 0; i < size; i++) {
							list.get(i).setModifyType("1");
							list.get(i).setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
							list.get(i).setOperatorId(memberBaseInfoRegisterDTO.getModifyId());
							list.get(i).setOperatorName(memberBaseInfoRegisterDTO.getModifyName());
							list.get(i).setVerifyId(belongRelationshipDTO.getVerifyId());
							list.get(i).setRecordId(memberId);
							memberBaseInfoDao.insertVerifyDetailInfo(list.get(i));
						}
						rs.setResult("success");
					}
				} else {
					rs.addErrorMessage("fail");
					rs.setResultMessage("会员ID不能为空");
				}
			} else if ("2".equals(modifyStatus)) {
				// 已变更公司名称
				list.add(companyNameVerifyDetail);
				list.add(artificialPersonNameverifyDetailInfo);
				list.add(artificialPersonMobileVerifyDetail);
				// 判断是否有营业执照
				if (memberBaseInfoRegisterDTO.getHasBusinessLicense() == 1) {
					VerifyDetailInfoDTO businessLicenseCertificatePicSrcVerifyDetail = new VerifyDetailInfoDTO();
					businessLicenseCertificatePicSrcVerifyDetail
							.setAfterChange(memberBaseInfoRegisterDTO.getBusinessLicenseCertificatePicSrc());
					businessLicenseCertificatePicSrcVerifyDetail.setAfterChangeDesc("修改后营业执照变更证明图片地址");
					businessLicenseCertificatePicSrcVerifyDetail
							.setChangeFieldId("business_license_certificate_pic_src");
					businessLicenseCertificatePicSrcVerifyDetail.setChangeTableId("member_licence_info");
					if (memberLicenceInfo != null) {
						businessLicenseCertificatePicSrcVerifyDetail
								.setBeforeChange(memberLicenceInfo.getBusinessLicenseCertificatePicSrc());
						businessLicenseCertificatePicSrcVerifyDetail.setBeforeChangeDesc("修改前营业执照变更证明图片地址");
					}
					list.add(businessLicenseCertificatePicSrcVerifyDetail);

					VerifyDetailInfoDTO buyerBusinessLicensePicSrcVerifyDetail = new VerifyDetailInfoDTO();
					buyerBusinessLicensePicSrcVerifyDetail
							.setAfterChange(memberBaseInfoRegisterDTO.getBuyerBusinessLicensePicSrc());
					buyerBusinessLicensePicSrcVerifyDetail.setAfterChangeDesc("修改后会员营业执照图片地址");
					buyerBusinessLicensePicSrcVerifyDetail.setChangeFieldId("buyer_business_license_pic_src");
					buyerBusinessLicensePicSrcVerifyDetail.setChangeTableId("member_licence_info");
					if (memberLicenceInfo != null) {
						buyerBusinessLicensePicSrcVerifyDetail
								.setBeforeChange(memberLicenceInfo.getBuyerBusinessLicensePicSrc());
						buyerBusinessLicensePicSrcVerifyDetail.setBeforeChangeDesc("修改前会员营业执照图片地址");
					}
					list.add(buyerBusinessLicensePicSrcVerifyDetail);
				} else if (memberBaseInfoRegisterDTO.getHasBusinessLicense() == 0) {
					VerifyDetailInfoDTO buyerGuaranteeLicensePicSrcVerifyDetail = new VerifyDetailInfoDTO();
					buyerGuaranteeLicensePicSrcVerifyDetail
							.setAfterChange(memberBaseInfoRegisterDTO.getBuyerGuaranteeLicensePicSrc());
					buyerGuaranteeLicensePicSrcVerifyDetail.setAfterChangeDesc("担保证明地址");
					buyerGuaranteeLicensePicSrcVerifyDetail.setChangeFieldId("buyer_guarantee_license_pic_src");
					buyerGuaranteeLicensePicSrcVerifyDetail.setChangeTableId("member_licence_info");
					if (memberLicenceInfo != null) {
						buyerGuaranteeLicensePicSrcVerifyDetail
								.setBeforeChange(memberLicenceInfo.getBuyerGuaranteeLicensePicSrc());
						buyerGuaranteeLicensePicSrcVerifyDetail.setBeforeChangeDesc("修改前担保证明地址");
					}
					list.add(buyerGuaranteeLicensePicSrcVerifyDetail);
				}
				if (memberId != null) {
					memberBaseInfoRegisterDTO.setMemberId(memberId);

					// 添加一条审批记录
					BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();
					belongRelationshipDTO.setMemberId(memberId);
					belongRelationshipDTO.setModifyId(memberBaseInfoRegisterDTO.getModifyId());
					belongRelationshipDTO.setModifyName(memberBaseInfoRegisterDTO.getModifyName());
					Long num = memberBaseInfoDao.insertVerifyInfoRegister(belongRelationshipDTO, "2");
					if (num != null) {
						// 判断memberStatusinfo是否已经存在，存在就修改考核状态
						VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(memberId, "2", null);
						if (v != null) {
							memberBaseInfoRegisterDTO.setVerifyInfoId(belongRelationshipDTO.getVerifyId());
							memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "2");
						} else {
							memberBaseInfoDao.insertMemberStatusInfoRegister(memberId,
									memberBaseInfoRegisterDTO.getCreateId(), memberBaseInfoRegisterDTO.getCreateName(),
									"2", belongRelationshipDTO.getVerifyId());
						}
						// 添加审批详情记录
						int size = list.size();
						for (int i = 0; i < size; i++) {
							list.get(i).setModifyType("2");
							list.get(i).setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
							list.get(i).setOperatorId(memberBaseInfoRegisterDTO.getModifyId());
							list.get(i).setOperatorName(memberBaseInfoRegisterDTO.getModifyName());
							list.get(i).setVerifyId(belongRelationshipDTO.getVerifyId());
							list.get(i).setRecordId(memberId);
							memberBaseInfoDao.insertVerifyDetailInfo(list.get(i));
						}
						rs.setResult("success");
						rs.setResultMessage("success");
					}

				} else {
					rs.addErrorMessage("fail");
					rs.setResultMessage("会员ID不能为空");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			logger.error("MemberBaseInfoServiceImpl======>insertPasswordVerify" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> updateMobilePhoneVerify(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO,
			Long userId) {
		// TODO Auto-generated method stub
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			List<VerifyDetailInfo> list = new ArrayList<VerifyDetailInfo>();
			Long memberId = memberBaseInfoRegisterDTO.getMemberId();
			Long modifyId = memberBaseInfoRegisterDTO.getModifyId();
			String modifyName = memberBaseInfoRegisterDTO.getModifyName();
			String artificialPersonMobile = memberBaseInfoRegisterDTO.getArtificialPersonMobile();
			String buyerGuaranteeLicensePicSrc = memberBaseInfoRegisterDTO.getBuyerGuaranteeLicensePicSrc();
			// 担保证明审核
			if (buyerGuaranteeLicensePicSrc != null) {
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date trancDate = format.parse(format.format(date));

				if (memberId != null) {
					// 更改审批状态默认为待审批和类型为修改手机号
					// memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO,
					// "3");
					// 查询会员手机号和担保证明数据
					MemberBaseDTO memberBaseDTO = new MemberBaseDTO();
					memberBaseDTO.setBuyerSellerType("1");
					memberBaseDTO.setMemberId(memberId.toString());
					MemberBaseDTO memberBase = memberBaseDAO.queryMemberBaseInfo(memberBaseDTO);
					String oldArtificialPersonMobile = "";
					String oldbuyerGuaranteeLicensePicSrc = "";
					if (memberBase != null) {
						// 原始的会员手机号和担保证明图片地址
						oldArtificialPersonMobile = memberBase.getArtificialPersonMobile();
						oldbuyerGuaranteeLicensePicSrc = memberBase.getBuyerGuaranteeLicensePicSrc();
					}
					// 添加审批状态
					BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();
					belongRelationshipDTO.setMemberId(memberId);
					belongRelationshipDTO.setModifyId(modifyId);
					belongRelationshipDTO.setModifyName(modifyName);
					Long num = memberBaseInfoDao.insertVerifyInfoRegister(belongRelationshipDTO, "3");
					if (num != null) {
						// 判断memberStatusinfo是否已经存在，存在就修改考核状态
						VerifyInfoDTO v = memberBaseInfoDao.selectMemberStatusInfo(memberId, "3", null);
						if (v != null) {
							memberBaseInfoRegisterDTO.setVerifyInfoId(belongRelationshipDTO.getVerifyId());
							memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "3");
						} else {
							memberBaseInfoDao.insertMemberStatusInfoRegister(memberId,
									memberBaseInfoRegisterDTO.getCreateId(), memberBaseInfoRegisterDTO.getCreateName(),
									"3", belongRelationshipDTO.getVerifyId());
						}
						// 手机号审核
						VerifyDetailInfo vdid = new VerifyDetailInfo();
						vdid.setRecordId(memberId);
						vdid.setVerifyId(belongRelationshipDTO.getVerifyId());
						vdid.setModifyType("3");
						vdid.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
						vdid.setOperatorId(modifyId);
						vdid.setOperatorName(modifyName);
						vdid.setContentName("手机号修改");
						vdid.setChangeTableId("member_company_info");
						vdid.setChangeFieldId("artificial_person_mobile");
						vdid.setBeforeChange(oldArtificialPersonMobile);
						vdid.setBeforeChangeDesc("改变前手机号码可用");
						vdid.setAfterChange(artificialPersonMobile.toString());
						vdid.setAfterChangeDesc("审批完成后手机号码可用");
						vdid.setOperateTime(trancDate);
						list.add(vdid);
						VerifyDetailInfo vdidGuaranteeLicense = new VerifyDetailInfo();
						vdidGuaranteeLicense.setRecordId(memberId);
						vdidGuaranteeLicense.setVerifyId(belongRelationshipDTO.getVerifyId());
						vdidGuaranteeLicense.setModifyType("3");
						vdidGuaranteeLicense.setOperatorId(modifyId);
						vdidGuaranteeLicense.setOperatorName(modifyName);
						vdidGuaranteeLicense.setContentName("手机号修改中担保会员修改");
						vdidGuaranteeLicense.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
						vdidGuaranteeLicense.setChangeTableId("member_licence_info");
						vdidGuaranteeLicense.setChangeFieldId("buyer_guarantee_license_pic_src");
						vdidGuaranteeLicense.setBeforeChange(oldbuyerGuaranteeLicensePicSrc);
						vdidGuaranteeLicense.setBeforeChangeDesc("改变前担保证明可用");
						vdidGuaranteeLicense.setAfterChange(buyerGuaranteeLicensePicSrc);
						vdidGuaranteeLicense.setAfterChangeDesc("审批完成后担保证明可用");
						vdidGuaranteeLicense.setOperateTime(trancDate);
						list.add(vdidGuaranteeLicense);
						int i = memberBaseOperationDAO.insertVerifyInfo(list);
						if (i > 0) {
							rs.setResult("success");
						} else {
							rs.setResultMessage("fail");
						}
					}
				} else {
					rs.setResultMessage("当前会员会空");
				}
			} else {
				downToYijifuPhoneModify(memberBaseInfoRegisterDTO);
				// 修改手机号
				memberBaseInfoRegisterDTO.setBuyerSellerType(1);
				memberBaseInfoDao.updateMemberCompanyInfo(memberBaseInfoRegisterDTO);
				// 5.调用用户中心接口通知保存新手机号码
				CustomerDTO customerDTO = new CustomerDTO();
				customerDTO.setMobile(artificialPersonMobile);
				customerDTO.setUserId(userId);
				// 5.调用用户中心接口通知保存新手机号码
				customerService.editCustomerMobile(customerDTO, new Long(modifyId));
				rs.setResult("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			logger.error("MemberBaseInfoServiceImpl======>updatePasswordVerify" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseInfoRegisterDTO> selectRegisterProgress(Long memberId) {
		ExecuteResult<MemberBaseInfoRegisterDTO> rs = new ExecuteResult<MemberBaseInfoRegisterDTO>();
		MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO = new MemberBaseInfoRegisterDTO();
		try {
			// 返回运营审核注册数据
			memberBaseInfoRegisterDTO = memberBaseInfoDao.queryMemberRegisterInfo(memberId,
					GlobalConstant.INFO_TYPE_VERIFY, "1");
			// 获取运营审核审批信息
			VerifyResultDTO verifyResultDTO = memberBaseOperationDAO
					.selectVerifyResultById(memberBaseInfoRegisterDTO.getVerifyInfoId());
			if (verifyResultDTO != null) {
				memberBaseInfoRegisterDTO.setRemark(verifyResultDTO.getRemark());
			}
			if (GlobalConstant.INFO_TYPE_VERIFY.equals(memberBaseInfoRegisterDTO.getModifyType())) {
				if ("3".equals(memberBaseInfoRegisterDTO.getStatus())) {
					rs.setResult(memberBaseInfoRegisterDTO);
					rs.setResultMessage("运营审核被驳回");
				} else if ("2".equals(memberBaseInfoRegisterDTO.getStatus())) {
					// 返回供应商审核注册数据
					MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTOcooperate = memberBaseInfoDao
							.queryMemberRegisterInfo(memberId, GlobalConstant.INFO_TYPE_VERIFY_COOPERATE, "1");
					if (memberBaseInfoRegisterDTOcooperate != null) {
						// 获取供应商审核审批信息
						VerifyResultDTO verifyResultDTO1 = memberBaseOperationDAO
								.selectVerifyResultById(memberBaseInfoRegisterDTOcooperate.getVerifyInfoId());
						if (verifyResultDTO1 != null) {
							memberBaseInfoRegisterDTOcooperate.setRemark(verifyResultDTO1.getRemark());
						}
						if ("3".equals(memberBaseInfoRegisterDTOcooperate.getStatus())) {
							rs.setResult(memberBaseInfoRegisterDTOcooperate);
							rs.setResultMessage("供应商审核被驳回");
						} else if ("2".equals(memberBaseInfoRegisterDTOcooperate.getStatus())) {
							rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
							rs.setResultMessage("供应商审核通过");
							rs.setResult(memberBaseInfoRegisterDTOcooperate);
						} else {
							rs.setResult(memberBaseInfoRegisterDTOcooperate);
							rs.setResultMessage("供应商审核待审批");
						}
					} else {
						rs.setResult(memberBaseInfoRegisterDTO);
						rs.setResultMessage("运营审核通过");
					}
				} else if ("4".equals(memberBaseInfoRegisterDTO.getStatus())) {
					rs.setResult(memberBaseInfoRegisterDTO);
					rs.setResultMessage("运营审核终止");
				} else {
					rs.setResult(memberBaseInfoRegisterDTO);
					rs.setResultMessage("运营审核待审批");
				}
			}

		} catch (Exception e) {
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl======>selectArtificialMobilePhone=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");

		}
		return rs;
	}

	@Override
	public ExecuteResult<String> selectMobilePhoneMemberStatusInfo(Long memberId) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			VerifyInfoDTO verifyInfoDTO = memberBaseInfoDao.selectMemberStatusInfo(memberId, "3", null);
			if (verifyInfoDTO != null) {
				String status = verifyInfoDTO.getVerifyStatus();
				rs.setResult(status);
				rs.setResultMessage("success");
			} else {
				// 没有查到数据返回01
				rs.setResult("01");
				rs.addErrorMessage("fail");
				rs.setResultMessage("没查到数据返回01");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl======>selectMobilePhoneMemberStatusInfo=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBackupContactInfo> getContactId(Long contactId) {
		ExecuteResult<MemberBackupContactInfo> rs = new ExecuteResult<MemberBackupContactInfo>();
		try {
			MemberBackupContactInfo memberBackupContactInfo = memberBaseOperationDAO.getContactId(contactId);
			String verifyStatus = memberBuyerDao.getMemberVerifyStatus(contactId, "28");
			if (null != verifyStatus && GlobalConstant.VERIFY_STATUS_WAIT.equals(verifyStatus)) {// 如果备份联系人修改
				List<MemberBuyerVerifyDetailInfoDTO> dtlist = memberBuyerDao
						.queryVerifyInfo(memberBackupContactInfo.getMemberId(), "28");
				if (dtlist != null && dtlist.size() > 0) {
					List<MemberBuyerVerifyDetailInfoDTO> dtlistNew = removeRepeatVerify(dtlist);
					for (MemberBuyerVerifyDetailInfoDTO dt : dtlistNew) {
						if ("contact_name".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactName(dt.getAfterChange());
						}
						if ("contact_mobile".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactMobile(dt.getAfterChange());
						}
						if ("contact_idcard".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactIdcard(dt.getAfterChange());
						}
						if ("contact_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactIdcardPicSrc(dt.getAfterChange());
						}
						if ("contact_idcard_pic_back_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactIdcardPicBackSrc(dt.getAfterChange());
						}
						if ("contact_person_idcard_pic_src".equalsIgnoreCase(dt.getChangeFieldId())) {
							memberBackupContactInfo.setContactPersonIdcardPicSrc(dt.getAfterChange());
						}

					}
				}
			}
			if (null != memberBackupContactInfo) {
				rs.setResult(memberBackupContactInfo);
			} else {
				rs.setResultMessage("查询没有数据");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("查询异常");
			rs.addErrorMessage("error");
			logger.error("MemberBaseInfoServiceImpl----->getContactId=" + e);
		}
		return rs;

	}

	@Override
	public ExecuteResult<List<VerifyDetailInfoDTO>> selectVerifyDetailInfoDTOList(Long memberId) {
		ExecuteResult<List<VerifyDetailInfoDTO>> rs = new ExecuteResult<List<VerifyDetailInfoDTO>>();
		try {
			VerifyInfoDTO verifyInfoDTO = memberBaseInfoDao.selectMemberStatusInfo(memberId, "3", null);
			// 查询当前审批的数据
			List<VerifyDetailInfoDTO> list = memberBaseInfoDao.selectVerifyInfoList(memberId,
					verifyInfoDTO.getVerifierId());
			rs.setResult(list);
			rs.setResultMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl======>selectVerifyDetailInfoDTOList=" + e);
			rs.setResultMessage("异常");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#querySupplierName(java.
	 * lang.String, cn.htd.common.Pager)
	 */
	@Override
	public ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> querySupplierName(String memberName, Pager pager) {
		ExecuteResult<DataGrid<MemberImportSuccInfoDTO>> rs = new ExecuteResult<DataGrid<MemberImportSuccInfoDTO>>();
		DataGrid<MemberImportSuccInfoDTO> dg = new DataGrid<MemberImportSuccInfoDTO>();
		try {
			Long count = memberBaseOperationDAO.selectSupplierNameCount(memberName);
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.selectSupplierName(memberName, pager);
			if (count > 0) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResult(dg);
				rs.setResultMessage("要查询的数据不存在");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.setResultMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->querySupplierName=" + e);
		}

		return rs;
	}

	@Override
	public ExecuteResult<MemberCompanyInfoDTO> selectMobilePhoneMemberId(MemberCompanyInfoDTO memberCompanyInfoDTO) {
		logger.info("调用的接口：MemberBaseInfoServiceImpl----->selectMobilePhoneMemberId");
		ExecuteResult<MemberCompanyInfoDTO> rs = new ExecuteResult<MemberCompanyInfoDTO>();
		try {
			List<MemberCompanyInfoDTO> memberCompanyInfoList = memberCompanyInfoDao
					.searchMemberCompanyInfo(memberCompanyInfoDTO);
			int size = memberCompanyInfoList.size();
			if (memberCompanyInfoList != null && size > 0) {
				if (size == 1) {
					rs.setResult(memberCompanyInfoList.get(0));
					rs.setResultMessage("success");
					rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				} else {
					rs.addErrorMessage("查询条件结果不唯一");
					rs.setResultMessage("fail");
				}
			} else {
				rs.addErrorMessage("没有返回的数据");
				rs.setResultMessage("fail");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			logger.error("MemberBaseInfoServiceImpl----->selectMobilePhoneMemberId=" + e);
		}
		return rs;
	}

	public ExecuteResult<String>  updateMemberBaseRegisterInfo4Check(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO){
		ExecuteResult<String> rs = new ExecuteResult<String>();

	      // 输入DTO的验证
			String emsg="";
	        ValidateResult validateResult = ValidationUtils.validateEntity(memberBaseInfoRegisterDTO);
	        // 有错误信息时返回错误信息
	        if (validateResult.isHasErrors()) {
	        	if(StringUtils.isNotBlank(validateResult.getErrorMsg()) && StringUtils.isNotBlank(validateResult.getErrorMsg().split(",")[0])){
	        	      emsg=validateResult.getErrorMsg().split(",")[0].split(":")[1];
				      rs.addErrorMessage(emsg.trim());
	        	}
				return rs;
	        }
	        if(memberBaseInfoRegisterDTO.getMemberId() !=null){
		        if(StringUtils.isNotBlank(memberBaseInfoRegisterDTO.getCompanyName()) && checkCompanyNameUnique(memberBaseInfoRegisterDTO.getCompanyName(),memberBaseInfoRegisterDTO.getMemberId())){
					rs.addErrorMessage("公司名称已经存在，请重新填写!");
					return rs;
		        }
				boolean mobilecheck = checkMemberMobile(memberBaseInfoRegisterDTO.getArtificialPersonMobile(), memberBaseInfoRegisterDTO.getMemberId());
				if (mobilecheck) {
					rs.addErrorMessage("手机号已存在，请重新填写!");
					return rs;
				}	
	        }
			return rs;
	}
	
	@Override
	public ExecuteResult<String> updateMemberBaseRegisterInfo(MemberBaseInfoRegisterDTO memberBaseInfoRegisterDTO) {
		// TODO Auto-generated method stub
		ExecuteResult<String> rs = new ExecuteResult<String>();
        rs=updateMemberBaseRegisterInfo4Check(memberBaseInfoRegisterDTO);
		try {
			if (memberBaseInfoRegisterDTO.getMemberId() != null) {
				String cooperateVendor = memberBaseInfoRegisterDTO.getCooperateVendor();
				if (StringUtils.isNotBlank(cooperateVendor)) {
					Long belongSellerID = memberCompanyInfoDao
							.selectBelongSellerId(memberBaseInfoRegisterDTO.getCooperateVendor());
					memberBaseInfoRegisterDTO.setBelongSellerId(belongSellerID);
					memberBaseInfoRegisterDTO.setCurBelongSellerId(belongSellerID);
				} else {
					List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO.getMemberInfoByCompanyCode(
							GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER);
					if (rsList.size() > 0) {
						Long belongSellerID = rsList.get(0).getId();
						memberBaseInfoRegisterDTO.setBelongSellerId(belongSellerID);
						memberBaseInfoRegisterDTO.setCurBelongSellerId(belongSellerID);
					}
				}
				// 拼接省市区镇详细地址
				if (memberBaseInfoRegisterDTO.getLocationTown() != null) {
					String locationAddr = memberBaseService
							.getAddressBaseByCode(memberBaseInfoRegisterDTO.getLocationTown())
							+ memberBaseInfoRegisterDTO.getLocationDetail();
					memberBaseInfoRegisterDTO.setLocationAddr(locationAddr);
				}
				// 添加是否异业判断
				if ("7".equals(memberBaseInfoRegisterDTO.getBusinessScope())) {
					memberBaseInfoRegisterDTO.setIsDiffIndustry(GlobalConstant.FLAG_NO);
				} else {
					memberBaseInfoRegisterDTO.setIsDiffIndustry(GlobalConstant.FLAG_YES);
				}
				memberBaseInfoDao.updateMemberCompanyInfo(memberBaseInfoRegisterDTO);
				memberBaseInfoDao.updateMemberLicenceInfo(memberBaseInfoRegisterDTO);
				memberBaseInfoDao.updateMemberBaseInfoPassword(memberBaseInfoRegisterDTO);
				memberBaseInfoDao.updateMobilePhoneMemberStatusInfo(memberBaseInfoRegisterDTO, "11");
				// memberBaseInfoDao.updateMemberExtendInfo(memberBaseInfoRegisterDTO);
				MemberRemoveRelationshipDTO memberRemoveRelationshipDTO = new MemberRemoveRelationshipDTO();
				memberRemoveRelationshipDTO.setVerifyInfoId(memberBaseInfoRegisterDTO.getVerifyInfoId());
				memberRemoveRelationshipDTO.setStatus("1");
				memberRemoveRelationshipDTO.setRemark(memberBaseInfoRegisterDTO.getRemark());
				memberRemoveRelationshipDTO.setModifyId(memberBaseInfoRegisterDTO.getModifyId());
				memberRemoveRelationshipDTO.setModifyName(memberBaseInfoRegisterDTO.getModifyName());
				memberBaseInfoDao.updateVerifyStatus(memberRemoveRelationshipDTO);
				rs.setResult("success");
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("会员ID和审批ID不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberBaseRegisterInfo=" + e);
		}
		return rs;
	}

	/**
	 * 生成支付账号
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifu(MemberBaseInfoDTO dto) {
		YijifuCorporateDTO payDto = new YijifuCorporateDTO();
		MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getId());
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseById(dto.getId(), GlobalConstant.IS_BUYER);
		payDto.setComName(baseInfo.getCompanyName());
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_INDIVIDUAL);
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setAddress(baseInfo.getLocationDetail());
		legalPerson.setCertNo(baseInfo.getArtificialPersonIdcard());
		legalPerson.setEmail(baseInfo.getContactEmail());
		legalPerson.setMobileNo(baseInfo.getArtificialPersonMobile().toString());
		legalPerson.setRealName(baseInfo.getArtificialPersonName());
		payDto.setLegalPerson(legalPerson);
		payDto.setLicenceNo(licenceInfo.getBuyerBusinessLicenseId());
		payDto.setEmail(baseInfo.getContactEmail());
		payDto.setOrganizationCode(licenceInfo.getOrganizationId());
		payDto.setOutUserId(baseInfo.getMemberCode());
		payDto.setTaxAuthorityNo(licenceInfo.getTaxManId());
		payDto.setMobileNo(baseInfo.getArtificialPersonMobile().toString());
		payDto.setVerifyRealName(GlobalConstant.REAL_NAME_NO);
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameSaveVerify(payDto);
		return callBack.getResult();
	}

	@Override
	public ExecuteResult<List<MemberBaseInfoDTO>> selectSupplierByMemberCode(String memberCode) {
		ExecuteResult<List<MemberBaseInfoDTO>> rs = new ExecuteResult<List<MemberBaseInfoDTO>>();
		List<MemberBaseInfoDTO> list = new ArrayList<MemberBaseInfoDTO>();
		try {
			MemberBaseInfoDTO dto = memberBaseOperationDAO.selectMemberBaseInfoByMemberCode(memberCode);
			List<BoxRelationship> boxRelationList = memberBaseOperationDAO.selectBoxRelationByMemberId(dto.getId());
			int size = boxRelationList.size();
			for (int i = 0; i < size; i++) {
				BoxRelationship boxRelationship = boxRelationList.get(i);
				Long memberId = boxRelationship.getSellerId();
				MemberBaseInfoDTO memberBaseInfoDTO = memberBaseOperationDAO.getMemberbaseBySellerId(memberId,
						GlobalConstant.IS_SELLER);
				list.add(memberBaseInfoDTO);
			}
			rs.setResult(list);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询异常");
			rs.setResultMessage("fail");
			logger.error("MemberBaseInfoServiceImpl----->selectSupplierByMemberCode=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberCompanyInfoDTO> searchVoidMemberCompanyInfo(MemberCompanyInfoDTO memberCompanyInfoDTO) {
		logger.info("调用的接口：MemberBaseInfoServiceImpl----->searchVoidMemberCompanyInfo");
		ExecuteResult<MemberCompanyInfoDTO> rs = new ExecuteResult<MemberCompanyInfoDTO>();
		try {
			List<MemberCompanyInfoDTO> memberCompanyInfoList = memberCompanyInfoDao
					.searchVoidMemberCompanyInfo(memberCompanyInfoDTO);
			int size = memberCompanyInfoList.size();
			if (memberCompanyInfoList != null && size > 0) {
				rs.setResult(memberCompanyInfoList.get(0));
				rs.setResultMessage("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("没有返回的数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			logger.error("MemberBaseInfoServiceImpl----->searchVoidMemberCompanyInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<SellerTypeInfoDTO>> selectSellerTypeList(String sellerType) {
		logger.info("调用的当前接口方法：MemberBaseInfoServiceImpl-----》selectSellerTypeList");
		// TODO Auto-generated method stub
		ExecuteResult<List<SellerTypeInfoDTO>> rs = new ExecuteResult<List<SellerTypeInfoDTO>>();
		try {
			List<SellerTypeInfoDTO> list = memberBaseOperationDAO.selectSellerTypeList(sellerType);
			if (list.size() > 0 && list != null) {
				rs.setResult(list);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.addErrorMessage("没查到数据");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.setResultMessage("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberBaseInfoServiceImpl-----》selectSellerTypeList=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberBaseInfoDTO>> selectMemberbaseListByTime(Long startTime, Long endTime) {
		ExecuteResult<List<MemberBaseInfoDTO>> rs = new ExecuteResult<List<MemberBaseInfoDTO>>();
		try {
			List<MemberBaseInfoDTO> memberList = memberBaseOperationDAO.selectMemberbaseListByTime(startTime, endTime);
			if (memberList.size() > 0) {
				rs.setResult(memberList);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("fail");
				rs.addErrorMessage("没有数据");
			}
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
		}
		return rs;
	}

	/**
	 * 修改支付信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModify(MemberBaseInfoDTO baseInfo, MemberBaseInfoDTO oldDto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		payDto.setUserId(baseInfo.getAccountNo());
		if (!baseInfo.getCompanyName().equals(oldDto.getCompanyName())) {
			payDto.setComName(baseInfo.getCompanyName());
		}

		if (!baseInfo.getArtificialPersonName().equals(oldDto.getArtificialPersonName())) {
			payDto.setLegalPersonName(baseInfo.getArtificialPersonName());
		}

		if (!baseInfo.getArtificialPersonIdcard().equals(oldDto.getArtificialPersonIdcard())) {
			payDto.setLegalPersonCertNo(baseInfo.getArtificialPersonIdcard());
			payDto.setLegalPersonName(baseInfo.getArtificialPersonName());
		}

		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 保存归属关系与包厢关系
	 * 
	 * @param dto
	 * @return
	 */
	private boolean saveBelongBoxRelation(MemberBaseInfoDTO dto) {
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		belongRelationshipDto.setMemberId(dto.getId());
		// UserDTO user =
		// userExportService.queryUserByLoginId("08019999").getResult();
		dto.setBelongManagerId("08019999");
		dto.setCurBelongManagerId("08019999");
		belongRelationshipDto.setCurBelongManagerId("08019999");
		belongRelationshipDto.setCurBelongSellerId(dto.getBelongSellerId());
		belongRelationshipDto.setBelongManagerId("08019999");
		belongRelationshipDto.setBelongSellerId(dto.getBelongSellerId());
		belongRelationshipDto.setModifyId(dto.getModifyId());
		belongRelationshipDto.setModifyName(dto.getModifyName());
		belongRelationshipDto.setBuyerFeature("16");// 默认电商类型
		belongRelationshipDto.setVerifyStatus("3");
		belongRelationshipDAO.insertBelongInfo(belongRelationshipDto);

		ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
		applyBusiRelationDto.setAuditStatus("1");
		applyBusiRelationDto.setMemberId(dto.getId());
		applyBusiRelationDto.setSellerId(dto.getBelongSellerId());
		applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
		applyBusiRelationDto.setCreateId(dto.getModifyId());
		applyBusiRelationDto.setCustomerManagerId("08019999");
		applyBusiRelationDto.setCreateName(dto.getModifyName());
		applyBusiRelationDto.setModifyId(dto.getModifyId());
		applyBusiRelationDto.setModifyName(dto.getModifyName());
		verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);

		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		memberBusinessRelationDTO.setBuyerId(dto.getId().toString());
		memberBusinessRelationDTO.setSellerId(dto.getBelongSellerId().toString());
		memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
		memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
		memberBusinessRelationDTO.setCreateId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setModifyId(dto.getModifyId().toString());
		memberBusinessRelationDTO.setCreateName(dto.getModifyName());
		memberBusinessRelationDTO.setModifyName(dto.getModifyName());
		memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

		return true;
	}

	@Override
	public ExecuteResult<List<MemberBaseInfoDTO>> getMemberInfoByCompanyCode(String companyCode, String isSellerBuyer) {
		ExecuteResult<List<MemberBaseInfoDTO>> rs = new ExecuteResult<List<MemberBaseInfoDTO>>();
		try {
			List<MemberBaseInfoDTO> rsList = memberBaseOperationDAO.getMemberInfoByCompanyCode(companyCode,
					isSellerBuyer);
			if (rsList.size() > 0) {
				rs.setResult(rsList);
			} else {
				rs.addErrorMessage("没有数据");
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("查询异常" + e);
			rs.addErrorMessage("查询异常");
			rs.setResultMessage("fail");
		}
		return rs;
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

	@Override
	public ExecuteResult<Boolean> selectIsBooleanVerifyInfo(Long memberId, String modifyType) {
		// TODO Auto-generated method stub
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			List<String> status = new ArrayList<String>();
			status.add("1");
			VerifyInfoDTO i = memberBaseInfoDao.selectMemberStatusInfo(memberId, modifyType, status);
			if (i != null) {
				rs.setResult(true);
				rs.setResultMessage("success");
			} else {
				rs.setResult(false);
				rs.addErrorMessage("没查到数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("异常");
			logger.error("MemberBaseInfoServiceImpl-----》selectIsBooleanVerifyInfo");
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MemberBaseDTO>> selectBoxRelationBuyerListBySellerId(Long sellerId, String buyerName,
			Pager<MemberBaseDTO> page) {
		ExecuteResult<DataGrid<MemberBaseDTO>> result = new ExecuteResult<DataGrid<MemberBaseDTO>>();
		DataGrid<MemberBaseDTO> dg = new DataGrid<MemberBaseDTO>();
		List<MemberBaseDTO> memberList = new ArrayList<MemberBaseDTO>();
		List<BoxRelationship> belongRelationList = null;
		List<Long> buyerIdList = new ArrayList<Long>();
		Long total = 0L;
		try {
			belongRelationList = boxRelationshipDao.queryBoxRelationListBySellerId(sellerId);
			if (belongRelationList != null && !belongRelationList.isEmpty()) {
				for (BoxRelationship relationship : belongRelationList) {
					buyerIdList.add(relationship.getBuyerId());
				}
				total = memberBaseInfoDao.selectMemberCountByBuyerIdList(buyerIdList, buyerName);
				if (total > 0) {
					memberList = memberBaseInfoDao.selectMemberInfoListByBuyerIdList(buyerIdList, buyerName, page);
					if (memberList != null && !memberList.isEmpty()) {
						dg.setRows(memberList);
					}
					dg.setTotal(total);
				}
			}
			result.setResult(dg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl----->selectBoxRelationBuyerListBySellerId=", e);
			result.addErrorMessage("error");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#getMemberIdByCode(java.
	 * lang.String)
	 */
	@Override
	public ExecuteResult<Long> getMemberIdByCode(String memberCode) {
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		try {
			Long dto = memberBaseOperationDAO.getMemberIdByCode(memberCode);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询到多个会员，请检查数据");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#getMemberCodeById(java.
	 * lang.Long)
	 */
	@Override
	public ExecuteResult<String> getMemberCodeById(Long memberId) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			String dto = memberBaseOperationDAO.getMemberCodeById(memberId);
			rs.setResult(dto);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询到多个会员，请检查数据");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseService#getManagerName(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public String getManagerName(String sellerId, String managerCode) {
		try {
			if (StringUtils.isEmpty(sellerId) && !sellerId.equals("0")) {
				sellerId = memberBaseInfoDao.selectSellerCodeByManager(managerCode);
			} else {
				try {
					sellerId = this.getMemberCodeById(new Long(sellerId)).getResult();
				} catch (Exception e) {
					sellerId = "";
				}
			}
			String tokenResponse = HttpUtils.httpGet(StaticProperty.TOKEN_URL);
			JSONObject tokenResponseJSON = JSONObject.parseObject(tokenResponse);
			StringBuffer buffer = new StringBuffer();
			buffer.append("?");
			if (StringUtils.isNotEmpty(sellerId)) {
				buffer.append("supplierCode=" + sellerId);
				buffer.append("&salemanCode=" + managerCode);
				String msg = "";
				if (tokenResponseJSON.getInteger("code") == 1) {
					buffer.append("&token=" + tokenResponseJSON.getString("data"));
					String urlParam = buffer.toString();
					msg = HttpUtils.httpGet(StaticProperty.MIDDLEWAREERP_URL + "member/getSalemanName" + urlParam);
					logger.info(msg);
				}
				JSONObject msgjson = JSONObject.parseObject(msg);
				if (tokenResponseJSON.getInteger("code") == 1) {
					String s = msgjson.getString("data");
					return s;
				}
			}

		} catch (Exception e) {
			logger.error("getManagerName error" + e);
			return "";
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#getManagerList(java.
	 * lang.String)
	 */
	@Override
	public ExecuteResult<List<SalemanDTO>> getManagerList(String memberCode) {
		ExecuteResult<List<SalemanDTO>> rs = new ExecuteResult<List<SalemanDTO>>();
		List<SalemanDTO> list = new ArrayList<SalemanDTO>();
		try {
			String tokenResponse = HttpUtils.httpGet(StaticProperty.TOKEN_URL);
			JSONObject tokenResponseJSON = JSONObject.parseObject(tokenResponse);
			StringBuffer buffer = new StringBuffer();
			buffer.append("?");
			if (StringUtils.isNotEmpty(memberCode)) {
				buffer.append("supplierCode=" + memberCode);
				String msg = "";
				if (tokenResponseJSON.getInteger("code") == 1) {
					buffer.append("&token=" + tokenResponseJSON.getString("data"));
					String urlParam = buffer.toString();
					msg = HttpUtils.httpGet(StaticProperty.MIDDLEWAREERP_URL + "member/getSaleman" + urlParam);
					logger.info(msg);
				}
				JSONObject msgjson = JSONObject.parseObject(msg);
				if (tokenResponseJSON.getInteger("code") == 1) {
					JSONArray s = (JSONArray) msgjson.get("data");
					String code = "";
					String name = "";
					for (int i = 0; i < s.size(); i++) {
						JSONObject json = (JSONObject) s.get(i);
						code = json.getString("salemanCode");
						name = json.getString("salemanName");
						SalemanDTO e = new SalemanDTO();
						e.setCustomerManagerCode(code);
						e.setCustomerManagerName(name);
						list.add(e);
					}
					rs.setResult(list);
				}
			}

		} catch (Exception e) {
			logger.error("getManagerList error" + e);
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> orderAfterDownErp(BoxAddDto dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			MemberImportSuccInfoDTO seller = memberBaseOperationDAO.querySellerIdByCode(dto.getSupplierCode());
			MemberImportSuccInfoDTO buyer = memberBaseOperationDAO.queryBuyerByCode(dto.getMemberCode());

			if (null != seller && null != buyer) {
				MemberBaseInfoDTO sellerInfo = memberBaseOperationDAO
						.getMemberbaseBySellerId(Long.valueOf(seller.getMemberId()), GlobalConstant.IS_SELLER);
				if ("1".equals(sellerInfo.getSellerType())) {
					String buyerId = buyer.getMemberId();
					String sellerId = seller.getMemberId();
					ApplyBusiRelationDTO applyBusiRelationDTO = applyRelationshipDao
							.queryBoxRelationInfo(Long.valueOf(buyerId), Long.valueOf(sellerId));
					if (null == applyBusiRelationDTO) {
						MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
						memberBusinessRelationDTO.setBuyerId(buyerId);
						memberBusinessRelationDTO.setSellerId(sellerId);
						memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
						memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
						memberBusinessRelationDTO.setCreateId(dto.getModifyId().toString());
						memberBusinessRelationDTO.setModifyId(dto.getModifyId().toString());
						memberBusinessRelationDTO.setCreateName(dto.getModifyName());
						memberBusinessRelationDTO.setModifyName(dto.getModifyName());
						memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);
					} else if (!ErpStatusEnum.SUCCESS.getValue().equals(applyBusiRelationDTO.getErpStatus())) {
						memberDownErpDAO.updateCompanyRelationDownErp(applyBusiRelationDTO.getBoxId());
					}

					Long count = memberBaseOperationDAO.queryBusinessBrandCategory(Long.valueOf(buyerId),
							Long.valueOf(sellerId), Long.valueOf(dto.getBrandCode()),
							Long.valueOf(dto.getClassCategoryCode()));
					if (count == 0) {
						String manageCode = "";
						// 获取归属客户经理
						List<SalemanDTO> salesMans = getManagerList(dto.getSupplierCode()).getResult();
						if (null != salesMans && salesMans.size() > 0) {

							SalemanDTO salesManRs = null;
							for (int i = 0; i < salesMans.size(); i++) {
								SalemanDTO salesMan = salesMans.get(i);
								if (salesMan.getCustomerManagerCode().indexOf("9999") > 0) {
									salesManRs = salesMan;
									break;
								}
							}
							if (null == salesManRs) {
								salesManRs = salesMans.get(0);
							}
							manageCode = salesManRs.getCustomerManagerCode();
						}
						ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
						applyBusiRelationDto.setAuditStatus("1");
						applyBusiRelationDto.setBrandId(Long.valueOf(dto.getBrandCode()));
						applyBusiRelationDto.setCategoryId(Long.valueOf(dto.getClassCategoryCode()));
						applyBusiRelationDto.setMemberId(Long.valueOf(buyerId));
						applyBusiRelationDto.setSellerId(Long.valueOf(sellerId));
						applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
						applyBusiRelationDto.setCreateId(dto.getModifyId());
						applyBusiRelationDto.setCustomerManagerId(manageCode);
						applyBusiRelationDto.setCreateName(dto.getModifyName());
						applyBusiRelationDto.setModifyId(dto.getModifyId());
						applyBusiRelationDto.setModifyName(dto.getModifyName());
						verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);

					}

					rs.setResult(true);
					rs.setResultMessage("success");
				} else {
					rs.setResult(false);
					rs.setResultMessage("fail");
					rs.addErrorMessage("外部供应商无需建立往来关系");
				}

			} else {
				rs.addErrorMessage("会员或者卖家不存在");
				rs.setResultMessage("fail");
				rs.setResult(false);
			}

		} catch (Exception e) {
			logger.error("orderAfterDownErp error" + e);
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberImportSuccInfoDTO> queryBuyerByCode(String memberCode) {
		ExecuteResult<MemberImportSuccInfoDTO> rs = new ExecuteResult<MemberImportSuccInfoDTO>();
		try {
			MemberImportSuccInfoDTO buyer = memberBaseOperationDAO.queryBuyerByCode(memberCode);
			if (null != buyer) {
				rs.setResult(buyer);
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberBaseInfoServiceImpl==================orderAfterDownErp error" + e);
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询会员信息失败");
		}
		return rs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.membercenter.service.MemberBaseInfoService#checkMemberMobile(java.
	 * lang.String)
	 */
	@Override
	public boolean checkMemberMobile(String mobile, Long memberId) {
		ArrayList<MemberCompanyInfoDTO> list = memberBaseOperationDAO.checkMemberMobile(mobile, memberId);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 修改实名信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuPhoneModify(MemberBaseInfoRegisterDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(Long.valueOf(dto.getMemberId()),
				GlobalConstant.IS_BUYER);
		payDto.setMobileNo(dto.getArtificialPersonMobile());
		payDto.setUserId(baseInfo.getAccountNo());

		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 除去重复数据
	 * 
	 * @param list
	 * @return
	 */
	private List<MemberBuyerVerifyDetailInfoDTO> removeRepeatVerify(List<MemberBuyerVerifyDetailInfoDTO> list) {
		List<MemberBuyerVerifyDetailInfoDTO> newList = new ArrayList<MemberBuyerVerifyDetailInfoDTO>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			MemberBuyerVerifyDetailInfoDTO dto = list.get(i);
			int repeatSize = newList.size();
			boolean isExit = false;
			if (repeatSize > 0) {
				for (int j = 0; j < repeatSize; j++) {
					MemberBuyerVerifyDetailInfoDTO newDto = newList.get(j);
					if (newDto.getChangeFieldId().equalsIgnoreCase(dto.getChangeFieldId())) {
						isExit = true;
						if (dto.getOperateTime().compareTo(newDto.getOperateTime()) > 0) {
							newList.remove(j);
							newList.add(dto);
						}
						break;
					}
				}
			}
			if (!isExit) {
				newList.add(dto);
			}

		}
		return newList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberBaseInfoService#
	 * queryMemberNameAllStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryMemberNameAllStatus(Long compid, String memberName) {
		ExecuteResult<List<MemberImportSuccInfoDTO>> rs = new ExecuteResult<List<MemberImportSuccInfoDTO>>();
		try {
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.queryMemberNameAllStatus(compid, memberName);
			rs.setResult(list);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateMemberBussinessType(MemberBaseInfoDTO dto) {
		logger.info("MemberBaseInfoServiceImpl----->updateMemberBussinessType执行，参数为:" + JSONObject.toJSONString(dto));
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();// 修改履历信息
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			dto.setModifyTime(trancDate);
			MemberBaseInfoDTO oldDto = memberBaseOperationDAO.getMemberbaseById(dto.getId(), GlobalConstant.IS_BUYER);
			if (null == oldDto.getBusinessType()) {
				oldDto.setBusinessType("");
			}
			if (oldDto.getHasBusinessLicense() == 1 && !oldDto.getBusinessType().equals(dto.getBusinessType())) {
				verDto = new VerifyDetailInfo();// 有效性修改履历
				String afterChangeDesc = "";
				String beforeChangeDesc = "";
				if ("2".equals(oldDto.getBusinessType())) {
					beforeChangeDesc = "企业账户类型为企业用户";
				} else {
					beforeChangeDesc = "企业账户类型为个体户";
				}
				if ("2".equals(dto.getBusinessType())) {
					afterChangeDesc = "企业账户类型更改为企业用户";
				} else {
					afterChangeDesc = "企业账户类型更改为个体户";
				}
				verDto.setAfterChangeDesc(afterChangeDesc);
				verDto.setBeforeChangeDesc(beforeChangeDesc);
				verDto.setAfterChange(dto.getBusinessType());
				verDto.setBeforeChange(oldDto.getBusinessType());
				verDto.setChangeFieldId("BUSINESS_TYPE");
				verDto.setChangeTableId("MEMBER_BASE_INFO");
				verDto.setContentName("会员企业账户类型修改");
				verDto.setModifyType(GlobalConstant.INFO_TYPE_DEFAULT);
				verDto.setOperateTime(trancDate);
				verDto.setOperatorId(dto.getModifyId());
				verDto.setOperatorName(dto.getModifyName());
				verDto.setRecordId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
				verDto.setRecordId(dto.getId());
				verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
				verDtoList.add(verDto);
				dto.setAccountNo(oldDto.getAccountNo());
				dto.setArtificialPersonIdcard(oldDto.getArtificialPersonIdcard());
				dto.setArtificialPersonName(oldDto.getArtificialPersonName());
				YijifuCorporateCallBackDTO payBack = yijifuBusinessTypeModify(dto);
				if (!"EXECUTE_SUCCESS".equals(payBack.getResultCode())) {
					rs.setResult(false);
					rs.addErrorMessage("支付修改失败");
					rs.setResultMessage("fail");
				} else {
					if ("2".equals(dto.getBusinessType())) {
						dto.setRealNameStatus("3");
						dto.setBuyerSellerType(GlobalConstant.IS_BUYER);
						memberBaseOperationDAO.updateMemberCompanyInfo(dto);
					}
					memberBaseOperationDAO.updateMemberBaseInfo(dto);
					memberBaseOperationDAO.insertVerifyInfo(verDtoList);
					rs.setResultMessage("success");
					rs.setResult(true);
				}
			}

			logger.info(
					"MemberBaseInfoServiceImpl----->updateMemberBussinessType执行结束，参数为:" + JSONObject.toJSONString(dto));
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("更新异常");
			rs.setResultMessage("fail");
			logger.error("MemberBaseInfoServiceImpl----->updateMemberBussinessType=" + e);
		}
		return rs;
	}

	private YijifuCorporateCallBackDTO yijifuBusinessTypeModify(MemberBaseInfoDTO baseInfo) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		payDto.setUserId(baseInfo.getAccountNo());
		if ("2".equals(baseInfo.getBusinessType())) {
			payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		} else {
			payDto.setLegalPersonName(baseInfo.getArtificialPersonName());
			payDto.setLegalPersonCertNo(baseInfo.getArtificialPersonIdcard());
			payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_INDIVIDUAL);
		}

		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	@Override
	public ExecuteResult<List<MemberImportSuccInfoDTO>> queryAccountNoListByName(String companyName) {
		ExecuteResult<List<MemberImportSuccInfoDTO>> rs = new ExecuteResult<List<MemberImportSuccInfoDTO>>();
		try {
			List<MemberImportSuccInfoDTO> list = memberBaseOperationDAO.queryAccountNoListByName(companyName);
			rs.setResult(list);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.addErrorMessage("查询失败");
			rs.setResultMessage("fail");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> IsHasInnerComapanyCert(String memberCode) {
		logger.info("MemberBaseInfoServiceImpl----->IsHasInnerComapanyCert执行开始，参数为:" + memberCode);
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			Long memberId = memberBaseOperationDAO.getMemberIdByCode(memberCode);
			MemberBaseInfoDTO sellerInfo = memberBaseOperationDAO.getMemberbaseBySellerId(memberId,
					GlobalConstant.IS_SELLER);
			if (null != sellerInfo) {
				String companyName = sellerInfo.getCompanyName();
				Long count = memberBaseOperationDAO.getInnerSellerInfoByName(companyName);
				if (count > 0) {
					rs.setResult("1");
					rs.setResultMessage("success");
				} else {
					rs.setResult("0");
					rs.setResultMessage("fail");
				}
			} else {
				rs.setResult("0");
				rs.setResultMessage("fail");
			}

			logger.info("MemberBaseInfoServiceImpl----->IsHasInnerComapanyCert执行结束，参数为:" + memberCode + ",结果为："
					+ JSONObject.toJSONString(rs));
		} catch (Exception e) {
			rs.setResult("2");
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->IsHasInnerComapanyCert执行异常，参数为:" + memberCode + "异常：" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseInfoDTO> getInnerInfoByOuterHTDCode(String memberCode) {
		logger.info("MemberBaseInfoServiceImpl----->getInnerInfoByOuterHTDCode执行开始，参数为:" + memberCode);
		ExecuteResult<MemberBaseInfoDTO> rs = new ExecuteResult<MemberBaseInfoDTO>();
		try {
			Long memberId = memberBaseOperationDAO.getMemberIdByCode(memberCode);
			MemberBaseInfoDTO sellerInfo = memberBaseOperationDAO.getMemberbaseBySellerId(memberId,
					GlobalConstant.IS_SELLER);
			if (null != sellerInfo) {
				String companyName = sellerInfo.getCompanyName();
				MemberBaseInfoDTO memeberBase = memberBaseOperationDAO.getInnerInfoByName(companyName);
				if (null != memeberBase) {
					rs.setResult(memeberBase);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("fail");
				}
			} else {
				rs.setResultMessage("fail");
			}

			logger.info("MemberBaseInfoServiceImpl----->getInnerInfoByOuterHTDCode执行结束，参数为:" + memberCode + ",结果为："
					+ JSONObject.toJSONString(rs));
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getInnerInfoByOuterHTDCode执行异常，参数为:" + memberCode + "异常：" + e);
		}
		return rs;
	}
	
		@Override
		public ExecuteResult<String> queryCompanyCodeBySellerId(Long sellerId) {
			ExecuteResult<String> result=new ExecuteResult<String>();
			if(sellerId==null||sellerId<=0){
				result.setCode(ErrorCodes.E10000.name());
				result.setResultMessage(ErrorCodes.E10000.getErrorMsg("sellerId"));
				return result;
			}
			String companyCode=memberCompanyInfoDao.queryCompanyCodeBySellerId(sellerId);
			result.setCode(ErrorCodes.SUCCESS.name());
			result.setResult(companyCode);
			return result;
		}
		
		
		/**
		 * @author li.jun
		 * @desc:校验公司名称是否唯一
		 * @param company
		 * @return
		 */
		public boolean checkCompanyNameUnique(String companyName,Long memberId) {
			List<MemberCompanyInfoDTO> list = memberBaseOperationDAO.checkCompanyNameUnique(companyName,memberId);
			if (list != null && list.size() > 0) {
				return true;
			}
			return false;
		}

}

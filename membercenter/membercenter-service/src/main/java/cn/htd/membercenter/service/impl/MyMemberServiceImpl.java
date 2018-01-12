package cn.htd.membercenter.service.impl;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.domain.TransactionRelation;
import cn.htd.basecenter.dto.TransactionRelationDTO;
import cn.htd.basecenter.service.TransactionRelationService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberCallCenterDAO;
import cn.htd.membercenter.dao.MemberModifyVerifyDAO;
import cn.htd.membercenter.dao.MemberStatusDao;
import cn.htd.membercenter.dao.MemberVerifyStatusDAO;
import cn.htd.membercenter.dao.MyMemberDAO;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.AthenaEventDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberCountDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberUncheckedDTO;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyMemberSearchDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MyMemberService;
import cn.htd.usercenter.service.CustomerService;

@Service("myMemberService")
public class MyMemberServiceImpl implements MyMemberService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBaseServiceImpl.class);

	@Resource
	private MyMemberDAO memberDAO;

	@Resource
	private MemberStatusDao memberStatusDao;

	@Resource
	private MemberVerifyStatusDAO memberVerifyStatusDao;

	@Resource
	private MemberModifyVerifyDAO memberModifyVerifyDAO;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private BelongRelationshipDAO belongRelationshipDao;
	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Autowired
	private MemberBaseService memberBaseService;
	@Autowired
	private CustomerService customerSerice;

	@Resource
	private MemberBusinessRelationDAO memberBusinessRelationDAO;

	@Resource
	private TransactionRelationService transactionRelationService;

	@Resource
	private MemberCallCenterDAO memberCallCenterDAO;

	@Override
	public ExecuteResult<DataGrid<MyMemberDTO>> selectMyMemberList(Pager page, Long sellerId,
			MyMemberSearchDTO memberSearch, String type) {
		ExecuteResult<DataGrid<MyMemberDTO>> rs = new ExecuteResult<DataGrid<MyMemberDTO>>();
		DataGrid<MyMemberDTO> dg = new DataGrid<MyMemberDTO>();
		try {
			List<MyMemberDTO> myMemberDtoList = null;
			Long count = null;
			if (type != null && type.equals("2")) {
				count = memberDAO.selectByTypeListCount(sellerId, memberSearch, 1, null, 1);
				if (count != null && count > 0) {
					myMemberDtoList = memberDAO.selectByTypeList(page, sellerId, memberSearch, 1, null, 1);
				}
			} else if (type != null && type.equals("3")) {
				count = memberDAO.selectByTypeListCount(sellerId, memberSearch, 1, 1, 0);
				if (count != null && count > 0) {
					myMemberDtoList = memberDAO.selectByTypeList(page, sellerId, memberSearch, 1, 1, 0);
				}
			}
			dg.setRows(myMemberDtoList);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->selectByTypeList=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("查询我的会员、担保会员出错");
		}

		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MyNoMemberDTO>> selectNoMemberList(Pager page, Long vendorId,
			MyMemberSearchDTO memberSearch, String type) {
		ExecuteResult<DataGrid<MyNoMemberDTO>> rs = new ExecuteResult<DataGrid<MyNoMemberDTO>>();
		DataGrid<MyNoMemberDTO> dg = new DataGrid<MyNoMemberDTO>();
		try {
			List<MyNoMemberDTO> myMemberDtoList = null;
			Long count = null;
			if (type != null && type.equals("1")) {
				count = memberDAO.selectNoMemberCount(vendorId, memberSearch, 0);
				if (count != null && count > 0) {
					myMemberDtoList = memberDAO.selectNoMemberList(page, vendorId, vendorId, memberSearch, 0);
					dg.setRows(myMemberDtoList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}
			}
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->selectNoMemberList=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> saveNoMemberRegistInfo(MyNoMemberDTO myNoMemberDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();
		MemberOutsideSupplierCompanyDTO outCompanyDto = new MemberOutsideSupplierCompanyDTO();
		try {
			if (myNoMemberDto != null) {
				try {
					// if (myNoMemberDto.getTaxManId() != null &&
					// !myNoMemberDto.getTaxManId().equals("")) {
					// List<MyNoMemberDTO> nmDto =
					// memberDAO.getNoMemberTaxManId(myNoMemberDto.getTaxManId(),
					// 0l);
					// if (nmDto != null && nmDto.size() >= 1) {
					// rs.addErrorMessage("您填写的纳税人识别号已被使用，请重新填写！");
					// return rs;
					// }
					// }
					if (myNoMemberDto.getCompanyName() != null) {
						List<MyNoMemberDTO> nmDto = memberDAO.getNoMemberName(myNoMemberDto.getCompanyName(), 0l);
						if (nmDto != null && nmDto.size() >= 1) {
							rs.addErrorMessage("会员名称已存在，请重新填写！");
							return rs;
						}
					}
					if (myNoMemberDto.getArtificialPersonMobile() != null) {
						boolean mobilecheck = memberBaseInfoService
								.checkMemberMobile(myNoMemberDto.getArtificialPersonMobile(), 0l);
						if (mobilecheck) {
							rs.addErrorMessage("法人手机号已存在，请重新填写！");
							return rs;
						}
					}
					String code = customerSerice.genMemberCode().getResult();
					if (code == null) {
						rs.addErrorMessage("生成memberCode失败！");
						return rs;
					}
					myNoMemberDto.setMemberCode(code);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Long memberId = memberDAO.getNoMemberBaseID(myNoMemberDto);
				if (memberId != null) {
					myNoMemberDto
							.setLocationAddr(memberBaseService.getAddressBaseByCode(myNoMemberDto.getLocationTown())
									+ myNoMemberDto.getLocationDetail());
					memberDAO.saveNoMemberCompanyInfo(myNoMemberDto);
					memberDAO.saveNoMemberInvoiceInfo(myNoMemberDto);
					memberDAO.saveNoMemberbelongInfo(myNoMemberDto);

					// 新建时就建立包厢关系
					MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
					memberBusinessRelationDTO.setBuyerId(myNoMemberDto.getMemberId().toString());
					memberBusinessRelationDTO.setSellerId(myNoMemberDto.getCurBelongSellerId().toString());
					memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
					memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
					memberBusinessRelationDTO.setCreateId(myNoMemberDto.getModifyId().toString());
					memberBusinessRelationDTO.setModifyId(myNoMemberDto.getModifyId().toString());
					memberBusinessRelationDTO.setCreateName(myNoMemberDto.getModifyName());
					memberBusinessRelationDTO.setModifyName(myNoMemberDto.getModifyName());
					memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

					outCompanyDto.setMemberId(myNoMemberDto.getMemberId());
					outCompanyDto.setBankAccount(myNoMemberDto.getBankAccount());
					outCompanyDto.setBankName(myNoMemberDto.getBankName());
					applyRelationshipDao.insertMemberBankInfo(outCompanyDto);

					belongRelationshipDTO.setMemberId(myNoMemberDto.getMemberId());
					belongRelationshipDTO.setBelongSellerId(myNoMemberDto.getCurBelongSellerId());
					belongRelationshipDTO.setModifyId(myNoMemberDto.getModifyId());
					belongRelationshipDTO.setModifyName(myNoMemberDto.getModifyName());

					Long num = applyRelationshipDao.insertVerifyInfo(belongRelationshipDTO, "13");

					if (num != null) {
						applyRelationshipDao.insertVerifyDetailInfo(belongRelationshipDTO, "13");
						myNoMemberDto.setVerifyId(belongRelationshipDTO.getVerifyId());
						memberDAO.saveNoMemberStatusInfo(myNoMemberDto);
					}
					rs.setResultMessage("success");
					rs.setResultMessage("保存成功！！");
				}

			}

		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->saveNoMemberRegistInfo=" + "申请注册非会员失败");
			e.printStackTrace();
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", "注册失败！"));
			rs.setResultMessage("error");
			// throw new RuntimeException("注册失败！！");

		}
		return rs;
	}

	@Override
	public ExecuteResult<String> modifyNoMemberInfo(MyNoMemberDTO myNoMemberDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		Long memberId = myNoMemberDto.getMemberId();
		try {
			if (memberId != null) {
				String companyName = myNoMemberDto.getCompanyName();
				MemberInvoiceDTO memberInvoiceDTO = memberCallCenterDAO
						.queryMemberInvoiceInfo(myNoMemberDto.getMemberCode());
				if ((!StringUtils.isEmpty(memberInvoiceDTO.getTaxManId())
						&& !memberInvoiceDTO.getTaxManId().equals(myNoMemberDto.getTaxManId()))
						|| (!StringUtils.isEmpty(memberInvoiceDTO.getBankName())
								&& !memberInvoiceDTO.getBankName().equals(myNoMemberDto.getBankName()))
						|| (!StringUtils.isEmpty(memberInvoiceDTO.getBankAccount())
								&& !memberInvoiceDTO.getBankAccount().equals(myNoMemberDto.getBankAccount()))
						|| (!StringUtils.isEmpty(memberInvoiceDTO.getInvoiceAddress())
								&& !memberInvoiceDTO.getInvoiceAddress().equals(myNoMemberDto.getInvoiceAddress()))
						|| (!StringUtils.isEmpty(memberInvoiceDTO.getContactPhone())
								&& !memberInvoiceDTO.getContactPhone().equals(myNoMemberDto.getContactPhone()))) {
					Calendar canModifyTime = Calendar.getInstance();
					canModifyTime.setTime(memberInvoiceDTO.getModifyTime());
					canModifyTime.add(Calendar.MONTH, 3);
					if (new Date().before(canModifyTime.getTime())) {
						rs.addErrorMessage("发票信息三个月内不可再次修改，请重新填写！");
						return rs;
					}
				}
				if (myNoMemberDto.getTaxManId() != null) {
					List<MyNoMemberDTO> nmDto = memberDAO.getNoMemberName(myNoMemberDto.getCompanyName(),
							myNoMemberDto.getMemberId());
					if (nmDto != null && nmDto.size() >= 1) {
						rs.addErrorMessage("会员名称已存在，请重新填写！");
						return rs;
					}
				}

				memberDAO.updateMemberBaseInfo(myNoMemberDto);
				myNoMemberDto.setLocationAddr(memberBaseService.getAddressBaseByCode(myNoMemberDto.getLocationTown())
						+ myNoMemberDto.getLocationDetail());
				myNoMemberDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
				memberDAO.updatMememberBankInfo(myNoMemberDto);
				// /memberDAO.saveNoMemberbelongInfo(myNoMemberDto);
				// 因为修改多了一条数据，注册已经注册了
				// 更新客商属性
				memberDAO.updateBelongRelationshipbuyerFeature(myNoMemberDto);

				if ("1".equals(myNoMemberDto.getModifyType())) {// 重新提交非会员审核流程
					MemberStatusInfo dto = new MemberStatusInfo();
					dto.setMemberId(memberId);
					dto.setInfoType(GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);
					dto.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
					dto.setModifyId(myNoMemberDto.getModifyId());
					dto.setModifyName(myNoMemberDto.getModifyName());
					dto.setSyncErrorMsg("");
					memberBaseOperationDAO.saveMemberStatusInfo(dto);
					VerifyResultDTO verify = memberBaseOperationDAO.selectVerifyByIdAndInfoType(
							myNoMemberDto.getMemberId(), GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);

					java.util.Date date = new java.util.Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date trancDate = new Date(format.parse(format.format(date)).getTime());
					VerifyInfo verufyInfo = new VerifyInfo();// 修改审核信息
					verufyInfo.setModifyId(dto.getModifyId());
					verufyInfo.setModifyName(dto.getModifyName());
					verufyInfo.setModifyTime(trancDate);
					verufyInfo.setId(verify.getVerifyId());
					verufyInfo.setRemark("");
					verufyInfo.setVerifierId(dto.getModifyId());
					verufyInfo.setVerifierName(dto.getModifyName());
					verufyInfo.setVerifyTime(trancDate);
					verufyInfo.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
					memberModifyVerifyDAO.updateVerifyInfo(verufyInfo);
				} else if ("2".equals(myNoMemberDto.getModifyType())) {
					MemberBaseInfoDTO memberBase = memberBaseOperationDAO
							.getMemberBaseInfoById(myNoMemberDto.getMemberId(), GlobalConstant.IS_BUYER);

					// 走非会员名称修改审核流程
					if (!companyName.equals(memberBase.getCompanyName())) {
						myNoMemberDto.setSellerId(memberBase.getBelongSellerId());
						saveCompanyNameModifyStatus(myNoMemberDto, memberBase.getCompanyName());
						myNoMemberDto.setCompanyName(null);
						myNoMemberDto.setInvoiceCompanyName(null);
						myNoMemberDto.setInvoiceNotify(null);

					}
				}
				memberDAO.updateMemberCompanyInfo(myNoMemberDto);
				memberDAO.updateMemberInvoiceInfo(myNoMemberDto);
				if (!"1".equals(myNoMemberDto.getModifyType())) {
					saveDownErpStatus(myNoMemberDto);
				}

				rs.setResultMessage("success");
				rs.setResultMessage("保存修改成功！！");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MyMemberServiceImpl----->modifyNoMemberInfo=" + "非会员信息修改失败");
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", "非会员信息修改失败！"));
			rs.setResultMessage("error");
			throw new RuntimeException("非会员信息修改失败！！");

		}

		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MyMemberDTO>> exportMyMemberList(Long vendorId, MyMemberSearchDTO memberSearch,
			String type) {
		ExecuteResult<DataGrid<MyMemberDTO>> rs = new ExecuteResult<DataGrid<MyMemberDTO>>();
		DataGrid<MyMemberDTO> dg = new DataGrid<MyMemberDTO>();
		try {
			List<MyMemberDTO> myMemberDtoList = null;
			if (type != null && type.equals("2")) {
				myMemberDtoList = memberDAO.selectByTypeList(null, vendorId, memberSearch, 1, null, 1);
			} else if (type != null && type.equals("3")) {
				myMemberDtoList = memberDAO.selectByTypeList(null, vendorId, memberSearch, 1, 1, 0);
			}
			try {
				if (myMemberDtoList != null) {
					if (myMemberDtoList.size() > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {
						rs.setResultMessage("导出最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
					} else {
						dg.setRows(myMemberDtoList);
						rs.setResult(dg);
					}
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("fail");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->selectByTypeList=" + e);
			rs.setResultMessage("error");
		}

		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MyNoMemberDTO>> exportNoMemberList(Long vendorId, MyMemberSearchDTO memberSearch,
			String type) {
		ExecuteResult<DataGrid<MyNoMemberDTO>> rs = new ExecuteResult<DataGrid<MyNoMemberDTO>>();
		DataGrid<MyNoMemberDTO> dg = new DataGrid<MyNoMemberDTO>();
		try {
			List<MyNoMemberDTO> myMemberDtoList = null;
			if (type != null && type.equals("1")) {
				myMemberDtoList = memberDAO.selectNoMemberList(null, vendorId, vendorId, memberSearch, 0);
			}
			try {
				if (myMemberDtoList != null) {
					if (myMemberDtoList.size() > GlobalConstant.MAXIMPORT_EXPORT_COUNT) {
						rs.setResultMessage("导出最大条数不能超过" + GlobalConstant.MAXIMPORT_EXPORT_COUNT + "条");
					} else {
						Iterator li = myMemberDtoList.iterator();
						while (li.hasNext()) {
							MyNoMemberDTO myMember = (MyNoMemberDTO) li.next();
							BelongRelationshipDTO belongRelationDto = null;
							belongRelationDto = belongRelationshipDao.selectBelongRelationInfo(myMember.getMemberId(),
									myMember.getSellerId());

							if (belongRelationDto != null) {
								myMember.setBelong("是");
							} else {
								myMember.setBelong("否");
							}
						}
						dg.setRows(myMemberDtoList);
						rs.setResult(dg);
					}
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("fail");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->selectNoMemberList=" + e);
			rs.setResultMessage("error");
		}

		return rs;
	}

	private boolean saveCompanyNameModifyStatus(MyNoMemberDTO myNoMemberDto, String oldCompanyName)
			throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		Date defaultDate = format.parse("0000-00-00 00:00:00");

		// 保存审核信息
		VerifyInfo verufyInfo = new VerifyInfo();
		verufyInfo.setCreateId(myNoMemberDto.getModifyId());
		verufyInfo.setCreateName(myNoMemberDto.getModifyName());
		verufyInfo.setCreateTime(trancDate);
		verufyInfo.setModifyId(myNoMemberDto.getModifyId());
		verufyInfo.setModifyName(myNoMemberDto.getModifyName());
		verufyInfo.setModifyTime(trancDate);
		verufyInfo.setModifyType(GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);
		verufyInfo.setRecordId(myNoMemberDto.getMemberId());
		verufyInfo.setRemark("");
		verufyInfo.setVerifierId(myNoMemberDto.getModifyId());
		verufyInfo.setVerifierName(myNoMemberDto.getModifyName());
		verufyInfo.setVerifyTime(trancDate);
		verufyInfo.setVerifyStatus(myNoMemberDto.getVerifyStatus());
		verufyInfo.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
		verufyInfo.setBelongSellerId(myNoMemberDto.getSellerId());
		memberBaseOperationDAO.saveVerifyInfo(verufyInfo);

		// 保存状态信息
		MemberStatusInfo memberStatus = new MemberStatusInfo();
		memberStatus.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
		memberStatus.setModifyId(myNoMemberDto.getModifyId());
		memberStatus.setModifyName(myNoMemberDto.getModifyName());
		memberStatus.setModifyTime(trancDate);
		memberStatus.setMemberId(myNoMemberDto.getMemberId());
		memberStatus.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);
		memberStatus.setCreateId(myNoMemberDto.getModifyId());
		memberStatus.setCreateName(myNoMemberDto.getModifyName());
		memberStatus.setCreateTime(trancDate);
		memberStatus.setSyncKey(KeygenGenerator.getUidKey());
		memberStatus.setVerifyTime(defaultDate);
		memberStatus.setVerifyId(verufyInfo.getId());
		memberStatus.setSyncErrorMsg("");
		memberBaseOperationDAO.deleteMemberStatus(memberStatus);
		memberBaseOperationDAO.insertMemberStatus(memberStatus);

		// 保存修改详细信息
		VerifyDetailInfo verDto = new VerifyDetailInfo();
		verDto.setAfterChangeDesc(myNoMemberDto.getCompanyName());
		verDto.setBeforeChangeDesc(oldCompanyName);
		verDto.setAfterChange(myNoMemberDto.getCompanyName());
		verDto.setBeforeChange(oldCompanyName);
		verDto.setChangeFieldId("COMPANY_NAME");
		verDto.setChangeTableId("MEMBER_COMPAY_INFO");
		verDto.setContentName("会员注册审核");
		verDto.setModifyType(GlobalConstant.INFO_TYPE_VERIFY);
		verDto.setOperateTime(trancDate);
		verDto.setOperatorId(myNoMemberDto.getModifyId());
		verDto.setOperatorName(myNoMemberDto.getModifyName());
		verDto.setVerifyId(verufyInfo.getId());
		verDto.setRecordId(myNoMemberDto.getMemberId());
		verDto.setVerifyId(verufyInfo.getId());
		verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
		List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
		verDtoList.add(verDto);
		memberBaseOperationDAO.insertVerifyInfo(verDtoList);

		return true;
	}

	@Override
	public ExecuteResult<Boolean> saveUnMemberCompanyVerify(MyNoMemberDTO myNoMemberDto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (GlobalConstant.VERIFY_STATUS_ACCESS.equals(myNoMemberDto.getVerifyStatus())) {
				myNoMemberDto.setCompanyName(myNoMemberDto.getNewCompanyName());
				memberDAO.updateMemberCompanyInfo(myNoMemberDto);
				myNoMemberDto.setInvoiceCompanyName(myNoMemberDto.getNewCompanyName());
				myNoMemberDto.setInvoiceNotify(myNoMemberDto.getNewCompanyName());
				memberDAO.updateMemberInvoiceInfo(myNoMemberDto);
				saveDownErpStatus(myNoMemberDto);

				// 关联交易名单
				TransactionRelationDTO transactionRelationDTO = new TransactionRelationDTO();
				transactionRelationDTO.setBuyerName(myNoMemberDto.getNewCompanyName());
				ExecuteResult<TransactionRelationDTO> executeResult = transactionRelationService
						.getSingleTransactionRelationByParams(transactionRelationDTO);
				if (executeResult.getResult() != null) {
					TransactionRelationDTO transactionRelation = executeResult.getResult();
					transactionRelationDTO.setId(transactionRelation.getId());
					transactionRelationDTO.setBuyerCode(
							memberBaseInfoService.getMemberCodeById(myNoMemberDto.getMemberId()).getResult());
					transactionRelationDTO.setIsExist("1");// 1.true 0.false
					transactionRelationDTO.setModifyId(myNoMemberDto.getModifyId() + "");
					transactionRelationDTO.setModifyName(myNoMemberDto.getModifyName());
					transactionRelationDTO.setModifyTime(new Date());
					transactionRelationService.updateTransactionRelation(transactionRelationDTO);
				}
			}
			saveUnMemberStatus(myNoMemberDto);
			rs.setResultMessage("success");
			rs.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("myMemberService--------------saveUnMemberCompanyVerify:" + e);
			rs.setResult(false);
			rs.setResultMessage("fail");
			rs.addErrorMessage("审核异常");
		}
		return rs;
	}

	/**
	 * 保存审核状态
	 * 
	 * @param myNoMemberDto
	 * @return
	 * @throws ParseException
	 */
	private boolean saveUnMemberStatus(MyNoMemberDTO myNoMemberDto) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		MemberStatusInfo statusInfo = new MemberStatusInfo();// 运营商审核状态信息
		statusInfo.setSyncErrorMsg(myNoMemberDto.getRemark());
		statusInfo.setModifyId(myNoMemberDto.getModifyId());
		statusInfo.setModifyName(myNoMemberDto.getModifyName());
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(myNoMemberDto.getMemberId());
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setModifyTime(trancDate);
		statusInfo.setVerifyStatus(myNoMemberDto.getVerifyStatus());
		statusInfo.setSyncKey(null);
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);
		memberBaseOperationDAO.saveMemberStatusInfo(statusInfo);// 更新会员运营审核状态

		// 更新审核表审核状态
		VerifyResultDTO verifyInfo = memberBaseOperationDAO.selectVerifyByIdAndInfoType(myNoMemberDto.getMemberId(),
				GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);// 查询审核id
		VerifyInfo verufyInfo = new VerifyInfo();
		verufyInfo.setModifyId(myNoMemberDto.getModifyId());
		verufyInfo.setModifyName(myNoMemberDto.getModifyName());
		verufyInfo.setModifyTime(trancDate);
		verufyInfo.setId(verifyInfo.getVerifyId());
		verufyInfo.setRemark(myNoMemberDto.getRemark());
		verufyInfo.setVerifierId(myNoMemberDto.getModifyId());
		verufyInfo.setVerifierName(myNoMemberDto.getModifyName());
		verufyInfo.setVerifyTime(trancDate);
		verufyInfo.setVerifyStatus(myNoMemberDto.getVerifyStatus());
		memberModifyVerifyDAO.updateVerifyInfo(verufyInfo);

		return true;
	}

	@Override
	public ExecuteResult<MyNoMemberDTO> getUnMemberModify(Long memberId) {
		ExecuteResult<MyNoMemberDTO> rs = new ExecuteResult<MyNoMemberDTO>();
		try {
			MyNoMemberDTO myNomember = new MyNoMemberDTO();
			VerifyResultDTO verifyInfo = memberBaseOperationDAO.selectVerifyByIdAndInfoType(memberId,
					GlobalConstant.INFO_TYPE_VERIFY_UNMEMBER_MODIFY);// 查询审核id
			List<Long> verifyIds = new ArrayList<Long>();
			verifyIds.add(verifyInfo.getVerifyId());
			List<VerifyDetailInfoDTO> verList = memberModifyVerifyDAO.selectVerifyByVerifyIds(verifyIds);
			if (verList.size() > 0) {
				myNomember.setCompanyName(verList.get(0).getBeforeChange());
				myNomember.setNewCompanyName(verList.get(0).getAfterChange());
				myNomember.setVerifyId(verifyInfo.getVerifyId());
			}
			rs.setResult(myNomember);
			rs.setResultMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("myMemberService--------------getUnMemberModify:" + e);
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
		}
		return rs;
	}

	/**
	 * 修改信息下行ERP
	 * 
	 * @param myNoMemberDTO
	 * @return
	 */
	private boolean saveDownErpStatus(MyNoMemberDTO dto) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate;
		try {
			trancDate = format.parse(format.format(new Date()));
			MemberStatusInfo statusInfo = new MemberStatusInfo();
			statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_MODIFY);
			statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
			statusInfo.setSyncErrorMsg(null != dto.getRemark() ? dto.getRemark() : "");
			statusInfo.setModifyId(dto.getModifyId());
			statusInfo.setModifyName(dto.getModifyName());
			statusInfo.setMemberId(dto.getMemberId());
			statusInfo.setVerifyId(0L);
			statusInfo.setVerifyTime(trancDate);
			statusInfo.setSyncKey(KeygenGenerator.getUidKey());
			statusInfo.setModifyTime(trancDate);
			statusInfo.setCreateId(dto.getModifyId());
			statusInfo.setCreateName(dto.getModifyName());
			statusInfo.setCreateTime(trancDate);
			memberBaseOperationDAO.deleteMemberStatus(statusInfo);
			memberBaseOperationDAO.insertMemberStatus(statusInfo);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public ExecuteResult<Long> calculationMemberDealEvent(Long sellerId) {
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		try {
			Long count = Long.valueOf(
					memberVerifyStatusDao.selectByStatusList(null, "1", sellerId, null, null, null, null).size());
			rs.setResult(count);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			e.printStackTrace();
			logger.error("myMemberService--------------calculationMemberDealEvent:" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Long> calculationBusinessRelationDealEvent(Long sellerId) {
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		try {
			Long count = Long.valueOf(applyRelationshipDao.selectBusinessRelationshipCount(sellerId, null).size());
			rs.setResult(count);
			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			e.printStackTrace();
			logger.error("myMemberService--------------calculationBusinessRelationDealEvent:" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<AthenaEventDTO>> calculationAthenaDealEvent() {
		ExecuteResult<List<AthenaEventDTO>> rs = new ExecuteResult<List<AthenaEventDTO>>();
		List<AthenaEventDTO> list = new ArrayList<AthenaEventDTO>();
		try {
			AthenaEventDTO dto = null;
			MemberBaseInfoDTO memberBaseInfoDTO = new MemberBaseInfoDTO();
			memberBaseInfoDTO.setBuyerSellerType(GlobalConstant.IS_BUYER);
			memberBaseInfoDTO.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
			memberBaseInfoDTO.setMemberVerifyStatus("1");

			// 会员注册审核事项
			dto = new AthenaEventDTO();
			dto.setInfoType(GlobalConstant.INFO_TYPE_VERIFY);
			dto.setCount(memberBaseOperationDAO.selectVerifyMemberCount(memberBaseInfoDTO));
			list.add(dto);

			// 会员修改审核事项
			dto = new AthenaEventDTO();
			dto.setInfoType(GlobalConstant.INFO_TYPE_MEMBER_MODIFY);
			dto.setCount(memberModifyVerifyDAO.selectMemberModifyCount(memberBaseInfoDTO));
			list.add(dto);

			// 非会员注册审核
			dto = new AthenaEventDTO();
			dto.setInfoType(GlobalConstant.INFO_TYPE_UNMEMBER_VERIFY);
			dto.setCount(memberModifyVerifyDAO.selectUnMemberVerifyCount(memberBaseInfoDTO));
			list.add(dto);

			// 非会员转会员注册审核
			dto = new AthenaEventDTO();
			dto.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_NO_TO_MEMBER);
			MemberUncheckedDTO dtoCheck = new MemberUncheckedDTO();
			dtoCheck.setVerify_status(GlobalConstant.VERIFY_STATUS_WAIT);
			dto.setCount(Long.valueOf(memberStatusDao.queryNonMemberToMemberListCount(dtoCheck)));
			list.add(dto);

			// 密码找回审核
			dto = new AthenaEventDTO();
			dto.setInfoType("1");// 密码找回
			dto.setCount(Long.valueOf(memberStatusDao.queryPasswordRecoveryVerifyListCount(dtoCheck)));
			list.add(dto);

			// 手机更改审核
			dto = new AthenaEventDTO();
			dto.setInfoType("3");// 手机号更改审核
			dto.setCount(Long.valueOf(memberStatusDao.queryPhoneChangeVerifyListCount(dtoCheck)));
			list.add(dto);

			// 解除归属关系审核
			dto = new AthenaEventDTO();
			MemberRemoveRelationshipDTO shipDto = new MemberRemoveRelationshipDTO();
			dto.setInfoType("25");// 接触归属关系
			shipDto.setStatus(GlobalConstant.VERIFY_STATUS_WAIT);
			dto.setCount(Long.valueOf(memberStatusDao.queryRemoveRelationshipCount(shipDto)));
			list.add(dto);

			rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			e.printStackTrace();
			logger.error("myMemberService--------------calculationAthenaDealEvent:" + e);
		}
		rs.setResult(list);
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> getNoMemberName(MyNoMemberDTO myNoMemberDto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			rs.setCode("00000");
			if (myNoMemberDto.getMemberId() == null) {
				myNoMemberDto.setMemberId(0L);
			}
			if (myNoMemberDto.getCompanyName() == null) {
				rs.setResultMessage("公司名称为空");
				rs.setResult(Boolean.FALSE);
				return rs;
			}
			List<MyNoMemberDTO> nmDto = memberDAO.getNoMemberName(myNoMemberDto.getCompanyName(),
					myNoMemberDto.getMemberId());
			if (nmDto != null && nmDto.size() >= 1) {
				rs.setResult(Boolean.TRUE);
				rs.addErrorMessage("会员名称已存在");
				return rs;
			}
			rs.setResult(Boolean.FALSE);
		} catch (Exception e) {
			rs.setCode("99999");
			rs.addErrorMessage(e.getMessage());
		}
		return rs;
	}

	/**
	 * VMS - 根据供应商Id查询该供应商下的会员、担保会员、非会员数量
	 * 
	 * @author li.jun
	 * @param sellerId
	 * @return
	 */
	@Override
	public ExecuteResult<MemberCountDTO> queryMemberCountInfo(MyMemberSearchDTO memberSearch,Long sellerId) {
		long startTime = System.currentTimeMillis();
		ExecuteResult<MemberCountDTO> result = new ExecuteResult<MemberCountDTO>();
		MemberCountDTO memberCount = new MemberCountDTO();
		String memberType = memberSearch.getMemberType();
		MyMemberSearchDTO search = new MyMemberSearchDTO();
		search.setSysFlag("1");
		search.setStatus("1");
		try {
			if(StringUtils.isEmpty(memberType)){//默认条件
				// 我的会员
				Long myMemberCount = gerMemberCount(sellerId, memberSearch, 1, null, 1);
				if (myMemberCount != null) {
					memberCount.setMyMemberCount(myMemberCount.intValue());
				}
				// 担保会员
				Long guaranteeMemberCount = gerMemberCount(sellerId, memberSearch, 1, 1, 0);
				if (guaranteeMemberCount != null) {
					memberCount.setGuaranteeMemberCount(guaranteeMemberCount.intValue());
				}
				// 非会员
				memberSearch.setStatus("");// 非会员有效无效都查
				Long noMemberCount = memberDAO.selectNoMemberCount(sellerId, memberSearch, 0);
				if(noMemberCount != null) {
					memberCount.setNoMemberCount(noMemberCount.intValue());
				}
			}else if("2".equals(memberType)){//带有会员条件的查询
				// 我的会员
				Long myMemberCount = gerMemberCount(sellerId, memberSearch, 1, null, 1);
				if (myMemberCount != null) {
					memberCount.setMyMemberCount(myMemberCount.intValue());
				}
				// 担保会员
				Long guaranteeMemberCount = gerMemberCount(sellerId, search, 1, 1, 0);
				if (guaranteeMemberCount != null) {
					memberCount.setGuaranteeMemberCount(guaranteeMemberCount.intValue());
				}
				// 非会员
				search.setStatus("");// 非会员有效无效都查
				Long noMemberCount = memberDAO.selectNoMemberCount(sellerId, search, 0);
				if(noMemberCount != null) {
					memberCount.setNoMemberCount(noMemberCount.intValue());
				}
			}else if("3".equals(memberType)){//带有担保会员条件的查询
				// 我的会员
				Long myMemberCount = gerMemberCount(sellerId, search, 1, null, 1);
				if (myMemberCount != null) {
					memberCount.setMyMemberCount(myMemberCount.intValue());
				}
				// 担保会员
				Long guaranteeMemberCount = gerMemberCount(sellerId, memberSearch, 1, 1, 0);
				if (guaranteeMemberCount != null) {
					memberCount.setGuaranteeMemberCount(guaranteeMemberCount.intValue());
				}
				// 非会员
				search.setStatus("");// 非会员有效无效都查
				Long noMemberCount = memberDAO.selectNoMemberCount(sellerId, search, 0);
				if(noMemberCount != null) {
					memberCount.setNoMemberCount(noMemberCount.intValue());
				}
			}else if("1".equals(memberType)){//带有非会员条件的查询
				// 我的会员
				Long myMemberCount = gerMemberCount(sellerId, search, 1, null, 1);
				if (myMemberCount != null) {
					memberCount.setMyMemberCount(myMemberCount.intValue());
				}
				// 担保会员
				Long guaranteeMemberCount = gerMemberCount(sellerId, search, 1, 1, 0);
				if (guaranteeMemberCount != null) {
					memberCount.setGuaranteeMemberCount(guaranteeMemberCount.intValue());
				}
				// 非会员
				memberSearch.setStatus("");// 非会员有效无效都查
				Long noMemberCount = memberDAO.selectNoMemberCount(sellerId, memberSearch, 0);
				if(noMemberCount != null) {
					memberCount.setNoMemberCount(noMemberCount.intValue());
				}
			}
			long endTime = System.currentTimeMillis();
			logger.info("程序运行时间：" + (endTime - startTime) / 1000 + "s");
		} catch (Exception e) {
			logger.error("查询会员数量报错-->" + e);
			result.addErrorMessage(e.getMessage());
		}
		result.setResult(memberCount);
		return result;
	}
	

	/**
	 * VMS - 查询我的会员、担保会员列表
	 * 
	 * @author li.jun
	 * @time 2018-01-10
	 */
	@Override
	public ExecuteResult<DataGrid<MyMemberDTO>> selectMemberList(Pager page, Long sellerId,
			MyMemberSearchDTO memberSearch, String type) {
		ExecuteResult<DataGrid<MyMemberDTO>> rs = new ExecuteResult<DataGrid<MyMemberDTO>>();
		DataGrid<MyMemberDTO> dg = new DataGrid<MyMemberDTO>();
		try {
			List<MyMemberDTO> myMemberDtoList = null;
			Long count = null;
			if (type != null && type.equals("2")) {
				count = gerMemberCount(sellerId, memberSearch, 1, null, 1);
				if (count != null && count > 0) {
					myMemberDtoList = gerMemberList(page, sellerId, memberSearch, 1, null, 1);
				}
			} else if (type != null && type.equals("3")) {
				count = gerMemberCount(sellerId, memberSearch, 1, 1, 0);
				if (count != null && count > 0) {
					myMemberDtoList = gerMemberList(page, sellerId, memberSearch, 1, 1, 0);
				}
			}
			dg.setRows(myMemberDtoList);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("MyMemberServiceImpl----->selectMemberList=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("查询我的会员、担保会员出错");
		}

		return rs;
	}

	/**
	 * VMS - 获取会员、担保会员列表
	 * @author li.jun
	 * @time 2018-01-10
	 * @return
	 */
	private List<MyMemberDTO> gerMemberList(Pager page, Long sellerId, MyMemberSearchDTO memberSearch,
			Integer canMallLogin, Integer hasGuaranteeLicense, Integer hasBusinessLicense) {
		List<MyMemberDTO> list = new ArrayList<MyMemberDTO>();
		List<MyMemberDTO> memberlist = null;
		List<Long> memberIds = new ArrayList<Long>();
		memberlist = memberDAO.selectMemberIdList(page, sellerId, memberSearch, canMallLogin, hasGuaranteeLicense,
				hasBusinessLicense);
		if (memberlist != null && memberlist.size() > 0) {
			for (MyMemberDTO dto : memberlist) {
				memberIds.add(dto.getMemberId());
			}
		}
		memberSearch.setMemberIds(memberIds);
		list = memberDAO.selectMemberList(page, sellerId, memberSearch, canMallLogin, hasGuaranteeLicense,
				hasBusinessLicense);
		return list;
	}

	/**
	 * VMS - 获取会员、担保会员数量
	 * @author li.jun
	 * @time 2018-01-10
	 * @return
	 */
	private Long gerMemberCount(Long sellerId, MyMemberSearchDTO memberSearch,
			Integer canMallLogin, Integer hasGuaranteeLicense, Integer hasBusinessLicense) {
		Long count = memberDAO.selectMemberListCount(sellerId, memberSearch, canMallLogin, hasGuaranteeLicense, hasBusinessLicense);
		return count;
	}

}

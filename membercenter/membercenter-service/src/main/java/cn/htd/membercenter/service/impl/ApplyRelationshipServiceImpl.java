package cn.htd.membercenter.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.encrypt.KeygenGenerator;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.membercenter.common.annotation.VerifyDetail;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.ApplyRelationshipDAO;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dao.BoxRelationshipDAO;
import cn.htd.membercenter.dao.ConsigneeAddressDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberBusinessRelationDAO;
import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dao.MemberStatusDao;
import cn.htd.membercenter.dao.MemberVerifySaveDAO;
import cn.htd.membercenter.dao.MyMemberDAO;
import cn.htd.membercenter.domain.MemberLicenceInfo;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BindingBankCardCallbackDTO;
import cn.htd.membercenter.dto.BindingBankCardDTO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.CategoryBrandDTO;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberContractInfo;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.QueryRegistProcessDTO;
import cn.htd.membercenter.dto.SalemanDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.enums.AuditStatusEnum;
import cn.htd.membercenter.service.ApplyRelationshipService;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberGradeService;
import cn.htd.membercenter.service.MemberStatusService;
import cn.htd.membercenter.service.PayInfoService;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.service.CustomerService;

@Service("applyRelationshipService")
public class ApplyRelationshipServiceImpl implements ApplyRelationshipService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBaseServiceImpl.class);

	@Resource
	BelongRelationshipDAO belongRelationshipDAO;
	@Resource
	MemberBusinessRelationDAO memberBusinessRelationDAO;
	@Resource
	private ApplyRelationshipDAO applyRelationshipDao;
	@Resource
	private MyMemberDAO myMemberDao;
	@Resource
	private BoxRelationshipDAO boxRelationshipDao;
	@Autowired
	private ItemCategoryService itemCategoryService;
	@Autowired
	private ItemBrandExportService itemBrandExportService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MemberStatusService memberStatusService;
	@Autowired
	MemberBaseService memberBaseService;
	@Resource
	private MemberStatusDao memberStatusDao;
	@Resource
	private ConsigneeAddressDAO consigneeAddressDAO;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Autowired
	private PayInfoService payInfoService;

	@Resource
	private MemberVerifySaveDAO verifyInfoDAO;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	@Resource
	private MemberGradeService memberGradeService;

	@Resource
	BelongRelationshipDAO belongRelationshipDao;

	@Override
	public ExecuteResult<String> applyNoBelongRelationship(BelongRelationshipDTO belongRelationshipDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (belongRelationshipDto != null) {
				// applyRelationshipDao.applyNoBelongRelationship(belongRelationshipDto);
				Long num = applyRelationshipDao.insertVerifyInfo(belongRelationshipDto,
						GlobalConstant.REMOVE_RELATION_VERIFY);
				saveRemoveRelationStatus(belongRelationshipDto);
				if (num != null) {
					/// applyRelationshipDao.insertVerifyDetailInfo(belongRelationshipDto,
					/// "25");
				}
				rs.setResultMessage("success");
				rs.setResultMessage("保存解除会员归属关系为待审核信息成功！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->ApplyNoBelongRelationship=" + "申请解除会员归属关系失败！！");
			rs.setResultMessage("error");
			throw new RuntimeException("申请解除会员归属关系失败！！");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> applynoMemberToMember(MyNoMemberDTO myNoMemberDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		BelongRelationshipDTO belongRelationshipDTO = new BelongRelationshipDTO();

		try {
			if (myNoMemberDto != null) {

				boolean mobilecheck = memberBaseInfoService.checkMemberMobile(myNoMemberDto.getArtificialPersonMobile(),
						myNoMemberDto.getMemberId());
				if (mobilecheck) {
					rs.addErrorMessage("手机号已存在，请重新填写!");
					return rs;
				}

				// Long memberId = myNoMemberDto.getMemberId();
				// Long modifyId = myNoMemberDto.getModifyId();
				// String modifyName = myNoMemberDto.getModifyName();
				// applyRelationshipDao.applynoMemberToMember(memberId,
				// modifyId, modifyName);
				String locationAddr = "";
				if (StringUtils.isBlank(myNoMemberDto.getLocationTown())
						|| myNoMemberDto.getLocationTown().equals("0")) {
					locationAddr = memberBaseService.getAddressBaseByCode(myNoMemberDto.getLocationCounty())// 拼接详细地址
							+ myNoMemberDto.getLocationDetail();
				} else {
					locationAddr = memberBaseService.getAddressBaseByCode(myNoMemberDto.getLocationTown())// 拼接详细地址
							+ myNoMemberDto.getLocationDetail();
				}

				myNoMemberDto.setLocationAddr(locationAddr);

				String memberType = myNoMemberDto.getMemberType();
				if (memberType.equals("2")) {
					myNoMemberDto.setHasBusinessLicense(1);
				} else if (memberType.equals("3")) {
					myNoMemberDto.setHasGuaranteeLicense(1);
					myNoMemberDto.setHasBusinessLicense(0);
				}
				if ("7".equals(myNoMemberDto.getIndustryCategory())) {
					myNoMemberDto.setIsDiffIndustry(GlobalConstant.FLAG_NO);
				} else {
					myNoMemberDto.setIsDiffIndustry(GlobalConstant.FLAG_YES);
				}
				myNoMemberDto.setIsPhoneAuthenticated(GlobalConstant.FLAG_NO);
				if (null == myNoMemberDto.getArtificialPersonPicBackSrc()) {
					myNoMemberDto.setArtificialPersonPicBackSrc("");
				}
				if (null == myNoMemberDto.getArtificialPersonPicSrc()) {
					myNoMemberDto.setArtificialPersonPicSrc("");
				}
				if (null != myNoMemberDto.getHasGuaranteeLicense() && 1 == myNoMemberDto.getHasGuaranteeLicense()) {
					myNoMemberDto.setHasBusinessLicense(0);
				}
				myNoMemberDto.setCurBelongSellerId(myNoMemberDto.getSellerId());

				myMemberDao.updateMemberCompanyInfo(myNoMemberDto);
				myMemberDao.deleteMemberLicenceInfo(myNoMemberDto);
				myMemberDao.insertMememberLicenceInfo(myNoMemberDto);

				belongRelationshipDTO.setMemberId(myNoMemberDto.getMemberId());
				belongRelationshipDTO.setCurBelongSellerId(myNoMemberDto.getCurBelongSellerId());
				belongRelationshipDTO.setModifyId(myNoMemberDto.getModifyId());
				belongRelationshipDTO.setBuyerFeature(myNoMemberDto.getBuyerFeature());
				belongRelationshipDTO.setModifyName(myNoMemberDto.getModifyName());

				MemberBaseInfoDTO memberBase = memberBaseOperationDAO.getMemberBaseInfoById(myNoMemberDto.getMemberId(),
						GlobalConstant.IS_BUYER);

				if (!myNoMemberDto.getSellerId().equals(memberBase.getCurBelongSellerId())) {
					String sellerCode = memberBaseOperationDAO.getMemberCodeById(myNoMemberDto.getSellerId());
					String manageCode = getManageDefaultCodeBySellerCode(sellerCode);
					belongRelationshipDTO.setCurBelongManagerId(manageCode);
					myNoMemberDto.setCurBelongManagerId(manageCode);

					belongRelationshipDao.updateBelongInfo(belongRelationshipDTO);
					belongRelationshipDTO.setVerifyStatus("3");
					belongRelationshipDao.insertBelongInfo(belongRelationshipDTO);
				}
				myMemberDao.updateMemberBaseInfo(myNoMemberDto);

				/*
				 * Long num = applyRelationshipDao.insertVerifyInfo(
				 * belongRelationshipDTO, "14"); if (num != null) {
				 * applyRelationshipDao.insertVerifyDetailInfo(
				 * belongRelationshipDTO, "14"); }
				 */
				// 非会员转会员状态改变而不是新增一条记录
				// applyRelationshipDao.updateMemberVerifyInfo(memberId, "1",
				// "14", modifyId, modifyName);
				// applyRelationshipDao.updateVerifyDetailInfo(memberId, "14",
				// modifyId, modifyName);
				addNoToMemberVerifyStatus(myNoMemberDto);
				saveDownErpStatus(myNoMemberDto);
				rs.setResultMessage("success");
				rs.setResultMessage("保存非会员转会员为待审核状态成功！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->ApplynoMemberToMember=" + "保存非会员转会员为待审核状态失败！！");
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", "保存非会员转会员为待审核状态系失败！！"));
			rs.setResultMessage("error");
			throw new RuntimeException("保存非会员转会员为待审核状态失败！！");
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<ApplyBusiRelationDTO>> selectBusinessRelationship(Long curBelongSellerId,
			String companyName) {
		ExecuteResult<DataGrid<ApplyBusiRelationDTO>> rs = new ExecuteResult<DataGrid<ApplyBusiRelationDTO>>();
		DataGrid<ApplyBusiRelationDTO> dg = new DataGrid<ApplyBusiRelationDTO>();
		try {
			List<ApplyBusiRelationDTO> businessApplyDtoList = null;
			// 查询个数
			List<ApplyBusiRelationDTO> businessApplyDtoListCount = applyRelationshipDao
					.selectBusinessRelationshipCount(curBelongSellerId, companyName);
			if (businessApplyDtoListCount != null) {
				for (int i = 0; i < businessApplyDtoListCount.size(); i++) {
					List<CategoryBrandDTO> CategoryBrandList = new ArrayList<CategoryBrandDTO>();
					businessApplyDtoList = applyRelationshipDao.selectBusinessRelationship(curBelongSellerId,
							companyName, businessApplyDtoListCount.get(i).getMemberId());
					for (int j = 0; j < businessApplyDtoList.size(); j++) {
						CategoryBrandDTO categoryBrandDTO = new CategoryBrandDTO();
						ExecuteResult<ItemCategoryDTO> category = itemCategoryService
								.getCategoryByCid(businessApplyDtoList.get(j).getCategoryId());
						if (category.getResult() != null) {
							categoryBrandDTO.setCategoryId(businessApplyDtoList.get(j).getCategoryId());
							categoryBrandDTO.setCategoryName(category.getResult().getCategoryCName());
						}
						ExecuteResult<ItemBrand> brand = itemBrandExportService
								.queryItemBrandById(businessApplyDtoList.get(j).getBrandId());
						if (brand.getResult() != null) {
							categoryBrandDTO.setBrandId(businessApplyDtoList.get(j).getBrandId());
							categoryBrandDTO.setBrandName(brand.getResult().getBrandName());
						}
						categoryBrandDTO.setBusinessId(businessApplyDtoList.get(j).getBusinessId());
						categoryBrandDTO.setCreateTime(businessApplyDtoList.get(j).getCreateTime());
						CategoryBrandList.add(categoryBrandDTO);
					}
					businessApplyDtoListCount.get(i).setCategoryBrand(CategoryBrandList);
				}

				dg.setRows(businessApplyDtoListCount);
				dg.setTotal(new Long(businessApplyDtoListCount.size()));
				rs.setResult(dg);
			} else {
				rs.setResultMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->selectBusinessRelationshipApply=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<QueryRegistProcessDTO> queryRegistProcess(Long memberId, String companyName,
			Long curBelongSellerId) {
		ExecuteResult<QueryRegistProcessDTO> rs = new ExecuteResult<QueryRegistProcessDTO>();
		try {
			List<QueryRegistProcessDTO> registProcessDtoList = applyRelationshipDao.queryRegistProcess(memberId,
					companyName, curBelongSellerId);
			if (registProcessDtoList != null && registProcessDtoList.size() > 0) {
				for (int i = 0; i < registProcessDtoList.size(); i++) {
					// 获取运营审核信息
					QueryRegistProcessDTO queryRegistProcessDTO = registProcessDtoList.get(i);
					if ("11".equals(queryRegistProcessDTO.getInfoType())) {
						rs.setResult(queryRegistProcessDTO);
					} else if ("12".equals(queryRegistProcessDTO.getInfoType())) {
						rs.setResult(queryRegistProcessDTO);
						return rs;
					} else if ("13".equals(queryRegistProcessDTO.getInfoType())) {
						// 非会员注册审核
						rs.setResult(queryRegistProcessDTO);
					} else if ("14".equals(queryRegistProcessDTO.getInfoType())) {
						// 非会员转会员注册审核
						rs.setResult(queryRegistProcessDTO);
						return rs;
					}
				}
			} else {
				rs.setResultMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->queryRegistProcess=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> busiRelationVerify(ApplyBusiRelationDTO businessRelatVerifyDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (businessRelatVerifyDto != null) {
				List<CategoryBrandDTO> categoryBrandList = businessRelatVerifyDto.getCategoryBrand();
				ApplyBusiRelationDTO applyBusiRelation = null;
				if (businessRelatVerifyDto.getAuditStatus().equals(AuditStatusEnum.PASSING_AUDIT.getCode())) {
					// 查询是否有包厢关系
					applyBusiRelation = applyRelationshipDao.queryBoxRelationInfo(businessRelatVerifyDto.getMemberId(),
							businessRelatVerifyDto.getSellerId());
					if (applyBusiRelation == null) {
						businessRelatVerifyDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
						applyRelationshipDao.insertBoxRelationInfo(businessRelatVerifyDto);
					}
				}
				for (int i = 0; i < categoryBrandList.size(); i++) {
					Long categoryId = categoryBrandList.get(i).getCategoryId();
					Long brandId = categoryBrandList.get(i).getBrandId();
					Long businessId = categoryBrandList.get(i).getBusinessId();
					String remark = businessRelatVerifyDto.getRemark();
					businessRelatVerifyDto.setCategoryId(categoryId);
					businessRelatVerifyDto.setBrandId(brandId);
					businessRelatVerifyDto.setBusinessId(businessId);
					Long modifyId = businessRelatVerifyDto.getModifyId();
					String modifyName = businessRelatVerifyDto.getModifyName();
					if (businessRelatVerifyDto.getAuditStatus().equals(AuditStatusEnum.PASSING_AUDIT.getCode())) {
						applyRelationshipDao.updatBusinessRelationship(businessId,
								businessRelatVerifyDto.getAuditStatus(), modifyId, modifyName, remark,
								ErpStatusEnum.PENDING.getValue(), businessRelatVerifyDto.getCustomerManagerId());
					} else if (businessRelatVerifyDto.getAuditStatus().equals(AuditStatusEnum.REJECT_AUDIT.getCode())) {
						applyRelationshipDao.updatBusinessRelationship(businessId,
								businessRelatVerifyDto.getAuditStatus(), modifyId, modifyName, remark, "", "");
					}
				}

				// goto会员经营关系及包厢关系同步到Redis中

				rs.setResultMessage("审核状态保存成功");

			} else {
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->busiRelationVerify=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> insertOutSellerInfo(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (outCompanyDto != null) {
				String membercode = outCompanyDto.getMemberCode();
				if (StringUtils.isBlank(membercode)) {
					MemberOutsideSupplierCompanyDTO checkDto = new MemberOutsideSupplierCompanyDTO();
					checkDto.setCompanyName(outCompanyDto.getCompanyName());
					checkDto.setArtificialPersonMobile(outCompanyDto.getArtificialPersonMobile());
					boolean mobilecheck = this.checkOutCompanyRegister(checkDto).getResult();
					if (!mobilecheck) {
						rs.addErrorMessage("手机号已存在，请重新填写!");
						return rs;
					}
				}

				if (null == outCompanyDto.getOperateId()) {
					outCompanyDto.setOperateId("0");
				}
				outCompanyDto.setIndustryCategory(outCompanyDto.getBusinessCategory());
				outCompanyDto.setBelongSellerId(outCompanyDto.getCurBelongSellerId());

				if ("2".equals(outCompanyDto.getCertificateType())) {// 一证一号，统一信用号就是营业执照号
					outCompanyDto.setBusinessLicenseId(outCompanyDto.getUnifiedSocialCreditCode());
				}

				Long num = null;
				String locationAddr = memberBaseService.getAddressBaseByCode(outCompanyDto.getLocationCounty())// 拼接详细地址
						+ outCompanyDto.getLocationDetail();
				outCompanyDto.setLocationAddr(locationAddr);

				String detailAttr = memberBaseService.getAddressBaseByCode(outCompanyDto.getBankCounty());
				outCompanyDto.setBankBranchIsLocated(detailAttr);

				try {
					String companyAddress = memberBaseService.getAddressBaseByCode(outCompanyDto.getLocationCounty())
							+ outCompanyDto.getLocationDetail();
					outCompanyDto.setLocationAddr(companyAddress);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (StringUtils.isNotBlank(membercode)) {// 由小b升级而来
					num = applyRelationshipDao.updateMemberBaseCodeInfo(outCompanyDto);
					Long memberID = applyRelationshipDao.getMemberID(membercode);
					MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseById(memberID,
							GlobalConstant.IS_BUYER);
					outCompanyDto.setMemberId(memberID);
					outCompanyDto.setAccountNo(baseInfo.getAccountNo());

					YijifuCorporateCallBackDTO modifyPayInfo = downToYijifuModifyUpdate(outCompanyDto);
					if (null == modifyPayInfo.getResultCode()
							|| !"EXECUTE_SUCCESS".equals(modifyPayInfo.getResultCode())) {
						String detail = modifyPayInfo.getResultDetail();
						if (null != detail
								&& (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
							rs.addErrorMessage("请输入正确身份证号");
							rs.setResultMessage("请输入正确身份证号");
						} else if (null != modifyPayInfo.getResultMessage()
								&& (modifyPayInfo.getResultMessage().indexOf("certNo") >= 0
										|| modifyPayInfo.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
							rs.addErrorMessage("请输入正确身份证号");
							rs.setResultMessage("请输入正确身份证号");
						} else {
							rs.addErrorMessage(detail);
							rs.setResultMessage(detail);
						}
						return rs;
					}
					outCompanyDto.setBuyerSellerType("2");
					outCompanyDto.setRealNameStatus("3");
					applyRelationshipDao.insertMemberCompanyInfo(outCompanyDto);

					// 更新会员审核状态
					MemberBaseInfoDTO dto = new MemberBaseInfoDTO();
					logger.info("==================公司companyID为:" + baseInfo.getCompanyId());
					dto.setCompanyId(baseInfo.getCompanyId());
					dto.setRealNameStatus("3");
					dto.setModifyId(Long.valueOf(outCompanyDto.getOperateId()));
					dto.setModifyName(outCompanyDto.getOperateName());
					// 绑定银行卡
					BindingBankCardCallbackDTO isBankSuccess = firstBindBankCard(outCompanyDto);

					if (null != isBankSuccess && "APPLY".equals(isBankSuccess.getPactStatus())) {
						outCompanyDto.setCardBindStatus("2");
						outCompanyDto.setBindId(isBankSuccess.getBindId());
					} else if (null != isBankSuccess && "ENABLE".equals(isBankSuccess.getPactStatus())) {
						outCompanyDto.setCardBindStatus("3");
						outCompanyDto.setBindId(isBankSuccess.getBindId());
					} else {
						outCompanyDto.setCardBindStatus("4");
					}

					memberBaseOperationDAO.updateMemberCompanyPayInfo(dto);

					// 更新供应商信息
					updateMemberAndSupplier(outCompanyDto);
				} else {
					// 获取当前客户经理iD
					outCompanyDto.setCurBelongManagerId("08019999");
					String code = customerService.genOUTSellerCode().getResult();
					if (code == null) {
						rs.setResultMessage("获得外部商家code失败！");
						return rs;
					}

					// 生成支付信息
					outCompanyDto.setMemberCode(code);
					outCompanyDto.setCanMallLogin(GlobalConstant.FLAG_YES);
					outCompanyDto.setStatus(GlobalConstant.STATUS_YES);
					YijifuCorporateCallBackDTO payInfoBack = downToYijifu(outCompanyDto, GlobalConstant.FLAG_NO);
					if (null == payInfoBack.getResultCode() || !"EXECUTE_SUCCESS".equals(payInfoBack.getResultCode())) {
						String detail = payInfoBack.getResultDetail();
						if (null != detail
								&& (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else if (null != payInfoBack.getResultMessage()
								&& (payInfoBack.getResultMessage().indexOf("certNo") >= 0
										|| payInfoBack.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
							rs.addErrorMessage("请输入正确身份证号");
						} else {
							rs.addErrorMessage(detail);
						}
						rs.setResultMessage("fail");
						return rs;
					}
					if ("AUTH_OK".equals(payInfoBack.getStatus())) {
						outCompanyDto.setRealNameStatus("3");
					} else {
						outCompanyDto.setRealNameStatus("1");
					}
					outCompanyDto.setRealNameStatus("3");// 统一设置成已实名
					String accountNo = "0";
					if (null != payInfoBack.getAccountNo()) {
						accountNo = payInfoBack.getAccountNo();
					}
					outCompanyDto.setAccountNo(accountNo);

					// 设置归属0801二代归属客户经理和归属商家
					setBelongSellerAndManage(outCompanyDto);

					// 设置是否异业
					if ("7".equals(outCompanyDto.getIndustryCategory())) {
						outCompanyDto.setIsDiffIndustry(GlobalConstant.FLAG_NO);
					} else {
						outCompanyDto.setIsDiffIndustry(GlobalConstant.FLAG_YES);
					}
					num = applyRelationshipDao.insertMemberBaseInfo(outCompanyDto);

					// 绑定银行卡
					BindingBankCardCallbackDTO isBankSuccess = firstBindBankCard(outCompanyDto);
					
					if (null != isBankSuccess && "APPLY".equals(isBankSuccess.getPactStatus())) {
						outCompanyDto.setCardBindStatus("2");
						outCompanyDto.setBindId(isBankSuccess.getBindId());
					} else if (null != isBankSuccess && "ENABLE".equals(isBankSuccess.getPactStatus())) {
						outCompanyDto.setCardBindStatus("3");
						outCompanyDto.setBindId(isBankSuccess.getBindId());
					} else {
						outCompanyDto.setCardBindStatus("4");
					}
					
				
					outCompanyDto.setBuyerSellerType("1");
					applyRelationshipDao.insertMemberCompanyInfo(outCompanyDto);
					outCompanyDto.setBuyerSellerType("2");
					applyRelationshipDao.insertMemberCompanyInfo(outCompanyDto);

					MemberConsigAddressDTO memberConsigAddressDto = new MemberConsigAddressDTO();
					memberConsigAddressDto.setInvoiceCompanyName(outCompanyDto.getCompanyName());
					memberConsigAddressDto.setInvoiceNotify(outCompanyDto.getCompanyName());
					memberConsigAddressDto.setMemberId(outCompanyDto.getMemberId());
					memberConsigAddressDto.setModifyId(Long.valueOf(outCompanyDto.getOperateId()));
					memberConsigAddressDto.setModifyName(outCompanyDto.getOperateName());
					consigneeAddressDAO.insertInvoiceInfo(memberConsigAddressDto);

					BuyerGradeInfoDTO memberGradeModel = new BuyerGradeInfoDTO();
					memberGradeModel.setBuyerId(outCompanyDto.getMemberId());
					memberGradeModel.setBuyerGrade("1");
					memberGradeModel.setPointGrade(1l);
					memberGradeModel.setCreateId(Long.valueOf(outCompanyDto.getOperateId()));
					memberGradeModel.setCreateName(outCompanyDto.getOperateName());
					memberGradeModel.setModifyId(Long.valueOf(outCompanyDto.getOperateId()));
					memberGradeModel.setModifyName(outCompanyDto.getOperateName());
					memberGradeService.insertGrade(memberGradeModel);

					saveBelongBoxRelation(outCompanyDto);// 保存会员归属关系
					addDownErpStatus(outCompanyDto);

					insertMemberAndSupplier(outCompanyDto);

					// 生成用户
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setLoginId(code);
					customerDTO.setMobile(outCompanyDto.getArtificialPersonMobile());
					customerDTO.setPassword(outCompanyDto.getArtificialPersonMobile());
					customerDTO.setName(outCompanyDto.getCompanyName());
					customerDTO.setIsVmsInnerUser(false);
					customerDTO.setCompanyId(outCompanyDto.getMemberId());
					customerService.addOUTSeller(customerDTO, Long.valueOf(outCompanyDto.getOperateId()));
				}
				if (num != null) {
					inserMemberAttachInfo(outCompanyDto);
					rs.setResult(outCompanyDto.getMemberId().toString());
					if ("4".equals(outCompanyDto.getCardBindStatus())) {
						rs.setResultMessage("绑定银行卡失败");
					} else {
						rs.setResultMessage("保存商家入驻成功");
					}
				}
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("请设置入驻商家信息!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->insertOutSellerInfo=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> updateOutSellerInfo(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		ExecuteResult<MemberOutsideSupplierCompanyDTO> companyDto = new ExecuteResult<MemberOutsideSupplierCompanyDTO>();
		String realNameStatus = "3";// 企业实名认证状态
		String cardBindStatus = "1";// 银行卡绑定状态
		String locationAddr = memberBaseService.getAddressBaseByCode(outCompanyDto.getLocationCounty())// 拼接详细地址
				+ outCompanyDto.getLocationDetail();
		outCompanyDto.setLocationAddr(locationAddr);

		String detailAttr = memberBaseService.getAddressBaseByCode(outCompanyDto.getBankCounty());
		outCompanyDto.setBankBranchIsLocated(detailAttr);
		try {
			if (outCompanyDto != null) {

				if ("2".equals(outCompanyDto.getCertificateType())) {// 一证一号，统一信用号就是营业执照号
					outCompanyDto.setBusinessLicenseId(outCompanyDto.getUnifiedSocialCreditCode());
				}

				Long memberId = outCompanyDto.getMemberId();
				MemberOutsideSupplierCompanyDTO outsideSupplierCompany = memberStatusDao
						.queryOutsideSupplierCompany(memberId);

				boolean isUpdateBank = true;
				if (outCompanyDto.getMemberId() != null) {
					companyDto = memberStatusService.queryOutsideSupplierCompany(outCompanyDto.getMemberId());
					try {
						YijifuCorporateCallBackDTO modifyPayInfo = downToYijifuModify(companyDto.getResult());
						if (null == modifyPayInfo.getResultCode()
								|| !"EXECUTE_SUCCESS".equals(modifyPayInfo.getResultCode())) {
							String detail = modifyPayInfo.getResultDetail();
							if (null != detail
									&& (detail.indexOf("certNo") >= 0 || detail.indexOf("legalPersonCertNo") >= 0)) {
								rs.addErrorMessage("请输入正确身份证号");
								rs.setResultMessage("请输入正确身份证号");
							} else if (null != modifyPayInfo.getResultMessage()
									&& (modifyPayInfo.getResultMessage().indexOf("certNo") >= 0
											|| modifyPayInfo.getResultMessage().indexOf("legalPersonCertNo") > 0)) {
								rs.addErrorMessage("请输入正确身份证号");
								rs.setResultMessage("请输入正确身份证号");
							} else {
								rs.addErrorMessage(detail);
								rs.setResultMessage(detail);
							}
							return rs;
						}

						boolean nobind = false;
						MemberOutsideSupplierCompanyDTO oldBankInfo = companyDto.getResult();
						if (oldBankInfo != null) {
							if (outCompanyDto.getBankAccountName().equalsIgnoreCase(oldBankInfo.getBankAccountName())
									&& outCompanyDto.getBankAccount().equalsIgnoreCase(oldBankInfo.getBankAccount())
									&& outCompanyDto.getBankName().equalsIgnoreCase(oldBankInfo.getBankName())
									&& outCompanyDto.getBankBranchJointLine()
											.equalsIgnoreCase(oldBankInfo.getBankBranchJointLine())
									&& outCompanyDto.getBankBranchIsLocated()
											.equalsIgnoreCase(oldBankInfo.getBankBranchIsLocated())) {
								nobind = true;
								outCompanyDto.setCardBindStatus(oldBankInfo.getCardBindStatus());
							}
						}

						if (!nobind) {
							// 绑定银行卡
							BindingBankCardCallbackDTO isBankSuccess = bindBankCard(companyDto.getResult(),
									outCompanyDto);
							if (null != isBankSuccess && "APPLY".equals(isBankSuccess.getPactStatus())) {
								cardBindStatus = "2";
								outCompanyDto.setBindId(isBankSuccess.getBindId());
							} else if (null != isBankSuccess && "ENABLE".equals(isBankSuccess.getPactStatus())) {
								cardBindStatus = "3";
								outCompanyDto.setBindId(isBankSuccess.getBindId());
							} else {
								cardBindStatus = "4";
							}
							outCompanyDto.setCardBindStatus(cardBindStatus);
						}

					} catch (Exception e) {
						e.printStackTrace();
						logger.error("ApplyRelationshipServiceImpl----->updateOutSellerInfo=" + e);
						rs.setResultMessage("修改注册实名认证失败");
						// rs.setResultMessage("error");
						realNameStatus = "1";
					}

					if (realNameStatus != null && cardBindStatus != null) {
						outCompanyDto.setRealNameStatus(realNameStatus);
						outCompanyDto.setRealNameStatus("3");// 统一设置成已实名
						try {
							String companyAddress = memberBaseService.getAddressBaseByCode(
									outCompanyDto.getLocationCounty()) + outCompanyDto.getLocationDetail();
							outCompanyDto.setLocationAddr(companyAddress);
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (isUpdateBank) {
							applyRelationshipDao.updateMemberBankInfo(outCompanyDto);
						}
						applyRelationshipDao.updateMemberLicenceInfo(outCompanyDto);
						applyRelationshipDao.updateMemberCompanyInfo(outCompanyDto);
						applyRelationshipDao.updateMemberExtendInfo(outCompanyDto);
						applyRelationshipDao.updatetMemberBaseInfo(outCompanyDto);

						saveVerifyDetail(outsideSupplierCompany, outCompanyDto);
						if ("4".equals(outCompanyDto.getCardBindStatus())) {
							rs.setResultMessage("绑定银行卡失败");
						} else {
							rs.setResultMessage("success");
						}
						rs.setResult("success");
					}

				} else {
					rs.setResultMessage("请设置会员ID!!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->updateOutSellerInfo=" + e);
			rs.setResultMessage("保存失败");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> updateMemberContractInfo(MemberContractInfo memberContractInfo) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (memberContractInfo != null) {
				if (memberContractInfo.getContractId() != null) {
					applyRelationshipDao.updateMemberContractInfo(memberContractInfo);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("请设置要修改的合同ID!!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->updateMemberContractInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> insertMemberContractInfo(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (outCompanyDto != null) {
				if (outCompanyDto.getMemberId() != null) {
					String contractCode;
					String tmpC = "10";
					String temCode = "00000000" + applyRelationshipDao.getHTCode();
					String lastFour = temCode.substring(temCode.length() - 4, temCode.length());
					String yearLast = new SimpleDateFormat("yyMMdd", Locale.CHINESE)
							.format(Calendar.getInstance().getTime());
					contractCode = tmpC + yearLast + lastFour;
					outCompanyDto.setContractCode(contractCode);
					applyRelationshipDao.insertMemberContractInfo(outCompanyDto);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("请设置入驻商家ID!!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ApplyRelationshipServiceImpl----->insertMemberContractInfo=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 生成支付账号
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifu(MemberOutsideSupplierCompanyDTO dto, int isMemberUpdate) {
		YijifuCorporateDTO payDto = new YijifuCorporateDTO();
		String mail = "";
		String organizationId = "";
		if (isMemberUpdate == 1) {
			MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getMemberId());
			MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getMemberId(),
					GlobalConstant.IS_SELLER);
			mail = baseInfo.getContactEmail();
			organizationId = licenceInfo.getOrganizationId();
		}
		payDto.setComName(dto.getCompanyName());
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setAddress(dto.getLocationDetail());
		legalPerson.setCertNo(dto.getArtificialPersonIdcard());
		legalPerson.setEmail(mail);
		legalPerson.setMobileNo(dto.getArtificialPersonMobile());
		legalPerson.setRealName(dto.getArtificialPersonName());
		payDto.setLegalPerson(legalPerson);
		if (StringUtils.isNotEmpty(dto.getBusinessLicenseId())) {
			payDto.setLicenceNo(dto.getBusinessLicenseId());
		} else {
			payDto.setLicenceNo(dto.getUnifiedSocialCreditCode());
		}
		payDto.setEmail(mail);
		payDto.setMobileNo(dto.getArtificialPersonMobile());
		payDto.setOrganizationCode(organizationId);
		payDto.setOutUserId(dto.getMemberCode());
		payDto.setTaxAuthorityNo(dto.getTaxManId());
		payDto.setVerifyRealName(GlobalConstant.REAL_NAME_NO);
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameSaveVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 修改实名信息
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModify(MemberOutsideSupplierCompanyDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		MemberLicenceInfo licenceInfo = memberBaseOperationDAO.selectMemberLicenceInfoById(dto.getMemberId());
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getMemberId(),
				GlobalConstant.IS_SELLER);
		if (!dto.getCompanyName().equals(baseInfo.getCompanyName())) {
			payDto.setComName(dto.getCompanyName());
		}
		if (!dto.getArtificialPersonMobile().equals(baseInfo.getArtificialPersonMobile())) {
			payDto.setMobileNo(dto.getArtificialPersonMobile());
		}
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		if (!dto.getBusinessLicenseId().equals(licenceInfo.getBusinessLicenseId())) {
			payDto.setLicenceNo(dto.getBusinessLicenseId());
		}
		if (!dto.getTaxManId().equals(licenceInfo.getTaxManId())) {
			payDto.setTaxAuthorityNo(dto.getTaxManId());
		}
		payDto.setUserId(baseInfo.getAccountNo());
		if (!dto.getArtificialPersonName().equals(baseInfo.getArtificialPersonName())
				|| !dto.getArtificialPersonIdcard().equals(baseInfo.getArtificialPersonIdcard())) {
			payDto.setLegalPersonCertNo(dto.getArtificialPersonIdcard());
			payDto.setLegalPersonName(dto.getArtificialPersonName());
		}
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 小b升级为大b账户
	 * 
	 * @param dto
	 */
	private YijifuCorporateCallBackDTO downToYijifuModifyUpdate(MemberOutsideSupplierCompanyDTO dto) {
		YijifuCorporateModifyDTO payDto = new YijifuCorporateModifyDTO();
		payDto.setComName(dto.getCompanyName());
		payDto.setMobileNo(dto.getArtificialPersonMobile());
		payDto.setCorporateUserType(GlobalConstant.CORPORATE_USER_TYPE_BUSINESS);
		payDto.setLicenceNo(dto.getBusinessLicenseId());
		payDto.setTaxAuthorityNo(dto.getTaxManId());
		payDto.setUserId(dto.getAccountNo());
		payDto.setLegalPersonCertNo(dto.getArtificialPersonIdcard());
		payDto.setLegalPersonName(dto.getArtificialPersonName());
		ExecuteResult<YijifuCorporateCallBackDTO> callBack = payInfoService.realNameModifyVerify(payDto);
		return callBack.getResult();
	}

	/**
	 * 解绑绑定银行卡
	 * 
	 * @param outCompanyDto
	 * @param dto
	 * @return
	 */
	private BindingBankCardCallbackDTO bindBankCard(MemberOutsideSupplierCompanyDTO dto,
			MemberOutsideSupplierCompanyDTO outCompanyDto) {

		BindingBankCardDTO bankDto = new BindingBankCardDTO();
		MemberBaseInfoDTO baseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(dto.getMemberId(),
				GlobalConstant.IS_SELLER);
		bankDto.setBankCardNo(outCompanyDto.getBankAccount());
		bankDto.setBankCode(outCompanyDto.getBankCode());
		bankDto.setBankName(outCompanyDto.getBankName());
		bankDto.setCertNo(outCompanyDto.getBusinessLicenseId());

		String city = memberBaseService.getAddressBaseByCode(outCompanyDto.getLocationCity());
		bankDto.setCity(city);
		String province = memberBaseService.getAddressBaseByCode(outCompanyDto.getBankProvince());
		bankDto.setMobile(outCompanyDto.getArtificialPersonMobile());
		bankDto.setProvince(province);
		bankDto.setPublicTag("Y");
		bankDto.setUserId(baseInfo.getAccountNo());
		bankDto.setRealName(outCompanyDto.getCompanyName());
		bankDto.setBindId(dto.getBindId());
		if ("3".equals(dto.getCardBindStatus())) {
			Boolean cardUnsign = payInfoService.cardUnsign(bankDto);
			if (cardUnsign) {
				return payInfoService.bindingBankCard(bankDto).getResult();
			}
			return null;
		} else {
			return payInfoService.bindingBankCard(bankDto).getResult();
		}
	}

	/**
	 * 新建商家绑定银行卡
	 * 
	 * @param dto
	 * @return
	 */
	private BindingBankCardCallbackDTO firstBindBankCard(MemberOutsideSupplierCompanyDTO dto) {

		BindingBankCardDTO bankDto = new BindingBankCardDTO();
		String city = memberBaseService.getAddressBaseByCode(dto.getLocationCity());
		bankDto.setCity(city);
		String province = memberBaseService.getAddressBaseByCode(dto.getBankProvince());
		bankDto.setProvince(province);
		bankDto.setBankCardNo(dto.getBankAccount());
		bankDto.setBankCode(dto.getBankCode());
		bankDto.setBankName(dto.getBankName());
		bankDto.setCertNo(dto.getBusinessLicenseId());
		bankDto.setMobile(dto.getArtificialPersonMobile());
		bankDto.setPublicTag("Y");
		bankDto.setUserId(dto.getAccountNo());
		bankDto.setRealName(dto.getCompanyName());
		return payInfoService.bindingBankCard(bankDto).getResult();
	}

	/**
	 * 保存归属关系与包厢关系
	 * 
	 * @param dto
	 * @return
	 */
	private boolean saveBelongBoxRelation(MemberOutsideSupplierCompanyDTO dto) {
		BelongRelationshipDTO belongRelationshipDto = new BelongRelationshipDTO();
		MemberBusinessRelationDTO memberBusinessRelationDTO = new MemberBusinessRelationDTO();
		belongRelationshipDto.setCurBelongManagerId(dto.getCurBelongManagerId());
		belongRelationshipDto.setCurBelongSellerId(dto.getCurBelongSellerId());
		belongRelationshipDto.setBelongSellerId(dto.getCurBelongSellerId());
		memberBusinessRelationDTO.setSellerId(dto.getCurBelongSellerId().toString());
		belongRelationshipDto.setMemberId(dto.getMemberId());
		belongRelationshipDto.setBelongManagerId(dto.getBelongManagerId());

		belongRelationshipDto.setModifyId(Long.valueOf(dto.getOperateId()));
		belongRelationshipDto.setModifyName(dto.getOperateName());
		belongRelationshipDto.setBuyerFeature("16");// 默认电商类型
		belongRelationshipDto.setVerifyStatus("3");
		belongRelationshipDAO.insertBelongInfo(belongRelationshipDto);

		ApplyBusiRelationDTO applyBusiRelationDto = new ApplyBusiRelationDTO();
		applyBusiRelationDto.setAuditStatus("1");
		applyBusiRelationDto.setMemberId(dto.getMemberId());
		applyBusiRelationDto.setSellerId(dto.getCurBelongSellerId());
		applyBusiRelationDto.setErpStatus(ErpStatusEnum.PENDING.getValue());
		applyBusiRelationDto.setCreateId(Long.valueOf(dto.getOperateId()));
		applyBusiRelationDto.setCustomerManagerId(dto.getBelongManagerId());
		applyBusiRelationDto.setCreateName(dto.getOperateName());
		applyBusiRelationDto.setModifyId(Long.valueOf(dto.getOperateId()));
		applyBusiRelationDto.setModifyName(dto.getOperateName());
		verifyInfoDAO.insertBusinessRelationInfo(applyBusiRelationDto);

		memberBusinessRelationDTO.setBuyerId(dto.getMemberId().toString());
		memberBusinessRelationDTO.setErpStatus(ErpStatusEnum.PENDING.getValue());
		memberBusinessRelationDTO.setDeleteFlag(GlobalConstant.FLAG_NO);
		memberBusinessRelationDTO.setCreateId(dto.getOperateId().toString());
		memberBusinessRelationDTO.setModifyId(dto.getOperateId().toString());
		memberBusinessRelationDTO.setCreateName(dto.getOperateName());
		memberBusinessRelationDTO.setModifyName(dto.getOperateName());
		memberBusinessRelationDAO.insertMeberBoxRelationInfo(memberBusinessRelationDTO);

		return true;
	}

	/**
	 * 
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean addDownErpStatus(MemberOutsideSupplierCompanyDTO dto) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(new Date()));
		MemberStatusInfo statusInfo = new MemberStatusInfo();
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_ERP_ADD);
		statusInfo.setVerifyStatus(ErpStatusEnum.PENDING.getValue());
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setSyncErrorMsg("");
		statusInfo.setModifyId(Long.valueOf(dto.getOperateId()));
		statusInfo.setModifyName(dto.getOperateName());
		statusInfo.setSyncKey("");
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(dto.getMemberId());
		statusInfo.setVerifyId(0L);
		statusInfo.setVerifyTime(trancDate);
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setModifyTime(trancDate);
		statusInfo.setCreateId(Long.valueOf(dto.getOperateId()));
		statusInfo.setCreateName(dto.getOperateName());
		statusInfo.setCreateTime(trancDate);
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

	/**
	 * 增加审核状态
	 * 
	 * @param myNoMemberDto
	 * @return
	 * @throws ParseException
	 */
	private boolean addNoToMemberVerifyStatus(MyNoMemberDTO dto) throws ParseException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		Date defaultDate = format.parse("0000-00-00 00:00:00");
		MemberStatusInfo statusInfo = new MemberStatusInfo();
		statusInfo.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
		statusInfo.setModifyId(dto.getModifyId());
		statusInfo.setModifyName(dto.getModifyName());
		statusInfo.setModifyTime(trancDate);
		statusInfo.setMemberId(dto.getMemberId());
		statusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_NO_TO_MEMBER);
		statusInfo.setCreateId(dto.getModifyId());
		statusInfo.setCreateName(dto.getModifyName());
		statusInfo.setCreateTime(trancDate);
		statusInfo.setSyncKey(KeygenGenerator.getUidKey());
		statusInfo.setVerifyTime(defaultDate);
		statusInfo.setVerifyId(0L);
		statusInfo.setSyncErrorMsg("");
		memberBaseOperationDAO.deleteMemberStatus(statusInfo);
		memberBaseOperationDAO.insertMemberStatus(statusInfo);
		return true;
	}

	private boolean saveCooperateStatus(MemberOutsideSupplierCompanyDTO dto) throws ParseException {
		MemberStatusInfo cooperateStatusInfo = new MemberStatusInfo();// 供应商状态审核信息
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		Date defaultDate = format.parse("0000-00-00 00:00:00");

		cooperateStatusInfo.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
		cooperateStatusInfo.setModifyId(Long.valueOf(dto.getOperateId()));
		cooperateStatusInfo.setModifyName(dto.getOperateName());
		cooperateStatusInfo.setModifyTime(trancDate);
		cooperateStatusInfo.setMemberId(dto.getMemberId());
		cooperateStatusInfo.setInfoType(GlobalConstant.INFO_TYPE_VERIFY_COOPERATE);
		cooperateStatusInfo.setCreateId(Long.valueOf(dto.getOperateId()));
		cooperateStatusInfo.setCreateName(dto.getOperateName());
		cooperateStatusInfo.setCreateTime(trancDate);
		cooperateStatusInfo.setSyncKey(KeygenGenerator.getUidKey());
		cooperateStatusInfo.setVerifyTime(defaultDate);
		cooperateStatusInfo.setVerifyId(0L);
		cooperateStatusInfo.setSyncErrorMsg("");
		memberBaseOperationDAO.deleteMemberStatus(cooperateStatusInfo);
		memberBaseOperationDAO.insertMemberStatus(cooperateStatusInfo);
		return true;
	}

	/**
	 * 保存解除归属关系审核状态
	 * 
	 * @param dto
	 * @return
	 * @throws ParseException
	 */
	private boolean saveRemoveRelationStatus(BelongRelationshipDTO dto) throws ParseException {
		MemberStatusInfo memberStatus = new MemberStatusInfo();// 供应商状态审核信息
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date trancDate = format.parse(format.format(date));
		Date defaultDate = format.parse("0000-00-00 00:00:00");

		memberStatus.setVerifyStatus(GlobalConstant.VERIFY_STATUS_WAIT);
		memberStatus.setModifyId(Long.valueOf(dto.getModifyId()));
		memberStatus.setModifyName(dto.getModifyName());
		memberStatus.setModifyTime(trancDate);
		memberStatus.setMemberId(dto.getMemberId());
		memberStatus.setInfoType(GlobalConstant.REMOVE_RELATION_VERIFY);
		memberStatus.setCreateId(Long.valueOf(dto.getModifyId()));
		memberStatus.setCreateName(dto.getModifyName());
		memberStatus.setCreateTime(trancDate);
		memberStatus.setSyncKey(KeygenGenerator.getUidKey());
		memberStatus.setVerifyTime(defaultDate);
		memberStatus.setVerifyId(dto.getVerifyId());
		memberStatus.setSyncErrorMsg("");
		memberBaseOperationDAO.deleteMemberStatus(memberStatus);
		memberBaseOperationDAO.insertMemberStatus(memberStatus);
		return true;
	}

	/**
	 * 更新会员供应商信息
	 * 
	 * @param dto
	 * @return
	 */
	private boolean updateMemberAndSupplier(MemberOutsideSupplierCompanyDTO dto) {
		applyRelationshipDao.updateMemberLicenceInfo(dto);
		return true;
	}

	/**
	 * 插入会员供应商信息
	 * 
	 * @param dto
	 * @return
	 */
	private boolean insertMemberAndSupplier(MemberOutsideSupplierCompanyDTO dto) {
		applyRelationshipDao.insertMemberLicenceInfo(dto);
		// applyRelationshipDao.insertMemberBankInfo(dto);
		return true;
	}

	/**
	 * 保存修改履历
	 * 
	 * @param oldOutCompanyDto
	 * @param outCompanyDto
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 */
	private boolean saveVerifyDetail(MemberOutsideSupplierCompanyDTO oldOutCompanyDto,
			MemberOutsideSupplierCompanyDTO outCompanyDto) {
		List<VerifyDetailInfo> verDtoList = new ArrayList<VerifyDetailInfo>();
		try {
			VerifyDetailInfo verDto = null;
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = format.parse(format.format(date));
			// 通过反射注解方式把修改参数映射到DTO对象
			Class<? extends MemberOutsideSupplierCompanyDTO> oldClazz = oldOutCompanyDto.getClass();
			Class<? extends MemberOutsideSupplierCompanyDTO> clazz = outCompanyDto.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				VerifyDetail verifyDetail = field.getAnnotation(VerifyDetail.class);
				if (null != verifyDetail) {
					String fieldName = field.getName();
					String getMothodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method fieldGet = clazz.getMethod(getMothodName);
					Method oldFieldGet = oldClazz.getMethod(getMothodName);
					String getValue = "";
					String oldGetValue = "";
					if (null != fieldGet.invoke(outCompanyDto)) {
						getValue = fieldGet.invoke(outCompanyDto).toString();
					}
					if (null != oldFieldGet.invoke(oldOutCompanyDto)) {
						oldGetValue = oldFieldGet.invoke(oldOutCompanyDto).toString();
					}
					if (!oldGetValue.equals(getValue)) {
						verDto = new VerifyDetailInfo();
						String beforDesc = oldGetValue;
						String afterDesc = getValue;
						String type = verifyDetail.type();
						if ("DICTIONARIES".equals(type)) {
							String key[] = verifyDetail.key();
							String value[] = verifyDetail.value();
							int l = key.length;
							for (int i = 0; i < l; i++) {
								if (getValue.equals(key[i])) {
									afterDesc = value[i];
								}
								if (oldGetValue.equals(key[i])) {
									beforDesc = value[i];
								}
							}
						}
						verDto.setBeforeChange(oldGetValue);
						verDto.setBeforeChangeDesc(beforDesc);
						verDto.setAfterChange(getValue);
						verDto.setAfterChangeDesc(afterDesc);
						verDto.setChangeFieldId(verifyDetail.fieldId());
						verDto.setChangeTableId(verifyDetail.tableId());
						verDto.setContentName(verifyDetail.contentName());
						verDto.setModifyType(GlobalConstant.INFO_TYPE_OUTER_COMPANY_MODIFY);
						verDto.setOperateTime(trancDate);
						verDto.setOperatorId(Long.valueOf(outCompanyDto.getOperateId()));
						verDto.setOperatorName(outCompanyDto.getOperateName());
						verDto.setVerifyId(GlobalConstant.NULL_DEFAUL_VALUE);
						verDto.setRecordId(outCompanyDto.getMemberId());
						verDto.setRecordType(GlobalConstant.RECORD_TYPE_MEMBER_ID);
						verDtoList.add(verDto);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (verDtoList.size() > 0) {
			memberBaseOperationDAO.insertVerifyInfo(verDtoList);// 插入履历信息
		}
		return true;

	}

	/**
	 * 设置二代供应商归属0801的相关信息
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	private boolean setBelongSellerAndManage(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		List<MemberBaseInfoDTO> memberbase = memberBaseInfoService
				.getMemberInfoByCompanyCode(GlobalConstant.DEFAULT_MEMBER_COOPERATE, GlobalConstant.IS_SELLER)
				.getResult();

		if ("1".equals(outCompanyDto.getSellerType())) {// 外部供应商
			if (memberbase.size() > 0) {
				Long sellerId = memberbase.get(0).getId();
				outCompanyDto.setCurBelongSellerId(sellerId);
				outCompanyDto.setBelongSellerId(sellerId);
			} else {
				outCompanyDto.setCurBelongSellerId(0L);
				outCompanyDto.setBelongSellerId(0L);
			}
			outCompanyDto.setBelongManagerId("08019999");
			outCompanyDto.setCurBelongManagerId("08019999");
		} else {
			if (memberbase.size() > 0) {
				Long sellerId = memberbase.get(0).getId();
				if (outCompanyDto.getBelongSellerId().equals(sellerId)) {// 二代归属0801
					outCompanyDto.setBelongManagerId("08019999");
					outCompanyDto.setCurBelongManagerId("08019999");

				} else {// 设置客户经理CODE
					MemberBaseInfoDTO sellerInfo = memberBaseInfoService
							.getMemberDetailBySellerId(outCompanyDto.getBelongSellerId()).getResult()
							.getMemberBaseInfoDTO();
					String manageCode = getManageDefaultCodeBySellerCode(sellerInfo.getMemberCode());
					outCompanyDto.setBelongManagerId(manageCode);
					outCompanyDto.setCurBelongManagerId(manageCode);
				}
			}

		}
		return true;
	}

	/**
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	private boolean inserMemberAttachInfo(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		String contractCode;
		String tmpC = "10";
		String temCode = "00000000" + applyRelationshipDao.getHTCode();
		String lastFour = temCode.substring(temCode.length() - 4, temCode.length());
		String yearLast = new SimpleDateFormat("yyMMdd", Locale.CHINESE).format(Calendar.getInstance().getTime());
		contractCode = tmpC + yearLast + lastFour;
		outCompanyDto.setContractCode(contractCode);

		applyRelationshipDao.insertMemberExtendInfo(outCompanyDto);
		applyRelationshipDao.insertMemberContractInfo(outCompanyDto);

		MemberOutsideSupplierCompanyDTO memberBankInfo = applyRelationshipDao
				.selectMemberBankInfo(outCompanyDto.getMemberId());
		if (null == memberBankInfo) {
			applyRelationshipDao.insertMemberBankInfo(outCompanyDto);
		} else {
			applyRelationshipDao.updateMemberBankInfo(outCompanyDto);
		}
		return true;
	}

	private String getManageDefaultCodeBySellerCode(String sellerCode) {
		String manageCode = "";
		// 获取归属客户经理
		List<SalemanDTO> salesMans = memberBaseInfoService.getManagerList(sellerCode).getResult();
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
		return manageCode;
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
	public ExecuteResult<Boolean> checkOutCompanyRegister(MemberOutsideSupplierCompanyDTO outCompanyDto) {
		logger.info("applyRelationshipService---------checkOutCompanyRegister服务开始执行，参数："
				+ JSONObject.toJSONString(outCompanyDto));
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Long count = applyRelationshipDao.selectOutCompanyCheckCount(outCompanyDto);
			if (count > 0) {
				rs.setResult(false);
				rs.setResultMessage("fail");
			} else {
				rs.setResult(true);
				rs.setResultMessage("success");
			}
			logger.info("applyRelationshipService---------checkOutCompanyRegister服务执行成功，参数："
					+ JSONObject.toJSONString(outCompanyDto) + ",结果为：" + JSONObject.toJSONString(rs));
		} catch (Exception e) {
			rs.setResult(false);
			rs.setResultMessage("fail");
			rs.addErrorMessage("校验异常");
			logger.info("applyRelationshipService---------checkOutCompanyRegister服务执行异常，参数："
					+ JSONObject.toJSONString(outCompanyDto));
		}
		return rs;
	}

}

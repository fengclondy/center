package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberBaseInfoDao;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dao.MemberLicenceInfoDao;
import cn.htd.membercenter.dao.MemberStatusDao;
import cn.htd.membercenter.domain.MemberBaseInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoVO;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberUncheckedDTO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberStatusService;
import cn.htd.membercenter.util.MemberUtil;
import cn.htd.usercenter.service.CustomerService;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberStatusServiceImpl
 * </p>
 * 
 * @author root
 * @date 2016年12月12日
 *       <p>
 *       Description: 非会员转会员 、密码找回、手机号 查询列表/查询详细信息相关功能接口
 *       </p>
 */
@Service("memberStatusService")
@SuppressWarnings("all")
public class MemberStatusServiceImpl implements MemberStatusService {
	private final static Logger logger = LoggerFactory.getLogger(MemberStatusServiceImpl.class);
	@Resource
	private CustomerService customerService;
	@Resource
	private MemberStatusDao memberStatusDao;
	@Resource
	private MemberBaseInfoDao memberBaseInfoDao;
	@Resource
	private BaseAddressService baseAddressService;
	@Resource
	private MemberLicenceInfoDao memberLicenceInfoDao;
	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;
	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	/**
	 * 非会员转会员查询列表
	 */
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryNonMemberToMemberList(Pager page, MemberUncheckedDTO dto) {
		logger.debug("--------------------非会员转会员查询---------------------");
		ExecuteResult<DataGrid<MemberUncheckedDTO>> rs = new ExecuteResult<DataGrid<MemberUncheckedDTO>>();
		DataGrid<MemberUncheckedDTO> dg = new DataGrid<MemberUncheckedDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getMember_type())) {// GUARANTEE_MEMBER("3",
																// "担保会员"),
																// NONE_MEMBER("1",
																// "非会员"),
																// REAL_MEMBER("2",
																// "正式会员");
				if ("3".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(0);
					dto.setHas_guarantee_license(1);
				} else if ("1".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(0);
				} else if ("2".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(1);
				}
				int count = memberStatusDao.queryNonMemberToMemberListCount(dto);

				List<MemberUncheckedDTO> memberUncheckedDTOs = memberStatusDao.queryNonMemberToMemberList(page, dto);
				for (MemberUncheckedDTO m : memberUncheckedDTOs) {// 判断会员类型
					if (null != m.getCan_mall_login() && null != m.getHas_guarantee_license()
							&& null != m.getHas_business_license()) {
						String memberType = MemberUtil.judgeMemberType(String.valueOf(m.getCan_mall_login()),
								String.valueOf(m.getHas_guarantee_license()),
								String.valueOf(m.getHas_business_license()));
						m.setMember_type(memberType);
					}
				}
				dg.setRows(memberUncheckedDTOs);
				dg.setTotal(new Long(count));
				rs.setResult(dg);
				rs.setResultMessage("success");
			} else {
				rs.addErrorMessage("缺失会员类型参数 member_type");
				rs.setResultMessage("error");
			}
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryNonMemberToMemberList】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 密码找回查询列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryPasswordRecoveryVerifyList(Pager page,
			MemberUncheckedDTO dto) {
		logger.debug("--------------------密码找回查询---------------------");
		ExecuteResult<DataGrid<MemberUncheckedDTO>> rs = new ExecuteResult<DataGrid<MemberUncheckedDTO>>();
		DataGrid<MemberUncheckedDTO> dg = new DataGrid<MemberUncheckedDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getMember_type())) {// GUARANTEE_MEMBER("3",
																// "担保会员"),
																// NONE_MEMBER("1",
																// "非会员"),
																// REAL_MEMBER("2",
																// "正式会员");
				if ("3".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(0);
					dto.setHas_guarantee_license(1);
				} else if ("1".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(0);
				} else if ("2".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(1);
				}
			}

			int count = memberStatusDao.queryPasswordRecoveryVerifyListCount(dto);

			List<MemberUncheckedDTO> memberUncheckedDTOs = memberStatusDao.queryPasswordRecoveryVerifyList(page, dto);
			for (MemberUncheckedDTO m : memberUncheckedDTOs) {// 判断会员类型
				if (null != m.getCan_mall_login() && null != m.getHas_guarantee_license()
						&& null != m.getHas_business_license()) {
					String memberType = MemberUtil.judgeMemberType(String.valueOf(m.getCan_mall_login()),
							String.valueOf(m.getHas_guarantee_license()), String.valueOf(m.getHas_business_license()));
					m.setMember_type(memberType);
				}
			}
			dg.setRows(memberUncheckedDTOs);
			dg.setTotal(new Long(count));
			rs.setResult(dg);
			rs.setResultMessage("success");

		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryPasswordRecoveryVerifyList】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 手机号更改查询列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryPhoneChangeVerifyList(Pager page, MemberUncheckedDTO dto) {
		logger.debug("--------------------手机号更改查询---------------------");
		ExecuteResult<DataGrid<MemberUncheckedDTO>> rs = new ExecuteResult<DataGrid<MemberUncheckedDTO>>();
		DataGrid<MemberUncheckedDTO> dg = new DataGrid<MemberUncheckedDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getMember_type())) {// GUARANTEE_MEMBER("3",
																// "担保会员"),
																// NONE_MEMBER("1",
																// "非会员"),
																// REAL_MEMBER("2",
																// "正式会员");
				if ("3".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(0);
					dto.setHas_guarantee_license(1);
				} else if ("1".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(0);
				} else if ("2".equalsIgnoreCase(dto.getMember_type())) {
					dto.setCan_mall_login(1);
					dto.setHas_business_license(1);
				}
			}
			int count = memberStatusDao.queryPhoneChangeVerifyListCount(dto);

			List<MemberUncheckedDTO> memberUncheckedDTOs = memberStatusDao.queryPhoneChangeVerifyList(page, dto);
			for (MemberUncheckedDTO m : memberUncheckedDTOs) {// 判断会员类型
				if (null != m.getCan_mall_login() && null != m.getHas_guarantee_license()
						&& null != m.getHas_business_license()) {
					String memberType = MemberUtil.judgeMemberType(String.valueOf(m.getCan_mall_login()),
							String.valueOf(m.getHas_guarantee_license()), String.valueOf(m.getHas_business_license()));
					m.setMember_type(memberType);
				}
			}
			dg.setRows(memberUncheckedDTOs);
			dg.setTotal(new Long(count));
			rs.setResult(dg);
			rs.setResultMessage("success");

		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryPhoneChangeVerifyList】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 非会员转会员待审核详细信息查询接口
	 */
	@Override
	public ExecuteResult<MemberUncheckedDetailDTO> queryNonMemberToMemberDetail(Long memberId) {
		logger.debug("--------------------非会员转会员详细信息查询---------------------");
		ExecuteResult<MemberUncheckedDetailDTO> rs = new ExecuteResult<MemberUncheckedDetailDTO>();
		try {
			MemberUncheckedDetailDTO memberUncheckedDetailDTO = memberStatusDao.queryNonMemberToMemberDetail(memberId);
			memberUncheckedDetailDTO.setBelong_seller_name(memberStatusDao.getBelongCompanyName(memberId));
			memberUncheckedDetailDTO.setCur_belong_seller_name(memberStatusDao.getOriginalBelongCompanyName(memberId));
			memberUncheckedDetailDTO.setBelong_manager_name(
					memberBaseInfoService.getManagerName(memberUncheckedDetailDTO.getBelong_seller_id().toString(),
							memberUncheckedDetailDTO.getBelong_manager_id()));
			// customerService.getCustomerInfo(memberUncheckedDetailDTO.getBelong_manager_id()).getName());
			memberUncheckedDetailDTO.setCur_belong_manager_name(
					memberBaseInfoService.getManagerName(memberUncheckedDetailDTO.getCur_belong_seller_id().toString(),
							memberUncheckedDetailDTO.getCur_belong_manager_id()));
			// customerService.getCustomerInfo(memberUncheckedDetailDTO.getCur_belong_manager_id()).getName());
			rs.setResult(memberUncheckedDetailDTO);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryNonMemberToMemberDetail】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 密码找回待审核详细信息查询接口
	 */
	@Override
	public ExecuteResult<MemberLicenceInfoDetailDTO> queryPasswordRecoveryVerifyDetail(Long memberId, String infoType) {
		logger.debug("--------------------密码找回详细信息查询---------------------");
		ExecuteResult<MemberLicenceInfoDetailDTO> rs = new ExecuteResult<MemberLicenceInfoDetailDTO>();
		try {
			MemberLicenceInfoDetailDTO memberUncheckedDetailDTO = memberStatusDao
					.queryPasswordRecoveryVerifyDetail(memberId, infoType);
			MemberBaseInfoVO memberBaseInfoVO = new MemberBaseInfoVO();
			memberBaseInfoVO.setId(memberId);
			MemberBaseInfoDTO memberBaseInfo = memberBaseOperationDAO.getMemberbaseBySellerId(memberId,
					GlobalConstant.IS_BUYER);
			// 会员类型
			memberUncheckedDetailDTO.setMember_type(memberBaseInfo.getMemberType());
			memberUncheckedDetailDTO.setBuyer_guarantee_license_pic_src_new("");
			memberUncheckedDetailDTO.setCompany_name_new("");
			memberUncheckedDetailDTO.setArtificial_person_mobile_new("");
			memberUncheckedDetailDTO.setArtificial_person_name_new("");
			memberUncheckedDetailDTO.setArtificial_person_idcard_pic_src_new("");
			memberUncheckedDetailDTO.setArtificial_person_pic_back_src_new("");
			memberUncheckedDetailDTO.setArtificial_person_pic_src_new("");
			memberUncheckedDetailDTO.setBuyer_business_license_pic_src_new("");

			List<VerifyDetailInfo> verifyDetailInfos = memberLicenceInfoDao
					.queryVerifyDetailInfoByVerifyId(new Long(memberUncheckedDetailDTO.getVerify_id()));
			if (verifyDetailInfos != null && verifyDetailInfos.size() > 0) {
				for (VerifyDetailInfo m : verifyDetailInfos) {
					if (m.getChangeTableId().equalsIgnoreCase("member_licence_info")) {
						// 新担保证明
						if (m.getChangeFieldId().equalsIgnoreCase("buyer_guarantee_license_pic_src")) {
							memberUncheckedDetailDTO.setBuyer_guarantee_license_pic_src_new(m.getAfterChange());
						}
						// 新营业执照
						if (m.getChangeFieldId().equalsIgnoreCase("buyer_business_license_pic_src")) {
							memberUncheckedDetailDTO.setBuyer_business_license_pic_src_new(m.getAfterChange());
						}
						// 变更证明
						if ("business_license_certificate_pic_src".equalsIgnoreCase(m.getChangeFieldId())) {
							memberUncheckedDetailDTO.setBusiness_license_certificate_pic_src(m.getAfterChange());
						}
					}
					if (m.getChangeTableId().equalsIgnoreCase("member_company_info")) {
						// 原公司名称
						if (m.getChangeFieldId().equalsIgnoreCase("company_name")) {
							memberUncheckedDetailDTO.setCompany_name(m.getBeforeChange());
						}
						// 新法人名称
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_name")) {
							memberUncheckedDetailDTO.setArtificial_person_name_new(m.getAfterChange());
						}
						// 新手机号
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_mobile")) {
							memberUncheckedDetailDTO.setArtificial_person_mobile_new(m.getAfterChange());
						}

						// 身份证正面
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_pic_src")) {
							memberUncheckedDetailDTO.setArtificial_person_pic_src_new(m.getAfterChange());
						}
						// 身份证反面
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_pic_back_src")) {
							memberUncheckedDetailDTO.setArtificial_person_pic_back_src_new(m.getAfterChange());
						}
						// 身份证手持
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_idcard_pic_src")) {
							memberUncheckedDetailDTO.setArtificial_person_idcard_pic_src_new(m.getAfterChange());
						}
					}
				}
			}
			rs.setResult(memberUncheckedDetailDTO);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryPasswordRecoveryVerifyDetail】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 手机号更改审核详细信息
	 */
	@Override
	public ExecuteResult<MemberLicenceInfoDetailDTO> queryPhoneChangeVerifyDetail(Long memberId) {
		logger.debug("--------------------手机号更改详细信息查询---------------------");
		ExecuteResult<MemberLicenceInfoDetailDTO> rs = new ExecuteResult<MemberLicenceInfoDetailDTO>();
		try {
			MemberLicenceInfoDetailDTO licenceInfoDetailDTO = memberStatusDao.queryPhoneChangeVerifyDetail(memberId);
			MemberBaseInfoVO memberBaseInfoVO = new MemberBaseInfoVO();
			memberBaseInfoVO.setId(memberId);
			MemberBaseInfo memberBaseInfo = memberBaseInfoDao.searchMemberBaseInfoById(memberBaseInfoVO);
			// 会员类型
			String memberType = MemberUtil.judgeMemberType(String.valueOf(memberBaseInfo.getCan_mall_login()),
					String.valueOf(memberBaseInfo.getHas_guarantee_license()),
					String.valueOf(memberBaseInfo.getHas_business_license()));
			licenceInfoDetailDTO.setMember_type(memberType);
			List<VerifyDetailInfo> verifyDetailInfos = memberLicenceInfoDao
					.queryVerifyDetailInfoByVerifyId(new Long(licenceInfoDetailDTO.getVerify_id()));
			if (verifyDetailInfos != null && verifyDetailInfos.size() > 0) {
				for (VerifyDetailInfo m : verifyDetailInfos) {
					if (m.getChangeTableId().equalsIgnoreCase("MEMBER_COMPANY_INFO")) {
						// 新手机号
						if (m.getChangeFieldId().equalsIgnoreCase("artificial_person_mobile")) {
							licenceInfoDetailDTO.setArtificial_person_mobile_new(m.getAfterChange());
							licenceInfoDetailDTO.setArtificial_person_mobile(m.getBeforeChange());
						}

					}

					if (m.getChangeTableId().equalsIgnoreCase("MEMBER_LICENCE_INFO")) {
						// 担保证明
						if (m.getChangeFieldId().equalsIgnoreCase("buyer_guarantee_license_pic_src")) {
							licenceInfoDetailDTO.setBuyer_guarantee_license_pic_src_new(m.getAfterChange());
						}

					}

				}
			}
			rs.setResult(licenceInfoDetailDTO);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryPhoneChangeVerifyDetail】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询外部供应商信息修改履历
	 */
	@Override
	public ExecuteResult<DataGrid<VerifyDetailInfo>> getVerifyById(Pager page, Long id) {
		ExecuteResult<DataGrid<VerifyDetailInfo>> rs = new ExecuteResult<DataGrid<VerifyDetailInfo>>();
		DataGrid<VerifyDetailInfo> dg = new DataGrid<VerifyDetailInfo>();
		try {
			// 查询总数
			Long count = memberStatusDao.getVerifyByIdCount(id, "26", "1");
			// if (count > 0) {
			// recordType 1会员 2商家
			List<VerifyDetailInfo> verifyById = memberStatusDao.getVerifyById(id, "26", "1", page);
			dg.setRows(verifyById);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");
			/*
			 * } else { rs.setResultMessage("要查询的数据不存在"); }
			 */
			// rs.setResultMessage("success");
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("查询异常");
			logger.error("MemberBaseInfoServiceImpl----->getVerifyById=" + e);
		}
		return rs;
	}

	/**
	 * 查询会员解除归属关系待审核列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberRemoveRelationshipDTO>> queryRemoveRelationship(Pager page,
			MemberRemoveRelationshipDTO dto) {
		ExecuteResult<DataGrid<MemberRemoveRelationshipDTO>> rs = new ExecuteResult<DataGrid<MemberRemoveRelationshipDTO>>();
		DataGrid<MemberRemoveRelationshipDTO> dg = new DataGrid<MemberRemoveRelationshipDTO>();
		try {
			Long count = memberStatusDao.queryRemoveRelationshipCount(dto);
			/*
			 * if (count > 0) { List<MemberRemoveRelationshipDTO>
			 * removeRelationshipList =
			 * memberStatusDao.queryRemoveRelationship(page, dto);
			 * dg.setRows(removeRelationshipList); page.setPageOffset(0);
			 * page.setRows(Integer.MAX_VALUE); dg.setTotal(count);
			 * rs.setResult(dg); rs.setResultMessage("success"); } else {
			 * rs.setResultMessage("fail"); rs.addErrorMessage("查询数据为空"); }
			 */
			List<MemberRemoveRelationshipDTO> removeRelationshipList = memberStatusDao.queryRemoveRelationship(page,
					dto);
			dg.setRows(removeRelationshipList);
			dg.setTotal(count);
			rs.setResult(dg);
			rs.setResultMessage("success");

		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryRemoveRelationship】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询会员解除归属关系待审核详情
	 */
	@Override
	public ExecuteResult<MemberRemoveRelationshipDTO> queryRemoveRelationshipDetail(String status, Long memberId) {
		ExecuteResult<MemberRemoveRelationshipDTO> rs = new ExecuteResult<MemberRemoveRelationshipDTO>();
		try {
			MemberRemoveRelationshipDTO relationshipDetail = memberStatusDao.queryRemoveRelationshipDetail(status,
					memberId);
			rs.setResult(relationshipDetail);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryRemoveRelationshipDetail】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询外部商家列表
	 */
	@Override
	public ExecuteResult<DataGrid<MemberOutsideSupplierDTO>> queryOutsideSupplier(Pager page,
			MemberOutsideSupplierDTO dto) {
		ExecuteResult<DataGrid<MemberOutsideSupplierDTO>> rs = new ExecuteResult<DataGrid<MemberOutsideSupplierDTO>>();
		DataGrid<MemberOutsideSupplierDTO> dg = new DataGrid<MemberOutsideSupplierDTO>();
		try {
			List<MemberOutsideSupplierDTO> queryOutsideSupplier = memberStatusDao.queryOutsideSupplier(page, dto);
			long count = memberStatusDao.queryOutsideSupplierCount(dto);
			if (queryOutsideSupplier.size() > 0) {
				for (MemberOutsideSupplierDTO m : queryOutsideSupplier) {
					m.setLocationAllAddress(getAddressBaseByCode(m.getLocationCounty()));
				}
			}
			dg.setTotal(count);
			dg.setRows(queryOutsideSupplier);
			rs.setResult(dg);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryOutsideSupplier】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	/**
	 * 查询外部商家公司信息
	 */
	@Override
	public ExecuteResult<MemberOutsideSupplierCompanyDTO> queryOutsideSupplierCompany(Long memberId) {
		ExecuteResult<MemberOutsideSupplierCompanyDTO> rs = new ExecuteResult<MemberOutsideSupplierCompanyDTO>();
		try {
			MemberOutsideSupplierCompanyDTO outsideSupplierCompany = memberStatusDao
					.queryOutsideSupplierCompany(memberId);
			outsideSupplierCompany.setBelongCompanyName(memberStatusDao.getBelongCompanyName(memberId));
			outsideSupplierCompany
					.setLocationAllAddress(getAddressBaseByCode(outsideSupplierCompany.getLocationCounty()));
			outsideSupplierCompany.setBankAllAddress(getAddressBaseByCode(outsideSupplierCompany.getBankCounty()));
			outsideSupplierCompany.setContractList(memberStatusDao.queryContractList(memberId));
			/*
			 * outsideSupplierCompany.setBankProvince(baseAddressService.
			 * getAddressName(outsideSupplierCompany.getBankProvince()));
			 * outsideSupplierCompany.setBankCity(baseAddressService.
			 * getAddressName(outsideSupplierCompany.getBankCity()));
			 * outsideSupplierCompany.setBankCounty(baseAddressService.
			 * getAddressName(outsideSupplierCompany.getBankCounty()));
			 */
			rs.setResult(outsideSupplierCompany);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryOutsideSupplierCompany】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}

	public String getAddressBaseByCode(String code) {
		ExecuteResult<BaseAddressDTO> baseAddress = baseAddressService.queryBaseAddressByCode(code);
		String addressStr = "";
		if (baseAddress.getResult() != null) {
			List<BaseAddressDTO> addressList = baseAddress.getResult().getParentBaseAddressDTO();
			if (CollectionUtils.isNotEmpty(addressList)) {
				for (BaseAddressDTO address : addressList) {
					addressStr += address.getName();
				}
			}
			addressStr += baseAddress.getResult().getName();
		}
		return addressStr;
	}

	/**
	 * 查询外部商家公司信息
	 */
	@Override
	public ExecuteResult<MemberOutsideSupplierCompanyDTO> queryOutsideSupplierCompanyByCode(String memberCode) {
		ExecuteResult<MemberOutsideSupplierCompanyDTO> rs = new ExecuteResult<MemberOutsideSupplierCompanyDTO>();
		try {

			Long memberId = memberBaseInfoService.getMemberIdByCode(memberCode).getResult();
			MemberOutsideSupplierCompanyDTO outsideSupplierCompany = memberStatusDao
					.queryOutsideSupplierCompany(memberId);
			outsideSupplierCompany.setBelongCompanyName(memberStatusDao.getBelongCompanyName(memberId));
			outsideSupplierCompany
					.setLocationAllAddress(getAddressBaseByCode(outsideSupplierCompany.getLocationCounty()));
			outsideSupplierCompany.setBankAllAddress(getAddressBaseByCode(outsideSupplierCompany.getBankCounty()));
			outsideSupplierCompany.setContractList(memberStatusDao.queryContractList(memberId));
			rs.setResult(outsideSupplierCompany);
			rs.setResultMessage("success");
		} catch (Exception e) {
			logger.error("执行方法【MemberStatusServiceImpl -  queryOutsideSupplierCompanyByCode】报错！{}", e);
			rs.addErrorMessage("系统异常，请联系系统管理员！");
			rs.setResultMessage("error");
		}
		return rs;
	}
}

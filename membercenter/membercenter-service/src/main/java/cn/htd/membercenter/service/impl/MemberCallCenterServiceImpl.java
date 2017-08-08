package cn.htd.membercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.dao.MemberCallCenterDAO;
import cn.htd.membercenter.dao.MemberCompanyInfoDao;
import cn.htd.membercenter.dto.GradeMemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberCallCenterDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.membercenter.service.MemberCallCenterService;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.service.EmployeeService;

@Service("memberCallCenterService")
public class MemberCallCenterServiceImpl implements MemberCallCenterService {
	private final static Logger logger = LoggerFactory.getLogger(MemberCallCenterServiceImpl.class);

	@Resource
	private MemberCallCenterDAO memberCallCenterDAO;
	@Autowired
	private MemberBaseService memberBaseService;
	@Autowired
	private EmployeeService employeeService;
	@Resource
	private MemberCompanyInfoDao memberCompanyInfoDao;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Override
	public ExecuteResult<MemberBaseInfoDTO> selectMemberBaseName(String memberCode, String buyerSellerType) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->selectMemberBaseName");
		ExecuteResult<MemberBaseInfoDTO> rs = new ExecuteResult<MemberBaseInfoDTO>();
		try {
			MemberBaseInfoDTO memberBaseInfoDTO = memberCallCenterDAO.selectMemberBaseName(memberCode, buyerSellerType);
			if (memberBaseInfoDTO != null) {
				String canMallLogin = memberBaseInfoDTO.getCanMallLogin().toString();
				String hasGuaranteeLicense = memberBaseInfoDTO.getHasGuaranteeLicense().toString();
				String hasBusinessLicense = memberBaseInfoDTO.getHasBusinessLicense().toString();
				String memberType = memberBaseService.judgeMemberType(canMallLogin, hasGuaranteeLicense,
						hasBusinessLicense);
				memberBaseInfoDTO.setMemberType(memberType);
				rs.setResult(memberBaseInfoDTO);
				rs.setResultMessage("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.setResultMessage(
						"根据会员编码memberCode:‘" + memberCode + "’和会员/商家类型buyerSellerType:‘" + buyerSellerType + "’没查到数据");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}
		} catch (Exception e) {
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMemberBaseName=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberGroupDTO> selectBuyCodeSellCode(String sellerCode, String buyerCode) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->selectBuyCodeSellCode");
		ExecuteResult<MemberGroupDTO> rs = new ExecuteResult<MemberGroupDTO>();
		try {
			MemberGroupDTO memberGroupDTO = memberCallCenterDAO.selectBuyCodeSellCode(sellerCode, buyerCode);
			MemberGradeDTO memberGradeDTO = memberCallCenterDAO.queryMemberGradeInfo(buyerCode);
			String grade = "";
			Long buyerId = null;
			if (memberGradeDTO != null) {
				grade = memberGradeDTO.getBuyerGrade();
				buyerId = Long.valueOf(memberGradeDTO.getMemberId());
			} else {
				rs.addErrorMessage("没查到等级数据");
				rs.setResultMessage("根据买家buyerCode:‘" + buyerCode + "’没查到等级数据");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				return rs;
			}
			if (memberGroupDTO != null) {
				memberGroupDTO.setBuyerGrade(grade);
				rs.setResult(memberGroupDTO);
				rs.setResultMessage("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				memberGroupDTO = new MemberGroupDTO();
				memberGroupDTO.setBuyerGrade(grade);
				memberGroupDTO.setBuyerId(buyerId);
				rs.setResult(memberGroupDTO);
				rs.setResultMessage("根据卖家sellerCode:‘" + sellerCode + "’和买家buyerCode:‘" + buyerCode + "’没查到分组数据");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectBuyIdSellId=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberConsigAddressDTO>> selectConsigAddressList(String memberCode) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->selectConsigAddressList");
		ExecuteResult<List<MemberConsigAddressDTO>> rs = new ExecuteResult<List<MemberConsigAddressDTO>>();
		try {
			List<MemberConsigAddressDTO> memberConsigAddressDtoList = null;
			memberConsigAddressDtoList = memberCallCenterDAO.selectConsigAddressList(memberCode);
			if (memberConsigAddressDtoList != null) {
				/*
				 * for (int i = 0; i < memberConsigAddressDtoList.size(); i++) {
				 * MemberConsigAddressDTO memberConsigAddress = new
				 * MemberConsigAddressDTO(); memberConsigAddress =
				 * memberConsigAddressDtoList.get(i); if
				 * (memberConsigAddress.getConsigneeAddressTown() != null) {
				 * String consigAddress = memberBaseService
				 * .getAddressBaseByCode(memberConsigAddress.
				 * getConsigneeAddressTown()) +
				 * memberConsigAddress.getConsigneeAddressDetail();
				 * memberConsigAddressDtoList.get(i).setConsigneeAddress(
				 * consigAddress); } }
				 */
				rs.setResult(memberConsigAddressDtoList);
				rs.setResultMessage("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.setResultMessage("要查询的数据不存在!!");
			}

		} catch (Exception e) {
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("ConsigneeAddressServiceImpl----->selectConsigAddressList=" + e);
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->queryMemberInvoiceInfo");
		ExecuteResult<MemberInvoiceDTO> rs = new ExecuteResult<MemberInvoiceDTO>();
		MemberInvoiceDTO member = null;
		member = memberCallCenterDAO.queryMemberInvoiceInfo(memberCode);
		try {
			if (member != null) {
				rs.setResult(member);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}
		} catch (Exception e) {
			rs.setResultMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberInoviceServiceImpl----->queryMemberInvoiceInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberGradeDTO> queryMemberGradeInfo(String memberCode) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->queryMemberGradeInfo");
		ExecuteResult<MemberGradeDTO> rs = new ExecuteResult<MemberGradeDTO>();
		try {
			MemberGradeDTO member = memberCallCenterDAO.queryMemberGradeInfo(memberCode);
			if (member != null) {
				String canMallLogin = member.getCanMallLogin();
				String hasGuaranteeLicense = member.getHasGuaranteeLicense();
				String hasBusinessLicense = member.getHasBusinessLicense();
				// 设置会员等级类型
				member.setMemberType(
						memberBaseService.judgeMemberType(canMallLogin, hasGuaranteeLicense, hasBusinessLicense));
				rs.setResult(member);
				rs.setCode("00000");
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
				rs.setCode("13999");
			}
		} catch (Exception e) {
			rs.setResultMessage("error");
			rs.setCode("99999");
			logger.error("MemberGradeServiceImpl----->queryMemberBaseInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberCallCenterDTO> selectMemberCallCenterInfo(String memberCode, String companyName,
			String artificialPersonMobile) {
		logger.info("查询接口方法：MemberCallCenterServiceImpl----->selectMemberCallCenterInfo");
		ExecuteResult<MemberCallCenterDTO> rs = new ExecuteResult<MemberCallCenterDTO>();
		try {
			MemberCallCenterDTO memberCallCenter = new MemberCallCenterDTO();
			memberCallCenter = memberCallCenterDAO.selectMemberCallCenterInfo(memberCode, companyName,
					artificialPersonMobile);
			if (memberCallCenter != null) {
				String isSeller = memberCallCenter.getAccountType();
				if ("1".equals(isSeller)) {
					memberCallCenter.setAccountType("供应商");
				} else if ("0".equals(isSeller)) {
					memberCallCenter.setAccountType("会员店");
				}
				Long curBelongSellerId = memberCallCenter.getCurBelongSellerId();
				String curBelongManagerId = memberCallCenter.getCurBelongManagerId();
				// 查看平台公司名称和客户经理名称
				MemberCompanyInfoDTO memberCompanyInfoDTO = new MemberCompanyInfoDTO();
				memberCompanyInfoDTO.setMemberId(curBelongSellerId);
				memberCompanyInfoDTO.setBuyerSellerType(2);
				// 查询平台公司名称
				List<MemberCompanyInfoDTO> memberCompanyInfoDTOList = memberCompanyInfoDao
						.searchMemberCompanyInfo(memberCompanyInfoDTO);
				int size = memberCompanyInfoDTOList.size();
				if (memberCompanyInfoDTOList != null && size > 0) {
					if (size == 1) {
						memberCallCenter.setCurBelongSellerName(memberCompanyInfoDTO.getCompanyName());
					} else {
						rs.addErrorMessage("查询条件结果不唯一");
					}
				}
				// 查看客户经理名称
				// EmployeeDTO curBelongManager =
				// employeeService.getEmployeeInfo(curBelongManagerId);
				//
				// if (curBelongManager != null) {
				memberCallCenter.setCurBelongManagerName(
						memberBaseInfoService.getManagerName(curBelongSellerId.toString(), curBelongManagerId));
				// }
				// 添加拼接的详细地址
				String addr = "";
				if (!StringUtils.isEmpty(memberCallCenter.getLocationTown())
						&& !memberCallCenter.getLocationTown().equals("0")) {
					addr = memberBaseService.getAddressBaseByCode(memberCallCenter.getLocationTown())
							+ memberCallCenter.getLocationDetail();
				} else {
					addr = memberBaseService.getAddressBaseByCode(memberCallCenter.getLocationCounty())
							+ memberCallCenter.getLocationDetail();
				}
				memberCallCenter.setAddress(addr);

				rs.setResult(memberCallCenter);
			} else {
				rs.setResultMessage("要查询的数据不存在!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMemberCallCenterInfo=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberCallCenterDTO> selectMobilePhoneCallCenterInfo(String managerMobile) {
		logger.info("查询接口方法：MemberCallCenterServiceImpl----->selectMobilePhoneCallCenterInfo");
		ExecuteResult<MemberCallCenterDTO> rs = new ExecuteResult<MemberCallCenterDTO>();
		try {
			EmployeeDTO curBelongManager = employeeService.getEmployeeInfoByMobile(managerMobile);

			if (curBelongManager != null) {

				MemberCallCenterDTO memberCallCenter = memberCallCenterDAO
						.queryCompanyInfoByCode(curBelongManager.getCompanyId());
				if (memberCallCenter == null) {
					memberCallCenter = new MemberCallCenterDTO();
				}
				memberCallCenter.setCurBelongSellerName(memberCallCenter.getCompanyName());
				// 查看客户经理名称
				// EmployeeDTO curBelongManager =
				// employeeService.getEmployeeInfo(curBelongManagerId);
				//
				// if (curBelongManager != null) {
				memberCallCenter.setCurBelongManagerName(curBelongManager.getName());
				// }
				rs.setResult(memberCallCenter);
			} else {
				rs.setResultMessage("要查询的数据不存在!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMobilePhoneCallCenterInfo=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
		}
		return rs;

	}

	@Override
	public ExecuteResult<List<GradeMemberCompanyInfoDTO>> selectGradeInfoList(String packageActiveStartTime,
			String packageActiveEndTime) {
		logger.info("调用的接口：MemberCallCenterServiceImpl=====》selectGradeInfoList");
		ExecuteResult<List<GradeMemberCompanyInfoDTO>> rs = new ExecuteResult<List<GradeMemberCompanyInfoDTO>>();
		try {
			List<GradeMemberCompanyInfoDTO> list = memberCallCenterDAO.selectGradeList(packageActiveStartTime,
					packageActiveEndTime);
			if (list.size() > 0 && list != null) {
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.addErrorMessage("fail");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.setResultMessage("没查到数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMobilePhoneCallCenterInfo=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberCallCenterService#
	 * selectMemberInfoByAccountNo(java.lang.String)
	 */
	@Override
	public ExecuteResult<MemberBaseInfoDTO> selectMemberInfoByAccountNo(String accountNo) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->selectMemberInfoByAccountNo");
		ExecuteResult<MemberBaseInfoDTO> rs = new ExecuteResult<MemberBaseInfoDTO>();
		try {
			if (StringUtils.isEmpty(accountNo)) {
				rs.addErrorMessage("参数错误");
			} else {
				MemberBaseInfoDTO memberBaseInfoDTO = memberCompanyInfoDao.selectMemberInfoByAccountNo(accountNo);
				if (memberBaseInfoDTO == null) {
					rs.addErrorMessage("未检索到会员信息，帐号：" + accountNo);
				} else {
					rs.setResult(memberBaseInfoDTO);
				}
			}
		} catch (Exception e) {
			rs.addErrorMessage("error");
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMemberInfoByAccountNo=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberCallCenterService#
	 * selectSellerInfoByAccountNo(java.lang.String)
	 */
	@Override
	public ExecuteResult<MemberBaseInfoDTO> selectSellerInfoByAccountNo(String accountNo) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->selectSellerInfoByAccountNo");
		ExecuteResult<MemberBaseInfoDTO> rs = new ExecuteResult<MemberBaseInfoDTO>();
		try {
			if (StringUtils.isEmpty(accountNo)) {
				rs.addErrorMessage("参数错误");
			} else {
				MemberBaseInfoDTO memberBaseInfoDTO = memberCompanyInfoDao.selectSellerInfoByAccountNo(accountNo);
				if (memberBaseInfoDTO == null) {
					rs.addErrorMessage("未检索到供应商信息，帐号：" + accountNo);
				} else {
					rs.setResult(memberBaseInfoDTO);
				}
			}
		} catch (Exception e) {
			rs.addErrorMessage("error");
			e.printStackTrace();
			logger.error("MemberCallCenterServiceImpl----->selectMemberInfoByAccountNo=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.MemberCallCenterService#
	 * queryMemberInvoiceInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode, String channelCode) {
		logger.info("调用接口：MemberCallCenterServiceImpl----->queryMemberInvoiceInfo");
		ExecuteResult<MemberInvoiceDTO> rs = new ExecuteResult<MemberInvoiceDTO>();
		MemberInvoiceDTO member = null;
		member = memberCallCenterDAO.queryMemberInvoiceInfoByChannelCode(memberCode, channelCode);
		try {
			if (member != null) {
				rs.setResult(member);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("要查询的数据不存在");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}
		} catch (Exception e) {
			rs.setResultMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberInoviceServiceImpl----->queryMemberInvoiceInfo=" + e);
		}
		return rs;
	}
}

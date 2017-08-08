package cn.htd.membercenter.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.dao.MemberBaseDAO;
import cn.htd.membercenter.dao.MemberBaseOperationDAO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.enums.MemberTypeEnum;
import cn.htd.membercenter.service.MemberBaseService;
import cn.htd.usercenter.dto.LoginLogDTO;
import cn.htd.usercenter.service.UserExportService;

@Service("memberBaseService")
public class MemberBaseServiceImpl implements MemberBaseService {

	private final static Logger logger = LoggerFactory.getLogger(MemberBaseServiceImpl.class);

	@Resource
	private MemberBaseDAO memberBaseDao;

	@Resource
	private MemberBaseOperationDAO memberBaseOperationDAO;

	@Autowired
	private UserExportService userExportService;

	@Autowired
	private BaseAddressService baseAddressService;

	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberBaseInfoById(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		try {
			String memberId = memberBaseDTO.getMemberId();
			String memberCode = memberBaseDTO.getMemberCode();
			if (memberBaseDTO.getBuyerSellerType() == null || memberBaseDTO.getBuyerSellerType().equals("")) {
				memberBaseDTO.setBuyerSellerType("1");
			}
			MemberBaseDTO member = null;
			if (StringUtils.isNotBlank(memberId) || StringUtils.isNotBlank(memberCode)) {
				member = memberBaseDao.queryMemberBaseInfoById(memberBaseDTO);
				String canMallLogin = member.getCanMallLogin();
				String hasGuaranteeLicense = member.getHasGuaranteeLicense();
				String hasBusinessLicense = member.getHasBusinessLicense();
				// 设置会员等级类型
				member.setMemberType(this.judgeMemberType(canMallLogin, hasGuaranteeLicense, hasBusinessLicense));
				// 设置公司注册地址详细信息
				if (StringUtils.isNotBlank(member.getLocationTown()) && !member.getLocationTown().equals("0")) {
					member.setRegisterAddress(
							this.getAddressBaseByCode(member.getLocationTown()) + member.getLocationDetail());
				} else {
					member.setRegisterAddress(
							this.getAddressBaseByCode(member.getLocationCounty()) + member.getLocationDetail());
				}

			}
			try {
				if (member != null) {
					rs.setResult(member);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("error");
				}
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberBaseServiceImpl----->queryMemberBaseInfo=" + e);
		}
		return rs;
	}

	@Override
	public String judgeMemberType(String canMallLogin, String hasGuaranteeLicense, String hasBusinessLicense) {
		String memberType = "";
		// 非商城登陆则为非会员
		if ("0".equals(canMallLogin)) {
			memberType = MemberTypeEnum.NONE_MEMBER.getCode();
		} else {
			// 商城登陆且有营业执照则为正式会员
			if ("1".equals(hasBusinessLicense)) {
				memberType = MemberTypeEnum.REAL_MEMBER.getCode();
			} else {
				// 商城登陆没有营业执照有担保执照则为担保会员
				if ("1".equals(hasGuaranteeLicense)) {
					memberType = MemberTypeEnum.GUARANTEE_MEMBER.getCode();
				}
			}
		}
		return memberType;
	}

	@Override
	public ExecuteResult<DataGrid<MemberBaseDTO>> queryMemberInfoBySellerId(MemberBaseDTO memberBaseDTO,
			Pager<MemberBaseDTO> pager) {
		ExecuteResult<DataGrid<MemberBaseDTO>> rs = new ExecuteResult<DataGrid<MemberBaseDTO>>();
		DataGrid<MemberBaseDTO> dg = new DataGrid<MemberBaseDTO>();
		List<MemberBaseDTO> resultList = new ArrayList<MemberBaseDTO>();
		long count = 0;
		try {
			memberBaseDTO.setSearchType("1");
			count = memberBaseDao.queryMemberInfoBySellerIdCount(memberBaseDTO);
			if (count > 0) {
				resultList = memberBaseDao.queryMemberInfoBySellerId(memberBaseDTO, pager);
			} else {
				memberBaseDTO.setSearchType("2");
				count = memberBaseDao.queryMemberInfoBySellerIdCount(memberBaseDTO);
				if (count > 0) {
					resultList = memberBaseDao.queryMemberInfoBySellerId(memberBaseDTO, pager);
				} else {
					memberBaseDTO.setSearchType("3");
					count = memberBaseDao.queryMemberInfoBySellerIdCount(memberBaseDTO);
					if (count > 0) {
						resultList = memberBaseDao.queryMemberInfoBySellerId(memberBaseDTO, pager);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(resultList)) {
				dg.setRows(resultList);
				dg.setTotal(count);
				rs.setResult(dg);
			} else {
				rs.setResultMessage("要查询的数据不存在");
			}
		} catch (Exception e) {
			logger.error("MemberBaseServiceImpl----->queryMemberInfoBySellerId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberBaseInfo4order(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		try {
			String memberId = memberBaseDTO.getMemberId();
			MemberBaseDTO member = null;
			if (StringUtils.isNotBlank(memberId)) {
				member = memberBaseDao.queryMemberBaseInfo4order(memberBaseDTO);
				// 设置公司注册地址详细信息
				String addr = member.getLocationDetail();
				if (StringUtils.isNotBlank(member.getLocationTown()) && !member.getLocationTown().equals("0")) {
					addr = getAddressBaseByCode(member.getLocationTown()) + addr;
				} else {
					addr = getAddressBaseByCode(member.getLocationCounty()) + addr;
				}

				member.setReceiptAddress(addr);
			}
			try {
				if (member != null) {
					rs.setResult(member);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("error");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberBaseServiceImpl----->queryMemberBaseInfo4order=" + e);
		}
		return rs;
	}

	@Override
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

	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberLoginInfo(MemberBaseDTO memberBaseDTO) {
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		try {
			String memberId = memberBaseDTO.getMemberId();
			MemberBaseDTO member = null;
			if (StringUtils.isNotBlank(memberId)) {
				member = memberBaseDao.queryMemberLoginInfo(memberBaseDTO);
				ExecuteResult<LoginLogDTO> lastLoginTime = userExportService.queryLastLoginLog(Long.valueOf(memberId));
				if (lastLoginTime.isSuccess()) {
					Date lastTime = lastLoginTime.getResult().getLoginTime();
					member.setLastLoginTime(lastTime);
				}
			}
			try {
				if (member != null) {
					rs.setResult(member);
					rs.setResultMessage("success");
				} else {
					rs.setResultMessage("要查询的数据不存在");
					rs.setResultMessage("error");
				}
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("MemberBaseServiceImpl----->queryMemberLoginInfo=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Integer> selectIsRealNameAuthenticated(Long memberId) {
		ExecuteResult<Integer> rs = new ExecuteResult<Integer>();
		try {
			Integer i = memberBaseDao.getRealNameStatus(memberId);
			if (i != null) {
				rs.setResult(i);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberBaseServiceImpl====>selectIsRealNameAuthenticated=");
		}
		return rs;
	}

	@Override
	public ExecuteResult<Long> selectBooleanCompanyName(String companyName) {
		logger.info("会员公司名称存在：MemberBaseService====>selectBooleanCompanyName");
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		try {
			Long memberId = memberBaseDao.selectBooleanCompanyName(companyName);
			if (memberId != null) {
				rs.setResult(memberId);
				rs.setResultMessage("true");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("没查到数据");
				rs.setResultMessage("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("异常");
			rs.setResultMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberBaseService====>selectBooleanCompanyName=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberBelongInfo(String memberCode) {
		logger.info("会员公司名称存在：MemberBaseService====>queryMemberBelongInfo,memberCode=" + memberCode);
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		if (StringUtils.isNotBlank(memberCode)) {
			try {
				MemberBaseDTO baseDTO = memberBaseDao.queryMemberBelongInfo(memberCode);
				if (baseDTO != null) {
					MemberBaseInfoDTO seller = memberBaseOperationDAO
							.getMemberbaseBySellerId(Long.valueOf(baseDTO.getMemberId()), GlobalConstant.IS_SELLER);
					if (seller == null) {
						rs.setResultMessage("true");
						rs.setResult(baseDTO);
						rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
					} else {
						rs.setResultMessage("false");
						rs.addErrorMessage("该小b已经具备供应商身份");
					}
				} else {
					rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
					rs.addErrorMessage("不存在此会员编码");
					rs.setResultMessage("false");
				}
			} catch (Exception e) {
				e.printStackTrace();
				rs.addErrorMessage("异常");
				rs.setResultMessage("error");
				rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
				logger.error("MemberBaseService====>queryMemberBelongInfo=" + e);
			}
		} else {
			rs.addErrorMessage("异常");
			rs.setResultMessage("没有入参");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}
	
	@Override
	public ExecuteResult<MemberBaseDTO> queryMemberBelongInfoNew(String memberCode) {
		
		logger.info("MemberBaseService====>queryMemberBelongInfoNew,memberCode=" + memberCode);
		
		ExecuteResult<MemberBaseDTO> rs = new ExecuteResult<MemberBaseDTO>();
		
		if(StringUtils.isEmpty(memberCode)){
			rs.addErrorMessage("异常");
			rs.setResultMessage("入参memberCode为空");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}

		try {
			MemberBaseDTO baseDTO = memberBaseDao.queryMemberBelongInfo(memberCode);
			
			if(baseDTO==null){
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("不存在此会员编码");
				rs.setResultMessage("false");
				return rs;
			}
			baseDTO.setBuyerSellerType("1");
			MemberBaseDTO memberBaseInfo = memberBaseDao.queryMemberBaseInfoById(baseDTO);
			
			baseDTO.setLocationCity(memberBaseInfo.getLocationCity());
			
			rs.setResultMessage("true");
			rs.setResult(baseDTO);
			rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			rs.addErrorMessage("异常");
			rs.setResultMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			logger.error("MemberBaseService====>queryMemberBelongInfo=" + e);
		}
	
		return rs;
	}

	@Override
	public ExecuteResult<Long> queryMemberInfoByCellPhone(String artificialPersonMobile) {
		logger.info("会员公司名称存在：MemberBaseService====>queryMemberInfoByCellPhone,artificialPersonMobile="
				+ artificialPersonMobile);
		ExecuteResult<Long> rs = new ExecuteResult<Long>();
		if (StringUtils.isNotBlank(artificialPersonMobile)) {
			try {
				long count = memberBaseDao.queryMemberInfoByCellPhone(artificialPersonMobile);
				if (count > 0) {
					rs.setResult(count);
					rs.setResultMessage("true");
					rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				} else {
					rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
					rs.addErrorMessage("没查到数据");
					rs.setResultMessage("false");
				}
			} catch (Exception e) {
				e.printStackTrace();
				rs.addErrorMessage("异常");
				rs.setResultMessage("error");
				rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
				logger.error("MemberBaseService====>queryMemberInfoByCellPhone=" + e);
			}
		} else {
			rs.addErrorMessage("异常");
			rs.setResultMessage("没有入参");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}
}

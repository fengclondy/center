package cn.htd.membercenter.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.exception.MembercenterBussinessException;
import cn.htd.membercenter.dao.MemberPlatformFeeInfoDAO;
import cn.htd.membercenter.dto.MemberPlatformFeeInfoDTO;
import cn.htd.membercenter.service.MemberPlatformFeeInfoService;

@Service("memberPlatformFeeInfoService")
public class MemberPlatformFeeInfoServiceImpl implements MemberPlatformFeeInfoService {
	private final static Logger logger = LoggerFactory.getLogger(MemberSellerDepositServiceImpl.class);

	@Resource
	private MemberPlatformFeeInfoDAO memberPlatformFeeInfoDAO;

	@Override
	public DataGrid<MemberPlatformFeeInfoDTO> searchSellerPlatform(String bondNam, String bondNo,
			Pager<MemberPlatformFeeInfoDTO> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberPlatformFeeInfoServiceImpl-searchSellerPlatform",
				JSONObject.toJSONString(bondNam), JSONObject.toJSONString(bondNo), JSONObject.toJSONString(pager));
		DataGrid<MemberPlatformFeeInfoDTO> result = new DataGrid<MemberPlatformFeeInfoDTO>();
		List<MemberPlatformFeeInfoDTO> feeInfoList = null;
		try {
			Long count = memberPlatformFeeInfoDAO.sellerPlatformCount(bondNam, bondNo);
			if (count > 0) {
				feeInfoList = memberPlatformFeeInfoDAO.searchSellerPlatform(bondNam, bondNo, pager);
				result.setRows(feeInfoList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberPlatformFeeInfoServiceImpl-searchSellerPlatform",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public DataGrid<MemberPlatformFeeInfoDTO> searchPlatformFeeInfo(Long sellerId, String startTime, String endTime,
			Pager<MemberPlatformFeeInfoDTO> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberPlatformFeeInfoServiceImpl-searchPlatformFeeInfo",
				JSONObject.toJSONString(sellerId), JSONObject.toJSONString(startTime), JSONObject.toJSONString(endTime),
				JSONObject.toJSONString(pager));
		DataGrid<MemberPlatformFeeInfoDTO> result = new DataGrid<MemberPlatformFeeInfoDTO>();
		List<MemberPlatformFeeInfoDTO> platformFeeList = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat myformat = new DecimalFormat("0.00");
		try {
			Long count = memberPlatformFeeInfoDAO.platformFeeInfoCount(sellerId, startTime, endTime);
			if (count > 0) {
				platformFeeList = memberPlatformFeeInfoDAO.searchPlatformFeeInfo(sellerId, startTime, endTime, pager);
				for (MemberPlatformFeeInfoDTO feeInfo : platformFeeList) {
					feeInfo.setDepositName("手动");
					if (feeInfo.getFee() != null) {
						feeInfo.setFeeName(myformat.format(feeInfo.getFee()));
					}
					if (feeInfo.getCreateTime() == null || "".equals(feeInfo.getCreateTime())) {
						feeInfo.setCreateTimeName("");
					} else {
						feeInfo.setCreateTimeName(formatter.format(feeInfo.getCreateTime()));
					}
				}
				result.setRows(platformFeeList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberPlatformFeeInfoServiceImpl-searchPlatformFeeInfo",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<MemberPlatformFeeInfoDTO> addPlatformFeeInfo(
			MemberPlatformFeeInfoDTO memberPlatformFeeInfoDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberPlatformFeeInfoServiceImpl-addPlatformFeeInfo",
				JSONObject.toJSONString(memberPlatformFeeInfoDTO));
		ExecuteResult<MemberPlatformFeeInfoDTO> result = new ExecuteResult<MemberPlatformFeeInfoDTO>();
		try {
			checkInputParameter(memberPlatformFeeInfoDTO);
			memberPlatformFeeInfoDAO.addPlatformFeeInfo(memberPlatformFeeInfoDTO);
			result.setResultMessage("修改成功！");
		} catch (MembercenterBussinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "MemberPlatformFeeInfoServiceImpl-addPlatformFeeInfo", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberPlatformFeeInfoServiceImpl-addPlatformFeeInfo",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * Validation的check
	 * 
	 * @param memberCompanyInfoDto
	 */
	private void checkInputParameter(MemberPlatformFeeInfoDTO memberPlatformFeeInfoDTO) {
		if (memberPlatformFeeInfoDTO.getSellerId() == null) {
			throw new MembercenterBussinessException("商家ID不能是空白");
		}
		if (memberPlatformFeeInfoDTO.getFee() == null) {
			throw new MembercenterBussinessException("缴费金额不能是空白");
		}
		if (memberPlatformFeeInfoDTO.getCreateId() == null) {
			throw new MembercenterBussinessException("创建人ID不能是空白");
		}
		if (StringUtils.isBlank(memberPlatformFeeInfoDTO.getRemarks())) {
			throw new MembercenterBussinessException("备注不能是空白");
		}
		if (StringUtils.isBlank(memberPlatformFeeInfoDTO.getCreateName())) {
			throw new MembercenterBussinessException("创建人名称");
		}
	}
}

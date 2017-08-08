package cn.htd.membercenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.common.constant.ErpStatusEnum;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.MemberTaskCallbackDAO;
import cn.htd.membercenter.dto.CompanyRelationDownErpCallbackDTO;
import cn.htd.membercenter.dto.MemberDownCallbackDTO;
import cn.htd.membercenter.dto.SalesmanDownErpCallbackDTO;
import cn.htd.membercenter.service.MemberErpCallbackService;

@Service("memberErpCallbackService")
public class MemberErpCallbackServiceImpl implements MemberErpCallbackService {
	private final static Logger logger = LoggerFactory.getLogger(MemberErpCallbackServiceImpl.class);

	@Resource
	MemberTaskCallbackDAO memberTaskCallbackDAO;

	@Override
	public ExecuteResult<Boolean> memberDownErpCallback(MemberDownCallbackDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			dto.setModifyTime(trancDate);
			String syncKey = dto.getMerchOrderNo();
			dto.setSyncKey(syncKey);
			if (dto.getErpResult() == GlobalConstant.SUCCESS) {
				dto.setStatus(ErpStatusEnum.SUCCESS.getValue());
				if (!StringUtils.isEmpty(dto.getErpMemberCode())) {
					memberTaskCallbackDAO.updateCompanyCode(dto);
				}
			} else {
				dto.setStatus(ErpStatusEnum.FAILURE.getValue());
			}

			if (!StringUtils.isEmpty(syncKey)) {
				memberTaskCallbackDAO.updateErpStatus(dto);
			}
			rs.setResult(true);
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("会员下行回调异常");
			logger.error("MemberErpCallbackServiceImpl----->memberDownErpCallback=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> companyRelationDownErpCallback(CompanyRelationDownErpCallbackDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			dto.setModifyTime(trancDate);
			String boxId = dto.getMerchOrderNo().split(GlobalConstant.ERP_DEAL_STR_SPIL)[1];
			dto.setBoxId(Long.valueOf(boxId));
			if (dto.getResult() == GlobalConstant.SUCCESS) {
				dto.setStatus(ErpStatusEnum.SUCCESS.getValue());
			} else {
				dto.setStatus(ErpStatusEnum.FAILURE.getValue());
			}
			memberTaskCallbackDAO.updateBoxErpStatus(dto);
			rs.setResult(true);
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("单位往来关系回调异常");
			logger.error("MemberErpCallbackServiceImpl----->CompanyRelationDownErpCallback=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> salesmanDownErpCallback(SalesmanDownErpCallbackDTO dto) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date trancDate = new Date(format.parse(format.format(date)).getTime());
			dto.setModifyTime(trancDate);
			String businessId = dto.getMerchOrderNo().split(GlobalConstant.ERP_DEAL_STR_SPIL)[1];
			dto.setBusinessId(Long.valueOf(businessId));
			if (dto.getResult() == GlobalConstant.SUCCESS) {
				dto.setStatus(ErpStatusEnum.SUCCESS.getValue());
			} else {
				dto.setStatus(ErpStatusEnum.FAILURE.getValue());
			}
			memberTaskCallbackDAO.updateBusinessErpStatus(dto);
			// 删除下行成功的品牌品类为空的经营关系
			try {
				memberTaskCallbackDAO.delNoUseBusinessErpStatus(dto);
			} catch (Exception e) {
				logger.error("salesmanDownErpCallback delNoUseBusinessErpStatus:" + e);
			}
			rs.setResult(true);
		} catch (Exception e) {
			rs.setResult(false);
			rs.addErrorMessage("客商业务员回调异常");
			logger.error("MemberErpCallbackServiceImpl----->salesmanDownErpCallback=" + e);
		}
		return rs;
	}

}

package cn.htd.marketcenter.service.impl.promotion;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.B2cCouponUseLogSyncHistoryDAO;
import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;
import cn.htd.marketcenter.dto.B2cCouponUseLogSyncDTO;
import cn.htd.marketcenter.service.B2cCouponUseLogSyncService;
import cn.htd.membercenter.service.MemberBaseInfoService;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("b2cCouponUseLogSyncService")
public class B2cCouponUseLogSyncServiceImpl implements
		B2cCouponUseLogSyncService {

	private static final Logger logger = LoggerFactory
			.getLogger(B2cCouponUseLogSyncServiceImpl.class);

	@Resource
	private B2cCouponUseLogSyncHistoryDAO b2cCouponUseLogSyncHistoryDAO;

	@Resource
	private MemberBaseInfoService memberBaseInfoService;

	@Override
	public ExecuteResult<String> saveB2cCouponUseLogSync(
			B2cCouponUseLogSyncDTO b2cCouponUseLogSyncDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			result.setCode(MarketCenterCodeConst.RETURN_SUCCESS);
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(b2cCouponUseLogSyncDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR,
						validateResult.getErrorMsg());
			}
			String memberId = b2cCouponUseLogSyncDTO.getMemberId();
			String memberCode = b2cCouponUseLogSyncDTO.getMemberCode();
			if (StringUtils.isEmpty(memberId)
					&& StringUtils.isEmpty(memberCode)) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR, "参数memberId:"
								+ memberId + "和参数memberCode:" + memberCode
								+ "不能同时为空");
			}
			String b2cSellerCode = "";
			if (!StringUtils.isEmpty(memberCode)) {
				b2cSellerCode = memberCode;
			} else {
				ExecuteResult<String> memRaoRes = memberBaseInfoService
						.getMemberCodeById(Long.parseLong(memberId));
				if (!memRaoRes.isSuccess()) {
					throw new MarketCenterBusinessException(
							MarketCenterCodeConst.MEMBER_INFO_NOT_EXIST,
							"会员信息不存在");
				}
				b2cSellerCode = memRaoRes.getResult();
			}
			B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO = JSON.parse(
					JSON.json(b2cCouponUseLogSyncDTO),
					B2cCouponUseLogSyncDMO.class);
			b2cCouponUseLogSyncDMO.setB2cSellerCode(b2cSellerCode);
			b2cCouponUseLogSyncHistoryDAO
					.saveB2cCouponUseLogSyncHistory(b2cCouponUseLogSyncDMO);
		} catch (DuplicateKeyException dupe) {
			result.setCode(MarketCenterCodeConst.RETURN_SUCCESS);
			logger.error("插入数据库是违反了主键唯一约束  请求参数:{}",
					JSONObject.toJSONString(b2cCouponUseLogSyncDTO));
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

}

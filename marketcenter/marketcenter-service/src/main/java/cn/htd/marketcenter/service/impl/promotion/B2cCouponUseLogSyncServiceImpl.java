package cn.htd.marketcenter.service.impl.promotion;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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

@Service("b2cCouponUseLogSyncService")
public class B2cCouponUseLogSyncServiceImpl implements B2cCouponUseLogSyncService {

	private static final Logger logger = LoggerFactory
			.getLogger(B2cCouponUseLogSyncServiceImpl.class);
	
	@Resource
	private B2cCouponUseLogSyncHistoryDAO b2cCouponUseLogSyncHistoryDAO;

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
			B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO = JSON.parse(JSON.json(b2cCouponUseLogSyncDTO), B2cCouponUseLogSyncDMO.class);
			b2cCouponUseLogSyncHistoryDAO.saveB2cCouponUseLogSyncHistory(b2cCouponUseLogSyncDMO);
		} catch(DuplicateKeyException dupe){
			result.setCode(MarketCenterCodeConst.RETURN_SUCCESS);
			logger.error("插入数据库是违反了主键唯一约束  请求参数:{}",JSONObject.toJSONString(b2cCouponUseLogSyncDTO));
		}catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

}

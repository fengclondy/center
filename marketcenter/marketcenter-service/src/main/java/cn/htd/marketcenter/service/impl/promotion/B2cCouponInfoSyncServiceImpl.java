package cn.htd.marketcenter.service.impl.promotion;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.B2cCouponInfoSyncHistoryDAO;
import cn.htd.marketcenter.dmo.B2cCouponInfoSyncDMO;
import cn.htd.marketcenter.dto.B2cCouponInfoSyncDTO;
import cn.htd.marketcenter.service.B2cCouponInfoSyncService;

@Service("b2cCouponInfoSyncService")
public class B2cCouponInfoSyncServiceImpl implements B2cCouponInfoSyncService {

	private static final Logger logger = LoggerFactory
			.getLogger(B2cCouponInfoSyncServiceImpl.class);

	@Resource
	private B2cCouponInfoSyncHistoryDAO b2cCouponInfoSyncHistoryDAO;

	/**
	 * B2C无敌券信息同步接口
	 */
	@Override
	public ExecuteResult<String> saveB2cCouponInfoSync(
			B2cCouponInfoSyncDTO b2cCouponInfoSyncDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			result.setCode(MarketCenterCodeConst.RETURN_SUCCESS);
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(b2cCouponInfoSyncDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR,
						validateResult.getErrorMsg());
			}
			
			Integer discountPercent = b2cCouponInfoSyncDTO.getDiscountPercent();
			if (null == discountPercent || discountPercent.intValue() < 0
					|| discountPercent.intValue()>100) {
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR,
						"折扣券单次使用百分比值不满足规则:"
								+ discountPercent);
			}
			Date date = new Date();
			if(b2cCouponInfoSyncDTO.getCouponEndTime().before(date)){
				throw new MarketCenterBusinessException(
						MarketCenterCodeConst.PARAMETER_ERROR,
						"结束时间不能小于当前时间:"
								+ b2cCouponInfoSyncDTO.getCouponEndTime());
			}
			B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO = JSON.parse(JSON.json(b2cCouponInfoSyncDTO), B2cCouponInfoSyncDMO.class);
			// 1-校验本次请求是否和最新一次修改的内容相同
			B2cCouponInfoSyncDMO daoRes = b2cCouponInfoSyncHistoryDAO
					.queryB2cCouponInfoSyncHistory(b2cCouponInfoSyncDMO);
			if(null == daoRes || (!b2cCouponInfoSyncDTO.getCouponName().equals(daoRes.getCouponName()) ||
					!b2cCouponInfoSyncDTO.getCouponDescribe().equals(daoRes.getCouponDescribe()) ||
					!b2cCouponInfoSyncDTO.getCouponType().equals(daoRes.getCouponType()) ||
					!b2cCouponInfoSyncDTO.getCouponProvideType().equals(daoRes.getCouponProvideType()) ||
					b2cCouponInfoSyncDTO.getCouponStartTime().compareTo(daoRes.getCouponStartTime()) != 0 ||
					b2cCouponInfoSyncDTO.getCouponEndTime().compareTo(daoRes.getCouponEndTime()) != 0 ||
					b2cCouponInfoSyncDTO.getDiscountThreshold().compareTo(daoRes.getDiscountThreshold()) != 0 ||
				    discountPercent.intValue() != daoRes.getDiscountPercent())){
				b2cCouponInfoSyncHistoryDAO.saveB2cCouponInfoSyncHistory(b2cCouponInfoSyncDMO);
			}
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
	
	public static void main(String[] args) {
		B2cCouponInfoSyncDTO b2cCouponInfoSyncDTO = new B2cCouponInfoSyncDTO();
		Date date = new Date();
		date.setMonth(10);
		date.setHours(10);
		date.setMinutes(50);
		date.setMinutes(40);
		b2cCouponInfoSyncDTO.setCouponEndTime(date);
		Date date1 = new Date();
		if(b2cCouponInfoSyncDTO.getCouponEndTime().before(date1)){
			System.out.println("ddd");
		}else{
			System.out.println("f");
		}
	}
}

package cn.htd.marketcenter.service.impl.mall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.dto.BuyerCouponConditionDTO;
import cn.htd.marketcenter.dto.BuyerCouponCountDTO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.BuyerReceiveCouponDTO;
import cn.htd.marketcenter.dto.UsedExpiredBuyerCouponDTO;
import cn.htd.marketcenter.service.BuyerCouponInfoService;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.handle.CouponRedisHandle;
import com.github.pagehelper.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("buyerCouponInfoService")
public class BuyerCouponInfoServiceImpl implements BuyerCouponInfoService {

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private CouponRedisHandle couponRedisHandle;

	@Resource
	private PromotionBaseService baseService;

	@Resource
	private BuyerCouponInfoDAO buyerCouponInfoDAO;

	@Override
	public ExecuteResult<DataGrid<BuyerCouponCountDTO>> queryBuyerReceiveCouponList(BuyerCouponInfoDTO condition,
			Pager<BuyerCouponInfoDTO> pager) {
		ExecuteResult<DataGrid<BuyerCouponCountDTO>> result = new ExecuteResult<DataGrid<BuyerCouponCountDTO>>();
		DataGrid<BuyerCouponCountDTO> dataGrid = new DataGrid<BuyerCouponCountDTO>();
		List<BuyerCouponCountDTO> receiveList = null;
		long count = 0;
		try {
			if (condition == null) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "查询条件不能为空");
			}
			if (StringUtil.isEmpty(condition.getPromotionId()) || StringUtil.isEmpty(condition.getLevelCode())) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动编码和层级编号不能为空");
			}
			count = buyerCouponInfoDAO.queryBuyerReceiveCouponCount(condition);
			if (count > 0) {
				receiveList = buyerCouponInfoDAO.queryBuyerReceiveCouponList(condition, pager);
				dataGrid.setRows(receiveList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<BuyerCouponInfoDTO>> queryBuyerCouponList(BuyerCouponConditionDTO condition,
			Pager<BuyerCouponInfoDTO> pager) {
		ExecuteResult<DataGrid<BuyerCouponInfoDTO>> result = new ExecuteResult<DataGrid<BuyerCouponInfoDTO>>();
		DataGrid<BuyerCouponInfoDTO> dataGrid = new DataGrid<BuyerCouponInfoDTO>();
		List<BuyerCouponInfoDTO> couponList = null;
		long count = 0;
		try {
			condition.setDeleteStatus(dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS,
					DictionaryConst.OPT_COUPON_STATUS_DELETED));
			count = buyerCouponInfoDAO.queryBuyerCouponCount(condition);
			if (count > 0) {
				couponList = buyerCouponInfoDAO.queryBuyerCouponList(condition, pager);
				dataGrid.setRows(couponList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<Map<String, Long>> getBuyerUnusedOwnCouponCountByType(String messageId, String buyerCode) {
		ExecuteResult<Map<String, Long>> result = new ExecuteResult<Map<String, Long>>();
		List<BuyerCouponCountDTO> couponCountList = null;
		Map<String, Long> countMap = new HashMap<String, Long>();
		String status = "";
		String type = "";
		long count = 0;
		long total = 0;
		String unusedStatus = dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS,
				DictionaryConst.OPT_COUPON_STATUS_UNUSED);
		try {
			if (StringUtils.isEmpty(buyerCode)) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "会员编码不能为空");
			}
			couponCountList = couponRedisHandle.getRedisBuyerCouponCount(buyerCode);
			if (couponCountList != null && !couponCountList.isEmpty()) {
				for (BuyerCouponCountDTO countDTO : couponCountList) {
					status = countDTO.getStatus();
					type = countDTO.getCouponType();
					count = countDTO.getReceiveCount();
					if (!unusedStatus.equals(status)) {
						continue;
					}
					total = 0;
					if (countMap.containsKey(type)) {
						total = countMap.get(type);
					}
					countMap.put(type, total + count);
				}
			}
			result.setResult(countMap);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<List<BuyerCouponCountDTO>> getBuyerOwnCouponCountByStatusType(String messageId,
			String buyerCode) {
		ExecuteResult<List<BuyerCouponCountDTO>> result = new ExecuteResult<List<BuyerCouponCountDTO>>();
		List<BuyerCouponCountDTO> couponCountList = null;
		try {
			if (StringUtils.isEmpty(buyerCode)) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "会员编码不能为空");
			}
			couponCountList = couponRedisHandle.getRedisBuyerCouponCount(buyerCode);
			result.setResult(couponCountList);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<BuyerCouponInfoDTO>> getBuyerOwnCouponList(String messageId,
			BuyerCouponConditionDTO condition, Pager<BuyerCouponInfoDTO> pager) {
		ExecuteResult<DataGrid<BuyerCouponInfoDTO>> result = new ExecuteResult<DataGrid<BuyerCouponInfoDTO>>();
		DataGrid<BuyerCouponInfoDTO> dataGrid = new DataGrid<BuyerCouponInfoDTO>();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(condition);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
						validateResult.getErrorMsg());
			}
			dataGrid = couponRedisHandle.getRedisBuyerCouponList(condition, pager);
			result.setResult(dataGrid);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> saveBuyerReceiveCoupon(String messageId, BuyerReceiveCouponDTO receiveDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BuyerCouponInfoDTO collectCoupon = null;
		BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();
		boolean buyerChkResult = false;
		String promotionId = receiveDTO.getPromotionId();

		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(receiveDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
						validateResult.getErrorMsg());
			}
			collectCoupon = couponRedisHandle.receiveMemberCollectCoupon2Redis(receiveDTO);
			if (collectCoupon != null) {
				buyerCheckInfo.setBuyerCode(receiveDTO.getBuyerCode());
				buyerCheckInfo.setBuyerGrade(receiveDTO.getBuyerGrade());
				buyerChkResult = baseService.checkPromotionBuyerRule(collectCoupon, buyerCheckInfo);
				if (!buyerChkResult) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_BUYER_NO_AUTHIORITY,
							"会员没有领该券权限");
				}
				baseService.deletePromotionUselessInfo(collectCoupon);
				couponRedisHandle.sendBuyerCoupon2Redis(collectCoupon);
			}
		} catch (MarketCenterBusinessException bcbe) {
		    if (collectCoupon != null) {
				couponRedisHandle.restoreMemberCollectCouponBack2Redis(collectCoupon);
			}
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteUsedExpiredBuyerCoupon(String messageId,
			UsedExpiredBuyerCouponDTO targetCouponDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(targetCouponDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
						validateResult.getErrorMsg());
			}
			couponRedisHandle.deleteRedisExpiredBuyerCouponInfo(targetCouponDTO);
		} catch (MarketCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		}
		return result;
	}
}

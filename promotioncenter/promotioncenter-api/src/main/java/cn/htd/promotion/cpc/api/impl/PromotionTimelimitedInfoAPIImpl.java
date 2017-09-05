package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.handle.PromotionTimelimitedRedisHandle;
import cn.htd.promotion.cpc.biz.handle.SeckillReduceImplHandle;
import cn.htd.promotion.cpc.biz.handle.SeckillReleaseImplHandle;
import cn.htd.promotion.cpc.biz.handle.SeckillReserveImplHandle;
import cn.htd.promotion.cpc.biz.handle.SeckillRollbackImplHandle;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.TimelimitedStatusEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Service("promotionTimelimitedInfoAPI")
public class PromotionTimelimitedInfoAPIImpl implements PromotionTimelimitedInfoAPI {

	private static final Logger logger = LoggerFactory.getLogger(PromotionTimelimitedInfoAPIImpl.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionTimelimitedInfoService promotionTimelimitedInfoService;

	@Resource
	private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private SeckillReserveImplHandle seckillReserveImplHandle;

	@Resource
	private SeckillReleaseImplHandle seckillReleaseImplHandle;

	@Resource
	private SeckillReduceImplHandle seckillReduceImplHandle;

	@Resource
	private SeckillRollbackImplHandle seckillRollbackImplHandle;

	/**
	 * 汇掌柜APP - 查询秒杀活动列表
	 * 
	 * 粉丝 未登录 默认取汇通达O2O旗舰店的秒杀商品；已登录则取归属会员店的秒杀商品(根据buyerCode)
	 * 
	 * @param messageId
	 * @param page
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> getPromotionTimelimitedList(String messageId,
			String buyerCode, Pager<TimelimitedInfoResDTO> page) {
		ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> result = new ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>>();
		DataGrid<PromotionTimelimitedShowDTO> datagrid = new DataGrid<PromotionTimelimitedShowDTO>();
		// 所有有效秒杀活动集合,用于返回前端
		List<PromotionTimelimitedShowDTO> timelimitedDTOList = new ArrayList<PromotionTimelimitedShowDTO>();
		// 所有有效秒杀活动集合,用于排序
		List<PromotionTimelimitedShowDTO> timelimitedAllDTOList = new ArrayList<PromotionTimelimitedShowDTO>();
		PromotionTimelimitedShowDTO timelimitedMallDTO = null;
		int count = 0;
		long total = 0;
		int offset = 0;
		int rows = Integer.MAX_VALUE;
		if (page != null) {
			offset = page.getPageOffset();
			rows = page.getRows();
		}
		try {
			List<TimelimitedInfoResDTO> timelitedInfoList = promotionTimelimitedInfoService
					.getPromotionTimelimitedInfoByBuyerCode(messageId, buyerCode);
			if (null != timelitedInfoList) {
				for (TimelimitedInfoResDTO timelitedinfo : timelitedInfoList) {
					TimelimitedInfoResDTO timelited = promotionTimelimitedRedisHandle
							.getTimelitedInfoByPromotionId(timelitedinfo.getPromotionId());
					if (null != timelited) {
						timelimitedMallDTO = new PromotionTimelimitedShowDTO();
						timelimitedMallDTO.setTimelimitedInfo(timelited);
						timelimitedAllDTOList.add(timelimitedMallDTO);
					}
				}

			}
			if (!timelimitedAllDTOList.isEmpty()) {
				total = timelimitedAllDTOList.size();
				logger.info("************ 有效秒杀活动列表总数为: " + total + "************");
				Collections.sort(timelimitedAllDTOList);
				while (total > count) {
					if (count >= offset && timelimitedDTOList.size() < rows) {
						timelimitedDTOList.add(timelimitedAllDTOList.get(count));
					}
					if (timelimitedDTOList.size() >= rows) {
						break;
					}
					count++;
				}
				datagrid.setTotal(total);
				datagrid.setRows(timelimitedDTOList);
			}
			result.setResult(datagrid);
		} catch (PromotionCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.setErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 汇掌柜APP - 查询秒杀活动详情
	 * 
	 * @param messageId
	 * @param promotionId
	 * @param buyerCode
	 *            会员编码
	 * @return
	 */
	@Override
	public ExecuteResult<PromotionTimelimitedShowDTO> getPromotionTimelimitedInfoDetail(String messageId,
			String promotionId, String buyerCode) {
		ExecuteResult<PromotionTimelimitedShowDTO> result = new ExecuteResult<PromotionTimelimitedShowDTO>();
		TimelimitedInfoResDTO tmpTimelimitedDTO = null;
		PromotionTimelimitedShowDTO timelimitedDTO = null;
		String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
		String returnCode = "";
		try {
			String remaincount = promotionRedisDB.getHash(timelimitedResultKey,
					RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT);
			tmpTimelimitedDTO = promotionTimelimitedRedisHandle.getTimelitedInfoByPromotionId(promotionId);
			if (null != tmpTimelimitedDTO) {
				return result;
			}
			timelimitedDTO = new PromotionTimelimitedShowDTO();
			timelimitedDTO.setTimelimitedInfo(tmpTimelimitedDTO);
			if (StringUtils.isNotBlank(remaincount)) {
				timelimitedDTO.setRemainCount(Integer.valueOf(remaincount));
			}
			if (Integer.valueOf(remaincount) <= 0) { // 剩余商品为0 被抢光
				timelimitedDTO.setRemainCount(0);
				timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
				returnCode = PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_SKU_NO_REMAIN;
			}
			if (timelimitedDTO.getPromotionExtendInfoDTO() != null) {
				if ((new Date()).before(timelimitedDTO.getPromotionExtendInfoDTO().getEffectiveTime())) { // 未开始
					timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
					returnCode = PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_NO_STAET_ERROR;
				} else if ((new Date()).after(timelimitedDTO.getPromotionExtendInfoDTO().getInvalidTime())) { // 已结束
					timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
					returnCode = PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_HAS_ENDED_ERROR;
				} else {
					timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());// 活动进行中
				}
			} else {
				returnCode = PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_PARAM_ERROR;
			}
			timelimitedDTO.setShowStatusStr(TimelimitedStatusEnum.getName(timelimitedDTO.getCompareStatus()));

			if (StringUtils.isEmpty(returnCode) && !StringUtils.isEmpty(buyerCode)) {
				if (!checkTimelimitedIsAvailableByBuyerCode(messageId, buyerCode, promotionId).getResult()) {
					returnCode = PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_BUYER_NO_AUTHIORITY;
				}
			}
			result.setCode(returnCode);
			result.setResult(timelimitedDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.setErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 汇掌柜APP - 根据会员编码查询是否有总部秒杀是否有效
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @param promotionId
	 * @return
	 */
	@Override
	public ExecuteResult<Boolean> checkTimelimitedIsAvailableByBuyerCode(String messageId, String buyerCode,
			String promotionId) {
		String timelimitedJsonStr = "";
		TimelimitedInfoResDTO timelimitedInfo = null;
		ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
		timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
		timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
		PromotionExtendInfoDTO promotionExtendInfoDTO = timelimitedInfo.getPromotionExtendInfoDTO();
		if (timelimitedInfo.getPromotionId().equals(promotionId)) {
			if (checkBuyerCodeValid(promotionExtendInfoDTO, buyerCode).getResult()) {
				restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_PERMISSION);
				restult.setResult(true);
			} else {
				restult.setCode(checkBuyerCodeValid(promotionExtendInfoDTO, buyerCode).getCode());// 秒杀结果信息
				restult.setResult(false);
			}
		} else {
			restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_ID_ERROR);// 秒杀活动编码不正确
		}
		return restult;
	}

	/**
	 * 汇掌柜APP - 校验秒杀活动参数是否正确
	 * 
	 * @param timelimitedInfo
	 * @return
	 */
	private ExecuteResult<Boolean> checkParamValid(PromotionExtendInfoDTO promotionExtendInfoDTO) {
		ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
		if (dictionary
				.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
				.equals(promotionExtendInfoDTO.getShowStatus())) {// 秒杀活动启用
			if ((new Date()).before(promotionExtendInfoDTO.getEffectiveTime())) {
				// 秒杀活动未开始
				restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_NO_STAET_ERROR);
			} else if ((new Date()).after(promotionExtendInfoDTO.getInvalidTime())) {
				// 秒杀送活动已经结束
				restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_HAS_ENDED_ERROR);
			} else {
				// 秒杀送活动进行中
				restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_IS_PROCESSING_ERROR);
				restult.setResult(true);
			}
		} else {
			// 秒杀送活动未启用
			restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_IS_DISABLE_ERROR);
		}

		return restult;
	}

	/**
	 * 汇掌柜APP - 校验会员参数是否合法
	 * 
	 * @param promotionExtendInfoDTO
	 * @return
	 */
	private ExecuteResult<Boolean> checkBuyerCodeValid(PromotionExtendInfoDTO promotionExtendInfoDTO,
			String buyerCode) {
		ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
		PromotionSellerRuleDTO sellerRuleDTO = promotionExtendInfoDTO.getSellerRuleDTO();
		List<PromotionSellerDetailDTO> sellerDetailList = null;
		if (null != sellerRuleDTO && null != sellerRuleDTO.getSellerDetailList()) {// 限制粉丝只能购买归属会员的秒杀商品
			sellerDetailList = sellerRuleDTO.getSellerDetailList();
			for (PromotionSellerDetailDTO sellerDetail : sellerDetailList) {
				if (!sellerDetail.getSellerCode().equals(buyerCode)) {
					// 粉丝没有秒杀权限
					restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_NOT_PERMISSION_ERROR);
				} else {
					// 校验秒杀活动状态
					restult.setResult(checkParamValid(promotionExtendInfoDTO).getResult());
				}
			}

		} else {// 平台粉丝都可购买该秒杀商品
			restult.setResult(checkParamValid(promotionExtendInfoDTO).getResult());
		}

		return restult;
	}

	@Override
	public ExecuteResult<String> reserveStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			seckillReserveImplHandle.checkAndChangeStock(messageId, seckillInfoReqDTO);
			result.setResult("success");
		} catch (PromotionCenterBusinessException pcbe) {
			result.setCode(pcbe.getCode());
			result.setErrorMessage(pcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> releaseStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			seckillReleaseImplHandle.checkAndChangeStock(messageId, seckillInfoReqDTO);
			result.setResult("success");
		} catch (PromotionCenterBusinessException pcbe) {
			result.setCode(pcbe.getCode());
			result.setErrorMessage(pcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> reduceStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			seckillReduceImplHandle.checkAndChangeStock(messageId, seckillInfoReqDTO);
			result.setResult("success");
		} catch (PromotionCenterBusinessException pcbe) {
			result.setCode(pcbe.getCode());
			result.setErrorMessage(pcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> rollbackStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			seckillRollbackImplHandle.checkAndChangeStock(messageId, seckillInfoReqDTO);
			result.setResult("success");
		} catch (PromotionCenterBusinessException pcbe) {
			result.setCode(pcbe.getCode());
			result.setErrorMessage(pcbe.getMessage());
		} catch (Exception e) {
			result.setCode(PromotionCenterConst.SYSTEM_ERROR);
			result.setErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateSeckillPromotionLog(String messageId, SeckillInfoReqDTO seckillInfoReqDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}

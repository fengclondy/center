package cn.htd.promotion.cpc.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoExtendDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.handle.PromotionBargainRedisHandle;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.CalculateUtils;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainOverviewResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.PromotonInfoResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("promotionBargainInfoService")
public class PromotionBargainInfoServiceImpl implements
		PromotionBargainInfoService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionBargainInfoServiceImpl.class);

	@Resource
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	@Resource
	private DictionaryUtils dictionary;
	@Resource
	private PromotionBaseService baseService;
	@Resource
	private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
	@Resource
	private PromotionBargainRedisHandle promotionBargainRedisHandle;
	@Resource
	private GeneratorUtils noGenerator;
	@Resource
	private PromotionSloganDAO promotionSloganDAO;
	@Resource
	private PromotionInfoDAO promotionInfoDAO;
	@Resource
	private PromotionAccumulatyDAO promotionAccumulatyDAO;
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	@Resource
	private PromotionInfoExtendDAO promotionInfoExtendDAO;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;

	/**
	 * 获取砍价商品详情
	 */
	@Override
	public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(
			BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		PromotionBargainInfoResDTO promotionBargainInfoResDTO = null;
		PromotionBargainInfoDMO promotionBargainInfo = new PromotionBargainInfoDMO();
		// 从redis里面获取砍价详情
		PromotionBargainInfoResDTO dto = new PromotionBargainInfoResDTO();
		dto.setPromotionId(buyerBargainLaunch.getPromotionId());
		List<PromotionBargainInfoResDTO> promotionBargainInfoResDTOList = promotionBargainRedisHandle
				.getRedisBargainInfoList(dto);
		PromotionInfoDTO promotionInfo = promotionInfoDAO
				.queryById(buyerBargainLaunch.getPromotionId());
		if (promotionBargainInfoResDTOList != null
				&& promotionBargainInfoResDTOList.size() > 0) {
			for (PromotionBargainInfoResDTO p : promotionBargainInfoResDTOList) {
				if (p.getLevelCode().equals(buyerBargainLaunch.getLevelCode())) {
					promotionBargainInfo.setPromotionId(p.getPromotionId());
					promotionBargainInfo.setLevelCode(p.getLevelCode());
					promotionBargainInfo.setGoodsPicture(p.getGoodsPicture());
					promotionBargainInfo.setGoodsName(p.getGoodsName());
					promotionBargainInfo.setGoodsCostPrice(p
							.getGoodsCostPrice());
					promotionBargainInfo.setGoodsFloorPrice(p
							.getGoodsFloorPrice());
					promotionBargainInfo.setGoodsNum(p.getGoodsNum());
					promotionBargainInfo.setPartakeTimes(p.getPartakeTimes());
					promotionBargainInfo.setPromotionDesc(p
							.getPromotionSlogan());
					promotionBargainInfo.setContactNameD(p.getContactName());
					promotionBargainInfo.setContactTelphoneD(p
							.getContactTelephone());
					promotionBargainInfo.setContactAddressD(p
							.getContactAddress());
					promotionBargainInfo.setOfflineStartTimeD(p
							.getOfflineStartTime());
					promotionBargainInfo.setOfflineEndTimeD(p
							.getOfflineEndTime());
					promotionBargainInfo.setTemplateFlagD(p.getTemplateFlag());
					promotionBargainInfo.setSellerNameD(p
							.getPromotionProviderSellerCode());
					promotionBargainInfo.setEffectiveTime(p.getEffectiveTime());
					promotionBargainInfo.setInvalidTime(p.getInvalidTime());
					promotionBargainInfo.setShowStatusD(p.getShowStatus());
					promotionBargainInfo.setStatusD(promotionInfo.getStatus());
					break;
				}
			}
		}
		// 查询活动详情
		LOGGER.info(
				"MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,入参{}",
				buyerBargainLaunch.getMessageId(),
				JSON.toJSONString(buyerBargainLaunch));
		PromotionBargainInfoDMO promotionBargainInfo1 = new PromotionBargainInfoDMO();
		if (StringUtils.isEmpty(buyerBargainLaunch.getBargainCode())
				&& StringUtils.isEmpty(buyerBargainLaunch.getBuyerCode())) {// 微信用户查看
			promotionBargainInfo1 = null;
		} else {
			promotionBargainInfo1 = promotionBargainInfoDAO
					.getPromotionBargainInfoDetail(buyerBargainLaunch);
		}
		LOGGER.info(
				"MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,出参{}",
				buyerBargainLaunch.getMessageId(),
				JSON.toJSONString(promotionBargainInfo));
		if (promotionBargainInfo1 != null) {// 曾经发起过砍价
			promotionBargainInfo.setBargainCodeD(promotionBargainInfo1
					.getBargainCodeD());
			promotionBargainInfo.setLaunchTime(promotionBargainInfo1
					.getLaunchTime());
			promotionBargainInfo.setBargainOverTime(promotionBargainInfo1
					.getBargainOverTime());
			promotionBargainInfo.setIsBargainOver(promotionBargainInfo1
					.getIsBargainOver());
			promotionBargainInfo.setGoodsCurrentPrice(promotionBargainInfo1
					.getGoodsCurrentPrice());
		} else {
			promotionBargainInfo.setGoodsCurrentPrice(promotionBargainInfo
					.getGoodsCostPrice());
		}
		// 根据砍价编码查询砍价记录
		List<BuyerBargainRecordResDTO> buyerBargainRecordResList = new ArrayList<BuyerBargainRecordResDTO>();
		if (!StringUtils.isEmpty(buyerBargainLaunch.getBargainCode())) {
			LOGGER.info(
					"MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",
					buyerBargainLaunch.getMessageId(),
					buyerBargainLaunch.getBargainCode() + ":"
							+ buyerBargainLaunch.getMessageId());
			List<BuyerBargainRecordDMO> buyerBargainRecordList = buyerBargainRecordDAO
					.getBuyerBargainRecordByBargainCode(buyerBargainLaunch
							.getBargainCode());
			LOGGER.info(
					"MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",
					buyerBargainLaunch.getMessageId(),
					JSON.toJSONString(buyerBargainRecordList));
			if (buyerBargainRecordList != null) {
				String str = JSONObject.toJSONString(buyerBargainRecordList);
				buyerBargainRecordResList = JSONObject.parseArray(str,
						BuyerBargainRecordResDTO.class);
			}
		} else if (promotionBargainInfo1 != null) {// 聚合页进来并且曾经发起过砍价
			LOGGER.info(
					"MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",
					buyerBargainLaunch.getMessageId(),
					buyerBargainLaunch.getBargainCode() + ":"
							+ buyerBargainLaunch.getMessageId());
			List<BuyerBargainRecordDMO> buyerBargainRecordList = buyerBargainRecordDAO
					.getBuyerBargainRecordByBargainCode(promotionBargainInfo1
							.getBargainCodeD());
			LOGGER.info(
					"MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",
					buyerBargainLaunch.getMessageId(),
					JSON.toJSONString(buyerBargainRecordList));
			if (buyerBargainRecordList != null) {
				String str = JSONObject.toJSONString(buyerBargainRecordList);
				buyerBargainRecordResList = JSONObject.parseArray(str,
						BuyerBargainRecordResDTO.class);

			}
		}

		if (promotionBargainInfo != null) {
			String str = JSONObject.toJSONString(promotionBargainInfo);
			LOGGER.info("before convert:" + str);
			promotionBargainInfoResDTO = JSONObject.parseObject(str,
					PromotionBargainInfoResDTO.class);
			LOGGER.info("after convert:"
					+ JSON.toJSONString(promotionBargainInfoResDTO));
			promotionBargainInfoResDTO
					.setBuyerBargainRecordList(buyerBargainRecordResList);
			if (!StringUtils.isEmpty(buyerBargainLaunch.getBuyerCode())) {// 曾经发起过砍价
				if (promotionBargainInfo1 != null) {
					if (1 == promotionBargainInfo1.getIsBargainOver()) {// 该用户发起的砍价已经砍完了
						promotionBargainInfoResDTO.setIsMyBargainOver("true");
					}
				}

			}
		}
		return promotionBargainInfoResDTO;
	}

	public ExecuteResult<PromotionExtendInfoDTO> addPromotionBargainInfo(
			PromotionExtendInfoDTO promotionExtendInfoDTO)
			throws PromotionCenterBusinessException {
		LOGGER.info(
				"MessageId{}:调用PromotionBargainInfoServiceImpl.addPromotionBargainInfo（）方法开始,入参{}",
				JSON.toJSONString(promotionExtendInfoDTO));
		ExecuteResult<PromotionExtendInfoDTO> result = new ExecuteResult<PromotionExtendInfoDTO>();
		try {
			if (null != promotionExtendInfoDTO.getPromotionAccumulatyList()
					&& !promotionExtendInfoDTO.getPromotionAccumulatyList()
							.isEmpty()) {
				String promotionType = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_TYPE,
						DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN);
				String promotionProviderType = dictionary
						.getValueByCode(
								DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
								DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_MEMBER_SHOP);
				promotionExtendInfoDTO.setPromotionType(promotionType);
				promotionExtendInfoDTO
						.setPromotionProviderType(promotionProviderType);
				// 输入DTO的验证
				ValidateResult validateResult = ValidationUtils
						.validateEntity(promotionExtendInfoDTO);
				if (validateResult.isHasErrors()) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PARAMETER_ERROR.getCode(),
							validateResult.getErrorMsg());
				}
				PromotionBargainInfoResDTO firstBargainDTO = (PromotionBargainInfoResDTO) promotionExtendInfoDTO
						.getPromotionAccumulatyList().get(0);

				for (PromotionAccumulatyDTO accumulatyDTO : promotionExtendInfoDTO
						.getPromotionAccumulatyList()) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
					bagainInfoDTO.setGoodsCostPrice(CalculateUtils
							.setScale(bagainInfoDTO.getGoodsCostPrice()));
					bagainInfoDTO.setGoodsFloorPrice(CalculateUtils
							.setScale(bagainInfoDTO.getGoodsFloorPrice()));
					bagainInfoDTO.setEffectiveTime(promotionExtendInfoDTO
							.getEffectiveTime());
					bagainInfoDTO.setInvalidTime(promotionExtendInfoDTO
							.getInvalidTime());
					bagainInfoDTO.setTemplateFlag(promotionExtendInfoDTO
							.getTemplateFlag());
					bagainInfoDTO.setPromotionName(promotionExtendInfoDTO
							.getPromotionName());
					bagainInfoDTO.setPromotionDescribe(promotionExtendInfoDTO
							.getPromotionDescribe());
					bagainInfoDTO.setTotalPartakeTimes(promotionExtendInfoDTO
							.getTotalPartakeTimes());
					bagainInfoDTO.setContactAddress(promotionExtendInfoDTO
							.getContactAddress());
					bagainInfoDTO.setContactName(promotionExtendInfoDTO
							.getContactName());
					bagainInfoDTO.setContactTelephone(promotionExtendInfoDTO
							.getContactTelephone());
					bagainInfoDTO.setOfflineStartTime(promotionExtendInfoDTO
							.getOfflineStartTime());
					bagainInfoDTO.setOfflineEndTime(promotionExtendInfoDTO
							.getOfflineEndTime());
					bagainInfoDTO
							.setPromotionProviderSellerCode(promotionExtendInfoDTO
									.getPromotionProviderSellerCode());
				}

				// 判断时间段内可有活动上架
				Integer isUpPromotionFlag = promotionInfoDAO
						.queryUpPromotionBargainCount(promotionExtendInfoDTO
								.getPromotionProviderSellerCode(),
								promotionExtendInfoDTO.getEffectiveTime(),
								promotionExtendInfoDTO.getInvalidTime(), null);
				if (null != isUpPromotionFlag
						&& isUpPromotionFlag.intValue() > 0) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
							"该时间段内已有活动进行");
				}
				promotionExtendInfoDTO.setShowStatus(dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
				if (null != promotionExtendInfoDTO
						&& "1".equals(firstBargainDTO.getUpFlag())) {
					promotionExtendInfoDTO.setHasUpFlag(0);
				} else {
					promotionExtendInfoDTO.setHasUpFlag(1);
				}

				PromotionExtendInfoDTO insertResult = baseService
						.insertPromotionInfo(promotionExtendInfoDTO);
				for (PromotionAccumulatyDTO accumulatyDTO : promotionExtendInfoDTO
						.getPromotionAccumulatyList()) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
					promotionBargainInfoDAO.add(bagainInfoDTO);
				}

				PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
				historyDTO.setPromotionId(promotionExtendInfoDTO
						.getPromotionId());
				historyDTO.setPromotionStatus(promotionExtendInfoDTO
						.getStatus());
				historyDTO.setPromotionStatusText(dictionary.getNameByValue(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						promotionExtendInfoDTO.getStatus()));
				historyDTO.setCreateId(promotionExtendInfoDTO.getCreateId());
				historyDTO
						.setCreateName(promotionExtendInfoDTO.getCreateName());
				promotionStatusHistoryDAO.add(historyDTO);

				PromotionSloganDTO sloganDTO = new PromotionSloganDTO();
				sloganDTO.setPromotionId(promotionExtendInfoDTO
						.getPromotionId());
				sloganDTO.setPromotionSlogan(firstBargainDTO
						.getPromotionSlogan());
				sloganDTO.setCreateId(promotionExtendInfoDTO.getCreateId());
				sloganDTO.setCreateName(promotionExtendInfoDTO.getCreateName());
				promotionSloganDAO.add(sloganDTO);

				// 保存到redis
				List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
				for (PromotionAccumulatyDTO accumulatyDTO : insertResult
						.getPromotionAccumulatyList()) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
					promotionBargainInfoList.add(bagainInfoDTO);
				}
				promotionBargainRedisHandle.addBargainInfo2Redis(
						promotionBargainInfoList, false);
				PromotionInfoDTO bargainDTO = new PromotionInfoDTO();
				bargainDTO.setPromotionId(promotionExtendInfoDTO
						.getPromotionId());
				String upFlag = promotionBargainInfoList.get(0).getUpFlag();
				if ("1".equals(upFlag)) {
					bargainDTO
							.setShowStatus(dictionary
									.getValueByCode(
											DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
											DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
				} else {
					bargainDTO.setShowStatus(dictionary.getValueByCode(
							DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
							DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
				}
				promotionBargainRedisHandle
						.saveBargainValidStatus2Redis(bargainDTO);
				result.setResult(insertResult);
			}
		} catch (PromotionCenterBusinessException pbs) {
			result.setCode(pbs.getCode());
			result.setErrorMessage(pbs.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(e.toString());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO)
			throws PromotionCenterBusinessException {
		ExecuteResult<String> result = new ExecuteResult<String>();
		PromotionInfoDTO promotionInfo = null;
		PromotionAccumulatyDTO accumulaty = new PromotionAccumulatyDTO();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(validDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			// 根据活动ID获取活动信息
			promotionInfo = promotionInfoDAO.queryById(validDTO
					.getPromotionId());
			if (promotionInfo == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
						"该促销活动不存在");
			}
			// 活动已删除
			if (dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE).equals(
					promotionInfo.getStatus())) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
						"该促销活动已被删除");
			}
			List<BuyerLaunchBargainInfoDMO> buyerLaunchList = buyerLaunchBargainInfoDAO
					.getBuyerLaunchBargainInfoByPromotionId(
							validDTO.getPromotionId(), null);
			if (null != buyerLaunchList && !buyerLaunchList.isEmpty()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_SOMEONE_INVOLVED.getCode(),
						"存在砍价记录不能删除");
			}
			promotionInfo.setStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
			promotionInfo.setModifyId(validDTO.getOperatorId());
			promotionInfo.setModifyName(validDTO.getOperatorName());
			accumulaty.setPromoionInfo(promotionInfo);
			promotionAccumulatyDAO.delete(accumulaty);
			promotionInfoDAO.updatePromotionStatusById(promotionInfo);
			PromotionSloganDTO slogan = new PromotionSloganDTO();
			slogan.setPromotionId(validDTO.getPromotionId());
			promotionSloganDAO.delete(slogan);
			PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
			historyDTO.setPromotionId(validDTO.getPromotionId());
			historyDTO.setPromotionStatus(dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
			historyDTO.setPromotionStatusText(dictionary.getNameByValue(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					historyDTO.getPromotionStatus()));
			historyDTO.setCreateId(validDTO.getOperatorId());
			historyDTO.setCreateName(validDTO.getOperatorName());
			promotionStatusHistoryDAO.add(historyDTO);
			promotionBargainRedisHandle.deleteRedisBargainInfo(validDTO
					.getPromotionId());
			result.setResult("处理成功");
		} catch (PromotionCenterBusinessException mcbe) {
			result.setCode(mcbe.getCode());
			result.setErrorMessage(mcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(
			PromotionBargainInfoResDTO dto)
			throws PromotionCenterBusinessException {
		ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
		List<PromotionBargainInfoResDTO> datagrid = null;
		try {
			datagrid = promotionBargainRedisHandle.getRedisBargainInfoList(dto);
			if(null != datagrid && !datagrid.isEmpty()){
				PromotionInfoDTO promotionInfoDTO = promotionInfoDAO.queryById(datagrid.get(0).getPromotionId());
				datagrid.get(0).setHasUpFlag(promotionInfoDTO.getHasUpFlag());
			}
			result.setResult(datagrid);
		} catch (PromotionCenterBusinessException pbe) {
			result.setCode(pbe.getCode());
			result.setErrorMessage(pbe.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionBargainInfoService.getPromotionBargainInfoList出现异常{}",
					JSON.toJSONString(dto), e.toString());
		}
		return result;
	}

	public ExecuteResult<PromotionExtendInfoDTO> updateBargainInfo(
			PromotionExtendInfoDTO promotionExtendInfoDTO)
			throws PromotionCenterBusinessException {
		LOGGER.info(
				"MessageId{}:调用promotionBargainInfoDAO.updateBargainInfo（）方法开始,入参{}",
				JSON.toJSONString(promotionExtendInfoDTO));
		ExecuteResult<PromotionExtendInfoDTO> result = new ExecuteResult<PromotionExtendInfoDTO>();
		String promotionId = promotionExtendInfoDTO.getPromotionId();
		try {
			if (null != promotionExtendInfoDTO.getPromotionAccumulatyList()
					&& !promotionExtendInfoDTO.getPromotionAccumulatyList()
							.isEmpty()) {
				String promotionType = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_TYPE,
						DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN);
				String promotionProviderType = dictionary
						.getValueByCode(
								DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
								DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_MEMBER_SHOP);
				promotionExtendInfoDTO.setPromotionType(promotionType);
				promotionExtendInfoDTO
						.setPromotionProviderType(promotionProviderType);
				// 输入DTO的验证
				ValidateResult validateResult = ValidationUtils
						.validateEntity(promotionExtendInfoDTO);
				if (validateResult.isHasErrors()) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PARAMETER_ERROR.getCode(),
							validateResult.getErrorMsg());
				}

				for (PromotionAccumulatyDTO accumulatyDTO : promotionExtendInfoDTO
						.getPromotionAccumulatyList()) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
					bagainInfoDTO.setPromotionId(promotionExtendInfoDTO
							.getPromotionId());
					bagainInfoDTO.setGoodsCostPrice(CalculateUtils
							.setScale(bagainInfoDTO.getGoodsCostPrice()));
					bagainInfoDTO.setGoodsFloorPrice(CalculateUtils
							.setScale(bagainInfoDTO.getGoodsFloorPrice()));
					bagainInfoDTO.setEffectiveTime(promotionExtendInfoDTO
							.getEffectiveTime());
					bagainInfoDTO.setInvalidTime(promotionExtendInfoDTO
							.getInvalidTime());
					bagainInfoDTO.setTemplateFlag(promotionExtendInfoDTO
							.getTemplateFlag());
					bagainInfoDTO.setPromotionName(promotionExtendInfoDTO
							.getPromotionName());
					bagainInfoDTO.setPromotionDescribe(promotionExtendInfoDTO
							.getPromotionDescribe());
					bagainInfoDTO.setTotalPartakeTimes(promotionExtendInfoDTO
							.getTotalPartakeTimes());
					bagainInfoDTO.setContactAddress(promotionExtendInfoDTO
							.getContactAddress());
					bagainInfoDTO.setContactName(promotionExtendInfoDTO
							.getContactName());
					bagainInfoDTO.setContactTelephone(promotionExtendInfoDTO
							.getContactTelephone());
					bagainInfoDTO.setOfflineStartTime(promotionExtendInfoDTO
							.getOfflineStartTime());
					bagainInfoDTO.setOfflineEndTime(promotionExtendInfoDTO
							.getOfflineEndTime());
					bagainInfoDTO
							.setPromotionProviderSellerCode(promotionExtendInfoDTO
									.getPromotionProviderSellerCode());
				}
				if (StringUtils.isEmpty(promotionId)) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PARAMETER_ERROR.getCode(),
							"修改砍价活动ID不能为空");
				}
				// 根据活动ID获取活动信息
				PromotionInfoDTO promotionInfoDTO = promotionInfoDAO
						.queryById(promotionId);
				if (promotionInfoDTO == null) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
							"砍价活动不存在");
				}
				// 不能修改已结束的活动
				if ((new Date()).after(promotionInfoDTO.getInvalidTime())) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_STATUS_NOT_CORRECT
									.getCode(),
							"砍价活动:" + promotionId + " 在已结束状态时不能进行修改");
				}
				// 判断时间段内可有活动上架
				Integer isUpPromotionFlag = promotionInfoDAO
						.queryUpPromotionBargainCount(promotionInfoDTO
								.getPromotionProviderSellerCode(),
								promotionExtendInfoDTO.getEffectiveTime(),
								promotionExtendInfoDTO.getInvalidTime(),
								promotionId);
				if (isUpPromotionFlag.intValue() > 0) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
							"该时间段内已有活动进行");
				}

				// 新增商品index
				List<Integer> addIndexList = new ArrayList<Integer>();
				for (int i = 0; i < promotionExtendInfoDTO
						.getPromotionAccumulatyList().size(); i++) {
					PromotionAccumulatyDTO accumulatyDTO = (PromotionBargainInfoResDTO) promotionExtendInfoDTO
							.getPromotionAccumulatyList().get(i);
					if (StringUtils.isEmpty(accumulatyDTO.getLevelCode())
							|| "0".equals(accumulatyDTO.getLevelCode())) {
						addIndexList.add(i);
					}
				}
				promotionExtendInfoDTO.setShowStatus(promotionInfoDTO
						.getShowStatus());
				promotionExtendInfoDTO.setStatus(promotionInfoDTO.getStatus());
				PromotionExtendInfoDTO updateResult = baseService
						.updatePromotionInfo(promotionExtendInfoDTO);
				for (int i = 0; i < promotionExtendInfoDTO
						.getPromotionAccumulatyList().size(); i++) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) updateResult
							.getPromotionAccumulatyList().get(i);
					if (addIndexList.contains(i)) {
						bagainInfoDTO.setCreateId(promotionExtendInfoDTO
								.getModifyId());
						bagainInfoDTO.setCreateName(promotionExtendInfoDTO
								.getModifyName());
						promotionBargainInfoDAO.add(bagainInfoDTO);
					} else {
						bagainInfoDTO.setModifyId(promotionExtendInfoDTO
								.getModifyId());
						bagainInfoDTO.setModifyName(promotionExtendInfoDTO
								.getModifyName());
						promotionBargainInfoDAO.update(bagainInfoDTO);
					}
				}

				// 更新处理不用修改状态日志
//				PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
//				historyDTO.setPromotionId(promotionExtendInfoDTO
//						.getPromotionId());
//				historyDTO.setPromotionStatus(promotionExtendInfoDTO
//						.getShowStatus());
//				historyDTO.setPromotionStatusText("修改砍价活动信息");
//				promotionStatusHistoryDAO.update(historyDTO);
				List<PromotionBargainInfoResDTO> promotionBargainInfoList = new ArrayList<PromotionBargainInfoResDTO>();
				for (PromotionAccumulatyDTO accumulatyDTO : updateResult
						.getPromotionAccumulatyList()) {
					PromotionBargainInfoResDTO bagainInfoDTO = (PromotionBargainInfoResDTO) accumulatyDTO;
					promotionBargainInfoList.add(bagainInfoDTO);
				}
				// 写入reids操作
				if (dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_NO_START).equals(
						promotionInfoDTO.getStatus())) {
					promotionBargainRedisHandle.addBargainInfo2Redis(
							promotionBargainInfoList, false);
				} else {
					promotionBargainRedisHandle.addBargainInfo2Redis(
							promotionBargainInfoList, true);
				}
				result.setResult(updateResult);
			}
		} catch (PromotionCenterBusinessException pbe) {
			result.setCode(pbe.getCode());
			result.setErrorMessage(pbe.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<PromotionInfoDTO> upDownShelvesPromotionInfo(
			PromotionValidDTO dto) throws PromotionCenterBusinessException {
		ExecuteResult<PromotionInfoDTO> result = new ExecuteResult<PromotionInfoDTO>();
		PromotionInfoDTO promotionInfoDTO = null;
		PromotionInfoDTO promotionInfoRedis = null;
		String statusUp = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
		String statusDown = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
		String statusCurrent = dto.getShowStatus();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(dto);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			if (StringUtils.isEmpty(statusCurrent)) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_STATUS_NOT_CORRECT.getCode(),
						"砍价活动状态不正确");
			}
			// 根据活动ID获取活动信息
			promotionInfoDTO = promotionInfoDAO.queryById(dto.getPromotionId());
			if (null == promotionInfoDTO) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "砍价活动不存在");
			}
			if (null == promotionInfoDTO.getDealFlag()
					|| promotionInfoDTO.getDealFlag().intValue() == 1) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
						"砍价活动已被删除");
			}
			if (statusCurrent.equals(statusUp)) {
				// 上架操作
				if (promotionInfoDTO.getShowStatus().equals(statusUp)) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_STATUS_NOT_CORRECT
									.getCode(),
							"砍价活动已上架，不需要重复上架");
				}
				if (!(new Date()).before(promotionInfoDTO.getInvalidTime())) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.BARGAIN_NOT_VALID.getCode(),
							"砍价活动已过有效期");
				}
			} else if (statusUp.equals(statusDown)) {
				// 下架操作
				if (promotionInfoDTO.getShowStatus().equals(statusDown)) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_STATUS_NOT_CORRECT
									.getCode(),
							"砍价活动已下架，不需要重复下架");
				}
				List<BuyerLaunchBargainInfoDMO> buyerLaunchList = buyerLaunchBargainInfoDAO
						.getBuyerLaunchBargainInfoByPromotionId(
								promotionInfoDTO.getPromotionId(), null);
				if (null != buyerLaunchList && !buyerLaunchList.isEmpty()) {
					throw new PromotionCenterBusinessException(
							ResultCodeEnum.PROMOTION_SOMEONE_INVOLVED.getCode(),
							"存在砍价记录不能删除");
				}
			}
			// 活动状态转换
			promotionInfoDTO.setShowStatus(statusCurrent);
			String convertStatus = setPromotionStatusInfo(promotionInfoDTO);
			dto.setStatus(convertStatus);
			promotionInfoDAO.upDownShelvesBargainInfo(dto);
			promotionInfoRedis = new PromotionInfoDTO();
			if (StringUtils.isNotEmpty(dto.getTemlateFlag())) {
				PromotionExtendInfoDTO extendDTO = new PromotionExtendInfoDTO();
				extendDTO.setPromotionId(dto.getPromotionId());
				extendDTO.setModifyId(dto.getOperatorId());
				extendDTO.setModifyName(dto.getOperatorName());
				extendDTO
						.setTemplateFlag(Integer.parseInt(dto.getTemlateFlag()));
				promotionInfoExtendDAO.update(extendDTO);
				PromotionBargainInfoResDTO dtoRedis = new PromotionBargainInfoResDTO();
				dtoRedis.setPromotionId(dto.getPromotionId());
				dtoRedis.setPreviewFlag("1");
				List<PromotionBargainInfoResDTO> promotionBargainList = promotionBargainRedisHandle
						.getRedisBargainInfoList(dtoRedis);
				if (null != promotionBargainList
						&& !promotionBargainList.isEmpty()) {
					for (PromotionBargainInfoResDTO res : promotionBargainList) {
						res.setTemplateFlag(Integer.parseInt(dto
								.getTemlateFlag()));
					}
					promotionBargainRedisHandle
							.addBargainInfo3Redis(promotionBargainList);
					PromotionInfoDTO bargainDTO = new PromotionInfoDTO();
					bargainDTO.setPromotionId(dto.getPromotionId());
					bargainDTO.setShowStatus(statusCurrent);
					promotionBargainRedisHandle
							.saveBargainValidStatus2Redis(bargainDTO);
				}
			}
		} catch (PromotionCenterBusinessException pbe) {
			result.setCode(pbe.getCode());
			result.setErrorMessage(pbe.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(
			PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page)
			throws PromotionCenterBusinessException {
		LOGGER.info(
				"MessageId{}:调用promotionBargainInfoDAO.queryPromotionInfoListBySellerCode（）方法开始,入参{}",
				JSON.toJSONString(reqDTO));
		DataGrid<PromotonInfoResDTO> dataGrid = new DataGrid<PromotonInfoResDTO>();
		ExecuteResult<DataGrid<PromotonInfoResDTO>> result = new ExecuteResult<DataGrid<PromotonInfoResDTO>>();
		List<PromotonInfoResDTO> resList = new ArrayList<PromotonInfoResDTO>();
		try {
			List<PromotionInfoDTO> promotionInfoList = promotionInfoDAO
					.queryPromotionInfoListBySellerCode(reqDTO, page);
			Long promotionInfoCount = promotionInfoDAO
					.queryPromotionInfoCountBySellerCode(reqDTO);
			if (null != promotionInfoList && !promotionInfoList.isEmpty()) {
				for (PromotionInfoDTO promotionInfo : promotionInfoList) {
					PromotonInfoResDTO resDTO = new PromotonInfoResDTO();
					resDTO.setPromotionId(promotionInfo.getPromotionId());
					resDTO.setPromotionName(promotionInfo.getPromotionName());
					resDTO.setEffectiveTime(promotionInfo.getEffectiveTime());
					resDTO.setInvalidTime(promotionInfo.getInvalidTime());
					resDTO.setStatus(promotionInfo.getStatus());
					// 已被砍商品数
					int bargainCount = buyerLaunchBargainInfoDAO
							.queryBuyerLaunchBargainInfoCount(promotionInfo
									.getPromotionId());
					resDTO.setBargainType(bargainCount);
					// 未被砍商品数量
					int noBargainCount = 0;
					List<PromotionAccumulatyDTO> accumuList = promotionAccumulatyDAO
							.queryAccumulatyListByPromotionId(
									promotionInfo.getPromotionId(), null);
					if (null != accumuList && accumuList.size() > bargainCount) {
						noBargainCount = accumuList.size() - bargainCount;
					}
					resDTO.setNoBargainItemQTY(noBargainCount);
					// 发起砍价人数
					Integer launchQTY = buyerBargainRecordDAO
							.queryPromotionBargainFilterJoinQTY(resDTO
									.getPromotionId());
					resDTO.setLaunchBargainQTY(launchQTY == null ? 0
							: launchQTY.intValue());
					resDTO.setHasUpFlag(promotionInfo.getHasUpFlag() == null ? 0
							: promotionInfo.getHasUpFlag().intValue());
					resDTO.setShowStatus(promotionInfo.getShowStatus());
					resList.add(resDTO);
				}
			}
			dataGrid.setRows(resList);
			dataGrid.setTotal(promotionInfoCount);
			result.setResult(dataGrid);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(
			String promotionId, Pager<String> page) {
		LOGGER.info(
				"MessageId{}:调用promotionBargainInfoDAO.queryPromotionBargainByPromotionId（）方法开始,入参{}",
				promotionId);
		DataGrid<PromotionBargainOverviewResDTO> dataGrid = new DataGrid<PromotionBargainOverviewResDTO>();
		ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> result = new ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>>();
		List<PromotionBargainOverviewResDTO> resList = new ArrayList<PromotionBargainOverviewResDTO>();
		try {
			List<PromotionBargainInfoDMO> bargainList = promotionBargainInfoDAO
					.queryPromotionBargainByPromotionId(promotionId, page);
			Long bargainCount = promotionBargainInfoDAO
					.queryPromotionBargainCountByPromotionId(promotionId);
			if (null != bargainList && !bargainList.isEmpty()) {
				for (PromotionBargainInfoDMO dmo : bargainList) {
					PromotionBargainOverviewResDTO resDTO = new PromotionBargainOverviewResDTO();
					resDTO.setPromotionId(dmo.getPromotionId());
					resDTO.setGoodsName(dmo.getGoodsName());
					resDTO.setGoodsCostPrice(dmo.getGoodsCostPrice() == null ? BigDecimal.ZERO
							: dmo.getGoodsCostPrice());
					resDTO.setGoodsFloorPrice(dmo.getGoodsFloorPrice() == null ? BigDecimal.ZERO
							: dmo.getGoodsFloorPrice());
					// 参砍数量
					resDTO.setGoodsNum(dmo.getGoodsNum() == null ? 0 : dmo
							.getGoodsNum());
					// 已发起砍价数量
					BuyerBargainLaunchReqDTO LaunchTimeDTO = new BuyerBargainLaunchReqDTO();
					LaunchTimeDTO.setLevelCode(dmo.getLevelCode());
					LaunchTimeDTO.setPromotionId(dmo.getPromotionId());
					List<BuyerLaunchBargainInfoDMO> launchList = buyerLaunchBargainInfoDAO
							.queryLaunchBargainInfoList(LaunchTimeDTO, null);
					resDTO.setLaunchTimes(launchList == null ? 0 : launchList
							.size());
					// 已砍完数量
					BuyerBargainLaunchReqDTO overTimeDTO = new BuyerBargainLaunchReqDTO();
					overTimeDTO.setLevelCode(dmo.getLevelCode());
					overTimeDTO.setPromotionId(dmo.getPromotionId());
					overTimeDTO.setIsBargainOver(1);
					List<BuyerLaunchBargainInfoDMO> overList = buyerLaunchBargainInfoDAO
							.queryLaunchBargainInfoList(overTimeDTO, null);
					resDTO.setOverTimes(overList == null ? 0 : overList.size());
					// 剩余商品数量
					if (dmo.getGoodsNum().intValue() == 0
							|| dmo.getGoodsNum() < resDTO.getOverTimes()) {
						resDTO.setSurplusTimes(0);
					} else {
						resDTO.setSurplusTimes(dmo.getGoodsNum()
								- resDTO.getOverTimes());
					}
					int goodsBuysers = this.buyerLaunchBargainInfoDAO.querygoodsBuysersCount(dmo.getPromotionId(), dmo.getBargainCodeD());
					resDTO.setGoodsBuysers(goodsBuysers);
					resList.add(resDTO);
				}
			}
			dataGrid.setTotal(bargainCount);
			dataGrid.setRows(resList);
			result.setResult(dataGrid);
			LOGGER.info(
					"MessageId{}:调用promotionBargainInfoDAO.queryPromotionBargainOverview（）方法开始,入参{}",
					JSON.toJSONString(result));
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 根据促销活动的有效期间设定促销活动状态
	 *
	 * @param promotionInfo
	 * @return
	 */
	public String setPromotionStatusInfo(PromotionInfoDTO promotionInfo) {
		Date nowDt = new Date();
		String status = promotionInfo.getStatus();
		String showStatus = promotionInfo.getShowStatus();
		if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
				DictionaryConst.OPT_PROMOTION_STATUS_DELETE).equals(status)) {
			return status;
		}
		if (dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(
				showStatus)
				|| dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS)
						.equals(showStatus)) {
			if (nowDt.before(promotionInfo.getEffectiveTime())) {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
			} else if (!nowDt.before(promotionInfo.getEffectiveTime())
					&& !nowDt.after(promotionInfo.getInvalidTime())) {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_START);
			} else {
				status = dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_STATUS,
						DictionaryConst.OPT_PROMOTION_STATUS_END);
			}
		} else if (StringUtils.isEmpty(status)) {
			status = dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_STATUS,
					DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
		}
		promotionInfo.setStatus(status);
		return status;
	}

	@Override
	public ExecuteResult<List<PromotionInfoDTO>> queryPromotionInfoEntry(
			PromotionInfoReqDTO dto) throws PromotionCenterBusinessException {
		ExecuteResult<List<PromotionInfoDTO>> result = new ExecuteResult<List<PromotionInfoDTO>>();
		List<PromotionInfoDTO> resultList = new ArrayList<PromotionInfoDTO>();
		Date currentDate = new Date();
		try {
			List<PromotionInfoDTO> promotionInfoList = promotionInfoDAO
					.queryPromotionBargainEntry(dto
							.getPromotionProviderSellerCode());
			PromotionInfoDTO resultDTO = null;
			if (null != promotionInfoList && !promotionInfoList.isEmpty()) {
				for (int i = 0; i < promotionInfoList.size(); i++) {
					if (currentDate.before(promotionInfoList.get(i)
							.getInvalidTime())) {
						resultDTO = promotionInfoList.get(i);
						break;
					}
				}
				if (null == resultDTO) {
					resultDTO = promotionInfoList
							.get(promotionInfoList.size() - 1);
				}
				resultList.add(resultDTO);
			}
			result.setResult(resultList);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}

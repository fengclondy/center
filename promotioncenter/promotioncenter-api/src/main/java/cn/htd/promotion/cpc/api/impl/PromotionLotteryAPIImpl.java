package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.PromotionLotteryAPI;
import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryResultReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryWinningReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.ScratchCardActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.ScratchCardDrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateScratchCardReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.GenricResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.ParticipateActivitySellerInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateScratchCardResDTO;
import cn.htd.promotion.cpc.dto.response.WinningRecordResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("promotionLotteryAPI")
public class PromotionLotteryAPIImpl implements PromotionLotteryAPI {

	private static final Logger logger = LoggerFactory
			.getLogger(PromotionLotteryAPIImpl.class);

	@Resource
	private PromotionInfoService promotionInfoService;

	@Resource
	private LuckDrawService luckDrawService;

	@Resource
	private PromotionLotteryService promotionLotteryService;

	@Override
	public ExecuteResult<DataGrid<PromotionInfoDTO>> getPromotionGashaponByInfo(
			PromotionInfoReqDTO promotionInfoReqDTO,
			Pager<PromotionInfoReqDTO> pager) {
		ExecuteResult<DataGrid<PromotionInfoDTO>> result = new ExecuteResult<DataGrid<PromotionInfoDTO>>();
		try {
			DataGrid<PromotionInfoDTO> promotionInfoList = promotionInfoService
					.getPromotionGashaponByInfo(promotionInfoReqDTO, pager);
			result.setResult(promotionInfoList);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			if (promotionInfoList.getSize() == 0 || promotionInfoList == null) {
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
			}
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));

		}
		return result;
	}

	@Override
	public String validateLuckDrawPermission(String validateLuckDrawReqDTOJson) {
		ValidateLuckDrawResDTO result = new ValidateLuckDrawResDTO();
		ValidateLuckDrawReqDTO requestDTO = new ValidateLuckDrawReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON
					.parseObject(validateLuckDrawReqDTOJson);
			requestDTO = JSONObject.toJavaObject(jsonObject,
					ValidateLuckDrawReqDTO.class);
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseMsg(validateResult.getReponseMsg());
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.validateLuckDrawPermission(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}

		return JSON.toJSONString(result);
	}

	@Override
	public String lotteryActivityPage(String lotteryActivityPageReqDTOJson) {
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		LotteryActivityPageReqDTO requestDTO = new LotteryActivityPageReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON
					.parseObject(lotteryActivityPageReqDTOJson);
			requestDTO = JSONObject.toJavaObject(jsonObject,
					LotteryActivityPageReqDTO.class);
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.lotteryActivityPage(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String lotteryActivityRulePage(
			String lotteryActivityRulePageReqDTOJson) {
		LotteryActivityRulePageResDTO result = new LotteryActivityRulePageResDTO();
		LotteryActivityRulePageReqDTO requestDTO = new LotteryActivityRulePageReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON
					.parseObject(lotteryActivityRulePageReqDTOJson);
			requestDTO = JSONObject.toJavaObject(jsonObject,
					LotteryActivityRulePageReqDTO.class);
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.lotteryActivityRulePage(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String queryWinningRecord(String winningRecordReqDTOJson) {
		WinningRecordResDTO result = new WinningRecordResDTO();
		WinningRecordReqDTO requestDTO = new WinningRecordReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON.parseObject(winningRecordReqDTOJson);
			requestDTO = JSONObject.toJavaObject(jsonObject,
					WinningRecordReqDTO.class);
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			WinningRecordResDMO resultDMO = luckDrawService
					.queryWinningRecord(requestDTO);
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(resultDMO);
			result = JSONObject
					.toJavaObject(jsonObj, WinningRecordResDTO.class);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String shareLinkHandle(String shareLinkHandleReqDTOJson) {
		ShareLinkHandleResDTO result = new ShareLinkHandleResDTO();
		ShareLinkHandleReqDTO requestDTO = new ShareLinkHandleReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON.parseObject(shareLinkHandleReqDTOJson);
			requestDTO = JSONObject.toJavaObject(jsonObject,
					ShareLinkHandleReqDTO.class);
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.shareLinkHandle(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 开始抽奖处理
	 * 
	 * @param drawLotteryParam
	 * @return
	 */
	@Override
	public String beginDrawLottery(String drawLotteryParam) {
		DrawLotteryReqDTO requestDTO = null;
		DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
		try {
			requestDTO = JSON.parseObject(drawLotteryParam,
					DrawLotteryReqDTO.class);
			if (requestDTO == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.ERROR.getCode(),
						ResultCodeEnum.ERROR.getMsg());
			}
			responseDTO.setMessageId(requestDTO.getMessageId());
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(requestDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			responseDTO = promotionLotteryService.beginDrawLottery(requestDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			responseDTO.setResponseCode(bcbe.getCode());
			responseDTO.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			responseDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			responseDTO.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(responseDTO);
	}

	/**
	 * 开始抽奖处理4刮刮卡
	 * 
	 * @param drawLotteryParam
	 * @return
	 */
	@Override
	public String beginDrawLotteryScratchCard(String drawLotteryParam) {
		ScratchCardDrawLotteryReqDTO requestDTO = null;
		DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
		try {
			requestDTO = JSON.parseObject(drawLotteryParam,
					ScratchCardDrawLotteryReqDTO.class);
			if (requestDTO == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.ERROR.getCode(),
						ResultCodeEnum.ERROR.getMsg());
			}
			responseDTO.setMessageId(requestDTO.getMessageId());
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(requestDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			responseDTO = promotionLotteryService.beginDrawLotteryScratchCard(requestDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			responseDTO.setResponseCode(bcbe.getCode());
			responseDTO.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			responseDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			responseDTO.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(responseDTO);
	}
	
	/**
	 * 查询抽奖结果
	 * 
	 * @param lotteryWinningParam
	 * @return
	 */
	@Override
	public String getDrawLotteryResult(String lotteryWinningParam) {
		DrawLotteryResultReqDTO requestDTO = null;
		BuyerWinningRecordDTO responseDTO = new BuyerWinningRecordDTO();
		try {
			requestDTO = JSON.parseObject(lotteryWinningParam,
					DrawLotteryResultReqDTO.class);
			if (requestDTO == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.ERROR.getCode(),
						ResultCodeEnum.ERROR.getMsg());
			}
			responseDTO.setMessageId(requestDTO.getMessageId());
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(requestDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			responseDTO = promotionLotteryService
					.getDrawLotteryResult(requestDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			responseDTO.setResponseCode(bcbe.getCode());
			responseDTO.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			responseDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			responseDTO.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(responseDTO);
	}

	/**
	 * 保存中奖信息
	 * 
	 * @param winningInfoParam
	 * @return
	 */
	@Override
	public String saveDrawLotteryWinningInfo(String winningInfoParam) {
		DrawLotteryWinningReqDTO requestDTO = null;
		GenricResDTO responseDTO = new GenricResDTO();
		try {
			requestDTO = JSON.parseObject(winningInfoParam,
					DrawLotteryWinningReqDTO.class);
			if (requestDTO == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.ERROR.getCode(),
						ResultCodeEnum.ERROR.getMsg());
			}
			responseDTO.setMessageId(requestDTO.getMessageId());
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(requestDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			responseDTO = promotionLotteryService
					.saveDrawLotteryWinning(requestDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			responseDTO.setResponseCode(bcbe.getCode());
			responseDTO.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			responseDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			responseDTO.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(responseDTO);
	}

	@Override
	public String addDrawLotteryInfo(String promotionInfoEditReqDTO) {
		PromotionExtendInfoDTO rt = new PromotionExtendInfoDTO();
		try {
			PromotionExtendInfoDTO promotionExtendInfoDTO = JSON.parseObject(
					promotionInfoEditReqDTO, PromotionExtendInfoDTO.class);
			ArrayList templist = (ArrayList) promotionExtendInfoDTO
					.getPromotionAccumulatyList();
			List<PromotionAwardInfoDTO> templist1 = new ArrayList<PromotionAwardInfoDTO>();
			for (int j = 0; j < templist.size(); j++) {

				PromotionAwardInfoDTO promotionAwardInfoDTO = JSONObject
						.toJavaObject(((JSONObject) templist.get(j)),
								PromotionAwardInfoDTO.class);
				templist1.add(promotionAwardInfoDTO);
			}
			promotionExtendInfoDTO.setPromotionAccumulatyList(templist1);

			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(promotionExtendInfoDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			rt = luckDrawService.addDrawLotteryInfo(promotionExtendInfoDTO);
			rt.setMessageId(promotionExtendInfoDTO.getMessageId());
		} catch (PromotionCenterBusinessException bcbe) {
			rt.setResponseCode(bcbe.getCode());
			rt.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(rt);
	}

	@Override
	public String editDrawLotteryInfo(String promotionInfoEditReqDTO) {
		PromotionExtendInfoDTO rt = null;
		PromotionExtendInfoDTO rts = null;
		try {
			PromotionExtendInfoDTO promotionExtendInfoDTO = JSON.parseObject(
					promotionInfoEditReqDTO, PromotionExtendInfoDTO.class);
			ArrayList templist = (ArrayList) promotionExtendInfoDTO
					.getPromotionAccumulatyList();
			List<PromotionAwardInfoDTO> templist1 = new ArrayList<PromotionAwardInfoDTO>();
			for (int j = 0; j < templist.size(); j++) {

				PromotionAwardInfoDTO promotionAwardInfoDTO = JSONObject
						.toJavaObject(((JSONObject) templist.get(j)),
								PromotionAwardInfoDTO.class);
				templist1.add(promotionAwardInfoDTO);
			}
			promotionExtendInfoDTO.setPromotionAccumulatyList(templist1);
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(promotionExtendInfoDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			rt = luckDrawService.editDrawLotteryInfo(promotionExtendInfoDTO);
			rts.setMessageId(promotionExtendInfoDTO.getMessageId());
			rts.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (PromotionCenterBusinessException bcbe) {
			rts.setResponseCode(bcbe.getCode());
			rts.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			rts.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rts.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(rt);
	}

	/**
	 * 参与有效活动的所有卖家信息
	 */
	@Override
	public String participateActivitySellerInfo(String messageId) {
		ParticipateActivitySellerInfoDTO result = new ParticipateActivitySellerInfoDTO();
		try {
			result = luckDrawService.participateActivitySellerInfo(messageId);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String viewDrawLotteryInfo(String promotionInfoId, String messageId) {
		PromotionExtendInfoDTO rt = null;
		try {
			rt = luckDrawService.viewDrawLotteryInfo(promotionInfoId);
			rt.setMessageId(messageId);
		} catch (PromotionCenterBusinessException bcbe) {
			rt.setResponseCode(bcbe.getCode());
			rt.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			rt.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rt.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(rt);
	}

	@Override
	public void updateLotteryResultState(Map<String, Object> map) {
		luckDrawService.updateLotteryResultState(map);

	}

	@Override
	public String validateScratchCard(String validateScratchCardReqDTOJson) {
		ValidateScratchCardResDTO result = new ValidateScratchCardResDTO();
		ValidateScratchCardReqDTO requestDTO = new ValidateScratchCardReqDTO();
		String messageId = "";
		try {
			requestDTO = JSON.parseObject(validateScratchCardReqDTOJson,
					ValidateScratchCardReqDTO.class);
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.validateScratchCard(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String scratchCardActivityPage(
			String scratchCardActivityPageReqDTOJson) {
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		ScratchCardActivityPageReqDTO requestDTO = new ScratchCardActivityPageReqDTO();
		String messageId = "";
		try {
			requestDTO = JSON
					.parseObject(scratchCardActivityPageReqDTOJson,ScratchCardActivityPageReqDTO.class);
			ValidateResult validateResult = DTOValidateUtil
					.validate(requestDTO);
			if (!validateResult.isPass()) {
				result.setResponseCode(ResultCodeEnum.PARAMETER_ERROR.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.scratchCardActivityPage(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 保存红包雨信息
	 * @param winningInfoParam
	 * @return
	 */
	@Override
	public String saveRedRainWinningInfo(String winningInfoParam) {
		DrawLotteryWinningReqDTO requestDTO = null;
		GenricResDTO responseDTO = new GenricResDTO();
		try {
			requestDTO = JSON.parseObject(winningInfoParam,
					DrawLotteryWinningReqDTO.class);
			if (requestDTO == null) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.ERROR.getCode(),
						ResultCodeEnum.ERROR.getMsg());
			}
			responseDTO.setMessageId(requestDTO.getMessageId());
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils
					.validateEntity(requestDTO);
			// 有错误信息时返回错误信息
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.PARAMETER_ERROR.getCode(),
						validateResult.getErrorMsg());
			}
			responseDTO = promotionLotteryService
					.saveRedRainWinningInfo(requestDTO);
		} catch (PromotionCenterBusinessException bcbe) {
			responseDTO.setResponseCode(bcbe.getCode());
			responseDTO.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			responseDTO.setResponseCode(ResultCodeEnum.ERROR.getCode());
			responseDTO.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}
		return JSON.toJSONString(responseDTO);
	}
}

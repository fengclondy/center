package cn.htd.promotion.cpc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.PromotionLotteryAPI;
import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;
import cn.htd.promotion.cpc.dto.response.WinningRecordResDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("promotionLotteryAPI")
public class PromotionLotteryAPIImpl implements PromotionLotteryAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionLotteryAPIImpl.class);

	@Resource
	private PromotionInfoService promotionInfoService;

	@Resource
	private LuckDrawService luckDrawService;

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
				result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
			} else {
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
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
				result.setResponseCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL
						.getCode());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.validateLuckDrawPermission(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionLotteryAPIImpl.validateLuckDrawPermission出现异常 OrgId：{}",
					messageId, requestDTO.getOrgId(), w.toString());
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
				result.setResponseCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL
						.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.lotteryActivityPage(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionLotteryAPIImpl.lotteryActivityPage出现异常 requestDTO：{}",
					messageId, JSONObject.toJSONString(requestDTO),
					w.toString());
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
				result.setResponseCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL
						.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.lotteryActivityRulePage(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionLotteryAPIImpl.lotteryActivityRulePage出现异常 promotionId：{}",
					messageId, requestDTO.getPromotionId(), w.toString());
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
				result.setResponseCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL
						.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			WinningRecordResDMO resultDMO = luckDrawService.queryWinningRecord(requestDTO);
			JSONObject jsonObj = (JSONObject) JSONObject.toJSON(resultDMO);
			result = JSONObject.toJavaObject(jsonObj, WinningRecordResDTO.class);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionLotteryAPIImpl.queryWinningRecord出现异常 request：{}",
					messageId, winningRecordReqDTOJson, w.toString());
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
				result.setResponseCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL
						.getCode());
				result.setResponseMsg(validateResult.getReponseMsg());
				return JSON.toJSONString(result);
			}
			messageId = requestDTO.getMessageId();
			result = luckDrawService.shareLinkHandle(requestDTO);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法PromotionLotteryAPIImpl.shareLinkHandle出现异常 promotionId：{}",
					messageId, requestDTO.getPromotionId(), w.toString());
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
		return null;
	}

	/**
	 * 查询抽奖结果
	 *
	 * @param drawLotteryParam
	 * @return
	 */
	@Override
	public String getDrawLotteryResult(String drawLotteryParam) {
		return null;
	}
}

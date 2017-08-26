package cn.htd.promotion.cpc.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.api.LuckDrawAPI;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("luckDrawAPI")
public class LuckDrawAPIImpl implements LuckDrawAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawAPIImpl.class);

	@Resource
	private LuckDrawService luckDrawService;

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
					"MessageId:{} 调用方法LuckDrawAPIImpl.validateLuckDrawPermission出现异常 OrgId：{}",
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
					"MessageId:{} 调用方法LuckDrawAPIImpl.lotteryActivityPage出现异常 requestDTO：{}",
					messageId, JSONObject.toJSONString(requestDTO), w.toString());
		}
		return JSON.toJSONString(result);
	}

	public static void main(String[] args) {
		String s = "{\"code\":\"00000\",\"resultMessage\":\"成功\",\"orgId\":\"41444444\"}";
		JSONObject jsonObject = JSON.parseObject(s);
		ValidateLuckDrawReqDTO requestDTO = new ValidateLuckDrawReqDTO();
		requestDTO = JSONObject.toJavaObject(jsonObject,
				ValidateLuckDrawReqDTO.class);
		System.out.println(requestDTO.getOrgId());
	}

	@Override
	public String lotteryActivityRulePage(
			String lotteryActivityRulePageReqDTOJson) {
		LotteryActivityRulePageResDTO result = new LotteryActivityRulePageResDTO();
		LotteryActivityRulePageReqDTO requestDTO = new LotteryActivityRulePageReqDTO();
		String messageId = "";
		try{
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
		}catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawAPIImpl.lotteryActivityRulePage出现异常 promotionId：{}",
					messageId, requestDTO.getPromotionId(), w.toString());
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String queryWinningRecord(String winningRecordReqDTOJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String shareLinkHandle(String shareLinkHandleReqDTOJson) {
		ShareLinkHandleResDTO result = new ShareLinkHandleResDTO();
		ShareLinkHandleReqDTO requestDTO = new ShareLinkHandleReqDTO();
		String messageId = "";
		try {
			JSONObject jsonObject = JSON
					.parseObject(shareLinkHandleReqDTOJson);
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
					"MessageId:{} 调用方法LuckDrawAPIImpl.shareLinkHandle出现异常 promotionId：{}",
					messageId, requestDTO.getPromotionId(), w.toString());
		}
		return JSON.toJSONString(result);
	}
}

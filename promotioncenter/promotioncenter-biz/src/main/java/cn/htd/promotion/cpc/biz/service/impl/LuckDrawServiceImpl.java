package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

import com.alibaba.fastjson.JSONObject;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);

	@Override
	public ValidateLuckDrawResDTO validateLuckDrawPermission(
			ValidateLuckDrawReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		ValidateLuckDrawResDTO result = new ValidateLuckDrawResDTO();
		try {
			// TODO 查询redis 查到数据返回 有值
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setPromotionId("");// TODO pomotionId
			// TODO 查询redis 查到数据返回 空
			result.setResponseCode(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION
					.getCode());
			result.setResponseMsg(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION
					.getMsg());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.validateLuckDrawPermission出现异常 OrgId：{}",
					messageId, requestDTO.getOrgId(), w.toString());
		}

		return result;
	}

	@Override
	public LotteryActivityPageResDTO lotteryActivityPage(
			LotteryActivityPageReqDTO request) {
		String messageId = request.getMessageId();
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		try {
			// TODO
			result.setPromotionName("");
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getMsg());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.lotteryActivityPage出现异常 request：{}",
					messageId, JSONObject.toJSONString(request), w.toString());
		}
		return result;
	}

}

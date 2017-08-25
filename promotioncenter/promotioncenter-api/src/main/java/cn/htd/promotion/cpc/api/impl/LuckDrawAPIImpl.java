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
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;

@Service("luckDrawAPI")
public class LuckDrawAPIImpl implements LuckDrawAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawAPIImpl.class);
	
	@Resource
	private LuckDrawService luckDrawService;

	@Override
	public ExecuteResult<String> validateLuckDrawPermission(
			ValidateLuckDrawReqDTO request) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		String messageId = "";
		try {
			/* 验空2017-02-13 */
			ValidateResult validateResult = DTOValidateUtil.validate(request);
			if (!validateResult.isPass()) {
				result.setCode(ResultCodeEnum.LUCK_DRAW_PARAM_IS_NULL.getCode());
				result.setErrorMessage(validateResult.getReponseMsg());
				return result;
			}
			messageId = request.getMessageId();
			luckDrawService.validateLuckDrawPermission(request, result);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			result.setErrorMessage(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawAPIImpl.validateLuckDrawPermission出现异常 OrgId：{}",
					messageId, request.getOrgId(), w.toString());
		}

		return result;
	}

}

package cn.htd.promotion.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.promotion.api.PromotionSloganAPI;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

@Service("promotionSloganAPI")
public class PromotionSloganAPIImpl implements PromotionSloganAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionSloganAPIImpl.class);

	@Resource
	private PromotionSloganService promotionSloganService;

	@Override
	public ExecuteResult<List<PromotionSloganResDTO>> queryBargainSloganBySellerCode(
			String providerSellerCode, String messageId) {
		ExecuteResult<List<PromotionSloganResDTO>> result = new ExecuteResult<List<PromotionSloganResDTO>>();
		
		if (StringUtils.isEmpty(providerSellerCode) || StringUtils.isEmpty(messageId)) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL
					.getMsg());
			LOGGER.error(
					"MessageId:{} 调用方法promotionSloganAPIImpl.queryBargainSloganBySellerCode出现异常{}",
					messageId, providerSellerCode + ":" + messageId);
			return result;
		}
		try {
			List<PromotionSloganResDTO> promotionSloganDTOList = promotionSloganService
					.queryBargainSloganBySellerCode(providerSellerCode,
							messageId);
			if (null == promotionSloganDTOList
					|| promotionSloganDTOList.size() == 0) {
				result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
			} else {
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
				result.setResult(promotionSloganDTOList);
			}

		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法promotionSloganService.queryBargainSloganBySellerCode出现异常{}",
					messageId, providerSellerCode + ":" + messageId,
					w.toString());
		}
		return result;
	}

}

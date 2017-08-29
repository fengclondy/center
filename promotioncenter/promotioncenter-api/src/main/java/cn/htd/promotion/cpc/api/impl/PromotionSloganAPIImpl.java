package cn.htd.promotion.cpc.api.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.api.PromotionSloganAPI;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        if (!StringUtils.isEmpty(providerSellerCode) && !StringUtils.isEmpty(messageId)) {
            return promotionSloganService.queryBargainSloganBySellerCode(providerSellerCode, messageId);
        } else {
            result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
            result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
            LOGGER.error("MessageId:{} 调用方法PromotionSloganAPIImpl.queryBargainSloganBySellerCode出现错误{}",
                    messageId, providerSellerCode + ":" + messageId);
        }
        return result;
    }

}

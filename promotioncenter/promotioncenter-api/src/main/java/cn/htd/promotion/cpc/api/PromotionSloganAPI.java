package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

public interface PromotionSloganAPI {

	public ExecuteResult<List<PromotionSloganResDTO>> queryBargainSloganBySellerCode(String providerSellerCode, String messageId);

}

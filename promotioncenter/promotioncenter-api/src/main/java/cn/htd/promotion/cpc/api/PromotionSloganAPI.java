package cn.htd.promotion.cpc.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;

public interface PromotionSloganAPI {

	public ExecuteResult<List<PromotionSloganDTO>> queryBargainSloganBySellerCode(String providerSellerCode, String messageId);

}

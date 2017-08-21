package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoResDTO;

public interface promotionInfoService {

	public List<PromotionInfoResDTO> getPromotionInfoByCondition(PromotionInfoReqDTO promotionInfo);
}

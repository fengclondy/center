package cn.htd.promotion.cpc.biz.service;


import java.util.List;

import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface PromotionTimelimitedInfoService {

	/**
	 * 查询活动宣传语
	 * 
	 * @param providerSellerCode
	 *            提供方商家编码
	 * @return
	 * @throws Exception
	 */
	public List<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId,String skuCode) throws PromotionCenterBusinessException;
}

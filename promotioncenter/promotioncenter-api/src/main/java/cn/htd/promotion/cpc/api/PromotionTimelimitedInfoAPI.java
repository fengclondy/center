package cn.htd.promotion.cpc.api;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

/**
 * 秒杀活动服务
 */
public interface PromotionTimelimitedInfoAPI {

	/**
	 * 根据商品编码取得秒杀信息
	 * 
	 * @param messageId
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode);


}

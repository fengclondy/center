package cn.htd.promotion.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

public interface BuyerBargainAPI {
	
	public ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId);

}

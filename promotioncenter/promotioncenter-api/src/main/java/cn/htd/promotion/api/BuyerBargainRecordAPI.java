package cn.htd.promotion.api;

import java.util.List;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;

public interface BuyerBargainRecordAPI {
	
	public ExecuteResult<List<BuyerBargainRecordResDTO>> getBuyerBargainRecordByBargainCode(String bargainCode,String messageId);

}

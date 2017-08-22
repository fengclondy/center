package cn.htd.promotion.cpc.biz.service;

import java.util.List;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;

public interface BuyerBargainRecordService {

	public List<BuyerBargainRecordResDTO> getBuyerBargainRecordByBargainCode(String bargainCode,String messageId);
	
	public Integer insertBuyerBargainRecord(BuyerBargainRecordReqDTO BuyerBargainRecord);
	
	public Boolean getThisPersonIsBargain(String bargainCode, String bargainPersonCode, String messageId);
	
}

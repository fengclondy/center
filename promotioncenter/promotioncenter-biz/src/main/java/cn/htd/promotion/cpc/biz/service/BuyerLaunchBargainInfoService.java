package cn.htd.promotion.cpc.biz.service;

import java.util.List;

import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

public interface BuyerLaunchBargainInfoService {

	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId);
	
}

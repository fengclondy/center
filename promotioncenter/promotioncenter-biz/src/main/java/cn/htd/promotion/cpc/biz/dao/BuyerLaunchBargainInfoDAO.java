package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerLaunchBargainInfoDAO")
public interface BuyerLaunchBargainInfoDAO {
	
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode);
}

package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerLaunchBargainInfoDAO")
public interface BuyerLaunchBargainInfoDAO {
	
	public List<BuyerLaunchBargainInfoDMO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode);
}

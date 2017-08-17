package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionBargainInfoDAO")
public interface PromotionBargainInfoDAO {
	
	public PromotionBargainInfoDMO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
}

package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionBargainInfoDAO")
public interface PromotionBargainInfoDAO extends BaseDAO<PromotionBargainInfoResDTO> {
	
	public PromotionBargainInfoDMO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
}

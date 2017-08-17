package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionInfoDAO")
public interface PromotionInfoDAO {

	public List<PromotionBargainInfoDMO> getPromotionInfoByCondition(PromotionInfoReqDTO promotionInfoReqDTO);
}

package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionBargainInfoDAO")
public interface PromotionBargainInfoDAO extends BaseDAO<PromotionBargainInfoResDTO> {
	
	public PromotionBargainInfoDMO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch);
	
    public PromotionBargainInfoDMO queryPromotionBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch);

	public Integer queryPromotionBargainStockSum(@Param("promotionId") String promotionId);

	public List<PromotionBargainInfoDMO> queryPromotionBargainBySellerCode(@Param("sellerCode") String sellerCode,
			@Param("page") Pager<String> page);

	public Long queryPromotionBargainCountBySellerCode(@Param("sellerCode") String sellerCode);
}

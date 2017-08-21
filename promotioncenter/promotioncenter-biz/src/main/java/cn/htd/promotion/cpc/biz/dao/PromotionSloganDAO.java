package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionSloganDAO")
public interface PromotionSloganDAO extends BaseDAO<PromotionSloganResDTO> {

	List<PromotionSloganDMO> queryBargainSloganBySellerCode(@Param("providerSellerCode") String providerSellerCode);

	PromotionSloganResDTO queryBargainSloganByPromotionId(@Param("promotionId") String promotionId);
	
}

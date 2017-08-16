package cn.htd.promotion.cpc.biz.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionSloganDAO")
public interface PromotionSloganDAO {

	List<PromotionSloganDMO> queryBargainSloganBySellerCode(@Param("providerSellerCode") String providerSellerCode);
	
}

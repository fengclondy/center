package cn.htd.promotion.cpc.biz.dao;

import org.springframework.stereotype.Repository;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.promotionInfoExtendDAO")
public interface PromotionInfoExtendDAO  extends BaseDAO<PromotionExtendInfoDTO>{

}

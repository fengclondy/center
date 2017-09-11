package cn.htd.promotion.cpc.biz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerBargainRecordDAO")
public interface BuyerBargainRecordDAO {

	public List<BuyerBargainRecordDMO> getBuyerBargainRecordByBargainCode(String bargainCode);
	
	public Integer insertBuyerBargainRecord(BuyerBargainRecordReqDTO BuyerBargainRecord);
	
	public BuyerBargainRecordDMO getThisPersonIsBargain(Map<String,String> map);

	public Integer queryPromotionBargainJoinQTY(@Param("promotionId") String promotionId);

	public Integer queryPromotionBargainFilterJoinQTY(@Param("promotionId") String promotionId);
	
}

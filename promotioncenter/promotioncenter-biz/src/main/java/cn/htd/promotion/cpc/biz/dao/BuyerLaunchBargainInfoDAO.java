package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerLaunchBargainInfoDAO")
public interface BuyerLaunchBargainInfoDAO {

	public List<BuyerLaunchBargainInfoDMO> getBuyerLaunchBargainInfoByBuyerCode(
			String buyerCode);
	
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch);

	public List<BuyerLaunchBargainInfoDMO> getBuyerLaunchBargainInfoByPromotionId(
			@Param("promotionId") String promotionId);

	public Integer queryBuyerLaunchBargainInfoNumber(
			@Param("barfainDTO") BuyerLaunchBargainInfoResDTO barfainDTO);

	public void addBuyerLaunchBargainInfo(
			@Param("barfainDTO") BuyerLaunchBargainInfoResDTO bargainInfoDTO);

}

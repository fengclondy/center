package cn.htd.promotion.cpc.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Repository("cn.htd.promotion.cpc.biz.dao.buyerLaunchBargainInfoDAO")
public interface BuyerLaunchBargainInfoDAO {

	public List<BuyerLaunchBargainInfoDMO> getBuyerLaunchBargainInfoByBuyerCode(
			String buyerCode);
	
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch);

	public List<BuyerLaunchBargainInfoDMO> getBuyerLaunchBargainInfoByPromotionId(
			@Param("promotionId") String promotionId, @Param("buyerCode") String buyerCode);

	public Integer queryBuyerLaunchBargainInfoNumber(
			@Param("bargainDTO") BuyerLaunchBargainInfoResDTO barfainDTO);

	public void addBuyerLaunchBargainInfo(
			@Param("bargainDTO") BuyerLaunchBargainInfoResDTO bargainInfoDTO);
	
	public BuyerLaunchBargainInfoDMO getBuyerBargainLaunchInfoByBargainCode(String bargainCode);

	public List<BuyerLaunchBargainInfoDMO> queryLaunchBargainInfoList(
			@Param("bargainDTO") BuyerBargainLaunchReqDTO buyerBargainLaunch, @Param("page") Pager<BuyerBargainLaunchReqDTO> page);

	public Long queryLaunchBargainInfoCount(
			@Param("bargainDTO") BuyerBargainLaunchReqDTO buyerBargainLaunch);

	public Integer queryBuyerLaunchBargainInfoCount(@Param("promotionId") String promotionId);

}

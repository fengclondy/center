package cn.htd.promotion.cpc.biz.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Service("promotionSloganService")
public class PromotionBargainInfoServiceImpl implements PromotionBargainInfoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoServiceImpl.class);

	@Resource
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	/**
	 * 获取砍价商品详情
	 */
	@Override
	public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		PromotionBargainInfoResDTO promotionBargainInfoResDTO = new PromotionBargainInfoResDTO();
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,入参{}",buyerBargainLaunch.getMessageId(),JSON.toJSONString(buyerBargainLaunch));
		PromotionBargainInfoDMO promotionBargainInfo = promotionBargainInfoDAO.getPromotionBargainInfoDetail(buyerBargainLaunch);
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,出参{}",buyerBargainLaunch.getMessageId(),JSON.toJSONString(promotionBargainInfo));
		if(promotionBargainInfo != null){
			String str = JSONObject.toJSONString(promotionBargainInfo);
			promotionBargainInfoResDTO = JSONObject.parseObject(str,PromotionBargainInfoResDTO.class);
		}
		return promotionBargainInfoResDTO;
	}
	
	 
}

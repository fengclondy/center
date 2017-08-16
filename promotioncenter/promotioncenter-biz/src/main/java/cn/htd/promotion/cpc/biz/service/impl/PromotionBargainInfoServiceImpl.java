package cn.htd.promotion.cpc.biz.service.impl;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;

@Service("promotionSloganService")
public class PromotionBargainInfoServiceImpl implements PromotionBargainInfoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoServiceImpl.class);

	@Resource
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	 
}

package cn.htd.promotion.cpc.biz.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.service.PromotionInfoService;

@Service("promotionInfoService")
public class PromotionInfoServiceImpl implements PromotionInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionInfoServiceImpl.class);
	
	@Resource
	private PromotionInfoDAO promotionInfoDAO;

}

package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("promotionSloganService")
public class PromotionBargainInfoServiceImpl implements PromotionBargainInfoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoServiceImpl.class);

	@Resource
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	@Override
	public List<PromotionBargainInfoResDTO> queryPromotionBargainInfoByPomotionId(
			String promotionId, String messageId) throws Exception {		
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.queryPromotionBargainInfoByPromotionId（）方法开始,入参{}",messageId,promotionId+":"+messageId);
		List<PromotionBargainInfoDMO> promotionBargainInfoDMOList = promotionBargainInfoDAO.queryPromotionBargainInfoByPomotionId(promotionId);
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.queryPromotionBargainInfoByPromotionId（）方法开始,出参{}",messageId,promotionId+":"+messageId);
		List<PromotionBargainInfoResDTO> promotionBargainInfoResDTOList = null;
		if(null != promotionBargainInfoDMOList){
			promotionBargainInfoResDTOList = new ArrayList<PromotionBargainInfoResDTO>();
			String str = JSONObject.toJSONString(promotionBargainInfoDMOList);
			promotionBargainInfoResDTOList = JSONObject.parseArray(str,PromotionBargainInfoResDTO.class);
		}
	} 
}

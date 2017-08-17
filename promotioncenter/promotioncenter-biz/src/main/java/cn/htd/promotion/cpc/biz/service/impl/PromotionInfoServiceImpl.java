package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.service.promotionInfoService;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoResDTO;

@Service("promotionBargainInfoService")
public class PromotionInfoServiceImpl implements promotionInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionInfoServiceImpl.class);
	
	@Resource
	private PromotionInfoDAO promotionInfoDAO;
	/**
	 * 根据查询条件获取促销活动详情
	 */
	@Override
	public List<PromotionInfoResDTO> getPromotionInfoByCondition(PromotionInfoReqDTO promotionInfo) {
		LOGGER.info("MessageId{}:调用promotionInfoDAO.getPromotionBargainInfoDetail（）方法开始,入参{}",promotionInfo.getMessageId(),
				JSON.toJSONString(promotionInfo));
		List<PromotionBargainInfoDMO> promotionBargainInfoDMOList = promotionInfoDAO.getPromotionInfoByCondition(promotionInfo);
		List<PromotionInfoResDTO> promotionInfoResDTOList = new ArrayList<PromotionInfoResDTO>();
		if(promotionBargainInfoDMOList != null){
			String str = JSONObject.toJSONString(promotionBargainInfoDMOList);
			promotionInfoResDTOList = JSONObject.parseArray(str,PromotionInfoResDTO.class);
		}
		return null;
	}

}

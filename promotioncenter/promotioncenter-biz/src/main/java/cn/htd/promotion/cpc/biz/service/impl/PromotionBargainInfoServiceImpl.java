package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Service("promotionBargainInfoService")
public class PromotionBargainInfoServiceImpl implements PromotionBargainInfoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoServiceImpl.class);

	@Resource
	private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
	
	/**
	 * 获取砍价商品详情
	 */
	@Override
	public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		PromotionBargainInfoResDTO promotionBargainInfoResDTO = new PromotionBargainInfoResDTO();
		//查询活动详情
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,入参{}",buyerBargainLaunch.getMessageId(),JSON.toJSONString(buyerBargainLaunch));
		PromotionBargainInfoDMO promotionBargainInfo = promotionBargainInfoDAO.getPromotionBargainInfoDetail(buyerBargainLaunch);
		LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,出参{}",buyerBargainLaunch.getMessageId(),JSON.toJSONString(promotionBargainInfo));
		//根据砍价编码查询砍价记录
		List<BuyerBargainRecordResDTO> buyerBargainRecordResList = new ArrayList<BuyerBargainRecordResDTO>();
		if(!StringUtils.isEmpty(buyerBargainLaunch.getBargainCode())){
			LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",buyerBargainLaunch.getMessageId(),
					buyerBargainLaunch.getBargainCode()+":"+buyerBargainLaunch.getMessageId());
			List<BuyerBargainRecordDMO> buyerBargainRecordList = buyerBargainRecordDAO.getBuyerBargainRecordByBargainCode(buyerBargainLaunch.getBargainCode());
			LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",buyerBargainLaunch.getMessageId(),
					JSON.toJSONString(buyerBargainRecordList));
			if(buyerBargainRecordList != null){
				String str = JSONObject.toJSONString(buyerBargainRecordList);
				buyerBargainRecordResList = JSONObject.parseArray(str,BuyerBargainRecordResDTO.class);
			}
		}
		
		if(promotionBargainInfo != null){
			String str = JSONObject.toJSONString(promotionBargainInfo);
			promotionBargainInfoResDTO = JSONObject.parseObject(str,PromotionBargainInfoResDTO.class);
		}
		promotionBargainInfoResDTO.setBuyerBargainRecordList(buyerBargainRecordResList);
		return promotionBargainInfoResDTO;
	}
	
	 
}

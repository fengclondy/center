package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Service("buyerLaunchBargainInfoService")
public class BuyerLaunchBargainInfoServiceImpl implements BuyerLaunchBargainInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerLaunchBargainInfoServiceImpl.class);
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	
	@Override
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",messageId,buyerCode+":"+messageId);
		List<BuyerLaunchBargainInfoDMO> buyerBargainInfoList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode(buyerCode);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",messageId,
				JSON.toJSONString(buyerBargainInfoList));
		List<BuyerLaunchBargainInfoResDTO> buyerLaunchBargainInfoResList = new ArrayList<BuyerLaunchBargainInfoResDTO>();
		if(buyerBargainInfoList != null){
			String str = JSONObject.toJSONString(buyerBargainInfoList);
			buyerLaunchBargainInfoResList = JSONObject.parseArray(str,BuyerLaunchBargainInfoResDTO.class);
		}
		return buyerLaunchBargainInfoResList;
	}

	@Override
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo（）方法开始,入参{}",buyerBargainLaunch.getMessageId(),
				JSON.toJSONString(buyerBargainLaunch));
		Integer flag = buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo(buyerBargainLaunch);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo（）方法开始,出参{}",buyerBargainLaunch.getMessageId(),
				"执行结果为："+flag);
		return flag;
	}

}

package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.biz.service.BuyerBargainRecordService;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;

@Service("buyerBargainRecordService")
public class BuyerBargainRecordServiceImpl implements BuyerBargainRecordService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerBargainRecordServiceImpl.class);
	
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
	
	@Override
	public List<BuyerBargainRecordResDTO> getBuyerBargainRecordByBargainCode(String bargainCode,String messageId) {
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",messageId,bargainCode+":"+messageId);
		List<BuyerBargainRecordDMO> buyerBargainRecordList = buyerBargainRecordDAO.getBuyerBargainRecordByBargainCode(bargainCode);
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",messageId,JSON.toJSONString(buyerBargainRecordList));
		List<BuyerBargainRecordResDTO> buyerBargainRecordResList = new ArrayList<BuyerBargainRecordResDTO>();
		if(buyerBargainRecordList != null){
			String str = JSONObject.toJSONString(buyerBargainRecordList);
			buyerBargainRecordResList = JSONObject.parseArray(str,BuyerBargainRecordResDTO.class);
		}
		return buyerBargainRecordResList;
	}

	@Override
	public Integer insertBuyerBargainRecord(BuyerBargainRecordReqDTO buyerBargainRecord) {
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.insertBuyerBargainRecord（）方法开始,入参{}",buyerBargainRecord.getMessageId(),JSON.toJSONString(buyerBargainRecord));
		Integer i = buyerBargainRecordDAO.insertBuyerBargainRecord(buyerBargainRecord);
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.insertBuyerBargainRecord（）方法结束,出参{}",buyerBargainRecord.getMessageId(),JSON.toJSONString(i));
		return i;
	}

}

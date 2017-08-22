package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.biz.service.BuyerBargainRecordService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
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

	@Override
	public Boolean getThisPersonIsBargain(String bargainCode, String bargainPersonCode, String messageId) {
		Boolean flag = true;
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getThisPersonIsBargain（）方法开始,入参{}",messageId,bargainCode + ":" +bargainPersonCode);
		Map<String, String> map = new HashMap<String,String>();
		map.put("bargainCode", bargainCode);
		map.put("bargainPersonCode", bargainPersonCode);
		BuyerBargainRecordDMO BuyerBargainRecordDMO = buyerBargainRecordDAO.getThisPersonIsBargain(map);
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.insertBuyerBargainRecord（）方法结束,出参{}",messageId,JSON.toJSONString(BuyerBargainRecordDMO));
		//该用户已经砍过
		if(BuyerBargainRecordDMO != null){
			flag = false;
		}
		return flag;
	}

	@Override
	public ExecuteResult<Integer> queryPromotionBargainJoinQTY(String promotionId, String messageId)
			throws PromotionCenterBusinessException {
		LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.queryPromotionBargainJoinQTY（）方法开始,入参{}",messageId,promotionId);
		ExecuteResult<Integer> result = new ExecuteResult<Integer>();
		Integer qty = null;
		try {
			qty = buyerBargainRecordDAO.queryPromotionBargainJoinQTY(promotionId);
			if(null == qty){
				result.setResult(0);
			}else{
				result.setResult(qty.intValue());
			}
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

}

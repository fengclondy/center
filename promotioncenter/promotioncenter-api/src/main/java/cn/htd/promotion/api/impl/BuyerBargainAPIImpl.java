package cn.htd.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.api.BuyerBargainAPI;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

@Service("buyerBargainAPI")
public class BuyerBargainAPIImpl implements BuyerBargainAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerBargainAPIImpl.class);
	
	@Resource
	private BuyerLaunchBargainInfoService buyerLaunchBargainInfoService;

	@Override
	public ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId) {
		ExecuteResult<List<BuyerLaunchBargainInfoResDTO>> result = new ExecuteResult<List<BuyerLaunchBargainInfoResDTO>>();
		List<BuyerLaunchBargainInfoResDTO> buyerBargainInfoList = buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode(buyerCode,messageId);
		result.setResult(buyerBargainInfoList);
		result.setCode(ResultCodeEnum.SUCCESS.getCode());
		if(buyerBargainInfoList.size() == 0){
			result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
		}else{
			result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
		}
		LOGGER.info("MessageId{}:getBuyerLaunchBargainInfoByBuyerCode（）方法,出参{}", messageId, JSON.toJSONString(result));
		return result;
	}

}

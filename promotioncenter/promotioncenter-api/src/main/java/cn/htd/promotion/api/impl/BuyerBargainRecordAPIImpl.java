package cn.htd.promotion.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

import cn.htd.promotion.api.BuyerBargainRecordAPI;
import cn.htd.promotion.cpc.biz.service.BuyerBargainRecordService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;

@Service("buyerBargainRecordAPI")
public class BuyerBargainRecordAPIImpl implements BuyerBargainRecordAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerBargainRecordAPIImpl.class);
	
	@Resource
	private BuyerBargainRecordService buyerBargainRecordService;

	@Override
	public ExecuteResult<List<BuyerBargainRecordResDTO>> getBuyerBargainRecordByBargainCode(String bargainCode,String messageId) {
		ExecuteResult<List<BuyerBargainRecordResDTO>> result = new ExecuteResult<List<BuyerBargainRecordResDTO>>();
		if(!StringUtils.isEmpty(bargainCode) && !StringUtils.isEmpty(messageId)){
			List<BuyerBargainRecordResDTO> BuyerBargainRecordList = buyerBargainRecordService.getBuyerBargainRecordByBargainCode(bargainCode,messageId);
			result.setResult(BuyerBargainRecordList);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			if(BuyerBargainRecordList.size() == 0 || BuyerBargainRecordList ==null){
				result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
			}else{
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
			}
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		
		LOGGER.info("MessageId{}:getBuyerBargainRecordByBargainCode（）方法,出参{}", messageId, JSON.toJSONString(result));
		return result;
	}

	@Override
	public ExecuteResult<Integer> insertBuyerBargainRecord(BuyerBargainRecordReqDTO buyerBargainRecord) {
		ExecuteResult<Integer> result = new ExecuteResult<Integer>();
		/*验空2017-08-16*/
		ValidateResult validateResult = DTOValidateUtil.validate(buyerBargainRecord);
		if(!validateResult.isPass()){
			result.setErrorMessage(validateResult.getReponseMsg());
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			return  result;
		}
		Integer flag = buyerBargainRecordService.insertBuyerBargainRecord(buyerBargainRecord);
		return null;
	}

}

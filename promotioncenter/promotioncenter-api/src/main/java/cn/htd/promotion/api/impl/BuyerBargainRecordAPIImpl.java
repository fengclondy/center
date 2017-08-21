package cn.htd.promotion.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import cn.htd.promotion.cpc.common.util.ExecuteResult;
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
		try{
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
		}catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法buyerBargainRecordService.getBuyerBargainRecordByBargainCode出现异常{}",
					messageId, bargainCode + ":" + messageId, w.toString());
		}
		return result;
	}

	@Override
	public ExecuteResult<Boolean> insertBuyerBargainRecord(BuyerBargainRecordReqDTO buyerBargainRecord) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		/*验空2017-08-16*/
//		ValidateResult validateResult = DTOValidateUtil.validate(buyerBargainRecord);
//		if(!validateResult.isPass()){
//			result.setErrorMessage(validateResult.getReponseMsg());
//			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
//			return  result;
//		}
		try{
			Integer i = buyerBargainRecordService.insertBuyerBargainRecord(buyerBargainRecord);
			if(1==i){
				result.setCode(ResultCodeEnum.SUCCESS.getCode());
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
				result.setResult(true);
			}else{
				result.setCode(ResultCodeEnum.ERROR.getCode());
				result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
				result.setResult(false);
			}
		}catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法buyerBargainRecordService.insertBuyerBargainRecord出现异常{}",
					buyerBargainRecord.getMessageId(), JSON.toJSONString(buyerBargainRecord), w.toString());
		}
		return result;
	}

	@Override
	public ExecuteResult<Boolean> getThisPersonIsBargain(String bargainCode, String bargainPersonCode, String messageId) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try{
			Boolean flag =  buyerBargainRecordService.getThisPersonIsBargain(bargainCode, bargainPersonCode, messageId);
			result.setResult(flag);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
		}catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法buyerBargainRecordService.getThisPersonIsBargain出现异常{}",
					messageId, bargainCode + ":" +bargainPersonCode, w.toString());
		}
		return result;
	}

}

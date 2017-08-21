package cn.htd.promotion.api.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.api.PromotionBargainInfoAPI;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

//@Service("promotionBargainInfoAPI")
//public class PromotionBargainInfoAPIImpl implements  PromotionBargainInfoAPI{
//	
//	@Resource
//	private PromotionBargainInfoService promotionBargainInfoService;
//
//	@Override
//	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(
//			BuyerBargainLaunchReqDTO buyerBargainLaunch) {
//		ExecuteResult<PromotionBargainInfoResDTO> result = new ExecuteResult<PromotionBargainInfoResDTO>();
//		/*验空2017-08-16*/
////		ValidateResult validateResult = DTOValidateUtil.validate(buyerBargainLaunch);
////		if(!validateResult.isPass()){
////			result.setErrorMessage(validateResult.getReponseMsg());
////			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
////			return  result;
////		}
//		PromotionBargainInfoResDTO promotionBargainInfo = promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
//		result.setResult(promotionBargainInfo);
//		result.setCode(ResultCodeEnum.SUCCESS.getCode());
//		if(promotionBargainInfo ==null){
//			result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
//		}else{
//			result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
//		}
//		return null;
//	}
//}
@Service("promotionBargainInfoAPI")
public class PromotionBargainInfoAPIImpl implements  PromotionBargainInfoAPI{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoAPIImpl.class);
	@Resource
	private PromotionBargainInfoService promotionBargainInfoService;

	@Override
	public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(
			BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		ExecuteResult<PromotionBargainInfoResDTO> result = new ExecuteResult<PromotionBargainInfoResDTO>();
		/*验空2017-08-16*/
		ValidateResult validateResult = DTOValidateUtil.validate(buyerBargainLaunch);
		if(!validateResult.isPass()){
			result.setErrorMessage(validateResult.getReponseMsg());
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			return  result;
		}
		try{
			PromotionBargainInfoResDTO promotionBargainInfo = promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
			result.setResult(promotionBargainInfo);
			result.setCode(ResultCodeEnum.SUCCESS.getCode());
			if(promotionBargainInfo ==null){
				result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
			}else{
				result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
			}
		}catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("MessageId:{} 调用方法promotionBargainInfoService.getPromotionBargainInfoDetail出现异常{}",
					buyerBargainLaunch.getMessageId(), JSON.toJSONString(buyerBargainLaunch), w.toString());
		}
		return result;
	}

}
>>>>>>> bc56cc04f485b510472f39a6f6f17604a4c3a019

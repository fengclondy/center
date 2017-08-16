package cn.htd.promotion.api.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.promotion.api.PromotionBargainInfoAPI;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.DTOValidateUtil;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Service("promotionBargainInfoAPI")
public class PromotionBargainInfoAPIImpl implements  PromotionBargainInfoAPI{
	
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
		PromotionBargainInfoResDTO promotionBargainInfo = promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
		result.setResult(promotionBargainInfo);
		result.setCode(ResultCodeEnum.SUCCESS.getCode());
		if(promotionBargainInfo ==null){
			result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
		}else{
			result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
		}
		return null;
	}

}

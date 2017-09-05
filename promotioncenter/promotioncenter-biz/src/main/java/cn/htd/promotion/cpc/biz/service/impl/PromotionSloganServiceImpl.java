package cn.htd.promotion.cpc.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionSloganDMO;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSloganDTO;

@Service("promotionSloganService")
public class PromotionSloganServiceImpl implements PromotionSloganService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionSloganServiceImpl.class);

	@Resource
	private PromotionSloganDAO promotionSloganDAO;
	
	@Override
	public ExecuteResult<List<PromotionSloganDTO>> queryBargainSloganBySellerCode(String providerSellerCode, String messageId)
			throws PromotionCenterBusinessException {
		ExecuteResult<List<PromotionSloganDTO>> result = new ExecuteResult<List<PromotionSloganDTO>>();
		List<PromotionSloganDMO> promotionSloganDMOList = null;
		LOGGER.info("MessageId{}:调用promotionSloganDAO.queryBargainSloganBySellerCode（）方法开始,入参{}",messageId,providerSellerCode+":"+messageId);
		try {
			promotionSloganDMOList = promotionSloganDAO.queryBargainSloganBySellerCode(providerSellerCode);;
			LOGGER.info("MessageId{}:调用promotionSloganDAO.queryBargainSloganBySellerCode（）方法开始,出参{}",JSON.toJSONString(promotionSloganDMOList));
			List<PromotionSloganDTO> promotionSloganDTOList = null;
			if(null != promotionSloganDMOList){
				promotionSloganDTOList = new ArrayList<PromotionSloganDTO>();
				String str = JSONObject.toJSONString(promotionSloganDMOList);
				promotionSloganDTOList = JSONObject.parseArray(str, PromotionSloganDTO.class);
				result.setResult(promotionSloganDTOList);
			}
		}catch(PromotionCenterBusinessException psb){
			result.setCode(psb.getCode());
			result.setErrorMessage(psb.getMessage());
		}catch (Exception e){
			result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(e.toString());
		}
		return result;
	}
}

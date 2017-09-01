package cn.htd.promotion.cpc.api.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.api.PromotionBargainInfoAPI;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainOverviewResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.PromotonInfoResDTO;

@Service("promotionBargainInfoAPI")
public class PromotionBargainInfoAPIImpl implements PromotionBargainInfoAPI {

    @Resource
    private PromotionBargainInfoService promotionBargainInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionBargainInfoAPIImpl.class);

    @Override
    public ExecuteResult<PromotionBargainInfoResDTO> getPromotionBargainInfoDetail(
            BuyerBargainLaunchReqDTO buyerBargainLaunch) {
        ExecuteResult<PromotionBargainInfoResDTO> result = new ExecuteResult<PromotionBargainInfoResDTO>();
        try {
            ValidateResult validateResult = ValidationUtils
                    .validateEntity(buyerBargainLaunch);
            if (validateResult.isHasErrors()) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            PromotionBargainInfoResDTO promotionBargainInfo =
                    promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
            result.setResult(promotionBargainInfo);
            if (promotionBargainInfo == null) {
                result.setCode(ResultCodeEnum.NORESULT.getCode());
                result.setResultMessage(ResultCodeEnum.NORESULT.getMsg());
            } else {
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());
            }
        } catch (PromotionCenterBusinessException pbs) {
            result.setCode(pbs.getCode());
            result.setErrorMessage(pbs.getMessage());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            LOGGER.error(MessageFormat.format("MessageId:{0} 调用方法promotionBargainInfoService.getPromotionBargainInfoDetail出现异常{1}",
                    buyerBargainLaunch.getMessageId(), JSON.toJSONString(buyerBargainLaunch)), e);
        }
        return result;
    }

    @Override
    public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(String messageId,
            String promotionId) {
        ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
        if (!StringUtils.isEmpty(promotionId) && !StringUtils.isEmpty(messageId)) {
            return promotionBargainInfoService.getPromotionBargainInfoList(messageId, promotionId);
        } else {
            result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
            result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
        }
        return result;
    }
    
 
    @Override
    public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(
    		PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page) {
		ExecuteResult<DataGrid<PromotonInfoResDTO>> result = new ExecuteResult<DataGrid<PromotonInfoResDTO>>();
		if(null != reqDTO){
			return promotionBargainInfoService.queryPromotionInfoListBySellerCode(reqDTO, page);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

    
    @Override
    public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(
			String promotionId,  Pager<String> page) {
		ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> result = new ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>>();
		if(!StringUtils.isEmpty(promotionId)){
			return promotionBargainInfoService.queryPromotionBargainOverview(promotionId, page);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

	@Override
	public ExecuteResult<PromotionInfoDTO> upDownShelvesPromotionInfo(
			PromotionValidDTO dto) {
		ExecuteResult<PromotionInfoDTO> result = new ExecuteResult<PromotionInfoDTO>();
		if(null != dto){
			return promotionBargainInfoService.upDownShelvesPromotionInfo(dto);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

	@Override
	public ExecuteResult<List<PromotionBargainInfoResDTO>> addPromotionBargainInfoRedis(
			List<PromotionBargainInfoResDTO> promotionBargainInfoList) {
		ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
		if(null != promotionBargainInfoList && !promotionBargainInfoList.isEmpty()){
			return promotionBargainInfoService.addPromotionBargainInfoRedis(promotionBargainInfoList);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if(null != validDTO){
			return promotionBargainInfoService.deleteBargainInfo(validDTO);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

	@Override
	public ExecuteResult<List<PromotionBargainInfoResDTO>> updateBargainInfo(
			List<PromotionBargainInfoResDTO> bargainInfoList) {
		ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
		if(null != bargainInfoList){
			return promotionBargainInfoService.updateBargainInfo(bargainInfoList);
		}else{
			result.setCode(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getCode());
			result.setErrorMessage(ResultCodeEnum.PROMOTION_PARAM_IS_NULL.getMsg());
		}
		return result;
	}

}
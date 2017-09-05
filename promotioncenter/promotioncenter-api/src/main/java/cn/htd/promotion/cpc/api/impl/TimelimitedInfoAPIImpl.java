package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.TimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

@Service("timelimitedInfoAPI")
public class TimelimitedInfoAPIImpl implements TimelimitedInfoAPI {
	
    private static final Logger logger = LoggerFactory.getLogger(TimelimitedInfoAPIImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private TimelimitedInfoService timelimitedInfoService;
    

	@Override
	public ExecuteResult<?> addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
			if (null == timelimitedInfoReqDTO) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动参数不能为空！");
			}
			
            timelimitedInfoService.addTimelimitedInfo(timelimitedInfoReqDTO, messageId);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.addTimelimitedInfo出现异常{}", messageId, timelimitedInfoReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
	}

	@Override
	public ExecuteResult<?> updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
    		if (null == timelimitedInfoReqDTO) {
    			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动参数不能为空！");
    		}
    		
			if (null == timelimitedInfoReqDTO.getPromotionId() || "".equals(timelimitedInfoReqDTO.getPromotionId().trim())) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "秒杀促销活动编码不能为空！");
			}
			
			TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getSingleTimelimitedInfoByPromotionId(timelimitedInfoReqDTO.getPromotionId(), messageId);
			if(null == timelimitedInfoResDTO){
				throw new PromotionCenterBusinessException(ResultCodeEnum.NORESULT.getCode(), "秒杀促销活动不存在！");
			}
    		
            timelimitedInfoService.updateTimelimitedInfo(timelimitedInfoReqDTO, messageId);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.updateTimelimitedInfo出现异常{}", messageId, timelimitedInfoReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
	}

    
    

}

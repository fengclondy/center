package cn.htd.promotion.cpc.api.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.TimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
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
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.addTimelimitedInfo出现异常{}", messageId,  e.toString());
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
    		
            timelimitedInfoService.updateTimelimitedInfo(timelimitedInfoReqDTO, messageId);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.updateTimelimitedInfo出现异常{}", messageId, timelimitedInfoReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
	}

	
	@Override
	public ExecuteResult<TimelimitedInfoResDTO> getSingleFullTimelimitedInfoByPromotionId(
			String promotionId, String messageId) {
        ExecuteResult<TimelimitedInfoResDTO> result = new ExecuteResult<TimelimitedInfoResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	TimelimitedInfoResDTO timelimitedInfoResDTO = timelimitedInfoService.getSingleFullTimelimitedInfoByPromotionId(promotionId,TimelimitedConstants.TYPE_REDIS_TIMELIMITED_REAL_REMAIN_COUNT, messageId);
        	result.setResult(timelimitedInfoResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.getSingleFullTimelimitedInfoByPromotionId出现异常{}", messageId, e.toString());
        }
        return result;
	}

	
	public ExecuteResult<DataGrid<TimelimitedInfoResDTO>> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
		ExecuteResult<DataGrid<TimelimitedInfoResDTO>> result = new ExecuteResult<DataGrid<TimelimitedInfoResDTO>>(); 
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	DataGrid<TimelimitedInfoResDTO> timelimitedInfoResDTOData = timelimitedInfoService.getTimelimitedInfosForPage(page,timelimitedInfoReqDTO, messageId);
        	result.setResult(timelimitedInfoResDTOData);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.getSingleFullTimelimitedInfoByPromotionId出现异常{}", messageId, e.toString());
        }
        return result;
	}

	@Override
	public ExecuteResult<String> updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
        ExecuteResult<String> result = new ExecuteResult<String>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	String status = timelimitedInfoService.updateShowStatusByPromotionId(timelimitedInfoReqDTO, messageId);
        	result.setResult(status);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法TimelimitedInfoAPIImpl.updateShowStatusByPromotionId出现异常{}", messageId, e.toString());
        }
        return result;
	}
    
	@Override
	public ExecuteResult<List<PromotionSellerDetailDTO>> getPromotionSellerDetailList(String promotionId,
			String messageId) {
		ExecuteResult<List<PromotionSellerDetailDTO>> result = new ExecuteResult<List<PromotionSellerDetailDTO>>();
		result.setCode(ResultCodeEnum.SUCCESS.getCode());
		result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

		try {
			List<PromotionSellerDetailDTO> list = timelimitedInfoService.getPromotionSellerDetailList(promotionId);
			result.setResult(list);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
			result.setErrorMessage(e.toString());
			logger.error(
					MessageFormat.format("MessageId:{0} 调用方法TimelimitedInfoAPIImpl.getPromotionSellerDetailList出现异常{1}",
							messageId, e.toString()),
					e);
		}
		return result;
	}
}

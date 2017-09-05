package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

import cn.htd.common.DataGrid;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.TimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;

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

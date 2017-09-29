package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.GroupbuyingAPI;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;

@Service("groupbuyingAPI")
public class GroupbuyingAPIImpl implements GroupbuyingAPI {
	
    private static final Logger logger = LoggerFactory.getLogger(GroupbuyingAPIImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private GroupbuyingService groupbuyingService;
    

	@Override
	public ExecuteResult<?> addGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId) {
		
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
			if (null == groupbuyingInfoCmplReqDTO) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数不能为空！");
			}
			
			groupbuyingService.addGroupbuyingInfo(groupbuyingInfoCmplReqDTO, messageId);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.addGroupbuyingInfo出现异常{}", messageId,  e.toString());
        }
        return result;
	}
	
	
	

}

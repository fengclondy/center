package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.GroupbuyingAPI;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;

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
            result.setErrorMessage(e.getMessage());
//            String msg=e.getMessage();  
//            if(e.getCause() instanceof PromotionCenterBusinessException){  
//            	PromotionCenterBusinessException be = (PromotionCenterBusinessException) e.getCause();
//                msg=be.getMessage();
//            }  
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.addGroupbuyingInfo出现异常{}", messageId,  e.toString());
        }
        return result;
	}
	
	
	@Override
	public ExecuteResult<?> updateGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId) {
		
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
    		if (null == groupbuyingInfoCmplReqDTO) {
    			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数不能为空！");
    		}
    		
			if (null == groupbuyingInfoCmplReqDTO.getPromotionId() || "".equals(groupbuyingInfoCmplReqDTO.getPromotionId().trim())) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "团购促销活动编码不能为空！");
			}
    		
			groupbuyingService.updateGroupbuyingInfo(groupbuyingInfoCmplReqDTO, messageId);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.updateGroupbuyingInfo出现异常{}", messageId, groupbuyingInfoCmplReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
	}


	@Override
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmplByPromotionId(String promotionId, String messageId) {
        ExecuteResult<GroupbuyingInfoCmplResDTO> result = new ExecuteResult<GroupbuyingInfoCmplResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = groupbuyingService.getGroupbuyingInfoCmplByPromotionId(promotionId, messageId);
        	result.setResult(groupbuyingInfoCmplResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getGroupbuyingInfoCmplByPromotionId出现异常{}", messageId, e.toString());
        }
        return result;
	}


	@Override
	public ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> getGroupbuyingInfoCmplForPage(Pager<GroupbuyingInfoReqDTO> page, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
		ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> result = new ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>>(); 
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	DataGrid<GroupbuyingInfoCmplResDTO> groupbuyingInfoCmplResDTOData = groupbuyingService.getGroupbuyingInfoCmplForPage(page,groupbuyingInfoReqDTO, messageId);
        	result.setResult(groupbuyingInfoCmplResDTOData);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getGroupbuyingInfoCmplForPage出现异常{}", messageId, e.toString());
        }
        return result;
	}
	
	
	
	
	

}

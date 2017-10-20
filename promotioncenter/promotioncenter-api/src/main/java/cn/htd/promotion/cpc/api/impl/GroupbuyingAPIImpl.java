package cn.htd.promotion.cpc.api.impl;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.GroupbuyingAPI;
import cn.htd.promotion.cpc.biz.handle.PromotionGroupbuyingRedisHandle;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.constants.GroupbuyingConstants;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;

@Service("groupbuyingAPI")
public class GroupbuyingAPIImpl implements GroupbuyingAPI {
	
    private static final Logger logger = LoggerFactory.getLogger(GroupbuyingAPIImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private GroupbuyingService groupbuyingService;
    
    @Resource
	private PromotionGroupbuyingRedisHandle promotionGroupbuyingRedisHandle;
    

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
	public ExecuteResult<GroupbuyingInfoResDTO> getSingleGroupbuyingInfoByPromotionId(String promotionId, String messageId) {
        ExecuteResult<GroupbuyingInfoResDTO> result = new ExecuteResult<GroupbuyingInfoResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	GroupbuyingInfoResDTO groupbuyingInfoResDTO = groupbuyingService.getSingleGroupbuyingInfoByPromotionId(promotionId, messageId);
        	result.setResult(groupbuyingInfoResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getSingleGroupbuyingInfoByPromotionId出现异常{}", messageId, e.toString());
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

	@Override
	public ExecuteResult<GroupbuyingRecordResDTO> getSingleGroupbuyingRecord(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId) {
		
        ExecuteResult<GroupbuyingRecordResDTO> result = new ExecuteResult<GroupbuyingRecordResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	GroupbuyingRecordResDTO groupbuyingRecordResDTO = groupbuyingService.getSingleGroupbuyingRecord(groupbuyingRecordReqDTO, messageId);
        	result.setResult(groupbuyingRecordResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getSingleGroupbuyingRecord出现异常{}", messageId, e.toString());
        }
        return result;
	}


	@Override
	public ExecuteResult<DataGrid<GroupbuyingRecordResDTO>> geGroupbuyingRecordForPage(Pager<GroupbuyingRecordReqDTO> page,GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId) {
		
		ExecuteResult<DataGrid<GroupbuyingRecordResDTO>> result = new ExecuteResult<DataGrid<GroupbuyingRecordResDTO>>(); 
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	DataGrid<GroupbuyingRecordResDTO> groupbuyingRecordResDTOData = groupbuyingService.geGroupbuyingRecordForPage(page,groupbuyingRecordReqDTO, messageId);
        	result.setResult(groupbuyingRecordResDTOData);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.geGroupbuyingRecordForPage出现异常{}", messageId, e.toString());
        }
        return result;
	}

    @Override
    public ExecuteResult<?> addGroupbuyingRecord2HttpINTFC(GroupbuyingRecordReqDTO dto, String messageId) {
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
            if (null == dto) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参团参数不能为空！");
            }
            
			if (null == dto.getPromotionId() || dto.getPromotionId().length() == 0) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "团购促销活动编码不能为空！");
			}

            groupbuyingService.addGroupbuyingRecord2HttpINTFC(dto,messageId);

        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.addGroupbuyingRecord2HttpINTFC 出现异常{}", messageId, dto.getPromotionId() + ":" + e.toString());
        }
        return result;
    }
    
	@Override
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmpl2HttpINTFC(String promotionId, String messageId) {
        ExecuteResult<GroupbuyingInfoCmplResDTO> result = new ExecuteResult<GroupbuyingInfoCmplResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
            if (null == promotionId || promotionId.length() == 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动编号不能为空！");
            }
            
        	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = promotionGroupbuyingRedisHandle.getGroupbuyingInfoCmplByPromotionId(promotionId);
        	result.setResult(groupbuyingInfoCmplResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getGroupbuyingInfoCmpl2HttpINTFC出现异常{}", messageId, e.toString());
        }
        return result;
	}


    @Override
    public ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> getGroupbuyingList2HttpINTFC(Pager<GroupbuyingInfoReqDTO> page, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
        ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> result = new ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
            if (null == groupbuyingInfoReqDTO || StringUtils.isEmpty(groupbuyingInfoReqDTO.getSellerCode())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "查询团购商品列表条件参数不能为空！");
            }
            if (null == page) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "查询团购商品页码不能为空！");
            }
            DataGrid<GroupbuyingInfoCmplResDTO> groupbuyingInfoCmplResDTOData = groupbuyingService.getGroupbuyingInfo4MobileForPage(page,groupbuyingInfoReqDTO, messageId);
            result.setResult(groupbuyingInfoCmplResDTOData);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getGroupbuyingInfoCmplForPage出现异常{}", messageId, e.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<?> updateShowStatusByPromotionId(SinglePromotionInfoReqDTO singlePromotionInfoReqDTO, String messageId) {
        ExecuteResult<String> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	
            String statusResult = groupbuyingService.updateShowStatusByPromotionId(singlePromotionInfoReqDTO,messageId);
            if(!GroupbuyingConstants.CommonStatusEnum.STATUS_SUCCESS.key().equals(statusResult)){
            	 result.setCode(statusResult);
                 result.setResultMessage(GroupbuyingConstants.CommonStatusEnum.getValue(statusResult));
            }

        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.updateShowStatusByPromotionId 出现异常{}", messageId, singlePromotionInfoReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<?> deleteGroupbuyingInfoByPromotionId(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
        ExecuteResult<?> result = new ExecuteResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {

        	String statusResult = groupbuyingService.deleteGroupbuyingInfoByPromotionId(groupbuyingInfoReqDTO,messageId);
            if(!GroupbuyingConstants.CommonStatusEnum.STATUS_SUCCESS.key().equals(statusResult)){
           	 result.setCode(statusResult);
             result.setResultMessage(GroupbuyingConstants.CommonStatusEnum.getValue(statusResult));
           }
            
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.deleteGroupbuyingInfoByPromotionId 出现异常{}", messageId, groupbuyingInfoReqDTO.getPromotionId() + ":" + e.toString());
        }
        return result;
    }


	@Override
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingHomePage2HttpINTFC(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
		
        ExecuteResult<GroupbuyingInfoCmplResDTO> result = new ExecuteResult<GroupbuyingInfoCmplResDTO>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultMessage(ResultCodeEnum.SUCCESS.getMsg());

        try {
        	
        	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = groupbuyingService.getGroupbuyingInfo4MobileHomePage(groupbuyingInfoReqDTO, messageId);
        	result.setResult(groupbuyingInfoCmplResDTO);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMessage(ResultCodeEnum.ERROR.getMsg());
            result.setErrorMessage(e.toString());
            logger.error("MessageId:{} 调用方法GroupbuyingAPIImpl.getGroupbuyingHomePage2HttpINTFC出现异常{}", messageId, e.toString());
        }
        
        return result;
		
	}
}

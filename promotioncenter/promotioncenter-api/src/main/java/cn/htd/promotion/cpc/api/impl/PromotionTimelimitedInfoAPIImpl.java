package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.handle.PromotionTimelimitedRedisHandle;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.TimelimitedStatusEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedResultDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("promotionTimelimitedInfoAPI")
public class PromotionTimelimitedInfoAPIImpl implements PromotionTimelimitedInfoAPI {
	
    private static final Logger logger = LoggerFactory.getLogger(PromotionTimelimitedInfoAPIImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionTimelimitedInfoService promotionTimelimitedInfoService;
    
    @Resource
    private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;

    @Resource
    private PromotionRedisDB promotionRedisDB;
	/**
	 * 根据商品编码取得秒杀信息
	 * 
	 * @param messageId
	 * @param skuCode 商品SKU编码
	 * @return
	 */
    @Override
    public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode) {
        ExecuteResult<TimelimitedInfoResDTO> result = new ExecuteResult<TimelimitedInfoResDTO>();
        List<TimelimitedInfoResDTO> tmpTimelimitedDTOList = null;
        List<String> skuCodeList = new ArrayList<String>();
        try {
        	logger.info("商品编码为:"+ skuCode);
            if (StringUtils.isEmpty(skuCode)) {
                throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR, "商品编码不能为空");
            }
            skuCodeList.add(skuCode);
            tmpTimelimitedDTOList = promotionTimelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList); 
            if (tmpTimelimitedDTOList == null || tmpTimelimitedDTOList.isEmpty()) {
                throw new PromotionCenterBusinessException(PromotionCenterConst.SKU_NO_TIMELIMITED, "该商品没有参加秒杀活动");
            }
            result.setResult(tmpTimelimitedDTOList.get(0));
        } catch (PromotionCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.setErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

	/**
	 * 汇掌柜APP -  根据会员编码查询是否有总部秒杀信息 
	 * 
	 * @param messageId
	 * @param buyerCode 会员编码
	 * @param promotionId 促销活动编码
	 * @return
	 */
	@Override
	public ExecuteResult<TimelimitedInfoResDTO> getPromotionTimelimitedByBuyerCodeAndPromotionId(String messageId,
			String buyerCode,String promotionId) {
	      ExecuteResult<TimelimitedInfoResDTO> result =
	                new ExecuteResult<TimelimitedInfoResDTO>();
	      TimelimitedInfoResDTO timelimitedInfo = null;
	      String returnCode = "";
	        try {
	        	logger.info("促销活动编码为:"+ promotionId + "会员编码为:"+ buyerCode);
	            if (StringUtils.isEmpty(promotionId) || StringUtils.isEmpty(buyerCode)) {
	                throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR, "会员编号和秒杀活动编号不能为空");
	            }	            
	            timelimitedInfo = promotionTimelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
	            if(!checkPromotionBuyerAuthority(timelimitedInfo,buyerCode)){
	            	returnCode = PromotionCenterConst.TIMELIMITED_BUYER_NO_AUTHIORITY;
	            }
	            result.setCode(returnCode);
	            result.setResult(timelimitedInfo);
	        } catch (PromotionCenterBusinessException bcbe) {
	            result.setCode(bcbe.getCode());
	            result.setErrorMessage(bcbe.getMessage());
	        } catch (Exception e) {
	            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
	            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
	        }
	        return result;
	}
	
	/**
	 * 汇掌柜APP -  校验会员是否有权限参加总部秒杀
	 * 
	 * @param timelimitedInfo 秒杀信息
	 * @param buyerCode  会员编码
	 * @return
	 */
	private boolean checkPromotionBuyerAuthority(TimelimitedInfoResDTO timelimitedInfo,String buyerCode){
		PromotionSellerDetailDTO sellerDetail= null;//timelimitedInfo.getSellerDetailDTO();
		if(null == sellerDetail){
			return true;
		}
		if(buyerCode.equals(sellerDetail.getSellerCode())){
			return true;
		}
		return false;
	}
    
	/**
	 * 汇掌柜APP - 查询秒杀活动列表
	 * 
	 *  粉丝 未登录 默认取汇通达O2O旗舰店的秒杀商品；已登录则取归属会员店的秒杀商品(根据buyerCode)
	 * @param messageId
	 * @param page
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> getPromotionTimelimitedList(String messageId,String buyerCode,Pager<TimelimitedInfoResDTO> page) {
        ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> result = new ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>>();
        DataGrid<PromotionTimelimitedShowDTO> datagrid = null;
        try {
            datagrid = promotionTimelimitedRedisHandle.getRedisTimelimitedInfoList(buyerCode, page);
            result.setResult(datagrid);
        } catch (PromotionCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.setErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
	}

	/**
	 * 汇掌柜APP - 查询秒杀活动详情
	 * 
	 * @param messageId
	 * @param promotionId
	 * @param buyerCode  会员编码
	 * @param buyerGrade 目前未用的，考虑后期扩展
	 * @return
	 */
	@Override
	public ExecuteResult<PromotionTimelimitedShowDTO> getPromotionTimelimitedInfoDetail(String messageId,
			String promotionId, String buyerCode, String buyerGrade) {
        ExecuteResult<PromotionTimelimitedShowDTO> result = new ExecuteResult<PromotionTimelimitedShowDTO>();
        TimelimitedInfoResDTO tmpTimelimitedDTO = null;
        PromotionTimelimitedShowDTO timelimitedDTO = null;
        TimelimitedResultDTO timelimitedResultDTO = null;
        String returnCode = "";
        try {
            tmpTimelimitedDTO = promotionTimelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
            //timelimitedResultDTO = tmpTimelimitedDTO.getTimelimitedResult();
            timelimitedDTO = new PromotionTimelimitedShowDTO();
            //timelimitedDTO.setTimelimitedInfo(tmpTimelimitedDTO);
            timelimitedDTO.setRemainCount(timelimitedResultDTO.getShowRemainSkuCount());
            if (timelimitedResultDTO.getShowRemainSkuCount() <= 0) {
                timelimitedDTO.setRemainCount(0);
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
                returnCode = PromotionCenterConst.TIMELIMITED_SKU_NO_REMAIN;
//            } else if ((new Date()).before(timelimitedDTO.getEffectiveTime())) {
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
//                returnCode = PromotionCenterConst.PROMOTION_NO_START;
//            } else if ((new Date()).after(timelimitedDTO.getInvalidTime())) {
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
//                returnCode = PromotionCenterConst.PROMOTION_HAS_EXPIRED;
            } else {
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());
            }
            timelimitedDTO.setShowStatusStr(TimelimitedStatusEnum.getName(timelimitedDTO.getCompareStatus()));
            if (StringUtils.isEmpty(returnCode) && !StringUtils.isEmpty(buyerCode)) {
    	            if(!checkPromotionBuyerAuthority(tmpTimelimitedDTO,buyerCode)){
    	            	returnCode = PromotionCenterConst.TIMELIMITED_BUYER_NO_AUTHIORITY;
                }
            }
            result.setCode(returnCode);
            result.setResult(timelimitedDTO);
        } catch (PromotionCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.setErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
	}

	/**
	 * 汇掌柜APP -  根据会员编码查询是否有总部秒杀信息
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @return
	 */
	@Override
	public boolean getPromotionTimelimitedByBuyerCode(String messageId,String buyerCode) {
		List<String> list = promotionTimelimitedRedisHandle.getRedisTimelimitedIndex(buyerCode, null);
		if(null !=list && list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 汇掌柜APP -  根据会员编码查询是否有总部秒杀是否有效
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @param promotionId
	 * @return
	 */
	@Override
	public ExecuteResult<Boolean> checkTimelimitedIsAvailableByBuyerCode(String messageId, String buyerCode, String promotionId) {
		String timelimitedJsonStr = "";
	    TimelimitedInfoResDTO timelimitedInfo = null;
	    ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
        timelimitedJsonStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
        timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoResDTO.class);
	    PromotionExtendInfoDTO promotionExtendInfoDTO =timelimitedInfo.getPromotionExtendInfoDTO();
        if(timelimitedInfo.getPromotionId().equals(promotionId)){
        	if(checkBuyerCodeValid(promotionExtendInfoDTO,buyerCode).getResult()){
        		restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_PERMISSION);
        		restult.setResult(true);
        	}else{
        		restult.setCode(checkBuyerCodeValid(promotionExtendInfoDTO,buyerCode).getCode());//秒杀结果信息
        		restult.setResult(false);
        	}
        }else{
        	restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_ID_ERROR);//秒杀活动编码不正确
        }
		return restult;
	}
	
	/**
	 * 汇掌柜APP - 校验秒杀活动参数是否正确
	 * 
	 * @param timelimitedInfo
	 * @return
	 */
    private ExecuteResult<Boolean> checkParamValid(PromotionExtendInfoDTO promotionExtendInfoDTO){ 
    	ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
    	if(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(promotionExtendInfoDTO.getShowStatus())){//秒杀活动启用
	        	if((new Date()).before(promotionExtendInfoDTO.getEffectiveTime())){
	       		    //秒杀活动未开始
	       		    restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_NO_STAET_ERROR);
	         	 } else if ((new Date()).after(promotionExtendInfoDTO.getInvalidTime())) {
	         	   //秒杀送活动已经结束
	               restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_HAS_ENDED_ERROR);
	            } else {
	               //秒杀送活动进行中
	               restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_IS_PROCESSING_ERROR);
	               restult.setResult(true);
	            }
    	}else{
            //秒杀送活动未启用
            restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_IS_DISABLE_ERROR);
    	}

	    return restult;
    }
	
	/**
	 * 汇掌柜APP - 校验会员参数是否合法
	 * 
	 * @param promotionExtendInfoDTO
	 * @return
	 */
    private ExecuteResult<Boolean> checkBuyerCodeValid(PromotionExtendInfoDTO promotionExtendInfoDTO,String buyerCode){ 
    	ExecuteResult<Boolean> restult = new ExecuteResult<Boolean>();
    	 PromotionSellerRuleDTO sellerRuleDTO =  promotionExtendInfoDTO.getSellerRuleDTO();
    	 List<PromotionSellerDetailDTO> sellerDetailList =null;
    	if(null != sellerRuleDTO && null != sellerRuleDTO.getSellerDetailList()){//限制粉丝只能购买归属会员的秒杀商品
    		sellerDetailList = sellerRuleDTO.getSellerDetailList();
    		for(PromotionSellerDetailDTO sellerDetail :sellerDetailList){
    			if(!sellerDetail.getSellerCode().equals(buyerCode)){ 
    				 //粉丝没有秒杀权限
    				 restult.setCode(PromotionCenterConst.TIMELIMITED_RESULT_PROMOTION_NOT_PERMISSION_ERROR);
    			}else{
    		        //校验秒杀活动状态  		
    				restult.setResult(checkParamValid(promotionExtendInfoDTO).getResult());
    			}
    		}
    		
    	}else{//平台粉丝都可购买该秒杀商品
    		restult.setResult(checkParamValid(promotionExtendInfoDTO).getResult());
    	}

	    return restult;
    }
	
	

}

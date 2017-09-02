package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.handle.PromotionTimelimitedRedisHandle;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.emums.TimelimitedStatusEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedResultDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//@Service("promotionTimelimitedInfoAPI")
public class PromotionTimelimitedInfoAPIImpl {
//implements PromotionTimelimitedInfoAPI {
//	
//    private static final Logger logger = LoggerFactory.getLogger(PromotionTimelimitedInfoAPIImpl.class);
//
//    @Resource
//    private DictionaryUtils dictionary;
//
//    @Resource
//    private PromotionTimelimitedInfoService promotionTimelimitedInfoService;
//    
//    @Resource
//    private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;
//
//	/**
//	 * 根据商品编码取得秒杀信息
//	 * 
//	 * @param messageId
//	 * @param skuCode 商品SKU编码
//	 * @return
//	 */
//    @Override
//    public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode) {
//        ExecuteResult<TimelimitedInfoResDTO> result = new ExecuteResult<TimelimitedInfoResDTO>();
//        List<TimelimitedInfoResDTO> tmpTimelimitedDTOList = null;
//        List<String> skuCodeList = new ArrayList<String>();
//        try {
//        	logger.info("商品编码为:"+ skuCode);
//            if (StringUtils.isEmpty(skuCode)) {
//                throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR, "商品编码不能为空");
//            }
//            skuCodeList.add(skuCode);
//            tmpTimelimitedDTOList = promotionTimelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList); 
//            if (tmpTimelimitedDTOList == null || tmpTimelimitedDTOList.isEmpty()) {
//                throw new PromotionCenterBusinessException(PromotionCenterConst.SKU_NO_TIMELIMITED, "该商品没有参加秒杀活动");
//            }
//            result.setResult(tmpTimelimitedDTOList.get(0));
//        } catch (PromotionCenterBusinessException bcbe) {
//            result.setCode(bcbe.getCode());
//            result.setErrorMessage(bcbe.getMessage());
//        } catch (Exception e) {
//            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
//            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
//        }
//        return result;
//    }
//
//	/**
//	 * 汇掌柜APP -  根据会员编码查询是否有总部秒杀信息 
//	 * 
//	 * @param messageId
//	 * @param buyerCode 会员编码
//	 * @param promotionId 促销活动编码
//	 * @return
//	 */
//	@Override
//	public ExecuteResult<TimelimitedInfoResDTO> getPromotionTimelimitedByBuyerCodeAndPromotionId(String messageId,
//			String buyerCode,String promotionId) {
//	      ExecuteResult<TimelimitedInfoResDTO> result =
//	                new ExecuteResult<TimelimitedInfoResDTO>();
//	      TimelimitedInfoResDTO timelimitedInfo = null;
//	      String returnCode = "";
//	        try {
//	        	logger.info("促销活动编码为:"+ promotionId + "会员编码为:"+ buyerCode);
//	            if (StringUtils.isEmpty(promotionId) || StringUtils.isEmpty(buyerCode)) {
//	                throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR, "会员编号和秒杀活动编号不能为空");
//	            }	            
//	            timelimitedInfo = promotionTimelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
//	            if(!checkPromotionBuyerAuthority(timelimitedInfo,buyerCode)){
//	            	returnCode = PromotionCenterConst.TIMELIMITED_BUYER_NO_AUTHIORITY;
//	            }
//	            result.setCode(returnCode);
//	            result.setResult(timelimitedInfo);
//	        } catch (PromotionCenterBusinessException bcbe) {
//	            result.setCode(bcbe.getCode());
//	            result.setErrorMessage(bcbe.getMessage());
//	        } catch (Exception e) {
//	            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
//	            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
//	        }
//	        return result;
//	}
//	
//	/**
//	 * 汇掌柜APP -  校验会员是否有权限参加总部秒杀
//	 * 
//	 * @param timelimitedInfo 秒杀信息
//	 * @param buyerCode  会员编码
//	 * @return
//	 */
//	private boolean checkPromotionBuyerAuthority(TimelimitedInfoResDTO timelimitedInfo,String buyerCode){
//		PromotionSellerDetailDTO sellerDetail=timelimitedInfo.getSellerDetailDTO();
//		if(null == sellerDetail){
//			return true;
//		}
//		if(buyerCode.equals(sellerDetail.getSellerCode())){
//			return true;
//		}
//		return false;
//	}
//    
//	/**
//	 * 汇掌柜APP - 查询秒杀活动列表
//	 * 
//	 *  粉丝 未登录 默认取汇通达O2O旗舰店的秒杀商品；已登录则取归属会员店的秒杀商品(根据buyerCode)
//	 * @param messageId
//	 * @param page
//	 * @return
//	 */
//	@Override
//	public ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> getPromotionTimelimitedList(String messageId,String buyerCode,Pager<TimelimitedInfoResDTO> page) {
//        ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> result = new ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>>();
//        DataGrid<PromotionTimelimitedShowDTO> datagrid = null;
//        try {
//            datagrid = promotionTimelimitedRedisHandle.getRedisTimelimitedInfoList(buyerCode, page);
//            result.setResult(datagrid);
//        } catch (PromotionCenterBusinessException bcbe) {
//            result.setCode(bcbe.getCode());
//            result.setErrorMessage(bcbe.getMessage());
//        } catch (Exception e) {
//            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
//            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
//        }
//        return result;
//	}
//
//	/**
//	 * 汇掌柜APP - 查询秒杀活动详情
//	 * 
//	 * @param messageId
//	 * @param promotionId
//	 * @param buyerCode  会员编码
//	 * @param buyerGrade 目前未用的，考虑后期扩展
//	 * @return
//	 */
//	@Override
//	public ExecuteResult<PromotionTimelimitedShowDTO> getPromotionTimelimitedInfoDetail(String messageId,
//			String promotionId, String buyerCode, String buyerGrade) {
//        ExecuteResult<PromotionTimelimitedShowDTO> result = new ExecuteResult<PromotionTimelimitedShowDTO>();
//        TimelimitedInfoResDTO tmpTimelimitedDTO = null;
//        PromotionTimelimitedShowDTO timelimitedDTO = null;
//        TimelimitedResultDTO timelimitedResultDTO = null;
//        String returnCode = "";
//        try {
//            tmpTimelimitedDTO = promotionTimelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
//            timelimitedResultDTO = tmpTimelimitedDTO.getTimelimitedResult();
//            timelimitedDTO = new PromotionTimelimitedShowDTO();
//            timelimitedDTO.setTimelimitedInfo(tmpTimelimitedDTO);
//            timelimitedDTO.setRemainCount(timelimitedResultDTO.getShowRemainSkuCount());
//            if (timelimitedResultDTO.getShowRemainSkuCount() <= 0) {
//                timelimitedDTO.setRemainCount(0);
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
//                returnCode = PromotionCenterConst.TIMELIMITED_SKU_NO_REMAIN;
//            } else if ((new Date()).before(timelimitedDTO.getEffectiveTime())) {
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
//                returnCode = PromotionCenterConst.PROMOTION_NO_START;
//            } else if ((new Date()).after(timelimitedDTO.getInvalidTime())) {
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
//                returnCode = PromotionCenterConst.PROMOTION_HAS_EXPIRED;
//            } else {
//                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());
//            }
//            timelimitedDTO.setShowStatusStr(TimelimitedStatusEnum.getName(timelimitedDTO.getCompareStatus()));
//            if (StringUtils.isEmpty(returnCode) && !StringUtils.isEmpty(buyerCode)) {
//    	            if(!checkPromotionBuyerAuthority(tmpTimelimitedDTO,buyerCode)){
//    	            	returnCode = PromotionCenterConst.TIMELIMITED_BUYER_NO_AUTHIORITY;
//                }
//            }
//            result.setCode(returnCode);
//            result.setResult(timelimitedDTO);
//        } catch (PromotionCenterBusinessException bcbe) {
//            result.setCode(bcbe.getCode());
//            result.setErrorMessage(bcbe.getMessage());
//        } catch (Exception e) {
//            result.setCode(PromotionCenterConst.SYSTEM_ERROR);
//            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
//        }
//        return result;
//	}
//
//	/**
//	 * 汇掌柜APP -  根据会员编码查询是否有总部秒杀信息
//	 * 
//	 * @param messageId
//	 * @param buyerCode
//	 * @return
//	 */
//	@Override
//	public boolean getPromotionTimelimitedByBuyerCode(String messageId,String buyerCode) {
//		List<String> list = promotionTimelimitedRedisHandle.getRedisTimelimitedIndex(buyerCode, null);
//		if(null !=list && list.size()>0){
//			return true;
//		}
//		return false;
//	}
//
}

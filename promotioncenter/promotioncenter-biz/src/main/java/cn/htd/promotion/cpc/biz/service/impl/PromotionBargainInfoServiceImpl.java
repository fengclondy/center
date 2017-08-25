package cn.htd.promotion.cpc.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionAccumulatyDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSloganDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerBargainRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.handle.PromotionBargainRedisHandle;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.CalculateUtils;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.PromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerBargainRecordResDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainOverviewResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.PromotonInfoResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("promotionBargainInfoService")
public class PromotionBargainInfoServiceImpl implements
                                             PromotionBargainInfoService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PromotionBargainInfoServiceImpl.class);

    @Resource
    private PromotionBargainInfoDAO promotionBargainInfoDAO;
    @Resource
    private DictionaryUtils dictionary;
    @Resource
    private PromotionBaseService baseService;
    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
    @Resource
    private PromotionBargainRedisHandle promotionBargainRedisHandle;
    @Resource
    private GeneratorUtils noGenerator;
    @Resource
    private PromotionSloganDAO promotionSloganDAO;
    @Resource
    private PromotionInfoDAO promotionInfoDAO;
    @Resource
    private PromotionAccumulatyDAO promotionAccumulatyDAO;
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;


    @Resource
    private BuyerBargainRecordDAO buyerBargainRecordDAO;

    /**
     * 获取砍价商品详情
     */
    @Override
    public PromotionBargainInfoResDTO getPromotionBargainInfoDetail(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
        PromotionBargainInfoResDTO promotionBargainInfoResDTO = new PromotionBargainInfoResDTO();
        //查询活动详情
        LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,入参{}",
                buyerBargainLaunch.getMessageId(), JSON.toJSONString(buyerBargainLaunch));
        PromotionBargainInfoDMO promotionBargainInfo =
                promotionBargainInfoDAO.getPromotionBargainInfoDetail(buyerBargainLaunch);
        LOGGER.info("MessageId{}:调用promotionBargainInfoDAO.getPromotionBargainInfoDetail（）方法开始,出参{}",
                buyerBargainLaunch.getMessageId(), JSON.toJSONString(promotionBargainInfo));
        //根据砍价编码查询砍价记录
        List<BuyerBargainRecordResDTO> buyerBargainRecordResList = new ArrayList<BuyerBargainRecordResDTO>();
        if (!StringUtils.isEmpty(buyerBargainLaunch.getBargainCode())) {
            LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",
                    buyerBargainLaunch.getMessageId(),
                    buyerBargainLaunch.getBargainCode() + ":" + buyerBargainLaunch.getMessageId());
            List<BuyerBargainRecordDMO> buyerBargainRecordList =
                    buyerBargainRecordDAO.getBuyerBargainRecordByBargainCode(buyerBargainLaunch.getBargainCode());
            LOGGER.info("MessageId{}:调用buyerBargainRecordDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",
                    buyerBargainLaunch.getMessageId(),
                    JSON.toJSONString(buyerBargainRecordList));
            if (buyerBargainRecordList != null) {
                String str = JSONObject.toJSONString(buyerBargainRecordList);
                buyerBargainRecordResList = JSONObject.parseArray(str, BuyerBargainRecordResDTO.class);
            }
        }

        if (promotionBargainInfo != null) {
            String str = JSONObject.toJSONString(promotionBargainInfo);
            promotionBargainInfoResDTO = JSONObject.parseObject(str, PromotionBargainInfoResDTO.class);
        }
        promotionBargainInfoResDTO.setBuyerBargainRecordList(buyerBargainRecordResList);
        return promotionBargainInfoResDTO;
    }

    @Override
    public ExecuteResult<List<PromotionBargainInfoResDTO>> addPromotionBargainInfoRedis(
            List<PromotionBargainInfoResDTO> promotionBargainInfoList) throws PromotionCenterBusinessException {
        ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionSloganResDTO sloganDTO = new PromotionSloganResDTO();
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
        List<PromotionAccumulatyDTO> accuDTOList = new ArrayList<PromotionAccumulatyDTO>();
        PromotionBargainInfoResDTO promotionBargainInfo = new PromotionBargainInfoResDTO();
        try {
            if (null != promotionBargainInfoList && !promotionBargainInfoList.isEmpty()) {
                String promotionType = dictionary.getValueByCode(
                        DictionaryConst.TYPE_PROMOTION_TYPE,
                        DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN);
                String promotionProviderType = dictionary.getValueByCode(
                        DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                        DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_MEMBER_SHOP);
                String promotionSlogan = promotionBargainInfoList.get(0).getPromotionSlogan();
                for (PromotionBargainInfoResDTO dto : promotionBargainInfoList) {
                    dto.setPromotionType(promotionType);
                    dto.setPromotionProviderType(promotionProviderType);
                    // 输入DTO的验证
                    ValidateResult validateResult = ValidationUtils
                            .validateEntity(dto);
                    if (validateResult.isHasErrors()) {
                        throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
                                validateResult.getErrorMsg());
                    }
                    dto.setGoodsCostPrice(CalculateUtils
                            .setScale(dto.getGoodsCostPrice()));
                    dto.setGoodsFloorPrice(CalculateUtils
                            .setScale(dto.getGoodsFloorPrice()));
                    accuDTOList.add(dto);
                }
                accuDTO = baseService
                        .insertManyAccumulatyPromotionInfo(accuDTOList);
                historyDTO.setPromotionId(accuDTO.getPromotionId());
                historyDTO.setPromotionStatus(accuDTO.getShowStatus());
                historyDTO.setPromotionStatusText(dictionary.getNameByValue(
                        DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        promotionBargainInfo.getShowStatus()));
                historyDTO.setCreateId(accuDTO.getCreateId());
                historyDTO.setCreateName(accuDTO.getCreateName());
                promotionStatusHistoryDAO.add(historyDTO);
                sloganDTO.setPromotionId(accuDTO.getPromotionId());
                sloganDTO.setPromotionSlogan(promotionSlogan);
                sloganDTO.setCreateId(accuDTO.getCreateId());
                sloganDTO.setCreateName(accuDTO.getCreateName());
                promotionSloganDAO.add(sloganDTO);
                historyList.add(historyDTO);
                promotionBargainInfo.setPromotionStatusHistoryList(historyList);
                promotionBargainRedisHandle
                        .addBargainInfo2Redis(promotionBargainInfoList);
                result.setResult(promotionBargainInfoList);
            }
        } catch (PromotionCenterBusinessException pbs) {
            result.setCode(pbs.getCode());
            result.setErrorMessage(pbs.getMessage());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(e.toString());
        }
        return result;
    }

    @Override
    public ExecuteResult<String> deleteBargainInfo(PromotionValidDTO validDTO)
            throws PromotionCenterBusinessException {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils
                    .validateEntity(validDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new PromotionCenterBusinessException(
                        ResultCodeEnum.PARAMETER_ERROR.getCode(),
                        validateResult.getErrorMsg());
            }
            baseService.deletePromotionInfo(validDTO);
            historyDTO.setPromotionId(validDTO.getPromotionId());
            historyDTO.setPromotionStatus(dictionary.getValueByCode(
                    DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            historyDTO.setPromotionStatusText(dictionary.getNameByValue(
                    DictionaryConst.TYPE_PROMOTION_STATUS,
                    historyDTO.getPromotionStatus()));
            historyDTO.setCreateId(validDTO.getOperatorId());
            historyDTO.setCreateName(validDTO.getOperatorName());
            promotionStatusHistoryDAO.add(historyDTO);
            promotionBargainRedisHandle.deleteRedisBargainInfo(validDTO
                    .getPromotionId());
            result.setResult("处理成功");
        } catch (PromotionCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.setErrorMessage(mcbe.getMessage());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<List<PromotionBargainInfoResDTO>> getPromotionBargainInfoList(String messageId,
            String promotionId) throws PromotionCenterBusinessException {
        ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
        List<PromotionBargainInfoResDTO> datagrid = null;
        try {
            datagrid = promotionBargainRedisHandle.getRedisBargainInfoList(promotionId);
            result.setResult(datagrid);
        } catch (PromotionCenterBusinessException pbe) {
            result.setCode(pbe.getCode());
            result.setErrorMessage(pbe.getMessage());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            LOGGER.error("MessageId:{} 调用方法PromotionBargainInfoService.getPromotionBargainInfoList出现异常{}",
                    messageId, promotionId, e.toString());
        }
        return result;
    }
	
	 @Override
	    public ExecuteResult<List<PromotionBargainInfoResDTO>> updateBargainInfo(List<PromotionBargainInfoResDTO> bargainInfoList)
	    	throws PromotionCenterBusinessException {
	        ExecuteResult<List<PromotionBargainInfoResDTO>> result = new ExecuteResult<List<PromotionBargainInfoResDTO>>();
	        PromotionAccumulatyDTO accuDTO = null;
	        PromotionInfoDTO promotionInfoDTO = null;
	        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
			List<PromotionAccumulatyDTO> accuDTOList = new ArrayList<PromotionAccumulatyDTO>();
	        List<PromotionStatusHistoryDTO> historyList = null;
	        String promotionId = "";
	        String modifyTimeStr = "";
	        String paramModifyTimeStr = "";
	        String status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst
	                .OPT_PROMOTION_STATUS_END);
	        try {
	        	if(null != bargainInfoList && !bargainInfoList.isEmpty()){
					promotionId = bargainInfoList.get(0).getPromotionId();
					String promotionType = dictionary.getValueByCode(
							DictionaryConst.TYPE_PROMOTION_TYPE,
							DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN);
					String promotionProviderType = dictionary.getValueByCode(
							DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE, 
							DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_MEMBER_SHOP);
					for (PromotionBargainInfoResDTO dto : bargainInfoList) {
						if(StringUtils.isEmpty(dto.getPromotionType())){
							dto.setPromotionType(promotionType);
						}
						if(StringUtils.isEmpty(dto.getPromotionProviderType())){
							dto.setPromotionProviderType(promotionProviderType);
						}
						// 输入DTO的验证
						ValidateResult validateResult = ValidationUtils
								.validateEntity(dto);
						if (validateResult.isHasErrors()) {
							throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
				                  validateResult.getErrorMsg());
						}
						dto.setGoodsCostPrice(CalculateUtils
								.setScale(dto.getGoodsCostPrice()));
						dto.setGoodsFloorPrice(CalculateUtils
								.setScale(dto.getGoodsFloorPrice()));
							accuDTOList.add(dto);
	            if (StringUtils.isEmpty(promotionId)) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "修改砍价活动ID不能为空");
	            }
	            paramModifyTimeStr = DateUtils.format(bargainInfoList.get(0).getModifyTime(), DateUtils.YMDHMS);
	            // 根据活动ID获取活动信息
	            promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
	            if (promotionInfoDTO == null) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "砍价活动不存在");
	            }
	            if (status.equals(promotionInfoDTO.getShowStatus())) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_STATUS_NOT_CORRECT.getCode(),
	                        "砍价活动:" + promotionId + " 只有在已结束状态时不能进行修改");
	            }
	            modifyTimeStr = DateUtils.format(promotionInfoDTO.getModifyTime(), DateUtils.YMDHMS);
	            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_HAS_MODIFIED.getCode(),
	                        "砍价活动:" + promotionId + " 已被修改请重新确认");
	            }
	            accuDTO = baseService.updateSingleAccumulatyPromotionInfo(accuDTOList);
	            historyDTO.setPromotionId(accuDTO.getPromotionId());
	            historyDTO.setPromotionStatus(accuDTO.getShowStatus());
	            historyDTO.setPromotionStatusText("修改砍价活动信息");
	            historyDTO.setCreateId(accuDTO.getCreateId());
	            historyDTO.setCreateName(accuDTO.getCreateName());
	            promotionStatusHistoryDAO.add(historyDTO);
	            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
	            accuDTO.setPromotionStatusHistoryList(historyList);
	            promotionBargainRedisHandle.deleteRedisBargainInfo(promotionId);
	            promotionBargainRedisHandle.addBargainInfo2Redis(bargainInfoList);
	            result.setResult(bargainInfoList);	
				}
			}
	        } catch (PromotionCenterBusinessException pbe) {
	            result.setCode(pbe.getCode());
	            result.setErrorMessage(pbe.getMessage());
	        } catch (Exception e) {
	            result.setCode(ResultCodeEnum.ERROR.getCode());
	            result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
	        }	
	        return result;
	 }

	@Override
	public ExecuteResult<DataGrid<PromotonInfoResDTO>> queryPromotionInfoListBySellerCode(
			PromotionInfoReqDTO reqDTO, Pager<PromotionInfoReqDTO> page) throws PromotionCenterBusinessException {
		DataGrid<PromotonInfoResDTO> dataGrid = new DataGrid<PromotonInfoResDTO>();
		ExecuteResult<DataGrid<PromotonInfoResDTO>> result = new ExecuteResult<DataGrid<PromotonInfoResDTO>>();
		List<PromotonInfoResDTO> resList = new ArrayList<PromotonInfoResDTO>();
		try {
			List<PromotionInfoDTO> promotionInfoList = promotionInfoDAO.queryPromotionInfoListBySellerCode(reqDTO, page);
			Long promotionInfoCount = promotionInfoDAO.queryPromotionInfoCountBySellerCode(reqDTO);
			if(null != promotionInfoList && !promotionInfoList.isEmpty()){
				for (PromotionInfoDTO promotionInfo : promotionInfoList) {
					PromotonInfoResDTO resDTO = new PromotonInfoResDTO();
					resDTO.setPromotionId(promotionInfo.getPromotionId());
					resDTO.setPromotionName(promotionInfo.getPromotionName());
					resDTO.setEffectiveTime(promotionInfo.getEffectiveTime());
					resDTO.setInvalidTime(promotionInfo.getInvalidTime());
					resDTO.setStatus(promotionInfo.getStatus());
					//参砍商品种类
					List<PromotionAccumulatyDTO> accumulatyList = promotionAccumulatyDAO.queryPromotionAccumulatyByPromotionId(resDTO.getPromotionId());
					resDTO.setBargainType(accumulatyList == null ? 0 : accumulatyList.size());
					//未砍商品数量
					Integer joinTypeQTY = buyerBargainRecordDAO.queryPromotionBargainJoinTypeQTY(resDTO.getPromotionId());
					Integer bargainQTY = promotionBargainInfoDAO.queryPromotionBargainStockSum(resDTO.getPromotionId());
					if(joinTypeQTY != null && bargainQTY != null){
						resDTO.setNoBargainItemQTY(bargainQTY.intValue() - joinTypeQTY.intValue());
					}else{
						resDTO.setNoBargainItemQTY(0);
					}
					//发起砍价人数
					Integer launchQTY = buyerBargainRecordDAO.queryPromotionBargainJoinQTY(resDTO.getPromotionId());
					resDTO.setLaunchBargainQTY(launchQTY == null ? 0 : launchQTY.intValue());
					resList.add(resDTO);
				}
			}
			dataGrid.setRows(resList);
			dataGrid.setTotal(promotionInfoCount);
			result.setResult(dataGrid);
		} catch (Exception e) {
			 result.setCode(ResultCodeEnum.ERROR.getCode());
	         result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> queryPromotionBargainOverview(
			String sellerCode, Pager<String> page) {
		DataGrid<PromotionBargainOverviewResDTO> dataGrid = new DataGrid<PromotionBargainOverviewResDTO>();
		ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>> result = new ExecuteResult<DataGrid<PromotionBargainOverviewResDTO>>();
		List<PromotionBargainOverviewResDTO> resList = new ArrayList<PromotionBargainOverviewResDTO>();
		BuyerBargainLaunchReqDTO launchDTO = new BuyerBargainLaunchReqDTO();
		try {
			List<PromotionBargainInfoDMO> bargainList  = promotionBargainInfoDAO.queryPromotionBargainBySellerCode(sellerCode, page);
			Long bargainCount = promotionBargainInfoDAO.queryPromotionBargainCountBySellerCode(sellerCode);
			if(null != bargainList && !bargainList.isEmpty()){
				for (PromotionBargainInfoDMO dmo : bargainList) {
					PromotionBargainOverviewResDTO resDTO = new PromotionBargainOverviewResDTO();
					resDTO.setPromotionId(dmo.getPromotionId());
					resDTO.setGoodsName(dmo.getGoodsName());
					resDTO.setGoodsCostPrice(dmo.getGoodsCostPrice() == null ? BigDecimal.ZERO : dmo.getGoodsCostPrice());
					resDTO.setGoodsFloorPrice(dmo.getGoodsFloorPrice() == null ? BigDecimal.ZERO : dmo.getGoodsFloorPrice());
					resDTO.setPartakeTimes(dmo.getPartakeTimes() == null ? 0 : dmo.getPartakeTimes());
					//已发起砍价数量
					launchDTO.setLevelCode(dmo.getLevelCode());
					launchDTO.setPromotionId(dmo.getPromotionId());
					List<BuyerLaunchBargainInfoResDTO> launchList = buyerLaunchBargainInfoDAO.queryLaunchBargainInfoList(launchDTO, null);
					resDTO.setLaunchTimes(launchList == null ? 0 : launchList.size());
					//已砍完数量
					launchDTO.setIsBargainOver(1);
					List<BuyerLaunchBargainInfoResDTO> overList = buyerLaunchBargainInfoDAO.queryLaunchBargainInfoList(launchDTO, null);
					resDTO.setOverTimes(overList == null ? 0 : overList.size());
					//剩余商品数量
					if(resDTO.getPartakeTimes().intValue() == 0 || resDTO.getPartakeTimes() < resDTO.getOverTimes()){
						resDTO.setSurplusTimes(0);
					}else{
						resDTO.setSurplusTimes(dmo.getPartakeTimes() - resDTO.getOverTimes());
					}
					resList.add(resDTO);
				}
			}
			dataGrid.setTotal(bargainCount);
			dataGrid.setRows(resList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}

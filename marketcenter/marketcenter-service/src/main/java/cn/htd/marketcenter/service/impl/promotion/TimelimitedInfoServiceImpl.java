package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.enums.TimelimitedStatusEnum;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dao.TimelimitedInfoDAO;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.domain.TimelimitedCheckInfo;
import cn.htd.marketcenter.dto.BuyerInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.PromotionValidDTO;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.TimelimitedInfoService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import cn.htd.membercenter.dto.MemberGroupDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("timelimitedInfoService")
public class TimelimitedInfoServiceImpl implements TimelimitedInfoService {

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private TimelimitedRedisHandle timelimitedRedisHandle;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;

    @Resource
    private TimelimitedInfoDAO timelimitedInfoDAO;

    @Override
    public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getMallTimelimitedList(String messageId,
            Pager<TimelimitedInfoDTO> page) {
        ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> result = new ExecuteResult<DataGrid<TimelimitedMallInfoDTO>>();
        DataGrid<TimelimitedMallInfoDTO> datagrid = null;
        try {
            datagrid = timelimitedRedisHandle
                    .getRedisTimelimitedInfoList(String.valueOf(YesNoEnum.NO.getValue()), "", page);
            result.setResult(datagrid);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getMallVIPTimelimitedList(String messageId,
            Pager<TimelimitedInfoDTO> page) {
        ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> result = new ExecuteResult<DataGrid<TimelimitedMallInfoDTO>>();
        DataGrid<TimelimitedMallInfoDTO> datagrid = null;
        try {
            datagrid = timelimitedRedisHandle
                    .getRedisTimelimitedInfoList(String.valueOf(YesNoEnum.YES.getValue()), "", page);
            result.setResult(datagrid);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<TimelimitedInfoDTO> getSkuTimelimitedInfo(String messageId, String skuCode) {
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        List<TimelimitedInfoDTO> tmpTimelimitedDTOList = null;
        List<String> skuCodeList = new ArrayList<String>();
        try {
            if (StringUtils.isEmpty(skuCode)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "商品编码不能为空");
            }
            skuCodeList.add(skuCode);
            tmpTimelimitedDTOList = timelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList);
            if (tmpTimelimitedDTOList == null || tmpTimelimitedDTOList.isEmpty()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.SKU_NO_TIMELIMITED, "该商品没有参加秒杀活动");
            }
            result.setResult(tmpTimelimitedDTOList.get(0));
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<Integer> getSkuTimelimitedAllCount(String messageId, String skuCode) {
        ExecuteResult<Integer> result = new ExecuteResult<Integer>();
        List<String> skuCodeList = new ArrayList<String>();
        List<String> promotionIdList = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        int allCount = 0;
        try {
            if (StringUtils.isEmpty(skuCode)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "商品编码不能为空");
            }
            skuCodeList.add(skuCode);
            promotionIdList = timelimitedRedisHandle.getRedisTimelimitedIndex("", skuCodeList, "", true);
            if (promotionIdList != null && !promotionIdList.isEmpty()) {
                for (String promotionId : promotionIdList) {
                    try {
                        timelimitedInfoDTO = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
                        if ((new Date()).after(timelimitedInfoDTO.getInvalidTime())) {
                            continue;
                        }
                        allCount += timelimitedInfoDTO.getTimelimitedSkuCount();
                    } catch (MarketCenterBusinessException bcbe) {
                        continue;
                    }
                }
            }
            result.setResult(allCount);
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<DataGrid<TimelimitedInfoDTO>> queryTimelimitedListByCondition(
            TimelimitedConditionDTO conditionDTO, Pager<TimelimitedInfoDTO> page) {
        ExecuteResult<DataGrid<TimelimitedInfoDTO>> result = new ExecuteResult<DataGrid<TimelimitedInfoDTO>>();
        DataGrid<TimelimitedInfoDTO> dataGrid = new DataGrid<TimelimitedInfoDTO>();
        TimelimitedInfoDTO searchConditionDTO = new TimelimitedInfoDTO();
        List<TimelimitedInfoDTO> timelimitedInfoList = new ArrayList<TimelimitedInfoDTO>();
        PromotionAccumulatyDTO accuDTO = null;
        long count = 0;
        try {
            searchConditionDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                    DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
            searchConditionDTO.setSkuCode(conditionDTO.getSkuCode());
            searchConditionDTO.setSkuName(conditionDTO.getSkuName());
            searchConditionDTO.setStatus(conditionDTO.getStatus());
            searchConditionDTO.setPromotionProviderSellerCode(conditionDTO.getSelleCode());
            searchConditionDTO.setDeleteStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            count = timelimitedInfoDAO.queryCount(searchConditionDTO);
            if (count > 0) {
                timelimitedInfoList = timelimitedInfoDAO.queryList(searchConditionDTO, page);
                for (TimelimitedInfoDTO timelimitedInfo : timelimitedInfoList) {
                    accuDTO = baseService.querySingleAccumulatyPromotionInfo(timelimitedInfo.getPromotionId(),
                            timelimitedInfo.getLevelCode());
                    timelimitedInfo.setPromotionAccumulaty(accuDTO);
                }
                dataGrid.setRows(timelimitedInfoList);
            }
            dataGrid.setTotal(count);
            result.setResult(dataGrid);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<TimelimitedMallInfoDTO> getMallTimelimitedInfo(String messageId, String promotionId,
            String buyerCode, String buyerGrade) {
        ExecuteResult<TimelimitedMallInfoDTO> result = new ExecuteResult<TimelimitedMallInfoDTO>();
        TimelimitedInfoDTO tmpTimelimitedDTO = null;
        TimelimitedMallInfoDTO timelimitedDTO = null;
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();
        TimelimitedResultDTO timelimitedResultDTO = null;
        String sellerCode = "";
        BuyerInfoDTO buyerInfo = new BuyerInfoDTO();
        MemberGroupDTO memberGroup = null;
        String returnCode = "";
        try {
            tmpTimelimitedDTO = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
            timelimitedResultDTO = tmpTimelimitedDTO.getTimelimitedResult();
            timelimitedDTO = new TimelimitedMallInfoDTO();
            timelimitedDTO.setTimelimitedInfo(tmpTimelimitedDTO);
            timelimitedDTO.setRemainCount(timelimitedResultDTO.getShowRemainSkuCount());
            if (timelimitedResultDTO.getShowRemainSkuCount() <= 0) {
                timelimitedDTO.setRemainCount(0);
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
                returnCode = MarketCenterCodeConst.TIMELIMITED_SKU_NO_REMAIN;
            } else if ((new Date()).before(timelimitedDTO.getEffectiveTime())) {
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
                returnCode = MarketCenterCodeConst.PROMOTION_NO_START;
            } else if ((new Date()).after(timelimitedDTO.getInvalidTime())) {
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
                returnCode = MarketCenterCodeConst.PROMOTION_HAS_EXPIRED;
            } else {
                timelimitedDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());
            }
            timelimitedDTO.setShowStatusStr(TimelimitedStatusEnum.getName(timelimitedDTO.getCompareStatus()));
            if (StringUtils.isEmpty(returnCode) && !StringUtils.isEmpty(buyerCode)) {
                sellerCode = timelimitedDTO.getPromotionProviderSellerCode();
                if (!StringUtils.isEmpty(sellerCode)) {
                    buyerInfo.setBuyerCode(buyerCode);
                    buyerInfo.setSellerCode(sellerCode);
                    memberGroup = baseService.getBuyerGroupRelationship(messageId, buyerInfo);
                    buyerCheckInfo.setBuyerGroupId(memberGroup != null ? String.valueOf(memberGroup.getGroupId()) : "");
                }
                if (StringUtils.isEmpty(returnCode)) {
                    buyerCheckInfo.setBuyerCode(buyerCode);
                    buyerCheckInfo.setBuyerGrade(buyerGrade);
                    if (!baseService.checkPromotionBuyerRule(timelimitedDTO, buyerCheckInfo)) {
                        returnCode = MarketCenterCodeConst.TIMELIMITED_BUYER_NO_AUTHIORITY;
                    }
                    if (!timelimitedRedisHandle.checkBuyerTimelimitedCondition(promotionId, buyerCode)) {
                        returnCode = MarketCenterCodeConst.BUYER_HAS_TIMELIMITED_ERROR;
                    }
                }
            }
            result.setCode(returnCode);
            result.setResult(timelimitedDTO);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<TimelimitedInfoDTO> queryTimelimitedInfo(String promotionId) {
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        PromotionAccumulatyDTO accuDTO = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        TimelimitedResultDTO timelimitedResultDTO = null;
        List<PromotionStatusHistoryDTO> historyList = null;
        try {
            accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId);
            // 获取秒杀活动信息
            timelimitedInfoDTO = timelimitedInfoDAO.queryTimelimitedInfoById(accuDTO);
            if (timelimitedInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该秒杀活动不存在!");
            }
            timelimitedInfoDTO.setPromotionAccumulaty(accuDTO);
            // 获取秒杀活动结果信息
            timelimitedResultDTO =
                    timelimitedRedisHandle.getRedisTimelimitedResult(timelimitedInfoDTO.getPromotionId());
            timelimitedInfoDTO.setTimelimitedResult(timelimitedResultDTO);
            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
            timelimitedInfoDTO.setPromotionStatusHistoryList(historyList);
            result.setResult(timelimitedInfoDTO);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
        try {
            timelimitedInfo.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                    DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(timelimitedInfo);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            checkTimelimitedDuringRepeat(timelimitedInfo);
            timelimitedInfo.setSkuTimelimitedPrice(CalculateUtils.setScale(timelimitedInfo.getSkuTimelimitedPrice()));
            timelimitedInfo.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID));
            accuDTO = baseService.insertSingleAccumulatyPromotionInfo(timelimitedInfo);
            timelimitedInfo.setPromotionAccumulaty(accuDTO);
            timelimitedInfoDAO.add(timelimitedInfo);
            historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
            historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
            historyDTO.setPromotionStatusText(dictionary
                    .getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, timelimitedInfo.getShowStatus()));
            historyDTO.setCreateId(timelimitedInfo.getCreateId());
            historyDTO.setCreateName(timelimitedInfo.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);
            historyList.add(historyDTO);
            timelimitedInfo.setPromotionStatusHistoryList(historyList);
            timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
            result.setResult(timelimitedInfo);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 校验秒杀信息是否有重复期间商品的秒杀活动存在
     *
     * @param timelimitedInfo
     * @throws MarketCenterBusinessException
     */
    private void checkTimelimitedDuringRepeat(TimelimitedInfoDTO timelimitedInfo) throws MarketCenterBusinessException {
        List<PromotionInfoDTO> promotionList = null;
        List<String> promotionIdList = new ArrayList<String>();
        TimelimitedCheckInfo condition = new TimelimitedCheckInfo();
        PromotionInfoDTO promotionInfo = null;
        String errorMsg = "";

        condition.setPromotionType(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
        condition.setDeleteStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
        condition.setSkuCode(timelimitedInfo.getSkuCode());
        condition.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
        promotionList = promotionInfoDAO.queryRepeatTimelimitedList(condition);
        if (promotionList != null && !promotionList.isEmpty()) {
            promotionInfo = promotionList.get(0);
            errorMsg = promotionInfo.getPromotionName();
            throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_DURING_REPEAT,
                    "和 " + errorMsg + " 秒杀活动的商品重复");
        } else {
            if (StringUtils.isNotBlank(timelimitedInfo.getPromotionProviderSellerCode())) {
                promotionList = promotionInfoDAO
                        .queryTimelimitedListBySku(condition, timelimitedInfo.getPromotionProviderSellerCode());
                if (promotionList != null && !promotionList.isEmpty()) { //针对VMS 秒杀更新活动已经结束，但状态为启用的促销活动
                    for (PromotionInfoDTO promotion : promotionList) {
                        promotionIdList.add(promotion.getPromotionId());
                    }
                    promotionInfoDAO.updateTimelimitedListBySku(promotionIdList, timelimitedInfo);
                }
            }
        }
    }

    @Override
    public ExecuteResult<String> saveTimelimitedValidStatus(PromotionValidDTO validDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionInfoDTO promotionInfo = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        PromotionStatusHistoryDTO startHistoryDTO = null;
        List<TimelimitedInfoDTO> timelimitedInfoList = null;
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(validDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            if (!dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validDTO.getShowStatus()) && !dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                            DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID).equals(validDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "促销活动启用状态不正确");
            }
            // 根据活动ID获取活动信息
            promotionInfo = promotionInfoDAO.queryById(validDTO.getPromotionId());
            if (promotionInfo == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该秒杀活动不存在");
            }
            timelimitedInfoList = timelimitedInfoDAO.queryTimelimitedInfoByPromotionId(validDTO.getPromotionId());
            if (timelimitedInfoList == null || timelimitedInfoList.isEmpty()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该秒杀活动不存在!");
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validDTO.getShowStatus())) {
                if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_NO_START).equals(promotionInfo.getStatus())
                        && !(new Date()).before(promotionInfo.getEffectiveTime()) && !(new Date())
                        .after(promotionInfo.getInvalidTime())) {
                    promotionInfo.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                            DictionaryConst.OPT_PROMOTION_STATUS_START));
                    startHistoryDTO = new PromotionStatusHistoryDTO();
                    startHistoryDTO.setPromotionId(promotionInfo.getPromotionId());
                    startHistoryDTO.setPromotionStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                            DictionaryConst.OPT_PROMOTION_STATUS_START));
                    startHistoryDTO.setPromotionStatusText(dictionary
                            .getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS,
                                    startHistoryDTO.getPromotionStatus()));
                    startHistoryDTO.setCreateId(validDTO.getOperatorId());
                    startHistoryDTO.setCreateName(validDTO.getOperatorName());
                }
            }
            promotionInfo.setShowStatus(validDTO.getShowStatus());
            promotionInfo.setModifyId(validDTO.getOperatorId());
            promotionInfo.setModifyName(validDTO.getOperatorName());
            promotionInfoDAO.savePromotionValidStatus(promotionInfo);
            historyDTO.setPromotionId(promotionInfo.getPromotionId());
            historyDTO.setPromotionStatus(validDTO.getShowStatus());
            historyDTO.setPromotionStatusText(
                    dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, validDTO.getShowStatus()));
            historyDTO.setCreateId(validDTO.getOperatorId());
            historyDTO.setCreateName(validDTO.getOperatorName());
            promotionStatusHistoryDAO.add(historyDTO);
            if (startHistoryDTO != null) {
                promotionStatusHistoryDAO.add(startHistoryDTO);
            }
            timelimitedRedisHandle.saveTimelimitedValidStatus2Redis(promotionInfo);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ExecuteResult<String> deleteTimelimitedInfo(PromotionValidDTO validDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(validDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            baseService.deletePromotionInfo(validDTO);
            historyDTO.setPromotionId(validDTO.getPromotionId());
            historyDTO.setPromotionStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
            historyDTO.setPromotionStatusText(
                    dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, historyDTO.getPromotionStatus()));
            historyDTO.setCreateId(validDTO.getOperatorId());
            historyDTO.setCreateName(validDTO.getOperatorName());
            promotionStatusHistoryDAO.add(historyDTO);
            timelimitedRedisHandle.deleteRedisTimelimitedInfo(validDTO.getPromotionId());
            result.setResult("处理成功");
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ExecuteResult<String> updateTimelimitedResultShowCount(TimelimitedResultDTO resultDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try {
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(resultDTO);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            timelimitedRedisHandle.updateRedisTimelimitedShowResult(resultDTO);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * @param timelimitedInfo
     * @author li.jun
     * @time 2017-07-04
     * @desc 更新VMS设置的秒杀活动信息
     */
    @Override
    public ExecuteResult<TimelimitedInfoDTO> updateTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) {
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionInfoDTO promotionInfoDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = null;
        String promotionId = timelimitedInfo.getPromotionId();
        String modifyTimeStr = "";
        String paramModifyTimeStr = "";
        String status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
        // 输入DTO的验证
        ValidateResult validateResult = ValidationUtils.validateEntity(timelimitedInfo);
        // 有错误信息时返回错误信息
        if (validateResult.isHasErrors()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                    validateResult.getErrorMsg());
        }
        try {
            if (StringUtils.isEmpty(promotionId) || StringUtils.isEmpty(timelimitedInfo.getLevelCode())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "修改秒杀活动ID和层级编码不能为空");
            }
            checkTimelimitedParameterValidity(timelimitedInfo);
            paramModifyTimeStr = DateUtils.format(timelimitedInfo.getModifyTime(), DateUtils.YMDHMS);
            // 根据活动ID获取活动信息
            promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
            if (promotionInfoDTO == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "秒杀活动不存在");
            }
            if (!(new Date()).before(promotionInfoDTO.getEffectiveTime()) || !status
                    .equals(promotionInfoDTO.getShowStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_STATUS_NOT_CORRECT,
                        "VMS秒杀活动:" + promotionId + " 只有在未启用状态时才能修改");
            }
            modifyTimeStr = DateUtils.format(promotionInfoDTO.getModifyTime(), DateUtils.YMDHMS);
            if (!modifyTimeStr.equals(paramModifyTimeStr)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_MODIFIED,
                        "VMS秒杀活动:" + promotionId + " 已被修改请重新确认");
            }
            timelimitedInfo.setShowStatus(status);
            accuDTO = baseService.updateSingleAccumulatyPromotionInfo(timelimitedInfo);
            timelimitedInfo.setPromotionAccumulaty(accuDTO);
            timelimitedInfoDAO.update(timelimitedInfo);
            historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
            historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
            historyDTO.setPromotionStatusText("修改VMS秒杀活动信息");
            historyDTO.setCreateId(timelimitedInfo.getCreateId());
            historyDTO.setCreateName(timelimitedInfo.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);
            historyList = promotionStatusHistoryDAO.queryByPromotionId(promotionId);
            timelimitedInfo.setPromotionStatusHistoryList(historyList);
            timelimitedRedisHandle.deleteRedisTimelimitedInfo(promotionId);
            timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
            result.setResult(timelimitedInfo);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * @param timelimitedInfo
     * @author li.jun
     * @time 2017-07-05
     * @desc 校验VMS设置的秒杀参数的合法性
     */
    public void checkTimelimitedParameterValidity(TimelimitedInfoDTO timelimitedInfo) {
        if (timelimitedInfo.getBuyerRuleDTO() == null || StringUtils
                .isNotBlank(timelimitedInfo.getBuyerRuleDTO().getTargetBuyerGroup())) {
            new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "修改秒杀活动时层级买家规则和分组会员不能为空");
        }
        if (timelimitedInfo.getTimelimitedThreshold() > timelimitedInfo.getTimelimitedSkuCount()) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "修改秒杀活动时每人限购量不能大于秒杀库存");
        }

    }

}

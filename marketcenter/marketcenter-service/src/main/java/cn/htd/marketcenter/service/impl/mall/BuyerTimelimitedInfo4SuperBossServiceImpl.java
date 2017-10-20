package cn.htd.marketcenter.service.impl.mall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.enums.TimelimitedStatusEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.DateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.dto.BuyerInfoDTO;
import cn.htd.marketcenter.dto.BuyerTimelimitedInfo4SuperBossDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;
import cn.htd.marketcenter.service.BuyerTimelimitedInfo4SuperBossService;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import cn.htd.membercenter.dto.MemberGroupDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("buyerTimelimitedInfo4SuperBossService")
public class BuyerTimelimitedInfo4SuperBossServiceImpl implements BuyerTimelimitedInfo4SuperBossService {

    private static final Logger logger = LoggerFactory.getLogger(BuyerTimelimitedInfo4SuperBossServiceImpl.class);

    @Resource
    private TimelimitedRedisHandle timelimitedRedisHandle;

    @Resource
    private PromotionBaseService baseService;
    
    @Resource
    private DictionaryUtils dictionary;

    @Override
    public ExecuteResult<BuyerTimelimitedInfo4SuperBossDTO> getBuyerTimelimitedInfo(String messageId, String buyerCode,
            String promotionId) {
        ExecuteResult<BuyerTimelimitedInfo4SuperBossDTO> result =
                new ExecuteResult<BuyerTimelimitedInfo4SuperBossDTO>();
        TimelimitedInfoDTO timelimitedInfo = null;
        BuyerTimelimitedInfo4SuperBossDTO sbDTO = new BuyerTimelimitedInfo4SuperBossDTO();
        int threshold = 0;
        int hasBuyCount = 0;
        try {
            if (StringUtils.isEmpty(promotionId) || StringUtils.isEmpty(buyerCode)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "会员编号和秒杀活动编号不能为空");
            }
            timelimitedInfo = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
            threshold = timelimitedInfo.getTimelimitedThreshold();
            hasBuyCount = timelimitedRedisHandle.getBuyerTimelimitedCount(buyerCode, promotionId);
            sbDTO.setBuyProductCountLimit(threshold);
            sbDTO.setHasBuyProductCount(hasBuyCount);
            result.setResult(sbDTO);
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
    public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getAllMallTimelimitedList(String messageId,
            Pager<TimelimitedInfoDTO> page) {
        ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> result = new ExecuteResult<DataGrid<TimelimitedMallInfoDTO>>();
        DataGrid<TimelimitedMallInfoDTO> datagrid = null;
        String promotionType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        try {
            datagrid = timelimitedRedisHandle.getRedisTimelimitedInfoList("", "", page,promotionType);
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
    public ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> getVmsMallTimelimitedList(String messageId,
            BuyerInfoDTO buyerInfo, Pager<TimelimitedInfoDTO> page) {
        ExecuteResult<DataGrid<TimelimitedMallInfoDTO>> result = new ExecuteResult<DataGrid<TimelimitedMallInfoDTO>>();
        DataGrid<TimelimitedMallInfoDTO> datagrid = new DataGrid<TimelimitedMallInfoDTO>();
        List<TimelimitedMallInfoDTO> mallDTOList = new ArrayList<TimelimitedMallInfoDTO>();
        List<TimelimitedMallInfoDTO> mallAllDTOList = new ArrayList<TimelimitedMallInfoDTO>();
        List<String> promotionIdList = null;
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        TimelimitedMallInfoDTO timelimitedMallDTO = null;
        TimelimitedResultDTO timelimitedResultDTO = null;
        MemberGroupDTO memberGroup = null;
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();
        Date nowDt = new Date();
        int count = 0;
        long total = 0;
        int offset = 0;
        int rows = Integer.MAX_VALUE;
        String buyerCode = buyerInfo.getBuyerCode();
        String sellerCode = buyerInfo.getSellerCode();
        String promotionType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        try {
            if (page != null) {
                offset = page.getPageOffset();
                rows = page.getRows();
            }
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(buyerInfo);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            promotionIdList = timelimitedRedisHandle.getRedisTimelimitedIndex("", null, sellerCode, false, promotionType);
            if (promotionIdList != null && !promotionIdList.isEmpty()) {
                memberGroup = baseService.getBuyerGroupRelationship(messageId, buyerInfo);
                buyerCheckInfo.setBuyerCode(buyerCode);
                buyerCheckInfo.setBuyerGroupId(memberGroup != null ? String.valueOf(memberGroup.getGroupId()) : "");
                for (String promotionId : promotionIdList) {
                    try {
                        timelimitedInfoDTO = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
                        timelimitedResultDTO = timelimitedInfoDTO.getTimelimitedResult();
                        timelimitedMallDTO = new TimelimitedMallInfoDTO();
                        timelimitedMallDTO.setTimelimitedInfo(timelimitedInfoDTO);
                        timelimitedMallDTO.setRemainCount(timelimitedResultDTO.getShowRemainSkuCount());
                        if (nowDt.after(DateUtils.getSpecifiedDay(timelimitedInfoDTO.getInvalidTime(), 2))) {
                            continue;
                        }
                        if (!baseService.checkPromotionBuyerRule(timelimitedInfoDTO, buyerCheckInfo)) {
                            continue;
                        }
                        if (timelimitedResultDTO.getShowRemainSkuCount() <= 0) {
                            timelimitedMallDTO.setRemainCount(0);
                            timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.CLEAR.getValue());
                        } else if (nowDt.before(timelimitedInfoDTO.getEffectiveTime())) {
                            timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.NO_START.getValue());
                        } else if (nowDt.after(timelimitedInfoDTO.getInvalidTime())) {
                            timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.ENDED.getValue());
                        } else {
                            timelimitedMallDTO.setCompareStatus(TimelimitedStatusEnum.PROCESSING.getValue());
                        }
                        timelimitedMallDTO
                                .setShowStatusStr(
                                        TimelimitedStatusEnum.getName(timelimitedMallDTO.getCompareStatus()));
                        mallAllDTOList.add(timelimitedMallDTO);
                    } catch (MarketCenterBusinessException bcbe) {
                        continue;
                    }
                }
                if (!mallAllDTOList.isEmpty()) {
                    total = mallAllDTOList.size();
                    Collections.sort(mallAllDTOList);
                    while (total > count) {
                        if (count >= offset && mallDTOList.size() < rows) {
                            mallDTOList.add(mallAllDTOList.get(count));
                        }
                        if (mallDTOList.size() >= rows) {
                            break;
                        }
                        count++;
                    }
                    datagrid.setTotal(total);
                    datagrid.setRows(mallDTOList);
                }
            }
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
}

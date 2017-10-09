package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dao.TimelimitedInfoDAO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import cn.htd.marketcenter.service.TimelimitedPurchaseService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("timelimitedPurchaseService")
public class TimelimitedPurchaseServiceImpl implements TimelimitedPurchaseService {

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


	/**
	 * 限时购  -  新增限时购活动信息
	 * @author li.jun
	 * @time 2017-10-09
	 * @param timelimitedInfo
	 * @return
	 */
    @Override
    public ExecuteResult<TimelimitedInfoDTO> addTimelimitedInfo(TimelimitedInfoDTO timelimitedInfo) { 
        ExecuteResult<TimelimitedInfoDTO> result = new ExecuteResult<TimelimitedInfoDTO>();
        PromotionInfoDTO promotionInfo=null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        List<PromotionStatusHistoryDTO> historyList = new ArrayList<PromotionStatusHistoryDTO>();
        try {
            timelimitedInfo.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
            timelimitedInfo.setSkuTimelimitedPrice(CalculateUtils.setScale(timelimitedInfo.getSkuTimelimitedPrice()));
            promotionInfo = baseService.insertPromotionInfo(timelimitedInfo);
            timelimitedInfo.setPromoionInfo(promotionInfo);
            List<PromotionAccumulatyDTO> accumulatyList = timelimitedInfo.getPromotionAccumulatyList();
            if(accumulatyList.size() > 0) {
            	for(PromotionAccumulatyDTO accumulaty: accumulatyList){ 
                    timelimitedInfoDAO.add((TimelimitedInfoDTO) accumulaty);
            	}
            }
            historyDTO.setPromotionId(timelimitedInfo.getPromotionId());
            historyDTO.setPromotionStatus(timelimitedInfo.getShowStatus());
            historyDTO.setPromotionStatusText(dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    timelimitedInfo.getShowStatus()));
            historyDTO.setCreateId(timelimitedInfo.getCreateId());
            historyDTO.setCreateName(timelimitedInfo.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);
            historyList.add(historyDTO);
            timelimitedInfo.setPromotionStatusHistoryList(historyList);
            //timelimitedRedisHandle.addTimelimitedInfo2Redis(timelimitedInfo);
            timelimitedRedisHandle.addTimelimitedPurchaseInfo2Redis(timelimitedInfo);
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
    
    
}

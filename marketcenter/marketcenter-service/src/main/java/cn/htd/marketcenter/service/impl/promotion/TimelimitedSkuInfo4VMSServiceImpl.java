package cn.htd.marketcenter.service.impl.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedSkuCountDTO;
import cn.htd.marketcenter.service.TimelimitedSkuInfo4VMSService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("timelimitedSkuInfo4VMSService")
public class TimelimitedSkuInfo4VMSServiceImpl implements TimelimitedSkuInfo4VMSService {

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private TimelimitedRedisHandle timelimitedRedisHandle;

    @Override
    public ExecuteResult<TimelimitedSkuCountDTO> getSkuTimelimitedAllCount(String messageId, String skuCode) {
        ExecuteResult<TimelimitedSkuCountDTO> result = new ExecuteResult<TimelimitedSkuCountDTO>();
        TimelimitedSkuCountDTO countDTO = new TimelimitedSkuCountDTO();
        List<String> skuCodeList = new ArrayList<String>();
        List<String> promotionIdList = null;
        String promotionType = dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        String dictValidStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
        TimelimitedInfoDTO timelimitedInfo = null;
        String timelimitedJsonStr = "";
        String validStatus = "";
        Date nowDt = new Date();

        try {
            if (StringUtils.isEmpty(skuCode)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "商品编码不能为空");
            }
            skuCodeList.add(skuCode);
            promotionIdList = timelimitedRedisHandle.getRedisTimelimitedIndex("", skuCodeList, "", true, promotionType);
            if (promotionIdList != null && !promotionIdList.isEmpty()) {
                for (String promotionId : promotionIdList) {
                    timelimitedJsonStr = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED, promotionId);
                    timelimitedInfo = JSON.parseObject(timelimitedJsonStr, TimelimitedInfoDTO.class);
                    if (timelimitedInfo == null) {
                        continue;
                    }
                    if (nowDt.after(timelimitedInfo.getInvalidTime())) {
                        continue;
                    }
                    validStatus = marketRedisDB.getHash(RedisConst.REDIS_TIMELIMITED_VALID, promotionId);
                    if (!StringUtils.isEmpty(validStatus) && !dictValidStatus.equals(validStatus)) {
                        countDTO.setInvalidSkuCount(
                                countDTO.getInvalidSkuCount() + timelimitedInfo.getTimelimitedSkuCount());
                    } else {
                        countDTO.setValidSkuCount(
                                countDTO.getValidSkuCount() + timelimitedInfo.getTimelimitedSkuCount());
                    }
                }
            }
            result.setResult(countDTO);
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }
}

package cn.htd.promotion.cpc.api.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.api.PromotionTimelimitedInfoAPI;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.biz.service.PromotionTimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("promotionTimelimitedInfoAPI")
public class PromotionTimelimitedInfoAPIImpl implements PromotionTimelimitedInfoAPI {
    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionTimelimitedInfoService promotionTimelimitedInfoService;
    
    @Override
    public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode) {
        ExecuteResult<TimelimitedInfoResDTO> result = new ExecuteResult<TimelimitedInfoResDTO>();
        List<TimelimitedInfoResDTO> tmpTimelimitedDTOList = null;
        List<String> skuCodeList = new ArrayList<String>();
        try {
            if (StringUtils.isEmpty(skuCode)) {
                throw new PromotionCenterBusinessException(PromotionCenterConst.PARAMETER_ERROR, "商品编码不能为空");
            }
            skuCodeList.add(skuCode);
            tmpTimelimitedDTOList =  promotionTimelimitedInfoService.getSkuPromotionTimelimitedInfo(messageId,skuCode);
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



}

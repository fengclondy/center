package cn.htd.marketcenter.service.impl.mall;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.ReceiveTriggerCouponDTO;
import cn.htd.marketcenter.service.TriggerCouponReceiveService;
import cn.htd.marketcenter.service.handle.CouponRedisHandle;
import cn.htd.marketcenter.service.handle.PromotionRedisHandle;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("triggerCouponReceiveService")
public class TriggerCouponReceiveServiceImpl implements TriggerCouponReceiveService {

    protected static transient Logger logger = LoggerFactory.getLogger(TriggerCouponReceiveServiceImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisHandle promotionRedisHandle;

    @Resource
    private CouponRedisHandle couponRedisHandle;

    //----- discard by jiangkun for 2017活动需求商城无敌券 on 20170927 start -----
    @Deprecated
    @Override
    public ExecuteResult<String> saveReceiveTriggerCoupon(String messageId, ReceiveTriggerCouponDTO triggerDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        List<PromotionDiscountInfoDTO> triggerCouponList = null;
        try {
//            // 输入DTO的验证
//            ValidateResult validateResult = ValidationUtils.validateEntity(triggerDTO);
//            // 有错误信息时返回错误信息
//            if (validateResult.isHasErrors()) {
//                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
//                        validateResult.getErrorMsg());
//            }
//            if (BigDecimal.ZERO.compareTo(triggerDTO.getCouponAmount()) >= 0) {
//                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "返券金额不能小于等于0");
//            }
//            triggerCouponList = getTriggerCouponInfoList();
//            addSendTriggerCoupon2BuyerAccount(messageId, triggerDTO, triggerCouponList);
        } catch (MarketCenterBusinessException mcbe) {
            result.setCode(mcbe.getCode());
            result.addErrorMessage(mcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }
    //----- discard by jiangkun for 2017活动需求商城无敌券 on 20170927 end -----

    /**
     * 取得触发返券类型取得可以返券的优惠券信息
     *
     * @return
     * @throws MarketCenterBusinessException
     */
    private List<PromotionDiscountInfoDTO> getTriggerCouponInfoList() throws MarketCenterBusinessException {
        List<PromotionDiscountInfoDTO> couponList = null;

        try {
            couponList = couponRedisHandle.getRedisTriggerCouponInfoList();
            if (couponList == null || couponList.isEmpty()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_NO_TRIGGER_SEND, "没有对应的触发返券的促销活动");
            }
        } catch (MarketCenterBusinessException mcbe) {
            logger.warn("\n 方法:[{}],异常:[{}]", "TriggerCouponReceiveServiceImpl-getTriggerCouponInfoList",
                    JSON.toJSONString(mcbe));
            throw mcbe;
        }
        return couponList;
    }

    /**
     * 根据触发返券的金额发送优惠券到小B的账户
     *
     * @param messageId
     * @param triggerDTO
     * @param triggerCouponList
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private void addSendTriggerCoupon2BuyerAccount(String messageId, ReceiveTriggerCouponDTO triggerDTO,
                                                   List<PromotionDiscountInfoDTO> triggerCouponList)
            throws MarketCenterBusinessException, Exception {
        BigDecimal couponAmount = triggerDTO.getCouponAmount();
        BigDecimal discountAmount = BigDecimal.ZERO;
        PromotionDiscountInfoDTO discountInfo = null;
        BuyerCouponInfoDTO couponDTO = null;
        String actionKey = messageId + triggerDTO.getBuyerCode() + couponAmount.toString();
        try {
            for (PromotionDiscountInfoDTO triggerCouponDTO : triggerCouponList) {
                discountAmount = triggerCouponDTO.getDiscountAmount();
                if (discountAmount.compareTo(couponAmount) == 0) {
                    discountInfo = triggerCouponDTO;
                    break;
                }
            }
            if (discountInfo == null) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_NO_TRIGGER_SEND, "没有对应的触发返券的促销活动");
            }
            if (!promotionRedisHandle.lockRedisPromotionAction(messageId, actionKey)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LOCK_FAIL_ERROR, "触发返券活动正在处理中不能重复处理 "
                        + "messageId:" + messageId + " 参数:" + JSON.toJSONString(triggerDTO));
            }
            couponDTO = new BuyerCouponInfoDTO();
            couponDTO.setPromotionAccumulaty(discountInfo);
            couponDTO.setBuyerCode(triggerDTO.getBuyerCode());
            couponDTO.setBuyerName(triggerDTO.getBuyerName());
            couponDTO.setCouponName(discountInfo.getPromotionName());
            couponDTO.setCouponUseRang(discountInfo.getCouponUseRangeDesc());
            couponDTO.setCouponTargetItemLimit(discountInfo.getCouponItemDesc());
            couponDTO.setCouponDescribe(discountInfo.getPromotionDescribe());
            couponDTO.setPromotionProviderType(discountInfo.getPromotionProviderType());
            couponDTO.setPromotionProviderSellerCode(discountInfo.getPromotionProviderSellerCode());
            couponDTO.setPromotionProviderShopId(discountInfo.getPromotionProviderShopId());
            couponDTO.setCouponType(discountInfo.getCouponKind());
            couponDTO.setCouponStartTime(discountInfo.getEffectiveStartTime());
            couponDTO.setCouponEndTime(discountInfo.getEffectiveEndTime());
            couponDTO.setDiscountThreshold(discountInfo.getDiscountThreshold());
            couponDTO.setCouponAmount(discountInfo.getDiscountAmount());
            couponDTO.setDiscountPercent(discountInfo.getDiscountPercent());
            couponDTO.setReceiveLimit(discountInfo.getReceiveLimit());
            couponDTO.setCreateId(0L);
            couponDTO.setCreateName("sys");
            couponRedisHandle.saveBuyerCoupon2Redis(couponDTO);
            promotionRedisHandle.unlockRedisPromotionAction(actionKey);
        } catch (MarketCenterBusinessException mcbe) {
            logger.warn("\n 方法:[{}],异常:[{}],参数:[{}][{}]",
                    "TriggerCouponReceiveServiceImpl-addSendTriggerCoupon2BuyerAccount", JSON.toJSONString(mcbe),
                    JSON.toJSONString(triggerDTO), JSON.toJSONString(triggerCouponList));
            throw mcbe;
        }
    }
}

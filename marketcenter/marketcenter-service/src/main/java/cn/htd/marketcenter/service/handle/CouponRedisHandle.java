package cn.htd.marketcenter.service.handle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerUseCouponLog;
import cn.htd.marketcenter.dto.BuyerCouponConditionDTO;
import cn.htd.marketcenter.dto.BuyerCouponCountDTO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.BuyerReceiveCouponDTO;
import cn.htd.marketcenter.dto.OrderItemPromotionDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;
import cn.htd.marketcenter.dto.UsedExpiredBuyerCouponDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service("couponRedisHandle")
public class CouponRedisHandle {

    protected static transient Logger logger = LoggerFactory.getLogger(CouponRedisHandle.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionBaseService baseService;

    /**
     * 保存优惠券活动的启用状态
     *
     * @param promotionInfo
     */
    public void saveCouponValidStatus2Redis(PromotionInfoDTO promotionInfo) {
        marketRedisDB
                .setHash(RedisConst.REDIS_COUPON_VALID, promotionInfo.getPromotionId(), promotionInfo.getShowStatus());
    }

    /**
     * 保存优惠券活动信息进Redis
     *
     * @param couponInfo
     */
    public void addCouponInfo2Redis(PromotionDiscountInfoDTO couponInfo) {
        String couponProvideType = couponInfo.getCouponProvideType();
        String couponJsonStr = "";
        String couponRedisKey = "";

        //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
        marketRedisDB.setHash(RedisConst.REDIS_COUPON_VALID, couponInfo.getPromotionId(), dictionary.getValueByCode
                (DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
//        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
//                DictionaryConst.OPT_COUPON_PROVIDE_TRIGGER_SEND).equals(couponProvideType)) {
//            marketRedisDB.setHash(RedisConst.REDIS_COUPON_TRIGGER, couponInfo.getPromotionId(), couponJsonStr);
//        } else {
        baseService.deletePromotionUselessInfo(couponInfo);
        baseService.deleteSellerUselessInfo(couponInfo);
        baseService.deleteCategoryUselessInfo(couponInfo);
        couponJsonStr = JSON.toJSONString(couponInfo);
        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
            DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT).equals(couponProvideType)) {
            couponRedisKey = RedisConst.REDIS_COUPON_MEMBER_COLLECT + "_" + couponInfo.getPromotionId();
            baseService.deleteBuyerUselessInfo(couponInfo);
            couponJsonStr = JSON.toJSONString(couponInfo);
            marketRedisDB.setAndExpire(couponRedisKey, couponJsonStr, couponInfo.getPrepEndTime());
        }
        marketRedisDB.tailPush(RedisConst.REDIS_COUPON_NEED_DEAL_LIST, couponJsonStr);
//        }
        //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
    }

    /**
     * 查询优惠券的领取数量
     *
     * @param promotionId
     * @return
     */
    public Integer getRedisCouponReceiveCount(String promotionId) {
        String receiveCountStr = "";
        Integer receiveCount = 0;

        receiveCountStr = marketRedisDB.getHash(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId);
        if (!StringUtils.isEmpty(receiveCountStr)) {
            receiveCount = Integer.valueOf(receiveCountStr);
        }
        return receiveCount;
    }

    /**
     * 会员自助领取优惠券处理
     *
     * @param receiveDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerCouponInfoDTO receiveMemberCollectCoupon2Redis(BuyerReceiveCouponDTO receiveDTO)
            throws MarketCenterBusinessException {
        String promotionId = receiveDTO.getPromotionId();
        String buyerCode = receiveDTO.getBuyerCode();
        String couponRedisKey = RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId;
        String couponInfoKey = RedisConst.REDIS_COUPON_MEMBER_COLLECT + "_" + promotionId;
        String popedJsonStr = "";
        String validStatus = "";
        BuyerCouponInfoDTO popedCoupon = null;
        List<DictionaryInfo> promotionStatusList =
                dictionary.getDictionaryOptList(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS);
        Map<String, String> promotionStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : promotionStatusList) {
            promotionStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        try {
            validStatus = marketRedisDB.getHash(RedisConst.REDIS_COUPON_VALID, promotionId);
            if (!promotionStatusMap.get(DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(validStatus)
                    && !promotionStatusMap.get(DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(validStatus)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_VALID, "该优惠券活动已失效");
            }
            if (!marketRedisDB.exists(couponInfoKey)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_COLLECT_HAS_EXPIRED, "该优惠券领取期限已过");
            }
            if (marketRedisDB.getLlen(couponRedisKey) <= 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_TOTAL_COLLECTED, "该优惠券已领光");
            }
            popedJsonStr = marketRedisDB.headPop(couponRedisKey);
            popedCoupon = JSON.parseObject(popedJsonStr, BuyerCouponInfoDTO.class);
            popedCoupon.setBuyerCode(buyerCode);
            popedCoupon.setBuyerName(receiveDTO.getBuyerName());
            popedCoupon.setCreateId(receiveDTO.getOperatorId());
            popedCoupon.setCreateName(receiveDTO.getOperatorName());
        } catch (MarketCenterBusinessException mcbe) {
            if (popedCoupon != null) {
                marketRedisDB.tailPush(couponRedisKey, popedJsonStr);
            }
            throw mcbe;
        }
        return popedCoupon;
    }

    /**
     * 将取出的优惠券塞回到redis队列中
     *
     * @param couponInfoDTO
     */
    public void restoreMemberCollectCouponBack2Redis(BuyerCouponInfoDTO couponInfoDTO) {
        String couponRedisKey = RedisConst.REDIS_COUPON_COLLECT + "_" + couponInfoDTO.getPromotionId();
        marketRedisDB.tailPush(couponRedisKey, JSON.toJSONString(couponInfoDTO));
    }

    /**
     * 会员自助领取优惠券处理
     *
     * @param buyerCouponDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public Integer sendBuyerCoupon2Redis(BuyerCouponInfoDTO buyerCouponDTO) throws MarketCenterBusinessException {
        Integer sendedCount = 0;
        Integer receiveLimit = buyerCouponDTO.getReceiveLimit();
        String buyerCode = buyerCouponDTO.getBuyerCode();
        String promotionId = buyerCouponDTO.getPromotionId();
        String buyerCouponReceiveKey = buyerCode + "&" + promotionId;
        try {
            sendedCount = marketRedisDB.incrHash(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey)
                    .intValue();
            if (sendedCount > receiveLimit) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_RECEIVE_LIMITED,
                        "优惠券活动编码:" + promotionId + " 会员编号:" + buyerCode + " 已达优惠券领取上限");
            }
            saveBuyerCoupon2Redis(buyerCouponDTO);
        } catch (MarketCenterBusinessException bcbe) {
            marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey, -1);
            throw bcbe;
        }
        return sendedCount;
    }

    /**
     * 将优惠券发送到会员帐户中并添加进Redis中
     *
     * @param buyerCouponDTO
     * @return
     */
    public void saveBuyerCoupon2Redis(BuyerCouponInfoDTO buyerCouponDTO) {
        String buyerCode = buyerCouponDTO.getBuyerCode();
        String promotionId = buyerCouponDTO.getPromotionId();
        String buyerCouponCode = noGenerator.generateCouponCode(buyerCouponDTO.getCouponType());
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String couponJsonStr = "";
        long couponAmount = CalculateUtils.multiply(buyerCouponDTO.getCouponAmount(), new BigDecimal(100)).longValue();

        buyerCouponDTO.setBuyerCouponCode(buyerCouponCode);
        buyerCouponDTO.setGetCouponTime(new Date());
        buyerCouponDTO.setCouponLeftAmount(buyerCouponDTO.getCouponAmount());
        buyerCouponDTO.setStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_UNUSED));
        buyerCouponDTO.setBuyerRuleDTO(null);
        couponJsonStr = JSON.toJSONString(buyerCouponDTO);
        marketRedisDB.tailPush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, couponJsonStr);
        marketRedisDB.setHash(buyerCouponRedisKey, buyerCouponCode, couponJsonStr);
        marketRedisDB.setHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode,
                String.valueOf(couponAmount));
        marketRedisDB.incrHash(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId);
        marketRedisDB
                .tailPush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId, buyerCode + "&" + buyerCouponCode);
    }

    /**
     * 删除Redis中的买家优惠券信息
     *
     * @param targetCouponDTO
     * @throws MarketCenterBusinessException
     */
    public void deleteRedisExpiredBuyerCouponInfo(UsedExpiredBuyerCouponDTO targetCouponDTO)
            throws MarketCenterBusinessException {
        Date nowDt = new Date();
        String buyerCode = targetCouponDTO.getBuyerCode();
        String buyerCouponCode = targetCouponDTO.getCouponCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        String couponJsonStr = "";
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        couponJsonStr = marketRedisDB.getHash(buyerCouponRedisKey, buyerCouponCode);
        buyerCouponInfo = JSON.parseObject(couponJsonStr, BuyerCouponInfoDTO.class);
        if (buyerCouponInfo == null) {
            return;
        }
        if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
            if (nowDt.after(buyerCouponInfo.getCouponEndTime())) {
                buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID)
                    .equals(marketRedisDB.getHash(RedisConst.REDIS_COUPON_VALID, buyerCouponInfo.getPromotionId()))) {
                buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID));
            }
        }
        if (!couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
            buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_DELETED));
            buyerCouponInfo.setModifyId(targetCouponDTO.getOperatorId());
            buyerCouponInfo.setModifyName(targetCouponDTO.getOperatorName());
            couponJsonStr = JSON.toJSONString(buyerCouponInfo);
            marketRedisDB.setHash(buyerCouponRedisKey, buyerCouponCode, couponJsonStr);
            marketRedisDB
                    .tailPush(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCode + "_" + buyerCouponCode,
                            couponJsonStr);
            marketRedisDB.addSet(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCode + "_" + buyerCouponCode);
        }
    }

    /**
     * 删除Redis中的买家优惠券信息
     *
     * @param targetCouponDTO
     * @throws MarketCenterBusinessException
     */
    public void deleteRedisExpired3MonthBuyerCouponInfo(BuyerCouponInfoDTO targetCouponDTO)
            throws MarketCenterBusinessException {
        String buyerCode = targetCouponDTO.getBuyerCode();
        String buyerCouponCode = targetCouponDTO.getBuyerCouponCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String buyerCouponReceiveKey = buyerCode + "&" + targetCouponDTO.getPromotionId();
        String useLogRedisKey = buyerCode + "&" + buyerCouponCode;
        Set<String> useLogFields = null;
        marketRedisDB.delHash(buyerCouponRedisKey, buyerCouponCode);
        marketRedisDB.delHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode);
        marketRedisDB.delHash(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey);
        useLogFields = marketRedisDB.getHashFields(RedisConst.REDIS_BUYER_COUPON_USELOG);
        if (useLogFields != null && !useLogFields.isEmpty()) {
            for (String field : useLogFields) {
                if (field.indexOf("&" + useLogRedisKey) > 0) {
                    marketRedisDB.delHash(RedisConst.REDIS_BUYER_COUPON_USELOG, field);
                }
            }
        }
        marketRedisDB.delHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogRedisKey + "&REVERSE");
        marketRedisDB.delHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogRedisKey + "&REDUCE");
    }

    /**
     * 会员优惠券数量统计
     *
     * @param buyerCode
     * @return
     */
    public List<BuyerCouponCountDTO> getRedisBuyerCouponCount(String buyerCode) {
        List<BuyerCouponCountDTO> countResult = new ArrayList<BuyerCouponCountDTO>();
        Map<String, BuyerCouponCountDTO> countMap = new HashMap<String, BuyerCouponCountDTO>();
        BuyerCouponCountDTO couponCount = null;
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String buyerCouponCode = "";
        String couponKey = "";
        String couponValue = "";
        String buyerCouponStatus = "";
        BuyerCouponInfoDTO buyerCouponInfo = null;
        Map<String, String> buyerCouponMap = null;
        Long couponCountNum = 0L;
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }

        buyerCouponMap = marketRedisDB.getHashOperations(buyerCouponRedisKey);
        if (buyerCouponMap != null && !buyerCouponMap.isEmpty()) {
            for (Entry<String, String> entry : buyerCouponMap.entrySet()) {
                buyerCouponCode = entry.getKey();
                couponValue = entry.getValue();
                buyerCouponInfo = JSON.parseObject(couponValue, BuyerCouponInfoDTO.class);
                if (buyerCouponInfo == null) {
                    marketRedisDB.delHash(buyerCouponRedisKey, buyerCouponCode);
                    continue;
                }
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_DELETED)
                        .equals(buyerCouponInfo.getStatus())) {
                    continue;
                }
                buyerCouponInfo = updateRedisExpiredBuyerCouponStatus(buyerCouponInfo, couponStatusMap);
                buyerCouponStatus = buyerCouponInfo.getStatus();
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED).equals(buyerCouponInfo.getStatus())) {
                    buyerCouponStatus = couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_USED);
                }
                couponKey = buyerCouponInfo.getCouponType() + "_" + buyerCouponStatus;
                if (countMap.containsKey(couponKey)) {
                    couponCount = countMap.get(couponKey);
                    couponCountNum = couponCount.getReceiveCount();
                } else {
                    couponCount = new BuyerCouponCountDTO();
                    couponCountNum = 0L;
                }
                couponCountNum++;
                couponCount.setCouponType(buyerCouponInfo.getCouponType());
                couponCount.setStatus(buyerCouponStatus);
                couponCount.setReceiveCount(couponCountNum);
                countMap.put(couponKey, couponCount);
            }
        }
        if (!countMap.isEmpty()) {
            for (Map.Entry<String, BuyerCouponCountDTO> entry : countMap.entrySet()) {
                countResult.add(entry.getValue());
            }
        }
        return countResult;
    }

    /**
     * 查询会员优惠券数量
     *
     * @param condition
     * @param pager
     * @return
     */
    public DataGrid<BuyerCouponInfoDTO> getRedisBuyerCouponList(BuyerCouponConditionDTO condition,
            Pager<BuyerCouponInfoDTO> pager) {
        DataGrid<BuyerCouponInfoDTO> dataGrid = new DataGrid<BuyerCouponInfoDTO>();
        List<BuyerCouponInfoDTO> tmpCouponList = new ArrayList<BuyerCouponInfoDTO>();
        List<BuyerCouponInfoDTO> couponResult = new ArrayList<BuyerCouponInfoDTO>();
        String buyerCode = condition.getBuyerCode();
        String type = condition.getCouponType();
        String status = condition.getStatus();
        List<String> couponCodeList = condition.getCouponCodeList();
        String buyerCouponCode = "";
        String buyerCouponValue = "";
        String buyerCouponLeftAmount = "";
        String tmpStatus = status;
        String buyerCouponStatus = "";
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        Map<String, String> buyerCouponMap = null;
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        long total = 0;
        int offset = 0;
        int index = 0;
        int rows = Integer.MAX_VALUE;
        if (pager != null) {
            offset = pager.getPageOffset();
            rows = pager.getRows();
        }
        String[] redisAmountKeyArr = null;
        List<String> redisAmountKeyList = new ArrayList<String>();
        List<String> couponAmountList = null;
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        buyerCouponMap = marketRedisDB.getHashOperations(buyerCouponRedisKey);
        if (buyerCouponMap != null && !buyerCouponMap.isEmpty()) {
            for (Entry<String, String> entry : buyerCouponMap.entrySet()) {
                buyerCouponCode = entry.getKey();
                buyerCouponValue = entry.getValue();
                buyerCouponInfo = JSON.parseObject(buyerCouponValue, BuyerCouponInfoDTO.class);
                if (buyerCouponInfo == null) {
                    marketRedisDB.delHash(buyerCouponRedisKey, buyerCouponCode);
                    continue;
                }
                if (!StringUtils.isEmpty(type) && !type.equals(buyerCouponInfo.getCouponType())) {
                    continue;
                }
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_DELETED)
                        .equals(buyerCouponInfo.getStatus())) {
                    continue;
                }
                buyerCouponInfo = updateRedisExpiredBuyerCouponStatus(buyerCouponInfo, couponStatusMap);
                buyerCouponStatus = buyerCouponInfo.getStatus();
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID).equals(buyerCouponInfo.getStatus())) {
                    continue;
                }
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED).equals(buyerCouponInfo.getStatus())) {
                    buyerCouponStatus = couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_USED);
                }
                if (StringUtils.isEmpty(status)) {
                    tmpStatus = buyerCouponStatus;
                }
                if (couponCodeList != null && !couponCodeList.isEmpty() && !couponCodeList.contains(buyerCouponCode)) {
                    continue;
                }
                if (tmpStatus.equals(buyerCouponStatus)) {
                    total++;
                    tmpCouponList.add(buyerCouponInfo);
                }
            }
            if (!tmpCouponList.isEmpty()) {
                Collections.sort(tmpCouponList, new Comparator<BuyerCouponInfoDTO>() {
                    public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
                        return o2.getGetCouponTime().compareTo(o1.getGetCouponTime());
                    }
                });
                for (BuyerCouponInfoDTO tmpCouponInfo : tmpCouponList) {
                    index ++;
                    if (index > offset && couponResult.size() < rows) {
                        buyerCouponCode = tmpCouponInfo.getBuyerCouponCode();
                        couponResult.add(tmpCouponInfo);
                        redisAmountKeyList.add(buyerCode + "&" + buyerCouponCode);
                    }
                    if (couponResult.size() > rows) {
                        break;
                    }
                }
                redisAmountKeyArr = (String[])redisAmountKeyList.toArray(new String[redisAmountKeyList.size()]);
                couponAmountList = marketRedisDB.getMHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, redisAmountKeyArr);
                for (int i = 0; i < couponResult.size(); i ++) {
                    buyerCouponInfo = couponResult.get(i);
                    buyerCouponLeftAmount = couponAmountList.get(i);
                    if (StringUtils.isEmpty(couponAmountList.get(i)) || "nil".equals(couponAmountList.get(i))) {
                        buyerCouponLeftAmount = "0";
                    }
                    buyerCouponInfo.setCouponLeftAmount(
                            CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
                }
            }
        }
        dataGrid.setTotal(total);
        dataGrid.setRows(couponResult);
        return dataGrid;
    }

    /**
     * 更新过期会员优惠券状态
     *
     * @param buyerCouponInfo
     * @param couponStatusMap
     * @return
     */
    private BuyerCouponInfoDTO updateRedisExpiredBuyerCouponStatus(BuyerCouponInfoDTO buyerCouponInfo,
            Map<String, String> couponStatusMap) {
        Date nowDt = new Date();
        boolean needSaveFlg = false;
        String buyerCode = "";
        String buyerCouponCode = "";
        String buyerCouponRedisKey = "";
        String buyerCouponLeftAmount = "";
        if (buyerCouponInfo != null) {
            buyerCode = buyerCouponInfo.getBuyerCode();
            buyerCouponCode = buyerCouponInfo.getBuyerCouponCode();
            buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
                if (nowDt.after(buyerCouponInfo.getCouponEndTime())) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
                    needSaveFlg = true;
                } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID).equals(marketRedisDB
                        .getHash(RedisConst.REDIS_COUPON_VALID, buyerCouponInfo.getPromotionId()))) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID));
                    needSaveFlg = true;
                }
                if (needSaveFlg) {
                    buyerCouponLeftAmount = marketRedisDB
                            .getHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode);
                    buyerCouponLeftAmount = StringUtils.isEmpty(buyerCouponLeftAmount) ? "0" : buyerCouponLeftAmount;
                    buyerCouponInfo.setCouponLeftAmount(
                            CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
                    marketRedisDB.setHash(buyerCouponRedisKey, buyerCouponCode, JSON.toJSONString(buyerCouponInfo));
                    marketRedisDB.tailPush(
                            RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCode + "_" + buyerCouponCode,
                            JSON.toJSONString(buyerCouponInfo));
                    marketRedisDB
                            .addSet(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCode + "_" + buyerCouponCode);
                }
            }
        }
        return buyerCouponInfo;
    }

    /**
     * 取得所有触发返券的信息
     *
     * @return
     */
    public List<PromotionDiscountInfoDTO> getRedisTriggerCouponInfoList() {
        List<PromotionDiscountInfoDTO> triggerCouponList = new ArrayList<PromotionDiscountInfoDTO>();
        Map<String, String> triggerCouponMap = marketRedisDB.getHashOperations(RedisConst.REDIS_COUPON_TRIGGER);
        PromotionDiscountInfoDTO couponInfo = null;
        String couponJsonStr = "";
        Date nowDt = new Date();
        String invalidStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
        if (triggerCouponMap == null || triggerCouponMap.isEmpty()) {
            return triggerCouponList;
        }
        for (Entry<String, String> entry : triggerCouponMap.entrySet()) {
            couponJsonStr = entry.getValue();
            couponInfo = JSON.parseObject(couponJsonStr, PromotionDiscountInfoDTO.class);
            if (invalidStatus
                    .equals(marketRedisDB.getHash(RedisConst.REDIS_COUPON_VALID, couponInfo.getPromotionId()))) {
                continue;
            }
            if (nowDt.after(couponInfo.getEffectiveEndTime())) {
                continue;
            }
            triggerCouponList.add(couponInfo);
        }
        if (!triggerCouponList.isEmpty()) {
            Collections.sort(triggerCouponList, new Comparator<PromotionDiscountInfoDTO>() {
                public int compare(PromotionDiscountInfoDTO o1, PromotionDiscountInfoDTO o2) {
                    return o1.getEffectiveEndTime().compareTo(o2.getEffectiveEndTime());
                }
            });
        }
        return triggerCouponList;
    }

    /**
     * 删除优惠券活动信息
     *
     * @param couponInfo
     */
    public void deleteRedisCouponInfo(PromotionDiscountInfoDTO couponInfo) {
        String couponProvideType = couponInfo.getCouponProvideType();
        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_TRIGGER_SEND).equals(couponProvideType)) {
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 start -----
            if (StringUtils.isEmpty(couponInfo.getB2cActivityCode())) {
                marketRedisDB.delHash(RedisConst.REDIS_COUPON_TRIGGER, couponInfo.getPromotionId());
            } else {
                marketRedisDB.delHash(RedisConst.REDIS_COUPON_TRIGGER, couponInfo.getB2cActivityCode());
            }
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20170930 end -----
        }
        marketRedisDB.delHash(RedisConst.REDIS_COUPON_RECEIVE_COUNT, couponInfo.getPromotionId());
    }

    /**
     * 锁定会员优惠券信息金额
     *
     * @param orderCouponInfo
     * @return
     * @throws MarketCenterBusinessException
     */
    private BuyerCouponInfoDTO dealRedisReverseBuyerCouponInfo(OrderItemPromotionDTO orderCouponInfo)
            throws MarketCenterBusinessException {
        Date nowDt = new Date();
        String buyerCode = orderCouponInfo.getBuyerCode();
        String couponCode = orderCouponInfo.getCouponCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String amountKey = buyerCode + "&" + couponCode;
        long afterDealAmount = 0;
        BigDecimal discount = orderCouponInfo.getDiscountAmount();
        long amount = 0;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        String buyerCouponStr = "";
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        buyerCouponStr = marketRedisDB.getHash(buyerCouponRedisKey, couponCode);
        buyerCouponInfo = JSON.parseObject(buyerCouponStr, BuyerCouponInfoDTO.class);
        if (buyerCouponInfo == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_NOT_EXIST, "该会员优惠券信息不存在!");
        }
        try {
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
                if (nowDt.after(buyerCouponInfo.getCouponEndTime())) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
                } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID).equals(marketRedisDB
                        .getHash(RedisConst.REDIS_COUPON_VALID, buyerCouponInfo.getPromotionId()))) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID));
                }
            }
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_EXPIRE).equals(buyerCouponInfo.getStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_HAS_EXPIRED,
                        "会员编号:" + buyerCode + " 优惠券编号:" + couponCode + " 已过期");
            }
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID).equals(buyerCouponInfo.getStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_STATUS_INVALID,
                        "会员编号:" + buyerCode + " 优惠券编号:" + couponCode + " 已失效");
            }
            if (!couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_NO_USE,
                        "会员编号:" + buyerCode + " 优惠券编号:" + couponCode + " 已被使用");
            }
            amount = CalculateUtils.multiply(discount, new BigDecimal(100)).longValue();
            amount = amount * -1;
            afterDealAmount = marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_COUPON_AMOUNT, amountKey, amount);
            if (afterDealAmount < 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_BALANCE_DEFICIENCY,
                        "会员编号:" + buyerCode + " 优惠券编号:" + couponCode + " 需优惠总金额:" + discount + " 余额不足");
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_FULL_CUT)
                    .equals(buyerCouponInfo.getCouponType())) {
                buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED));
            } else if (dictionary
                    .getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
                    .equals(buyerCouponInfo.getCouponType())) {
                if (afterDealAmount == 0) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED));
                } else {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED));
                }
            }
            buyerCouponInfo
                    .setCouponLeftAmount(CalculateUtils.divide(new BigDecimal(afterDealAmount), new BigDecimal(100)));
            buyerCouponInfo.setModifyId(orderCouponInfo.getOperaterId());
            buyerCouponInfo.setModifyName(orderCouponInfo.getOperaterName());
            marketRedisDB.setHash(buyerCouponRedisKey, couponCode, JSON.toJSONString(buyerCouponInfo));
        } catch (MarketCenterBusinessException bcbe) {
            if (MarketCenterCodeConst.BUYER_COUPON_BALANCE_DEFICIENCY.equals(bcbe.getCode())) {
                marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_COUPON_AMOUNT, amountKey, amount * -1);
            }
            throw bcbe;
        }
        return buyerCouponInfo;
    }

    /**
     * 释放锁定时会员优惠券信息
     *
     * @param orderCouponInfo
     */
    private BuyerCouponInfoDTO dealRedisReleaseBuyerCouponInfo(OrderItemPromotionDTO orderCouponInfo) {
        Date nowDt = new Date();
        String buyerCode = orderCouponInfo.getBuyerCode();
        String couponCode = orderCouponInfo.getCouponCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String amountKey = buyerCode + "&" + couponCode;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        BigDecimal discount = orderCouponInfo.getDiscountAmount();
        long afterDealAmount = 0;
        long amount = 0;
        long lockLogCount = 0;
        long useLogCount = 0;
        String tmpStr = "";
        String buyerCouponStr = "";
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        buyerCouponStr = marketRedisDB.getHash(buyerCouponRedisKey, couponCode);
        buyerCouponInfo = JSON.parseObject(buyerCouponStr, BuyerCouponInfoDTO.class);
        if (buyerCouponInfo == null) {
            return null;
        }
        try {
            if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_FULL_CUT)
                    .equals(buyerCouponInfo.getCouponType())) {
                tmpStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, amountKey + "&REVERSE");
                lockLogCount = StringUtils.isEmpty(tmpStr) ? 0 : Long.parseLong(tmpStr);
                tmpStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, amountKey + "&REDUCE");
                useLogCount = StringUtils.isEmpty(tmpStr) ? 0 : Long.parseLong(tmpStr);
                if (lockLogCount == 0 && useLogCount == 0) {
                    afterDealAmount =
                            CalculateUtils.multiply(buyerCouponInfo.getCouponAmount(), new BigDecimal(100)).longValue();
                    marketRedisDB
                            .setHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, amountKey, String.valueOf(afterDealAmount));
                    if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED)
                            .equals(buyerCouponInfo.getStatus())) {
                        buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED));
                    }
                }
            } else if (dictionary
                    .getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
                    .equals(buyerCouponInfo.getCouponType())) {
                amount = CalculateUtils.multiply(discount, new BigDecimal(100)).longValue();
                afterDealAmount = marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_COUPON_AMOUNT, amountKey, amount);
                if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED).equals(buyerCouponInfo.getStatus())) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED));
                }
            }
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(buyerCouponInfo.getStatus())) {
                if (nowDt.after(buyerCouponInfo.getCouponEndTime())) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
                } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID).equals(marketRedisDB
                        .getHash(RedisConst.REDIS_COUPON_VALID, buyerCouponInfo.getPromotionId()))) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID));
                }
            }
            buyerCouponInfo
                    .setCouponLeftAmount(CalculateUtils.divide(new BigDecimal(afterDealAmount), new BigDecimal(100)));
            buyerCouponInfo.setModifyId(orderCouponInfo.getOperaterId());
            buyerCouponInfo.setModifyName(orderCouponInfo.getOperaterName());
        } finally {
            marketRedisDB.setHash(buyerCouponRedisKey, couponCode, JSON.toJSONString(buyerCouponInfo));
        }
        return buyerCouponInfo;
    }

    /**
     * 扣减锁定时会员优惠券信息
     *
     * @param orderCouponInfo
     */
    private BuyerCouponInfoDTO dealRedisReduceBuyerCouponInfo(OrderItemPromotionDTO orderCouponInfo) {
        Date nowDt = new Date();
        String buyerCode = orderCouponInfo.getBuyerCode();
        String couponCode = orderCouponInfo.getCouponCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String amountKey = buyerCode + "&" + couponCode;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        long lockLogCount = 0;
        boolean needSaveFlg = false;
        String buyerCouponStr = "";
        String tmpStr = "";
        List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
        Map<String, String> couponStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : couponStatusList) {
            couponStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        buyerCouponStr = marketRedisDB.getHash(buyerCouponRedisKey, couponCode);
        buyerCouponInfo = JSON.parseObject(buyerCouponStr, BuyerCouponInfoDTO.class);
        if (buyerCouponInfo == null) {
            return null;
        }
        try {
            if (couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_LOCKED).equals(buyerCouponInfo.getStatus())) {
                tmpStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, amountKey + "&REVERSE");
                lockLogCount = StringUtils.isEmpty(tmpStr) ? 0 : Long.parseLong(tmpStr);
                if (lockLogCount == 0) {
                    buyerCouponInfo.setStatus(couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_USED));
                    needSaveFlg = true;
                }
            }
            buyerCouponInfo.setModifyId(orderCouponInfo.getOperaterId());
            buyerCouponInfo.setModifyName(orderCouponInfo.getOperaterName());
        } finally {
            if (needSaveFlg) {
                marketRedisDB.setHash(buyerCouponRedisKey, couponCode, JSON.toJSONString(buyerCouponInfo));
            }
        }
        return needSaveFlg ? buyerCouponInfo : null;
    }

    /**
     * 处理会员优惠券金额
     *
     * @param distinctCouponMap
     * @param dealType
     * @throws MarketCenterBusinessException
     */
    public void dealRedisBuyerCouponAmountList(Map<String, OrderItemPromotionDTO> distinctCouponMap, String dealType)
            throws MarketCenterBusinessException {
        List<OrderItemPromotionDTO> rollbackCouponList = new ArrayList<OrderItemPromotionDTO>();
        List<BuyerCouponInfoDTO> buyerCouponList = new ArrayList<BuyerCouponInfoDTO>();
        OrderItemPromotionDTO couponInfo = null;
        BuyerCouponInfoDTO buyerCouponInfo = null;
        String redisUpdateKey = "";
        try {
            for (Entry<String, OrderItemPromotionDTO> entry : distinctCouponMap.entrySet()) {
                buyerCouponInfo = null;
                couponInfo = entry.getValue();
                if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE.equals(dealType)) {
                    buyerCouponInfo = dealRedisReverseBuyerCouponInfo(couponInfo);
                } else if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE.equals(dealType)) {
                    buyerCouponInfo = dealRedisReleaseBuyerCouponInfo(couponInfo);
                } else if (DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE.equals(dealType)) {
                    buyerCouponInfo = dealRedisReduceBuyerCouponInfo(couponInfo);
                }
                if (buyerCouponInfo != null) {
                    rollbackCouponList.add(couponInfo);
                    buyerCouponList.add(buyerCouponInfo);
                }
            }
        } catch (MarketCenterBusinessException bcbe) {
            if (!rollbackCouponList.isEmpty() && DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE.equals(dealType)) {
                for (OrderItemPromotionDTO rollbackCoupon : rollbackCouponList) {
                    dealRedisReleaseBuyerCouponInfo(rollbackCoupon);
                }
            }
            throw bcbe;
        }
        for (BuyerCouponInfoDTO buyerCouponDTO : buyerCouponList) {
            redisUpdateKey = buyerCouponDTO.getBuyerCode() + "_" + buyerCouponDTO.getBuyerCouponCode();
            marketRedisDB.tailPush(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + redisUpdateKey,
                    JSON.toJSONString(buyerCouponDTO));
            marketRedisDB.addSet(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, redisUpdateKey);
        }
    }

    /**
     * 更新Redis中的优惠券使用记录并更新DB
     *
     * @param useLogList
     */
    public void updateRedisUseCouponLog(List<BuyerUseCouponLog> useLogList) {
        String orderNo = "";
        String orderItemNo = "";
        String buyerCode = "";
        String buyerCouponCode = "";
        String useLogRedisKey = "";
        String useLogCountKey = "";
        String useLogStr = "";
        long lockedLogCount = 0;
        List<DictionaryInfo> buyerPromotionStatusList =
                dictionary.getDictionaryOptList(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS);
        Map<String, String> buyerPromotionStatusMap = new HashMap<String, String>();
        for (DictionaryInfo dictionaryInfo : buyerPromotionStatusList) {
            buyerPromotionStatusMap.put(dictionaryInfo.getCode(), dictionaryInfo.getValue());
        }
        if (useLogList == null || useLogList.isEmpty()) {
            return;
        }
        for (BuyerUseCouponLog useLog : useLogList) {
            orderNo = useLog.getOrderNo();
            orderItemNo = useLog.getOrderItemNo();
            buyerCode = useLog.getBuyerCode();
            buyerCouponCode = useLog.getBuyerCouponCode();
            useLogRedisKey = orderNo + "&" + orderItemNo + "&" + buyerCode + "&" + buyerCouponCode;
            useLogCountKey = buyerCode + "&" + buyerCouponCode;
            useLogStr = JSON.toJSONString(useLog);
            marketRedisDB.setHash(RedisConst.REDIS_BUYER_COUPON_USELOG, useLogRedisKey, useLogStr);
            marketRedisDB.tailPush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_USELOG, useLogStr);
            if (buyerPromotionStatusMap.get(DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE)
                    .equals(useLog.getUseType())) {
                marketRedisDB.incrHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogCountKey + "&REVERSE");
                continue;
            }
            if (buyerPromotionStatusMap.get(DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REDUCE)
                    .equals(useLog.getUseType())) {
                marketRedisDB.incrHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogCountKey + "&REDUCE");
            }
            lockedLogCount = marketRedisDB
                    .incrHashBy(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogCountKey + "&REVERSE", -1);
            if (lockedLogCount < 0) {
                marketRedisDB.setHash(RedisConst.REDIS_BUYER_COUPON_USELOG_COUNT, useLogCountKey + "&REVERSE", "0");
            }
        }
    }

    /**
     * 从Redis中取得锁定时订单行用券log信息
     *
     * @param orderCouponDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseCouponLog getRedisReverseBuyerCouponUseLog(OrderItemPromotionDTO orderCouponDTO)
            throws MarketCenterBusinessException {
        String orderNo = orderCouponDTO.getOrderNo();
        String orderItemNo = orderCouponDTO.getOrderItemNo();
        String buyerCode = orderCouponDTO.getBuyerCode();
        String buyerCouponCode = orderCouponDTO.getCouponCode();
        BuyerUseCouponLog useLog = null;
        useLog = getRedisBuyerCouponUseLog(orderCouponDTO);
        if (useLog != null) {
            if (!orderCouponDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_DOUBLE_REVERSE,
                        "订单号:" + orderNo + " 订单行号:" + orderItemNo + " 会员编号:" + buyerCode + " 优惠券编号:" + buyerCouponCode
                                + " 该订单已使用过此优惠券不能重复使用");
            }
            if (useLog.getCouponUsedAmount().compareTo(orderCouponDTO.getDiscountAmount()) != 0) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_DEAL_DIFF_NONEY,
                        "订单号:" + orderNo + " 订单行号:" + orderItemNo + " 会员编号:" + buyerCode + " 优惠券编号:" + buyerCouponCode
                                + " 该订单已使用此优惠券但使用金额不同");
            }
            return null;
        }
        useLog = new BuyerUseCouponLog();
        useLog.setBuyerCode(orderCouponDTO.getBuyerCode());
        useLog.setPromotionId(orderCouponDTO.getPromotionId());
        useLog.setLevelCode(orderCouponDTO.getLevelCode());
        useLog.setOrderNo(orderCouponDTO.getOrderNo());
        useLog.setOrderItemNo(orderCouponDTO.getOrderItemNo());
        useLog.setBuyerCouponCode(orderCouponDTO.getCouponCode());
        useLog.setUseType(orderCouponDTO.getPromoitionChangeType());
        useLog.setCouponUsedAmount(orderCouponDTO.getDiscountAmount());
        useLog.setCreateId(orderCouponDTO.getOperaterId());
        useLog.setCreateName(orderCouponDTO.getOperaterName());
        useLog.setModifyId(orderCouponDTO.getOperaterId());
        useLog.setModifyName(orderCouponDTO.getOperaterName());
        return useLog;
    }

    /**
     * 从Redis中取得非锁定时订单行用券log信息
     *
     * @param orderCouponDTO
     * @return
     * @throws MarketCenterBusinessException
     */
    public BuyerUseCouponLog getRedisReleaseBuyerCouponUseLog(OrderItemPromotionDTO orderCouponDTO)
            throws MarketCenterBusinessException {
        String orderNo = orderCouponDTO.getOrderNo();
        String orderItemNo = orderCouponDTO.getOrderItemNo();
        String buyerCode = orderCouponDTO.getBuyerCode();
        String buyerCouponCode = orderCouponDTO.getCouponCode();
        BuyerUseCouponLog useLog = null;
        useLog = getRedisBuyerCouponUseLog(orderCouponDTO);
        if (useLog == null) {
            return null;
        }
        if (orderCouponDTO.getPromoitionChangeType().equals(useLog.getUseType())) {
            return null;
        }
        if (!dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE).equals(useLog.getUseType())) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_STATUS_ERROR,
                    "订单号:" + orderNo + " 订单行号:" + orderItemNo + " 会员编号:" + buyerCode + " 优惠券编号:" + buyerCouponCode
                            + " 该订单已使用过此优惠券不能重复使用");
        }
        useLog.setUseType(orderCouponDTO.getPromoitionChangeType());
        useLog.setModifyId(orderCouponDTO.getOperaterId());
        useLog.setModifyName(orderCouponDTO.getOperaterName());
        return useLog;
    }

    /**
     * 从Redis中取得订单行用券log信息
     *
     * @param orderCouponDTO
     * @return
     */
    private BuyerUseCouponLog getRedisBuyerCouponUseLog(OrderItemPromotionDTO orderCouponDTO)
            throws MarketCenterBusinessException {
        String orderNo = orderCouponDTO.getOrderNo();
        String orderItemNo = orderCouponDTO.getOrderItemNo();
        String buyerCode = orderCouponDTO.getBuyerCode();
        String buyerCouponCode = orderCouponDTO.getCouponCode();
        String useLogRedisKey = orderNo + "&" + orderItemNo + "&" + buyerCode + "&" + buyerCouponCode;
        String useLogStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_USELOG, useLogRedisKey);
        return JSON.parseObject(useLogStr, BuyerUseCouponLog.class);
    }

}

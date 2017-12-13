package cn.htd.marketcenter.service.impl.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.common.utils.ThreadExecutorService;
import cn.htd.marketcenter.common.utils.ValidateResult;
import cn.htd.marketcenter.common.utils.ValidationUtils;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.domain.BuyerCheckInfo;
import cn.htd.marketcenter.domain.SellerProductInfo;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.OrderInfoDTO;
import cn.htd.marketcenter.dto.OrderItemCouponDTO;
import cn.htd.marketcenter.dto.OrderItemInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;
import cn.htd.marketcenter.dto.PromotionCategoryDetailDTO;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDTO;
import cn.htd.marketcenter.dto.PromotionItemDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerDetailDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedResultDTO;
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.marketcenter.service.BuyerInterestValidService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service("buyerInterestValidService")
public class BuyerInterestValidServiceImpl implements BuyerInterestValidService {

    private static final Logger logger = LoggerFactory.getLogger(BuyerInterestValidServiceImpl.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private TimelimitedRedisHandle timelimitedRedisHandle;

    @Override
    public ExecuteResult<TradeInfoDTO> getAvailableCouponInfo(String messageId, TradeInfoDTO cart) {
        ExecuteResult<TradeInfoDTO> result = new ExecuteResult<TradeInfoDTO>();
        List<OrderInfoDTO> orderList = cart.getOrderList();
        List<OrderItemInfoDTO> productsList = null;
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        Map<String, OrderInfoDTO> orderInfoMap = new HashMap<String, OrderInfoDTO>();
        List<OrderItemInfoDTO> allProductList = new ArrayList<OrderItemInfoDTO>();
        List<String> skuCodeList = new ArrayList<String>();
        Map<String, OrderItemInfoDTO> skuCodeMap = new HashMap<String, OrderItemInfoDTO>();
        List<OrderItemInfoDTO> tmpProductsList = null;
        String sellerCode = "";
        String skuCode = "";
        List<OrderItemCouponDTO> allCouponList = null;
        List<OrderItemCouponDTO> avaliableCouponList = new ArrayList<OrderItemCouponDTO>();
        List<OrderItemCouponDTO> unavaliableCouponList = new ArrayList<OrderItemCouponDTO>();
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
        try {
            cart.initBeforeCalculateCoupon();
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(cart);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            if (orderList.isEmpty()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "进货单商品不能为空");
            }
            for (OrderInfoDTO productsDTO : orderList) {
                productsList = productsDTO.getOrderItemList();
                if (productsList.isEmpty()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "进货单商品不能为空");
                }
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                sellerCode = productsDTO.getSellerCode();
                orderInfoMap.put(sellerCode, productsDTO);
                allProductList.addAll(productsDTO.getOrderItemList());
                for (OrderItemInfoDTO productDTO : productsDTO.getOrderItemList()) {
                    skuCode = productDTO.getSkuCode();
                    skuCodeList.add(skuCode);
                    skuCodeMap.put(skuCode, productDTO);
                }
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
            }
            // 校验购物车商品是否有秒杀商品
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            validateTimelimitedProducts(messageId, cart);
//            validateCouponProducts(messageId, cart, null, dictMap);
            validateTimelimitedProducts(messageId, cart, skuCodeList, skuCodeMap);
            allCouponList = validateCouponProducts(messageId, cart, null, orderInfoMap, allProductList);
            if (allCouponList != null && !allCouponList.isEmpty()) {
                Collections.sort(allCouponList, new Comparator<BuyerCouponInfoDTO>() {
                    public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
                        return o2.getGetCouponTime().compareTo(o1.getGetCouponTime());
                    }
                });
                for (OrderItemCouponDTO tmpItemCouponDTO : allCouponList) {
                    deleteOrderItemCouponUselessInfo(tmpItemCouponDTO);
                    if (StringUtils.isEmpty(tmpItemCouponDTO.getErrorMsg())) {
                        avaliableCouponList.add(tmpItemCouponDTO);
                    } else {
                        unavaliableCouponList.add(tmpItemCouponDTO);
                    }
                }
                cart.setAvaliableCouponList(avaliableCouponList);
                cart.setUnavaliableCouponList(unavaliableCouponList);
            }
            //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
            result.setResult(cart);
        } catch (MarketCenterBusinessException bcbe) {
            result.setCode(bcbe.getCode());
            result.addErrorMessage(bcbe.getMessage());
        } catch (Exception e) {
            result.setCode(MarketCenterCodeConst.SYSTEM_ERROR);
            result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
        }
        return result;
    }

    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----

    /**
     * 校验购物车中是否有参加促销活动的商品
     *
     * @param messageId
     * @param targetPromotionMap
     * @throws Exception
     */
    private void validatePromotionProducts(String messageId,
            Map<String, Map<String, List<OrderItemInfoDTO>>> targetPromotionMap) throws Exception {
        logger.info("***********校验购物车中是否有促销活动商品 messageId:[{}] 开始***********", messageId);
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = ThreadExecutorService.getInstance().getBoundedThreadPool();
        List<Future<String>> workResultList = new ArrayList<Future<String>>();
        Map<String, List<OrderItemInfoDTO>> tmpPromotionMap = null;
        Map<String, List<OrderItemInfoDTO>> validTargetPromotionMap = null;
        String limitedDiscountType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT);
        try {
            if (targetPromotionMap.containsKey(limitedDiscountType)) {
                tmpPromotionMap = targetPromotionMap.get(limitedDiscountType);
                if (tmpPromotionMap != null && !tmpPromotionMap.isEmpty()) {
                    while (tmpPromotionMap.size() > 2) {
                        validTargetPromotionMap = new HashMap<String, List<OrderItemInfoDTO>>();
                        for (Entry<String, List<OrderItemInfoDTO>> entry : tmpPromotionMap.entrySet()) {
                            validTargetPromotionMap.put(entry.getKey(), entry.getValue());
                            tmpPromotionMap.remove(entry.getKey());
                            if (validTargetPromotionMap.size() >= 2) {
                                break;
                            }
                        }
                        workResultList.add(executorService
                                .submit(new ValidTimelimitedDiscountTask(messageId, validTargetPromotionMap)));
                    }
                    for (Entry<String, List<OrderItemInfoDTO>> entry : tmpPromotionMap.entrySet()) {
                        checkTimelimitedDiscountInfo(messageId, entry.getKey(), tmpPromotionMap.get(entry.getKey()));
                    }
                }
            }
            for (Future<String> workRst : workResultList) {
                logger.info("\n 方法:[{}],线程执行结果:[{}]", "validatePromotionProducts", workRst.get());
            }
        } catch (Exception e) {
            if (workResultList != null && !workResultList.isEmpty()) {
                for (Future<String> workRst : workResultList) {
                    if (!workRst.isDone()) {
                        workRst.cancel(true);
                    }
                }
            }
            if (e instanceof MarketCenterBusinessException) {
                logger.info("***********校验购物车中是否有促销活动商品 messageId:[{}],校验结果:[{}]***********", messageId,
                        e.getMessage());
            } else {
                logger.error("***********校验购物车中是否有促销活动商品 messageId:[{}],异常:[{}]***********", messageId,
                        ExceptionUtils.getStackTraceAsString(e));
            }
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("***********校验购物车中是否有促销活动商品 messageId:[{}],结束|调用耗时[{}]ms***********", messageId,
                    (endTime - startTime));
        }
    }
    //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----

    /**
     * 校验购物车中是否有秒杀和限时购商品
     *
     * @param messageId
     * @param cart
     * @param skuCodeList
     * @param skuCodeMap
     * @throws MarketCenterBusinessException
     */
    //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//    private void validateTimelimitedProducts(String messageId, TradeInfoDTO cart) throws MarketCenterBusinessException {
    private void validateTimelimitedProducts(String messageId, TradeInfoDTO cart, List<String> skuCodeList,
            Map<String, OrderItemInfoDTO> skuCodeMap) throws MarketCenterBusinessException {
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        logger.info("***********校验购物车中是否有秒杀商品 messageId:[{}] 开始***********", messageId);
        long startTime = System.currentTimeMillis();
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        List<OrderInfoDTO> orderList = cart.getOrderList();
//        List<OrderItemInfoDTO> productsList = null;
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
//        List<String> skuCodeList = new ArrayList<String>();
//        Map<String, List<OrderItemInfoDTO>> skuCodeMap = new HashMap<String, List<OrderItemInfoDTO>>();
//        List<OrderItemInfoDTO> tmpProductsList = null;
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        List<TimelimitedInfoDTO> timelimitedInfoList = null;
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        OrderItemInfoDTO tmpProductDTO = null;
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
        String skuCode = "";
        boolean hasTimelimitedSkuFlg = false;

        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        for (OrderInfoDTO orderInfoDTO : orderList) {
//            productsList = orderInfoDTO.getOrderItemList();
//            for (OrderItemInfoDTO productDTO : productsList) {
//                skuCode = productDTO.getSkuCode();
//                skuCodeList.add(skuCode);
//                if (skuCodeMap.containsKey(skuCode)) {
//                    tmpProductsList = skuCodeMap.get(skuCode);
//                } else {
//                    tmpProductsList = new ArrayList<OrderItemInfoDTO>();
//                }
//                tmpProductsList.add(productDTO);
//                skuCodeMap.put(skuCode, tmpProductsList);
//            }
//        }
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        timelimitedInfoList = timelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList);
        timelimitedInfoList = timelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList, dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        if (timelimitedInfoList != null && !timelimitedInfoList.isEmpty()) {
            hasTimelimitedSkuFlg = true;
            for (TimelimitedInfoDTO timelimitedInfo : timelimitedInfoList) {
                skuCode = timelimitedInfo.getSkuCode();
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//                tmpProductsList = skuCodeMap.get(skuCode);
//                for (OrderItemInfoDTO tmpProductDTO : tmpProductsList) {
//                    tmpProductDTO.setHasTimelimitedFlag(true);
//                    tmpProductDTO.setTimelimitedInfo(timelimitedInfo);
//                }
                tmpProductDTO = skuCodeMap.get(skuCode);
                tmpProductDTO.setHasTimelimitedFlag(true);
                tmpProductDTO.setTimelimitedInfo(timelimitedInfo);
                tmpProductDTO.setPromotionId(timelimitedInfo.getPromotionId());
                tmpProductDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                        DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
                logger.info("***********校验购物车中是否有秒杀商品 messageId:[{}],商品skuCode:[{}],参加秒杀活动PromotionId:[{}]***********",
                        messageId, skuCode, timelimitedInfo.getPromotionId());
            }
        }
        cart.setHasTimelimitedProduct(hasTimelimitedSkuFlg);
        long endTime = System.currentTimeMillis();
        logger.info("***********校验购物车中是否有秒杀商品 messageId:[{}],是否存在参加秒杀活动商品:[{}] 结束|调用耗时[{}]ms***********", messageId,
                hasTimelimitedSkuFlg, (endTime - startTime));
    }

    /**
     * 对于过期或失效的优惠券更新Reids中优惠券信息
     *
     * @param couponInfo
     */
    private void updateExpireInvalidRedisCouponInfo(final BuyerCouponInfoDTO couponInfo) {
        new Thread() {
            public void run() {
                String buyerCode = couponInfo.getBuyerCode();
                String buyerCouponCode = couponInfo.getBuyerCouponCode();
                String buyerCouponLeftAmount =
                        marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode);
                buyerCouponLeftAmount =
                        StringUtils.isEmpty(buyerCouponLeftAmount) || "nil".equals(buyerCouponLeftAmount) ? "0"
                                : buyerCouponLeftAmount;
                couponInfo.setCouponLeftAmount(
                        CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
                marketRedisDB.setHash(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode,
                        JSON.toJSONString(couponInfo));
                marketRedisDB.tailPush(
                        RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCode + "_" + buyerCouponCode,
                        JSON.toJSONString(couponInfo));
                marketRedisDB.addSet(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCode + "_" + buyerCouponCode);
            }
        }.start();
    }

    /**
     * 校验可用会员可用优惠券
     *
     * @param messageId
     * @param cart
     * @param targetCouponCodeList
     * @param orderInfoMap
     * @param allProductList
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//    private void validateCouponProducts(String messageId, TradeInfoDTO cart, List<String> targetCouponCodeList,
    private List<OrderItemCouponDTO> validateCouponProducts(String messageId, TradeInfoDTO cart,
            List<String> targetCouponCodeList, Map<String, OrderInfoDTO> orderInfoMap,
            List<OrderItemInfoDTO> allProductList) throws MarketCenterBusinessException, Exception {
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        logger.info("***********校验可用会员可用优惠券 messageId:[{}] 开始***********", messageId);
        long startTime = System.currentTimeMillis();
        Jedis jedis = null;
        List<String> valueList = null;
        List<String> tmpValueList = null;
        Map<String, String> validMap = null;
        ExecutorService executorService = null;
        List<Future<List<OrderItemCouponDTO>>> workResultList = new ArrayList<Future<List<OrderItemCouponDTO>>>();
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        List<OrderInfoDTO> orderList = cart.getOrderList();
//        Map<String, OrderInfoDTO> orderInfoMap = new HashMap<String, OrderInfoDTO>();
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        List<OrderItemCouponDTO> allCouponList = new ArrayList<OrderItemCouponDTO>();
        OrderItemCouponDTO tmpItemCouponDTO = null;
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        List<OrderItemCouponDTO> avaliableCouponList = new ArrayList<OrderItemCouponDTO>();
//        List<OrderItemCouponDTO> unavaliableCouponList = new ArrayList<OrderItemCouponDTO>();
//        List<OrderItemInfoDTO> allProductList = new ArrayList<OrderItemInfoDTO>();
//        OrderItemCouponDTO tmpOrderCoupon = null;
//        String couponValidStatus = "";
//        String sellerCode = "";
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        String buyerCode = cart.getBuyerCode();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        String unusedStatus =
//                dictMap.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_UNUSED);
//        String expirdStatus =
//                dictMap.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_EXPIRE);
//        String invalidStatus =
//                dictMap.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_INVALID);
//        String invalidPromotionStatus = dictMap.get(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS + "&"
//                + DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID);
//        String buyerCouponCode = "";
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        String buyerCouponValue = "";
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        String buyerCouponLeftAmount = "";
//        List<BuyerCouponInfoDTO> couponInfoList = new ArrayList<BuyerCouponInfoDTO>();
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
        boolean hasTargetCouponFlag = false;

        if (targetCouponCodeList != null && !targetCouponCodeList.isEmpty()) {
            hasTargetCouponFlag = true;
        }
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        for (OrderInfoDTO tmpOrderInfoDTO : orderList) {
//            sellerCode = tmpOrderInfoDTO.getSellerCode();
//            orderInfoMap.put(sellerCode, tmpOrderInfoDTO);
//            allProductList.addAll(tmpOrderInfoDTO.getOrderItemList());
//        }
        //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        try {
            jedis = marketRedisDB.getResource();
            if (!jedis.exists(buyerCouponRedisKey)) {
                return allCouponList;
            }
            if (hasTargetCouponFlag) {
                valueList = new ArrayList<String>();
                validMap = jedis.hgetAll(RedisConst.REDIS_COUPON_VALID);
                for (String targetCouponCode : targetCouponCodeList) {
                    buyerCouponValue = jedis.hget(buyerCouponRedisKey, targetCouponCode);
                    valueList.add(buyerCouponValue);
                }
            } else {
                valueList = jedis.hvals(buyerCouponRedisKey);
                validMap = jedis.hgetAll(RedisConst.REDIS_COUPON_VALID);
            }
//            for (Entry<String, String> entry : valueMap.entrySet()) {
//                buyerCouponCode = entry.getKey();
//                buyerCouponValue = entry.getValue();
//                couponInfo = JSON.parseObject(buyerCouponValue, BuyerCouponInfoDTO.class);
//                if (couponInfo == null) {
//                    continue;
//                }
//                if (hasTargetCouponFlag && !targetCouponCodeList.contains(buyerCouponCode)) {
//                    continue;
//                }
//                if (new Date().before(couponInfo.getCouponStartTime())) {
//                    if (hasTargetCouponFlag) {
//                        tmpOrderCoupon = new OrderItemCouponDTO();
//                        tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
//                        tmpOrderCoupon.setErrorMsg("优惠券未到使用开始期限不能使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
//                        unavaliableCouponList.add(tmpOrderCoupon);
//                    }
//                    continue;
//
//                }
//                if (!unusedStatus.equals(couponInfo.getStatus())) {
//                    if (hasTargetCouponFlag) {
//                        tmpOrderCoupon = new OrderItemCouponDTO();
//                        tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
//                        tmpOrderCoupon.setErrorMsg("优惠券已被使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
//                        unavaliableCouponList.add(tmpOrderCoupon);
//                    }
//                    continue;
//                }
//                if (new Date().after(couponInfo.getCouponEndTime())) {
//                    couponInfo.setStatus(expirdStatus);
//                    updateExpireInvalidRedisCouponInfo(jedis, couponInfo);
//                    if (hasTargetCouponFlag) {
//                        tmpOrderCoupon = new OrderItemCouponDTO();
//                        tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
//                        tmpOrderCoupon.setErrorMsg("优惠券已过期 优惠券编号:" + couponInfo.getBuyerCouponCode());
//                        unavaliableCouponList.add(tmpOrderCoupon);
//                    }
//                    continue;
//                }
//                couponValidStatus = validMap.get(couponInfo.getPromotionId());
//                if (invalidPromotionStatus.equals(couponValidStatus)) {
//                    couponInfo.setStatus(invalidStatus);
//                    updateExpireInvalidRedisCouponInfo(jedis, couponInfo);
//                    if (hasTargetCouponFlag) {
//                        tmpOrderCoupon = new OrderItemCouponDTO();
//                        tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
//                        tmpOrderCoupon.setErrorMsg("优惠券已失效 优惠券编号:" + couponInfo.getBuyerCouponCode());
//                        unavaliableCouponList.add(tmpOrderCoupon);
//                    }
//                    continue;
//                }
//                if (couponInfo.getDiscountThreshold() == null
//                        || BigDecimal.ZERO.compareTo(couponInfo.getDiscountThreshold()) >= 0) {
//                    continue;
//                }
//                buyerCouponLeftAmount =
//                        jedis.hget(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode);
//                if (StringUtils.isEmpty(buyerCouponLeftAmount) || "nil".equals(buyerCouponLeftAmount)) {
//                    if (hasTargetCouponFlag) {
//                        tmpOrderCoupon = new OrderItemCouponDTO();
//                        tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
//                        tmpOrderCoupon.setErrorMsg("优惠券余额不足 优惠券编号:" + couponInfo.getBuyerCouponCode());
//                        unavaliableCouponList.add(tmpOrderCoupon);
//                    }
//                    continue;
//                }
//                couponInfo.setCouponLeftAmount(
//                        CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
//                couponInfoList.add(couponInfo);
//            }
            long endTime0 = System.currentTimeMillis();
            logger.info("***********取得会员所有优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
                    (endTime0 - startTime));
            if (!valueList.isEmpty()) {
                if (valueList.size() > 4) {
                    executorService = ThreadExecutorService.getInstance().getBoundedThreadPool();
                    while (valueList.size() > 4) {
                        tmpValueList = new ArrayList<String>();
                        tmpValueList.add(valueList.get(0));
                        tmpValueList.add(valueList.get(1));
                        tmpValueList.add(valueList.get(2));
                        tmpValueList.add(valueList.get(3));
                        valueList.remove(0);
                        valueList.remove(0);
                        valueList.remove(0);
                        valueList.remove(0);
                        workResultList.add(executorService
                                .submit(new ValidBuyerAvaliableCouponTask(messageId, tmpValueList, validMap,
                                        hasTargetCouponFlag, orderInfoMap, allProductList)));
                    }
                }
                for (String couponInfoStr : valueList) {
                    tmpItemCouponDTO =
                            checkBuyerCouponInfo(messageId, couponInfoStr, validMap, hasTargetCouponFlag, orderInfoMap,
                                    allProductList);
                    if (tmpItemCouponDTO != null) {
                        allCouponList.add(tmpItemCouponDTO);
                    }
                }
                if (!workResultList.isEmpty()) {
                    for (Future<List<OrderItemCouponDTO>> workRst : workResultList) {
                        allCouponList.addAll(workRst.get());
                    }
                }
                long endTime1 = System.currentTimeMillis();
                logger.info("***********校验会员优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
                        (endTime1 - endTime0));
                //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//                if (allCouponList != null && !allCouponList.isEmpty()) {
//                    Collections.sort(allCouponList, new Comparator<BuyerCouponInfoDTO>() {
//                        public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
//                            return o2.getGetCouponTime().compareTo(o1.getGetCouponTime());
//                        }
//                    });
//                    for (OrderItemCouponDTO tmpItemCouponDTO : allCouponList) {
//                        if (StringUtils.isEmpty(tmpItemCouponDTO.getErrorMsg())) {
//                            avaliableCouponList.add(tmpItemCouponDTO);
//                        } else {
//                            unavaliableCouponList.add(tmpItemCouponDTO);
//                        }
//                    }
//                }
//                long endTime2 = System.currentTimeMillis();
//                logger.info("***********切割会员可用不可用优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
//                        (endTime2 - endTime1));
                //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
            }
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            cart.setAvaliableCouponList(avaliableCouponList);
//            cart.setUnavaliableCouponList(unavaliableCouponList);
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
        } catch (Exception e) {
            if (workResultList != null && !workResultList.isEmpty()) {
                for (Future<List<OrderItemCouponDTO>> workRst : workResultList) {
                    if (!workRst.isDone()) {
                        workRst.cancel(true);
                    }
                }
            }
            logger.error("***********校验会员可用优惠券 messageId:[{}],错误信息:[{}] ***********", messageId,
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            marketRedisDB.releaseResource(jedis);
            long endTime = System.currentTimeMillis();
            logger.info("***********校验会员可用优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId, (endTime - startTime));
        }
        return allCouponList;
    }

    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----

    /**
     * 删除会员优惠券DTO中无效数据
     *
     * @param orderItemCouponDTO
     */
    private void deleteOrderItemCouponUselessInfo(OrderItemCouponDTO orderItemCouponDTO) {
        orderItemCouponDTO.setPromotionProviderType(null);
        orderItemCouponDTO.setPromotionProviderSellerCode(null);
        orderItemCouponDTO.setPromotionProviderShopId(null);
        orderItemCouponDTO.setPromotionProviderShopName(null);
        orderItemCouponDTO.setEffectiveTime(null);
        orderItemCouponDTO.setInvalidTime(null);
        orderItemCouponDTO.setBuyerCode(null);
        orderItemCouponDTO.setBuyerName(null);
        orderItemCouponDTO.setReceiveLimit(null);
        orderItemCouponDTO.setProductList(null);
        orderItemCouponDTO.setBuyerRuleDTO(null);
        orderItemCouponDTO.setSellerRuleDTO(null);
        orderItemCouponDTO.setCategoryItemRuleDTO(null);
        orderItemCouponDTO.setVerifierId(null);
        orderItemCouponDTO.setVerifierName(null);
        orderItemCouponDTO.setVerifyTime(null);
        orderItemCouponDTO.setVerifyRemark(null);
        orderItemCouponDTO.setCreateId(null);
        orderItemCouponDTO.setCreateName(null);
        orderItemCouponDTO.setCreateTime(null);
        orderItemCouponDTO.setModifyId(null);
        orderItemCouponDTO.setModifyName(null);
        orderItemCouponDTO.setModifyTime(null);
        orderItemCouponDTO.setBuyerRuleId(null);
        orderItemCouponDTO.setSellerRuleId(null);
        orderItemCouponDTO.setCategoryItemRuleId(null);
        orderItemCouponDTO.setPromotionStatusHistoryList(null);
    }

    /**
     * 获取折扣券剩余金额
     *
     * @param couponInfo
     */
    private void getBuyerCouponLeftAmount(OrderItemCouponDTO couponInfo) {
        String couponKind = couponInfo.getCouponType();
        String buyerCouponLeftAmount = "";
        if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
                .equals(couponKind)) {
            buyerCouponLeftAmount = marketRedisDB.getHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT,
                    couponInfo.getBuyerCode() + "&" + couponInfo.getBuyerCouponCode());
            if (StringUtils.isEmpty(buyerCouponLeftAmount)) {
                couponInfo.setCouponLeftAmount(BigDecimal.ZERO);
            } else {
                couponInfo.setCouponLeftAmount(
                        CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
            }
        }
    }
    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----

    @Override
    public ExecuteResult<TradeInfoDTO> calculateTradeDiscount(String messageId, TradeInfoDTO cart) {
        ExecuteResult<TradeInfoDTO> result = new ExecuteResult<TradeInfoDTO>();
        List<OrderInfoDTO> orderList = cart.getOrderList();
        List<OrderItemInfoDTO> productsList = null;
        String promotionType = "";
        boolean isValidPromotionType = false;
        List<String> couponCodeList = null;
        String key = "";
        String value = "";
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        Map<String, OrderInfoDTO> orderInfoMap = new HashMap<String, OrderInfoDTO>();
        List<OrderItemInfoDTO> allProductList = new ArrayList<OrderItemInfoDTO>();
        List<String> skuCodeList = new ArrayList<String>();
        Map<String, OrderItemInfoDTO> skuCodeMap = new HashMap<String, OrderItemInfoDTO>();
        List<OrderItemInfoDTO> tmpProductsList = null;
        String sellerCode = "";
        String skuCode = "";
        List<OrderItemInfoDTO> targetItemInfoList = null;
        Map<String, List<OrderItemInfoDTO>> targetPromotionIdMap = null;
        Map<String, Map<String, List<OrderItemInfoDTO>>> targetPromotionMap =
                new HashMap<String, Map<String, List<OrderItemInfoDTO>>>();
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
        try {
            cart.initBeforeCalculateCoupon();
            // 输入DTO的验证
            ValidateResult validateResult = ValidationUtils.validateEntity(cart);
            // 有错误信息时返回错误信息
            if (validateResult.isHasErrors()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR,
                        validateResult.getErrorMsg());
            }
            promotionType = cart.getPromotionType();
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            if (StringUtils.isEmpty(promotionType)) {
//                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "参加促销活动类型不能为空");
//            }
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
            if (orderList.isEmpty()) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "订单信息不能为空");
            }
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
            for (OrderInfoDTO productsDTO : orderList) {
                productsList = productsDTO.getOrderItemList();
                if (productsList == null || productsList.isEmpty()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "购买商品不能为空");
                }
                sellerCode = productsDTO.getSellerCode();
                orderInfoMap.put(sellerCode, productsDTO);
                allProductList.addAll(productsDTO.getOrderItemList());
                for (OrderItemInfoDTO productDTO : productsDTO.getOrderItemList()) {
                    skuCode = productDTO.getSkuCode();
                    skuCodeList.add(skuCode);
                    skuCodeMap.put(skuCode, productDTO);
                    if (!StringUtils.isEmpty(productDTO.getPromotionId()) && !dictionary
                            .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                                    DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
                            .equals(productDTO.getPromotionType()) && !StringUtils.isEmpty(productDTO.getPromotionId())
                            && !dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                            DictionaryConst.OPT_PROMOTION_TYPE_COUPON).equals(productDTO.getPromotionType())) {
                        if (targetPromotionMap.containsKey(productDTO.getPromotionType())) {
                            targetPromotionIdMap = targetPromotionMap.get(productDTO.getPromotionType());
                        } else {
                            targetPromotionIdMap = new HashMap<String, List<OrderItemInfoDTO>>();
                        }
                        if (targetPromotionIdMap.containsKey(productDTO.getPromotionId())) {
                            targetItemInfoList = targetPromotionIdMap.get(productDTO.getPromotionId());
                        } else {
                            targetItemInfoList = new ArrayList<OrderItemInfoDTO>();
                        }
                        targetItemInfoList.add(productDTO);
                        targetPromotionIdMap.put(productDTO.getPromotionId(), targetItemInfoList);
                        targetPromotionMap.put(productDTO.getPromotionType(), targetPromotionIdMap);
                    }
                }
            }
            if (!StringUtils.isEmpty(promotionType)) {
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
                // 秒杀活动时
                if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                        DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED).equals(promotionType)) {
                    if (orderList.size() > 1) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "秒杀活动订单只能单个购买");
                    }
                    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                    productsList = orderList.get(0).getOrderItemList();
                    if (productsList.size() > 1) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "秒杀活动订单只能单个购买");
                    }
                    //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
                    if (StringUtils.isEmpty(cart.getPromotionId())) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "参加秒杀活动编号不能为空");
                    }
                    //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                }
            }
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
//                // 使用优惠券时
//            } else if (dictMap
//                    .get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
//                    .equals(promotionType)) {
//                if (cart.getCouponCodeList() == null || cart.getCouponCodeList().isEmpty()) {
//                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "使用优惠券时优惠券编码不能为空");
//                }
//            }
//            for (OrderInfoDTO productsDTO : orderList) {
//                productsList = productsDTO.getOrderItemList();
//                if (productsList.isEmpty()) {
//                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "购买商品不能为空");
//                }
//                // 秒杀活动时
//                if (dictMap
//                        .get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
//                        .equals(promotionType) && productsList.size() > 1) {
//                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "秒杀活动订单只能单个购买");
//                }
//            }
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
            // 秒杀活动时
            if (dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
                    .equals(promotionType)) {
                logger.info("***********计算优惠券分摊或取得秒杀价格 messageId:[{}] 进入秒杀计算***********", messageId);
                cart = calculateTimelimitedDiscount(messageId, cart.getPromotionId(), cart);
                // 使用优惠券时
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            } else if (dictMap
//                    .get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
//                    .equals(promotionType)) {
            } else {
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
                logger.info("***********计算优惠券分摊或取得秒杀价格 messageId:[{}] 进入秒杀商品校验***********", messageId);
                validateTimelimitedProducts(messageId, cart, skuCodeList, skuCodeMap);
                if (cart.isHasTimelimitedProduct()) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.HAS_TIMELIMITED_SKU, "订单中有参加秒杀活动的商品");
                }
                if (targetPromotionMap != null && !targetPromotionMap.isEmpty()) {
                    validatePromotionProducts(messageId, targetPromotionMap);
                }
                //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
                logger.info("***********计算优惠券分摊或取得秒杀价格 messageId:[{}] 进入优惠券分摊计算***********", messageId);
                couponCodeList = cart.getCouponCodeList();
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//                cart = calculateCouponDiscount(messageId, couponCodeList, cart, dictMap);
                cart = calculateCouponDiscount(messageId, couponCodeList, cart, orderInfoMap, allProductList);
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
            }
            result.setResult(cart);
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
     * 根据选择的秒杀信息取得订单行的商品的秒杀价格
     *
     * @param messageId
     * @param promotionId
     * @param cart
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private TradeInfoDTO calculateTimelimitedDiscount(String messageId, String promotionId, TradeInfoDTO cart)
            throws MarketCenterBusinessException, Exception {
        logger.info("***********取得订单行的商品的秒杀价格 messageId:[{}],秒杀活动ID:[{}] 开始***********", messageId, promotionId);
        String buyerCode = cart.getBuyerCode();
        String buyerGrade = cart.getBuyerGrade();
        TimelimitedInfoDTO timelimitedInfo = null;
        BuyerCheckInfo buyerCheckInfo = new BuyerCheckInfo();
        List<OrderInfoDTO> orderList = cart.getOrderList();
        OrderInfoDTO orderInfo = orderList.get(0);
        List<OrderItemInfoDTO> productsList = orderInfo.getOrderItemList();
        OrderItemInfoDTO productInfo = productsList.get(0);
        try {
            timelimitedInfo = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
            if ((new Date()).before(timelimitedInfo.getEffectiveTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NO_START, "秒杀活动未开始");
            } else if ((new Date()).after(timelimitedInfo.getInvalidTime())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED, "秒杀活动已结束");
            }
            buyerCheckInfo.setBuyerCode(buyerCode);
            buyerCheckInfo.setBuyerGrade(buyerGrade);
            if (!checkPromotionBuyerRule(messageId, timelimitedInfo, buyerCheckInfo)) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_BUYER_NO_AUTHIORITY,
                        "会员没有该秒杀活动参加权限");
            }
            if (!productInfo.getSkuCode().equals(timelimitedInfo.getSkuCode())) {
                throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_SKUCODE_ERROR,
                        "订单中购买商品和参加秒杀活动商品不符合");
            }
            productInfo.setGoodsPrice(timelimitedInfo.getSkuTimelimitedPrice());
            productInfo.setGoodsPriceType(dictionary.getValueByCode(DictionaryConst.TYPE_SKU_PRICE_TYPE,
                    DictionaryConst.OPT_SKU_PRICE_TYPE_TIMELIMITED));
            productInfo.setHasTimelimitedFlag(true);
            productInfo.setTimelimitedInfo(timelimitedInfo);
            orderInfo.setPayTimeLimit(DateUtils.addMinutes(new Date(), timelimitedInfo.getTimelimitedValidInterval()));
            orderInfo.setTimelimitedOrder(true);
            cart.setHasTimelimitedProduct(true);
        } catch (MarketCenterBusinessException bcbe) {
            logger.info("***********取得订单行的商品的秒杀价格 messageId:[{}],错误信息:[{}] ***********", messageId, bcbe.getMessage());
            throw bcbe;
        } catch (Exception e) {
            throw e;
        }
        logger.info("***********取得订单行的商品的秒杀价格 messageId:[{}],秒杀活动ID:[{}],秒杀商品skuCode:[{}],秒杀价格:[{}] 结束***********",
                messageId, promotionId, productInfo.getSkuCode(), productInfo.getGoodsPrice());
        return cart;
    }

    /**
     * 根据选择的优惠券信息计算订单行的分摊金额
     *
     * @param messageId
     * @param couponCodeList
     * @param cart
     * @param orderInfoMap
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private TradeInfoDTO calculateCouponDiscount(String messageId, List<String> couponCodeList, TradeInfoDTO cart,
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
            Map<String, OrderInfoDTO> orderInfoMap, List<OrderItemInfoDTO> allProductList)
            throws MarketCenterBusinessException, Exception {
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],使用优惠券:[{}] 开始***********", messageId,
                JSON.toJSONString(couponCodeList));

        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        List<OrderItemCouponDTO> unavaliableCouponList = null;
//        OrderItemCouponDTO unavaliableCoupon = null;
        List<OrderItemCouponDTO> allCouponList = null;
        List<OrderItemCouponDTO> avaliableCouponList = new ArrayList<OrderItemCouponDTO>();
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        try {
            // 校验购物车商品是否有秒杀商品
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 start -----
//            validateTimelimitedProducts(messageId, cart);
//            if (cart.isHasTimelimitedProduct()) {
//                throw new MarketCenterBusinessException(MarketCenterCodeConst.HAS_TIMELIMITED_SKU, "订单中有参加秒杀活动的商品");
//            }
            //----- delete by jiangkun for 2017双12活动限时购 on 20171013 end -----
            if (couponCodeList != null && !couponCodeList.isEmpty()) {
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//                validateCouponProducts(messageId, cart, couponCodeList, orderInfoMap, allProductList, dictMap);
//                unavaliableCouponList = cart.getUnavaliableCouponList();
//                if (unavaliableCouponList != null && !unavaliableCouponList.isEmpty()) {
//                    unavaliableCoupon = unavaliableCouponList.get(0);
//                    logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],存在不可用优惠券:[{}] ***********", messageId,
//                            JSON.toJSONString(unavaliableCouponList));
//                    throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_NO_USE,
//                            unavaliableCoupon.getErrorMsg());
//                }
                allCouponList = validateCouponProducts(messageId, cart, couponCodeList, orderInfoMap, allProductList);
                if (allCouponList != null && !allCouponList.isEmpty()) {
                    for (OrderItemCouponDTO tmpItemCouponDTO : allCouponList) {
                        if (!StringUtils.isEmpty(tmpItemCouponDTO.getErrorMsg())) {
                            logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],存在不可用优惠券:[{}] ***********",
                                    messageId, JSON.toJSONString(tmpItemCouponDTO));
                            throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_NO_USE,
                                    tmpItemCouponDTO.getErrorMsg());
                        } else {
                            avaliableCouponList.add(tmpItemCouponDTO);
                        }
                    }
                    cart.setAvaliableCouponList(avaliableCouponList);
                }
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
                calculateAvaliableCoupon(messageId, cart);
            }
        } catch (MarketCenterBusinessException bcbe) {
            throw bcbe;
        } catch (Exception e) {
            throw e;
        }
        logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],使用优惠券:[{}] 结束***********", messageId,
                JSON.toJSONString(couponCodeList));
        return cart;
    }

    /**
     * 遍历购物车对于一个商品适用多个优惠券时，选取一张优惠券，因为一个商品只允许使用一张店铺优惠券，一张平台优惠券, 并将优惠券金额分配到每个商品上
     *
     * @param messageId
     * @param cart
     */
    private void calculateAvaliableCoupon(String messageId, TradeInfoDTO cart) {
        logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 开始***********", messageId);
        List<OrderItemCouponDTO> avalibleCouponList = cart.getAvaliableCouponList();
        List<OrderInfoDTO> orderList = null;
        List<OrderItemInfoDTO> orderItemList = null;
        Map<String, List<OrderItemCouponDTO>> avalibleShopCouponMap = new HashMap<String, List<OrderItemCouponDTO>>();
        List<OrderItemCouponDTO> avalibleCouponSubList = null;
        List<OrderItemCouponDTO> avaliblePlatformCouponList = new ArrayList<OrderItemCouponDTO>();
        String shopCouponType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP);
        String platformCouponType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_PLATFORM);
        Iterator<String> it = null;
        String sellerCode = "";
        String couponProviderType = "";

        if (avalibleCouponList == null || avalibleCouponList.isEmpty()) {
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 没有可用优惠券 结束***********", messageId);
            return;
        }
        for (OrderItemCouponDTO tmpCouponInfo : avalibleCouponList) {
            couponProviderType = tmpCouponInfo.getPromotionProviderType();
            sellerCode = tmpCouponInfo.getPromotionProviderSellerCode();
            if (couponProviderType.equals(shopCouponType)) {
                if (avalibleShopCouponMap.containsKey(sellerCode)) {
                    avalibleCouponSubList = avalibleShopCouponMap.get(sellerCode);
                } else {
                    avalibleCouponSubList = new ArrayList<OrderItemCouponDTO>();
                }
                avalibleCouponSubList.add(tmpCouponInfo);
                avalibleShopCouponMap.put(sellerCode, avalibleCouponSubList);
            } else if (couponProviderType.equals(platformCouponType)) {
                avaliblePlatformCouponList.add(tmpCouponInfo);
            }
        }
        if (!avalibleShopCouponMap.isEmpty()) {
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 计算店铺优惠券分摊金额 开始***********", messageId);
            it = avalibleShopCouponMap.keySet().iterator();
            while (it.hasNext()) {
                sellerCode = it.next();
                avalibleCouponSubList = avalibleShopCouponMap.get(sellerCode);
                shareCouponDiscountToProducts(messageId, shopCouponType, avalibleCouponSubList);
            }
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 计算店铺优惠券分摊金额 结束***********", messageId);
        }
        if (!avaliblePlatformCouponList.isEmpty()) {
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}]  计算平台优惠券分摊金额 开始***********", messageId);
            shareCouponDiscountToProducts(messageId, platformCouponType, avaliblePlatformCouponList);
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 计算平台优惠券分摊金额 结束***********", messageId);
        }
        orderList = cart.getOrderList();
        for (OrderInfoDTO orderInfo : orderList) {
            orderItemList = orderInfo.getOrderItemList();
            for (OrderItemInfoDTO orderItemInfo : orderItemList) {
                if (orderItemInfo.isHasUsedCoupon()) {
                    orderInfo.setHasUsedCoupon(true);
                    break;
                }
            }
        }
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
        cart.setAvaliableCouponList(null);
        cart.setUnavaliableCouponList(null);
        //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
        logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 结束***********", messageId);
    }

    /**
     * 优惠券优惠金额分摊到商品
     *
     * @param messageId
     * @param couponProvideType
     * @param couponList
     */
    private void shareCouponDiscountToProducts(String messageId, String couponProvideType,
            List<OrderItemCouponDTO> couponList) {
        BigDecimal payTotal = BigDecimal.ZERO;
        BigDecimal oldDiscountTotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        OrderItemCouponDTO targetCoupon = null;
        List<OrderItemInfoDTO> productList = null;
        List<OrderItemCouponDTO> productCouponList = null;
        OrderItemCouponDTO productCouponInfo = null;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal sharedDiscount = BigDecimal.ZERO;
        int count = 0;
        if (couponList == null || couponList.isEmpty()) {
            return;
        }
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
//        for (OrderItemCouponDTO couponInfo : couponList) {
//            discountTotal = calculateCouponTotalDiscountAmount(messageId, couponInfo, dictMap);
//            if (BigDecimal.ZERO.compareTo(discountTotal) >= 0) {
//                continue;
//            }
//            if (BigDecimal.ZERO.compareTo(oldDiscountTotal) == 0) {
//                oldDiscountTotal = discountTotal;
//                targetCoupon = couponInfo;
//            }
//            if (discountTotal.compareTo(oldDiscountTotal) > 0) {
//                targetCoupon = couponInfo;
//                oldDiscountTotal = discountTotal;
//            }
//        }
//        if (targetCoupon == null) {
//            return;
//        }
        targetCoupon = couponList.get(0);
        //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
        discountTotal = targetCoupon.getTotalDiscountAmount();
        productList = targetCoupon.getProductList();
        logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}],确定优惠券编码:[{}],优惠总金额:[{}] ***********", messageId,
                targetCoupon.getBuyerCouponCode(), discountTotal);
        payTotal = targetCoupon.getItemTotalAmount();
        for (OrderItemInfoDTO productDTO : productList) {
            count++;
            // 优惠金额*（单个商品总金额/活动商品总金额）
            discount = CalculateUtils
                    .divide(CalculateUtils.multiply(discountTotal, productDTO.getOrderItemTotal()), payTotal);
            if (productList.size() > 1 && count == productList.size()) {// 最后一件优惠=总优惠-前面的优惠金额之和（为防止最后校验不通过）
                if (discountTotal.compareTo(sharedDiscount) >= 0) {
                    discount = CalculateUtils.subtract(discountTotal, sharedDiscount);// 该商品优惠金额=优惠总金额-前面的金额之和
                }
            } else {
                if (CalculateUtils.subtract(discountTotal, sharedDiscount).compareTo(discount) < 0) {
                    discount = CalculateUtils.subtract(discountTotal, sharedDiscount);
                }
            }
            if (BigDecimal.ZERO.compareTo(discount) >= 0) {
                continue;
            }
            productDTO.setHasUsedCoupon(true);
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                    DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_PLATFORM).equals(couponProvideType)) {
                productDTO.setPlatformCouponDiscount(discount);
                logger.info(
                        "***********计算订单行的可用优惠券及分摊金额 messageId:[{}],确定优惠券编码:[{}],商品skuCode:[{}],分摊优惠金额:[{}]***********",
                        messageId, targetCoupon.getBuyerCouponCode(), productDTO.getSkuCode(), discount);
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                    DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP).equals(couponProvideType)) {
                productDTO.setShopCouponDiscount(discount);
                logger.info(
                        "***********计算订单行的可用优惠券及分摊金额 messageId:[{}],确定优惠券编码:[{}],商品skuCode:[{}],分摊优惠金额:[{}]***********",
                        messageId, targetCoupon.getBuyerCouponCode(), productDTO.getSkuCode(), discount);
            }
            sharedDiscount = CalculateUtils.add(sharedDiscount, discount);
            // 将商品适用优惠券及优惠分摊金额保存到商品中
            productCouponInfo = new OrderItemCouponDTO();
            productCouponInfo.setBuyerCouponInfo(targetCoupon);
            productCouponInfo.setSharedDiscountAmount(discount);
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 start -----
            deleteOrderItemCouponUselessInfo(productCouponInfo);
            //----- add by jiangkun for 2017双12活动限时购 on 20171013 end -----
            productCouponList = productDTO.getAvalibleCouponList();
            if (productCouponList == null) {
                productCouponList = new ArrayList<OrderItemCouponDTO>();
            }
            productCouponList.add(productCouponInfo);
            productDTO.setAvalibleCouponList(productCouponList);
            logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}],商品skuCode:[{}],分摊优惠总金额:[{}]***********", messageId,
                    productDTO.getSkuCode(), productDTO.getTotalDiscountAmount());
        }
    }

    /**
     * 根据优惠券列表计算每张优惠券的优惠总金额
     *
     * @param messageId
     * @param couponInfo
     * @return
     */
    private BigDecimal calculateCouponTotalDiscountAmount(String messageId, OrderItemCouponDTO couponInfo) {
        BigDecimal payTotal = BigDecimal.ZERO;
        String couponKind = "";
        BigDecimal discountTotal = BigDecimal.ZERO;
        BigDecimal leftDiscountAmount = BigDecimal.ZERO;
        BigDecimal discountPercent = BigDecimal.ZERO;
        couponKind = couponInfo.getCouponType();
        payTotal = getOrderItemsTotal(couponInfo.getProductList());
        couponInfo.setItemTotalAmount(payTotal);
        logger.info("***********计算可用优惠券优惠总金额 messageId:[{}],优惠券编码:[{}], 购买总金额:[{}],使用门槛:[{}]***********", messageId,
                couponInfo.getBuyerCouponCode(), payTotal, couponInfo.getDiscountThreshold());
        if (payTotal.compareTo(couponInfo.getDiscountThreshold()) >= 0) {
            if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_FULL_CUT)
                    .equals(couponKind)) {
                discountTotal =
                        payTotal.compareTo(couponInfo.getCouponAmount()) >= 0 ? couponInfo.getCouponAmount() : payTotal;
                logger.info("***********计算可用优惠券优惠总金额 messageId:[{}],优惠券编码:[{}],优惠总金额:[{}]***********", messageId,
                        couponInfo.getBuyerCouponCode(), discountTotal);
            } else if (dictionary
                    .getValueByCode(DictionaryConst.TYPE_COUPON_KIND, DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
                    .equals(couponKind)) {
                leftDiscountAmount = couponInfo.getCouponLeftAmount();
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 start -----
                if (BigDecimal.ZERO.compareTo(leftDiscountAmount) < 0) {
                    discountPercent =
                            CalculateUtils.divide(new BigDecimal(couponInfo.getDiscountPercent()), new BigDecimal(100));
                    discountTotal = CalculateUtils.multiply(payTotal, discountPercent);
                    discountTotal =
                            leftDiscountAmount.compareTo(discountTotal) > 0 ? discountTotal : leftDiscountAmount;
                    discountTotal = payTotal.compareTo(discountTotal) >= 0 ? discountTotal : payTotal;
                    logger.info("***********计算可用优惠券优惠总金额 messageId:[{}],优惠券编码:[{}],折扣比例:[{}],优惠总金额:[{}]***********",
                            messageId, couponInfo.getBuyerCouponCode(), discountPercent, discountTotal);
                }
                //----- modify by jiangkun for 2017双12活动限时购 on 20171013 end -----
            }
            if (BigDecimal.ZERO.compareTo(discountTotal) < 0) {
                couponInfo.setTotalDiscountAmount(discountTotal);
            }
        }
        return discountTotal;
    }

    /**
     * 取得对象商品列表的支付总金额
     *
     * @param productList
     * @return
     */
    private BigDecimal getOrderItemsTotal(List<OrderItemInfoDTO> productList) {
        BigDecimal orderItemTotal = BigDecimal.ZERO;
        if (productList != null && !productList.isEmpty()) {
            for (OrderItemInfoDTO productDTO : productList) {
                orderItemTotal = orderItemTotal.add(productDTO.getOrderItemTotal());
            }
        }
        return BigDecimal.ZERO.compareTo(orderItemTotal) >= 0 ? BigDecimal.ZERO : orderItemTotal;
    }

    /**
     * 检查优惠活动会员规则
     *
     * @param messageId
     * @param promotionAccuDTO
     * @param buyerInfo
     * @return
     */
    private boolean checkPromotionBuyerRule(String messageId, PromotionAccumulatyDTO promotionAccuDTO,
            BuyerCheckInfo buyerInfo) {
        PromotionBuyerRuleDTO ruleDTO = promotionAccuDTO.getBuyerRuleDTO();
        List<String> levelList = null;
        List<PromotionBuyerDetailDTO> detailList = null;
        if (ruleDTO == null) {
            return true;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(ruleDTO.getRuleTargetType())) {
            levelList = ruleDTO.getTargetBuyerLevelList();
            if (levelList == null || levelList.isEmpty()) {
                return true;
            }
            for (String level : levelList) {
                if (level.equals(buyerInfo.getBuyerGrade())) {
                    return true;
                }
            }
            return false;
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT).equals(ruleDTO.getRuleTargetType())) {
            detailList = ruleDTO.getBuyerDetailList();
            if (detailList == null || detailList.isEmpty()) {
                return true;
            }
            for (PromotionBuyerDetailDTO detailDTO : detailList) {
                if (detailDTO.getBuyerCode().equals(buyerInfo.getBuyerCode())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final class ValidBuyerAvaliableCouponTask implements Callable<List<OrderItemCouponDTO>> {

        private String messageId;

        private Map<String, OrderInfoDTO> orderInfoMap;

        private List<OrderItemInfoDTO> allProductsList;

        private List<String> targetBuyerCouponList;

        private Map<String, String> validMap;

        private boolean hasTargetCouponFlag;

        public ValidBuyerAvaliableCouponTask(String messageId, List<String> couponList, Map<String, String> validMap,
                boolean hasTargetCouponFlag, Map<String, OrderInfoDTO> orderInfoMap,
                List<OrderItemInfoDTO> allProductsList) {
            this.messageId = messageId;
            this.targetBuyerCouponList = couponList;
            this.validMap = validMap;
            this.hasTargetCouponFlag = hasTargetCouponFlag;
            this.orderInfoMap = orderInfoMap;
            this.allProductsList = allProductsList;
        }

        @Override
        public List<OrderItemCouponDTO> call() throws Exception{
            List<OrderItemCouponDTO> resultList = new ArrayList<OrderItemCouponDTO>();
            OrderItemCouponDTO tmpItemCouponDTO = null;
            int listSize = targetBuyerCouponList.size();
            if (listSize == 0) {
                return null;
            }
            for (String couponInfoStr : targetBuyerCouponList) {
                tmpItemCouponDTO =
                        checkBuyerCouponInfo(messageId, couponInfoStr, validMap, hasTargetCouponFlag, orderInfoMap,
                                allProductsList);
                if (tmpItemCouponDTO != null) {
                    resultList.add(tmpItemCouponDTO);
                }
            }
            return resultList;
        }
    }

    //----- add by jiangkun for 2017活动需求商城限时购 on 20171009 start -----
    public final class ValidTimelimitedDiscountTask implements Callable<String> {

        private String messageId;

        private Map<String, List<OrderItemInfoDTO>> promotionIdMap;

        public ValidTimelimitedDiscountTask(String messageId, Map<String, List<OrderItemInfoDTO>> promotionIdMap) {
            this.messageId = messageId;
            this.promotionIdMap = promotionIdMap;
        }

        @Override
        public String call() throws Exception {
            logger.info("***********校验购物车满足限时购商品 messageId:[{}] 开始***********", messageId);
            long startTime = System.currentTimeMillis();
            String retMsg = "处理正常";
            try {
                if (promotionIdMap != null && !promotionIdMap.isEmpty()) {
                    for (Entry<String, List<OrderItemInfoDTO>> entry : promotionIdMap.entrySet()) {
                        checkTimelimitedDiscountInfo(messageId, entry.getKey(), promotionIdMap.get(entry.getKey()));
                    }
                }
            } catch (Exception e) {
                retMsg = e.getMessage();
                throw e;
            } finally {
                long endTime = System.currentTimeMillis();
                logger.info("***********校验购物车满足限时购商品 messageId:[{}],处理结果:[{}],结束|调用耗时{}ms***********", messageId,
                        retMsg, (endTime - startTime));
            }
            return retMsg;
        }
    }
    //----- add by jiangkun for 2017活动需求商城限时购 on 20171009 end -----

    private OrderItemCouponDTO checkBuyerCouponInfo(String messageId, String couponInfoStr,
            Map<String, String> validMap, boolean hasTargetCouponFlag, Map<String, OrderInfoDTO> orderInfoMap,
            List<OrderItemInfoDTO> allProductsList) {
        BuyerCouponInfoDTO couponInfo = JSON.parseObject(couponInfoStr, BuyerCouponInfoDTO.class);
        String promotionId = "";
        String levelCode = "";
        String couponProviderType = "";
        String couponProviderCode = "";
        List<OrderItemInfoDTO> avaliableProductList = null;
        BigDecimal payTotal = BigDecimal.ZERO;
        if (couponInfo == null) {
            return null;
        }
        promotionId = couponInfo.getPromotionId();
        levelCode = couponInfo.getLevelCode();
        couponProviderType = couponInfo.getPromotionProviderType();
        couponProviderCode = couponInfo.getPromotionProviderSellerCode();
        if (new Date().before(couponInfo.getCouponStartTime())) {
            if (hasTargetCouponFlag) {
                return exchange2OrderItemCoupon(messageId, couponInfo, null,
                        "优惠券未到使用开始期限不能使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
            }
            return null;
        }
        if (!dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_UNUSED)
                .equals(couponInfo.getStatus())) {
            if (hasTargetCouponFlag) {
                return exchange2OrderItemCoupon(messageId, couponInfo, null,
                        "优惠券已被使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
            }
            return null;
        }
        if (new Date().after(couponInfo.getCouponEndTime())) {
            couponInfo.setStatus(dictionary
                    .getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
            updateExpireInvalidRedisCouponInfo(couponInfo);
            if (hasTargetCouponFlag) {
                return exchange2OrderItemCoupon(messageId, couponInfo, null,
                        "优惠券已过期 优惠券编号:" + couponInfo.getBuyerCouponCode());
            }
            return null;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_INVALID).equals(validMap.get(promotionId))) {
            couponInfo.setStatus(dictionary
                    .getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_INVALID));
            updateExpireInvalidRedisCouponInfo(couponInfo);
            if (hasTargetCouponFlag) {
                return exchange2OrderItemCoupon(messageId, couponInfo, null,
                        "优惠券已失效 优惠券编号:" + couponInfo.getBuyerCouponCode());
            }
            return null;
        }
        if (couponInfo.getDiscountThreshold() == null
                || BigDecimal.ZERO.compareTo(couponInfo.getDiscountThreshold()) >= 0) {
            return null;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP).equals(couponProviderType)) {
            if (!orderInfoMap.containsKey(couponProviderCode)) {
                logger.info(
                        "***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 优惠券不适用购买商品的店铺***********",
                        messageId, promotionId, levelCode);
                return exchange2OrderItemCoupon(messageId, couponInfo, null,
                        "优惠券不适用购买商品的店铺 优惠券编号:" + couponInfo.getBuyerCouponCode());
            }
            avaliableProductList =
                    checkAvaliableCouponProducts(couponInfo, orderInfoMap.get(couponProviderCode).getOrderItemList());
        } else {
            avaliableProductList = checkAvaliableCouponProducts(couponInfo, allProductsList);
        }
        if (avaliableProductList == null || avaliableProductList.isEmpty()) {
            logger.info(
                    "***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 会员购买商品都不满足优惠券使用规则***********",
                    messageId, promotionId, levelCode);
            return exchange2OrderItemCoupon(messageId, couponInfo, null,
                    "会员购买商品不满足优惠券使用规则 优惠券编号:" + couponInfo.getBuyerCouponCode());
        }
        payTotal = getOrderItemsTotal(avaliableProductList);
        if (payTotal.compareTo(couponInfo.getDiscountThreshold()) < 0) {
            logger.info("***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}],使用门槛:[{}] 低于使用门槛***********",
                    messageId, promotionId, levelCode, couponInfo.getDiscountThreshold());
            return exchange2OrderItemCoupon(messageId, couponInfo, null,
                    "满减券低于使用门槛 优惠券编号:" + couponInfo.getBuyerCouponCode());
        }
        logger.info("***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 可用优惠券 ***********", messageId,
                promotionId, levelCode);
        return exchange2OrderItemCoupon(messageId, couponInfo, avaliableProductList);
    }

    private OrderItemCouponDTO exchange2OrderItemCoupon(String messageId, BuyerCouponInfoDTO targetBuyerCoupon,
            List<OrderItemInfoDTO> avaliableProductList, String... errorMsg) {
        OrderItemCouponDTO orderCoupon = new OrderItemCouponDTO();
        orderCoupon.setBuyerCouponInfo(targetBuyerCoupon);
        if (avaliableProductList != null && !avaliableProductList.isEmpty()) {
            orderCoupon.setProductList(avaliableProductList);
            //----- add by jiangkun for 2017双12活动限时购 on 20171016 start -----
            getBuyerCouponLeftAmount(orderCoupon);
            //----- add by jiangkun for 2017双12活动限时购 on 20171016 end -----
            calculateCouponTotalDiscountAmount(messageId, orderCoupon);
        }
        if (errorMsg != null && errorMsg.length > 0) {
            orderCoupon.setErrorMsg(errorMsg[0]);
        }
        return orderCoupon;
    }

    private List<OrderItemInfoDTO> checkAvaliableCouponProducts(BuyerCouponInfoDTO targetBuyerCoupon,
            List<OrderItemInfoDTO> productsList) {
        List<OrderItemInfoDTO> couponProductList = new ArrayList<OrderItemInfoDTO>();
        SellerProductInfo sellerProductInfo = null;

        for (OrderItemInfoDTO productDTO : productsList) {
            //----- add by jiangkun for 2017双12活动限时购 on 20171016 start -----
            if (!StringUtils.isEmpty(productDTO.getPromotionId())) {
                continue;
            }
            //----- add by jiangkun for 2017双12活动限时购 on 20171016 end -----
            if (BigDecimal.ZERO.compareTo(productDTO.getOrderItemTotal()) >= 0) {
                continue;
            }
            sellerProductInfo = new SellerProductInfo();
            sellerProductInfo.setSellerCode(productDTO.getSellerCode());
            sellerProductInfo.setCategoryId(productDTO.getThirdCategoryId());
            sellerProductInfo.setBrandId(productDTO.getBrandId());
            sellerProductInfo.setChannelCode(productDTO.getChannelCode());
            sellerProductInfo.setSkuCode(productDTO.getSkuCode());
            if (!checkPromotionSellerRule(targetBuyerCoupon, sellerProductInfo) || !checkPromotionCategoryItemRule(
                    targetBuyerCoupon, sellerProductInfo)) {
                continue;
            }
            couponProductList.add(productDTO);
        }
        return couponProductList;
    }

    private boolean checkPromotionSellerRule(BuyerCouponInfoDTO targetBuyerCoupon, SellerProductInfo productInfo) {
        PromotionSellerRuleDTO ruleDTO = targetBuyerCoupon.getSellerRuleDTO();
        String sellerType = "";
        List<PromotionSellerDetailDTO> detailList = null;
        List<String> sellerCodeList = null;
        String channelCode = "";

        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP).equals(targetBuyerCoupon.getPromotionProviderType())
                && !targetBuyerCoupon.getPromotionProviderSellerCode().equals(productInfo.getSellerCode())) {
            return false;
        }
        if (ruleDTO == null) {
            return true;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
                DictionaryConst.OPT_PROMOTION_SELLER_RULE_APPIONT).equals(ruleDTO.getRuleTargetType())) {
            sellerType = ruleDTO.getTargetSellerType();
            if (StringUtils.isEmpty(sellerType)) {
                return true;
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
                    DictionaryConst.OPT_PROMOTION_SELLER_TYPE_ALL).equals(sellerType)) {
                return true;
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
                    DictionaryConst.OPT_PROMOTION_SELLER_TYPE_HTD).equals(sellerType) && dictionary
                    .getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_HTD)
                    .equals(productInfo.getChannelCode())) {
                return true;
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
                    DictionaryConst.OPT_PROMOTION_SELLER_TYPE_OUTER).equals(sellerType) && dictionary
                    .getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL, DictionaryConst.OPT_PRODUCT_CHANNEL_OUTER)
                    .equals(productInfo.getChannelCode())) {
                return true;
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
                    DictionaryConst.OPT_PROMOTION_SELLER_TYPE_PRODUCTPLUS).equals(sellerType)) {
                channelCode = dictionary.getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL,
                        DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS);
                if (productInfo.getChannelCode().startsWith(channelCode)) {
                    return true;
                }
            }
            return false;
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
                DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART).equals(ruleDTO.getRuleTargetType())) {
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
            detailList = ruleDTO.getSellerDetailList();
            sellerCodeList = ruleDTO.getTargetSellerCodeList();
            if ((detailList == null || detailList.isEmpty()) && (sellerCodeList == null || sellerCodeList.isEmpty())) {
                return true;
            }
            if (detailList != null && !detailList.isEmpty()) {
                for (PromotionSellerDetailDTO detailDTO : detailList) {
                    if (detailDTO.getSellerCode().equals(productInfo.getSellerCode())) {
                        return true;
                    }
                }
            }
            if (sellerCodeList != null && !sellerCodeList.isEmpty()) {
                if (sellerCodeList.contains(productInfo.getSellerCode())) {
                    return true;
                }
            }
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 end -----
            return false;
        }
        return true;
    }

    private boolean checkPromotionCategoryItemRule(BuyerCouponInfoDTO targetBuyerCoupon,
            SellerProductInfo productInfo) {
        PromotionCategoryItemRuleDTO ruleDTO = targetBuyerCoupon.getCategoryItemRuleDTO();
        List<PromotionCategoryDetailDTO> categoryDetailList = null;
        List<PromotionItemDetailDTO> itemDetailList = null;
        Map<String, String> categoryCodeMap = null;
        List<String> skuCodeList = null;
        String itemLimit = "";
        String brandIdStr = "";

        if (ruleDTO == null) {
            return true;
        }

        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
                DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY).equals(ruleDTO.getRuleTargetType())) {
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
            categoryDetailList = ruleDTO.getCategoryDetailList();
            categoryCodeMap = ruleDTO.getTargetCategoryCodeMap();
            if ((categoryDetailList == null || categoryDetailList.isEmpty()) && (categoryCodeMap == null
                    || categoryCodeMap.isEmpty())) {
                return true;
            }
            if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                for (PromotionCategoryDetailDTO categoryDetailDTO : categoryDetailList) {
                    if (categoryDetailDTO.getCategoryId().longValue() == productInfo.getCategoryId().longValue()) {
                        if (categoryDetailDTO.getBids() == null || categoryDetailDTO.getBids().isEmpty()) {
                            return true;
                        }
                        for (Long brandId : categoryDetailDTO.getBids()) {
                            if (brandId.longValue() == productInfo.getBrandId().longValue()) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (categoryCodeMap != null && !categoryCodeMap.isEmpty()) {
                if (categoryCodeMap.containsKey(productInfo.getCategoryId().toString())) {
                    brandIdStr = categoryCodeMap.get(productInfo.getCategoryId().toString());
                    if (StringUtils.isEmpty(brandIdStr)) {
                        return true;
                    }
                    if (("," + productInfo.getBrandId().toString() + ",").indexOf("," + brandIdStr + ",") >= 0) {
                        return true;
                    }
                }
            }
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 end -----
            return false;
        } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
                DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM).equals(ruleDTO.getRuleTargetType())) {
            itemLimit = ruleDTO.getTargetItemLimit();
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 start -----
            itemDetailList = ruleDTO.getItemDetailList();
            skuCodeList = ruleDTO.getTargetItemCodeList();
            if ((itemDetailList == null || itemDetailList.isEmpty()) && (skuCodeList == null || skuCodeList
                    .isEmpty())) {
                return true;
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_TYPE,
                    DictionaryConst.OPT_PROMOTION_ITEM_TYPE_SUIT).equals(itemLimit)) {
                if (itemDetailList != null && !itemDetailList.isEmpty()) {
                    for (PromotionItemDetailDTO itemDetailDTO : itemDetailList) {
                        if (itemDetailDTO.getSkuCode().equals(productInfo.getSkuCode())) {
                            return true;
                        }
                    }
                }
                if (skuCodeList != null && !skuCodeList.isEmpty()) {
                    if (skuCodeList.contains(productInfo.getSkuCode())) {
                        return true;
                    }
                }
                return false;
            } else if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_TYPE,
                    DictionaryConst.OPT_PROMOTION_ITEM_TYPE_UNSUIT).equals(itemLimit)) {
                if (itemDetailList != null && !itemDetailList.isEmpty()) {
                    for (PromotionItemDetailDTO itemDetailDTO : itemDetailList) {
                        if (itemDetailDTO.getSkuCode().equals(productInfo.getSkuCode())) {
                            return false;
                        }
                    }
                }
                if (skuCodeList != null && !skuCodeList.isEmpty()) {
                    if (skuCodeList.contains(productInfo.getSkuCode())) {
                        return false;
                    }
                }
                return true;
            }
            //----- modify by jiangkun for 2017活动需求商城无敌券 on 20171009 end -----
        }
        return true;
    }

    private void checkTimelimitedDiscountInfo(String messageId, String promotionId, List<OrderItemInfoDTO> orderItemInfoList) {
        Date nowDt = new Date();
        TimelimitedInfoDTO timelimitedInfoDTO = null;
        List<?> accuDTOList = null;
        Map<String, TimelimitedInfoDTO> timelimitedInfoDTOMap = new HashMap<String, TimelimitedInfoDTO>();
        TimelimitedInfoDTO tmpTimelimitedInfoDTO = null;
        Integer goodsCount = 0;
        String skuCode = "";
        int stockNum = 0;
        TimelimitedResultDTO resultDTO = null;
        TimelimitedInfoDTO tmpNewTimelimitedInfoDTO = null;

        timelimitedInfoDTO = timelimitedRedisHandle.getRedisTimelimitedInfo(promotionId);
        accuDTOList = timelimitedInfoDTO.getPromotionAccumulatyList();
        if (accuDTOList != null && !accuDTOList.isEmpty()) {
            for (int i = 0; i < accuDTOList.size(); i++) {
                tmpTimelimitedInfoDTO = (TimelimitedInfoDTO) accuDTOList.get(i);
                timelimitedInfoDTOMap.put(tmpTimelimitedInfoDTO.getSkuCode(), tmpTimelimitedInfoDTO);
            }
        }
        for (OrderItemInfoDTO itemInfoDTO : orderItemInfoList) {
            skuCode = itemInfoDTO.getSkuCode();
            if (!timelimitedInfoDTOMap.containsKey(skuCode)) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}] 限时购活动中不包含会员购买商品***********",
                        messageId, promotionId, skuCode);
                throw new MarketCenterBusinessException(
                        MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NO_CONTAIN_SKU,
                        "限时购活动中不包含购买商品 活动编号:" + promotionId + " SKU编码:" + skuCode);
            }
            tmpTimelimitedInfoDTO = timelimitedInfoDTOMap.get(skuCode);
            if (nowDt.after(tmpTimelimitedInfoDTO.getEndTime())) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}],结束时间:[{}] 购买商品的限时购活动已结束***********",
                        messageId, promotionId, skuCode, tmpTimelimitedInfoDTO.getEndTime());
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_IS_OVER,
                        "购买商品的限时购活动已结束 活动编号:" + promotionId + " SKU编码:" + skuCode + " 结束时间:"
                                + tmpTimelimitedInfoDTO.getEndTime());
            }
            if (nowDt.before(tmpTimelimitedInfoDTO.getStartTime())) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}],开始时间:[{}] 购买商品的限时购活动未开始***********",
                        messageId, promotionId, skuCode, tmpTimelimitedInfoDTO.getStartTime());
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NOT_BEGIN,
                        "购买商品的限时购活动未开始 活动编号:" + promotionId + " SKU编码:" + skuCode + " 开始时间:"
                                + tmpTimelimitedInfoDTO.getStartTime());
            }
            goodsCount = itemInfoDTO.getGoodsCount();
            if (goodsCount < tmpTimelimitedInfoDTO.getTimelimitedThresholdMin()) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}],起订量:[{}],购买数量:[{}] 会员购买商品数量不满足限时购起订量***********",
                        messageId, promotionId, skuCode, tmpTimelimitedInfoDTO.getTimelimitedThresholdMin(),
                        goodsCount);
                throw new MarketCenterBusinessException(
                        MarketCenterCodeConst.LIMITED_TIME_PURCHASE_MIN_QUANTITY,
                        "会员购买商品数量不满足限时购起订量 活动编号:" + promotionId + " SKU编码:" + skuCode + " 起订量:"
                                + tmpTimelimitedInfoDTO.getTimelimitedThresholdMin() + " 购买数量:" + goodsCount);
            }
            if (goodsCount > tmpTimelimitedInfoDTO.getTimelimitedThreshold()) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}],限购量:[{}],购买数量:[{}] 会员购买商品数量超过限时购限购量***********",
                        messageId, promotionId, skuCode, tmpTimelimitedInfoDTO.getTimelimitedThreshold(),
                        goodsCount);
                throw new MarketCenterBusinessException(
                        MarketCenterCodeConst.LIMITED_TIME_PURCHASE_MAX_QUANTITY,
                        "会员购买商品数量超过限时购限购量 活动编号:" + promotionId + " SKU编码:" + skuCode + " 限购量:"
                                + tmpTimelimitedInfoDTO.getTimelimitedThreshold() + " 购买数量:" + goodsCount);
            }
            resultDTO = tmpTimelimitedInfoDTO.getTimelimitedResult();
            stockNum = Integer.valueOf(resultDTO.getShowRemainSkuCount()).intValue();
            if (goodsCount > stockNum) {
                logger.info(
                        "***********校验购物车满足限时购商品 messageId:[{}],限时购活动编码:[{}],SKU编码:[{}],购买数量:[{}],库存数量:[{}] 会员购买商品超过限时购商品数量***********",
                        messageId, promotionId, skuCode, itemInfoDTO.getGoodsPrice(),
                        tmpTimelimitedInfoDTO.getSkuTimelimitedPrice());
                throw new MarketCenterBusinessException(MarketCenterCodeConst.LIMITED_TIME_PURCHASE_NO_COUNT,
                        "会员购买商品数量超过限时购限购量 活动编号:" + promotionId + " SKU编码:" + skuCode + " 购买数量:" + goodsCount
                                + " 库存数量:" + stockNum);
            }
            itemInfoDTO.setGoodsPrice(tmpTimelimitedInfoDTO.getSkuTimelimitedPrice());
            itemInfoDTO.setGoodsPriceType(dictionary.getValueByCode(DictionaryConst.TYPE_SKU_PRICE_TYPE,
                    DictionaryConst.OPT_SKU_PRICE_TYPE_LIMITED_DISCOUNT));
            itemInfoDTO.setGoodsTotal(itemInfoDTO.getGoodsPrice().multiply(new BigDecimal(goodsCount)));
            tmpNewTimelimitedInfoDTO = new TimelimitedInfoDTO();
            tmpNewTimelimitedInfoDTO.setPromotionId(tmpTimelimitedInfoDTO.getPromotionId());
            tmpNewTimelimitedInfoDTO.setLevelCode(tmpTimelimitedInfoDTO.getLevelCode());
            itemInfoDTO.setTimelimitedInfo(tmpTimelimitedInfoDTO);
        }
    }
}

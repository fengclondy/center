package cn.htd.marketcenter.service.impl.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import javax.annotation.Resource;

import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
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
import cn.htd.marketcenter.dto.TradeInfoDTO;
import cn.htd.marketcenter.service.BuyerInterestValidService;
import cn.htd.marketcenter.service.handle.TimelimitedRedisHandle;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("buyerInterestValidService")
public class BuyerInterestValidServiceImpl implements BuyerInterestValidService {

	private static final Logger logger = LoggerFactory.getLogger(BuyerInterestValidServiceImpl.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private MarketCenterRedisDB marketRedisDB;;

	@Resource
	private TimelimitedRedisHandle timelimitedRedisHandle;

	/**
	 * 初始化校验用字典信息
	 *
	 * @param dictMap
	 * @param dictKey
	 */
	private void initDictionaryMap(Map<String, String> dictMap, String dictKey) {
		List<DictionaryInfo> dictionaryList = null;
		dictionaryList = dictionary.getDictionaryOptList(dictKey);
		if (dictionaryList != null && !dictionaryList.isEmpty()) {
			for (DictionaryInfo dictionaryInfo : dictionaryList) {
				dictMap.put(dictKey + "&" + dictionaryInfo.getCode(), dictionaryInfo.getValue());
			}
		}
	}

	/**
	 * 初始化校验优惠券用字典信息
	 *
	 * @return
	 */
	private Map<String, String> initCouponCheckDictMap() {
		Map<String, String> dictMap = new HashMap<String, String>();
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_BUYER_RULE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_SELLER_RULE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_SELLER_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PRODUCT_CHANNEL);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_ITEM_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_COUPON_STATUS);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_SKU_PRICE_TYPE);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_COUPON_KIND);
		initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS);
		return dictMap;
	}

	@Override
	public ExecuteResult<TradeInfoDTO> getAvailableCouponInfo(String messageId, TradeInfoDTO cart) {
		ExecuteResult<TradeInfoDTO> result = new ExecuteResult<TradeInfoDTO>();
		List<OrderInfoDTO> orderList = cart.getOrderList();
		List<OrderItemInfoDTO> productsList = null;
		Map<String, String> dictMap = initCouponCheckDictMap();
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
			}
			// 校验购物车商品是否有秒杀商品
			validateTimelimitedProducts(messageId, cart);
			validateCouponProducts(messageId, cart, null, dictMap);
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
	 * 校验购物车中是否有秒杀商品
	 *
	 * @param messageId
	 * @param cart
	 * @throws MarketCenterBusinessException
	 */
	private void validateTimelimitedProducts(String messageId, TradeInfoDTO cart) throws MarketCenterBusinessException {
		logger.info("***********校验购物车中是否有秒杀商品 messageId:[{}] 开始***********", messageId);
		long startTime = System.currentTimeMillis();
		List<OrderInfoDTO> orderList = cart.getOrderList();
		List<OrderItemInfoDTO> productsList = null;
		List<String> skuCodeList = new ArrayList<String>();
		Map<String, List<OrderItemInfoDTO>> skuCodeMap = new HashMap<String, List<OrderItemInfoDTO>>();
		List<OrderItemInfoDTO> tmpProductsList = null;
		List<TimelimitedInfoDTO> timelimitedInfoList = null;
		String skuCode = "";
		boolean hasTimelimitedSkuFlg = false;

		for (OrderInfoDTO orderInfoDTO : orderList) {
			productsList = orderInfoDTO.getOrderItemList();
			for (OrderItemInfoDTO productDTO : productsList) {
				skuCode = productDTO.getSkuCode();
				skuCodeList.add(skuCode);
				if (skuCodeMap.containsKey(skuCode)) {
					tmpProductsList = skuCodeMap.get(skuCode);
				} else {
					tmpProductsList = new ArrayList<OrderItemInfoDTO>();
				}
				tmpProductsList.add(productDTO);
				skuCodeMap.put(skuCode, tmpProductsList);
			}
		}
		timelimitedInfoList = timelimitedRedisHandle.getRedisTimelimitedInfoBySkuCode(skuCodeList);
		if (timelimitedInfoList != null && !timelimitedInfoList.isEmpty()) {
			hasTimelimitedSkuFlg = true;
			for (TimelimitedInfoDTO timelimitedInfo : timelimitedInfoList) {
				skuCode = timelimitedInfo.getSkuCode();
				tmpProductsList = skuCodeMap.get(skuCode);
				for (OrderItemInfoDTO tmpProductDTO : tmpProductsList) {
					tmpProductDTO.setHasTimelimitedFlag(true);
					tmpProductDTO.setTimelimitedInfo(timelimitedInfo);
				}
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
	 * @param jedis
	 * @param couponInfo
	 */
	private void updateExpireInvalidRedisCouponInfo(Jedis jedis, BuyerCouponInfoDTO couponInfo) {
		String buyerCode = couponInfo.getBuyerCode();
		String buyerCouponCode = couponInfo.getBuyerCouponCode();
		String buyerCouponLeftAmount = jedis.hget(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" +
				buyerCouponCode);
		buyerCouponLeftAmount = StringUtils.isEmpty(buyerCouponLeftAmount) || "nil".equals(buyerCouponLeftAmount) ? "0" : buyerCouponLeftAmount;
		couponInfo.setCouponLeftAmount(
				CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
		jedis.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode, JSON.toJSONString(couponInfo));
		jedis.rpush(
				RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCode + "_" + buyerCouponCode,
				JSON.toJSONString(couponInfo));
		jedis.sadd(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCode + "_" + buyerCouponCode);
	}

	/**
	 * 校验可用会员可用优惠券
	 *
	 * @param messageId
	 * @param cart
	 * @param targetCouponCodeList
	 * @param dictMap
	 * @return
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	private void validateCouponProducts(String messageId, TradeInfoDTO cart, List<String> targetCouponCodeList,
			Map<String, String> dictMap) throws MarketCenterBusinessException, Exception {
		logger.info("***********校验可用会员可用优惠券 messageId:[{}] 开始***********", messageId);
		long startTime = System.currentTimeMillis();
		Jedis jedis = null;
		Map<String, String> valueMap = null;
		Map<String, String> validMap = null;
		BuyerCouponInfoDTO couponInfo = null;
		List<OrderInfoDTO> orderList = cart.getOrderList();
		Map<String, OrderInfoDTO> orderInfoMap = new HashMap<String, OrderInfoDTO>();
		List<OrderItemCouponDTO> allCouponList = new ArrayList<OrderItemCouponDTO>();
		List<OrderItemCouponDTO> avaliableCouponList = new ArrayList<OrderItemCouponDTO>();
		List<OrderItemCouponDTO> unavaliableCouponList = new ArrayList<OrderItemCouponDTO>();
		List<OrderItemInfoDTO> allProductList = new ArrayList<OrderItemInfoDTO>();
		OrderItemCouponDTO tmpOrderCoupon = null;
		String couponValidStatus = "";
		String sellerCode = "";
		String buyerCode = cart.getBuyerCode();
		String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
		String unusedStatus = dictMap
				.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_UNUSED);
		String expirdStatus = dictMap
				.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_EXPIRE);
		String invalidStatus = dictMap
				.get(DictionaryConst.TYPE_COUPON_STATUS + "&" + DictionaryConst.OPT_COUPON_STATUS_INVALID);
		String invalidPromotionStatus = dictMap.get(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS + "&" + DictionaryConst
				.OPT_PROMOTION_VERIFY_STATUS_INVALID);
		String buyerCouponCode = "";
		String buyerCouponValue = "";
		String buyerCouponLeftAmount = "";
		List<BuyerCouponInfoDTO> couponInfoList = new ArrayList<BuyerCouponInfoDTO>();
		ForkJoinPool forkJoinPool = null;
		Future<List<OrderItemCouponDTO>> taskResult = null;
		boolean hasTargetCouponFlag = false;

		if (targetCouponCodeList != null && !targetCouponCodeList.isEmpty()) {
			hasTargetCouponFlag = true;
		}
		for (OrderInfoDTO tmpOrderInfoDTO : orderList) {
			sellerCode = tmpOrderInfoDTO.getSellerCode();
			orderInfoMap.put(sellerCode, tmpOrderInfoDTO);
			allProductList.addAll(tmpOrderInfoDTO.getOrderItemList());
		}
		try {
			jedis = marketRedisDB.getResource();
			if (!jedis.exists(buyerCouponRedisKey)) {
				return;
			}
			valueMap = jedis.hgetAll(buyerCouponRedisKey);
			validMap = jedis.hgetAll(RedisConst.REDIS_COUPON_VALID);
			forkJoinPool = new ForkJoinPool();
			for (Entry<String, String> entry : valueMap.entrySet()) {
				buyerCouponCode = entry.getKey();
				buyerCouponValue = entry.getValue();
				couponInfo = JSON.parseObject(buyerCouponValue, BuyerCouponInfoDTO.class);
				if (couponInfo == null) {
					continue;
				}
				if (hasTargetCouponFlag && !targetCouponCodeList.contains(buyerCouponCode)) {
					continue;
				}
				if (new Date().before(couponInfo.getCouponStartTime())) {
					if (hasTargetCouponFlag) {
						tmpOrderCoupon = new OrderItemCouponDTO();
						tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
						tmpOrderCoupon.setErrorMsg("优惠券未到使用开始期限不能使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
						unavaliableCouponList.add(tmpOrderCoupon);
					}
					continue;

				}
				if (!unusedStatus.equals(couponInfo.getStatus())) {
					if (hasTargetCouponFlag) {
						tmpOrderCoupon = new OrderItemCouponDTO();
						tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
						tmpOrderCoupon.setErrorMsg("优惠券已被使用 优惠券编号:" + couponInfo.getBuyerCouponCode());
						unavaliableCouponList.add(tmpOrderCoupon);
					}
					continue;
				}
				if (new Date().after(couponInfo.getCouponEndTime())) {
					couponInfo.setStatus(expirdStatus);
					updateExpireInvalidRedisCouponInfo(jedis, couponInfo);
					if (hasTargetCouponFlag) {
						tmpOrderCoupon = new OrderItemCouponDTO();
						tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
						tmpOrderCoupon.setErrorMsg("优惠券已过期 优惠券编号:" + couponInfo.getBuyerCouponCode());
						unavaliableCouponList.add(tmpOrderCoupon);
					}
					continue;
				}
				couponValidStatus = validMap.get(couponInfo.getPromotionId());
				if (invalidPromotionStatus.equals(couponValidStatus)) {
					couponInfo.setStatus(invalidStatus);
					updateExpireInvalidRedisCouponInfo(jedis, couponInfo);
					if (hasTargetCouponFlag) {
						tmpOrderCoupon = new OrderItemCouponDTO();
						tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
						tmpOrderCoupon.setErrorMsg("优惠券已失效 优惠券编号:" + couponInfo.getBuyerCouponCode());
						unavaliableCouponList.add(tmpOrderCoupon);
					}
					continue;
				}
				if (couponInfo.getDiscountThreshold() == null
						|| BigDecimal.ZERO.compareTo(couponInfo.getDiscountThreshold()) >= 0) {
					continue;
				}
				buyerCouponLeftAmount = jedis.hget(RedisConst.REDIS_BUYER_COUPON_AMOUNT,
						buyerCode + "&" + buyerCouponCode);
				if (StringUtils.isEmpty(buyerCouponLeftAmount) || "nil".equals(buyerCouponLeftAmount)) {
					if (hasTargetCouponFlag) {
						tmpOrderCoupon = new OrderItemCouponDTO();
						tmpOrderCoupon.setBuyerCouponInfo(couponInfo);
						tmpOrderCoupon.setErrorMsg("优惠券余额不足 优惠券编号:" + couponInfo.getBuyerCouponCode());
						unavaliableCouponList.add(tmpOrderCoupon);
					}
					continue;
				}
				couponInfo.setCouponLeftAmount(
						CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
				couponInfoList.add(couponInfo);
			}
			long endTime0 = System.currentTimeMillis();
			logger.info("***********取得会员所有优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
					(endTime0 - startTime));
			if (!couponInfoList.isEmpty()) {
				taskResult = forkJoinPool.submit(new ValidBuyerAvaliableCouponTask(messageId, dictMap, couponInfoList,
						orderInfoMap, allProductList));
				allCouponList = taskResult.get();
				long endTime1 = System.currentTimeMillis();
				logger.info("***********校验会员优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
						(endTime1 - endTime0));
				if (allCouponList != null && !allCouponList.isEmpty()) {
					for (OrderItemCouponDTO tmpItemCouponDTO : allCouponList) {
						if (StringUtils.isEmpty(tmpItemCouponDTO.getErrorMsg())) {
							avaliableCouponList.add(tmpItemCouponDTO);
						} else {
							unavaliableCouponList.add(tmpItemCouponDTO);
						}
					}
				}
				long endTime2 = System.currentTimeMillis();
				logger.info("***********切割会员可用不可用优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId,
						(endTime2 - endTime1));
			}
			cart.setAvaliableCouponList(avaliableCouponList);
			cart.setUnavaliableCouponList(unavaliableCouponList);
		} catch (InterruptedException ie) {
			logger.error("***********校验会员可用优惠券 messageId:[{}],错误信息:[{}] ***********", messageId,
					ExceptionUtils.getStackTraceAsString(ie));
		} catch (ExecutionException ee) {
			logger.error("***********校验会员可用优惠券 messageId:[{}],错误信息:[{}] ***********", messageId,
					ExceptionUtils.getStackTraceAsString(ee));
		} finally {
			if (forkJoinPool != null) {
				forkJoinPool.shutdown();
			}
			marketRedisDB.releaseResource(jedis);
			long endTime = System.currentTimeMillis();
			logger.info("***********校验会员可用优惠券 messageId:[{}] 结束|调用耗时{}ms***********", messageId, (endTime - startTime));
		}
	}

	@Override
	public ExecuteResult<TradeInfoDTO> calculateTradeDiscount(String messageId, TradeInfoDTO cart) {
		ExecuteResult<TradeInfoDTO> result = new ExecuteResult<TradeInfoDTO>();
		List<OrderInfoDTO> orderList = cart.getOrderList();
		List<OrderItemInfoDTO> productsList = null;
		String promotionType = "";
		boolean isValidPromotionType = false;
		List<String> couponCodeList = null;
		Map<String, String> dictMap = initCouponCheckDictMap();
		String key = "";
		String value = "";
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
			if (StringUtils.isEmpty(promotionType)) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "参加促销活动类型不能为空");
			}
			if (orderList.isEmpty()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "订单信息不能为空");
			}

			for (Entry<String, String> entry : dictMap.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.startsWith(DictionaryConst.TYPE_PROMOTION_TYPE) && value.equals(promotionType)) {
					isValidPromotionType = true;
					break;
				}
			}
			if (!isValidPromotionType) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "参加促销活动类型不正确");
			}
			// 秒杀活动时
			if (dictMap.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
					.equals(promotionType)) {
				if (orderList.size() > 1) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "秒杀活动订单只能单个购买");
				}
				if (StringUtils.isEmpty(cart.getPromotionId())) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "参加秒杀活动编号不能为空");
				}
				// 使用优惠券时
			} else if (dictMap
					.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
					.equals(promotionType)) {
				if (cart.getCouponCodeList() == null || cart.getCouponCodeList().isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "使用优惠券时优惠券编码不能为空");
				}
			}
			for (OrderInfoDTO productsDTO : orderList) {
				productsList = productsDTO.getOrderItemList();
				if (productsList.isEmpty()) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "购买商品不能为空");
				}
				// 秒杀活动时
				if (dictMap
						.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
						.equals(promotionType) && productsList.size() > 1) {
					throw new MarketCenterBusinessException(MarketCenterCodeConst.PARAMETER_ERROR, "秒杀活动订单只能单个购买");
				}
			}
			// 秒杀活动时
			if (dictMap.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED)
					.equals(promotionType)) {
				logger.info("***********计算优惠券分摊或取得秒杀价格 messageId:[{}] 进入秒杀计算***********", messageId);
				cart = calculateTimelimitedDiscount(messageId, cart.getPromotionId(), cart, dictMap);
				// 使用优惠券时
			} else if (dictMap
					.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_COUPON)
					.equals(promotionType)) {
				logger.info("***********计算优惠券分摊或取得秒杀价格 messageId:[{}] 进入优惠券分摊计算***********", messageId);
				couponCodeList = cart.getCouponCodeList();
				cart = calculateCouponDiscount(messageId, couponCodeList, cart, dictMap);
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
	 * @param dictMap
	 * @return
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	private TradeInfoDTO calculateTimelimitedDiscount(String messageId, String promotionId, TradeInfoDTO cart,
			Map<String, String> dictMap) throws MarketCenterBusinessException, Exception {
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
			if (!checkPromotionBuyerRule(messageId, timelimitedInfo, buyerCheckInfo, dictMap)) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_BUYER_NO_AUTHIORITY,
						"会员没有该秒杀活动参加权限");
			}
			if (!productInfo.getSkuCode().equals(timelimitedInfo.getSkuCode())) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.TIMELIMITED_SKUCODE_ERROR,
						"订单中购买商品和参加秒杀活动商品不符合");
			}
			productInfo.setGoodsPrice(timelimitedInfo.getSkuTimelimitedPrice());
			productInfo.setGoodsPriceType(dictMap
					.get(DictionaryConst.TYPE_SKU_PRICE_TYPE + "&" + DictionaryConst.OPT_SKU_PRICE_TYPE_TIMELIMITED));
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
	 * @param dictMap
	 * @return
	 * @throws MarketCenterBusinessException
	 * @throws Exception
	 */
	private TradeInfoDTO calculateCouponDiscount(String messageId, List<String> couponCodeList, TradeInfoDTO cart,
			Map<String, String> dictMap) throws MarketCenterBusinessException, Exception {
		logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],使用优惠券:[{}] 开始***********", messageId,
				JSON.toJSONString(couponCodeList));
		List<OrderItemCouponDTO> unavaliableCouponList = null;
		OrderItemCouponDTO unavaliableCoupon = null;
		try {
			// 校验购物车商品是否有秒杀商品
			validateTimelimitedProducts(messageId, cart);
			if (cart.isHasTimelimitedProduct()) {
				throw new MarketCenterBusinessException(MarketCenterCodeConst.HAS_TIMELIMITED_SKU, "订单中有参加秒杀活动的商品");
			}
			if (couponCodeList != null && !couponCodeList.isEmpty()) {
				validateCouponProducts(messageId, cart, couponCodeList, dictMap);
				unavaliableCouponList = cart.getUnavaliableCouponList();
				if (unavaliableCouponList != null && !unavaliableCouponList.isEmpty()) {
					unavaliableCoupon = unavaliableCouponList.get(0);
					logger.info("***********根据选择的优惠券信息计算订单行的分摊金额 messageId:[{}],存在不可用优惠券:[{}] ***********", messageId,
							JSON.toJSONString(unavaliableCouponList));
					throw new MarketCenterBusinessException(MarketCenterCodeConst.BUYER_COUPON_NO_USE,
							unavaliableCoupon.getErrorMsg());
				}
				calculateAvaliableCoupon(messageId, cart, dictMap);
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
	 * @param dictMap
	 * @param cart
	 */
	private void calculateAvaliableCoupon(String messageId, TradeInfoDTO cart, Map<String, String> dictMap) {
		logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 开始***********", messageId);
		List<OrderItemCouponDTO> avalibleCouponList = cart.getAvaliableCouponList();
		List<OrderInfoDTO> orderList = null;
		List<OrderItemInfoDTO> orderItemList = null;
		Map<String, List<OrderItemCouponDTO>> avalibleShopCouponMap = new HashMap<String, List<OrderItemCouponDTO>>();
		List<OrderItemCouponDTO> avalibleCouponSubList = null;
		List<OrderItemCouponDTO> avaliblePlatformCouponList = new ArrayList<OrderItemCouponDTO>();
		String shopCouponType = dictMap.get(
				DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&" + DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP);
		String platformCouponType = dictMap.get(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&"
				+ DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_PLATFORM);
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
				shareCouponDiscountToProducts(messageId, shopCouponType, avalibleCouponSubList, dictMap);
			}
			logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 计算店铺优惠券分摊金额 结束***********", messageId);
		}
		if (!avaliblePlatformCouponList.isEmpty()) {
			logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}]  计算平台优惠券分摊金额 开始***********", messageId);
			shareCouponDiscountToProducts(messageId, platformCouponType, avaliblePlatformCouponList, dictMap);
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
		logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}] 结束***********", messageId);
	}

	/**
	 * 优惠券优惠金额分摊到商品
	 *
	 * @param messageId
	 * @param couponProvideType
	 * @param couponList
	 * @param dictMap
	 */
	private void shareCouponDiscountToProducts(String messageId, String couponProvideType,
			List<OrderItemCouponDTO> couponList, Map<String, String> dictMap) {
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
		for (OrderItemCouponDTO couponInfo : couponList) {
			discountTotal = calculateCouponTotalDiscountAmount(messageId, couponInfo, dictMap);
			if (BigDecimal.ZERO.compareTo(discountTotal) >= 0) {
				continue;
			}
			if (BigDecimal.ZERO.compareTo(oldDiscountTotal) == 0) {
				oldDiscountTotal = discountTotal;
				targetCoupon = couponInfo;
			}
			if (discountTotal.compareTo(oldDiscountTotal) > 0) {
				targetCoupon = couponInfo;
				oldDiscountTotal = discountTotal;
			}
		}
		if (targetCoupon == null) {
			return;
		}
		discountTotal = targetCoupon.getTotalDiscountAmount();
		productList = targetCoupon.getProductList();
		logger.info("***********计算订单行的可用优惠券及分摊金额 messageId:[{}],确定优惠券编码:[{}],优惠总金额:[{}] ***********", messageId,
				targetCoupon.getBuyerCouponCode(), discountTotal);
		payTotal = targetCoupon.getItemTotalAmount();
		for (OrderItemInfoDTO productDTO : productList) {
			count++;
			// 优惠金额*（单个商品总金额/活动商品总金额）
			discount = CalculateUtils.divide(CalculateUtils.multiply(discountTotal, productDTO.getOrderItemTotal()),
					payTotal);
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
			if (dictMap.get(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&"
					+ DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_PLATFORM).equals(couponProvideType)) {
				productDTO.setPlatformCouponDiscount(discount);
				logger.info(
						"***********计算订单行的可用优惠券及分摊金额 messageId:[{}],确定优惠券编码:[{}],商品skuCode:[{}],分摊优惠金额:[{}]***********",
						messageId, targetCoupon.getBuyerCouponCode(), productDTO.getSkuCode(), discount);
			} else if (dictMap.get(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&"
					+ DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP).equals(couponProvideType)) {
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
	 * @param dictMap
	 * @return
	 */
	private BigDecimal calculateCouponTotalDiscountAmount(String messageId, OrderItemCouponDTO couponInfo,
			Map<String, String> dictMap) {
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
			if (dictMap.get(DictionaryConst.TYPE_COUPON_KIND + "&" + DictionaryConst.OPT_COUPON_KIND_FULL_CUT)
					.equals(couponKind)) {
				discountTotal = payTotal.compareTo(couponInfo.getCouponAmount()) >= 0 ? couponInfo.getCouponAmount()
						: payTotal;
				logger.info("***********计算可用优惠券优惠总金额 messageId:[{}],优惠券编码:[{}],优惠总金额:[{}]***********", messageId,
						couponInfo.getBuyerCouponCode(), discountTotal);
			} else if (dictMap.get(DictionaryConst.TYPE_COUPON_KIND + "&" + DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
					.equals(couponKind)) {
				leftDiscountAmount = couponInfo.getCouponLeftAmount();
				discountPercent = CalculateUtils.divide(new BigDecimal(couponInfo.getDiscountPercent()),
						new BigDecimal(100));
				discountTotal = CalculateUtils.multiply(payTotal, discountPercent);
				discountTotal = leftDiscountAmount.compareTo(discountTotal) > 0 ? discountTotal : leftDiscountAmount;
				discountTotal = payTotal.compareTo(discountTotal) >= 0 ? discountTotal : payTotal;
				logger.info("***********计算可用优惠券优惠总金额 messageId:[{}],优惠券编码:[{}],折扣比例:[{}],优惠总金额:[{}]***********",
						messageId, couponInfo.getBuyerCouponCode(), discountPercent, discountTotal);
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
	 * @param dictMap
	 * @return
	 */
	private boolean checkPromotionBuyerRule(String messageId, PromotionAccumulatyDTO promotionAccuDTO,
			BuyerCheckInfo buyerInfo, Map<String, String> dictMap) {
		PromotionBuyerRuleDTO ruleDTO = promotionAccuDTO.getBuyerRuleDTO();
		List<String> levelList = null;
		List<PromotionBuyerDetailDTO> detailList = null;
		if (ruleDTO == null) {
			return true;
		}
		if (dictMap
				.get(DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&" + DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE)
				.equals(ruleDTO.getRuleTargetType())) {
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
		} else if (dictMap
				.get(DictionaryConst.TYPE_PROMOTION_BUYER_RULE + "&" + DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT)
				.equals(ruleDTO.getRuleTargetType())) {
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

	public final class ValidBuyerAvaliableCouponTask extends RecursiveTask<List<OrderItemCouponDTO>> {

		private static final long serialVersionUID = 3119130710855802652L;

		private String messageId;

		private Map<String, String> dictMap;

		private Map<String, OrderInfoDTO> orderInfoMap;

		private List<OrderItemInfoDTO> allProductsList;

		private List<BuyerCouponInfoDTO> targetBuyerCouponList;

		public ValidBuyerAvaliableCouponTask(String messageId, Map<String, String> dictMap,
				List<BuyerCouponInfoDTO> buyerCouponInfoList, Map<String, OrderInfoDTO> orderInfoMap,
				List<OrderItemInfoDTO> allProductsList) {
			this.messageId = messageId;
			this.dictMap = dictMap;
			this.targetBuyerCouponList = buyerCouponInfoList;
			this.orderInfoMap = orderInfoMap;
			this.allProductsList = allProductsList;
		}

		@Override
		protected List<OrderItemCouponDTO> compute() {
			List<OrderItemCouponDTO> resultList = new ArrayList<OrderItemCouponDTO>();
			List<BuyerCouponInfoDTO> leftList = new ArrayList<BuyerCouponInfoDTO>();
			List<BuyerCouponInfoDTO> rightList = new ArrayList<BuyerCouponInfoDTO>();
			ValidBuyerAvaliableCouponTask leftTask = null;
			ValidBuyerAvaliableCouponTask rightTask = null;
			List<OrderItemCouponDTO> leftTaskRst = null;
			List<OrderItemCouponDTO> rightTaskRst = null;
			int listSize = targetBuyerCouponList.size();
			int halfSize = 0;
			if (listSize == 0) {
				return null;
			}
			if (listSize == 1) {
				resultList.add(checkBuyerCouponInfo(targetBuyerCouponList.get(0)));
				return resultList;
			}
			halfSize = listSize / 2;
			for (int i = 0; i < listSize; i++) {
				if (i < halfSize) {
					leftList.add(targetBuyerCouponList.get(i));
				} else {
					rightList.add(targetBuyerCouponList.get(i));
				}
			}
			leftTask = new ValidBuyerAvaliableCouponTask(messageId, dictMap, leftList, orderInfoMap, allProductsList);
			rightTask = new ValidBuyerAvaliableCouponTask(messageId, dictMap, rightList, orderInfoMap, allProductsList);
			leftTask.fork();
			rightTask.fork();
			leftTaskRst = leftTask.join();
			rightTaskRst = rightTask.join();
			if (leftTaskRst != null && !leftTaskRst.isEmpty()) {
				resultList.addAll(leftTaskRst);
			}
			if (rightTaskRst != null && !rightTaskRst.isEmpty()) {
				resultList.addAll(rightTaskRst);
			}
			return resultList;
		}

		private OrderItemCouponDTO checkBuyerCouponInfo(BuyerCouponInfoDTO targetBuyerCoupon) {
			String sellerCode = targetBuyerCoupon.getPromotionProviderSellerCode();
			String promotionId = targetBuyerCoupon.getPromotionId();
			String levelCode = targetBuyerCoupon.getLevelCode();
			String couponProviderType = targetBuyerCoupon.getPromotionProviderType();
			String couponProviderCode = targetBuyerCoupon.getPromotionProviderSellerCode();
			List<OrderItemInfoDTO> avaliableProductList = null;
			BigDecimal payTotal = BigDecimal.ZERO;
			if (dictMap.get(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&"
					+ DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP).equals(couponProviderType)) {
				if (!orderInfoMap.containsKey(couponProviderCode)) {
					logger.info(
							"***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 优惠券不适用购买商品的店铺***********",
							messageId, promotionId, levelCode);
					return exchange2OrderItemCoupon(targetBuyerCoupon, null, "优惠券不适用购买商品的店铺 优惠券编号:" +
							targetBuyerCoupon.getBuyerCouponCode());
				}
				avaliableProductList = checkAvaliableCouponProducts(targetBuyerCoupon,
						orderInfoMap.get(sellerCode).getOrderItemList());
			} else {
				avaliableProductList = checkAvaliableCouponProducts(targetBuyerCoupon, allProductsList);
			}
			if (avaliableProductList == null || avaliableProductList.isEmpty()) {
				logger.info(
						"***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 会员购买商品都不满足优惠券使用规则***********",
						messageId, promotionId, levelCode);
				return exchange2OrderItemCoupon(targetBuyerCoupon, null, "会员购买商品不满足优惠券使用规则 优惠券编号:"
						+ targetBuyerCoupon.getBuyerCouponCode());
			}
			payTotal = getOrderItemsTotal(avaliableProductList);
			if (payTotal.compareTo(targetBuyerCoupon.getDiscountThreshold()) < 0) {
				logger.info(
						"***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}],使用门槛:[{}] 低于使用门槛***********",
						messageId, promotionId, levelCode, targetBuyerCoupon.getDiscountThreshold());
				return exchange2OrderItemCoupon(targetBuyerCoupon, null, "满减券低于使用门槛 优惠券编号:"
						+ targetBuyerCoupon.getBuyerCouponCode());
			}
			logger.info("***********校验可用会员可用优惠券 messageId:[{}],优惠券活动编码:[{}],优惠券活动层级编码:[{}] 可用优惠券 ***********",
					messageId, promotionId, levelCode);
			return exchange2OrderItemCoupon(targetBuyerCoupon, avaliableProductList);
		}

		private OrderItemCouponDTO exchange2OrderItemCoupon(BuyerCouponInfoDTO targetBuyerCoupon,
				List<OrderItemInfoDTO> avaliableProductList, String... errorMsg) {
			OrderItemCouponDTO orderCoupon = new OrderItemCouponDTO();
			orderCoupon.setBuyerCouponInfo(targetBuyerCoupon);
			if (avaliableProductList != null && !avaliableProductList.isEmpty()) {
				orderCoupon.setProductList(avaliableProductList);
				calculateCouponTotalDiscountAmount(messageId, orderCoupon, dictMap);
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
				if (BigDecimal.ZERO.compareTo(productDTO.getOrderItemTotal()) >= 0) {
					continue;
				}
				sellerProductInfo = new SellerProductInfo();
				sellerProductInfo.setSellerCode(productDTO.getSellerCode());
				sellerProductInfo.setCategoryId(productDTO.getThirdCategoryId());
				sellerProductInfo.setBrandId(productDTO.getBrandId());
				sellerProductInfo.setChannelCode(productDTO.getChannelCode());
				sellerProductInfo.setSkuCode(productDTO.getSkuCode());
				if (!checkPromotionSellerRule(targetBuyerCoupon, sellerProductInfo)
						|| !checkPromotionCategoryItemRule(targetBuyerCoupon, sellerProductInfo)) {
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
			String channelCode = "";

			if (dictMap
					.get(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE + "&"
							+ DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP)
					.equals(targetBuyerCoupon.getPromotionProviderType())
					&& !targetBuyerCoupon.getPromotionProviderSellerCode().equals(productInfo.getSellerCode())) {
				return false;
			}
			if (ruleDTO == null) {
				return true;
			}
			if (dictMap.get(DictionaryConst.TYPE_PROMOTION_SELLER_RULE + "&"
					+ DictionaryConst.OPT_PROMOTION_SELLER_RULE_APPIONT).equals(ruleDTO.getRuleTargetType())) {
				sellerType = ruleDTO.getTargetSellerType();
				if (StringUtils.isEmpty(sellerType)) {
					return true;
				}
				if (dictMap.get(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE + "&"
						+ DictionaryConst.OPT_PROMOTION_SELLER_TYPE_ALL).equals(sellerType)) {
					return true;
				} else if (dictMap
						.get(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE + "&"
								+ DictionaryConst.OPT_PROMOTION_SELLER_TYPE_HTD)
						.equals(sellerType)
						&& dictMap
								.get(DictionaryConst.TYPE_PRODUCT_CHANNEL + "&"
										+ DictionaryConst.OPT_PRODUCT_CHANNEL_HTD)
								.equals(productInfo.getChannelCode())) {
					return true;
				} else if (dictMap
						.get(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE + "&"
								+ DictionaryConst.OPT_PROMOTION_SELLER_TYPE_OUTER)
						.equals(sellerType)
						&& dictMap
								.get(DictionaryConst.TYPE_PRODUCT_CHANNEL + "&"
										+ DictionaryConst.OPT_PRODUCT_CHANNEL_OUTER)
								.equals(productInfo.getChannelCode())) {
					return true;
				} else if (dictMap.get(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE + "&"
						+ DictionaryConst.OPT_PROMOTION_SELLER_TYPE_PRODUCTPLUS).equals(sellerType)) {
					channelCode = dictMap.get(DictionaryConst.TYPE_PRODUCT_CHANNEL + "&"
							+ DictionaryConst.OPT_PRODUCT_CHANNEL_PRODUCTPLUS);
					if (productInfo.getChannelCode().startsWith(channelCode)) {
						return true;
					}
				}
				return false;
			} else if (dictMap.get(
					DictionaryConst.TYPE_PROMOTION_SELLER_RULE + "&" + DictionaryConst.OPT_PROMOTION_SELLER_RULE_PART)
					.equals(ruleDTO.getRuleTargetType())) {
				detailList = ruleDTO.getSellerDetailList();
				if (detailList == null || detailList.isEmpty()) {
					return true;
				}
				for (PromotionSellerDetailDTO detailDTO : detailList) {
					if (detailDTO.getSellerCode().equals(productInfo.getSellerCode())) {
						return true;
					}
				}
				return false;
			}
			return true;
		}

		private boolean checkPromotionCategoryItemRule(BuyerCouponInfoDTO targetBuyerCoupon,
				SellerProductInfo productInfo) {
			PromotionCategoryItemRuleDTO ruleDTO = targetBuyerCoupon.getCategoryItemRuleDTO();
			List<PromotionCategoryDetailDTO> categoryDetailList = null;
			List<PromotionItemDetailDTO> itemDetailList = null;
			String itemLimit = "";
			if (ruleDTO == null) {
				return true;
			}

			if (dictMap
					.get(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE + "&"
							+ DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY)
					.equals(ruleDTO.getRuleTargetType())) {
				categoryDetailList = ruleDTO.getCategoryDetailList();
				if (categoryDetailList == null || categoryDetailList.isEmpty()) {
					return true;
				}
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
				return false;
			} else if (dictMap
					.get(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE + "&"
							+ DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_ITEM)
					.equals(ruleDTO.getRuleTargetType())) {
				itemLimit = ruleDTO.getTargetItemLimit();
				itemDetailList = ruleDTO.getItemDetailList();
				if (itemDetailList == null || itemDetailList.isEmpty()) {
					return true;
				}
				if (dictMap.get(
						DictionaryConst.TYPE_PROMOTION_ITEM_TYPE + "&" + DictionaryConst.OPT_PROMOTION_ITEM_TYPE_SUIT)
						.equals(itemLimit)) {
					for (PromotionItemDetailDTO itemDetailDTO : itemDetailList) {
						if (itemDetailDTO.getSkuCode().equals(productInfo.getSkuCode())) {
							return true;
						}
					}
					return false;
				} else if (dictMap.get(
						DictionaryConst.TYPE_PROMOTION_ITEM_TYPE + "&" + DictionaryConst.OPT_PROMOTION_ITEM_TYPE_UNSUIT)
						.equals(itemLimit)) {
					for (PromotionItemDetailDTO itemDetailDTO : itemDetailList) {
						if (itemDetailDTO.getSkuCode().equals(productInfo.getSkuCode())) {
							return false;
						}
					}
					return true;
				}
			}
			return true;
		}
	}
}

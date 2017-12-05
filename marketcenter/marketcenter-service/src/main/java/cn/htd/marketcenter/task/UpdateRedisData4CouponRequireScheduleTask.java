package cn.htd.marketcenter.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.B2cCouponInfoSyncHistoryDAO;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.dao.PromotionDiscountInfoDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 根据优惠券需求变更1.1，调整了数据结构更新Reids的数据
 * 变更点：
 * 1.自助领券的活动对于领券期间内的RedisKey调整
 * B2B_MIDDLE_COUPON_MEMBER_COLLECT_[promotionId]_[levelCode]->B2B_MIDDLE_COUPON_MEMBER_COLLECT_[promotionId]
 * B2B_MIDDLE_COUPON_COLLECT_[promotionId]_[levelCode] -> B2B_MIDDLE_COUPON_COLLECT_[promotionId]
 * B2B_MIDDLE_COUPON_RECEIVE_COUNT 保存内容 [promotionId]&[levelCode] -> [promotionId]
 * 2.自动返券和自助领券对于未删除和未过期的活动需要记录领券人的信息，Redis中增加B2B_MIDDLE_COUPON_SEND_LIST
 * B2B_MIDDLE_COUPON_SEND_LIST_[promotionId]  保存内容 [buyerCode]&[buyerCouponCode]
 * 3.对于优惠券活动所有未删除的，Redis中添加B2B_MIDDLE_COUPON_VALID记录活动是否有效
 * B2B_MIDDLE_COUPON_VALID 保存内容 [PromotionId]:3或者4
 * 4.对于优惠券的领取数量的RedisKey进行刷新
 * B2B_MIDDLE_COUPON_RECEIVE_COUNT的Key [promotionId]&[levelCode] -> [promotionId]
 * B2B_MIDDLE_BUYER_COUPON_RECEIVE_COUNT的Key [buyerCode]&[promotionId]&[levelCode] -> [buyerCode]&[promotionId]
 * 6.调整B2B_MIDDLE_TIMELIMITED_INDEX 的结构 原来：skucode+isvip+(sellercode) 调整后 promotiontype + skucode+isvip+(sellercode) 即多加了一个promotiontype
 */
public class UpdateRedisData4CouponRequireScheduleTask implements IScheduleTaskDealSingle<String> {

    protected static transient Logger logger = LoggerFactory.getLogger(UpdateRedisData4CouponRequireScheduleTask.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private PromotionDiscountInfoDAO promotionDiscountInfoDAO;

    @Resource
    private BuyerCouponInfoDAO buyerCouponInfoDAO;

    private static final String REDIS_DATA_FLUSHED_FLAG = "B2B_MIDDLE_REDIS_DATA_FLUSHED_FLAG";

    private static final String REDIS_COUPON_CODE_KEY = "B2B_MIDDLE_COUPONCODE_PSB_SEQ";

    @Override
    public Comparator<String> getComparator() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Override
    public List<String> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1)
            throws Exception {
        List<String> resultList = new ArrayList<String>();
        String flushedFlag = "";

        if (marketRedisDB.exists(REDIS_DATA_FLUSHED_FLAG)) {
            flushedFlag = marketRedisDB.get(REDIS_DATA_FLUSHED_FLAG);
            resultList.add(flushedFlag);
        } else {
            resultList.add("ALL");
        }
        return resultList;
    }

    @Override
    public boolean execute(String flushFlag, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateRedisData4CouponRequireScheduleTask-execute",
                JSONObject.toJSONString(flushFlag), "ownSign:" + ownSign);
        boolean result = true;
        String runedStr = flushFlag;
        Jedis jedis = null;
        if (StringUtils.isEmpty(flushFlag)) {
            return result;
        }
        try {
            jedis = marketRedisDB.getResource();
            if ("ALL".equalsIgnoreCase(runedStr)) {
                runedStr = "";
            }
            if (flushFlag.indexOf(",1") < 0) {
                flushReidsMemberCollectCouponInfo(jedis);
                runedStr += ",1";
            }
            if (flushFlag.indexOf(",2") < 0) {
                flushRedisCouponSendedInfo(jedis);
                runedStr += ",2";
            }
            if (flushFlag.indexOf(",3") < 0) {
                flushRedisCouponValid(jedis);
                runedStr += ",3";
            }
            if (flushFlag.indexOf(",4") < 0) {
                flushRedisCouponCount(jedis);
                runedStr += ",4";
            }
            if (flushFlag.indexOf(",6") < 0) {
                flushReidsPromotionTimelimited(jedis);
                runedStr += ",6";
            }
            if (flushFlag.indexOf(",7") < 0) {
                flushRedisCompensateCouponAmount4Double11(jedis);
                runedStr += ",7";
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateRedisData4CouponRequireScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            jedis.set(REDIS_DATA_FLUSHED_FLAG, runedStr);
            marketRedisDB.releaseResource(jedis);
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateRedisData4CouponRequireScheduleTask-execute",
                    JSON.toJSONString(runedStr));
        }
        return result;
    }

    /**
     * 自助领券的活动对于领券期间内的RedisKey调整
     *
     * @param jedis
     * @throws Exception
     */
    private void flushReidsMemberCollectCouponInfo(Jedis jedis) throws Exception {
        List<PromotionDiscountInfoDTO> targetCouponInfoList = null;
        String collectType = dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE, DictionaryConst
                .OPT_COUPON_PROVIDE_MEMBER_COLLECT);
        String promotionId = "";
        String levelCode = "";
        PromotionAccumulatyDTO accuDTO = null;
        int provideAllCount = 0;
        int collectableCount = 0;
        String receivedCount = "";
        long diffTime = 0L;
        int seconds = 0;
        BuyerCouponInfoDTO couponDTO = null;
        Pipeline pipeline = jedis.pipelined();
        List<Object> result = Lists.newArrayList();

        targetCouponInfoList = promotionDiscountInfoDAO.queryCollectableDiscountInfo(collectType);
        if (targetCouponInfoList == null || targetCouponInfoList.isEmpty()) {
            return;
        }
        logger.info("\n 方法:[{}],待处理Member_Collect的RedisKey调整数量:[{}]", "flushReidsMemberCollectCouponInfo-work",
                targetCouponInfoList.size());
        for (PromotionDiscountInfoDTO couponInfo : targetCouponInfoList) {
            try {
                promotionId = couponInfo.getPromotionId();
                levelCode = couponInfo.getLevelCode();
                accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId);
                couponInfo.setPromotionAccumulaty(accuDTO);
                provideAllCount = couponInfo.getProvideCount();
                receivedCount = jedis.hget(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId + "&" + levelCode);
                if (StringUtils.isEmpty(receivedCount) || "nil".equals(receivedCount)) {
                    receivedCount = jedis.hget(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId);
                }
                if (StringUtils.isEmpty(receivedCount) || "nil".equals(receivedCount)) {
                    receivedCount = "0";
                }
                collectableCount = provideAllCount - Integer.valueOf(receivedCount);
                jedis.set(RedisConst.REDIS_COUPON_MEMBER_COLLECT + "_" + promotionId, JSON.toJSONString(couponInfo));
                jedis.hset(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId, receivedCount);
                jedis.del(RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId + "_" + levelCode);
                jedis.del(RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId);
                jedis.hdel(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId + "&" + levelCode);
                jedis.del(RedisConst.REDIS_COUPON_MEMBER_COLLECT + "_" + promotionId + "_" + levelCode);
                if (collectableCount <= 0) {
                    continue;
                }
                for (int i = 0; i < collectableCount; i++) {
                    couponDTO = new BuyerCouponInfoDTO();
                    couponDTO.setPromotionAccumulaty(couponInfo);
                    couponDTO.setCouponName(couponInfo.getPromotionName());
                    couponDTO.setCouponUseRang(couponInfo.getCouponUseRangeDesc());
                    couponDTO.setCouponTargetItemLimit(couponInfo.getCouponItemDesc());
                    couponDTO.setCouponDescribe(couponInfo.getPromotionDescribe());
                    couponDTO.setPromotionProviderType(couponInfo.getPromotionProviderType());
                    couponDTO.setPromotionProviderSellerCode(couponInfo.getPromotionProviderSellerCode());
                    couponDTO.setPromotionProviderShopId(couponInfo.getPromotionProviderShopId());
                    couponDTO.setCouponType(couponInfo.getCouponKind());
                    couponDTO.setCouponStartTime(couponInfo.getEffectiveStartTime());
                    couponDTO.setCouponEndTime(couponInfo.getEffectiveEndTime());
                    couponDTO.setDiscountThreshold(couponInfo.getDiscountThreshold());
                    couponDTO.setCouponAmount(couponInfo.getDiscountAmount());
                    couponDTO.setDiscountPercent(couponInfo.getDiscountPercent());
                    couponDTO.setReceiveLimit(couponInfo.getReceiveLimit());
                    pipeline.rpush(RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId, JSON.toJSONString(couponDTO));
                }
                result = pipeline.syncAndReturnAll();
                for (int i = 0; i < result.size(); i++) {
                    logger.info("\n 方法:[{}],发送数量:[{}], 发送结果:[{}]", "flushReidsMemberCollectCouponInfo-work",
                            i, result.get(i).toString());
                }
                diffTime = couponInfo.getPrepEndTime().getTime() - new Date().getTime();
                seconds = (int) (diffTime / 1000);
                jedis.expire(RedisConst.REDIS_COUPON_MEMBER_COLLECT + "_" + promotionId, seconds);
                jedis.expire(RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId, seconds);
                logger.info("\n 方法:[{}],待处理Member_Collect的promotionId:[{}],处理成功",
                        "flushReidsMemberCollectCouponInfo-work", promotionId);
            } catch (Exception e) {
                logger.error("\n 方法:[{}],待处理Member_Collect的promotionId:[{}],异常信息:[{}]",
                        "flushReidsMemberCollectCouponInfo-work", promotionId, ExceptionUtils.getStackTraceAsString(e));
            }
        }
        logger.info("\n 方法:[{}],待处理Member_Collect的RedisKey调整处理完成", "flushRedisCouponSendedInfo-work");
    }

    /**
     * 自动返券和自助领券对于未删除和未过期的活动需要记录领券人的信息
     *
     * @param jedis
     * @throws Exception
     */
    private void flushRedisCouponSendedInfo(Jedis jedis) throws Exception {
        List<PromotionDiscountInfoDTO> targetCouponInfoList = null;
        List<BuyerCouponInfoDTO> buyerCouponList = null;
        String promotionId = "";
        String key = "";
        String value = "";
        long totalBuyerCouponCnt = 0L;
        int totalPageNum = 0;
        List<Object> result = Lists.newArrayList();
        Pager<PromotionDiscountInfoDTO> page = new Pager<PromotionDiscountInfoDTO>();

        targetCouponInfoList = promotionDiscountInfoDAO.queryNoEndedDiscountInfo();
        if (targetCouponInfoList == null || targetCouponInfoList.isEmpty()) {
            return;
        }
        logger.info("\n 方法:[{}],待处理Coupon_Send_List的数量:[{}]", "flushRedisCouponSendedInfo-work", targetCouponInfoList
                .size());
        Pipeline pipeline = jedis.pipelined();
        for (PromotionDiscountInfoDTO couponInfo : targetCouponInfoList) {
            promotionId = couponInfo.getPromotionId();
            key = RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId;
            totalBuyerCouponCnt = buyerCouponInfoDAO.queryBuyerCouponCountByDiscountInfo(couponInfo);
            if (totalBuyerCouponCnt == 0) {
                continue;
            }
            totalPageNum = CalculateUtils.divide(new BigDecimal(totalBuyerCouponCnt), new BigDecimal(5000), 0,
                    RoundingMode.CEILING).intValue();
            for (int p = 0; p < totalPageNum; p++) {
                page.setStartPageIndex(p + 1);
                page.setRows(5000);
                buyerCouponList = buyerCouponInfoDAO.queryBuyerCouponListByDiscountInfo(couponInfo, page);
                if (buyerCouponList != null && !buyerCouponList.isEmpty()) {
                    jedis.del(key);
                    for (BuyerCouponInfoDTO buyerCouponInfo : buyerCouponList) {
                        value = buyerCouponInfo.getBuyerCode() + "&" + buyerCouponInfo.getBuyerCouponCode();
                        pipeline.rpush(key, value);
                    }
                    result = pipeline.syncAndReturnAll();
                    for (int i = 0; i < result.size(); i++) {
                        logger.info("\n 方法:[{}],发送数量:[{}], 发送结果:[{}]", "flushRedisCouponSendedInfo-work",
                                p * 5000 + i, result.get(i).toString());
                    }
                }
            }
        }
        logger.info("\n 方法:[{}],待处理Coupon_Send_List处理完成", "flushRedisCouponSendedInfo-work");
    }

    /**
     * 对于优惠券活动所有未删除的，Redis中添加B2B_MIDDLE_COUPON_VALID记录活动是否有效
     *
     * @param jedis
     * @throws Exception
     */
    private void flushRedisCouponValid(Jedis jedis) throws Exception {
        List<PromotionInfoDTO> targetCouponInfoList = null;
        Map<String, String> validMap = new HashMap<String, String>();
        Map<String, String> savedValidMap = null;
        String key = "";
        String value = "";
        PromotionInfoDTO condition = new PromotionInfoDTO();
        String validStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, DictionaryConst
                .OPT_PROMOTION_VERIFY_STATUS_VALID);
        condition.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst
                .OPT_PROMOTION_TYPE_COUPON));
        condition.setDeleteStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst
                .OPT_PROMOTION_STATUS_DELETE));
        targetCouponInfoList = promotionInfoDAO.queryPromotionListByType(condition);
        if (targetCouponInfoList == null || targetCouponInfoList.isEmpty()) {
            return;
        }
        logger.info("\n 方法:[{}],待处理Coupon_Valid的数量:[{}]", "flushRedisCouponValid-work", targetCouponInfoList
                .size());
        for (PromotionInfoDTO promotionInfo : targetCouponInfoList) {
            validMap.put(promotionInfo.getPromotionId(), validStatus);
        }
        if (jedis.exists(RedisConst.REDIS_COUPON_VALID)) {
            savedValidMap = jedis.hgetAll(RedisConst.REDIS_COUPON_VALID);
            for (Entry<String, String> entry : savedValidMap.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                validMap.put(key, value);
            }
        }
        jedis.hmset(RedisConst.REDIS_COUPON_VALID, validMap);
        logger.info("\n 方法:[{}],待处理Coupon_Valid处理完成", "flushRedisCouponValid-work");
    }

    /**
     * 对于优惠券活动所有未删除的，Redis中添加B2B_MIDDLE_COUPON_VALID记录活动是否有效
     *
     * @param jedis
     * @throws Exception
     */
    private void flushRedisCouponCount(Jedis jedis) throws Exception {
        Set<String>  couponRecieveCountKey = null;
        Set<String> buyerCouponReceiveCountKey = null;
        String value = "";
        String newKey = "";
        String[] strArr= null;

        if (jedis.exists(RedisConst.REDIS_COUPON_RECEIVE_COUNT)) {
            couponRecieveCountKey = jedis.hkeys(RedisConst.REDIS_COUPON_RECEIVE_COUNT);
            for (String key : couponRecieveCountKey) {
                value = jedis.hget(RedisConst.REDIS_COUPON_RECEIVE_COUNT, key);
                if (key.indexOf("&") <= 0) {
                    continue;
                }
                strArr = key.split("&");
                newKey = strArr[0];
                jedis.hset(RedisConst.REDIS_COUPON_RECEIVE_COUNT, newKey, value);
                jedis.hdel(RedisConst.REDIS_COUPON_RECEIVE_COUNT, key);
            }
        }
        if (jedis.exists(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT)) {
            buyerCouponReceiveCountKey = jedis.hkeys(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT);
            for (String key : buyerCouponReceiveCountKey) {
                value = jedis.hget(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, key);
                if (key.indexOf("&") > 0) {
                    strArr = key.split("&");
                    if (strArr.length > 2) {
                        newKey = strArr[0] + "&" + strArr[1];
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
                jedis.hset(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, newKey, value);
                jedis.hdel(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, key);
            }
        }
    }


    /**
     * 调整B2B_MIDDLE_TIMELIMITED_INDEX的key结构，详见 顶部备注6.
     *
     * @param jedis
     * @throws Exception
     */
    private void flushReidsPromotionTimelimited(Jedis jedis) throws Exception {
        Map<String, String> indexMap = null;
        String promotionType = dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED);
        String timelimitedPurchaseType = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                DictionaryConst.OPT_PROMOTION_TYPE_LIMITED_DISCOUNT);
        if (jedis.exists(RedisConst.REDIS_TIMELIMITED_INDEX)) {
            indexMap = jedis.hgetAll(RedisConst.REDIS_TIMELIMITED_INDEX);
            for (Entry<String, String> entry : indexMap.entrySet()) {
            	String key = entry.getKey();
            	String[] keyArr = key.split("&");
                if (!keyArr[0].equals(timelimitedPurchaseType) && !keyArr[0]
                        .equals(promotionType)) { //没有2 和 3 开头的活动 2.秒杀 3.限时购
	                String newKey = promotionType + "&" + key;
                    jedis.hset(RedisConst.REDIS_TIMELIMITED_INDEX, newKey, entry.getValue());
                    jedis.hdel(RedisConst.REDIS_TIMELIMITED_INDEX, key);
                }
            }
        }
    }

    /**
     * 2017双11无敌券金额差额补偿对象处理
     *
     * @param jedis
     * @throws Exception
     */
    private void flushRedisCompensateCouponAmount4Double11(Jedis jedis) throws Exception {
        String promotionId = "";
        String b2cActivityCode = "28";
        PromotionInfoDTO promotionInfoCondition = new PromotionInfoDTO();
        PromotionInfoDTO targetPromotionInfo = null;
        PromotionAccumulatyDTO accuDTO = null;
        PromotionDiscountInfoDTO targetDiscountInfo = null;
        BuyerCouponInfoDTO couponDTO = null;
        BuyerCouponInfoDTO tmpCouponInfoDTO = null;
        String couponAmountStr = "";
        String couponStr = "";
        String buyerCode = "";
        String buyerCouponCode = "";
        String key = "";
        String validCouponAmount = "";
        String valueStr = "";
        String[] keyArr = null;
        int totalAmount = 0;
        boolean hasSuccessFlag = true;
        Map<String, String> sendedCouponInfoMap = new HashMap<String, String>();
        List<String> targetCompensateList = getTargetCompensateList4Double11CouponAmount();
        if (targetCompensateList == null || targetCompensateList.isEmpty()) {
            logger.info("************* 2017双11无敌券金额差额补偿对象列表为空 *************");
            return;
        }
        promotionInfoCondition.setB2cActivityCode(b2cActivityCode);
        promotionInfoCondition.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE));
        targetPromotionInfo = promotionInfoDAO.queryPromotionInfoByB2cActivityCode(promotionInfoCondition);
        if (targetPromotionInfo == null) {
            logger.info("************* 2017双11无敌券活动信息为空 b2cActivityCode:[{}]*************", b2cActivityCode);
            return;
        }
        if ((new Date()).after(targetPromotionInfo.getInvalidTime())) {
            logger.info("************* 2017双11无敌券活动信息已经结束 targetPromotionInfo:[{}]*************", JSON.toJSONString(targetPromotionInfo));
            return;
        }
        promotionId = targetPromotionInfo.getPromotionId();
        accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionId);
        // 获取优惠券活动信息
        targetDiscountInfo = promotionDiscountInfoDAO.queryDiscountInfoById(accuDTO);
        if (targetDiscountInfo == null) {
            logger.info("************* 2017双11无敌券活动信息不存在 targetPromotionInfo:[{}]*************", JSON.toJSONString(targetPromotionInfo));
            return;
        }
        targetDiscountInfo.setPromotionAccumulaty(accuDTO);
        couponDTO = new BuyerCouponInfoDTO();
        couponDTO.setPromotionAccumulaty(targetDiscountInfo);
        couponDTO.setCouponName(targetDiscountInfo.getPromotionName());
        couponDTO.setCouponUseRang(targetDiscountInfo.getCouponUseRangeDesc());
        couponDTO.setCouponTargetItemLimit(targetDiscountInfo.getCouponItemDesc());
        couponDTO.setCouponDescribe(targetDiscountInfo.getPromotionDescribe());
        couponDTO.setPromotionProviderType(targetDiscountInfo.getPromotionProviderType());
        couponDTO.setPromotionProviderSellerCode(targetDiscountInfo.getPromotionProviderSellerCode());
        couponDTO.setPromotionProviderShopId(targetDiscountInfo.getPromotionProviderShopId());
        couponDTO.setCouponType(targetDiscountInfo.getCouponKind());
        couponDTO.setCouponStartTime(targetDiscountInfo.getEffectiveStartTime());
        couponDTO.setCouponEndTime(targetDiscountInfo.getEffectiveEndTime());
        couponDTO.setDiscountThreshold(targetDiscountInfo.getDiscountThreshold());
        couponDTO.setCouponAmount(targetDiscountInfo.getDiscountAmount());
        couponDTO.setDiscountPercent(targetDiscountInfo.getDiscountPercent());
        couponDTO.setReceiveLimit(targetDiscountInfo.getReceiveLimit());
        couponDTO.setCreateId(0L);
        couponDTO.setCreateName("sys");
        couponDTO.setCouponLeftAmount(couponDTO.getCouponAmount());
        couponDTO.setStatus("1");
        couponDTO.setBuyerRuleDTO(null);
        couponDTO.setSellerRuleDTO(null);
        baseService.deletePromotionUselessInfo(couponDTO);
        for (String targetCompensateStr : targetCompensateList) {
            tmpCouponInfoDTO = JSON.parseObject(targetCompensateStr, BuyerCouponInfoDTO.class);
            if (tmpCouponInfoDTO == null) {
                continue;
            }
            buyerCode = tmpCouponInfoDTO.getBuyerCode();
            buyerCouponCode = generateCouponCode(jedis, couponDTO.getCouponType());
            couponDTO.setBuyerCode(buyerCode);
            couponDTO.setBuyerName(tmpCouponInfoDTO.getBuyerName());
            couponDTO.setBuyerCouponCode(buyerCouponCode);
            couponDTO.setPromotionProviderSellerCode(tmpCouponInfoDTO.getPromotionProviderSellerCode());
            couponDTO.setCouponUseRang(tmpCouponInfoDTO.getCouponUseRang());
            couponDTO.setCouponAmount(tmpCouponInfoDTO.getCouponAmount());
            couponDTO.setCouponLeftAmount(tmpCouponInfoDTO.getCouponAmount());
            couponDTO.setGetCouponTime(new Date());
            couponStr = JSON.toJSONString(couponDTO);
            sendedCouponInfoMap.put(buyerCode + "&" + buyerCouponCode, couponStr);
            couponAmountStr = String.valueOf(
                    CalculateUtils.multiply(couponDTO.getCouponAmount(), new BigDecimal(100)).longValue());
            jedis.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode, couponStr);
            jedis.hset(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode,
                    couponAmountStr);
            jedis.hincrBy(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId, 1);
            jedis.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId, 1);
            jedis.rpush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, couponStr);
            jedis.rpush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId, buyerCode + "&" + buyerCouponCode);
        }
        for (Entry<String, String> entry : sendedCouponInfoMap.entrySet()) {
            key = entry.getKey();
            valueStr = entry.getValue();
            keyArr = key.split("&");
            buyerCode = keyArr[0];
            buyerCouponCode = keyArr[1];
            tmpCouponInfoDTO = JSON.parseObject(valueStr, BuyerCouponInfoDTO.class);
            couponStr = jedis.hget(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode);
            couponDTO = JSON.parseObject(couponStr, BuyerCouponInfoDTO.class);
            if (couponDTO == null) {
                hasSuccessFlag = false;
                logger.error("************* 2017双11无敌券活动补券内容为空 couponKey:[{}], couponInfo:[{}] *************", key,
                        couponStr);
                continue;
            }
            couponAmountStr = jedis.hget(RedisConst.REDIS_BUYER_COUPON_AMOUNT, key);
            validCouponAmount = String.valueOf(
                    CalculateUtils.multiply(tmpCouponInfoDTO.getCouponAmount(), new BigDecimal(100)).longValue());
            if (StringUtils.isEmpty(couponAmountStr)) {
                hasSuccessFlag = false;
                logger.error("************* 2017双11无敌券活动补券金额为空 couponKey:[{}], couponAmount:[{}] *************", key,
                        couponAmountStr);
                continue;
            }
            if (!couponAmountStr.equals(validCouponAmount)) {
                hasSuccessFlag = false;
                logger.error("************* 2017双11无敌券活动补券金额不相等 couponKey:[{}], 补偿金额:[{}]，所需金额:[{}] *************", key,
                        couponAmountStr, validCouponAmount);
                continue;
            }
            totalAmount += Integer.valueOf(couponAmountStr);
            logger.info("************* 2017双11无敌券 用户编号:[{}], 领券数量:[{}], 补偿优惠券编号:[{}], 补偿金额:[{}] *************", buyerCode,
                    jedis.hget(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId), buyerCouponCode, couponAmountStr);
        }
        logger.info("************* 2017双11无敌券金额差额 补偿结果:[{}] 总金额:[{}]*************", hasSuccessFlag, totalAmount);
    }

    /**
     * 优惠券编码生成方法
     *
     * @param jedis
     * @param platCode 优惠券类型 1：满减券，2:折扣券
     * @return
     */
    private String generateCouponCode(Jedis jedis, String platCode) {
        Long maxValue = 100000000000L;
        Long seqIndexLong = jedis.incr(REDIS_COUPON_CODE_KEY);
        if (seqIndexLong >= maxValue) {
            jedis.set(REDIS_COUPON_CODE_KEY, "1");
            seqIndexLong = 1L;
        }
        int maxStrLength = maxValue.toString().length() - 1;
        String zeroString = String.format("%0" + maxStrLength + "d", seqIndexLong);
        if (StringUtils.isEmpty(platCode)) {
            platCode = "0";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(platCode);
        stringBuilder.append(zeroString);
        return stringBuilder.toString();
    }
    /**
     * 取得2017双11无敌券金额差额补偿对象
     * @return
     */
    private List<String> getTargetCompensateList4Double11CouponAmount() {
        List<String> targetCompensateList = new ArrayList<String>();
//        targetCompensateList.add("{\"buyerCode\":\"htd20070002\",\"buyerName\":\"好好的测试公司\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒专用\",\"couponAmount\":1640}");
        targetCompensateList.add("{\"buyerCode\":\"htd20085675\",\"buyerName\":\"南京市雨花台区阿杰手机销售部\",\"promotionProviderSellerCode\":\"htd1278013\",\"couponUseRang\":\"南京汇享莱网络有限公司专用\",\"couponAmount\":1640}");
        targetCompensateList.add("{\"buyerCode\":\"htd918694\",\"buyerName\":\"锡山区查桥金花电器商行\",\"promotionProviderSellerCode\":\"htd953007\",\"couponUseRang\":\"无锡汇衡达商贸有限公司专用\",\"couponAmount\":1620}");
        targetCompensateList.add("{\"buyerCode\":\"htd1079004\",\"buyerName\":\"桓台民联商贸有限公司\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":4900}");
        targetCompensateList.add("{\"buyerCode\":\"htd1102276\",\"buyerName\":\"射阳县广丰电器贸易有限公司\",\"promotionProviderSellerCode\":\"htd950439\",\"couponUseRang\":\"射阳县美创商贸有限公司专用\",\"couponAmount\":2400}");
        targetCompensateList.add("{\"buyerCode\":\"htd20072983\",\"buyerName\":\"射阳县合德镇刘奇家电经营部\",\"promotionProviderSellerCode\":\"htd950439\",\"couponUseRang\":\"射阳县美创商贸有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd144443\",\"buyerName\":\"鄱阳县柘港成友家电店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1136290\",\"buyerName\":\"建湖县冈东镇翠萍家电商店\",\"promotionProviderSellerCode\":\"htd1136241\",\"couponUseRang\":\"建湖汇丰达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd064810\",\"buyerName\":\"南京广利得冷暖设备有限公司\",\"promotionProviderSellerCode\":\"htd1031009\",\"couponUseRang\":\"南京汇万连环境科技有限公司专用\",\"couponAmount\":900}");
        targetCompensateList.add("{\"buyerCode\":\"htd928341\",\"buyerName\":\"邹平县临池镇春兴家电商场\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":4600}");
        targetCompensateList.add("{\"buyerCode\":\"htd339242\",\"buyerName\":\"赣榆区金山镇金百合婚庆用品经营部\",\"promotionProviderSellerCode\":\"htd776062\",\"couponUseRang\":\"连云港汇达达商贸有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd050598\",\"buyerName\":\"镇江世友商贸电器有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":5950}");
        targetCompensateList.add("{\"buyerCode\":\"htd000612\",\"buyerName\":\"连云港市国安电器贸易有限公司\",\"promotionProviderSellerCode\":\"htd547164\",\"couponUseRang\":\"连云港汇豪商贸有限公司专用\",\"couponAmount\":1670}");
        targetCompensateList.add("{\"buyerCode\":\"htd1029031\",\"buyerName\":\"南通辰云贸易有限公司\",\"promotionProviderSellerCode\":\"htd493085\",\"couponUseRang\":\"南通三创商贸有限公司专用\",\"couponAmount\":2740}");
        targetCompensateList.add("{\"buyerCode\":\"htd524531\",\"buyerName\":\"偃师市翟镇毅丰家电超市\",\"promotionProviderSellerCode\":\"htd553224\",\"couponUseRang\":\"洛阳汇峰网络科技有限公司专用\",\"couponAmount\":6210}");
        targetCompensateList.add("{\"buyerCode\":\"htd253026\",\"buyerName\":\"宿迁市宿豫区王先辉电器经营部\",\"promotionProviderSellerCode\":\"htd140237\",\"couponUseRang\":\"宿迁邦元商贸有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd000951\",\"buyerName\":\"江苏洁亚贸易有限公司\",\"promotionProviderSellerCode\":\"htd241110\",\"couponUseRang\":\"南京汇洁信商贸有限公司专用\",\"couponAmount\":2520}");
        targetCompensateList.add("{\"buyerCode\":\"htd000090\",\"buyerName\":\"江苏烁宇电器贸易有限公司\",\"promotionProviderSellerCode\":\"htd786013\",\"couponUseRang\":\"南京汇宇通商贸有限公司专用\",\"couponAmount\":2440}");
        targetCompensateList.add("{\"buyerCode\":\"htd154545\",\"buyerName\":\"兴化市美盛电器维修部\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":790}");
        targetCompensateList.add("{\"buyerCode\":\"htd616481\",\"buyerName\":\"苏州第五季机电设备有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":6300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1103644\",\"buyerName\":\"连云港乐利电器有限公司\",\"promotionProviderSellerCode\":\"htd776062\",\"couponUseRang\":\"连云港汇达达商贸有限公司专用\",\"couponAmount\":4020}");
        targetCompensateList.add("{\"buyerCode\":\"htd914417\",\"buyerName\":\"扬州高通商贸有限公司\",\"promotionProviderSellerCode\":\"htd627340\",\"couponUseRang\":\"扬州汇高通网络有限公司专用\",\"couponAmount\":3840}");
        targetCompensateList.add("{\"buyerCode\":\"htd1067230\",\"buyerName\":\"瑞安市东海家电经营部（普通合伙）\",\"promotionProviderSellerCode\":\"htd1272136\",\"couponUseRang\":\"温州汇瑞网络科技有限公司专用\",\"couponAmount\":4430}");
        targetCompensateList.add("{\"buyerCode\":\"htd004207\",\"buyerName\":\"江阴市旦旦家电有限公司\",\"promotionProviderSellerCode\":\"htd1221080\",\"couponUseRang\":\"江阴市汇顺达商贸有限公司专用\",\"couponAmount\":3240}");
        targetCompensateList.add("{\"buyerCode\":\"htd687214\",\"buyerName\":\"扬州苏翔电器有限公司\",\"promotionProviderSellerCode\":\"htd1093058\",\"couponUseRang\":\"扬州汇美达网络科技有限公司专用\",\"couponAmount\":2920}");
        targetCompensateList.add("{\"buyerCode\":\"htd278229\",\"buyerName\":\"魏桥永生家电商店\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":800}");
        targetCompensateList.add("{\"buyerCode\":\"htd101490\",\"buyerName\":\"盐城市奔美利商贸有限公司\",\"promotionProviderSellerCode\":\"htd1100343\",\"couponUseRang\":\"盐城艾特网络有限公司专用\",\"couponAmount\":3600}");
        targetCompensateList.add("{\"buyerCode\":\"htd551931\",\"buyerName\":\"洛阳恩荣实业有限公司\",\"promotionProviderSellerCode\":\"htd348889\",\"couponUseRang\":\"洛阳汇易电子商务有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd480204\",\"buyerName\":\"长兴李家巷小山家电经营部\",\"promotionProviderSellerCode\":\"htd565465\",\"couponUseRang\":\"长兴汇恒网络科技有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd1315052\",\"buyerName\":\"南通易和隆商贸有限公司\",\"promotionProviderSellerCode\":\"htd1098270\",\"couponUseRang\":\"南通晟泽电器有限公司专用\",\"couponAmount\":6070}");
        targetCompensateList.add("{\"buyerCode\":\"htd1047300\",\"buyerName\":\"镇江海龙电器销售有限公司\",\"promotionProviderSellerCode\":\"htd1020018\",\"couponUseRang\":\"镇江汇兴海电器商贸有限公司专用\",\"couponAmount\":5900}");
        targetCompensateList.add("{\"buyerCode\":\"htd115284\",\"buyerName\":\"海门市金康家电经营部\",\"promotionProviderSellerCode\":\"htd987538\",\"couponUseRang\":\"南通汇拓商贸有限公司专用\",\"couponAmount\":3100}");
        targetCompensateList.add("{\"buyerCode\":\"htd325156\",\"buyerName\":\"滁州市老友商贸有限公司\",\"promotionProviderSellerCode\":\"htd587565\",\"couponUseRang\":\"滁州市昂扬商贸有限公司专用\",\"couponAmount\":570}");
        targetCompensateList.add("{\"buyerCode\":\"htd416325\",\"buyerName\":\"兴民家电\",\"promotionProviderSellerCode\":\"htd197562\",\"couponUseRang\":\"南阳市汇美达网络科技有限公司专用\",\"couponAmount\":2030}");
        targetCompensateList.add("{\"buyerCode\":\"htd1309077\",\"buyerName\":\"菏泽市众筹网络科技有限公司\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1076098\",\"buyerName\":\"菏泽市牡丹区陆翠荣家电经营部\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":4000}");
        targetCompensateList.add("{\"buyerCode\":\"htd20071666\",\"buyerName\":\"菏泽市牡丹区立鼎家用电器经销处\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":4100}");
        targetCompensateList.add("{\"buyerCode\":\"htd797837\",\"buyerName\":\"菏泽市牡丹区腾讯电器销售有限公司\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":4900}");
        targetCompensateList.add("{\"buyerCode\":\"htd536832\",\"buyerName\":\"兴化市向华电器经营部\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":6050}");
        targetCompensateList.add("{\"buyerCode\":\"htd002355\",\"buyerName\":\"宿迁市宿城区弘业家电经营部\",\"promotionProviderSellerCode\":\"htd140237\",\"couponUseRang\":\"宿迁邦元商贸有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd682466\",\"buyerName\":\"苏州歌德电器有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":6200}");
        targetCompensateList.add("{\"buyerCode\":\"htd001361\",\"buyerName\":\"淮安市清浦区黄码志红家电城\",\"promotionProviderSellerCode\":\"htd541342\",\"couponUseRang\":\"淮安市汇丰达商贸有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd023237\",\"buyerName\":\"苏州裕隆达制冷设备工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":1800}");
        targetCompensateList.add("{\"buyerCode\":\"htd152422\",\"buyerName\":\"江苏鑫义东冷暖设备有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":6300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1106241\",\"buyerName\":\"兴化市宇飞电器有限公司\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":530}");
        targetCompensateList.add("{\"buyerCode\":\"htd177749\",\"buyerName\":\"茌平县海达家电有限公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1315235\",\"buyerName\":\"兴化市龙津家电经营部\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":4770}");
        targetCompensateList.add("{\"buyerCode\":\"htd1043261\",\"buyerName\":\"温州盈恒贸易有限公司\",\"promotionProviderSellerCode\":\"htd115164\",\"couponUseRang\":\"温州汇盈网络有限公司专用\",\"couponAmount\":2400}");
        targetCompensateList.add("{\"buyerCode\":\"htd333108\",\"buyerName\":\"湖南泰合商贸有限公司\",\"promotionProviderSellerCode\":\"htd236433\",\"couponUseRang\":\"湖南村达网络有限公司专用\",\"couponAmount\":2830}");
        targetCompensateList.add("{\"buyerCode\":\"htd409025\",\"buyerName\":\"张店康东电器商行\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1065634\",\"buyerName\":\"宁海县佳泰手机店\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":1500}");
        targetCompensateList.add("{\"buyerCode\":\"htd064288\",\"buyerName\":\"临安广远家电有限公司\",\"promotionProviderSellerCode\":\"htd398542\",\"couponUseRang\":\"临安汇远达网络科技有限公司专用\",\"couponAmount\":440}");
        targetCompensateList.add("{\"buyerCode\":\"htd1136868\",\"buyerName\":\"菏泽市久力电器有限公司\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":4000}");
        targetCompensateList.add("{\"buyerCode\":\"htd1052169\",\"buyerName\":\"乐平市礼林众超家电店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd497878\",\"buyerName\":\"淄博诚立商贸有限公司\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1080961\",\"buyerName\":\"成武县信达商贸有限公司\",\"promotionProviderSellerCode\":\"htd1138850\",\"couponUseRang\":\"成武瑞汇德网络科技有限公司专用\",\"couponAmount\":730}");
        targetCompensateList.add("{\"buyerCode\":\"htd1288122\",\"buyerName\":\"兴化市三鑫电器有限公司\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":4420}");
        targetCompensateList.add("{\"buyerCode\":\"htd974856\",\"buyerName\":\"淄博标普商贸有限公司\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":1800}");
        targetCompensateList.add("{\"buyerCode\":\"htd003477\",\"buyerName\":\"江阴市璜土华丽家电炉具商行\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":5310}");
        targetCompensateList.add("{\"buyerCode\":\"htd20081044\",\"buyerName\":\"镇江天润电器销售有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":6100}");
        targetCompensateList.add("{\"buyerCode\":\"htd786641\",\"buyerName\":\"扬州恒丰电气有限公司\",\"promotionProviderSellerCode\":\"htd470236\",\"couponUseRang\":\"扬州万晟电气有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd432605\",\"buyerName\":\"菏泽市牡丹沙土阳光家电门市\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":4100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1192062\",\"buyerName\":\"淄博汇和电器有限公司\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd286158\",\"buyerName\":\"扬州汇达空调销售有限公司\",\"promotionProviderSellerCode\":\"htd216249\",\"couponUseRang\":\"扬州汇聚鼎网络有限公司专用\",\"couponAmount\":2420}");
        targetCompensateList.add("{\"buyerCode\":\"htd1075606\",\"buyerName\":\"台州市益新家电有限公司\",\"promotionProviderSellerCode\":\"htd1107139\",\"couponUseRang\":\"台州汇东网络有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd429203\",\"buyerName\":\"张店付家镇军燕家电维修服务部\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd047552\",\"buyerName\":\"茌平县海盟家电有限公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":1200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1033014\",\"buyerName\":\"宁海县桃源街道泉水九州一石百货店\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1038066\",\"buyerName\":\"宁海县一市天宏电器商店\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1043127\",\"buyerName\":\"宁海县岔路镇万兴家电商店\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":800}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073096\",\"buyerName\":\"黄山乐松商贸有限公司\",\"promotionProviderSellerCode\":\"htd1138789\",\"couponUseRang\":\"黄山惠立达商贸有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd971926\",\"buyerName\":\"黄山市庆成商贸有限公司\",\"promotionProviderSellerCode\":\"htd246191\",\"couponUseRang\":\"黄山市汇成达商贸有限公司专用\",\"couponAmount\":150}");
        targetCompensateList.add("{\"buyerCode\":\"htd1067235\",\"buyerName\":\"瑞安湖岭镇繁荣家电商行\",\"promotionProviderSellerCode\":\"htd1061062\",\"couponUseRang\":\"温州汇立网络科技有限公司专用\",\"couponAmount\":2520}");
        targetCompensateList.add("{\"buyerCode\":\"htd709003\",\"buyerName\":\"茌平县肖庄镇肖庄街吉刚家电门市部\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":1100}");
        targetCompensateList.add("{\"buyerCode\":\"htd653107\",\"buyerName\":\"平顶山市品胜电器有限公司\",\"promotionProviderSellerCode\":\"htd444549\",\"couponUseRang\":\"平顶山市汇盛达电子商务有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd479446\",\"buyerName\":\"茌平县宏大家电客服有限责任公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd883370\",\"buyerName\":\"安徽省繁昌县新港永盛家电经营部\",\"promotionProviderSellerCode\":\"htd715321\",\"couponUseRang\":\"马鞍山江浦达商贸有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd20071447\",\"buyerName\":\"广德县冉文祥农资零售店\",\"promotionProviderSellerCode\":\"htd1324456\",\"couponUseRang\":\"宣城汇通达农村电子商务有限公司专用\",\"couponAmount\":2925}");
        targetCompensateList.add("{\"buyerCode\":\"htd545293\",\"buyerName\":\"宜兴市官林镇美欣电器维修服务部\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1179002\",\"buyerName\":\"兰陵县洪旭厨卫经营部\",\"promotionProviderSellerCode\":\"htd1057159\",\"couponUseRang\":\"临沂安吉汇达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd629580\",\"buyerName\":\"高安市黄沙长久家电经营部\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":2800}");
        targetCompensateList.add("{\"buyerCode\":\"htd643502\",\"buyerName\":\"高安市祥符众超电器店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":1400}");
        targetCompensateList.add("{\"buyerCode\":\"htd182915\",\"buyerName\":\"朱黄花家电经营部\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":2400}");
        targetCompensateList.add("{\"buyerCode\":\"htd453423\",\"buyerName\":\"常州市满好电器销售有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd909434\",\"buyerName\":\"高安市新街爱林家电\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1038076\",\"buyerName\":\"宁海县前童镇章圆电器商店\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd1027099\",\"buyerName\":\"黄堆集阳光精品家电商城\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":3700}");
        targetCompensateList.add("{\"buyerCode\":\"htd735982\",\"buyerName\":\"樟树市临江镇金平家电城\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd985896\",\"buyerName\":\"苏州昕澔宸机电设备工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":6300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1099173\",\"buyerName\":\"苏州万利顺暖通机电工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd834744\",\"buyerName\":\"常州市德艺时代商贸有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd594367\",\"buyerName\":\"象山定塘望江五交化商店\",\"promotionProviderSellerCode\":\"htd466983\",\"couponUseRang\":\"象山汇盛网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1123017\",\"buyerName\":\"苏州舒润楼宇冷暖设备工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd039673\",\"buyerName\":\"扬州快乐电器有限公司\",\"promotionProviderSellerCode\":\"htd216249\",\"couponUseRang\":\"扬州汇聚鼎网络有限公司专用\",\"couponAmount\":5720}");
        targetCompensateList.add("{\"buyerCode\":\"htd780810\",\"buyerName\":\"辉县市路路通电器有限公司\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd233021\",\"buyerName\":\"丰城市曲江天鸿家电城\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":2600}");
        targetCompensateList.add("{\"buyerCode\":\"htd251017\",\"buyerName\":\"邹平县好生顺聚家电商场\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":1200}");
        targetCompensateList.add("{\"buyerCode\":\"htd698306\",\"buyerName\":\"宁海县三星电子有限公司\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":1500}");
        targetCompensateList.add("{\"buyerCode\":\"htd409809\",\"buyerName\":\"孟津新力拓商贸有限公司\",\"promotionProviderSellerCode\":\"htd553224\",\"couponUseRang\":\"洛阳汇峰网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1324260\",\"buyerName\":\"新罗区五星家用电器经营部\",\"promotionProviderSellerCode\":\"htd1272130\",\"couponUseRang\":\"龙岩市四通八达网络科技有限公司专用\",\"couponAmount\":20}");
        targetCompensateList.add("{\"buyerCode\":\"htd1312328\",\"buyerName\":\"泸州冰泉制冷设备有限公司\",\"promotionProviderSellerCode\":\"htd1312094\",\"couponUseRang\":\"泸州市达美商贸有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1036096\",\"buyerName\":\"淮安市联盛达电器有限公司\",\"promotionProviderSellerCode\":\"htd541342\",\"couponUseRang\":\"淮安市汇丰达商贸有限公司专用\",\"couponAmount\":750}");
        targetCompensateList.add("{\"buyerCode\":\"htd084402\",\"buyerName\":\"靖江市恒瑞电器有限公司\",\"promotionProviderSellerCode\":\"htd665618\",\"couponUseRang\":\"靖江汇恒电器有限公司专用\",\"couponAmount\":570}");
        targetCompensateList.add("{\"buyerCode\":\"htd1138604\",\"buyerName\":\"肇源县肇源镇福瑞海家电行\",\"promotionProviderSellerCode\":\"htd1104517\",\"couponUseRang\":\"黑龙江汇智达网络科技有限责任公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd528947\",\"buyerName\":\"内乡莫多商贸有限公司\",\"promotionProviderSellerCode\":\"htd706730\",\"couponUseRang\":\"内乡县汇恒达电子商务有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1053033\",\"buyerName\":\"山东海陆顺物流有限公司\",\"promotionProviderSellerCode\":\"htd1007010\",\"couponUseRang\":\"莘县汇信达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd480329\",\"buyerName\":\"邹平县孙镇万达家电商场\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":1300}");
        targetCompensateList.add("{\"buyerCode\":\"htd502945\",\"buyerName\":\"卫辉市大唐美的旗舰店\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":2350}");
        targetCompensateList.add("{\"buyerCode\":\"htd964060\",\"buyerName\":\"辉县市未来电器销售中心\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":6200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1072220\",\"buyerName\":\"滨州日日顺电器有限公司\",\"promotionProviderSellerCode\":\"htd1059242\",\"couponUseRang\":\"滨州汇泰达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1048031\",\"buyerName\":\"驻马店市瑞峰家电有限公司\",\"promotionProviderSellerCode\":\"htd912879\",\"couponUseRang\":\"驻马店市汇美电子商务有限公司专用\",\"couponAmount\":270}");
        targetCompensateList.add("{\"buyerCode\":\"htd1308083\",\"buyerName\":\"池州汇美商贸有限公司\",\"promotionProviderSellerCode\":\"htd1083182\",\"couponUseRang\":\"池州汇旺达电子商务有限公司专用\",\"couponAmount\":4250}");
        targetCompensateList.add("{\"buyerCode\":\"htd830366\",\"buyerName\":\"宁波华旅通讯有限公司奉化分公司\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":1100}");
        targetCompensateList.add("{\"buyerCode\":\"htd20094039\",\"buyerName\":\"杭州萧山益农大众家电部\",\"promotionProviderSellerCode\":\"htd362335\",\"couponUseRang\":\"杭州汇章网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd502051\",\"buyerName\":\"濉溪县韩村桂启家电门市部\",\"promotionProviderSellerCode\":\"htd827643\",\"couponUseRang\":\"淮北汇贤达网络科技有限公司专用\",\"couponAmount\":170}");
        targetCompensateList.add("{\"buyerCode\":\"htd1075924\",\"buyerName\":\"长兴水口东刚家电商行\",\"promotionProviderSellerCode\":\"htd565465\",\"couponUseRang\":\"长兴汇恒网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd068941\",\"buyerName\":\"邹平海亭家电贸易有限公司\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd817494\",\"buyerName\":\"苏州鹏跃冷暖设备工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":3200}");
        targetCompensateList.add("{\"buyerCode\":\"htd491332\",\"buyerName\":\"苏州建通冷气设备工程有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":1800}");
        targetCompensateList.add("{\"buyerCode\":\"htd1182113\",\"buyerName\":\"漳州市芗城区星晨电器商行\",\"promotionProviderSellerCode\":\"htd1183007\",\"couponUseRang\":\"漳州汇兴网络科技有限公司专用\",\"couponAmount\":3100}");
        targetCompensateList.add("{\"buyerCode\":\"htd20094106\",\"buyerName\":\"张家港市欣润源暖通设备有限公司\",\"promotionProviderSellerCode\":\"htd114039\",\"couponUseRang\":\"张家港盛世欣兴贸易有限公司专用\",\"couponAmount\":20}");
        targetCompensateList.add("{\"buyerCode\":\"htd560608\",\"buyerName\":\"南阳市侨汇吉能电器有限公司美的旗舰店\",\"promotionProviderSellerCode\":\"htd197562\",\"couponUseRang\":\"南阳市汇美达网络科技有限公司专用\",\"couponAmount\":3245}");
        targetCompensateList.add("{\"buyerCode\":\"htd1129301\",\"buyerName\":\"唐山金诚家用电器销售有限公司\",\"promotionProviderSellerCode\":\"htd1073078\",\"couponUseRang\":\"唐山汇扬网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1253001\",\"buyerName\":\"萍乡市准福商贸有限公司\",\"promotionProviderSellerCode\":\"htd1233065\",\"couponUseRang\":\"萍乡润祥网络科技有限公司专用\",\"couponAmount\":3960}");
        targetCompensateList.add("{\"buyerCode\":\"htd1186031\",\"buyerName\":\"如皋市谢记电器大卖场\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":2850}");
        targetCompensateList.add("{\"buyerCode\":\"htd469086\",\"buyerName\":\"南通市坤鑫贸易有限公司\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":2500}");
        targetCompensateList.add("{\"buyerCode\":\"htd833505\",\"buyerName\":\"如皋市文峰超市常青加盟店\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":3750}");
        targetCompensateList.add("{\"buyerCode\":\"htd070815\",\"buyerName\":\"池州市唐田小丁家电城\",\"promotionProviderSellerCode\":\"htd1083182\",\"couponUseRang\":\"池州汇旺达电子商务有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1102637\",\"buyerName\":\"桓台县索镇慧冰电器商行\",\"promotionProviderSellerCode\":\"htd1102543\",\"couponUseRang\":\"淄博汇盛网络科技有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd869646\",\"buyerName\":\"江阴市长泾完美电器商行\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":6170}");
        targetCompensateList.add("{\"buyerCode\":\"htd008885\",\"buyerName\":\"茌平县海成商务服务有限公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":2300}");
        targetCompensateList.add("{\"buyerCode\":\"htd535182\",\"buyerName\":\"南阳旭辉商贸有限公司\",\"promotionProviderSellerCode\":\"htd197562\",\"couponUseRang\":\"南阳市汇美达网络科技有限公司专用\",\"couponAmount\":405}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073191\",\"buyerName\":\"常州凯宁冷暖设备工程有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1192053\",\"buyerName\":\"常州梓洋电器有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":6105}");
        targetCompensateList.add("{\"buyerCode\":\"htd933628\",\"buyerName\":\"常州盛世欣兴格力电器客户服务中心有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd585981\",\"buyerName\":\"高唐县琉璃寺镇东方家电门市部\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd544523\",\"buyerName\":\"无锡市深梁制冷设备有限公司\",\"promotionProviderSellerCode\":\"htd482911\",\"couponUseRang\":\"无锡汇之利商贸有限公司专用\",\"couponAmount\":800}");
        targetCompensateList.add("{\"buyerCode\":\"htd387786\",\"buyerName\":\"常州市欣泰好电器有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd846295\",\"buyerName\":\"海安美言电器商行\",\"promotionProviderSellerCode\":\"htd1098270\",\"couponUseRang\":\"南通晟泽电器有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd004765\",\"buyerName\":\"灌云县温馨电器有限公司\",\"promotionProviderSellerCode\":\"htd1271096\",\"couponUseRang\":\"灌云县汇润达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd819020\",\"buyerName\":\"枞阳县朱氏日日顺电器销售有限公司\",\"promotionProviderSellerCode\":\"htd214886\",\"couponUseRang\":\"安庆市千百汇网络技术服务有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd942484\",\"buyerName\":\"高安市八景云峰家电店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1053548\",\"buyerName\":\"麻城市龟山龙金家电\",\"promotionProviderSellerCode\":\"htd155826\",\"couponUseRang\":\"武汉汇瑞达网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd009310\",\"buyerName\":\"无锡市国立电器有限公司\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1052062\",\"buyerName\":\"京口区四意家电经营部\",\"promotionProviderSellerCode\":\"htd1020018\",\"couponUseRang\":\"镇江汇兴海电器商贸有限公司专用\",\"couponAmount\":1300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1144332\",\"buyerName\":\"临沂市兰山区于敏家用电器经营部\",\"promotionProviderSellerCode\":\"htd1057159\",\"couponUseRang\":\"临沂安吉汇达网络科技有限公司专用\",\"couponAmount\":1000}");
        targetCompensateList.add("{\"buyerCode\":\"htd009012\",\"buyerName\":\"江苏科菲冷暖电器工程有限公司\",\"promotionProviderSellerCode\":\"htd1100355\",\"couponUseRang\":\"常州市汇德菲网络科技有限公司专用\",\"couponAmount\":2740}");
        targetCompensateList.add("{\"buyerCode\":\"htd004759\",\"buyerName\":\"镇江市四海空调电器有限公司\",\"promotionProviderSellerCode\":\"htd1020018\",\"couponUseRang\":\"镇江汇兴海电器商贸有限公司专用\",\"couponAmount\":4500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1135436\",\"buyerName\":\"台州市海顺家电有限公司\",\"promotionProviderSellerCode\":\"htd1107139\",\"couponUseRang\":\"台州汇东网络有限公司专用\",\"couponAmount\":1040}");
        targetCompensateList.add("{\"buyerCode\":\"htd423205\",\"buyerName\":\"廊坊市华龙商业有限公司\",\"promotionProviderSellerCode\":\"htd182378\",\"couponUseRang\":\"廊坊市汇隆益网络科技有限公司专用\",\"couponAmount\":750}");
        targetCompensateList.add("{\"buyerCode\":\"htd1199067\",\"buyerName\":\"泰州市海陵区宇辉电器有限公司\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":1170}");
        targetCompensateList.add("{\"buyerCode\":\"htd962978\",\"buyerName\":\"盐城市迅邦商贸有限公司\",\"promotionProviderSellerCode\":\"htd1100343\",\"couponUseRang\":\"盐城艾特网络有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"htd855100\",\"buyerName\":\"湖州吴兴兴联家电商店\",\"promotionProviderSellerCode\":\"htd542073\",\"couponUseRang\":\"杭州汇宁美商贸有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073371\",\"buyerName\":\"于都佳达商行\",\"promotionProviderSellerCode\":\"htd1028063\",\"couponUseRang\":\"赣州汇泰网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd1220245\",\"buyerName\":\"漳州市客易来贸易有限公司\",\"promotionProviderSellerCode\":\"htd1214181\",\"couponUseRang\":\"漳州市汇利达网络科技有限公司专用\",\"couponAmount\":3060}");
        targetCompensateList.add("{\"buyerCode\":\"htd391759\",\"buyerName\":\"聊城北辰电器有限公司\",\"promotionProviderSellerCode\":\"htd149125\",\"couponUseRang\":\"聊城佰易汇网络有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1057607\",\"buyerName\":\"苏州艾迪格电器销售有限公司\",\"promotionProviderSellerCode\":\"htd1068137\",\"couponUseRang\":\"太仓汇鑫达电器销售有限公司专用\",\"couponAmount\":1525}");
        targetCompensateList.add("{\"buyerCode\":\"htd274741\",\"buyerName\":\"南通市智星暖通设备工程有限公司\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":2300}");
        targetCompensateList.add("{\"buyerCode\":\"htd351332\",\"buyerName\":\"扬中盛世格力电器有限公司\",\"promotionProviderSellerCode\":\"htd648159\",\"couponUseRang\":\"扬中市汇通网络有限公司专用\",\"couponAmount\":3500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1323699\",\"buyerName\":\"萍乡市安源区亨恒通家用电器商行\",\"promotionProviderSellerCode\":\"htd1233065\",\"couponUseRang\":\"萍乡润祥网络科技有限公司专用\",\"couponAmount\":3940}");
        targetCompensateList.add("{\"buyerCode\":\"htd1233046\",\"buyerName\":\"吉安市青原区鸿运电器批发部\",\"promotionProviderSellerCode\":\"htd1129075\",\"couponUseRang\":\"吉安市吉州区翔隆网络科技有限公司专用\",\"couponAmount\":20}");
        targetCompensateList.add("{\"buyerCode\":\"htd1133397\",\"buyerName\":\"万载县潭埠广兴家居城\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":1800}");
        targetCompensateList.add("{\"buyerCode\":\"htd417102\",\"buyerName\":\"常州市武进区芙蓉亿客隆家电超市\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":1800}");
        targetCompensateList.add("{\"buyerCode\":\"htd1048104\",\"buyerName\":\"偃师市缑氏镇新阳家电城\",\"promotionProviderSellerCode\":\"htd553224\",\"couponUseRang\":\"洛阳汇峰网络科技有限公司专用\",\"couponAmount\":2900}");
        targetCompensateList.add("{\"buyerCode\":\"htd1099104\",\"buyerName\":\"安庆市开发区壹达制冷服务部\",\"promotionProviderSellerCode\":\"htd1031084\",\"couponUseRang\":\"安庆三创商贸有限公司专用\",\"couponAmount\":3320}");
        targetCompensateList.add("{\"buyerCode\":\"htd1272197\",\"buyerName\":\"长兴和平运恒家电经营部\",\"promotionProviderSellerCode\":\"htd565465\",\"couponUseRang\":\"长兴汇恒网络科技有限公司专用\",\"couponAmount\":850}");
        targetCompensateList.add("{\"buyerCode\":\"htd988562\",\"buyerName\":\"利辛县永恒电子科技有限公司\",\"promotionProviderSellerCode\":\"htd1130221\",\"couponUseRang\":\"阜阳市万源网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd145858\",\"buyerName\":\"如皋市皓日家电经营部\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":2050}");
        targetCompensateList.add("{\"buyerCode\":\"htd1022001\",\"buyerName\":\"常州利群亿品暖通工程有限公司\",\"promotionProviderSellerCode\":\"htd662094\",\"couponUseRang\":\"江苏汇通达环境科技设备有限公司常州分公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1048078\",\"buyerName\":\"永清县北辛溜永兴电器经销部\",\"promotionProviderSellerCode\":\"htd182378\",\"couponUseRang\":\"廊坊市汇隆益网络科技有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd053215\",\"buyerName\":\"卫辉市苏宁电器有限公司\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":3250}");
        targetCompensateList.add("{\"buyerCode\":\"htd1057038\",\"buyerName\":\"常德华达电器销售有限公司\",\"promotionProviderSellerCode\":\"htd1056343\",\"couponUseRang\":\"常德汇华网络销售有限公司专用\",\"couponAmount\":820}");
        targetCompensateList.add("{\"buyerCode\":\"htd1075012\",\"buyerName\":\"瑞安市万乐家电店\",\"promotionProviderSellerCode\":\"htd1061062\",\"couponUseRang\":\"温州汇立网络科技有限公司专用\",\"couponAmount\":2110}");
        targetCompensateList.add("{\"buyerCode\":\"htd1314228\",\"buyerName\":\"费县梁邱凤伟家电销售部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd943516\",\"buyerName\":\"如皋市江滨家电城\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":5450}");
        targetCompensateList.add("{\"buyerCode\":\"htd1075392\",\"buyerName\":\"乐亭县虹科家电商行\",\"promotionProviderSellerCode\":\"htd1073078\",\"couponUseRang\":\"唐山汇扬网络科技有限公司专用\",\"couponAmount\":220}");
        targetCompensateList.add("{\"buyerCode\":\"htd764273\",\"buyerName\":\"卫辉市李源屯镇新美电器门市部\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":5600}");
        targetCompensateList.add("{\"buyerCode\":\"htd781851\",\"buyerName\":\"大丰区众友家电经营部\",\"promotionProviderSellerCode\":\"htd1154070\",\"couponUseRang\":\"盐城大丰海存网络有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd516894\",\"buyerName\":\"岳阳市同兴电器销售有限公司\",\"promotionProviderSellerCode\":\"htd434674\",\"couponUseRang\":\"湖南汇远商贸有限公司专用\",\"couponAmount\":2110}");
        targetCompensateList.add("{\"buyerCode\":\"htd811753\",\"buyerName\":\"兴化市大邹镇信仁家用电器经营部\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":4510}");
        targetCompensateList.add("{\"buyerCode\":\"htd038426\",\"buyerName\":\"常州市吉兴商贸有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd106818\",\"buyerName\":\"洛阳睿超商贸有限公司\",\"promotionProviderSellerCode\":\"htd553224\",\"couponUseRang\":\"洛阳汇峰网络科技有限公司专用\",\"couponAmount\":900}");
        targetCompensateList.add("{\"buyerCode\":\"htd871760\",\"buyerName\":\"禹州市广诚电器服务有限公司\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd368302\",\"buyerName\":\"宿州市埇桥区梦想家具店\",\"promotionProviderSellerCode\":\"htd1139400\",\"couponUseRang\":\"宿州市九州泰隆网络科技有限公司专用\",\"couponAmount\":1000}");
        targetCompensateList.add("{\"buyerCode\":\"htd932652\",\"buyerName\":\"茌平县徐涛家电车业商行\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1130180\",\"buyerName\":\"常州创家美环境科技有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":2200}");
        targetCompensateList.add("{\"buyerCode\":\"htd891703\",\"buyerName\":\"临沂批发城永佳家用电器经营部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1298430\",\"buyerName\":\"滕州市西岗步步高家电城\",\"promotionProviderSellerCode\":\"htd1065426\",\"couponUseRang\":\"滕州市汇集网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1083119\",\"buyerName\":\"苏州建怡格空调有限公司\",\"promotionProviderSellerCode\":\"htd136060\",\"couponUseRang\":\"苏州建格空调有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd003171\",\"buyerName\":\"常州豪怡电器有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":6420}");
        targetCompensateList.add("{\"buyerCode\":\"htd351875\",\"buyerName\":\"常州丰瑞森贸易有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1124025\",\"buyerName\":\"黑龙江省泽众商贸有限公司\",\"promotionProviderSellerCode\":\"htd1104517\",\"couponUseRang\":\"黑龙江汇智达网络科技有限责任公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd084272\",\"buyerName\":\"临沂经济技术开发区皓宇家电销售部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1178166\",\"buyerName\":\"荆州汇鹏达电子商务有限公司\",\"promotionProviderSellerCode\":\"htd155826\",\"couponUseRang\":\"武汉汇瑞达网络科技有限公司专用\",\"couponAmount\":450}");
        targetCompensateList.add("{\"buyerCode\":\"htd1085341\",\"buyerName\":\"袁州区慈化志强家电总汇\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":4400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1192056\",\"buyerName\":\"河南新美达电器有限公司\",\"promotionProviderSellerCode\":\"htd1182288\",\"couponUseRang\":\"濮阳达辰商贸有限公司专用\",\"couponAmount\":720}");
        targetCompensateList.add("{\"buyerCode\":\"htd312087\",\"buyerName\":\"邹平县好生镇昊鑫家电商场\",\"promotionProviderSellerCode\":\"htd910279\",\"couponUseRang\":\"山东汇智达网络科技有限公司专用\",\"couponAmount\":1000}");
        targetCompensateList.add("{\"buyerCode\":\"htd1071510\",\"buyerName\":\"莒南县鑫科家电商场\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd1188158\",\"buyerName\":\"濮阳市新动力电器商贸有限公司\",\"promotionProviderSellerCode\":\"htd1312146\",\"couponUseRang\":\"濮阳市金博汇商贸有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1138730\",\"buyerName\":\"邢台经济开发区博跃通讯门市\",\"promotionProviderSellerCode\":\"htd1324244\",\"couponUseRang\":\"河北聚品汇网络科技有限公司专用\",\"couponAmount\":210}");
        targetCompensateList.add("{\"buyerCode\":\"htd1322721\",\"buyerName\":\"萍乡市吉杰贸易有限公司\",\"promotionProviderSellerCode\":\"htd1233065\",\"couponUseRang\":\"萍乡润祥网络科技有限公司专用\",\"couponAmount\":3520}");
        targetCompensateList.add("{\"buyerCode\":\"htd1327331\",\"buyerName\":\"安庆优汇商贸有限公司\",\"promotionProviderSellerCode\":\"htd1272206\",\"couponUseRang\":\"安庆圣久商贸有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1065820\",\"buyerName\":\"温州市瓯海仙岩国欧家电经营部\",\"promotionProviderSellerCode\":\"htd1061062\",\"couponUseRang\":\"温州汇立网络科技有限公司专用\",\"couponAmount\":2100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1326215\",\"buyerName\":\"京山县新市镇京达中央家电商行\",\"promotionProviderSellerCode\":\"htd1257030\",\"couponUseRang\":\"京山京汇达商贸有限公司专用\",\"couponAmount\":1320}");
        targetCompensateList.add("{\"buyerCode\":\"htd225094\",\"buyerName\":\"射阳县黄沙港镇洪洋家电门市\",\"promotionProviderSellerCode\":\"htd950439\",\"couponUseRang\":\"射阳县美创商贸有限公司专用\",\"couponAmount\":1600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1315199\",\"buyerName\":\"固安县马庄镇高鹏家具电器超市\",\"promotionProviderSellerCode\":\"htd182378\",\"couponUseRang\":\"廊坊市汇隆益网络科技有限公司专用\",\"couponAmount\":1030}");
        targetCompensateList.add("{\"buyerCode\":\"htd1068253\",\"buyerName\":\"大沙新新路李耀强农资门市部\",\"promotionProviderSellerCode\":\"htd1059268\",\"couponUseRang\":\"武汉汇通七彩网络科技有限公司专用\",\"couponAmount\":140}");
        targetCompensateList.add("{\"buyerCode\":\"htd1274819\",\"buyerName\":\"卫辉市后河镇永达电器门市部\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":5900}");
        targetCompensateList.add("{\"buyerCode\":\"htd165911\",\"buyerName\":\"卫辉市庞寨乡甲万家电门市部\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":4200}");
        targetCompensateList.add("{\"buyerCode\":\"htd988975\",\"buyerName\":\"宿迁市天畅电器有限公司\",\"promotionProviderSellerCode\":\"htd786626\",\"couponUseRang\":\"宿迁昡齐商贸有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1031122\",\"buyerName\":\"威海临港经济技术开发区草庙子镇信利来家电商场\",\"promotionProviderSellerCode\":\"htd1031101\",\"couponUseRang\":\"威海龙汇网络科技有限公司专用\",\"couponAmount\":1300}");
        targetCompensateList.add("{\"buyerCode\":\"htd393892\",\"buyerName\":\"禹州市熙茗家电销售部\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd486924\",\"buyerName\":\"芜湖华诚中电家电销售有限公司\",\"promotionProviderSellerCode\":\"htd1312372\",\"couponUseRang\":\"芜湖市狼途商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1102868\",\"buyerName\":\"芦溪县银河镇永康电器店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":6400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1335329\",\"buyerName\":\"漳州盛世恒丰机电设备有限公司\",\"promotionProviderSellerCode\":\"htd1327330\",\"couponUseRang\":\"漳州市汇通恒丰网络科技有限公司专用\",\"couponAmount\":20}");
        targetCompensateList.add("{\"buyerCode\":\"htd070276\",\"buyerName\":\"常州久久家电销售有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":6020}");
        targetCompensateList.add("{\"buyerCode\":\"htd1275141\",\"buyerName\":\"萍乡市安源区亿嘉家用电器商行\",\"promotionProviderSellerCode\":\"htd1233065\",\"couponUseRang\":\"萍乡润祥网络科技有限公司专用\",\"couponAmount\":2620}");
        targetCompensateList.add("{\"buyerCode\":\"htd813643\",\"buyerName\":\"淄博永阳商贸有限公司\",\"promotionProviderSellerCode\":\"htd740362\",\"couponUseRang\":\"山东汇美信网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1094036\",\"buyerName\":\"阜阳奎星电子科技有限公司\",\"promotionProviderSellerCode\":\"htd1130221\",\"couponUseRang\":\"阜阳市万源网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd20093504\",\"buyerName\":\"温州利是贸易有限公司\",\"promotionProviderSellerCode\":\"htd214097\",\"couponUseRang\":\"温州汇耀贸易有限公司专用\",\"couponAmount\":1705}");
        targetCompensateList.add("{\"buyerCode\":\"htd1063044\",\"buyerName\":\"宁波高新区胜辉通讯器材店\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":1900}");
        targetCompensateList.add("{\"buyerCode\":\"922672\",\"buyerName\":\"镇江金宏电器有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":2000}");
        targetCompensateList.add("{\"buyerCode\":\"htd427580\",\"buyerName\":\"常州金太阳冷暖电器工程有限公司\",\"promotionProviderSellerCode\":\"htd080120\",\"couponUseRang\":\"常州汇洁信商贸有限公司专用\",\"couponAmount\":720}");
        targetCompensateList.add("{\"buyerCode\":\"htd003627\",\"buyerName\":\"江阴市海泉电器商贸有限公司\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":6230}");
        targetCompensateList.add("{\"buyerCode\":\"htd691707\",\"buyerName\":\"常州市顺博家电销售有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":2200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1070548\",\"buyerName\":\"万全镇黄丝路刘云岭农资门市部\",\"promotionProviderSellerCode\":\"htd1059268\",\"couponUseRang\":\"武汉汇通七彩网络科技有限公司专用\",\"couponAmount\":830}");
        targetCompensateList.add("{\"buyerCode\":\"htd416244\",\"buyerName\":\"全南县创新家电店\",\"promotionProviderSellerCode\":\"htd863568\",\"couponUseRang\":\"赣州汇晨网络科技有限公司专用\",\"couponAmount\":450}");
        targetCompensateList.add("{\"buyerCode\":\"htd1275131\",\"buyerName\":\"萍乡悦通达网络科技有限公司\",\"promotionProviderSellerCode\":\"htd1233065\",\"couponUseRang\":\"萍乡润祥网络科技有限公司专用\",\"couponAmount\":2120}");
        targetCompensateList.add("{\"buyerCode\":\"htd1085174\",\"buyerName\":\"沂南县凤财家电销售中心\",\"promotionProviderSellerCode\":\"htd1084384\",\"couponUseRang\":\"沂南县汇合网络技术有限公司专用\",\"couponAmount\":1990}");
        targetCompensateList.add("{\"buyerCode\":\"htd684159\",\"buyerName\":\"菏泽市高新区吕陵镇凯琪电器商场\",\"promotionProviderSellerCode\":\"htd481733\",\"couponUseRang\":\"菏泽市汇聚网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd20084700\",\"buyerName\":\"惠安县新源鑫家电商行\",\"promotionProviderSellerCode\":\"htd1299748\",\"couponUseRang\":\"惠安县汇顺网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd265411\",\"buyerName\":\"东阳市南马严良手机店\",\"promotionProviderSellerCode\":\"htd580015\",\"couponUseRang\":\"金华市汇宁网络科技有限公司专用\",\"couponAmount\":80}");
        targetCompensateList.add("{\"buyerCode\":\"htd925075\",\"buyerName\":\"卫辉市庞寨乡柳位中峰家电城\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":2950}");
        targetCompensateList.add("{\"buyerCode\":\"htd1080748\",\"buyerName\":\"滁州市琅琊区邦益家电经营部\",\"promotionProviderSellerCode\":\"htd718727\",\"couponUseRang\":\"滁州市汇元达网络有限公司专用\",\"couponAmount\":1480}");
        targetCompensateList.add("{\"buyerCode\":\"htd706929\",\"buyerName\":\"嵊州市现代家电有限公司\",\"promotionProviderSellerCode\":\"htd881469\",\"couponUseRang\":\"嵊州市汇聚商贸有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd620822\",\"buyerName\":\"淮安市欧凯源电器有限公司\",\"promotionProviderSellerCode\":\"htd541342\",\"couponUseRang\":\"淮安市汇丰达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd406216\",\"buyerName\":\"徐州喜来乐家电有限公司\",\"promotionProviderSellerCode\":\"htd332862\",\"couponUseRang\":\"徐州驰隆科贸有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd774470\",\"buyerName\":\"莱州市城港路街道乐彩电器经销部\",\"promotionProviderSellerCode\":\"htd496857\",\"couponUseRang\":\"烟台新凌汇科网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd223770\",\"buyerName\":\"茌平县宏大商贸有限公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd961587\",\"buyerName\":\"平顶山市广弘商贸有限公司\",\"promotionProviderSellerCode\":\"htd444549\",\"couponUseRang\":\"平顶山市汇盛达电子商务有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd1034009\",\"buyerName\":\"荣成市夏庄兆华家电经销部\",\"promotionProviderSellerCode\":\"htd1031101\",\"couponUseRang\":\"威海龙汇网络科技有限公司专用\",\"couponAmount\":2350}");
        targetCompensateList.add("{\"buyerCode\":\"htd813843\",\"buyerName\":\"南陵县大华家电经营部\",\"promotionProviderSellerCode\":\"htd715321\",\"couponUseRang\":\"马鞍山江浦达商贸有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd1334958\",\"buyerName\":\"丹阳市汇运电器有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":5630}");
        targetCompensateList.add("{\"buyerCode\":\"htd731436\",\"buyerName\":\"镇江新区创美电器销售有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd471738\",\"buyerName\":\"辉县市振宏商贸有限公司\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":4400}");
        targetCompensateList.add("{\"buyerCode\":\"htd109209\",\"buyerName\":\"镇江市天仁电器有限公司\",\"promotionProviderSellerCode\":\"htd1020018\",\"couponUseRang\":\"镇江汇兴海电器商贸有限公司专用\",\"couponAmount\":1500}");
        targetCompensateList.add("{\"buyerCode\":\"htd223139\",\"buyerName\":\"莱州市朱桥镇玮晶家电商场\",\"promotionProviderSellerCode\":\"htd496857\",\"couponUseRang\":\"烟台新凌汇科网络科技有限公司专用\",\"couponAmount\":1400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1071422\",\"buyerName\":\"句容市白兔镇同心家电商场\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":5200}");
        targetCompensateList.add("{\"buyerCode\":\"htd554357\",\"buyerName\":\"获嘉县金海贸易有限公司\",\"promotionProviderSellerCode\":\"htd516668\",\"couponUseRang\":\"新乡市汇和电子商务有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd008569\",\"buyerName\":\"丹阳市顺天暖通电气有限公司\",\"promotionProviderSellerCode\":\"htd738355\",\"couponUseRang\":\"江苏汇得诺电器商贸有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd740903\",\"buyerName\":\"南阳市盛义昌商贸有限公司\",\"promotionProviderSellerCode\":\"htd995426\",\"couponUseRang\":\"南阳汇凯电子商务有限公司专用\",\"couponAmount\":20}");
        targetCompensateList.add("{\"buyerCode\":\"htd1067080\",\"buyerName\":\"奉化惠丰通讯商行\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":2010}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073585\",\"buyerName\":\"宁波海曙百通通讯设备商行\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":1100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1163254\",\"buyerName\":\"奉化市莼湖刘飞手机店\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":2000}");
        targetCompensateList.add("{\"buyerCode\":\"htd240778\",\"buyerName\":\"东营市亚安商贸有限公司\",\"promotionProviderSellerCode\":\"htd986318\",\"couponUseRang\":\"东营市汇鑫宇网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd314235\",\"buyerName\":\"卫辉市太公镇昌盛电器门市部\",\"promotionProviderSellerCode\":\"htd347532\",\"couponUseRang\":\"新乡市汇泽电子商务有限公司专用\",\"couponAmount\":6250}");
        targetCompensateList.add("{\"buyerCode\":\"htd1164054\",\"buyerName\":\"成武县汶上镇刘传信家电经营商\",\"promotionProviderSellerCode\":\"htd1138850\",\"couponUseRang\":\"成武瑞汇德网络科技有限公司专用\",\"couponAmount\":2010}");
        targetCompensateList.add("{\"buyerCode\":\"htd1163089\",\"buyerName\":\"成武县九女镇远海车行\",\"promotionProviderSellerCode\":\"htd1138850\",\"couponUseRang\":\"成武瑞汇德网络科技有限公司专用\",\"couponAmount\":740}");
        targetCompensateList.add("{\"buyerCode\":\"htd446000\",\"buyerName\":\"鄱阳县众超购物中心\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":4400}");
        targetCompensateList.add("{\"buyerCode\":\"htd528967\",\"buyerName\":\"临沂市兰山区刘正启家电经营部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1163237\",\"buyerName\":\"高安市先友家电城\",\"promotionProviderSellerCode\":\"htd1085359\",\"couponUseRang\":\"南昌川和网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1061006\",\"buyerName\":\"象山县石浦阿根家电商行\",\"promotionProviderSellerCode\":\"htd466983\",\"couponUseRang\":\"象山汇盛网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd004228\",\"buyerName\":\"常州市丰南五金电器有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd962558\",\"buyerName\":\"袁州区南庙百姓家电\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":2400}");
        targetCompensateList.add("{\"buyerCode\":\"htd1320487\",\"buyerName\":\"驻马店市恒美电器有限公司\",\"promotionProviderSellerCode\":\"htd1308190\",\"couponUseRang\":\"驻马店市驿美达电子商务有限公司专用\",\"couponAmount\":1440}");
        targetCompensateList.add("{\"buyerCode\":\"htd156203\",\"buyerName\":\"南阳市家电大世界美的经营部\",\"promotionProviderSellerCode\":\"htd995426\",\"couponUseRang\":\"南阳汇凯电子商务有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1052134\",\"buyerName\":\"宁波市镇海区骆驼南方手机店\",\"promotionProviderSellerCode\":\"htd162888\",\"couponUseRang\":\"宁波华百汇网络科技有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd20072982\",\"buyerName\":\"湖北汇庆通网络科技有限公司\",\"promotionProviderSellerCode\":\"htd155826\",\"couponUseRang\":\"武汉汇瑞达网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd001546\",\"buyerName\":\"江阴市华士怡声家用电器商店\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":3660}");
        targetCompensateList.add("{\"buyerCode\":\"htd1335486\",\"buyerName\":\"临沂凯多家电有限公司\",\"promotionProviderSellerCode\":\"htd1335324\",\"couponUseRang\":\"临沂汇一网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd000768\",\"buyerName\":\"镇江枫叶暖通工程有限公司\",\"promotionProviderSellerCode\":\"htd130362\",\"couponUseRang\":\"扬中汇得诺电器商贸有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1276226\",\"buyerName\":\"昌邑市三联家电有限公司\",\"promotionProviderSellerCode\":\"htd1121021\",\"couponUseRang\":\"昌邑汇盛达网络科技有限公司专用\",\"couponAmount\":290}");
        targetCompensateList.add("{\"buyerCode\":\"htd1181157\",\"buyerName\":\"泉州市洛江区万安聚安电器商行\",\"promotionProviderSellerCode\":\"htd1170071\",\"couponUseRang\":\"泉州汇铭网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd20078059\",\"buyerName\":\"龙海市榜山明发电器商行\",\"promotionProviderSellerCode\":\"htd1327330\",\"couponUseRang\":\"漳州市汇通恒丰网络科技有限公司专用\",\"couponAmount\":270}");
        targetCompensateList.add("{\"buyerCode\":\"htd1053158\",\"buyerName\":\"南京凯吉机电设备科技有限公司\",\"promotionProviderSellerCode\":\"htd1031009\",\"couponUseRang\":\"南京汇万连环境科技有限公司专用\",\"couponAmount\":1700}");
        targetCompensateList.add("{\"buyerCode\":\"htd383585\",\"buyerName\":\"南昌和兴空调设备有限公司\",\"promotionProviderSellerCode\":\"htd880532\",\"couponUseRang\":\"南昌汇名网络科技有限公司专用\",\"couponAmount\":1050}");
        targetCompensateList.add("{\"buyerCode\":\"htd1026031\",\"buyerName\":\"威海市贡利电子商务有限公司\",\"promotionProviderSellerCode\":\"htd1020015\",\"couponUseRang\":\"威海金东汇网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd890578\",\"buyerName\":\"瑞安市绿舟电器商场\",\"promotionProviderSellerCode\":\"htd1061062\",\"couponUseRang\":\"温州汇立网络科技有限公司专用\",\"couponAmount\":1950}");
        targetCompensateList.add("{\"buyerCode\":\"htd157549\",\"buyerName\":\"瑞安市塘下新电家电商场\",\"promotionProviderSellerCode\":\"htd1061062\",\"couponUseRang\":\"温州汇立网络科技有限公司专用\",\"couponAmount\":2130}");
        targetCompensateList.add("{\"buyerCode\":\"htd20095543\",\"buyerName\":\"东阿县铜城光大家电销售中心\",\"promotionProviderSellerCode\":\"htd1007004\",\"couponUseRang\":\"山东聊城汇义达网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1052218\",\"buyerName\":\"山东大明天安家电有限公司\",\"promotionProviderSellerCode\":\"htd1039231\",\"couponUseRang\":\"济宁汇通天安网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd541666\",\"buyerName\":\"海宁市许村许巷春妹电器商店\",\"promotionProviderSellerCode\":\"htd638802\",\"couponUseRang\":\"嘉兴汇丰网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1074125\",\"buyerName\":\"池州市鸿森商贸有限公司\",\"promotionProviderSellerCode\":\"htd1065104\",\"couponUseRang\":\"池州市蓝天电子商务有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd565309\",\"buyerName\":\"建湖县颜单镇正霞家电店\",\"promotionProviderSellerCode\":\"htd1136241\",\"couponUseRang\":\"建湖汇丰达商贸有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073333\",\"buyerName\":\"青白江兴嘉家电经营部\",\"promotionProviderSellerCode\":\"htd1070708\",\"couponUseRang\":\"成都汇泰邦网络有限责任公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1312088\",\"buyerName\":\"海宁金通电器有限公司\",\"promotionProviderSellerCode\":\"htd638802\",\"couponUseRang\":\"嘉兴汇丰网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd1221228\",\"buyerName\":\"哈尔滨莹润嘉经贸有限责任公司\",\"promotionProviderSellerCode\":\"htd1162015\",\"couponUseRang\":\"哈尔滨泽转汇科技发展有限公司专用\",\"couponAmount\":550}");
        targetCompensateList.add("{\"buyerCode\":\"htd801472\",\"buyerName\":\"章贡区美利家电维修服务中心\",\"promotionProviderSellerCode\":\"htd863568\",\"couponUseRang\":\"赣州汇晨网络科技有限公司专用\",\"couponAmount\":140}");
        targetCompensateList.add("{\"buyerCode\":\"htd028819\",\"buyerName\":\"桓台县果里镇刘宝乐家电经营部\",\"promotionProviderSellerCode\":\"htd1102543\",\"couponUseRang\":\"淄博汇盛网络科技有限公司专用\",\"couponAmount\":40}");
        targetCompensateList.add("{\"buyerCode\":\"htd1271097\",\"buyerName\":\"湖北载德农业生产资料有限公司\",\"promotionProviderSellerCode\":\"htd1265046\",\"couponUseRang\":\"湖北汇通赛普网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd20087289\",\"buyerName\":\"鄢陵县新大新家电销售有限公司\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1299334\",\"buyerName\":\"禹州市小吕武亚芳家电门市部\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1309084\",\"buyerName\":\"襄城县广荣商贸有限公司\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd431841\",\"buyerName\":\"南昌市西湖区家电市场奥鑫家用电器商行\",\"promotionProviderSellerCode\":\"htd880532\",\"couponUseRang\":\"南昌汇名网络科技有限公司专用\",\"couponAmount\":250}");
        targetCompensateList.add("{\"buyerCode\":\"htd1299283\",\"buyerName\":\"聊城开发区广平领英家电销售部\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1119070\",\"buyerName\":\"桓台县新城镇宏宇家电经营部\",\"promotionProviderSellerCode\":\"htd1102543\",\"couponUseRang\":\"淄博汇盛网络科技有限公司专用\",\"couponAmount\":1540}");
        targetCompensateList.add("{\"buyerCode\":\"htd1026008\",\"buyerName\":\"文登区金丰家电商场\",\"promotionProviderSellerCode\":\"htd1020015\",\"couponUseRang\":\"威海金东汇网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1209196\",\"buyerName\":\"泗水县星村镇传宏家电商场\",\"promotionProviderSellerCode\":\"htd1187318\",\"couponUseRang\":\"泗水硕实汇通达网络科技有限公司专用\",\"couponAmount\":210}");
        targetCompensateList.add("{\"buyerCode\":\"htd1076100\",\"buyerName\":\"袁州区西村镇刘文招家电经销部\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":4600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1105343\",\"buyerName\":\"袁州区彬江镇众超电器店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":3400}");
        targetCompensateList.add("{\"buyerCode\":\"htd20086844\",\"buyerName\":\"张家港市禄鑫暖通设备有限公司\",\"promotionProviderSellerCode\":\"htd114039\",\"couponUseRang\":\"张家港盛世欣兴贸易有限公司专用\",\"couponAmount\":2040}");
        targetCompensateList.add("{\"buyerCode\":\"htd20087674\",\"buyerName\":\"武义壶川家电有限公司\",\"promotionProviderSellerCode\":\"htd968244\",\"couponUseRang\":\"金华市汇丰网络有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd428267\",\"buyerName\":\"进贤县众超电器李渡店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd857837\",\"buyerName\":\"建湖县恒济镇孟庄明礼家电经营部\",\"promotionProviderSellerCode\":\"htd1136241\",\"couponUseRang\":\"建湖汇丰达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd384511\",\"buyerName\":\"茌平县海恒顺物流配送服务有限公司\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd192373\",\"buyerName\":\"上栗县桐木众超电器店2\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":6000}");
        targetCompensateList.add("{\"buyerCode\":\"htd1107159\",\"buyerName\":\"阳信县京东商贸中心\",\"promotionProviderSellerCode\":\"htd986318\",\"couponUseRang\":\"东营市汇鑫宇网络科技有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd1052069\",\"buyerName\":\"聊城红箭制冷工程有限公司中央空调旗舰店\",\"promotionProviderSellerCode\":\"htd1007004\",\"couponUseRang\":\"山东聊城汇义达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd20095524\",\"buyerName\":\"沈阳晨邦科技股份有限公司\",\"promotionProviderSellerCode\":\"htd30070087\",\"couponUseRang\":\"沈阳汇邦达网络科技有限公司专用\",\"couponAmount\":3790}");
        targetCompensateList.add("{\"buyerCode\":\"htd309341\",\"buyerName\":\"社旗小岭家电销售有限公司\",\"promotionProviderSellerCode\":\"htd197562\",\"couponUseRang\":\"南阳市汇美达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd508561\",\"buyerName\":\"临沂市兰山区锐朝商贸有限公司\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1276356\",\"buyerName\":\"宿州市埇桥区解集乡李国栋家电门市部\",\"promotionProviderSellerCode\":\"htd1139400\",\"couponUseRang\":\"宿州市九州泰隆网络科技有限公司专用\",\"couponAmount\":620}");
        targetCompensateList.add("{\"buyerCode\":\"htd1186197\",\"buyerName\":\"范县思赢家具家电有限公司\",\"promotionProviderSellerCode\":\"htd1200044\",\"couponUseRang\":\"濮阳市汇美达网络科技有限公司专用\",\"couponAmount\":800}");
        targetCompensateList.add("{\"buyerCode\":\"htd598995\",\"buyerName\":\"桐乡新美电器有限公司\",\"promotionProviderSellerCode\":\"htd765261\",\"couponUseRang\":\"嘉兴汇众环境科技有限公司专用\",\"couponAmount\":350}");
        targetCompensateList.add("{\"buyerCode\":\"htd1320577\",\"buyerName\":\"襄州区峪山镇陈义平家电经营部\",\"promotionProviderSellerCode\":\"htd1312095\",\"couponUseRang\":\"襄阳市宝成达人网络科技有限公司专用\",\"couponAmount\":2000}");
        targetCompensateList.add("{\"buyerCode\":\"htd1023055\",\"buyerName\":\"邓州市飞昂电器门市部\",\"promotionProviderSellerCode\":\"htd995426\",\"couponUseRang\":\"南阳汇凯电子商务有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1326196\",\"buyerName\":\"平顶山市富实鑫商贸有限公司\",\"promotionProviderSellerCode\":\"htd1146048\",\"couponUseRang\":\"平顶山市汇康电子商务有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1103585\",\"buyerName\":\"桐乡市龙翔恒利电器有限公司\",\"promotionProviderSellerCode\":\"htd765261\",\"couponUseRang\":\"嘉兴汇众环境科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd769014\",\"buyerName\":\"诸暨雄城数码家电有限公司\",\"promotionProviderSellerCode\":\"htd138039\",\"couponUseRang\":\"诸暨市云汇达商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd661010\",\"buyerName\":\"长兴县汾阳楼家电商行\",\"promotionProviderSellerCode\":\"htd638802\",\"couponUseRang\":\"嘉兴汇丰网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd002834\",\"buyerName\":\"嘉兴市秀洲区新塍开开家电商店\",\"promotionProviderSellerCode\":\"htd765261\",\"couponUseRang\":\"嘉兴汇众环境科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1183033\",\"buyerName\":\"嘉兴市秀洲区油车港镇聚合家用电器经营部\",\"promotionProviderSellerCode\":\"htd765261\",\"couponUseRang\":\"嘉兴汇众环境科技有限公司专用\",\"couponAmount\":350}");
        targetCompensateList.add("{\"buyerCode\":\"htd574324\",\"buyerName\":\"禹州市古城镇董留根家电经营部\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd763372\",\"buyerName\":\"广饶县大码头乡忠信家电城\",\"promotionProviderSellerCode\":\"htd986318\",\"couponUseRang\":\"东营市汇鑫宇网络科技有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1028021\",\"buyerName\":\"海陵区依云湾家电商行\",\"promotionProviderSellerCode\":\"htd575274\",\"couponUseRang\":\"扬州润美电器有限公司专用\",\"couponAmount\":230}");
        targetCompensateList.add("{\"buyerCode\":\"htd1335059\",\"buyerName\":\"颍泉区周棚镇张海燕家电经营部\",\"promotionProviderSellerCode\":\"htd578351\",\"couponUseRang\":\"阜阳汇松达电器销售有限公司专用\",\"couponAmount\":270}");
        targetCompensateList.add("{\"buyerCode\":\"htd956566\",\"buyerName\":\"杭州余杭伟鹏家电有限公司\",\"promotionProviderSellerCode\":\"htd500001\",\"couponUseRang\":\"杭州汇鹏电子商务有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd996574\",\"buyerName\":\"广饶县李鹊镇红岗电脑城\",\"promotionProviderSellerCode\":\"htd986318\",\"couponUseRang\":\"东营市汇鑫宇网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd020559\",\"buyerName\":\"阜宁县郭墅张成家电门市\",\"promotionProviderSellerCode\":\"htd1253042\",\"couponUseRang\":\"盐城汇众网络科技有限公司专用\",\"couponAmount\":1200}");
        targetCompensateList.add("{\"buyerCode\":\"htd858391\",\"buyerName\":\"淮安广通电器有限公司\",\"promotionProviderSellerCode\":\"htd381148\",\"couponUseRang\":\"淮安汇百通商贸有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd002179\",\"buyerName\":\"常州市永邦家电有限公司\",\"promotionProviderSellerCode\":\"htd662094\",\"couponUseRang\":\"江苏汇通达环境科技设备有限公司常州分公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd008934\",\"buyerName\":\"张家港乐易购电器有限公司\",\"promotionProviderSellerCode\":\"htd114039\",\"couponUseRang\":\"张家港盛世欣兴贸易有限公司专用\",\"couponAmount\":150}");
        targetCompensateList.add("{\"buyerCode\":\"htd1289235\",\"buyerName\":\"博兴县曹王镇富强家电销售部\",\"promotionProviderSellerCode\":\"htd986318\",\"couponUseRang\":\"东营市汇鑫宇网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd20079806\",\"buyerName\":\"阜宁县新沟镇众众家电门市\",\"promotionProviderSellerCode\":\"htd1253042\",\"couponUseRang\":\"盐城汇众网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd220010\",\"buyerName\":\"苏州宁菱电器销售有限公司\",\"promotionProviderSellerCode\":\"htd1103236\",\"couponUseRang\":\"南京汇晟通电器销售有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd187462\",\"buyerName\":\"江西桑海经济技术开发区声福家电修理店\",\"promotionProviderSellerCode\":\"htd651451\",\"couponUseRang\":\"江西汇牛网络有限公司专用\",\"couponAmount\":400}");
        targetCompensateList.add("{\"buyerCode\":\"htd424752\",\"buyerName\":\"南京鼎晟机电设备有限公司\",\"promotionProviderSellerCode\":\"htd1031009\",\"couponUseRang\":\"南京汇万连环境科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd735583\",\"buyerName\":\"洛阳澳美制冷产品有限公司\",\"promotionProviderSellerCode\":\"htd348889\",\"couponUseRang\":\"洛阳汇易电子商务有限公司专用\",\"couponAmount\":800}");
        targetCompensateList.add("{\"buyerCode\":\"htd054702\",\"buyerName\":\"宜兴惠邦电器有限公司\",\"promotionProviderSellerCode\":\"htd216511\",\"couponUseRang\":\"无锡汇聚恒商贸有限公司专用\",\"couponAmount\":10}");
        targetCompensateList.add("{\"buyerCode\":\"htd20078544\",\"buyerName\":\"宁海县长街巧爱百货商行\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1093323\",\"buyerName\":\"溧阳市别桥凯尔家电经营部\",\"promotionProviderSellerCode\":\"htd135775\",\"couponUseRang\":\"南京汇译通信息科技有限公司专用\",\"couponAmount\":2040}");
        targetCompensateList.add("{\"buyerCode\":\"htd295712\",\"buyerName\":\"射阳县陈洋训龙家电门市\",\"promotionProviderSellerCode\":\"htd950439\",\"couponUseRang\":\"射阳县美创商贸有限公司专用\",\"couponAmount\":600}");
        targetCompensateList.add("{\"buyerCode\":\"htd1073553\",\"buyerName\":\"禹州市广联商贸有限公司\",\"promotionProviderSellerCode\":\"htd866000\",\"couponUseRang\":\"禹州市汇城电子商务有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1183154\",\"buyerName\":\"孝南区毛陈镇孝武电器毛陈店\",\"promotionProviderSellerCode\":\"htd1178132\",\"couponUseRang\":\"孝感市汇武商贸有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd967748\",\"buyerName\":\"常州丹之迪商贸有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1079013\",\"buyerName\":\"日照市尚航商贸有限公司\",\"promotionProviderSellerCode\":\"htd1057159\",\"couponUseRang\":\"临沂安吉汇达网络科技有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd313547\",\"buyerName\":\"宁阳县蒋集镇新建家电销售中心\",\"promotionProviderSellerCode\":\"htd791447\",\"couponUseRang\":\"泰安汇力网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd041516\",\"buyerName\":\"茌平县乐平铺镇希亮车业家电商场\",\"promotionProviderSellerCode\":\"htd990370\",\"couponUseRang\":\"茌平县汇海达网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd804350\",\"buyerName\":\"临沂市河东区清涛家电销售部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1102208\",\"buyerName\":\"费县薛庄镇倪新华家电通讯城\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":100}");
        targetCompensateList.add("{\"buyerCode\":\"htd1071416\",\"buyerName\":\"罗庄区鑫乾家电销售部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd750738\",\"buyerName\":\"常州市雄光商贸有限公司\",\"promotionProviderSellerCode\":\"htd317461\",\"couponUseRang\":\"常州欣满意电器销售有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1315193\",\"buyerName\":\"苍山县神山镇李艳雷家电经营部\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":300}");
        targetCompensateList.add("{\"buyerCode\":\"htd1102215\",\"buyerName\":\"莒南县日日顺家电商场\",\"promotionProviderSellerCode\":\"htd352180\",\"couponUseRang\":\"临沂汇集网络科技有限公司专用\",\"couponAmount\":500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1071176\",\"buyerName\":\"张家港市康明鑫暖通设备有限公司\",\"promotionProviderSellerCode\":\"htd114039\",\"couponUseRang\":\"张家港盛世欣兴贸易有限公司专用\",\"couponAmount\":200}");
        targetCompensateList.add("{\"buyerCode\":\"htd193695\",\"buyerName\":\"如皋市美乐家电经营部\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":3850}");
        targetCompensateList.add("{\"buyerCode\":\"htd20094854\",\"buyerName\":\"万载县海洋电器经营部\",\"promotionProviderSellerCode\":\"htd30070023\",\"couponUseRang\":\"万载县普瑞达网络科技有限公司专用\",\"couponAmount\":50}");
        targetCompensateList.add("{\"buyerCode\":\"htd331727\",\"buyerName\":\"邓州市佳申商贸有限公司\",\"promotionProviderSellerCode\":\"htd995426\",\"couponUseRang\":\"南阳汇凯电子商务有限公司专用\",\"couponAmount\":2500}");
        targetCompensateList.add("{\"buyerCode\":\"htd1036066\",\"buyerName\":\"宁海县长街锦新家电商场\",\"promotionProviderSellerCode\":\"htd440625\",\"couponUseRang\":\"宁海汇佳网络科技有限公司专用\",\"couponAmount\":700}");
        targetCompensateList.add("{\"buyerCode\":\"htd1080099\",\"buyerName\":\"如皋市丁堰镇创佳家用电器经营部\",\"promotionProviderSellerCode\":\"htd1038004\",\"couponUseRang\":\"如皋市惠客莱网络有限公司专用\",\"couponAmount\":2200}");
        targetCompensateList.add("{\"buyerCode\":\"htd1186234\",\"buyerName\":\"濮阳县渠村乡贵民家电城\",\"promotionProviderSellerCode\":\"htd1200044\",\"couponUseRang\":\"濮阳市汇美达网络科技有限公司专用\",\"couponAmount\":2160}");
        targetCompensateList.add("{\"buyerCode\":\"htd528005\",\"buyerName\":\"淅川县齐欣商贸有限公司\",\"promotionProviderSellerCode\":\"htd995426\",\"couponUseRang\":\"南阳汇凯电子商务有限公司专用\",\"couponAmount\":200}");
        return targetCompensateList;
    }
}

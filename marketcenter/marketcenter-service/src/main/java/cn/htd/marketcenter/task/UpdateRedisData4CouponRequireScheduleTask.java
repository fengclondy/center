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

    @Resource
    private B2cCouponInfoSyncHistoryDAO b2cCouponInfoSyncHistoryDAO;

    private static final String REDIS_DATA_FLUSHED_FLAG = "B2B_MIDDLE_REDIS_DATA_FLUSHED_FLAG";

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
                String newKey = promotionType + "&";
                String[] keyArr = null;
                String key = "";
                String promotionIdStr = "";
                key = entry.getKey();
                newKey = newKey + key;
                promotionIdStr = entry.getValue();
                keyArr = key.split("&");
                if (!keyArr[0].equals(timelimitedPurchaseType) && !keyArr[0]
                        .equals(promotionType)) { //没有2 和 3 开头的活动 2.秒杀 3.限时购
                    jedis.hset(RedisConst.REDIS_TIMELIMITED_INDEX, newKey, promotionIdStr);
                    jedis.hdel(RedisConst.REDIS_TIMELIMITED_INDEX, key);
                }
            }
        }
    }
}

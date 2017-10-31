package cn.htd.marketcenter.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.ExecuteResult;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.enums.NoticeTypeEnum;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.PromotionDiscountInfoDAO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionBuyerDetailDTO;
import cn.htd.marketcenter.dto.PromotionBuyerRuleDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionSellerRuleDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.SellerBelongRelationDTO;
import cn.htd.membercenter.service.BelongRelationshipService;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberGradeService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

/**
 * 定时任务 优惠券返券准备处理
 */
public class PrepareSendCouponScheduleTask implements IScheduleTaskDealMulti<PromotionDiscountInfoDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(PrepareSendCouponScheduleTask.class);

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private SendSmsEmailService sendSmsEmailService;

    @Resource
    private MemberGradeService memberGradeService;

    @Resource
    private MemberBaseInfoService memberBaseInfoService;

    @Resource
    private BelongRelationshipService belongRelationshipService;

    @Resource
    private PromotionDiscountInfoDAO promotionDiscountInfoDAO;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    private static final String REDIS_COUPON_CODE_KEY = "B2B_MIDDLE_COUPONCODE_PSB_SEQ";

    private int threadPoolSize = Runtime.getRuntime().availableProcessors();

    private volatile AtomicInteger collectedCount = new AtomicInteger();

    @Override
    public Comparator<PromotionDiscountInfoDTO> getComparator() {
        return new Comparator<PromotionDiscountInfoDTO>() {
            public int compare(PromotionDiscountInfoDTO o1, PromotionDiscountInfoDTO o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 根据条件,查询当前调度服务器可处理的任务
     *
     * @param taskParameter    任务的自定义参数
     * @param ownSign          当前环境名称
     * @param taskQueueNum     当前任务类型的任务队列数量
     * @param taskQueueList    当前调度服务器,分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<PromotionDiscountInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "PrepareSendCouponScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSON.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        List<PromotionDiscountInfoDTO> dealTargetList = new ArrayList<PromotionDiscountInfoDTO>();
        try {
            if (marketRedisDB.getLlen(RedisConst.REDIS_COUPON_NEED_DEAL_LIST) > 0) {
                dealTargetList.add(new PromotionDiscountInfoDTO());
            }
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "PrepareSendCouponScheduleTask-selectTasks",
                    JSON.toJSONString(dealTargetList));
        }
        return dealTargetList;
    }

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
     *
     * @param tasks   任务数组
     * @param ownSign 当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(PromotionDiscountInfoDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "PrepareSendCouponScheduleTask-execute", JSON.toJSONString(tasks),
                "ownSign:" + ownSign);
        boolean result = true;
        String couponProvideType = "";
        ThreadPoolExecutor threadPoolExecutor = null;
        ArrayBlockingQueue<Runnable> workQueue = null;
        Jedis jedis = null;
        PromotionDiscountInfoDTO discountInfo = null;
        List<Future<Integer>> workResultList = null;
        String validStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
        String collectType = dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_MEMBER_COLLECT);
        String presentType = dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_PROVIDE_TYPE,
                DictionaryConst.OPT_COUPON_PROVIDE_AUTO_PRESENT);
        String valueStr = "";

        try {
            jedis = marketRedisDB.getResource();
            workQueue = new ArrayBlockingQueue<Runnable>(10);
            threadPoolExecutor =
                    new ThreadPoolExecutor(threadPoolSize, threadPoolSize * 2, 1, TimeUnit.MINUTES, workQueue,
                            new ThreadPoolExecutor.AbortPolicy());
            while (jedis.llen(RedisConst.REDIS_COUPON_NEED_DEAL_LIST) > 0) {
                long startTime = System.currentTimeMillis();
                try {
                    workResultList = new ArrayList<Future<Integer>>();
                    valueStr = jedis.lpop(RedisConst.REDIS_COUPON_NEED_DEAL_LIST);
                    discountInfo = JSON.parseObject(valueStr, PromotionDiscountInfoDTO.class);
                    if (discountInfo == null) {
                        continue;
                    }
                    if (!validStatus.equals(jedis.hget(RedisConst.REDIS_COUPON_VALID, discountInfo.getPromotionId()))) {
                        continue;
                    }
                    couponProvideType = discountInfo.getCouponProvideType();
                    if (collectType.equals(couponProvideType)) {
                        updateMemberCollectCoupon(threadPoolExecutor, jedis, workResultList, discountInfo);
                    } else if (presentType.equals(couponProvideType)) {
                        updateAutoPresentCoupon(threadPoolExecutor, jedis, workResultList, discountInfo);
                    }
                    for (Future<Integer> workRst : workResultList) {
                        logger.info("\n 方法:[{}],线程执行结果:[{}]", "PrepareSendCouponScheduleTask-execute", workRst.get());

                    }
                    discountInfo.setDealFlag(YesNoEnum.YES.getValue());
                    promotionDiscountInfoDAO.updateCouponDealFlag(discountInfo);
                } catch (Exception e) {
                    result = false;
                    logger.error("\n 方法:[{}],异常:[{}]", "PrepareSendCouponScheduleTask-execute",
                            ExceptionUtils.getStackTraceAsString(e));
                    if (discountInfo != null) {
                        jedis.rpush(RedisConst.REDIS_COUPON_NEED_DEAL_LIST, valueStr);
                    }
                } finally {
                    long endTime = System.currentTimeMillis();
                    logger.info("\n 发送优惠券名称:[{}],结束时间:[{}],耗时:[{}]", discountInfo.getPromotionName(),
                            DateUtils.getCurrentDate(""), (endTime - startTime) + "ms");
                }
            }
        } finally {
            if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.shutdown();
            }
            marketRedisDB.releaseResource(jedis);
        }
        return result;
    }

    /**
     * 对于领券的优惠券活动根据发券数量往redis中push领券队列
     *
     * @param threadPoolExecutor
     * @param jedis
     * @param workResultList
     * @param dealTargetInfo
     * @throws Exception
     */

    private void updateMemberCollectCoupon(ThreadPoolExecutor threadPoolExecutor, Jedis jedis,
            List<Future<Integer>> workResultList, PromotionDiscountInfoDTO dealTargetInfo) throws Exception {
        String promotionId = dealTargetInfo.getPromotionId();
        String tmpPromotionId = dealTargetInfo.getModifyPromotionId();
        String couponRedisListKey = RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId;
        int provideCount = 0;
        int receivedCount = 0;
        int needProvideAllCount = 0;
        String receiveCountStr = "";
        int replaceCount = 0;
        int taskProvideCount = 0;
        int distributedCount = 0;
        int cellCount = 0;
        Future<Integer> workResult = null;
        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
        int isNeedRemind = dealTargetInfo.getIsNeedRemind();
        PromotionBuyerRuleDTO buyerRuleDTO = dealTargetInfo.getBuyerRuleDTO();
        List<PromotionBuyerDetailDTO> buyerDetailList = null;
        int receiveLimit = dealTargetInfo.getReceiveLimit();
        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
        try {
            if (!(new Date()).before(dealTargetInfo.getPrepEndTime())) {
                logger.info("\n 方法:[{}],取消发券:[{}],发券名称:[{}],发券结束时间:[{}]",
                        "PrepareSendCouponScheduleTask-updateMemberCollectCoupon", "领券的期间已经过期",
                        dealTargetInfo.getPromotionName(),
                        DateUtils.format(dealTargetInfo.getPrepEndTime(), DateUtils.YYDDMMHHMMSS));
                return;
            }
            provideCount = dealTargetInfo.getProvideCount();
            //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
            if (NoticeTypeEnum.NO.getValue() != isNeedRemind) {
                buyerDetailList = getTargetBuyerDetailList(buyerRuleDTO);
                if (buyerDetailList != null && !buyerDetailList.isEmpty()) {
                    provideCount = buyerDetailList.size() * receiveLimit;
                } else {
                    provideCount = 0;
                }
            }
            //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            if (StringUtils.isEmpty(dealTargetInfo.getModifyPromotionId())) {
                tmpPromotionId = promotionId;
            } else {
                if (jedis.exists(RedisConst.REDIS_COUPON_SEND_LIST + "_" + dealTargetInfo.getModifyPromotionId())) {
                    replaceCount =
                            jedis.llen(RedisConst.REDIS_COUPON_SEND_LIST + "_" + dealTargetInfo.getModifyPromotionId())
                                    .intValue();
                }
            }
            if (jedis.hexists(RedisConst.REDIS_COUPON_RECEIVE_COUNT, tmpPromotionId)) {
                receiveCountStr = jedis.hget(RedisConst.REDIS_COUPON_RECEIVE_COUNT, tmpPromotionId);
                if (!StringUtils.isEmpty(receiveCountStr)) {
                    receivedCount = Integer.valueOf(receiveCountStr);
                }
            }
            needProvideAllCount = provideCount - receivedCount;
            //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//            if (jedis.exists(couponRedisListKey)) {
//                collectedCount = new AtomicInteger(jedis.llen(couponRedisListKey).intValue());
//            } else {
//                collectedCount = new AtomicInteger();
//            }
            //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            if (needProvideAllCount > 0) {
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
                if (jedis.exists(couponRedisListKey)) {
                    collectedCount = new AtomicInteger(jedis.llen(couponRedisListKey).intValue());
                } else {
                    collectedCount = new AtomicInteger();
                }
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
                for (int i = 0; i < threadPoolSize; i++) {
                    workResult = threadPoolExecutor
                            .submit(new InitMemberCollectCoupon(i, needProvideAllCount, dealTargetInfo));
                    workResultList.add(workResult);
                    logger.info("\n 方法:[{}],线程池中现在的线程数目[{}],队列中正在等待执行的任务数量[{}]",
                            "PrepareSendCouponScheduleTask-updateMemberCollectCoupon", threadPoolExecutor.getPoolSize(),
                            threadPoolExecutor.getQueue().size());
                }
            }
            if (replaceCount > 0) {
                taskProvideCount =
                        (new BigDecimal(replaceCount)).divide(new BigDecimal(threadPoolSize), 0, RoundingMode.CEILING)
                                .intValue();
                for (int i = 0; i < threadPoolSize; i++) {
                    cellCount = replaceCount - distributedCount > taskProvideCount ? taskProvideCount
                            : replaceCount - distributedCount;
                    if (cellCount <= 0) {
                        break;
                    }
                    workResult = threadPoolExecutor.submit(new ReplaceSendedBuyerCoupon(dealTargetInfo));
                    workResultList.add(workResult);
                    distributedCount += cellCount;
                    logger.info("\n 方法:[{}],线程池中现在的线程数目[{}],队列中正在等待执行的任务数量[{}]",
                            "PrepareSendCouponScheduleTask-updateMemberCollectCoupon", threadPoolExecutor.getPoolSize(),
                            threadPoolExecutor.getQueue().size());
                }
            }
            long diffTime = dealTargetInfo.getPrepEndTime().getTime() - new Date().getTime();
            int seconds = (int) (diffTime / 1000);
            jedis.expire(couponRedisListKey, seconds);
            logger.info("\n 方法:[{}],发券名称:[{}],线程启动结束", "PrepareSendCouponScheduleTask-updateMemberCollectCoupon",
                    dealTargetInfo.getPromotionName());
        } catch (Exception e) {
            logger.error("\n 方法:[{}],发券名称:[{}],异常:[{}],参数:[{}]",
                    "PrepareSendCouponScheduleTask-updateMemberCollectCoupon", dealTargetInfo.getPromotionName(),
                    ExceptionUtils.getStackTraceAsString(e), JSON.toJSONString(dealTargetInfo));

            throw e;
        }
    }

    /**
     * 对于自动发放的优惠券根据会员规则将对象会员插入发放对象明细表留待发放
     *
     * @param threadPoolExecutor
     * @param jedis
     * @param workResultList
     * @param dealTargetInfo
     * @throws Exception
     */
    private void updateAutoPresentCoupon(ThreadPoolExecutor threadPoolExecutor, Jedis jedis,
            List<Future<Integer>> workResultList, PromotionDiscountInfoDTO dealTargetInfo) throws Exception {
        PromotionBuyerRuleDTO buyerRule = null;
        //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//        List<String> targetBuyerLevelList = new ArrayList<String>();
//        ExecuteResult<List<MemberGradeDTO>> targetMemberResult = null;
//        List<MemberGradeDTO> targetMemberList = null;
        //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
        List<PromotionBuyerDetailDTO> buyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
        //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//        PromotionBuyerDetailDTO buyerDTO = null;
        //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
        int provideCount = 0;
        int taskProvideCount = 0;
        int distributedCount = 0;
        int cellCount = 0;
        List<Future<Integer>> tmpWorkResultList = new ArrayList<Future<Integer>>();
        Future<Integer> workResult = null;
        try {
            if (!(new Date()).before(dealTargetInfo.getEffectiveEndTime())) {
                logger.info("\n 方法:[{}],取消发券:[{}],发券名称:[{}],发券结束时间:[{}]",
                        "PrepareSendCouponScheduleTask-updateMemberCollectCoupon", "会员优惠券已过期无需发放",
                        dealTargetInfo.getPromotionName(),
                        DateUtils.format(dealTargetInfo.getEffectiveEndTime(), DateUtils.YYDDMMHHMMSS));
                return;
            }
            if (!StringUtils.isEmpty(dealTargetInfo.getModifyPromotionId())) {
                if (jedis.exists(RedisConst.REDIS_COUPON_SEND_LIST + "_" + dealTargetInfo.getModifyPromotionId())) {
                    provideCount =
                            jedis.llen(RedisConst.REDIS_COUPON_SEND_LIST + "_" + dealTargetInfo.getModifyPromotionId())
                                    .intValue();
                }
                if (provideCount > 0) {
                    logger.info("\n 方法:[{}],新发券活动名称:[{}],替代发券对象数量:[{}]",
                            "PrepareSendCouponScheduleTask-ReplaceSendedBuyerCoupon", dealTargetInfo.getPromotionName(),
                            provideCount);
                    distributedCount = 0;
                    taskProvideCount = (new BigDecimal(provideCount))
                            .divide(new BigDecimal(threadPoolSize), 0, RoundingMode.CEILING).intValue();
                    for (int i = 0; i < threadPoolSize; i++) {
                        cellCount = provideCount - distributedCount > taskProvideCount ? taskProvideCount
                                : provideCount - distributedCount;
                        if (cellCount <= 0) {
                            break;
                        }
                        workResult = threadPoolExecutor.submit(new ReplaceSendedBuyerCoupon(dealTargetInfo));
                        tmpWorkResultList.add(workResult);
                        distributedCount += cellCount;
                        logger.info("\n 方法:[{}],线程池中现在的线程数目[{}],队列中正在等待执行的任务数量[{}]",
                                "PrepareSendCouponScheduleTask-ReplaceSendedBuyerCoupon",
                                threadPoolExecutor.getPoolSize(), threadPoolExecutor.getQueue().size());
                    }
                }
                for (Future<Integer> workRst : tmpWorkResultList) {
                    logger.info("\n 方法:[{}],新发券活动名称:[{}],线程执行结果:[{}]",
                            "PrepareSendCouponScheduleTask-ReplaceSendedBuyerCoupon", workRst.get());
                }
            }
            buyerRule = dealTargetInfo.getBuyerRuleDTO();
            if (buyerRule == null) {
                return;
            }
            //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
//                    DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(buyerRule.getRuleTargetType())) {
//                targetBuyerLevelList = buyerRule.getTargetBuyerLevelList();
//                if (targetBuyerLevelList != null && !targetBuyerLevelList.isEmpty()) {
//                    for (String level : targetBuyerLevelList) {
//                        targetMemberResult = memberGradeService.selectMemberByGrade(level);
//                        if (!targetMemberResult.isSuccess()) {
//                            throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_AUTO_PRESENT_NO_MEMBER,
//                                    StringUtils.join(targetMemberResult.getErrorMessages(), ","));
//                        }
//                        targetMemberList = targetMemberResult.getResult();
//                        if (targetMemberList != null && !targetMemberList.isEmpty()) {
//                            for (MemberGradeDTO memberDTO : targetMemberList) {
//                                if (StringUtils.isEmpty(memberDTO.getMemberCode())) {
//                                    continue;
//                                }
//                                buyerDTO = new PromotionBuyerDetailDTO();
//                                buyerDTO.setBuyerCode(memberDTO.getMemberCode());
//                                buyerDTO.setBuyerName(memberDTO.getCompanyName());
//                                buyerDetailList.add(buyerDTO);
//                            }
//                        }
//                    }
//                }
//            } else {
//                buyerDetailList = buyerRule.getBuyerDetailList();
//            }
            buyerDetailList = getTargetBuyerDetailList(buyerRule);
            //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            if (buyerDetailList == null || buyerDetailList.isEmpty()) {
                return;
            }
            provideCount = buyerDetailList.size();
            logger.info("\n 方法:[{}],发券名称:[{}],发券对象数量:[{}]", "PrepareSendCouponScheduleTask-updateAutoPresentCoupon",
                    dealTargetInfo.getPromotionName(), provideCount);
            if (provideCount > 0) {
                workResultList = new ArrayList<Future<Integer>>();
                distributedCount = 0;
                taskProvideCount =
                        (new BigDecimal(provideCount)).divide(new BigDecimal(threadPoolSize), 0, RoundingMode.CEILING)
                                .intValue();
                for (int i = 0; i < threadPoolSize; i++) {
                    cellCount = provideCount - distributedCount > taskProvideCount ? taskProvideCount
                            : provideCount - distributedCount;
                    if (cellCount <= 0) {
                        break;
                    }
                    workResult = threadPoolExecutor
                            .submit(new AutoPresentBuyerCoupon(distributedCount, distributedCount + cellCount,
                                    dealTargetInfo, buyerDetailList));
                    workResultList.add(workResult);
                    distributedCount += cellCount;
                    logger.info("\n 方法:[{}],线程池中现在的线程数目[{}],队列中正在等待执行的任务数量[{}]",
                            "PrepareSendCouponScheduleTask-updateAutoPresentCoupon", threadPoolExecutor.getPoolSize(),
                            threadPoolExecutor.getQueue().size());
                }
            }
        } catch (MarketCenterBusinessException mcbe) {
            logger.warn("\n 方法:[{}],发券名称:[{}],异常:[{}],参数:[{}]", "PrepareSendCouponScheduleTask-updateAutoPresentCoupon",
                    dealTargetInfo.getPromotionName(), JSON.toJSONString(mcbe), JSON.toJSONString(dealTargetInfo));
            throw mcbe;
        } catch (Exception e) {
            logger.error("\n 方法:[{}],发券名称:[{}],异常:[{}],参数:[{}]",
                    "PrepareSendCouponScheduleTask-updateAutoPresentCoupon", dealTargetInfo.getPromotionName(),
                    ExceptionUtils.getStackTraceAsString(e), JSON.toJSONString(dealTargetInfo));
            throw e;
        }
    }

    public final class InitMemberCollectCoupon implements Callable<Integer> {
        private String threadName;
        private PromotionDiscountInfoDTO targetDiscountInfo;
        private int needSendCount = 0;

        public InitMemberCollectCoupon(int id, int needSendCount, PromotionDiscountInfoDTO discountInfo) {
            this.threadName =
                    String.valueOf(Thread.currentThread().getId()) + "-" + discountInfo.getPromotionName() + "-" + id;
            this.targetDiscountInfo = discountInfo;
            this.needSendCount = needSendCount;
        }

        public Integer call() throws Exception {
            long startTime = System.currentTimeMillis();
            int localCount = 0;
            String promotionId = targetDiscountInfo.getPromotionId();
            String couponRedisListKey = RedisConst.REDIS_COUPON_COLLECT + "_" + promotionId;
            BuyerCouponInfoDTO couponDTO = null;
            String couponInfoDTOStr = "";
            Jedis jedis = null;
            Pipeline pipeline = null;
            List<Object> result = Lists.newArrayList();
            logger.info("\n 线程名称:[{}],方法:[{}],开始时间:[{}]", threadName, "initMemberCollectCoupon-start",
                    DateUtils.getCurrentDate(""));
            try {
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
                couponInfoDTOStr = JSON.toJSONString(couponDTO);
                jedis = marketRedisDB.getResource();
                pipeline = jedis.pipelined();
                while (collectedCount.getAndIncrement() < needSendCount) {
                    localCount++;
                    pipeline.rpush(couponRedisListKey, couponInfoDTOStr);
                }
                result = pipeline.syncAndReturnAll();
                for (int i = 0; i < result.size(); i++) {
                    logger.info("\n 线程名称:[{}],方法:[{}],发送数量:[{}], 发送结果:[{}]", threadName, "initMemberCollectCoupon-work",
                            i, result.get(i).toString());
                }
            } catch (Exception e) {
                logger.error("\n 线程名称:[{}],方法:[{}],总发送数量:[{}],错误内容:[{}]", threadName, "initMemberCollectCoupon-end",
                        collectedCount, ExceptionUtils.getStackTraceAsString(e));
                throw e;
            } finally {
                marketRedisDB.releaseResource(jedis);
                long endTime = System.currentTimeMillis();
                logger.info("\n 线程名称:[{}],方法:[{}],本线程发送数量:[{}],结束时间:[{}],耗时:[{}]", threadName,
                        "initMemberCollectCoupon-end", localCount, DateUtils.getCurrentDate(""),
                        (endTime - startTime) + "ms");
            }
            return localCount;
        }
    }

    public final class ReplaceSendedBuyerCoupon implements Callable<Integer> {
        private String threadName;
        private PromotionDiscountInfoDTO targetDiscountInfo;

        public ReplaceSendedBuyerCoupon(PromotionDiscountInfoDTO discountInfo) {
            this.threadName =
                    String.valueOf(Thread.currentThread().getId()) + "-" + discountInfo.getPromotionName() + "-REPLACE";
            this.targetDiscountInfo = discountInfo;
        }

        public Integer call() throws Exception {
            long startTime = System.currentTimeMillis();
            int localCount = 0;
            String promotionId = targetDiscountInfo.getPromotionId();
            String oldPromotionId = targetDiscountInfo.getModifyPromotionId();
            String[] tmpArr = null;
            String buyerCouponKey = "";
            String buyerCode = "";
            String newBuyerCouponCode = "";
            String oldBuyerCouponCode = "";
            String oldCouponAmount = "";
            BigDecimal oldCouponDecimal = null;
            String oldCouponStr = "";
            BuyerCouponInfoDTO oldCouponDTO = null;
            BuyerCouponInfoDTO couponDTO = null;
            String couponAmountStr = "";
            String couponStr = "";
            Jedis jedis = null;
            Transaction multi = null;
            List<Object> mutilRst = null;
            List<Object> result = Lists.newArrayList();
            List<DictionaryInfo> couponStatusList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_STATUS);
            List<DictionaryInfo> couponTypeList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_COUPON_KIND);
            Map<String, String> couponStatusMap = new HashMap<String, String>();
            Map<String, String> couponTypeMap = new HashMap<String, String>();
            for (DictionaryInfo dict : couponStatusList) {
                couponStatusMap.put(dict.getCode(), dict.getValue());
            }
            for (DictionaryInfo dict : couponTypeList) {
                couponTypeMap.put(dict.getCode(), dict.getValue());
            }
            logger.info("\n 线程名称:[{}],方法:[{}],开始时间:[{}]", threadName, "ReplaceMemberCollectCoupon-start",
                    DateUtils.getCurrentDate(""));
            try {
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
                couponAmountStr = String.valueOf(
                        CalculateUtils.multiply(couponDTO.getCouponAmount(), new BigDecimal(100)).longValue());
                jedis = marketRedisDB.getResource();
                while (jedis.llen(RedisConst.REDIS_COUPON_SEND_LIST + "_" + oldPromotionId) > 0) {
                    buyerCouponKey = jedis.lpop(RedisConst.REDIS_COUPON_SEND_LIST + "_" + oldPromotionId);
                    logger.info("\n 线程名称:[{}],方法:[{}],原优惠券Key:[{}]", threadName, "ReplaceMemberCollectCoupon-work",
                            buyerCouponKey);
                    if (buyerCouponKey.indexOf("&") <= 0) {
                        continue;
                    }
                    tmpArr = buyerCouponKey.split("&");
                    buyerCode = tmpArr[0];
                    oldBuyerCouponCode = tmpArr[1];
                    if (!jedis.hexists(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, oldBuyerCouponCode)) {
                        continue;
                    }
                    oldCouponStr = jedis.hget(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, oldBuyerCouponCode);
                    oldCouponDTO = JSON.parseObject(oldCouponStr, BuyerCouponInfoDTO.class);
                    if (oldCouponDTO == null) {
                        continue;
                    }
                    if (!couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_UNUSED).equals(oldCouponDTO.getStatus())
                            && !couponStatusMap.get(DictionaryConst.OPT_COUPON_STATUS_INVALID)
                            .equals(oldCouponDTO.getStatus())) {
                        logger.info("\n 线程名称:[{}],方法:[{}],跳过原因:[{}],原优惠券:[{}]", threadName,
                                "ReplaceMemberCollectCoupon-work", "非可用状态", oldCouponStr);
                        jedis.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId, 1);
                        continue;
                    }
                    oldCouponAmount = jedis.hget(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCouponKey);
                    oldCouponDecimal = CalculateUtils.divide(new BigDecimal(oldCouponAmount), new BigDecimal(100));
                    if (BigDecimal.ZERO.compareTo(oldCouponDecimal) >= 0) {
                        logger.info("\n 线程名称:[{}],方法:[{}],跳过原因:[{}],原优惠券:[{}]", threadName,
                                "ReplaceMemberCollectCoupon-work", "优惠券余额小于等于0", oldCouponStr);
                        jedis.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId, 1);
                        continue;
                    }
                    couponDTO.setBuyerCode(buyerCode);
                    couponDTO.setBuyerName(oldCouponDTO.getBuyerName());
                    newBuyerCouponCode = generateCouponCode(jedis, couponDTO.getCouponType());
                    couponDTO.setBuyerCouponCode(newBuyerCouponCode);
                    couponDTO.setGetCouponTime(new Date());
                    if (couponTypeMap.get(DictionaryConst.OPT_COUPON_KIND_DISCOUNT)
                            .equals(targetDiscountInfo.getCouponKind())) {
                        couponDTO.setCouponAmount(oldCouponDecimal);
                        couponDTO.setCouponLeftAmount(oldCouponDecimal);
                        couponAmountStr = String.valueOf(
                                CalculateUtils.multiply(couponDTO.getCouponAmount(), new BigDecimal(100)).longValue());
                    }
                    couponStr = JSON.toJSONString(couponDTO);
                    logger.info("\n 线程名称:[{}],方法:[{}],原优惠券:[{}],替换优惠券:[{}]", threadName,
                            "ReplaceMemberCollectCoupon-start", oldCouponStr, couponStr);
                    multi = jedis.multi();
                    multi.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, newBuyerCouponCode, couponStr);
                    multi.hset(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + newBuyerCouponCode,
                            couponAmountStr);
                    multi.hincrBy(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId, 1);
                    multi.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId, 1);
                    multi.rpush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, couponStr);
                    multi.rpush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId,
                            buyerCode + "&" + newBuyerCouponCode);
                    mutilRst = multi.exec();
                    if (mutilRst == null || mutilRst.isEmpty()) {
                        logger.error("\n 线程名称:[{}],方法:[{}],异常:[{}]", threadName, "ReplaceMemberCollectCoupon-work",
                                JSON.toJSONString(mutilRst));
                    }
                    localCount++;
                }
                for (int i = 0; i < result.size(); i++) {
                    logger.info("\n 线程名称:[{}],方法:[{}],发送数量:[{}], 发送结果:[{}]", threadName,
                            "ReplaceMemberCollectCoupon-work", i, result.get(i).toString());
                }
            } catch (Exception e) {
                logger.error("\n 线程名称:[{}],方法:[{}],总发送数量:[{}],错误内容:[{}]", threadName, "ReplaceMemberCollectCoupon-end",
                        localCount, ExceptionUtils.getStackTraceAsString(e));
                throw e;
            } finally {
                marketRedisDB.releaseResource(jedis);
                long endTime = System.currentTimeMillis();
                logger.info("\n 线程名称:[{}],方法:[{}],本线程发送数量:[{}],结束时间:[{}],耗时:[{}]", threadName,
                        "ReplaceMemberCollectCoupon-end", localCount, DateUtils.getCurrentDate(""),
                        (endTime - startTime) + "ms");
            }
            return localCount;
        }
    }

    public final class AutoPresentBuyerCoupon implements Callable<Integer> {
        private int startIdx;
        private int endIdx;
        private String threadName;
        private PromotionDiscountInfoDTO targetDiscountInfo;
        private List<PromotionBuyerDetailDTO> buyerDetailList;

        public AutoPresentBuyerCoupon(int startIdx, int endIdx, PromotionDiscountInfoDTO discountInfo,
                List<PromotionBuyerDetailDTO> buyerDetailList) {
            this.startIdx = startIdx;
            this.endIdx = endIdx;
            this.threadName =
                    String.valueOf(Thread.currentThread().getId()) + "-" + discountInfo.getPromotionName() + "-"
                            + startIdx + "-" + endIdx;
            this.targetDiscountInfo = discountInfo;
            this.buyerDetailList = buyerDetailList;
        }

        public Integer call() throws Exception {
            long startTime = System.currentTimeMillis();
            int sendedCount = 0;
            String promotionId = targetDiscountInfo.getPromotionId();
            Integer receiveLimit = targetDiscountInfo.getReceiveLimit();
            BuyerCouponInfoDTO couponDTO = null;
            String buyerCouponReceiveKey = "";
            Jedis jedis = null;
            Pipeline pipeline = null;
            List<Object> result = Lists.newArrayList();
            //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//            Transaction multi = null;
            //----- delete by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            List<PromotionBuyerDetailDTO> targetBuyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
            List<PromotionBuyerDetailDTO> sendBuyerDetailList = null;
            List<PromotionBuyerDetailDTO> needsendNextBuyerDetailList = null;
            List<PromotionBuyerDetailDTO> restoreCountList = null;
            int idxCount = 0;
            String couponAmountStr = "";
            String buyerCode = "";
            String buyerCouponCode = "";
            String couponStr = "";
            List<Object> mutilRst = null;
            //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
            int isNeedRemind = targetDiscountInfo.getIsNeedRemind();
            boolean needGetBelongSellerFlg = false;
            PromotionSellerRuleDTO sellerRuleDTO = targetDiscountInfo.getSellerRuleDTO();
            PromotionBuyerDetailDTO tmpDetailDTO = null;
            List<String> buyerCodeList = new ArrayList<String>();
            Map<String, PromotionBuyerDetailDTO> buyerInfoMap = new HashMap<String, PromotionBuyerDetailDTO>();
            ExecuteResult<List<SellerBelongRelationDTO>> belongRelationResult = null;
            List<SellerBelongRelationDTO> belongRelationList = null;
            String tmpMemberCode = "";
            Map<String, Integer> remindTargetBuyerMap = new HashMap<String, Integer>();
            //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            String validStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
            logger.info("\n 线程名称:[{}],方法:[{}],开始时间:[{}]", threadName, "autoPresentBuyerCoupon-work",
                    DateUtils.getCurrentDate(""));
            if (buyerDetailList == null || buyerDetailList.isEmpty() || receiveLimit <= 0) {
                return sendedCount;
            }
            try {
                for (int i = startIdx; i < endIdx; i++) {
                    //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//                    targetBuyerDetailList.add(buyerDetailList.get(i));
                    tmpDetailDTO = buyerDetailList.get(i);
                    targetBuyerDetailList.add(tmpDetailDTO);
                    buyerCodeList.add(tmpDetailDTO.getBuyerCode());
                    buyerInfoMap.put(tmpDetailDTO.getBuyerCode(), tmpDetailDTO);
                    //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
                }
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
                needGetBelongSellerFlg = isBelongSellerRule(sellerRuleDTO);
                if (needGetBelongSellerFlg) {
                    belongRelationResult = belongRelationshipService.queryBelongRelationListByMemberCodeList(buyerCodeList);
                    if (!belongRelationResult.isSuccess()) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_GET_BELONG_SELLER_ERROR,
                                StringUtils.join(belongRelationResult.getErrorMessages(), ","));
                    }
                    belongRelationList = belongRelationResult.getResult();
                    if (belongRelationList != null && !belongRelationList.isEmpty()) {
                        for (SellerBelongRelationDTO belongRelationDTO : belongRelationList) {
                            tmpMemberCode = belongRelationDTO.getMemberCode();
                            tmpDetailDTO = buyerInfoMap.get(tmpMemberCode);
                            if (tmpDetailDTO != null) {
                                tmpDetailDTO.setBelognSellerCode(belongRelationDTO.getCurBelongSellerCode());
                                tmpDetailDTO.setBelongSellerName(belongRelationDTO.getCurBelongSellerName());
                            }
                        }
                    }
                }
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
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
                couponAmountStr = String.valueOf(
                        CalculateUtils.multiply(couponDTO.getCouponAmount(), new BigDecimal(100)).longValue());
                jedis = marketRedisDB.getResource();
                pipeline = jedis.pipelined();
                while (targetBuyerDetailList != null && !targetBuyerDetailList.isEmpty()) {
                    if (!validStatus.equals(jedis.hget(RedisConst.REDIS_COUPON_VALID, promotionId))) {
                        break;
                    }
                    sendBuyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
                    needsendNextBuyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
                    restoreCountList = new ArrayList<PromotionBuyerDetailDTO>();
                    for (PromotionBuyerDetailDTO buyerDTO : targetBuyerDetailList) {
                        buyerCouponReceiveKey = buyerDTO.getBuyerCode() + "&" + promotionId;
                        pipeline.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey, 1);
                    }
                    result = pipeline.syncAndReturnAll();
                    idxCount = 0;
                    for (Object src : result) {
                        if ((Long) src > receiveLimit) {
                            restoreCountList.add(targetBuyerDetailList.get(idxCount));
                        } else {
                            sendBuyerDetailList.add(targetBuyerDetailList.get(idxCount));
                            if ((Long) src < receiveLimit) {
                                needsendNextBuyerDetailList.add(targetBuyerDetailList.get(idxCount));
                            }
                        }
                        idxCount++;
                    }
                    if (!restoreCountList.isEmpty()) {
                        for (PromotionBuyerDetailDTO tmpDTO : restoreCountList) {
                            buyerCouponReceiveKey = tmpDTO.getBuyerCode() + "&" + promotionId;
                            pipeline.hincrBy(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey, -1);
                        }
                        pipeline.sync();
                    }
                    if (sendBuyerDetailList.isEmpty()) {
                        break;
                    }
                    for (PromotionBuyerDetailDTO tmpDTO : sendBuyerDetailList) {
                        buyerCode = tmpDTO.getBuyerCode();
                        couponDTO.setBuyerCode(buyerCode);
                        couponDTO.setBuyerName(tmpDTO.getBuyerName());
                        buyerCouponCode = generateCouponCode(jedis, couponDTO.getCouponType());
                        couponDTO.setBuyerCouponCode(buyerCouponCode);
                        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
                        if (needGetBelongSellerFlg) {
                            if (StringUtils.isEmpty(tmpDTO.getBelognSellerCode())) {
                                logger.warn("\n 线程名称:[{}],方法:[{}],警告:[{}],会员编号:[{}],会员名称:[{}]", threadName,
                                        "autoPresentBuyerCoupon-work", "没有会员归属平台公司信息", buyerCode,
                                        tmpDTO.getBuyerName());
                                continue;
                            }
                            couponDTO.setPromotionProviderSellerCode(tmpDTO.getBelognSellerCode());
                        }
                        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
                        couponDTO.setGetCouponTime(new Date());
                        couponStr = JSON.toJSONString(couponDTO);
                        //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
//                        multi = jedis.multi();
//                        multi.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode, couponStr);
//                        multi.hset(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode,
//                                couponAmountStr);
//                        multi.hincrBy(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId, 1);
//                        multi.rpush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, couponStr);
//                        multi.rpush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId,
//                                buyerCode + "&" + buyerCouponCode);
//                        mutilRst = multi.exec();
//                        if (mutilRst == null || mutilRst.isEmpty()) {
//                            logger.error("\n 线程名称:[{}],方法:[{}],异常:[{}]", threadName, "autoPresentBuyerCoupon-work",
//                                    JSON.toJSONString(mutilRst));
//                        }
                        pipeline.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode, couponStr);
                        pipeline.hset(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode,
                                couponAmountStr);
                        pipeline.hincrBy(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId, 1);
                        pipeline.rpush(RedisConst.REDIS_BUYER_COUPON_NEED_SAVE_LIST, couponStr);
                        pipeline.rpush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId,
                                buyerCode + "&" + buyerCouponCode);
                        //----- modify by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
                        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
                        remindTargetBuyerMap.put(buyerCode, receiveLimit);
                        //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
                        sendedCount++;
                    }
                    pipeline.sync();
                    targetBuyerDetailList = needsendNextBuyerDetailList;
                }
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
                if (remindTargetBuyerMap != null && !remindTargetBuyerMap.isEmpty()) {
                    if (isNeedRemind == NoticeTypeEnum.SMS.getValue() || isNeedRemind == NoticeTypeEnum.POPUPSMS
                            .getValue()) {
                        new Thread(new SendMemberSMSNoticeThread(targetDiscountInfo.getPromotionName(),
                                remindTargetBuyerMap)).start();
                    }
                }
                //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
            } catch (Exception e) {
                logger.error("\n 线程名称:[{}],方法:[{}],异常:[{}]", threadName, "autoPresentBuyerCoupon-work",
                        ExceptionUtils.getStackTraceAsString(e));
                throw e;
            } finally {
                marketRedisDB.releaseResource(jedis);
                long endTime = System.currentTimeMillis();
                logger.info("\n 线程名称:[{}],方法:[{}],发送数量:[{}],结束时间:[{}],耗时:[{}]", threadName,
                        "autoPresentBuyerCoupon-end", sendedCount, DateUtils.getCurrentDate(""),
                        (endTime - startTime) + "ms");
            }
            return sendedCount;
        }
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

    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 start -----
    /**
     * 根据会员规则取得发送优惠券的对象会员列表
     *
     * @param buyerRule
     * @return
     */
    private List<PromotionBuyerDetailDTO> getTargetBuyerDetailList(PromotionBuyerRuleDTO buyerRule) {
        List<String> targetBuyerLevelList = new ArrayList<String>();
        ExecuteResult<List<MemberGradeDTO>> targetMemberResult = null;
        List<MemberGradeDTO> targetMemberList = null;
        List<PromotionBuyerDetailDTO> buyerDetailList = new ArrayList<PromotionBuyerDetailDTO>();
        PromotionBuyerDetailDTO buyerDTO = null;

        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
                DictionaryConst.OPT_PROMOTION_BUYER_RULE_GRADE).equals(buyerRule.getRuleTargetType())) {
            targetBuyerLevelList = buyerRule.getTargetBuyerLevelList();
            if (targetBuyerLevelList != null && !targetBuyerLevelList.isEmpty()) {
                for (String level : targetBuyerLevelList) {
                    targetMemberResult = memberGradeService.selectMemberByGrade(level);
                    if (!targetMemberResult.isSuccess()) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_AUTO_PRESENT_NO_MEMBER,
                                StringUtils.join(targetMemberResult.getErrorMessages(), ","));
                    }
                    targetMemberList = targetMemberResult.getResult();
                    if (targetMemberList != null && !targetMemberList.isEmpty()) {
                        for (MemberGradeDTO memberDTO : targetMemberList) {
                            if (StringUtils.isEmpty(memberDTO.getMemberCode())) {
                                continue;
                            }
                            buyerDTO = new PromotionBuyerDetailDTO();
                            buyerDTO.setBuyerCode(memberDTO.getMemberCode());
                            buyerDTO.setBuyerName(memberDTO.getCompanyName());
                            buyerDetailList.add(buyerDTO);
                        }
                    }
                }
            }
        } else {
            buyerDetailList = buyerRule.getBuyerDetailList();
        }
        return buyerDetailList;
    }

    /**
     * 校验卖家规则是否是取得归属平台信息
     *
     * @param sellerRule
     * @return
     */
    private boolean isBelongSellerRule(PromotionSellerRuleDTO sellerRule) {
        String sellerType = "";
        if (sellerRule == null) {
            return false;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_RULE,
                DictionaryConst.OPT_PROMOTION_SELLER_RULE_APPIONT).equals(sellerRule.getRuleTargetType())) {
            sellerType = sellerRule.getTargetSellerType();
            if (StringUtils.isEmpty(sellerType)) {
                return false;
            }
            if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_SELLER_TYPE,
                    DictionaryConst.OPT_PROMOTION_SELLER_TYPE_HTD_BELONG).equals(sellerType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对于发券会员提醒通知Task
     */
    private final class SendMemberSMSNoticeThread implements Runnable {

        private String threadName;

        private Map<String, Integer> remindTargetBuyerMap;

        public SendMemberSMSNoticeThread(String promotionName, Map<String, Integer> remindTargetBuyerMap) {
            this.threadName =
                    String.valueOf(Thread.currentThread().getId()) + "-" + promotionName + "-" + "短信通知发券会员";
            this.remindTargetBuyerMap = remindTargetBuyerMap;
        }

        @Override
        public void run() {
            String buyerCode = "";
            ExecuteResult<MemberBaseInfoDTO> memberBaseInfoResult = null;
            MemberBaseInfoDTO memberBaseInfoDTO = null;
            String phoneNum = "";
            SendSmsDTO sendSmsDTO = null;
            for (Map.Entry<String, Integer> entry : remindTargetBuyerMap.entrySet()) {
                buyerCode = entry.getKey();
                memberBaseInfoResult = memberBaseInfoService.queryMemberCompanyInfo(buyerCode);
                if (!memberBaseInfoResult.isSuccess()) {
                    logger.warn("\n 线程名称:[{}],方法:[{}],警告:[{}],会员编号:[{}]", threadName, "SendMemberSMSNoticeThread-run",
                            "没有会员法人手机号", buyerCode);
                    continue;
                }
                memberBaseInfoDTO = memberBaseInfoResult.getResult();
                if (memberBaseInfoDTO == null) {
                    logger.warn("\n 线程名称:[{}],方法:[{}],警告:[{}],会员编号:[{}]", threadName, "SendMemberSMSNoticeThread-run",
                            "没有会员法人手机号", buyerCode);
                    continue;
                }
                phoneNum = memberBaseInfoDTO.getArtificialPersonMobile();
                if (StringUtils.isEmpty(phoneNum)) {
                    logger.warn("\n 线程名称:[{}],方法:[{}],警告:[{}],会员编号:[{}]", threadName, "SendMemberSMSNoticeThread-run",
                            "没有会员法人手机号", buyerCode);
                    continue;
                }
                sendSmsDTO = new SendSmsDTO();
                sendSmsDTO.setPhone(phoneNum);
                sendSmsDTO.setSmsType(dictionary.getValueByCode(DictionaryConst.TYPE_SMS_TEMPLATE_TYPE, DictionaryConst.OPT_SMS_MEMBER_RECEIVE_COUPON_NOTICE));
                sendSmsEmailService.sendSms(sendSmsDTO);
            }
        }
    }

    /**
     * 设置弹框提醒的Redis信息
     *
     * @param pipeline
     * @param targetDiscountInfo
     * @param remindTargetBuyerMap
     */
    private void initMemberPopupNotice2Redis(Pipeline pipeline, PromotionDiscountInfoDTO targetDiscountInfo,
            Map<String, Integer> remindTargetBuyerMap) {
        String promotionId = targetDiscountInfo.getPromotionId();
        Integer receiveLimit = 0;
        String buyerCode = "";
        for (Map.Entry<String, Integer> entry : remindTargetBuyerMap.entrySet()) {
            buyerCode = entry.getKey();
            receiveLimit = entry.getValue();
            pipeline.hset(RedisConst.REDIS_POPUP_NOTICE_INFO_HASH + "_" + buyerCode, promotionId, receiveLimit.toString());
        }
        pipeline.sync();
    }
    //----- add by jiangkun for 2017活动需求商城优惠券激活 on 20171030 end -----
}

package cn.htd.marketcenter.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DateUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.B2cCouponInfoSyncHistoryDAO;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.dao.PromotionDiscountInfoDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dao.PromotionStatusHistoryDAO;
import cn.htd.marketcenter.dmo.B2cCouponInfoSyncDMO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionAccumulatyDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import cn.htd.marketcenter.dto.PromotionStatusHistoryDTO;
import cn.htd.marketcenter.service.PromotionBaseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

/**
 * 根据B2C同步的触发返券活动信息更新促销活动信息
 */
@Transactional
public class UpdateSyncB2cCouponInfoScheduleTask implements IScheduleTaskDealMulti<B2cCouponInfoSyncDMO> {

    protected static transient Logger logger = LoggerFactory.getLogger(UpdateSyncB2cCouponInfoScheduleTask.class);

    @Resource
    private GeneratorUtils generator;

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private B2cCouponInfoSyncHistoryDAO b2cCouponInfoSyncHistoryDAO;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private PromotionDiscountInfoDAO promotionDiscountInfoDAO;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;

    @Resource
    private BuyerCouponInfoDAO buyerCouponInfoDAO;

    private int threadPoolSize = Runtime.getRuntime().availableProcessors();

    @Override
    public Comparator<B2cCouponInfoSyncDMO> getComparator() {
        return new Comparator<B2cCouponInfoSyncDMO>() {
            public int compare(B2cCouponInfoSyncDMO o1, B2cCouponInfoSyncDMO o2) {
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
    public List<B2cCouponInfoSyncDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateSyncB2cCouponInfoScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        List<B2cCouponInfoSyncDMO> dealTargetList = new ArrayList<B2cCouponInfoSyncDMO>();
        try {
            if (marketRedisDB.getSetLen(RedisConst.REDIS_SYNC_B2C_COUPON_SET) > 0) {
                dealTargetList.add(new B2cCouponInfoSyncDMO());
            }
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-selectTasks",
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
    public boolean execute(B2cCouponInfoSyncDMO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        String targetB2cActivityCode = "";
        String hostIp = generator.getHostIp();
        String threadId = String.valueOf(Thread.currentThread().getId());
        String deleteStatus = dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE);
        List<B2cCouponInfoSyncDMO> syncCouponInfoList = null;
        PromotionInfoDTO promotionInfoDTO = null;
        try {
            while (marketRedisDB.getSetLen(RedisConst.REDIS_SYNC_B2C_COUPON_SET) > 0) {
                targetB2cActivityCode = marketRedisDB.popSet(RedisConst.REDIS_SYNC_B2C_COUPON_SET);
                if (StringUtils.isEmpty(targetB2cActivityCode)) {
                    continue;
                }
                if (marketRedisDB.setHashNx(RedisConst.REDIS_DEAL_B2C_COUPON_HASH, targetB2cActivityCode,
                        hostIp + "_" + threadId).longValue() <= 0) {
                    marketRedisDB.addSet(RedisConst.REDIS_SYNC_B2C_COUPON_SET, targetB2cActivityCode);
                    continue;
                }
                logger.info("\n 方法:[{}],入参:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute", targetB2cActivityCode);
                try {
                    syncCouponInfoList = queryDealTargetB2cCouponInfoSyncDMO(targetB2cActivityCode);
                    if (syncCouponInfoList == null || syncCouponInfoList.isEmpty()) {
                        continue;
                    }
                    promotionInfoDTO = queryB2cCouponPromotionInfo(targetB2cActivityCode, deleteStatus);
                    for (B2cCouponInfoSyncDMO syncCouponInfo : syncCouponInfoList) {
                        syncCouponInfo.setDealFailReason("");
                        try {
                            updateSyncB2cCouponInfo(syncCouponInfo, promotionInfoDTO);
                            updateB2cCouponInfoDealResult(syncCouponInfo);
                            break;
                        } catch (MarketCenterBusinessException mcbe) {
                            updateB2cCouponInfoDealResult(syncCouponInfo);
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    marketRedisDB.addSet(RedisConst.REDIS_SYNC_B2C_COUPON_SET, targetB2cActivityCode);
                    throw ex;
                } finally {
                    marketRedisDB.delHash(RedisConst.REDIS_DEAL_B2C_COUPON_HASH, targetB2cActivityCode);
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }

    /**
     * 查询最新的同步待处理B2C活动信息
     *
     * @param targetB2cActivityCode
     * @return
     * @throws Exception
     */
    private List<B2cCouponInfoSyncDMO> queryDealTargetB2cCouponInfoSyncDMO(String targetB2cActivityCode)
            throws Exception {
        B2cCouponInfoSyncDMO couponInfoCondition = new B2cCouponInfoSyncDMO();
        couponInfoCondition.setB2cActivityCode(targetB2cActivityCode);
        couponInfoCondition.setDealFlag(YesNoEnum.NO.getValue());
        return b2cCouponInfoSyncHistoryDAO.queryNeedDealB2cCouponInfoByB2cActivityCode(couponInfoCondition);
    }

    /**
     * 根据B2C活动编码查询活动信息
     *
     * @param targetB2cActivityCode
     * @param deleteStatus
     * @return
     * @throws Exception
     */
    private PromotionInfoDTO queryB2cCouponPromotionInfo(String targetB2cActivityCode, String deleteStatus)
            throws Exception {
        PromotionInfoDTO promotionInfoCondition = new PromotionInfoDTO();
        promotionInfoCondition.setB2cActivityCode(targetB2cActivityCode);
        promotionInfoCondition.setStatus(deleteStatus);
        return promotionInfoDAO.queryPromotionInfoByB2cActivityCode(promotionInfoCondition);
    }

    /**
     * 处理促销活动
     *
     * @param b2cCouponInfoSyncDMO
     * @param promotionInfoDTO
     * @throws Exception
     */
    public void updateSyncB2cCouponInfo(B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO, PromotionInfoDTO promotionInfoDTO)
            throws Exception {
        Date nowDt = new Date();
        PromotionDiscountInfoDTO couponInfoDTO = null;
        String promotionId = "";
        int targetCouponListSize = 0;
        ExecutorService executorService = null;
        int distributedCount = 0;
        int taskProvideCount = 0;
        int cellCount = 0;
        List<Future<Integer>> workResultList = new ArrayList<Future<Integer>>();
        Future<Integer> workResult = null;
        BuyerCouponInfoDTO buyerCouponInfoDTO = null;
        long effeciveTime = 0L;
        long couponStartTime = 0L;

        try {
            if (promotionInfoDTO == null) {
                if (nowDt.after(b2cCouponInfoSyncDMO.getCouponEndTime())) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED,
                            "同步的优惠券活动信息已过期");
                }
                couponInfoDTO = addB2cCouponPromotionInfo(b2cCouponInfoSyncDMO);
            } else {
                if (nowDt.after(promotionInfoDTO.getInvalidTime())) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED,
                            "已产生的优惠券返券活动信息已过期不能修改");
                }
                if (nowDt.after(promotionInfoDTO.getEffectiveTime())) {
                    effeciveTime = promotionInfoDTO.getEffectiveTime().getTime();
                    couponStartTime = b2cCouponInfoSyncDMO.getCouponStartTime().getTime();
                    if (effeciveTime != couponStartTime) {
                        throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_STARTED,
                                "已产生的优惠券返券活动信息已开始开始时间不能变动");
                    }
                }
                if (!dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(promotionInfoDTO.getShowStatus())
                        && !dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(promotionInfoDTO.getShowStatus())) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_VALID,
                            "已产生的优惠券返券活动信息已失效不能修改");
                }
                couponInfoDTO = updateB2cCouponPromotionInfo(b2cCouponInfoSyncDMO, promotionInfoDTO);
            }
            if (couponInfoDTO == null) {
                return;
            }
            promotionId = couponInfoDTO.getPromotionId();
            marketRedisDB.setHash(RedisConst.REDIS_COUPON_VALID, promotionId, dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                            DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
            marketRedisDB.setHash(RedisConst.REDIS_COUPON_TRIGGER, couponInfoDTO.getB2cActivityCode(),
                    JSON.toJSONString(couponInfoDTO));
            targetCouponListSize =
                    marketRedisDB.getLlen(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId).intValue();
            if (targetCouponListSize == 0) {
                return;
            }
            executorService = Executors.newFixedThreadPool(threadPoolSize);
            taskProvideCount = (new BigDecimal(targetCouponListSize))
                    .divide(new BigDecimal(threadPoolSize), 0, RoundingMode.CEILING).intValue();
            for (int i = 0; i < threadPoolSize; i++) {
                cellCount = targetCouponListSize - distributedCount > taskProvideCount ? taskProvideCount
                        : targetCouponListSize - distributedCount;
                if (cellCount <= 0) {
                    break;
                }
                workResult = executorService
                        .submit(new UpdateRedisBuyerCouponInfo(distributedCount, distributedCount + cellCount,
                                couponInfoDTO));
                workResultList.add(workResult);
                distributedCount += cellCount;
            }
            buyerCouponInfoDTO = new BuyerCouponInfoDTO();
            buyerCouponInfoDTO.setPromotionId(couponInfoDTO.getPromotionId());
            buyerCouponInfoDTO.setLevelCode(couponInfoDTO.getLevelCode());
            buyerCouponInfoDTO.setCouponName(couponInfoDTO.getPromotionName());
            buyerCouponInfoDTO.setCouponStartTime(couponInfoDTO.getEffectiveTime());
            buyerCouponInfoDTO.setCouponEndTime(couponInfoDTO.getInvalidTime());
            buyerCouponInfoDTO.setDiscountThreshold(couponInfoDTO.getDiscountThreshold());
            buyerCouponInfoDTO.setDiscountPercent(couponInfoDTO.getDiscountPercent());
            buyerCouponInfoDTO.setModifyId(couponInfoDTO.getModifyId());
            buyerCouponInfoDTO.setModifyName(couponInfoDTO.getModifyName());
            buyerCouponInfoDAO.updateB2cActivityInfo2BuyerCoupon(buyerCouponInfoDTO);
            for (Future<Integer> workRst : workResultList) {
                logger.info("\n 方法:[{}],线程执行结果:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute", workRst.get());
            }
        } catch (MarketCenterBusinessException mcbe) {
            logger.warn("\n 方法:[{}],优惠券活动名称:[{}],异常:[{}],参数:[{}]",
                    "UpdateSyncB2cCouponInfoScheduleTask-updateSyncB2cCouponInfo", b2cCouponInfoSyncDMO.getCouponName(),
                    ExceptionUtils.getStackTraceAsString(mcbe), JSON.toJSONString(b2cCouponInfoSyncDMO));
            b2cCouponInfoSyncDMO.setDealFailReason(mcbe.getMessage());
            throw mcbe;
        } catch (Exception e) {
            logger.error("\n 方法:[{}],优惠券活动名称:[{}],异常:[{}],参数:[{}]",
                    "UpdateSyncB2cCouponInfoScheduleTask-updateSyncB2cCouponInfo", b2cCouponInfoSyncDMO.getCouponName(),
                    ExceptionUtils.getStackTraceAsString(e), JSON.toJSONString(b2cCouponInfoSyncDMO));
            throw e;
        } finally {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
        }
    }

    /**
     * 更新促销活动更新结果信息
     *
     * @param b2cCouponInfoSyncDMO
     */
    private void updateB2cCouponInfoDealResult(B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO) {

        b2cCouponInfoSyncDMO.setModifyId(0L);
        b2cCouponInfoSyncDMO.setModifyName("sys");
        if (StringUtils.isEmpty(b2cCouponInfoSyncDMO.getDealFailReason())) {
            b2cCouponInfoSyncDMO.setDealFlag(YesNoEnum.YES.getValue());
            b2cCouponInfoSyncHistoryDAO.updateB2cCouponInfoDealSuccessResult(b2cCouponInfoSyncDMO);
        } else {
            b2cCouponInfoSyncDMO.setDealFlag(YesNoEnum.ERROR.getValue());
            b2cCouponInfoSyncHistoryDAO.updateB2cCouponInfoDealFailResult(b2cCouponInfoSyncDMO);
        }
    }

    /**
     * 更新促销活动信息
     *
     * @param b2cCouponInfoSyncDMO
     * @param promotionInfoDTO
     * @return
     * @throws Exception
     */
    private PromotionDiscountInfoDTO updateB2cCouponPromotionInfo(B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO,
            PromotionInfoDTO promotionInfoDTO) throws Exception {

        PromotionDiscountInfoDTO targetCouponInfoDTO = null;
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();

        accuDTO = baseService.querySingleAccumulatyPromotionInfo(promotionInfoDTO.getPromotionId());
        // 获取优惠券活动信息
        targetCouponInfoDTO = promotionDiscountInfoDAO.queryDiscountInfoById(accuDTO);
        if (targetCouponInfoDTO == null) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_NOT_EXIST, "该促销活动不存在!");
        }
        targetCouponInfoDTO.setPromotionAccumulaty(accuDTO);
        if (!targetCouponInfoDTO.getCouponKind().equals(b2cCouponInfoSyncDMO.getCouponType())) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_KIND_HAS_CHANGED, "同步的优惠券活动的券类型发生变化");
        }
        if (!targetCouponInfoDTO.getCouponProvideType().equals(b2cCouponInfoSyncDMO.getCouponProvideType())) {
            throw new MarketCenterBusinessException(MarketCenterCodeConst.COUPON_PROVIDER_TYPE_HAS_CHANGED,
                    "同步的优惠券活动的券发送方式发生变化");
        }
        if (!checkHasChangeCouponInfo(b2cCouponInfoSyncDMO, targetCouponInfoDTO)) {
            return null;
        }
        targetCouponInfoDTO.setPromotionName(b2cCouponInfoSyncDMO.getCouponName());
        targetCouponInfoDTO.setPromotionDescribe(b2cCouponInfoSyncDMO.getCouponDescribe());
        targetCouponInfoDTO.setEffectiveTime(b2cCouponInfoSyncDMO.getCouponStartTime());
        targetCouponInfoDTO.setInvalidTime(b2cCouponInfoSyncDMO.getCouponEndTime());
        targetCouponInfoDTO.setEffectiveStartTime(b2cCouponInfoSyncDMO.getCouponStartTime());
        targetCouponInfoDTO.setEffectiveEndTime(b2cCouponInfoSyncDMO.getCouponEndTime());
        targetCouponInfoDTO.setDiscountThreshold(b2cCouponInfoSyncDMO.getDiscountThreshold());
        targetCouponInfoDTO.setDiscountPercent(b2cCouponInfoSyncDMO.getDiscountPercent());
        targetCouponInfoDTO.setModifyId(b2cCouponInfoSyncDMO.getCreateId());
        targetCouponInfoDTO.setModifyName(b2cCouponInfoSyncDMO.getCreateName());
        baseService.updateSingleAccumulatyPromotionInfo(targetCouponInfoDTO);
        promotionDiscountInfoDAO.update(targetCouponInfoDTO);
        historyDTO.setPromotionId(targetCouponInfoDTO.getPromotionId());
        historyDTO.setPromotionStatus(targetCouponInfoDTO.getShowStatus());
        historyDTO.setPromotionStatusText("修改优惠券活动信息");
        historyDTO.setCreateId(targetCouponInfoDTO.getCreateId());
        historyDTO.setCreateName(targetCouponInfoDTO.getCreateName());
        promotionStatusHistoryDAO.add(historyDTO);
        return targetCouponInfoDTO;
    }

    /**
     * 判断同步促销活动信息是否和DB中已存在信息发生变化
     *
     * @param b2cCouponInfoSyncDMO
     * @param couponInfoDTO
     * @return
     */
    private boolean checkHasChangeCouponInfo(B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO,
            PromotionDiscountInfoDTO couponInfoDTO) {

        if (!couponInfoDTO.getPromotionName().equals(b2cCouponInfoSyncDMO.getCouponName())) {
            return true;
        }
        if (!couponInfoDTO.getPromotionDescribe().equals(b2cCouponInfoSyncDMO.getCouponDescribe())) {
            return true;
        }
        if (couponInfoDTO.getEffectiveTime().compareTo(b2cCouponInfoSyncDMO.getCouponStartTime()) != 0) {
            return true;
        }
        if (couponInfoDTO.getInvalidTime().compareTo(b2cCouponInfoSyncDMO.getCouponEndTime()) != 0) {
            return true;
        }
        if (couponInfoDTO.getDiscountThreshold().compareTo(b2cCouponInfoSyncDMO.getDiscountThreshold()) != 0) {
            return true;
        }
        if (couponInfoDTO.getDiscountPercent().compareTo(b2cCouponInfoSyncDMO.getDiscountPercent()) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 插入促销活动信息
     *
     * @param b2cCouponInfoSyncDMO
     * @return
     * @throws Exception
     */
    private PromotionDiscountInfoDTO addB2cCouponPromotionInfo(B2cCouponInfoSyncDMO b2cCouponInfoSyncDMO)
            throws Exception {

        PromotionDiscountInfoDTO couponInfoDTO = new PromotionDiscountInfoDTO();
        PromotionAccumulatyDTO accuDTO = null;
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();

        couponInfoDTO.setPromotionName(b2cCouponInfoSyncDMO.getCouponName());
        couponInfoDTO.setPromotionDescribe(b2cCouponInfoSyncDMO.getCouponDescribe());
        couponInfoDTO.setPromotionType(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE, DictionaryConst.OPT_PROMOTION_TYPE_COUPON));
        couponInfoDTO.setPromotionProviderType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_PROVIDER_TYPE,
                DictionaryConst.OPT_PROMOTION_PROVIDER_TYPE_SHOP));
        couponInfoDTO.setEffectiveTime(b2cCouponInfoSyncDMO.getCouponStartTime());
        couponInfoDTO.setInvalidTime(b2cCouponInfoSyncDMO.getCouponEndTime());
        couponInfoDTO.setCostAllocationType(dictionary.getValueByCode(DictionaryConst.TYPE_COST_ALLOCATION_TYPE,
                DictionaryConst.OPT_COST_ALLOCATION_TYPE_PLATFORM));
        couponInfoDTO.setShowStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS));
        couponInfoDTO.setB2cActivityCode(b2cCouponInfoSyncDMO.getB2cActivityCode());
        couponInfoDTO.setRewardType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE,
                DictionaryConst.OPT_PROMOTION_REWARD_TYPE_VOUCHER));
        couponInfoDTO.setCouponKind(b2cCouponInfoSyncDMO.getCouponType());
        couponInfoDTO.setCouponProvideType(b2cCouponInfoSyncDMO.getCouponProvideType());
        couponInfoDTO.setProvideCount(0);
        couponInfoDTO.setReceiveLimit(0);
        couponInfoDTO.setDiscountThreshold(b2cCouponInfoSyncDMO.getDiscountThreshold());
        couponInfoDTO.setDiscountAmount(BigDecimal.ZERO);
        couponInfoDTO.setDiscountPercent(b2cCouponInfoSyncDMO.getDiscountPercent());
        couponInfoDTO.setEffectiveStartTime(b2cCouponInfoSyncDMO.getCouponStartTime());
        couponInfoDTO.setEffectiveEndTime(b2cCouponInfoSyncDMO.getCouponEndTime());
        couponInfoDTO.setCreateId(b2cCouponInfoSyncDMO.getCreateId());
        couponInfoDTO.setCreateName(b2cCouponInfoSyncDMO.getCreateName());
        accuDTO = baseService.insertSingleAccumulatyPromotionInfo(couponInfoDTO);
        couponInfoDTO.setPromotionAccumulaty(accuDTO);
        promotionDiscountInfoDAO.add(couponInfoDTO);
        historyDTO.setPromotionId(couponInfoDTO.getPromotionId());
        historyDTO.setCreateId(couponInfoDTO.getCreateId());
        historyDTO.setCreateName(couponInfoDTO.getCreateName());
        historyDTO.setPromotionStatus(couponInfoDTO.getStatus());
        historyDTO.setPromotionStatusText(
                dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, couponInfoDTO.getStatus()));
        promotionStatusHistoryDAO.add(historyDTO);
        return couponInfoDTO;
    }

    /**
     * 更新Redis会员优惠券数据Thread
     */
    private final class UpdateRedisBuyerCouponInfo implements Callable<Integer> {
        private int startIdx;
        private int endIdx;
        private String threadName;
        private PromotionDiscountInfoDTO targetDiscountInfo;

        public UpdateRedisBuyerCouponInfo(int startIdx, int endIdx, PromotionDiscountInfoDTO discountInfo) {
            this.startIdx = startIdx;
            this.endIdx = endIdx;
            this.threadName =
                    String.valueOf(Thread.currentThread().getId()) + "-" + discountInfo.getPromotionName() + "-"
                            + startIdx + "-" + endIdx;
            this.targetDiscountInfo = discountInfo;
        }

        public Integer call() throws Exception {
            long startTime = System.currentTimeMillis();
            String promotionId = targetDiscountInfo.getPromotionId();
            BuyerCouponInfoDTO couponDTO = null;
            Jedis jedis = null;
            List<String> targetBuyerCouponList = null;
            String[] tmpStrArr = null;
            String buyerCode = "";
            String buyerCouponCode = "";
            String couponStr = "";
            int updatedCount = 0;
            logger.info("\n 线程名称:[{}],方法:[{}],开始时间:[{}]", threadName, "UpdateRedisBuyerCouponInfo-work",
                    DateUtils.getCurrentDate(""));

            try {
                jedis = marketRedisDB.getResource();
                targetBuyerCouponList =
                        jedis.lrange(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId, startIdx, endIdx - 1);
                if (targetBuyerCouponList != null && !targetBuyerCouponList.isEmpty()) {
                    for (String buyerCouponStr : targetBuyerCouponList) {
                        tmpStrArr = buyerCouponStr.split("&");
                        buyerCode = tmpStrArr[0];
                        buyerCouponCode = tmpStrArr[1];
                        couponStr = jedis.hget(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode);
                        couponDTO = JSON.parseObject(couponStr, BuyerCouponInfoDTO.class);
                        if (couponDTO != null) {
                            couponDTO.setCouponName(targetDiscountInfo.getPromotionName());
                            couponDTO.setCouponDescribe(targetDiscountInfo.getPromotionDescribe());
                            couponDTO.setCouponStartTime(targetDiscountInfo.getEffectiveStartTime());
                            couponDTO.setCouponEndTime(targetDiscountInfo.getEffectiveEndTime());
                            couponDTO.setDiscountThreshold(targetDiscountInfo.getDiscountThreshold());
                            couponDTO.setDiscountPercent(targetDiscountInfo.getDiscountPercent());
                            jedis.hset(RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode, buyerCouponCode, JSON.toJSONString(couponDTO));
                            updatedCount++;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("\n 线程名称:[{}],方法:[{}],异常:[{}]", threadName, "UpdateRedisBuyerCouponInfo-work",
                        ExceptionUtils.getStackTraceAsString(e));
                throw e;
            } finally {
                marketRedisDB.releaseResource(jedis);
                long endTime = System.currentTimeMillis();
                logger.info("\n 线程名称:[{}],方法:[{}],发送数量:[{}],结束时间:[{}],耗时:[{}]", threadName,
                        "UpdateRedisBuyerCouponInfo-end", updatedCount, DateUtils.getCurrentDate(""),
                        (endTime - startTime) + "ms");
            }
            return updatedCount;
        }
    }
}

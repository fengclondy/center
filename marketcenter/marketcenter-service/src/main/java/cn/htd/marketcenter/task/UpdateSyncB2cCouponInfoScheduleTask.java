package cn.htd.marketcenter.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.enums.YesNoEnum;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.consts.MarketCenterCodeConst;
import cn.htd.marketcenter.dao.B2cCouponInfoSyncHistoryDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dmo.B2cCouponInfoSyncDMO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 根据B2C同步的触发返券活动信息更新促销活动信息
 */
public class UpdateSyncB2cCouponInfoScheduleTask implements IScheduleTaskDealMulti<B2cCouponInfoSyncDMO> {

    protected static transient Logger logger = LoggerFactory.getLogger(UpdateSyncB2cCouponInfoScheduleTask.class);

    @Resource
    private GeneratorUtils generator;

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private B2cCouponInfoSyncHistoryDAO b2cCouponInfoSyncHistoryDAO;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

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
        Jedis jedis = null;
        String targetB2cActivityCode = "";
        String hostIp = generator.getHostIp();
        String threadId = String.valueOf(Thread.currentThread().getId());
        String deleteStatus = dictionary.getCodeByValue(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE);
        try {
            jedis = marketRedisDB.getResource();
            while (jedis.scard(RedisConst.REDIS_SYNC_B2C_COUPON_SET) > 0) {
                targetB2cActivityCode = jedis.spop(RedisConst.REDIS_SYNC_B2C_COUPON_SET);
                if (StringUtils.isEmpty(targetB2cActivityCode)) {
                    continue;
                }
                if (jedis.hsetnx(RedisConst.REDIS_DEAL_B2C_COUPON_HASH, targetB2cActivityCode, hostIp + "_" + threadId)
                        .longValue() <= 0) {
                    jedis.sadd(targetB2cActivityCode);
                    continue;
                }
                logger.info("\n 方法:[{}],入参:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute", targetB2cActivityCode);
                try {

                } catch (Exception ex) {
                    jedis.sadd(targetB2cActivityCode);
                    jedis.hdel(RedisConst.REDIS_DEAL_B2C_COUPON_HASH, targetB2cActivityCode);
                    throw ex;
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateSyncB2cCouponInfoScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            marketRedisDB.releaseResource(jedis);
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
    private B2cCouponInfoSyncDMO queryDealTargetB2cCouponInfoSyncDMO(String targetB2cActivityCode) throws Exception {

        B2cCouponInfoSyncDMO couponInfoCondition = new B2cCouponInfoSyncDMO();
        B2cCouponInfoSyncDMO lastestSyncDTO = null;

        if (!StringUtils.isEmpty(targetB2cActivityCode)) {
            couponInfoCondition.setB2cActivityCode(targetB2cActivityCode);
            couponInfoCondition.setDealFlag(YesNoEnum.NO.getValue());
            lastestSyncDTO =
                    b2cCouponInfoSyncHistoryDAO.queryLastestB2cCouponInfoByB2cActivityCode(couponInfoCondition);
        }
        return lastestSyncDTO;
    }

    /**
     * 根据B2C活动编码查询活动信息
     *
     * @param targetB2cActivityCode
     * @param deleteStatus
     * @return
     * @throws MarketCenterBusinessException
     * @throws Exception
     */
    private PromotionInfoDTO queryB2cCouponPromotionInfo(String targetB2cActivityCode, String deleteStatus) throws MarketCenterBusinessException, Exception {

        PromotionInfoDTO promotionInfoCondition = new PromotionInfoDTO();
        PromotionInfoDTO promotionInfoDTO = null;
        Date nowDt = new Date();


        if (!StringUtils.isEmpty(targetB2cActivityCode)) {
            promotionInfoCondition.setB2cActivityCode(targetB2cActivityCode);
            promotionInfoCondition.setStatus(deleteStatus);
            promotionInfoDTO = promotionInfoDAO.queryPromotionInfoByB2cActivityCode(promotionInfoCondition);
            if (promotionInfoDTO != null) {
                if (nowDt.after(promotionInfoDTO.getInvalidTime())) {
                    throw new MarketCenterBusinessException(MarketCenterCodeConst.PROMOTION_HAS_EXPIRED,  "秒杀活动编号:" + promotionInfoDTO.getPromotionId() + " 该活动已结束");
                }
            }
        }
        return promotionInfoDTO;
    }
}

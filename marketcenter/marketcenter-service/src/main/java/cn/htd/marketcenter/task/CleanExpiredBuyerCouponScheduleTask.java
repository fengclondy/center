
package cn.htd.marketcenter.task;

import cn.htd.common.Pager;
import cn.htd.common.util.SysProperties;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.exception.MarketCenterBusinessException;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.domain.BuyerCouponCondition;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.service.handle.CouponRedisHandle;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 根据优惠券有效时间更新会员优惠券的状态 清除过期优惠券
 */
public class CleanExpiredBuyerCouponScheduleTask implements IScheduleTaskDealMulti<BuyerCouponInfoDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(CleanExpiredBuyerCouponScheduleTask.class);

    /**
     * 清除Redis中会员已过期优惠券信息的时间间隔(单位：月)
     */
    private static final String EXPIRE_BUYER_COUPON_INTERVAL = "remove.redis.buyer.coupon.interval";

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private BuyerCouponInfoDAO buyerCouponInfoDAO;

    @Override
    public Comparator<BuyerCouponInfoDTO> getComparator() {
        return new Comparator<BuyerCouponInfoDTO>() {
            public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
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
    public List<BuyerCouponInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "CleanExpiredBuyerCouponScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        BuyerCouponCondition condition = new BuyerCouponCondition();
        Pager<BuyerCouponInfoDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<BuyerCouponInfoDTO> buyerCouponInfoDTOList = null;

        if (eachFetchDataNum > 0) {
            pager = new Pager<BuyerCouponInfoDTO>();
            pager.setPageOffset(0);
            pager.setRows(eachFetchDataNum);
        }
        try {
            if (taskQueueList != null && taskQueueList.size() > 0) {
                for (TaskItemDefine taskItem : taskQueueList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                condition.setTaskQueueNum(taskQueueNum);
                condition.setTaskIdList(taskIdList);
                condition.setCleanRedisInterval(SysProperties.getProperty(EXPIRE_BUYER_COUPON_INTERVAL));
                buyerCouponInfoDTOList = buyerCouponInfoDAO.queryNeedCleanRedisBuyerCoupon4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "CleanExpiredBuyerCouponScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanExpiredBuyerCouponScheduleTask-selectTasks",
                    JSONObject.toJSONString(buyerCouponInfoDTOList));
        }
        return buyerCouponInfoDTOList;
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
    public boolean execute(BuyerCouponInfoDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "CleanExpiredBuyerCouponScheduleTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        try {
            if (tasks != null && tasks.length > 0) {
                for (BuyerCouponInfoDTO buyerCouponInfoDTO : tasks) {
                    deleteRedisExpiredBuyerCouponInfo(buyerCouponInfoDTO);
                    buyerCouponInfoDAO.updateBuyerCouponCleanRedisStatus(buyerCouponInfoDTO);
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "CleanExpiredBuyerCouponScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanExpiredBuyerCouponScheduleTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }

    /**
     * 删除Redis中的买家优惠券信息
     *
     * @param targetCouponDTO
     * @throws MarketCenterBusinessException
     */
    private void deleteRedisExpiredBuyerCouponInfo(BuyerCouponInfoDTO targetCouponDTO)
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

}

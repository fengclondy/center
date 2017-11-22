
package cn.htd.marketcenter.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.BuyerUseTimelimitedLogDAO;
import cn.htd.marketcenter.domain.BuyerUseTimelimitedLog;
import cn.htd.marketcenter.service.handle.PromotionRedisHandle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 释放秒杀活动预锁1小时以上，但是没提交订单而导致被锁定的库存
 */
public class ReleaseTimelimitedLockedNoOrderStockScheduleTask
        implements IScheduleTaskDealMulti<BuyerUseTimelimitedLog> {

    protected static transient Logger logger =
            LoggerFactory.getLogger(ReleaseTimelimitedLockedNoOrderStockScheduleTask.class);

    /*
     RedisMessageId数据
      */
    private static final String REDIS_MESSAGE_ID_KEY = "B2B_MIDDLE_MESSAGEID_MSB_SEQ";

    /**
     * 清除Reids中预锁但是没有提交订单的库存信息的时间间隔(单位：分钟)
     */
    private static final String RELEASE_TIMELIMITED_LOCKED_STOCK_INTERVAL = "release.timelimited.locked.stock.interval";

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private RedisDB redisDB;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionRedisHandle promotionRedisHandle;

    @Resource
    private BuyerUseTimelimitedLogDAO buyerUseTimelimitedLogDAO;

    @Override
    public Comparator<BuyerUseTimelimitedLog> getComparator() {
        return new Comparator<BuyerUseTimelimitedLog>() {
            public int compare(BuyerUseTimelimitedLog o1, BuyerUseTimelimitedLog o2) {
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
    public List<BuyerUseTimelimitedLog> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]",
                "ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        BuyerUseTimelimitedLog condition = new BuyerUseTimelimitedLog();
        Pager<BuyerUseTimelimitedLog> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<BuyerUseTimelimitedLog> buyerUseTimelimitedLogList = null;
        if (eachFetchDataNum > 0) {
            pager = new Pager<BuyerUseTimelimitedLog>();
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
                condition.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
                        DictionaryConst.OPT_PROMOTION_TYPE_TIMELIMITED));
                condition.setUseType(dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                        DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE));
                condition.setReleaseStockInterval(SysProperties.getProperty(RELEASE_TIMELIMITED_LOCKED_STOCK_INTERVAL));
                buyerUseTimelimitedLogList = buyerUseTimelimitedLogDAO.queryNeedReleaseTimelimitedStock4Task(condition,
                        pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-selectTasks",
                    JSONObject.toJSONString(buyerUseTimelimitedLogList));
        }
        return buyerUseTimelimitedLogList;
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
    public boolean execute(BuyerUseTimelimitedLog[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        String reverseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_REVERSE);
        String releaseStatus = dictionary.getValueByCode(DictionaryConst.TYPE_BUYER_PROMOTION_STATUS,
                DictionaryConst.OPT_BUYER_PROMOTION_STATUS_RELEASE);
        String messageId = "";
        String seckillLockNo = "";
        String promotionId = "";
        String buyerCode = "";
        String useLogJsonStr = "";
        Integer skuCount = 0;
        long buyerTimelimitedCount = 0L;
        BuyerUseTimelimitedLog redisUseLog = null;
        try {
            if (tasks != null && tasks.length > 0) {
                for (BuyerUseTimelimitedLog useTimelimitedLog : tasks) {
                    seckillLockNo = useTimelimitedLog.getSeckillLockNo();
                    promotionId = useTimelimitedLog.getPromotionId();
                    buyerCode = useTimelimitedLog.getBuyerCode();
                    messageId = generateMessageId();
                    if (promotionRedisHandle.lockRedisPromotionAction(messageId, seckillLockNo)) {
                        try {
                            useLogJsonStr = marketRedisDB.getHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG,
                                    buyerCode + "&" + promotionId);
                            redisUseLog = JSON.parseObject(useLogJsonStr, BuyerUseTimelimitedLog.class);
                            if (redisUseLog == null) {
                                buyerUseTimelimitedLogDAO.updateTimelimitedReleaseStockStatus(useTimelimitedLog);
                                continue;
                            }
                            if (StringUtils.isEmpty(redisUseLog.getOrderNo()) && seckillLockNo.equals(redisUseLog
                                    .getSeckillLockNo()) && reverseStatus.equals(redisUseLog.getUseType())) {
                                skuCount = redisUseLog.getUsedCount();
                                marketRedisDB.incrHashBy(RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId,
                                        RedisConst.REDIS_TIMELIMITED_REAL_REMAIN_COUNT, skuCount);
                                marketRedisDB.incrHashBy(RedisConst.REDIS_TIMELIMITED_RESULT + "_" + promotionId,
                                        RedisConst.REDIS_TIMELIMITED_SHOW_REMAIN_COUNT, skuCount);
                                buyerTimelimitedCount =
                                        marketRedisDB.incrHashBy(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT,
                                                buyerCode + "&" + promotionId,
                                                skuCount * -1);
                                if (buyerTimelimitedCount < 0) {
                                    marketRedisDB.setHash(RedisConst.REDIS_BUYER_TIMELIMITED_COUNT,
                                            buyerCode + "&" + promotionId, "0");
                                }
                                redisUseLog.setUseType(releaseStatus);
                                redisUseLog.setModifyTime(new Date());
                                marketRedisDB.delHash(RedisConst.REDIS_BUYER_TIMELIMITED_USELOG,
                                        buyerCode + "&" + promotionId);
                                marketRedisDB.tailPush(RedisConst.REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG,
                                        JSON.toJSONString(redisUseLog));
                            }
                            buyerUseTimelimitedLogDAO.updateTimelimitedReleaseStockStatus(useTimelimitedLog);
                        } finally {
                            promotionRedisHandle.unlockRedisPromotionAction(seckillLockNo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "ReleaseTimelimitedLockedNoOrderStockScheduleTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }

    /**
     * ID生成 23位，13位时间戳+6位IP4后两段左补0+4位循环（0001-9999）左补0
     *
     * @return
     */
    private String generateMessageId() {

        String id = "";
        String ip = "";
        try {
            id = String.valueOf(System.currentTimeMillis());
            ip = InetAddress.getLocalHost().getHostAddress();
            String[] sub2 = ip.split("\\.");
            Long seqIndexLong = redisDB.incr(REDIS_MESSAGE_ID_KEY);
            if (seqIndexLong >= 10000L) {
                redisDB.set(REDIS_MESSAGE_ID_KEY, "1");
                seqIndexLong = 1L;
            }
            int maxStrLength = 4;
            String sub3 = String.format("%0" + maxStrLength + "d", seqIndexLong);
            id = id + String.format("%03d", Integer.valueOf(sub2[2])) + String.format("%03d", Integer.valueOf(sub2[3]))
                    + sub3;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return id;
    }
}

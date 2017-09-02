package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.promotion.cpc.biz.dao.BuyerWinningRecordDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务 保存会员中奖记录进DB处理
 */
public class UpdateBuyerWinningRecordScheduleTask implements IScheduleTaskDealMulti<BuyerWinningRecordDMO> {

    protected static transient Logger logger = LoggerFactory.getLogger(UpdateBuyerWinningRecordScheduleTask.class);

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private BuyerWinningRecordDAO buyerWinningRecordDAO;

    @Override
    public Comparator<BuyerWinningRecordDMO> getComparator() {
        return new Comparator<BuyerWinningRecordDMO>() {
            public int compare(BuyerWinningRecordDMO o1, BuyerWinningRecordDMO o2) {
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
    public List<BuyerWinningRecordDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateBuyerWinningRecordScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        List<BuyerWinningRecordDMO> dealTargetList = new ArrayList<BuyerWinningRecordDMO>();
        try {
            if (promotionRedisDB.getLlen(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST) > 0) {
                dealTargetList.add(new BuyerWinningRecordDMO());
            }
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateBuyerWinningRecordScheduleTask-selectTasks",
                    JSONObject.toJSONString(dealTargetList));
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
    public boolean execute(BuyerWinningRecordDMO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateBuyerWinningRecordScheduleTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        BuyerWinningRecordDMO dealTargetInfo = null;
        int addCount = 0;
        String useLogStr = "";
        try {
            while (promotionRedisDB.getLlen(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST) > 0) {
                useLogStr = promotionRedisDB.headPop(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST);
                dealTargetInfo = JSON.parseObject(useLogStr, BuyerWinningRecordDMO.class);
                if (dealTargetInfo == null) {
                    continue;
                }
                buyerWinningRecordDAO.addBuyerWinningRecord(dealTargetInfo);
                addCount++;
            }
        } finally {
            logger.info("\n 方法:[{}],插入件数:[{}]", "UpdateBuyerWinningRecordScheduleTask-execute", addCount);
        }
        return true;
    }
}

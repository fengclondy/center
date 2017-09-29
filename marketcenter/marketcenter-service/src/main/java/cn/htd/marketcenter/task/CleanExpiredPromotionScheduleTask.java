package cn.htd.marketcenter.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.DateUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对于过期7天的优惠券活动，清除Redis中过期优惠券活动相关信息
 */
public class CleanExpiredPromotionScheduleTask implements IScheduleTaskDealMulti<PromotionInfoDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(CleanExpiredPromotionScheduleTask.class);

    /**
     * 清除过期优惠券的过期天数
     */
    private static final String EXPIRE_PROMOTION_INTERVAL = "remove.redis.promotion.interval";

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Override
    public Comparator<PromotionInfoDTO> getComparator() {
        return new Comparator<PromotionInfoDTO>() {
            public int compare(PromotionInfoDTO o1, PromotionInfoDTO o2) {
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
    public List<PromotionInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        PromotionInfoDTO condition = new PromotionInfoDTO();
        Pager<PromotionInfoDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<PromotionInfoDTO> couponInfoDTOList = null;
        if (eachFetchDataNum > 0) {
            pager = new Pager<PromotionInfoDTO>();
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
                condition.setPromotionType(taskParameter);
                couponInfoDTOList = promotionInfoDAO.queryNeedCleanRedisPromotion4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                    JSONObject.toJSONString(couponInfoDTOList));
        }
        return couponInfoDTOList;
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
    public boolean execute(PromotionInfoDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "CleanExpiredPromotionScheduleTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        List<DictionaryInfo> promotionTypeList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_PROMOTION_TYPE);
        Map<String, String> promotionTypeMap = new HashMap<String, String>();
        int expirePromotionInterval = Integer.parseInt(SysProperties.getProperty(EXPIRE_PROMOTION_INTERVAL));
        Date expireDt = DateUtils.getSpecifiedDay(new Date(), -1 * expirePromotionInterval);
        String promotionId = "";
        String promotionType = "";
        try {
            if (tasks != null && tasks.length > 0) {
                for (DictionaryInfo dictionaryInfo : promotionTypeList) {
                    promotionTypeMap.put(dictionaryInfo.getValue(), dictionaryInfo.getCode());
                }
                for (PromotionInfoDTO promotionInfoDTO : tasks) {
                    promotionId = promotionInfoDTO.getPromotionId();
                    promotionType = promotionInfoDTO.getPromotionType();
                    if (expireDt.compareTo(promotionInfoDTO.getInvalidTime()) > 0) {
                        if (promotionTypeMap.containsKey(promotionType) && DictionaryConst.OPT_PROMOTION_TYPE_COUPON
                                .equals(promotionTypeMap.get(promotionType))) {
                            marketRedisDB.del(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId);
                            marketRedisDB.delHash(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId);
                            marketRedisDB.delHash(RedisConst.REDIS_COUPON_VALID, promotionId);
                            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170929 start -----
                            if (!StringUtils.isEmpty(promotionInfoDTO.getB2cActivityCode())) {
                                marketRedisDB.delHash(RedisConst.REDIS_COUPON_TRIGGER,
                                        promotionInfoDTO.getB2cActivityCode());
                            }
                            //----- add by jiangkun for 2017活动需求商城无敌券 on 20170929 end -----
                        }
                        promotionInfoDAO.updateCleanedRedisPromotionStatus(promotionInfoDTO);
                    }
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "CleanExpiredPromotionScheduleTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanExpiredPromotionScheduleTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }
}

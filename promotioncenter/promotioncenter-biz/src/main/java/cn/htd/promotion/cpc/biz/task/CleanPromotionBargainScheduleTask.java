
package cn.htd.promotion.cpc.biz.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dto.DictionaryInfo;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

/**
 * 对于过期砍价活动，清除Redis中过期砍价活动相关信息
 */
public class CleanPromotionBargainScheduleTask implements IScheduleTaskDealMulti<PromotionInfoDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(CleanPromotionBargainScheduleTask.class);

    /**
     * 清除过期优惠券的过期天数
     */
//    private static final String EXPIRE_PROMOTION_INTERVAL = "remove.redis.promotion.interval";

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionRedisDB promotionRedisDB;

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
        List<PromotionInfoDTO> promotionInfoDTOList = null;
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
                promotionInfoDTOList = promotionInfoDAO.queryNeedCleanRedisPromotion4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "CleanExpiredPromotionScheduleTask-selectTasks",
                    JSONObject.toJSONString(promotionInfoDTOList));
        }
        return promotionInfoDTOList;
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
//        int expirePromotionInterval = Integer.parseInt(SysProperties.getProperty(EXPIRE_PROMOTION_INTERVAL));
//        Date expireDt = DateUtil.getSpecifiedDay(new Date(), -1 * expirePromotionInterval);
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
                    Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
                    cal.setTime(promotionInfoDTO.getInvalidTime());
            		cal.add(Calendar.DAY_OF_MONTH, +7);//取当前日期的后七天.   
                    if (Calendar.getInstance().getTime().compareTo(cal.getTime()) > 0) {
                        if (promotionTypeMap.containsKey(promotionType) && DictionaryConst.OPT_PROMOTION_TYPE_BARGAIN
                                .equals(promotionTypeMap.get(promotionType))) {
                        	promotionRedisDB.delHash(RedisConst.REDIS_BARGAIN, promotionId);
                			promotionRedisDB.delHash(RedisConst.REDIS_BARGAIN_VALID,promotionId);
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

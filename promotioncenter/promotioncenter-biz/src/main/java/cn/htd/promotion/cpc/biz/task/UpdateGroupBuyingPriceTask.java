package cn.htd.promotion.cpc.biz.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingInfoDAO;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.constants.GroupbuyingConstants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * 定时将真实团购价格更新到库表中
 * Created by tangjiayong on 2017/11/11.
 */
public class UpdateGroupBuyingPriceTask implements IScheduleTaskDealMulti<GroupbuyingInfoResDTO> {

    protected static transient Logger logger = LoggerFactory.getLogger(UpdateGroupBuyingPriceTask.class);

    @Resource
    private GroupbuyingInfoDAO groupbuyingInfoDAO;

    @Resource
    private GroupbuyingService groupbuyingService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    
    @Override
    public Comparator<GroupbuyingInfoResDTO> getComparator() {
        return new Comparator<GroupbuyingInfoResDTO>() {
            public int compare(GroupbuyingInfoResDTO o1, GroupbuyingInfoResDTO o2) {
                Long id1 = o1.getGroupbuyingId();
                Long id2 = o2.getGroupbuyingId();
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
    public List<GroupbuyingInfoResDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                                   List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateGroupBuyingPriceTask-selectTasks",
                "taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
                JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
        GroupbuyingInfoCmplReqDTO condition = new GroupbuyingInfoCmplReqDTO();
        Pager<GroupbuyingInfoCmplReqDTO> pager = null;
        List<String> taskIdList = new ArrayList<String>();
        List<GroupbuyingInfoResDTO> groupbuyingDTOList = null;
        if (eachFetchDataNum > 0) {
            pager = new Pager<GroupbuyingInfoCmplReqDTO>();
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
                groupbuyingDTOList = groupbuyingInfoDAO.queryNeedUpdateGroupbuying4Task(condition, pager);
            }
        } catch (Exception e) {
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateGroupBuyingPriceTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateGroupBuyingPriceTask-selectTasks",
                    JSONObject.toJSONString(groupbuyingDTOList));
        }
        return groupbuyingDTOList;
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
    public boolean execute(GroupbuyingInfoResDTO[] tasks, String ownSign) throws Exception {
        logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateGroupBuyingPriceTask-execute",
                JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
        boolean result = true;
        try {
            if (tasks != null && tasks.length > 0) {
                for (GroupbuyingInfoResDTO dto : tasks) {
                    String promotionId =dto.getPromotionId();
                    //计算真实价格
                    Map<String, String> resultMap = groupbuyingService.getGBActorCountAndPriceByPromotionId(promotionId,null);
                    String realGroupbuyingPrice = resultMap.get(GroupbuyingConstants.GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE_KEY);
                    String realActorCount = resultMap.get(GroupbuyingConstants.GROUPBUYINGINFO_REAL_ACTOR_COUNT_KEY);
                    //保存真实价格
                    if (!StringUtils.isEmpty(realGroupbuyingPrice) && !StringUtils.isEmpty(realActorCount)){
                    	// 1.如果Redis执行失败，数据库不执行；2.如果Redis执行成功，数据库执行失败，下次还会进行1操作
                    	
                    	boolean redisFlag = true;
                    	try {
                      	    String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + promotionId;
    			         	// redis设置真实参团人数
    			      	    stringRedisTemplate.opsForHash().put(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_ACTOR_COUNT, realActorCount);
    			        	// redis设置真实拼团价
    			      	    stringRedisTemplate.opsForHash().put(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE, realGroupbuyingPrice);
                        	
						} catch (Exception e) {
							 redisFlag = false;
							 logger.error("UpdateGroupBuyingPriceTask-execute-promotionId:"+promotionId+",redis设置异常！",e.toString());
						}
			    
                    	if(redisFlag){//redis设置成功
                            GroupbuyingInfoReqDTO groupbuyingInfoReqDTO = new GroupbuyingInfoReqDTO();
                            groupbuyingInfoReqDTO.setPromotionId(promotionId);
                            groupbuyingInfoReqDTO.setRealActorCount(Integer.valueOf(realActorCount));// 真实参团人数
                            groupbuyingInfoReqDTO.setRealGroupbuyingPrice(new BigDecimal(realGroupbuyingPrice));// 真实拼团价
                            groupbuyingInfoDAO.updateGBActorCountAndPrice(groupbuyingInfoReqDTO);
                    	}

                    }else{
                        logger.error("UpdateGroupBuyingPriceTask-execute-promotionId:"+promotionId+",realGroupbuyingPrice:"+realGroupbuyingPrice+",realActorCount："+realActorCount);
                    }
                }
            }
        } catch (Exception e) {
            result = false;
            logger.error("\n 方法:[{}],异常:[{}]", "UpdateGroupBuyingPriceTask-execute",
                    ExceptionUtils.getStackTraceAsString(e));
        } finally {
            logger.info("\n 方法:[{}],出参:[{}]", "UpdateGroupBuyingPriceTask-execute",
                    JSONObject.toJSONString(result));
        }
        return result;
    }



}

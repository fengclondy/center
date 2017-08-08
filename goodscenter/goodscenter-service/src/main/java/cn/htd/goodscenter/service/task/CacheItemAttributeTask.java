package cn.htd.goodscenter.service.task;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemAttributeDAO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * 缓存属性到redis
 */
public class CacheItemAttributeTask implements IScheduleTaskDealMulti<ItemAttrDTO> {

    @Autowired
    private RedisDB redisDB;

    @Resource
    private ItemAttributeDAO itemAttributeDAO;

    private static final Logger logger = LoggerFactory.getLogger(CacheItemAttributeTask.class);
    @Override
    public boolean execute(ItemAttrDTO[] itemAttrDTOs, String s) throws Exception {
        try {
            for (ItemAttrDTO itemAttrDTO : itemAttrDTOs) {
                String key = Constants.REDIS_KEY_PREFIX_ATTRIBUTE + String.valueOf(itemAttrDTO.getId());
                String value =  itemAttrDTO.getName();
                redisDB.setAndExpire(key, value, Constants.CACHE_EXPIRE);
                logger.info("执行缓存的属性任务【CacheItemAttributeTask】, KEY : {}, VALUE : {}", key, value);
                logger.info("结果：{}", redisDB.get(key));
            }
        } catch (Exception e) {
            logger.error("执行缓存的属性任务【CacheItemAttributeTask】出错, 错误信息:", e);
            return false;
        }
        return true;
    }

    @Override
    public List<ItemAttrDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("查询需要缓存的属性任务【CacheItemAttributeTask】开始");
        List<ItemAttrDTO> itemAttrDTOList = new ArrayList();
        try{
            if (taskItemList != null && taskItemList.size() > 0) {
                itemAttrDTOList = this.itemAttributeDAO.selectItemAttributeListTask(this.getTaskParam(taskQueueNum, taskItemList));
            }
        } catch (Exception e){
            logger.error("查询需要缓存的属性任务【CacheItemAttributeTask】出错, 错误信息", e);
        } finally {
            logger.info("查询需要缓存的属性任务【CacheItemAttributeTask】结束");
        }
        return itemAttrDTOList;
    }

    @Override
    public Comparator<ItemAttrDTO> getComparator() {
        return new Comparator<ItemAttrDTO>() {
            public int compare(ItemAttrDTO o1, ItemAttrDTO o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    private Map<String, Object> getTaskParam(int taskQueueNum, List<TaskItemDefine> taskItemList) {
        Map<String, Object> paramMap = new HashMap<>();
        List<String> taskIdList = new ArrayList();
        for (TaskItemDefine taskItem : taskItemList) {
            taskIdList.add(taskItem.getTaskItemId());
        }
        paramMap.put("taskQueueNum", taskQueueNum);
        paramMap.put("taskIdList", taskIdList);
        return paramMap;
    }
}

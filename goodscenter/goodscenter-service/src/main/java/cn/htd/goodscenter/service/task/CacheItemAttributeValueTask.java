package cn.htd.goodscenter.service.task;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemAttributeValueDAO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * 缓存属性值
 */
public class CacheItemAttributeValueTask implements IScheduleTaskDealMulti<ItemAttrValueDTO> {

    @Autowired
    private RedisDB redisDB;

    @Resource
    private ItemAttributeValueDAO itemAttributeValueDAO;

    private static final Logger logger = LoggerFactory.getLogger(CacheItemAttributeValueTask.class);

    @Override
    public boolean execute(ItemAttrValueDTO[] itemAttrValueDTOs, String s) throws Exception {
        try {
            for (ItemAttrValueDTO itemAttrValueDTO : itemAttrValueDTOs) {
                String key = Constants.REDIS_KEY_PREFIX_ATTRIBUTE_VALUE + String.valueOf(itemAttrValueDTO.getId());
                String value = itemAttrValueDTO.getName();
                redisDB.setAndExpire(key, value, Constants.CACHE_EXPIRE);
                logger.info("执行缓存的属性值任务【CacheItemAttributeValueTask】, KEY : {}, VALUE : {}", key, value);
            }
        } catch (Exception e) {
            logger.error("执行缓存的属性值任务【CacheItemAttributeValueTask】出错 :", e);
            return false;
        }
        return true;
    }

    @Override
    public List<ItemAttrValueDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("查询需要缓存的属性值任务【CacheItemAttributeValueTask】开始");
        List<ItemAttrValueDTO> itemAttrValueDTOList = new ArrayList();
        try{
            if (taskItemList != null && taskItemList.size() > 0) {
                itemAttrValueDTOList = this.itemAttributeValueDAO.selectItemAttributeValueListTask(this.getTaskParam(taskQueueNum, taskItemList));
            }
        } catch (Exception e){
            logger.error("查询需要缓存的属性值任务【CacheItemAttributeValueTask】出错 :", e);
        } finally {
            logger.info("查询需要缓存的属性值任务【CacheItemAttributeValueTask】结束");
        }
        return itemAttrValueDTOList;
    }

    @Override
    public Comparator<ItemAttrValueDTO> getComparator() {
        return new Comparator<ItemAttrValueDTO>() {
            public int compare(ItemAttrValueDTO o1, ItemAttrValueDTO o2) {
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

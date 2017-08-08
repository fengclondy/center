package cn.htd.goodscenter.service.task;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.domain.ItemBrand;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * 缓存商品品牌任务
 * 将品牌信息缓存到REDIS中
 * @author chenkang
 */
public class CacheItemBrandTask implements IScheduleTaskDealMulti<ItemBrand> {

    @Autowired
    private RedisDB redisDB;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    private static final Logger logger = LoggerFactory.getLogger(CacheItemBrandTask.class);

    @Override
    public boolean execute(ItemBrand[] itemBrands, String s) throws Exception {
        try {
            for (ItemBrand itemBrand : itemBrands) {
                String key = Constants.REDIS_KEY_PREFIX_BRAND + String.valueOf(itemBrand.getBrandId());
                String value = itemBrand.getBrandName();
                redisDB.setAndExpire(key, value, Constants.CACHE_EXPIRE);
                logger.info("执行缓存的品牌任务【CacheItemBrandTask】, KEY : {}, VALUE : {}", key, value);
            }
        } catch (Exception e) {
            logger.error("执行缓存的品牌任务【CacheItemBrandTask】出错, 错误信息 :", e);
            return false;
        }
        return true;
    }

    @Override
    public List<ItemBrand> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("查询需要缓存的品牌任务【CacheItemBrandTask】开始");
        List<ItemBrand> itemBrandList = new ArrayList();
        try{
            if (taskItemList != null && taskItemList.size() > 0) {
                itemBrandList = itemBrandDAO.selectBrandList(this.getTaskParam(taskQueueNum, taskItemList));
            }
        } catch (Exception e){
            logger.error("查询需要缓存的品牌任务【CacheItemBrandTask】出错, 错误信息 :", e);
        } finally {
            logger.info("查询需要缓存的品牌任务【CacheItemBrandTask】结束");
        }
        return itemBrandList;
    }

    @Override
    public Comparator<ItemBrand> getComparator() {
        return new Comparator<ItemBrand>() {
            public int compare(ItemBrand o1, ItemBrand o2) {
                Long id1 = o1.getBrandId();
                Long id2 = o2.getBrandId();
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

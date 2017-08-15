package cn.htd.goodscenter.service.task;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.PreSaleProductPushMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.PreSaleProductPush;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时扫描商品表，拉取预售商品数据到预售商品推送表
 */
public class PreSaleProductQueryTask implements IScheduleTaskDealMulti<Item> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PreSaleProductQueryTask.class);

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private PreSaleProductPushMapper preSaleProductPushMapper;

    @Autowired
    private RedisDB redisDB;

    private SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将预售数据保存到预售数据推送表
     * @param items
     * @param s
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(Item[] items, String s) throws Exception {
        return true;
    }

    /**
     * 查询商品表，获取预售数据
     * @param taskParameter
     * @param ownSign
     * @param taskQueueNum
     * @param taskItemList
     * @param eachFetchDataNum
     * @return
     * @throws Exception
     */
    @Override
    public List<Item> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("查询预售商品数据【PreSaleProductQueryTask】任务开始");
        List<Item> itemList = new ArrayList();
        try{
            if (taskItemList != null && taskItemList.size() > 0) {
                String lastSyscTimeStr = this.redisDB.get(Constants.LAST_SYSC_PRE_SALE_PRODUCT_TIME); // 从REDIS获取最近同步时间戳 格式：【yyyy-MM-dd HH:mm:ss】
                if (StringUtils.isEmpty(lastSyscTimeStr)) {
                    lastSyscTimeStr = "2017-05-03 00:00:00"; // 默认开始时间
                }
                Date lastSyscTime = sp.parse(lastSyscTimeStr);
                Date now = new Date();
                Map map = this.getTaskParam(taskQueueNum, taskItemList);
                map.put("lastSyscTime",lastSyscTime);
                itemList = this.itemMybatisDAO.queryPreSaleItemList(map);
                for (Item item : itemList) {
                    Date date = new Date();
                    PreSaleProductPush preSaleProductPush = new PreSaleProductPush();
                    preSaleProductPush.setItemId(item.getItemId());
                    preSaleProductPush.setPushStatus(0);
                    preSaleProductPush.setPushVersion(1);
                    preSaleProductPush.setCreateId(Constants.SYSTEM_CREATE_ID);
                    preSaleProductPush.setCreateName(Constants.SYSTEM_CREATE_NAME);
                    preSaleProductPush.setCreateTime(date);
                    preSaleProductPush.setModifyId(Constants.SYSTEM_CREATE_ID);
                    preSaleProductPush.setModifyName(Constants.SYSTEM_CREATE_NAME);
                    preSaleProductPush.setModifyTime(date);
                    this.preSaleProductPushMapper.insert(preSaleProductPush);
                }
                this.redisDB.set(Constants.LAST_SYSC_PRE_SALE_PRODUCT_TIME, sp.format(now));
            }
        } catch (Exception e){
            logger.error("查询预售商品数据【PreSaleProductQueryTask】出错, 错误信息", e);
        } finally {

            logger.info("查询预售商品数据【PreSaleProductQueryTask】任务结束");
        }
        return itemList;
    }

    @Override
    public Comparator<Item> getComparator() {
        return new Comparator<Item>() {
            public int compare(Item o1, Item o2) {
                Long id1 = o1.getItemId();
                Long id2 = o2.getItemId();
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

package cn.htd.goodscenter.service.task;

import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.middleware.common.message.erp.BrandMessage;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * 商品品牌下行补偿任务
 *  - 任务针对品牌下行erp失败的数据
 * @author chenkang
 * @date 2017-01-04
 */
public class ItemBrandDownErpSupplyTask implements IScheduleTaskDealMulti<ItemBrand> {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    private static final Logger logger = LoggerFactory.getLogger(ItemBrandDownErpSupplyTask.class);

    /**
     * 执行任务
     * @param itemBrands
     * @param ownSign
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(ItemBrand[] itemBrands, String ownSign) throws Exception {
        logger.info("ItemBrandDownErpSupplyTask execute Entrance, itemBrands : {}, ownSign : {}", JSONObject.toJSONString(itemBrands), JSONObject.toJSONString(ownSign));
        boolean result = true;
        try {
            if (itemBrands != null && itemBrands.length > 0) {
                for (ItemBrand itemBrand : itemBrands) {
                    // 更新为下行中
                    ItemBrand brandUpdate = new ItemBrand();
                    brandUpdate.setBrandId(itemBrand.getBrandId());
                    brandUpdate.setErpStatus(ItemErpStatusEnum.DOWNING.getCode());
                    brandUpdate.setErpDownTime(new Date());
                    brandUpdate.setModifyId(Constants.SYSTEM_CREATE_ID);
                    brandUpdate.setModifyName(Constants.SYSTEM_CREATE_NAME);
                    brandUpdate.setModifyTime(new Date());
                    this.itemBrandDAO.updateForTask(brandUpdate);
                    // 下行
                    BrandMessage brandMessage = new BrandMessage();
                    brandMessage.setBrandCode(String.valueOf(itemBrand.getBrandId()));
                    brandMessage.setBrandName(itemBrand.getBrandName());
                    brandMessage.setIsUpdateFlag(0); // TODO :是否需要传递
                    brandMessage.setValidTags(itemBrand.getBrandStatus()); // 1:有效  0:无效
                    MQSendUtil mqSendUtil = new MQSendUtil();
                    mqSendUtil.setAmqpTemplate(amqpTemplate);
                    mqSendUtil.sendToMQWithRoutingKey(brandMessage, MQRoutingKeyConstant.BRAND_DOWN_ERP_ROUTING_KEY);
                }
            }
        }catch (Exception e) {
            logger.error("ItemBrandDownErpSupplyTask execute error", e);
            result = false;
        }
        return result;
    }

    /**
     * 选择任务数据。
     * @param taskParameter 任务参数
     * @param ownSign 当前环境名称
     * @param taskQueueNum 当前任务类型的任务队列数量
     * @param taskItemList 当前调度服务器，分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<ItemBrand> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("ItemBrandDownErpSupplyTask selectTasks Entrance, taskParameter : {}, ownSign : {}, taskQueueNum : {}, taskItemList : {}," +
                "eachFetchDataNum : {}", new Object[]{JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskParameter),
                JSONObject.toJSONString(taskQueueNum), JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum)});
        List<ItemBrand> itemBrandList = new ArrayList<ItemBrand>();
        try{
            List<String> taskIdList = new ArrayList<String>();
            Map<String, Object> paramMap = new HashMap<>();
            if (taskItemList != null && taskItemList.size() > 0) {
                for (TaskItemDefine taskItem : taskItemList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                // erp下行失败的数据
                paramMap.put("erpStatus", ItemErpStatusEnum.FAIL.getCode());
                // 根据id和队列数取摩，在taskIdList中则被该任务查询并执行
                paramMap.put("taskQueueNum", taskQueueNum);
                paramMap.put("taskIdList", taskIdList);
                // 默认查询数量
                paramMap.put("rows", eachFetchDataNum > 0 ? eachFetchDataNum : 1000);
                itemBrandList = itemBrandDAO.selectErpFailBrandList(paramMap);
            }
        } catch (Exception e){
            logger.error("ItemBrandDownErpSupplyTask selectTasks error:", e);
        }
        return itemBrandList;
    }

    @Override
    public Comparator getComparator() {
        return new Comparator<ItemBrand>() {
            public int compare(ItemBrand o1, ItemBrand o2) {
                Long id1 = o1.getBrandId();
                Long id2 = o2.getBrandId();
                return id1.compareTo(id2);
            }
        };
    }
}

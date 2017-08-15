package cn.htd.goodscenter.service.task;

import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.dto.presale.PreSaleProductPictrueDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProductPushDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 单节点-单线程-单批次推送
 * 定时扫描预售数据推送表，将符合条件的商品推送MQ
 */
public class PreSaleProductPushTask implements IScheduleTaskDealMulti<PreSaleProductPush> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(PreSaleProductPushTask.class);

    @Autowired
    private PreSaleProductPushMapper preSaleProductPushMapper;

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private ItemPictureDAO itemPictureDAO;

    @Autowired
    private ItemSkuDAO itemSkuDAO;

    @Autowired
    private ItemDescribeDAO itemDescribeDAO;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @Resource
    private ItemBrandDAO itemBrandDAO;

    @Autowired
    private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

    @Autowired
    private RedisDB redisDB;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public boolean execute(PreSaleProductPush[] preSaleProductPushs, String s) throws Exception {
        for (PreSaleProductPush preSaleProductPush : preSaleProductPushs) {
            try {
                MQSendUtil mqSendUtil = new MQSendUtil();
                mqSendUtil.setAmqpTemplate(amqpTemplate);
                mqSendUtil.sendToMQWithRoutingKey(getPreSaleProductPushDTO(preSaleProductPush.getItemId()), MQRoutingKeyConstant.BRAND_DOWN_ERP_ROUTING_KEY);
            } catch (Exception e) {
                // TODO : 推送失败
            }

        }
        return true;
    }

    @Override
    public List<PreSaleProductPush> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("查询待推送的预售商品数据【PreSaleProductPushTask】任务开始");
        List<PreSaleProductPush> preSaleProductPushList = new ArrayList();
        try {
            if (taskItemList != null && taskItemList.size() > 0) {
                preSaleProductPushList = this.preSaleProductPushMapper.selectbySchedule(this.getTaskParam(taskQueueNum, taskItemList));
            }
        } catch (Exception e){
            logger.error("查询待推送的预售商品数据【PreSaleProductPushTask】出错, 错误信息", e);
        } finally {
            logger.info("查询待推送的预售商品数据【PreSaleProductPushTask】任务结束");
        }
        return preSaleProductPushList;
    }

    @Override
    public Comparator<PreSaleProductPush> getComparator() {
        return new Comparator<PreSaleProductPush>() {
            public int compare(PreSaleProductPush o1, PreSaleProductPush o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }


    /**
     *
     * @param itemId
     * @return
     */
    private PreSaleProductPushDTO getPreSaleProductPushDTO(Long itemId) {
        PreSaleProductPushDTO preSaleProductPushDTO = new PreSaleProductPushDTO();
        Item item = this.itemMybatisDAO.queryItemByPk(itemId);
        List<ItemSku> itemSkuList = this.itemSkuDAO.queryByItemId(itemId);
        ItemDescribe itemDescribe = this.itemDescribeDAO.getDescByItemId(itemId);
        List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(itemId);
        preSaleProductPushDTO.setSpxxname(item.getItemName());
        preSaleProductPushDTO.setSpxxnmno(item.getErpCode());
        preSaleProductPushDTO.setIsPreSell(item.isPreSale() ? 1 : 0);
        /** 设置品牌品类 **/
        Long brandId = item.getBrand();
        Long thirdCategoryId = item.getCid();
        this.setCategoryAndBrandInfo(preSaleProductPushDTO, thirdCategoryId, brandId);
        /** 设置图片 **/
        List<PreSaleProductPictrueDTO> preSaleProductPictrueDTOs = new ArrayList<>();
        for (ItemPicture itemPicture : itemPictureList) {
            PreSaleProductPictrueDTO preSaleProductPictrueDTO = new PreSaleProductPictrueDTO();
            preSaleProductPictrueDTO.setUrl(itemPicture.getPictureUrl());
            preSaleProductPictrueDTO.setSort(String.valueOf(itemPicture.getSortNumber()));
            preSaleProductPictrueDTO.setImageType(itemPicture.getIsFirst() == 1 ? "PRIMARY" : "");
            preSaleProductPictrueDTOs.add(preSaleProductPictrueDTO);
        }
        preSaleProductPushDTO.setPreSaleProductPictrueDTOs(preSaleProductPictrueDTOs);
        return preSaleProductPushDTO;
    }

    private void setCategoryAndBrandInfo(PreSaleProductPushDTO preSaleProductPushDTO, Long cid, Long brandId) {
        preSaleProductPushDTO.setBrandCode(String.valueOf(brandId));
        /** 品类信息 **/
        ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(cid, ">>");
        if (executeResultCategory.isSuccess()) {
            Map<String, Object> categoryMap = executeResultCategory.getResult();
            preSaleProductPushDTO.setCategory1(String.valueOf(categoryMap.get("firstCategoryId")));
            preSaleProductPushDTO.setCategory2(String.valueOf(categoryMap.get("secondCategoryId")));
            preSaleProductPushDTO.setCategory3(String.valueOf(categoryMap.get("thirdCategoryId")));
            preSaleProductPushDTO.setCategory1Name(String.valueOf(categoryMap.get("firstCategoryName")));
            preSaleProductPushDTO.setCategory2Name(String.valueOf(categoryMap.get("secondCategoryName")));
            preSaleProductPushDTO.setCategory3Name(String.valueOf(categoryMap.get("thirdCategoryName")));
        }
        /** 品牌信息 **/
        String brandName = null;
        try {
            // 优先从REDIS获取品牌
            String key = Constants.REDIS_KEY_PREFIX_BRAND + String.valueOf(brandId);
            brandName = redisDB.get(key);
        } catch (Exception e) {
            logger.error("从REDIS获取品牌出错, 错误信息 :", e);
        }
        if (StringUtils.isEmpty(brandName)) {
            // 从DB获取
            ItemBrand itemBrand = this.itemBrandDAO.queryById(brandId);
            if (itemBrand != null) {
                preSaleProductPushDTO.setBrandName(itemBrand.getBrandName());
            }
        } else {
            preSaleProductPushDTO.setBrandName(brandName);
        }
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

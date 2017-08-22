package cn.htd.goodscenter.service.task;

import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.AddressUtils;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.dto.presale.PreSaleProductAttributeDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProductPictureDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProductPushDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProductSaleAreaDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.pricecenter.dto.HzgPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import com.alibaba.fastjson.JSON;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.concurrent.BlockingQueue;

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

    @Autowired
    private ItemBrandDAO itemBrandDAO;

    @Autowired
    private MemberBaseInfoService memberBaseInfoService;

    @Autowired
    private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

    @Autowired
    private CategoryAttrDAO categoryAttrDAO;

    @Autowired
    private ItemSalesAreaMapper itemSalesAreaMapper;

    @Autowired
    private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;

    @Autowired
    private ItemSkuPriceService itemSkuPriceService;

    @Autowired
    private RedisDB redisDB;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private AddressUtils addressUtils;

    @Override
    public boolean execute(PreSaleProductPush[] preSaleProductPushs, String s) throws Exception {
        for (PreSaleProductPush preSaleProductPush : preSaleProductPushs) {
            try {
                // 更新为推送中
                int result = this.preSaleProductPushMapper.updateStatus(preSaleProductPush.getId(), Constants.PRE_SALE_ITEM_PUSH_ING, Arrays.asList(new Integer[]{Constants.PRE_SALE_ITEM_PUSH_PRE, Constants.PRE_SALE_ITEM_PUSH_FAIL})); // 待推送和推送失败的可以重新推送
                if (result == 0) {
                    return true;
                }
                MQSendUtil mqSendUtil = new MQSendUtil();
                mqSendUtil.setAmqpTemplate(amqpTemplate);
                PreSaleProductPushDTO preSaleProductPushDTO = getPreSaleProductPushDTO(preSaleProductPush.getItemId());
                preSaleProductPushDTO.setVersion(preSaleProductPush.getPushVersion());
                System.out.println(JSON.toJSONString(preSaleProductPushDTO));
                mqSendUtil.sendToMQWithRoutingKey(preSaleProductPushDTO, MQRoutingKeyConstant.PRE_SALE_PRODUCT_PUSH_ROUTING_KEY);
                // 更新为推送完成
                this.preSaleProductPushMapper.updateStatus(preSaleProductPush.getId(), Constants.PRE_SALE_ITEM_PUSH_SUCCESS, Arrays.asList(new Integer[]{1}));
            } catch (Exception e) {
                logger.error("推送预售商品失败, itemId ：{}", preSaleProductPush.getItemId(), e);
                // 推送失败
                this.preSaleProductPushMapper.updateStatus(preSaleProductPush.getId(), Constants.PRE_SALE_ITEM_PUSH_FAIL, Arrays.asList(new Integer[]{1}));
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
        /** 查询数据 **/
        Item item = this.itemMybatisDAO.queryItemByPk(itemId);
        if (item == null) {
            throw new RuntimeException("item不存在, item_id : " + itemId);
        }
        List<ItemSku> itemSkuList = this.itemSkuDAO.queryByItemId(itemId);
        if (itemSkuList == null || itemSkuList.size() == 0) {
            throw new RuntimeException("sku不存在, item_id : " + itemId);
        }
        ItemDescribe itemDescribe = this.itemDescribeDAO.getDescByItemId(itemId);
        List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(itemId);
        boolean is0801 = false;
        /** 设置seller **/
        Long sellerId = item.getSellerId();
        ExecuteResult<String> sellerCodeResult = this.memberBaseInfoService.getMemberCodeById(sellerId);
        if (sellerCodeResult != null && sellerCodeResult.isSuccess()) {
            String sellerCode = sellerCodeResult.getResult();
            String sellerName;
            String companyCode;
            ExecuteResult<MemberBaseInfoDTO> executeResult = memberBaseInfoService.queryMemberBaseInfoByMemberCode(sellerCode);
            if (executeResult != null && executeResult.isSuccess()) {
                MemberBaseInfoDTO memberBaseInfoDTO = executeResult.getResult();
                sellerName = memberBaseInfoDTO.getCompanyName();
                companyCode = memberBaseInfoDTO.getCompanyCode();
            } else {
                throw new RuntimeException("queryMemberBaseInfoByMemberCode出错， item_id : " + itemId + ", 错误信息 : " + executeResult.getErrorMessages());
            }
            preSaleProductPushDTO.setSellerId(String.valueOf(sellerId));
            preSaleProductPushDTO.setSellerCode(sellerCode);
            preSaleProductPushDTO.setSellerName(sellerName);
            preSaleProductPushDTO.setIsPreSell(item.isPreSale() ? ("0801".equals(companyCode) ? 2 : 3) : 0); //0.非预售，1.是预售，2.总部预售，3.分部预售
            is0801 = "0801".equals(companyCode);
        } else {
            throw new RuntimeException("getMemberCodeById出错， item_id : " + itemId + ", 错误信息 : " + sellerCodeResult.getErrorMessages());
        }
        /** 设置item **/
        preSaleProductPushDTO.setSpxxname(item.getItemName());
        preSaleProductPushDTO.setSpxxnmno(item.getErpCode());
        /** 设置sku **/
        ItemSku itemSku = itemSkuList.get(0);
        preSaleProductPushDTO.setSkuCode(itemSku.getSkuCode());
        /** 设置详情 **/
        preSaleProductPushDTO.setDescribeContent(itemDescribe == null ? "" : itemDescribe.getDescribeContent());
        /** 设置品牌品类 **/
        Long brandId = item.getBrand();
        Long thirdCategoryId = item.getCid();
        this.setCategoryAndBrandInfo(preSaleProductPushDTO, thirdCategoryId, brandId);
        /** 设置图片 **/
        List<PreSaleProductPictureDTO> preSaleProductPictureDTOs = new ArrayList<>();
        for (ItemPicture itemPicture : itemPictureList) {
            PreSaleProductPictureDTO preSaleProductPictureDTO = new PreSaleProductPictureDTO();
            preSaleProductPictureDTO.setUrl(itemPicture.getPictureUrl());
            preSaleProductPictureDTO.setSort(String.valueOf(itemPicture.getSortNumber()));
            preSaleProductPictureDTO.setImageType(itemPicture.getIsFirst() == 1 ? "PRIMARY" : "");
            preSaleProductPictureDTOs.add(preSaleProductPictureDTO);
        }
        preSaleProductPushDTO.setSpjpgs(preSaleProductPictureDTOs);
        /** 设置上下架 **/
        String shelfType = is0801 ? Constants.SHELF_TYPE_IS_AREA : Constants.SHELF_TYPE_IS_BOX;
        ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSku.getSkuId(), shelfType, Constants.IS_VISABLE_TRUE);
        preSaleProductPushDTO.setListStatus(itemSkuPublishInfo != null ? 1 : 2); // 上架状态 1：上架 2：下架
        if (itemSkuPublishInfo != null) {
            preSaleProductPushDTO.setKcnum(itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity());
        }
        /** 设置销售属性 **/
        preSaleProductPushDTO.setItemAttr(this.parseItemAttribute(item.getAttributes()));
        /** 设置销售区域 **/
        preSaleProductPushDTO.setRegion(this.parseSaleArea(itemId, shelfType));
        /** 设置价格 **/
        ExecuteResult<HzgPriceDTO> hzgPriceDTOExecuteResult = this.itemSkuPriceService.queryHzgTerminalPriceByTerminalType(itemSku.getSkuId());
        if (hzgPriceDTOExecuteResult != null && hzgPriceDTOExecuteResult.isSuccess()) {
            HzgPriceDTO hzgPriceDTO = hzgPriceDTOExecuteResult.getResult();
            if (hzgPriceDTO != null) { // TODO : 没有价格是否需要推送
                preSaleProductPushDTO.setRecommendedPrice(hzgPriceDTO.getRetailPrice());
                preSaleProductPushDTO.setMemberPrice(hzgPriceDTO.getSalePrice());
                preSaleProductPushDTO.setVipPrice(hzgPriceDTO.getVipPrice());
            }
        } else {
            throw new RuntimeException("queryHzgTerminalPriceByTerminalType出错, item_id : " + itemId + ", 错误信息 ：" + hzgPriceDTOExecuteResult.getErrorMessages());
        }
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

    private List<PreSaleProductAttributeDTO> parseItemAttribute(String attributes) {
        List<PreSaleProductAttributeDTO> list = new ArrayList();
        try {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(attributes)) {
                if (!attributes.startsWith("{")) {
                    attributes = "{" + attributes;
                }
                if (!attributes.endsWith("}")) {
                    attributes = attributes + "}";
                }
                Map<String, Object> map = JSONObject.fromObject(attributes);
                if (MapUtils.isEmpty(map)) {
                    return list;
                }
                for (String s : map.keySet()) {
                    Long attributeId = Long.valueOf(s);
                    Object attributeValueObj = map.get(s);
                    String attributeName = this.getAttributeName(attributeId);
                    if (org.apache.commons.lang3.StringUtils.isEmpty(attributeName)) {
                        continue;
                    }
                    if (attributeValueObj instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) attributeValueObj;
                        int size = jsonArray.size();
                        for (int i = 0; i < size; i++) {
                            Object attrValueObj = jsonArray.get(i);
                            if(attrValueObj == null || !org.apache.commons.lang3.StringUtils.isNumeric(attrValueObj + "")){
                                logger.error("执行方法【parseItemAttribute】attributeValueId", attrValueObj);
                                continue;
                            }
                            Integer attributeValueId = Integer.valueOf(attrValueObj + "");
                            String attributeValueName = this.getAttributeValueName(attributeValueId);
                            if (!org.apache.commons.lang3.StringUtils.isEmpty(attributeValueName)) {
                                PreSaleProductAttributeDTO preSaleProductAttributeDTO = new PreSaleProductAttributeDTO();
                                preSaleProductAttributeDTO.setAttrName(attributeName);
                                preSaleProductAttributeDTO.setAttrValue(attributeValueName);
                                list.add(preSaleProductAttributeDTO);
                            }
                        }
                    } else {
                        Integer attributeValueId = Integer.valueOf(attributeValueObj + "");
                        String attributeValueName = this.getAttributeValueName(attributeValueId);
                        PreSaleProductAttributeDTO preSaleProductAttributeDTO = new PreSaleProductAttributeDTO();
                        preSaleProductAttributeDTO.setAttrName(attributeName);
                        preSaleProductAttributeDTO.setAttrValue(attributeValueName);
                        list.add(preSaleProductAttributeDTO);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("解析商品销售属性出错, 属性串 : {}, 错误信息 : ", attributes, e);
        }
        return list;
    }

    private List<PreSaleProductSaleAreaDTO> parseSaleArea(Long itemId, String shelfType) {
        List<PreSaleProductSaleAreaDTO> preSaleProductSaleAreaDTOs = new ArrayList<>();
        ItemSalesArea salesAreaPublic = itemSalesAreaMapper.selectByItemId(itemId, shelfType);
        if (null != salesAreaPublic) {
            if (salesAreaPublic.getIsSalesWholeCountry().intValue() == 0) {
                // TODO :详情
                Long salesAreaId = salesAreaPublic.getSalesAreaId();
                List<ItemSalesAreaDetail> salesAreaDetailList = itemSalesAreaDetailMapper.selectAreaDetailsBySalesAreaIdAll(salesAreaId);
                List<String> provinces = new ArrayList<>(); // 省集合
                List<String> citys = new ArrayList<>(); // 市集合
                List<String> countrys = new ArrayList<>(); // 区集合
                for (ItemSalesAreaDetail area : salesAreaDetailList) {
                    if ("1".equals(area.getSalesAreaType())) {
                        if (area.getAreaCode().length() == 2) {
                            provinces.add(area.getAreaCode());
                        }
                    }
                    if ("2".equals(area.getSalesAreaType())) {
                        if (area.getAreaCode().length() == 4) {
                            citys.add(area.getAreaCode());
                        }
                    }
                    if ("3".equals(area.getSalesAreaType())) {
                        if (area.getAreaCode().length() == 6) {
                            countrys.add(area.getAreaCode());
                        }
                    }
                }
                for (String area : countrys) { // 从三级开始循环
                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
                    preSaleProductSaleAreaDTO.setRegionCounty(area);
                    preSaleProductSaleAreaDTO.setRegionCity(area.substring(0, 4));
                    preSaleProductSaleAreaDTO.setRegionProvince(area.substring(0, 2));
                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCounty()) == null) {
                        continue;
                    }
                    preSaleProductSaleAreaDTO.setRegion(
                            addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName()
                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()).getName()
                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCounty()).getName());
                    if (citys.contains(preSaleProductSaleAreaDTO.getRegionCity())) {
                        citys.remove(preSaleProductSaleAreaDTO.getRegionCity());
                    }
                    if (provinces.contains(preSaleProductSaleAreaDTO.getRegionProvince())) {
                        provinces.remove(preSaleProductSaleAreaDTO.getRegionProvince());
                    }
                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
                }
                for (String area : citys) { // 循环没有三级的二级
                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
                    preSaleProductSaleAreaDTO.setRegionCity(area);
                    preSaleProductSaleAreaDTO.setRegionProvince(area.substring(0, 2));
                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()) == null) {
                        continue;
                    }
                    preSaleProductSaleAreaDTO.setRegion(
                            addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName()
                                    + "-" + addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionCity()).getName());
                    if (provinces.contains(preSaleProductSaleAreaDTO.getRegionProvince())) {
                        provinces.remove(preSaleProductSaleAreaDTO.getRegionProvince());
                    }
                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
                }
                for (String area : provinces) { // 循环没有二级的一级
                    PreSaleProductSaleAreaDTO preSaleProductSaleAreaDTO = new PreSaleProductSaleAreaDTO();
                    preSaleProductSaleAreaDTO.setRegionProvince(area);
                    if (addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()) == null) {
                        continue;
                    }
                    preSaleProductSaleAreaDTO.setRegion(addressUtils.getAddressName(preSaleProductSaleAreaDTO.getRegionProvince()).getName());
                    preSaleProductSaleAreaDTOs.add(preSaleProductSaleAreaDTO);
                }
            }
        }
        return preSaleProductSaleAreaDTOs;
    }

    private String getAttributeName(Long attributeId) {
        if (attributeId != null) {
            String attributeName = this.redisDB.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE + attributeId);
            if (org.apache.commons.lang3.StringUtils.isEmpty(attributeName)) {
                return this.categoryAttrDAO.getAttrNameByAttrId(attributeId);
            } else {
                return attributeName;
            }
        } else {
            return null;
        }
    }

    private String getAttributeValueName(Integer attributeValueId) {
        if (attributeValueId != null) {
            String attributeValueName = this.redisDB.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE_VALUE + attributeValueId);
            if (org.apache.commons.lang3.StringUtils.isEmpty(attributeValueName)) {
                return this.categoryAttrDAO.getAttrValueNameByAttrValueId(Long.valueOf(attributeValueId));
            } else {
                return attributeValueName;
            }
        } else {
            return null;
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

    public static void main(String[] args) {
//        BlockingQueue


    }
}

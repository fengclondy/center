package cn.htd.goodscenter.service.impl.stock.handler;

import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.utils.RedissonClientUtil;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoHistoryMapper;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuPublishInfoHistory;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.StockInParamIllegalException;
import cn.htd.goodscenter.service.impl.stock.exception.StockPublishInfoIsNullException;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * SKU可见库存变动抽象类
 * @author chenakng
 * @date 2016/11/23.
 */
public abstract class AbstractSkuStockChangeHandler implements StockChangeAble {

    @Resource
    protected ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

    @Resource
    private ItemSkuPublishInfoHistoryMapper itemSkuPublishInfoHistoryMapper;

    @Resource
    private ItemSkuDAO itemSkuDAO;

    @Resource
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private RedissonClientUtil redissonClientUtil;

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(AbstractSkuStockChangeHandler.class);

    @Override
    public void changeStock(Order4StockChangeDTO order4StockChangeDTO) throws Exception {
        logger.info("changeStock entrance, order4StockChangeDTO : {}", order4StockChangeDTO);
        String orderNo = order4StockChangeDTO.getOrderNo();
        String orderResource = order4StockChangeDTO.getOrderResource();
        String messageId = order4StockChangeDTO.getMessageId();
        if (StringUtils.isEmpty(orderNo)) {
            throw new StockInParamIllegalException("入参校验错误, orderNo为空");
        }
        if (StringUtils.isEmpty(messageId)) {
            throw new StockInParamIllegalException("入参校验错误, messageId为空");
        }
        if (StringUtils.isEmpty(orderResource)) {
            throw new StockInParamIllegalException("入参校验错误, orderResource为空");
        }
        List<Order4StockEntryDTO> orderEntries = order4StockChangeDTO.getOrderEntries();
        // 针对订单行进行库存修改
        for (Order4StockEntryDTO order4StockEntryDTO : orderEntries) {
            // 冗余字段，方便传递参数
            order4StockEntryDTO.setOrderNo(orderNo);
            order4StockEntryDTO.setOrderResource(orderResource);
            order4StockEntryDTO.setMessageId(messageId);
            // lock and change
            this.changeEntryStock(order4StockEntryDTO);
        }
    }

    /**
     * 锁定订单行的商品库存，对库存进行修改
     * @param order4StockEntryDTO 订单行参数
     * @return 修改结果
     */
    @Override
    public void changeEntryStock(Order4StockEntryDTO order4StockEntryDTO) throws Exception {
        logger.info("changeWithLock, orderEntry : {}", order4StockEntryDTO);
        RLock rLock = null;
        try {
            // 校验必传参数
            if (order4StockEntryDTO == null) {
                throw new StockInParamIllegalException("订单行为NULL");
            }
            if (StringUtils.isEmpty(order4StockEntryDTO.getSkuCode())) {
                throw new StockInParamIllegalException("商品SKU编码为空");
            }
            if (order4StockEntryDTO.getQuantity() == null || order4StockEntryDTO.getQuantity() < 0) {
                throw new StockInParamIllegalException("入参校验错误, Quantity非法, Quantity : " + order4StockEntryDTO.getQuantity());
            }
            String skuCode = order4StockEntryDTO.getSkuCode();
            // 查询STOCK_ID
            ItemSkuPublishInfo itemSkuPublishInfo = null;
            try {
                ItemSku itemSku = this.itemSkuDAO.selectItemSkuBySkuCode(skuCode);
                Item item = this.itemMybatisDAO.queryItemByPk(itemSku.getItemId());
                if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(item.getProductChannelCode())) {
                    if (order4StockEntryDTO.getIsBoxFlag() == null) {
                        throw new StockInParamIllegalException("内部供应商商品是否包厢关系必传");
                    }
                    itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectBySkuCodeAndShelfType(skuCode, order4StockEntryDTO.getIsBoxFlag());
                } else if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(item.getProductChannelCode())) { // 外部供应商
                    itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectBySkuCodeAndShelfType(skuCode, 0);
                }
            } catch (Exception e) {
                logger.error("查询库存失败, 错误信息 : ", e);
            }
            if (itemSkuPublishInfo == null) {
                throw new StockPublishInfoIsNullException("操作库存失败-查询库存失败, 商品SKU编码 : " + order4StockEntryDTO.getSkuCode() + "订单编码 : " + order4StockEntryDTO.getOrderNo());
            }
            // STOCK_ID作为竞争资源标志
            Long stockId = itemSkuPublishInfo.getId();
            String lockKey = Constants.REDIS_KEY_PREFIX_STOCK + String.valueOf(stockId); // 竞争资源标志
            RedissonClient redissonClient = redissonClientUtil.getInstance();
            rLock = redissonClient.getLock(lockKey);
            /** 上锁 **/
            rLock.lock();
            // 做业务
            this.doChange(order4StockEntryDTO, stockId);
        } finally {
            /** 释放锁资源 **/
            if (rLock != null) {
                rLock.unlock();
            }
        }
    }

    /**
     * 具体的扣减，锁定，释放，回滚操作。
     * @param order4StockEntryDTO
     * @param stockId
     * @return
     */
    protected abstract void doChange(Order4StockEntryDTO order4StockEntryDTO, Long stockId) throws Exception;

    protected void updateItemSkuPublishInfo(ItemSkuPublishInfo itemSkuPublishInfo) {
        this.itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
    }

    protected void addItemSkuPublishInfoHistory(ItemSkuPublishInfoHistory itemSkuPublishInfoHistory) {
        this.itemSkuPublishInfoHistoryMapper.insert(itemSkuPublishInfoHistory);
    }

    /**
     * 创建库存历史记录
     * @param orderNo
     * @param resource
     * @param quantity
     * @param itemSkuPublishInfo
     */
    protected ItemSkuPublishInfoHistory createItemSkuPublishInfoHistory(String orderNo, String resource, Integer quantity, String updateType, String messageId, ItemSkuPublishInfo itemSkuPublishInfo) {
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = new ItemSkuPublishInfoHistory();
        itemSkuPublishInfoHistory.setOrderNo(orderNo);
        itemSkuPublishInfoHistory.setQuantity(quantity);
        itemSkuPublishInfoHistory.setResource(resource);
        itemSkuPublishInfoHistory.setUpdateType(updateType);
        itemSkuPublishInfoHistory.setSkuId(itemSkuPublishInfo.getSkuId());
        itemSkuPublishInfoHistory.setStockId(itemSkuPublishInfo.getId());
        itemSkuPublishInfoHistory.setReserveQuantity(itemSkuPublishInfo.getReserveQuantity());
        itemSkuPublishInfoHistory.setDisplayQuantity(itemSkuPublishInfo.getDisplayQuantity());
        itemSkuPublishInfoHistory.setCreateId(itemSkuPublishInfo.getCreateId());
        itemSkuPublishInfoHistory.setCreateName(itemSkuPublishInfo.getCreateName());
        itemSkuPublishInfoHistory.setMessageId(messageId);
        return itemSkuPublishInfoHistory;
    }

    protected String formatExceptionMessage(ItemSkuPublishInfo itemSkuPublishInfo, Order4StockEntryDTO order4StockEntryDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("可见库存数: ");
        stringBuilder.append(itemSkuPublishInfo.getDisplayQuantity());
        stringBuilder.append(",锁定库存数: ");
        stringBuilder.append(itemSkuPublishInfo.getReserveQuantity());
        stringBuilder.append(",可卖库存数: ");
        stringBuilder.append(itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity());
        stringBuilder.append(",订单编号: ");
        stringBuilder.append(order4StockEntryDTO.getOrderNo());
        stringBuilder.append(",订单商品数量: ");
        stringBuilder.append(order4StockEntryDTO.getQuantity());
        stringBuilder.append(",商品SKU_CODE: ");
        stringBuilder.append(order4StockEntryDTO.getSkuCode());
        return stringBuilder.toString();
    }

    /**
     *
     * @param orderNo 订单号
     * @param stockId 库存id
     * @param stockTypeEnum 操作类型
     */
    protected boolean idempotentHandle(String orderNo, Long stockId, StockTypeEnum stockTypeEnum) {
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = new ItemSkuPublishInfoHistory();
        itemSkuPublishInfoHistory.setOrderNo(orderNo);
        itemSkuPublishInfoHistory.setStockId(stockId);
        itemSkuPublishInfoHistory.setUpdateType(stockTypeEnum.name());
        List<ItemSkuPublishInfoHistory> itemSkuPublishInfoHistoryList = this.itemSkuPublishInfoHistoryMapper.select(itemSkuPublishInfoHistory);
        // 没有相关记录，不走幂等逻辑
        if (itemSkuPublishInfoHistoryList != null && itemSkuPublishInfoHistoryList.size() > 0) {
            // 幂等逻辑,直接返回成功，记录日志
            logger.info("触发幂等逻辑, 直接返回操作成功, 上次操作记录, itemSkuPublishInfoHistory : {}", JSONArray.fromObject(itemSkuPublishInfoHistoryList));
            return true;
        }
        return false;
    }

    /**
     * 校验修改库存的前置条件
     * @param orderNo 订单号
     * @param stockId 库存id   商品库存
     * @param preCondition 前置条件
     * @return
     */
    protected boolean validateStockChangePreCondition(String orderNo, Long stockId, StockTypeEnum preCondition, Integer quantity) {
        ItemSkuPublishInfoHistory itemSkuPublishInfoHistory = new ItemSkuPublishInfoHistory();
        itemSkuPublishInfoHistory.setOrderNo(orderNo);
        itemSkuPublishInfoHistory.setStockId(stockId);
        // 查询该订单、商品最近库存操作记录
        ItemSkuPublishInfoHistory preStockChangeRecord = this.itemSkuPublishInfoHistoryMapper.selectLastStockRecord(itemSkuPublishInfoHistory);
        // 查看有没有前置操作的相关记录
        if (preStockChangeRecord != null && preCondition.name().equals(preStockChangeRecord.getUpdateType())) {
            if (preStockChangeRecord.getQuantity().equals(quantity)) {
                logger.info("存在库存修改的前置条件,校验通过. pre-stock-change-record: {}", JSONArray.fromObject(preStockChangeRecord));
                return true;
            } else {
                logger.error("之前：{}的数量:{}和目前的数量：{}不一致, 订单号：{}, 库存ID：{}", preStockChangeRecord.getUpdateType(), preStockChangeRecord.getQuantity(), quantity, orderNo,stockId);
            }
        }
        return false;
    }
}

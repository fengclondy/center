package cn.htd.goodscenter.service.listener;

import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.OutItemStatusEnum;
import cn.htd.goodscenter.dto.productplus.ProductPlusProductImportDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusProductSupplyDTO;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.Date;

/**
 * 京东外接商品补充数据-商品导入服务【增量】
 */
public class JdSupplyProductDataImportServiceImpl implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(JdSupplyProductDataImportServiceImpl.class);

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private ItemSkuDAO itemSkuDAO;

    @Autowired
    private JdProductDataImportServiceImpl jdProductDataImportService;

    private static final Long JD_CREATE_ID = 0L;

    private static final String JD_CREATE_NAME = "JD";

    @Transactional
    @Override
    public void onMessage(Message message) {
        logger.info("京东商品增量导入监听开始");
        try {
            String messageBody = new String(message.getBody(),"utf-8");
            this.modifyData(messageBody);
        } catch (Exception e) {
            logger.error("京东商品增量导入失败, 错误信息 : ", e);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            logger.info("京东商品增量导入监听结束");
        }
    }

    private void modifyData(String messageBody) throws Exception {
        logger.info("京东商品增量导入, message : {}", messageBody);
        ProductPlusProductSupplyDTO productPlusProductSupplyDTO = this.parseMessage(messageBody);
        // 校验
        if (productPlusProductSupplyDTO == null) {
            logger.error("productPlusProductSupplyDTO is null");
            return;
        }
        Integer delFlag = productPlusProductSupplyDTO.getDelFlag();
        ProductPlusProductImportDTO productPlusProductImportDTO = productPlusProductSupplyDTO.getProductPlusProductImportDTO();
        if (delFlag == null) {
            logger.error("delFlag is null");
            return;
        }
        if (productPlusProductImportDTO == null) {
            logger.error("productPlusProductImportDTO is null");
            return;
        }
        // 判断业务类型 新增还是删除
        if (delFlag.intValue() == 0) { // 新增
            // 如果之前库里不存在则插入，如果存在则更新
            jdProductDataImportService.importData(productPlusProductImportDTO);
        } else if (delFlag.intValue() == 1) { // 删除 做下架处理
            downShelves(productPlusProductImportDTO);
        }
    }

    /**
     * 下架操作
     * @param productPlusProductImportDTO
     */
    private void downShelves(ProductPlusProductImportDTO productPlusProductImportDTO) {
        logger.info("京东商品增量导入【下架】, productPlusProductImportDTO : {}", productPlusProductImportDTO);
        String outerSkuId = productPlusProductImportDTO.getOuterSkuId();
        // 根据外部skuid查询
        ItemSku itemSku = itemSkuDAO.queryItemSkuByOuterSkuId(outerSkuId);
        if (itemSku == null) {
            logger.error("itemSku is not exist");
        }
        Long itemId = itemSku.getItemId();
        Item itemDb = this.itemMybatisDAO.queryItemByPk(itemId);
        if (itemDb == null) {
            logger.error("item is not exist");
            return;
        }
        Item item = new Item();
        item.setItemId(itemId);
        item.setOuterItemStatus(OutItemStatusEnum.NOT_SHELVES.getCode()); // 外部商品状态是下架
        if (itemDb.getItemStatus().equals(HtdItemStatusEnum.SHELVED.getCode())) { // 如果原来是上架状态，更新为下架。
            item.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode());
        }
        item.setModified(new Date());
        item.setModifyId(JD_CREATE_ID);
        item.setModifyName(JD_CREATE_NAME);
        this.itemMybatisDAO.updateByPrimaryKeySelective(item);
    }

    private ProductPlusProductSupplyDTO parseMessage(String message) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, ProductPlusProductSupplyDTO.class);
    }
}

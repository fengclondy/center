package cn.htd.goodscenter.service.listener;

import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.OutItemStatusEnum;
import cn.htd.goodscenter.dto.productplus.ProductPlusProductStatusSupplyDTO;
import org.apache.commons.lang3.StringUtils;
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
 * 京东外接商品补充数据-外部商品上下架状态【增量】
 */
public class JdSupplyProductStatusServiceImpl implements MessageListener {

    Logger logger = LoggerFactory.getLogger(JdSupplyProductStatusServiceImpl.class);

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private ItemSkuDAO itemSkuDAO;

    private static final Long JD_CREATE_ID = 0L;

    private static final String JD_CREATE_NAME = "JD";

    @Transactional
    @Override
    public void onMessage(Message message) {
        logger.info("京东商品上下架增量监听开始");
        try {
            String messageBody = new String(message.getBody(), "UTF-8");
            this.modifyProductStatus(messageBody);
        } catch (Exception e) {
            logger.error("京东商品上下架增量监听错误, 错误信息 : ", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            logger.info("京东商品上下架增量监听结束");
        }
    }

    private void modifyProductStatus(String message) throws IOException {
        logger.info("京东商品上下架增量处理, : {}", message);
        ProductPlusProductStatusSupplyDTO productPlusProductStatusSupplyDTO = this.parseMessage(message);
        // 校验
        if (productPlusProductStatusSupplyDTO == null) {
            logger.error("productPlusProductStatusSupplyDTO is null");
            return;
        }
        if (StringUtils.isEmpty(productPlusProductStatusSupplyDTO.getOuterItemStatus())) {
            logger.error("outerItemStatus is empty");
            return;
        }
        if (StringUtils.isEmpty(productPlusProductStatusSupplyDTO.getOuterSkuId())) {
            logger.error("outerSkuId is empty");
            return;
        }
        // 根据外部skuid查询
        ItemSku itemSku = itemSkuDAO.queryItemSkuByOuterSkuId(productPlusProductStatusSupplyDTO.getOuterSkuId());
        if (itemSku == null) {
            logger.error("itemSku is not exist");
            return;
        }
        Long itemId = itemSku.getItemId();
        Item itemDb = this.itemMybatisDAO.queryItemByPk(itemId);
        if (itemDb == null) {
            logger.error("item is not exist");
            return;
        }
        // jd状态转换 0: 未上架 1: 已上架 2: 非图书商品表示预上架，图书商品表示前台屏蔽 10: POP商品删除状态 
        String outerItemStatus = null;
        Integer itemStatus = null; // 默认没有值
        if (productPlusProductStatusSupplyDTO.getOuterItemStatus().equals("1")) { // 1: 已上架
            // 更新外部商品状态为上架
            outerItemStatus = OutItemStatusEnum.SHELVED.getCode();
            logger.info("京东商品上下架增量处理, 更新外部商品状态为上架, outerSKuId : {}", productPlusProductStatusSupplyDTO.getOuterSkuId());
        } else if (productPlusProductStatusSupplyDTO.getOuterItemStatus().equals("0")) { // 0: 未上架
            logger.info("京东商品上下架增量处理, 更新外部商品状态为下架, outerSKuId : {}", productPlusProductStatusSupplyDTO.getOuterSkuId());
            // 更新外部商品状态为下架
            outerItemStatus = OutItemStatusEnum.NOT_SHELVES.getCode();
            // 如果原来是上架状态，则更新为下架状态
            if (itemDb.getItemStatus().equals(HtdItemStatusEnum.SHELVED.getCode())) {
                itemStatus = HtdItemStatusEnum.NOT_SHELVES.getCode();
                logger.info("京东商品上下架增量处理, 原商品是上架状态，更新商品状态为下架, outerSKuId : {}", productPlusProductStatusSupplyDTO.getOuterSkuId());
            }
        } else {
            logger.error("outerItemStatus is illegal : {}", productPlusProductStatusSupplyDTO.getOuterItemStatus());
        }
        Item item = new Item();
        item.setItemId(itemId);
        item.setOuterItemStatus(outerItemStatus);
        item.setItemStatus(itemStatus);
        item.setModified(new Date());
        item.setModifyId(JD_CREATE_ID);
        item.setModifyName(JD_CREATE_NAME);
        this.itemMybatisDAO.updateByPrimaryKeySelective(item);
    }

    private ProductPlusProductStatusSupplyDTO parseMessage(String message) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, ProductPlusProductStatusSupplyDTO.class);
    }
}

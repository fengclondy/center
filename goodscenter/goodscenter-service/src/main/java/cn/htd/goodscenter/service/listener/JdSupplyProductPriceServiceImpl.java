package cn.htd.goodscenter.service.listener;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.dto.productplus.ProductPlusPriceSupplyDTO;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  京东外接商品补充数据-价格修改服务
 *  @author chenkang
 */
public class JdSupplyProductPriceServiceImpl implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(JdSupplyProductDataImportServiceImpl.class);

    @Resource
    private ItemSkuPriceService itemSkuPriceService;

    @Autowired
    private ItemSkuDAO itemSkuDAO;

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    private static final Long JD_CREATE_ID = 0L;

    private static final String JD_CREATE_NAME = "JD";

    /**
     * 入口
     * @param message
     */
    @Transactional
    @Override
    public void onMessage(Message message) {
        logger.info("京东商品价格增量监听开始");
        try {
            String messageBody = new String(message.getBody(),"utf-8");
            final ObjectMapper mapper = new ObjectMapper();
            ProductPlusPriceSupplyDTO productPlusPriceSupplyDTO = mapper.readValue(messageBody, ProductPlusPriceSupplyDTO.class);
            this.modifyPrice(productPlusPriceSupplyDTO);
        } catch (Exception e) {
            logger.error("京东商品价格增量监听失败, 解析Message失败:", e);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            logger.info("京东商品价格增量监听结束");
        }
    }

    public void modifyPrice(ProductPlusPriceSupplyDTO productPlusPriceSupplyDTO) throws Exception {
        logger.info("京东商品价格增量监听处理开始, 消息 : {}", JSONObject.fromObject(productPlusPriceSupplyDTO));
        // 校验
        if (productPlusPriceSupplyDTO == null) {
            logger.error("入参为空");
            return;
        }
        if (StringUtils.isEmpty(productPlusPriceSupplyDTO.getOuterSkuId())) {
            logger.error("外部商品编码为空");
            return;
        }
        if (productPlusPriceSupplyDTO.getCostPrice() == null || productPlusPriceSupplyDTO.getCostPrice().floatValue() <= 0) {
            logger.error("京东商品成本价非法 : {}", productPlusPriceSupplyDTO.getCostPrice());
            return;
        }
        if (productPlusPriceSupplyDTO.getSaleLimitedPrice() == null || productPlusPriceSupplyDTO.getSaleLimitedPrice().floatValue() <= 0) {
            logger.error("京东商品销售价非法 : {}", productPlusPriceSupplyDTO.getSaleLimitedPrice());
            return;
        }
        String outerSkuId = productPlusPriceSupplyDTO.getOuterSkuId(); // 外部商品SKU_ID
        ItemSku itemSku = this.itemSkuDAO.queryItemSkuByOuterSkuId(outerSkuId);
        if (itemSku == null) {
            logger.error("queryItemSkuByOuterSkuId查询不到结果: 根据outerSkuId查询不到sku信息, outerSkuId : {}", outerSkuId);
            return;
        }
        ExecuteResult<ItemSkuBasePriceDTO> result = this.itemSkuPriceService.queryItemSkuBasePrice(itemSku.getSkuId());
        if (result != null && result.isSuccess()) {
            ItemSkuBasePriceDTO itemSkuBasePriceDTO = result.getResult();
            if (itemSkuBasePriceDTO != null) {
                BigDecimal costPrice = productPlusPriceSupplyDTO.getCostPrice(); // 外部成本价
                BigDecimal saleLimitedPrice = productPlusPriceSupplyDTO.getSaleLimitedPrice(); // 外部销售价
                BigDecimal priceFloatingRatio = itemSkuBasePriceDTO.getPriceFloatingRatio(); // 价格浮动比例
                BigDecimal retailPriceFloatingRatio = itemSkuBasePriceDTO.getRetailPriceFloatingRatio(); // 零售价格浮动比例
                String vipPriceFloatingRatio = itemSkuBasePriceDTO.getVipPriceFloatingRatio(); // VIP浮动比例
                BigDecimal areaSalePrice = null; // 商城销售价
                if (priceFloatingRatio != null && priceFloatingRatio.floatValue() >= 0) {
                    areaSalePrice = costPrice.multiply(new BigDecimal("1").add(priceFloatingRatio)); // 商城销售价 = 外部供货价 乘以 (1 + 价格浮动比例)
                }
                BigDecimal retailPrice = null; // 商城零售价
                if (areaSalePrice != null && areaSalePrice.floatValue() >= 0 && retailPriceFloatingRatio != null && retailPriceFloatingRatio.floatValue() >= 0) {
                    retailPrice = areaSalePrice.multiply(new BigDecimal("1").add(retailPriceFloatingRatio)); // 商城零售价 = 商城销售价 乘以 (1 + 价格浮动比例)
                }
                // 更新VIP价格
                BigDecimal vipPriceFloatingRatioBigDecimal;
                try {
                    vipPriceFloatingRatioBigDecimal = new BigDecimal(vipPriceFloatingRatio);
                } catch (Exception e) {
                    vipPriceFloatingRatioBigDecimal = null;
                }
                BigDecimal vipPrice = null;
                if (vipPriceFloatingRatioBigDecimal != null && vipPriceFloatingRatioBigDecimal.floatValue() > 0) {
                    vipPrice = costPrice.multiply(new BigDecimal("1").add(vipPriceFloatingRatioBigDecimal));
                }
                // 更新vip价格
                ExecuteResult<InnerItemSkuPrice> executeResultInnerPrice = this.itemSkuPriceService.queryInnerItemSkuMemberLevelPrice(itemSkuBasePriceDTO.getSkuId(), Constants.PRICE_TYPE_BUYER_GRADE, Constants.VIP_BUYER_GRADE, 0);
                InnerItemSkuPrice innerItemSkuPrice = executeResultInnerPrice.getResult();
                if (vipPrice != null) {
                    if (innerItemSkuPrice != null) {
                        innerItemSkuPrice.setPrice(wrapDecimal(vipPrice, 2));
                        ExecuteResult<String> executeResultUpdate = this.itemSkuPriceService.updateInnerItemSkuPrice(innerItemSkuPrice);
                        if (!executeResultUpdate.isSuccess()) {
                            throw new Exception("更新内部会员VIP等级价格出错, 错误信息 : " + executeResultUpdate.getErrorMessages());
                        } else {
                            logger.info("京东商品价格增量处理成功 : SUCCESS : 存在VIP浮动比例, 更新京东VIP等级价");
                        }
                    } else {
                        this.itemMybatisDAO.updateHasVipPrice(itemSku.getItemId(), 1);
                        InnerItemSkuPrice innerItemSkuPriceParam = new InnerItemSkuPrice();
                        innerItemSkuPriceParam.setSkuId(itemSku.getSkuId());
                        innerItemSkuPriceParam.setItemId(itemSku.getItemId());
                        innerItemSkuPriceParam.setSellerId(0L);
                        innerItemSkuPriceParam.setShopId(0L);
                        innerItemSkuPriceParam.setPriceType(Constants.PRICE_TYPE_BUYER_GRADE);
                        innerItemSkuPriceParam.setBuyerGrade(Constants.VIP_BUYER_GRADE);
                        innerItemSkuPriceParam.setPrice(wrapDecimal(vipPrice, 2));
                        innerItemSkuPriceParam.setCreateId(0L);
                        innerItemSkuPriceParam.setCreateName("system");
                        this.itemSkuPriceService.addInnerItemSkuPrice(innerItemSkuPriceParam);
                    }
                } else {
                    // 设置item hasVipPrice = 0
                    this.itemMybatisDAO.updateHasVipPrice(itemSku.getItemId(), 0);
                    // inner_sku_price 删除一条vip价格记录
                    if (innerItemSkuPrice != null) {
                        InnerItemSkuPrice innerItemSkuPriceParam = new InnerItemSkuPrice();
                        innerItemSkuPriceParam.setGradePriceId(innerItemSkuPrice.getGradePriceId());
                        innerItemSkuPriceParam.setDeleteFlag(1);
                        this.itemSkuPriceService.updateInnerItemSkuPrice(innerItemSkuPriceParam);
                    }
                }
                // 更新
                itemSkuBasePriceDTO.setCostPrice(wrapDecimal(costPrice, 2));
                itemSkuBasePriceDTO.setSaleLimitedPrice(wrapDecimal(saleLimitedPrice, 2));
                itemSkuBasePriceDTO.setAreaSalePrice(wrapDecimal(areaSalePrice, 2));
                itemSkuBasePriceDTO.setRetailPrice(wrapDecimal(retailPrice, 2));
                itemSkuBasePriceDTO.setModifyTime(new Date());
                itemSkuBasePriceDTO.setModifyId(JD_CREATE_ID);
                itemSkuBasePriceDTO.setModifyName(JD_CREATE_NAME);
                ItemSkuBasePrice itemSkuBasePrice = new ItemSkuBasePrice();
                BeanUtils.copyProperties(itemSkuBasePriceDTO, itemSkuBasePrice);
                ExecuteResult executePriceResult = itemSkuPriceService.updateItemSkuBasePrice(itemSkuBasePrice);
                if (executePriceResult != null && executePriceResult.isSuccess()) {
                    logger.info("京东商品价格增量处理成功 : SUCCESS");
                } else {
                    throw new Exception(executePriceResult.getErrorMessages().toString());
                }
            } else {
                logger.error("京东商品价格增量处理失败:根据外部商品SKU_ID未找到价格记录, outSkuId :" + outerSkuId);
            }
        } else {
            logger.error("京东商品价格增量处理失败:根据外部商品SKU_ID未找到价格记录, outSkuId :" + outerSkuId);
        }
    }

    /**
     * 设置小数位数 （默认四舍五入）
     * @param origin
     * @return
     */
    private static BigDecimal wrapDecimal(BigDecimal origin, int newScale) {
        if (origin == null) {
            return origin;
        } else {
            return origin.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        }
    }
}

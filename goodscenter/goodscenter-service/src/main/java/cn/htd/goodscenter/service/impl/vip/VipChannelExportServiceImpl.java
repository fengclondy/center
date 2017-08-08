package cn.htd.goodscenter.service.impl.vip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.dto.AddressInfo;
import cn.htd.common.util.AddressUtils;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.vip.VipChannelItemInfoMapper;
import cn.htd.goodscenter.domain.vip.VipChannelItemInfo;
import cn.htd.goodscenter.dto.mall.MallSkuInDTO;
import cn.htd.goodscenter.dto.mall.MallSkuOutDTO;
import cn.htd.goodscenter.dto.vip.VipChannelItemOutDTO;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.goodscenter.service.vip.VipChannelExportService;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.service.ItemSkuPriceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * vip商品接口
 */
@Service("vipChannelExportService")
public class VipChannelExportServiceImpl implements VipChannelExportService {
    /**
     * 日志
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemSkuDAO itemSkuDAO;

    @Autowired
    private ItemMybatisDAO itemMybatisDAO;

    @Autowired
    private ItemPictureDAO itemPictureDAO;

    @Resource
    private VipChannelItemInfoMapper vipChannelItemInfoMapper;

    @Resource
    private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

    @Autowired
    private AddressUtils addressUtil;

    @Autowired
    private MallItemExportService mallItemExportService;

    @Autowired
    private ItemSkuPriceService itemSkuPriceService;

    @Override
    public ExecuteResult<List<VipChannelItemOutDTO>> queryVipItemList(Pager pager, String cityCode) {
        logger.info("查询VIP频道VIP商品专区商品");
        ExecuteResult<List<VipChannelItemOutDTO>> executeResult = new ExecuteResult();
        List<VipChannelItemOutDTO> list = new ArrayList();
        try {
            // 在大厅上架, 必须要有vip价格，上架状态
            List<VipChannelItemInfo> vipChannelItemInfoList = this.vipChannelItemInfoMapper.selectListByPage(pager);
            for (VipChannelItemInfo vipChannelItemInfo : vipChannelItemInfoList) {
                VipChannelItemOutDTO vipChannelItemOutDTO = new VipChannelItemOutDTO();
                String skuCode = vipChannelItemInfo.getSkuCode();
                ItemSku itemSku = this.itemSkuDAO.selectItemSkuBySkuCode(skuCode);
                if (itemSku == null) {
                    logger.error("VIP频道：查询不到SKU信息, skuCode : {}", skuCode);
                    continue;
                }
                Long itemId = itemSku.getItemId();
                Item item = this.itemMybatisDAO.queryItemByPk(itemId);
                if (item == null) {
                    logger.error("VIP频道：查询不到ITEM信息, itemId : {}, skuCode : {}", itemId, skuCode);
                    continue;
                }
                vipChannelItemOutDTO.setSkuCode(skuCode);
                vipChannelItemOutDTO.setItemCode(item.getItemCode());
                vipChannelItemOutDTO.setItemId(item.getItemId());
                vipChannelItemOutDTO.setItemName(item.getItemName());
                vipChannelItemOutDTO.setModelType(item.getModelType());
                vipChannelItemOutDTO.setSellerId(item.getSellerId());
                vipChannelItemOutDTO.setShopId(item.getShopId());
                vipChannelItemOutDTO.setSkuId(itemSku.getSkuId());
                // 商品ITEM主图
                List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(item.getItemId());
                vipChannelItemOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
                // 有没有vip价格
                ExecuteResult<InnerItemSkuPrice> priceExecuteResult = this.itemSkuPriceService.queryInnerItemSkuMemberLevelPrice(itemSku.getSkuId(),
                                Constants.PRICE_TYPE_BUYER_GRADE, Constants.VIP_BUYER_GRADE, 0); // 查询大厅的vip价格
                if (priceExecuteResult.isSuccess()) {
                    InnerItemSkuPrice innerItemSkuPrice = priceExecuteResult.getResult();
                    if (innerItemSkuPrice != null) {
                        vipChannelItemOutDTO.setVipPrice(innerItemSkuPrice.getPrice());
                    } else {
                        logger.error("VIP频道：查询不到VIP_PRICE信息, skuCode : {}", skuCode);
                        continue;
                    }
                } else {
                    logger.error("VIP频道：查询不到VIP_PRICE信息, skuCode : {}", skuCode);
                    continue;
                }
                // 上架状态，是否有在大厅上架
                Integer itemStatus = item.getItemStatus();
                String channelCode = item.getProductChannelCode();
                if (channelCode.equals(ProductChannelEnum.INTERNAL_SUPPLIER.getCode())) { // 内部供应商商品
                    boolean flag = false; // 是否有在大厅上架
                    if (itemStatus.equals(HtdItemStatusEnum.SHELVED.getCode())) { // itemStatus = 5
                        // 有在大厅上架
                        ItemSkuPublishInfo itemSkuPublishInfo = itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(itemSku.getSkuId(), "2", "1");
                        if (itemSkuPublishInfo != null && (itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity()) > 0) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        logger.error("VIP频道：商品不在大厅上架, skuCode : {}", skuCode);
                        continue;
                    }
                    // 校验大厅销售区域是否在此城市站
                    boolean isInSale = this.isInSaleArea(itemId, cityCode);
                    if (!isInSale) {
                        logger.error("VIP频道：商品不在当前城市站销售, skuCode : {}, cityCode : {}", skuCode, cityCode);
                        continue;
                    }
                } else if (channelCode.equals(ProductChannelEnum.JD_PRODUCT.getCode())) {
                    if (!itemStatus.equals(HtdItemStatusEnum.SHELVED.getCode())) { // 不是上架状态
                        logger.error("VIP频道：京东商品不是上架状态, skuCode : {}", skuCode);
                        continue;
                    }
                } else {
                    logger.error("VIP频道：不包含该渠道类型商品, channelCode : {}, skuCode : {}, channelCode, skuCode");
                    continue;
                }
                list.add(vipChannelItemOutDTO);
            }
            executeResult.setResult(list);
        } catch (Exception e) {
            logger.error("查询VIP频道VIP商品专区商品出错", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<List<VipChannelItemOutDTO>> queryFlashByVipItemList(List<String> flashByVipSkuCodeList) {
        logger.info("查询促销秒杀页VIP秒杀专区商品");
        ExecuteResult<List<VipChannelItemOutDTO>> executeResult = new ExecuteResult();
        List<VipChannelItemOutDTO> list = new ArrayList();
        try {
            if (flashByVipSkuCodeList != null && flashByVipSkuCodeList.size() > 0) {
                for (String skuCode : flashByVipSkuCodeList) {
                    VipChannelItemOutDTO vipChannelItemOutDTO = new VipChannelItemOutDTO();
                    MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
                    mallSkuInDTO.setSkuCode(skuCode);
                    ExecuteResult<MallSkuOutDTO> mallSkuExecuteResult = this.mallItemExportService.queryMallItemDetail(mallSkuInDTO);
                    if (mallSkuExecuteResult.isSuccess()) {
                        MallSkuOutDTO mallSkuOutDTO = mallSkuExecuteResult.getResult();
                        vipChannelItemOutDTO.setSkuCode(mallSkuOutDTO.getSkuCode());
                        vipChannelItemOutDTO.setItemCode(mallSkuOutDTO.getItemCode());
                        vipChannelItemOutDTO.setItemId(mallSkuOutDTO.getItemId());
                        vipChannelItemOutDTO.setItemName(mallSkuOutDTO.getItemName());
                        vipChannelItemOutDTO.setItemPictureUrl(mallSkuOutDTO.getItemPictureUrl());
                        vipChannelItemOutDTO.setModelType(mallSkuOutDTO.getModelType());
                        vipChannelItemOutDTO.setSellerId(mallSkuOutDTO.getSellerId());
                        vipChannelItemOutDTO.setShopId(mallSkuOutDTO.getShopId());
                        vipChannelItemOutDTO.setSkuId(mallSkuOutDTO.getSkuId());
                    } else {
                        logger.error("查询VIP频道VIP商品专区商品基本信息出错, 错误信息 : {}, skuCode : {}", mallSkuExecuteResult.getErrorMessages(), skuCode);
                        continue;
                    }
                    list.add(vipChannelItemOutDTO);
                }
            }
            executeResult.setResult(list);
        } catch (Exception e) {
            logger.error("查询促销秒杀页VIP秒杀专区商品出错");
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> pushVipItemToVipChannel(String skuCodes) {
        logger.info("推送VIP价商品编码至VIP频道, 商品编码 : {}", skuCodes);
        ExecuteResult<String> executeResult = new ExecuteResult();
        try {
            // 校验
            if (!StringUtils.isEmpty(skuCodes)) {
                // 删除所有VIP价商品
                this.vipChannelItemInfoMapper.deleteAll();
                // INSERT OR UPDATE
                skuCodes = skuCodes.trim();
                String[] skuCodeArray = skuCodes.split(Constants.SEPARATOR_COMMA);
                List<VipChannelItemInfo> records = new ArrayList();
                for (String skuCode : skuCodeArray) {
                    // 校验skuCode的合法性
                    ItemSkuDTO itemSkuDTO = itemSkuDAO.queryItemSkuDetailBySkuCode(skuCode);
                    if (itemSkuDTO != null) {
                        VipChannelItemInfo vipChannelItemInfo = new VipChannelItemInfo();
                        vipChannelItemInfo.setCreateId(Constants.SYSTEM_CREATE_ID);
                        vipChannelItemInfo.setCreateName(Constants.SYSTEM_CREATE_NAME);
                        vipChannelItemInfo.setDeleteFlag(Constants.DELETE_FLAG_NO);
                        vipChannelItemInfo.setSkuCode(skuCode);
                        records.add(vipChannelItemInfo);
                    } else {
                        throw new Exception("商品编码【: " + skuCode + "】有误,查询不到商品");
                    }
                }
                this.vipChannelItemInfoMapper.batchInsert(records);
            } else {
                // 删除所有VIP价商品
                this.vipChannelItemInfoMapper.deleteAll();
            }
        } catch (Exception e) {
            logger.error("推送VIP价商品编码至VIP频道出错, 错误信息 : ", e);
            executeResult.addErrorMessage(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> queryVipChannelItemStr() {
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            List<VipChannelItemInfo> vipChannelItemInfoList = this.vipChannelItemInfoMapper.selectListByPage(null);
            StringBuilder stringBuilder = new StringBuilder();
            int size = vipChannelItemInfoList.size();
            int index = 0;
            for (VipChannelItemInfo vipChannelItemInfo : vipChannelItemInfoList) {
                stringBuilder.append(vipChannelItemInfo.getSkuCode());
                if (index < size - 1) {
                    stringBuilder.append(Constants.SEPARATOR_COMMA);
                }
                index++;
            }
            executeResult.setResult(stringBuilder.toString());
        } catch (Exception e) {
            logger.error("查询推送VIP价商品编码至VIP频道出错, 错误信息 : ", e);
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 获取item主图
     * @param itemPictureList 商品item的所有图片
     * @return 主图url
     */
    private String getFirstItemPicture(List<ItemPicture> itemPictureList) {
        String picUrl = null;
        if (itemPictureList != null && itemPictureList.size() > 0) {
            for (ItemPicture itemPicture : itemPictureList) {
                if (itemPicture.getIsFirst() == 1) {
                    picUrl = itemPicture.getPictureUrl();
                    break;
                }
            }
        }
        return picUrl;
    }

    private boolean isInSaleArea(Long itemId, String cityCode) {
        if (!StringUtils.isEmpty(cityCode)) {
            String parentCityCode = null;
            List<String> childCodeList = null;
            AddressInfo currentAddress = addressUtil.getAddressName(cityCode);
            if(currentAddress != null){
                AddressInfo parentAddress = addressUtil.getAddressName(currentAddress.getParentCode());
                if (parentAddress != null) {
                    parentCityCode = parentAddress.getCode();
                }
            }
            List<AddressInfo> childAddressList = addressUtil.getAddressList(cityCode);
            if(CollectionUtils.isNotEmpty(childAddressList)){
                childCodeList= Lists.newArrayList();
                for(AddressInfo childAddressInfo:childAddressList){
                    childCodeList.add(childAddressInfo.getCode());
                }
            }
            int count = this.itemMybatisDAO.queryItemIsInSaleArea(itemId, 0, parentCityCode, cityCode, childCodeList); // vip频道默认是区域商品，所有是否包厢是0
            return count >= 1;
        } else { // 全国
            return true;
        }
    }
}

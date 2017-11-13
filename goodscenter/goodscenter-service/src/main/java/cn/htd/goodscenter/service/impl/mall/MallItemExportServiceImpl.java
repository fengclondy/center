package cn.htd.goodscenter.service.impl.mall;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import cn.htd.common.util.SysProperties;
import cn.htd.goodscenter.dao.*;
import cn.htd.goodscenter.domain.*;
import cn.htd.goodscenter.dto.ItemSearchItemDataDTO;
import cn.htd.goodscenter.dto.ItemSearchResultDTO;
import cn.htd.goodscenter.dto.mall.*;
import cn.htd.membercenter.dto.MemberShipDTO;
import cn.htd.zeus.tc.api.OrderQueryAPI;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordOutDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsInDTO;
import cn.htd.zeus.tc.dto.OrderRecentQueryPurchaseRecordsOutDTO;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.dto.AddressInfo;
import cn.htd.common.middleware.ItemStockResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.util.AddressUtils;
import cn.htd.goodscenter.common.channel.ItemChannelConstant;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.productplus.SellerOuterProductChannelMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.productplus.SellerOuterProductChannel;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.SkuInfoDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.indto.QueryHotSellItemInDTO;
import cn.htd.goodscenter.dto.indto.QueryNewPublishItemInDTO;
import cn.htd.goodscenter.dto.outdto.HotSellItemOutDTO;
import cn.htd.goodscenter.dto.outdto.NewPublishItemOutDTO;
import cn.htd.goodscenter.dto.productplus.ProductPlusItemDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.impl.stock.ItemSkuPublishInfoUtil;
import cn.htd.goodscenter.service.mall.MallItemExportService;
import cn.htd.goodscenter.service.productplus.ProductPlusExportService;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.google.common.collect.Lists;

/**
 * 商品服务 - 前台相关
 *
 * @author chenkang
 * @date 2017-01-09
 */
@Service("mallItemExportService")
public class MallItemExportServiceImpl implements MallItemExportService {

	@Autowired
	private ItemMybatisDAO itemMybatisDAO;

	@Autowired
	private ItemPictureDAO itemPictureDAO;

	@Autowired
	private ItemSkuDAO itemSkuDAO;

	@Autowired
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;

	@Autowired
	private ItemDescribeDAO itemDescribeDAO;

	@Autowired
	private ItemSkuPriceService itemSkuPriceService;

	@Autowired
	private CategoryAttrDAO categoryAttrDAO;

	@Autowired
	private ItemCategoryService itemCategoryService;

	@Resource
	private ItemBrandDAO itemBrandDAO;

	@Autowired
	private ProductPlusExportService productPlusExportService;

	@Autowired
	private ItemSalesAreaMapper itemSalesAreaMapper;

	@Autowired
	private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;

	@Autowired
	private ItemSpuMapper itemSpuMapper;

	@Autowired
	private AddressUtils addressUtil;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SellerOuterProductChannelMapper sellerOuterProductChannelMapper;

	@Autowired
	private RedisDB redisDB;

	@Autowired
	private OrderQueryAPI orderQueryAPI;

	@Autowired
	private SuperbossProductPushDAO superbossProductPushDAO;

	private static final Logger logger = LoggerFactory.getLogger(MallItemExportServiceImpl.class);

	/**
	 * 购物车信息
	 * 
	 * @param mallSkuInDTOList
	 *            进货单参数
	 * @return
	 */
	@Override
	public ExecuteResult<List<MallSkuOutDTO>> queryCartItemList(List<MallSkuInDTO> mallSkuInDTOList) {
		ExecuteResult<List<MallSkuOutDTO>> executeResult = new ExecuteResult();
		List<MallSkuOutDTO> skuInfoDTOList = new ArrayList();
		try {
			if (mallSkuInDTOList != null && mallSkuInDTOList.size() > 0) {
				for (MallSkuInDTO mallSkuInDTO : mallSkuInDTOList) {
					if (mallSkuInDTO != null && !StringUtils.isEmpty(mallSkuInDTO.getSkuCode())) {
						// 商品基本信息
						MallSkuOutDTO mallSkuOutDTO = this.queryBasicItemSkuInfo(mallSkuInDTO.getSkuCode());
						// 商品ITEM主图
						List<ItemPicture> itemPictureList = this.itemPictureDAO
								.queryItemPicsById(mallSkuOutDTO.getItemId());
						mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
						// 商品SKU的销售属性，只有外部供应商有销售属性
						if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode()
								.equals(mallSkuOutDTO.getProductChannelCode())) {
							mallSkuOutDTO.setSaleAttributeList(
									this.parseItemSkuAttribute(mallSkuOutDTO.getSkuSaleAttrStr()));
						}
						/** 类目属性 **/
						mallSkuOutDTO.setItemAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemAttrStr()));
						skuInfoDTOList.add(mallSkuOutDTO);
					}
				}
				// 结果
				executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
				executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
				executeResult.setResult(skuInfoDTOList);
			}
		} catch (Exception e) {
			logger.error("进货单商品列表查询失败, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 商品详情页
	 * 
	 * @param mallSkuInDTO
	 * @return
	 */
	@Override
	public ExecuteResult<MallSkuOutDTO> queryMallItemDetailPage(MallSkuInDTO mallSkuInDTO) {
		ExecuteResult<MallSkuOutDTO> executeResult = new ExecuteResult();
		try {
			if (mallSkuInDTO == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				return executeResult;
			}
			String skuCode = mallSkuInDTO.getSkuCode();
			String itemCode = mallSkuInDTO.getItemCode();
			if (StringUtils.isEmpty(itemCode) && StringUtils.isEmpty(skuCode)) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage("itemCode和skuCode都为空");
				executeResult.addErrorMessage("itemCode和skuCode都为空");
				return executeResult;
			}
			// 商城列表页进入详情页，只传ITEM_CODE，查询默认SKU
			if (StringUtils.isNotEmpty(itemCode)) {
				if (StringUtils.isEmpty(skuCode)) {
					skuCode = this.queryDefaultSkuCodeByItemCode(itemCode);
					logger.info("商品详情页信息, 查询到默认skuCode : {}, itemCode : {}", skuCode, itemCode);
				} else {
					ItemSku itemSku = this.itemSkuDAO.selectItemSkuBySkuCode(skuCode);
					if (itemSku == null) { // 如果sku是乱输的，默认找一个可用的
						skuCode = this.queryDefaultSkuCodeByItemCode(itemCode);
						logger.info("商品详情页信息, 传入的skuCode不存在,  查询到默认skuCode : {}, itemCode : {}", skuCode, itemCode);
					} else { // 检验skuCode 和 itemCode是否匹配
						Item item = this.itemMybatisDAO.queryItemByPk(itemSku.getItemId());
						if (!item.getItemCode().equals(itemCode)) { // 不匹配
							skuCode = this.queryDefaultSkuCodeByItemCode(itemCode); // 以item为准
						}
					}
				}
			}
			// 商品基本信息
			MallSkuOutDTO mallSkuOutDTO = this.queryBasicItemSkuInfo(skuCode);
			if (mallSkuOutDTO.getItemStatus() == null
					|| HtdItemStatusEnum.DELETED.getCode() == mallSkuOutDTO.getItemStatus()
					|| HtdItemStatusEnum.AUDITING.getCode() == mallSkuOutDTO.getItemStatus()) {
				executeResult.setCode(ResultCodeEnum.ERROR.getCode());
				executeResult.setResultMessage("商品状态非法. itemStatus : " + mallSkuOutDTO.getItemStatus());
				return executeResult;
			}
			mallSkuOutDTO.setTunnelMap(mallSkuInDTO.getTunnelMap());
			// 图片
			List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(mallSkuOutDTO.getItemId());
			// 商品ITEM主图
			mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
			// ITEM图片
			mallSkuOutDTO.setItemPictureList(itemPictureList);
			/** 只有外部供应商有SKU图片 **/
			if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
				List<ItemSkuPicture> itemSkuPictureList = this.itemSkuDAO
						.selectSkuPictureBySkuId(mallSkuOutDTO.getSkuId());
				mallSkuOutDTO.setItemSkuPictureList(itemSkuPictureList);
			}
			/** 品牌品类 **/
			this.setCategoryAndBrandInfo(mallSkuOutDTO);
			/** 销售属性, 只有外部供应商有销售属性 **/
			if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
				// 当前sku的销售属性
				mallSkuOutDTO.setSaleAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getSkuSaleAttrStr()));
				// item的销售属性
				mallSkuOutDTO.setItemSaleAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemSaleAttrStr()));
				// item下所有可卖sku及属性
				List<ItemSku> itemSkuList = this.itemSkuDAO.queryByItemId(mallSkuOutDTO.getItemId());
				Map<String, List<MallSkuAttributeDTO>> allAbleSaleAttributeList = new HashMap<String, List<MallSkuAttributeDTO>>();
				for (ItemSku itemSku1 : itemSkuList) {
					String key = itemSku1.getSkuId() + Constants.SEPARATOR_UNDERLINE + itemSku1.getSkuCode();
					allAbleSaleAttributeList.put(key, this.parseItemSkuAttribute(itemSku1.getAttributes()));
				}
				mallSkuOutDTO.setAllAbleSaleAttributeList(allAbleSaleAttributeList);
			}
			/** 平台公司设置颜色属性 **/
			if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
				mallSkuOutDTO.setColour(mallSkuOutDTO.getSkuSaleAttrStr());
			}
			/** 类目属性 **/
			mallSkuOutDTO.setItemAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemAttrStr()));
			/** 商品详情 **/
			ItemDescribe itemDescribe = this.itemDescribeDAO.getDescByItemId(mallSkuOutDTO.getItemId());
			if (itemDescribe != null) {
				mallSkuOutDTO.setItemDescribe(itemDescribe.getDescribeContent());
			}
			// 结果
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuOutDTO);
		} catch (Exception e) {
			logger.error("商品详情页查询出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 商品详情
	 * 
	 * @param mallSkuInDTO
	 * @return
	 */
	@Override
	public ExecuteResult<MallSkuOutDTO> queryMallItemDetail(MallSkuInDTO mallSkuInDTO) {
		ExecuteResult<MallSkuOutDTO> executeResult = new ExecuteResult();
		try {
			if (mallSkuInDTO == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				return executeResult;
			}
			String skuCode = mallSkuInDTO.getSkuCode();
			if (StringUtils.isEmpty(skuCode)) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
				executeResult.setResultMessage("商品SKU编码为空");
				executeResult.addErrorMessage("商品SKU编码为空");
				return executeResult;
			}
			// 商品基本信息
			MallSkuOutDTO mallSkuOutDTO = this.queryBasicItemSkuInfo(skuCode);
			mallSkuOutDTO.setTunnelMap(mallSkuInDTO.getTunnelMap());
			// 商品ITEM主图
			List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(mallSkuOutDTO.getItemId());
			mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
			// 商品模板
			ItemSpu itemSpu = this.itemSpuMapper.selectById(mallSkuOutDTO.getSpuId());
			if (itemSpu != null) {
				mallSkuOutDTO.setItemSpuCode(itemSpu.getSpuCode());
			}
			// 品牌品类
			this.setCategoryAndBrandInfo(mallSkuOutDTO);
			// 商品SKU的销售属性，只有外部供应商有销售属性
			if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
				mallSkuOutDTO.setSaleAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getSkuSaleAttrStr()));
			}
			/** 类目属性 **/
			mallSkuOutDTO.setItemAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemAttrStr()));
			// 结果
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuOutDTO);
		} catch (Exception e) {
			logger.error("商品详情查询出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 商品详情
	 * 
	 * @param mallSkuInDTOs
	 * @return
	 */
	@Override
	public ExecuteResult<List<MallSkuOutDTO>> queryMallItemDetailList(List<MallSkuInDTO> mallSkuInDTOs) {
		ExecuteResult<List<MallSkuOutDTO>> executeResult = new ExecuteResult();
		try {
			if (mallSkuInDTOs == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				return executeResult;
			}
			List<MallSkuOutDTO> mallSkuOutDTOList = new ArrayList();
			Map<String, Map<String, Object>> mapMap = new HashMap<>(); // 存储透传map
			if (mallSkuInDTOs.size() > 0) {
				List<String> skuCodeList = new ArrayList<>();
				for (MallSkuInDTO mallSkuInDTO : mallSkuInDTOs) {
					String skuCode = mallSkuInDTO.getSkuCode();
					skuCodeList.add(skuCode);
					mapMap.put(skuCode, mallSkuInDTO.getTunnelMap());
				}
				// 商品基本信息
				mallSkuOutDTOList = this.queryBasicItemSkuInfoList(skuCodeList);
				for (MallSkuOutDTO mallSkuOutDTO : mallSkuOutDTOList) {
					// 透传turnMap
					mallSkuOutDTO.setTunnelMap(mapMap.get(mallSkuOutDTO.getSkuCode()));
					// 商品ITEM主图
					List<ItemPicture> itemPictureList = this.itemPictureDAO
							.queryItemPicsById(mallSkuOutDTO.getItemId());
					mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
					// 商品模板
					if (mallSkuOutDTO.isSpu()) {
						ItemSpu itemSpu = this.itemSpuMapper.selectById(mallSkuOutDTO.getSpuId());
						if (itemSpu != null) {
							mallSkuOutDTO.setItemSpuCode(itemSpu.getSpuCode());
						}
					}
					// 品牌品类
					this.setCategoryAndBrandInfo(mallSkuOutDTO);
					// 商品SKU的销售属性，只有外部供应商有销售属性
					if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
						mallSkuOutDTO
								.setSaleAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getSkuSaleAttrStr()));
					}
					/** 类目属性 **/
					mallSkuOutDTO.setItemAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemAttrStr()));
				}
			}
			// 结果
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuOutDTOList);
		} catch (Exception e) {
			logger.error("商品详情查询出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 查询库存信息
	 * 
	 * @param mallSkuStockInDTO
	 * @return
	 */
	@Override
	public ExecuteResult<MallSkuStockOutDTO> queryMallItemStockInfo(final MallSkuStockInDTO mallSkuStockInDTO) {
		ExecuteResult<MallSkuStockOutDTO> executeResult = new ExecuteResult();
		try {
			if (mallSkuStockInDTO == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				return executeResult;
			}
			String skuCode = mallSkuStockInDTO.getSkuCode();
			if (StringUtils.isEmpty(skuCode)) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
				executeResult.setResultMessage("商品SKU编码为空");
				executeResult.addErrorMessage("商品SKU编码为空");
				return executeResult;
			}
			if (StringUtils.isEmpty(mallSkuStockInDTO.getProductChannelCode())) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
				executeResult.setResultMessage("商品渠道编码为空");
				executeResult.addErrorMessage("商品渠道编码为空");
				return executeResult;
			}
			if (StringUtils.isEmpty(mallSkuStockInDTO.getCityCode())) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
				executeResult.setResultMessage("城市站编码为空");
				executeResult.addErrorMessage("城市站编码为空");
				return executeResult;
			}
			try {
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						syncTotalStock(mallSkuStockInDTO.getSkuCode(), mallSkuStockInDTO.getSupplierCode());
					}
				});
			} catch (Exception e) {
				logger.error("同步时间库存出错：", e);
			}
			MallSkuStockOutDTO mallSkuStockOutDTO = new MallSkuStockOutDTO();
			// 出参
			ItemSkuPublishInfo itemSkuPublishInfo = null;
			Integer isUpShelf = 0; // 默认下架状态
			Integer outIsBoxFlag = mallSkuStockInDTO.getIsBoxFlag(); // 默认不变
			// 入参
			String productChannelCode = mallSkuStockInDTO.getProductChannelCode();
			Integer isBoxFlag = mallSkuStockInDTO.getIsBoxFlag();
			String cityCode = mallSkuStockInDTO.getCityCode();
			ItemSkuDTO itemSku = itemSkuDAO.queryItemSkuDetailBySkuCode(skuCode);
			Long skuId = itemSku.getSkuId();
			Integer itemStatus = itemSku.getItemStatus();
			// 内部供应商
			if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(productChannelCode)) {
				if (isBoxFlag != null) {
					if (itemStatus.equals(HtdItemStatusEnum.SHELVED.getCode())) {
						// 如果itemStatus是上架状态，继续判断itemSkuPublishInfo
						String shelfType = isBoxFlag.equals(Constants.BOX_FLAG_IS_BOX) ? Constants.SHELF_TYPE_IS_BOX
								: Constants.SHELF_TYPE_IS_AREA;
						ItemSkuPublishInfo itemSkuPublishInfo1 = this.itemSkuPublishInfoMapper
								.selectByItemSkuAndShelfType(skuId, shelfType, Constants.IS_VISABLE_TRUE);
						if (itemSkuPublishInfo1 != null) { // 是否上架
							if (isInSaleArea(itemSku.getItemId(), isBoxFlag, cityCode)) { // 在此城市站发布
								isUpShelf = 1;
								itemSkuPublishInfo = itemSkuPublishInfo1;
								outIsBoxFlag = isBoxFlag;
							}
						}
					}
				} else {
					executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
					executeResult.setResultMessage("内部供应商商品是否包厢标记必传");
					executeResult.addErrorMessage("内部供应商商品是否包厢标记必传");
					return executeResult;
				}
				// 外部供应商
			} else if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(productChannelCode)) {
				itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, "2", "1");
				isUpShelf = itemStatus == HtdItemStatusEnum.SHELVED.getCode() ? 1 : 0;
				outIsBoxFlag = 0;
				// 京东+商品
			} else if (ProductChannelEnum.JD_PRODUCT.getCode().equals(productChannelCode)) {
				itemSkuPublishInfo = null;
				isUpShelf = itemStatus == HtdItemStatusEnum.SHELVED.getCode() ? 1 : 0;
				outIsBoxFlag = 0;
			}
			mallSkuStockOutDTO.setSkuCode(mallSkuStockInDTO.getSkuCode());
			mallSkuStockOutDTO.setIsBoxFlag(outIsBoxFlag);
			mallSkuStockOutDTO.setIsUpShelf(isUpShelf);
			mallSkuStockOutDTO.setItemSkuPublishInfo(itemSkuPublishInfo);
			mallSkuStockOutDTO.setSkuCode(skuCode);
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuStockOutDTO);
		} catch (Exception e) {
			logger.error("查询商品上下架和库存信息出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 商品库存和基本信息 - 订单中心校验使用
	 * 
	 * @param mallSkuWithStockInDTO
	 * @return
	 */
	@Override
	public ExecuteResult<MallSkuWithStockOutDTO> queryMallItemDetailWithStock(
			MallSkuWithStockInDTO mallSkuWithStockInDTO) {
		ExecuteResult<MallSkuWithStockOutDTO> executeResult = new ExecuteResult();
		try {
			MallSkuWithStockOutDTO mallSkuWithStockOutDTO = new MallSkuWithStockOutDTO();
			// 查询商品基本信息
			MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
			mallSkuInDTO.setSkuCode(mallSkuWithStockInDTO.getSkuCode());
			mallSkuInDTO.setTunnelMap(mallSkuWithStockInDTO.getTunnelMap());
			ExecuteResult<MallSkuOutDTO> mallSkuOutDTOExecuteResult = this.queryMallItemDetail(mallSkuInDTO);
			if (mallSkuOutDTOExecuteResult.isSuccess()) {
				MallSkuOutDTO mallSkuOutDTO = mallSkuOutDTOExecuteResult.getResult();
				mallSkuWithStockOutDTO.setMallSkuOutDTO(mallSkuOutDTO);
				// 查询库存
				String productChannelCode = mallSkuOutDTO.getProductChannelCode();
				MallSkuStockInDTO mallSkuStockInDTO = new MallSkuStockInDTO();
				mallSkuStockInDTO.setSkuCode(mallSkuWithStockInDTO.getSkuCode());
				mallSkuStockInDTO.setIsBoxFlag(mallSkuWithStockInDTO.getIsBoxFlag());
				mallSkuStockInDTO.setProductChannelCode(productChannelCode);
				mallSkuStockInDTO.setCityCode(mallSkuWithStockInDTO.getCityCode());
				ExecuteResult<MallSkuStockOutDTO> mallSkuStockOutDTOExecuteResult = this
						.queryMallItemStockInfo(mallSkuStockInDTO);
				if (mallSkuStockOutDTOExecuteResult.isSuccess()) {
					MallSkuStockOutDTO mallSkuStockOutDTO = mallSkuStockOutDTOExecuteResult.getResult();
					mallSkuWithStockOutDTO.setMallSkuStockOutDTO(mallSkuStockOutDTO);
					executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
					executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
					executeResult.setResult(mallSkuWithStockOutDTO);
				} else {
					throw new Exception(mallSkuStockOutDTOExecuteResult.getErrorMessages() + "");
				}
			} else {
				throw new Exception(mallSkuOutDTOExecuteResult.getErrorMessages() + "");
			}
		} catch (Exception e) {
			logger.error("查询商品详情和库存出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 批量查询商品库存和基本信息 - 订单中心校验使用
	 * 
	 * @param mallSkuWithStockInDTOList
	 * @return
	 */
	@Override
	public ExecuteResult<List<MallSkuWithStockOutDTO>> queryMallItemDetailWithStockList(
			List<MallSkuWithStockInDTO> mallSkuWithStockInDTOList) {
		ExecuteResult<List<MallSkuWithStockOutDTO>> executeResult = new ExecuteResult();
		try {
			// 结果
			List<MallSkuWithStockOutDTO> mallSkuWithStockOutDTOList = new ArrayList();
			// 批量查询商品基本信息开始
			List<MallSkuInDTO> mallSkuInDTOs = new ArrayList<>();
			for (MallSkuWithStockInDTO mallSkuWithStockInDTO : mallSkuWithStockInDTOList) {
				MallSkuInDTO mallSkuInDTO = new MallSkuInDTO();
				mallSkuInDTO.setSkuCode(mallSkuWithStockInDTO.getSkuCode());
				Map<String, Object> map = new HashMap();
				map.put("isBoxFlag", mallSkuWithStockInDTO.getIsBoxFlag());
				map.put("cityCode", mallSkuWithStockInDTO.getCityCode());
				mallSkuInDTO.setTunnelMap(map);
				mallSkuInDTOs.add(mallSkuInDTO);
			}
			// 批量查询商品基本信息
			ExecuteResult<List<MallSkuOutDTO>> mallSkuOutDTOListResult = this.queryMallItemDetailList(mallSkuInDTOs);
			if (mallSkuOutDTOListResult.isSuccess()) {
				List<MallSkuOutDTO> mallSkuOutDTOList = mallSkuOutDTOListResult.getResult();
				for (MallSkuOutDTO mallSkuOutDTO : mallSkuOutDTOList) { // 查询库存
					MallSkuWithStockOutDTO mallSkuWithStockOutDTO = new MallSkuWithStockOutDTO();
					// 查询库存
					Map<String, Object> trunMap = mallSkuOutDTO.getTunnelMap();
					MallSkuStockInDTO mallSkuStockInDTO = new MallSkuStockInDTO();
					mallSkuStockInDTO.setSkuCode(mallSkuOutDTO.getSkuCode());
					mallSkuStockInDTO.setIsBoxFlag((Integer) trunMap.get("isBoxFlag"));
					mallSkuStockInDTO.setProductChannelCode(mallSkuOutDTO.getProductChannelCode());
					mallSkuStockInDTO.setCityCode((String) trunMap.get("cityCode"));
					// 查询库存信息
					ExecuteResult<MallSkuStockOutDTO> mallSkuStockOutDTOExecuteResult = this
							.queryMallItemStockInfo(mallSkuStockInDTO);
					if (mallSkuStockOutDTOExecuteResult.isSuccess()) {
						MallSkuStockOutDTO mallSkuStockOutDTO = mallSkuStockOutDTOExecuteResult.getResult();
						mallSkuWithStockOutDTO.setMallSkuOutDTO(mallSkuOutDTO);
						mallSkuWithStockOutDTO.setMallSkuStockOutDTO(mallSkuStockOutDTO);
						mallSkuWithStockOutDTOList.add(mallSkuWithStockOutDTO);
					} else {
						throw new Exception("批量查询商品库存失败, 错误信息 : " + mallSkuStockOutDTOExecuteResult.getErrorMessages());
					}
				}
			} else {
				throw new Exception("批量查询商品详情失败, 错误信息 : " + mallSkuOutDTOListResult.getErrorMessages());
			}
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuWithStockOutDTOList);
		} catch (Exception e) {
			logger.error("批量查询商品详情和库存出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 选择包厢标记和库存信息
	 * 
	 * @param mallSkuStockInDTO
	 * @return
	 */
	@Override
	public ExecuteResult<MallChoiceBoxProductOutDTO> choiceMallItemBoxFlagAndStockInfo(
			final MallChoiceBoxProductInDTO mallSkuStockInDTO) {
		// 校验入参
		ExecuteResult<MallChoiceBoxProductOutDTO> executeResult = new ExecuteResult();
		if (mallSkuStockInDTO == null) { // 入参是否为空
			executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
			executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
			executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
			return executeResult;
		}
		String skuCode = mallSkuStockInDTO.getSkuCode(); // 商品sku编码
		String productChannelCode = mallSkuStockInDTO.getProductChannelCode(); // 商品渠道编码
		String cityCode = mallSkuStockInDTO.getCityCode(); // 城市站编号
		Integer isLogin = mallSkuStockInDTO.getIsLogin(); // 是否登录
		Integer isHasRelation = mallSkuStockInDTO.getIsHasRelation(); // 是否有经营关系
		if (StringUtils.isEmpty(skuCode)) {
			executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
			executeResult.setResultMessage("商品SKU编码为空");
			executeResult.addErrorMessage("商品SKU编码为空");
			return executeResult;
		}
		if (StringUtils.isEmpty(mallSkuStockInDTO.getProductChannelCode())) {
			executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
			executeResult.setResultMessage("商品渠道编码为空");
			executeResult.addErrorMessage("商品渠道编码为空");
			return executeResult;
		}
		// 定义返回参数
		MallChoiceBoxProductOutDTO mallChoiceBoxProductOutDTO = new MallChoiceBoxProductOutDTO();
		ItemSkuPublishInfo itemSkuPublishInfo = null;
		Integer isUpShelf = null;
		Integer outIsBoxFlag = null;
		try {
			// 异步查询实际库存
			// taskExecutor.execute(new Runnable() {
			// @Override
			// public void run() {
			// syncTotalStock(mallSkuStockInDTO.getSkuCode(),
			// mallSkuStockInDTO.getSupplierCode());
			// }
			// });

			syncTotalStock(mallSkuStockInDTO.getSkuCode(), mallSkuStockInDTO.getSupplierCode());

			ItemSkuDTO itemSku = itemSkuDAO.queryItemSkuDetailBySkuCode(skuCode);
			Long skuId = itemSku.getSkuId();
			Integer itemStatus = itemSku.getItemStatus();
			// 1. 内部供应商
			if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(productChannelCode)) {
				if (isLogin == null) {
					executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
					executeResult.setResultMessage("是否登录为空");
					executeResult.addErrorMessage("是否登录为空");
					return executeResult;
				}
				if (isHasRelation == null) {
					executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
					executeResult.setResultMessage("是否经营关系为空");
					executeResult.addErrorMessage("是否经营关系为空");
					return executeResult;
				}
				if (itemStatus.equals(HtdItemStatusEnum.SHELVED.getCode())) { // 0.
																				// item是上架状态
					// 首先查询商品是否在包厢或者大厅上架
					boolean isBoxProduct = false; // 包厢是否上架
					ItemSkuPublishInfo boxItemSkuPublishInfo = this.itemSkuPublishInfoMapper
							.selectByItemSkuAndShelfType(skuId, "1", "1");
					if (boxItemSkuPublishInfo != null) { // 包厢上架
						if (isInSaleArea(itemSku.getItemId(), 1, cityCode)) { // 包厢在此城市站发布
							isBoxProduct = true;
						}
					}
					boolean isAreaProduct = false; // 区域是否上架
					ItemSkuPublishInfo areaItemSkuPublishInfo = this.itemSkuPublishInfoMapper
							.selectByItemSkuAndShelfType(skuId, "2", "1");
					if (areaItemSkuPublishInfo != null) {
						if (isInSaleArea(itemSku.getItemId(), 0, cityCode)) { // 区域在此城市站发布
							isAreaProduct = true;
						}
					}
					// 1 是否登录
					if (isLogin == 0) { // 1.1 未登录
						if (isBoxProduct && isAreaProduct) { // 1.11 未登录
																// 如果区域和包厢都上架，优先选择大厅
							isUpShelf = 1;
							itemSkuPublishInfo = areaItemSkuPublishInfo;
							outIsBoxFlag = 0;
						} else if (isBoxProduct) { // 1.12 未登录 只有包厢都上架，选择包厢
							isUpShelf = 1;
							itemSkuPublishInfo = boxItemSkuPublishInfo;
							outIsBoxFlag = 1;
						} else if (isAreaProduct) { // 1.13 未登录 只有区域都上架，选择区域
							itemSkuPublishInfo = areaItemSkuPublishInfo;
							isUpShelf = 1;
							outIsBoxFlag = 0;
						} else { // 1.14 都未上架，显示下架
							isUpShelf = 0;
							itemSkuPublishInfo = null;
							outIsBoxFlag = 0;
						}
					} else { // 1.2 已登录
						// 2 是否包厢商品
						if (isBoxProduct) { // 2.1 是包厢商品
							// 3 是否经营关系
							if (isHasRelation == 1) { // 3.1 有经营关系，选择为包厢上架
								isUpShelf = 1;
								itemSkuPublishInfo = boxItemSkuPublishInfo;
								outIsBoxFlag = 1;
							} else { // 3.2 无经营关系
								// 4. 无经营关系继续看是否区域上架
								if (isAreaProduct) { // 4.1 区域上架, 选择区域
									itemSkuPublishInfo = areaItemSkuPublishInfo;
									isUpShelf = 1;
									outIsBoxFlag = 0;
								} else { // 4.2 区域不上架, 选择包厢【申请经营关系】
									// 选择为包厢上架
									isUpShelf = 1;
									itemSkuPublishInfo = boxItemSkuPublishInfo;
									outIsBoxFlag = 1;
								}
							}
						} else { // 2.2 不是包厢商品
							if (isAreaProduct) { // 5.1 是区域商品, 选择区域
								itemSkuPublishInfo = areaItemSkuPublishInfo;
								isUpShelf = 1;
								outIsBoxFlag = 0;
							} else { // 5.1 不是包厢商品,也不是区域商品, 显示下架
								isUpShelf = 0;
								itemSkuPublishInfo = null;
								outIsBoxFlag = 0;
							}
						}
					}
				} else { // 0. item是下架状态
					isUpShelf = 0; // 下架状态
					itemSkuPublishInfo = null;
					outIsBoxFlag = 0;
				}
				// 2. 外部供应商
			} else if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(productChannelCode)) {
				itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId, "2", "1"); // 外部供应商商品是大厅库存
				isUpShelf = itemStatus == HtdItemStatusEnum.SHELVED.getCode() ? 1 : 0; // 上下架状态
				outIsBoxFlag = 0; // 默认是区域商品
				// 3. 京东+商品
			} else if (ProductChannelEnum.JD_PRODUCT.getCode().equals(productChannelCode)) {
				itemSkuPublishInfo = null; // 京东没有库存，调用方实时从中间件查询
				isUpShelf = itemStatus == HtdItemStatusEnum.SHELVED.getCode() ? 1 : 0; // 上下架状态
				outIsBoxFlag = 0; // 京东默认是区域商品
			}
			mallChoiceBoxProductOutDTO.setIsBoxProduct(outIsBoxFlag);
			mallChoiceBoxProductOutDTO.setIsUpShelf(isUpShelf);
			mallChoiceBoxProductOutDTO.setItemSkuPublishInfo(itemSkuPublishInfo);
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallChoiceBoxProductOutDTO);
		} catch (Exception e) {
			logger.error("查询商品上下架和库存信息出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<MallSkuOutDTO> queryMallFlashBuyItemDetail(MallSkuInDTO mallSkuInDTO) {
		ExecuteResult<MallSkuOutDTO> executeResult = new ExecuteResult();
		try {
			if (mallSkuInDTO == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				return executeResult;
			}
			String skuCode = mallSkuInDTO.getSkuCode();
			if (StringUtils.isEmpty(skuCode)) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_ILLEGAL.getCode());
				executeResult.setResultMessage("商品SKU编码为空");
				executeResult.addErrorMessage("商品SKU编码为空");
				return executeResult;
			}
			/** 商品基本信息 **/
			MallSkuOutDTO mallSkuOutDTO = this.queryBasicItemSkuInfo(skuCode);
			// 商品ITEM主图
			List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(mallSkuOutDTO.getItemId());
			mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
			/** sku图片 **/
			if (ProductChannelEnum.JD_PRODUCT.getCode().equals(mallSkuOutDTO.getProductChannelCode())) { // 外接商品图片只有item图片
				List<ItemSkuPicture> itemSkuPictureList = new ArrayList<>();
				if (itemPictureList != null) {
					for (ItemPicture itemPicture : itemPictureList) {
						ItemSkuPicture itemSkuPicture = new ItemSkuPicture();
						BeanUtils.copyProperties(itemPicture, itemSkuPicture);
						itemSkuPictureList.add(itemSkuPicture);
					}
				}
				mallSkuOutDTO.setItemSkuPictureList(itemSkuPictureList);
			} else {
				List<ItemSkuPicture> itemSkuPictureList = this.itemSkuDAO
						.selectSkuPictureBySkuId(mallSkuOutDTO.getSkuId());
				mallSkuOutDTO.setItemSkuPictureList(itemSkuPictureList);
			}
			/** 品牌品类 **/
			this.setCategoryAndBrandInfo(mallSkuOutDTO);
			/** 销售属性, 只有外部供应商有销售属性 **/
			if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSkuOutDTO.getProductChannelCode())) {
				// 当前sku的销售属性
				mallSkuOutDTO.setSaleAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getSkuSaleAttrStr()));
			}
			/** 类目属性 **/
			mallSkuOutDTO.setItemAttributeList(this.parseItemSkuAttribute(mallSkuOutDTO.getItemAttrStr()));
			/** item describe **/
			ItemDescribe itemDescribe = this.itemDescribeDAO.getDescByItemId(mallSkuOutDTO.getItemId());
			if (itemDescribe != null) {
				mallSkuOutDTO.setItemDescribe(itemDescribe.getDescribeContent());
			}
			// 结果
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(mallSkuOutDTO);
			executeResult.setResult(mallSkuOutDTO);
		} catch (Exception e) {
			logger.error("秒杀商品详情页查询出错, 错误信息 : ", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ItemFavouriteOutDTO queryFavouriteItemInfo(Long skuId) {
		ItemFavouriteOutDTO itemFavouriteOutDTO = null;
		try {
			itemFavouriteOutDTO = new ItemFavouriteOutDTO();
			MallSkuOutDTO mallSkuOutDTO = this.queryBasicItemSkuInfo(skuId);
			// 商品ITEM主图
			List<ItemPicture> itemPictureList = this.itemPictureDAO.queryItemPicsById(mallSkuOutDTO.getItemId());
			mallSkuOutDTO.setItemPictureUrl(this.getFirstItemPicture(itemPictureList));
			itemFavouriteOutDTO.setSkuCode(mallSkuOutDTO.getSkuCode());
			itemFavouriteOutDTO.setSkuId(mallSkuOutDTO.getSkuId());
			itemFavouriteOutDTO.setItemId(mallSkuOutDTO.getItemId());
			itemFavouriteOutDTO.setItemCode(mallSkuOutDTO.getItemCode());
			itemFavouriteOutDTO.setItemName(mallSkuOutDTO.getItemName());
			itemFavouriteOutDTO.setItemStatus(mallSkuOutDTO.getItemStatus());
			itemFavouriteOutDTO.setProductChannelCode(mallSkuOutDTO.getProductChannelCode());
			itemFavouriteOutDTO.setItemPictureUrl(mallSkuOutDTO.getItemPictureUrl());
			itemFavouriteOutDTO.setSellerId(mallSkuOutDTO.getSellerId());
			itemFavouriteOutDTO.setShopId(mallSkuOutDTO.getShopId());
			itemFavouriteOutDTO.setThirdCategoryId(mallSkuOutDTO.getThirdCategoryId());
			itemFavouriteOutDTO.setBrandId(mallSkuOutDTO.getBrandId());
		} catch (Exception e) {
			logger.error("查询收藏页商品列表信息出错, 错误信息 : ", e);
		}
		return itemFavouriteOutDTO;
	}

	/**
	 * 根据SKU_CODE 查询商品基本信息
	 * 
	 * @param skuCode
	 *            商品编码
	 * @return MallSkuOutDTO
	 */
	private MallSkuOutDTO queryBasicItemSkuInfo(String skuCode) throws Exception {
		// 从redis获取基本信息
		// MallSkuOutDTO mallSkuOutDTO = null;
		// String key = Constants.REDIS_KEY_PREFIX_SKU_BASIC_INFO + skuCode;
		// String value = redisDB.get(key);
		// mallSkuOutDTO = JSON.parseObject(value, MallSkuOutDTO.class);
		// if (mallSkuOutDTO != null) {
		// return mallSkuOutDTO;
		// }
		// 从数据库获取
		MallSkuOutDTO mallSkuOutDTO = new MallSkuOutDTO();
		try {
			ItemSku itemSku = this.itemSkuDAO.selectItemSkuBySkuCode(skuCode);
			if (itemSku == null) {
				throw new Exception("查询不到SKU信息, SKU_CODE : " + skuCode);
			}
			Long skuId = itemSku.getSkuId();
			Long itemId = itemSku.getItemId();
			Item item = this.itemMybatisDAO.queryItemByPk(itemId);
			if (item == null) {
				throw new Exception("查询不到ITEM信息, ITEM_ID : " + itemId);
			}
			// SKU
			mallSkuOutDTO.setSkuCode(itemSku.getSkuCode());
			mallSkuOutDTO.setSkuId(skuId);
			mallSkuOutDTO.setEanCode(itemSku.getEanCode());
			mallSkuOutDTO.setSubTitle(itemSku.getSubTitle());
			// ITEM
			mallSkuOutDTO.setSkuErpCode(item.getErpCode());
			mallSkuOutDTO.setItemId(itemId);
			mallSkuOutDTO.setItemName(item.getItemName());
			mallSkuOutDTO.setItemCode(item.getItemCode());
			mallSkuOutDTO.setModelType(item.getModelType());
			mallSkuOutDTO.setWeightUnit(item.getWeightUnit());
			mallSkuOutDTO.setWeight(item.getWeight());
			mallSkuOutDTO.setNetWeight(item.getNetWeight());
			mallSkuOutDTO.setLength(item.getLength());
			mallSkuOutDTO.setWidth(item.getWidth());
			mallSkuOutDTO.setHeight(item.getHeight());
			mallSkuOutDTO.setOrigin(item.getOrigin());
			mallSkuOutDTO.setAd(item.getAd());
			mallSkuOutDTO.setItemQualification(item.getItemQualification());
			mallSkuOutDTO.setPackingList(item.getPackingList());
			mallSkuOutDTO.setItemStatus(item.getItemStatus());
			mallSkuOutDTO.setSellerId(item.getSellerId());
			mallSkuOutDTO.setShopId(item.getShopId());
			mallSkuOutDTO.setIsVipItem(item.getIsVipItem());
			mallSkuOutDTO.setVipItemType(item.getVipItemType());
			mallSkuOutDTO.setVipSyncFlag(item.getVipSyncFlag());
			mallSkuOutDTO.setShopFreightTemplateId(item.getShopFreightTemplateId()); // 运费模板（外部供应商）
			mallSkuOutDTO.setFreightAmount(item.getFreightAmount()); // 运费（内部供应商）
			mallSkuOutDTO.setOuterSkuId(itemSku.getOuterSkuId());
			mallSkuOutDTO.setProductChannelCode(item.getProductChannelCode());
			mallSkuOutDTO.setBrandId(item.getBrand());
			mallSkuOutDTO.setThirdCategoryId(item.getCid());
			mallSkuOutDTO.setSpu(item.getIsSpu() == 1);
			mallSkuOutDTO.setSpuId(item.getItemSpuId());
			// 属性
			mallSkuOutDTO.setSkuSaleAttrStr(itemSku.getAttributes());
			mallSkuOutDTO.setItemSaleAttrStr(item.getAttrSale());
			mallSkuOutDTO.setItemAttrStr(item.getAttributes());
			mallSkuOutDTO.setErpFirstCategoryCode(item.getErpFirstCategoryCode());
			mallSkuOutDTO.setErpFiveCategoryCode(item.getErpFiveCategoryCode()); // erp五级类目
			// 预售
			mallSkuOutDTO.setIsPreSale(item.getIsPreSale());
			if (1 == item.getIsPreSale()) {
				mallSkuOutDTO.setPreSaleStartTime(item.getPreSaleStarttime());
				mallSkuOutDTO.setPreSaleEndTime(item.getPreSaleEndtime());
			}
			//// 设置到redis
			// String redisValue = JSON.toJSONString(mallSkuOutDTO);
			// redisDB.setAndExpire(key, redisValue, 300);
		} catch (Exception e) {
			logger.error("查询商品基本信息出错, 错误信息:", e);
			throw e;
		}
		return mallSkuOutDTO;
	}

	/**
	 * 根据SKU_CODE 查询商品基本信息
	 * 
	 * @param skuCodeList
	 *            商品编码列表
	 * @return MallSkuOutDTO
	 */
	private List<MallSkuOutDTO> queryBasicItemSkuInfoList(List<String> skuCodeList) throws Exception {
		// 从redis获取基本信息
		List<MallSkuOutDTO> list = new ArrayList();
		try {
			List<ItemSkuDTO> itemSkuList = this.itemSkuDAO.selectItemSkuListBySkuCodeList(skuCodeList);
			for (ItemSkuDTO itemSkuDTO : itemSkuList) {
				// SKU
				MallSkuOutDTO mallSkuOutDTO = new MallSkuOutDTO();
				mallSkuOutDTO.setSkuCode(itemSkuDTO.getSkuCode());
				mallSkuOutDTO.setSkuId(itemSkuDTO.getSkuId());
				mallSkuOutDTO.setEanCode(itemSkuDTO.getEanCode());
				mallSkuOutDTO.setSkuErpCode(itemSkuDTO.getErpCode());
				// ITEM
				mallSkuOutDTO.setItemId(itemSkuDTO.getItemId());
				mallSkuOutDTO.setItemName(itemSkuDTO.getItemName());
				mallSkuOutDTO.setItemCode(itemSkuDTO.getItemCode());
				mallSkuOutDTO.setModelType(itemSkuDTO.getModelType());
				mallSkuOutDTO.setWeightUnit(itemSkuDTO.getWeightUnit());
				mallSkuOutDTO.setWeight(itemSkuDTO.getWeight());
				mallSkuOutDTO.setNetWeight(itemSkuDTO.getNetWeight());
				mallSkuOutDTO.setLength(itemSkuDTO.getLength());
				mallSkuOutDTO.setWidth(itemSkuDTO.getWidth());
				mallSkuOutDTO.setHeight(itemSkuDTO.getHeight());
				mallSkuOutDTO.setOrigin(itemSkuDTO.getOrigin());
				mallSkuOutDTO.setAd(itemSkuDTO.getAd());
				mallSkuOutDTO.setItemQualification(itemSkuDTO.getItemQualification());
				mallSkuOutDTO.setPackingList(itemSkuDTO.getPackingList());
				mallSkuOutDTO.setItemStatus(itemSkuDTO.getItemStatus());
				mallSkuOutDTO.setSellerId(itemSkuDTO.getSellerId());
				mallSkuOutDTO.setShopId(itemSkuDTO.getShopId());
				mallSkuOutDTO.setIsVipItem(itemSkuDTO.getIsVipItem());
				mallSkuOutDTO.setVipItemType(itemSkuDTO.getVipItemType());
				mallSkuOutDTO.setVipSyncFlag(itemSkuDTO.getVipSyncFlag());
				mallSkuOutDTO.setShopFreightTemplateId(itemSkuDTO.getShopFreightTemplateId()); // 运费模板（外部供应商）
				mallSkuOutDTO.setFreightAmount(itemSkuDTO.getFreightAmount()); // 运费（内部供应商）
				mallSkuOutDTO.setOuterSkuId(itemSkuDTO.getOuterSkuId());
				mallSkuOutDTO.setProductChannelCode(itemSkuDTO.getProductChannelCode());
				mallSkuOutDTO.setBrandId(itemSkuDTO.getBrand());
				mallSkuOutDTO.setThirdCategoryId(itemSkuDTO.getCid());
				mallSkuOutDTO.setSpu(itemSkuDTO.isSpu());
				mallSkuOutDTO.setSpuId(itemSkuDTO.getSpuId());
				// 属性
				mallSkuOutDTO.setSkuSaleAttrStr(itemSkuDTO.getSkuAttributeStr()); // sku销售属性
				mallSkuOutDTO.setItemSaleAttrStr(itemSkuDTO.getAttrSaleStr()); // item销售属性
				mallSkuOutDTO.setItemAttrStr(itemSkuDTO.getAttributesStr()); // item类目属性
				mallSkuOutDTO.setErpFirstCategoryCode(itemSkuDTO.getErpFirstCategoryCode());
				mallSkuOutDTO.setErpFiveCategoryCode(itemSkuDTO.getErpFiveCategoryCode()); // erp五级类目
				// 预售
				mallSkuOutDTO.setIsPreSale(itemSkuDTO.getIsPreSale());
				if (itemSkuDTO.getIsPreSale() == 1) {
					mallSkuOutDTO.setPreSaleStartTime(itemSkuDTO.getPreSaleStartTime());
					mallSkuOutDTO.setPreSaleEndTime(itemSkuDTO.getPreSaleEndTime());
				}
				list.add(mallSkuOutDTO);
			}
		} catch (Exception e) {
			logger.error("查询商品基本信息出错, 错误信息:", e);
			throw e;
		}
		return list;
	}

	/**
	 * 根据SKU_CODE 查询商品基本信息
	 * 
	 * @param skuId
	 *            商品编码
	 * @return MallSkuOutDTO
	 */
	private MallSkuOutDTO queryBasicItemSkuInfo(Long skuId) {
		MallSkuOutDTO mallSkuOutDTO = new MallSkuOutDTO();
		try {
			ItemSku itemSku = this.itemSkuDAO.queryItemSkuBySkuId(skuId);
			if (itemSku == null) {
				throw new RuntimeException("查询不到SKU信息, SKU_CODE : " + skuId);
			}
			Long itemId = itemSku.getItemId();
			Item item = this.itemMybatisDAO.queryItemByPk(itemId);
			if (item == null) {
				throw new RuntimeException("查询不到ITEM信息, ITEM_ID : " + itemId);
			}
			// SKU
			mallSkuOutDTO.setSkuCode(itemSku.getSkuCode());
			mallSkuOutDTO.setSkuId(skuId);
			// ITEM
			mallSkuOutDTO.setItemId(itemId);
			mallSkuOutDTO.setItemName(item.getItemName());
			mallSkuOutDTO.setItemCode(item.getItemCode());
			mallSkuOutDTO.setModelType(item.getModelType());
			mallSkuOutDTO.setWeightUnit(item.getWeightUnit());
			mallSkuOutDTO.setWeight(item.getWeight());
			mallSkuOutDTO.setNetWeight(item.getNetWeight());
			mallSkuOutDTO.setLength(item.getLength());
			mallSkuOutDTO.setWidth(item.getWidth());
			mallSkuOutDTO.setHeight(item.getHeight());
			mallSkuOutDTO.setOrigin(item.getOrigin());
			mallSkuOutDTO.setAd(item.getAd());
			mallSkuOutDTO.setItemQualification(item.getItemQualification());
			mallSkuOutDTO.setPackingList(item.getPackingList());
			mallSkuOutDTO.setItemStatus(item.getItemStatus());
			mallSkuOutDTO.setSellerId(item.getSellerId());
			mallSkuOutDTO.setShopId(item.getShopId());
			mallSkuOutDTO.setIsVipItem(item.getIsVipItem());
			mallSkuOutDTO.setVipItemType(item.getVipItemType());
			mallSkuOutDTO.setVipSyncFlag(item.getVipSyncFlag());
			mallSkuOutDTO.setShopFreightTemplateId(item.getShopFreightTemplateId()); // 运费模板（外部供应商）
			mallSkuOutDTO.setFreightAmount(item.getFreightAmount()); // 运费（内部供应商）
			mallSkuOutDTO.setOuterSkuId(itemSku.getOuterSkuId());
			mallSkuOutDTO.setProductChannelCode(item.getProductChannelCode());
			mallSkuOutDTO.setBrandId(item.getBrand());
			mallSkuOutDTO.setThirdCategoryId(item.getCid());
			mallSkuOutDTO.setErpFirstCategoryCode(item.getErpFirstCategoryCode());
			// 预售
			mallSkuOutDTO.setIsPreSale(item.getIsPreSale());
			if (1 == item.getIsPreSale()) {
				mallSkuOutDTO.setPreSaleStartTime(item.getPreSaleStarttime());
				mallSkuOutDTO.setPreSaleEndTime(item.getPreSaleEndtime());
			}
			// 属性
			mallSkuOutDTO.setSkuSaleAttrStr(itemSku.getAttributes());
			mallSkuOutDTO.setItemSaleAttrStr(item.getAttrSale());
			mallSkuOutDTO.setItemAttrStr(item.getAttributes());
		} catch (Exception e) {
			logger.error("查询商品基本信息出错, 错误信息:", e);
			throw e;
		}
		return mallSkuOutDTO;
	}

	/**
	 * 查询商品品牌品类详细信息
	 * 
	 * @param mallSkuOutDTO
	 *            目标
	 */
	private void setCategoryAndBrandInfo(MallSkuOutDTO mallSkuOutDTO) {
		/** 品类信息 **/
		ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService
				.queryItemOneTwoThreeCategoryName(mallSkuOutDTO.getThirdCategoryId(), ">>");
		if (executeResultCategory.isSuccess()) {
			Map<String, Object> categoryMap = executeResultCategory.getResult();
			mallSkuOutDTO.setFirstCategoryId((Long) categoryMap.get("firstCategoryId"));
			mallSkuOutDTO.setSecondCategoryId((Long) categoryMap.get("secondCategoryId"));
			mallSkuOutDTO.setCategoryName((String) categoryMap.get("categoryName"));
			mallSkuOutDTO.setFirstCategoryName((String) categoryMap.get("firstCategoryName"));
			mallSkuOutDTO.setSecondCategoryName((String) categoryMap.get("secondCategoryName"));
			mallSkuOutDTO.setThirdCategoryName((String) categoryMap.get("thirdCategoryName"));
		}
		/** 品牌信息 **/
		String brandName = null;
		try {
			// 优先从REDIS获取品牌
			String key = Constants.REDIS_KEY_PREFIX_BRAND + String.valueOf(mallSkuOutDTO.getBrandId());
			brandName = redisDB.get(key);
		} catch (Exception e) {
			logger.error("从REDIS获取品牌出错, 错误信息 :", e);
		}
		if (StringUtils.isEmpty(brandName)) {
			// 从DB获取
			ItemBrand itemBrand = this.itemBrandDAO.queryById(mallSkuOutDTO.getBrandId());
			if (itemBrand != null) {
				mallSkuOutDTO.setBrandName(itemBrand.getBrandName());
			}
		} else {
			mallSkuOutDTO.setBrandName(brandName);
		}
	}

	/**
	 * 根据商品ITEM编码查询可用的商品SKU编码
	 * 
	 * @param itemCode
	 *            商品ITEM编码
	 * @return 默认SKU编码
	 */
	private String queryDefaultSkuCodeByItemCode(String itemCode) throws Exception {
		String defaultSkuCode = null;
		try {
			Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
			if (item == null) {
				throw new Exception("查询不到item信息, itemCode : " + itemCode);
			}
			String productChannelCode = item.getProductChannelCode();
			if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(productChannelCode)
					|| ProductChannelEnum.JD_PRODUCT.getCode().equals(productChannelCode)) { // 内部供应商
																								// 和京东商品
				List<ItemSku> skuList = this.itemSkuDAO.queryByItemId(item.getItemId());
				if (skuList.size() == 1) { // item 和 item_sku 1v1
					ItemSku itemSku = skuList.get(0);
					defaultSkuCode = itemSku.getSkuCode();
				} else {
					throw new Exception("内部供应商商品和京东商品根据itemCode期望查到一条SKU, 实际查询到的条数 : " + skuList.size()
							+ ", itemCode : " + itemCode);
				}
			} else if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(productChannelCode)) { // 外部供应商
				// 查询item下所有SKU
				List<ItemSku> skuList = this.itemSkuDAO.queryByItemId(item.getItemId());
				if (skuList.size() > 0) {
					for (ItemSku itemSku : skuList) {
						// 1. 优先判断主sku
						if (itemSku.getSkuType().equals(1)) {
							ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper
									.selectByItemSkuAndShelfType(itemSku.getSkuId(), "2", "1");
							if (itemSkuPublishInfo != null && (itemSkuPublishInfo.getDisplayQuantity()
									- itemSkuPublishInfo.getReserveQuantity() > 0)) {
								defaultSkuCode = itemSku.getSkuCode();
								break;
							}
						}
					}
					// 2. 主sku不下架，查询其他可用的
					for (ItemSku itemSku : skuList) {
						ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper
								.selectByItemSkuAndShelfType(itemSku.getSkuId(), "2", "1");
						if (itemSkuPublishInfo != null && (itemSkuPublishInfo.getDisplayQuantity()
								- itemSkuPublishInfo.getReserveQuantity() > 0)) {
							defaultSkuCode = itemSku.getSkuCode();
							break;
						}
					}
					// 3. 都没有上架的，默认取主sku
					if (StringUtils.isEmpty(defaultSkuCode)) {
						for (ItemSku itemSku : skuList) {
							if (itemSku.getSkuType().equals(1)) {
								defaultSkuCode = itemSku.getSkuCode();
								break;
							}
						}
					}
				} else {
					throw new Exception("外部供应商商品根据itemCode查询不到SKU记录, itemCode : " + itemCode);
				}

			} else {
				throw new Exception(
						"商品渠道编码非法, productChannelCode : " + productChannelCode + ", itemCode : " + itemCode);
			}
		} catch (Exception e) {
			logger.error("根据商品itemCode查询默认skuCode出错, itemCode : {}", itemCode, e);
			throw e;
		}
		return defaultSkuCode;
	}

	/**
	 * 解析销售属性
	 * 
	 * @param attributes
	 *            商品销售属性
	 * @return 商品销售属性DTO
	 */
	private List<MallSkuAttributeDTO> parseItemSkuAttribute(String attributes) {
		List<MallSkuAttributeDTO> list = new ArrayList();
		try {
			if (StringUtils.isNotEmpty(attributes)) {
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
					if (StringUtils.isEmpty(attributeName)) {
						continue;
					}
					if (attributeValueObj instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray) attributeValueObj;
						int size = jsonArray.size();
						for (int i = 0; i < size; i++) {
							Object attrValueObj = jsonArray.get(i);
							if (attrValueObj == null || !StringUtils.isNumeric(attrValueObj + "")) {
								logger.error("执行方法【parseItemSkuAttribute】attributeValueId", attrValueObj);
								continue;
							}
							Integer attributeValueId = Integer.valueOf(attrValueObj + "");
							String attributeValueName = this.getAttributeValueName(attributeValueId);
							if (!StringUtils.isEmpty(attributeValueName)) {
								MallSkuAttributeDTO mallSkuAttributeDTO = getMallSkuAttributeDTO(attributeId,
										attributeName, attributeValueId, attributeValueName);
								list.add(mallSkuAttributeDTO);
							}
						}
					} else {
						Integer attributeValueId = Integer.valueOf(attributeValueObj + "");
						String attributeValueName = this.getAttributeValueName(attributeValueId);
						MallSkuAttributeDTO mallSkuAttributeDTO = getMallSkuAttributeDTO(attributeId, attributeName,
								attributeValueId, attributeValueName);
						list.add(mallSkuAttributeDTO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("解析商品销售属性出错, 属性串 : {}, 错误信息 : ", attributes, e);
		}
		return list;
	}

	private MallSkuAttributeDTO getMallSkuAttributeDTO(Long attributeId, String attributeName, Integer attributeValueId,
			String attributeValueName) {
		MallSkuAttributeDTO mallSkuAttributeDTO = new MallSkuAttributeDTO();
		mallSkuAttributeDTO.setAttributeId(attributeId);
		mallSkuAttributeDTO.setAttributeName(attributeName);
		mallSkuAttributeDTO.setAttributeValueId(Long.valueOf(attributeValueId));
		mallSkuAttributeDTO.setAttributeValueName(attributeValueName);
		return mallSkuAttributeDTO;
	}

	private String getAttributeName(Long attributeId) {
		if (attributeId != null) {
			String attributeName = this.redisDB.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE + attributeId);
			if (StringUtils.isEmpty(attributeName)) {
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
			if (StringUtils.isEmpty(attributeValueName)) {
				return this.categoryAttrDAO.getAttrValueNameByAttrValueId(Long.valueOf(attributeValueId));
			} else {
				return attributeValueName;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取item主图
	 * 
	 * @param itemPictureList
	 *            商品item的所有图片
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

	@Override
	public ExecuteResult<List<NewPublishItemOutDTO>> queryNewPublishItemList(
			QueryNewPublishItemInDTO queryNewPublishItemInDTO) {
		ExecuteResult<List<NewPublishItemOutDTO>> result = new ExecuteResult<List<NewPublishItemOutDTO>>();
		if (queryNewPublishItemInDTO == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("queryNewPublishItemInDTO")));
			return result;
		}
		ValidateResult validateResult = DTOValidateUtil.validate(queryNewPublishItemInDTO);
		if (!validateResult.isPass()) {
			result.setCode(ErrorCodes.E10006.name());
			result.setErrorMessages(Lists.newArrayList(validateResult.getMessage()));
			return result;
		}
		// 优先使用商品+商品
		List<Item> itemList = Lists.newArrayList();

		if ("1".equals(queryNewPublishItemInDTO.getIsLoginFlag()) && !queryNewPublishItemInDTO.isBelongsHtd()) {// 已经登陆，则优先商品＋
			ProductPlusItemDTO productPlusItemDTO = new ProductPlusItemDTO();
			productPlusItemDTO.setSellerId(queryNewPublishItemInDTO.getSupplierId());
			productPlusItemDTO.setProductChannelCode(ItemChannelConstant.ITME_CHANNEL_OF_THIRD_PART_JD);
			Pager pager = new Pager();
			pager.setRows(queryNewPublishItemInDTO.getCount());
			ExecuteResult<DataGrid<ProductPlusItemDTO>> productPlusList = productPlusExportService
					.querySellerProductPlusProductList(productPlusItemDTO, pager);
			if (productPlusList.isSuccess() && productPlusList.getResult() != null
					&& CollectionUtils.isNotEmpty(productPlusList.getResult().getRows())) {
				List<ProductPlusItemDTO> rows = productPlusList.getResult().getRows();
				for (ProductPlusItemDTO itemPlusItem : rows) {
					if (StringUtils.isNotEmpty(queryNewPublishItemInDTO.getItemCode())
							&& queryNewPublishItemInDTO.getItemCode().equals(itemPlusItem.getItemCode())) {
						continue;
					}
					Item t = new Item();
					t.setItemCode(itemPlusItem.getItemCode());
					t.setItemName(itemPlusItem.getItemName());
					t.setItemId(itemPlusItem.getItemId());
					t.setSellerId(itemPlusItem.getSellerId());
					t.setCid(itemPlusItem.getCid());
					t.setBrand(itemPlusItem.getBrandId());
					t.setProductChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
					t.setListtingTime(itemPlusItem.getListtingTime());
					itemList.add(t);

				}
			}
		}

		// 如果不足，则用商家的商品补足
		if (itemList.size() < queryNewPublishItemInDTO.getCount() * 2) {
			List<Item> tempItemList = Lists.newArrayList();
			if ("0".equals(queryNewPublishItemInDTO.getSupplierType())) {
				AddressInfo currentAddress = addressUtil.getAddressName(queryNewPublishItemInDTO.getCurrentSiteCode());

				if (currentAddress != null) {
					AddressInfo parentAddress = addressUtil.getAddressName(currentAddress.getParentCode());
					queryNewPublishItemInDTO.setProvinceSiteCode(parentAddress.getCode());
				}

				List<AddressInfo> childAddressList = addressUtil
						.getAddressList(queryNewPublishItemInDTO.getCurrentSiteCode());

				if (CollectionUtils.isNotEmpty(childAddressList)) {
					List<String> childCodeList = Lists.newArrayList();
					for (AddressInfo childAddressInfo : childAddressList) {
						childCodeList.add(childAddressInfo.getCode());
					}

					queryNewPublishItemInDTO.setSiteChildCodeList(childCodeList);
				}

				tempItemList = itemMybatisDAO.queryInnerNewPublishItemList(queryNewPublishItemInDTO);
			}
			if ("1".equals(queryNewPublishItemInDTO.getSupplierType())) {
				tempItemList = itemMybatisDAO.queryOuterNewPublishItemList(queryNewPublishItemInDTO);
			}

			for (Item sellerItem : tempItemList) {
				itemList.add(sellerItem);
			}
		}

		// 按listtime降序排一下

		Collections.sort(itemList, new Comparator<Item>() {
			public int compare(Item o1, Item o2) {
				if (o1.getListtingTime() == null) {
					o1.setListtingTime(o1.getModified());
				}
				if (o2.getListtingTime() == null) {
					o2.setListtingTime(o2.getModified());
				}

				if (o1.getListtingTime() == null || o2.getListtingTime() == null) {
					return -1;
				}
				boolean result = o1.getListtingTime().before(o2.getListtingTime());
				return result ? 1 : -1;
			};
		});

		List<NewPublishItemOutDTO> newPublishItemOutList = Lists.newArrayList();
		for (Item item : itemList) {

			if (newPublishItemOutList.size() >= queryNewPublishItemInDTO.getCount()) {
				break;
			}

			NewPublishItemOutDTO newPublishItemOutDTO = new NewPublishItemOutDTO();
			List<ItemSku> skuList = itemSkuDAO.queryByItemId(item.getItemId());
			if (CollectionUtils.isNotEmpty(skuList)) {
				newPublishItemOutDTO.setSkuId(skuList.get(0).getSkuId());
			}
			if (ProductChannelEnum.JD_PRODUCT.getCode().equals(item.getProductChannelCode())) {
				newPublishItemOutDTO.setProdPlus(true);
			}
			newPublishItemOutDTO.setItemName(item.getItemName());
			newPublishItemOutDTO.setSupplierId(item.getSellerId());
			newPublishItemOutDTO.setThirdLevelCatId(item.getCid());
			newPublishItemOutDTO.setBrandId(item.getBrand());
			newPublishItemOutDTO.setItemCode(item.getItemCode());
			newPublishItemOutList.add(newPublishItemOutDTO);
		}

		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(newPublishItemOutList);
		return result;
	}

	private ItemPicture getFirstPicture(List<ItemPicture> picList) {
		if (CollectionUtils.isEmpty(picList)) {
			return null;
		}
		ItemPicture result = null;
		for (ItemPicture pic : picList) {
			result = pic;
			if (pic.getIsFirst() == 1) {
				break;
			}
		}
		return result;
	}

	@Override
	public ExecuteResult<List<MallSearchItemDTO>> queryMallSearchItemInfo(List<String> itemIdList) {
		ExecuteResult<List<MallSearchItemDTO>> result = new ExecuteResult<List<MallSearchItemDTO>>();

		if (CollectionUtils.isEmpty(itemIdList)) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemIdList")));
			return result;
		}
		List<MallSearchItemDTO> searchItemList = itemMybatisDAO.queryItemSearchListInfo(itemIdList);

		if (CollectionUtils.isNotEmpty(searchItemList)) {
			for (MallSearchItemDTO mallSearchItemDTO : searchItemList) {
				// 查询图片
				List<ItemPicture> pictures = itemPictureDAO.queryItemPicsById(mallSearchItemDTO.getItemId());
				if (CollectionUtils.isNotEmpty(pictures)) {
					for (ItemPicture p : pictures) {
						if (p.getIsFirst() == 1) {
							mallSearchItemDTO.setFirstPic(p);
							break;
						}
					}
				}

				if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(mallSearchItemDTO.getItemChannelCode())) {
					ExecuteResult<BigDecimal> priceResult = itemSkuPriceService
							.queryMinLadderPriceByItemId(mallSearchItemDTO.getItemId());
					if (priceResult.isSuccess() && priceResult.getResult() != null) {
						mallSearchItemDTO.setRetailPrice(priceResult.getResult());
					}
				} else {
					List<ItemSku> skuList = itemSkuDAO.queryByItemId(mallSearchItemDTO.getItemId());

					if (CollectionUtils.isNotEmpty(skuList) && skuList.get(0) != null) {
						// 查询价格
						ExecuteResult<ItemSkuBasePriceDTO> priceResult = itemSkuPriceService
								.queryItemSkuBasePrice(skuList.get(0).getSkuId());
						if (priceResult.isSuccess() && priceResult.getResult() != null) {
							mallSearchItemDTO.setRetailPrice(priceResult.getResult().getRetailPrice());
						}
					}

				}

			}
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(searchItemList);
		return result;
	}

	@Override
	public ExecuteResult<ItemSalesAreaDTO> queryInnerSupplierItemSalesArea(Long itemId, Integer isBoxFlag) {
		ExecuteResult<ItemSalesAreaDTO> result = new ExecuteResult<ItemSalesAreaDTO>();

		if (isBoxFlag == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("isBoxFlag")));
			return result;
		}

		if (itemId == null || itemId <= 0L) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemId")));
			return result;
		}

		ItemSalesArea itemSalesArea = itemSalesAreaMapper.selectByItemId(itemId, isBoxFlag == 1 ? "1" : "2");
		if (itemSalesArea == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("itemSalesArea")));
			return result;
		}
		ItemSalesAreaDTO itemSalesAreaDTO = new ItemSalesAreaDTO();
		if (itemSalesArea.getIsSalesWholeCountry() == 1) {
			itemSalesAreaDTO.setIsSalesWholeCountry(1);
			result.setResult(itemSalesAreaDTO);
			result.setCode(ErrorCodes.SUCCESS.name());
			return result;
		}
		List<ItemSalesAreaDetail> salesAreaDetailList = itemSalesAreaDetailMapper
				.selectAreaDetailsBySalesAreaId(itemSalesArea.getSalesAreaId());

		List<AddressInfo> cityList = Lists.newArrayList();
		for (ItemSalesAreaDetail salsAreaDetail : salesAreaDetailList) {
			// 省
			if ("1".equals(salsAreaDetail.getSalesAreaType()) && StringUtils.isNotEmpty(salsAreaDetail.getAreaCode())) {
				List<AddressInfo> addressInfoList = addressUtil.getAddressList(salsAreaDetail.getAreaCode());
				if (CollectionUtils.isNotEmpty(addressInfoList)) {
					cityList.addAll(addressInfoList);
				}
			}
			// 市
			if ("2".equals(salsAreaDetail.getSalesAreaType()) && StringUtils.isNotEmpty(salsAreaDetail.getAreaCode())) {
				AddressInfo addressInfo = addressUtil.getAddressName(salsAreaDetail.getAreaCode());
				if (addressInfo != null) {
					cityList.add(addressInfo);
				}
			}
			// 区
			if ("3".equals(salsAreaDetail.getSalesAreaType()) && StringUtils.isNotEmpty(salsAreaDetail.getAreaCode())) {
				AddressInfo districtAddressInfo = addressUtil.getAddressName(salsAreaDetail.getAreaCode());
				if (districtAddressInfo != null && StringUtils.isNotEmpty(districtAddressInfo.getCode())) {
					AddressInfo addressInfo = addressUtil.getAddressName(districtAddressInfo.getParentCode());
					if (addressInfo != null) {
						cityList.add(addressInfo);
					}
				}
			}

		}
		itemSalesAreaDTO.setIsSalesWholeCountry(0);
		itemSalesAreaDTO.setCityList(cityList);
		result.setResult(itemSalesAreaDTO);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	private void syncTotalStock(String skuCode, String supplierCode) {
		ItemSku itemSku = itemSkuDAO.selectItemSkuBySkuCode(skuCode);
		if (itemSku == null) {
			return;
		}
		// 查询库存上架信息
		List<ItemSkuPublishInfo> itemSkuPublishInfoList = itemSkuPublishInfoMapper
				.queryItemSkuShelfStatus(itemSku.getSkuId());

		if (CollectionUtils.isEmpty(itemSkuPublishInfoList)) {
			return;
		}

		Item item = itemMybatisDAO.queryItemByPk(itemSku.getItemId());

		if (item == null) {
			return;
		}
		ItemSpu spu = itemSpuMapper.selectById(item.getItemSpuId());
		if (spu == null) {
			return;
		}
		ItemStockResponseDTO itemStockResponse = MiddlewareInterfaceUtil.getSingleItemStock(supplierCode,
				spu.getSpuCode());

		if (itemStockResponse == null) {
			return;
		}

		Integer stockNum = (itemStockResponse.getStoreNum() == null || itemStockResponse.getStoreNum() <= 0) ? 0
				: itemStockResponse.getStoreNum();

		for (ItemSkuPublishInfo itemSkuPublishInfo : itemSkuPublishInfoList) {
			ItemSkuPublishInfoUtil.doUpdateItemSkuPublishInfo(0L, "system", itemSku, stockNum, itemSkuPublishInfo);
		}

	}

	@Override
	public ExecuteResult<List<HotSellItemOutDTO>> queryHotSellItemList(QueryHotSellItemInDTO queryHotSellItemInDTO) {

		ExecuteResult<List<HotSellItemOutDTO>> result = new ExecuteResult<List<HotSellItemOutDTO>>();
		if (queryHotSellItemInDTO == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("queryHotSellItemInDTO")));
			return result;
		}
		ValidateResult validateResult = DTOValidateUtil.validate(queryHotSellItemInDTO);
		if (!validateResult.isPass()) {
			result.setCode(ErrorCodes.E10006.name());
			result.setErrorMessages(Lists.newArrayList(validateResult.getMessage()));
			return result;
		}
		List<HotSellItemOutDTO> hotSellItemList = Lists.newArrayList();
		// 先判断供应商是否接入商品+
		if ("1".equals(queryHotSellItemInDTO.getIsLoginFlag()) && !queryHotSellItemInDTO.isBelongsHtd()) {
			SellerOuterProductChannel sellerOuterProductChannel = new SellerOuterProductChannel();
			sellerOuterProductChannel.setSellerId(queryHotSellItemInDTO.getSupplierId());
			sellerOuterProductChannel.setChannelCode(ProductChannelEnum.JD_PRODUCT.getCode());
			SellerOuterProductChannel sellerOuterProductChannelDb = this.sellerOuterProductChannelMapper
					.selectBySellerIdAndChannelCode(sellerOuterProductChannel);

			if (sellerOuterProductChannelDb != null && sellerOuterProductChannelDb.getAccessStatus() == 1) {
				// 优先查询热销商品+商品
				List<HotSellItemOutDTO> prodPlusHotSellList = itemMybatisDAO
						.queryProductPlusItemList(queryHotSellItemInDTO);
				for (HotSellItemOutDTO hotSellItemOutDTO : prodPlusHotSellList) {
					hotSellItemOutDTO.setProdPlus(true);
				}
				hotSellItemList.addAll(prodPlusHotSellList);
			}
		}

		if ("0".equals(queryHotSellItemInDTO.getSupplierType())) {
			AddressInfo currentAddress = addressUtil.getAddressName(queryHotSellItemInDTO.getCurrentSiteCode());

			if (currentAddress != null) {
				AddressInfo parentAddress = addressUtil.getAddressName(currentAddress.getParentCode());
				queryHotSellItemInDTO.setProvinceSiteCode(parentAddress.getCode());
			}

			List<AddressInfo> childAddressList = addressUtil.getAddressList(queryHotSellItemInDTO.getCurrentSiteCode());

			if (CollectionUtils.isNotEmpty(childAddressList)) {
				List<String> childCodeList = Lists.newArrayList();
				for (AddressInfo childAddressInfo : childAddressList) {
					childCodeList.add(childAddressInfo.getCode());
				}

				queryHotSellItemInDTO.setSiteChildCodeList(childCodeList);
			}
			List<HotSellItemOutDTO> innerHotSellList = itemMybatisDAO.queryInnerHotSellItemList(queryHotSellItemInDTO);
			hotSellItemList.addAll(innerHotSellList);
		}
		if ("1".equals(queryHotSellItemInDTO.getSupplierType())) {
			List<HotSellItemOutDTO> outerHotSellList = itemMybatisDAO.queryOuterHotSellItemList(queryHotSellItemInDTO);
			hotSellItemList.addAll(outerHotSellList);
		}

		Collections.sort(hotSellItemList, new Comparator<HotSellItemOutDTO>() {
			public int compare(HotSellItemOutDTO o1, HotSellItemOutDTO o2) {
				Long offVal = o2.getSalesVolume() - o1.getSalesVolume();
				return offVal.intValue();
			}
		});

		List<HotSellItemOutDTO> finalHotSellItemList = Lists.newArrayList();

		int i = 0;
		for (HotSellItemOutDTO hotSellItemOutDTO : hotSellItemList) {
			// List<ItemPicture>
			// itemPicList=itemPictureDAO.queryItemPicsById(hotSellItemOutDTO.getItemId());
			// hotSellItemOutDTO.setFirstPic(getFirstPicture(itemPicList));
			finalHotSellItemList.add(hotSellItemOutDTO);
			if (i >= 4) {
				break;
			}
			i++;
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		result.setResult(finalHotSellItemList);
		return result;
	}

	/**
	 * 判断销售区域
	 * 
	 * @param itemId
	 * @param isBoxFlag
	 * @param cityCode
	 * @return
	 */
	@Override
	public boolean isInSaleArea(Long itemId, Integer isBoxFlag, String cityCode) {
		if ("1".equals(cityCode)) {
			return true;
		}

		String parentCityCode = null;
		List<String> childCodeList = null;
		AddressInfo currentAddress = addressUtil.getAddressName(cityCode);
		if (currentAddress != null) {
			AddressInfo parentAddress = addressUtil.getAddressName(currentAddress.getParentCode());
			parentCityCode = parentAddress.getCode();
		}
		List<AddressInfo> childAddressList = addressUtil.getAddressList(cityCode);
		if (CollectionUtils.isNotEmpty(childAddressList)) {
			childCodeList = Lists.newArrayList();
			for (AddressInfo childAddressInfo : childAddressList) {
				childCodeList.add(childAddressInfo.getCode());
			}
		}
		int count = this.itemMybatisDAO.queryItemIsInSaleArea(itemId, isBoxFlag, parentCityCode, cityCode,
				childCodeList);
		return count >= 1;
	}

	/**
	 * 是否包厢商品 在销售区域上架
	 * 
	 * @param skuId
	 * @param cityCode
	 * @return 是否
	 */
	@Override
	public boolean isBoxProduct(Long skuId, String cityCode) {
		boolean isBoxProduct = false; // 包厢是否上架
		ItemSkuPublishInfo boxItemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId,
				Constants.SHELF_TYPE_IS_BOX, Constants.IS_VISABLE_TRUE);
		if (boxItemSkuPublishInfo != null) { // 包厢上架
			ItemSku itemSku = this.itemSkuDAO.queryItemSkuBySkuId(skuId);
			isBoxProduct = isInSaleArea(itemSku.getItemId(), 1, cityCode);// 包厢在此城市站发布
		}
		return isBoxProduct;
	}

	/**
	 * 是否区域商品 在销售区域上架
	 * 
	 * @param skuId
	 * @param cityCode
	 * @return
	 */
	@Override
	public boolean isAreaProduct(Long skuId, String cityCode) {
		boolean isBoxProduct = false; // 包厢是否上架
		if ("1".equals(cityCode)) {// 全国
			return true;
		}
		ItemSkuPublishInfo boxItemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByItemSkuAndShelfType(skuId,
				Constants.SHELF_TYPE_IS_AREA, Constants.IS_VISABLE_TRUE);
		if (boxItemSkuPublishInfo != null) { // 包厢上架
			ItemSku itemSku = this.itemSkuDAO.queryItemSkuBySkuId(skuId);
			isBoxProduct = isInSaleArea(itemSku.getItemId(), 0, cityCode);// 包厢在此城市站发布
		}
		return isBoxProduct;
	}

	/**
	 * <p>
	 * 推荐商品 A 推荐商品取运营中台推荐的0801的商品一款（取排序值最大的一款）和所有有包厢关系的供应商商品一款 B
	 * 有包厢关系的供应商商品取值：优先展示最近采购过的商品，如无采购商品取最近上架的商品。 C
	 * 所有商品排除无经营关系的包厢商品、不在会员注册地的区域商品、商品+商品、平台公司设置的秒杀商品。 D 所有商品按上架时间降序排序 E
	 * 平台公司商品价格：展示销售单价 F 销量取商品总销售量 G 商品图片、商品标题链接到商品详情页；供应商名称链接到供应商门户
	 * </p>
	 * `
	 *
	 * @param mallRecommendItemInDTO
	 *            入参
	 */
	@Override
	public ExecuteResult<DataGrid<MallRecommendItemOutDTO>> queryRecommendItemList(
			MallRecommendItemInDTO mallRecommendItemInDTO, Pager pager) {
		ExecuteResult<DataGrid<MallRecommendItemOutDTO>> executeResult = new ExecuteResult<>();
		DataGrid<MallRecommendItemOutDTO> dtoDataGrid = new DataGrid<>();
		List<MallRecommendItemOutDTO> mallRecommendItemOutDTOList = new ArrayList<>();
		try {
			// 参数校验
			if (mallRecommendItemInDTO == null || StringUtils.isEmpty(mallRecommendItemInDTO.getBuyerCode())
					|| mallRecommendItemInDTO.getBuyerId() == null
					|| StringUtils.isEmpty(mallRecommendItemInDTO.getAreaCode())
					|| mallRecommendItemInDTO.getBoxSellerDtoList() == null
					|| mallRecommendItemInDTO.getPromotionItemIdList() == null || pager == null) {
				logger.info("查询推荐商品校验不通过，错误信息：入参都不能为空, mallRecommendItemInDTO: {}, pager : {}",
						JSON.toJSONString(mallRecommendItemInDTO), JSON.toJSONString(pager));
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
				executeResult.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL.getMessage());
				executeResult.addErrorMsg("入参都不能为空");
				return executeResult;
			}
			// 获取0801推荐商品一个
			MallRecommendItemOutDTO htdMallRecommendItem = this.getOneRecommendItem(mallRecommendItemInDTO);
			if (htdMallRecommendItem != null) {
				mallRecommendItemOutDTOList.add(htdMallRecommendItem);
			}
			// 查询大B满足条件的一款商品 && 按照上架时间倒序排序
			List<MallRecommendItemOutDTO> oneItem4EveryBoxSellerDTOList = this
					.queryOneItem4AllBoxSeller(mallRecommendItemInDTO);
			// // 排除直营和查询大B满足条件的一款商品相同的商品
			// if (htdMallRecommendItem != null && oneItem4EveryBoxSellerDTOList
			// != null) {
			// for (MallRecommendItemOutDTO mallRecommendItemOutDTO :
			// oneItem4EveryBoxSellerDTOList) {
			// if
			// (mallRecommendItemOutDTO.getItemCode().equals(htdMallRecommendItem.getItemCode()))
			// {
			// oneItem4EveryBoxSellerDTOList.remove(mallRecommendItemOutDTO);
			// break;
			// }
			// }
			// }
			this.sortItemByListtingTime(oneItem4EveryBoxSellerDTOList);
			// 完成集合
			mallRecommendItemOutDTOList.addAll(oneItem4EveryBoxSellerDTOList);
			// 返回分页结果
			List<MallRecommendItemOutDTO> pagedItemOutDTOList = this.getLimitBoxSellerIdList(
					mallRecommendItemOutDTOList, pager.getPageOffset(), pager.getRows() + pager.getPageOffset());
			dtoDataGrid.setTotal(Long.valueOf(mallRecommendItemOutDTOList.size()));
			dtoDataGrid.setRows(pagedItemOutDTOList);
			executeResult.setResult(dtoDataGrid);
		} catch (Exception e) {
			logger.error("查询推荐商品出错，错误信息：", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMsg(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 获取0801推荐的一款商品
	 * 
	 * @return
	 */
	private MallRecommendItemOutDTO getOneRecommendItem(MallRecommendItemInDTO mallRecommendItemInDTO)
			throws Exception {
		MallRecommendItemOutDTO recommend0801Item = null;
		// 获取运营设置的总部推荐商品
		Map<String, String> resultMap = superbossProductPushDAO.queryHtdRecommendProduct();
		// 调用搜索中心接口，查询商品信息
		if (resultMap != null) {
			String itemCode = resultMap.get("itemCode");
			String htdFlag = resultMap.get("htdFlag");
			List<NameValuePair> params = new ArrayList<>();
			Long buyerId = mallRecommendItemInDTO.getBuyerId();
			String areaCode = mallRecommendItemInDTO.getAreaCode(); // 市
			params.add(new NameValuePair("buyerId", String.valueOf(buyerId)));
			params.add(new NameValuePair("addressCode", String.valueOf(areaCode)));
			List<String> itemCodeList = new ArrayList<String>();
			itemCodeList.add(itemCode);
			params.add(new NameValuePair("itemCodeList", JSON.toJSONString(itemCodeList)));
			ItemSearchResultDTO itemSearchResultDTO = this.requestSearch(params);
			if (itemSearchResultDTO != null && itemSearchResultDTO.getStatus().equals("success")) {
				List<ItemSearchItemDataDTO> itemDateList = itemSearchResultDTO.getDataList();
				ItemSearchItemDataDTO itemSearchItemDataDTO = null;
				if (itemDateList != null && itemDateList.size() > 0) {
					itemSearchItemDataDTO = itemDateList.get(0);
				}
				if (itemSearchItemDataDTO != null) {
					recommend0801Item = new MallRecommendItemOutDTO();
					recommend0801Item.setIsDirectItem(htdFlag.equals("1") ? 1 : 0);
					recommend0801Item.setItemCode(itemSearchItemDataDTO.getItemCode());
					recommend0801Item.setItemName(itemSearchItemDataDTO.getItemName());
					recommend0801Item.setItemId(Long.valueOf(itemSearchItemDataDTO.getItemId()));
					recommend0801Item.setSellerId(Long.valueOf(itemSearchItemDataDTO.getSellerId()));
					recommend0801Item.setShopId(Long.valueOf(itemSearchItemDataDTO.getShopId()));
					recommend0801Item.setSellerName(itemSearchItemDataDTO.getSellerName());
					recommend0801Item.setPictureUrl(itemSearchItemDataDTO.getImgURL());
					recommend0801Item.setSalePrice(BigDecimal.valueOf(itemSearchItemDataDTO.getPrice()));
					recommend0801Item.setSalesVolume(itemSearchItemDataDTO.getSalesVolume());
					recommend0801Item.setListtingTime(itemSearchItemDataDTO.getListtingTime());
				}
			}
		}
		return recommend0801Item;
	}

	/**
	 * 获取包厢关系大B符合条件的一款商品
	 * 
	 * @param mallRecommendItemInDTO
	 * @return
	 */
	private List<MallRecommendItemOutDTO> queryOneItem4AllBoxSeller(MallRecommendItemInDTO mallRecommendItemInDTO)
			throws Exception {
		List<MallRecommendItemOutDTO> mallRecommendItemOutDTOList = new ArrayList<>();
		// 1. 优先查询最近购买的商品：查询买家最近购买的商品 不在会员注册地的区域商品、商品+商品、平台公司设置的秒杀商品
		List<MemberShipDTO> boxSellerDtoList = mallRecommendItemInDTO.getBoxSellerDtoList();
		if (boxSellerDtoList.size() == 0) { // 如果包厢关系大B列表为0，直接返回0个商品
			return mallRecommendItemOutDTOList;
		}
		String buyerCode = mallRecommendItemInDTO.getBuyerCode();
		Long buyerId = mallRecommendItemInDTO.getBuyerId();
		List<Long> promotionIdList = mallRecommendItemInDTO.getPromotionItemIdList(); // 秒杀活动列表
		String areaCode = mallRecommendItemInDTO.getAreaCode(); // 市
		if (promotionIdList.size() == 0) {
			promotionIdList = null;
		}
		List<String> boxSellerCodeList = this.getBoxSellerCodeList(boxSellerDtoList);
		List<String> boxSellerIdList = this.getBoxSellerIdList(boxSellerDtoList);
		List<String> hasPurchaseRecordSellerIdList = new ArrayList<>();
		// 调用订单中心，批量查询大B的购买记录商品
		OrderRecentQueryPurchaseRecordsInDTO orderRecentQueryPurchaseRecordsInDTO = new OrderRecentQueryPurchaseRecordsInDTO();
		orderRecentQueryPurchaseRecordsInDTO.setBuyerCode(buyerCode);
		orderRecentQueryPurchaseRecordsInDTO.setBoxSellerCodeList(boxSellerCodeList);
		logger.info("开始调用订单中心查询最近购买记录, 入参 : {}", JSON.toJSONString(orderRecentQueryPurchaseRecordsInDTO));
		OrderRecentQueryPurchaseRecordsOutDTO queryPurchaseRecordsOutDTO = orderQueryAPI
				.queryPurchaseRecordsByBuyerCodeAndSellerCode(orderRecentQueryPurchaseRecordsInDTO);
		logger.info("结束调用订单中心查询最近购买记录, 结果 : {}", JSON.toJSONString(queryPurchaseRecordsOutDTO));
		if (ResultCodeEnum.SUCCESS.getCode().equals(queryPurchaseRecordsOutDTO.getResponseCode())) {
			// 购买记录list
			List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList = queryPurchaseRecordsOutDTO
					.getOrderRecentQueryPurchaseRecordOutDTOList();
			// 购买记录是否大于0
			if (orderRecentQueryPurchaseRecordOutDTOList != null
					&& orderRecentQueryPurchaseRecordOutDTOList.size() > 0) {
				// 批量检查这些item是否满足可买条件
				List<String> itemCodeList = this
						.getItemCodeListFromRecentQueryPurchaseRecord(orderRecentQueryPurchaseRecordOutDTOList);
				List<NameValuePair> params = new ArrayList<>();
				params.add(new NameValuePair("buyerId", String.valueOf(buyerId)));
				params.add(new NameValuePair("addressCode", String.valueOf(areaCode)));
				params.add(new NameValuePair("itemCodeList", JSON.toJSONString(itemCodeList)));
				ItemSearchResultDTO itemSearchResultDTO = this.requestSearch(params);
				if (itemSearchResultDTO != null && itemSearchResultDTO.getStatus().equals("success")) {
					List<ItemSearchItemDataDTO> itemDateList = itemSearchResultDTO.getDataList();
					for (OrderRecentQueryPurchaseRecordOutDTO orderRecentQueryPurchaseRecordOutDTO : orderRecentQueryPurchaseRecordOutDTOList) {
						// 按照购买记录的顺序 （查询出来的商品购买记录是降序的）
						for (ItemSearchItemDataDTO itemSearchItemDataDTO : itemDateList) {
							if (!boxSellerIdList.contains(itemSearchItemDataDTO.getSellerId())) {
								continue;
							}
							if (hasPurchaseRecordSellerIdList.contains(itemSearchItemDataDTO.getSellerId())) {
								continue;
							}
							// 购买记录中和搜索比较，如果在搜索结果中，则加入结果集
							if (orderRecentQueryPurchaseRecordOutDTO.getItemCode()
									.equals(itemSearchItemDataDTO.getItemCode())) {
								MallRecommendItemOutDTO mallRecommendItemOutDTO = new MallRecommendItemOutDTO();
								mallRecommendItemOutDTO.setIsDirectItem(0);
								mallRecommendItemOutDTO.setItemCode(itemSearchItemDataDTO.getItemCode());
								mallRecommendItemOutDTO.setItemName(itemSearchItemDataDTO.getItemName());
								mallRecommendItemOutDTO.setItemId(Long.valueOf(itemSearchItemDataDTO.getItemId()));
								mallRecommendItemOutDTO.setSellerId(Long.valueOf(itemSearchItemDataDTO.getSellerId()));
								mallRecommendItemOutDTO.setShopId(Long.valueOf(itemSearchItemDataDTO.getShopId()));
								mallRecommendItemOutDTO.setSellerName(itemSearchItemDataDTO.getSellerName());
								mallRecommendItemOutDTO.setPictureUrl(itemSearchItemDataDTO.getImgURL());
								mallRecommendItemOutDTO
										.setSalePrice(BigDecimal.valueOf(itemSearchItemDataDTO.getPrice()));
								mallRecommendItemOutDTO.setSalesVolume(itemSearchItemDataDTO.getSalesVolume());
								mallRecommendItemOutDTO.setListtingTime(itemSearchItemDataDTO.getListtingTime());
								mallRecommendItemOutDTOList.add(mallRecommendItemOutDTO);
								hasPurchaseRecordSellerIdList.add(itemSearchItemDataDTO.getSellerId());
							}
						}
					}
				}
			}
		}
		// 2. 查询搜索获取最新上架的商品（给每个大B）
		List<String> needSearchSolrSellerIdList = this.getNeedSearchSolrSellerIdList(boxSellerIdList,
				hasPurchaseRecordSellerIdList);
		if (needSearchSolrSellerIdList.size() > 0) {
			mallRecommendItemOutDTOList.addAll(
					this.queryNewestItemBySearchor(buyerId, needSearchSolrSellerIdList, areaCode, promotionIdList));
		}
		return mallRecommendItemOutDTOList;
	}

	/**
	 * 从购买记录中提取itemCode
	 * 
	 * @param orderRecentQueryPurchaseRecordOutDTOList
	 * @return
	 */
	private List<String> getItemCodeListFromRecentQueryPurchaseRecord(
			List<OrderRecentQueryPurchaseRecordOutDTO> orderRecentQueryPurchaseRecordOutDTOList) {
		List<String> itemCodeList = new ArrayList<String>();
		for (OrderRecentQueryPurchaseRecordOutDTO orderRecentQueryPurchaseRecordOutDTO : orderRecentQueryPurchaseRecordOutDTOList) {
			itemCodeList.add(orderRecentQueryPurchaseRecordOutDTO.getItemCode());
		}
		return itemCodeList;
	}

	/**
	 * 查询搜索中心，获取大B对应的最新上架的商品
	 * 
	 * @param buyerId
	 * @param needSearchSolrSellerIdList
	 * @param areaCode
	 * @param promotionIdList
	 * @return
	 */
	private List<MallRecommendItemOutDTO> queryNewestItemBySearchor(Long buyerId,
			List<String> needSearchSolrSellerIdList, String areaCode, List<Long> promotionIdList) {
		List<MallRecommendItemOutDTO> mallRecommendItemOutDTOList = new ArrayList<>();
		try {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new NameValuePair("buyerId", String.valueOf(buyerId)));
			params.add(new NameValuePair("addressCode", String.valueOf(areaCode)));
			params.add(new NameValuePair("sellerIdList", JSON.toJSONString(needSearchSolrSellerIdList)));
			params.add(new NameValuePair("sort", "4")); // 上架时间降序
			if (promotionIdList != null && promotionIdList.size() > 0) {
				params.add(new NameValuePair("promotionItemIdList", JSON.toJSONString(promotionIdList)));
			}
			ItemSearchResultDTO itemSearchResultDTO = requestSearch(params);
			if (itemSearchResultDTO != null && itemSearchResultDTO.getStatus().equals("success")) {
				List<ItemSearchItemDataDTO> itemDateList = itemSearchResultDTO.getDataList();
				List<String> handledMemberCodeList = new ArrayList<>(); // 已经处理过的大B集合
				for (ItemSearchItemDataDTO itemSearchItemDataDTO : itemDateList) {
					if (handledMemberCodeList.contains(itemSearchItemDataDTO.getSellerId())) {
						continue;
					} else {
						MallRecommendItemOutDTO mallRecommendItemOutDTO = new MallRecommendItemOutDTO();
						mallRecommendItemOutDTO.setIsDirectItem(0);
						mallRecommendItemOutDTO.setItemCode(itemSearchItemDataDTO.getItemCode());
						mallRecommendItemOutDTO.setItemName(itemSearchItemDataDTO.getItemName());
						mallRecommendItemOutDTO.setItemId(Long.valueOf(itemSearchItemDataDTO.getItemId()));
						mallRecommendItemOutDTO.setSellerId(Long.valueOf(itemSearchItemDataDTO.getSellerId()));
						mallRecommendItemOutDTO.setShopId(Long.valueOf(itemSearchItemDataDTO.getShopId()));
						mallRecommendItemOutDTO.setSellerName(itemSearchItemDataDTO.getSellerName());
						mallRecommendItemOutDTO.setPictureUrl(itemSearchItemDataDTO.getImgURL());
						mallRecommendItemOutDTO.setSalePrice(BigDecimal.valueOf(itemSearchItemDataDTO.getPrice()));
						mallRecommendItemOutDTO.setSalesVolume(itemSearchItemDataDTO.getSalesVolume());
						mallRecommendItemOutDTO.setListtingTime(itemSearchItemDataDTO.getListtingTime());
						mallRecommendItemOutDTOList.add(mallRecommendItemOutDTO);
						// 已经获取到一个商品的，加入已经完成的大B集合中
						handledMemberCodeList.add(itemSearchItemDataDTO.getSellerId());
					}
				}
			} else {
				throw new Exception("调用搜索查询大B最新上架数据出错 ：" + JSON.toJSONString(itemSearchResultDTO));
			}
		} catch (Exception e) {
			logger.error("调用搜索查询大B最新上架数据出错, 错误信息：", e);
		}
		return mallRecommendItemOutDTOList;
	}

	/**
	 * post请求
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private ItemSearchResultDTO requestSearch(List<NameValuePair> params) throws Exception {
		ItemSearchResultDTO itemSearchResultDTO = null;
		HttpClient client = new HttpClient();
		String url = SysProperties.getProperty("search_center_search_url");
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setRequestBody(params.toArray(new NameValuePair[] {}));
		client.getHttpConnectionManager().getParams().setConnectionTimeout(200);
		client.getHttpConnectionManager().getParams().setSoTimeout(8000);
		client.executeMethod(postMethod);
		int code = postMethod.getStatusCode();
		if (code == HttpStatus.SC_OK) {
			String resp = postMethod.getResponseBodyAsString();
			itemSearchResultDTO = JSON.parseObject(resp, ItemSearchResultDTO.class);
		}
		return itemSearchResultDTO;
	}

	/**
	 * 分页返回商品
	 * 
	 * @param mallRecommendItemOutDTOList
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	private static List<MallRecommendItemOutDTO> getLimitBoxSellerIdList(
			List<MallRecommendItemOutDTO> mallRecommendItemOutDTOList, int startIndex, int endIndex) {
		List<MallRecommendItemOutDTO> limitRecommendItemList = new ArrayList<>();
		int size = mallRecommendItemOutDTOList.size();
		if (startIndex > endIndex || endIndex <= 0 || startIndex > size) { // 不在范围内，返回0个
			return limitRecommendItemList;
		}
		if (size < endIndex) {
			limitRecommendItemList = mallRecommendItemOutDTOList.subList(startIndex, size);
		} else {
			limitRecommendItemList = mallRecommendItemOutDTOList.subList(startIndex, endIndex);
		}
		return limitRecommendItemList;
	}

	private List<String> getBoxSellerCodeList(List<MemberShipDTO> boxSellerDtoList) {
		List<String> boxSellerCodeList = new ArrayList<>();
		for (MemberShipDTO memberShipDTO : boxSellerDtoList) {
			boxSellerCodeList.add(memberShipDTO.getMemberCode());
		}
		return boxSellerCodeList;
	}

	private List<String> getBoxSellerIdList(List<MemberShipDTO> boxSellerDtoList) {
		List<String> boxSellerIdList = new ArrayList<>();
		for (MemberShipDTO memberShipDTO : boxSellerDtoList) {
			boxSellerIdList.add(String.valueOf(memberShipDTO.getMemberId()));
		}
		return boxSellerIdList;
	}

	/**
	 * 所有包厢关系的大B集合 减去已经有购买记录的大B = 需要查询搜索的sellerId
	 * 
	 * @param boxSellerIdList
	 * @param hasPurchaseRecordSellerIdList
	 * @return
	 */
	private List<String> getNeedSearchSolrSellerIdList(List<String> boxSellerIdList,
			List<String> hasPurchaseRecordSellerIdList) {
		Set all = new HashSet(boxSellerIdList);
		Set handled = new HashSet(hasPurchaseRecordSellerIdList);
		all.removeAll(handled);
		List<String> needSearchList = new ArrayList<>();
		needSearchList.addAll(all);
		return needSearchList;
	}

	/**
	 * 按照上架时间排序
	 * 
	 * @param oneItem4AllBoxSellerDTOList
	 */
	private void sortItemByListtingTime(List<MallRecommendItemOutDTO> oneItem4AllBoxSellerDTOList) {
		Collections.sort(oneItem4AllBoxSellerDTOList, new Comparator<MallRecommendItemOutDTO>() {
			@Override
			public int compare(MallRecommendItemOutDTO o1, MallRecommendItemOutDTO o2) {
				if (o1.getListtingTime() == null || o2.getListtingTime() == null) {
					return 1;
				}
				if (o1.getListtingTime().getTime() > o2.getListtingTime().getTime()) {
					return -1;
				}
				if (o1.getListtingTime().getTime() < o2.getListtingTime().getTime()) {
					return 1;
				}
				return 0;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecuteResult<MallMobileItemOutDTO> queryMobileItemDetail(String itemCode) {
		ExecuteResult<MallMobileItemOutDTO> result = new ExecuteResult<MallMobileItemOutDTO>();
		List<String> skuIds = new ArrayList<String>();
		try {
			Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
			if (item == null) {
				throw new Exception("查询不到item信息, itemCode : " + itemCode);
			}
			List<SkuInfoDTO> list = this.itemMybatisDAO.queryItemSkuInfo(item.getItemId());
			if(null != list && !list.isEmpty()){
				for (SkuInfoDTO skuInfoDTO : list) {
					skuIds.add(skuInfoDTO.getSkuId() + "");
				}
				ItemSkuPublishInfo publishInfo = itemSkuPublishInfoMapper.queryMobileExternalPublishInfoBySkuId(skuIds);
				MallMobileItemOutDTO resultDTO = new MallMobileItemOutDTO();
				if(null != publishInfo){
					resultDTO.setMimQuantity(publishInfo.getMimQuantity());
					resultDTO.setMaxPurchaseQuantity(publishInfo.getMaxPurchaseQuantity());
					resultDTO.setIsPurchaseLimit(publishInfo.getIsPurchaseLimit());
				}
				List<ItemSkuLadderPrice> priceList = itemSkuPriceService.queryMobileExternalLadderPriceBySkuId(skuIds);
				if(null != priceList && !priceList.isEmpty()){
					Collections.sort(priceList, new LadderPriceComparator());
					resultDTO.setLadderMaxPrice(priceList.get(priceList.size() - 1).getPrice());
					resultDTO.setLadderMinPrice(priceList.get(0).getPrice());
				}
				result.setResult(resultDTO);
				result.setCode(ResultCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			result.addErrorMsg(e.getMessage());
			logger.error("根据商品itemCode查询默认skuCode出错, itemCode : {}", itemCode, e);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static class LadderPriceComparator implements Comparator { 
	    public int compare(Object object1, Object object2) {
	    	ItemSkuLadderPrice t1 = (ItemSkuLadderPrice) object1; 
	    	ItemSkuLadderPrice t2 = (ItemSkuLadderPrice) object2; 
		    return t1.getPrice().compareTo(t2.getPrice());
		} 
	} 
}

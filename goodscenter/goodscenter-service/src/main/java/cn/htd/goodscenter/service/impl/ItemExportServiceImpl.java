package cn.htd.goodscenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.dto.AddressInfo;
import cn.htd.common.middleware.ItemStockResponseDTO;
import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.common.middleware.MiddlewareResponseCodeEnum;
import cn.htd.common.middleware.MiddlewareResponseObject;
import cn.htd.common.mq.MQRoutingKeyConstant;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.AddressUtils;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.channel.ItemChannelConstant;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.SpringUtils;
import cn.htd.goodscenter.dao.CategoryAttrDAO;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ItemDraftDescribeMapper;
import cn.htd.goodscenter.dao.ItemDraftMapper;
import cn.htd.goodscenter.dao.ItemDraftPictureMapper;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSalesAreaDetailMapper;
import cn.htd.goodscenter.dao.ItemSalesAreaMapper;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.dao.ItemSkuTotalStockMapper;
import cn.htd.goodscenter.dao.ModifyDetailInfoMapper;
import cn.htd.goodscenter.dao.TradeInventoryDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.domain.ItemDraftDescribe;
import cn.htd.goodscenter.domain.ItemDraftPicture;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.domain.ItemSkuTotalStock;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemAdDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemAttrValueItemDTO;
import cn.htd.goodscenter.dto.ItemCatCascadeDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.dto.ItemDBDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemQueryInDTO;
import cn.htd.goodscenter.dto.ItemQueryOutDTO;
import cn.htd.goodscenter.dto.ItemShopCartDTO;
import cn.htd.goodscenter.dto.ItemShopCidDTO;
import cn.htd.goodscenter.dto.ItemStatusModifyDTO;
import cn.htd.goodscenter.dto.ItemWaringOutDTO;
import cn.htd.goodscenter.dto.SkuColorGroupPictureDTO;
import cn.htd.goodscenter.dto.SkuInfoDTO;
import cn.htd.goodscenter.dto.SkuPictureDTO;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.WaitAuditItemInfoDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemPlatLinkStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ProductChannelEnum;
import cn.htd.goodscenter.dto.indto.PauseItemInDTO;
import cn.htd.goodscenter.dto.indto.QueryItemStockDetailInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockSearchInDTO;
import cn.htd.goodscenter.dto.outdto.ItemToDoCountDTO;
import cn.htd.goodscenter.dto.outdto.QueryItemStockDetailOutDTO;
import cn.htd.goodscenter.dto.outdto.SyncItemStockSearchOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;
import cn.htd.goodscenter.service.ItemAttrValueItemExportService;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.ItemExportService;
import cn.htd.goodscenter.service.ItemSpuExportService;
import cn.htd.goodscenter.service.impl.stock.ItemSkuPublishInfoUtil;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import cn.htd.goodscenter.service.utils.ItemDTOToDomainUtil;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.middleware.common.message.erp.ProductMessage;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.dto.StandardPriceDTO;
import cn.htd.pricecenter.service.ItemSkuPriceService;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

@Service("itemExportService")
public class ItemExportServiceImpl implements ItemExportService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ItemExportServiceImpl.class);
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemPictureDAO itemPictureDAO;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private TradeInventoryDAO tradeInventoryDAO;
	@Resource
	private ItemCategoryDAO itemCategoryDAO;
	@Resource
	private ItemAttrValueItemExportService itemAttrValueItemExportService;
	@Resource
	private ItemDescribeDAO itemDescribeDAO;
	@Resource
	private ItemSkuTotalStockMapper itemSkuTotalStockMapper;
	@Resource
	private ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ItemSkuPriceService itemSkuPriceService;
	@Resource
	private ItemBrandExportService itemBrandExportService;
	@Resource
	private ItemDraftMapper itemDraftMapper;
	@Resource
	private ItemDraftDescribeMapper itemDraftDescribeMapper;
	@Resource
	private ItemDraftPictureMapper itemDraftPictureMapper;
	@Resource
	private ModifyDetailInfoMapper modifyDetailInfoMapper;
	@Resource
	private ItemSpuMapper itemSpuMapper;
	@Resource
	private ItemSpuExportService itemSpuExportService;
	@Resource
	private DictionaryUtils dictionaryUtils;
	@Resource
	private MemberBaseInfoService memberBaseInfoService;
	@Resource
	private ItemSalesAreaMapper itemSalesAreaMapper;
	@Resource
	private ItemSalesAreaDetailMapper itemSalesAreaDetailMapper;
	@Autowired
	private AddressUtils addressUtil;

	/**
	 * <p>
	 * Discription:[方法功能中文描述:批量修改商品状态]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyItemStatusBatch(
			ItemStatusModifyDTO statusDTO) {
		LOGGER.info("================开始批量修改商品状态=====================");
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (statusDTO.getItemStatus() == null) {
				executeResult.addErrorMessage("商品状态为空！");
				return executeResult;
			}
			if (statusDTO.getOperator() == null) {
				executeResult.addErrorMessage("Operator为空！");
				return executeResult;
			}
			List<Long> ids = statusDTO.getItemIds();
			if (ids == null || ids.size() <= 0) {
				executeResult.addErrorMessage("商品ID为空！");
				return executeResult;
			}
			// 平台下架商品 下架通过平台商品发布的所有商品
			if (2 == statusDTO.getOperator()
					&& statusDTO.getItemStatus() == ItemStatusEnum.UNSHELVED
							.getCode()) {
				List<ItemDTO> items = null;
				List<Long> subIds = null;
				for (Long id : ids) {
					// 查询通过选择平台商品发布的商家商品
					items = Lists.newArrayList();// this.itemMybatisDAO.querySellerItems(id);
					// 下架所有商品
					subIds = new ArrayList<Long>();
					for (ItemDTO item : items) {
						subIds.add(item.getItemId());
					}
					if (subIds.isEmpty()) {
						continue;
					} else {
						itemMybatisDAO.updateItemStatusBatch(subIds,
								"平台对应商品已经下架！", statusDTO.getItemStatus(),
								statusDTO.getAuditFlag());
					}
				}
				this.itemMybatisDAO.updateItemStatusBatch(ids,
						statusDTO.getChangeReason(), statusDTO.getItemStatus(),
						statusDTO.getAuditFlag());
				// 商家上架商品 校验平台商品状态 平台商品下架的 该商品不能上架
			} else if (1 == statusDTO.getOperator()
					&& statusDTO.getItemStatus() == ItemStatusEnum.SALING
							.getCode()) {
				itemMybatisDAO.updateItemStatusBatch(
						this.getInputItemIds(statusDTO),
						statusDTO.getChangeReason(), statusDTO.getItemStatus(),
						statusDTO.getAuditFlag());
				// 审核通过卖家商品 并且要加入到平台商品库 的商品 查询出原有商品 并且加入平台商品库
			} else if (1 == statusDTO.getOperator()
					&& statusDTO.getItemStatus() == ItemStatusEnum.SHELVING
							.getCode()) {
				List<Long> modifyItemIds = this.getInputItemIds(statusDTO);
				statusDTO.setItemIds(modifyItemIds);
				if (!modifyItemIds.isEmpty()) {
					if (statusDTO.isCreatePlatItem()) {
						this.copyItemToPlat(statusDTO);
					}
					itemMybatisDAO
							.updateItemStatusBatch(modifyItemIds,
									statusDTO.getChangeReason(),
									statusDTO.getItemStatus(),
									statusDTO.getAuditFlag());
				}
			} else {
				// 其他修改
				itemMybatisDAO.updateItemStatusBatch(ids,
						statusDTO.getChangeReason(), statusDTO.getItemStatus(),
						statusDTO.getAuditFlag());
			}
			executeResult.setResultMessage("操作成功");
		} catch (Exception e) {
			LOGGER.error("执行方法【modifyItemStatusById】报错：{}", e);
			executeResult.addErrorMessage("执行方法【modifyItemStatusById】报错："
					+ e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("================结束批量修改商品状态=====================");
		}
		return executeResult;
	}

	/**
	 * <p>
	 * Discription:[过滤对应平台库商品 已经下架的 店铺商品]
	 * </p>
	 */
	private List<Long> getInputItemIds(ItemStatusModifyDTO statusDTO) {
		ItemQueryInDTO inDTO = new ItemQueryInDTO();
		List<Long> idList = new ArrayList<Long>();
		for (Long itemId : statusDTO.getItemIds()) {
			inDTO = new ItemQueryInDTO();
			inDTO.setId(Integer.parseInt(Long.toString(itemId)));
			List<ItemQueryOutDTO> items = this.itemMybatisDAO.queryItemList(
					inDTO, null);
			if (!items.isEmpty()) {
				inDTO.setId(null);
				inDTO.setPlatItemId(items.get(0).getItemId());
				List<ItemQueryOutDTO> dbItem = this.itemMybatisDAO
						.queryItemList(inDTO, null);
				if (dbItem != null && dbItem.size() > 0) {
					if (ItemStatusEnum.UNSHELVED.getCode() >= dbItem.get(0)
							.getItemStatus()) {
						continue;
					}
				}
			}

			idList.add(itemId);
		}
		return idList;
	}

	/**
	 * <p>
	 * Discription:[查询出原有商品 并且加入平台商品库]
	 * </p>
	 */
	private void copyItemToPlat(ItemStatusModifyDTO statusDTO) {
		for (Long itemId : statusDTO.getItemIds()) {
			ItemDTO item = this.getItemById(itemId,
					HtdItemStatusEnum.PASS.getCode()).getResult();
			if (item == null) {
				continue;
			}
			// 修改原商品为 已加入商品库
			ItemDTO param = new ItemDTO();
			param.setItemId(item.getItemId());
			param.setCopied(2);
			this.itemMybatisDAO.updateItem(param);
			// 设置加入平台商品库的商品属性
			item.setItemStatus(ItemStatusEnum.SALING.getCode());
			item.setListtingTime(new Date());
			item.setDelistingTime(null);
			item.setPlatLinkStatus(ItemPlatLinkStatusEnum.STORED.getCode());
			item.setOperator(2);// 平台商品
			item.setAddSource(null);
			item.setShopCid(null);
			item.setShopId(null);
			item.setSellerId(null);
			item.setPlstItemId(null);
			// 加入平台商品库
			this.addItemInfo(item);
			// 店铺商品入库时，该商品前台商品来源显示平台上传
			param.setAddSource(2);
			param.setPlstItemId(item.getItemId());
			this.itemMybatisDAO.updateItem(param);
		}
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述:查询商品的信息列表]
	 * </p>
	 */
	@Override
	public DataGrid<ItemQueryOutDTO> queryItemList(ItemQueryInDTO itemInDTO,
			Pager page) {
		DataGrid<ItemQueryOutDTO> dataGrid = new DataGrid<ItemQueryOutDTO>();
		try {
			// 状态列表
			// List<Integer> itemStatusList = new ArrayList<Integer>();
			// itemStatusList.add(0);
			// itemStatusList.add(1);
			// itemStatusList.add(2);
			// itemInDTO.setItemStatusList(itemStatusList);
			List<ItemQueryOutDTO> listItemDTO = itemMybatisDAO.queryItemList(
					itemInDTO, page);
			Long size = itemMybatisDAO.queryCount(itemInDTO);
			// List<SellPriceDTO> prices = null;
			List<ItemPicture> pics = null;
			for (ItemQueryOutDTO item : listItemDTO) {
				// 查询图片
				pics = this.itemPictureDAO.queryItemPicsById(item.getItemId());
				if (!pics.isEmpty()) {
					item.setPictureUrl(pics.get(0).getPictureUrl());
				}
				// 设置品牌
				ExecuteResult<ItemBrand> itemBrandResult = itemBrandExportService
						.queryItemBrandById(item.getBrand());
				if (itemBrandResult != null
						&& itemBrandResult.getResult() != null) {
					item.setBrandName(itemBrandResult.getResult()
							.getBrandName());
				}
				// 如果商品审核状态是待审核，需要查询临时表item_draft以及对应的图片和描述
				if (item.getItemStatus() == HtdItemStatusEnum.AUDITING
						.getCode()) {
					// 如果审核状态是待审核,商品列表中商品名称、品牌、类目需要展示临时表item_draft中未通过审核的数据.
					ItemDraft itemDraft = itemDraftMapper.selectByItemId(item
							.getItemId());
					if (itemDraft != null) {
						item.setItemName(itemDraft.getItemName());
						ExecuteResult<ItemBrand> executeResult = itemBrandExportService
								.queryItemBrandById(itemDraft.getBrand());
						if (executeResult != null && executeResult.isSuccess()) {
							item.setBrandName(executeResult.getResult()
									.getBrandName());
						}
						item.setCid(Integer.valueOf(String.valueOf(itemDraft
								.getCid())));
					}
				}
			}
			dataGrid.setTotal(size);
			dataGrid.setRows(listItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述:查询商品的信息列表]
	 * </p>
	 */
	public DataGrid<ItemQueryOutDTO> querySellerItemList(
			ItemQueryInDTO itemInDTO, Pager page) {
		DataGrid<ItemQueryOutDTO> dataGrid = new DataGrid<ItemQueryOutDTO>();
		try {
			List<ItemQueryOutDTO> listItemDTO = itemMybatisDAO.queryItemList(
					itemInDTO, page);
			Long size = itemMybatisDAO.queryCount(itemInDTO);
			if (listItemDTO != null && listItemDTO.size() > 0) {
				for (ItemQueryOutDTO outDto : listItemDTO) {
					// 销售属性
					String attrSale = outDto.getAttrSale();
					List<ItemAttrDTO> attrSales = null;
					if (StringUtils.isNotEmpty(attrSale)) {
						attrSales = this.getItemAttrList(attrSale);
						outDto.setAttrSales(attrSales);
					}
					// 非销售属性
					String attrStr = outDto.getAttributes();
					List<ItemAttrDTO> attrs = null;
					if (StringUtils.isNotEmpty(attrStr)) {
						attrs = this.getItemAttrList(attrStr);
						outDto.setAttributess(attrs);
					}
					// 根据cid查询类目属性
					ExecuteResult<List<ItemCatCascadeDTO>> er = itemCategoryService
							.queryParentCategoryList(3,
									Long.valueOf(outDto.getCid()));
					outDto.setItemCatCascadeDTO(er.getResult());
					List<VenusItemSkuOutDTO> venusItemSkuOutDTOs = itemSkuDAO
							.selectByItemIdAndSellerId(outDto.getItemId(),
									Long.valueOf(outDto.getSellerId()));
					if (venusItemSkuOutDTOs != null
							&& venusItemSkuOutDTOs.size() > 0) {
						for (VenusItemSkuOutDTO skuOut : venusItemSkuOutDTOs) {
							// 根据sku的销售属性keyId:valueId查询商品属性
							ExecuteResult<List<ItemAttrDTO>> itemAttr = itemCategoryService
									.queryCatAttrByKeyVals(skuOut
											.getAttributes());
							// 根据skuID查询对应ｓｋｕ下面的显示库存
							skuOut.setDisplayQuantity(itemSkuPublishInfoMapper
									.queryBySkuId(skuOut.getSkuId()).get(0)
									.getDisplayQuantity());
							skuOut.setItemAttr(itemAttr.getResult());
							DataGrid<ItemSkuLadderPrice> ladderList = itemSkuPriceService
									.queryLadderPriceBySellerIdAndSkuId(
											itemInDTO.getSellerId(),
											skuOut.getSkuId());
							if (ladderList.getRows() != null
									&& ladderList.getRows().size() > 0) {
								skuOut.setItemSkuLadderPrices(ladderList
										.getRows());
							}
						}
					}
					outDto.setVenusItemSkuOutDTOs(venusItemSkuOutDTOs);
				}
			}
			dataGrid.setRows(listItemDTO);
			dataGrid.setTotal(size);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}

		return dataGrid;
	}

	@Override
	public ExecuteResult<String> modifySellerItemStatus(ItemStatusModifyDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (dto.getItemStatus() == null) {
				result.addErrorMessage("商品状态为空！");
				return result;
			}
			List<Long> ids = dto.getItemIds();
			if (ids == null || ids.size() <= 0) {
				result.addErrorMessage("商品ID为空！");
				return result;
			}
			itemMybatisDAO.updateShopItemStatusByItemId(dto);
			result.setResultMessage("success");

		} catch (Exception e) {
			LOGGER.error("执行方法【updateShopItemStatusByItemId】报错：{}", e);
			result.addErrorMessage("执行方法【updateShopItemStatusByItemId】报错："
					+ e.getMessage());
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述：根据id查询详情]
	 * </p>
	 */
	@Override
	public ExecuteResult<ItemDTO> getItemById(Long itemId, Integer itemSatus) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		try {
			if (itemId == null) {
				result.addErrorMessage("itemId是null！");
				return result;
			}
			if (itemSatus == null) {
				result.addErrorMessage("itemSatus是null！");
				return result;
			}
			ItemDTO itemDTO = itemMybatisDAO.getItemDTOById(itemId);
			if (itemDTO == null) {
				result.addErrorMessage("没有该商品信息！");
				return result;
			}
			// 查询商品描述，并且设置到ItemDto中
			ItemDescribe itemDescribe = itemDescribeDAO.getDescByItemId(itemId);
			if (itemDescribe != null) {
				itemDTO.setItemDescribe(itemDescribe);
				itemDTO.setDescribeUrl(itemDescribe.getDescribeContent());
			}
			// 非销售属性
			String attrStr = itemDTO.getAttributesStr();
			List<ItemAttrDTO> attrs = null;
			if (StringUtils.isNotEmpty(attrStr)) {
				attrs = this.getItemAttrList(attrStr);
				itemDTO.setAttributes(attrs);
			}
			// 销售区域
			ItemSalesArea salesAreaPublic = itemSalesAreaMapper.selectByItemId(
					itemId, "2");
			if (null != salesAreaPublic) {
				if (salesAreaPublic.getIsSalesWholeCountry().intValue() == 0) {
					Map<String, Map<String, List<String>>> publicSalesAreaMap = assemblingItemArea(salesAreaPublic
							.getSalesAreaId());
					itemDTO.setPublicSalesAreaMap(publicSalesAreaMap);
				} else {
					itemDTO.setPublicSalesAreaMap(new HashMap<String, Map<String,List<String>>>());
				}
			}
			ItemSalesArea salesAreaBox = itemSalesAreaMapper.selectByItemId(
					itemId, "1");
			if (null != salesAreaBox) {
				if (salesAreaBox.getIsSalesWholeCountry().intValue() == 0) {
					Map<String, Map<String, List<String>>> boxSalesAreaMap = assemblingItemArea(salesAreaBox
							.getSalesAreaId());
					itemDTO.setBoxSalesAreaMap(boxSalesAreaMap);
				} else {
					itemDTO.setBoxSalesAreaMap(new HashMap<String, Map<String,List<String>>>());
				}
			}

			// 商品图片
			String[] picUrls = this.getItemPictures(itemDTO.getItemId());
			itemDTO.setPicUrls(picUrls);
			// 查询商品SKU 阶梯价 图片 库存 成本价
			List<SkuInfoDTO> skus = this.getItemSkuList(itemId,
					itemDTO.getProductChannelCode());
			itemDTO.setSkuInfos(skus);
			// 销售属性
			if (ProductChannelEnum.EXTERNAL_SUPPLIER.getCode().equals(
					itemDTO.getProductChannelCode())) {
				String attrSale = itemDTO.getAttrSaleStr();
				List<ItemAttrDTO> attrSales = null;
				if (StringUtils.isNotEmpty(attrSale)) {
					attrSales = this.getItemAttrList(attrSale);
					itemDTO.setAttrSale(attrSales);
				}
				// 添加：根据颜色分组的sku图片
				itemDTO.setSkuColorGroupPictureDTOList(this
						.getSkuColorGroupPictureDTOList(skus));
			}
			if (ProductChannelEnum.INTERNAL_SUPPLIER.getCode().equals(
					itemDTO.getProductChannelCode())) {
				List<ItemSku> itemSkus = itemSkuDAO.queryByItemId(itemId);
				ExecuteResult<StandardPriceDTO> standardPriceDTOExecuteResult = itemSkuPriceService
						.queryAllPrice4InnerSeller(itemSkus.get(0).getSkuId());
				ExecuteResult<MemberDetailInfo> memberDetailResult = memberBaseInfoService
						.getMemberDetailBySellerId(itemDTO.getSellerId());
				ItemSpu itemSpu = itemSpuMapper.selectById(Long.valueOf(itemDTO
						.getItemSpuId()));
				String floorPrice = null;
				String supplierCode = null;
				String itemSpuCode = null;
				if (itemSpu != null
						&& StringUtils.isNotEmpty(itemSpu.getSpuCode())
						&& memberDetailResult != null
						&& memberDetailResult.isSuccess()
						&& memberDetailResult.getResult() != null
						&& memberDetailResult.getResult()
								.getMemberBaseInfoDTO() != null
						&& StringUtils.isNotEmpty(memberDetailResult
								.getResult().getMemberBaseInfoDTO()
								.getMemberCode())) {
					// use memberCode like 'htd218861',not companyCode
					supplierCode = memberDetailResult.getResult()
							.getMemberBaseInfoDTO().getMemberCode();// getCompanyCode();
					itemSpuCode = itemSpu.getSpuCode();

					floorPrice = MiddlewareInterfaceUtil.findItemFloorPrice(
							supplierCode, itemSpuCode);
				}
				if (standardPriceDTOExecuteResult.getResult() != null
						&& standardPriceDTOExecuteResult.getResult()
								.getItemSkuBasePrice() != null
						&& floorPrice != null) {
					standardPriceDTOExecuteResult.getResult()
							.getItemSkuBasePrice()
							.setSaleLimitedPrice(new BigDecimal(floorPrice));
				}
				// itemDTO设置StandardPriceDTO
				itemDTO.setStandardPriceDTO(standardPriceDTOExecuteResult
						.getResult());
				// 查询总库存
				if (StringUtils.isNotEmpty(supplierCode)
						&& StringUtils.isNotEmpty(itemSpuCode)) {
					ItemStockResponseDTO itemStockResponse = MiddlewareInterfaceUtil
							.getSingleItemStock(supplierCode, itemSpuCode);
					Integer stockNum = (itemStockResponse == null
							|| itemStockResponse.getStoreNum() == null || itemStockResponse
							.getStoreNum() <= 0) ? 0 : itemStockResponse
							.getStoreNum();
					itemDTO.setInventory(stockNum);
				}
			}
			
			//审核人
			itemDTO.setVerifyName("管理员");
			//审核状态
			itemDTO.setVerifyStatus(1);
			
			// 当审核状态是待审核时,商品信息、图片、描述读取临时表item_draft中的数据．
			if (HtdItemStatusEnum.AUDITING.getCode() == itemSatus
					|| HtdItemStatusEnum.REJECTED.getCode() == itemSatus) {
				// 如果审核状态是待审核,商品列表中商品名称、品牌、类目需要展示临时表item_draft中未通过审核的数据.
				ItemDraft itemDraft = itemDraftMapper.selectByItemId(itemDTO
						.getItemId());
				if (itemDraft != null) {
					// itemDTO.setItemName(itemDraft.getItemName());
					BeanUtils.copyProperties(itemDraft, itemDTO);
					ExecuteResult<ItemBrand> executeResult = itemBrandExportService
							.queryItemBrandById(itemDraft.getBrand());
					if (executeResult != null && executeResult.isSuccess()) {
						itemDTO.setBrandName(executeResult.getResult()
								.getBrandName());
					}
					itemDTO.setAttrSaleStr(itemDraft.getAttrSale());
					itemDTO.setAttributesStr(itemDraft.getAttributes());
					// 长
					if (itemDraft.getLength() != null) {
						itemDTO.setLength(itemDraft.getLength().toString());
					}
					// 宽
					if (itemDraft.getWidth() != null) {
						itemDTO.setWidth(itemDraft.getWidth().toString());
					}
					// 高
					if (itemDraft.getHeight() != null) {
						itemDTO.setHeight(itemDraft.getHeight().toString());
					}
					// 描述
					ItemDraftDescribe itemDraftDescribe = itemDraftDescribeMapper
							.selectByItemDraftId(itemDraft.getItemDraftId());
					ItemDescribe itemDescribeAuding = new ItemDescribe();
					BeanUtils.copyProperties(itemDraftDescribe,
							itemDescribeAuding, new String[] {
									"itemDraftDesId", "itemDraftId" });
					itemDescribeAuding.setItemId(itemId);
					itemDTO.setItemDescribe(itemDescribeAuding);
					// 图片
					String[] draftPicUrls = this.getItemDraftPictures(itemDraft
							.getItemDraftId());
					//审核人
					if(itemDraft.getVerifyName() != null){
						itemDTO.setVerifyName(itemDraft.getVerifyName());
					}
					//审核状态
					if(itemDraft.getVerifyStatus() != null){
						itemDTO.setVerifyStatus(itemDraft.getVerifyStatus());
					}
					itemDTO.setPicUrls(draftPicUrls);
				}
			}
			result.setResult(itemDTO);
		} catch (Exception e) {
			LOGGER.error("执行方法【getItemById】报错:", e);
			result.addErrorMessage("执行方法【getItemById】报错:" + e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * 商品销售区域拼装
	 * 
	 * @return
	 */
	private Map<String, Map<String, List<String>>> assemblingItemArea(
			Long salesAreaId) {
		List<ItemSalesAreaDetail> saleDefaultAreaList = itemSalesAreaDetailMapper
				.selectAreaDetailsBySalesAreaIdAll(salesAreaId);
		List<String> areaFirstList = new ArrayList<String>();
		List<String> areaSecondList = new ArrayList<String>();
		List<String> areaThreeList = new ArrayList<String>();
		for (ItemSalesAreaDetail salesA : saleDefaultAreaList) {
			if (salesA.getAreaCode().length() == 2) {
				areaFirstList.add(salesA.getAreaCode());
			}
			if (salesA.getAreaCode().length() == 4) {
				areaSecondList.add(salesA.getAreaCode());
			}
			if (salesA.getAreaCode().length() == 6) {
				areaThreeList.add(salesA.getAreaCode());
			}
		}

		Map<String, List<String>> areaSecondThreesMap = new HashMap<String, List<String>>();
		for (String areaS : areaSecondList) {
			List<String> areaThreeTemporaryList = new ArrayList<String>();
			for (String areaT : areaThreeList) {
				String areaSecond = areaT.substring(0, 4);
				if (areaS.equals(areaSecond)) {
					AddressInfo addressT = addressUtil.getAddressName(areaT);
					if (addressT == null) {
						continue;
					}
					areaThreeTemporaryList.add(addressT.getName());
				}
			}
			areaSecondThreesMap.put(areaS, areaThreeTemporaryList);
		}

		Map<String, Map<String, List<String>>> areaFirstSecondThreeMap = new HashMap<String, Map<String, List<String>>>();
		for (String areaF : areaFirstList) {
			Map<String, List<String>> areaAssemblingMap = new HashMap<String, List<String>>();
			for (String key : areaSecondThreesMap.keySet()) {
				String areaFirstStr = key.substring(0, 2);
				if (areaFirstStr.equals(areaF)) {
					AddressInfo addressS = addressUtil.getAddressName(key);
					if (addressS == null) {
						continue;
					}
					areaAssemblingMap.put(addressS.getName(),
							areaSecondThreesMap.get(key));
				}
			}
			AddressInfo addressF = addressUtil.getAddressName(areaF);
			if (addressF == null) {
				continue;
			}
			areaFirstSecondThreeMap.put(addressF.getName(), areaAssemblingMap);
		}
		return areaFirstSecondThreeMap;
	}

	/**
	 * 获取按照颜色属性的分组图片
	 * 
	 * @param skuInfoDTOList
	 * @return
	 */
	private List<SkuColorGroupPictureDTO> getSkuColorGroupPictureDTOList(
			List<SkuInfoDTO> skuInfoDTOList) {
		List<SkuColorGroupPictureDTO> skuColorGroupPictureDTOList = new ArrayList();
		try {
			Map<String, List<SkuPictureDTO>> skuColorGroupPictureMap = new HashMap<>(); // 用map保证颜色不重复，
			for (SkuInfoDTO skuInfoDTO : skuInfoDTOList) {
				List<ItemAttrDTO> skuSaleAttributes = skuInfoDTO
						.getSkuSaleAttributes();
				List<SkuPictureDTO> skuPics = skuInfoDTO.getSkuPics();
				// 如果有颜色属性
				String color = null;
				for (ItemAttrDTO itemAttrDTO : skuSaleAttributes) {
					if ("颜色".equals(itemAttrDTO.getName())) { // TODO ：配置常量 属性
						List<ItemAttrValueDTO> values = itemAttrDTO.getValues(); // 属性值集合
						if (values == null) {
							continue;
						}
						for (ItemAttrValueDTO itemAttrValueDTO : values) {
							color = itemAttrValueDTO.getName();
						}
						break;
					}
				}
				if (color != null) {
					skuColorGroupPictureMap.put(color, skuPics);
				}
			}
			Iterator<String> iterator = skuColorGroupPictureMap.keySet()
					.iterator();
			while (iterator.hasNext()) {
				String color = iterator.next();
				SkuColorGroupPictureDTO skuColorGroupPictureDTO = new SkuColorGroupPictureDTO();
				skuColorGroupPictureDTO.setColor(color);
				skuColorGroupPictureDTO
						.setSkuPictureDTOList(skuColorGroupPictureMap
								.get(color));
				skuColorGroupPictureDTOList.add(skuColorGroupPictureDTO);
			}
		} catch (Exception e) {
			LOGGER.error("查询SKU按照颜色属性分组的图片出错, 错误信息 :", e);
		}
		return skuColorGroupPictureDTOList;
	}

	/**
	 * <p>
	 * Discription:[根据itemId 串，查询对应的item 数据信息]
	 * </p>
	 */
	@Override
	public List<ItemDTO> getItemDTOByItemIds(Long[] iids) {
		return this.itemMybatisDAO.getItemDTOByItemIds(iids);
	}

	/**
	 * <p>
	 * Discription:[获取商品图片]
	 * </p>
	 */
	private String[] getItemPictures(Long itemId) {
		List<ItemPicture> pics = this.itemPictureDAO.queryItemPicsById(itemId);
		List<String> picUrls = new ArrayList<String>();
		for (ItemPicture itemPicture : pics) {
			picUrls.add(itemPicture.getPictureUrl());
		}
		return picUrls.toArray(new String[] {});
	}

	/**
	 * <p>
	 * Discription:[获取临时表(item_draft_picture)商品图片]
	 * </p>
	 */
	private String[] getItemDraftPictures(Long itemDraftId) {
		List<ItemDraftPicture> itemDraftPictures = itemDraftPictureMapper
				.queryItemDraftPicsByDraftId(itemDraftId);
		List<String> picUrls = new ArrayList<String>();
		for (ItemDraftPicture itemDraftPicture : itemDraftPictures) {
			picUrls.add(itemDraftPicture.getPictureUrl());
		}
		return picUrls.toArray(new String[] {});
	}

	@Override
	public ExecuteResult<ItemDTO> addItemInfo(ItemDTO itemDTO) {
		LOGGER.info("=============开始发布商品====================");
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		try {
			// 校验空值
			if (itemDTO == null) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			Integer operator = itemDTO.getOperator();
			if (operator == 1) {// 卖家添加商品
				result = this.addSellerItem(itemDTO);
			} else if (operator == 2) {// 平台添加商品
				result = this.addPlatformItem(itemDTO);
			} else {
				result.addErrorMessage("orperator值不正确！" + operator);
				return result;
			}

			// 保存商品描述
			ItemDescribe itemDescribe = new ItemDescribe();
			itemDescribe.setItemId(result.getResult().getItemId());
			itemDescribe.setCreateId(itemDTO.getSellerId());
			itemDescribe.setCreateName(itemDTO.getCreateName());
			itemDescribe.setModifyId(itemDTO.getSellerId());
			itemDescribe.setModifyName(itemDTO.getModifyName());
			itemDescribe.setDescribeContent(itemDTO.getDescribeUrl());
			itemDescribe.setCreateTime(new Date());
			itemDescribe.setModifyTime(new Date());
			itemDescribeDAO.insert(itemDescribe);
		} catch (Exception e) {
			LOGGER.error("执行方法【addItemInfo】报错:", e);
			result.addErrorMessage("执行方法【addItemInfo】报错:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束发布商品====================");
		}
		return result;
	}

	/**
	 * 检查商品信息必需字段是否有值
	 * 
	 * @return
	 */
	private String checkItemInfo(ItemDTO itemDTO) {

		if (itemDTO == null) {
			return "商品信息为空";
		}
		String errorMsg = "";
		if (itemDTO.getItemId() == null) {
			errorMsg = errorMsg + "商品ID不能为空;";
		}
		if (itemDTO.getSellerId() == null) {
			errorMsg = errorMsg + "卖家ID不能为空;";
		}
		if (itemDTO.getShopCid() == null) {
			errorMsg = errorMsg + "店铺类目ID不能为空;";
		}
		if (StringUtils.isBlank(itemDTO.getItemName())) {
			errorMsg = errorMsg + "商品名称不能为空;";
		}
		if (itemDTO.getCid() == null) {
			errorMsg = errorMsg + "类目ID不能为空;";
		}
		if (itemDTO.getBrand() == null) {
			errorMsg = errorMsg + "品牌ID不能为空;";
		}
		if (itemDTO.getItemStatus() == null) {
			errorMsg = errorMsg + "商品状态不能为空;";
		}
		if (itemDTO.getShopId() == null) {
			errorMsg = errorMsg + "店铺ID不能为空;";
		}
		if (StringUtils.isBlank(itemDTO.getAttrSaleStr())) {
			errorMsg = errorMsg + "商品销售属性不能为空;";
		}
		if (itemDTO.getShopFreightTemplateId() == null) {
			errorMsg = errorMsg + "商品运费模板不能为空;";
		}

		return errorMsg;

	}

	/**
	 * 保存修改后的供应商商品待审核信息及商品图片信息
	 * 
	 * @param itemDTO
	 * @return
	 */
	@Transactional
	@Override
	public ExecuteResult<ItemDTO> modifyItemById(ItemDTO itemDTO) {
		LOGGER.info("=============开始修改商品信息=================");
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		try {
			// 校验空值
			if (itemDTO == null) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(itemDTO
					.getItemId());
			if (dbItem == null) {// 商品信息不能为空
				result.addErrorMessage("没有查询到该商品信息！");
				return result;
			}

			itemDTO.setCreateId(dbItem.getCreateId());
			itemDTO.setCreateName(dbItem.getCreateName());
			itemDTO.setCreated(dbItem.getCreated());
			// 校验商品信息一些必须字段是否有值
			String checkResult = checkItemInfo(itemDTO);
			if (StringUtils.isNotBlank(checkResult)) {
				result.addErrorMessage(checkResult);
				return result;
			}
			// 更改记录 存到modify_detail_info 商品详情页更改记录都要保存到这个表，包括所有的字段
			// itemDTO前台 dbItem：数据库
			if (dbItem != null) {// 如果数据库中存在这条记录．
				// saveChangedRecord(itemDTO,dbItem);//replace by zhangxiaolong
				// ModifyDetailInfoUtil.saveChangedRecord(itemDTO, dbItem);
			}
			// 商品只有审核通过或者不通过才可以修改
			int passCode = HtdItemStatusEnum.PASS.getCode();
			int rejectedCode = HtdItemStatusEnum.REJECTED.getCode();
			Integer itemStatus = itemDTO.getItemStatus();
			if (itemStatus != null && itemStatus != passCode
					&& itemStatus != rejectedCode) {
				itemDTO.setErpFirstCategoryCode(null);
				itemDTO.setErpFiveCategoryCode(null);
				// itemDTO.setItemStatus(HtdItemStatusEnum.AUDITING.getCode());
			}
			// 审核状态不通过，修改保存的时候变为待审核
			if (itemStatus != null && itemStatus == rejectedCode) {
				itemDTO.setItemStatus(HtdItemStatusEnum.AUDITING.getCode());
			}

			String productChannelCode = itemDTO.getProductChannelCode();
			// productChannelCode = "10";
			// 外部供应商商品时, 如果商品状态为审核通过, 则更新商品状态为未上架
			if (itemStatus != null
					&& itemStatus.equals(HtdItemStatusEnum.PASS.getCode())) {
				if (productChannelCode
						.equals(ProductChannelEnum.EXTERNAL_SUPPLIER.getCode())) {
					itemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES
							.getCode());
				}
			}
			// 修改信息后进入待审核状态
			// itemDTO.setItemStatus(HtdItemStatusEnum.AUDITING.getCode());
			// itemDTO.setItemStatus(1);//内部供应商需要审核
			if (StringUtils.isNotEmpty(productChannelCode)
					&& productChannelCode
							.equals(ProductChannelEnum.EXTERNAL_SUPPLIER
									.getCode())) {
				// itemDTO.setItemStatus(2);//外部供应商不需要审核
				result = this.modifySellerItem(itemDTO);
			} else {
				// 平台公司直接推入模板库
				SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
				spuInfoDTO.setItemId(itemDTO.getItemId());
				spuInfoDTO.setCreateId(itemDTO.getModifyId());
				spuInfoDTO.setCreateName(itemDTO.getModifyName());
				spuInfoDTO.setCreateTime(new Date());
				itemSpuExportService.addItemSpuInfo(spuInfoDTO);
				result = this.modifyPlatformItem(itemDTO);
			}

		} catch (Exception e) {
			LOGGER.error("执行方法【modifyItemById】报错:{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("=============结束修改商品信息=================");
		}

		return result;
	}

	@Override
	public ExecuteResult<ItemShopCartDTO> getSkuShopCart(ItemShopCartDTO skuDTO) {
		ExecuteResult<ItemShopCartDTO> result = new ExecuteResult<ItemShopCartDTO>();
		try {
			if (skuDTO == null) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			ItemSku sku = this.itemMybatisDAO.getItemSkuById(skuDTO);
			if (sku == null) {
				result.addErrorMessage("没有查询到该SKU！");
				return result;
			}

			List<ItemAttrDTO> attrList = this.getItemAttrList(sku
					.getAttributes());
			skuDTO.setAttrSales(attrList);
			// skuDTO.setItemName(sku.getItemName());
			// skuDTO.setCid(sku.getCid());
			// skuDTO.setItemStatus(sku.getItemStatus());
			// skuDTO.setSkuScope(sku.getSkuScope());
			// skuDTO.setHasPrice(sku.getHasPrice() == 1 ? true : false);
			List<SkuPictureDTO> pics = this.itemMybatisDAO.querySkuPics(sku
					.getSkuId());
			if (pics != null && pics.size() > 0) {
				skuDTO.setSkuPicUrl(pics.get(0).getPicUrl());
			}
			skuDTO.setSkuPics(pics);
			// 查询价格
			// BigDecimal skuPrice = this.getSkuPrice(skuDTO);
			// skuDTO.setSkuPrice(skuPrice);
			// 增加库存
			Integer qty = this.tradeInventoryDAO
					.queryBySkuId(skuDTO.getSkuId()).getTotalInventory();
			skuDTO.setQty(qty);
			result.setResult(skuDTO);
		} catch (Exception e) {
			result.addErrorMessage("报错信息：" + e.getMessage());
			LOGGER.error("执行方法【getSkuShopCart】报错：{}", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyItemAdBatch(List<ItemAdDTO> ads) {
		LOGGER.info("================开始批量修改商品广告词=============");
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (ads == null || ads.size() <= 0) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			this.itemMybatisDAO.modifyItemAdBatch(ads);
		} catch (Exception e) {
			result.addErrorMessage("执行方法【modifyItemAdBatch】报错：{}"
					+ e.getMessage());
			LOGGER.error("执行方法【modifyItemAdBatch】报错：{}", e.getMessage());
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("================结束批量修改商品广告词=============");
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyItemShopCidBatch(
			List<ItemShopCidDTO> cids) {
		LOGGER.info("============开始批量修改商品店铺分类=============");
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (cids == null || cids.size() <= 0) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			this.itemMybatisDAO.modifyItemShopCidBatch(cids);
		} catch (Exception e) {
			result.addErrorMessage("执行方法【modifyItemShopCidBatch】报错:{}"
					+ e.getMessage());
			LOGGER.info("执行方法【modifyItemShopCidBatch】报错:{}", e);
			throw new RuntimeException(e);
		} finally {
			LOGGER.info("============开始批量修改商品店铺分类=============");
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[添加卖家商品]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> addSellerItem(ItemDTO itemDTO)
			throws Exception {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		// 校验空值
		result = this.checkExistSellerItemNull(itemDTO);
		if (!result.isSuccess()) {
			return result;
		}

		ExecuteResult<List<SkuInfoDTO>> skuResult = null;
		try {
			itemDTO.setItemCode(ItemCodeGenerator.generateItemCode());
			itemDTO.setProductChannelCode("20");
			itemDTO.setItemStatus(4);
			Item item = ItemDTOToDomainUtil.itemDTO2Item(itemDTO);
			// 先保存商品基本信息并返回itemId
			Long itemId = this.insertItem(item);

			itemDTO.setItemId(itemId);
			// 把商品Id和图片路径保存到商品图片表
			this.insertItemPics(itemDTO);
			// 保存商品SKU信息及阶梯价格信息
			skuResult = this.insertItemSkuInfo(itemDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setResult(itemDTO);
		itemDTO.setSkuInfos(skuResult.getResult());
		return result;
	}

	/**
	 * <p>
	 * Discription:[添加商品属性]
	 * </p>
	 *
	 * @param saleAttrs
	 *            属性列表
	 * @param attrType
	 *            属性类型:1:销售属性;2:非销售属性
	 */
	private void addItemAttrs(List<ItemAttrDTO> saleAttrs, int attrType,
			long itemId) {
		List<ItemAttrValueItemDTO> paramList = new ArrayList<ItemAttrValueItemDTO>();
		ItemAttrValueItemDTO dto = null;
		for (ItemAttrDTO attr : saleAttrs) {
			for (ItemAttrValueDTO value : attr.getValues()) {
				dto = new ItemAttrValueItemDTO();
				dto.setAttrId(attr.getId());
				dto.setAttrType(attrType);
				dto.setValueId(value.getId());
				dto.setItemId(itemId);
				paramList.add(dto);
			}
		}
		if (paramList.size() > 0) {
			this.itemAttrValueItemExportService.addItemAttrValueItem(paramList);
		}
	}

	/**
	 * <p>
	 * Discription:[保存SKU相关信息]
	 * </p>
	 */
	private ExecuteResult<List<SkuInfoDTO>> insertItemSkuInfo(ItemDTO itemDTO) {
		ExecuteResult<List<SkuInfoDTO>> result = new ExecuteResult<List<SkuInfoDTO>>();
		List<SkuInfoDTO> list = new ArrayList<SkuInfoDTO>();
		List<SkuInfoDTO> skus = itemDTO.getSkuInfos();
		ItemSku itemSku = null;
		for (SkuInfoDTO sku : skus) {
			if (sku.getAttributes() != null) {// 前台页面会传入空的无效SKU 这里过滤
				itemSku = new ItemSku();
				itemSku.setSkuCode(ItemCodeGenerator.generateSkuCode());
				itemSku.setAttributes(sku.getAttributes());
				itemSku.setItemId(itemDTO.getItemId());
				itemSku.setSubTitle(sku.getSubTitle());
				itemSku.setSellerId(itemDTO.getSellerId());
				itemSku.setShopId(itemDTO.getShopId());
				itemSku.setSkuStatus(1);
				itemSku.setCreateId(itemDTO.getCreateId());
				itemSku.setCreateName(itemDTO.getCreateName());
				itemSku.setCreated(new Date());
				itemSku.setModifyId(itemDTO.getModifyId());
				itemSku.setModifyName(itemDTO.getModifyName());
				itemSku.setModified(new Date());
				itemSku.setEanCode(sku.getEanCode());
				// 插入SKU表
				this.itemSkuDAO.add(itemSku);
				sku.setSkuId(itemSku.getSkuId());
				sku.setSkuCode(itemSku.getSkuCode());
				SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
				BeanUtils.copyProperties(sku, skuInfoDTO);
				list.add(skuInfoDTO);
				// 插入SKU 图片
				this.inserItemSkuPictures(sku, itemDTO);
				// 插入SKU区域阶梯价 和 SKU区域阶梯价日志
				this.insertItemSkuSellPrices(sku, itemDTO);
				// 插入SKU库存表
				this.insertItemSkuInventory(sku, itemDTO);
			}
		}
		result.setResult(list);
		return result;
	}

	/**
	 * <p>
	 * Discription:[插入SKU库存表]
	 * </p>
	 */
	private void insertItemSkuInventory(SkuInfoDTO sku, ItemDTO itemDTO) {
		List<ItemSkuPublishInfo> itemSkuPublishInfos = sku
				.getItemSkuPublishInfos();
		for (ItemSkuPublishInfo itemSkuPublishInfo : itemSkuPublishInfos) {
			itemSkuPublishInfo.setItemId(itemDTO.getItemId());
			itemSkuPublishInfo.setSkuId(sku.getSkuId());
			itemSkuPublishInfo.setSkuCode(sku.getSkuCode());
			itemSkuPublishInfo.setIsVisable(1);
			itemSkuPublishInfo.setCreateId(itemDTO.getCreateId());
			itemSkuPublishInfo.setCreateName(itemDTO.getCreateName());
			itemSkuPublishInfo.setModifyId(itemDTO.getModifyId());
			itemSkuPublishInfo.setModifyName(itemDTO.getModifyName());
			itemSkuPublishInfo.setCreateTime(new Date());
			itemSkuPublishInfo.setModifyTime(new Date());
			itemSkuPublishInfoMapper.add(itemSkuPublishInfo);
		}
	}

	/**
	 *
	 * <p>
	 * Discription:[插入SKU区域阶梯价 和 SKU区域阶梯价日志]
	 * </p>
	 */
	private void insertItemSkuSellPrices(SkuInfoDTO sku, ItemDTO itemDTO) {
		LOGGER.info("======开始插入SKU价格=======");
		List<ItemSkuLadderPrice> skuLadders = sku.getSkuLadderPrices();
		LOGGER.info("传入参数：" + JSON.toJSONString(sku.getSkuLadderPrices()));
		if (skuLadders != null && skuLadders.size() > 0) {
			for (ItemSkuLadderPrice skuLadder : skuLadders) {
				skuLadder.setItemId(itemDTO.getItemId());
				skuLadder.setSkuId(sku.getSkuId());
				skuLadder.setSellerId(itemDTO.getSellerId());
				skuLadder.setShopId(itemDTO.getShopId());
				skuLadder.setDeleteFlag(0);
				skuLadder.setCreateId(itemDTO.getCreateId());
				skuLadder.setCreateName(itemDTO.getCreateName());
				skuLadder.setModifyId(itemDTO.getModifyId());
				skuLadder.setModifyName(itemDTO.getModifyName());
				skuLadder.setCreateTime(new Date());
				skuLadder.setModifyTime(new Date());
				this.itemSkuPriceService.addItemSkuLadderPrice(skuLadder);
			}
		}
		LOGGER.info("======结束插入SKU价格=======");
	}

	/**
	 * <p>
	 * Discription:[插入SKU 图片]
	 * </p>
	 */
	private void inserItemSkuPictures(SkuInfoDTO sku, ItemDTO itemDTO) {
		ItemSkuPicture skuPic = null;
		for (SkuPictureDTO pic : sku.getSkuPics()) {
			if (!StringUtils.isBlank(pic.getPicUrl())) { // 去掉空值
				skuPic = new ItemSkuPicture();
				skuPic.setItemId(itemDTO.getItemId());
				skuPic.setPictureUrl(pic.getPicUrl());
				skuPic.setPictureStatus("1");
				skuPic.setSellerId(itemDTO.getSellerId());
				skuPic.setShopId(itemDTO.getShopId());
				skuPic.setSkuId(sku.getSkuId());
				skuPic.setSortNumber(pic.getSortNumber());
				skuPic.setSaleAttribute(pic.getSaleAttribute());
				skuPic.setCreateId(itemDTO.getCreateId());
				skuPic.setCreateName(itemDTO.getCreateName());
				skuPic.setModifyId(itemDTO.getModifyId());
				skuPic.setModifyName(itemDTO.getModifyName());
				this.itemSkuDAO.insertSkuPicture(skuPic);
			}
		}
	}

	/**
	 * <p>
	 * Discription:[卖家添加商品时校验空值]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> checkExistSellerItemNull(ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		if (itemDTO.getSellerId() == null) {
			result.addErrorMessage("参数【SellerId】为空！");
			return result;
		}
		if (itemDTO.getShopCid() == null) {
			result.addErrorMessage("参数【ShopCid】为空！");
			return result;
		}
		if (StringUtils.isBlank(itemDTO.getItemName())) {
			result.addErrorMessage("参数【ItemName】为空！");
			return result;
		}
		if (itemDTO.getCid() == null) {
			result.addErrorMessage("参数【Cid】为空！");
			return result;
		}
		if (itemDTO.getBrand() == null) {
			result.addErrorMessage("参数【Brand】为空！");
			return result;
		}
		if (itemDTO.getHasPrice() == null) {
			result.addErrorMessage("参数【HasPrice】为空！");
			return result;
		}

		if (itemDTO.getInventory() == null) {
			result.addErrorMessage("参数【Inventory】为空！");
			return result;
		}

		if (itemDTO.getWeight() == null) {
			result.addErrorMessage("参数【Weight】为空！");
			return result;
		}

		if (itemDTO.getLength() == null) {
			result.addErrorMessage("参数【Length】为空！");
			return result;
		}

		if (itemDTO.getWidth() == null) {
			result.addErrorMessage("参数【Width】为空！");
			return result;
		}

		if (itemDTO.getHeight() == null) {
			result.addErrorMessage("参数【Height】为空！");
			return result;
		}

		if (itemDTO.getNetWeight() == null) {
			result.addErrorMessage("参数【NetWeight】为空！");
			return result;
		}

		if (itemDTO.getTaxRate() == null) {
			result.addErrorMessage("参数【TaxRate】为空！");
			return result;
		}

		if (itemDTO.getModelType() == null && itemDTO.getModelType() != "") {
			result.addErrorMessage("参数【ModelType】为空！");
			return result;
		}

		if (itemDTO.getPicUrls() != null && itemDTO.getPicUrls().length <= 0) {
			result.addErrorMessage("参数【PicUrls】为空！");
			return result;
		}
		if (itemDTO.getShopId() == null) {
			result.addErrorMessage("参数【ShopId】为空！");
			return result;
		}
		if (StringUtils.isBlank(itemDTO.getAttrSaleStr())) {
			result.addErrorMessage("参数【AttrSaleStr】为空！");
			return result;
		}
		// if (itemDTO.getSellPrices() == null) {
		// result.addErrorMessage("参数【SellPrices】为空！");
		// return result;
		// }
		if (itemDTO.getSkuInfos() == null) {
			result.addErrorMessage("参数【SkuInfos】为空！");
			return result;
		}
		if (itemDTO.getOperator() == null) {
			result.addErrorMessage("参数【Operator】为空！");
			return result;
		}

		if (itemDTO.getShopFreightTemplateId() == null) {
			result.addErrorMessage("参数【ShopFreightTemplateId】为空！");
			return result;
		}

		// // 销售属性
		// if (itemDTO.getAttrSale() == null || itemDTO.getAttrSale().isEmpty())
		// {
		// result.addErrorMessage("参数【AttrSale】为空！");
		// return result;
		// }
		// // 非销售属性
		// if (itemDTO.getAttributes() == null ||
		// itemDTO.getAttributes().isEmpty()) {
		// result.addErrorMessage("参数【Attributes】为空！");
		// return result;
		// }
		return result;
	}

	/**
	 *
	 * <p>
	 * Discription:[插入商品区域价]
	 * </p>
	 */
	// private void insertItemSellPrices(ItemDTO itemDTO) {
	// if (null != itemDTO.getSellPrices()) {
	// for (SellPriceDTO sellPrice : itemDTO.getSellPrices()) {
	// if (sellPrice.getMinNum() != null) {
	// ItemPrice itemPrice = ItemDTOToDomainUtil.itemDTO2SellPrice(itemDTO,
	// sellPrice);
	// itemPrice.setItemId(itemDTO.getItemId());
	// this.itemPriceDAO.add(itemPrice);
	// }
	// }
	// }
	// }

	/**
	 * <p>
	 * Discription:[插入ITEM 表数据 ]
	 * </p>
	 */
	private Long insertItem(Item item) {
		itemMybatisDAO.addItem(item);
		return item.getItemId();
	}

	/**
	 * <p>
	 * Discription:[ 添加平台商品库商品 暂时保存： itemStatus:4 platLinkStatus:1 确定发布:
	 * itemStatus:4 platLinkStatus:3 ]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> addPlatformItem(ItemDTO itemDTO)
			throws Exception {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		// 校验空值
		result = this.checkExistPlatItemNull(itemDTO);
		if (!result.isSuccess()) {
			return result;
		}
		try {
			Item item = ItemDTOToDomainUtil.itemDTO2Item(itemDTO);
			// 先保存商品基本信息并返回itemId
			Long itemId = this.insertItem(item);
			// 保存平台商品非销售属性
			this.addItemAttrs(itemDTO.getAttributes(), 2, itemId);
			// 保存平台商品销售属性
			this.addItemAttrs(itemDTO.getAttrSale(), 1, itemId);
			itemDTO.setItemId(itemId);
			// 把商品Id和图片路径保存到商品图片表
			this.insertItemPics(itemDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setResult(itemDTO);
		return result;
	}

	/**
	 * <p>
	 * Discription:[把商品Id和图片路径保存到商品图片表]
	 * </p>
	 */
	private void insertItemPics(ItemDTO itemDTO) {
		// 把商品Id和图片路径保存到商品图片表
		ItemPicture itemPicture = null;
		String[] picUrls = itemDTO.getPicUrls();
		if (picUrls != null && picUrls.length > 0) {
			int i = 1;
			for (String pictureUrl : picUrls) {
				if (StringUtils.isNotEmpty(pictureUrl)) {
					itemPicture = new ItemPicture();
					if (i == 1) {
						itemPicture.setIsFirst(1);
					}
					itemPicture.setSortNumber(i);
					itemPicture.setItemId(itemDTO.getItemId());
					itemPicture.setSellerId(itemDTO.getSellerId());
					itemPicture.setShopId(itemDTO.getShopId() == null ? 0L
							: itemDTO.getShopId());
					itemPicture.setPictureUrl(pictureUrl);
					itemPicture.setPictureStatus(1);
					itemPicture.setCreateId(itemDTO.getModifyId());
					itemPicture.setCreateName(itemDTO.getModifyName());
					itemPicture.setModifyName(itemDTO.getModifyName());
					itemPicture.setModifyId(itemDTO.getModifyId());
					try {
						this.itemPictureDAO.add(itemPicture);
					} catch (Exception e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		}
	}

	/**
	 * <p>
	 * Discription:[检查平台商品必填参数是否存在空值]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> checkExistPlatItemNull(ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		if (StringUtils.isBlank(itemDTO.getItemName())) {
			result.addErrorMessage("参数【ItemName】为空！");
			return result;
		}
		if (itemDTO.getCid() == null) {
			result.addErrorMessage("参数【Cid】为空！");
			return result;
		}
		if (itemDTO.getBrand() == null) {
			result.addErrorMessage("参数【Brand】为空！");
			return result;
		}
		if (itemDTO.getHasPrice() == null) {
			result.addErrorMessage("参数【HasPrice】为空！");
			return result;
		}
		if (StringUtils.isBlank(itemDTO.getDescribeUrl())) {
			result.addErrorMessage("参数【DescribeUrl】为空！");
			return result;
		}
		if (itemDTO.getItemStatus() == null) {
			result.addErrorMessage("参数【ItemStatus】为空！");
			return result;
		}
		if (itemDTO.getPicUrls() == null || itemDTO.getPicUrls().length <= 0) {
			result.addErrorMessage("参数【PicUrls】为空！");
			return result;
		}
		if (itemDTO.getPlatLinkStatus() == null) {
			result.addErrorMessage("参数【PlatLinkStatus】为空！");
			return result;
		}
		// 非销售属性
		if (itemDTO.getAttributes() == null
				|| itemDTO.getAttributes().isEmpty()) {
			result.addErrorMessage("参数【Attributes】为空！");
			return result;
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[获取商品的SKU信息 查询商品SKU 阶梯价 图片 库存 成本价]
	 * </p>
	 */
	private List<SkuInfoDTO> getItemSkuList(Long itemId,
			String productChannelCode) throws Exception {
		List<SkuInfoDTO> list = this.itemMybatisDAO.queryItemSkuInfo(itemId);
		for (SkuInfoDTO sku : list) {
			// 阶梯价
			List<ItemSkuLadderPrice> skuLadderPrices = itemSkuPriceService
					.getSkuLadderPrice(sku.getSkuId());
			// 图片
			List<SkuPictureDTO> skuPics = this.itemMybatisDAO.querySkuPics(sku
					.getSkuId());
			if (StringUtils.isNotEmpty(productChannelCode)
					&& productChannelCode
							.equals(ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL)) {
				// 查询sku实际库存:只有内部供应商价格有实际库存，阶梯价只有显示库存。
				ItemSkuTotalStock itemSkuTotalStock = itemSkuTotalStockMapper
						.queryBySkuId(sku.getSkuId());
				sku.setSkuInventory(itemSkuTotalStock == null ? 0
						: itemSkuTotalStock.getInventory());
			}
			// 销售属性 外部供应商商品
			List<ItemAttrDTO> skuSaleAttributes = new ArrayList();
			if (productChannelCode
					.equals(ItemChannelConstant.ITME_CHANNEL_OF_EXTERNAL)) {
				skuSaleAttributes = this.getItemAttrList(sku.getAttributes());
			}
			// 查询商品sku库存:包括起订量和限定量．
			List<ItemSkuPublishInfo> itemSkuPublishInfos = itemSkuPublishInfoMapper
					.queryBySkuId(sku.getSkuId());
			sku.setItemSkuPublishInfos(itemSkuPublishInfos);
			sku.setSkuPics(skuPics);
			sku.setSkuLadderPrices(skuLadderPrices);
			sku.setSkuSaleAttributes(skuSaleAttributes);
		}
		return list;
	}

	/**
	 * <p>
	 * Discription:[根据属性字符串查询属性和属性值]
	 * </p>
	 */
	private List<ItemAttrDTO> getItemAttrList(String attributes) {
		List<ItemAttrDTO> resultList = new ArrayList();
		try {
			if (StringUtils.isNotEmpty(attributes)) {
				if (!attributes.startsWith("{")) {
					attributes = "{" + attributes;
				}
				if (!attributes.endsWith("}")) {
					attributes = attributes + "}";
				}
				// 使用json解析属性
				Map<String, Object> map = JSONObject.fromObject(attributes);
				if (map != null && !map.isEmpty()) {
					for (String attributeId : map.keySet()) {
						String attributeValeName = this.getAttributeName(Long
								.valueOf(attributeId));
						if (StringUtils.isNotEmpty(attributeValeName)) {
							ItemAttrDTO itemAttrDTO = new ItemAttrDTO();
							itemAttrDTO.setId(Long.valueOf(attributeId));
							itemAttrDTO.setName(attributeValeName);
							itemAttrDTO.setStatus(1);
							Object attributeValueObj = map.get(attributeId);
							if (attributeValueObj instanceof JSONArray) {
								JSONArray jsonArray = (JSONArray) attributeValueObj;
								int size = jsonArray.size();
								List<ItemAttrValueDTO> values = new ArrayList<>();
								for (int i = 0; i < size; i++) {
									if (jsonArray.get(i) == null
											|| !StringUtils.isNumeric(jsonArray
													.get(i) + "")) {
										LOGGER.error(
												"执行方法【getItemAttrList】attributeValueId",
												jsonArray.get(i));
										continue;
									}
									Integer attributeValueId = Integer
											.valueOf(jsonArray.get(i) + "");
									String attributeValueName = this
											.getAttributeValueName(attributeValueId);
									ItemAttrValueDTO itemAttrValueDTO = new ItemAttrValueDTO();
									itemAttrValueDTO.setId(Long
											.valueOf(attributeValueId));
									itemAttrValueDTO
											.setName(attributeValueName);
									values.add(itemAttrValueDTO);
									itemAttrDTO.setValues(values);
								}
							} else {
								Integer attributeValueId = Integer
										.valueOf(attributeValueObj + "");
								String attributeValueName = this
										.getAttributeValueName(attributeValueId);
								List<ItemAttrValueDTO> values = new ArrayList<>();
								ItemAttrValueDTO itemAttrValueDTO = new ItemAttrValueDTO();
								itemAttrValueDTO.setId(Long
										.valueOf(attributeValueId));
								itemAttrValueDTO.setName(attributeValueName);
								values.add(itemAttrValueDTO);
								itemAttrDTO.setValues(values);
							}
							resultList.add(itemAttrDTO);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("执行方法【getItemAttrList】报错:", e);
		}
		return resultList;
	}

	@Autowired
	private RedisDB redisDB;

	@Autowired
	private CategoryAttrDAO categoryAttrDAO;

	private String getAttributeName(Long attributeId) {
		if (attributeId != null) {
			String attributeName = this.redisDB
					.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE + attributeId);
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
			String attributeValueName = this.redisDB
					.get(Constants.REDIS_KEY_PREFIX_ATTRIBUTE_VALUE
							+ attributeValueId);
			if (StringUtils.isEmpty(attributeValueName)) {
				return this.categoryAttrDAO.getAttrValueNameByAttrValueId(Long
						.valueOf(attributeValueId));
			} else {
				return attributeValueName;
			}
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Discription:[更新卖家商品]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> modifySellerItem(ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		// result = this.checkExistSellerItemNull(itemDTO);
		if (!result.isSuccess()) {
			return result;
		}
		if (itemDTO.getOperator() == 1) {
			itemDTO.setItemCode(null);
			itemDTO.setCid(null);
			itemDTO.setItemName(null);
		} else {
			// 只保存修改过的字段和属性
			itemDTO.setItemCode(null);
			itemDTO.setItemName(null);
			itemDTO.setSellerId(null);
			itemDTO.setShopId(null);
			itemDTO.setItemQualification(null);
		}
		// 修改商品信息
		this.itemMybatisDAO.updateItem(itemDTO);
		// 修改商品图片
		this.updateItemPictures(itemDTO);
		if (itemDTO.getOperator() == 1) {
			// 修改商品阶梯价
			// this.updateItemPrices(itemDTO);
			// 修改SKU相关信息
			this.updateItemSkuInfos(itemDTO);
			// 修改商品关联属性
			// this.modifyItemAttrs(itemDTO.getAttributes(),
			// itemDTO.getAttrSale(), itemDTO.getItemId());
		}
		// 修改商品描述
		this.modifyItemDescribe(itemDTO);
		return result;
	}

	/**
	 * <p>
	 * Discription:[修改商品属性]
	 * </p>
	 *
	 * @param attributes
	 *            非销售属性
	 * @param attrSales
	 *            销售属性
	 * @param itemId
	 *            商品ID
	 */
	private void modifyItemAttrs(List<ItemAttrDTO> attributes,
			List<ItemAttrDTO> attrSales, Long itemId) {
		List<ItemAttrValueItemDTO> paramList = new ArrayList<ItemAttrValueItemDTO>();
		ItemAttrValueItemDTO dto = null;
		for (ItemAttrDTO attr : attributes) {// 非销售属性
			for (ItemAttrValueDTO value : attr.getValues()) {
				dto = new ItemAttrValueItemDTO();
				dto.setAttrId(attr.getId());
				dto.setAttrType(2);
				dto.setValueId(value.getId());
				dto.setItemId(itemId);
				paramList.add(dto);
			}
		}
		for (ItemAttrDTO attr : attrSales) {// 销售属性
			for (ItemAttrValueDTO value : attr.getValues()) {
				dto = new ItemAttrValueItemDTO();
				dto.setAttrId(attr.getId());
				dto.setAttrType(1);
				dto.setValueId(value.getId());
				dto.setItemId(itemId);
				paramList.add(dto);
			}
		}
		this.itemAttrValueItemExportService.modifyItemAttrValueItem(paramList);
	}

	/**
	 * <p>
	 * Discription:[ 更新商品SKU信息<br>
	 * 传入的参数说明：<br>
	 * 修改的SKU skuid不为空 这些SKU做修改<br>
	 * 删除的SKU 不传入 这些根据数据库原有的SKU与传入的SKU做对比筛选 <br>
	 * 添加的SKU 插入新SKU<br>
	 * <p/>
	 * ]
	 * </p>
	 */
	private void updateItemSkuInfos(ItemDTO itemDTO) {
		List<SkuInfoDTO> insertSkus = new ArrayList<SkuInfoDTO>();
		List<SkuInfoDTO> updateSkus = new ArrayList<SkuInfoDTO>();
		Map<Long, SkuInfoDTO> existsSkus = new HashMap<Long, SkuInfoDTO>();
		for (SkuInfoDTO skuDTO : itemDTO.getSkuInfos()) {
			// 判断SKUID是否为空
			if (skuDTO.getSkuId() != null) {
				existsSkus.put(skuDTO.getSkuId(), skuDTO);
				updateSkus.add(skuDTO);
			} else {
				insertSkus.add(skuDTO);
			}
		}
		List<SkuInfoDTO> dbSkus = this.itemMybatisDAO.queryItemSkuInfo(itemDTO
				.getItemId());
		Map<Long, SkuInfoDTO> deleteSkus = new HashMap<Long, SkuInfoDTO>();
		for (SkuInfoDTO skuInfo : dbSkus) {
			deleteSkus.put(skuInfo.getSkuId(), skuInfo);
		}

		// 筛选出需要删除的SKU
		for (int i = 0; i < dbSkus.size(); i++) {
			SkuInfoDTO sku = dbSkus.get(i);
			if (existsSkus.containsKey(sku.getSkuId())) {
				deleteSkus.remove(sku.getSkuId());
			}
		}
		// 插入需要新添加的SKU
		LOGGER.info("插入的SKU：" + JSON.toJSONString(insertSkus));
		itemDTO.setSkuInfos(insertSkus);
		this.insertItemSkuInfo(itemDTO);
		// 更新需要更新的SKU
		LOGGER.info("更新的SKU：" + JSON.toJSONString(insertSkus));
		itemDTO.setSkuInfos(updateSkus);
		this.updateModifiedSku(itemDTO);
		// 删除需要删除的SKU
		LOGGER.info("删除的SKU：" + JSON.toJSONString(insertSkus));
		Iterator<Long> idsIt = deleteSkus.keySet().iterator();
		while (idsIt.hasNext()) {
			this.itemSkuDAO.deleteSkuById(idsIt.next(), itemDTO.getModifyId(),
					itemDTO.getModifyName());
		}

	}

	/**
	 * <p>
	 * Discription:[更新需要更新的SKU]
	 * </p>
	 */
	private void updateModifiedSku(ItemDTO itemDTO) {
		LOGGER.info("==========开始更新SKU信息============");
		ItemSku itemSku = null;
		for (SkuInfoDTO skuInfo : itemDTO.getSkuInfos()) {
			itemSku = new ItemSku();
			itemSku.setAttributes(skuInfo.getAttributes());
			itemSku.setItemId(itemDTO.getItemId());
			itemSku.setSellerId(itemDTO.getSellerId());
			itemSku.setShopId(itemDTO.getShopId());
			itemSku.setSkuId(skuInfo.getSkuId());
			itemSku.setSkuStatus(1);
			itemSku.setModifyId(itemDTO.getModifyId());
			itemSku.setModifyName(itemDTO.getModifyName());
			itemSku.setEanCode(skuInfo.getEanCode());
			this.itemSkuDAO.update(itemSku);
			// 更新SKU库存
			this.updateItemInventory(skuInfo, itemDTO);
			// 更新SKU价格
			this.updateItemSkuPrices(skuInfo, itemDTO);
			// 更新SKU图片
			this.updateItemSkuPictures(skuInfo, itemDTO);
		}
		LOGGER.info("==========结束更新SKU信息============");
	}

	/**
	 * <p>
	 * Discription:[修改SKU库存]
	 * </p>
	 */
	private void updateItemInventory(SkuInfoDTO skuInfo, ItemDTO itemDTO) {
		// this.tradeInventoryDAO.modifyInventoryBySkuIds(skuInfo.getSkuId(),
		// skuInfo.getSkuInventory());
		List<ItemSkuPublishInfo> list = skuInfo.getItemSkuPublishInfos();
		if (list != null && list.size() > 0) {
			for (ItemSkuPublishInfo dto : list) {
				dto.setModifyId(itemDTO.getModifyId());
				dto.setModifyName(itemDTO.getModifyName());
				dto.setSkuId(skuInfo.getSkuId());
				this.itemSkuPublishInfoMapper.updateBySkuId(dto);
			}
		}

	}

	/**
	 * <p>
	 * Discription:[更新SKU图片]
	 * </p>
	 */
	private void updateItemSkuPictures(SkuInfoDTO skuInfo, ItemDTO itemDTO) {
		// this.itemSkuDAO.deleteSkuPictures(skuInfo.getSkuId());
		this.itemSkuDAO.updateDeleteFlagBySkuId(skuInfo.getSkuId());
		this.inserItemSkuPictures(skuInfo, itemDTO);
	}

	/**
	 *
	 * <p>
	 * Discription:[ 更新SKU区域阶梯价 ]
	 * </p>
	 */
	private void updateItemSkuPrices(SkuInfoDTO skuInfo, ItemDTO itemDTO) {
		LOGGER.info("=============开始更新SKU价格=================");
		ItemSkuLadderPrice dto = new ItemSkuLadderPrice();
		dto.setSkuId(skuInfo.getSkuId());
		dto.setModifyId(itemDTO.getModifyId());
		dto.setModifyName(itemDTO.getModifyName());
		this.itemSkuPriceService.deleteLadderPriceBySkuId(dto);
		LOGGER.info("=============删除价格成功SKU价格=================");
		this.insertItemSkuSellPrices(skuInfo, itemDTO);
		LOGGER.info("=============结束更新SKU价格=================");
	}

	/**
	 *
	 * <p>
	 * Discription:[更新商品区域阶梯价格]
	 * </p>
	 */
	// private void updateItemPrices(ItemDTO itemDTO) {
	// // 删除原来的价格
	// this.itemPriceDAO.deleteItemPrices(itemDTO.getItemId());
	// // 插入新的价格
	// this.insertItemSellPrices(itemDTO);
	// }

	/**
	 * <p>
	 * Discription:[修改商品图片]
	 * </p>
	 */
	private void updateItemPictures(ItemDTO itemDTO) {
		// 删除原来的商品图片
		// this.itemPictureDAO.deleteItemPicture(itemDTO.getItemId());
		if (itemDTO.getOperator() == 1) {
			this.itemPictureDAO.updateDeleteFlagByItemIdAndSellerId(
					itemDTO.getItemId(), itemDTO.getSellerId());
		} else {
			this.itemPictureDAO.updateDeleteFlagByItemId(itemDTO.getItemId());
		}
		// 添加新的商品图片
		this.insertItemPics(itemDTO);
	}

	/**
	 * <p>
	 * Discription:[平台修改商品]
	 * </p>
	 */
	private ExecuteResult<ItemDTO> modifyPlatformItem(ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		// 校验空值
		// result = this.checkExistPlatItemNull(itemDTO);
		if (!result.isSuccess()) {
			result.addErrorMessage("必要参数存在空值！");
			return result;
		}
		Integer itemStatus = itemDTO.getItemStatus();
		ItemDraft itemDraft = itemDraftMapper.selectByItemId(itemDTO
				.getItemId());
		if (itemStatus != null
				&& itemStatus.equals(HtdItemStatusEnum.PASS.getCode())) {
			// //外部供应商商品时, 如果商品状态为审核通过, 则更新商品状态为未上架
			// if(itemDTO.getProductChannelCode().equals(ProductChannelEnum.EXTERNAL_SUPPLIER.getCode())){
			// itemDTO.setItemStatus(HtdItemStatusEnum.NOT_SHELVES.getCode());
			// }
			// TODO 2016-11-24 START
			// 如果商品状态为审核通过, 内部供应商商品时, 则商品信息下行ERP
			if (itemDTO.getProductChannelCode().equals(
					ProductChannelEnum.INTERNAL_SUPPLIER.getCode())) {
				MQSendUtil mqSendUtil = SpringUtils.getBean(MQSendUtil.class);
				ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(Long
						.valueOf(itemDTO.getItemSpuId()));
				ProductMessage productMessage = new ProductMessage();
				productMessage.setProductCode(itemSpu.getSpuCode());
				productMessage.setBrandCode(itemDTO.getBrand().toString());
				productMessage
						.setCategoryCode(itemDTO.getErpFiveCategoryCode());
				productMessage.setChargeUnit(itemDTO.getWeightUnit());
				productMessage.setMarque(itemDTO.getModelType());
				productMessage.setProductSpecifications(itemDTO
						.getItemQualification());
				productMessage.setProductName(itemDTO.getItemName());
				productMessage.setOutputRate(itemDTO.getTaxRate().toString());
				productMessage.setProductColorCode("0");
				productMessage.setProductColorName("0");
				productMessage.setOrigin(itemDTO.getOrigin());
				productMessage.setQualityGrade("0");
				productMessage.setFunctionIntroduction("0");
				productMessage.setValidTags(1);
				productMessage.setIncomeTaxRates("0.17");
				productMessage.setPackingContent("1");
				productMessage.setIsUpdateFlag(1);
				LOGGER.info("商品开始下行ERP, 下行信息 : {}",
						JSONObject.fromObject(productMessage));
				mqSendUtil.sendToMQWithRoutingKey(productMessage,
						MQRoutingKeyConstant.ITEM_DOWN_ERP_ROUTING_KEY);
			}
			// TODO 2016-11-24 END
			// use draft to override item and sku by zhanxiaolong
			if (itemDTO.getProductChannelCode().equals(
					ProductChannelEnum.INTERNAL_SUPPLIER.getCode())) {
				syncDraft2Item(itemDraft, itemDTO);
			}

		}
		// 修改草稿的状态
		if (itemDraft != null) {
			itemDraft.setStatus(itemStatus);
			itemDraftMapper.updateByPrimaryKeySelective(itemDraft);
		}

		// 只保存修改过的字段和属性
		itemDTO.setItemCode(null);
		itemDTO.setItemName(null);
		itemDTO.setSellerId(null);
		itemDTO.setShopId(null);
		itemDTO.setItemQualification(null);
		// 修改商品信息
		this.itemMybatisDAO.updateItem(itemDTO);
		// 修改商品图片
		this.updateItemPictures(itemDTO);
		// 修改SKU相关信息
		// this.updateItemSkuInfos(itemDTO);
		// 修改商品关联属性
		// this.modifyItemAttrs(itemDTO.getAttributes(), itemDTO.getAttrSale(),
		// itemDTO.getItemId());
		// 修改商品描述
		this.modifyItemDescribe(itemDTO);

		result.setResult(itemDTO);
		return result;
	}

	private void syncDraft2Item(ItemDraft itemDraft, ItemDTO itemDTO) {
		if (itemDTO == null || itemDTO.getItemId() == null
				|| itemDTO.getItemId() <= 0) {
			return;
		}
		if (itemDraft == null) {
			return;
		}
		itemDTO.setAd(itemDraft.getAd());
		if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(itemDraft
				.getAttributes()))) {
			itemDTO.setAttributesStr(itemDraft.getAttributes());
		}
		// 处理sku 颜色字段 放到sku表的销售属性字段上去
		List<ItemSku> itemSkuList = itemSkuDAO.queryByItemId(itemDraft
				.getItemId());

		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			ItemSku itemSku = itemSkuList.get(0);
			itemSku.setAttributes(itemDraft.getAttrSale());
			itemSku.setModified(new Date());
			itemSku.setModifyId(itemDraft.getModifyId());
			itemSku.setModifyName(itemDraft.getModifyName());
			itemSkuDAO.update(itemSku);
		}

		if (itemDraft.getCid() != null) {
			itemDTO.setCid(itemDraft.getCid());
		}
		if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(itemDraft
				.getEanCode()))) {
			itemDTO.setEanCode(itemDraft.getEanCode());
		}

		if (itemDraft.getIsSpu() != null && itemDraft.getIsSpu().equals(1)) {
			itemDTO.setSpu(Boolean.TRUE);
		}
		if (StringUtils.isNotEmpty(itemDraft.getItemName())) {
			itemDTO.setItemName(itemDraft.getItemName());
		}
		if (itemDraft.getItemSpuId() != null) {
			itemDTO.setItemSpuId(itemDraft.getItemSpuId().intValue());
		}
		itemDTO.setHeight(itemDraft.getHeight() == null ? "0" : itemDraft
				.getHeight().toPlainString());
		itemDTO.setLength(itemDraft.getLength() == null ? "0" : itemDraft
				.getLength().toPlainString());
		itemDTO.setWidth(itemDraft.getWidth() == null ? "0" : itemDraft
				.getWidth().toPlainString());
		itemDTO.setModelType(itemDraft.getModelType());
		itemDTO.setNetWeight(itemDraft.getNetWeight());
		itemDTO.setOrigin(itemDraft.getOrigin());
		itemDTO.setTaxRate(itemDraft.getTaxRate());
		itemDTO.setWeight(itemDraft.getWeight() == null ? new BigDecimal(0)
				: itemDraft.getWeight());
		itemDTO.setWeightUnit(itemDraft.getWeightUnit());
	}

	/**
	 * <p>
	 * Discription:[获取商品价格]
	 * </p>
	 */
	// private BigDecimal getSkuPrice(ItemShopCartDTO skuDTO) throws Exception {
	// PriceQueryParam param = new PriceQueryParam();
	// param.setAreaCode(skuDTO.getAreaCode());
	// param.setItemId(skuDTO.getItemId());
	// param.setQty(skuDTO.getQty() == null ? 1 : skuDTO.getQty());
	// param.setShopId(skuDTO.getShopId());
	// param.setSkuId(skuDTO.getSkuId());
	// param.setBuyerId(skuDTO.getBuyerId());
	// param.setSellerId(skuDTO.getSellerId());
	// if (!skuDTO.isHasPrice()) {
	// BigDecimal price = this.itemPriceService.getInquiryPrice(param);
	// skuDTO.setHasPrice(price == null ? false : true);
	// }
	// return this.itemPriceService.getSkuShowPrice(param);
	//
	// }
	@Override
	public ExecuteResult<DataGrid<ItemQueryOutDTO>> queryItemByCid(Long cid,
			Pager page) {
		ExecuteResult<DataGrid<ItemQueryOutDTO>> result = new ExecuteResult<DataGrid<ItemQueryOutDTO>>();
		DataGrid<ItemQueryOutDTO> dataGrid = new DataGrid<ItemQueryOutDTO>();
		try {
			List<ItemQueryOutDTO> list = new ArrayList<ItemQueryOutDTO>();
			Long count = 0l;
			if (cid != null) {
				ItemCategoryDTO itemCategory = itemCategoryDAO.queryById(cid);
				if (itemCategory != null && itemCategory.getCategoryLev() == 3) {
					// 入过传入是三级类目 用三级类目查询商品
					ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
					itemInDTO.setCid(cid);
					itemInDTO.setOperator(2);
					itemInDTO.setItemStatus(4);
					DataGrid<ItemQueryOutDTO> d1 = this.queryItemList(
							itemInDTO, page);
					d1.getRows();
					list = d1.getRows();
					count += d1.getTotal();
				}
				if (itemCategory != null && itemCategory.getCategoryLev() == 2) {
					// 入过传入是2级类目 用二级类目查询下面的三级类目 用三级类目查询商品
					ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
					itemCategoryDTO.setCategoryParentCid(cid);
					List<ItemCategoryDTO> itemcList = itemCategoryDAO
							.queryItemCategoryAllList(itemCategoryDTO, null);
					List<Long> idsList = new ArrayList<Long>();
					for (int i = 0; i < itemcList.size(); i++) {
						idsList.add(itemcList.get(i).getCategoryCid());
					}
					Long[] ids = idsList.toArray(new Long[] {});
					ItemQueryInDTO itemQueryInDTO = new ItemQueryInDTO();
					if (ids.length > 0) {
						itemQueryInDTO.setCids(ids);
						itemQueryInDTO.setOperator(2);
						itemQueryInDTO.setItemStatus(4);
						DataGrid<ItemQueryOutDTO> d2 = this.queryItemList(
								itemQueryInDTO, page);
						d2.getRows();
						list = d2.getRows();
						count += d2.getTotal();
					} else {
						list = null;
					}

				}
				if (itemCategory != null && itemCategory.getCategoryLev() == 1) {
					// 传入的是一级类目

					// 用一级类目查询旗下的二级类目
					ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
					itemCategoryDTO.setCategoryParentCid(cid);
					List<ItemCategoryDTO> itemcList2 = itemCategoryDAO
							.queryItemCategoryAllList(itemCategoryDTO, null);
					List<Long> cids2List = new ArrayList<Long>();
					for (int i = 0; i < itemcList2.size(); i++) {
						cids2List.add(itemcList2.get(i).getCategoryCid());
					}
					Long[] cids2 = cids2List.toArray(new Long[] {});
					// 用二级类目数字 查询旗下的三级类目
					ItemCategoryDTO itemCategoryDTO2 = new ItemCategoryDTO();
					if (cids2.length > 0) {
						itemCategoryDTO2.setCategoryParentCids(cids2);
					}
					List<ItemCategoryDTO> itemcList3 = itemCategoryDAO
							.queryItemCategoryAllList(itemCategoryDTO2, null);
					List<Long> cids3List = new ArrayList<Long>();
					for (int i = 0; i < itemcList3.size(); i++) {
						cids3List.add(itemcList3.get(i).getCategoryCid());
					}
					Long[] cids3 = cids3List.toArray(new Long[] {});
					// 用三级类目组 查询旗下的商品
					ItemQueryInDTO itemQueryInDTO = new ItemQueryInDTO();
					if (cids3.length > 0) {
						itemQueryInDTO.setCids(cids3);
						itemQueryInDTO.setOperator(2);
						itemQueryInDTO.setItemStatus(4);
						DataGrid<ItemQueryOutDTO> d3 = this.queryItemList(
								itemQueryInDTO, page);
						d3.getRows();
						list = d3.getRows();
						count += d3.getTotal();
					} else {
						list = null;
					}
				}
			} else {
				ItemQueryInDTO itemQueryInDTO = new ItemQueryInDTO();
				itemQueryInDTO.setOperator(2);
				itemQueryInDTO.setItemStatus(4);
				DataGrid<ItemQueryOutDTO> d4 = this.queryItemList(
						itemQueryInDTO, page);
				d4.getRows();
				list = d4.getRows();
				count += d4.getTotal();
			}
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
			result.setResultMessage("success");
		} catch (Exception e) {
			result.setResultMessage("error");
			result.getErrorMessages().add(e.getMessage());
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<ItemQueryOutDTO>> queryItemByPlatItemId(
			Long itemId, Pager<Long> pager) {
		ExecuteResult<DataGrid<ItemQueryOutDTO>> result = new ExecuteResult<DataGrid<ItemQueryOutDTO>>();
		DataGrid<ItemQueryOutDTO> dg = new DataGrid<ItemQueryOutDTO>();
		ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
		itemInDTO.setPlatItemId(itemId);
		Pager<ItemQueryOutDTO> page = new Pager<ItemQueryOutDTO>();
		page.setRows(pager.getRows());
		page.setPage(pager.getPage());
		List<ItemQueryOutDTO> list = this.itemMybatisDAO.queryItemList(
				itemInDTO, page);
		dg.setTotal(this.itemMybatisDAO.queryCount(itemInDTO));
		dg.setRows(list);
		result.setResult(dg);
		return result;
	}

	@Override
	public ExecuteResult<String> modifyItemPlatStatus(List<Long> ids,
			Integer status) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (ids == null || ids.size() <= 0) {
			result.addErrorMessage("参数为空！");
			return result;
		}
		this.itemMybatisDAO.updateItemPlatStatus(ids, status);
		return result;
	}

	@Override
	public ExecuteResult<String> modifyShopItemStatus(
			ItemStatusModifyDTO statusDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (statusDTO == null) {
			result.addErrorMessage("参数为空！");
			return result;
		}
		if (statusDTO.getItemStatus() == null) {
			result.addErrorMessage("商品状态是空！");
			return result;
		}
		this.itemMybatisDAO.updateShopItemStatus(statusDTO);
		return result;
	}

	@Override
	public ExecuteResult<String> deleteItem(Long itemId) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		ItemDTO item = this.itemMybatisDAO.getItemDTOById(itemId);
		if (item == null) {
			result.addErrorMessage("商品不存在！");
			return result;
		}
		// 在售 和 审核通过的商品不可以删除
		if (HtdItemStatusEnum.PASS.getCode() == item.getItemStatus()
				|| HtdItemStatusEnum.SHELVED.getCode() == item.getItemStatus()) {
			result.addErrorMessage("商品是在售或审核通过状态，不能删除！");
			return result;
		}
		ItemDTO dto = new ItemDTO();
		dto.setItemId(itemId);
		dto.setItemStatus(6);
		this.itemMybatisDAO.updateItem(dto);
		return result;
	}

	@Override
	public ExecuteResult<String> addItemToPlat(Long itemId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		List<Long> itemIds = new ArrayList<Long>();
		itemIds.add(itemId);
		ItemStatusModifyDTO dto = new ItemStatusModifyDTO();
		dto.setItemIds(itemIds);
		this.copyItemToPlat(dto);
		return result;
	}

	@Override
	public ExecuteResult<String> updateStatusUserShopCate(
			ItemStatusModifyDTO statusDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			if (statusDTO.getItemStatus() == null) {
				executeResult.addErrorMessage("商品状态为空！");
				return executeResult;
			}
			if (statusDTO.getOperator() == null) {
				executeResult.addErrorMessage("Operator为空！");
				return executeResult;
			}
			List<Long> ids = statusDTO.getItemIds();
			if (ids == null || ids.size() <= 0) {
				executeResult.addErrorMessage("商品ID为空！");
				return executeResult;
			}
			itemMybatisDAO.updateStatusUserShopCate(ids,
					statusDTO.getChangeReason(), statusDTO.getItemStatus());
		} catch (Exception e) {
			LOGGER.error("执行方法【modifyItemStatusById】报错：{}", e);
			executeResult.addErrorMessage("执行方法【modifyItemStatusById】报错："
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<List<ItemDBDTO>> queryItemDB() {
		ExecuteResult<List<ItemDBDTO>> result = new ExecuteResult<List<ItemDBDTO>>();
		try {
			List<ItemDBDTO> itemDBDTOList = this.itemMybatisDAO.queryItemDB();
			result.setResult(itemDBDTOList);
		} catch (Exception e) {
			result.addErrorMessage("queryItemDB：" + e.toString());
			LOGGER.error("queryItemDB：" + e.toString());
		}
		return result;
	}

	@Override
	public Long queryStayItemVolume(ItemQueryInDTO itemInDTO) {
		Long stayItemCount = itemMybatisDAO.queryCount(itemInDTO);
		return stayItemCount;
	}

	@Override
	public ExecuteResult<Boolean> mondifyMarketPrice(String[] itemIds,
			BigDecimal price) {
		ExecuteResult<Boolean> result = new ExecuteResult<Boolean>();
		try {
			if (itemIds != null && itemIds.length > 0) {
				this.itemMybatisDAO.mondifyMarketPrice(itemIds, price);
			}
			result.setResult(true);
		} catch (Exception e) {
			result.addErrorMessage("mondifyMarketPrice：" + e.toString());
			LOGGER.error("mondifyMarketPrice：" + e.toString());
		}
		return result;
	}

	@Override
	public List<Item> queryItemBySyncTime(Date syncTime, Pager page) {
		return itemMybatisDAO.queryItemBySyncTime(syncTime, page);
	}

	@Override
	public List<SkuPictureDTO> querySkuPics(@Param("skuId") Long skuId) {
		return itemMybatisDAO.querySkuPics(skuId);
	}

	@Override
	public List<ItemQueryOutDTO> queryItemsList(ItemQueryInDTO itemInDTO,
			Pager page) {
		return itemMybatisDAO.queryItemList(itemInDTO, page);
	}

	/**
	 * 根据item_id查找有效sku信息
	 * 
	 * @param itemId
	 * @return
	 */
	@Override
	public List<SkuInfoDTO> queryItemSkuInfo(Long itemId) {
		return itemMybatisDAO.queryItemSkuInfo(itemId);
	}

	/**
	 * 修改商品详情表
	 * 
	 * @param itemDTO
	 */
	public void modifyItemDescribe(ItemDTO itemDTO) {
		ItemDescribe itemDescribe = new ItemDescribe();
		itemDescribe.setItemId(itemDTO.getItemId());
		itemDescribe.setModifyId(itemDTO.getModifyId());
		itemDescribe.setModifyName(itemDTO.getModifyName());
		itemDescribe.setModifyTime(new Date());
		itemDescribe.setDescribeContent(itemDTO.getDescribeUrl());
		ItemDescribe dbItemDesc = itemDescribeDAO.getDescByItemId(itemDTO
				.getItemId());
		if (dbItemDesc == null) {
			itemDescribe.setCreateId(itemDTO.getModifyId());
			itemDescribe.setCreateName(itemDTO.getModifyName());
			itemDescribe.setCreateTime(new Date());
			itemDescribeDAO.insertSelective(itemDescribe);
			return;
		}
		itemDescribeDAO.updateByItemId(itemDescribe);
	}

	@Override
	public ExecuteResult<DataGrid<SyncItemStockSearchOutDTO>> querySyncItemStockSearchList(SyncItemStockSearchInDTO syncItemStockSearchInDTO, Pager page) {
		ExecuteResult<DataGrid<SyncItemStockSearchOutDTO>> result = new ExecuteResult<DataGrid<SyncItemStockSearchOutDTO>>();
		// 校验参数是否全为空
		if (syncItemStockSearchInDTO == null || syncItemStockSearchInDTO.getPageSize() == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.SUCCESS.getErrorMsg("syncItemStockSearchInDTO")));
			return result;
		}
		// 根据一级和二级找到对应三级目录
		List<Long> catIdList = null;
		if (syncItemStockSearchInDTO.getThirdCatId() != null) {
			catIdList = Lists.newArrayList();
			catIdList.add(syncItemStockSearchInDTO.getThirdCatId());
		}
		if (syncItemStockSearchInDTO.getThirdCatId() == null && syncItemStockSearchInDTO.getSecondCatId() != null) {
			catIdList = getThirdCatIdListByParentCatId(syncItemStockSearchInDTO.getSecondCatId());
		}
		if (syncItemStockSearchInDTO.getThirdCatId() == null && syncItemStockSearchInDTO.getSecondCatId() == null && syncItemStockSearchInDTO.getFirstCatId() != null) {
			catIdList = getThirdCatIdListByParentCatId(syncItemStockSearchInDTO.getSecondCatId());
		}
		syncItemStockSearchInDTO.setThirdCatIdList(catIdList);
		Long totalCount = itemMybatisDAO.querySyncItemStockListCount(syncItemStockSearchInDTO);
		page.setTotalCount(totalCount.intValue());
		syncItemStockSearchInDTO.setStart((page.getPage() - 1) * page.getRows());
		syncItemStockSearchInDTO.setPageSize(page.getRows());
		List<SyncItemStockSearchOutDTO> list = itemMybatisDAO.querySyncItemStockList(syncItemStockSearchInDTO);
		// 补足公司名和一级目录和二级目录
		if (CollectionUtils.isNotEmpty(list)) {
			for (SyncItemStockSearchOutDTO syncItemStockSearchOutDTO : list) {
				makeUpFirstAndSecondCat(syncItemStockSearchOutDTO);
			}
		}
		DataGrid<SyncItemStockSearchOutDTO> dataGrid = new DataGrid<SyncItemStockSearchOutDTO>(list);
		dataGrid.setTotal(totalCount);
		dataGrid.setPageSize(page.getRows());
		result.setResult(dataGrid);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	private List<String> getItemSpuCodeList(List<SyncItemStockSearchOutDTO> list) {
		List<String> itemSpuCodeList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(list)) {
			for (SyncItemStockSearchOutDTO itemStockSearchOutDTO : list) {
				itemSpuCodeList.add(itemStockSearchOutDTO.getSpuCode());
			}
		}
		return itemSpuCodeList;
	}

	private List<Long> getThirdCatIdListByParentCatId(Long parentCatId) {
		List<Long> catIdList = null;
		ExecuteResult<DataGrid<ItemCategoryDTO>> catResult = itemCategoryService
				.queryItemCategoryList(parentCatId);

		if (catResult.isSuccess() && catResult.getResult() != null) {
			catIdList = Lists.newArrayList();
			List<ItemCategoryDTO> thirdCatList = catResult.getResult()
					.getRows();
			if (CollectionUtils.isNotEmpty(thirdCatList)) {
				for (ItemCategoryDTO thirdCat : thirdCatList) {
					catIdList.add(thirdCat.getCategoryCid());
				}
			}
		}
		return catIdList;
	}

	private void makeUpFirstAndSecondCat(
			SyncItemStockSearchOutDTO syncItemStockSearchOutDTO) {
		ExecuteResult<List<ItemCatCascadeDTO>> catResult = itemCategoryService
				.queryParentCategoryList(syncItemStockSearchOutDTO
						.getThirdCatId());
		if (!catResult.isSuccess()) {
			return;
		}
		List<ItemCatCascadeDTO> itemCatCascadeList = catResult.getResult();

		if (CollectionUtils.isEmpty(itemCatCascadeList)) {
			return;
		}

		ItemCatCascadeDTO firstCat = itemCatCascadeList.get(0);
		if (firstCat == null) {
			return;
		}

		syncItemStockSearchOutDTO.setFirstCatName(firstCat.getCname());

		List<ItemCatCascadeDTO> childCatCascadeList = firstCat.getChildCats();

		if (CollectionUtils.isEmpty(childCatCascadeList)) {
			return;
		}

		ItemCatCascadeDTO secondCat = childCatCascadeList.get(0);

		if (secondCat == null) {
			return;
		}

		syncItemStockSearchOutDTO.setSecondCatName(secondCat.getCname());

	}

	@Override
	public ExecuteResult<String> syncItemStock(
			SyncItemStockInDTO syncItemStockInDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();

		if (syncItemStockInDTO == null
				|| syncItemStockInDTO.getOperatorId() == null
				|| CollectionUtils.isEmpty(syncItemStockInDTO.getData())) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("syncItemStockInDTO")));
			return result;
		}

		String accessToken = MiddlewareInterfaceUtil.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("accessToken")));
			return result;
		}

		for (Map<String, Object> mapObj : syncItemStockInDTO.getData()) {
			String spuCode = (String) mapObj.get("productCode");
			String supplierCode = (String) mapObj.get("supplierCode");
			if (StringUtils.isEmpty(spuCode)) {
				continue;
			}

			ExecuteResult<MemberImportSuccInfoDTO> memberImportSuccInfoDTO = memberBaseInfoService
					.querySellerIdByCode(supplierCode);

			if (memberImportSuccInfoDTO == null
					|| !memberImportSuccInfoDTO.isSuccess()
					|| memberImportSuccInfoDTO.getResult() == null
					|| !StringUtils.isNumeric(memberImportSuccInfoDTO
							.getResult().getMemberId())) {
				continue;
			}

			Long sellerId = Long.valueOf(memberImportSuccInfoDTO.getResult()
					.getMemberId());

			ItemSpu itemSpu = itemSpuMapper.queryItemSpuBySpuCode(spuCode);
			if (itemSpu == null) {
				continue;
			}
			ItemSku itemSku = itemSkuDAO.queryItemSkuBySellerIdAndSpuId(
					itemSpu.getSpuId(), sellerId);
			if (itemSku == null) {
				continue;
			}
			// 查询库存上架信息
			List<ItemSkuPublishInfo> itemSkuPublishInfoList = itemSkuPublishInfoMapper
					.queryItemSkuShelfStatus(itemSku.getSkuId());
			if (CollectionUtils.isEmpty(itemSkuPublishInfoList)) {
				continue;
			}
			LOGGER.info(
					"调取中间件查询getSingleItemStock开始, supplierCode : {}, spuCode : {}",
					supplierCode, spuCode);
			ItemStockResponseDTO itemStockResponse = MiddlewareInterfaceUtil
					.getSingleItemStock(supplierCode, spuCode);
			LOGGER.info("调取中间件查询getSingleItemStock结束, itemStockResponse : {}",
					JSON.toJSONString(itemStockResponse));
			Integer stockNum = (itemStockResponse == null
					|| itemStockResponse.getStoreNum() == null || itemStockResponse
					.getStoreNum() <= 0) ? 0 : itemStockResponse.getStoreNum();

			for (ItemSkuPublishInfo itemSkuPublishInfo : itemSkuPublishInfoList) {
				ItemSkuPublishInfoUtil.doUpdateItemSkuPublishInfo(
						syncItemStockInDTO.getOperatorId(),
						syncItemStockInDTO.getOperatorName(), itemSku,
						stockNum, itemSkuPublishInfo);
			}
		}

		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> pauseItemByShopId(PauseItemInDTO pauseItemInDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (pauseItemInDTO == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("pauseItemInDTO")));
			return result;
		}

		if (pauseItemInDTO.getOperatorId() == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("OperatorId")));
			return result;
		}

		if (StringUtils.isEmpty(pauseItemInDTO.getOperatorName())) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("OperatorName")));
			return result;
		}

		if (pauseItemInDTO.getSellerId() == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("SellerId")));
			return result;
		}

		if (pauseItemInDTO.getShopId() == null) {
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000
					.getErrorMsg("ShopId")));
			return result;
		}
		itemMybatisDAO.pauseItemByShopId(pauseItemInDTO);
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public boolean checkErpFirstAndErpFiveCode(String erpFirstCategoryCode,
			String erpFiveCategoryCode) {
		// erpFirstCategoryCode = "15";//真实ERP一级类目code
		// erpFiveCategoryCode = "1524060101";//真实ERP五级类目code
		// 使用HTTP方式调用中间件获取ERP一级类目与五级类目的关系
		boolean isExistRelate = true;
		try {
			String accessToken = MiddlewareInterfaceUtil.getAccessToken();
			// 这个是中间件提供的有效的token,可以直接拿来测试
			// String accessToken = "4f603451-50f6-47e3-8203-9827619ae66f";
			String url = MiddlewareInterfaceConstant.MIDDLEWARE_ChECK_ERP_FIRST_AND_ERP_FIVE_CODE_URL;
			StringBuffer sbf = new StringBuffer(url);
			sbf.append("?firstCategreyCode=").append(erpFirstCategoryCode);
			sbf.append("&fiveCategreyCode=").append(erpFiveCategoryCode);
			sbf.append("&token=").append(accessToken);
			String resultJson = MiddlewareInterfaceUtil.httpGet(sbf.toString(),
					Boolean.FALSE);
			MiddlewareResponseObject middlewareResponseObject = (MiddlewareResponseObject) JSONObject
					.toBean(JSONObject.fromObject(resultJson),
							MiddlewareResponseObject.class);
			String code = middlewareResponseObject.getCode();
			String msg = middlewareResponseObject.getMsg();
			if (MiddlewareResponseCodeEnum.EXIST_RELATE.getCode().equals(code)) {
				isExistRelate = true;
			} else {
				isExistRelate = false;
				LOGGER.error("checkErpFirstAndErpFiveCode：" + msg);
			}
		} catch (Exception e) {
			isExistRelate = false;
			LOGGER.error("checkErpFirstAndErpFiveCode：", e);
		}
		return isExistRelate;
	}

	@Override
	public DataGrid<ItemToDoCountDTO> queryToDoCount(PauseItemInDTO dto) {
		DataGrid<ItemToDoCountDTO> dataGrid = new DataGrid<ItemToDoCountDTO>();
		try {
			List<ItemToDoCountDTO> list = itemMybatisDAO.selectToDoCount(dto);
			if (list != null && list.size() > 0) {
				for (ItemToDoCountDTO idto : list) {
					idto.setStatusName(HtdItemStatusEnum.getEnumBycode(idto
							.getItemStatus()));
				}
				dataGrid.setRows(list);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<Item> getItemByCode(String itemCode) {
		ExecuteResult<Item> executeResult = new ExecuteResult<>();
		try {
			if (StringUtils.isEmpty(itemCode)) {
				executeResult.addErrorMessage("itemCode is null");
				return executeResult;
			}
			Item item = this.itemMybatisDAO.queryItemByItemCode(itemCode);
			executeResult.setResult(item);
		} catch (Exception e) {
			LOGGER.error("根据itemCode查询item出错, 错误信息 : ", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> updateItem(ItemDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			itemMybatisDAO.updateItem(dto);
			result.setResult("success");
		} catch (Exception e) {
			LOGGER.error("更新Item出错, 错误信息 : ", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 审核内部供应商商品
	 *
	 * @param itemDTO
	 * @author zhangxiaolong
	 */
	@Transactional
	@Override
	public ExecuteResult<ItemDTO> auditInternalSupplierItem(ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		try {
			if (itemDTO == null) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			if (itemDTO.getItemId() == null || itemDTO.getItemId() <= 0L) {
				result.addErrorMessage("itemId为空！");
				return result;
			}
			if (itemDTO.getModifyId() == null) {
				result.addErrorMessage("ModifyId为空！");
				return result;
			}
			if (StringUtils.isEmpty(itemDTO.getModifyName())) {
				result.addErrorMessage("ModifyName为空！");
				return result;
			}

			ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(itemDTO
					.getItemId());
			if (dbItem == null) {
				result.addErrorMessage("没有查询到该商品信息！");
				return result;
			}

			Integer itemStatus = itemDTO.getItemStatus();
			ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(Long
					.valueOf(dbItem.getItemSpuId()));

			if (itemSpu == null) {
				result.addErrorMessage("itemSpu为空！");
				return result;
			}
			if (itemStatus != null
					&& itemStatus.equals(HtdItemStatusEnum.PASS.getCode())) {

				if (StringUtils.isEmpty(itemDTO.getErpFirstCategoryCode())) {
					result.addErrorMessage("getErpFirstCategoryCode为空！");
					return result;
				}

				if (StringUtils.isEmpty(dbItem.getErpFirstCategoryCode())) {
					dbItem.setErpFirstCategoryCode(itemDTO
							.getErpFirstCategoryCode());
				}

				if (StringUtils.isEmpty(dbItem.getErpFiveCategoryCode())
						&& StringUtils
								.isEmpty(itemDTO.getErpFiveCategoryCode())) {
					result.addErrorMessage("ErpFiveCategoryCode为空！");
					return result;
				}

				if (StringUtils.isEmpty(dbItem.getErpFiveCategoryCode())) {
					dbItem.setErpFiveCategoryCode(itemDTO
							.getErpFiveCategoryCode());
				}

				itemMybatisDAO.updateAuditStatusChangeReason("",
						itemDTO.getItemId());
				ItemDraft itemDraft = itemDraftMapper.selectByItemId(itemDTO
						.getItemId());
				if(null != itemDraft){
					itemDraft.setVerifyStatus(1);
					itemDraft.setVerifyName(itemDTO.getModifyName());
					itemDraftMapper.updateItemDraftVerifyStatusByPrimaryKey(itemDraft);
					
					if(itemDraft.getItemSpuId()!=null){
						itemSpu=itemSpuMapper.selectByPrimaryKey(itemDraft.getItemSpuId());
					}
				}
				// 如果商品状态为审核通过, 内部供应商商品时, 则商品信息下行ERP
				doItemDownErp(itemSpu, dbItem);
			}
			// 审核不通过
			if (itemStatus != null
					&& itemStatus.equals(HtdItemStatusEnum.REJECTED.getCode())) {
				ItemDraft itemDraft = itemDraftMapper.selectByItemId(itemDTO
						.getItemId());
				// 修改主数据状态
				// Integer mainStatus=itemStatus;
				if (dbItem.getItemStatus() != HtdItemStatusEnum.SHELVED
						.getCode()) {
					// mainStatus=HtdItemStatusEnum.SHELVED.getCode();
					itemMybatisDAO.updateItemStatusByPk(itemDTO.getItemId(),
							itemStatus, itemDTO.getModifyId(),
							itemDTO.getModifyName());
				}

				if (StringUtils.isNotEmpty(itemDTO.getStatusChangeReason())) {
					itemMybatisDAO.updateAuditStatusChangeReason(
							itemDTO.getStatusChangeReason(),
							itemDTO.getItemId());
				}
				// 修改草稿的状态
				if (itemDraft != null) {
					itemDraft.setStatus(itemStatus);
					itemDraft.setVerifyStatus(itemStatus);
					itemDraft.setVerifyName(itemDTO.getModifyName());
					itemDraft.setModifyName(itemDTO.getModifyName());
					itemDraftMapper.updateByPrimaryKeySelective(itemDraft);
				}
			}
			// 修改了erp的一级和五级类目
			Item tempItem = new Item();
			if (StringUtils.isNotEmpty(itemDTO.getErpFirstCategoryCode())) {
				tempItem.setErpFirstCategoryCode(itemDTO
						.getErpFirstCategoryCode());
				itemSpu.setErpFirstCategoryCode(itemDTO
						.getErpFirstCategoryCode());
			}
			if (StringUtils.isNotEmpty(itemDTO.getErpFiveCategoryCode())) {
				tempItem.setItemId(itemDTO.getItemId());
				tempItem.setErpFiveCategoryCode(itemDTO
						.getErpFiveCategoryCode());
				itemSpu.setErpFiveCategoryCode(itemDTO.getErpFiveCategoryCode());
				itemMybatisDAO.updateFirstAndFiveCategoryCodeByItemId(tempItem);
				itemSpuMapper.updateBySpuIdSelective(itemSpu);
			}
			result.setResult(itemDTO);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ItemExportServiceImpl::auditInternalSupplierItem:", e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	private void doItemDownErp(ItemSpu itemSpu, ItemDTO dbItem) {
		LOGGER.info("ERPdoItemDownErp start " + itemSpu.getSpuCode());
		// 查草稿
		ItemDraft itemDraft = itemDraftMapper
				.selectByItemId(dbItem.getItemId());
		if (itemDraft != null && 0 == itemDraft.getStatus()) {
			LOGGER.info("ERPdoItemDownErp 存在待审核的草稿数据 {}",
					JSON.toJSON(itemDraft));
			// 用草稿来覆盖
			if (StringUtils.isNotEmpty(itemDraft.getItemName())) {
				dbItem.setItemName(itemDraft.getItemName());
			}

			if (itemDraft.getCid() != null && itemDraft.getCid() > 0) {
				dbItem.setCid(itemDraft.getCid());
			}

			if (itemDraft.getBrand() != null && itemDraft.getBrand() > 0) {
				dbItem.setBrand(itemDraft.getBrand());
			}

			if (StringUtils.isNotEmpty(itemDraft.getModelType())) {
				dbItem.setModelType(itemDraft.getModelType());
			}

			if (StringUtils.isNotEmpty(dictionaryUtils.getNameByValue(
					DictionaryConst.TYPE_ITEM_UNIT, itemDraft.getWeightUnit()))) {
				dbItem.setWeightUnit(itemDraft.getWeightUnit());
			}

			if (itemDraft.getTaxRate() != null) {
				dbItem.setTaxRate(itemDraft.getTaxRate());
			}

			if (StringUtils.isNotEmpty(itemDraft.getOrigin())) {
				dbItem.setOrigin(itemDraft.getOrigin());
			}
		}

		MQSendUtil mqSendUtil = SpringUtils.getBean(MQSendUtil.class);
		ProductMessage productMessage = new ProductMessage();
		productMessage.setProductCode(itemSpu.getSpuCode());
		productMessage.setBrandCode(dbItem.getBrand().toString());
		productMessage.setCategoryCode(dbItem.getErpFiveCategoryCode());
		productMessage.setChargeUnit(dictionaryUtils.getNameByValue(
				DictionaryConst.TYPE_ITEM_UNIT, dbItem.getWeightUnit()));
		productMessage
				.setMarque(StringUtils.isEmpty(dbItem.getModelType()) ? "0"
						: dbItem.getModelType());
		productMessage.setProductSpecifications(StringUtils.isEmpty(StringUtils
				.trimToEmpty(dbItem.getItemQualification())) ? "0" : dbItem
				.getItemQualification());
		productMessage.setProductName(dbItem.getItemName());
		productMessage.setOutputRate(dbItem.getTaxRate() == null ? "0" : dbItem
				.getTaxRate().toString());
		productMessage.setProductColorCode("0");
		productMessage.setProductColorName("0");
		productMessage.setOrigin(StringUtils.isEmpty(StringUtils
				.trimToEmpty(dbItem.getOrigin())) ? "0" : dbItem.getOrigin());
		productMessage.setQualityGrade("0");
		productMessage.setFunctionIntroduction("0");
		productMessage.setValidTags(1);
		productMessage.setIncomeTaxRates("0");
		productMessage.setPackingContent("1");
		if (StringUtils.isEmpty(itemSpu.getErpCode())
				|| "0".equals(itemSpu.getErpCode())) {
			productMessage.setIsUpdateFlag(0);
		} else {
			productMessage.setIsUpdateFlag(1);
		}
		productMessage.setItemId(dbItem.getItemId() + "");
		LOGGER.info("商品开始下行ERP, 下行信息 : {}",
				JSONObject.fromObject(productMessage));
		mqSendUtil.sendToMQWithRoutingKey(productMessage,
				MQRoutingKeyConstant.ITEM_DOWN_ERP_ROUTING_KEY);
		itemMybatisDAO.updateErpStatus(dbItem.getItemId(),
				ItemErpStatusEnum.DOWNING.getCode(), "");
		LOGGER.info("ERPdoItemDownErp end " + itemSpu.getSpuCode());
	}

	/**
	 * 运营人员修改平台公司商品
	 * 
	 * @param itemDTO
	 * @author zhangxiaolong
	 */
	@Transactional
	@Override
	public ExecuteResult<ItemDTO> modifyInternalSupplierItemBySalesman(
			ItemDTO itemDTO) {
		ExecuteResult<ItemDTO> result = new ExecuteResult<ItemDTO>();
		try {
			// 校验空值
			if (itemDTO == null) {
				result.setCode(ErrorCodes.E10001.name());
				result.addErrorMessage("参数为空！");
				return result;
			}
			ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(itemDTO
					.getItemId());
			if (dbItem == null) {
				result.setCode(ErrorCodes.E10001.name());
				result.addErrorMessage("没有查询到该商品信息！");
				return result;
			}
			// 数据保存到item_draft
			ItemDraft changedItemDraft = itemDraftMapper.selectByItemId(itemDTO
					.getItemId());
			// 更改记录 存到modify_detail_info 商品详情页更改记录都要保存到这个表，包括所有的字段
			// ModifyDetailInfoUtil.saveChangedRecord(itemDTO, dbItem);
			convertItemDTO2Item(itemDTO, dbItem, changedItemDraft);

			// 更新spu表中的ERP一级和五级类目

			if (dbItem.getItemSpuId() != null
					&& StringUtils
							.isNotEmpty(itemDTO.getErpFirstCategoryCode())
					&& StringUtils.isNotEmpty(itemDTO.getErpFiveCategoryCode())) {
				ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(Long
						.valueOf(dbItem.getItemSpuId()));
				if (itemSpu != null) {
					itemSpu.setErpFirstCategoryCode(itemDTO
							.getErpFirstCategoryCode());
					itemSpu.setErpFiveCategoryCode(itemDTO
							.getErpFiveCategoryCode());
					itemSpuMapper.updateBySpuIdSelective(itemSpu);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					"ItemExportServiceImpl::modifyInternalSupplierItemBySalesman:",
					e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	private void convertItemDTO2Item(ItemDTO itemDTO, ItemDTO dbItem,
			ItemDraft changedItemDraft) {
		if (itemDTO == null || dbItem == null) {
			return;
		}
		boolean isItemInfoChanged = false;
		// 3级类目
		if (itemDTO.getCid() != null
				&& !itemDTO.getCid().equals(dbItem.getCid())) {
			dbItem.setCid(itemDTO.getCid());
			if (changedItemDraft != null) {
				changedItemDraft.setCid(itemDTO.getCid());
			}
			isItemInfoChanged = true;
		}
		// 品牌编码
		if (itemDTO.getBrand() != null
				&& !itemDTO.getBrand().equals(dbItem.getBrand())) {
			dbItem.setBrand(itemDTO.getBrand());
			if (changedItemDraft != null) {
				changedItemDraft.setBrand(itemDTO.getBrand());
			}
			isItemInfoChanged = true;
		}
		// String isSpuFlag=itemDTO.isSpu()?"1":"0";
		// String dbIsSpuFlag=dbItem.isSpu()?"1":"0";
		// if(!isSpuFlag.equals(dbIsSpuFlag)){
		// dbItem.setSpu(itemDTO.isSpu());
		// changedItemDraft.setIsSpu(Integer.valueOf(isSpuFlag));
		// isItemInfoChanged=true;
		// }

		dbItem.setSpu(true);

		if (StringUtils.isNotEmpty(itemDTO.getAd())
				&& !itemDTO.getAd().equals(dbItem.getAd())) {
			dbItem.setAd(itemDTO.getAd());
			if (changedItemDraft != null) {
				changedItemDraft.setAd(itemDTO.getAd());
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getWeightUnit())
				&& !itemDTO.getWeightUnit().equals(dbItem.getWeightUnit())) {
			dbItem.setWeightUnit(itemDTO.getWeightUnit());
			if (changedItemDraft != null) {
				changedItemDraft.setWeightUnit(itemDTO.getWeightUnit());
			}
		}

		if (itemDTO.getWeight() != null
				&& !itemDTO.getWeight().equals(dbItem.getWeight())) {
			dbItem.setWeight(itemDTO.getWeight());
			if (changedItemDraft != null) {
				changedItemDraft.setWeight(itemDTO.getWeight());
			}
			isItemInfoChanged = true;
		}
		if (itemDTO.getNetWeight() != null
				&& !itemDTO.getNetWeight().equals(dbItem.getNetWeight())) {
			dbItem.setNetWeight(itemDTO.getNetWeight());
			if (changedItemDraft != null) {
				changedItemDraft.setNetWeight(itemDTO.getNetWeight());
			}
			isItemInfoChanged = true;
		}
		if (StringUtils.isNotEmpty(itemDTO.getHeight())
				&& !itemDTO.getHeight().equals(dbItem.getHeight())) {
			dbItem.setHeight(itemDTO.getHeight());
			if (changedItemDraft != null) {
				changedItemDraft.setHeight(new BigDecimal(itemDTO.getHeight()));
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getModelType())
				&& !itemDTO.getModelType().equals(dbItem.getModelType())) {
			dbItem.setModelType(itemDTO.getModelType());
			if (changedItemDraft != null) {
				changedItemDraft.setModelType(itemDTO.getModelType());
			}
			isItemInfoChanged = true;
		}

		if (itemDTO.getTaxRate() != null
				&& !itemDTO.getTaxRate().equals(dbItem.getTaxRate())) {
			dbItem.setTaxRate(itemDTO.getTaxRate());
			if (changedItemDraft != null) {
				changedItemDraft.setTaxRate(itemDTO.getTaxRate());
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getOrigin())
				&& !itemDTO.getOrigin().equals(dbItem.getOrigin())) {
			dbItem.setOrigin(itemDTO.getOrigin());
			if (changedItemDraft != null) {
				changedItemDraft.setOrigin(itemDTO.getOrigin());
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getProductChannelCode())
				&& !itemDTO.getProductChannelCode().equals(
						dbItem.getProductChannelCode())) {
			dbItem.setProductChannelCode(itemDTO.getProductChannelCode());
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getLength())
				&& !itemDTO.getLength().equals(dbItem.getLength())) {
			dbItem.setLength(itemDTO.getLength());
			if (changedItemDraft != null) {
				changedItemDraft.setLength(new BigDecimal(itemDTO.getLength()));
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getWidth())
				&& !itemDTO.getWidth().equals(dbItem.getWidth())) {
			dbItem.setWidth(itemDTO.getWidth());
			if (changedItemDraft != null) {
				changedItemDraft.setWidth(new BigDecimal(itemDTO.getWidth()));
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getErpFirstCategoryCode())
				&& !itemDTO.getErpFirstCategoryCode().equals(
						dbItem.getErpFirstCategoryCode())) {
			dbItem.setErpFirstCategoryCode(itemDTO.getErpFirstCategoryCode());
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getErpFiveCategoryCode())
				&& !itemDTO.getErpFiveCategoryCode().equals(
						dbItem.getErpFiveCategoryCode())) {
			dbItem.setErpFiveCategoryCode(itemDTO.getErpFiveCategoryCode());
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getAttributesStr())
				&& !itemDTO.getAttributesStr()
						.equals(dbItem.getAttributesStr())) {
			dbItem.setAttributesStr(itemDTO.getAttributesStr());
			if (changedItemDraft != null) {
				changedItemDraft.setAttributes(itemDTO.getAttributesStr());
			}
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getItemQualification())
				&& !itemDTO.getItemQualification().equals(
						dbItem.getItemQualification())) {
			dbItem.setItemQualification(itemDTO.getItemQualification());
			isItemInfoChanged = true;
		}

		if (StringUtils.isNotEmpty(itemDTO.getPackingList())
				&& !itemDTO.getPackingList().equals(dbItem.getPackingList())) {
			dbItem.setPackingList(itemDTO.getPackingList());
			isItemInfoChanged = true;
		}
		if (isItemInfoChanged) {
			itemMybatisDAO.updateItem(dbItem);
			if (changedItemDraft != null
					&& ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL
							.equals(itemDTO.getProductChannelCode())) {
				// 更新草稿表
				itemDraftMapper.updateByPrimaryKeySelective(changedItemDraft);
			}
		}
		// 商品图片
		if (itemDTO.getPicUrls() != null) {
			itemPictureDAO.updateDeleteFlagByItemId(itemDTO.getItemId());
			insertItemPics(itemDTO);
			if (changedItemDraft != null
					&& ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL
							.equals(itemDTO.getProductChannelCode())) {
				// 更新草稿的图片
				doUpdateItemDraftPicture(itemDTO, dbItem, changedItemDraft);
			}
		}
		// 描述
		if (StringUtils.isNotEmpty(itemDTO.getDescribeUrl())) {
			modifyItemDescribe(itemDTO);
			if (changedItemDraft != null
					&& ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL
							.equals(itemDTO.getProductChannelCode())) {
				// 更新草稿的描述
				doUpdateItemDraftDescribe(itemDTO, changedItemDraft);
			}
		}

	}

	private void doUpdateItemDraftPicture(ItemDTO itemDTO, ItemDTO itemFromDb,
			ItemDraft itemDraftFromDB) {
		// 图片
		List<ItemDraftPicture> draftPicturesList = Lists.newArrayList();
		// 把商品Id和图片路径保存到商品图片表
		String[] picUrls = itemDTO.getPicUrls();
		if (picUrls != null && picUrls.length > 0) {
			int i = 1;
			for (String pictureUrl : picUrls) {
				if (StringUtils.isEmpty(pictureUrl)) {
					continue;
				}
				ItemDraftPicture draftPic = new ItemDraftPicture();
				if (i == 1) {
					draftPic.setIsFirst(1);
				} else {
					draftPic.setIsFirst(0);
				}
				draftPic.setSortNumber(i);
				draftPic.setItemDraftId(itemDraftFromDB.getItemDraftId());
				draftPic.setSellerId(itemDTO.getSellerId() == null ? 0L
						: itemDTO.getSellerId());
				draftPic.setShopId(itemFromDb.getShopId() == null ? 0
						: itemFromDb.getShopId());
				draftPic.setPictureUrl(pictureUrl);
				draftPic.setPictureStatus(1);
				draftPic.setCreated(new Date());
				draftPic.setCreateId(itemDTO.getModifyId());
				draftPic.setCreateName(itemDTO.getModifyName());
				draftPic.setModifyName(itemDTO.getModifyName());
				draftPic.setModifyId(itemDTO.getModifyId());
				draftPic.setModified(new Date());
				draftPicturesList.add(draftPic);
				i++;
			}
		}

		if (CollectionUtils.isNotEmpty(draftPicturesList)) {
			itemDraftPictureMapper.deleteDraftPicByItemDraftId(itemDraftFromDB
					.getItemDraftId());
			// 更新图片
			itemDraftPictureMapper.batchInsert(draftPicturesList);
		}

	}

	private void doUpdateItemDraftDescribe(ItemDTO itemDTO,
			ItemDraft itemDraftFromDB) {
		// 查询数据库中描述
		ItemDraftDescribe describeFromDb = itemDraftDescribeMapper
				.selectByItemDraftId(itemDraftFromDB.getItemDraftId());
		if (describeFromDb == null) {
			describeFromDb = new ItemDraftDescribe();
			describeFromDb.setCreateId(itemDTO.getModifyId());
			describeFromDb.setCreateName(itemDTO.getModifyName());
			describeFromDb.setCreateTime(new Date());
			describeFromDb.setDescribeContent(itemDTO.getDescribeUrl());
			describeFromDb.setItemDraftId(itemDraftFromDB.getItemDraftId());
			describeFromDb.setModifyId(itemDTO.getModifyId());
			describeFromDb.setModifyName(itemDTO.getModifyName());
			describeFromDb.setModifyTime(new Date());
			itemDraftDescribeMapper.insertSelective(describeFromDb);
		} else {
			describeFromDb.setModifyId(itemDTO.getModifyId());
			describeFromDb.setModifyName(itemDTO.getModifyName());
			describeFromDb.setModifyTime(new Date());
			describeFromDb.setDescribeContent(itemDTO.getDescribeUrl());
			itemDraftDescribeMapper.updateByPrimaryKeyWithBLOBs(describeFromDb);
		}
	}

	@Override
	public DataGrid<ItemQueryOutDTO> queryItemListForSaleManageSystem(
			ItemQueryInDTO itemInDTO, Pager page) {
		DataGrid<ItemQueryOutDTO> dataGrid = new DataGrid<ItemQueryOutDTO>();
		List<ItemQueryOutDTO> listItemDTO = null;
		Long size = 0L;
		try {
			// 待审核和审核驳回
			if (itemInDTO.getItemStatus() == 0
					|| itemInDTO.getItemStatus() == 2) {
				listItemDTO = itemMybatisDAO.queryItemDraftForSaleManageSystem(
						itemInDTO, page);
				size = itemMybatisDAO
						.queryCountItemDraftForSaleManageSystem(itemInDTO);
			} else {// 其他状态查商品主表
				listItemDTO = itemMybatisDAO.queryItemForSaleManageSystem(
						itemInDTO, page);
				List<ItemQueryOutDTO> itemList = new ArrayList<ItemQueryOutDTO>();
				if(null != itemList){
					for (ItemQueryOutDTO itemQueryOutDTO : listItemDTO) {
						itemQueryOutDTO.setShelvesStatus(itemInDTO.getShelvesStatus());
						itemList.add(itemQueryOutDTO);
					}
				}
				size = itemMybatisDAO
						.queryCountItemForSaleManageSystem(itemInDTO);
			}
			if (CollectionUtils.isNotEmpty(listItemDTO)) {
				for (ItemQueryOutDTO item : listItemDTO) {
					ExecuteResult<Map<String, Object>> categoryResult = itemCategoryService
							.queryItemOneTwoThreeCategoryName(
									Long.valueOf(item.getCid()), ">");
					if (categoryResult != null
							&& MapUtils.isNotEmpty(categoryResult.getResult())) {
						String catName = (String) categoryResult.getResult()
								.get("categoryName");
						item.setcName(catName);
					}
				}
			}
			dataGrid.setTotal(size);
			dataGrid.setRows(listItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return dataGrid;
	}

	@Override
	public ExecuteResult<Item> queryItemById(Long itemId) {
		ExecuteResult<Item> executeResult = new ExecuteResult();
		try {
			Item item = this.itemMybatisDAO.queryItemByPk(itemId);
			if (item != null) {
				executeResult.setResult(item);
				executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
				executeResult.setCode(ResultCodeEnum.SUCCESS.getMessage());
			} else {
				executeResult.setCode(ResultCodeEnum.OUTPUT_IS_NULL.getCode());
				executeResult.setResultMessage("item is null");
				executeResult.addErrorMsg("item is null");
				return executeResult;
			}
		} catch (Exception e) {
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.addErrorMsg(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public DataGrid<ItemWaringOutDTO> queryPagedFailedItemWarningList(Pager page) {
		DataGrid<ItemWaringOutDTO> itemWaringOutDTODataGrid = new DataGrid<>();
		try {
			Long failedDownErpCount = itemMybatisDAO.queryFailedDownErpCount();

			if (failedDownErpCount == null || failedDownErpCount <= 0L) {
				itemWaringOutDTODataGrid.setTotal(0L);
				return itemWaringOutDTODataGrid;
			}

			List<ItemWaringOutDTO> resultList = itemMybatisDAO
					.queryFailedDownErpList(page.getPageOffset(),
							page.getRows());

			itemWaringOutDTODataGrid.setTotal(failedDownErpCount);

			itemWaringOutDTODataGrid.setRows(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ItemExportServiceImpl::queryPagedFailedItemWarningList:"
					+ e);
		}
		return itemWaringOutDTODataGrid;
	}

	@Transactional
	@Override
	public ExecuteResult<String> driveItemDownErpByHand(Long itemId) {
		ExecuteResult<String> result = new ExecuteResult<>();

		if (itemId == null || itemId <= 0L) {
			result.addErrorMessage("itemId为空！");
			return result;
		}

		try {
			ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(itemId);
			if (dbItem == null) {
				result.addErrorMessage("没有查询到该商品信息！");
				return result;
			}

			if (StringUtils.isEmpty(dbItem.getErpFirstCategoryCode())) {
				result.addErrorMessage("getErpFirstCategoryCode为空！");
				return result;
			}

			if (StringUtils.isEmpty(dbItem.getErpFiveCategoryCode())) {
				result.addErrorMessage("ErpFiveCategoryCode为空！");
				return result;
			}
			// 如果商品状态为审核通过, 内部供应商商品时, 则商品信息下行ERP
			ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(Long
					.valueOf(dbItem.getItemSpuId()));
			if (itemSpu == null) {
				result.addErrorMessage("itemSpu为空！");
				return result;
			}
			doItemDownErp(itemSpu, dbItem);

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return result;
	}

	@Override
	public ExecuteResult<List<WaitAuditItemInfoDTO>> queryWaitAuditItemInfo() {
		ExecuteResult<List<WaitAuditItemInfoDTO>> result = new ExecuteResult<List<WaitAuditItemInfoDTO>>();
		List<WaitAuditItemInfoDTO> list = Lists.newArrayList();
		WaitAuditItemInfoDTO waitAuditItemInfoDTO = new WaitAuditItemInfoDTO();
		Integer waitAuditCount = itemMybatisDAO.queryWaitingAuditItemInfo();
		waitAuditItemInfoDTO.setInfoType("1");
		waitAuditItemInfoDTO.setCount(waitAuditCount);
		list.add(waitAuditItemInfoDTO);
		result.setResult(list);
		return result;
	}

	@Override
	public ExecuteResult<QueryItemStockDetailOutDTO> queryItemQuantityInfo(
			QueryItemStockDetailInDTO quantityInfoInDTO) {
		ExecuteResult<QueryItemStockDetailOutDTO> executeResult = new ExecuteResult<>();
		try {
			if (quantityInfoInDTO == null) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL
						.getCode());
				executeResult
						.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL
								.getMessage());
				executeResult.addErrorMsg("quantityInfoInDTO is null");
				return executeResult;
			}
			if (quantityInfoInDTO.getIsBoxFlag() == null
					|| StringUtils.isEmpty(quantityInfoInDTO.getItemCode())) {
				executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL
						.getCode());
				executeResult
						.setResultMessage(ResultCodeEnum.INPUT_PARAM_IS_NULL
								.getMessage());
				executeResult
						.addErrorMsg("IsBoxFlag is null or ItemCode is empty");
				return executeResult;
			}
			QueryItemStockDetailOutDTO queryItemStockDetailOutDTO = this.itemSkuPublishInfoMapper
					.queryItemStockDetailInDTO(quantityInfoInDTO);
			if (queryItemStockDetailOutDTO == null) {
				queryItemStockDetailOutDTO = new QueryItemStockDetailOutDTO();
			}
			executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
			executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
			executeResult.setResult(queryItemStockDetailOutDTO);
		} catch (Exception e) {
			LOGGER.error("查询供应商商品列表总库存和锁定库存失败，错误信息：", e);
			executeResult.setCode(ResultCodeEnum.ERROR.getCode());
			executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
			executeResult.addErrorMsg(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 限时购 - 新增活动 - 查询商品接口
	 * @author li.jun
	 * @time 2017-10-09
	 */
	@Override
	public ItemQueryOutDTO querySellerCenterItem(ItemQueryInDTO itemInDTO) {
			ItemQueryOutDTO itemOutDto= new ItemQueryOutDTO();
			try {
				itemOutDto = itemMybatisDAO.querySellerCenterItem(itemInDTO);
				if(null != itemOutDto){
					// 销售属性
					String attrSale = itemOutDto.getAttrSale();
					List<ItemAttrDTO> attrSales = null;
					if (StringUtils.isNotEmpty(attrSale)) {
						attrSales = this.getItemAttrList(attrSale);
						itemOutDto.setAttrSales(attrSales);
					}
					// 非销售属性
					String attrStr = itemOutDto.getAttributes();
					List<ItemAttrDTO> attrs = null;
					if (StringUtils.isNotEmpty(attrStr)) {
						attrs = this.getItemAttrList(attrStr);
						itemOutDto.setAttributess(attrs);
					}
					// 根据cid查询类目属性
					ExecuteResult<List<ItemCatCascadeDTO>> er = itemCategoryService.queryParentCategoryList(3,Long.valueOf(itemOutDto.getCid()));
					itemOutDto.setItemCatCascadeDTO(er.getResult());
					List<VenusItemSkuOutDTO> venusItemSkuOutDTOs = itemSkuDAO.selectByItemIdAndSellerId(itemOutDto.getItemId(),Long.valueOf(itemOutDto.getSellerId()));
					if (venusItemSkuOutDTOs != null && venusItemSkuOutDTOs.size() > 0) {
						for (VenusItemSkuOutDTO skuOut : venusItemSkuOutDTOs) {
							// 根据sku的销售属性keyId:valueId查询商品属性
							ExecuteResult<List<ItemAttrDTO>> itemAttr = itemCategoryService.queryCatAttrByKeyVals(skuOut.getAttributes());
							// 根据skuID查询对应ｓｋｕ下面的显示库存
							skuOut.setDisplayQuantity(itemSkuPublishInfoMapper.queryBySkuId(skuOut.getSkuId()).get(0).getDisplayQuantity());
							skuOut.setItemAttr(itemAttr.getResult());
							DataGrid<ItemSkuLadderPrice> ladderList = itemSkuPriceService.queryLadderPriceBySellerIdAndSkuId(itemInDTO.getSellerId(),skuOut.getSkuId());
							if (ladderList.getRows() != null&& ladderList.getRows().size() > 0) {
								 skuOut.setItemSkuLadderPrices(ladderList.getRows());
							}
						}
					}
					itemOutDto.setVenusItemSkuOutDTOs(venusItemSkuOutDTOs);
			  }   
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
			return itemOutDto;
	}

	@Override
	public List<ItemQueryOutDTO> querySellerCenterItemList(ItemQueryInDTO itemInDTO) {
		List<ItemQueryOutDTO> itemOutDto= new ArrayList<ItemQueryOutDTO>();
		itemOutDto = itemMybatisDAO.querySellerCenterItemList(itemInDTO);
		return itemOutDto;
	}

	@Override
	public VenusItemSkuOutDTO queryItemPicsFirst(Long itemId) {
		VenusItemSkuOutDTO itemSKU = new VenusItemSkuOutDTO();
		List<ItemSkuPicture> itemSkuPicture = itemSkuDAO.queryItemSKUPicsFirst(itemId);
		if(null != itemSkuPicture && itemSkuPicture.size() > 0){
			itemSKU.setItemSkuPictureList(itemSkuPicture);
		}else{
			List<ItemPicture> itemPicture = itemPictureDAO.queryItemPicsFirst(itemId);
			itemSKU.setItemPictureList(itemPicture);
		}
		return itemSKU;
	}

	/**
	 * 限时购 - 根据itemCode 查询sku属性相关信息
	 * @author li.jun
	 * @time 2017-10-26
	 */
	@Override
	public ExecuteResult<List<VenusItemSkuOutDTO>> getItemSkuList(String itemCode) {
		ExecuteResult<List<VenusItemSkuOutDTO>> result = new ExecuteResult<List<VenusItemSkuOutDTO>>();
		try{
			Item item = itemMybatisDAO.queryItemByItemCode(itemCode);
			if(null !=item){
				List<VenusItemSkuOutDTO> venusItemSkuOutDTOs = itemSkuDAO.selectItemSkuByItemId(item.getItemId());
				if (null != venusItemSkuOutDTOs && venusItemSkuOutDTOs.size() > 0) {
					for (VenusItemSkuOutDTO skuOut : venusItemSkuOutDTOs) {
						// 根据sku的销售属性keyId:valueId查询商品属性
						ExecuteResult<List<ItemAttrDTO>> itemAttr = itemCategoryService.queryCatAttrByKeyVals(skuOut.getAttributes());
						skuOut.setItemAttr(itemAttr.getResult());
					}
				}
				result.setResult(venusItemSkuOutDTOs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return result;
	}


	/**
	 * 限时购 - 根据skuCode 查询库存和阶梯价等相关信息相关信息
	 * @author li.jun
	 * @time 2017-10-26
	 */
	@Override
	public ExecuteResult<VenusItemSkuOutDTO> getItemSkuBySkuCode(String skuCode) {
		ExecuteResult<VenusItemSkuOutDTO> result = new ExecuteResult<VenusItemSkuOutDTO>();
		VenusItemSkuOutDTO skuOut = new VenusItemSkuOutDTO();
		try{
			ItemSku itemSku = itemSkuDAO.selectItemSkuBySkuCode(skuCode);
			if(null != itemSku){
				skuOut.setSkuCode(skuCode);
				//根据skuID查询对应sku下面的库存
				List<ItemSkuPublishInfo> itemSkuPublishInfo = itemSkuPublishInfoMapper.queryBySkuId(itemSku.getSkuId());
				if(null != itemSkuPublishInfo && itemSkuPublishInfo.size()>0){
					ItemSkuPublishInfo  publishInfo = itemSkuPublishInfo.get(0);
					//skuOut.setDisplayQuantity(publishInfo.getDisplayQuantity());
					skuOut.setMimQuantity(publishInfo.getMimQuantity());
					if(publishInfo.getIsPurchaseLimit() == 0){//是否限购 0不限 1限购 //不限购的时候把这个最大设置为99999999
						skuOut.setMaxPurchaseQuantity(999999999);
					}else{
						skuOut.setMaxPurchaseQuantity(publishInfo.getMaxPurchaseQuantity());
					}
					skuOut.setReserveQuantity(publishInfo.getReserveQuantity());
				}
				//根据skuID 和sellerId查询对应的阶梯价
				DataGrid<ItemSkuLadderPrice> ladderList = itemSkuPriceService.queryLadderPriceBySellerIdAndSkuId(itemSku.getSellerId(),itemSku.getSkuId());
				if (ladderList.getRows() != null&& ladderList.getRows().size() > 0) {
					skuOut.setItemSkuLadderPrices(ladderList.getRows());
				}
				//查询商品图片信息，如果skupicture存在,则取skupicture 否则取itempicture
				List<ItemSkuPicture> skuPictureList = itemSkuDAO.selectSkuPictureBySkuId(itemSku.getSkuId());
				if(null !=skuPictureList && skuPictureList.size()>0){
					skuOut.setItemSkuPictureList(skuPictureList);
				}else{
				    List<ItemPicture> pictureList = itemPictureDAO.queryItemPicsById(itemSku.getItemId());
				    skuOut.setItemPictureList(pictureList);
				}
				result.setResult(skuOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return result;
	}

	
}

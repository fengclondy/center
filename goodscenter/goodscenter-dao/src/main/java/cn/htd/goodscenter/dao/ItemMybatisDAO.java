package cn.htd.goodscenter.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListInDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsMyItemListOutDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.dto.ItemAdDTO;
import cn.htd.goodscenter.dto.ItemDBDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemQueryInDTO;
import cn.htd.goodscenter.dto.ItemQueryOutDTO;
import cn.htd.goodscenter.dto.ItemShopCartDTO;
import cn.htd.goodscenter.dto.ItemShopCidDTO;
import cn.htd.goodscenter.dto.ItemStatusModifyDTO;
import cn.htd.goodscenter.dto.ItemWaringOutDTO;
import cn.htd.goodscenter.dto.SkuInfoDTO;
import cn.htd.goodscenter.dto.SkuPictureDTO;
import cn.htd.goodscenter.dto.indto.PauseItemInDTO;
import cn.htd.goodscenter.dto.indto.QueryHotSellItemInDTO;
import cn.htd.goodscenter.dto.indto.QueryNewPublishItemInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockSearchInDTO;
import cn.htd.goodscenter.dto.mall.MallSearchItemDTO;
import cn.htd.goodscenter.dto.outdto.HotSellItemOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemToDoCountDTO;
import cn.htd.goodscenter.dto.outdto.SyncItemStockSearchOutDTO;
import cn.htd.goodscenter.dto.presale.PreSaleProdQueryDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusQueryDropdownItemInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusQueryDropdownItemListOutDTO;
import cn.htd.goodscenter.dto.vip.VipItemEntryInfoDTO;
import cn.htd.goodscenter.dto.vip.VipItemListInDTO;
import cn.htd.goodscenter.dto.vip.VipItemListOutDTO;

public interface ItemMybatisDAO extends BaseDAO<Item> {

	public List<ItemQueryOutDTO> queryItemList(@Param("entity") ItemQueryInDTO itemInDTO, @Param("page") Pager<ItemQueryOutDTO> page);

	/**
	 * 
	 * <p>
	 * Discription:[批量更改商品状态]
	 * </p>
	 */
	public void updateItemStatusBatch(@Param("ids") List<Long> ids, @Param("changeReason") String changeReason, @Param("itemStatus") int itemStatus, @Param("auditFlag") String auditFlag);

	public Long queryCount(@Param("entity") ItemQueryInDTO itemInDTO);

	public ItemDTO getItemDTOById(@Param("id") Long itemId);

	/**
	 * <p>
	 * Discription:[根据itemId 串，查询对应的item 数据信息]
	 * </p>
	 */
	public List<ItemDTO> getItemDTOByItemIds(@Param("iids") Long[] iids);

	public List<SkuInfoDTO> queryItemSkuInfo(@Param("id") Long itemId);

	public void addItem(Item item);

	/**
	 * 
	 * <p>
	 * Discription:[更新商品信息]
	 * </p>
	 */
	public void updateItem(ItemDTO itemDTO);

	public long queryItemCounts(@Param("params") Map<String, Object> params);

	/**
	 * 
	 * <p>
	 * Discription:[根据SKU id 查询SKU信息 包括商品名称]
	 * </p>
	 */
	public ItemSku getItemSkuById(@Param("param") ItemShopCartDTO skuDTO);

	/**
	 * 
	 * <p>
	 * Discription:[批量修改商品广告语]
	 * </p>
	 */
	public void modifyItemAdBatch(@Param("ads") List<ItemAdDTO> ads);

	/**
	 * 
	 * <p>
	 * Discription:[批量修改商品的店铺分类]
	 * </p>
	 */
	public void modifyItemShopCidBatch(@Param("cids") List<ItemShopCidDTO> cids);

	/**
	 * 
	 * <p>
	 * Discription:[查询SKU图片]
	 * </p>
	 */
	public List<SkuPictureDTO> querySkuPics(@Param("skuId") Long skuId);

	/**
	 * 
	 * <p>
	 * Discription:[获取商品ID]
	 * </p>
	 */
	public Long getItemId();

	/**
	 * 
	 * <p>
	 * Discription:[根据平台库商品ID查询商品衍生出的]
	 * </p>
	 */
	//public List<ItemDTO> querySellerItems(@Param("itemId") Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[根据平台商品ID 查询卖该商品的店铺]
	 * </p>
	 */
	//public List<Long> queryShopIdByPlatItemId(@Param("itemId") Long itemId, @Param("page") Pager<Long> pager);

	/**
	 * 
	 * <p>
	 * Discription:[根据平台商品ID 查询卖该商品的店铺总数量]
	 * </p>
	 */
	//public Long queryShopCountByPlatItemId(@Param("itemId") Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[商品库待入库已入库状态修改]
	 * </p>
	 */
	public void updateItemPlatStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[按照店铺ID修改商品状态]
	 * </p>
	 */
	public void updateShopItemStatus(ItemStatusModifyDTO statusDTO);

	/**
	 *
	 * <p>
	 * Discription:[按照itemId修改商品状态]
	 * </p>
	 */
	public void updateShopItemStatusByItemId(ItemStatusModifyDTO itemStatusModifyDTO);

	/**
	 * 
	 * <p>
	 * Discription:[批量更改商品状态，特殊情况用于修改店铺类目关闭启用使用]
	 * </p>
	 */
	public void updateStatusUserShopCate(@Param("ids") List<Long> ids, @Param("changeReason") String changeReason, @Param("itemStatus") int itemStatus);

	/**
	 * 
	 * <p>
	 * Discription:[根据商品的非销售属性查询商品信息]
	 * </p>
	 */
	public List<ItemDTO> queryItemDTOByAttr(@Param("attr") String attr);

	/**
	 * 
	 * <p>
	 * Discription:[根据cid和sellerId查询商品信息]
	 * </p>
	 */
	public List<ItemDTO> getItemByCid(ItemDTO itemDTO);

	/**
	 *
	 * <p>
	 * Discription:[查询活动待办]
	 * </p>
	 */
	public List<ItemDBDTO> queryItemDB();

	/**
	 *
	 * <p>
	 * Discription:[批量修改市场价格]
	 * </p>
	 */
	public void mondifyMarketPrice(@Param("itemIds") String[] itemIds, @Param("price") BigDecimal price);

	/**
	 * 用于搜索引擎同步数据
	 * @param syncTime 同步时间
	 * @return
	 */
	public List<Item> queryItemBySyncTime(@Param("syncTime") Date syncTime,@Param("page") Pager page);
	/**
	 * 
	 * @param itemName
	 * @return
	 */
	public Integer queryItemByName(@Param("itemName") String itemName,@Param("sellerId") String sellerId,@Param("shopId") String shopId);
	
	public Item queryItemByPk(@Param("itemId") Long itemId);
	
	public void batchUpdateHtdItemStatus(@Param("itemIdList") List<Long> itemIdList,@Param("statusChangeReason") String statusChangeReason,@Param("itemStatus") Integer itemStatus,
			@Param("modifyId") Long modifyId,@Param("modifyName") String modifyName);

	/**
	 * 根据品牌品类查询商品
	 * @param item
	 * @return
	 */
	public Long queryItemCountByCidAndBrandId(Item item);
	
	public Long querySyncItemStockListCount(SyncItemStockSearchInDTO syncItemStockSearchInDTO);
	
	public List<SyncItemStockSearchOutDTO> querySyncItemStockList(SyncItemStockSearchInDTO syncItemStockSearchInDTO);
	
	public void pauseItemByShopId(PauseItemInDTO pauseItemInDTO);
	
	public String queryItemCodeSeq();
	
	public int updateByPrimaryKeySelective(Item item);
	
	public void updateItemStatusByPk(@Param("itemId") Long itemId,@Param("itemStatus") Integer itemStatus,
			@Param("modifyId") Long modifyId,@Param("modifyName")  String modifyName);
	
	
	public void updateItemAuditStatusByPk(@Param("itemId") Long itemId,@Param("itemStatus") Integer itemStatus,
			@Param("modifyId") Long modifyId,@Param("modifyName")  String modifyName,
			@Param("erpStatus")  String erpStatus,@Param("erpErrorMsg")  String erpErrorMsg,
			@Param("erpCode")  String erpCode);
	
	public List<VenusQueryDropdownItemListOutDTO> selectItemInfoByCatAndBrandId(VenusQueryDropdownItemInDTO venusQueryDropdownItemInDTO);

	/**
	 * 根据shop_id,seller_id统计商品状态
	 * @return
	 */
	List<ItemToDoCountDTO> selectToDoCount(PauseItemInDTO dto);
	
	void updateErpStatus(@Param("itemId") Long itemId,@Param("erpStatus") String erpStatus,@Param("errorMsg") String errorMsg);

	Item queryItemByItemCode(String itemCode);
	
	/**
	 * 查询内部供应商新上架商品
	 * 
	 * @param queryNewPublishItemInDTO
	 * @return
	 */
	List<Item> queryInnerNewPublishItemList(QueryNewPublishItemInDTO queryNewPublishItemInDTO);
	
	/**
	 * 查询外部供应商新上架商品
	 * 
	 * @param queryNewPublishItemInDTO
	 * @return
	 */
	List<Item> queryOuterNewPublishItemList(QueryNewPublishItemInDTO queryNewPublishItemInDTO);
	
	/**
	 * 查询下行失败商品
	 * 
	 * @param paramMap
	 * @return
	 */
	List<Item> queryFailedDownErpItemList(Map<String,Object> paramMap);
	
	List<VipItemListOutDTO> queryVipItemList(VipItemListInDTO vipItemListInDTO);
	
	List<VipItemEntryInfoDTO> queryVipItemEntryDetailList(Long vipItemId);
	
	Long queryItemCountBySpuIdAndSellerId(@Param("spuId") Long spuId,@Param("sellerId") Long sellerId);
	
	Item queryItemInfoBySpuIdAndSellerId(@Param("spuId") Long spuId,@Param("sellerId") Long sellerId);
	
	List<HotSellItemOutDTO> queryInnerHotSellItemList(QueryHotSellItemInDTO queryHotSellItemInDTO);
	
	List<HotSellItemOutDTO> queryOuterHotSellItemList(QueryHotSellItemInDTO queryHotSellItemInDTO);
	
	List<HotSellItemOutDTO> queryProductPlusItemList(QueryHotSellItemInDTO queryHotSellItemInDTO);

	int updateHasVipPrice(@Param("itemId") Long itemId, @Param("hasVipPrice") Integer hasVipPrice);
	
	List<MallSearchItemDTO> queryItemSearchListInfo(List<String> list);

	int queryItemIsInSaleArea(@Param("itemId")Long itemId, @Param("isBoxFlag")Integer isBoxFlag, @Param("provinceSiteCode")String provinceSiteCode,
							  @Param("currentSiteCode")String currentSiteCode, @Param("siteChildCodeList")List<String> siteChildCodeList);
	
	//add by zhangxiaolong start
	public List<ItemQueryOutDTO> queryItemForSaleManageSystem(@Param("entity") ItemQueryInDTO itemInDTO, @Param("page") Pager<ItemQueryOutDTO> page);
	
	public Long queryCountItemForSaleManageSystem(@Param("entity") ItemQueryInDTO itemInDTO);
	
	public List<ItemQueryOutDTO> queryItemDraftForSaleManageSystem(@Param("entity") ItemQueryInDTO itemInDTO, @Param("page") Pager<ItemQueryOutDTO> page);
	
	public Long queryCountItemDraftForSaleManageSystem(@Param("entity") ItemQueryInDTO itemInDTO);
	
	public void updateItemModifyTimeByItemId(@Param("itemId") Long itemId,@Param("upShelf") Integer upShelf);

	String queryMaxItemCode();

	String queryMaxSkuCode();

	String queryMaxSpuCode();

	List<ItemWaringOutDTO> queryFailedDownErpList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	Long queryFailedDownErpCount();
	
	List<Map<String,Object>> batchQueryItemCodeBySpuCodeAndSellerId(@Param("supplierId") Long supplierId,@Param("list") List<String> list);
	
	Integer queryWaitingAuditItemInfo();
	
	void updateAuditStatusChangeReason(@Param("changeReason") String changeReason,@Param("itemId") Long itemId);
	//add by zhangxiaolong end

	int updateFirstAndFiveCategoryCodeByItemId(Item item);

	/**
	 *
	 * @param map
	 * @param isIncrement 是否增量
	 * @return
	 */
	List<Item> queryPreSaleItemList(Map<String, Object> map);

	void updatePreSaleFlagByItemId(@Param("preSaleFlag")int preSaleFlag,@Param("itemId") Long itemId);
	
	PreSaleProdQueryDTO queryPreSaleItemInfo(@Param("skuCode") String skuCode);

	void updateItemModifiedByItemId(Long itemId);
	
	public ItemQueryOutDTO querySellerCenterItem(@Param("entity") ItemQueryInDTO itemInDTO);
	
	public List<ItemQueryOutDTO> querySellerCenterItemList(@Param("entity") ItemQueryInDTO itemInDTO);
	
	public List<Item> queryItemBySellerId(@Param("sellerId") String sellerId);

	public List<Item> queryItemInfo(@Param("itemName")String itemName,@Param("sellerId")Long sellerId);

    Long queryVmsDraftItemSkuListCount(@Param("param") QueryVmsMyItemListInDTO queryVmsMyItemListInDTO);

	List<QueryVmsMyItemListOutDTO> queryVmsDraftItemSkuList(@Param("param") QueryVmsMyItemListInDTO queryVmsMyItemListInDTO, @Param("pager")Pager pager);
}
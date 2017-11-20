package cn.htd.goodscenter.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.htd.goodscenter.dto.indto.QueryItemStockDetailInDTO;
import cn.htd.goodscenter.dto.middleware.outdto.QueryItemStockOutDTO;
import cn.htd.goodscenter.dto.outdto.QueryItemStockDetailOutDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemPicture;
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
import cn.htd.goodscenter.dto.WaitAuditItemInfoDTO;
import cn.htd.goodscenter.dto.indto.PauseItemInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockInDTO;
import cn.htd.goodscenter.dto.indto.SyncItemStockSearchInDTO;
import cn.htd.goodscenter.dto.outdto.ItemToDoCountDTO;
import cn.htd.goodscenter.dto.outdto.SyncItemStockSearchOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍 :商品信息的接口类 Item]
 * </p>
 */
public interface ItemExportService {
	/**
	 * <p>
	 * Discription:[方法功能中文描述:商品信息管理的批量操作 （修改商品的状态）]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemStatusBatch(ItemStatusModifyDTO statusDTO);

	/**
	 * 
	 * <p>
	 * Discription:[ 按照店铺ID修改商品状态 ]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopItemStatus(ItemStatusModifyDTO statusDTO);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:查询商品的信息列表]
	 * </p>
	 */
	public DataGrid<ItemQueryOutDTO> queryItemList(ItemQueryInDTO itemInDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:根据id查询商品详情]
	 * </p>
	 */
	public ExecuteResult<ItemDTO> getItemById(Long itemId,Integer itemStatus);

	/**
	 * <p>
	 * Discription:[根据itemId 串，查询对应的item 数据信息]
	 * </p>
	 */
	public List<ItemDTO> getItemDTOByItemIds(Long[] iids);

	/**
	 * <p>
	 * Discription:[ 商品信息添加<br>
	 * 平台调用：operator:2 平台<br>
	 * 暂存：itemStatus:4 在售; platLinStatus:1 未符合待入库; <br>
	 * 发布：itemStatus:4 在售; platLinStatus:3 已入库; <br>
	 * 卖家调用：operator:1 卖家<br>
	 * 1)从平台库选择：addSource:2 <br>
	 * 暂存：itemStatus:1 未发布; plstItemId:从平台库选择的商品ID<br>
	 * 发布：itemStatus:2 待审核; plstItemId:从平台库选择的商品ID<br>
	 * 2)自定义添加：addSource:1<br>
	 * 暂存：itemStatus:1 未发布; <br>
	 * 发布：itemStatus:2 待审核; <br>
	 * 
	 * ]
	 * </p>
	 */
	public ExecuteResult<ItemDTO> addItemInfo(ItemDTO itemDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据ID更改商品信息]
	 * </p>
	 */
	public ExecuteResult<ItemDTO> modifyItemById(ItemDTO itemDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询商品信息用于购物车展示]
	 * </p>
	 */
	public ExecuteResult<ItemShopCartDTO> getSkuShopCart(ItemShopCartDTO skuDto);

	/**
	 * 
	 * <p>
	 * Discription:[批量修改商品广告语]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemAdBatch(List<ItemAdDTO> ads);

	/**
	 * 
	 * <p>
	 * Discription:[批量修改商品店铺分类]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemShopCidBatch(List<ItemShopCidDTO> ads);

	/**
	 * 
	 * <p>
	 * Discription:[根据Cid查询商品信息]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemQueryOutDTO>> queryItemByCid(Long cid, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[根据平台商品ID 查询卖该商品的店铺]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemQueryOutDTO>> queryItemByPlatItemId(Long itemId, Pager<Long> pager);

	/**
	 * 
	 * <p>
	 * Discription:[修改平台商品库商品入库状态]
	 * </p>
	 * 
	 * @param ids
	 * @param status
	 *            plat_link_status 与平台商品库关联状态：1：未符合待入库2：待入库3：已入库4：删除
	 */
	public ExecuteResult<String> modifyItemPlatStatus(List<Long> ids, Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[ 删除商品 除在售 和 待审核的商品都可以删除 ]
	 * </p>
	 */
	public ExecuteResult<String> deleteItem(Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[将商品添加到平台商品库]
	 * </p>
	 */
	public ExecuteResult<String> addItemToPlat(Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[批量更改商品状态，特殊情况用于修改店铺类目关闭启用使用]
	 * </p>
	 */
	public ExecuteResult<String> updateStatusUserShopCate(ItemStatusModifyDTO itemStatusModifyDTO);

	/**
	 *
	 * <p>
	 * Discription:[查询宝贝待办]
	 * </p>
	 */
	public ExecuteResult<List<ItemDBDTO>> queryItemDB();

	/**
	 * 
	 * <p>
	 * Discription:[根据条件查询平台待处理的商品数量]
	 * </p>
	 */
	public Long queryStayItemVolume(ItemQueryInDTO itemInDTO);

	/**
	 *
	 * <p>
	 * Discription:[批量修改市场价格]
	 * </p>
	 */
	public ExecuteResult<Boolean> mondifyMarketPrice(String[] itemIds, BigDecimal price);
	
	/**
	 * 用于搜索引擎同步数据
	 * */
	public List<Item> queryItemBySyncTime(Date syncTime,@Param("page") Pager page);

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
	 * Discription:[查询商品信息列表，不包含图片和价格]
	 * </p>
	 */
	public List<ItemQueryOutDTO> queryItemsList(ItemQueryInDTO itemInDTO, Pager page);

	public List<SkuInfoDTO> queryItemSkuInfo(Long itemId);
	
	public ExecuteResult<DataGrid<SyncItemStockSearchOutDTO>> querySyncItemStockSearchList(SyncItemStockSearchInDTO syncItemStockSearchInDTO, Pager page);
	/**
	 * 同步库存
	 * @param syncItemStockInDTO
	 * @return
	 */
	public ExecuteResult<String> syncItemStock(SyncItemStockInDTO syncItemStockInDTO);
	
	/**
	 * 根据店铺id下架商品
	 * @param pauseItemInDTO
	 * @return
	 */
	public ExecuteResult<String> pauseItemByShopId(PauseItemInDTO pauseItemInDTO);

	/**
	 * 检查ERP一级类目与五级类目的关系
	 * @param erpFirstCategoryCode
	 * @param erpFiveCategoryCode
     * @return
     */
	public boolean checkErpFirstAndErpFiveCode(String erpFirstCategoryCode,String erpFiveCategoryCode);

	/**
	 * 根据shop_id,seller_id统计商品状态数量
	 * @return
	 */
	DataGrid<ItemToDoCountDTO> queryToDoCount(PauseItemInDTO dto);

	public DataGrid<ItemQueryOutDTO> querySellerItemList(ItemQueryInDTO itemInDTO, Pager page);

	ExecuteResult<String> modifySellerItemStatus(ItemStatusModifyDTO dto);

	/**
	 * 根据itemCode查询item信息
	 * @param itemCode
	 * @return
	 */
	public ExecuteResult<Item> getItemByCode(String itemCode);

	public ExecuteResult<String> updateItem(ItemDTO dto);
	
	/**
	 * 审核内部供应商商品
	 * 
	 * @param itemDTO
	 * @return
	 */
	public ExecuteResult<ItemDTO> auditInternalSupplierItem(ItemDTO itemDTO);
	
	/**
	 * 运营人员修改内部供应商商品
	 * 
	 * @param itemDTO
	 * @return
	 */
	public ExecuteResult<ItemDTO> modifyInternalSupplierItemBySalesman(ItemDTO itemDTO);
	
    /**
     * 
     * @param itemInDTO
     * @param page
     * @return
     */
	public DataGrid<ItemQueryOutDTO> queryItemListForSaleManageSystem(ItemQueryInDTO itemInDTO, Pager page);

	/**
	 * 查询item单表用
	 * @param itemId
	 * @return
	 */
	ExecuteResult<Item> queryItemById(Long itemId);

	/**
	 * 查询下行失败商品列表
	 *
	 * @return
	 */
	public DataGrid<ItemWaringOutDTO> queryPagedFailedItemWarningList(Pager page);

	public ExecuteResult<String> driveItemDownErpByHand(Long itemId);
	
	public ExecuteResult<List<WaitAuditItemInfoDTO>> queryWaitAuditItemInfo();

	/**
	 * 运营系统-供应商商品列表-查询总库存和锁定库存接口
	 * @param quantityInfoInDTO
	 * @return
	 */
	public ExecuteResult<QueryItemStockDetailOutDTO> queryItemQuantityInfo(QueryItemStockDetailInDTO quantityInfoInDTO);
	
	
	/**
	 * 限时购 - 新增活动 - 查询商品接口
	 * @author li.jun
	 * @time 2017-10-09
	 */
	public ItemQueryOutDTO querySellerCenterItem(ItemQueryInDTO itemInDTO);

	/**
	 * 限时购 - 新增活动 - 模糊查询商品名称
	 * @author li.jun
	 * @time 2017-10-09
	 */
	public List<ItemQueryOutDTO> querySellerCenterItemList(ItemQueryInDTO itemInDTO);
	
	
	/**
	 * 限时购 - 新增活动 - 查询商品主图
	 * @author li.jun
	 * @time 2017-10-09
	 */
	public VenusItemSkuOutDTO queryItemPicsFirst(Long itemId);
	
	/**
	 * 限时购 - 根据itemCode 查询sku属性相关信息
	 * @author li.jun
	 * @time 2017-10-26
	 */
	public ExecuteResult<List<VenusItemSkuOutDTO>> getItemSkuList(String itemCode); 
	
	/**
	 * 限时购 - 根据skuCode 查询库存和阶梯价等相关信息相关信息
	 * @author li.jun
	 * @time 2017-10-26
	 */
	public ExecuteResult<VenusItemSkuOutDTO> getItemSkuBySkuCode(String skuCode); 
	
	/**
	 * 超级老板中间件 - 根据sellerId,areaCode查当前供应商是否有商品上架
	 * @author xmz
	 * @time 2017-11-16
	 */
	public ExecuteResult<String> queryItemBySellerId(String sellerId, String areaCode);

}

package cn.htd.pricecenter.service;

import java.math.BigDecimal;
import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;
import cn.htd.pricecenter.domain.ItemSkuLadderPrice;
import cn.htd.pricecenter.dto.CommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.ItemSkuBasePriceDTO;
import cn.htd.pricecenter.dto.OrderItemSkuPriceDTO;
import cn.htd.pricecenter.dto.QueryCommonItemSkuPriceDTO;
import cn.htd.pricecenter.dto.StandardPriceDTO;

/**
 * 标准价格服务
 * 
 * @author zhangxiaolong
 *
 */
public interface ItemSkuPriceService {
	
	/**
	 * 新增一个基础价格
	 * @param itemSkuBasePrice
	 * @return
	 */
	public ExecuteResult<String> saveItemSkuBasePrice(ItemSkuBasePrice itemSkuBasePrice);
	
	/**
	 * 更新基础价格
	 * @param itemSkuBasePrice
	 * @return
	 */
	public ExecuteResult<String> updateItemSkuBasePrice(ItemSkuBasePrice itemSkuBasePrice);
	
	/**
	 * 新增一个内部价格：分组价，包厢价，区域价，会员星级价
	 * @param innerItemSkuPrice
	 * @return
	 */
	public ExecuteResult<String> addInnerItemSkuPrice(InnerItemSkuPrice innerItemSkuPrice);
	
	/**
	 * 更新内部价格
	 * 
	 * @param innerItemSkuPrice
	 * @return
	 */
	public ExecuteResult<String> updateInnerItemSkuPrice(InnerItemSkuPrice innerItemSkuPrice);

	/**
	 * 查询内部价格之会员等级
	 * @param skuId
	 * @param memberLevel
	 * @return
	 */
	public ExecuteResult<InnerItemSkuPrice> queryInnerItemSkuMemberLevelPrice(Long skuId, String priceType, String memberLevel, Integer isBoxFlag);
	
	/**
	 * 新增一个阶梯价格
	 * 
	 * @param itemSkuLadderPrice
	 * @return
	 */
	public ExecuteResult<String> addItemSkuLadderPrice(ItemSkuLadderPrice itemSkuLadderPrice);
	
	/**
	 * 更新阶梯价格
	 *  
	 * @param itemSkuLadderPrice
	 * @return
	 */
	public ExecuteResult<String> updateItemSkuLadderPrice(ItemSkuLadderPrice itemSkuLadderPrice);
	
	/**
	 * 修改标准价格
	 * 
	 * @param standardPriceDTO
	 * @return
	 */
	public ExecuteResult<String> updateItemSkuStandardPrice(StandardPriceDTO standardPriceDTO,Integer isBoxFlag);
	
	/**
	 * 批量查询基础价格
	 * 
	 * @param skuIdList
	 * @return
	 */
	public ExecuteResult<List<ItemSkuBasePrice>> batchQueryItemSkuBasePrice(List<Long> skuIdList);
	
	/**
	 * 查询内部供应商标准价格
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 */
	public ExecuteResult<StandardPriceDTO> queryStandardPrice4InnerSeller(Long skuId, Integer isBoxFlag);

	/**
	 * 查询基本价格
	 *
	 * @param skuId skuId
	 * @return 基本价格
	 */
	public ExecuteResult<ItemSkuBasePriceDTO> queryItemSkuBasePrice(Long skuId);

	/**
	 * 查询阶梯价格
	 * @param skuId
	 * @return
     */
	public List<ItemSkuLadderPrice> getSkuLadderPrice(Long skuId);

	/**
	 * 查询所有内部供应商价格(基础价格、区域价、分组价、会员等级价)
	 * @param skuId
	 * @return
     */
	public ExecuteResult<StandardPriceDTO> queryAllPrice4InnerSeller(Long skuId);

	/**
	 * 根据ItemId更新删除标记
	 * @param record
	 * @return
     */
	int updateDeleteFlagByItemId(ItemSkuLadderPrice record);
	
  public ExecuteResult<String> deleteAreaPrice(Long skuId,String areaCode);
  
  public ExecuteResult<String> deleteItemSkuInnerPrice(Long skuId,Integer isBoxFlag);

	/**
	 * 删除阶梯价格(根据skuId更新删除标记)
	 * @param skuId
	 * @return
	 */
	public ExecuteResult<String> deleteItemSkuLadderPrice(Long skuId);
	/**
	 * 批量更新阶梯价格
	 *
	 * @param itemSkuLadderPriceList
	 * @return
	 */
	public ExecuteResult<String> batchUpdateItemSkuLadderPrice(List<ItemSkuLadderPrice> itemSkuLadderPriceList);

	/**
	 * 根据查询基本价格
	 * 目前支持商品加的查询。因为外接商品SKUID唯一的
	 * @param itemCode 商品编码，内部商品时保存ERP编码，外接商品时保存外接商品SKUID
	 * @return
	 */
	ExecuteResult<ItemSkuBasePrice> queryItemSkuBasePriceByItemCode(String itemCode);

	/**
	 * 根据查询sellerId和skuId查询列表
	 */
	DataGrid<ItemSkuLadderPrice> queryLadderPriceBySellerIdAndSkuId(Long sellerId,Long skuId);
	
	/**
	 * 查询itemsku通用价格
	 * 
	 * @param queryCommonItemSkuPriceDTO
	 * @return
	 */
	ExecuteResult<CommonItemSkuPriceDTO> queryCommonItemSkuPrice(QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO);

	/**
	 * 根据skuId更新阶梯价格删除标记
	 *
	 */
	ExecuteResult<String> deleteLadderPriceBySkuId(ItemSkuLadderPrice itemSkuLadderPrice);
	
	/**
	 * 根据itemid查询阶梯价中最小的值
	 * 
	 * @param itemId
	 * @return
	 */
	ExecuteResult<BigDecimal> queryMinLadderPriceByItemId(Long itemId);
	
	/**
	 * 查询订单商品所需价格
	 * 
	 * @param queryCommonItemSkuPriceDTO
	 * @return
	 */
	ExecuteResult<OrderItemSkuPriceDTO> queryOrderItemSkuPrice(QueryCommonItemSkuPriceDTO queryCommonItemSkuPriceDTO);
}

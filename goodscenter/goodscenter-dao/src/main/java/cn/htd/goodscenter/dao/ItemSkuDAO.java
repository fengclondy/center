package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.dto.vms.QueryOffShelfItemInDTO;
import cn.htd.goodscenter.dto.vms.QueryOffShelfItemOutDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsItemPublishInfoInDTO;
import cn.htd.goodscenter.dto.vms.QueryVmsItemPublishInfoOutDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.ItemSkuPicture;
import cn.htd.goodscenter.domain.TradeInventory;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.indto.JudgeRecevieAddressInDTO;
import cn.htd.goodscenter.dto.outdto.CheckPromotionItemSkuOutDTO;
import cn.htd.goodscenter.dto.outdto.QueryFlashbuyItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInfoInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemListStyleOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemMainDataOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuPublishInfoOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderImportItemOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusOrderItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.po.QueryVenusItemListParamDTO;

public interface ItemSkuDAO extends BaseDAO<ItemSku> {

	/**
	 * 
	 * <p>
	 * Discription:[获取SKU编码]
	 * </p>
	 */
	Long getSkuId();

	/**
	 * 
	 * <p>
	 * Discription:[插入SKU图片]
	 * </p>
	 */
	void insertSkuPicture(ItemSkuPicture skuPic);

	/**
	 * 
	 * <p>
	 * Discription:[插入SKU库存]
	 * </p>
	 */
	void insertItemSkuInventory(TradeInventory inv);

	/**
	 * 
	 * <p>
	 * Discription:[删除SKU图片]
	 * </p>
	 */
	void deleteSkuPictures(@Param("skuId") Long skuId);

	/**
	 * 
	 * <p>
	 * Discription:[删除SKU]
	 * </p>
	 */
	void deleteSkuById(@Param("skuId") Long skuId,@Param("modifyId") Long modifyId,@Param("modifyName") String modifyName);

	/**
	 * 
	 * <p>
	 * Discription:[查询SKU库存]
	 * </p>
	 */
	Integer querySkuInventory(Long skuId);

	/**
	 * 
	 * <p>
	 * Discription:[更新SKU库存]
	 * </p>
	 */
	void updateItemSkuInventory(@Param("entity") TradeInventory inv);

	/**
	 * 
	 * <p>
	 * Discription:[查询商品属性]
	 * </p>
	 */
	ItemSku queryItemSkuBySkuId(@Param("skuId") Long skuId);
	
	List<ItemSku> queryByDate(@Param("syncTime") Date syncTime,@Param("page") Pager page);
	
	List<ItemSkuPicture> querySkuPictureBySyncTime(@Param("syncTime") Date syncTime,@Param("page") Pager page);

	List<ItemSkuPicture> selectSkuPictureBySkuId(@Param("skuId") Long skuId);
	
	List<ItemSku> queryByItemId(@Param("itemId") Long itemId);
	
	Long queryItemSkuListCount(QueryVenusItemListParamDTO queryVenusItemListParamDTO);
	
	List<VenusItemListStyleOutDTO> queryItemSkuList(QueryVenusItemListParamDTO queryVenusItemListParamDTO);
	
	List<VenusItemSkuPublishInfoOutDTO> queryVenusItemSkuPublishInfoList(VenusItemSkuPublishInfoInDTO venusItemSkuPublishInfoInDTO);
	
	Long queryVenusItemSkuPublishInfoListCount(VenusItemSkuPublishInfoInDTO venusItemSkuPublishInfoInDTO);
	
	List<VenusItemMainDataOutDTO> queryItemMainDataForVenus(VenusItemMainDataInDTO venusItemSpuInDTO);
	    
    Long queryItemMainDataForVenusCount(VenusItemMainDataInDTO venusItemSpuInDTO);
    
    VenusItemSkuDetailOutDTO queryItemSkuDetail(Long skuId);
    
    VenusOrderItemSkuDetailOutDTO queryVenusOrderItemSkuDetail(Long skuId);
    
    List<CheckPromotionItemSkuOutDTO> querySkuInfoForCheckPromotionActivity(@Param("skuCodeList") List<String> skuCodeList);
    
    QueryFlashbuyItemSkuOutDTO queryFlashbuyItemSkuBySkuCode(@Param("skuCode") String skuCode);
    
    String querySkuCodeSeq();

	/**
	 * 根据外接外接商品sku_id查询。 （唯一）
	 * @param outSkuId
	 * @return
	 */
	ItemSku queryItemSkuByOuterSkuId(String outSkuId);

	int updateDeleteFlagBySkuId(@Param("skuId") Long skuId);



	/**
	 * 根据条件查询sku商品列表
	 * @return
	 */
	List<VenusItemSkuOutDTO> queryItemSkuListByCondition(@Param("entity") VenusItemSkuOutDTO venusItemSkuOutDTO);

	Long queryItemSkuCountByCondition(@Param("entity") VenusItemSkuOutDTO venusItemSkuOutDTO);

	/**
	 * 根据商品编码查询sku信息
	 * @param skuCode
	 * @return
	 */
	ItemSku selectItemSkuBySkuCode(String skuCode);

	/**
	 * 根据商品编码查询sku信息
	 * @param skuCodeList
	 * @return
	 */
	List<ItemSkuDTO> selectItemSkuListBySkuCodeList(@Param("skuCodeList") List<String> skuCodeList);

	List<VenusItemSkuOutDTO> selectByItemIdAndSellerId(@Param("itemId") Long itemId,@Param("sellerId") Long sellerId );

	ItemSkuDTO queryItemSkuDetailBySkuCode(String skuCode);
	
	Long queryItemCountBySaleAreaCode(JudgeRecevieAddressInDTO judgeRecevieAddressInDTO);
	
	List<ItemSku> queryItemSkuBySellerIdAndSpuId(@Param("itemSpuId") Long itemSpuId,@Param("sellerId") Long sellerId );
	
	
	VenusOrderImportItemOutDTO queryVenusOrderImportItemInfo(@Param("itemCode")  String itemCode);
	
	/**
	 * 限时购 - 根据itemCode 查询sku相关信息
	 * @author li.jun
	 * @time 2017-10-26
	 */
	List<VenusItemSkuOutDTO> selectItemSkuByItemId(@Param("itemId") Long itemId);

    List<ItemSkuPicture> queryItemSKUPicsFirst(@Param("itemId") Long itemId);

	/**
	 * 包厢商品列表查询
	 * @param queryVmsItemPublishInfoInDTO
	 * @return
	 */
	Long queryVmsItemSkuPublishInfoListCount(@Param("param") QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO);

	/**
	 * 包厢商品列表查询
	 * @param queryVmsItemPublishInfoInDTO
	 * @param page
	 * @return
	 */
	List<QueryVmsItemPublishInfoOutDTO> queryVmsItemSkuPublishInfoList(@Param("param") QueryVmsItemPublishInfoInDTO queryVmsItemPublishInfoInDTO, @Param("page") Pager<String> page);

    Long queryVmsOffShelfItemSkuPublishInfoListCount(@Param("param") QueryOffShelfItemInDTO queryOffShelfItemInDTO);

	List<QueryOffShelfItemOutDTO> queryVmsOffShelfItemSkuPublishInfoList(@Param("param") QueryOffShelfItemInDTO queryOffShelfItemInDTO, @Param("page")Pager pager);

	List<Map<String, Object>> queryALLOffShelfItemList(@Param("sellerId") Long sellerId, @Param("isBoxFlag")Integer isBoxFlag);

}

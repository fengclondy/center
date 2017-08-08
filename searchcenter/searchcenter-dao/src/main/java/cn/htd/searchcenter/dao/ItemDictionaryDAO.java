package cn.htd.searchcenter.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.BaseAddressDTO;
import cn.htd.searchcenter.domain.HotWordDTO;
import cn.htd.searchcenter.domain.ItemAttrDTO;
import cn.htd.searchcenter.domain.ItemAttrValueDTO;

public interface ItemDictionaryDAO {

	public String queryBelongRelationship(@Param("sellerId") Long sellerId);

	public String querySellerName(@Param("sellerId") Long sellerId);

	public String queryItemStatus(@Param("itemId") Long itemId);

	public String queryBoxRelationship(@Param("sellerId") Long sellerId);

	public String queryItemVisable(@Param("itemId") Long itemId,
			@Param("isBox") int isBox);

	public String queryAreaByCode(@Param("areaCode") String areaCode);

	public String querySellerTypeById(@Param("sellerId") Long sellerId);

	public String queryDescribeByItemId(@Param("itemId") Long itemId);

	public String querySalesVolumeByItemCode(@Param("itemCode") String itemCode);

	public BigDecimal queryExternalItemPrice(@Param("itemId") Long itemId);

	public List<String> querySalesAreaByItemId(@Param("itemId") Long itemId, @Param("isBox") int isBox);

	public Integer queryIsSalesWholeCountry(@Param("itemId") Long itemId, @Param("isBox") int isBox);

	public List<BaseAddressDTO> queryAreaThreeAndSecond();

	public int queryJDItemVisable(@Param("itemId") Long itemId);

	public int querySeckillItemStatus(@Param("itemId") Long itemId, @Param("nowTime") Date nowTime);

	public int queryItemVisableCount(@Param("itemId") Long itemId);

	public String queryItemPicture(@Param("itemId") Long itemId);

	public List<HotWordDTO> queryHotWordByItemCode(@Param("itemCode") String itemCode);

	public String queryCidNameByCid(@Param("cid") Long cid);

	public String queryBrandNameByBrandId(@Param("brandId") Long brandId);

	public List<ItemAttrDTO> queryItemAttrByCid(@Param("cid") String cid);
	
	public List<ItemAttrValueDTO> queryItemAttrValueByCid(@Param("attrId") String attrId, @Param("cid") String cid);

	public List<String> queryItemCategoryCidBySyncTime(@Param("syncTime") Date syncTime);

	public Integer queryIsPublicSaleWholeCountry(@Param("itemId") Long itemId, @Param("isBox") int isBox);

	public String queryOtherItemPicture(@Param("itemId") Long itemId);
}

package cn.htd.searchcenter.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.searchcenter.domain.BaseAddressDTO;
import cn.htd.searchcenter.domain.SyncTime;
public interface ItemDictionaryService {

	public String queryBelongRelationship(Long sellerId) throws Exception;

	public String querySellerName(Long sellerId) throws Exception;

	public boolean queryItemStatus(Long itemId) throws Exception;

	public String queryBoxRelationship(Long sellerId) throws Exception;

	public boolean queryItemVisable(Long itemId, int isBox) throws Exception;

	public String queryAreaByCode(String areaCode) throws Exception;

	public String querySellerTypeById(Long sellerId) throws Exception;

	public String queryDescribeByItemId(Long itemId) throws Exception;
	
	public Long querySalesVolumeByItemCode(String itemCode) throws Exception;

	public List<String> querySalesAreaByItemId(Long itemId, int isBox) throws Exception;

	public boolean queryIsSalesWholeCountry(Long itemId, int isBox) throws Exception;

	public List<BaseAddressDTO> queryAreaThreeAndSecond() throws Exception;

	public boolean queryJDItemVisable(Long itemId) throws Exception;

	public int queryItemVisableCount(Long itemId, int isBox) throws Exception;
	
	public String queryItemPicture(Long itemId) throws Exception;

	public String queryHotWordByItemCode(String itemCode) throws Exception;

	public String queryCidNameByCid(Long cid) throws Exception;

	public String queryBrandNameByBrandId(Long brandId) throws Exception;

	public Map<String, String> queryItemAttrByCid(List<String> cidList) throws Exception;

	public List<String> queryItemCategoryCidBySyncTime(Date syncTime) throws Exception;

	public Integer queryIsPublicSaleWholeCountry(Long itemId, int isBox)throws Exception;

	
}

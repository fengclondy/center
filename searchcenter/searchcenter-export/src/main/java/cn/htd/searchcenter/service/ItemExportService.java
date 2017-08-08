package cn.htd.searchcenter.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.ItemDTO;

public interface ItemExportService {

	/**
	 * 商品搜索，具体到SKU
	 */
	public List<ItemDTO> queryItemInfoBySyncTime(Date lastSyncTime, int start,
			int end);

	public int queryItemInfoBySyncTimCount(Date lastSyncTime);

	/**
	 * 商品搜索，具体到SKU
	 */
	public Long queryItemInfoNotShield(@Param("shopId") Long shopId,
			@Param("brandId") Long brandId, @Param("cid") Long cid);

	public List<ItemDTO> queryItemAttrInfoBySyncTime(Date lastSyncTime,
			Long itemId);
	
	/**
	 * 京东商品
	 */
	public List<ItemDTO> queryJDItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int queryJDItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;
	
	/**
	 * 外部商品
	 */
	public List<ItemDTO> queryExternalItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int queryExternalItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;
	
	/**
	 * 秒杀商品
	 */
	public List<ItemDTO> querySeckillItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int querySeckillItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;
	
	/**
	 * 汇通达内部大厅商品
	 */
	public List<ItemDTO> queryHTDPublicItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int queryHTDPublicItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;

	
	/**
	 * 汇通达内部包厢商品
	 */
	public List<ItemDTO> queryHTDBoxItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int queryHTDBoxItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;

}

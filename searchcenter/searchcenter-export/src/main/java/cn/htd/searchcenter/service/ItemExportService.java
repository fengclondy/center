package cn.htd.searchcenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.searchcenter.domain.ItemDTO;

public interface ItemExportService {

	/**
	 * 外部商品
	 */
	public List<ItemDTO> queryExternalItemInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;
	public int queryExternalItemInfoCountBySyncTime(Date lastSyncTime)throws Exception;
	
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

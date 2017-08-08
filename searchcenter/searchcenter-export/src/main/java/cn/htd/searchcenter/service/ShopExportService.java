package cn.htd.searchcenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.searchcenter.domain.ShopDTO;

public interface ShopExportService {
	
	/**
	 * 商品搜索，具体到SKU
	 */
	public List<ShopDTO> queryShopInfoBySyncTime(Date lastSyncTime, int start, int end)throws Exception;

	public int queryShopInfoCountBySyncTime(Date lastSyncTime)throws Exception;

	/**
	 * 获取地区名称
	 */
	public String getAreaName(String code)throws Exception;

	/**
	 * 获取品类名称
	 */
	public String getCidNames(String cids)throws Exception;

	/**
	 * 获取店铺运营状态
	 * @param shopId
	 * @return
	 */
	public boolean queryShopStatusByShopId(Long shopId)throws Exception;

	public String queryCidNameAndCidByShopId(Long shopId)throws Exception;

	public String queryShopQQByShopId(Long shopId)throws Exception;

}

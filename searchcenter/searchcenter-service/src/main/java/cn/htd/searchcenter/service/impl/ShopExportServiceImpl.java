package cn.htd.searchcenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.ShopInfoDAO;
import cn.htd.searchcenter.domain.ShopDTO;
import cn.htd.searchcenter.service.ShopExportService;


@Service("shopExportService")
public class ShopExportServiceImpl implements ShopExportService{

	@Resource
	private ShopInfoDAO shopInfoDao;
	
	
	/**
	 * 商品搜索，具体到SKU
	 */
	public List<ShopDTO> queryShopInfoBySyncTime(Date syncTime, int start, int end)throws Exception {
		return shopInfoDao.queryShopInfoBySyncTime(syncTime, start, end);
	}
	

	@Override
	public int queryShopInfoCountBySyncTime(Date lastSyncTime)throws Exception {
		return shopInfoDao.queryShopInfoCountBySyncTime(lastSyncTime);
	}


	@Override
	public String getAreaName(String code)throws Exception {
		return shopInfoDao.getAreaName(code);
	}


	@Override
	public String getCidNames(String cids)throws Exception {
		return shopInfoDao.getCidNames(cids);
	}


	@Override
	public boolean queryShopStatusByShopId(Long shopId)throws Exception {
		int shopStatus = shopInfoDao.queryShopStatusByShopId(shopId);
		if(shopStatus == 1){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public String queryCidNameAndCidByShopId(Long shopId) throws Exception {
		return shopInfoDao.queryCidNameAndCidByShopId(shopId);
	}


	@Override
	public String queryShopQQByShopId(Long shopId) throws Exception {
		return shopInfoDao.queryShopQQByShopId(shopId);
	}
}

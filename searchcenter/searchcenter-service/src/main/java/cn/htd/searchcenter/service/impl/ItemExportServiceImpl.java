package cn.htd.searchcenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.ItemInfoDAO;
import cn.htd.searchcenter.datasource.DataSource;
import cn.htd.searchcenter.domain.ItemDTO;
import cn.htd.searchcenter.service.ItemExportService;

@Service("itemExportService")
@DataSource("dataSource_goodsCenter")
public class ItemExportServiceImpl implements ItemExportService {

	@Resource
	private ItemInfoDAO itemInfoDao;

	/**
	 * 外部商品
	 */
	@Override
	public List<ItemDTO> queryExternalItemInfoBySyncTime(Date syncTime,
			int start, int end) throws Exception {
		return itemInfoDao.queryExternalItemInfoBySyncTime(syncTime, start, end);
	}

	@Override
	public int queryExternalItemInfoCountBySyncTime(Date syncTime)
			throws Exception {
		return itemInfoDao.queryExternalItemInfoCountBySyncTime(syncTime);
	}

	/**
	 * 汇通达内部大厅商品
	 */
	@Override
	public List<ItemDTO> queryHTDPublicItemInfoBySyncTime(Date syncTime,
			int start, int end) throws Exception {
		return itemInfoDao.queryHTDPublicItemInfoBySyncTime(syncTime, start, end);
	}

	@Override
	public int queryHTDPublicItemInfoCountBySyncTime(Date syncTime)
			throws Exception {
		return itemInfoDao.queryHTDPublicItemInfoCountBySyncTime(syncTime);
	}

	@Override
	public List<ItemDTO> queryHTDBoxItemInfoBySyncTime(Date syncTime,
			int start, int end) throws Exception {
		return itemInfoDao.queryHTDBoxItemInfoBySyncTime(syncTime, start, end);
	}

	@Override
	public int queryHTDBoxItemInfoCountBySyncTime(Date syncTime)
			throws Exception {
		return itemInfoDao.queryHTDBoxItemInfoCountBySyncTime(syncTime);
	}

}

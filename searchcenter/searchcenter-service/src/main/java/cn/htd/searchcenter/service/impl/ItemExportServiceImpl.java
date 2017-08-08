package cn.htd.searchcenter.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import cn.htd.searchcenter.dao.ItemInfoDAO;
import cn.htd.searchcenter.domain.ItemDTO;
import cn.htd.searchcenter.service.ItemExportService;

@Service("itemExportService")
public class ItemExportServiceImpl implements ItemExportService {

	@Resource
	private ItemInfoDAO itemInfoDao;

	/**
	 * 商品搜索，具体到SKU
	 */
	public List<ItemDTO> queryItemInfoBySyncTime(Date syncTime, int start,
			int end) {
		return itemInfoDao.queryItemInfoBySyncTime(syncTime, start, end,
				new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public int queryItemInfoBySyncTimCount(Date lastSyncTime) {
		return itemInfoDao.queryItemInfoBySyncTimCount(lastSyncTime,
				new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public Long queryItemInfoNotShield(Long shopId, Long brandId, Long cid) {
		return itemInfoDao.queryItemInfoNotShield(shopId, brandId, cid);
	}

	@Override
	public List<ItemDTO> queryItemAttrInfoBySyncTime(Date syncTime, Long itemId) {
		return itemInfoDao.queryItemAttrInfoBySyncTime(syncTime, itemId);
	}

	/**
	 * 京东商品
	 */
	@Override
	public List<ItemDTO> queryJDItemInfoBySyncTime(Date syncTime, int start,
			int end) throws Exception {
		return itemInfoDao.queryJDItemInfoBySyncTime(syncTime, start, end);
	}

	@Override
	public int queryJDItemInfoCountBySyncTime(Date syncTime)
			throws Exception {
		return itemInfoDao.queryJDItemInfoCountBySyncTime(syncTime);
	}

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
	 * 秒杀商品
	 */
	@Override
	public List<ItemDTO> querySeckillItemInfoBySyncTime(Date syncTime,
			int start, int end) throws Exception {
		return itemInfoDao.querySeckillItemInfoBySyncTime(syncTime, start, end);
	}

	@Override
	public int querySeckillItemInfoCountBySyncTime(Date syncTime)
			throws Exception {
		return itemInfoDao.querySeckillItemInfoCountBySyncTime(syncTime);
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

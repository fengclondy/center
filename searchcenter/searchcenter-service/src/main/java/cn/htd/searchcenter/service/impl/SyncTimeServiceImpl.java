package cn.htd.searchcenter.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.SyncTimeDAO;
import cn.htd.searchcenter.datasource.DataSource;
import cn.htd.searchcenter.domain.SyncTime;
import cn.htd.searchcenter.service.SyncTimeService;

@Service("syncTimeService")
@DataSource("dataSource_goodsCenter")
public class SyncTimeServiceImpl implements SyncTimeService{

	@Resource
	private SyncTimeDAO syncTimeDao;
	
	@Override
	public int insert(SyncTime syncTime) {
		return syncTimeDao.insert(syncTime);
	}

	@Override
	public int updateById(SyncTime syncTime) {
		return syncTimeDao.updateById(syncTime);
	}

	@Override
	public SyncTime getSyncTimeByType(int type) {
		return syncTimeDao.getSyncTimeByType(type);
	}

	@Override
	public void updateSyncTime(Date date) {
		syncTimeDao.updateSyncTime(date);
	}

}

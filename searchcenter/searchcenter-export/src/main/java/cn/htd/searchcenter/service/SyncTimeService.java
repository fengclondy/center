package cn.htd.searchcenter.service;

import java.util.Date;

import cn.htd.searchcenter.domain.SyncTime;

public interface SyncTimeService {

	int insert(SyncTime syncTime);
	
	int updateById(SyncTime syncTime);
	
	SyncTime getSyncTimeByType(int type);

	void updateSyncTime(Date date);
}

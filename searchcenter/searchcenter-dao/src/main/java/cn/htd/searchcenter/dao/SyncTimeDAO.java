package cn.htd.searchcenter.dao;

import java.util.Date;

import cn.htd.searchcenter.domain.SyncTime;

public interface SyncTimeDAO {

	int insert(SyncTime syncTime);
	
	int updateById(SyncTime syncTime);
	
	SyncTime getSyncTimeByType(int type);

	void updateSyncTime(Date date);
}

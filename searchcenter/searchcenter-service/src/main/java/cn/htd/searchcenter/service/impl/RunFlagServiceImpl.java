package cn.htd.searchcenter.service.impl;

import java.sql.Timestamp;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.htd.searchcenter.dao.RunFlagDAO;
import cn.htd.searchcenter.service.RunFlagService;

@Service("runFlagService")
public class RunFlagServiceImpl implements RunFlagService{

	@Resource
	private RunFlagDAO runFlagDao;
	
	@Override
	public String queryJobRunFlagByType(int type) {
		String runFlag = runFlagDao.queryJobRunFlagByType(type);
		if(null == runFlag || "".equals(runFlag)){
			return null;
		}
		return runFlag;
	}

	@Override
	public void updateRunFlagById(int type, String runFlag) {
		runFlagDao.updateRunFlagById(type, runFlag, new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public Integer updateAllRunFlag(String runFlag) {
		return runFlagDao.updateAllRunFlag(runFlag, new Timestamp(System.currentTimeMillis()));
	}

	@Override
	public Integer updateAllRunFlagForce(String runFlag) {
		return runFlagDao.updateAllRunFlagForce(runFlag, new Timestamp(System.currentTimeMillis()));
	}

}

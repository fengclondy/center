package cn.htd.searchcenter.service;

public interface RunFlagService {

	String queryJobRunFlagByType(int type);
	
	void updateRunFlagById(int type, String runFlag);

	Integer updateAllRunFlag(String runFlag);

	Integer updateAllRunFlagForce(String runFlag);
}

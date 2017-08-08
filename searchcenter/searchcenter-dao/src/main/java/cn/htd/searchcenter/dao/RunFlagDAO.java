package cn.htd.searchcenter.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface RunFlagDAO {

	public String queryJobRunFlagByType(@Param("type") int type);

	public void updateRunFlagById(@Param("type") int type,
			@Param("runFlag") String runFlag,
			@Param("modifiedTime") Date modifiedTime);

	public Integer updateAllRunFlag(@Param("runFlag") String runFlag,
			@Param("modifiedTime") Date modifiedTime);

	public Integer updateAllRunFlagForce(@Param("runFlag") String runFlag,
									@Param("modifiedTime") Date modifiedTime);

}

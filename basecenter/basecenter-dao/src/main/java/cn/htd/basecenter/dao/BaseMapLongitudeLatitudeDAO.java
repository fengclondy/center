package cn.htd.basecenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.BaseMapLongitudeLatitude;

public interface BaseMapLongitudeLatitudeDAO {
	
	void insertBaseMapLongitudeLatitude(BaseMapLongitudeLatitude baseMapLongitudeLatitude);
	
	BaseMapLongitudeLatitude queryMapLongitudeAndLatitudeByAreaCode(@Param("type") Integer type,@Param("areaCode") String code);
	
}

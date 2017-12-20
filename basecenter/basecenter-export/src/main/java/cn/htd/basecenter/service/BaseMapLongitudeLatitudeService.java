package cn.htd.basecenter.service;

import cn.htd.basecenter.domain.BaseMapLongitudeLatitude;
import cn.htd.common.ExecuteResult;

public interface BaseMapLongitudeLatitudeService {
	ExecuteResult<BaseMapLongitudeLatitude> queryMapLongitudeAndLatitudeByAreaCode(Integer type,String cityCode);
	
	void addMapLongitudeAndLatitude(BaseMapLongitudeLatitude baseMapLongitudeLatitude);
}

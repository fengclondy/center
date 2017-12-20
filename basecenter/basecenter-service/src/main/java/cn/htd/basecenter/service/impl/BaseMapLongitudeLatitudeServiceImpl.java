package cn.htd.basecenter.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dao.BaseMapLongitudeLatitudeDAO;
import cn.htd.basecenter.domain.BaseMapLongitudeLatitude;
import cn.htd.basecenter.service.BaseMapLongitudeLatitudeService;
import cn.htd.common.ExecuteResult;

@Service("baseMapLongitudeLatitudeService")
public class BaseMapLongitudeLatitudeServiceImpl implements BaseMapLongitudeLatitudeService{
	
	@Resource
	private BaseMapLongitudeLatitudeDAO baseMapLongitudeLatitudeDAO;

	@Override
	public ExecuteResult<BaseMapLongitudeLatitude>  queryMapLongitudeAndLatitudeByAreaCode(Integer type, String cityCode) {
		ExecuteResult<BaseMapLongitudeLatitude> result=new ExecuteResult<BaseMapLongitudeLatitude>();
		if(StringUtils.isEmpty(cityCode)||type==null){
			result.setCode("0");
			result.setResultMessage("参数不正确");
		}
		BaseMapLongitudeLatitude baseMapLongitudeLatitude=baseMapLongitudeLatitudeDAO.queryMapLongitudeAndLatitudeByAreaCode(type, cityCode);
		result.setCode("1");
		result.setResult(baseMapLongitudeLatitude);
		return result;
	}

	@Override
	public void addMapLongitudeAndLatitude(BaseMapLongitudeLatitude baseMapLongitudeLatitude) {
		if(baseMapLongitudeLatitude==null){
			return;
		}
		baseMapLongitudeLatitudeDAO.insertBaseMapLongitudeLatitude(baseMapLongitudeLatitude);
	}

}

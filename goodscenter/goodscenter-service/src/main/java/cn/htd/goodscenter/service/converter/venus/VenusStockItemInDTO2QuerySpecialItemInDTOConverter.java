package cn.htd.goodscenter.service.converter.venus;

import cn.htd.goodscenter.dto.middleware.indto.QuerySpecialItemInDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusStockItemInDTO;
import cn.htd.goodscenter.service.converter.Converter;

public class VenusStockItemInDTO2QuerySpecialItemInDTOConverter implements Converter<VenusStockItemInDTO, QuerySpecialItemInDTO>{

	@Override
	public QuerySpecialItemInDTO convert(VenusStockItemInDTO source) {
		QuerySpecialItemInDTO querySpecialItemInDTO=new QuerySpecialItemInDTO();
		return querySpecialItemInDTO;
	}

}

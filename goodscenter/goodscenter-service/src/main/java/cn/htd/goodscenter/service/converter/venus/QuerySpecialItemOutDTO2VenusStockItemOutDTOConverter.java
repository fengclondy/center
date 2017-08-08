package cn.htd.goodscenter.service.converter.venus;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.dto.middleware.outdto.QuerySpecialItemOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusStockItemOutDTO;
import cn.htd.goodscenter.service.converter.Converter;

public class QuerySpecialItemOutDTO2VenusStockItemOutDTOConverter implements Converter<QuerySpecialItemOutDTO, VenusStockItemOutDTO>{

	@Override
	public VenusStockItemOutDTO convert(QuerySpecialItemOutDTO source) {
		VenusStockItemOutDTO venusStockItemOutDTO=new VenusStockItemOutDTO();
		if(StringUtils.isNotEmpty(source.getSellNum())){
			venusStockItemOutDTO.setAvaliableQty(Long.valueOf(source.getSellNum()));
		}
		venusStockItemOutDTO.setBrandName(source.getBrandName());
		venusStockItemOutDTO.setCategoryName(source.getCategroyName());
		venusStockItemOutDTO.setErpCatName(source.getErpCategreyCode());
		venusStockItemOutDTO.setErpCode(source.getErpProductCode());
		venusStockItemOutDTO.setItemCode(source.getProductCode());
		venusStockItemOutDTO.setItemName(source.getProductName());
		venusStockItemOutDTO.setProdAttr(source.getProductAttribute());
		venusStockItemOutDTO.setSalesDept(source.getDepartmentName());
		venusStockItemOutDTO.setSupplierName(source.getSupplierName());
		venusStockItemOutDTO.setWarehouseName(source.getWarehouseName());
		return venusStockItemOutDTO;
	}

}

package cn.htd.goodscenter.service.converter.venus;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.dto.middleware.outdto.QuerySpecialItemOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusAgreementItemOutDTO;
import cn.htd.goodscenter.service.converter.Converter;

public class QuerySpecialItemOutDTO2VenusAgreementItemOutDTOConverter implements Converter<QuerySpecialItemOutDTO, VenusAgreementItemOutDTO>{

	@Override
	public VenusAgreementItemOutDTO convert(QuerySpecialItemOutDTO source) {
		VenusAgreementItemOutDTO venusAgreementItemOutDTO=new VenusAgreementItemOutDTO();
		venusAgreementItemOutDTO.setAgreementCode(source.getProtocolNum());
		venusAgreementItemOutDTO.setCategoryName(source.getCategroyName());
		venusAgreementItemOutDTO.setEndDate(source.getEndTime());
		venusAgreementItemOutDTO.setErpCatName(source.getCategroyName());
		venusAgreementItemOutDTO.setErpCode(source.getErpCategreyCode());
		venusAgreementItemOutDTO.setItemCode(source.getProductCode());
		venusAgreementItemOutDTO.setItemName(source.getProductName());
		venusAgreementItemOutDTO.setProdAttr(source.getProductAttribute());
		if(StringUtils.isNotEmpty(source.getSellNum())){
			venusAgreementItemOutDTO.setPurchaseQty(Long.valueOf(source.getSellNum()));
		}
		if(StringUtils.isNotEmpty(source.getSurplusNum())){
			venusAgreementItemOutDTO.setRemainingQty(Long.valueOf(source.getSurplusNum()));
		}
		
		venusAgreementItemOutDTO.setSalesDept(source.getDepartmentName());
		venusAgreementItemOutDTO.setStartDate(source.getStartTime());
		if(StringUtils.isNotEmpty(source.getAllocationNum())){
			venusAgreementItemOutDTO.setStockQty(Long.valueOf(source.getAllocationNum()));
		}
		venusAgreementItemOutDTO.setSupplierName(source.getSupplierName());
		venusAgreementItemOutDTO.setWarehouseName(source.getWarehouseName());
		return venusAgreementItemOutDTO;
	}

}

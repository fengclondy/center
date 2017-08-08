package cn.htd.goodscenter.service.converter.venus;

import java.util.Date;

import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.service.converter.Converter;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

public class VenusItemInDTO2ItemSkuConverter implements Converter<VenusItemInDTO, ItemSku>{

	@Override
	public ItemSku convert(VenusItemInDTO source) {
		ItemSku itemSku=new ItemSku();
		if(source.getItemId()!=null &&source.getItemId()>0L){
			itemSku.setItemId(source.getItemId());
		}
		itemSku.setSkuCode(ItemCodeGenerator.generateSkuCode());
		itemSku.setSellerId(source.getHtdVendorId());
		itemSku.setSkuStatus(1);
		itemSku.setSkuType(1);
		itemSku.setEanCode(source.getEanCode());
		itemSku.setAttributes(source.getColor());
		itemSku.setCreateId(source.getOperatorId());
		itemSku.setCreated(new Date());
		itemSku.setCreateName(source.getOperatorName());
		itemSku.setModifyId(source.getOperatorId());
		itemSku.setModified(new Date());
		itemSku.setModifyName(source.getOperatorName());
		return itemSku;
	}

}

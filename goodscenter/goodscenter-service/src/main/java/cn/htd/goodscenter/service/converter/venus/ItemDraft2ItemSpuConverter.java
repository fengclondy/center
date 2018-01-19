package cn.htd.goodscenter.service.converter.venus;

import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.service.converter.Converter;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class ItemDraft2ItemSpuConverter implements Converter<ItemDraft, ItemSpu>{

	@Override
	public ItemSpu convert(ItemDraft source) {
		ItemSpu itemSpu=new ItemSpu();
		itemSpu.setBrandId(source.getBrand());
		itemSpu.setCategoryAttributes(source.getAttributes());
		itemSpu.setCategoryId(source.getCid());
		itemSpu.setCreateId(source.getCreateId());
		itemSpu.setCreateName(source.getCreateName());
		itemSpu.setCreateTime(new Date());
		itemSpu.setGrossWeight(source.getWeight());
		if(source.getHeight()!=null){
			itemSpu.setHigh(source.getHeight().intValue());
		}
		if(source.getLength()!=null){
			itemSpu.setLength(source.getLength().intValue());
		}
		if(source.getTaxRate()!=null){
			itemSpu.setTaxRate(source.getTaxRate());
		}
		itemSpu.setModelType(source.getModelType());
		itemSpu.setModifyId(source.getCreateId());
		itemSpu.setModifyName(source.getCreateName());
		itemSpu.setModifyTime(new Date());
		itemSpu.setNetWeight(source.getNetWeight());
		itemSpu.setOrigin(source.getOrigin());
		itemSpu.setSpuCode(ItemCodeGenerator.generateSpuCode());
		itemSpu.setSpuName(source.getItemName());
		itemSpu.setStatus("0");
		if(StringUtils.isNotEmpty(source.getWeightUnit())){
			itemSpu.setUnit(source.getWeightUnit());
		}
		if(source.getWidth()!=null){
			itemSpu.setWide(source.getWidth().intValue());
		}
		return itemSpu;
	}

}

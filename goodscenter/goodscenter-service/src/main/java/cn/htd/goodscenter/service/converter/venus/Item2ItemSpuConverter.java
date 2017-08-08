package cn.htd.goodscenter.service.converter.venus;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.service.converter.Converter;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

public class Item2ItemSpuConverter implements Converter<Item, ItemSpu>{

	@Override
	public ItemSpu convert(Item source) {
		ItemSpu itemSpu=new ItemSpu();
		itemSpu.setAfterService(source.getAfterService());
		itemSpu.setBrandId(source.getBrand());
		itemSpu.setCategoryAttributes(source.getAttributes());
		itemSpu.setCategoryId(source.getCid());
		itemSpu.setCreateId(source.getCreateId());
		itemSpu.setCreateName(source.getCreateName());
		itemSpu.setCreateTime(new Date());
		itemSpu.setErpFirstCategoryCode(source.getErpFirstCategoryCode());
		itemSpu.setErpFiveCategoryCode(source.getErpFiveCategoryCode());
		itemSpu.setGrossWeight(source.getWeight());
		if(source.getHeight()!=null){
			itemSpu.setHigh(source.getHeight().intValue());
		}
		itemSpu.setItemQualification(source.getItemQualification());
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
		itemSpu.setPackingList(source.getPackingList());
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

package cn.htd.goodscenter.service.converter.venus;

import java.util.Date;

import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.service.converter.Converter;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

public class Item2ItemSkuConverter implements Converter<Item, ItemSku>{

	@Override
	public ItemSku convert(Item source) {
		ItemSku itemSku=new ItemSku();
		itemSku.setItemId(source.getItemId());
		itemSku.setSkuCode(ItemCodeGenerator.generateSkuCode());
		itemSku.setSellerId(source.getSellerId());
		itemSku.setSkuStatus(1);
		itemSku.setSkuType(1);
		itemSku.setAttributes(source.getAttrSale());
		itemSku.setCreateId(source.getCreateId());
		itemSku.setCreated(new Date());
		itemSku.setCreateName(source.getCreateName());
		itemSku.setModifyId(source.getCreateId());
		itemSku.setModified(new Date());
		itemSku.setModifyName(source.getCreateName());
		return itemSku;
	}

}

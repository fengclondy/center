package cn.htd.goodscenter.service.converter.venus;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.service.converter.Converter;

public class VenusItemInDTO2ItemDTOConverter implements Converter<VenusItemInDTO, ItemDTO>{

	@Override
	public ItemDTO convert(VenusItemInDTO source) {
		ItemDTO itemDTO=new ItemDTO();
		itemDTO.setItemId(source.getItemId());
		itemDTO.setItemCode(source.getProductCode());
		itemDTO.setItemStatus(source.getProductStatus());
		itemDTO.setCid(source.getThirdLevelCategoryId());
		itemDTO.setBrand(source.getBrandId());
		itemDTO.setModelType(source.getSerial());
		itemDTO.setItemName(source.getProductName());
		itemDTO.setWeightUnit(source.getUnit());
		if(StringUtils.isNotEmpty(source.getTaxRate())){
			itemDTO.setTaxRate(new BigDecimal(source.getTaxRate()));
		}
		if(StringUtils.isNotEmpty(source.getNetWeight())){
			itemDTO.setNetWeight(new BigDecimal(source.getNetWeight()));
		}
		
		itemDTO.setLength(source.getLength());
		itemDTO.setWidth(source.getWidth());
		itemDTO.setHeight(source.getHeight());
		itemDTO.setAttrSaleStr(source.getColor());
		itemDTO.setAd(source.getAd());
		itemDTO.setOrigin(source.getOriginPlace());
		itemDTO.setEanCode(source.getEanCode());
		
		
		if(CollectionUtils.isNotEmpty(source.getPictures())){
			String[] picUrls=new String[source.getPictures().size()];
			int i=0;
			for(ItemPicture itemPicture:source.getPictures()){
				picUrls[i]=itemPicture.getPictureUrl();
			}
			itemDTO.setPicUrls(picUrls);
		}
		
		itemDTO.setAttributesStr(source.getCategoryAttribute());
		if(source.getDescribe()!=null){
			itemDTO.setDescribeUrl(source.getDescribe().getDescribeContent());
		}
		if(source.getOperatorId()!=null){
			itemDTO.setOperator(source.getOperatorId().intValue());
			itemDTO.setModified(new Date());
			itemDTO.setModifyId(source.getOperatorId());
		}
		if(StringUtils.isNotEmpty(source.getOperatorName())){
			itemDTO.setModifyName(source.getOperatorName());
		}
		
		itemDTO.setSellerId(source.getHtdVendorId());	
		
		itemDTO.setShopId(source.getShopId());
		return itemDTO;
	}

}

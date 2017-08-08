package cn.htd.goodscenter.service.converter.venus;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.common.channel.ItemChannelConstant;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.goodscenter.dto.venus.indto.VenusItemInDTO;
import cn.htd.goodscenter.service.converter.Converter;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;



public class VenusItemInDTO2ItemConverter implements Converter<VenusItemInDTO, Item>{

	@Override
	public Item convert(VenusItemInDTO source) {
		if(source ==null){
			return null;
		}
		
		Item target=new Item();
		
		//如果商品主键不为空，则设置上去
		if(source.getItemId()!=null){
			target.setItemId(source.getItemId());
		}
		
		if(StringUtils.isNotEmpty(source.getProductCode())){
			target.setItemCode(source.getProductCode());
		}else{
			target.setItemCode(ItemCodeGenerator.generateItemCode());
		}
		
		target.setItemName(source.getProductName());
		if(source.getProductStatus()!=null){
			target.setItemStatus(HtdItemStatusEnum.AUDITING.getCode());
		}
		target.setSellerId(source.getHtdVendorId());
		target.setShopId(source.getShopId());
		target.setCid(source.getThirdLevelCategoryId());
		target.setBrand(source.getBrandId());
		target.setModelType(source.getSerial());
		target.setWeightUnit(source.getUnit());
		target.setTaxRate(new BigDecimal(source.getTaxRate()));
		if(StringUtils.isNotEmpty(source.getGrossWeight())){
			target.setWeight(new BigDecimal(source.getGrossWeight()));
		}
		if(StringUtils.isNotEmpty(source.getNetWeight())){
			target.setNetWeight(new BigDecimal(source.getNetWeight()));
		}
		if(StringUtils.isNotEmpty(source.getLength())){
			target.setLength(new BigDecimal(source.getLength()));
		}
		
		if(StringUtils.isNotEmpty(source.getWidth())){
			target.setWidth(new BigDecimal(source.getWidth()));
		}
		
		if(StringUtils.isNotEmpty(source.getHeight())){
			target.setHeight(new BigDecimal(source.getHeight()));
		}
		
		if(StringUtils.isNotEmpty(source.getAd())){
			target.setAd(source.getAd());
		}
		
		if(StringUtils.isNotEmpty(source.getOriginPlace())){
			target.setOrigin(source.getOriginPlace());
		}
		
		if(StringUtils.isNotEmpty(source.getCategoryAttribute())){
			target.setAttributes(source.getCategoryAttribute());
		}
		
		if(StringUtils.isNotEmpty(source.getColor())){
			target.setAttrSale(source.getColor());
		}
		
		if(source.getShopCid()!=null){
			target.setShopCid(source.getShopCid());
		}
		
		target.setProductChannelCode(ItemChannelConstant.ITME_CHANNEL_OF_INTERNAL);
		
		target.setErpStatus(ItemErpStatusEnum.WAITING.getCode());
		
		target.setCreated(new Date());
		target.setCreateId(source.getOperatorId());
		target.setCreateName(source.getOperatorName());
		
		target.setModified(new Date());
		target.setModifyId(source.getOperatorId());
		target.setModifyName(source.getOperatorName());
		target.setIsSpu(1);
		
		return target;
	}

	
}

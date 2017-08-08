package cn.htd.goodscenter.service.converter.venus;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import cn.htd.goodscenter.service.converter.Converter;

public class VenusItemSkuPublishInDTO2ItemSkuPublishInfoConverter implements Converter<VenusItemSkuPublishInDTO, ItemSkuPublishInfo>{

	@Override
	public ItemSkuPublishInfo convert(VenusItemSkuPublishInDTO source) {
		ItemSkuPublishInfo itemSkuPublishInfo=new ItemSkuPublishInfo();
		itemSkuPublishInfo.setSkuId(source.getSkuId());
		if(source.getItemId()!=null){
			itemSkuPublishInfo.setItemId(source.getItemId());
		}
		itemSkuPublishInfo.setSkuCode(source.getSkuCode());
		itemSkuPublishInfo.setIsBoxFlag("1".equals(source.getShelfType())?1:0);
		itemSkuPublishInfo.setNote(source.getNote());
		if(StringUtils.isNumeric(source.getDisplayQty())){
			itemSkuPublishInfo.setDisplayQuantity(Integer.parseInt(source.getDisplayQty()));
		}
		if(StringUtils.isNumeric(source.getMinBuyQty())){
			itemSkuPublishInfo.setMimQuantity(Integer.parseInt(source.getMinBuyQty()));
		}
		if("1".equals(source.getIsPurchaseLimit())){
			itemSkuPublishInfo.setIsPurchaseLimit(1);
			if(StringUtils.isNumeric(source.getMaxPurchaseQty())){
				itemSkuPublishInfo.setMaxPurchaseQuantity(Integer.parseInt(source.getMaxPurchaseQty()));
			}
		}else{
			itemSkuPublishInfo.setIsPurchaseLimit(0);
			itemSkuPublishInfo.setMaxPurchaseQuantity(0);
		}
		//上架
		if("1".equals(source.getIsVisible())){
			itemSkuPublishInfo.setIsVisable(1);
			itemSkuPublishInfo.setVisableTime(new Date());
		}else{
			//下架
			itemSkuPublishInfo.setIsVisable(0);
			itemSkuPublishInfo.setInvisableTime(new Date());
		}
		
		if("1".equals(source.getIsAutomaticVisible())){
			itemSkuPublishInfo.setIsAutomaticVisable(1);
			if("1".equals(source.getIsAutomaticVisibleByStock())){
				itemSkuPublishInfo.setIsAutomaticVisable(1);
			}
			if(source.getAutomaticVisibleUpTime() != null){
				itemSkuPublishInfo.setAutomaticStarttime(source.getAutomaticVisibleUpTime());
			}
			if(source.getAutomaticVisibleDownTime() != null){
				itemSkuPublishInfo.setAutomaticEndtime(source.getAutomaticVisibleDownTime());
			}
		}
		
		itemSkuPublishInfo.setShippingCost(StringUtils.isEmpty(source.getShippingCost())?0.0d:Double.valueOf(source.getShippingCost()));
		
		if(StringUtils.isNumeric(source.getErpSync())){
			itemSkuPublishInfo.setErpSync(Integer.parseInt(source.getErpSync()));
		}
		itemSkuPublishInfo.setCreateId(source.getOperatorId());
		itemSkuPublishInfo.setCreateName(source.getOperatorName());
		itemSkuPublishInfo.setCreateTime(new Date());
		itemSkuPublishInfo.setModifyId(source.getOperatorId());
		itemSkuPublishInfo.setModifyName(source.getOperatorName());
		itemSkuPublishInfo.setModifyTime(new Date());
		
		return itemSkuPublishInfo;
	}

}

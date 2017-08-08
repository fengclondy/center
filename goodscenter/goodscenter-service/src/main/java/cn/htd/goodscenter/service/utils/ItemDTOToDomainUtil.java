package cn.htd.goodscenter.service.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;

/**
 * <p>
 * Description: [ItemDTO转换domain类]
 * </p>
 */
public class ItemDTOToDomainUtil {

	/**
	 * <p>
	 * Discription:[ItemDTO转换ItemPrice]
	 * </p>
	 */
//	public static ItemPrice itemDTO2SellPrice(ItemDTO itemDTO, SellPriceDTO sellPrice) {
//		ItemPrice itemPrice = new ItemPrice();
//		if (null != sellPrice.getSellPrice()) {
//			itemPrice.setSellPrice(sellPrice.getSellPrice());
//		}
//		if (null != sellPrice.getMinNum()) {
//			itemPrice.setMinNum(sellPrice.getMinNum());
//		}
//		if (null != sellPrice.getMaxNum()) {
//			itemPrice.setMaxNum(sellPrice.getMaxNum());
//		}
//		if (null != sellPrice.getAreaId()) {
//			itemPrice.setAreaId(sellPrice.getAreaId());
//		}
//		if (null != sellPrice.getAreaName()) {
//			itemPrice.setAreaName(sellPrice.getAreaName());
//		}
//		if (null != sellPrice.getStepIndex()) {
//			itemPrice.setStepIndex(sellPrice.getStepIndex());
//		}
//		if (null != itemDTO.getSellerId()) {
//			itemPrice.setSellerId(itemDTO.getSellerId());
//		}
//		if (null != itemDTO.getCreated()) {
//			itemPrice.setCreated(itemDTO.getCreated());
//		}
//		if (null != itemDTO.getShopId()) {
//			itemPrice.setShopId(itemDTO.getShopId());
//		}
//		return itemPrice;
//	}

	/**
	 * <p>
	 * Discription:[ItemDTO转换Item]
	 * </p>
	 */
	public static Item itemDTO2Item(ItemDTO itemDTO) {
		Item item = new Item();
		if (null != itemDTO.getItemName()) {
			item.setItemName(itemDTO.getItemName());
		}
		if (null != itemDTO.getItemCode()) {
			item.setItemCode(itemDTO.getItemCode());
		}
		if (null != itemDTO.getCid()) {
			item.setCid(itemDTO.getCid());
		}
		if (null != itemDTO.getSellerId()) {
			item.setSellerId(itemDTO.getSellerId());
		}
		if (null != itemDTO.getItemStatus()) {
			item.setItemStatus(itemDTO.getItemStatus());
		}
		if (null != itemDTO.getAttributesStr()) {
			item.setAttributes(itemDTO.getAttributesStr());
		}
		if (null != itemDTO.getAttrSaleStr()) {
			item.setAttrSale(itemDTO.getAttrSaleStr());
		}
		if (null != itemDTO.getCreated()) {
			item.setCreated(itemDTO.getCreated());
		}
		if (null != itemDTO.getModified()) {
			item.setModified(itemDTO.getModified());
		}
		if (null != itemDTO.getShopId()) {
			item.setShopId(itemDTO.getShopId());
		}
		if (null != itemDTO.getBrand()) {
			item.setBrand(itemDTO.getBrand());
		}
		if (null != itemDTO.getMarketPrice()) {
			item.setMarketPrice(itemDTO.getMarketPrice());
		}
		if (null != itemDTO.getMarketPrice2()) {
			item.setMarketPrice2(itemDTO.getMarketPrice2());
		}
		
		if (null != itemDTO.getWeight()) {
			item.setWeight(itemDTO.getWeight());
		}

		if(itemDTO.getErpStatus() != null){
			item.setErpStatus(itemDTO.getErpStatus());
		}

		if (null != itemDTO.getPackingList()) {
			item.setPackingList(itemDTO.getPackingList());
		}
		if (null != itemDTO.getAfterService()) {
			item.setAfterService(itemDTO.getAfterService());
		}
		if (null != itemDTO.getAd()) {
			item.setAd(itemDTO.getAd());
		}
		if (null != itemDTO.getTimingListing()) {
			item.setTimingListing(itemDTO.getTimingListing());
		}
		if (null != itemDTO.getListtingTime()) {
			item.setListtingTime(itemDTO.getListtingTime());
		}
		if (null != itemDTO.getDelistingTime()) {
			item.setDelistingTime(itemDTO.getDelistingTime());
		}
		
		if (null != itemDTO.getStatusChangeReason()) {
			item.setStatusChangeReason(itemDTO.getStatusChangeReason());
		}
		if (null != itemDTO.getShopCid()) {
			item.setShopCid(itemDTO.getShopCid());
		}
		if (null != itemDTO.getGuidePrice()) {
			item.setGuidePrice(itemDTO.getGuidePrice());
		}
		if (null != itemDTO.getOrigin()) {
			item.setOrigin(itemDTO.getOrigin());
		}

		if (null != itemDTO.getHasPrice()) {
			item.setHasPrice(itemDTO.getHasPrice());
		}
	
		if (null != itemDTO.getKeywords()) {
			item.setKeywords(itemDTO.getKeywords());
		}
		if (null != itemDTO.getWeightUnit()) {
			item.setWeightUnit(itemDTO.getWeightUnit());
		}
		if (null != itemDTO.getShopFreightTemplateId()) {
			item.setShopFreightTemplateId(itemDTO.getShopFreightTemplateId());
		}
		if (null != itemDTO.getLength()) {
			item.setLength(new BigDecimal(itemDTO.getLength()));
		}
		if(null !=itemDTO.getWidth()){
			item.setWidth(new BigDecimal(itemDTO.getWidth()));
		}
		if(null !=itemDTO.getHeight()){
			item.setHeight(new BigDecimal(itemDTO.getHeight()));
		}
		if (null != itemDTO.getPayType()) {
			item.setPayType(itemDTO.getPayType());
		}
		if(null != itemDTO.getItemQualification()){
			item.setItemQualification(itemDTO.getItemQualification());
		}
		if(null != itemDTO.getModelType()){
			item.setModelType(itemDTO.getModelType());
		}
		if(null != itemDTO.getTaxRate()){
			item.setTaxRate(itemDTO.getTaxRate());
		}
		if(null != itemDTO.getNetWeight()){
			item.setNetWeight(itemDTO.getNetWeight());
		}
		if(null != itemDTO.getItemPictureUrl()){
			item.setItemPictureUrl(itemDTO.getItemPictureUrl());
		}
		if(null != itemDTO.getProductChannelCode()){
			item.setProductChannelCode(itemDTO.getProductChannelCode());
		}
		if(StringUtils.isNotEmpty(itemDTO.getErpFirstCategoryCode())){
			item.setErpFirstCategoryCode(itemDTO.getErpFirstCategoryCode());
		}
		if(StringUtils.isNotEmpty(itemDTO.getErpFiveCategoryCode())){
			item.setErpFiveCategoryCode(itemDTO.getErpFiveCategoryCode());
		}
		return item;
	}


	/**
	 * <p>
	 * Discription:[ItemDTO转换Item]
	 * </p>
	 */
	public static ItemSkuPublishInfo ItemPuclishDTO2Domain(VenusItemSkuPublishInDTO dto){
		ItemSkuPublishInfo itemSkuPublishInfo = new ItemSkuPublishInfo();

		if(null != dto.getSkuId()){
			itemSkuPublishInfo.setSkuId(dto.getSkuId());
		}
		if(null != dto.getSkuCode() && !"".equals(dto.getSkuCode())){
			itemSkuPublishInfo.setSkuCode(dto.getSkuCode());
		}
		if(null != dto.getItemId()){
			itemSkuPublishInfo.setItemId(dto.getItemId());
		}
		if(null != dto.getIsBoxFlag()){
			itemSkuPublishInfo.setIsBoxFlag(dto.getIsBoxFlag());
		}
		if(null != dto.getNote()){
			itemSkuPublishInfo.setNote(dto.getNote());
		}
		if(null != dto.getDisplayQty()){
			itemSkuPublishInfo.setDisplayQuantity(new Integer(dto.getDisplayQty()));
		}
		if(null != dto.getReserveQty()){
			itemSkuPublishInfo.setReserveQuantity(new Integer(dto.getReserveQty()));
		}
		if(null != dto.getMinBuyQty()){
			itemSkuPublishInfo.setMimQuantity(new Integer(dto.getMinBuyQty()));
		}
		if(null != dto.getIsPurchaseLimit()){
			itemSkuPublishInfo.setIsPurchaseLimit(new Integer(dto.getIsPurchaseLimit()));
		}
		if(null != dto.getMaxPurchaseQty()){
			itemSkuPublishInfo.setMaxPurchaseQuantity(new Integer(dto.getMaxPurchaseQty()));
		}
		if(null != dto.getIsVisible()){
			itemSkuPublishInfo.setIsVisable(new Integer(dto.getIsVisible()));
		}
		if(null != dto.getVisableTime()){
			itemSkuPublishInfo.setVisableTime(dto.getVisableTime());
		}
		if(null != dto.getInVisableTime()){
			itemSkuPublishInfo.setInvisableTime(dto.getInVisableTime());
		}
		if(null != dto.getIsAutomaticVisible()){
			itemSkuPublishInfo.setIsAutomaticVisable(new Integer(dto.getIsAutomaticVisible()));
		}
		if(null != dto.getIsAutomaticVisibleByStock()){
			itemSkuPublishInfo.setAutomaticVisableWithStock(new Integer(dto.getIsAutomaticVisibleByStock()));
		}
		if(null != dto.getAutomaticVisibleUpTime()){
			itemSkuPublishInfo.setAutomaticStarttime(dto.getAutomaticVisibleUpTime());
		}
		if(null != dto.getAutomaticVisibleDownTime()){
			itemSkuPublishInfo.setAutomaticEndtime(dto.getAutomaticVisibleDownTime());
		}
		if(null !=dto.getShippingCost()){
			itemSkuPublishInfo.setShippingCost(new Double(dto.getShippingCost()));
		}
		if(null != dto.getErpSync()){
			itemSkuPublishInfo.setErpSync(new Integer(dto.getErpSync()));
		}
		if(null != dto.getCreateId()){
			itemSkuPublishInfo.setCreateId(dto.getCreateId());
		}
		if(null != dto.getCreateName()){
			itemSkuPublishInfo.setCreateName(dto.getCreateName());
		}
		if(null != dto.getModifyId()){
			itemSkuPublishInfo.setModifyId(dto.getModifyId());
		}
		if(null != dto.getModifyName()){
			itemSkuPublishInfo.setModifyName(dto.getModifyName());
		}
		return itemSkuPublishInfo;
	}
}

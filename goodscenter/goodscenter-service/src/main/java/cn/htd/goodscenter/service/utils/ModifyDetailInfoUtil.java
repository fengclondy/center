package cn.htd.goodscenter.service.utils;

import java.util.Date;

import cn.htd.common.util.SpringApplicationContextHolder;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ModifyDetailInfoMapper;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ModifyDetailInfo;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.enums.ShopModifyColumn;

/**
 * 
 * @author zhangxiaolong
 *
 */
public class ModifyDetailInfoUtil {

	public static void saveChangedRecord(ItemDTO itemDTO, ItemDTO dbItem) {
		ModifyDetailInfoMapper modifyDetailInfoMapper = SpringApplicationContextHolder
				.getBean(ModifyDetailInfoMapper.class);

		if (modifyDetailInfoMapper == null) {
			return;
		}
		ItemDescribeDAO itemDescribeDAO = SpringApplicationContextHolder
				.getBean(ItemDescribeDAO.class);

		ModifyDetailInfo mdInfo = new ModifyDetailInfo();
		// 品牌编码
		if (validaIsNotSameBoforeAndAfter(itemDTO.getBrand(), dbItem.getBrand())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.brand.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getBrand().toString());
			mdInfo.setAfterChange(itemDTO.getBrand().toString());
			mdInfo.setModifyFieldId(ShopModifyColumn.brand.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 平台三级类目
		if (validaIsNotSameBoforeAndAfter(itemDTO.getCid(), dbItem.getCid())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.cid.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getCid().toString());
			mdInfo.setAfterChange(itemDTO.getCid().toString());
			mdInfo.setModifyFieldId(ShopModifyColumn.cid.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 店铺二级类目
		if (validaIsNotSameBoforeAndAfter(itemDTO.getShopCid(),
				dbItem.getShopCid())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.shop_cid.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getShopCid().toString());
			mdInfo.setAfterChange(itemDTO.getShopCid()==null?"0":itemDTO.getShopCid().toString());
			mdInfo.setModifyFieldId(ShopModifyColumn.shop_cid.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 是否为新增商品
		if (validaIsNotSameBoforeAndAfter(itemDTO.isSpu(), dbItem.isSpu())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.is_spu.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.isSpu() + "");
			mdInfo.setAfterChange(itemDTO.isSpu() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.is_spu.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 广告语
		if (validaIsNotSameBoforeAndAfter(itemDTO.getAd(), dbItem.getAd())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.ad.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getAd() + "");
			mdInfo.setAfterChange(itemDTO.getAd() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.ad.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品毛重
		if (validaIsNotSameBoforeAndAfter(itemDTO.getWeight(),
				dbItem.getWeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.weight.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getWeight() + "");
			mdInfo.setAfterChange(itemDTO.getWeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.weight.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品净重
		if (validaIsNotSameBoforeAndAfter(itemDTO.getNetWeight(),
				dbItem.getNetWeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.net_weight.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getNetWeight() + "");
			mdInfo.setAfterChange(itemDTO.getNetWeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.net_weight.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品高
		if (validaIsNotSameBoforeAndAfter(itemDTO.getHeight(),
				dbItem.getHeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.height.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getHeight() + "");
			mdInfo.setAfterChange(itemDTO.getHeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.height.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品型号
		if (validaIsNotSameBoforeAndAfter(itemDTO.getModelType(),
				dbItem.getModelType())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.model_type.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getModelType() + "");
			mdInfo.setAfterChange(itemDTO.getModelType() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.model_type.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品税率
		if (validaIsNotSameBoforeAndAfter(itemDTO.getTaxRate(),
				dbItem.getTaxRate())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.tax_rate.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getTaxRate() + "");
			mdInfo.setAfterChange(itemDTO.getTaxRate() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.tax_rate.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品税率
		if (validaIsNotSameBoforeAndAfter(itemDTO.getTaxRate(),
				dbItem.getTaxRate())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.tax_rate.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getTaxRate() + "");
			mdInfo.setAfterChange(itemDTO.getTaxRate() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.tax_rate.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品产地
		if (validaIsNotSameBoforeAndAfter(itemDTO.getOrigin(),
				dbItem.getOrigin())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.origin.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getOrigin() + "");
			mdInfo.setAfterChange(itemDTO.getOrigin() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.origin.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品来源
		if (validaIsNotSameBoforeAndAfter(itemDTO.getProductChannelCode(),
				dbItem.getProductChannelCode())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.product_channel_code
					.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getProductChannelCode() + "");
			mdInfo.setAfterChange(itemDTO.getProductChannelCode() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.product_channel_code
					.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// ERP一级类目编码
		if (validaIsNotSameBoforeAndAfter(itemDTO.getErpFirstCategoryCode(),
				dbItem.getErpFirstCategoryCode())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.erp_first_category_code
					.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getErpFirstCategoryCode() + "");
			mdInfo.setAfterChange(itemDTO.getErpFirstCategoryCode() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.erp_first_category_code
					.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// ERP五级类目编码
		if (validaIsNotSameBoforeAndAfter(itemDTO.getErpFiveCategoryCode(),
				dbItem.getErpFiveCategoryCode())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.erp_five_category_code
					.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getErpFiveCategoryCode() + "");
			mdInfo.setAfterChange(itemDTO.getErpFiveCategoryCode() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.erp_five_category_code
					.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品长
		if (validaIsNotSameBoforeAndAfter(itemDTO.getLength(),
				dbItem.getLength())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.length.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getLength() + "");
			mdInfo.setAfterChange(itemDTO.getLength() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.length.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品宽
		if (validaIsNotSameBoforeAndAfter(itemDTO.getWidth(), dbItem.getWidth())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.width.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getWidth() + "");
			mdInfo.setAfterChange(itemDTO.getWidth() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.width.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品销售属性
		if (validaIsNotSameBoforeAndAfter(itemDTO.getAttrSaleStr(),
				dbItem.getAttrSaleStr())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.attr_sale.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getAttrSaleStr() + "");
			mdInfo.setAfterChange(itemDTO.getAttrSaleStr() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.attr_sale.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 装箱清单
		if (validaIsNotSameBoforeAndAfter(itemDTO.getPackingList(),
				dbItem.getPackingList())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.packing_list.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getPackingList() + "");
			mdInfo.setAfterChange(itemDTO.getPackingList() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.packing_list.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品描述
		ItemDescribe describe = itemDescribeDAO.getDescByItemId(dbItem
				.getItemId());
		if (validaIsNotSameBoforeAndAfter(itemDTO.getDescribeUrl(),
				describe.getDescribeContent())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.describe_content
					.getLabel());
			mdInfo.setModifyRecordId(describe.getDesId());
			mdInfo.setBeforeChange(describe.getDescribeContent() + "");
			mdInfo.setAfterChange(itemDTO.getDescribeUrl() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.describe_content.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item_describe.name());
			mdInfo.setCreateId(itemDTO.getModifyId());
			mdInfo.setCreateName(itemDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
	}
	
	public static void saveChangedRecordForVenusItem(ItemDTO venusItemInDTO, ItemDTO dbItem) {
		if(venusItemInDTO==null||dbItem==null){
			return;
		}
		
		ModifyDetailInfoMapper modifyDetailInfoMapper = SpringApplicationContextHolder
				.getBean(ModifyDetailInfoMapper.class);

		if (modifyDetailInfoMapper == null) {
			return;
		}
		ItemDescribeDAO itemDescribeDAO = SpringApplicationContextHolder
				.getBean(ItemDescribeDAO.class);
		
		ModifyDetailInfo mdInfo = new ModifyDetailInfo();
		if(validaIsNotSameBoforeAndAfter(venusItemInDTO.getCid(),dbItem.getCid())){
			mdInfo.setModifyRecordType(ShopModifyColumn.cid.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getCid().toString());
			mdInfo.setAfterChange(venusItemInDTO.getCid().toString());
			mdInfo.setModifyFieldId(ShopModifyColumn.cid.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		
		
		// 品牌编码
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getBrand(), dbItem.getBrand())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.brand.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getBrand().toString());
			mdInfo.setAfterChange(venusItemInDTO.getBrand().toString());
			mdInfo.setModifyFieldId(ShopModifyColumn.brand.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		
		
		// 广告语
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getAd(), dbItem.getAd())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.ad.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getAd() + "");
			mdInfo.setAfterChange(venusItemInDTO.getAd() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.ad.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品毛重
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getWeight(),
				dbItem.getWeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.weight.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getWeight() + "");
			mdInfo.setAfterChange(venusItemInDTO.getWeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.weight.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品净重
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getNetWeight(),
				dbItem.getNetWeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.net_weight.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getNetWeight() + "");
			mdInfo.setAfterChange(venusItemInDTO.getNetWeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.net_weight.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品高
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getHeight(),
				dbItem.getHeight())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.height.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getHeight() + "");
			mdInfo.setAfterChange(venusItemInDTO.getHeight() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.height.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品型号
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getModelType(),
				dbItem.getModelType())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.model_type.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getModelType() + "");
			mdInfo.setAfterChange(venusItemInDTO.getModelType() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.model_type.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品税率
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getTaxRate(),
				dbItem.getTaxRate())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.tax_rate.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getTaxRate() + "");
			mdInfo.setAfterChange(venusItemInDTO.getTaxRate() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.tax_rate.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品税率
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getTaxRate(),
				dbItem.getTaxRate())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.tax_rate.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getTaxRate() + "");
			mdInfo.setAfterChange(venusItemInDTO.getTaxRate() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.tax_rate.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品产地
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getOrigin(),
				dbItem.getOrigin())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.origin.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getOrigin() + "");
			mdInfo.setAfterChange(venusItemInDTO.getOrigin() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.origin.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		
		
		// 商品长
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getLength(),
				dbItem.getLength())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.length.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getLength() + "");
			mdInfo.setAfterChange(venusItemInDTO.getLength() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.length.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品宽
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getWidth(), dbItem.getWidth())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.width.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getWidth() + "");
			mdInfo.setAfterChange(venusItemInDTO.getWidth() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.width.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		// 商品销售属性
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getAttrSaleStr(),
				dbItem.getAttrSaleStr())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.attr_sale.getLabel());
			mdInfo.setModifyRecordId(dbItem.getItemId());
			mdInfo.setBeforeChange(dbItem.getAttrSaleStr() + "");
			mdInfo.setAfterChange(venusItemInDTO.getAttrSaleStr() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.attr_sale.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		
		// 商品店铺类目
//		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getShopCid(),
//				dbItem.getShopCid())) {
//			mdInfo.setModifyRecordType(ShopModifyColumn.shop_cid.getLabel());
//			mdInfo.setModifyRecordId(dbItem.getItemId());
//			mdInfo.setBeforeChange(dbItem.getShopCid() + "");
//			mdInfo.setAfterChange(venusItemInDTO.getShopCid() + "");
//			mdInfo.setModifyFieldId(ShopModifyColumn.shop_cid.name());
//			mdInfo.setModifyTableId(ShopModifyColumn.item.name());
//			mdInfo.setCreateId(venusItemInDTO.getModifyId());
//			mdInfo.setCreateName(venusItemInDTO.getModifyName());
//			mdInfo.setCreateTime(new Date());
//			modifyDetailInfoMapper.insert(mdInfo);
//		}
		
		// 商品描述
		ItemDescribe describe = itemDescribeDAO.getDescByItemId(dbItem
				.getItemId());
		if (validaIsNotSameBoforeAndAfter(venusItemInDTO.getDescribeUrl(),
				describe.getDescribeContent())) {
			mdInfo.setModifyRecordType(ShopModifyColumn.describe_content
					.getLabel());
			mdInfo.setModifyRecordId(describe.getDesId());
			mdInfo.setBeforeChange(describe.getDescribeContent() + "");
			mdInfo.setAfterChange(venusItemInDTO.getDescribeUrl() + "");
			mdInfo.setModifyFieldId(ShopModifyColumn.describe_content.name());
			mdInfo.setModifyTableId(ShopModifyColumn.item_describe.name());
			mdInfo.setCreateId(venusItemInDTO.getModifyId());
			mdInfo.setCreateName(venusItemInDTO.getModifyName());
			mdInfo.setCreateTime(new Date());
			modifyDetailInfoMapper.insert(mdInfo);
		}
		
	}
	 
	private static boolean validaIsNotSameBoforeAndAfter(Object before,
			Object after) {

		if (before == null && after != null) {
			return true;
		} else if (before != null && after != null && !before.equals(after)) {
			return true;
		} else {
			return false;
		}
	}
}

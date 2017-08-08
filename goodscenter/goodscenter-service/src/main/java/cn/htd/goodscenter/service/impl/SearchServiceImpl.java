package cn.htd.goodscenter.service.impl;

import cn.htd.goodscenter.dto.*;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.SearchDAO;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

	@Resource
	private SearchDAO searchDAO;
	@Resource
	private ItemCategoryService itemCategoryService;
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemBrandDAO itemBrandDAO;
	@Resource
	private ItemCategoryDAO itemCategoryDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public DataGrid<ItemSkuDTO> queryItemSkus(SearchInDTO inDTO, Pager pager) throws Exception {
		DataGrid<ItemSkuDTO> dg = new DataGrid<ItemSkuDTO>();
		List<ItemSkuDTO> itemSkus = this.searchDAO.queryItemSkus(inDTO, pager);
		long count = this.searchDAO.queryItemSkusCount(inDTO, null);
		//PriceQueryParam param = new PriceQueryParam();
		for (ItemSkuDTO itemSkuDTO : itemSkus) {
		//	param.setAreaCode(inDTO.getAreaCode());
		//	param.setItemId(itemSkuDTO.getItemId());
		//	param.setQty(1);
		//	param.setShopId(itemSkuDTO.getShopId());
		//	param.setSkuId(itemSkuDTO.getSkuId());
		//	param.setBuyerId(inDTO.getBuyerId());
		//	param.setSellerId(itemSkuDTO.getSellerId());
			// SKU价格
		//	itemSkuDTO.setSkuPrice(this.itemPriceService.getSkuShowPrice(param));
			// SKU询价
		//	itemSkuDTO.setSkuInquiryPirce(this.itemPriceService.getInquiryPrice(param));
			// SKU属性
			itemSkuDTO.setAttributes(this.itemCategoryService.queryCatAttrByKeyVals(itemSkuDTO.getSkuAttributeStr()).getResult());
			// SKU照片
			itemSkuDTO.setPicUrl(this.itemMybatisDAO.querySkuPics(itemSkuDTO.getSkuId()).get(0).getPicUrl());
		}
		dg.setRows(itemSkus);
		dg.setTotal(count);
		return dg;
	}

	@Override
	public SearchOutDTO getSearchConditions(SearchInDTO inDTO) {
		SearchOutDTO result = new SearchOutDTO();
		List<Item> itemIds = this.searchDAO.queryConditionIds(inDTO);
		List<String> attrStrs = new ArrayList<String>();
		List<Long> brandIds = new ArrayList<Long>();
		List<Long> catIds = new ArrayList<Long>();
		for (Item item : itemIds) {
			// 整理品牌ID
			if (!brandIds.contains(item.getBrand())) {
				brandIds.add(item.getBrand());
			}
			// 整理类目ID
			if (!catIds.contains(item.getCid())) {
				catIds.add(item.getCid());
			}
			// 整理属性
			if (!attrStrs.contains(item.getAttributes())) {
				attrStrs.add(item.getAttributes());
			}
		}
		// 品牌
		List<ItemBrandDTO> brands = this.itemBrandDAO.queryBrandByIds(brandIds);
		// 类目
		List<ItemCatCascadeDTO> cats = this.itemCategoryService.queryParentCategoryList(catIds.toArray(new Long[] {})).getResult();
		// 商品非销售属性
		List<ItemAttrDTO> attrs = this.getItemAttrs(attrStrs);
		result.setBrands(brands);
		result.setCategories(cats);
		result.setAttributes(attrs);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Discription:[获取搜索用的非销售属性]
	 * </p>
	 * 
	 * @param attrStrs
	 * @return
	 */
	private List<ItemAttrDTO> getItemAttrs(List<String> attrStrs) {
		List<ItemAttrDTO> attrList = new ArrayList<ItemAttrDTO>();
		List<String> attrIds = new ArrayList<String>();
		List<String> attrValIds = new ArrayList<String>();
		for (String attributesStr : attrStrs) {
			if (!StringUtils.isBlank(attributesStr)) {
				String[] keyVals = attributesStr.split(";");
				String[] strs = null;
				for (String str : keyVals) {
					strs = str.split(",");
					for (String keyVal : strs) {
						String[] kvs = keyVal.split(":");
						if (!attrIds.contains(kvs[0])) {
							attrIds.add(kvs[0]);
						}
						if (!attrValIds.contains(kvs[1])) {
							attrValIds.add(kvs[1]);
						}
					}

				}
			}
		}
		if (attrIds == null || attrIds.size() <= 0) {
			return attrList;
		}
		attrList = this.itemCategoryDAO.queryItemAttrList(attrIds);
		for (ItemAttrDTO itemAttr : attrList) {
			List<ItemAttrValueDTO> valueList = this.itemCategoryDAO.queryItemAttrValueList(itemAttr.getId(), attrValIds);
			itemAttr.setValues(valueList);
		}
		return attrList;
	}

	@Override
	public DataGrid<ItemSkuDTO> queryByItemSkusList(SearchInDTO inDTO, Pager pager) {
		DataGrid<ItemSkuDTO> dg = new DataGrid<ItemSkuDTO>();
		List<ItemSkuDTO> itemSkusList = searchDAO.queryByItemSkusList(inDTO, pager);
		dg.setRows(itemSkusList);
		return dg;
	}
}

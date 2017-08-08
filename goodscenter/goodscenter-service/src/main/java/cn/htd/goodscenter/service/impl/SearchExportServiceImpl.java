package cn.htd.goodscenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.domain.ItemCategory;
import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.SearchInDTO;
import cn.htd.goodscenter.dto.SearchOutDTO;
import cn.htd.goodscenter.service.SearchItemExportService;
import cn.htd.goodscenter.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: [搜索功能实现类]
 * </p>
 */
@Service("searchItemExportService")
public class SearchExportServiceImpl implements SearchItemExportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchExportServiceImpl.class);

	@Resource
	private ItemCategoryDAO itemCategoryDAO;
	@Resource
	private SearchService searchService;

	@Override
	public SearchOutDTO searchItemSolrTemp(SearchInDTO inDTO, Pager pager) {
		SearchOutDTO result = null;
		try {
			List<Long> catIdSet = new ArrayList<Long>();// 类目ID
			Long cid = inDTO.getCid();
			if (cid != null) {// 类目ID 转换为三级类目ID组
				List<ItemCategory> catsList = this.itemCategoryDAO.queryThirdCatsList(cid);
				for (ItemCategory itemCategory : catsList) {
					catIdSet.add(itemCategory.getCid());
				}
				if (catsList.size() == 0) {
					catIdSet.add(-1L);
				}
			}
			inDTO.setCategoryIds(catIdSet);
			if (StringUtils.isBlank(inDTO.getAreaCode())) {
				inDTO.setAreaCode("0");
			}

			// 商品属性分解
			List<String> attrList = this.getItemAttrStrList(inDTO.getAttributes());
			inDTO.setAttrList(attrList);
			// 分页查询商品
			//DataGrid<ItemSkuDTO> resultDg = this.searchService.queryItemSkus(inDTO, pager);
			// 根据输入的条件，反推出应该返回的条件
			result = this.searchService.getSearchConditions(inDTO);
			if (null == result) {
				result = new SearchOutDTO();
			}
			//result.setItemDTOs(resultDg);

		} catch (Exception e) {
			LOGGER.error("执行方法【searchItem】报错:{}", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@Override
	public SearchOutDTO searchItem(SearchInDTO inDTO, Pager pager) {
		SearchOutDTO result = null;
		try {
			List<Long> catIdSet = new ArrayList<Long>();// 类目ID
			Long cid = inDTO.getCid();
			if (cid != null) {// 类目ID 转换为三级类目ID组
				List<ItemCategory> catsList = this.itemCategoryDAO.queryThirdCatsList(cid);
				for (ItemCategory itemCategory : catsList) {
					catIdSet.add(itemCategory.getCid());
				}
				if (catsList.size() == 0) {
					catIdSet.add(-1L);
				}
			}
			inDTO.setCategoryIds(catIdSet);
			if (StringUtils.isBlank(inDTO.getAreaCode())) {
				inDTO.setAreaCode("0");
			}

			// 商品属性分解
			List<String> attrList = this.getItemAttrStrList(inDTO.getAttributes());
			inDTO.setAttrList(attrList);
			// 分页查询商品
			DataGrid<ItemSkuDTO> resultDg = this.searchService.queryItemSkus(inDTO, pager);
			// 根据输入的条件，反推出应该返回的条件
			result = this.searchService.getSearchConditions(inDTO);
			if (null == result) {
				result = new SearchOutDTO();
			}
			result.setItemDTOs(resultDg);

		} catch (Exception e) {
			LOGGER.error("执行方法【searchItem】报错:{}", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Discription:[获取类目属性字符串列表]
	 * </p>
	 */
	private List<String> getItemAttrStrList(String attributes) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isBlank(attributes)) {
			return list;
		}
		String[] attrArray = attributes.split(";");
		for (String attrStr : attrArray) {
			list.add(attrStr);
		}
		return list;
	}

	@Override
	public SearchOutDTO deleteItemValidation(SearchInDTO inDTO, Pager pager) {
		SearchOutDTO result = new SearchOutDTO();
		try {
			DataGrid<ItemSkuDTO> skusList = searchService.queryByItemSkusList(inDTO, pager);
			result.setItemDTOs(skusList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

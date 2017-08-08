package cn.htd.searchcenter.controllers.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.htd.assembling.SearchSortList;
import cn.htd.searchcenter.common.CommonUtil;
import cn.htd.searchcenter.forms.SearchItemForm;
import cn.htd.searchcenter.searchData.ItemData;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.service.SearchSolrExportService;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SearchItemController {

	@Resource
	private SearchSolrExportService searchSolrExportService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/searchItem", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchItem(final SearchItemForm itemForm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != itemForm) {
				Pager pager = new Pager();
				ObjectMapper obj = new ObjectMapper();
				boolean hasSku = false;
				boolean canBuy = false;
				boolean onlyProductPlus = false;
				boolean isLogin = false;
				boolean isHotCome = false;
				boolean isAccessJD = false;
				List<String> businessRelationSellerIdList = null;
				List<String> shieldCidAndBrandId = null;
				Map<String, List<String>> filterParam = null;
				if (StringUtils.isNotEmpty(itemForm.getKeyword())) {
					itemForm.setKeyword(CommonUtil.escapeSolrKeyword(itemForm
							.getKeyword()));
				}
				if (null != itemForm.getPage() && null != itemForm.getRows()) {
					pager.setPage(itemForm.getPage());
					pager.setRows(itemForm.getRows());
				}

				if (null != itemForm.getBuyerId()
						&& itemForm.getBuyerId().longValue() != 0) {
					businessRelationSellerIdList = searchSolrExportService
							.queryBusinessRelationSellerIds(itemForm
									.getBuyerId());
				}

				if (StringUtils.isNotEmpty(itemForm.getShieldCidAndBrandId())) {
					shieldCidAndBrandId = new ArrayList<String>();
					shieldCidAndBrandId = obj.readValue(
							itemForm.getShieldCidAndBrandId(), List.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getFilterParam())) {
					filterParam = new HashMap<String, List<String>>();
					filterParam = obj.readValue(itemForm.getFilterParam(),
							Map.class);
				}
				if (null == itemForm.getSort()) {
					itemForm.setSort(1);
				}
				if (StringUtils.isNotEmpty(itemForm.getHasSku())) {
					hasSku = itemForm.getHasSku().equals("1") ? true : false;
				}
				if (StringUtils.isNotEmpty(itemForm.getCanBuy())) {
					canBuy = itemForm.getCanBuy().equals("1") ? true : false;
				}
				if (StringUtils.isNotEmpty(itemForm.getOnlyProductPlus())) {
					onlyProductPlus = itemForm.getOnlyProductPlus().equals("1") ? true
							: false;
				}
				if (StringUtils.isNotEmpty(itemForm.getIsLogin())) {
					isLogin = itemForm.getIsLogin().equals("1") ? true : false;
				}
				if (StringUtils.isNotEmpty(itemForm.getIsHotCome())) {
					isHotCome = itemForm.getIsHotCome().equals("1") ? true
							: false;
				}
				if (StringUtils.isNotEmpty(itemForm.getIsAccessJD())) {
					isAccessJD = itemForm.getIsAccessJD().equals("1") ? true
							: false;
					if (isAccessJD) {
						if (null != itemForm.getBelongRelationSellerId()
								&& itemForm.getBelongRelationSellerId()
										.longValue() != 0) {
							isAccessJD = true;
						} else {
							isAccessJD = false;
						}
					}
				}
				BigDecimal section = new BigDecimal(0.0001);
				if (null != itemForm.getPriceStart()) {
					itemForm.setPriceStart(itemForm.getPriceStart().subtract(
							section));
				}
				if (null != itemForm.getPriceEnd()) {
					itemForm.setPriceEnd(itemForm.getPriceEnd().add(section));
				}

				if (StringUtils.isNotEmpty(itemForm.getItemCollectionList())) {
					List<String> itemCollectionCodes = obj.readValue(
							itemForm.getItemCollectionList(), List.class);
					businessRelationSellerIdList = searchSolrExportService
							.queryBusinessRelationSellerIds(itemForm
									.getBuyerId());
					List<Object> itemConnectionList = searchSolrExportService
							.searchCollectionItem(itemCollectionCodes,
									businessRelationSellerIdList, isLogin,
									itemForm.getBuyerId(), pager);
					List<ItemData> list = new ArrayList<ItemData>();
					for (Object object : itemConnectionList) {
						ItemData item = (ItemData) object;
						for (String code : itemCollectionCodes) {
							if (code.equals(item.getItemCode())) {
								int index = itemForm.getItemCollectionList()
										.indexOf(item.getItemCode());
								item.setIndex(index);
								list.add(item);
							}
						}
					}
					SearchSortList<ItemData> sortItem = new SearchSortList<ItemData>();
					if (null != list && list.size() > 0) {
						sortItem.sortByMethod(list, "getIndex", false);
					}
					resultMap.put("status", "success");
					resultMap.put("itemConnection", list);
					return resultMap;
				}
				SearchDataGrid<String> dg = searchSolrExportService.searchItem(
						itemForm.getAddressCode(), itemForm.getKeyword(),
						pager, itemForm.getSort(), itemForm.getBuyerId(),
						itemForm.getCategoryId(), itemForm.getBrandId(),
						itemForm.getPriceStart(), itemForm.getPriceEnd(),
						itemForm.getShopId(),
						itemForm.getBelongRelationSellerId(),
						itemForm.getBelongRelationSellerIdAndName(),
						businessRelationSellerIdList, hasSku, canBuy,
						onlyProductPlus, isLogin, isHotCome, isAccessJD,
						itemForm.getBuyerGrade(), shieldCidAndBrandId,
						filterParam);
				if (null != dg) {
					resultMap.put("dataList", dg.getDataList());
					resultMap.put("screenMap", dg.getScreenMap());
					resultMap.put("total", dg.getTotal());
					if (null != itemForm.getShopId()) {
						List<Object> newItemList = searchSolrExportService
								.searchNewItem(dg.getShopActivityItemQuery(),
										dg.getShopActivityItemFilterQuery(),
										isLogin, businessRelationSellerIdList,
										itemForm.getBuyerId());
						List<Object> popularityItemList = searchSolrExportService
								.searchPopularityItem(
										dg.getShopActivityItemQuery(),
										dg.getShopActivityItemFilterQuery(),
										isLogin, businessRelationSellerIdList,
										itemForm.getBuyerId());
						resultMap.put("popularityItem", popularityItemList);
						resultMap.put("newItem", newItemList);
					}
				} else {
					resultMap.put("dataList", null);
					resultMap.put("screenMap", null);
					resultMap.put("total", 0);
					resultMap.put("popularityItem", null);
					resultMap.put("newItem", null);
				}
				resultMap.put("status", "success");
			} else {
				resultMap.put("status", "error");
			}
		} catch (Exception e) {
			resultMap.put("status", "error");
			e.printStackTrace();
		}
		return resultMap;
	}
}

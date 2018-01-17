package cn.htd.searchcenter.controllers.mobile;

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

import cn.htd.searchcenter.common.CommonUtil;
import cn.htd.searchcenter.forms.SearchItemForm;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.service.SearchSolrExportMobileService;
import cn.htd.searchcenter.service.SearchSolrExportService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SearchItemMobileController {

	@Resource
	private SearchSolrExportMobileService searchSolrExportMobileService;
	@Resource
	private SearchSolrExportService searchSolrExportService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/searchItemMobile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchItem(final SearchItemForm itemForm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != itemForm) {
				Pager pager = new Pager();
				ObjectMapper obj = new ObjectMapper();
				boolean isAccessJD = false;
				List<String> businessRelationSellerIdList = null;
				List<String> shieldCidAndBrandId = null;
				List<String> promotionItemIdList = null;
				List<String> sellerIdList = null;
				List<String> itemCodeList = null;
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
				if (null != itemForm.getPromotionItemIdList()) {
					promotionItemIdList = new ArrayList<String>();
					promotionItemIdList = obj.readValue(
							itemForm.getPromotionItemIdList(), List.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getShieldCidAndBrandId())) {
					shieldCidAndBrandId = new ArrayList<String>();
					shieldCidAndBrandId = obj.readValue(
							itemForm.getShieldCidAndBrandId(), List.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getItemCodeList())) {
					itemCodeList = new ArrayList<String>();
					itemCodeList = obj.readValue(itemForm.getItemCodeList(),
							List.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getFilterParam())) {
					filterParam = new HashMap<String, List<String>>();
					filterParam = obj.readValue(itemForm.getFilterParam(),
							Map.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getSellerIdList())) {
					sellerIdList = new ArrayList<String>();
					sellerIdList = obj.readValue(itemForm.getSellerIdList(),
							List.class);
				}
				if (StringUtils.isNotEmpty(itemForm.getSellerIdList())
						|| StringUtils.isNotEmpty(itemForm.getItemCodeList())) {
					pager.setPage(1);
					pager.setRows(500);
				}
				if (null == itemForm.getSort()) {
					itemForm.setSort(1);
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
				if(StringUtils.isEmpty(itemForm.getRowsFlag())){
					itemForm.setRowsFlag("4");
				}
				SearchDataGrid<String> dg = searchSolrExportMobileService
						.searchItemMobile(itemForm.getAddressCode(),
								itemForm.getKeyword(), pager,
								itemForm.getSort(), itemForm.getBuyerId(),
								itemForm.getCategoryId(),
								itemForm.getBrandId(), itemForm.getShopId(),
								itemForm.getBelongRelationSellerId(),
								businessRelationSellerIdList, isAccessJD,
								shieldCidAndBrandId, filterParam,
								promotionItemIdList, sellerIdList, itemCodeList);
				if (null != dg) {
					resultMap.put("dataList", dg.getDataList());
					resultMap.put("screenMap", dg.getScreenMap());
					resultMap.put("total", dg.getTotal());
					if (null != itemForm.getShopId()) {
						List<Object> newItemList = searchSolrExportMobileService
								.searchNewItemMobile(
										dg.getShopActivityItemQuery(),
										dg.getShopActivityItemFilterQuery(),
										businessRelationSellerIdList,
										itemForm.getBuyerId());
						List<Object> popularityItemList = new ArrayList<Object>();
						if("1".equals(itemForm.getPromotionItemFlag())){
							popularityItemList = searchSolrExportMobileService
									.searchPopularityItemMobile(
											dg.getShopActivityItemQuery(),
											dg.getShopActivityItemFilterQuery(),
											businessRelationSellerIdList,
											itemForm.getBuyerId(), itemForm.getRowsFlag(), null);
						}else{
							popularityItemList = searchSolrExportMobileService
									.searchPopularityItemMobile(
											dg.getShopActivityItemQuery(),
											dg.getShopActivityItemFilterQuery(),
											businessRelationSellerIdList,
											itemForm.getBuyerId(),itemForm.getRowsFlag(), newItemList);
						}
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

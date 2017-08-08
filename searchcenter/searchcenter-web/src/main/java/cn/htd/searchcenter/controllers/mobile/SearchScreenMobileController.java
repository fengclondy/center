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
public class SearchScreenMobileController {

	@Resource
	private SearchSolrExportMobileService searchSolrExportMobileService;
	@Resource
	private SearchSolrExportService searchSolrExportService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/searchScreenMobile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchScreenMobile(final SearchItemForm itemForm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != itemForm) {
				Pager pager = new Pager();
				ObjectMapper obj = new ObjectMapper();
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
				SearchDataGrid<String> dg = searchSolrExportMobileService
						.searchScreenMobile(itemForm.getAddressCode(),
								itemForm.getKeyword(), itemForm.getBuyerId(),
								itemForm.getShopId(),
								itemForm.getBelongRelationSellerId(),
								businessRelationSellerIdList, isAccessJD,
								shieldCidAndBrandId, filterParam);
				if (null != dg) {
					resultMap.put("screenMap", dg.getScreenMap());
				} else {
					resultMap.put("screenMap", null);
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

package cn.htd.searchcenter.controllers.mall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.htd.searchcenter.service.SearchSolrExportService;

@Controller
public class SearchShopThreeCategoryController {

	@Resource
	private SearchSolrExportService searchSolrExportService;
	
	@RequestMapping(value = "/searchShopThreeCategory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchShopThreeCategory(final String shopId, final String areaCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(shopId)) {
				List<String> shopCidList = searchSolrExportService.searchShopThreeCategory(shopId, areaCode);
				resultMap.put("shopCidList", shopCidList);
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

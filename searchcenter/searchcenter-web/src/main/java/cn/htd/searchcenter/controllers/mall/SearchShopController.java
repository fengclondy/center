package cn.htd.searchcenter.controllers.mall;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.htd.searchcenter.common.CommonUtil;
import cn.htd.searchcenter.forms.SearchShopForm;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.service.SearchSolrExportService;

@Controller
public class SearchShopController {

	@Resource
	private SearchSolrExportService searchSolrExportService;

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@RequestMapping(value = "/searchShop", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchShop(final SearchShopForm shopForm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != shopForm) {
				Pager pager = new Pager();
				if (StringUtils.isNotEmpty(shopForm.getKeyword())) {
					shopForm.setKeyword(CommonUtil.escapeSolrKeyword(shopForm
							.getKeyword()));
				}
				if (null != shopForm.getPage() && null != shopForm.getRows()) {
					pager.setPage(shopForm.getPage());
					pager.setRows(shopForm.getRows());
				}
				if (null == shopForm.getSort()) {
					shopForm.setSort(1);
				}
				SearchDataGrid<String> dg = searchSolrExportService.searchShop(
						shopForm.getAddressCode(), shopForm.getKeyword(),
						pager, shopForm.getSort(), shopForm.getBuyerId());
				resultMap.put("dataList", dg.getDataList());
				resultMap.put("total", dg.getTotal());
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

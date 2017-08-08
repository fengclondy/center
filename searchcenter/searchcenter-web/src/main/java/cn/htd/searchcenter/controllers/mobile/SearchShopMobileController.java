package cn.htd.searchcenter.controllers.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.htd.searchcenter.common.CommonUtil;
import cn.htd.searchcenter.forms.SearchShopForm;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.service.SearchSolrExportMobileService;

@Controller
public class SearchShopMobileController {

	@Resource
	private SearchSolrExportMobileService searchSolrExportMobileService;

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@RequestMapping(value = "/searchShopMobile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchShop(final SearchShopForm shopForm) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (null != shopForm) {
				ObjectMapper obj = new ObjectMapper();
				Pager pager = new Pager();
				List<String> boxRelationVenderIdList = null;
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
				if (StringUtils.isNotEmpty(shopForm
						.getBoxRelationVenderIdList())) {
					boxRelationVenderIdList = new ArrayList<String>();
					boxRelationVenderIdList = obj.readValue(
							shopForm.getBoxRelationVenderIdList(), List.class);
				}
				SearchDataGrid<String> dg = searchSolrExportMobileService
						.searchShopMobile(shopForm.getAddressCode(),
								shopForm.getKeyword(), pager,
								shopForm.getSort(), shopForm.getBuyerId(),
								boxRelationVenderIdList);
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

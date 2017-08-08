package cn.htd.goodscenter.service;

import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.SearchInDTO;
import cn.htd.goodscenter.dto.SearchOutDTO;

/**
 * <p>
 * Description: [商品搜索服务]
 * </p>
 */
public interface SearchItemExportService {


	public SearchOutDTO searchItemSolrTemp(SearchInDTO inDTO, Pager pager);
		
	
	public SearchOutDTO searchItem(SearchInDTO inDTO, Pager pager);

	public SearchOutDTO deleteItemValidation(SearchInDTO inDTO, Pager pager);
}

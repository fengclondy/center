package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.ItemSkuDTO;
import cn.htd.goodscenter.dto.SearchInDTO;
import cn.htd.goodscenter.dto.SearchOutDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.Pager;

/**
 * 
 * <p>
 * Description: [商品搜索业务service]
 * </p>
 */
public interface SearchService {

	/**
	 * 
	 * <p>
	 * Discription:[根据条件查询SKU相关信息]
	 * </p>
	 */
	DataGrid<ItemSkuDTO> queryItemSkus(SearchInDTO inDTO, Pager pager) throws Exception;

	/**
	 * 
	 * <p>
	 * Discription:[根据输入的条件，反推出应该返回的条件<br>
	 * 查询反推的所有类目<br>
	 * 查询反推的所有品牌<br>
	 * 查询反推的所有非销售属性<br>
	 * ]
	 * </p>
	 */
	SearchOutDTO getSearchConditions(SearchInDTO inDTO);

	DataGrid<ItemSkuDTO> queryByItemSkusList(SearchInDTO inDTO, Pager pager);
}

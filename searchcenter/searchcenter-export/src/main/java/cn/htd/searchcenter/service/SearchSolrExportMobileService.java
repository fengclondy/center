package cn.htd.searchcenter.service;

import java.util.List;
import java.util.Map;

import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;

public interface SearchSolrExportMobileService {

	/**
	 * 
	 * <p>
	 * Discription:[根据关键字搜索店铺]
	 * </p>
	 * 
	 * @param addressCode
	 *            地区代码
	 * @param keyword
	 *            店铺关键字
	 * @param pager
	 * @param sort
	 *            排序方式（默认：1，商品价格：2，发布时间：3，销量：4） 都为降序 * @param buyerId 卖家Id
	 */
	public SearchDataGrid<String> searchShopMobile(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId,
			List<String> boxRelationVenderIdList);

	/**
	 * 商品搜索（Solr）
	 * 
	 * @param addressCode
	 *            登录所在城市站
	 * @param keyword
	 *            搜索关键字
	 * @param pager
	 *            分页
	 * @param sort
	 *            排序方式（默认：1，商品价格降序：2，商品价格升序：3，发布时间降序： 4，销量降序：5）
	 * @param buyerId
	 *            会员ID
	 * @param brandId
	 *            品牌ID
	 * @param categoryId
	 *            品类ID
	 * @param shopId
	 *            限制店铺ID
	 * @param belongRelationSellerId
	 * @param belongRelationSellerIdList
	 *            存在归属关系卖家ID
	 * @param businessRelationSellerIdList
	 *            存在经营关系卖家ID列表
	 * @param filterParam
	 *            筛选项目（属性code=值code，属性code=值code）
	 * @param sellerIdList
	 * @return
	 */
	SearchDataGrid<String> searchItemMobile(String addressCode, String keyword,
			Pager pager, Integer sort, Long buyerId, Long categoryId,
			Long brandId, Long shopId, Long belongRelationSellerId,
			List<String> businessRelationSellerIdList, boolean isAccessJD,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam,
			List<String> promotionItemIdList, List<String> sellerIdList,
			List<String> itemCodeList);

	List<Object> searchNewItemMobile(String query, String filterQuery,
			List<String> businessRelationSellerIdList, Long buyerId);

	List<Object> searchPopularityItemMobile(String query, String filterQuery,
			List<String> businessRelationSellerIdList, Long buyerId,
			String rowsFlag, List<Object> newItemList);

	/**
	 * 超级老板手机端筛选项搜索
	 * 
	 * @param addressCode
	 * @param keyword
	 * @param buyerId
	 * @param shopId
	 * @param belongRelationSellerId
	 * @param businessRelationSellerIdList
	 * @param isAccessJD
	 * @param shieldCidAndBrandId
	 * @param filterParam
	 * @return
	 */
	public SearchDataGrid<String> searchScreenMobile(String addressCode,
			String keyword, Long buyerId, Long shopId,
			Long belongRelationSellerId,
			List<String> businessRelationSellerIdList, boolean isAccessJD,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam);
}

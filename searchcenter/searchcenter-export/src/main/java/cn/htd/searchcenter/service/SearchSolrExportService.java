package cn.htd.searchcenter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.htd.searchcenter.searchData.ItemData;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;

public interface SearchSolrExportService {

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
	 *            1 修改时间升序(默认) 2 修改时间降序 3 评分升序 4评分降序 5销量升序 6销量降序
	 * @param buyerId
	 *            卖家Id
	 */
	public SearchDataGrid<String> searchShop(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId);

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
	 *            排序方式（默认：1，修改时间 2，3 销售量：4，5，上架时间：6，7，价格：8，9）asc，desc
	 * @param buyerId
	 *            会员ID
	 * @param brandId
	 *            品牌ID
	 * @param categoryId
	 *            品类ID
	 * @param priceStart
	 *            价格区间开始
	 * @param priceStart
	 *            价格区间开始
	 * @param shopId
	 *            限制店铺ID
	 * @param belongRelationSellerId 
	 * @param belongRelationSellerIdList
	 *            存在归属关系卖家ID
	 * @param businessRelationSellerIdList
	 *            存在经营关系卖家ID列表
	 * @param hasSku
	 *            是否有库存
	 * @param canBuy
	 *            是否可买
	 * @param onlyProductPlus
	 *            只搜索商品+商品
	 * @param isLogin
	 *            是否登录
	 * @param filterParam
	 *            筛选项目（属性code=值code，属性code=值code）逗号分隔
	 * @return
	 */
	SearchDataGrid<String> searchItem(String addressCode, String keyword,
			Pager pager, Integer sort, Long buyerId, Long categoryId,
			Long brandId, BigDecimal priceStart, BigDecimal priceEnd,
			Long shopId, Long belongRelationSellerId, String belongRelationSellerIdAndName,
			List<String> businessRelationSellerIdList, boolean hasSku,
			boolean canBuy, boolean onlyProductPlus, boolean isLogin,
			boolean isHotCome, boolean isAccessJD, String buyerGrade,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam);

	public List<String> queryBusinessRelationSellerIds(Long memberId);

	/**
	 * 新品
	 * @param query
	 * @param buyerId 
	 * @param businessRelationSellerIdList 
	 * @param isLogin 
	 * @return
	 */
	public List<Object> searchNewItem(String query, String filterQuery, boolean isLogin, List<String> businessRelationSellerIdList, Long buyerId);
	
	/**
	 * 人气
	 * @param query
	 * @param buyerId 
	 * @param businessRelationSellerIdList 
	 * @param isLogin 
	 */
	public List<Object> searchPopularityItem(String query, String filterQuery, boolean isLogin, List<String> businessRelationSellerIdList, Long buyerId);

	/**
	 * 商品收藏
	 * @param itemCollectionList
	 * @param businessRelationSellerIdList
	 * @param isLogin
	 * @param buyerId
	 * @return
	 */
	public List<Object> searchCollectionItem(List<String> itemCollectionList,
			List<String> businessRelationSellerIdList, boolean isLogin,
			Long buyerId, Pager pager);

	public List<String> searchShopThreeCategory(String shopId, String areaCode);
}

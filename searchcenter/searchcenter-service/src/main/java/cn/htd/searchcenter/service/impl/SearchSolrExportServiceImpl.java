package cn.htd.searchcenter.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import cn.htd.assembling.SearchSolrItemAssembling;
import cn.htd.assembling.SearchSortList;
import cn.htd.common.ExecuteResult;
import cn.htd.common.util.SysProperties;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;
import cn.htd.membercenter.service.MemberBusinessRelationService;
import cn.htd.searchcenter.searchData.ItemData;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.searchData.ShopData;
import cn.htd.searchcenter.service.SearchSolrExportService;

@Service("searchSolrExportService")
public class SearchSolrExportServiceImpl implements SearchSolrExportService {
	private Logger logger = Logger.getLogger(SearchSolrExportServiceImpl.class);

	public void setShopSolrClient(String url) {
		this.shopSolrClient = new HttpSolrServer(url);
	}

	public void setItemSolrClient(String url) {
		this.itemSolrClient = new HttpSolrServer(url);
	}

	public void setItemAttrSolrClient(String url) {
		this.itemAttrSolrClient = new HttpSolrServer(url);
	}

	@Resource(name = "shopSlaveHttpSolrServer")
	private HttpSolrClient shopSolrClient;
	@Resource(name = "itemSlaveHttpSolrServer")
	private HttpSolrClient itemSolrClient;
	@Resource(name = "itemAttrSlaveHttpSolrServer")
	private HttpSolrClient itemAttrSolrClient;
	@Resource(name = "memberBusinessRelationService")
	private MemberBusinessRelationService memberBusinessRelationService;

	public static void main(String args[]) {
		List<String> busList = new ArrayList<String>();
		busList.add("17606532134");

		SearchSolrExportServiceImpl service = new SearchSolrExportServiceImpl();
		service.setShopSolrClient("http://171.16.47.87:8983/solr/shop");
		service.setItemSolrClient("http://171.16.47.87:8983/solr/item");
		service.setItemAttrSolrClient("http://171.16.47.87:8983/solr/attr");
		// Pager page = new Pager();
		// page.setPage(1);
		// page.setRows(64);
		// SearchDataGrid<String> dg = service.searchItem("3201",
		// "电视", page, 1, null, null, null, null,
		// null, null, null, null, null, false, false,
		// false, false, true, false, null, null, null);
		// List<Object> itemList = dg.getDataList();
		// System.out.println(itemList.size());
		// for (Object object : itemList) {
		// if (object instanceof ItemData) {
		// ItemData data = (ItemData) object;
		// // System.out.println(data.getItemName() + "==="
		// // + data.getItemType() + "====" + data.getPrice());
		// }
		// }

		List<String> list = service.searchShopThreeCategory("639", null);
		for (String s : list) {
			System.out.println(s);
		}

		// public SearchDataGrid<String> searchItem(String addressCode,
		// String keyword, Pager pager, Integer sort, Long buyerId,
		// Long categoryId, Long brandId, BigDecimal priceStart,
		// BigDecimal priceEnd, Long shopId, Long belongRelationSellerId,
		// String belongRelationSellerIdAndName,
		// List<String> businessRelationSellerIdList, boolean hasSku,
		// boolean canBuy, boolean onlyProductPlus, boolean isLogin,
		// boolean isHotCome, boolean isAccessJD, String buyerGrade,
		// List<String> shieldCidAndBrandId,
		// Map<String, List<String>> filterParam) {
	}

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
	 * @param belongRelationSellerIdList
	 *            存在归属关系卖家ID
	 * @param businessRelationSellerIdList
	 *            存在经营关系卖家ID列表(集合内部sellerId,brandId,CategoryId直接拼接，不需要逗号分隔)
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
	@Override
	public SearchDataGrid<String> searchItem(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId,
			Long categoryId, Long brandId, BigDecimal priceStart,
			BigDecimal priceEnd, Long shopId, Long belongRelationSellerId,
			String belongRelationSellerIdAndName,
			List<String> businessRelationSellerIdList, boolean hasSku,
			boolean canBuy, boolean onlyProductPlus, boolean isLogin,
			boolean isHotCome, boolean isAccessJD, String buyerGrade,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam) {
		logger.info("come in searchItem start");
		logger.info("addressCode:" + addressCode);
		logger.info("keyword:" + keyword);
		logger.info("sort:" + sort);
		logger.info("buyerId:" + buyerId);
		logger.info("categoryId:" + categoryId);
		logger.info("brandId:" + brandId);
		logger.info("priceStart:" + priceStart);
		logger.info("priceEnd:" + priceEnd);
		logger.info("shopId:" + shopId);
		logger.info("belongRelationSellerId:" + belongRelationSellerId);
		logger.info("belongRelationSellerIdAndName:"
				+ belongRelationSellerIdAndName);
		logger.info("businessRelationSellerIdList:"
				+ businessRelationSellerIdList);
		logger.info("hasSku:" + hasSku);
		logger.info("canBuy:" + canBuy);
		logger.info("onlyProductPlus:" + onlyProductPlus);
		logger.info("isLogin:" + isLogin);
		logger.info("isHotCome:" + isHotCome);
		logger.info("isAccessJD:" + isAccessJD);
		logger.info("buyerGrade:" + buyerGrade);
		logger.info("shieldCidAndBrandId:" + shieldCidAndBrandId);
		logger.info("filterParam:" + filterParam);
		SearchDataGrid<String> dg = new SearchDataGrid<String>();
		// 构建搜索条件
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		List<String> qList = new ArrayList<String>();
		List<String> filterList = new ArrayList<String>();

		// 搜索店铺名称和关键字
		if (StringUtils.isNotEmpty(keyword)) {
			qList.add("_text_:" + keyword);
		}

		if (brandId != null) {
			qList.add("(brandId:" + brandId.longValue() + ")");
		}

		if (shopId != null) {
			boolean isExternalShop = false;
			String sellerType = "";
			String sellerIdStr = "";
			String sellerTypeAndSellerId = queryShopTypeAndSellerId(shopId);
			if (StringUtils.isNotEmpty(sellerTypeAndSellerId)) {
				sellerType = sellerTypeAndSellerId.split(":")[0];
				if ("2".equals(sellerType)) {
					isExternalShop = true;
					if (null != filterParam
							&& null != filterParam.get("cidName")) {
						List<String> cidCodeList = filterParam.get("cidName");
						filterParam.remove("cidName");
						filterParam.put("shopCid", cidCodeList);
					}
				}
				sellerIdStr = sellerTypeAndSellerId.split(":")[1];
				if (StringUtils.isNotEmpty(sellerIdStr)
						&& null != belongRelationSellerId) {
					Long sellerId = Long.parseLong(sellerIdStr);
					if (sellerId.longValue() != belongRelationSellerId
							.longValue()) {
						isAccessJD = false;
					}
				}
			}
			if (categoryId != null && StringUtils.isNotEmpty(sellerType)) {
				if ("2".equals(sellerType)) {
					qList.add("(shopCid:" + categoryId.longValue() + ")");
				} else {
					qList.add("(cid:" + categoryId.longValue() + ")");
				}
			}
			StringBuffer shopSb = new StringBuffer();
			if (isLogin) {
				if (StringUtils.isEmpty(sellerIdStr) || "0".equals(sellerIdStr)) {
					logger.info("登陆状态下，店铺没有上架则不展示商品:" + sellerIdStr);
					return null;
				}
				shopSb.append("(shopId:" + shopId.longValue());
				if (!isExternalShop) {
					if (isAccessJD) {
						shopSb.append(" OR (itemType:2"
								+ joinShieldBrandAndCategory(shieldCidAndBrandId)
								+ ")");
					} else {
						shopSb.append(" (NOT itemType:2)");
					}
				} else {
					shopSb.append(" (NOT itemType:2)");
				}
				shopSb.append(")");
			} else {
				shopSb.append("(shopId:" + shopId.longValue()
						+ " NOT itemType:2)");
			}
			qList.add(shopSb.toString());
		} else {
			if (categoryId != null) {
				qList.add("(cid:" + categoryId.longValue() + ")");
			}
		}

		if (isLogin) {
			if (canBuy) {
				StringBuffer canBuySb = new StringBuffer();
				if (businessRelationSellerIdList != null
						&& businessRelationSellerIdList.size() > 0) {
					canBuySb.append("((((shelvesFlag:3 OR shelvesFlag:4) AND ("
							+ join(businessRelationSellerIdList)
							+ ")) OR shelvesFlag:1 OR shelvesFlag:2) ");
				} else {
					canBuySb.append("((shelvesFlag:1) OR (shelvesFlag:2) ");
				}
				if (isAccessJD) {
					canBuySb.append(" OR (itemType:2 "
							+ joinShieldBrandAndCategory(shieldCidAndBrandId)
							+ ") OR ");
				} else {
					canBuySb.append(" NOT itemType:2 OR ");
				}
				canBuySb.append(" (itemType:5) OR (itemType:1)) ");
				filterList.add(canBuySb.toString());
			}

			if (StringUtils.isNotEmpty(addressCode)) {
				StringBuffer addressSb = new StringBuffer();
				if (addressCode.length() >= 4) {
					addressCode = "(areaCode:" + addressCode + " OR areaCode:"
							+ addressCode.substring(0, 2) + ")";
				} else {
					addressCode = "areaCode:" + addressCode;
				}
				if (null != businessRelationSellerIdList
						&& businessRelationSellerIdList.size() > 0) {
					addressSb
							.append("((((((shelvesFlag:3 OR shelvesFlag:4) AND ( "
									+ join(businessRelationSellerIdList)
									+ ")) "
									+ " OR (shelvesFlag:3 OR shelvesFlag:4)) OR shelvesFlag:1 ");
				} else {
					addressSb
							.append(" (((shelvesFlag:1 OR shelvesFlag:2 OR shelvesFlag:3 ");
				}
				addressSb.append(") AND " + addressCode);
				addressSb.append(" AND isSalesWholeCountry:false) ");
				addressSb.append(" OR ");
				addressSb
						.append("((((shelvesFlag:1) OR (shelvesFlag:4) OR (shelvesFlag:3) OR (itemType:1 ");
				if (isAccessJD) {
					addressSb.append(" OR (itemType:2 "
							+ joinShieldBrandAndCategory(shieldCidAndBrandId));
				} else {
					addressSb.append(" (NOT itemType:2 ");
				}
				addressSb
						.append("))) AND  isSalesWholeCountry:true NOT shelvesFlag:2))) ");
				filterList.add(addressSb.toString());
			}
			if (priceStart != null || priceEnd != null) {
				filterList.add(" (price:["
						+ (priceStart != null ? priceStart.toString() : 0)
						+ " TO "
						+ (priceEnd != null ? priceEnd.toString() : "*")
						+ "]) ");
			}

			if (onlyProductPlus) {
				StringBuffer jdSb = new StringBuffer();
				if (isAccessJD) {
					// 只展示京东+商品
					jdSb.append(" itemType:2 "
							+ joinShieldBrandAndCategory(shieldCidAndBrandId)
							+ "  NOT itemType:1 NOT itemType:3 NOT itemType:4 ");
				} else {
					// 什么都不展示
					jdSb.append(" itemType:888 ");
				}
				filterList.add(jdSb.toString());
			}
		} else {
			if (StringUtils.isNotEmpty(addressCode)) {
				StringBuffer addressSb = new StringBuffer();
				if (addressCode.length() >= 4) {
					addressCode = "(areaCode:" + addressCode + " OR areaCode:"
							+ addressCode.substring(0, 2) + ")";
				} else {
					addressCode = "areaCode:" + addressCode;
				}
				addressSb
						.append("(((shelvesFlag:1 OR shelvesFlag:2 OR shelvesFlag:3) ");
				addressSb.append(" AND " + addressCode);
				addressSb.append(" AND isSalesWholeCountry:false) ");
				addressSb
						.append(" OR ((itemType:5 OR itemType:1 OR itemType:2 OR shelvesFlag:1 OR shelvesFlag:2 OR shelvesFlag:3) AND isSalesWholeCountry:true)) ");
				filterList.add(addressSb.toString());
			} else {
				filterList
						.add("(itemType:5 OR itemType:1 OR itemType:2 OR shelvesFlag:1 OR shelvesFlag:2 OR shelvesFlag:3) ");
			}

			if (priceStart != null || priceEnd != null) {
				filterList.add(" (unLoginPrice:["
						+ (priceStart != null ? priceStart.toString() : 0)
						+ " TO "
						+ (priceEnd != null ? priceEnd.toString() : "*")
						+ "]) ");
			}
		}

		if (hasSku) {
			filterList.add(" (quantity:[1 TO *]) ");
		}

		String queryStr = "";
		if (qList.size() > 0) {
			for (int i = 0; i < qList.size(); i++) {
				queryStr = queryStr + qList.get(i);
				if (i < qList.size() - 1) {
					queryStr = queryStr + " AND ";
				}
			}
		}

		String filterStr = "";
		if (filterList.size() > 0) {
			for (int i = 0; i < filterList.size(); i++) {
				filterStr = filterStr + filterList.get(i);
				if (i < filterList.size() - 1) {
					filterStr = filterStr + " AND ";
				}
			}
		}

		if (null != filterParam) {
			List<String> searchList = new ArrayList<String>();
			List<String> brandCodeList = filterParam.get("brandName");
			List<String> cidCodeList = filterParam.get("cidName");
			List<String> shopCidList = filterParam.get("shopCid");
			List<String> attributeList = filterParam.get("attribute");
			List<String> sellerNameList = filterParam.get("sellerName");
			if (null != brandCodeList && brandCodeList.size() > 0) {
				String blParam = "";
				for (String bl : brandCodeList) {
					blParam += "brandId: " + bl + " OR ";
				}
				if (!"".equals(blParam)) {
					searchList
							.add(" ("
									+ blParam.substring(0, blParam.length() - 4)
									+ ") ");
				}
			}
			if (null != cidCodeList && cidCodeList.size() > 0) {
				String cidParam = "";
				for (String cid : cidCodeList) {
					cidParam += "cid: " + cid + " OR ";
				}
				if (!"".equals(cidParam)) {
					searchList.add(" ("
							+ cidParam.substring(0, cidParam.length() - 4)
							+ ") ");
				}
			}
			if (null != shopCidList && shopCidList.size() > 0) {
				String shopCidParam = "";
				for (String shopCid : shopCidList) {
					shopCidParam += "shopCid:" + shopCid + " OR ";
				}
				if (!"".equals(shopCidParam)) {
					searchList.add(" ("
							+ shopCidParam.substring(0,
									shopCidParam.length() - 4) + ") ");
				}
			}
			if (null != attributeList && attributeList.size() > 0) {
				String atParam = "";
				for (String at : attributeList) {
					if (StringUtils.isNotEmpty(at)) {
						atParam += "attr_id: \"" + at.split(":")[1] + "\" OR ";
					}
				}
				if (!"".equals(atParam)) {
					searchList
							.add(" ("
									+ atParam.substring(0, atParam.length() - 4)
									+ ") ");
				}
			}
			if (null != sellerNameList && sellerNameList.size() > 0) {
				String sellerParam = "";
				for (String seller : sellerNameList) {
					sellerParam += "sellerNameScreen: \"" + seller + "\" OR ";
				}
				if (!"".equals(sellerParam)) {
					if (isAccessJD) {
						if (StringUtils
								.isNotEmpty(belongRelationSellerIdAndName)
								&& sellerParam
										.indexOf(belongRelationSellerIdAndName) >= 0) {
							sellerParam += "itemType:2 OR ";
						}
					}
					searchList
							.add(" ("
									+ sellerParam.substring(0,
											sellerParam.length() - 4) + ") ");
				}
			}

			if (searchList.size() > 0) {
				for (int i = 0; i < searchList.size(); i++) {
					filterStr += " AND " + searchList.get(i);
				}
			}
		}
		query.setQuery(queryStr);
		query.setFilterQueries(filterStr);
		if (null != shopId) {
			dg.setShopActivityItemQuery(queryStr);
			dg.setShopActivityItemFilterQuery(filterStr);
		}

		// 设置排序
		if (sort == 1) {
			if (null != shopId) {
				query.setSort("itemType", ORDER.asc);
			} else {
				query.set("defType", "edismax");
				if (StringUtils.isNotEmpty(keyword)) {
					String keyScore = "itemName^5";
					query.set("qf", keyScore);
				}
				String score = "rord(itemType)^4 ord(hasQuantity)^3 recip(ord(listtingTime),1,1000,1000)^1.5";
				query.set("bf", score);
			}
		}
		if (sort == 2) {// 销量降序(修改时间)
			query.setSort("salesVolume", ORDER.desc);
		}
		if (sort == 3) {// 销量降序(修改时间)
			query.setSort("salesVolume", ORDER.asc);
		}
		if (sort == 4) {// 时间降序(修改时间)
			query.setSort("listtingTime", ORDER.desc);
		}
		if (sort == 5) {// 时间降序(修改时间)
			query.setSort("listtingTime", ORDER.asc);
		}
		if (sort == 6) {// 价格降序(修改时间)
			if (isLogin) {
				query.setSort("price", ORDER.desc);
			} else {
				query.setSort("unLoginPrice", ORDER.desc);
			}
		}
		if (sort == 7) {// 价格降序(修改时间)
			if (isLogin) {
				query.setSort("price", ORDER.asc);
			} else {
				query.setSort("unLoginPrice", ORDER.asc);
			}
		}

		// 获取查询筛选项
		Map<String, Object> screenMap = searchScreenMap(query);
		// 热搜商品查询
		List<Object> hotWordList = new ArrayList<Object>();
		if (isHotCome) {
			hotWordList = hotWordData(queryStr, filterStr, isLogin,
					businessRelationSellerIdList, buyerId, buyerGrade, keyword,
					sort, screenMap, belongRelationSellerIdAndName, isAccessJD);
			String ids = HotWordIds(hotWordList);
			if (StringUtils.isNotEmpty(ids)) {
				query.setFilterQueries(query.getFilterQueries()[0] + ids);
			}
		}
		int totalCount = calculationItemTotal(query);
		// 设置分页
		query.setStart((pager.getPage() - 1) * pager.getRows());
		query.setRows(pager.getRows());
		logger.info("queryStr:" + query.getQuery());
		logger.info("filterStr:" + query.getFilterQueries()[0]);
		try {
			QueryResponse queryResponse = itemSolrClient.query(query,
					METHOD.POST);
			SolrDocumentList results = queryResponse.getResults();
			dg.setTotal(totalCount + hotWordList.size());
			List<Object> itemList = assemblingItemData(results, isLogin,
					businessRelationSellerIdList, buyerId, sort, screenMap,
					belongRelationSellerIdAndName, isAccessJD);
			hotWordList.addAll(itemList);
			if (hotWordList.size() > pager.getRows()) {
				List<Object> filterItemList = new ArrayList<Object>();
				for (int i = 0; i < pager.getRows(); i++) {
					filterItemList.add(hotWordList.get(i));
				}
				dg.setDataList(filterItemList);
			}else{
				dg.setDataList(hotWordList);
			}
			logger.info("商品查询条数：" + totalCount);
			dg.setScreenMap(screenMap);
		} catch (Exception e) {
			logger.error("searchItem is error", e);
		}
		logger.info("come in searchItem end");
		return dg;
	}

	private String HotWordIds(List<Object> hotWordList) {
		String hotWordItemIds = "";
		for (Object obj : hotWordList) {
			if (obj instanceof ItemData) {
				ItemData itemData = (ItemData) obj;
				hotWordItemIds += " NOT id:" + itemData.getId();
			}
		}
		return hotWordItemIds;
	}

	private String queryShopTypeAndSellerId(Long shopId) {
		String sellerTypeAndSellerId = "";
		try {
			SolrQuery query = new SolrQuery();
			query.setRequestHandler("/select");
			query.setQuery("shopId:" + shopId);
			QueryResponse queryResponse = shopSolrClient.query(query,
					METHOD.POST);
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			for (SolrDocument doc : solrDocumentList) {
				String sellerType = doc.getFieldValue("sellerType") == null ? null
						: doc.getFieldValue("sellerType").toString();
				String sellerId = doc.getFieldValue("sellerId") == null ? null
						: doc.getFieldValue("sellerId").toString();
				sellerTypeAndSellerId = sellerType + ":" + sellerId;
				break;
			}
		} catch (Exception e) {
			logger.error("queryShopTypeAndSellerId is error", e);
		}
		return sellerTypeAndSellerId;
	}

	private String join(List<String> businessRelationSellerIdList) {
		String result = "";
		if (businessRelationSellerIdList.size() > 500) {
			for (int i = 0; i < 500; i++) {
				result += " vbc:" + businessRelationSellerIdList.get(i) + " OR";
			}
		} else {
			for (String sellerId : businessRelationSellerIdList) {
				result += " vbc:" + sellerId + " OR";
			}
		}
		return result.substring(0, result.length() - 2);
	}

	/***
	 * 店铺搜索（SOLR）
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
	@Override
	public SearchDataGrid<String> searchShop(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId) {
		logger.info("come in searchShop start");
		logger.info("addressCode:" + addressCode);
		logger.info("keyword:" + keyword);
		logger.info("sort:" + sort);
		logger.info("buyerId:" + buyerId);
		SearchDataGrid<String> dg = new SearchDataGrid<String>();
		// 构建搜索条件
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		String queryStr = "";
		// 搜索店铺名称和关键字
		if (StringUtils.isNotEmpty(keyword)) {
			queryStr = "_text_:" + keyword;
		}
		try {
			logger.info("queryStr:" + queryStr);
			if ("".equals(queryStr)) {
				return null;
			}
			query.setQuery(queryStr);
			// 设置排序
			int total = calculationShopTotal(query);
			// 设置分页
			query.setStart((pager.getPage() - 1) * pager.getRows());
			query.setRows(pager.getRows());
			QueryResponse queryResponse = shopSolrClient.query(query,
					METHOD.POST);
			SolrDocumentList results = queryResponse.getResults();
			dg.setTotal(total);
			List<Object> shopList = assemblingShopData(results);
			dg.setDataList(shopList);
			logger.info("店铺查询条数：" + total);
		} catch (Exception e) {
			logger.error("searchShop is error", e);
		}
		logger.info("come in searchShop end");
		return dg;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> searchScreenMap(SolrQuery query) {
		Map<String, Object> screenMap = new LinkedHashMap<String, Object>();
		List<String> cidList = new ArrayList<String>();
		SolrQuery queryCrumbs = new SolrQuery();
		queryCrumbs = query;
		queryCrumbs.setFacet(true);
		queryCrumbs.addFacetField(new String[] { "brandNameScreen",
				"cidNameScreen", "cid", "sellerNameScreen" });
		QueryResponse response;
		try {
			response = itemSolrClient.query(queryCrumbs, METHOD.POST);
			List<FacetField> facetFields = response.getFacetFields();
			for (FacetField facet : facetFields) {
				// 品牌名称筛选项获取
				if ("brandNameScreen".equals(facet.getName())) {
					List<Count> counts = facet.getValues();
					List<String> brandList = new ArrayList<String>();
					for (Count count : counts) {
						if (count.getCount() > 0) {
							brandList.add(count.getName());
						}
					}
					screenMap.put("brandName", brandList);
				}
				// 品类名称筛选项获取
				if ("cidNameScreen".equals(facet.getName())) {
					List<Count> counts = facet.getValues();
					List<String> cidNameList = new ArrayList<String>();
					for (Count count : counts) {
						if (count.getCount() > 0) {
							cidNameList.add(count.getName());
						}
					}
					screenMap.put("cidName", cidNameList);
				}
				if ("cid".equals(facet.getName())) {
					List<Count> counts = facet.getValues();
					for (Count count : counts) {
						if (count.getCount() > 0) {
							cidList.add(count.getName());
						}
					}
				}
				if ("sellerNameScreen".equals(facet.getName())) {
					List<Count> counts = facet.getValues();
					List<String> sellerNameList = new ArrayList<String>();
					for (Count count : counts) {
						if (count.getCount() > 0) {
							sellerNameList.add(count.getName());
						}
					}
					screenMap.put("sellerName", sellerNameList);
				}
			}
			LinkedHashMap<String, List<String>> attributeMap = convertAttr(cidList);
			List<String> brandName = (List<String>) screenMap.get("brandName");
			List<String> cidName = (List<String>) screenMap.get("cidName");
			List<String> sellerName = (List<String>) screenMap
					.get("sellerName");
			attributeMap.put("sellerName", sellerName);
			screenMap.clear();
			screenMap.put("cidName", cidName);
			screenMap.put("brandName", brandName);
			screenMap.put("attribute", attributeMap);
		} catch (Exception e) {
			logger.error("searchScreenMap is error", e);
		}
		return screenMap;
	}

	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, List<String>> convertAttr(List<String> cidList) {
		LinkedHashMap<String, List<String>> attrMap = new LinkedHashMap<String, List<String>>();
		try {
			if (null != cidList && cidList.size() > 0) {
				String queryStr = "";
				for (String cid : cidList) {
					queryStr = "cid:" + cid + " OR ";
				}
				if (!"".equals(queryStr)) {
					SolrQuery query = new SolrQuery();
					query.setRequestHandler("/select");
					query.setQuery(queryStr.substring(0, queryStr.length() - 4));
					logger.info("convertAttr query:" + query.getQuery());
					QueryResponse queryResponse = itemAttrSolrClient.query(
							query, METHOD.POST);
					SolrDocumentList results = queryResponse.getResults();
					List<String> attrAllList = new ArrayList<String>();
					for (SolrDocument solrDocument : results) {
						if (null != solrDocument.getFieldValue("attrAll")) {
							attrAllList.add(solrDocument.getFieldValue(
									"attrAll").toString());
						}
					}
					if (attrAllList.size() > 0) {
						for (String attr : attrAllList) {
							Map<String, List<String>> aMap = JSONObject
									.fromObject(attr);
							attrMap.putAll(aMap);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("convertAttr is error", e);
		}
		return attrMap;
	}

	private List<Object> hotWordData(String queryStr, String filterStr,
			boolean isLogin, List<String> businessRelationSellerIdList,
			Long buyerId, String buyerGrade, String keyword, Integer sort,
			Map<String, Object> screenMap,
			String belongRelationSellerIdAndName, boolean isAccessJD) {
		List<Object> itemList = new ArrayList<Object>();
		try {
			if (StringUtils.isNotEmpty(keyword)) {
				filterStr = filterStr + " AND (hotWord:" + keyword + ")";
			} else {
				logger.info("hotWord keyword is null");
				return null;
			}
			SolrQuery hotWordQuery = new SolrQuery();
			hotWordQuery.setQuery(queryStr);
			hotWordQuery.setFilterQueries(filterStr);
			logger.info("hotWord query:" + queryStr);
			logger.info("hotWord filter:" + filterStr);
			QueryResponse queryResponse = itemSolrClient.query(hotWordQuery,
					METHOD.POST);
			SolrDocumentList results = queryResponse.getResults();
			long numFound = results.getNumFound();
			if (numFound == 0) {
				return itemList;
			} else {
				itemList = SearchSolrItemAssembling.SolrItemDateAssembling(
						results, isLogin, businessRelationSellerIdList,
						buyerId, sort, screenMap,
						belongRelationSellerIdAndName, isAccessJD, null);
			}
		} catch (Exception e) {
			logger.error("hotWordData error!", e);
			return itemList;
		}

		if (itemList.size() > 0) {
			List<Object> retainList = new ArrayList<Object>();
			for (int i = 0; i < itemList.size(); i++) {
				if (i == 4) {
					break;
				}
				retainList.add(itemList.get(i));
			}
			itemList.clear();
			itemList.addAll(retainList);
		}
		return itemList;
	}

	private List<Object> assemblingItemData(SolrDocumentList results,
			boolean isLogin, List<String> businessRelationSellerIdList,
			Long buyerId, Integer sort, Map<String, Object> screenMap,
			String belongRelationSellerIdAndName, boolean isAccessJD)
			throws ParseException, SolrServerException, IOException {
		Map<String, String> priceMap = filterItemData(results,
				businessRelationSellerIdList, isLogin);
		List<Object> itemList = SearchSolrItemAssembling
				.SolrItemDateAssembling(results, isLogin,
						businessRelationSellerIdList, buyerId, sort, screenMap,
						belongRelationSellerIdAndName, isAccessJD, priceMap);
		return itemList;
	}

	private Map<String, String> filterItemData(SolrDocumentList results,
			List<String> businessRelationSellerIdList, boolean isLogin)
			throws SolrServerException, IOException {
		Map<String, String> priceMap = new HashMap<String, String>();
		List<String> itemIdList = new ArrayList<String>();
		if (isLogin) {
			for (SolrDocument doc : results) {
				String shelvesFlag = doc.getFieldValue("shelvesFlag") == null ? null
						: doc.getFieldValue("shelvesFlag").toString();
				String vbc = doc.getFieldValue("vbc") == null ? null : doc
						.getFieldValue("vbc").toString();
				if (null != shelvesFlag && "4".equals(shelvesFlag)) {
					if (!businessRelationSellerIdList.contains(vbc)) {
						String itemId = doc.getFieldValue("itemId") == null ? null
								: doc.getFieldValue("itemId").toString();
						if (null != itemId) {
							itemIdList.add(itemId + "4");
						}
					}
				}
			}
			if (itemIdList.size() > 0) {
				String idStr = "";
				for (String id : itemIdList) {
					idStr += "id:" + id + " OR ";
				}
				if (!"".equals(idStr)) {
					idStr = idStr.substring(0, idStr.length() - 4);
					SolrQuery q = new SolrQuery();
					q.setQuery(idStr);
					QueryResponse queryResponse = itemSolrClient.query(q,
							METHOD.POST);
					SolrDocumentList itemResults = queryResponse.getResults();
					for (SolrDocument sd : itemResults) {
						String itemId = sd.getFieldValue("itemId") == null ? null
								: sd.getFieldValue("itemId").toString();
						String price = sd.getFieldValue("price") == null ? null
								: sd.getFieldValue("price").toString();
						if (null != itemId) {
							priceMap.put(itemId, price);
						}
					}
				}
			}
		}
		return priceMap;
	}

	private List<Object> assemblingShopData(SolrDocumentList results) {
		List<Object> shopList = new ArrayList<Object>();
		for (SolrDocument doc : results) {
			ShopData shopData = new ShopData();
			shopData.setShopId(doc.getFieldValue("shopId") == null ? null : doc
					.getFieldValue("shopId").toString());
			shopData.setShopName(doc.getFieldValue("shopName") == null ? null
					: doc.getFieldValue("shopName").toString());
			shopData.setCidNames(doc.getFieldValue("cids") == null ? null : doc
					.getFieldValue("cids").toString());
			shopData.setSellerName(doc.getFieldValue("sellerName") == null ? null
					: doc.getFieldValue("sellerName").toString());
			shopData.setShopQQ(doc.getFieldValue("shopQQ") == null ? null : doc
					.getFieldValue("shopQQ").toString());
			if (null != doc.getFieldValue("logoUrl")) {
				shopData.setShopLog(doc.getFieldValue("logoUrl").toString());
			}
			if (null != doc.getFieldValue("locationAddress")) {
				shopData.setLocationAddress(doc
						.getFieldValue("locationAddress").toString());
			}
			if (null != doc.getFieldValue("sellerId")) {
				shopData.setSellerId(doc.getFieldValue("sellerId").toString());
			}
			if (null != doc.getFieldValue("sellerType")) {
				shopData.setSellerType(doc.getFieldValue("sellerType")
						.toString());
			}
			shopList.add(shopData);
		}
		return shopList;
	}

	public int calculationItemTotal(SolrQuery query) {
		long total = 0;
		try {
			QueryResponse queryResponseF = itemSolrClient.query(query,
					METHOD.POST);
			total = queryResponseF.getResults().getNumFound();
		} catch (Exception e) {
			logger.error("calculationItemTotal is error", e);
		}
		return Integer.parseInt(String.valueOf(total));
	}

	public int calculationShopTotal(SolrQuery query) {
		long total = 0;
		try {
			QueryResponse queryResponseF = shopSolrClient.query(query,
					METHOD.POST);
			total = queryResponseF.getResults().getNumFound();
		} catch (Exception e) {
			logger.error("calculationShopTotal is error", e);
		}
		return Integer.parseInt(String.valueOf(total));
	}

	public static String joinShieldBrandAndCategory(
			List<String> shieldCidAndBrandId) {
		String shieldStr = "";
		if (null != shieldCidAndBrandId && shieldCidAndBrandId.size() > 0) {
			if (shieldCidAndBrandId.size() > 500) {
				for (int i = 0; i < 500; i++) {
					shieldStr += "\"" + shieldCidAndBrandId.get(i) + "\" ";
				}
			} else {
				for (int i = 0; i < shieldCidAndBrandId.size(); i++) {
					shieldStr += "\"" + shieldCidAndBrandId.get(i) + "\" ";
				}
			}
			shieldStr = " NOT shieldCidAndBrandId:(" + shieldStr + ")";
		}
		return shieldStr;
	}

	@Override
	public List<String> queryBusinessRelationSellerIds(Long memberId) {
		// 检索经营关系
		ExecuteResult<List<MemberBusinessRelationDTO>> bussinessShipRslt = memberBusinessRelationService
				.selectMemberBussinsessRelationShip(memberId);
		List<String> businessSellerIds = new ArrayList<String>();
		if (bussinessShipRslt.isSuccess()) {
			List<MemberBusinessRelationDTO> relationShipSellers = bussinessShipRslt
					.getResult();
			for (MemberBusinessRelationDTO memberBusinessRelationDTO : relationShipSellers) {
				businessSellerIds
						.add(memberBusinessRelationDTO.getSellerId()
								+ String.valueOf(memberBusinessRelationDTO
										.getBrandId())
								+ memberBusinessRelationDTO.getCategoryId());
			}
		}
		return businessSellerIds;
	}

	@Override
	public List<Object> searchNewItem(String query, String filterQuery,
			boolean isLogin, List<String> businessRelationSellerIdList,
			Long buyerId) {
		logger.info("come in searchNewItem start");
		logger.info("searchNewItem query:" + query);
		// 构建搜索条件
		List<Object> itemList = new ArrayList<Object>();
		SolrQuery q = new SolrQuery();
		q.setRequestHandler("/select");
		if (StringUtils.isNotEmpty(query)) {
			try {
				q.setQuery(query);
				q.setFilterQueries(filterQuery);
				q.addSort("listtingTime", ORDER.desc);
				q.setRows(12);
				QueryResponse queryResponse = itemSolrClient.query(q,
						METHOD.POST);
				SolrDocumentList results = queryResponse.getResults();
				itemList = assemblingItemData(results, isLogin,
						businessRelationSellerIdList, buyerId, null, null,
						null, false);
				logger.info("新品商品查询条数：" + itemList.size());
			} catch (Exception e) {
				logger.error("searchNewItem is error", e);
				return null;
			}
		}
		logger.info("come in searchNewItem end");
		return itemList;
	}

	@Override
	public List<Object> searchPopularityItem(String query, String filterQuery,
			boolean isLogin, List<String> businessRelationSellerIdList,
			Long buyerId) {
		logger.info("come in searchPopularityItem start");
		logger.info("searchPopularityItem query:" + query);
		// 构建搜索条件
		List<Object> itemList = new ArrayList<Object>();
		SolrQuery q = new SolrQuery();
		q.setRequestHandler("/select");
		if (StringUtils.isNotEmpty(query)) {
			try {
				q.setQuery(query);
				q.setFilterQueries(filterQuery);
				q.addSort("salesVolume", ORDER.desc);
				q.setRows(4);
				QueryResponse queryResponse = itemSolrClient.query(q,
						METHOD.POST);
				SolrDocumentList results = queryResponse.getResults();
				itemList = assemblingItemData(results, isLogin,
						businessRelationSellerIdList, buyerId, null, null,
						null, false);
				logger.info("人气商品查询条数：" + itemList.size());
			} catch (Exception e) {
				logger.error("searchPopularityItem is error", e);
				return null;
			}
		}
		logger.info("come in searchPopularityItem end");
		return itemList;
	}

	@Override
	public List<Object> searchCollectionItem(List<String> itemCollectionList,
			List<String> businessRelationSellerIdList, boolean isLogin,
			Long buyerId, Pager pager) {
		logger.info("come in searchCollectionItem start");
		logger.info("itemCollectionList:" + itemCollectionList);
		logger.info("businessRelationSellerIdList:"
				+ businessRelationSellerIdList);
		List<Object> itemList = new ArrayList<Object>();
		// 构建搜索条件
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		String queryStr = "";
		String filterStr = "";
		if (null != itemCollectionList) {
			for (String itemCollectionCode : itemCollectionList) {
				queryStr += "itemCode:" + itemCollectionCode + " OR ";
			}
		}
		if (null != businessRelationSellerIdList
				&& businessRelationSellerIdList.size() > 0) {
			filterStr = "((((shelvesFlag:3 OR shelvesFlag:4) AND ("
					+ join(businessRelationSellerIdList)
					+ ")) OR (shelvesFlag:3 OR shelvesFlag:4)) OR shelvesFlag:1 OR itemType:1 OR itemType:2 OR itemType:5)";
		} else {
			filterStr = " (shelvesFlag:1 OR shelvesFlag:2 OR shelvesFlag:3 OR itemType:1 OR itemType:2 OR itemType:5)";
		}
		if (!"".equals(queryStr)) {
			queryStr = "(" + queryStr.substring(0, queryStr.length() - 4) + ")";
			query.setQuery(queryStr);
			query.setFilterQueries(filterStr);
		}
		logger.info("searchCollectionItem query:" + query.getQuery());
		logger.info("searchCollectionItem filter:"
				+ query.getFilterQueries()[0]);
		// 设置分页
		query.setStart((pager.getPage() - 1) * pager.getRows());
		query.setRows(pager.getRows());
		try {
			QueryResponse queryResponse = itemSolrClient.query(query,
					METHOD.POST);
			SolrDocumentList results = queryResponse.getResults();
			itemList = assemblingItemData(results, isLogin,
					businessRelationSellerIdList, buyerId, null, null, null,
					false);
			logger.info("商品收藏查询条数：" + itemList.size());
		} catch (Exception e) {
			logger.error("PopularityAndNewItem is error", e);
			return null;
		}
		logger.info("come in searchCollectionItem end");
		return itemList;
	}

	@Override
	public List<String> searchShopThreeCategory(String shopId, String areaCode) {
		logger.info("come in searchShopThreeCategory start");
		logger.info("shopId:" + shopId);
		logger.info("areaCode:" + areaCode);
		List<String> cidList = new ArrayList<String>();
		// 构建搜索条件
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		if (StringUtils.isNotEmpty(shopId)) {
			query.setQuery("shopId:" + shopId);
			if (StringUtils.isNotEmpty(areaCode)) {
				query.addFilterQuery("areaCode:" + areaCode);
			}
			try {
				query.setFacet(true);
				query.addFacetField("cid");
				logger.info("searchShopThreeCategory query:" + query.getQuery());
				if(null != query.getFilterQueries()){
					for (String filterQuery : query.getFilterQueries()) {
						logger.info("searchShopThreeCategory filter:" + filterQuery);
					}
				}
				QueryResponse queryResponse = itemSolrClient.query(query,
						METHOD.POST);
				List<FacetField> facetFields = queryResponse.getFacetFields();
				for (FacetField facet : facetFields) {
					if ("cid".equals(facet.getName())) {
						List<Count> counts = facet.getValues();
						for (Count count : counts) {
							if (count.getCount() > 0) {
								cidList.add(count.getName());
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("searchShopThreeCategory is error", e);
				return null;
			}
		}
		logger.info("come in searchShopThreeCategory end");
		return cidList;
	}
}

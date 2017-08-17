package cn.htd.searchcenter.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import cn.htd.assembling.SearchSolrItemAssembling;
import cn.htd.searchcenter.searchData.ItemData;
import cn.htd.searchcenter.searchData.Pager;
import cn.htd.searchcenter.searchData.SearchDataGrid;
import cn.htd.searchcenter.searchData.ShopData;
import cn.htd.searchcenter.service.SearchSolrExportMobileService;

@Service("searchSolrExportMobileService")
public class SearchSolrExportMobileServiceImpl implements
		SearchSolrExportMobileService {

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

	public static void main(String[] args) {
		SearchSolrExportMobileServiceImpl service = new SearchSolrExportMobileServiceImpl();
		service.setShopSolrClient("http://172.16.1.56:8983/solr/shop");
		service.setItemSolrClient("http://172.16.1.56:8983/solr/item");
		service.setItemAttrSolrClient("http://172.16.1.56:8983/solr/attr");
		List<String> busList = new ArrayList<String>();
		busList.add("17606532353");
		SearchDataGrid<String> grid = service.searchItemMobile("3201", null,
				new Pager(), 1, 90425L, null, null, 532L, null, busList, false,
				null, null, null, null, null);
		List<Object> itemList = grid.getDataList();
		System.out.println(itemList.size());
		for (Object object : itemList) {
			if (object instanceof ItemData) {
				ItemData data = (ItemData) object;
				System.out.println(data.getItemName() + "==="
						+ data.getItemType() + "====" + data.getPrice());
			}
		}
	}

//	public SearchDataGrid<String> searchItemMobile(String addressCode,
//			String keyword, Pager pager, Integer sort, Long buyerId,
//			Long categoryId, Long brandId, Long shopId,
//			Long belongRelationSellerId,
//			List<String> businessRelationSellerIdList, boolean isAccessJD,
//			List<String> shieldCidAndBrandId,
//			Map<String, List<String>> filterParam,
//			List<String> promotionItemIdList, List<String> sellerIdList,
//			List<String> itemCodeList) {

	@Override
	public SearchDataGrid<String> searchShopMobile(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId,
			List<String> boxRelationVenderIdList) {
		logger.info("come in searchShopMobile start");
		logger.info("addressCode:" + addressCode);
		logger.info("keyword:" + keyword);
		logger.info("sort:" + sort);
		logger.info("buyerId:" + buyerId);
		logger.info("boxRelationVenderIdList:" + boxRelationVenderIdList);
		SearchDataGrid<String> dg = new SearchDataGrid<String>();
		// 构建搜索条件
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		String queryStr = "";
		String filterStr = "";
		try {
			// 搜索店铺名称和关键字
			if (StringUtils.isNotEmpty(keyword)) {
				queryStr = "_text_:" + keyword;
			}else{
				queryStr = "*:*";
			}
			if (null != boxRelationVenderIdList
					&& boxRelationVenderIdList.size() > 0) {
				for (Object str : boxRelationVenderIdList) {
					filterStr += "sellerId:" + str + " OR ";
				}
				filterStr = filterStr.substring(0, filterStr.length() - 4);
			}
			filterStr = filterStr + " NOT sellerType:2";
			logger.info("queryStr:" + queryStr);
			logger.info("filterStr:" + filterStr);
			query.setQuery(queryStr);
			query.setFilterQueries(filterStr);
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
			logger.error("searchShopMobile is error", e);
		}
		logger.info("come in searchShopMobile end");
		return dg;
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
	 *            排序方式（默认：1，商品价格降序：2，商品价格升序：3，发布时间降序： 4，销量降序：5）
	 * @param buyerId
	 *            会员ID
	 * @param brandId
	 *            品牌ID
	 * @param categoryId
	 *            品类ID
	 * @param shopId
	 *            限制店铺ID
	 * @param belongRelationSellerIdList
	 *            存在归属关系卖家ID
	 * @param businessRelationSellerIdList
	 *            存在经营关系卖家ID列表(集合内部sellerId,brandId,CategoryId直接拼接，不需要逗号分隔)
	 * @param filterParam
	 *            筛选项目（属性code=值code，属性code=值code）逗号分隔
	 * @return
	 */
	@Override
	public SearchDataGrid<String> searchItemMobile(String addressCode,
			String keyword, Pager pager, Integer sort, Long buyerId,
			Long categoryId, Long brandId, Long shopId,
			Long belongRelationSellerId,
			List<String> businessRelationSellerIdList, boolean isAccessJD,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam,
			List<String> promotionItemIdList, List<String> sellerIdList,
			List<String> itemCodeList) {
		logger.info("come in searchItemMobile start");
		logger.info("addressCode:" + addressCode);
		logger.info("keyword:" + keyword);
		logger.info("sort:" + sort);
		logger.info("buyerId:" + buyerId);
		logger.info("categoryId:" + categoryId);
		logger.info("brandId:" + brandId);
		logger.info("shopId:" + shopId);
		logger.info("belongRelationSellerId:" + belongRelationSellerId);
		logger.info("businessRelationSellerIdList:"
				+ businessRelationSellerIdList);
		logger.info("isAccessJD:" + isAccessJD);
		logger.info("shieldCidAndBrandId:" + shieldCidAndBrandId);
		logger.info("filterParam:" + filterParam);
		logger.info("promotionItemIdList:" + promotionItemIdList);
		logger.info("sellerIdList:" + sellerIdList);
		logger.info("itemCodeList:" + itemCodeList);
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

		if (null != sellerIdList && sellerIdList.size() > 0) {
			String sellerIdStr = "";
			for (String str : sellerIdList) {
				sellerIdStr += str + " ";
			}
			qList.add("sellerId:(" + sellerIdStr + ")");
		}

		if (shopId != null) {
			String sellerType = "";
			String sellerIdStr = "";
			String sellerTypeAndSellerId = queryShopTypeAndSellerId(shopId);
			if (StringUtils.isNotEmpty(sellerTypeAndSellerId)) {
				sellerType = sellerTypeAndSellerId.split(":")[0];
				sellerIdStr = sellerTypeAndSellerId.split(":")[1];
				if ("2".equals(sellerType)) {
					logger.info("外部供应商店铺不展示任何商品:" + sellerIdStr);
					return null;
				}
			}
			if (categoryId != null && StringUtils.isNotEmpty(sellerType)) {
				qList.add("(cid:" + categoryId.longValue() + ")");
			}
			if (StringUtils.isEmpty(sellerIdStr) || "0".equals(sellerIdStr)) {
				logger.info("登陆状态下，店铺没有上架则不展示商品:" + sellerIdStr);
				return null;
			}
			qList.add("(shopId:" + shopId.longValue() + ")");
		} else {
			if (categoryId != null) {
				qList.add("(cid:" + categoryId.longValue() + ")");
			}
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
				addressSb.append("(((((shelvesFlag:3 OR shelvesFlag:4) AND ( "
						+ join(businessRelationSellerIdList)
						+ ")) OR shelvesFlag:1 OR shelvesFlag:2 ");
			} else {
				addressSb.append(" (((shelvesFlag:1 OR shelvesFlag:2");
			}
			addressSb.append(") AND (" + addressCode);
			addressSb.append(" OR isSalesWholeCountry:true NOT shelvesFlag:4)))");
			addressSb.append(" AND hasQuantity:true");
			filterList.add(addressSb.toString());
		} else {
			logger.info("该会员地址为空：" + addressCode);
			return null;
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
			if (null != promotionItemIdList && promotionItemIdList.size() > 0) {
				String promotionItemIdStr = "";
				for (String str : promotionItemIdList) {
					promotionItemIdStr += str + " ";
				}
				filterStr = filterStr + "NOT itemId:(" + promotionItemIdStr
						+ ")";
			}
			if (null != itemCodeList && itemCodeList.size() > 0) {
				String itemCodeStr = "";
				for (String str : itemCodeList) {
					itemCodeStr += str + " ";
				}
				filterStr = filterStr + "AND itemCode:(" + itemCodeStr + ")";
			}
			if(StringUtils.isEmpty(queryStr)){
				queryStr = "*:*";
			}
		}

		if (null != filterParam) {
			List<String> searchList = new ArrayList<String>();
			List<String> brandCodeList = filterParam.get("brandId");
			List<String> cidCodeList = filterParam.get("cid");
			List<String> attributeList = filterParam.get("attribute");
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
			if (null != attributeList && attributeList.size() > 0) {
				String atParam = "";
				for (String at : attributeList) {
					if (StringUtils.isNotEmpty(at)) {
						atParam += "attr_id: \"" + at + "\" OR ";
					}
				}
				if (!"".equals(atParam)) {
					searchList
							.add(" ("
									+ atParam.substring(0, atParam.length() - 4)
									+ ") ");
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
			// 默认无排序
		}
		if (sort == 2) {// 商品价格降序
			query.setSort("price", ORDER.desc);
		}
		if (sort == 3) {// 商品价格升序
			query.setSort("price", ORDER.asc);
		}
		if (sort == 4 || (null != sellerIdList && sellerIdList.size() > 0)) {// 发布时间降序
			query.setSort("listtingTime", ORDER.desc);
		}
		if (sort == 5) {// 销量降序
			query.setSort("salesVolume", ORDER.desc);
		}
		if (sort == 6) {//销量升序
			query.setSort("salesVolume", ORDER.asc);
		}

		// 获取查询筛选项
		Map<String, Object> screenMap = searchScreenMap(query);
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
			dg.setTotal(totalCount);
			List<Object> itemList = assemblingItemData(results,
					businessRelationSellerIdList, buyerId, sort, screenMap,
					isAccessJD);
			dg.setDataList(itemList);
			logger.info("商品查询条数：" + totalCount);
			dg.setScreenMap(screenMap);
		} catch (Exception e) {
			logger.error("searchItemMobile is error", e);
		}
		logger.info("come in searchItemMobile end");
		return dg;
	}

	private List<Object> assemblingItemData(SolrDocumentList results,
			List<String> businessRelationSellerIdList, Long buyerId,
			Integer sort, Map<String, Object> screenMap, boolean isAccessJD)
			throws ParseException, SolrServerException, IOException {
		if (null != sort) {
			if (sort.intValue() == 2) {
				sort = 6;
			}
			if (sort.intValue() == 3) {
				sort = 7;
			}
		}
		Map<String, String> priceMap = filterItemData(results,
				businessRelationSellerIdList, true);
		List<Object> itemList = SearchSolrItemAssembling
				.SolrItemDateAssembling(results, true,
						businessRelationSellerIdList, buyerId, sort, screenMap,
						null, isAccessJD, priceMap);
		return itemList;
	}
	
	private Map<String, String> filterItemData(SolrDocumentList results,
			List<String> businessRelationSellerIdList, boolean isLogin)
			throws SolrServerException, IOException {
		Map<String, String> priceMap = new HashMap<String, String>();
		List<String> itemIdList = new ArrayList<String>();
		if (null != results) {
			for (SolrDocument doc : results) {
				String shelvesFlag = doc.getFieldValue("shelvesFlag") == null ? null
						: doc.getFieldValue("shelvesFlag").toString();
				String vbc = doc.getFieldValue("vbc") == null ? null : doc
						.getFieldValue("vbc").toString();
				if (null != shelvesFlag && "2".equals(shelvesFlag)) {
					if (null != businessRelationSellerIdList && businessRelationSellerIdList.contains(vbc)) {
						String itemId = doc.getFieldValue("itemId") == null ? null
								: doc.getFieldValue("itemId").toString();
						if (null != itemId) {
							itemIdList.add(itemId + "3");
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
					QueryResponse queryResponse = itemSolrClient.query(q, METHOD.POST);
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

	private String joinShieldBrandAndCategory(List<String> shieldCidAndBrandId) {
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

	@SuppressWarnings("unchecked")
	private Map<String, Object> searchScreenMap(SolrQuery query) {
		Map<String, Object> screenMap = new LinkedHashMap<String, Object>();
		List<String> cidList = new ArrayList<String>();
		SolrQuery queryCrumbs = new SolrQuery();
		queryCrumbs = query;
		queryCrumbs.setFacet(true);
		queryCrumbs.addFacetField(new String[] { "brandNameScreen",
				"cidNameScreen", "cid" });
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
			}
			LinkedHashMap<String, List<String>> attributeMap = convertAttr(cidList);
			List<String> brandName = (List<String>) screenMap.get("brandName");
			List<String> cidName = (List<String>) screenMap.get("cidName");
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

	private List<Object> assemblingShopData(SolrDocumentList results) {
		List<Object> shopList = new ArrayList<Object>();
		for (SolrDocument doc : results) {
			ShopData shopData = new ShopData();
			String shopId = doc.getFieldValue("shopId") == null ? null : doc
					.getFieldValue("shopId").toString();
			shopData.setShopId(shopId);
			shopData.setShopName(doc.getFieldValue("shopName") == null ? null
					: doc.getFieldValue("shopName").toString());
			String cidNames = doc.getFieldValue("cids") == null ? null : doc
					.getFieldValue("cids").toString();
			if(StringUtils.isNotEmpty(cidNames)){
				String cids[] = cidNames.split(",");
				String cidStr = "";
				for (int i = 0; i < cids.length; i++) {
					cidStr += cids[i].split(":")[0] + ",";
				}
				shopData.setCidNames(cidStr.substring(0, cidStr.length() - 1));
			}
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

	@Override
	public List<Object> searchNewItemMobile(String query, String filterQuery,
			List<String> businessRelationSellerIdList, Long buyerId) {
		logger.info("come in searchNewItemMobile start");
		logger.info("searchNewItemMobile query:" + query);
		// 构建搜索条件
		List<Object> itemList = new ArrayList<Object>();
		SolrQuery q = new SolrQuery();
		q.setRequestHandler("/select");
		if (StringUtils.isNotEmpty(query)) {
			try {
				q.setQuery(query);
				q.setFilterQueries(filterQuery);
				q.addSort("listtingTime", ORDER.desc);
				q.setRows(4);
				QueryResponse queryResponse = itemSolrClient.query(q,
						METHOD.POST);
				SolrDocumentList results = queryResponse.getResults();
				itemList = assemblingItemData(results,
						businessRelationSellerIdList, buyerId, null, null,
						false);
				logger.info("新品商品查询条数：" + itemList.size());
			} catch (Exception e) {
				logger.error("searchNewItemMobile is error", e);
				return null;
			}
		}
		logger.info("come in searchNewItemMobile end");
		return itemList;
	}

	@Override
	public List<Object> searchPopularityItemMobile(String query,
			String filterQuery, List<String> businessRelationSellerIdList,
			Long buyerId, List<Object> newItemList) {
		logger.info("come in searchPopularityItemMobile start");
		logger.info("searchPopularityItemMobile query:" + query);
		// 构建搜索条件
		List<Object> itemList = new ArrayList<Object>();
		SolrQuery q = new SolrQuery();
		q.setRequestHandler("/select");
		if (StringUtils.isNotEmpty(query)) {
			if (null != newItemList && newItemList.size() > 0) {
				String filterNewItemStr = "";
				for (Object object : newItemList) {
					if (object instanceof ItemData) {
						ItemData data = (ItemData) object;
						filterNewItemStr += data.getId() + " ";
					}
				}
				if (!"".equals(filterNewItemStr)) {
					filterQuery = filterQuery + "NOT id:(" + filterNewItemStr
							+ ")";
				}
			}
			try {
				q.setQuery(query);
				q.setFilterQueries(filterQuery);
				q.addSort("salesVolume", ORDER.desc);
				q.setRows(4);
				QueryResponse queryResponse = itemSolrClient.query(q,
						METHOD.POST);
				SolrDocumentList results = queryResponse.getResults();
				itemList = assemblingItemData(results,
						businessRelationSellerIdList, buyerId, null, null,
						false);
				logger.info("人气商品查询条数：" + itemList.size());
			} catch (Exception e) {
				logger.error("searchPopularityItemMobile is error", e);
				return null;
			}
		}
		logger.info("come in searchPopularityItemMobile end");
		return itemList;
	}

	@Override
	public SearchDataGrid<String> searchScreenMobile(String addressCode,
			String keyword, Long buyerId, Long shopId,
			Long belongRelationSellerId,
			List<String> businessRelationSellerIdList, boolean isAccessJD,
			List<String> shieldCidAndBrandId,
			Map<String, List<String>> filterParam) {
		logger.info("come in searchScreenMobile start");
		logger.info("addressCode:" + addressCode);
		logger.info("keyword:" + keyword);
		logger.info("buyerId:" + buyerId);
		logger.info("shopId:" + shopId);
		logger.info("belongRelationSellerId:" + belongRelationSellerId);
		logger.info("businessRelationSellerIdList:"
				+ businessRelationSellerIdList);
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

		if (shopId != null) {
			String sellerType = "";
			String sellerIdStr = "";
			String sellerTypeAndSellerId = queryShopTypeAndSellerId(shopId);
			if (StringUtils.isNotEmpty(sellerTypeAndSellerId)) {
				sellerType = sellerTypeAndSellerId.split(":")[0];
				sellerIdStr = sellerTypeAndSellerId.split(":")[1];
				if ("2".equals(sellerType)) {
					logger.info("外部供应商店铺不展示任何商品:" + sellerIdStr);
					return null;
				}
				if (StringUtils.isNotEmpty(sellerIdStr)
						&& null != belongRelationSellerId) {
					Long sellerId = Long.parseLong(sellerIdStr);
					if (sellerId.longValue() != belongRelationSellerId
							.longValue()) {
						isAccessJD = false;
					}
				}
			}
			StringBuffer shopSb = new StringBuffer();
			if (StringUtils.isEmpty(sellerIdStr) || "0".equals(sellerIdStr)) {
				logger.info("登陆状态下，店铺没有上架则不展示商品:" + sellerIdStr);
				return null;
			}
			shopSb.append("(shopId:" + shopId.longValue());
			if (isAccessJD) {
				shopSb.append(" OR (itemType:2"
						+ joinShieldBrandAndCategory(shieldCidAndBrandId) + ")");
			} else {
				shopSb.append(" (NOT itemType:2)");
			}
			shopSb.append(")");
			qList.add(shopSb.toString());
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
				addressSb.append("(((((shelvesFlag:3 OR shelvesFlag:4) AND ( "
						+ join(businessRelationSellerIdList)
						+ ")) OR shelvesFlag:1 ");
			} else {
				addressSb.append(" (((shelvesFlag:1 OR shelvesFlag:2");
			}
			addressSb.append(") AND " + addressCode);
			addressSb.append(") OR ");
			addressSb.append("(((shelvesFlag:1 OR shelvesFlag:2)");
			if (isAccessJD) {
				addressSb.append(" OR (itemType:2 "
						+ joinShieldBrandAndCategory(shieldCidAndBrandId));
			} else {
				addressSb.append(" (NOT itemType:2 ");
			}
			addressSb.append(")) AND  isSalesWholeCountry:true)) ");
			filterList.add(addressSb.toString());
		} else {
			logger.info("该会员地址为空：" + addressCode);
			return null;
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
			List<String> brandCodeList = filterParam.get("brandId");
			List<String> cidCodeList = filterParam.get("cid");
			List<String> attributeList = filterParam.get("attribute");
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
			if (null != attributeList && attributeList.size() > 0) {
				String atParam = "";
				for (String at : attributeList) {
					if (StringUtils.isNotEmpty(at)) {
						atParam += "attr_id: \"" + at + "\" OR ";
					}
				}
				if (!"".equals(atParam)) {
					searchList
							.add(" ("
									+ atParam.substring(0, atParam.length() - 4)
									+ ") ");
				}
			}
			if (searchList.size() > 0) {
				for (int i = 0; i < searchList.size(); i++) {
					filterStr += " AND " + searchList.get(i);
				}
			}
		}
		if (StringUtils.isEmpty(queryStr)) {
			queryStr = "*:*";
		}
		query.setQuery(queryStr);
		query.setFilterQueries(filterStr);
		logger.info("queryStr:" + query.getQuery());
		logger.info("filterStr:" + query.getFilterQueries()[0]);
		try {
			// 获取查询筛选项
			Map<String, Object> screenMap = searchScreenMap(query);
			dg.setScreenMap(screenMap);
		} catch (Exception e) {
			logger.error("searchScreenMobile is error", e);
		}
		logger.info("come in searchScreenMobile end");
		return dg;
	}
}

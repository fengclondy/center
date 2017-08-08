package cn.htd.searchcenter.dump.job.assembling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.htd.searchcenter.domain.ItemDTO;
import cn.htd.searchcenter.dump.domain.ItemSearchData;
import cn.htd.searchcenter.service.ItemDictionaryService;
import cn.htd.searchcenter.service.ItemExportService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service("itemDumpJobAssembling")
public class ItemDumpJobAssembling {

	private static final Logger LOG = Logger
			.getLogger(ItemDumpJobAssembling.class.getName());

	@Resource(name = "itemMasterHttpSolrServer")
	private HttpSolrClient cloudSolrClient;
	@Resource
	private ItemExportService ItemExportService;
	@Resource
	private ItemDictionaryService itemDictionaryService;

	/**
	 * 删除solr数据
	 * 
	 * @param itemId
	 * @param itemType
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public boolean removeSolrData(Long itemId, Long itemType)
			throws SolrServerException, IOException {
		boolean ret = true;
		if (null != itemId && null != itemType) {
			cloudSolrClient.deleteByQuery("itemId:" + itemId + " AND itemType:"
					+ itemType);
			cloudSolrClient.commit();
		} else {
			ret = false;
		}
		return ret;
	}

	public String removalAttrData(String str) throws Exception {
		if (StringUtils.isNotEmpty(str)) {
			String attr = "";
			String[] strs = str.split(",");
			List<String> strList = Arrays.asList(strs);
			HashSet<String> h = new HashSet<String>(strList);
			strList = new ArrayList<String>();
			strList.addAll(h);
			List<String> resetList = new ArrayList<String>();
			for (String s : strList) {
				resetList.add(s);
			}
			for (int i = 0; i < resetList.size(); i++) {
				int index = resetList.get(i).indexOf(":");
				if (index == 0 || index == -1
						|| index == (resetList.get(i).length() - 1)) {
					strList.remove(i);
					continue;
				}
				attr += resetList.get(i) + ",";
			}
			return attr.substring(0, attr.length() - 1);
		} else {
			return "";
		}
	}

	public void flush(List<SolrInputDocument> itemAttrSolrList)
			throws SolrServerException, IOException {
		if (itemAttrSolrList.size() > 0) {
			UpdateRequest req = new UpdateRequest();
			req.add(itemAttrSolrList);
			req.setCommitWithin(10000);
			req.process(cloudSolrClient);
		}
	}

	public List<SolrInputDocument> searchSolr(Long itemId, Long itemType) {
		List<SolrInputDocument> results = new ArrayList<SolrInputDocument>();

		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");

		String q = "";
		if (null != itemId && null != itemType) {
			q = "itemId:" + itemId + " AND itemType:" + itemType;
		} else {
			LOG.error("searchSolr itemId is null or itemType is null");
		}

		query.setQuery(q);
		query.setRows(10000);
		QueryResponse Query = null;
		try {
			Query = cloudSolrClient.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SolrDocumentList solrDocumentList = Query.getResults();
		for (SolrDocument solrDocument : solrDocumentList) {
			results.add(ClientUtils.toSolrInputDocument(solrDocument));
		}
		return results;
	}

	public void solrDataImport(List<ItemDTO> list, Date lastSyncTime)
			throws Exception {
		if (list.size() == 0) {
			LOG.info("no data for item");
		} else {
			List<SolrInputDocument> itemSolrList = new ArrayList<SolrInputDocument>();
			// 拼装商品信息
			List<ItemSearchData> itemList = itemDataAssembling(list,
					lastSyncTime);
			LOG.info("导入商品信息");
			for (ItemSearchData data : itemList) {
				LOG.info("itemId : " + data.getItemId());
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", data.getId());
				doc.addField("itemId", data.getItemId());
				doc.addField("itemName", data.getItemName());
				doc.addField("itemCode", data.getItemCode());
				doc.addField("sellerId", data.getSellerId());
				doc.addField("brandId", data.getBrandId());
				doc.addField("cid", data.getCid());
				doc.addField("shopCid", data.getShopCid());
				doc.addField("vbc", data.getVbc());
				doc.addField("cidName", data.getCidName());
				doc.addField("cidNameScreen", data.getCidNameScreen());
				doc.addField("brandName", data.getBrandName());
				doc.addField("brandNameScreen", data.getBrandNameScreen());
				doc.addField("productChannelCode", data.getProductChannelCode());
				doc.addField("describeContent", data.getDescribeContent());
				doc.addField("salesVolume", data.getSalesVolume());
				doc.addField("isSalesWholeCountry",
						data.getIsSalesWholeCountry());
				doc.addField("itemType", data.getItemType());
				doc.addField("listtingTime", data.getListtingTime());
				doc.addField("quantity", data.getQuantity());
				doc.addField("price", data.getPrice());
				doc.addField("areaCode", data.getAreaCode());
				doc.addField("unLoginPrice", data.getUnLoginPrice());
				doc.addField("shopId", data.getShopId());
				doc.addField("attrAll", data.getAttrAll());
				doc.addField("attr_id", data.getAttr_id());
				doc.addField("sellerName", data.getSellerName());
				doc.addField("sellerNameScreen", data.getSellerNameScreen());
				doc.addField("sellerType", data.getSellerType());
				doc.addField("imgURL", data.getImgURL());
				doc.addField("shieldCidAndBrandId",
						data.getShieldCidAndBrandId());
				doc.addField("hasQuantity", data.getHasQuantity());
				doc.addField("shelvesFlag", data.getShelvesFlag());
				if(data.getItemType().intValue() == 3L){
					doc.addField("publicSaleWholeCountryFlag", data.getPublicSaleWholeCountryFlag());
				}
				if (!"".equals(data.getHotWord())) {
					doc.addField("hotWord", data.getHotWord(), 8.0f);
				}
				itemSolrList.add(doc);
			}
			flush(itemSolrList);
		}
	}

	private boolean filterInvalidData(ItemDTO item) throws Exception {
		boolean filterFlag = true;
		Long itemType = 999L;
		if ("10".equals(item.getProductChannelCode())
				&& null != item.getAreaDisplayQuantity()) {
			itemType = 4L;
		}
		// 包厢商品
		if ("10".equals(item.getProductChannelCode())
				&& null != item.getBoxDisplayQuantity()) {
			itemType = 3L;
		}
		// 外部商品
		if ("20".equals(item.getProductChannelCode())) {
			itemType = 1L;
		}
		// 京东商品
		if ("3010".equals(item.getProductChannelCode())) {
			itemType = 2L;
		}
		// 秒杀商品
		if (item.getTimelimitedSkuCount() != null
				&& item.getTimelimitedSkuCount() > 0) {
			itemType = 5L;
		}
		if (itemType.longValue() != 999) {
			removeSolrData(item.getItemId(), itemType.longValue());
			if (itemType.longValue() == 2 || itemType.longValue() == 1) {
				boolean isVisable = itemDictionaryService.queryItemStatus(item
						.getItemId());
				if (!isVisable) {
					filterFlag = false;
				}
			}
			if (itemType.longValue() == 3) {
				boolean isVisable = itemDictionaryService.queryItemVisable(
						item.getItemId(), 1);
				if (!isVisable) {
					filterFlag = false;
				}
			}
			if (itemType.longValue() == 4) {
				boolean isVisable = itemDictionaryService.queryItemVisable(
						item.getItemId(), 0);
				if (!isVisable) {
					filterFlag = false;
				}
			}
			if (itemType.longValue() == 5) {
				boolean isVisable = itemDictionaryService
						.querySeckillItemStatus(item.getItemId());
				if (!isVisable) {
					filterFlag = false;
				}
			}
		} else {
			filterFlag = false;
		}
		return filterFlag;
	}

	public List<ItemSearchData> itemDataAssembling(List<ItemDTO> list,
			Date lastSyncTime) {
		List<ItemSearchData> results = new ArrayList<ItemSearchData>();
		for (ItemDTO item : list) {
			try {
				if (null == item.getItemId()
						|| StringUtils.isEmpty(item.getItemName())
						|| StringUtils.isEmpty(item.getItemCode())) {
					continue;
				}
				boolean filterFlag = filterInvalidData(item);
				if (!filterFlag) {
					continue;
				}
				ItemSearchData data = new ItemSearchData();
				data.setItemId(item.getItemId());
				data.setItemName(item.getItemName());
				data.setItemCode(item.getItemCode());
				data.setSellerId(item.getSellerId());
				data.setShopId(item.getShopId());
				data.setCid(item.getCid());
				data.setShopCid(item.getShopCid());
				data.setBrandId(item.getBrandId());
				data.setProductChannelCode(item.getProductChannelCode());
				// 商品品类名称
				String cidName = itemDictionaryService.queryCidNameByCid(item
						.getCid());
				if (null == cidName) {
					continue;
				} else {
					data.setCidName(cidName);
					data.setCidNameScreen(cidName + ":" + item.getCid());
				}
				// 商品品牌名称
				String brandName = itemDictionaryService
						.queryBrandNameByBrandId(item.getBrandId());
				if (null == brandName) {
					continue;
				} else {
					data.setBrandName(brandName);
					data.setBrandNameScreen(brandName + ":" + item.getBrandId());
				}
				// 商品图片
				String pictureURL = itemDictionaryService.queryItemPicture(item
						.getItemId());
				data.setImgURL(pictureURL);
				// 商品销量
				long salesVolume = itemDictionaryService
						.querySalesVolumeByItemCode(item.getItemCode());
				data.setSalesVolume(salesVolume);
				// 商品描述
				String describe = itemDictionaryService
						.queryDescribeByItemId(item.getItemId());
				data.setDescribeContent(describe);
				// 商品属性拼装
				String[] attr_id = itemAttrAssembling(item.getAttr_id());
				data.setAttr_id(attr_id);
				if(!"3010".equals(item.getProductChannelCode())){
					// 获取供应商名称
					String sellerName = itemDictionaryService.querySellerName(item
							.getSellerId());
					data.setSellerName(sellerName);
					data.setSellerNameScreen(item.getSellerId() + ":" + sellerName);
					// 获取供应商类型
					String sellerType = itemDictionaryService
							.querySellerTypeById(item.getSellerId());
					data.setSellerType(sellerType);
				}
				// 获取热搜关键词
				String hotword = itemDictionaryService
						.queryHotWordByItemCode(item.getItemCode());
				data.setVbc(String.valueOf(item.getSellerId())
						+ String.valueOf(item.getBrandId())
						+ String.valueOf(item.getCid()));
				data.setHotWord(hotword);
				// 大厅商品
				if ("10".equals(item.getProductChannelCode())
						&& null != item.getAreaDisplayQuantity()) {
					// 是否销售全国
					boolean isSalesWholeCountry = itemDictionaryService
							.queryIsSalesWholeCountry(item.getItemId(), 0);
					int shelvesFlag = itemDictionaryService
							.queryItemVisableCount(item.getItemId(), 0);
					// 获取销售区域code
					if (!isSalesWholeCountry) {
						String[] areaCodes = saleAreas(item.getItemId(), 0);
						data.setAreaCode(areaCodes);
					}
					data.setId(String.valueOf(item.getItemId())
							+ String.valueOf(4L));
					data.setItemType(4L);
					data.setShelvesFlag(shelvesFlag);
					data.setIsSalesWholeCountry(isSalesWholeCountry);
					data.setPrice(item.getAreaSalePrice());
					data.setUnLoginPrice(item.getRetailPrice());
					data.setQuantity(item.getAreaDisplayQuantity());
					data.setListtingTime(item.getListtingTime());
					afterAddResult(results, data);
				}
				// 包厢商品
				if ("10".equals(item.getProductChannelCode())
						&& item.getBoxDisplayQuantity() != null) {
					// 是否销售全国
					boolean isSalesWholeCountry = itemDictionaryService
							.queryIsSalesWholeCountry(item.getItemId(), 1);
					int shelvesFlag = itemDictionaryService
							.queryItemVisableCount(item.getItemId(), 1);
					int publicSaleWholeCountryFlag = itemDictionaryService.queryIsPublicSaleWholeCountry(item.getItemId(), 0);
					// 获取销售区域code
					if(!isSalesWholeCountry){
						String[] areaCodes = saleAreas(item.getItemId(), 1);
						data.setAreaCode(areaCodes);
					}
					data.setId(String.valueOf(item.getItemId())
							+ String.valueOf(3L));
					data.setItemType(3L);
					data.setShelvesFlag(shelvesFlag);
					data.setIsSalesWholeCountry(isSalesWholeCountry);
					data.setPublicSaleWholeCountryFlag(publicSaleWholeCountryFlag);
					data.setPrice(item.getBoxSalePrice());
					data.setUnLoginPrice(item.getRetailPrice());
					data.setQuantity(item.getBoxDisplayQuantity());
					data.setListtingTime(item.getListtingTime());
					afterAddResult(results, data);
				}
				// 外部商品
				if ("20".equals(item.getProductChannelCode())) {
					BigDecimal exterPrice = itemDictionaryService
							.queryExternalItemPrice(item.getItemId());
					if (null == exterPrice) {
						continue;
					}
					data.setId(String.valueOf(item.getItemId())
							+ String.valueOf(1L));
					data.setItemType(1L);
					data.setIsSalesWholeCountry(true);
					data.setPrice(exterPrice);
					data.setUnLoginPrice(exterPrice);
					data.setQuantity(item.getAreaDisplayQuantity());
					data.setListtingTime(item.getListtingTime());
					afterAddResult(results, data);
				}
				// 京东商品
				if ("3010".equals(item.getProductChannelCode())) {
					data.setId(String.valueOf(item.getItemId())
							+ String.valueOf(2L));
					data.setItemType(2L);
					data.setSellerName("");
					data.setIsSalesWholeCountry(true);
					data.setPrice(item.getAreaSalePrice());
					data.setUnLoginPrice(item.getRetailPrice());
					data.setQuantity(9999L);
					data.setShieldCidAndBrandId(data.getCid() + ":"
							+ data.getBrandId());
					data.setListtingTime(item.getListtingTime());
					afterAddResult(results, data);
				}
				// 秒杀商品
				if (item.getTimelimitedSkuCount() != null
						&& item.getTimelimitedSkuCount() > 0) {
					data.setId(String.valueOf(item.getItemId())
							+ String.valueOf(0L));
					data.setItemType(5L);
					data.setIsSalesWholeCountry(true);
					data.setPrice(item.getSkuTimelimitedPrice());
					data.setUnLoginPrice(item.getSkuTimelimitedPrice());
					data.setQuantity(item.getTimelimitedSkuCount());
					data.setListtingTime(item.getEffectiveTime());
					afterAddResult(results, data);
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	private String[] itemAttrAssembling(String attrId) throws JsonParseException,
			JsonMappingException, IOException {
		String attr_id[] = null;
		try {
				if (StringUtils.isNotEmpty(attrId)) {
					if (!attrId.startsWith("{") && !attrId.endsWith("}")) {
						attrId = "{" + attrId + "}";
					}
					Map<String, Object> map = JSONObject.fromObject(attrId);
					List<String> commlist = new ArrayList<String>();
					if (null != map && !map.isEmpty()) {
						for (String key : map.keySet()) {
							Object attributeValueObj = map.get(key);
							if (attributeValueObj instanceof Integer) {
								if (null != attributeValueObj
										&& !"".equals(attributeValueObj)) {
									commlist.add(String
											.valueOf(attributeValueObj));
								}
							} else if (attributeValueObj instanceof JSONArray) {
								List<Integer> jsonArray = (List<Integer>) attributeValueObj;
								for (Integer intVal : jsonArray) {
									if (null != intVal
											&& intVal.intValue() != 0) {
										commlist.add(String.valueOf(intVal));
									}
								}
							}
						}
					}
					int size = commlist.size();
					attr_id = (String[]) commlist
							.toArray(new String[size]);
			}
		} catch (Exception e) {
			return new String[0];
		}
		return attr_id;
	}

	private void afterAddResult(List<ItemSearchData> itemSearchDataList,
			ItemSearchData data) throws Exception {
		LOG.info("id:" + data.getId());
		LOG.info("itemId:" + data.getItemId());
		LOG.info("sellerId:" + data.getSellerId());
		LOG.info("itemName:" + data.getItemName());
		LOG.info("listtingTime:" + data.getListtingTime());
		ItemSearchData newData = new ItemSearchData();
		ClassReflection.reflectionAttr(data, newData);
		if (newData.getQuantity() > 0) {
			newData.setHasQuantity(true);
		} else {
			newData.setHasQuantity(false);
		}
		itemSearchDataList.add(newData);
	}

	private String[] saleAreas(Long itemId, int isBox) throws Exception {
		List<String> saleAreas = itemDictionaryService.querySalesAreaByItemId(
				itemId, isBox);
		if (null != saleAreas) {
			Map<String, Integer> areaProvinceMap = new HashMap<String, Integer>();
			for (int i = 0; i < saleAreas.size(); i++) {
				if (saleAreas.get(i).length() == 2) {
					areaProvinceMap.put(saleAreas.get(i), i);
				}
			}
			List<Integer> indexList = new ArrayList<Integer>();
			for (int i = 0, len = saleAreas.size(); i < len; i++) {
				if (saleAreas.get(i).length() > 2) {
					String province = saleAreas.get(i).substring(0, 2);
					Integer index = areaProvinceMap.get(province);
					if (null != index) {
						indexList.add(index);
						areaProvinceMap.remove(province);
					}
				}
			}
			if (indexList.size() > 0) {
				Collections.sort(indexList);
				for (int i = indexList.size() - 1; i >= 0; i--) {
					saleAreas.remove(indexList.get(i).intValue());
				}
			}
			List<String> list2 = new ArrayList<String>();
			for (int i = 0; i < saleAreas.size(); i++) {
				if (saleAreas.get(i).length() == 6) {
					list2.add(saleAreas.get(i).substring(0, 4));
				} else {
					list2.add(saleAreas.get(i));
				}
			}
			HashSet<String> h = new HashSet<String>(list2);
			list2.clear();
			list2.addAll(h);
			int size = list2.size();
			String[] value = (String[]) list2.toArray(new String[size]);
			return value;
		} else {
			return new String[0];
		}
	}
}

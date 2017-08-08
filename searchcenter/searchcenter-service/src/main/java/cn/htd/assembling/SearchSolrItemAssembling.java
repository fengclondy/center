package cn.htd.assembling;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import cn.htd.searchcenter.searchData.ItemData;

public class SearchSolrItemAssembling {
	/**
	 * JD商品拼装
	 * 
	 * @param itemData
	 * @param doc
	 * @param belongRelationships
	 * @param buyerGrade
	 * @param isLogin
	 * @param belongRelationSellerIdAndName
	 * @param screenMap
	 * @param isAccessJD
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ItemData assemblingJDItem(boolean isLogin,
			String belongRelationships, SolrDocument doc, ItemData itemData,
			Map<String, Object> screenMap,
			String belongRelationSellerIdAndName, boolean isAccessJD) {
		itemData.setVBC(true);
		itemData.setItemType(2);
		if (isLogin) {
			itemData.setPrice(Double.parseDouble(doc.getFieldValue("price")
					.toString()));
			if (isAccessJD && null != screenMap) {
				LinkedHashMap<String, List<String>> attributeMap = (LinkedHashMap<String, List<String>>) screenMap
						.get("attribute");
				List<String> sellerNameList = attributeMap.get("sellerName");
				if (null == sellerNameList || sellerNameList.size() == 0) {
					List<String> nameList = new ArrayList<String>();
					nameList.add(belongRelationSellerIdAndName);
					((LinkedHashMap<String, List<String>>) screenMap
							.get("attribute")).put("sellerName", nameList);
				} else {
					if (!sellerNameList.contains(belongRelationSellerIdAndName)) {
						((LinkedHashMap<String, List<String>>) screenMap
								.get("attribute")).get("sellerName").add(
								belongRelationSellerIdAndName);
					}
				}
			}
		} else {
			itemData.setSellerName("");
			itemData.setPrice(Double.parseDouble(doc.getFieldValue(
					"unLoginPrice").toString()));
		}
		return itemData;
	}

	/**
	 * 大厅商品拼装
	 * 
	 * @param isLogin
	 * @param belongStr
	 * @param buyerGrade
	 * @param belongRelationships
	 * @param doc
	 * @param itemScreenList
	 * @param itemData
	 * @return
	 */
	public static ItemData assemblingPublicItem(boolean isLogin,
			String belongRelationships, SolrDocument doc, ItemData itemData,
			Map<String, String> priceMap) {
		itemData.setVBC(true);
		itemData.setItemType(4);
		if (isLogin) {
			if (null != priceMap && !priceMap.isEmpty()) {
				for (String itemId : priceMap.keySet()) {
					if (itemId.equals(itemData.getItemId())) {
						itemData.setItemType(3);
						itemData.setPrice(Double.parseDouble(priceMap
								.get(itemId)));
						itemData.setVBC(true);
						return itemData;
					}
				}
			}
			itemData.setPrice(Double.parseDouble(doc.getFieldValue("price")
					.toString()));
		} else {
			itemData.setPrice(Double.parseDouble(doc.getFieldValue(
					"unLoginPrice").toString()));
		}
		return itemData;
	}

	/**
	 * 包厢商品拼装
	 * 
	 * @param isLogin
	 * @param belongStr
	 * @param buyerGrade
	 * @param belongRelationships
	 * @param doc
	 * @param boxRelationSellerIdList
	 * @param itemData
	 * @return
	 */
	public static ItemData assemblingBoxItem(boolean isLogin,
			String belongRelationships, SolrDocument doc, Long buyerId,
			ItemData itemData, Map<String, String> priceMap) {
		itemData.setItemType(3);
		if (isLogin) {
			if (null != priceMap && !priceMap.isEmpty()) {
				for (String itemId : priceMap.keySet()) {
					if (itemId.equals(itemData.getItemId())) {
						itemData.setItemType(4);
						itemData.setPrice(Double.parseDouble(priceMap
								.get(itemId)));
						itemData.setVBC(true);
						return itemData;
					}
				}
			}
			if (!itemData.isVBC()) {
				itemData.setPrice(Double.parseDouble(doc.getFieldValue(
						"unLoginPrice").toString()));
				itemData.setVBC(false);
			} else {
				itemData.setPrice(Double.parseDouble(doc.getFieldValue("price")
						.toString()));
				itemData.setVBC(true);
			}
		} else {
			itemData.setPrice(Double.parseDouble(doc.getFieldValue(
					"unLoginPrice").toString()));
			itemData.setVBC(true);
		}
		return itemData;
	}

	@SuppressWarnings("unchecked")
	public static List<Object> SolrItemDateAssembling(SolrDocumentList results,
			boolean isLogin, List<String> businessRelationSellerIdList,
			Long buyerId, Integer sort, Map<String, Object> screenMap,
			String belongRelationSellerIdAndName, boolean isAccessJD,
			Map<String, String> priceMap) throws ParseException {
		List<ItemData> itemList = new ArrayList<ItemData>();
		for (SolrDocument doc : results) {
			ItemData itemData = new ItemData();
			int itemType = Integer.parseInt(doc.getFieldValue("itemType")
					.toString());
			String id = doc.getFieldValue("id").toString();
			String belongRelationships = doc
					.getFieldValue("belongRelationships") == null ? "" : doc
					.getFieldValue("belongRelationships").toString();
			String sellerId = doc.getFieldValue("sellerId").toString();
			itemData.setId(id);
			itemData.setVBC(false);
			itemData.setSellerId(sellerId);
			itemData.setItemId(doc.getFieldValue("itemId").toString());
			itemData.setItemCode(doc.getFieldValue("itemCode") == null ? null
					: doc.getFieldValue("itemCode").toString());
			itemData.setShopId(doc.getFieldValue("shopId") == null ? "0" : doc
					.getFieldValue("shopId").toString());
			itemData.setItemName(doc.getFieldValue("itemName").toString());
			itemData.setSalesVolume(Integer.parseInt(doc
					.getFieldValue("salesVolume") == null ? "0" : doc
					.getFieldValue("salesVolume").toString()));
			itemData.setSalesWholeCountry(Boolean.parseBoolean(doc
					.getFieldValue("isSalesWholeCountry") == null ? "false"
					: doc.getFieldValue("isSalesWholeCountry").toString()));
			itemData.setImgURL(doc.getFieldValue("imgURL") == null ? "" : doc
					.getFieldValue("imgURL").toString());
			itemData.setSellerName(doc.getFieldValue("sellerName") == null ? ""
					: doc.getFieldValue("sellerName").toString());
			itemData.setListtingTime(doc.getFieldValue("listtingTime") == null ? null
					: (Date) doc.getFieldValue("listtingTime"));
			if (isLogin) {
				if (null != businessRelationSellerIdList
						&& businessRelationSellerIdList.size() > 0) {
					String brandId = doc.getFieldValue("brandId") == null ? null
							: doc.getFieldValue("brandId").toString();
					String cid = doc.getFieldValue("cid") == null ? null : doc
							.getFieldValue("cid").toString();
					String vbc = doc.getFieldValue("vbc") == null ? null : doc
							.getFieldValue("vbc").toString();
					if (null != brandId && null != cid && null != vbc) {
						for (String str : businessRelationSellerIdList) {
							if (vbc.equals(str)) {
								itemData.setVBC(true);
							}
						}
					}
				}
			}
			// 外部商品，秒杀商品
			if (itemType == 1 || itemType == 5) {
				itemData.setVBC(true);
				itemData.setSalesWholeCountry(true);
				itemData.setPrice(Double.parseDouble(doc.getFieldValue("price")
						.toString()));
				if (itemType == 1) {
					itemData.setItemType(1);
				} else {
					itemData.setItemType(5);
				}
				itemList.add(itemData);
				continue;
			}

			// 京东商品
			if (itemType == 2) {
				ItemData itemJD = assemblingJDItem(isLogin,
						belongRelationships, doc, itemData, screenMap,
						belongRelationSellerIdAndName, isAccessJD);
				if (null != itemJD) {
					itemList.add(itemJD);
				}
				continue;
			}

			// 包厢商品
			if (itemType == 3) {
				ItemData itemBox = assemblingBoxItem(isLogin,
						belongRelationships, doc, buyerId, itemData, priceMap);
				if (null != itemBox) {
					itemList.add(itemBox);
				}
				continue;
			}

			// 大厅商品
			if (itemType == 4) {
				ItemData itemPublic = assemblingPublicItem(isLogin,
						belongRelationships, doc, itemData, priceMap);
				if (null != itemPublic) {
					itemList.add(itemPublic);
				}
				continue;
			}
		}
		SearchSortList<ItemData> sortItem = new SearchSortList<ItemData>();
		if (null != sort) {
			if (sort.intValue() == 6) {
				sortItem.sortByMethod(itemList, "getPrice", true);
			} else if (sort.intValue() == 7) {
				sortItem.sortByMethod(itemList, "getPrice", false);
			}
		}
		List<Object> returnItem = (List) itemList;
		return returnItem;
	}
}

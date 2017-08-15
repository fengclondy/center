package cn.htd.searchcenter.dump.job.assembling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.htd.searchcenter.domain.MemberCompanyInfoDTO;
import cn.htd.searchcenter.domain.ShopDTO;
import cn.htd.searchcenter.dump.domain.ShopSearchData;
import cn.htd.searchcenter.service.MemberCompanyInfoService;
import cn.htd.searchcenter.service.ShopExportService;

@Service("shopDumpJobAssembling")
public class ShopDumpJobAssembling {
	private static final Logger LOG = Logger
			.getLogger(ShopDumpJobAssembling.class.getName());

	@Resource(name = "shopMasterHttpSolrServer")
	private HttpSolrClient cloudSolrClient;

	@Resource
	private ShopExportService shopExportService;
	@Resource
	private MemberCompanyInfoService memberCompanyInfoService;

	public String removalData(String cid) {
		if (StringUtils.isNotEmpty(cid)) {
			String cidStr = "";
			String[] cids = cid.split(",");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < cids.length; i++) {
				list.add(cids[i]);
			}
			HashSet<String> h = new HashSet<String>(list);
			list.clear();
			list.addAll(h);
			for (int i = 0; i < list.size(); i++) {
				if (i == 2) {
					cidStr += list.get(i) + ",";
					break;
				} else {
					cidStr += list.get(i) + ",";
				}
			}
			return cidStr.substring(0, cidStr.length() - 1);
		} else {
			return "";
		}
	}

	private boolean removeSolr(String shopId) {
		boolean ret = true;
		if (StringUtils.isNotEmpty(shopId)) {
			try {
				cloudSolrClient.deleteById(shopId);
				cloudSolrClient.commit();
			} catch (Exception e) {
				ret = false;
				e.printStackTrace();
			}
		} else {
			ret = false;
		}
		return ret;
	}

	public void flush(List<SolrInputDocument> shopSolrList)
			throws SolrServerException, IOException {
		if (shopSolrList.size() > 0) {
			UpdateRequest req = new UpdateRequest();
			req.add(shopSolrList);
			req.setCommitWithin(10000);
			req.process(cloudSolrClient);
		}
	}

	public void solrDataImport(List<ShopDTO> list, Date lastSyncTime)
			throws Exception {
		if (list.size() == 0) {
			LOG.info("no data for shop");
		} else {
			List<SolrInputDocument> shopSolrList = new ArrayList<SolrInputDocument>();
			// 拼装店铺信息
			List<ShopSearchData> shopList = shopDataAssembling(list,
					lastSyncTime);
			LOG.info("导入店铺信息");
			for (ShopSearchData data : shopList) {
				System.out.println("shopId : " + data.getShopId());
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("shopId", data.getShopId());
				doc.addField("shopName", data.getShopName());
				doc.addField("sellerId", data.getSellerId());
				doc.addField("status", data.getStatus());
				doc.addField("shopUrl", data.getShopUrl());
				doc.addField("logoUrl", data.getLogoUrl());
				doc.addField("keyword", data.getKeyword());
				doc.addField("introduce",
						data.getIntroduce() != null ? data.getIntroduce() : " ");
				doc.addField("disclaimer", data.getDisclaimer());
				doc.addField("passTime", data.getPassTime());
				doc.addField("endTime", data.getEndTime());
				doc.addField("businessType", data.getBusinessType());
				doc.addField("shopType", data.getShopType());
				doc.addField("cids", data.getCids());
				doc.addField("sellerName", data.getSellerName());
				doc.addField("locationAddress", data.getLocationAddress());
				doc.addField("sellerType", data.getSellerType());
				doc.addField("shopQQ", data.getShopQQ());
				shopSolrList.add(doc);
			}
			flush(shopSolrList);
		}
	}

	private boolean filterInvalidData(ShopDTO shop) throws Exception {
		boolean filterFlag = true;
		filterFlag = shopExportService
				.queryShopStatusByShopId(shop.getShopId());
		if (filterFlag) {
			filterFlag = removeSolr(String.valueOf(shop.getShopId()));
		}
		return filterFlag;
	}

	private List<ShopSearchData> shopDataAssembling(List<ShopDTO> list,
			Date lastSyncTime) throws Exception {
		List<ShopSearchData> results = new ArrayList<ShopSearchData>();
		for (ShopDTO shop : list) {
			try {
				boolean filterFlag = filterInvalidData(shop);
				if (!filterFlag) {
					continue;
				}
				ShopSearchData data = new ShopSearchData();
				data.setShopId(shop.getShopId());
				data.setShopName(shop.getShopName());
				data.setSellerId(shop.getSellerId());
				data.setStatus(shop.getStatus());
				data.setShopUrl(shop.getShopUrl());
				data.setLogoUrl(shop.getLogoUrl());
				//data.setKeyword(shop.getKeyword());
				data.setIntroduce(shop.getIntroduce());
				data.setDisclaimer(shop.getDisclaimer());
				data.setPassTime(shop.getPassTime());
				data.setEndTime(shop.getEndTime());
				data.setBusinessType(shop.getBusinessType());
				data.setShopType(shop.getShopType());
				if(null != shop.getSellerId()){
					MemberCompanyInfoDTO memberCompanyInfo = memberCompanyInfoService.queryMemberCompanyInfoBySellerId(shop.getSellerId());
					String sellerType = memberCompanyInfoService.queryMemberBaseInfoBySellerId(shop.getSellerId());
					if(null == memberCompanyInfo || StringUtils.isEmpty(sellerType)){
						continue;
					}
					data.setSellerName(memberCompanyInfo.getCompanyName());
					data.setSellerType(sellerType);
					if (StringUtils.isNotEmpty(memberCompanyInfo.getLocationProvince())
							&& StringUtils.isNotEmpty(memberCompanyInfo.getLocationCity())) {
						String locationProvince = shopExportService
								.getAreaName(memberCompanyInfo.getLocationProvince());
						String locationCity = shopExportService.getAreaName(memberCompanyInfo
								.getLocationCity());
						data.setLocationAddress(locationProvince + "   "
								+ locationCity);
					}
				}
				String cids = removalData(shopExportService
						.queryCidNameAndCidByShopId(shop.getShopId()));
				String shopQQ = shopExportService.queryShopQQByShopId(data
						.getShopId());
				data.setShopQQ(shopQQ);
				data.setCids(cids);
				results.add(data);
			} catch (Exception e) {
				LOG.error("shopDataAssembling is error", e);
				continue;
			}
		}
		return results;
	}
}

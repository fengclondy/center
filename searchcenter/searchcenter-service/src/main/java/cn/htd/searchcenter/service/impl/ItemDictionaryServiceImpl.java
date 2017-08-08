package cn.htd.searchcenter.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.ItemDictionaryDAO;
import cn.htd.searchcenter.domain.BaseAddressDTO;
import cn.htd.searchcenter.domain.HotWordDTO;
import cn.htd.searchcenter.domain.ItemAttrDTO;
import cn.htd.searchcenter.domain.ItemAttrValueDTO;
import cn.htd.searchcenter.service.ItemDictionaryService;

@Service("itemDictionaryServiceImpl")
public class ItemDictionaryServiceImpl implements ItemDictionaryService {

	@Resource
	private ItemDictionaryDAO itemDictionaryDao;

	@Override
	public String queryBelongRelationship(Long sellerId) {
		return itemDictionaryDao.queryBelongRelationship(sellerId);
	}

	@Override
	public String querySellerName(Long sellerId) {
		return itemDictionaryDao.querySellerName(sellerId);
	}

	@Override
	public boolean queryItemStatus(Long itemId) {
		boolean isUpper = false;
		String itemStatus = itemDictionaryDao.queryItemStatus(itemId);
		if ("5".equals(itemStatus)) {
			isUpper = true;
		}
		return isUpper;
	}

	@Override
	public String queryBoxRelationship(Long sellerId) {
		return itemDictionaryDao.queryBoxRelationship(sellerId);
	}

	@Override
	public boolean queryItemVisable(Long itemId, int isBox) {
		String visable = itemDictionaryDao.queryItemVisable(itemId, isBox);
		if ("1".equals(visable)) {
			String itemStatus = itemDictionaryDao.queryItemStatus(itemId);
			if("5".equals(itemStatus)){
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String queryAreaByCode(String areaCode) {
		return itemDictionaryDao.queryAreaByCode(areaCode);
	}

	@Override
	public String querySellerTypeById(Long sellerId) {
		return itemDictionaryDao.querySellerTypeById(sellerId);
	}

	@Override
	public String queryDescribeByItemId(Long itemId) throws Exception {
		return itemDictionaryDao.queryDescribeByItemId(itemId);
	}

	@Override
	public Long querySalesVolumeByItemCode(String itemCode) throws Exception {
		String salesVolume = itemDictionaryDao
				.querySalesVolumeByItemCode(itemCode);
		if (StringUtils.isNotEmpty(salesVolume)) {
			return Long.parseLong(salesVolume);
		} else {
			return 0L;
		}
	}

	@Override
	public BigDecimal queryExternalItemPrice(Long itemId) throws Exception {
		return itemDictionaryDao.queryExternalItemPrice(itemId);
	}

	@Override
	public List<String> querySalesAreaByItemId(Long itemId, int isBox)
			throws Exception {
		List<String> areaList = itemDictionaryDao.querySalesAreaByItemId(
				itemId, isBox);
		if (null == areaList || areaList.size() == 0) {
			return null;
		}
		return areaList;
	}

	@Override
	public boolean queryIsSalesWholeCountry(Long itemId, int isBox)
			throws Exception {
		Integer isSalesWholeCountry = itemDictionaryDao
				.queryIsSalesWholeCountry(itemId, isBox);
		if (null != isSalesWholeCountry) {
			if (isSalesWholeCountry == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<BaseAddressDTO> queryAreaThreeAndSecond() throws Exception {
		return itemDictionaryDao.queryAreaThreeAndSecond();
	}

	@Override
	public boolean queryJDItemVisable(Long itemId) throws Exception {
		boolean visableFlag = false;
		int jdItemVisable = itemDictionaryDao.queryJDItemVisable(itemId);
		if (jdItemVisable == 1) {
			visableFlag = true;
		}
		return visableFlag;
	}

	@Override
	public boolean querySeckillItemStatus(Long itemId) throws Exception {
		boolean visableFlag = false;
		int seckillVisable = itemDictionaryDao.querySeckillItemStatus(itemId,
				new Timestamp(System.currentTimeMillis()));
		if (seckillVisable == 1) {
			visableFlag = true;
		}
		return visableFlag;
	}

	@Override
	public int queryItemVisableCount(Long itemId, int isBox) throws Exception {
		int visableCount = itemDictionaryDao.queryItemVisableCount(itemId);
		int shelvesFlag = 0;
		if (visableCount != 0) {
			if (isBox == 0) {
				if (visableCount == 1) {
					shelvesFlag = 1;
				} else {
					shelvesFlag = 2;
				}
			} else {
				if (visableCount == 1) {
					shelvesFlag = 3;
				} else {
					shelvesFlag = 4;
				}
			}
		} else {
			shelvesFlag = 0;
		}
		return shelvesFlag;
	}

	@Override
	public String queryHotWordByItemCode(String itemCode) throws Exception {
		String returnMap = "";
		List<HotWordDTO> hotWordList = itemDictionaryDao
				.queryHotWordByItemCode(itemCode);
		for (HotWordDTO hotWord : hotWordList) {
			if (StringUtils.isNotEmpty(hotWord.getHotWord())) {
				returnMap = hotWord.getHotWord().replace(",", " ");
			}
		}
		return returnMap;
	}

	@Override
	public String queryItemPicture(Long itemId) throws Exception {
		String pictureURL = itemDictionaryDao.queryItemPicture(itemId);
		if (StringUtils.isNotEmpty(pictureURL)) {
			return pictureURL;
		} else {
			String otherPictureURL = itemDictionaryDao.queryOtherItemPicture(itemId);
			if(StringUtils.isNotEmpty(otherPictureURL)){
				return otherPictureURL;
			}else{
				return "";
			}
		}
	}

	@Override
	public String queryCidNameByCid(Long cid) throws Exception {
		String cidName = itemDictionaryDao.queryCidNameByCid(cid);
		if (StringUtils.isNotEmpty(cidName)) {
			return cidName;
		} else {
			return null;
		}
	}

	@Override
	public String queryBrandNameByBrandId(Long brandId) throws Exception {
		String brandName = itemDictionaryDao.queryBrandNameByBrandId(brandId);
		if (StringUtils.isNotEmpty(brandName)) {
			return brandName;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> queryItemAttrByCid(List<String> cidList)
			throws Exception {
		Map<String, String> returnMap = new HashMap<String, String>();
		List<ItemAttrDTO> attrList = new ArrayList<ItemAttrDTO>();
		List<ItemAttrValueDTO> attrValueList = new ArrayList<ItemAttrValueDTO>();
		for (String cid : cidList) {
			ItemAttrDTO attr = new ItemAttrDTO();
			List<ItemAttrDTO> attrListR = itemDictionaryDao
					.queryItemAttrByCid(cid);
			String attrId = "";
			String attrName = "";
			for (ItemAttrDTO itemAttrDTO : attrListR) {
				if (StringUtils.isNotEmpty(itemAttrDTO.getAttrId())
						&& StringUtils.isNotEmpty(itemAttrDTO.getAttrName())) {
					attrId += itemAttrDTO.getAttrId() + ",";
					attrName += itemAttrDTO.getAttrName() + ",";
					
					List<ItemAttrValueDTO> attrValueListR = itemDictionaryDao
							.queryItemAttrValueByCid(itemAttrDTO.getAttrId(), cid);
					ItemAttrValueDTO attrValue = new ItemAttrValueDTO();
					String valueId = "";
					String valueName = "";
					for (ItemAttrValueDTO itemAttrValueDTO : attrValueListR) {
						if (StringUtils.isNotEmpty(itemAttrValueDTO.getValueId())
								&& StringUtils.isNotEmpty(itemAttrValueDTO
										.getValueName())) {
							valueId += itemAttrValueDTO.getValueId() + ",";
							valueName += itemAttrValueDTO.getValueName() + ",";
						}
					}
					if (!"".equals(valueId) && !"".equals(valueName)) {
						attrValue.setAttrId(itemAttrDTO.getAttrId());
						attrValue.setValueId(valueId.substring(0, valueId.length() - 1));
						attrValue.setValueName(valueName.substring(0, valueName.length() - 1));
						attrValueList.add(attrValue);
					}
				}
			}
			if (!"".equals(attrId) && !"".equals(attrName)) {
				attr.setCid(cid);
				attr.setAttrId(attrId.substring(0, attrId.length() - 1));
				attr.setAttrName(attrName.substring(0, attrName.length() - 1));
				attrList.add(attr);
			}
		}
		if (null != attrList && null != attrValueList && attrList.size() > 0
				&& attrValueList.size() > 0) {
			for (ItemAttrDTO attr : attrList) {
				Map<String, Object> attrMap = new HashMap<String, Object>();
				try {
					if (StringUtils.isNotEmpty(attr.getAttrId())
							&& StringUtils.isNotEmpty(attr.getAttrName())) {
						String[] attrIds = attr.getAttrId().split(",");
						String[] attrNames = attr.getAttrName().split(",");
						for (int i = 0; i < attrIds.length; i++) {
							for (ItemAttrValueDTO attrValue : attrValueList) {
								if (StringUtils.isNotEmpty(attrValue
										.getValueId())
										&& StringUtils.isNotEmpty(attrValue
												.getValueName())) {
									if (null != attrIds[i]
											&& attrIds[i].equals(attrValue
													.getAttrId())) {
										attrMap.put(
												attrIds[i] + ":" + attrNames[i],
												attrValue.getValueId()
														+ ":"
														+ attrValue
																.getValueName());
									}
								}
							}
						}
						if (null != attrMap && !attrMap.isEmpty())
							for (String key : attrMap.keySet()) {
								List<String> valueList = new ArrayList<String>();
								String[] valueIds = ((attrMap.get(key)
										.toString()).split(":")[0]).split(",");
								String[] valueNames = ((attrMap.get(key)
										.toString()).split(":")[1]).split(",");
								for (int i = 0; i < valueIds.length; i++) {
									valueList.add(valueIds[i] + ":"
											+ valueNames[i]);
								}
								attrMap.put(key, valueList);
							}
					}
					returnMap.put(attr.getCid(), JSONObject.fromObject(attrMap)
							.toString());
				} catch (Exception e) {
					continue;
				}
			}
		}
		return returnMap;
	}

	@Override
	public List<String> queryItemCategoryCidBySyncTime(Date syncTime) {
		return itemDictionaryDao.queryItemCategoryCidBySyncTime(syncTime);
	}

	@Override
	public Integer queryIsPublicSaleWholeCountry(Long itemId, int isBox)
			throws Exception {
		Integer isPublicSaleWholeCountry = itemDictionaryDao.queryIsPublicSaleWholeCountry(itemId, isBox);
		if(null == isPublicSaleWholeCountry){
			return 0;
		}else{
			return isPublicSaleWholeCountry.intValue();
		}
	}
}

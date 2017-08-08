package cn.htd.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.dto.AddressInfo;
import cn.htd.common.dto.DictionaryInfo;

/**
 * 取得地址信息
 * 
 * @author jiangkun
 */
@Component("addressUtils")
public class AddressUtils {

	// Redis地址数据
	private static final String REDIS_ADDRESS = "B2B_MIDDLE_ADDRESS";
	// Redis地址关系数据
	private static final String REDIS_ADDRESS_RELATION = "B2B_MIDDLE_ADDRESS_RELATION";

	@Resource
	private RedisDB redisDB;

	@Resource
	private DictionaryUtils dictionary;

	/**
	 * 根据地址编码取得地址名称
	 * 
	 * @param addressCode
	 * @return
	 */
	public AddressInfo getAddressName(String addressCode) {
		AddressInfo addressInfo = new AddressInfo();
		String addressStr = "";
		String[] addressArr = null;
		if (StringUtils.isBlank(addressCode)) {
			return null;
		}
		addressStr = redisDB.getHash(REDIS_ADDRESS, addressCode);
		if (StringUtils.isBlank(addressStr)) {
			return null;
		}

		addressInfo.setCode(addressCode);
		addressInfo.setName(addressStr);
		if (addressStr.indexOf("&") >= 0) {
			addressArr = addressStr.split("&");
			addressInfo.setName(addressArr[0]);
			if (addressArr.length > 1) {
				addressInfo.setLevel(Integer.parseInt(addressArr[1]));
			}
			if (addressArr.length > 2) {
				addressInfo.setParentCode(addressArr[2]);
			}
		}
		return addressInfo;
	}

	/**
	 * 取得下一级别地址列表
	 * 
	 * @param parentAddressCode
	 * @return
	 */
	public List<AddressInfo> getAddressList(String parentAddressCode) {
		List<AddressInfo> addressList = new ArrayList<AddressInfo>();
		AddressInfo addressInfo = null;
		String addressCode = parentAddressCode;
		String subAddressStr = "";
		String[] subAddressArr = null;

		if (StringUtils.isBlank(parentAddressCode)) {
			addressCode = "province";
		}
		subAddressStr = redisDB.getHash(REDIS_ADDRESS_RELATION, addressCode);
		if (StringUtils.isBlank(subAddressStr)) {
			return null;
		}
		subAddressArr = subAddressStr.split(",");
		for (String subAddressCode : subAddressArr) {
			addressInfo = getAddressName(subAddressCode);
			if (addressInfo != null) {
				addressList.add(addressInfo);
			}
		}
		return addressList;
	}

	/**
	 * 判断子地址编码是否是父地址编码的下属
	 * 
	 * @param parentAddressCode
	 * @param subAddressCode
	 * @return
	 */
	public boolean checkAddressRelation(String parentAddressCode, String subAddressCode) {
		AddressInfo parentAddress = getAddressName(parentAddressCode);
		AddressInfo subAddress = getAddressName(subAddressCode);
		List<DictionaryInfo> addressLevelList = null;
		int parentLevel = 0;
		int subLevel = 0;
		int maxLoop = 0;
		boolean hasParentLevel = false;
		boolean hasSubLevel = false;
		String subParentCode = subAddress.getParentCode();
		String subAddrCode = subAddressCode;
		String subAddressStr = "";
		if (parentAddress == null || subAddress == null) {
			return false;
		}
		if (StringUtils.isEmpty(subParentCode)) {
			return false;
		}
		parentLevel = parentAddress.getLevel();
		subLevel = subAddress.getLevel();
		if (parentLevel == 0 || subLevel == 0) {
			return false;
		}
		addressLevelList = dictionary.getDictionaryOptList(DictionaryConst.TYPE_ADDRESS_LEVEL);
		if (addressLevelList != null) {
			for (DictionaryInfo addressLevel : addressLevelList) {
				if (String.valueOf(parentLevel).equals(addressLevel.getValue())) {
					hasParentLevel = true;
				}
				if (String.valueOf(subLevel).equals(addressLevel.getValue())) {
					hasSubLevel = true;
				}
			}
			if (!(hasParentLevel && hasSubLevel)) {
				return false;
			}
		}
		if (parentLevel >= subLevel) {
			return false;
		}
		maxLoop = subLevel - parentLevel;
		while (maxLoop > 0) {
			parentAddress = getAddressName(subParentCode);
			subAddressStr = redisDB.getHash(REDIS_ADDRESS_RELATION, subParentCode);
			if (StringUtils.isEmpty(subAddressStr)) {
				return false;
			}
			subAddressStr = "," + subAddressStr + ",";
			if (!subAddressStr.contains("," + subAddrCode + ",")) {
				return false;
			}
			subAddrCode = subParentCode;
			subParentCode = parentAddress.getParentCode();
			maxLoop--;
		}
		return true;
	}
}
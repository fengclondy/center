package cn.htd.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.dto.DictionaryInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 取得字典信息
 * 
 * @author jiangkun
 */

@Component("dictionaryUtils")
public class DictionaryUtils {

	// Redis字典数据
	private static final String REDIS_DICTIONARY = "B2B_MIDDLE_DICTIONARY";
	// Redis字典类型数据
	private static final String REDIS_DICTIONARY_TYPE = "B2B_MIDDLE_DICTIONARY_TYPE";
	//----- add by jiangkun for 性能优化 on 20171011 start -----
	private static Map<String, String> DICTIONARY_TYPE_MAP = new HashMap<String, String>();
	private static Map<String, Map<String, String>> DICTIONARY_VALUE_MAP  = new HashMap<String, Map<String, String>>();
	private static long flushedTime = System.currentTimeMillis();
	private static final List<String> EXCEPT_CACHE_TYPE_LIST = Arrays.asList(DictionaryConst.TYPE_ITEM_UNIT);
	//----- add by jiangkun for 性能优化 on 20171011 end -----
	@Resource
	private RedisDB redisDB;

	//----- add by jiangkun for 性能优化 on 20171011 start -----
	/**
	 * 初始化字典静态变量
     *
	 * @throws Exception
	 */
	public void init() throws Exception {
		String key = "";
		String value = "";
		String dictionaryValKey = "";
		Map<String, String> dictValueMap = null;
		Map<String, String> dictTypeMap = redisDB.getHashOperations(REDIS_DICTIONARY_TYPE);

		if (dictTypeMap != null && !dictTypeMap.isEmpty() ) {
			for (Map.Entry<String, String> entry : dictTypeMap.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				DICTIONARY_TYPE_MAP.put(key, value);
				dictionaryValKey = REDIS_DICTIONARY + "_" + key;
				dictValueMap = redisDB.getHashOperations(dictionaryValKey);
				if (dictValueMap != null && !dictValueMap.isEmpty()) {
					DICTIONARY_VALUE_MAP.put(key, dictValueMap);
				}
			}
		}
	}

	/**
	 * 取得字典信息
	 *
	 * @param typeCode
	 * @return
	 */
	private Map<String, String> getDictAllValueMap(String typeCode) {
		Map<String, String> dictValueMap = null;

		if (!EXCEPT_CACHE_TYPE_LIST.contains(typeCode) && DICTIONARY_VALUE_MAP.containsKey(typeCode)) {
			return DICTIONARY_VALUE_MAP.get(typeCode);
		}
		dictValueMap = redisDB.getHashOperations(REDIS_DICTIONARY + "_" + typeCode);
		if (dictValueMap != null && !dictValueMap.isEmpty()) {
			DICTIONARY_VALUE_MAP.put(typeCode, dictValueMap);
		}
		return dictValueMap;
	}

	/**
	 * 每4小时清除字典的缓存
	 */
	private void flushDictCacheEvery4H() {
		boolean needFlushFlag = (System.currentTimeMillis() - flushedTime) > (1000 * 60);
		if (needFlushFlag) {
			synchronized (DictionaryUtils.class) {
				needFlushFlag = (System.currentTimeMillis() - flushedTime) > (1000 * 60);
				if (needFlushFlag) {
					DICTIONARY_TYPE_MAP = new HashMap<String, String>();
					DICTIONARY_VALUE_MAP = new HashMap<String, Map<String, String>>();
					flushedTime = System.currentTimeMillis();
				}
			}
		}
	}
	//----- add by jiangkun for 性能优化 on 20171011 end -----

	/**
	 * 根据字典类型和字典值取得字典名称-字典备考
	 * 
	 * @param typeCode
	 * @param value
	 * @return
	 */
	public String getDescribeByValue(String typeCode, String value) {
		List<DictionaryInfo> resultList = null;
		String describe = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		resultList = getDictionaryNameList(typeCode);
		if (resultList != null && !resultList.isEmpty()) {
			for (DictionaryInfo dictInfo : resultList) {
				if (value.equals(dictInfo.getValue())) {
					describe = dictInfo.getName();
					describe += StringUtils.isEmpty(dictInfo.getComment()) ? "" : "-" + dictInfo.getComment();
					break;
				}
			}
		}
		return describe;
	}

	/**
	 * 根据字典类型和字典编码取得字典名称
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public String getNameByCode(String typeCode, String code) {
		List<DictionaryInfo> resultList = null;
		String name = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(code)) {
			return "";
		}
		resultList = getDictionaryNameList(typeCode);
		if (resultList != null && !resultList.isEmpty()) {
			for (DictionaryInfo dictInfo : resultList) {
				if (code.equals(dictInfo.getCode())) {
					name = dictInfo.getName();
					break;
				}
			}
		}
		return name;
	}

	/**
	 * 根据字典类型和字典值取得字典名称
	 * 
	 * @param typeCode
	 * @param value
	 * @return
	 */
	public String getNameByValue(String typeCode, String value) {
		List<DictionaryInfo> resultList = null;
		String name = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		resultList = getDictionaryNameList(typeCode);
		if (resultList != null && !resultList.isEmpty()) {
			for (DictionaryInfo dictInfo : resultList) {
				if (value.equals(dictInfo.getValue())) {
					name = dictInfo.getName();
					break;
				}
			}
		}
		return name;
	}

	/**
	 * 根据字典类型取得可以展示的名称项目
	 * 
	 * @param typeCode
	 * @return
	 */
	private List<DictionaryInfo> getDictionaryNameList(String typeCode) {
		List<DictionaryInfo> resultList = new ArrayList<DictionaryInfo>();
		DictionaryInfo dictInfo = null;
		Map<String, String> vopMap = null;
		Iterator<String> key = null;
		String keyStr = "";
		String[] keyArr = null;
		String[] keyNameArr = null;
		String keyName = "";
		String keyComment = "";
		if (StringUtils.isBlank(typeCode)) {
			return resultList;
		}
		//----- modify by jiangkun for 性能优化 on 20171011 start -----
//		vopMap = redisDB.getHashOperations(REDIS_DICTIONARY + "_" + typeCode);
		flushDictCacheEvery4H();
		vopMap = getDictAllValueMap(typeCode);
		//----- modify by jiangkun for 性能优化 on 20171011 end -----
		if (vopMap != null && vopMap.size() > 0) {
			key = vopMap.keySet().iterator();
			while (key.hasNext()) {
				keyComment = "";
				keyStr = key.next();
				keyArr = keyStr.split("&");
				dictInfo = new DictionaryInfo();
				dictInfo.setCode(keyArr[0]);
				dictInfo.setValue(keyArr[1]);
				keyName = vopMap.get(keyStr);
				if (keyName.indexOf("&") >= 0) {
					keyNameArr = keyName.split("&");
					keyName = keyNameArr[0];
					if (keyNameArr.length > 1) {
						keyComment = keyNameArr[1];
					}
				}
				dictInfo.setName(keyName);
				dictInfo.setComment(keyComment);
				resultList.add(dictInfo);
			}
		}
		return resultList;
	}

	/**
	 * 根据字典类型和字典编码取得字典值
	 * 
	 * @param typeCode
	 * @param value
	 * @return
	 */
	public String getCodeByValue(String typeCode, String value) {
		List<DictionaryInfo> dictList = null;
		String code = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		dictList = getDictionaryOptList(typeCode);
		if (dictList != null && dictList.size() > 0) {
			for (DictionaryInfo dict : dictList) {
				if (value.equals(dict.getValue())) {
					code = dict.getCode();
					break;
				}
			}
		}
		return code;
	}

	/**
	 * 根据字典类型和字典值取得字典编码
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public String getValueByCode(String typeCode, String code) {
		List<DictionaryInfo> dictList = null;
		String value = "";
		if (StringUtils.isBlank(typeCode)) {
			return "";
		}
		if (StringUtils.isBlank(code)) {
			return "";
		}
		dictList = getDictionaryOptList(typeCode);
		if (dictList != null && dictList.size() > 0) {
			for (DictionaryInfo dict : dictList) {
				if (code.equals(dict.getCode())) {
					value = dict.getValue();
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 根据字典类型取得字典值项目
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<DictionaryInfo> getDictionaryOptList(String typeCode) {
		List<DictionaryInfo> resultList = new ArrayList<DictionaryInfo>();
		DictionaryInfo dict = null;
		String dictTypeStr = "";
		String[] dictTypeArr = null;
		String[] codeValueArr = null;
		String[] codeNameArr = null;
		String code = "";
		String value = "";
		String name = "";
		String comment = "";
		if (StringUtils.isBlank(typeCode)) {
			return null;
		}
		//----- modify by jiangkun for 性能优化 on 20171011 start -----
//		dictTypeStr = redisDB.getHash(REDIS_DICTIONARY_TYPE, typeCode);
//		if (StringUtils.isBlank(dictTypeStr)) {
//			return null;
//		}
		Map<String, String> dictValueMap = null;
		boolean hasUpdDictValMapFlg = false;
		flushDictCacheEvery4H();
		if (!EXCEPT_CACHE_TYPE_LIST.contains(typeCode) && DICTIONARY_TYPE_MAP.containsKey(typeCode)) {
			dictTypeStr = DICTIONARY_TYPE_MAP.get(typeCode);
		} else {
			dictTypeStr = redisDB.getHash(REDIS_DICTIONARY_TYPE, typeCode);
			if (StringUtils.isBlank(dictTypeStr)) {
				return null;
			}
			DICTIONARY_TYPE_MAP.put(typeCode, dictTypeStr);
		}
		dictValueMap = getDictAllValueMap(typeCode);
		if (dictValueMap == null || dictValueMap.isEmpty()) {
			return null;
		}
		//----- modify by jiangkun for 性能优化 on 20171011 end -----
		dictTypeArr = dictTypeStr.split(",");
		if (dictTypeArr != null && dictTypeArr.length > 0) {
			for (String codeValue : dictTypeArr) {
				comment = "";
				codeValueArr = codeValue.split("&");
				code = codeValueArr[0];
				value = codeValueArr[1];
				//----- modify by jiangkun for 性能优化 on 20171011 start -----
//				name = redisDB.getHash(REDIS_DICTIONARY + "_" + typeCode, codeValue);
                if (dictValueMap.containsKey(codeValue)) {
                	name = dictValueMap.get(codeValue);
				} else {
					name = redisDB.getHash(REDIS_DICTIONARY + "_" + typeCode, codeValue);
					if (StringUtils.isEmpty(name)) {
						name = "";
					}
					hasUpdDictValMapFlg = true;
					dictValueMap.put(codeValue, name);
				}
				//----- modify by jiangkun for 性能优化 on 20171011 end -----
				if (name.indexOf("&") >= 0) {
					codeNameArr = name.split("&");
					name = codeNameArr[0];
					if (codeNameArr.length > 1) {
						comment = codeNameArr[1];
					}
				}
				dict = new DictionaryInfo();
				dict.setCode(code);
				dict.setName(name);
				dict.setValue(value);
				dict.setComment(comment);
				resultList.add(dict);
			}
			//----- add by jiangkun for 性能优化 on 20171011 start -----
			if (hasUpdDictValMapFlg) {
				DICTIONARY_VALUE_MAP.put(typeCode, dictValueMap);
			}
			//----- add by jiangkun for 性能优化 on 20171011 start -----
		}
		return resultList;
	}

	/**
	 * 根据类型和名称获取对应字典类型对象
	 * 
	 * @param typeCode
	 * @param name
	 * @return
	 */
	public DictionaryInfo getDictionaryByName(String typeCode, String name) {
		List<DictionaryInfo> dictionaryList = null;
		String dictionaryName = "";
		if (StringUtils.isBlank(typeCode)) {
			return null;
		}
		if (StringUtils.isBlank(name)) {
			return null;
		}
		dictionaryList = getDictionaryOptList(typeCode);
		if (dictionaryList == null || dictionaryList.isEmpty()) {
			return null;
		}
		for (DictionaryInfo dictionary : dictionaryList) {
			dictionaryName = StringUtils.trimToEmpty(dictionary.getName());
			if (StringUtils.trimToEmpty(name).equals(dictionaryName)) {
				return dictionary;
			}
		}
		return null;
	}
}
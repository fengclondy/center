package cn.htd.basecenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.Constants;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.dto.IllegalCharacterDTO;
import cn.htd.basecenter.service.IllegalCharacterService;
import cn.htd.basecenter.service.SensitiveWordFilterService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;

/**
 * <p>
 * Description: [处理敏感词，方法接口类]
 * </p>
 */
@Service("sensitiveWordFilterService")
public class SensitiveWordFilterServiceImpl implements SensitiveWordFilterService {
	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordFilterServiceImpl.class);
	private static final char endTag = (char) (1); // 关键词结束符
	private static Map<Character, HashMap> filterMap = new HashMap<Character, HashMap>(1024);// 敏感词库

	@Resource
	private IllegalCharacterService illegalCharacterService;

	@Override
	public ExecuteResult<String> initSensitiveWord() {
		ExecuteResult<String> er = new ExecuteResult<String>();
		// 读取敏感词库
		IllegalCharacterDTO illegalCharacterDTO = new IllegalCharacterDTO();
		illegalCharacterDTO.setDeleteFlag(YesNoEnum.NO.getValue());
		DataGrid<IllegalCharacterDTO> dataList = illegalCharacterService.queryIllegalCharacterList(illegalCharacterDTO,
				null);
		if (null == dataList || null == dataList.getRows()) {
			logger.info("初始化敏感字数据库读取发生错误！");
			er.addErrorMsg("初始化敏感字数据库读取发生错误！");
			return er;
		}

		List<IllegalCharacterDTO> illcList = dataList.getRows();
		String[] filterWordList = new String[illcList.size()];
		int i = 0;
		for (IllegalCharacterDTO ic : illcList) {
			String txt = ic.getContent();
			if (null != txt && 0 < txt.trim().length()) {
				filterWordList[i] = txt;
				i++;
			}
		}

		if (null != filterWordList && 0 < filterWordList.length) {
			init(filterWordList);// 刷新
		}

		er.setResult("初始化成功！");
		return er;
	}

	@Override
	public ExecuteResult<String> handle(String swf) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		// 参数验证
		if (StringUtils.isBlank(swf)) {
			er.addErrorMessage("参数传递错误，请验证后再尝试");
			return er;
		}
		// 初始化字符库
		if (filterMap.isEmpty()) {
			initSensitiveWord();
		}
		// 过滤并返回结果
		String swfo = getFilterString(swf, Constants.SENSITIVE_WORD_REPLACE_CHAR);// 替换完成结果
		er.setResult(swfo);
		return er;
	}

	/**
	 * <p>
	 * Discription:[加载敏感词库]
	 * </p>
	 */
	private void init(String[] filterWordList) {
		for (String filterWord : filterWordList) {
			char[] charArray = filterWord.trim().toCharArray();
			int len = charArray.length;
			if (len > 0) {
				Map<Character, HashMap> subMap = filterMap;
				for (int i = 0; i < len - 1; i++) {
					Map<Character, HashMap> obj = subMap.get(charArray[i]);
					if (obj == null) {
						int size = (int) Math.max(2, 16 / Math.pow(2, i));
						HashMap<Character, HashMap> subMapTmp = new HashMap<Character, HashMap>(size);
						subMap.put(charArray[i], subMapTmp);
						subMap = subMapTmp;
					} else {
						subMap = obj;
					}
				}
				Map<Character, HashMap> obj = subMap.get(charArray[len - 1]);
				if (obj == null) {
					int size = (int) Math.max(2, 16 / Math.pow(2, len - 1));
					HashMap<Character, HashMap> subMapTmp = new HashMap<Character, HashMap>(size);
					subMapTmp.put(endTag, null);
					subMap.put(charArray[len - 1], subMapTmp);
				} else {
					obj.put(endTag, null);
				}
			}
		}
	}

	/**
	 * <p>
	 * Discription:[关键字替换成指定字符串]
	 * </p>
	 */
	private static String getFilterString(String info, String replaceTag) {
		if (StringUtils.isBlank(info) || StringUtils.isBlank(replaceTag)) {
			return info;
		}
		char[] charArray = info.toCharArray();
		int len = charArray.length;
		StringBuilder newInfo = new StringBuilder();
		int i = 0;
		while (i < len) {
			int end = -1;
			int index;
			Map<Character, HashMap> sub = filterMap;
			for (index = i; index < len; index++) {
				sub = sub.get(charArray[index]);
				if (sub == null) {
					if (end == -1) {
						newInfo.append(charArray[i]);
						i++;
						break;
					} else {
						for (int j = i; j <= end; j++) {
							newInfo.append(replaceTag);
						}
						i = end + 1;
						break;
					}
				} else {
					if (sub.containsKey(endTag)) {
						end = index;
					}
				}
			}
			if (index >= len) {
				if (end == -1) {
					newInfo.append(charArray[i]);
					i++;
				} else {
					for (int j = i; j <= end; j++) {
						newInfo.append(replaceTag);
					}
					i = end + 1;
				}
			}
		}
		return newInfo.toString();
	}

}

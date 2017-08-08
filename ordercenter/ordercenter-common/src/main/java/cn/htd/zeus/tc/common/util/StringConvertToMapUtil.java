package cn.htd.zeus.tc.common.util;

import java.util.HashMap;
import java.util.Map;

/*
 * 将json格式解析成map
 * 注：json格式的字符串中间不能带任何双引号
 */
public class StringConvertToMapUtil {
	
	/*
	 * 将格式{erpDownTimes=0,ddd=w2}解析成map
	 * 注：json格式的字符串中间不能带任何双引号
	 */
	public static Object getValue(String param) {
		Map map = new HashMap();
		String str = "";
		String key = "";
		Object value = "";
		char[] charList = param.toCharArray();
		boolean valueBegin = false;
		for (int i = 0; i < charList.length; i++) {
			char c = charList[i];
			if (c == '{') {
				if (valueBegin == true) {
					value = getValue(param.substring(i, param.length()));
					i = param.indexOf('}', i) + 1;
					map.put(key, value);
				}
			} else if (c == '=') {
				valueBegin = true;
				key = str;
				str = "";
			} else if (c == ',') {
				valueBegin = false;
				value = str;
				str = "";
				map.put(key, value);
			} else if (c == '}') {
				if (str != "") {
					value = str;
				}
				map.put(key, value);
				return map;
			} else if (c != ' ') {
				str += c;
			}
		}
		return map;
	}
}

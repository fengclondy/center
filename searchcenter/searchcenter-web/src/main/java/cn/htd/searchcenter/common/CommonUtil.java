package cn.htd.searchcenter.common;

import org.apache.commons.lang3.StringUtils;
public class CommonUtil {
	
	public static String escapeQueryChars(String s) {
		StringBuilder sb = new StringBuilder();  
		try {
		    for (int i = 0; i < s.length(); i++) {  
		      char c = s.charAt(i);  
		      if (c == '\\' || c == '+' || c == '!' || c == ':'  
		        || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'  
		        || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'  
		        || Character.isWhitespace(c)) {  
		        sb.append('\\');
		      }  
		      sb.append(c);  
		    }  
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return sb.toString();  
	  }  

	public static String escapeSolrKeyword(String keyword) {
		String value = "";
		if (StringUtils.isNotEmpty(keyword)) {
			value = CommonUtil.escapeQueryChars(keyword);
			if (value.indexOf("AND") >= 0) {
				value = value.replace("AND", "A N D");
			}
			if (value.indexOf("OR") >= 0) {
				value = value.replace("OR", "O R");
			}
		}
		return value;
	}
}

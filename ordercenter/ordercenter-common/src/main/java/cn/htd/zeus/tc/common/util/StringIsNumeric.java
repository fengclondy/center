package cn.htd.zeus.tc.common.util;

import org.apache.commons.lang.StringUtils;

public class StringIsNumeric {
	/*
	 * 判断字符串是否是数字,是返回true
	 */
	public static boolean isNumeric(String str){ 
		if(StringUtils.isEmpty(str)){
		    return false; 
		}
	   for (int i = str.length();--i>=0;){    
		  if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		return true;  
	}  
}
